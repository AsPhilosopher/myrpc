package com.wheel.server;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA
 * Date: 2018/1/1
 * Time: 下午2:50
 *
 * @author 陈樟杰
 */
public class InterfaceMap {
    private static final Map<String, String> INTERFACE_CLASS = new HashMap<>();

    static {
        INTERFACE_CLASS.put("com.wheel.test.TestService", "com.wheel.test.TestServiceImpl");
    }

    public static String getClassFullName(String ifc) {
        return INTERFACE_CLASS.get(ifc);
    }
}
