package com.yly.cdr.util;

import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

public class XmlUtils
{
    private static final String OUTPUT_TYPE = "xml";

    private static final String OUTPUT_INDENT_YES = "yes";

    private static final String OUTPUT_INDENT_PROPNAME = "{http://xml.apache.org/xslt}indent-amount";

    private static final String INDENT_AMOUNT = "4";

    private static final String XML_STRING_LIST = "item";

    private static final String XML_OBJECT_LIST = "component";

    /**
     * 创建公用服务规则接口参数
     * @param pocdin000040uv01 检验报告对象
     * @param rootName 所构造的xml文件根元素名称
     * @param fieldsToBlanked 不构建在xml文件中的属性集合
     * @return 生成的xml参数
     */
    public static String callCreateByDto(Object rootObj, String rootName,
            String... fieldsToBlanked) throws Exception
    {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

        DocumentBuilder dBuilder = documentBuilderFactory.newDocumentBuilder();

        Document doc = dBuilder.newDocument();

        String rn = rootObj.getClass().getSimpleName().toLowerCase();

        if (!StringUtils.isEmpty(rootName))
        {
            rn = rootName;
        }

        Element rootElement = doc.createElement(rn);

        doc.appendChild(rootElement);

        buildInputParam(rootObj, doc, rootElement, fieldsToBlanked);

        Transformer transFormer = TransformerFactory.newInstance().newTransformer();

        transFormer.setOutputProperty(OutputKeys.METHOD, OUTPUT_TYPE);

        transFormer.setOutputProperty(OutputKeys.INDENT, OUTPUT_INDENT_YES);

        transFormer.setOutputProperty(OUTPUT_INDENT_PROPNAME, INDENT_AMOUNT);

        StringWriter paramWriter = new StringWriter();

        DOMSource xmlSource = new DOMSource(doc);

        Result output = new StreamResult(paramWriter);

        transFormer.transform(xmlSource, output);

        StringBuffer paramBuffer = paramWriter.getBuffer();

        paramWriter.close();

        return paramBuffer.toString();
    }

    /**
     * 构造调用规则接口时需输入的xml参数
     * @param convertedObj 用来构造xml的检验报告相关对象内容
     * @param doc 已构造完成的xml文档对象
     * @param fieldsToBlanked 不构建在xml文件中的属性集合
     */
    private static void buildInputParam(Object convertedObj, Document doc,
            Element element, String... fieldsToBlanked) throws Exception
    {
        Class<? extends Object> convertedObjClass = convertedObj.getClass();

        Field[] fieldList = convertedObjClass.getDeclaredFields();

        for (Field field : fieldList)
        {
            field.setAccessible(true);

            Class<?> fieldType = field.getType();

            String fieldName = field.getName();

            if (fieldsToBlanked != null && fieldsToBlanked.length != 0)
            {
                if (isFieldExistence(fieldsToBlanked, fieldName))
                {
                    continue;
                }
            }

            Element childElement = doc.createElement(fieldName);

            if (List.class.isAssignableFrom(fieldType))
            {
                List<?> convertList = (List<?>) field.get(convertedObj);

                if (convertList != null && !convertList.isEmpty())
                {
                    for (Object object : convertList)
                    {
                        if (String.class.isInstance(object))
                        {
                            Element childsElement = doc.createElement(XML_STRING_LIST);

                            Text childTextNode = doc.createTextNode(StringUtils.nullToEmpty(String.valueOf(object)));

                            childsElement.appendChild(childTextNode);

                            childElement.appendChild(childsElement);
                        }
                        else
                        {
                            Element childsElement = doc.createElement(XML_OBJECT_LIST);

                            buildInputParam(object, doc, childsElement,
                                    fieldsToBlanked);

                            childElement.appendChild(childsElement);
                        }

                        element.appendChild(childElement);
                    }
                }
            }
            else
            {
                Text childTextNode = doc.createTextNode(StringUtils.nullToEmpty(String.valueOf(field.get(convertedObj))));

                childElement.appendChild(childTextNode);

                element.appendChild(childElement);
            }
        }
    }

