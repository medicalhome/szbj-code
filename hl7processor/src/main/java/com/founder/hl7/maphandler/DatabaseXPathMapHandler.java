package com.founder.hl7.maphandler;

import java.lang.reflect.Field;
import java.util.Map;

import com.founder.hl7.annotation.ChildType;

/**
 * 用于处理从数据库配置表中读取DTO与XML节点/属性的映射配置。
 * 
 * @author wen_ruichao
 *
 */
public class DatabaseXPathMapHandler implements XPathMapHandler
{

    private String messageId;

    @Override
    public Map<String, Field> getXPathMap(Class<?> clazz)
    {
        return null;
    }

    @Override
    public Class<?> getXPathMapChildType(Field field)
    {
        ChildType childType = field.getAnnotation(ChildType.class);
        return childType == null ? null : childType.value();
    }

    @Override
    public void setMessageId(String messageId)
    {
        this.messageId = messageId;
    }
}
