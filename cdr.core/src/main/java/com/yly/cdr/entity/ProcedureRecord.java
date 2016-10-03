package com.yly.cdr.entity;

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
@Table(name = "PROCEDURE_RECORD")
public class ProcedureRecord implements Serializable
{

    private BigDecimal recordSn;

    private BigDecimal procedureSn;

    private String operator;

    private String operatorName;

    private String operationType;

    private Date operationTime;

    private String operationDescription;

    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;

    private String operationTypeName;

    @Id
    @GeneratedValue(generator = "guid-generator")
    @Generator(name = "guid-generator", strategy = "guid")
    public BigDecimal getRecordSn()
    {
        return this.recordSn;
    }

    public void setRecordSn(BigDecimal recordSn)
    {
        this.recordSn = recordSn;
    }

    @Column(name = "PROCEDURE_SN", nullable = false, precision = 22, scale = 0)
    public BigDecimal getProcedureSn()
    {
        return this.procedureSn;
    }

    public void setProcedureSn(BigDecimal procedureSn)
    {
        this.procedureSn = procedureSn;
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

    @Column(name = "OPERATION_TYPE", length = 50)
    public String getOperationType()
    {
        return this.operationType;
    }

    public void setOperationType(String operationType)
    {
        this.operationType = operationType;
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

    @Column(name = "OPERATION_DESCRIPTION", length = 50)
    public String getOperationDescription()
    {
        return this.operationDescription;
    }

    public void setOperationDescription(String operationDescription)
    {
        this.operationDescription = operationDescription;
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

    @Column(name = "OPERATION_TYPE_NAME", length = 100)
    public String getOperationTypeName()
    {
        return this.operationTypeName;
    }

    public void setOperationTypeName(String operationTypeName)
    {
        this.operationTypeName = operationTypeName;
    }

}
