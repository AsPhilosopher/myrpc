package com.wheel.test;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA
 * Date: 2017/10/24
 * Time: 下午3:12
 *
 * @author 陈樟杰
 */
public class SerializableBean implements Serializable {
    String string;
    Integer a;
    double d;

    public SerializableBean() {
    }

    public SerializableBean(String string, Integer a, double d) {
        this.string = string;
        this.a = a;
        this.d = d;
    }

    public String getString() {
        return string;
    }

    public SerializableBean setString(String string) {
        this.string = string;
        return this;
    }

    public Integer getA() {
        return a;
    }

    public SerializableBean setA(Integer a) {
        this.a = a;
        return this;
    }

    public double getD() {
        return d;
    }

    public SerializableBean setD(double d) {
        this.d = d;
        return this;
    }

    @Override
    public String toString() {
        return "SerializableBean{" +
                "string='" + string + '\'' +
                ", a=" + a +
                ", d=" + d +
                '}';
    }
}
