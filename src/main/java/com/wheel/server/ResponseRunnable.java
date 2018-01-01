package com.wheel.server;

import com.wheel.dto.ResponseData;
import com.wheel.dto.enums.ResponseEnum;
import com.wheel.utils.SerializeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created with IntelliJ IDEA
 * Date: 2018/1/1
 * Time: 下午2:07
 *
 * @author 陈樟杰
 */
public class ResponseRunnable implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ResponseRunnable.class);
    /**
     * 通道
     */
    private SocketChannel channel;
    /**
     * 响应数据
     */
    private Object data;

    public ResponseRunnable(SocketChannel channel, Object data) {
        this.channel = channel;
        this.data = data;
    }

    public SocketChannel getChannel() {
        return channel;
    }

    public ResponseRunnable setChannel(SocketChannel channel) {
        this.channel = channel;
        return this;
    }

    public Object getData() {
        return data;
    }

    public ResponseRunnable setData(Object data) {
        this.data = data;
        return this;
    }

    @Override
    public void run() {
        ResponseData responseData = new ResponseData(ResponseEnum.SUCCESS, data);
        byte[] bytes = SerializeUtils.toByteArray(responseData);
        ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
        buffer.put(bytes);
        buffer.flip();
        try {
            channel.write(buffer);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        /**
         * 关闭连接
         */
        try {
            channel.close();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
