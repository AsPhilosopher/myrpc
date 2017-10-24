package com.wheel.test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Date: 2017/10/24
 * Time: 上午11:26
 *
 * @author 陈樟杰
 */
public class NIOClient {
    /**
     * 请求服务器
     *
     * @param host
     * @param port
     * @throws IOException
     */
    public void request(String host, int port) throws IOException {
        InetSocketAddress address = new InetSocketAddress(InetAddress.getByName(host), port);
        SocketChannel socket = null;
        try {
            socket = SocketChannel.open();
            socket.connect(address);

            /**
             * 得到对象字节数组
             */
            SerializableBean serializableBean = new SerializableBean("11", 11, 11.11);
            byte[] bytes = ReadWriteObject.toByteArray(serializableBean);
            ByteBuffer buffer = ByteBuffer.allocate(bytes.length);

            /**
             * 传输对象字节数组
             */
            buffer.put(bytes);
            buffer.flip();
            socket.write(buffer);

            buffer.clear();
            int count;
            int size = 0;
            while (0 != (count = socket.read(buffer)) && -1 != count) {
                size += count;
                //需要扩容
                if (size >= buffer.capacity()) {
                    buffer = ExtendBuffer.extendBuffer(buffer, 2);
                }
            }

            /**
             * 获得对象字节数组 进行对应长度复制
             */
            bytes = new byte[size];
            System.arraycopy(buffer.array(), 0, bytes, 0, size);
            List list = (List) ReadWriteObject.toObject(buffer.array());
            System.out.println(list);

            buffer = null;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new NIOClient().request("localhost", 8099);
    }
}
