package com.wheel.utils;

import java.nio.ByteBuffer;

/**
 * Created with IntelliJ IDEA
 * Date: 2017/11/7
 * Time: 下午5:13
 *
 * @author 陈樟杰
 */
public class BufferExtendUtils {
    /**
     * 按倍数扩容 需要返回引用
     *
     * @param byteBuffer
     * @param multiple
     * @return
     */
    public static ByteBuffer extendBuffer(ByteBuffer byteBuffer, Double multiple) {
        int size = byteBuffer.capacity();
        ByteBuffer newBuffer = ByteBuffer.allocate((int) (size * multiple));
        newBuffer.put(byteBuffer.array());
        byteBuffer = null;
        byteBuffer = newBuffer;
        return byteBuffer;
    }
}
