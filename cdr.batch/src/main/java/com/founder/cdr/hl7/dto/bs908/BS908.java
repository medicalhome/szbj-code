package com.founder.cdr.hl7.dto.bs908;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.founder.cdr.hl7.dto.AccessControl;
import com.founder.cdr.hl7.dto.BaseDto;

public class BS908 extends BaseDto
{
    /**
     * 消息类别代码
     */
    @NotEmpty(message = "{BS908.msgId}")
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
     * 权限设定信息
     */
    @NotEmpty(message = "{BS908.AccessControl}")
    private List<AccessControl> AccessControl;

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

    public List<AccessControl> getAccessControl()
    {
        return AccessControl;
    }
}
