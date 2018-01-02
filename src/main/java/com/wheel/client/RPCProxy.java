package com.wheel.client;

import com.wheel.dto.RequestData;
import com.wheel.dto.ResponseData;
import com.wheel.dto.enums.ResponseEnum;
import com.wheel.exception.RPCException;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA
 * Date: 2017/12/13
 * Time: 下午2:52
 *
 * @author 陈樟杰
 */
public class RPCProxy implements InvocationHandler {
    /**
     * 远程服务IP
     */
    private String ip;
    /**
     * 远程服务端口
     */
    private Integer port;
    /**
     * RPC请求数据
     */
    private RequestData requestData;

    public RPCProxy() {
    }

    public RPCProxy(String ip, Integer port, RequestData requestData) {
        this.ip = ip;
        this.port = port;
        this.requestData = requestData;
    }

    public String getIp() {
        return ip;
    }

    public RPCProxy setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public Integer getPort() {
        return port;
    }

    public RPCProxy setPort(Integer port) {
        this.port = port;
        return this;
    }

    public RequestData getRequestData() {
        return requestData;
    }

    public RPCProxy setRequestData(RequestData requestData) {
        this.requestData = requestData;
        return this;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        requestData.setMethodName(method.getName());
        requestData.setArgs(args);
        ResponseData responseData = NIOClient.remoteRequest(ip, port, requestData);
        /**
         * RPC调用出现异常
         */
        if (!ResponseEnum.SUCCESS.getCode().equals(responseData.getCode())) {
            throw new RPCException(responseData.getMessage());
        }
        return responseData.getData();
    }
}
