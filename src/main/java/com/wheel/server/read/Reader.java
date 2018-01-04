package com.wheel.server.read;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

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
     * @param bufferSize
     * @return
     */
    byte[] read(SelectionKey selectionKey, int bufferSize) throws IOException;
}
