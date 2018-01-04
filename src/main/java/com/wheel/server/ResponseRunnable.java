package com.wheel.server;

import com.wheel.dto.ResponseData;
import com.wheel.dto.enums.ResponseEnum;
import com.wheel.server.write.Writer;
import com.wheel.server.write.impl.DumpWriter;
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

    /**
     *
     */
    private Writer writer = new DumpWriter();

    public ResponseRunnable(SocketChannel channel) {
        this.channel = channel;
    }

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
        this.writer.write(this.channel, responseData);
    }

    /**
     * 抛了异常后 调用这个方法返回
     *
     * @param message   异常描述
     * @param exception 异常
     */
    public void exceptionRun(String message, Exception exception) {
        ResponseData responseData = new ResponseData(ResponseEnum.FAIL, null);
        responseData.setMessage(exception + "(" + message + ") message:" + exception.getMessage());

        this.writer.write(this.channel, responseData);
    }

    /**
     * 抛了异常后 调用这个方法返回
     *
     * @param message   异常描述
     * @param throwable
     */
    public void exceptionRun(String message, Throwable throwable) {
        ResponseData responseData = new ResponseData(ResponseEnum.FAIL, null);
        responseData.setMessage(throwable + "(" + message + ") message:" + throwable.getMessage());

        this.writer.write(this.channel, responseData);
    }
}
