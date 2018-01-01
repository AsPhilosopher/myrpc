package com.wheel.server.write.impl;

import com.wheel.server.write.Writer;

import java.nio.channels.SelectionKey;

/**
 * Created with IntelliJ IDEA
 * Date: 2018/1/1
 * Time: 上午11:22
 *
 * @author 陈樟杰
 */
public class DumpWriter implements Writer {
    /**
     * 往通道里写内容
     *
     * @param selectionKey
     * @param object
     */
    @Override
    public void write(SelectionKey selectionKey, Object object) {

    }

    /**
     * 往通道里写内容
     *
     * @param selectionKey
     * @param bytes
     */
    @Override
    public void write(SelectionKey selectionKey, byte[] bytes) {

    }
}
