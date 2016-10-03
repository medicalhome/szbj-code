package com.yly.hl7.xml;

import java.lang.reflect.Field;

import org.w3c.dom.Node;

public interface ValueHandler
{

    public void getValue(Node node, Field field, Object obj) throws Exception;

    public void updateValue(Node node, Field field, Object obj)
            throws Exception;

    public void setValue(Node node, Field field, Object obj) throws Exception;
}
