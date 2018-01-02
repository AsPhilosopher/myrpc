package com.wheel.client;

import com.wheel.dto.RequestData;
import com.wheel.dto.ResponseData;
import com.wheel.utils.BufferUtils;
import com.wheel.utils.SerializeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created with IntelliJ IDEA
 * Date: 2017/12/13
 * Time: 下午3:07
 *
 * @author 陈樟杰
 */
public class NIOClient {
    private static final Logger logger = LoggerFactory.getLogger(NIOClient.class);

    /**
     * 将请求对象序列化传输到远程 并返回结果
     *
     * @param ip
     * @param port
     * @param requestData
     * @return
     */
    public static ResponseData remoteRequest(String ip, Integer port, RequestData requestData) {
        InetSocketAddress address = null;
        SocketChannel socket = null;
        try {
            address = new InetSocketAddress(InetAddress.getByName(ip), port);
            socket = SocketChannel.open();
            socket.connect(address);
        } catch (UnknownHostException e) {
            logger.error(e + "");
        } catch (IOException e) {
            logger.error(e + "");
        }

        byte[] objectBytes = SerializeUtils.toByteArray(requestData);
        ByteBuffer buffer = ByteBuffer.allocate(objectBytes.length);

        /**
         * 传输对象字节数组
         */
        buffer.put(objectBytes);
        buffer.flip();
        try {
            socket.write(buffer);
        } catch (IOException e) {
            logger.error(e + "");
        }
        buffer.clear();

        int count;
        int size = 0;
        try {
            while (0 != (count = socket.read(buffer)) && -1 != count) {
                size += count;
                //需要扩容
                if (size >= buffer.capacity()) {
                    buffer = BufferUtils.extendBuffer(buffer, 2.0);
                }
            }
        } catch (IOException e) {
            logger.error(e + "");
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    logger.error(e + "");
                }
            }
        }

        /**
         * 获得对象字节数组 进行对应长度复制
         */
        objectBytes = new byte[size];
        System.arraycopy(buffer.array(), 0, objectBytes, 0, size);
        Object resultData = SerializeUtils.toObject(buffer.array());

        return (ResponseData) resultData;
    }
}
