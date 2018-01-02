package com.wheel.exception;

/**
 * Created with IntelliJ IDEA
 * Date: 2018/1/2
 * Time: 下午2:57
 *
 * @author 陈樟杰
 */
public class RPCException extends RuntimeException {
    public RPCException(String message) {
        super(message);
    }
}
