package com.wheel.client;

import com.wheel.dto.RequestData;
import com.wheel.utils.XMLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static final Logger logger = LoggerFactory.getLogger(AOPFactory.class);

    private static final String FILE_NAME = "client.xml";
    private static final String _IP = "ip";
    private static final String _PORT = "port";

    /**
     * 数据先本地写死
     */
    private static final String IP = XMLUtils.getValueByNode(FILE_NAME, _IP);
    private static final Integer PORT = Integer.valueOf(XMLUtils.getValueByNode(FILE_NAME, _PORT));

    /**
     * 根据服务名返回代理对象
     *
     * @param serviceName
     * @param <T>
     * @return
     */
    public static <T> T getBean(String serviceName) {
        String ifc = XMLUtils.getAttributeByName(FILE_NAME, serviceName, "interface");
        String executeTimeoutStr = XMLUtils.getAttributeByName(FILE_NAME, serviceName, "execute_timeout");
        String responseTimeoutStr = XMLUtils.getAttributeByName(FILE_NAME, serviceName, "response_timeout");

        Class<T> tclass = null;
        try {
            tclass = (Class<T>) Class.forName(ifc);
        } catch (ClassNotFoundException e) {
            logger.error("" + e);
        }

        Long executeTimeout = Long.valueOf(executeTimeoutStr);
        Long responseTimeout = Long.valueOf(responseTimeoutStr);

        RequestData requestData = new RequestData(serviceName, ifc, executeTimeout, responseTimeout);
        return getBean(tclass, requestData);
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

        InvocationHandler handler = new RPCProxy(IP, PORT, requestData);
        return (T) Proxy.newProxyInstance(tClass.getClassLoader(), new Class[]{tClass}, handler);
    }
}
