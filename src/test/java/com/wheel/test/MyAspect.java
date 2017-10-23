package com.wheel.test;

import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA
 * Date: 2017/10/23
 * Time: 下午2:47
 *
 * @author 陈樟杰
 */
@Aspect
@Component
public class MyAspect {


    //配置切点 不执行方法体
    @Pointcut(value = "execution(* com.wheel.test.AOPTest.*(..)) && args(param)")
//    @Pointcut(value = "execution(* com.wheel.test.*.*(*)) && args(param)")
    public void pointcut(Integer param) {
        System.out.println("切入点pointcut()" + param);
    }

    @Before("pointcut(param)")
    public void before(Integer param) {
        System.out.println("do something before");
        System.out.println(param + "-----");
    }

    @After("pointcut(param)")
    public void after(Integer param) {
        System.out.println("do something after");
        System.out.println(param + "+++++");
    }

    @AfterThrowing(value = "pointcut(param)", throwing = "ex")
    public void afterThrowing(Integer param, Throwable ex) {
        System.out.println("throw");
        System.out.println(ex.getMessage());
    }
}
