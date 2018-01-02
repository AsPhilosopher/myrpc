package com.wheel.dto;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA
 * Date: 2017/12/13
 * Time: 下午2:06
 *
 * @author 陈樟杰
 */
public class RequestData implements Serializable {
    private static final long serialVersionUID = 4329060581890362788L;
    /**
     * 服务名称
     */
    private String serviceName;
    /**
     * 接口全名
     */
    private String interfaceFullName;
    /**
     * 方法名
     */
    private String methodName;
    /**
     * 参数列表
     */
    private Object[] args;
    /**
     * 执行超时
     */
    private Long executeTimeout;
    /**
     * 响应超时
     */
    private Long responseTimeout;

    public RequestData() {
    }

    public RequestData(String serviceName, String interfaceFullName, Long executeTimeout, Long responseTimeout) {
        this.serviceName = serviceName;
        this.interfaceFullName = interfaceFullName;
        this.executeTimeout = executeTimeout;
        this.responseTimeout = responseTimeout;

        /**
         * 默认超时时间
         */
        if (null == executeTimeout || executeTimeout <= 0) {
            executeTimeout = 1000L;
        }
        if (null == responseTimeout || responseTimeout <= 0) {
            responseTimeout = 1000L;
        }
    }

    public String getServiceName() {
        return serviceName;
    }

    public RequestData setServiceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    public String getInterfaceFullName() {
        return interfaceFullName;
    }

    public RequestData setInterfaceFullName(String interfaceFullName) {
        this.interfaceFullName = interfaceFullName;
        return this;
    }

    public String getMethodName() {
        return methodName;
    }

    public RequestData setMethodName(String methodName) {
        this.methodName = methodName;
        return this;
    }

    public Object[] getArgs() {
        return args;
    }

    public RequestData setArgs(Object[] args) {
        this.args = args;
        return this;
    }

    public Long getExecuteTimeout() {
        return executeTimeout;
    }

    public RequestData setExecuteTimeout(Long executeTimeout) {
        this.executeTimeout = executeTimeout;
        return this;
    }

    public Long getResponseTimeout() {
        return responseTimeout;
    }

    public RequestData setResponseTimeout(Long responseTimeout) {
        this.responseTimeout = responseTimeout;
        return this;
    }
}
