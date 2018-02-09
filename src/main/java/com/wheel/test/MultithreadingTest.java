package com.wheel.test;

import com.wheel.client.AOPFactory;

/**
 * Created with IntelliJ IDEA
 * Date: 2018/2/9
 * Time: 下午4:04
 *
 * @author 陈樟杰
 */
public class MultithreadingTest {
    public static void main(String[] args) {
        //获取代理bean
        TestService testService = AOPFactory.getBean("test");
        int i = 0;

        while (true) {
            /*new Thread(() -> {
                System.out.println(testService.action());
                testService.action(10);
                testService.fun();
                System.out.println(testService.fun(10, "5555"));
            }).start();

            new Thread(() -> {
                System.out.println(testService.action());
                testService.action(10);
                testService.fun();
                System.out.println(testService.fun(10, "5555"));
            }).start();

            new Thread(() -> {
                System.out.println(testService.action());
                testService.action(10);
                testService.fun();
                System.out.println(testService.fun(10, "5555"));
            }).start();*/

            System.out.println(testService.action());
            testService.action(10);
            testService.fun();
            System.out.println(testService.fun(10, "5555"));
            System.out.println(i++);
        }

    }
}
