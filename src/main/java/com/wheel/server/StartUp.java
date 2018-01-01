package com.wheel.server;

/**
 * Created with IntelliJ IDEA
 * Date: 2018/1/1
 * Time: 下午2:41
 *
 * @author 陈樟杰
 */
public class StartUp {
    public static void main(String[] args) {
        System.out.println("------START------");
        Thread thread = new Thread(new NIORPCServer());
        thread.run();
        System.out.println("------END------");
    }
}
