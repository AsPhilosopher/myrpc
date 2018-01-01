package com.wheel.test;

import com.wheel.client.AOPFactory;

/**
 * Created with IntelliJ IDEA
 * Date: 2018/1/1
 * Time: 下午2:43
 *
 * @author 陈樟杰
 */
public class ClientTest {
    public static void main(String[] args) {
        TestService testService = AOPFactory.getBean(TestService.class, null);
        System.out.println(testService.action() + " @@@@@");
    }
}
