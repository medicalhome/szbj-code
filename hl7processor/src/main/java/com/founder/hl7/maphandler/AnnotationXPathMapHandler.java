package com.founder.hl7.maphandler;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.founder.hl7.annotation.ChildType;
import com.founder.hl7.annotation.XmlTarget;

/**
 * 用于处理从Java注解中读取DTO与XML节点/属性的映射配置。
 * 
 * @author xu_dengfeng
 *
 */
public class AnnotationXPathMapHandler implements XPathMapHandler
{
    private String messageId;

    @Override
    public Map<String, Field> getXPathMap(Class<?> clazz)
    {
        Map<String, Field> xmlMap = new HashMap<String, Field>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields)
        {
            XmlTarget target = field.getAnnotation(XmlTarget.class);
            if (target != null)
            {
                field.setAccessible(true);
                xmlMap.put(target.value(), field);
            }
        }
        return xmlMap;
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
