package com.wheel.test;

import java.io.*;

/**
 * Created with IntelliJ IDEA
 * Date: 2017/10/24
 * Time: 下午3:58
 *
 * @author 陈樟杰
 */
public class ReadWriteObject {
    /**
     * 对象转数组
     *
     * @param obj
     * @return
     */
    public static byte[] toByteArray(Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray();
            oos.close();
            bos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return bytes;
    }

    /**
     * 数组转对象
     *
     * @param bytes
     * @return
     */
    public static Object toObject(byte[] bytes) {
        Object obj = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bis);
            obj = ois.readObject();
            ois.close();
            bis.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return obj;
    }

    public static void main(String[] args) {
        SerializableBean serializableBean = new SerializableBean("11", 11, 11.11);
        byte[] bytes = toByteArray(serializableBean);
        System.out.println(bytes.length);
        System.out.println(toObject(bytes));
    }
}
