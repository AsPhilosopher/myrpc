package com.wheel.server;

import com.wheel.dto.RequestData;
import com.wheel.server.loadbalance.LoadBalanceStrategy;
import com.wheel.server.loadbalance.LoadBalanceUtils;
import com.wheel.server.loadbalance.impl.SimpleLoadBalanceStrategy;
import com.wheel.utils.BufferUtils;
import com.wheel.utils.SerializeUtils;
import com.wheel.utils.ThreadPoolFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.concurrent.*;

/**
 * Created with IntelliJ IDEA
 * Date: 2018/1/1
 * Time: 上午11:01
 *
 * @author 陈樟杰
 */
public class NIORPCServer implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(NIORPCServer.class);

    private final Integer NUMBER = 3;

    private final Integer PORT = 8899;

    private final String HOSTNAME = "localhost";

    /**
     * 选择器，主要用来监控各个通道的事件
     */
    private Selector selector;
    /**
     * 服务器通道
     */
    private ServerSocketChannel serversocket;
    /**
     * 连接列表
     */
    private SocketChannel[] socketChannels;
    /**
     * 缓冲区大小
     */
    private final Integer SIZE = 256;
    /**
     * 线程池大小
     */
    private final Integer POOL_SIZE = 5;

    /**
     * 负载均衡策略
     */
    private LoadBalanceStrategy loadBalanceStrategy;
    /**
     * 执行线程池
     */
    private ExecutorService executeThreadPool;
    /**
     * 响应线程池
     */
    private ExecutorService responseThreadPool;

    public NIORPCServer() {
        this.init();
    }

    /**
     * 初始化方法
     */
    private void init() {
        try {
            //创建选择器
            this.selector = SelectorProvider.provider().openSelector();

            //打开第一个服务器通道
            this.serversocket = ServerSocketChannel.open();
            //告诉程序现在不是阻塞方式的
            this.serversocket.configureBlocking(false);
            //获取现在与该通道关联的套接字
            this.serversocket.socket().bind(new InetSocketAddress(this.HOSTNAME, this.PORT));

            /**
             * 将选择器注册到通道上，返回一个选择键
             * OP_ACCEPT用于套接字接受操作的操作集位 这里注册的是服务端channel
             */
            this.serversocket.register(this.selector, SelectionKey.OP_ACCEPT);

            /**
             * 获取连接列表
             */
            this.socketChannels = this.getSocketChannels();
            /**
             * 设置连接单元列表
             */
            this.setConnectionUnitList();
            /**
             * 设置负载均衡策略
             */
            this.loadBalanceStrategy = new SimpleLoadBalanceStrategy(selector);

            this.executeThreadPool = ThreadPoolFactory.getFixedThreadPool(POOL_SIZE);
            this.responseThreadPool = ThreadPoolFactory.getFixedThreadPool(POOL_SIZE);
        } catch (Exception e) {
            logger.error(e + "");
        }

    }

    /**
     * 客户端连接服务器
     *
     * @throws IOException
     */
    public void accept(SelectionKey key) throws IOException {
        LoadBalanceUtils.executeLoadBalanceStrategy(this.loadBalanceStrategy, key);
    }

    /**
     * 从通道中读取数据
     * 并且判断是给那个服务通道的
     *
     * @throws IOException
     */
    public void execute(SelectionKey key) throws IOException {
        //初始化缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(this.SIZE);
        //通过选择键来找到之前注册的通道
        SocketChannel channel = (SocketChannel) key.channel();

        int count;
        int size = 0;
        while (0 != (count = channel.read(buffer)) && -1 != count) {
            size += count;
            //需要扩容
            if (size >= buffer.capacity()) {
                buffer = BufferUtils.extendBuffer(buffer, 2.0);
            }
        }
        if (-1 == count) {
            this.releaseChannel(key);
            logger.info("Finish:{}", key);
        }

        /**
         * 获得对象字节数组 进行对应长度复制
         */
        byte[] bytes = new byte[size];
        System.arraycopy(buffer.array(), 0, bytes, 0, size);
        RequestData requestData = (RequestData) SerializeUtils.toObject(bytes);

        ExecuteCallable executeCallable = new ExecuteCallable(requestData);
        Future future = executeThreadPool.submit(executeCallable);
        ResponseRunnable responseRunnable = null;
        Object result = null;
        try {
            result = future.get(requestData.getExecuteTimeout(), TimeUnit.MICROSECONDS);
        } catch (Exception e) {
            logger.info("执行超时");
            logger.error(e + "");
            responseRunnable = new ResponseRunnable(channel);
            responseRunnable.exceptionRun(e);
            return;
        }

        /**
         * 执行失败 抛了异常
         */
        if (null != result && result == executeCallable.getErrorReuslt()) {
            responseRunnable = new ResponseRunnable(channel);
            responseRunnable.exceptionRun(executeCallable.getErrorReuslt().getThrowable());
            return;
        }

        responseRunnable = new ResponseRunnable(channel, result);
        future = responseThreadPool.submit(responseRunnable);
        try {
            future.get(requestData.getResponseTimeout(), TimeUnit.MICROSECONDS);
        } catch (Exception e) {
            logger.info("响应超时");
            logger.error(e + "");
            responseRunnable.exceptionRun(e);
        }
    }

    private void releaseChannel(SelectionKey key) throws IOException {
        key.channel().close();
        key.cancel();
    }

    @Override
    public void run() {
        while (true) {
            try {
                //选择一组键，其相应的通道已为 I/O 操作准备就绪。
                this.selector.select();

                //返回此选择器的已选择键集
                Iterator selectorKeys = this.selector.selectedKeys().iterator();
                while (selectorKeys.hasNext()) {
                    //这里找到当前的选择键
                    SelectionKey key = (SelectionKey) selectorKeys.next();
                    //然后将它从返回键队列中删除
                    selectorKeys.remove();
                    // 选择键无效
                    if (!key.isValid()) {
                        logger.warn("Invalid key:{}", key);
                        continue;
                    }
                    if (key.isAcceptable()) {
                        //如果遇到请求那么就响应
                        this.accept(key);
                    } else if (key.isReadable()) {
                        //读取客户端的数据并响应
                        this.execute(key);
                    }
                }
            } catch (Exception e) {
                logger.error(e + "");
                //关闭serversocket
                try {
                    this.serversocket.close();
                } catch (IOException ioe) {
                    logger.error(ioe + "");
                }
            }
        }

    }

    /**
     * 获取连接列表
     *
     * @return
     */
    private SocketChannel[] getSocketChannels() {
        return new SocketChannel[NUMBER];
    }

    /**
     * 设置连接单元列表
     *
     * @return
     */
    private void setConnectionUnitList() {
        LoadBalanceUtils.setConnectionUnitList(this.socketChannels);
    }
}