    /**
     * 创建公用服务规则接口参数
     * @param pocdin000040uv01 检验报告对象
     * @param rootName 所构造的xml文件根元素名称
     * @param fieldsToBlanked 不构建在xml文件中的属性集合
     * @return 生成的xml参数
     */
    public static String callCreateCommonByDto(Object rootObj, String rootName,
            String... fieldsToBlanked) throws Exception
    {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

        DocumentBuilder dBuilder = documentBuilderFactory.newDocumentBuilder();

        Document doc = dBuilder.newDocument();

        String rn = rootObj.getClass().getSimpleName().toLowerCase();

        if (!StringUtils.isEmpty(rootName))
        {
            rn = rootName;
        }

        Element rootElement = doc.createElement(rn);

        doc.appendChild(rootElement);

        buildCommonInputParam(rootObj, doc, rootElement, fieldsToBlanked);

        Transformer transFormer = TransformerFactory.newInstance().newTransformer();

        transFormer.setOutputProperty(OutputKeys.METHOD, OUTPUT_TYPE);

        transFormer.setOutputProperty(OutputKeys.INDENT, OUTPUT_INDENT_YES);

        transFormer.setOutputProperty(OUTPUT_INDENT_PROPNAME, INDENT_AMOUNT);

        StringWriter paramWriter = new StringWriter();

        DOMSource xmlSource = new DOMSource(doc);

        Result output = new StreamResult(paramWriter);

        transFormer.transform(xmlSource, output);

        StringBuffer paramBuffer = paramWriter.getBuffer();

        paramWriter.close();

        return paramBuffer.toString();
    }

    /**
     * 构造调用规则接口时需输入的xml参数
     * @param convertedObj 用来构造xml的检验报告相关对象内容
     * @param doc 已构造完成的xml文档对象
     * @param fieldsToBlanked 不构建在xml文件中的属性集合
     */
    private static void buildCommonInputParam(Object convertedObj,
            Document doc, Element element, String... fieldsToBlanked)
            throws Exception
    {
        Class<? extends Object> convertedObjClass = convertedObj.getClass();

        Field[] fieldList = convertedObjClass.getDeclaredFields();

        for (Field field : fieldList)
        {
            field.setAccessible(true);

            Class<?> fieldType = field.getType();

            String fieldName = field.getName();

            if (fieldsToBlanked != null && fieldsToBlanked.length != 0)
            {
                if (isFieldExistence(fieldsToBlanked, fieldName))
                {
                    continue;
                }
            }

            Element childElement = doc.createElement(fieldName);

            if (List.class.isAssignableFrom(fieldType))
            {
                List<?> convertList = (List<?>) field.get(convertedObj);

                if (convertList != null && !convertList.isEmpty())
                {
                    for (Object object : convertList)
                    {
                        childElement = (Element) childElement.cloneNode(false);

                        buildCommonInputParam(object, doc, childElement,
                                fieldsToBlanked);

                        element.appendChild(childElement);
                    }
                }
            }
            else
            {
                String[] eleAttrs = fieldName.split("_");

                // 元素属性请用"元素名_属性名"形式, 其中每个list元素的根属性为"root_属性名"形式。
                if (eleAttrs.length > 1)
                {
                    String eleName = eleAttrs[0];
                    String attrName = eleAttrs[1];

                    Attr attr = doc.createAttribute(attrName);
                    attr.setValue(StringUtils.nullToEmpty(String.valueOf(field.get(convertedObj))));

                    if("root".equals(eleName))
                    {
                        element.setAttributeNode(attr);
                    }
                    else
                    {
                        NodeList nodes = element.getElementsByTagName(eleName);
                        
                        if (nodes != null && nodes.getLength() != 0)
                        {
                            Element ele = (Element) nodes.item(0);
                            ele.setAttributeNode(attr);
                        }
                    }
                }
                else
                {
                    Text childTextNode = doc.createTextNode(StringUtils.nullToEmpty(String.valueOf(field.get(convertedObj))));

                    childElement.appendChild(childTextNode);

                    element.appendChild(childElement);
                }
            }
        }
    }

    /**
     * 判断某属性是否存在
     * @param strs 属性集合
     * @param target 目标属性
     * @return 是否存在标识
     */
    private static boolean isFieldExistence(String[] strs, String target)
    {
        boolean isFieldExistence = false;

        if (strs != null && strs.length != 0)
        {
            for (String str : strs)
            {
                if (str.equals(target))
                {
                    isFieldExistence = true;

                    break;
                }
            }
        }

        return isFieldExistence;
    }
}
