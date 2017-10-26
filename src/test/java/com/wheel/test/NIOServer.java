package com.wheel.test;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created with IntelliJ IDEA
 * Date: 2017/10/24
 * Time: 上午10:12
 *
 * @author 陈樟杰
 */
public class NIOServer implements Runnable {
    //第一个端口
    private Integer port1 = 8099;
    //第一个服务器通道 服务A
    private ServerSocketChannel serversocket1;
    //连接1
    private SocketChannel clientchannel1;
    //连接2
    private SocketChannel clientchannel2;

    //选择器，主要用来监控各个通道的事件
    private Selector selector;

    private final Integer SIZE = 128;

    public static AtomicLong atomicLongAcc = new AtomicLong(0);
    public static AtomicLong atomicLongRead = new AtomicLong(0);

    public NIOServer() {
        this.init();
    }

    /**
     * 这个method的作用
     * 1：是初始化选择器
     * 2：打开两个通道
     * 3：给通道上绑定一个socket
     * 4：将选择器注册到通道上
     */
    private void init() {
        try {
            //创建选择器
            this.selector = SelectorProvider.provider().openSelector();
            //打开第一个服务器通道
            this.serversocket1 = ServerSocketChannel.open();
            //告诉程序现在不是阻塞方式的
            this.serversocket1.configureBlocking(false);
            //获取现在与该通道关联的套接字
            this.serversocket1.socket().bind(new InetSocketAddress("localhost", this.port1));

            //将选择器注册到通道上，返回一个选择键
            //OP_ACCEPT用于套接字接受操作的操作集位 这里注册的是服务端channel
            this.serversocket1.register(this.selector, SelectionKey.OP_ACCEPT);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 这个方法是连接
     * 客户端连接服务器
     *
     * @throws IOException
     */
    public void accept(SelectionKey key) throws IOException {
        atomicLongAcc.incrementAndGet();

        ServerSocketChannel server = (ServerSocketChannel) key.channel();

        Random random = new Random();
        int rad = random.nextInt(100);
        if (rad % 2 == 0) {
            System.out.println("connect111-----");
            clientchannel1 = server.accept();
            clientchannel1.configureBlocking(false);
            //OP_READ用于读取操作的操作集位 这里注册的是客户端channel
            clientchannel1.register(this.selector, SelectionKey.OP_READ);
        } else {
            System.out.println("connect222-----");
            clientchannel2 = server.accept();
            clientchannel2.configureBlocking(false);
            //OP_READ用于读取操作的操作集位
            clientchannel2.register(this.selector, SelectionKey.OP_READ);
        }
    }

    /**
     * 从通道中读取数据
     * 并且判断是给那个服务通道的
     *
     * @throws IOException
     */
    public void read(SelectionKey key) throws IOException {
        atomicLongRead.incrementAndGet();

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
                buffer = ExtendBuffer.extendBuffer(buffer, 2.0);
            }
        }
        if (-1 == count) {
            this.releaseChannel(key);
            System.out.println("Finish------");
        }

        /**
         * 获得对象字节数组 进行对应长度复制
         */
        byte[] bytes = new byte[size];
        System.arraycopy(buffer.array(), 0, bytes, 0, size);
        SerializableBean serializableBean = (SerializableBean) ReadWriteObject.toObject(bytes);
        System.out.println("get: " + serializableBean);

        /**
         * 得到对象字节数组
         */
        List<SerializableBean> serializableBeanList = new ArrayList<SerializableBean>(4);
        serializableBeanList.add(serializableBean);
        serializableBeanList.add(new SerializableBean("1", 11, 11.22));
        serializableBeanList.add(new SerializableBean("2", 22, 22.22));
        serializableBeanList.add(new SerializableBean("3", 33, 33.22));
        bytes = ReadWriteObject.toByteArray(serializableBeanList);

        /**
         * 传输对象字节数组
         */
        buffer = null;
        buffer = ByteBuffer.allocate(bytes.length);
        buffer.put(bytes);
        buffer.flip();
        channel.write(buffer);

        buffer = null;
        this.releaseChannel(key);
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
                    if (!key.isValid()) { // 选择键无效
                        System.out.println("Invalid key");
                        continue;
                    }
                    if (key.isAcceptable()) {
                        //如果遇到请求那么就响应
                        this.accept(key);
                    } else if (key.isReadable()) {
                        //读取客户端的数据
                        this.read(key);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                try {//关闭serversocket
                    this.serversocket1.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }

    }

    public static void main(String[] args) {
        NIOServer server = new NIOServer();
        Thread thread = new Thread(server);
        thread.start();

        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(atomicLongAcc.get() + " @@@");
        System.out.println(atomicLongRead.get() + " $$$");
    }
}
