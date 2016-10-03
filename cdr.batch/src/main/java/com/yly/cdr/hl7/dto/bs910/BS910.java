package com.yly.cdr.hl7.dto.bs910;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.yly.cdr.hl7.dto.BaseDto;
import com.yly.cdr.hl7.dto.Role;

public class BS910 extends BaseDto
{
    /**
     * 消息类别代码
     */
    @NotEmpty(message = "{BS910.msgId}")
    private String msgId;

    /**
     * 消息名称 
     */
    private String msgName;

    /**
     * 消息源系统编码
     */
    private String sourceSysCode;

    /**
     * 消息目标系统编码
     */
    private String targetSysCode;

    /**
     * 消息创建时间
     */
    private String createTime;

    /**
     * 角色信息
     */
    @NotEmpty(message = "{BS910.Role}")
    private List<Role> Role;

    public String getMsgId()
    {
        return msgId;
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

    public List<Role> getRole()
    {
        return Role;
    }
}
