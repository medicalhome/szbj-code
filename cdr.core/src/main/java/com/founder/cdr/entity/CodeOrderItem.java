package com.founder.cdr.entity;

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
@Table(name = "CODE_ORDER_ITEM")
public class CodeOrderItem implements Serializable
{
    private BigDecimal codeOrderItemId;
    
    private BigDecimal csId;
    
    private String code;

    private String value;
    
    private String messageTypeCode;

    private String messageTypeName;
    
    private String pyCode;
    
    private String seqNo;
    
    private String versionNo;
    
    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;
    
    @Id
    @GeneratedValue(generator = "native-generator")
    @Generator(name = "native-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "SEQ_CODE_ORDER_ITEM") })
    public BigDecimal getCodeOrderItemId()
    {
        return codeOrderItemId;
    }

    public void setCodeOrderItemId(BigDecimal codeOrderItemId)
    {
        this.codeOrderItemId = codeOrderItemId;
    }

    @Column(name = "CS_ID", nullable = false, precision = 22, scale = 0)
    public BigDecimal getCsId()
    {
        return csId;
    }

    public void setCsId(BigDecimal csId)
    {
        this.csId = csId;
    }

    @Column(name = "CODE", length = 50)
    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    @Column(name = "VALUE", length = 255)
    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    @Column(name = "MESSAGE_TYPE_CODE", length = 12)
    public String getMessageTypeCode()
    {
        return messageTypeCode;
    }

    public void setMessageTypeCode(String messageTypeCode)
    {
        this.messageTypeCode = messageTypeCode;
    }

    @Column(name = "MESSAGE_TYPE_NAME", length = 192)
    public String getMessageTypeName()
    {
        return messageTypeName;
    }

    public void setMessageTypeName(String messageTypeName)
    {
        this.messageTypeName = messageTypeName;
    }

    @Column(name = "PY_CODE", length = 300)
    public String getPyCode()
    {
        return pyCode;
    }

    public void setPyCode(String pyCode)
    {
        this.pyCode = pyCode;
    }

    @Column(name = "SEQ_NO", length = 32)
    public String getSeqNo()
    {
        return seqNo;
    }

    public void setSeqNo(String seqNo)
    {
        this.seqNo = seqNo;
    }

    @Column(name = "VERSION_NO", length = 32)
    public String getVersionNo()
    {
        return versionNo;
    }

    public void setVersionNo(String versionNo)
    {
        this.versionNo = versionNo;
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
}
