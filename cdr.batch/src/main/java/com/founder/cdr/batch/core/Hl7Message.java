package com.founder.cdr.batch.core;

import java.math.BigDecimal;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.TextMessage;

public class Hl7Message implements TextMessage
{
    /**
     * 消息ID
     */
    public final static String HEADER_SERVICE_ID = "service_id";

    /**
     * 域ID
     */
    public final static String HEADER_DOMAIN_ID = "domain_id";

    /**
     * 医嘱小分类
     */
    public final static String HEADER_ORDER_DISPATCH_TYPE = "order_dispatch_type_code";

    /**
     * 执行科室
     */
    public final static String HEADER_EXEC_UNIT = "exec_unit_id";
    
    /**
     * 申请科室编码
     */
    public final static String APPLY_UNIT_ID = "apply_unit_id";
    
    /**
     * 扩展码
     */
    public final static String EXTEND_SUB_ID = "extend_sub_id";

    /**
     * 医疗机构
     */
    public final static String HEADER_HOSPITAL_CD = "hospital_id";

    /**
     * 消息源系统编码
     */
    public final static String HEADER_SOURCE_SYS_CD = "send_sys_id";
    
    /**
     * 医嘱执行分类编码
     */
    public final static String ORDER_EXEC_ID = "order_exec_id";

    /**
     * 消息类型 
     */
    public final static String HEADER_MSG_TYPE = "message_type";
    
    /**
     * msg_id
     */
    public final static String MSG_ID = "msg_id";

    /**
     * 最后处理时间
     */
    public static final String HEADER_LAST_HANDLE_TIME = "last_handle_time";

    // $Author :wu_biao
    // $Date : 2013/04/01 14:44$
    // [BUG]14801 ADD BEGIN
    // 处理累计时间
    public static final String HEADER_TOTAL_HANDLE_TIME = "total_handle_time";

    // [BUG]14801 ADD END

    /**
     * 处理次数
     */
    public static final String HEADER_HANDLE_TIMES = "handle_times";

    public static final String HEADER_HANDLE_SEND_TIMES = "handle_send_times";

    public static final String HEADER_HANDLE_EXTERNAL_SEND_TIMES = "handle_external_send_times";

    // modify by jin_peng
    public static final String HEADER_EXTERNAL_LAST_HANDLE_TIME = "last_external_handle_time";
    
    public static final String HEADER_EXTERNAL_TOTAL_HANDLE_TIME = "total_external_handle_time";
    
    public static final String HEADER_LAST_HANDLE_SEND_TIME = "last_handle_send_time";
    
    public static final String HEADER_TOTAL_HANDLE_SEND_TIME = "total_handle_send_time";

    /**
     * 消息的唯一标识
     */
    public static final String PROPERTY_ID = "db_id";

    /**
     * 消息的创建时间
     */
    public static final String CREATE_TIME = "createTime";

    /**
     * 系统停止命令
     */
    private final static String COMMAND_SHUTDOWN = "SHUTDOWN";

    /**
     * send_system_id
     */
    public final static String TARGET_SYSTEM_ID = "target_system_id";

    /**
     * send_sys_code
     */
    public final static String TARGET_SYSTEM_CODE = "targetSysCode";

    private String uuid;

    private String reWsSflag;

    private String reExternalSendFlag;

    private String text;

    private Map<String, Object> headers;

    private String type;

    private long timestamp;

    private Destination replyTo;

    private boolean redelivered;

    private int priority;

    private String id;

    private long expiration;

    private Destination destination;

    private int deliveryMode;

    private String correlationID;

    private byte[] correlationIDBytes;

    private String queueName;

    private String paramsExternal;
    
    private String isRestartRetry;
    
    private String msgMode;

    public Hl7Message()
    {
        headers = new HashMap<String, Object>();
    }

