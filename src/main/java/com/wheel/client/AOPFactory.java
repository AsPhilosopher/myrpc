package com.wheel.client;

import com.wheel.dto.RequestData;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * Created with IntelliJ IDEA
 * Date: 2017/12/13
 * Time: 下午2:48
 *
 * @author 陈樟杰
 */
public class AOPFactory {
    /**
     * 数据先本地写死
     */
    private static final String IP = "127.0.0.1";
    private static final Integer PORT = 8899;
    private static final RequestData REQUEST_DATA =
            new RequestData("test", "com.wheel.test.TestService", "action", new Object[]{}, 1000L, 1000L);

    /**
     * 根据服务名返回代理对象
     *
     * @param serviceName
     * @param <T>
     * @return
     */
    public static <T> T getBean(String serviceName) {
        return null;
    }

    /**
     * 根据接口类 请求数据返回代理对象
     *
     * @param tClass
     * @param requestData
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> tClass, RequestData requestData) {

        InvocationHandler handler = new RPCProxy(IP, PORT, REQUEST_DATA);
        return (T) Proxy.newProxyInstance(tClass.getClassLoader(), new Class[]{tClass}, handler);
    }
}
