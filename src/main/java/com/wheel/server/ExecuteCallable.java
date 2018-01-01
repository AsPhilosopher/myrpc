package com.wheel.server;

import com.wheel.dto.RequestData;

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
    private RequestData requestData;

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
        return method.invoke(tclass.newInstance(), args);
    }
}
