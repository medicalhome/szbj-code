package com.yly.cdr.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import javax.persistence.Id;

import com.founder.fasf.core.util.daohelper.annotation.Generator;
import com.founder.fasf.core.util.daohelper.annotation.Parameter;

import java.io.Serializable;

@Entity
@Table(name = "SYSTEM_AUDIT_LOG")
public class SystemAuditLog implements Serializable
{

    private BigDecimal auditLogSn;
    
    private BigDecimal accountLogSn;

    private String patientLid;

    private String patientDomain;

    private Date operationTime;

    private String dataTypeName;

    private String businessDataNo;

    private String itemName;
    
    private String messageContent;
    
    private String successSendFlag;

    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;
    
    private String orgCode;
    
    private String orgName;

    @Id
    @GeneratedValue(generator = "native-generator")
    @Generator(name = "native-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "SEQ_SYSTEM_AUDIT_LOG") })
    public BigDecimal getAuditLogSn()
    {
        return this.auditLogSn;
    }

    public void setAuditLogSn(BigDecimal auditLogSn)
    {
        this.auditLogSn = auditLogSn;
    }
    
    @Column(name = "ACCOUNT_LOG_SN", nullable = false, precision = 22, scale = 0)
    public BigDecimal getAccountLogSn()
    {
        return this.accountLogSn;
    }

    public void setAccountLogSn(BigDecimal accountLogSn)
    {
        this.accountLogSn = accountLogSn;
    }

    @Column(name = "PATIENT_LID", nullable = false, length = 12)
    public String getPatientLid()
    {
        return this.patientLid;
    }

    public void setPatientLid(String patientLid)
    {
        this.patientLid = patientLid;
    }

    @Column(name = "PATIENT_DOMAIN", nullable = false, length = 6)
    public String getPatientDomain()
    {
        return this.patientDomain;
    }

    public void setPatientDomain(String patientDomain)
    {
        this.patientDomain = patientDomain;
    }

    @Column(name = "DATA_TYPE_NAME", length = 150)
    public String getDataTypeName()
    {
        return this.dataTypeName;
    }

    public void setDataTypeName(String dataTypeName)
    {
        this.dataTypeName = dataTypeName;
    }

    @Column(name = "BUSINESS_DATA_NO", length = 80)
    public String getBusinessDataNo()
    {
        return this.businessDataNo;
    }

    public void setBusinessDataNo(String businessDataNo)
    {
        this.businessDataNo = businessDataNo;
    }
    
    @Column(name = "MESSAGE_CONTENT")
    public String getMessageContent()
    {
        return this.messageContent;
    }

    public void setMessageContent(String messageContent)
    {
        this.messageContent = messageContent;
    }
    
    @Column(name = "SUCCESS_SEND_FLAG", length = 1)
    public String getSuccessSendFlag()
    {
        return this.successSendFlag;
    }

    public void setSuccessSendFlag(String successSendFlag)
    {
        this.successSendFlag = successSendFlag;
    }
    
    @Column(name = "ITEM_NAME", length = 1200)
    public String getItemName()
    {
        return this.itemName;
    }

    public void setItemName(String itemName)
    {
        this.itemName = itemName;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "OPERATION_TIME", length = 7)
    public Date getOperationTime()
    {
        return this.operationTime;
    }

    public void setOperationTime(Date operationTime)
    {
        this.operationTime = operationTime;
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

    @Column(name = "UPDATE_COUNT", nullable = false, precision = 22, scale = 0)
    public BigDecimal getUpdateCount()
    {
        return this.updateCount;
    }

    public void setUpdateCount(BigDecimal updateCount)
    {
        this.updateCount = updateCount;
    }

    @Column(name = "DELETE_FLAG", nullable = false, length = 1)
    public String getDeleteFlag()
    {
        return this.deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag)
    {
        this.deleteFlag = deleteFlag;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DELETE_TIME", length = 7)
    public Date getDeleteTime()
    {
        return this.deleteTime;
    }

    public void setDeleteTime(Date deleteTime)
    {
        this.deleteTime = deleteTime;
    }

    @Column(name = "ORG_CODE", length = 50)
    public String getOrgCode()
    {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode)
    {
        this.orgCode = orgCode;
    }
    
    @Column(name = "ORG_NAME", length = 50)
    public String getOrgName()
    {
        return this.orgName;
    }

    public void setOrgName(String orgName)
    {
        this.orgName = orgName;
    }
    
}
