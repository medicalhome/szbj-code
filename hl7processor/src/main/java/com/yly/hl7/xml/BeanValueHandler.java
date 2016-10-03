package com.yly.hl7.xml;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.xml.namespace.NamespaceContext;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.yly.hl7.MapNamespaceContext;
import com.yly.hl7.maphandler.XPathMapHandler;
import com.yly.hl7.util.XmlXPathUtil;

public class BeanValueHandler implements BaseValueHandler
{

    private XPathMapHandler xpathMapHandler;

    public void setXpathMapHandler(XPathMapHandler xpathMapHandler)
    {
        this.xpathMapHandler = xpathMapHandler;
    }

    @Override
    public void getValue(Node base, Field field, Object obj) throws Exception
    {
        process(base, obj, false);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void process(Node base, Object obj, boolean isUpdate)
            throws Exception
    {
        // if(base == null)
        // return;
        Map<String, Field> map = xpathMapHandler.getXPathMap(obj.getClass());
        Iterator<String> it = map.keySet().iterator();
        String xpath = null;
        XmlXPathUtil xmlXPathUtil = new XmlXPathUtil();
        xmlXPathUtil.setEncoding("utf-8");
        NamespaceContext namespaceContext = new MapNamespaceContext();

        Field childField = null;
        Class<?> valueType = null;
        String typeName = null;
        while (it.hasNext())
        {
            xpath = it.next();
            childField = map.get(xpath);
            valueType = childField.getType();
            typeName = valueType.getName();
            if (xpath == null || "".equalsIgnoreCase(xpath))
            {
                continue;
            }
            // xpath = xpath.substring(1);
            // 多节点对应的情况
            if (List.class.isAssignableFrom(valueType))
            {
                NodeList nodeList = xmlXPathUtil.getListValue(xpath, base,
                        namespaceContext);
                Class<?> typeClass = xpathMapHandler.getXPathMapChildType(childField);
                if (typeClass == null)
                {
                    throw new RuntimeException("缺少子类型的设定。");
                }
                ValueHandler handler = ValueHandlerFactory.getHandler(typeClass.getName());
                if (BaseValueHandler.class.isInstance(handler))
                {
                    BaseValueHandler baseValueHandler = (BaseValueHandler) handler;
                    baseValueHandler.setXpathMapHandler(xpathMapHandler);
                }
                int count = 0;
                if (nodeList != null)
                {
                    count = nodeList.getLength();
                }
                List list = (List<?>) childField.get(obj);
                Object item = null;
                if (isUpdate)
                {
                    appendListData(nodeList, list, handler);
                    this.removeNodeList(nodeList);
                }
                else
                {
                    if (list == null)
                    {
                        list = new ArrayList();
                        childField.set(obj, list);
                    }
                    for (int i = 0; i < count; i++)
                    {
                        if (String.class.isAssignableFrom(typeClass))
                        {
                            list.add(nodeList.item(i).getTextContent());
                        }
                        else
                        {
                            item = typeClass.newInstance();
                            handler.getValue(nodeList.item(i), childField, item);
                            list.add(item);
                        }
                    }
                }
            }
            else
            { // 单节点对应的情况
                Node result = xmlXPathUtil.getSingleNodeValue(xpath, base,
                        namespaceContext);
                if (result != null)
                {
                    ValueHandler handler = ValueHandlerFactory.getHandler(typeName);
                    if (isUpdate)
                    {
                        handler.updateValue(result, childField, obj);
                    }
                    else
                    {
                        handler.getValue(result, childField, obj);
                    }
                }
            }
        }
    }

    private void appendListData(NodeList nodeList, List<?> list,
            ValueHandler handler) throws Exception
    {
        if (list == null || list.size() < 1 || nodeList == null
            || nodeList.getLength() < 1)
        {
            return;
        }
        Node template = nodeList.item(0);
        Element parent = (Element) template.getParentNode();
        Node newNode = null;
        for (Object item : list)
        {
            newNode = template.cloneNode(true);
            handler.updateValue(newNode, null, item);
            parent.insertBefore(newNode, template);
        }
    }

    private void removeNodeList(NodeList nodeList)
    {
        if (nodeList == null || nodeList.getLength() < 1)
        {
            return;
        }
        Element parent = (Element) nodeList.item(0).getParentNode();
        int count = nodeList.getLength();
        for (int i = 0; i < count; i++)
            parent.removeChild(nodeList.item(i));
    }

    @Override
    public void updateValue(Node node, Field field, Object obj)
            throws Exception
    {
        process(node, obj, true);
    }

    /**
     * @param base
     * @param field
     * @param obj
     * retrun void
     */
    public void setValue(Node base, Field field, Object obj) throws Exception
    {
        Map<String, Field> xpathMap = xpathMapHandler.getXPathMap(obj.getClass());
        Iterator<String> iterator = xpathMap.keySet().iterator();
        while (iterator.hasNext())
        {
            String xpath = iterator.next();
            if (xpath == null || "".equalsIgnoreCase(xpath))
            {
                continue;
            }
            Field childField = xpathMap.get(xpath);
            if (childField.get(obj) == null)
            {
                continue;
            }
            xpath = xpath.substring(1);
            Class<?> valueType = childField.getType();
            if (List.class.isAssignableFrom(valueType))
            {
                Element element = null;
                Element parent = (Element) base;
                StringTokenizer tokens = new StringTokenizer(xpath, "/");
                while (tokens.hasMoreTokens())
                {
                    String name = tokens.nextToken();
                    // TODO
                    NodeList list = parent.getElementsByTagName(name.substring(2));
                    if (list.getLength() > 0)
                    {
                        element = (Element) list.item(0);
                    }
                    else
                    {
                        element = base.getOwnerDocument().createElement(
                                name.substring(2));
                        parent.appendChild(element);
                        parent = element;
                    }
                }
                Class<?> typeClass = xpathMapHandler.getXPathMapChildType(childField);
                if (typeClass == null)
                {
                    throw new RuntimeException("缺少子类型的设定。");
                }
                List<?> list = (List<?>) childField.get(obj);
                for (Object object : list)
                {
                    ValueHandler handler = ValueHandlerFactory.getHandler(typeClass.getName());
                    handler.setValue(element, null, object);
                }
            }
            else
            {
                String typeName = valueType.getName();
                Element element = null;
                Element parent = (Element) base;
                StringTokenizer tokens = new StringTokenizer(xpath, "/");
                while (tokens.hasMoreTokens())
                {
                    String name = tokens.nextToken();
                    if (name.contains("s:"))
                    {
                        // TODO
                        NodeList list = parent.getElementsByTagName(name.substring(2));
                        if (list.getLength() > 0)
                        {
                            element = (Element) list.item(0);
                        }
                        else
                        {
                            element = base.getOwnerDocument().createElement(
                                    name.substring(2));
                            parent.appendChild(element);
                            parent = element;
                        }
                    }
                    else if (name.contains("@"))
                    {
                        Attr attr = base.getOwnerDocument().createAttribute(
                                name.substring(1));
                        ValueHandlerFactory.getHandler(typeName).updateValue(
                                attr, childField, obj);
                        if (element == null)
                        {
                            element = parent;
                        }
                        element.setAttributeNode(attr);
                    }
                    else if (name.contains("text()"))
                    {
                        ValueHandlerFactory.getHandler(typeName).updateValue(
                                element, childField, obj);
                    }
                }
            }
        }
    }
}
