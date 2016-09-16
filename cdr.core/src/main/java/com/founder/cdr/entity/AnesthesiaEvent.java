package com.founder.cdr.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import com.founder.fasf.core.util.daohelper.annotation.Generator;

import java.io.Serializable;

@Entity
@Table(name = "ANESTHESIA_EVENT")
public class AnesthesiaEvent implements Serializable
{

    private BigDecimal eventSn;

    private BigDecimal anesthesiaSn;

    private String operator;

    private String operatorName;

    private String operationType;

    private Date startTime;

    private Date finishedTime;

    private String operationContent;

    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;

    @Id
    @GeneratedValue(generator = "guid-generator")
    @Generator(name = "guid-generator", strategy = "guid")
    public BigDecimal getEventSn()
    {
        return this.eventSn;
    }

    public void setEventSn(BigDecimal eventSn)
    {
        this.eventSn = eventSn;
    }

    @Column(name = "ANESTHESIA_SN", nullable = false, precision = 22, scale = 0)
    public BigDecimal getAnesthesiaSn()
    {
        return this.anesthesiaSn;
    }

    public void setAnesthesiaSn(BigDecimal anesthesiaSn)
    {
        this.anesthesiaSn = anesthesiaSn;
    }

    @Column(name = "OPERATOR", length = 20)
    public String getOperator()
    {
        return this.operator;
    }

    public void setOperator(String operator)
    {
        this.operator = operator;
    }

    @Column(name = "OPERATOR_NAME", length = 56)
    public String getOperatorName()
    {
        return this.operatorName;
    }

    public void setOperatorName(String operatorName)
    {
        this.operatorName = operatorName;
    }

    @Column(name = "OPERATION_TYPE", length = 20)
    public String getOperationType()
    {
        return this.operationType;
    }

    public void setOperationType(String operationType)
    {
        this.operationType = operationType;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "START_TIME", length = 7)
    public Date getStartTime()
    {
        return this.startTime;
    }

    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "FINISHED_TIME", length = 7)
    public Date getFinishedTime()
    {
        return this.finishedTime;
    }

    public void setFinishedTime(Date finishedTime)
    {
        this.finishedTime = finishedTime;
    }

    @Column(name = "OPERATION_CONTENT", length = 50)
    public String getOperationContent()
    {
        return this.operationContent;
    }

    public void setOperationContent(String operationContent)
    {
        this.operationContent = operationContent;
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
