package com.wheel.test.utils;

import com.wheel.utils.XMLUtils;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA
 * Date: 2017/11/7
 * Time: 下午4:56
 *
 * @author 陈樟杰
 */
public class XMLUtilsTest {
    @Test
    public void getValueByNodeTest() {
        System.out.println(XMLUtils.getValueByNode("utils/test.xml", "node"));
    }
}
