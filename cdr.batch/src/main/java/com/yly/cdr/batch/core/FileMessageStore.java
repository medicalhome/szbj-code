package com.yly.cdr.batch.core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.jms.JMSException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.yly.cdr.util.DateUtils;

/**
 * 
 * @author xu_dengfeng
 *
 * <pre>
 * <?xml version="1.0" encoding="UTF-8"?>
 * <Hl7Message>
 *     <uuid>消息唯一ID</uuid>
 *     <serviceId>项目自定义服务ID</serviceId>
 *     <messageType>消息类型（HL7v3消息编号）</messageType>
 *     <body>
 *     <![CDATA[
 *     消息内容
 *     ]]>
 *     </body>
 * </Hl7Message>
 * </pre>
 */
public class FileMessageStore implements MessageStore
{
    private static final Logger logger = LoggerFactory.getLogger(FileMessageStore.class);

    private static final String TAG_ROOT = "Hl7Message";

    private static final String TAG_UUID = "uuid";

    private static final String TAG_SERVICEID = "serviceId";

    private static final String TAG_MESSAGE_TYPE = "messageType";

    private static final String TAG_BODY = "body";

    /**
     * 错误原因
     */
    private static final String TAG_REASON = "reason";

    private String baseDir;

    private File store;

    private File queueStore;

    private File errorStore;

    private File backupStore;

    private String suffix = ".xml";

    // $Author :wu_biao
    // $Date : 2013/03/13 
    // 警告通知框架 ADD BEGIN
    @Override
    public List<Hl7Message> retrieveMessages(String queueName,String state,String date)
    {
    // 警告通知框架 ADD END
        List<Hl7Message> result = new ArrayList<Hl7Message>();
        File path = new File(queueStore, queueName);
        File[] files = path.listFiles();
        File file = null;
        Hl7Message msg = null;
        for (int i = 0; files != null && i < files.length; i++)
        {
            file = files[i];
            msg = loadFile(file);
            if (msg != null)
                result.add(msg);
        }
        return result;
    }

    private Hl7Message loadFile(File file)
    {
        Hl7Message message = new Hl7Message();
        String name = file.getName();
        String uuid = null;
        if (name.length() > 3)
            uuid = name.substring(0, name.length() - 4);
        message.setUuid(uuid);

        DocumentBuilder builder = getDocumentBuilder();
        try
        {
            Document doc = builder.parse(file);
            Element root = doc.getDocumentElement();
            NodeList nodes = root.getChildNodes();
            int size = nodes.getLength();
            for (int i = 0; i < size; i++)
            {
                Node item = nodes.item(i);
                if (item.getNodeType() == Node.ELEMENT_NODE)
                {
                    if (TAG_UUID.equals(item.getNodeName()))
                    {
                        Node tn = item.getFirstChild();
                        if (tn != null)
                            message.setUuid(tn.getNodeValue());
                    }
                    else if (TAG_SERVICEID.equals(item.getNodeName()))
                    {
                        Node tn = item.getFirstChild();
                        if (tn != null)
                            message.setServiceId(tn.getNodeValue());
                    }
                    else if (TAG_MESSAGE_TYPE.equals(item.getNodeName()))
                    {
                        Node tn = item.getFirstChild();
                        if (tn != null)
                            message.setMessageType(tn.getNodeValue());
                    }
                    else if (TAG_BODY.equals(item.getNodeName()))
                    {
                        Node tn = item.getFirstChild();
                        if (tn != null)
                            message.setText(tn.getNodeValue());
                    }
                    else
                        logger.warn("Unknow message format");
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            message = null;
        }

        return message;
    }

    private DocumentBuilder getDocumentBuilder()
    {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try
        {
            // Convert CDATA section to text node
            dbf.setCoalescing(true);
            DocumentBuilder builder = dbf.newDocumentBuilder();
            return builder;
        }
        catch (ParserConfigurationException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void saveError(Hl7Message message, String reason)
    {
        File source = new File(queueStore, message.getUuid() + this.suffix);
        source.delete();
        File dest = new File(errorStore, message.getUuid() + this.suffix);
        saveMessage(dest, message, null);
    }

    private void moveFile(File source, File dest)
    {
        if (dest.isFile() && dest.exists())
            dest.delete();

        source.renameTo(dest);
    }

    @Override
    public void saveMessage(Hl7Message message) throws Exception
    {
        String uuid = message.getUuid();
        if (uuid == null || "".equalsIgnoreCase(uuid))
        {
            uuid = UUID.randomUUID().toString();
            message.setUuid(uuid);
        }

        File file = new File(queueStore, uuid + this.suffix);
        saveMessage(file, message, null);
    }

    private void saveMessage(File file, Hl7Message message, String reason)
    {
        try
        {
            FileOutputStream fos = new FileOutputStream(file);
            OutputStreamWriter writer = new OutputStreamWriter(fos, "UTF-8");

            Document doc = this.messageToDoc(message, reason);
            writer.write(docToString(doc));
            writer.flush();
            writer.close();
            fos.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private Document messageToDoc(Hl7Message message, String reason)
    {
        DocumentBuilder builder = getDocumentBuilder();
        Document doc = builder.newDocument();
        Element root = doc.createElement(TAG_ROOT);
        doc.appendChild(root);

        addElement(doc, root, TAG_UUID, message.getUuid());
        addElement(doc, root, TAG_SERVICEID, message.getServiceId());
        addElement(doc, root, TAG_MESSAGE_TYPE, message.getMessageType());

        if (reason != null && !"".equals(reason))
            addElement(doc, root, TAG_REASON, reason);

        try
        {
            addElement(doc, root, TAG_BODY, message.getText());
        }
        catch (JMSException e)
        {
            e.printStackTrace();
            throw new RuntimeException("Unknow JMS Exception");
        }

        return doc;
    }

    private void addElement(Document doc, Element parent, String tagName,
            String content)
    {
        Element e = doc.createElement(tagName);
        if (content.contains("<"))
        {
            CDATASection cdataSection = doc.createCDATASection(content);
            e.appendChild(cdataSection);
        }
        else
            e.setTextContent(content);
        parent.appendChild(e);
    }

    private String docToString(Document doc)
    {
        try
        {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            // transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount",
            // "4");

            StreamResult streamResult = new StreamResult(new StringWriter());
            DOMSource domSource = new DOMSource(doc);
            transformer.transform(domSource, streamResult);
            String xmlString = streamResult.getWriter().toString();
            return xmlString;
        }
        catch (TransformerException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public String getBaseDir()
    {
        return baseDir;
    }

    public void setBaseDir(String baseDir)
    {
        this.baseDir = baseDir;
        this.store = new File(this.baseDir);
        if (!store.isDirectory() || !store.exists())
            store.mkdirs();

        queueStore = new File(baseDir, "data");
        queueStore.mkdirs();
        errorStore = new File(baseDir, "error");
        errorStore.mkdir();
        backupStore = new File(baseDir, "backup");
        backupStore.mkdir();
    }

    @Override
    public void removeMessage(Hl7Message message,String flag)
    {
        File source = new File(queueStore, message.getUuid() + this.suffix);
        String date = DateUtils.dateToString(new Date(),
                DateUtils.PATTERN_COMPACT_DATE);
        File target = new File(new File(backupStore, date), message.getUuid()
            + this.suffix);
        moveFile(source, target);
    }

    @Override
    public void removeMessageExternal(Hl7Message message, String flag)
    {
        // TODO Auto-generated method stub
        
    }
}
