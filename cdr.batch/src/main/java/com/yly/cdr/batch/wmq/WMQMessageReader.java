package com.yly.cdr.batch.wmq;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.Segment;
import ca.uhn.hl7v2.model.Type;
import ca.uhn.hl7v2.model.Varies;
import ca.uhn.hl7v2.model.v24.segment.MSH;
import ca.uhn.hl7v2.parser.Parser;

import com.ibm.mq.MQException;
import com.ibm.mq.MQGetMessageOptions;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQQueue;
import com.ibm.mq.constants.MQConstants;
import com.ibm.mq.headers.MQDataException;
import com.ibm.mq.headers.MQHeaderIterator;
import com.ibm.mq.headers.MQMD;
import com.ibm.mq.headers.MQRFH2;
import com.ibm.mq.headers.MQRFH2.Element;
import com.yly.cdr.batch.core.Hl7Message;
import com.yly.cdr.batch.core.ServiceMapper;
import com.yly.cdr.core.Constants;
import com.yly.cdr.util.ExceptionUtils;
import com.yly.cdr.wmq.WMQQueueFactory;

@Component
public class WMQMessageReader
{
    protected static Logger logger = LoggerFactory.getLogger(WMQMessageReader.class);

    private static final Parser p = (new DefaultHapiContext()).getGenericParser();
    
    private MQGetMessageOptions gmo;
    
    @Autowired
    private WMQQueueFactory queueFactory;

    @Autowired
    private ServiceMapper serviceMapper;

    private String queueName;

    public WMQMessageReader()
    {
        initGetOption();
    }

    /**
     * 从MQ接收一条消息，队列为空的情况下无限期等待。
     * 
     * @return
     * @throws Exception
     */
    public Hl7Message receive() throws Exception
    {
        int openOptions = MQConstants.MQOO_OUTPUT
            | MQConstants.MQOO_FAIL_IF_QUIESCING;
        openOptions = MQConstants.MQOO_INPUT_AS_Q_DEF
            | MQConstants.MQOO_INQUIRE;
        MQQueue queue = null;
        MQMessage message = new MQMessage();
        try
        {
            queue = queueFactory.getMQQueue(queueName, openOptions);
            message.characterSet = queueFactory.getCharacterSet();
            logger.debug("开始获取下一条消息。。。。。。");
            // 从队列中取出消息
            gmo.waitInterval = MQConstants.MQWI_UNLIMITED;
            queue.get(message, gmo);
            logger.debug("成功获取消息");            
        }
        catch (MQException e)
        {
            //wubiao BUG14649 test
            if (e.completionCode == MQConstants.MQCC_FAILED
                && e.reasonCode == MQConstants.MQRC_NO_MSG_AVAILABLE)
            {
                logger.debug("获取消息失败，原因：{}+{}",e.reasonCode,e.completionCode);
                return null;
            }
            //wubiao BUG14649 test
            else if (e.reasonCode == MQConstants.MQRC_CHANNEL_NOT_AVAILABLE
                || e.reasonCode == MQConstants.MQRC_CONNECTION_BROKEN
                || e.reasonCode == MQConstants.MQRC_CONNECTION_ERROR
                || e.reasonCode == MQConstants.MQRC_CONNECTION_STOPPED
                || e.reasonCode == MQConstants.MQRC_Q_MGR_QUIESCING
                || e.reasonCode == MQConstants.MQRC_GET_INHIBITED)
            {
                queueFactory.setReconnect();
                logger.error("队列管理器不可用，需要重新创建队列管理器! 原因：{}", e.reasonCode);
            }
            else
                throw e;
        }
        finally
        {
            queue.close();
        }


//        return constructMessage(message);
        return constructV2TypeMessage(message);
        
    }
    
