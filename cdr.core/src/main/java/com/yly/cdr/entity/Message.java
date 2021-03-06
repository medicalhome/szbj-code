package com.yly.cdr.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.founder.fasf.core.util.daohelper.annotation.Generator;
import com.founder.fasf.core.util.daohelper.annotation.Parameter;

@Entity
@Table(name = "MESSAGE")
public class Message implements Serializable
{
    private static final long serialVersionUID = 4881498898902557678L;

    private BigDecimal id;

    private String vid;

    private String content;

    private Date datetime;
    
    private Date createTime;
    
    private Date updateTime;
    
    private String serviceId;
    
    private String domainId;
    
    private String execUnitId;
    
    private String orderDispatchTypeCode;
    
    private String orgCode;
    
    private String  sourceSysCd;
    
    private String  msgId;

    // 消息状态。（0:未运行；1:正在运行；2:保留。）
    private String state;

    private String batchId;
    
    private String externalState;
    
    private String msgMode;

    @Id
    @GeneratedValue(generator = "native-generator")
    @Generator(name = "native-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "SEQ_MESSAGE") })
    public BigDecimal getId()
    {
        return this.id;
    }

    public void setId(BigDecimal id)
    {
        this.id = id;
    }

    @Column(name = "VID", nullable = false, length = 16)
    public String getVid()
    {
        return this.vid;
    }

    public void setVid(String vid)
    {
        this.vid = vid;
    }

    @Column(name = "CONTENT")
    public String getContent()
    {
        return this.content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DATETIME", nullable = false, length = 7)
    public Date getDatetime()
    {
        return this.datetime;
    }

    public void setDatetime(Date datetime)
    {
        this.datetime = datetime;
    }

    @Column(name = "STATE", nullable = false, length = 1)
    public String getState()
    {
        return this.state;
    }

    public void setState(String state)
    {
        this.state = state;
    }
    
    @Column(name = "EXTERNAL_STATE", nullable = false, length = 1)
    public String getExternalState()
    {
        return this.externalState;
    }

    public void setExternalState(String externalState)
    {
        this.externalState = externalState;
    }

    @Column(name = "BATCH_ID", length = 64)
    public String getBatchId()
    {
        return batchId;
    }

    public void setBatchId(String batchId)
    {
        this.batchId = batchId;
    }
    
    @Temporal(TemporalType.DATE)
    @Column(name = "CREATE_TIME", nullable = false, length = 7)
    public Date getCreateTime()
    {
        return this.createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "UPDATE_TIME", nullable = false, length = 7)
    public Date getUpdateTime()
    {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }

    @Column(name = "service_id", length = 40)
    public String getServiceId()
    {
        return serviceId;
    }

    public void setServiceId(String serviceId)
    {
        this.serviceId = serviceId;
    }

    @Column(name = "domain_id", length = 40)
    public String getDomainId()
    {
        return domainId;
    }

    public void setDomainId(String domainId)
    {
        this.domainId = domainId;
    }

    @Column(name = "exec_unit_id", length = 120)
    public String getExecUnitId()
    {
        return execUnitId;
    }

    public void setExecUnitId(String execUnitId)
    {
        this.execUnitId = execUnitId;
    }

    @Column(name = "order_dispatch_type_code", length = 40)
    public String getOrderDispatchTypeCode()
    {
        return orderDispatchTypeCode;
    }

    public void setOrderDispatchTypeCode(String orderDispatchTypeCode)
    {
        this.orderDispatchTypeCode = orderDispatchTypeCode;
    }

    @Column(name = "org_code", length = 50)
    public String getOrgCode()
    {
        return orgCode;
    }

    public void setOrgCode(String orgCode)
    {
        this.orgCode = orgCode;
    }

    @Column(name = "source_sys_cd", length = 50)
    public String getSourceSysCd()
    {
        return sourceSysCd;
    }

    public void setSourceSysCd(String sourceSysCd)
    {
        this.sourceSysCd = sourceSysCd;
    }
    
    @Column(name = "msg_id", length = 50)
    public String getMsgId()
    {
        return msgId;
    }

    public void setMsgId(String msgId)
    {
        this.msgId = msgId;
    }
    
    @Column(name = "msg_mode", length = 10)
    public String getMsgMode()
    {
        return msgMode;
    }

    public void setMsgMode(String msgMode)
    {
        this.msgMode = msgMode;
    }
    
    

}
