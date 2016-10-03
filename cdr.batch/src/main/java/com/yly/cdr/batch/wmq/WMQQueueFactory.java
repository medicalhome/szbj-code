package com.yly.cdr.batch.wmq;
//
//import java.util.Hashtable;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.InitializingBean;
//
//import com.ibm.mq.MQException;
//import com.ibm.mq.MQQueue;
//import com.ibm.mq.MQQueueManager;
//import com.ibm.mq.constants.MQConstants;
//
//public class WMQQueueFactory implements InitializingBean
//{
//    private final static Logger logger = LoggerFactory.getLogger(WMQQueueFactory.class);
//
//    private MQQueueManager queueManager;
//
//    @SuppressWarnings("rawtypes")
//    private Hashtable env = new Hashtable();
//
//    private String host;
//
//    private String channel;
//
//    private String ccsid;
//
//    private String port;
//
//    private String transport;
//
//    private String managerName;
//
//    private boolean reconnect = false;
//
//    public MQQueueManager getQueueManager() throws Exception
//    {
//        if (this.reconnect)
//            createQueueManager();
//
//        return queueManager;
//    }
//
//    public String getHost()
//    {
//        return host;
//    }
//
//    public void setHost(String host)
//    {
//        this.host = host;
//    }
//
//    public String getChannel()
//    {
//        return channel;
//    }
//
//    public void setChannel(String channel)
//    {
//        this.channel = channel;
//    }
//
//    public String getCcsid()
//    {
//        return ccsid;
//    }
//
//    public void setCcsid(String ccsid)
//    {
//        this.ccsid = ccsid;
//    }
//
//    public String getPort()
//    {
//        return port;
//    }
//
//    public void setPort(String port)
//    {
//        this.port = port;
//    }
//
//    public String getTransport()
//    {
//        return transport;
//    }
//
//    public void setTransport(String transport)
//    {
//        this.transport = transport;
//    }
//
//    public String getManagerName()
//    {
//        return managerName;
//    }
//
//    public void setManagerName(String managerName)
//    {
//        this.managerName = managerName;
//    }
//
//    public void setReconnect()
//    {
//        this.reconnect = true;
//    }
//
//    @SuppressWarnings("unchecked")
//    @Override
//    public void afterPropertiesSet() throws Exception
//    {
//        env.put(MQConstants.HOST_NAME_PROPERTY, host);
//        env.put(MQConstants.CHANNEL_PROPERTY, channel);
//        // 服务器MQ服务使用的编码(Coded Character Set Identifier:CCSID)
//        // 1381代表GBK、1208代表UTF
//        env.put(MQConstants.CCSID_PROPERTY, Integer.valueOf(ccsid).intValue());
//        env.put(MQConstants.PORT_PROPERTY, Integer.valueOf(port).intValue());
//        if ("1".equalsIgnoreCase(transport))
//            env.put(MQConstants.TRANSPORT_PROPERTY,
//                    MQConstants.TRANSPORT_MQSERIES);
//
//        createQueueManager();
//    }
//
//    private void createQueueManager() throws Exception
//    {
//        logger.info(
//                "创建新的队列管理器对象：\r\n服务器:{}\r\n通道:{}\r\n编码:{}\r\n端口:{}\r\n绑定类型:{}\r\n服务管理器:{}",
//                new String[] { host, channel, ccsid, port, transport,
//                        managerName });
//        queueManager = new MQQueueManager(managerName, env);
//        this.reconnect = false;
//    }
//
//    public MQQueue getMQQueue(String queueName, int openOptions)
//            throws MQException
//    {
//        return this.queueManager.accessQueue(queueName, openOptions);
//    }
//
//    public int getCharacterSet() throws MQException
//    {
//        return this.queueManager.getCharacterSet();
//    }
//}
