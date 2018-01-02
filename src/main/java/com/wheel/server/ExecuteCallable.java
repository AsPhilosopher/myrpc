package com.wheel.server;

import com.wheel.dto.RequestData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * Created with IntelliJ IDEA
 * Date: 2018/1/1
 * Time: 下午2:06
 *
 * @author 陈樟杰
 */
public class ExecuteCallable implements Callable {
    private static final Logger logger = LoggerFactory.getLogger(ExecuteCallable.class);

    private RequestData requestData;
    private ErrorReuslt errorReuslt;

    public ExecuteCallable(RequestData requestData) {
        this.requestData = requestData;
    }

    public RequestData getRequestData() {
        return requestData;
    }

    public ExecuteCallable setRequestData(RequestData requestData) {
        this.requestData = requestData;
        return this;
    }

    public ErrorReuslt getErrorReuslt() {
        return errorReuslt;
    }

    public ExecuteCallable setErrorReuslt(ErrorReuslt errorReuslt) {
        this.errorReuslt = errorReuslt;
        return this;
    }

    @Override
    public Object call() throws Exception {
        String methodName = requestData.getMethodName();
        String interfaceFullName = requestData.getInterfaceFullName();
        Object[] args = requestData.getArgs();

        /**
         * 获取参数的类类型
         */
        Class[] argClasses = null;
        if (null != args) {
            argClasses = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                Object arg = args[i];
                argClasses[i] = arg.getClass();
            }
        } else {
            argClasses = new Class[0];
        }

        Class tclass = Class.forName(InterfaceMap.getClassFullName(interfaceFullName));
        Method method = tclass.getMethod(methodName, argClasses);
        /**
         * 抛异常 则返回对应值
         */
        try {
            return method.invoke(tclass.newInstance(), args);
        } catch (InvocationTargetException e) {
            logger.error(e + "");
            return (this.errorReuslt = new ErrorReuslt(501, e.getTargetException()));
        }
    }

    /**
     * 执行失败 返回的结构
     */
    public class ErrorReuslt {
        private Integer code;
        private Throwable throwable;

        public ErrorReuslt(Integer code, Throwable throwable) {
            this.code = code;
            this.throwable = throwable;
        }

        public Integer getCode() {
            return code;
        }

        public ErrorReuslt setCode(Integer code) {
            this.code = code;
            return this;
        }

        public Throwable getThrowable() {
            return throwable;
        }

        public ErrorReuslt setThrowable(Throwable throwable) {
            this.throwable = throwable;
            return this;
        }
    }
}
