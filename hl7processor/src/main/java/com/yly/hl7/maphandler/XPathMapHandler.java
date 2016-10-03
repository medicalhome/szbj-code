package com.yly.hl7.maphandler;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 用于处理DTO与XML节点/属性的映射配置的接口。
 * 
 * @author xu_dengfeng
 *
 */
public interface XPathMapHandler
{
    /**
     * 取得给定类各属性的XPath映射
     * 
     * @param clazz 对象类
     * @return 给定类的属性映射表
     */
    public Map<String, Field> getXPathMap(Class<?> clazz);

    /**
     * 取得指定属性的子类型
     * 
     * 当一个DTO中使用另外一个DTO或者另外一个DTO的列表作为属性，则需要定义子类型
     * @param field 对象属性
     * @return 子类型的Class
     */
    public Class<?> getXPathMapChildType(Field field);

    public void setMessageId(String messageId);
}