    private Hl7Message constructV2TypeMessage(MQMessage message){
    	
    	try {
    		Hl7Message result = new Hl7Message();
            Map<String, String> header = extractHeader(message);

            String msgText = "";
    		
    		message.seek(0);
    		MQHeaderIterator hit = new MQHeaderIterator(message);
    		msgText = hit.getBodyAsText();
    		if(msgText.startsWith("MSH")){
    			msgText = msgText.replaceAll("\n", "\r");
    			Message hapiMsg = p.parse(msgText);
    			
    			MSH msh = (MSH) hapiMsg.get("MSH");
        		String modelType = msh.getMessageType().getMessageType()+"_"+msh.getMessageType().getMsg2_TriggerEvent();
        		if("CDA_ZDA".equals(modelType)){
        			Segment s = (Segment) hapiMsg.get("ZDA");
        			Type[] tArr = s.getField(8);
        			Varies v = (Varies) tArr[0];
        			String str = v.getData().toString();
        			byte[] afterDecode = Base64.decodeBase64(str);
        			String decodedXml = new String(afterDecode, Constants.BASE64_FORMAT);
        			msgText = decodedXml;
        			result.setMsgMode("V3");
        		} else {
        			result.setMsgMode("V2");
        			result.setMessageType(modelType);
        			result.setServiceId(modelType);
        		}
    		} else {
    			result.setMsgMode("V3");
    		}
    		
    		if(result.getMsgMode().equals("V3")){
    			String serviceId = Hl7Message.extractServiceId(header);
    	        String msgType = serviceId == null ? null
    	                : serviceMapper.getMessageId(serviceId);
    			if (msgType != null) {
    				if ("".equals(msgType.trim())) {
    					msgType = null;
    					logger.error("serviceID赋值错误!");
    				}
    			}
    			if (msgType == null) {
    				msgType = "unknow";
    			}
    			if (serviceId == null) {
    				logger.warn("消息头中没有服务ID");
    				result.setMessageType(msgType);
    			} else {
    				result.setServiceId(serviceId);
    				result.setMessageType(msgType);
    			}
    		}
    		
    		result.setText(msgText);
    		result.setQueueName(queueName);

            Calendar date = message.putDateTime;
            if (date == null)
                date = GregorianCalendar.getInstance();
            result.setJMSTimestamp(date.getTimeInMillis());

        	return result;
    	} catch (Exception e) {
			// TODO: handle exception
    		e.printStackTrace();
    		return null;
		}
    	
    }
    
    /**
     * 根据WMQ的消息构建SpringIntegration所使用的Message对象
     * 参考
     * http://publib.boulder.ibm.com/infocenter/wmqv7/v7r0/index.jsp?topic=%2Fcom.ibm.mq.csqzak.doc%2Ffr14000_.htm
     * http://publib.boulder.ibm.com/infocenter/wmqv7/v7r0/index.jsp?topic=%2Fcom.ibm.mq.csqzak.doc%2Ffr14000_.htm
     * @param message
     * @return
     * @throws Exception
     */
    private Hl7Message constructMessage(MQMessage message) throws Exception
    {
        Hl7Message result = new Hl7Message();

        Map<String, String> header = extractHeader(message);

        String msgText = "";
        String serviceId = Hl7Message.extractServiceId(header);

        String msgType = serviceId == null ? null
                : serviceMapper.getMessageId(serviceId);
        if (msgType != null)
        {
            if ("".equals(msgType.trim()))
            {
                msgType = null;
                logger.error("serviceID赋值错误!");
            }
        }
        if (msgType == null)
        {
            msgType = "unknow";
        }

        message.seek(0);
        MQHeaderIterator hit = new MQHeaderIterator(message);
        msgText = hit.getBodyAsText();
        if (serviceId == null)
        {
            logger.warn("消息头中没有服务ID");
            result.setMessageType(msgType);
        }
        else
        {
            result.setServiceId(serviceId);
            result.setMessageType(msgType);
        }
        result.setText(msgText);
        Calendar date = message.putDateTime;
        if (date == null)
            date = GregorianCalendar.getInstance();
        result.setJMSTimestamp(date.getTimeInMillis());
        // $Author :wu_biao
        // $Date : 2013/03/04 14:44$
        // [BUG]14248 ADD BEGIN
        String domainId = Hl7Message.extractDomainId(header);
        result.setDomainId(domainId);
        
        String orderDispatchType = Hl7Message.extractOrderDispatchType(header);
        result.setOrderDispatchType(orderDispatchType);
        
        String execUnitId = Hl7Message.extractExecUnitId(header);
        result.setExecUnitId(execUnitId);
        
        String hospitalCd = Hl7Message.extractHospitalCd(header);
        result.setHospitalCd(hospitalCd);
        
        String sourceSysCd = Hl7Message.extractSourceSysCd(header);
        result.setSourceSysCd(sourceSysCd);
        
        String orderExecId = Hl7Message.extractOrderExecId(header);
        result.setOrderExecId(orderExecId);
        
        String extendsSubId = Hl7Message.extractExtendsSubId(header);
        result.setExtendsSubId(extendsSubId);
        
        String applyUnitId = Hl7Message.extractApplyUnitId(header);
        result.setApplyUnitId(applyUnitId);
        
        byte[] b = message.messageId;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < b.length; ++i){
        	sb.append(toHexString(b[i]));
        }
        String msgId = sb.toString();
        result.setMsgId(msgId);
        // [BUG]14248 ADD END        

