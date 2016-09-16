package com.founder.cdr.batch.send.others;

import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import com.founder.cdr.batch.core.Hl7Message;
import com.founder.cdr.batch.processor.MessageProcessor;
import com.founder.cdr.core.Constants;
import com.founder.cdr.entity.Message;
import com.founder.cdr.hl7.dto.BaseDto;
import com.founder.cdr.hl7.dto.WarningMessageLabReport;
import com.founder.cdr.hl7.dto.WarningMessageLabReportResult;
import com.founder.cdr.hl7.dto.warningMessage.WARNINGMESSAGE;
import com.founder.cdr.util.PropertiesUtils;
import com.founder.cdr.util.StringUtils;
import com.founder.cdr.wmq.Hl7MessageWmq;
import com.founder.cdr.wmq.WMQMessageSender;
import com.founder.cdr.wmq.WMQQueueFactory;

/**
 * 警告通知共通方法
 * 
 * @author jin_peng
 */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class WarningOthers<T>
{
    private static final String OUTPUT_TYPE = "xml";

    private static final String OUTPUT_INDENT_YES = "yes";

    private static final String OUTPUT_INDENT_PROPNAME = "{http://xml.apache.org/xslt}indent-amount";

    private static final String INDENT_AMOUNT = "4";

    private static final String XML_STRING_LIST = "item";

    private static final String XML_OBJECT_LIST = "component";

    private static final String SERVICE_ID_BS319 = "BS319";

    private static final String SERVICE_ID_BS354 = "BS354";

    @Autowired
    private MessageProcessor processor;

    private static String host;

    private static String channel;

    private static String ccsid;

    private static String port;

    private static String transport;

    private static String managerName;
    
    private static String visitDept="";

    static
    {
        host = PropertiesUtils.getValue("mq.in.hostname");

        channel = PropertiesUtils.getValue("mq.in.channel");

        ccsid = PropertiesUtils.getValue("mq.in.ccsid");

        port = PropertiesUtils.getValue("mq.in.port");

        transport = PropertiesUtils.getValue("mq.in.transport");

        managerName = PropertiesUtils.getValue("mq.in.qmName");
    }

    /**
     * 构造方法
     */
    public WarningOthers()
    {

    }

    public static boolean isMathValue(String str)
    {
        boolean isMath = false;

        String valueMatched = "^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))+)$";

        if (!StringUtils.isEmpty(str))
        {
            if (str.matches(valueMatched))
            {
                isMath = true;
            }
        }

        return isMath;
    }

    /**
     * 创建公用服务规则接口参数
     * @param pocdin000040uv01 检验报告对象
     * @param rootName 所构造的xml文件根元素名称
     * @param fieldsToBlanked 不构建在xml文件中的属性集合
     * @return 生成的xml参数
     */
    public String callCreateByDto(Object rootObj, String rootName,
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
    private void buildInputParam(Object convertedObj, Document doc,
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
            	String elValue = StringUtils.nullToEmpty(String.valueOf(field.get(convertedObj)));
            	if("visitDept".equals(fieldName)){
            		visitDept = elValue;
            	}
            	
            	if("itemDept".equals(fieldName)){
            		elValue = visitDept;
            	}
            	
                Text childTextNode = doc.createTextNode(elValue);

                childElement.appendChild(childTextNode);

                element.appendChild(childElement);
            }
        }
    }
    //
    /**
     * 创建公用服务规则接口参数
     * @param pocdin000040uv01 检验报告对象
     * @param rootName 所构造的xml文件根元素名称
     * @param fieldsToBlanked 不构建在xml文件中的属性集合
     * @return 生成的xml参数
     */
    public String callCreateByDtoInfectious(Object rootObj, String rootName,
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

        buildInputParamInfectious(rootObj, doc, rootElement, fieldsToBlanked);

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
    private void buildInputParamInfectious(Object convertedObj, Document doc,
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
                            //Element childsElement = doc.createElement(XML_OBJECT_LIST);
                        	childElement = (Element)childElement.cloneNode(false);
                        	
                        	buildInputParamInfectious(object, doc, childElement,
                                    fieldsToBlanked);

                            //childElement.appendChild(childsElement);
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
    //

    public String getSendMessageContent(WARNINGMESSAGE warningMessage)
            throws Exception
    {
        Document doc = getSendMessageDocument(warningMessage);

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

    private Document getSendMessageDocument(WARNINGMESSAGE warningMessage)
            throws Exception
    {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

        DocumentBuilder dBuilder = documentBuilderFactory.newDocumentBuilder();

        Document doc = dBuilder.newDocument();

        Element rootElement = getElement("msg", doc);

        doc.appendChild(rootElement);

        if (warningMessage != null)
        {
            // a.构造消息头信息
            Element head = getElement("head", doc);

            Element msgId = getElementByValue("msgId",
                    warningMessage.getMsgId(), doc);
            Element msgName = getElementByValue("msgName",
                    warningMessage.getMsgName(), doc);
            Element sourceSysCode = getElementByValue("sourceSysCode",
                    warningMessage.getSourceSysCode(), doc);
            Element targetSysCode = getElementByValue("targetSysCode",
                    warningMessage.getTargetSysCode(), doc);
            Element createTime = getElementByValue("createTime",
                    warningMessage.getCreateTime(), doc);

            // b。将构造完成的头信息加入到文档中
            contactElements(head, msgId, msgName, sourceSysCode, targetSysCode,
                    createTime);

            contactElements(rootElement, head);

            // a.构造消息体信息
            Element body = getElement("body", doc);

            // b.构造患者相关信息
            Element patient = getElement("patient", doc);

            Element patientDomain = getElementByValue("patientDomain",
                    warningMessage.getPatientDomain(), doc);
            Element patientLid = getElementByValue("patientLid",
                    warningMessage.getPatientLid(), doc);
            Element patientName = getElementByValue("patientName",
                    warningMessage.getPatientName(), doc);
            Element genderCode = getElementByValue("genderCode",
                    warningMessage.getGenderCode(), doc);
            Element age = getElementByValue("age", warningMessage.getAge(), doc);
            Element visitDate = getElementByValue("visitDate",
                    warningMessage.getVisitDate(), doc);
            Element visitTimes = getElementByValue("visitTimes",
                    warningMessage.getVisitTimes(), doc);
            Element visitDept = getElementByValue("visitDept",
                    warningMessage.getVisitDept(), doc);
            Element visitDeptName = getElementByValue("visitDeptName",
                    warningMessage.getVisitDeptName(), doc);
            /**
             * 医疗机构
             */
            Element hospitalCodeEl = getElementByValue("hospitalCode",
                    warningMessage.getOrgCode(), doc);
            /**
             * 医疗机构名称
             */
            Element hospitalNameEl = getElementByValue("hospitalName",
                    warningMessage.getOrgName(), doc);
            
            Element wardsNo = getElementByValue("wardsNo",
                    warningMessage.getWardsNo(), doc);
            Element bedNo = getElementByValue("bedNo",
                    warningMessage.getBedNo(), doc);
            Element telcom = getElementByValue("telcom",
                    warningMessage.getTelcom(), doc);
            
            // b.将构造完成的患者相关信息加入到文档中
            contactElements(body, patient);

            contactElements(patient, patientDomain, patientLid, patientName,
                    genderCode, age, visitDate, visitTimes, visitDept,
                    visitDeptName,hospitalCodeEl,hospitalNameEl, wardsNo, bedNo, telcom);

            // b.构造报告警告信息
            Element warningInfo = getElement("warningInfo", doc);

            // c.构造报告警告头信息
            Element warningType = getElementByValue("warningType",
                    warningMessage.getWarningType(), doc);
            Element reportNo = getElementByValue("reportNo",
                    warningMessage.getReportNo(), doc);
            Element reportLink = getElementByValue("reportLink",
                    warningMessage.getReportLink(), doc);
            /**
             * 报告时间
             */
            Element reportDateEl = getElementByValue("reportDate",
                    warningMessage.getReportDate(), doc);
            /**
             * 确认报告时间
             */
            Element reviewDateEl = getElementByValue("reviewDate",
                    warningMessage.getReviewDate(), doc);
            
            // 将构造完成的报告警告头信息加入到文档中
            contactElements(warningInfo, warningType, reportNo, reportLink,reportDateEl,reviewDateEl);

            // c.构造报告项目及结果信息
            List<WarningMessageLabReport> reportList = warningMessage.getReport();

            List<Element> resElementList = getLabResultItemElement(reportList,
                    doc);

            // 将构造完成的检验项目及具体结果信息加入到文档中
            if (resElementList != null && !resElementList.isEmpty())
            {
                for (Element element : resElementList)
                {
                    contactElements(warningInfo, element);
                }
            }

            // 将构造完成的报告警告信息加入文档中
            contactElements(body, warningInfo);

            // b.构造消息体根层信息
            Element requestTime = getElementByValue("requestTime",
                    warningMessage.getRequestTime(), doc);
            Element applyPerson = getElementByValue("applyPerson",
                    warningMessage.getApplyPerson(), doc);
            Element applyPersonName = getElementByValue("applyPersonName",
                    warningMessage.getApplyPersonName(), doc);

            // 将构造出的消息体根层信息加入到文档中
            contactElements(body, requestTime, applyPerson, applyPersonName);

            // 将构造完成的文档加入到文档根目录中
            contactElements(rootElement, body);
        }

        return doc;
    }

    private List<Element> getLabResultItemElement(
            List<WarningMessageLabReport> reportList, Document doc)
    {
        List<Element> eleList = null;

        if (reportList != null && !reportList.isEmpty())
        {
            eleList = new ArrayList<Element>();

            for (WarningMessageLabReport warningMessageLabReport : reportList)
            {
                Element warningItem = getElement("warningItem", doc);

                Element labItemCode = getElementByValue("labItemCode",
                        warningMessageLabReport.getLabItemCode(), doc);
                Element labItemName = getElementByValue("labItemName",
                        warningMessageLabReport.getLabItemName(), doc);

                contactElements(warningItem, labItemCode, labItemName);

                List<WarningMessageLabReportResult> warningLabReportItem = warningMessageLabReport.getReportResult();

                if (warningLabReportItem != null
                    && !warningLabReportItem.isEmpty())
                {
                    for (WarningMessageLabReportResult warningMessageLabReportResult : warningLabReportItem)
                    {
                        Element waringDetail = getElement("waringDetail", doc);

                        Element itemCode = getElementByValue("itemCode",
                                warningMessageLabReportResult.getItemCode(),
                                doc);
                        Element itemNameCn = getElementByValue("itemNameCn",
                                warningMessageLabReportResult.getItemNameCn(),
                                doc);
                        Element itemNameEn = getElementByValue("itemNameEn",
                                warningMessageLabReportResult.getItemNameEn(),
                                doc);
                        Element itemNumValue = getElementByValue(
                                "itemNumValue",
                                warningMessageLabReportResult.getItemNumValue(),
                                doc);
                        Element itemStrValue = getElementByValue(
                                "itemStrValue",
                                warningMessageLabReportResult.getItemStrValue(),
                                doc);
                        Element itemUnit = getElementByValue("itemUnit",
                                warningMessageLabReportResult.getItemUnit(),
                                doc);
                        Element checkStatus = getElementByValue("checkStatus",
                                warningMessageLabReportResult.getCheckStatus(),
                                doc);
                        Element checkMessage = getElementByValue(
                                "checkMessage",
                                warningMessageLabReportResult.getCheckMessage(),
                                doc);
                        Element suggestion = getElementByValue(
                                "suggestion",
                                warningMessageLabReportResult.getSuggestion(),
                                doc);
                        contactElements(waringDetail, itemCode, itemNameCn,
                                itemNameEn, itemNumValue, itemStrValue,
                                itemUnit, checkStatus, checkMessage,suggestion);

                        contactElements(warningItem, waringDetail);
                    }
                }

                eleList.add(warningItem);
            }
        }

        return eleList;
    }

    private Element getElement(String name, Document doc)
    {
        Element childsElement = doc.createElement(name);

        return childsElement;
    }

    private Element getElementByValue(String name, String value, Document doc)
    {
        Element childsElement = doc.createElement(name);

        childsElement.setTextContent(value);

        return childsElement;
    }

    private void contactElements(Element parentElement, Element... element)
    {
        if (element != null && element.length != 0)
        {
            for (Element ele : element)
            {
                parentElement.appendChild(ele);
            }
        }
    }

    private boolean isFieldExistence(String[] strs, String target)
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

    public BaseDto getBaseDto(String xmlContent, String resultDtoId)
            throws Exception
    {
        processor.setMessageId(resultDtoId);

        Message message = new Message();

        message.setContent(xmlContent);

        BaseDto base = null;

        base = processor.process(message);

        return base;
    }

    public String getServiceId(String sourceSystemId)
    {
        String serviceId = "";

        if (Constants.SOURCE_LAB.equals(sourceSystemId))
        {
            serviceId = SERVICE_ID_BS319;
        }
        else if (Constants.SOURCE_MICROBE.equals(sourceSystemId))
        {
            serviceId = SERVICE_ID_BS354;
        }

        return serviceId;
    }

    public String getUpdateXml(String template, T obj, String updateDtoId)
            throws Exception
    {
        // 生成要发送的XML
        /**Hl7v3XmlProcessor<T> builder = new Hl7v3XmlProcessor<T>();

        FileXPathMapHandler mapHandler = new FileXPathMapHandler();

        mapHandler.setMessageId(updateDtoId);

        mapHandler.setClassLoader(this.getClass().getClassLoader());

        StringWriter outWriter = new StringWriter();

        builder.createTo(template, obj, mapHandler, outWriter);

        return outWriter.toString();*/

        return null;
    }

    public void sendMessges(WMQMessageSender sender, Hl7Message hl7message,
            String queueName) throws Exception
    {
        WMQQueueFactory factory = new WMQQueueFactory();

        factory.setHost(host);

        factory.setManagerName(managerName);

        factory.setPort(port);

        factory.setChannel(channel);

        factory.setCcsid(ccsid);

        factory.setTransport(transport);

        try
        {
            factory.afterPropertiesSet();
        }
        catch (Exception e)
        {
            e.printStackTrace();

            throw new RuntimeException("警告通知发送消息时连接队列管理器失败！");
        }

        sender.setQueueFactory(factory);

        Hl7MessageWmq hl7MessageWmq = new Hl7MessageWmq();
        hl7MessageWmq.setServiceId(hl7message.getServiceId());
        hl7MessageWmq.setHospitalCd(hl7message.getHospitalCd());
        hl7MessageWmq.setDomainId(hl7message.getDomainId());
        hl7MessageWmq.setDatabaseId(hl7message.getDatabaseId());
        hl7MessageWmq.setExecUnitId(hl7message.getExecUnitId());
        hl7MessageWmq.setHospitalCd(hl7message.getHospitalCd());
        hl7MessageWmq.setSourceSysCd(hl7message.getSourceSysCd());
        hl7MessageWmq.setOrderDispatchType(hl7message.getOrderDispatchType());
        hl7MessageWmq.setQueueName(hl7message.getQueueName());
        hl7MessageWmq.setContent(hl7message.getContent());
        hl7MessageWmq.setTargetSystemCode(hl7message.getTargetSystemCode());
        sender.sendMessageTo(hl7MessageWmq, queueName);
    }

    public Hl7Message getHl7Message(Message message, String serviceId,
            String messageContent, String targetSystemId, String queueName,
            String patientDomain,String orgCode, String sourceSysCd) throws Exception
    {
        Hl7Message hl7message = new Hl7Message();

        hl7message.setServiceId(serviceId);
        
        hl7message.setHospitalCd(orgCode);

        hl7message.setDomainId(patientDomain);

        hl7message.setExecUnitId(Constants.MSG_EXEC_UNIT_ID);

        hl7message.setOrderDispatchType(Constants.MSG_ORDER_DISPATCH_TYPE);

        hl7message.setTargetSystemCode(targetSystemId);

        hl7message.setSourceSysCd(sourceSysCd);

        hl7message.setQueueName(queueName);

        hl7message.setContent(messageContent);

        return hl7message;
    }
}
