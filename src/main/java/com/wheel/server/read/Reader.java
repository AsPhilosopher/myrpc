package com.wheel.server.read;

import java.nio.channels.SelectionKey;

/**
 * Created with IntelliJ IDEA
 * Date: 2018/1/1
 * Time: 上午11:17
 *
 * @author 陈樟杰
 */
public interface Reader {
    /**
     * 从通道里读内容
     *
     * @param selectionKey
     * @return
     */
    byte[] read(SelectionKey selectionKey);
}
