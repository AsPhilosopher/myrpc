package com.wheel.server.read.impl;

import com.wheel.server.read.Reader;

import java.nio.channels.SelectionKey;

/**
 * Created with IntelliJ IDEA
 * Date: 2018/1/1
 * Time: 上午11:21
 *
 * @author 陈樟杰
 */
public class IncrementReader implements Reader {
    /**
     * 从通道里读内容
     *
     * @param selectionKey
     * @return
     */
    @Override
    public byte[] read(SelectionKey selectionKey) {
        return new byte[0];
    }
}
