package com.wheel.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA
 * Date: 2017/10/23
 * Time: 下午2:49
 *
 * @author 陈樟杰
 */
@Component("aopTest")
public class AOPTest {
    @Autowired
    @Qualifier("componentBean")
    private ComponentBean componentBean;

    public void aop(Integer a) {
        a.getClass();
        if(a == 0) {
            throw new RuntimeException("a=0");
        }
        componentBean.doSomething();
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
    }
}
