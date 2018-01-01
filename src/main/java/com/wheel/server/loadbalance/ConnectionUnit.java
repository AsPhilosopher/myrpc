package com.wheel.server.loadbalance;

import java.nio.channels.SocketChannel;

/**
 * Created with IntelliJ IDEA
 * Date: 2018/1/1
 * Time: 下午1:15
 *
 * @author 陈樟杰
 *
 * 连接单元类 用来封装连接属性
 */
public class ConnectionUnit {
    /**
     * 连接
     */
    private SocketChannel socketChannel;

    public ConnectionUnit(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    public SocketChannel getSocketChannel() {
        return socketChannel;
    }

    public ConnectionUnit setSocketChannel(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
        return this;
    }
}
