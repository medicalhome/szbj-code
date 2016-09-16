package com.founder.hl7.xml;

import java.lang.reflect.Field;

import org.w3c.dom.Node;

public class IntegerValueHandler implements ValueHandler
{

    @Override
    public void getValue(Node node, Field field, Object obj) throws Exception
    {
        String value = node.getTextContent();
        field.set(obj, Integer.valueOf(value == null ? "0" : value));
    }

    @Override
    public void updateValue(Node node, Field field, Object obj)
            throws Exception
    {
        String value = String.valueOf(field.get(obj));
        node.setTextContent(value == null ? "" : value);
    }

    public void addValue(Node node, Field field, Object obj)
    {

    }

    @Override
    public void setValue(Node node, Field field, Object obj) throws Exception
    {
        // TODO Auto-generated method stub

    }

}
