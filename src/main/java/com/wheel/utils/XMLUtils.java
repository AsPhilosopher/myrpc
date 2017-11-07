package com.wheel.utils;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA
 * Date: 2017/11/7
 * Time: 下午4:29
 *
 * @author 陈樟杰
 */
public class XMLUtils {
    private static final Logger logger = LoggerFactory.getLogger(XMLUtils.class);
    private static final String LEFT_FALL = "/";
    private static final String ID = "id";

    private static final URL path = XMLUtils.class.getResource(LEFT_FALL);


    /**
     * 获得根路径下 第一个该名字节点里的内容 会去掉字符串尾部空格
     *
     * @param filePath
     * @param nodeName
     * @return
     */
    public static String getValueByNode(String filePath, String nodeName) {
        File file = new File(path.getPath() + filePath);
        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            document = reader.read(file);
        } catch (DocumentException e) {
            logger.error(e.getMessage());
        }

        Element root = document.getRootElement();
        Iterator iterator = root.elementIterator(nodeName);
        if (iterator.hasNext()) {
            Element element = (Element) iterator.next();
            return element.getTextTrim();
        }
        return null;
    }

    /**
     * 根据id获得某个属性值
     *
     * @param filePath
     * @param id
     * @param attributeName
     * @return
     */
    public static String getAttributeById(String filePath, String id, String attributeName) {
        File file = new File(path.getPath() + filePath);
        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            document = reader.read(file);
        } catch (DocumentException e) {
            logger.error(e.getMessage());
        }

        Element root = document.getRootElement();
        Iterator iterator = root.elementIterator();
        while (iterator.hasNext()) {
            Element element = (Element) iterator.next();
            Attribute attributeId = element.attribute(ID);
            if (null != attributeId && id.equals(attributeId.getValue())) {
                Attribute attribute = element.attribute(attributeName);
                return (attribute == null) ? null : attribute.getValue();
            }
        }
        return null;
    }
}
