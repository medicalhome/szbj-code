package com.yly.cdr.util;

import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Before;
import org.junit.Test;

import com.ibm.mq.MQException;
import com.yly.cdr.util.PropertiesUtils;
import com.yly.cdr.util.mq.MQClient;
import com.yly.cdr.util.mq.MQSession;

public class MQClientTest
{

    /**
     * 消息ID
     */
    private String messageId = "PRPAIN201301UV02";
    
    /**
     * 队列名称
     */
    private String qName;
    
    private MQClient mqClient;
    
    @Before
    public void setUp()
    {
        MQSession mqSession = new MQSession();
        mqSession.setHostname(PropertiesUtils.getValue("mq.hostname"));
        mqSession.setChannel(PropertiesUtils.getValue("mq.channel"));
        mqSession.setCcsid(Integer.parseInt(PropertiesUtils.getValue("mq.ccsid")));
        mqSession.setPort(Integer.parseInt(PropertiesUtils.getValue("mq.port")));
        
        try
        {
            mqClient = new MQClient(mqSession, PropertiesUtils.getValue("mq.qmName"));
        }
        catch (MQException e)
        {
            e.printStackTrace();
        }
        
        qName = PropertiesUtils.getValue("mq.qName." + messageId);
    }
    
    @Test
    public void testGetQueueLength()
    {
        try
        {
            mqClient.getQueueLength(qName);
        }
        catch (MQException e)
        {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testSend()
    {
        try
        {
            mqClient.send(qName, testData());
            mqClient.commitQueueManager(qName);
        }
        catch (MQException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

//    @Test
    public void testReceive() throws Exception
    {
        String message = null;
        message = mqClient.receive(qName);
        mqClient.commitQueueManager(qName);
        assertNotNull(message);
    }

    /**
     * 测试数据
     * 
     * @return 消息
     */
    private String testData()
    {
        String file = "com/yly/cdr/batch/" + messageId.toLowerCase() + "/" + messageId.substring(0, 4) + "_" + messageId.substring(4) + ".xml";
        BufferedReader in = new BufferedReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream(file)));

        StringBuilder sb = new StringBuilder();
        try
        {
            String s = "";
            while ((s = in.readLine()) != null)
            {
                sb.append(s);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                in.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
