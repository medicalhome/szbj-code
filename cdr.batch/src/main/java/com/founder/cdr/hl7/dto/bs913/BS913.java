package com.founder.cdr.hl7.dto.bs913;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.founder.cdr.hl7.dto.BaseDto;
import com.founder.cdr.hl7.dto.Notification;
import com.founder.cdr.util.DateUtils;

public class BS913 extends BaseDto
{
    /**
     * 消息类别代码
     */
    @NotEmpty(message = "{BS913.msgId}")
    private String msgId;
    /**
     * 版本号
     */
    private String version;
    /**
     * 消息名称 
     */
    @NotEmpty(message = "{BS913.msgName}")
    private String msgName;
    /**
     * 消息源系统编码
     */
    @NotEmpty(message = "{BS913.sourceSysCode}")
    private String sourceSysCode;
    /**
     * 消息目标系统编码
     */
    private String targetSysCode;
    /**
     * 消息创建时间
     */
    @NotEmpty(message = "{BS913.createTime}")
    private String createTime;
    /**
     * 通知对象设定信息
     */
    @NotEmpty(message = "{BS913.notifications}")
    private List<Notification> notifications;

    public String getMsgId()
    {
        return msgId;
    }

    public String getVersion()
    {
        return version;
    }

    public String getMsgName()
    {
        return msgName;
    }

    public String getSourceSysCode()
    {
        return sourceSysCode;
    }

    public String getTargetSysCode()
    {
        return targetSysCode;
    }

    public String getCreateTime()
    {
        return createTime;
    }

    public List<Notification> getNotifications()
    {
        return notifications;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("MessageId=");
        builder.append(getMessage().getId()+",");
        builder.append("Datetime=");
        builder.append(DateUtils.dateToString(getMessage().getDatetime(),
                null) + ",");
        builder.append("BS913 [notifications=");
        builder.append(notifications);
        builder.append("]");
        return builder.toString();
    }
    
    
}
