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
    //创建缓冲区
//    private ByteBuffer buffer = ByteBuffer.allocate(5);

    //访问服务器
    public void request(String host, int port) throws IOException {
        InetSocketAddress address = new InetSocketAddress(InetAddress.getByName(host), port);
        SocketChannel socket = null;
        try {

            socket = SocketChannel.open();
            socket.connect(address);

            SerializableBean serializableBean = new SerializableBean("11", 11, 11.11);
            byte[] bytes = ReadWriteObject.toByteArray(serializableBean);
//            System.out.println(bytes.length + "  length");
            ByteBuffer buffer = ByteBuffer.allocate(bytes.length);

            buffer.put(bytes);
            buffer.flip();
            socket.write(buffer);

            buffer.clear();

            buffer = ByteBuffer.allocate(100);
            int count;
            int size = 0;
            while (0 != (count = socket.read(buffer)) && -1 != count) {
                size += count;
                //需要扩容
                if (size >= buffer.capacity()) {
                    buffer = ExtendBuffer.extendBuffer(buffer, 2);
                }
            }
            buffer.flip();

            System.out.println(size + " size");
            bytes = new byte[size];
            System.arraycopy(buffer.array(), 0, bytes, 0, size);
            /*SerializableBean serializableBean1 = (SerializableBean) ReadWriteObject.toObject(bytes);
            System.out.println(serializableBean1 + "####");*/

            List list = (List) ReadWriteObject.toObject(buffer.array());
            System.out.println(list);

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
