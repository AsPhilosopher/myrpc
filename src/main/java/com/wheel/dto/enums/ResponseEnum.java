package com.wheel.dto.enums;

/**
 * Created with IntelliJ IDEA
 * Date: 2018/1/1
 * Time: 下午12:24
 *
 * @author 陈樟杰
 */
public enum ResponseEnum {
    SUCCESS(200, "SUCCESS"),
    FAIL(500, "FAIL");

    private Integer code;
    private String message;

    ResponseEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