    public String getUuid()
    {
        return uuid;
    }

    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }

    public String getReWsSflag()
    {
        return reWsSflag;
    }

    public void setReWsSflag(String reWsSflag)
    {
        this.reWsSflag = reWsSflag;
    }

    public String getReExternalSendFlag()
    {
        return reExternalSendFlag;
    }

    public void setReExternalSendFlag(String reExternalSendFlag)
    {
        this.reExternalSendFlag = reExternalSendFlag;
    }

    public String getServiceId()
    {
        return (String) headers.get(HEADER_SERVICE_ID);
    }

    public void setServiceId(String serviceId)
    {
        headers.put(HEADER_SERVICE_ID, serviceId);
    }

    public String getDomainId()
    {
        return (String) headers.get(HEADER_DOMAIN_ID);
    }

    public void setDomainId(String domainId)
    {
        headers.put(HEADER_DOMAIN_ID, domainId);
    }

    public String getSourceSysCd()
    {
        return (String) headers.get(HEADER_SOURCE_SYS_CD);
    }

    public void setSourceSysCd(String domainId)
    {
        headers.put(HEADER_SOURCE_SYS_CD, domainId);
    }

    public String getHospitalCd()
    {
        return (String) headers.get(HEADER_HOSPITAL_CD);
    }

    public void setHospitalCd(String domainId)
    {
        headers.put(HEADER_HOSPITAL_CD, domainId);
    }

    public String getExecUnitId()
    {
        return (String) headers.get(HEADER_EXEC_UNIT);
    }

    public void setExecUnitId(String domainId)
    {
        headers.put(HEADER_EXEC_UNIT, domainId);
    }

    public String getOrderDispatchType()
    {
        return (String) headers.get(HEADER_ORDER_DISPATCH_TYPE);
    }

    public void setOrderDispatchType(String domainId)
    {
        headers.put(HEADER_ORDER_DISPATCH_TYPE, domainId);
    }
    
    public String getOrderExecId()
    {
        return (String) headers.get(ORDER_EXEC_ID);
    }

    public void setOrderExecId(String orderExecId)
    {
        headers.put(ORDER_EXEC_ID, orderExecId);
    }
    
    public String getExtendSubId()
    {
        return (String) headers.get(EXTEND_SUB_ID);
    }

    public void setExtendsSubId(String extendsSubId)
    {
        headers.put(EXTEND_SUB_ID, extendsSubId);
    }

    public String getApplyUnitId()
    {
        return (String) headers.get(APPLY_UNIT_ID);
    }

    public void setApplyUnitId(String applyUnitId)
    {
        headers.put(APPLY_UNIT_ID, applyUnitId);
    }
    public String getQueueName()
    {
        return this.queueName;
    }

    public void setQueueName(String queueName)
    {
        this.queueName = queueName;
    }

    public String getMessageType()
    {
        return (String) headers.get(HEADER_MSG_TYPE);
    }

    public void setMessageType(String messageType)
    {
        headers.put(HEADER_MSG_TYPE, messageType);
    }

    public BigDecimal getDatabaseId()
    {
        return (BigDecimal) headers.get(PROPERTY_ID);
    }

    public void setDatabaseId(BigDecimal dbId)
    {
        headers.put(PROPERTY_ID, dbId);
    }

    public String getCreateTime()
    {
        return (String) headers.get(CREATE_TIME);
    }

    public void setCreateTime(String dbId)
    {
        headers.put(CREATE_TIME, dbId);
    }

    public boolean isShutdownCommand()
    {
        return COMMAND_SHUTDOWN.equals(headers.get(HEADER_SERVICE_ID));
    }

    public void setShutdownCommand()
    {
        headers.put(HEADER_SERVICE_ID, COMMAND_SHUTDOWN);
    }

    public String getTargetSystemId()
    {
        return (String) headers.get(TARGET_SYSTEM_ID);
    }

    public void setTargetSystemId(String targetSystemId)
    {
        headers.put(TARGET_SYSTEM_ID, targetSystemId);
    }

    public String getTargetSystemCode()
    {
        return (String) headers.get(TARGET_SYSTEM_CODE);
    }

    public void setTargetSystemCode(String targetSystemCode)
    {
        headers.put(TARGET_SYSTEM_CODE, targetSystemCode);
    }
    
    public String getMsgId()
    {
        return (String) headers.get(MSG_ID);
    }

    public void setMsgId(String msgId)
    {
        headers.put(MSG_ID, msgId);
    }

    @Override
    public void acknowledge() throws JMSException
    {
    }

    @Override
    public void clearBody() throws JMSException
    {
        this.text = null;
    }

    @Override
    public void clearProperties() throws JMSException
    {
        this.headers.clear();
    }

    @Override
    public boolean getBooleanProperty(String name) throws JMSException
    {
        Boolean bl = (Boolean) this.headers.get(name);
        if (bl != null)
            return bl.booleanValue();
        else
            return false;
    }

    @Override
    public byte getByteProperty(String name) throws JMSException
    {
        Byte bt = (Byte) this.headers.get(name);
        if (bt != null)
            return bt.byteValue();
        else
            return 0;
    }

    @Override
    public double getDoubleProperty(String name) throws JMSException
    {
        Double d = (Double) this.headers.get(name);
        if (d != null)
            return d.doubleValue();

        return 0;
    }

    @Override
    public float getFloatProperty(String name) throws JMSException
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getIntProperty(String name) throws JMSException
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String getJMSCorrelationID() throws JMSException
    {
        return this.correlationID;
    }

    @Override
    public byte[] getJMSCorrelationIDAsBytes() throws JMSException
    {
        return this.correlationIDBytes;
    }

    @Override
    public int getJMSDeliveryMode() throws JMSException
    {
        return this.deliveryMode;
    }

    @Override
    public Destination getJMSDestination() throws JMSException
    {
        return this.destination;
    }

    @Override
    public long getJMSExpiration() throws JMSException
    {
        return this.expiration;
    }

    @Override
    public String getJMSMessageID() throws JMSException
    {
        return this.id;
    }

    @Override
    public int getJMSPriority() throws JMSException
    {
        return this.priority;
    }

    @Override
    public boolean getJMSRedelivered() throws JMSException
    {
        return this.redelivered;
    }

    @Override
    public Destination getJMSReplyTo() throws JMSException
    {
        return this.replyTo;
    }

    @Override
    public long getJMSTimestamp() throws JMSException
    {
        return this.timestamp;
    }

    @Override
    public String getJMSType() throws JMSException
    {
        return this.type;
    }

    @Override
    public long getLongProperty(String name) throws JMSException
    {
        Long value = (Long) this.headers.get(name);
        if (value != null)
            return value.longValue();
        else
            return -1;
    }

    @Override
    public Object getObjectProperty(String name) throws JMSException
    {
        return this.headers.get(name);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public Enumeration getPropertyNames() throws JMSException
    {
        return new IteratorEnumeration(this.headers.entrySet().iterator());
    }

    @Override
    public short getShortProperty(String name) throws JMSException
    {
        Short value = (Short) this.headers.get(name);
        if (value != null)
            return value.shortValue();
        else
            return 0;
    }

    @Override
    public String getStringProperty(String name) throws JMSException
    {
        return (String) this.headers.get(name);
    }

    @Override
    public boolean propertyExists(String name) throws JMSException
    {
        return this.headers.containsKey(name);
    }

    @Override
    public void setBooleanProperty(String name, boolean value)
            throws JMSException
    {
        this.headers.put(name, Boolean.valueOf(value));
    }

    @Override
    public void setByteProperty(String name, byte value) throws JMSException
    {
        this.headers.put(name, Byte.valueOf(value));
    }

    @Override
    public void setDoubleProperty(String name, double value)
            throws JMSException
    {
        this.headers.put(name, Double.valueOf(value));
    }

    @Override
    public void setFloatProperty(String name, float value) throws JMSException
    {
        this.headers.put(name, Float.valueOf(value));
    }

    @Override
    public void setIntProperty(String name, int value) throws JMSException
    {
        this.headers.put(name, Integer.valueOf(value));
    }

    @Override
    public void setJMSCorrelationID(String correlationID) throws JMSException
    {
        this.correlationID = correlationID;
    }

    @Override
    public void setJMSCorrelationIDAsBytes(byte[] correlationID)
            throws JMSException
    {
        this.correlationIDBytes = correlationID;
    }

    @Override
    public void setJMSDeliveryMode(int deliveryMode) throws JMSException
    {
        this.deliveryMode = deliveryMode;
    }

    @Override
    public void setJMSDestination(Destination destination) throws JMSException
    {
        this.destination = destination;
    }

    @Override
    public void setJMSExpiration(long expiration) throws JMSException
    {
        this.expiration = expiration;
    }

    @Override
    public void setJMSMessageID(String id) throws JMSException
    {
        this.id = id;
    }

    @Override
    public void setJMSPriority(int priority) throws JMSException
    {
        this.priority = priority;
    }

    @Override
    public void setJMSRedelivered(boolean redelivered) throws JMSException
    {
        this.redelivered = redelivered;
    }

    @Override
    public void setJMSReplyTo(Destination replyTo) throws JMSException
    {
        this.replyTo = replyTo;
    }

    @Override
    public void setJMSTimestamp(long timestamp) throws JMSException
    {
        this.timestamp = timestamp;
    }

    @Override
    public void setJMSType(String type) throws JMSException
    {
        this.type = type;
    }

    @Override
    public void setLongProperty(String name, long value) throws JMSException
    {
        this.headers.put(name, Long.valueOf(value));
    }

    @Override
    public void setObjectProperty(String name, Object value)
            throws JMSException
    {
        this.headers.put(name, value);
    }

    @Override
    public void setShortProperty(String name, short value) throws JMSException
    {
        this.headers.put(name, Short.valueOf(value));
    }

    @Override
    public void setStringProperty(String name, String value)
            throws JMSException
    {
        this.headers.put(name, value);
    }

    @Override
    public String getText() throws JMSException
    {
        return this.text;
    }

    public String getContent()
    {
        return this.text;
    }

    @Override
    public void setText(String string) throws JMSException
    {
        this.text = string;
    }

    public void setContent(String string)
    {
        this.text = string;
    }

    class IteratorEnumeration<T> implements Enumeration<T>
    {
        private Iterator<T> iterator;

        public IteratorEnumeration(Iterator<T> it)
        {
            this.iterator = it;
        }

        @Override
        public boolean hasMoreElements()
        {
            return this.iterator.hasNext();
        }

        @Override
        public T nextElement()
        {
            return iterator.next();
        }
    }

    // $Author :wu_biao
    // $Date : 2013/03/04 14:44$
    // [BUG]14248 ADD BEGIN
    public static String extractDomainId(Map<String, String> header)
    {
        String domainId = null;
        if (header == null)
            domainId = null;
        else
            domainId = header.get(HEADER_DOMAIN_ID);

        if (domainId == null || "".equalsIgnoreCase(domainId))
            domainId = null;

        return domainId;
    }

    public static String extractOrderDispatchType(Map<String, String> header)
    {
        String orderDispatchType = null;
        if (header == null)
            orderDispatchType = null;
        else
            orderDispatchType = header.get(HEADER_ORDER_DISPATCH_TYPE);

        if (orderDispatchType == null || "".equalsIgnoreCase(orderDispatchType))
            orderDispatchType = null;

        return orderDispatchType;
    }

    public static String extractServiceId(Map<String, String> header)
    {
        String serviceId = null;
        if (header == null)
            serviceId = null;
        else
            serviceId = header.get(HEADER_SERVICE_ID);

        if (serviceId == null || "".equalsIgnoreCase(serviceId))
            serviceId = null;

        return serviceId;
    }

    public static String extractExecUnitId(Map<String, String> header)
    {
        String execUnitId = null;
        if (header == null)
            execUnitId = null;
        else
            execUnitId = header.get(HEADER_EXEC_UNIT);

        if (execUnitId == null || "".equalsIgnoreCase(execUnitId))
            execUnitId = null;

        return execUnitId;
    }

    public static String extractHospitalCd(Map<String, String> header)
    {
        String hospitalCd = null;
        if (header == null)
            hospitalCd = null;
        else
            hospitalCd = header.get(HEADER_HOSPITAL_CD);

        if (hospitalCd == null || "".equalsIgnoreCase(hospitalCd))
            hospitalCd = null;

        return hospitalCd;
    }

    public static String extractSourceSysCd(Map<String, String> header)
    {
        String hospitalCd = null;
        if (header == null)
            hospitalCd = null;
        else
            hospitalCd = header.get(HEADER_SOURCE_SYS_CD);

        if (hospitalCd == null || "".equalsIgnoreCase(hospitalCd))
            hospitalCd = null;

        return hospitalCd;
    }
    
    public static String extractOrderExecId(Map<String, String> header)
    {
        String orderExecId = null;
        if (header == null)
        	orderExecId = null;
        else
        	orderExecId = header.get(ORDER_EXEC_ID);

        if (orderExecId == null || "".equalsIgnoreCase(orderExecId))
        	orderExecId = null;

        return orderExecId;
    }
    
    public static String extractExtendsSubId(Map<String, String> header)
    {
        String extendsSubId = null;
        if (header == null)
        	extendsSubId = null;
        else
        	extendsSubId = header.get(EXTEND_SUB_ID);

        if (extendsSubId == null || "".equalsIgnoreCase(extendsSubId))
        	extendsSubId = null;

        return extendsSubId;
    }
    
    public static String extractApplyUnitId(Map<String, String> header)
    {
        String applyUnitId = null;
        if (header == null)
        	applyUnitId = null;
        else
        	applyUnitId = header.get(APPLY_UNIT_ID);

        if (applyUnitId == null || "".equalsIgnoreCase(applyUnitId))
        	applyUnitId = null;

        return applyUnitId;
    }

    public static boolean acceptHeader(String name)
    {
        if (Hl7Message.HEADER_SERVICE_ID.equalsIgnoreCase(name)
            || Hl7Message.HEADER_DOMAIN_ID.equalsIgnoreCase(name)
            || Hl7Message.HEADER_ORDER_DISPATCH_TYPE.equalsIgnoreCase(name)
            || Hl7Message.HEADER_EXEC_UNIT.equalsIgnoreCase(name)
            || Hl7Message.HEADER_HOSPITAL_CD.equalsIgnoreCase(name)
            || Hl7Message.HEADER_SOURCE_SYS_CD.equalsIgnoreCase(name)
            || Hl7Message.ORDER_EXEC_ID.equalsIgnoreCase(name)
            || Hl7Message.EXTEND_SUB_ID.equalsIgnoreCase(name)
            || Hl7Message.APPLY_UNIT_ID.equalsIgnoreCase(name)
            )
            return true;

        return false;
    }

    // [BUG]14248 ADD END

    public String getParamsExternal()
    {
        return paramsExternal;
    }

    public void setParamsExternal(String paramsExternal)
    {
        this.paramsExternal = paramsExternal;
    }

    public String getIsRestartRetry()
    {
        return isRestartRetry;
    }

    public void setIsRestartRetry(String isRestartRetry)
    {
        this.isRestartRetry = isRestartRetry;
    }

	public String getMsgMode() {
		return msgMode;
	}

	public void setMsgMode(String msgMode) {
		this.msgMode = msgMode;
	}

}
