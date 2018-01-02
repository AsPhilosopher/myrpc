package com.wheel.server;

import com.wheel.utils.XMLUtils;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.List;
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
    private static final String FILE_NAME = "server.xml";
    private static final String MAPPER = "mapper";
    private static final String INTERFACE = "interface";
    private static final String IMPLEMENT = "implement";

    static {
        List<Element> elementList = XMLUtils.getNodesByName(FILE_NAME, MAPPER);
        for (Element element : elementList) {
            INTERFACE_CLASS.put(element.attribute(INTERFACE).getValue(), element.attribute(IMPLEMENT).getValue());
        }
    }

    public static String getClassFullName(String ifc) {
        return INTERFACE_CLASS.get(ifc);
    }

    /**
     * 空的初始化方法 目的是执行static方法块 防止第一次RPC调用超时
     */
    public static void init() {

    }
}
