package com.wheel.server.write.impl;

import com.wheel.server.write.Writer;
import com.wheel.utils.SerializeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 * Created with IntelliJ IDEA
 * Date: 2018/1/1
 * Time: 上午11:22
 *
 * @author 陈樟杰
 */
public class DumpWriter implements Writer {
    private static final Logger logger = LoggerFactory.getLogger(DumpWriter.class);

    /**
     * 往通道里写内容
     *
     * @param channel
     * @param object
     */
    @Override
    public void write(SocketChannel channel, Object object) {
        byte[] bytes = SerializeUtils.toByteArray(object);
        this.write(channel, bytes);
    }

    /**
     * 往通道里写内容
     *
     * @param channel
     * @param bytes
     */
    @Override
    public void write(SocketChannel channel, byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
        buffer.put(bytes);
        buffer.flip();
        try {
            channel.write(buffer);
        } catch (IOException e) {
            logger.error(e + "");
        } finally {
            try {
                channel.close();
            } catch (IOException e) {
                logger.error(e + "");
            }
        }
    }
}
