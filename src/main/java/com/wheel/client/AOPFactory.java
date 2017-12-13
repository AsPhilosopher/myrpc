package com.wheel.client;

import com.wheel.dto.RequestData;

/**
 * Created with IntelliJ IDEA
 * Date: 2017/12/13
 * Time: 下午2:48
 *
 * @author 陈樟杰
 */
public class AOPFactory {
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
        return null;
    }
}
