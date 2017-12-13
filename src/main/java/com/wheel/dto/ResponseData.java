package com.wheel.dto;

/**
 * Created with IntelliJ IDEA
 * Date: 2017/12/13
 * Time: 下午3:16
 *
 * @author 陈樟杰
 */
public class ResponseData {
    /**
     * 结果码
     */
    private Integer code;
    /**
     * 结果信息
     */
    private String message;
    /**
     * 结果数据
     */
    private Object data;

    public Integer getCode() {
        return code;
    }

    public ResponseData setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ResponseData setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getData() {
        return data;
    }

    public ResponseData setData(Object data) {
        this.data = data;
        return this;
    }
}