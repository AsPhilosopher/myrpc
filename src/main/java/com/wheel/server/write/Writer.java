package com.wheel.server.write;

import java.nio.channels.SelectionKey;

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
     * @param selectionKey
     * @param object
     */
    void write(SelectionKey selectionKey, Object object);

    /**
     * 往通道里写内容
     *
     * @param selectionKey
     * @param bytes
     */
    void write(SelectionKey selectionKey, byte[] bytes);
}
