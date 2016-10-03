package com.yly.hl7.xml;

import java.lang.reflect.Field;

import org.w3c.dom.Node;

public class StringValueHandler implements ValueHandler
{

    @Override
    public void getValue(Node node, Field field, Object obj) throws Exception
    {
        String value = node.getTextContent();
        field.set(obj, value == null ? "" : value);
    }

    @Override
    public void updateValue(Node node, Field field, Object obj)
            throws Exception
    {
        Object value = field.get(obj);
        node.setTextContent(value == null ? "" : String.valueOf(value));
    }

    public void setValue(Node node, Field field, Object obj)
    {

    }
}
