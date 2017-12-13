package com.wheel.dto;

/**
 * Created with IntelliJ IDEA
 * Date: 2017/12/13
 * Time: 下午2:06
 *
 * @author 陈樟杰
 */
public class RequestData {
    /**
     * 服务名称
     */
    private String serviceName;
    /**
     * 接口全名
     */
    private String interfaceFullName;
    /**
     * 执行超时
     */
    private Long executeTimeout;
    /**
     * 响应超时
     */
    private Long responseTimeout;

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
