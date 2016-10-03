package com.yly.hl7.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.yly.hl7.MapNamespaceContext;

/**
 * 用于XML处理的工具类
 * 
 * @author xu_dengfeng
 * @author wen_ruichao
 *
 */
public class XmlXPathUtil
{
    private static final Logger logger = LoggerFactory.getLogger(XmlXPathUtil.class);

    /** XML编码 */
    private String encoding = "utf-8";

    private NamespaceContext defaultNamespaceContext;

    public XmlXPathUtil()
    {
        defaultNamespaceContext = new MapNamespaceContext();
    }

    public NodeList getListValue(String query, Node xmlDoc,
            NamespaceContext nsContext)
    {
        return (NodeList) evaluate(query, xmlDoc, nsContext,
                XPathConstants.NODESET);
    }

    /**
     * 获取XPath的值
     * 
     * @param query
     * @param xmlDocument
     * @param nsContext
     * @return String
     */
    public String getStringValue(String query, Node xmlDoc,
            NamespaceContext nsContext)
    {
        return (String) evaluate(query, xmlDoc, nsContext,
                XPathConstants.STRING);
    }

    public Double getNumberValue(String query, Node xmlDoc,
            NamespaceContext nsContext)
    {
        return (Double) evaluate(query, xmlDoc, nsContext,
                XPathConstants.NUMBER);
    }

    /**
     * 加载XML String资源
     * 
     * @param xmlString xml格式的字符串
     * @return Node
     */
    public Document loadXMLResource(String xmlString)
    {
        if (0xFEFF == xmlString.charAt(0))
        {
            xmlString = xmlString.substring(1);
        }
        InputSource source = new InputSource(new BufferedReader(
                new StringReader(xmlString)));
        return this.xmlSourceToDocument(source);
    }

    /**
     * 加载XML byte[]资源
     * 
     * @param xmlFile xml文件
     * @return Node
     */
    public Document loadXMLResource(byte xmlByte[])
    {
        String xmlString = "";
        try
        {
            xmlString = new String(xmlByte, encoding);
        }
        catch (UnsupportedEncodingException e)
        {
            if (logger.isDebugEnabled())
            {
                logger.debug(e.getMessage());
            }
        }
        if (0xFEFF == xmlString.charAt(0))
        {
            xmlString = xmlString.substring(1);
        }
        InputSource source = new InputSource(new BufferedReader(
                new StringReader(xmlString)));
        return this.xmlSourceToDocument(source);
    }

    /**
     * 加载XML File资源
     * 
     * @param xmlFile xml文件
     * @return Node
     */
    public Document loadXMLResource(File xmlFile)
    {
        InputSource source = null;
        try
        {
            source = new InputSource(new FileInputStream(xmlFile));
        }
        catch (FileNotFoundException e)
        {
            if (logger.isDebugEnabled())
            {
                logger.debug(e.getMessage());
            }
        }
        return this.xmlSourceToDocument(source);
    }

    /**
     * 把xml source 转换为Document
     * 
     * @param source
     * @return
     */
    private Document xmlSourceToDocument(InputSource source)
    {
        source.setEncoding(encoding);
        Document document = null;
        try
        {
            document = loadDocument(source);
        }
        catch (SAXParseException spe)
        {
            if (null != spe.getSystemId())
            {
                if (logger.isDebugEnabled())
                {
                    logger.debug("xpath解析错误，出错的行数是：" + spe.getLineNumber()
                        + "，uri：" + spe.getSystemId());
                    logger.debug(spe.getMessage());
                }
            }
            else
            {
                if (logger.isDebugEnabled())
                {
                    logger.debug(spe.getMessage());
                }
            }
        }
        catch (SAXException se)
        {
            document = null;
            if (logger.isDebugEnabled())
            {
                logger.debug("解析XML错误，请确保存在格式正确的XML文档。");
            }
        }
        catch (IOException ioe)
        {
            document = null;
            if (logger.isDebugEnabled())
            {
                logger.debug("不能加载文档，文档不可读取。");
            }
        }
        return document;
    }

    /**
     * 从InputSource加载document
     * 
     * @param source
     * @return Node
     * @throws SAXException
     * @throws IOException
     */
    private Document loadDocument(InputSource source) throws SAXException,
            IOException
    {
        Document document = null;
        DocumentBuilder parser = null;
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        domFactory.setNamespaceAware(true);
        domFactory.setValidating(false);
        try
        {
            parser = domFactory.newDocumentBuilder();
        }
        catch (ParserConfigurationException pce)
        {
            if (logger.isDebugEnabled())
            {
                logger.debug(pce.getMessage());
            }
        }
        parser.reset();
        document = parser.parse(source);
        return document;
    }

    /**
     * 设置xml编码
     * 
     * @param encoding
     */
    public void setEncoding(String encoding)
    {
        this.encoding = encoding;
    }

    public Node getSingleNodeValue(String query, Node xmlDoc,
            NamespaceContext nsContext)
    {
        return (Node) evaluate(query, xmlDoc, nsContext, XPathConstants.NODE);
    }

    public Object evaluate(String query, Node xmlDoc,
            NamespaceContext nsContext, QName returnType)
    {
        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        if (nsContext == null)
        {
            nsContext = this.defaultNamespaceContext;
        }
        xpath.setNamespaceContext(nsContext);
        XPathExpression expr = null;
        try
        {
            expr = xpath.compile(query);
        }
        catch (XPathExpressionException xpee)
        {
            Throwable x = xpee;
            if (null != xpee.getCause())
            {
                x = xpee.getCause();
                if ("javax.xml.transform.TransformerException".equals(x.getClass().getName()))
                {
                    if (logger.isDebugEnabled())
                    {
                        logger.debug("xpath表达式错误：所有的命名空间需要转换。");
                    }
                }
                else
                {
                    if (logger.isDebugEnabled())
                    {
                        logger.debug("xpath表达式错误：可能表达式格式有误。");
                    }
                }
            }
            return null;
        }
        Object result = null;
        try
        {
            result = expr.evaluate(xmlDoc, returnType);
            if (result == null)
            {
                logger.trace("xpath取值失败：{}", xpath);
            }
        }
        catch (XPathExpressionException e)
        {
            e.printStackTrace();
        }
        return result;
    }
}
