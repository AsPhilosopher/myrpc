package com.wheel.test;

import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA
 * Date: 2017/10/23
 * Time: 下午5:02
 *
 * @author 陈樟杰
 */
@Component("componentBean")
public class ComponentBean {
    public void doSomething() {
        System.out.println("doSomething @@@@");
    }
}
