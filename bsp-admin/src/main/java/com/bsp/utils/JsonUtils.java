package com.bsp.utils;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.dom4j.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * JSON工具类
 */
public class JsonUtils {
    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    public static String toJsonStr(Object obj) {
        String res = JSON.toJSONString(obj);
        logger.info("对象转字符串================(" + res + ")");
        return res;
    }

    public static <T> T toObject(String jsonStr, Class<T> clazz) {
        T target = null;
        try {
            target = JSON.parseObject(jsonStr, clazz);
        } catch (JSONException exception) {
            logger.error("JSON数据转对象失败================(" + jsonStr + ")", exception);
            return target;
        }
        logger.info("JSON数据转对象成功================(" + jsonStr + ")");
        return target;
    }



    /**
     * org.dom4j.Document 转  com.alibaba.fastjson.JSONObject
     *
     * @param xml
     * @return
     */
    public static JSONObject xmlToJSONObject(String xml) {
        JSONObject jsonObject = null;
        try {
            jsonObject = elementToJSONObject(strToDocument(xml).getRootElement());
        } catch (DocumentException e) {
            logger.error("XML解析转JSON对象失败====================", e);
            return null;
        }
        return jsonObject;
    }

    /**
     * String 转 org.dom4j.Document
     *
     * @param xml
     * @return
     * @throws DocumentException
     */
    private static Document strToDocument(String xml) throws DocumentException {
        return DocumentHelper.parseText(xml);
    }


    /**
     * org.dom4j.Element 转  com.alibaba.fastjson.JSONObject
     *
     * @param node
     * @return
     */
    private static JSONObject elementToJSONObject(Element node) {
        JSONObject result = new JSONObject();
        // 当前节点的名称、文本内容和属性
        @SuppressWarnings("unchecked")
		List<Attribute> listAttr = node.attributes();// 当前节点的所有属性的list
        for (Attribute attr : listAttr) {// 遍历当前节点的所有属性
            result.put(attr.getName(), attr.getValue());
        }
        // 递归遍历当前节点所有的子节点
        @SuppressWarnings("unchecked")
		List<Element> listElement = node.elements();// 所有一级子节点的list
        if (!listElement.isEmpty()) {
            for (Element e : listElement) {// 遍历所有一级子节点
                if (e.attributes().isEmpty() && e.elements().isEmpty()) // 判断一级节点是否有属性和子节点
                {
                    result.put(e.getName(), e.getTextTrim());// 沒有则将当前节点作为上级节点的属性对待
                } else {
                    if (!result.containsKey(e.getName())) // 判断父节点是否存在该一级节点名称的属性
                    {
                        result.put(e.getName(), new JSONArray());// 没有则创建
                    }
                    ((JSONArray) result.get(e.getName())).add(elementToJSONObject(e));// 将该一级节点放入该节点名称的属性对应的值中
                }
            }
        }
        return result;
    }
}
