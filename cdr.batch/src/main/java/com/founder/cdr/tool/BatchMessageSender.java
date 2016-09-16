package com.founder.cdr.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import com.founder.cdr.batch.core.Hl7Message;
import com.founder.cdr.wmq.Hl7MessageWmq;
import com.founder.cdr.wmq.WMQMessageSender;
import com.founder.cdr.wmq.WMQQueueFactory;

/**
 * 用来将指定目录下的文件内容作为消息批量发送到MQ的工具类。
 * 
 * @author xu_dengfeng
 *
 */
public class BatchMessageSender
{
    private final static String DEFAULT_QUEUE_KEY = "*";

    @Autowired
    private WMQMessageSender sender;

    private Properties settings;

    private Properties queueMapping;

    private String defaultQueue;

    public BatchMessageSender()
    {
        initConfig();

        initQueueSender();
    }

    private void initQueueSender()
    {
        WMQQueueFactory factory = new WMQQueueFactory();
        factory.setHost(settings.getProperty("mq.hostname"));
        factory.setManagerName(settings.getProperty("mq.qmName"));
        factory.setPort(settings.getProperty("mq.port"));
        factory.setChannel(settings.getProperty("mq.channel"));
        factory.setCcsid(settings.getProperty("mq.ccsid"));
        factory.setTransport(settings.getProperty("mq.transport"));
        try
        {
            factory.afterPropertiesSet();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new RuntimeException("连接队列管理器失败！");
        }

        sender = new WMQMessageSender();
        sender.setQueueFactory(factory);
    }

    private void initConfig()
    {
        settings = new Properties();
        queueMapping = new Properties();
        try
        {
            settings.load(new ClassPathResource("setting.properties").getInputStream());
            queueMapping.load(new ClassPathResource("queue_mapping.properties").getInputStream());
            defaultQueue = queueMapping.getProperty(DEFAULT_QUEUE_KEY);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            error("加载队列配置信息失败。");
        }
    }

    public void send(String path)
    {
        File file = new File(path);
        if (!(file.exists() && file.isDirectory()))
        {
            error(path + "不存在或者不是一个目录。");
            return;
        }

        try
        {
            sendDirectory(file);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            error("发送失败!");
        }
    }

    public void sendDirectory(File dir) throws Exception
    {
        File[] files = dir.listFiles();
        Arrays.sort(files, new Comparator<File>()
        {
            public int compare(File f1, File f2)
            {
                if (f1.isDirectory() && f2.isDirectory() || f1.isFile()
                    && f2.isFile())
                    return Integer.valueOf(f1.getName().compareTo(f2.getName()));
                else if (f1.isDirectory())
                    return -1;
                else
                    return 1;
            }
        });

        for (File file : files)
        {
            if (file.isDirectory())
                sendDirectory(file);
            else
                sendFile(file);
        }
    }

    private void sendFile(File file) throws Exception
    {
        info("发送文件" + file.getAbsolutePath());
        FileProperty prop = parseFileName(file.getName());
        String message = readFile(file);
        sendMessage(prop, message);
    }

    private void sendMessage(FileProperty prop, String message)
            throws Exception
    {
        String queueName = queueMapping.getProperty(prop.serviceId,
                defaultQueue);
        Hl7Message hl7message = new Hl7Message();
        hl7message.setContent(message);
        hl7message.setServiceId(prop.serviceId);
        hl7message.setDomainId(prop.domain_id);
        hl7message.setOrderDispatchType(prop.order_dispatch_type_code);
        hl7message.setExecUnitId(prop.exec_unit_id);
        hl7message.setSourceSysCd(prop.source_sys_cd);
        hl7message.setHospitalCd(prop.hospital_cd);
        hl7message.setDatabaseId(new BigDecimal(prop.sequence));
        
        Hl7MessageWmq hl7MessageWmq =new Hl7MessageWmq();
        hl7MessageWmq.setServiceId(hl7message.getServiceId());
        hl7MessageWmq.setDomainId(hl7message.getDomainId());
        hl7MessageWmq.setDatabaseId(hl7message.getDatabaseId());
        hl7MessageWmq.setExecUnitId(hl7message.getExecUnitId());
        hl7MessageWmq.setOrderDispatchType(hl7message.getOrderDispatchType());
        hl7MessageWmq.setSourceSysCd(hl7message.getSourceSysCd());
        hl7MessageWmq.setHospitalCd(hl7message.getHospitalCd());
        hl7MessageWmq.setQueueName(hl7message.getQueueName());
        hl7MessageWmq.setContent(hl7message.getContent());

        sender.sendMessage(hl7MessageWmq, queueName);
    }

