package com.wheel.server.read.impl;

import com.wheel.server.read.Reader;
import com.wheel.utils.BufferUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 * Created with IntelliJ IDEA
 * Date: 2018/1/1
 * Time: 上午11:21
 *
 * @author 陈樟杰
 */
public class IncrementReader implements Reader {
    private static final Logger logger = LoggerFactory.getLogger(IncrementReader.class);
    /**
     * 从通道里读内容
     *
     * @param selectionKey
     * @param bufferSize
     * @return
     */
    @Override
    public byte[] read(SelectionKey selectionKey, int bufferSize) throws IOException {
        //通过选择键来找到之前注册的通道
        SocketChannel channel = (SocketChannel) selectionKey.channel();
        //初始化缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(bufferSize);
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
            this.releaseChannel(selectionKey);
            logger.info("Finish:{}", selectionKey);
        }

        /**
         * 获得对象字节数组 进行对应长度复制
         */
        byte[] bytes = new byte[size];
        System.arraycopy(buffer.array(), 0, bytes, 0, size);
        return bytes;
    }

    /**
     *
     *
     * @param key
     * @throws IOException
     */
    private void releaseChannel(SelectionKey key) throws IOException {
        key.channel().close();
        key.cancel();
    }
}
