package com.founder.hl7;

import java.io.File;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.founder.hl7.maphandler.XPathMapHandler;
import com.founder.hl7.util.XmlXPathUtil;
import com.founder.hl7.xml.BaseValueHandler;
import com.founder.hl7.xml.ValueHandlerFactory;

/**
 * 用来处理HL7v3消息或者CDR文档的类
 * 
 * @author xu_dengfeng
 *
 * @param <T> 数据对象的类型
 */
public class Hl7v3XmlProcessor<T>
{
    public static final String FLAG_YES = "yes";

    public static final String ENCODE_UTF8 = "UTF-8";

    /**
     * 定义消息XML的父节点名称
     */
    public static final String ROOT_MESSAGE = "controlActProcess";

    /**
     * 定义CDR文档内容的根节点名称
     */
    private static final String ROOT_CDA = "ClinicalDocument";

    /**
     * 定义自定义xml消息的根节点名称
     */
    private static final String ROOT_MSG = "msg";

    /**
     * 创建XML文档的模板文件存放路径
     */
    private String templateDir = "./";

    /**
     * 创建XML文档用的模板文件扩展名
     */
    private String templateSuffix = ".xml";

    private XPathMapHandler xPathMapHandler;

    private XmlXPathUtil util = new XmlXPathUtil();

    public String getTemplateDir()
    {
        return templateDir;
    }

    public void setTemplateDir(String templateDir)
    {
        this.templateDir = templateDir;
    }

    /**
     * 将给定的XML字符串转换为给定的数据对象
     * 
     * @param message 要转换的XML字符串
     * @param obj 目标数据对象
     * @param mapHandler 用于读取数据对象与XML节点/属性映射关系的实现类
     * @throws Exception
     */
    public void parse(String message, T obj) throws Exception
    {
        Document doc = util.loadXMLResource(message);
        parse(doc.getDocumentElement(), obj);
    }

    /**
     * 将给定XML文件转换为给定的数据对象
     * 
     * @param msgFile 要转换的XML文件
     * @param obj 目标数据对象
     * @param mapHandler 用于读取数据对象与XML节点/属性映射关系的实现类
     * @throws Exception
     */
    public void parse(File msgFile, T obj) throws Exception
    {
        Document doc = util.loadXMLResource(msgFile);
        parse(doc.getDocumentElement(), obj);
    }

    /**
     * 将给定的XML元素转换为给定的数据对象
     * 
     * @param root 要转换的XML元素
     * @param obj 目标数据对象
     * @param mapHandler 用于读取数据对象与XML节点/属性映射关系的实现类
     * @throws Exception
     */
    public void parse(Element root, T obj) throws Exception
    {
        Element base = getBaseElement(root);

        BaseValueHandler handler = (BaseValueHandler) ValueHandlerFactory.getHandler(obj.getClass().getName());
        handler.setXpathMapHandler(xPathMapHandler);
        handler.getValue(base, null, obj);
    }

    /**
     * 取得消息或者CDA文档的内容节点
     * 
     * @param root XML的根节点
     * @return 消息的消息体或者CDA的根节点
     */
    private Element getBaseElement(Element root)
    {
        String rootName = root.getLocalName();
        if (ROOT_CDA.equalsIgnoreCase(rootName))
            return root;
        else if (ROOT_MSG.equalsIgnoreCase(rootName))
            return root;
        else
        {
            NodeList nodeList = root.getElementsByTagName(ROOT_MESSAGE);
            if (nodeList == null || nodeList.getLength() < 1)
                return root;
            return (Element) nodeList.item(0);
        }
    }

    /**
     * 根据给定的模板和数据对象，按照映射关系创建XML，并把内容XML内容输出到
     * 给定的StringWriter中。
     * 
     * @param template 用来创建消息的模板XML文件名，不带扩展名。一般与消息根节点同名。
     * @param obj 要转换为XML文档的数据对象
     * @param mapHandler 用于读取数据对象与XML节点/属性映射关系的实现类
     * @param writer XML文档的输出目标
     * @throws Exception
     */
    public void create(String template, T obj, XPathMapHandler mapHandler,
            StringWriter writer) throws Exception
    {
        Document doc = util.loadXMLResource(new File(this.templateDir
            + template + this.templateSuffix));
        Element base = this.getBaseElement(doc.getDocumentElement());
        BaseValueHandler handler = (BaseValueHandler) ValueHandlerFactory.getHandler(obj.getClass().getName());
        handler.setXpathMapHandler(mapHandler);
        handler.updateValue(base, null, obj);

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, ENCODE_UTF8);
        transformer.setOutputProperty(OutputKeys.INDENT, FLAG_YES);

        DOMSource xmlSource = new DOMSource(doc);
        Result output = new StreamResult(writer);
        transformer.transform(xmlSource, output);
    }

    public void setXPathMapHandler(XPathMapHandler xPathMapHandler)
    {
        this.xPathMapHandler = xPathMapHandler;
    }
}