    private FileProperty parseFileName(String name)
    {
        FileProperty prop = new FileProperty();
        prop.fileName = name;

        String[] tokens = name.split("_", 8);
        // 排序_服务ID_域ID_医嘱小分类_执行科室_文件名称_医疗机构编码_消息源系统编码
        if (tokens.length == 8)
        {
            prop.sequence = tokens[0];
            prop.serviceId = tokens[1];
            prop.domain_id = tokens[2];
            prop.order_dispatch_type_code = tokens[3];
            prop.exec_unit_id = tokens[4];
            prop.hospital_cd = tokens[6];
            prop.source_sys_cd = tokens[7];
            
        }
        // 排序_服务ID_文件名称_医疗机构编码_消息源系统编码
        else if (tokens.length == 5)
        {
            prop.sequence = tokens[0];
            prop.serviceId = tokens[1];
            prop.hospital_cd = tokens[3];
            prop.source_sys_cd = tokens[4];
            prop.domain_id = "1";
            prop.order_dispatch_type_code = "0";
            prop.exec_unit_id = "0";
        }
        // 服务ID_文件名称_医疗机构编码_消息源系统编码
        else if (tokens.length == 4)
        {
            prop.serviceId = tokens[0];
            prop.sequence = tokens[1];
            prop.hospital_cd = tokens[2];
            prop.source_sys_cd = tokens[3];
            prop.domain_id = "1";
            prop.order_dispatch_type_code = "0";
            prop.exec_unit_id = "0";
        }

        return prop;
    }

    private String readFile(File file) throws Exception
    {
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
        BufferedReader reader = new BufferedReader(isr);
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null)
        {
            sb.append(line);
        }
        reader.close();
        isr.close();
        fis.close();

        return sb.toString();
    }

    private void error(String text)
    {
        System.err.println(text);
    }

    private void info(String text)
    {
        System.out.println(text);
    }

    private static void output(String text)
    {
        System.out.println(text);
    }

    class FileProperty
    {
        /**发送顺序*/
        public String sequence;

        /**服务ID*/
        public String serviceId;

        /**域ID*/
        public String domain_id;

        /**医嘱小分类CD*/
        public String order_dispatch_type_code;

        /**执行科室*/
        public String exec_unit_id;
        
        /**消息源系统编码*/
        public String source_sys_cd;
        
        /**医疗机构*/
        public String hospital_cd;

        /**完整文件名*/
        public String fileName;
    }

    /**
     * @param args 要发送的消息所在目录
     */
    public static void main(String[] args)
    {
        if (args == null || args.length == 0)
        {
            output("参数不正确！请输入要发送的消息所在目录。");
            System.exit(-1);
        }
        else if ("usage".equalsIgnoreCase(args[0]))
        {
            output("Usage:");
            output("java " + BatchMessageSender.class.getName()
                + " <%data file path%>");
            output("说明: ");
            output("    文件名可以是下面三种格式中的一种");
            output("    1)排序_服务ID_域ID_医嘱小分类_执行科室_文件名称_医疗机构编码_消息源系统编码");
            output("    2)排序_服务ID_文件名称排序_服务ID_文件名称_医疗机构编码_消息源系统编码");
            output("    3)服务ID_文件名称_医疗机构编码_消息源系统编码");
            output("    文件名称中请不要包含下划线，否则报错。");
            System.exit(0);
        }

        BatchMessageSender sender = new BatchMessageSender();
        sender.send(args[0]);
    }
}
