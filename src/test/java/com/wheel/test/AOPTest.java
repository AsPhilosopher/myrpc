package com.wheel.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA
 * Date: 2017/10/23
 * Time: 下午2:49
 *
 * @author 陈樟杰
 */
@Component("aopTest")
public class AOPTest {

    public void aop(Integer a) {
        a.getClass();
        if(a == 0) {
            throw new RuntimeException("a=0");
        }
        System.out.println("do some thing");
    }

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
        AOPTest aopTest = (AOPTest) context.getBean("aopTest");
        aopTest.aop(10);
        try {
            aopTest.aop(null);
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("hehehe");
    }
}
