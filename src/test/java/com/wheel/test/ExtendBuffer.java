package com.wheel.test;

import java.nio.ByteBuffer;

/**
 * Created with IntelliJ IDEA
 * Date: 2017/10/24
 * Time: 下午4:15
 *
 * @author 陈樟杰
 */
public class ExtendBuffer {
    public static ByteBuffer extendBuffer(ByteBuffer byteBuffer, double multiple) {
        int size = byteBuffer.capacity();
        ByteBuffer newBuffer = ByteBuffer.allocate((int) (size * multiple));
        newBuffer.put(byteBuffer.array());
        byteBuffer = null;
        byteBuffer = newBuffer;
//        byteBuffer.flip();
        return byteBuffer;
    }
}
