package com.wheel.server.loadbalance.impl;

import com.wheel.server.loadbalance.ConnectionUnit;
import com.wheel.server.loadbalance.LoadBalanceStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.List;
import java.util.Random;

/**
 * Created with IntelliJ IDEA
 * Date: 2018/1/1
 * Time: 下午1:16
 *
 * @author 陈樟杰
 */
public class SimpleLoadBalanceStrategy implements LoadBalanceStrategy {
    private static final Logger logger = LoggerFactory.getLogger(SimpleLoadBalanceStrategy.class);
    /**
     * 选择器，主要用来监控各个通道的事件
     */
    private Selector selector;

    public SimpleLoadBalanceStrategy(Selector selector) {
        this.selector = selector;
    }

    public Selector getSelector() {
        return selector;
    }

    public SimpleLoadBalanceStrategy setSelector(Selector selector) {
        this.selector = selector;
        return this;
    }

    /**
     * 获取连接 现在只是简单的随机算法
     *
     * @param connectionUnitList
     * @param key
     * @return
     */
    @Override
    public ConnectionUnit getBestConnection(List<ConnectionUnit> connectionUnitList, SelectionKey key) {
        ServerSocketChannel server = (ServerSocketChannel) key.channel();

        Random random = new Random();
        int rad = random.nextInt(connectionUnitList.size() << 1);
        ConnectionUnit connectionUnit = connectionUnitList.get(rad % connectionUnitList.size());

        try {
            connectionUnit.setSocketChannel(server.accept());
            connectionUnit.getSocketChannel().configureBlocking(false);
            //OP_READ用于读取操作的操作集位 这里注册的是客户端channel
            connectionUnit.getSocketChannel().register(this.selector, SelectionKey.OP_READ);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return connectionUnit;
    }
}
