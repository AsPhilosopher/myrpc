package com.wheel.server.write;

import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 * Created with IntelliJ IDEA
 * Date: 2018/1/1
 * Time: 上午11:17
 *
 * @author 陈樟杰
 */
public interface Writer {
    /**
     * 往通道里写内容
     *
     * @param channel
     * @param object
     */
    void write(SocketChannel channel, Object object);

    /**
     * 往通道里写内容
     *
     * @param channel
     * @param bytes
     */
    void write(SocketChannel channel, byte[] bytes);
}
