package com.founder.hl7.xml;

import java.lang.reflect.Field;

import org.apache.commons.lang3.time.DateUtils;
import org.w3c.dom.Node;

import com.founder.hl7.maphandler.XPathMapHandler;

/**
 * 
 * @author wen_ruichao
 */
public class DateValueHandler implements ValueHandler
{

    private static final String[] DATE_PATTERN = new String[] {
            "yyyyMMddHHmmss", "yyyyMMdd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd" };

    @Override
    public void getValue(Node node, Field field, Object obj) throws Exception
    {
        String value = node.getTextContent();
        // 使用DateUtils类转换
        field.set(obj, DateUtils.parseDate(value, DATE_PATTERN));
    }

    @Override
    public void updateValue(Node node, Field field, Object obj)
            throws Exception
    {
        Object value = field.get(obj);
        if (value == null)
        {
            node.setTextContent("");
        }
        else
        {
            node.setTextContent(String.valueOf(value));
        }
    }

    @Override
    public void setValue(Node node, Field field, Object obj) throws Exception
    {
        // TODO Auto-generated method stub

    }
}