        return result;
    }

    private static String toHexString(byte b){
        String s = Integer.toHexString(b & 0xFF);
        if (s.length() == 1){
            return "0" + s;
        }else{
            return s;
        }
    }

    
    private Map<String, String> extractHeader(MQMessage message)
    {
        Map<String, String> header = new HashMap<String, String>();

        MQRFH2 rfh2 = null;
        try
        {
            rfh2 = new MQRFH2(message);
            Element e = rfh2.getFolder("usr", false);
            if (e != null)
            {
                Element[] elements = e.getChildren();
                for (Element ei : elements)
                {
                    String name = ei.getName();
                    if (Hl7Message.acceptHeader(name))
                    {
                        String value = ei.getStringValue();
                        header.put(name, value);
                    }
                    else
                        continue;
                }
            }
        }
        catch (MQDataException e1)
        {
            if (e1.completionCode == MQConstants.MQCC_FAILED
                && e1.reasonCode == MQConstants.MQRC_INSUFFICIENT_DATA) // MQConstants.MQRC_PROPERTY_NOT_AVAILABLE)
                logger.warn("No header data availiable.");
            else
                // e1.printStackTrace();
                logger.error("MQ异常：\r\n{}", ExceptionUtils.getStackTrace(e1));
        }
        catch (IOException e1)
        {
            // e1.printStackTrace();
            logger.error("接收消息时IO异常：\r\n{}", ExceptionUtils.getStackTrace(e1));
        }

        return header;
    }

    /**
     * 设置获取消息的选项
     */
    private void initGetOption()
    {
        // 设置取出消息的属性（默认属性）
        // Set the put message options.（设置放置消息选项）
        gmo = new MQGetMessageOptions();
        // Get messages under sync point control
        // 在同步点控制下获取消息
        gmo.options = gmo.options + MQConstants.MQGMO_SYNCPOINT;

        // Wait if no messages on the Queue
        // 如果在队列上没有消息则等待
        gmo.options = gmo.options + MQConstants.MQGMO_WAIT;
        gmo.options = gmo.options + MQConstants.MQGMO_PROPERTIES_COMPATIBILITY;

        // Fail if QueueManager Quiescing
        // 如果队列管理器停顿则失败
        gmo.options = gmo.options + MQConstants.MQGMO_FAIL_IF_QUIESCING;

        // Sets the time limit for the wait.
        // 设置等待的毫秒时间限制
        gmo.waitInterval = MQConstants.MQWI_UNLIMITED;
        // gmo.matchOptions = MQConstants.MQMO_MATCH_MSG_ID;
    }

    public String getQueueName()
    {
        return queueName;
    }

    public void setQueueName(String queueName)
    {
        this.queueName = queueName;
    }
}
