package com.wheel.test;

/**
 * Created with IntelliJ IDEA
 * Date: 2018/1/1
 * Time: 上午10:19
 *
 * @author 陈樟杰
 */
public class TestServiceImpl implements TestService {
    @Override
    public Integer action() {
        System.out.println("do action");
        return 200;
    }

    @Override
    public void action(Integer a) {
        System.out.println("DO ACTION " + a);
    }

    @Override
    public void fun() {
        System.out.println("fun fun");

        /*String s = null;
        System.out.println(s.getBytes());*/
//        throw new RuntimeException("Test Exception");
    }

    @Override
    public String fun(Integer a) {
        System.out.println("FUN " + a);
        /*try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        return "SUCCESS";
    }
}
