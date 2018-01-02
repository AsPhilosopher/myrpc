package com.wheel.test;

import com.wheel.client.AOPFactory;
import com.wheel.dto.RequestData;

/**
 * Created with IntelliJ IDEA
 * Date: 2018/1/1
 * Time: 下午2:43
 *
 * @author 陈樟杰
 */
public class ClientTest {
    public static void main(String[] args) {
        RequestData requestData =
                new RequestData("test", "com.wheel.test.TestService", 1000L, 1000L);
        TestService testService = AOPFactory.getBean(TestService.class, requestData);


        System.out.println(testService.action());
        testService.action(10);
        testService.fun();
        System.out.println(testService.fun(10));
    }
}
