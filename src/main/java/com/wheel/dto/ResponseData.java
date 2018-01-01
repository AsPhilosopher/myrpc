package com.wheel.dto;

import com.wheel.dto.enums.ResponseEnum;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA
 * Date: 2017/12/13
 * Time: 下午3:16
 *
 * @author 陈樟杰
 */
public class ResponseData implements Serializable {
    private static final long serialVersionUID = 9130211433825531795L;
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

    public ResponseData() {
    }

    public ResponseData(ResponseEnum responseEnum, Object data) {
        this.code = responseEnum.getCode();
        this.message = responseEnum.getMessage();
        this.data = data;
    }

    public ResponseData(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

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
