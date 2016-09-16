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
@Table(name = "NURSING_OPERATION")
public class NursingOperation implements Serializable
{

    private BigDecimal operationSn;

    private BigDecimal visitSn;

    private BigDecimal documentSn;

    private BigDecimal orderSn;

    private BigDecimal patientSn;

    private String patientDomain;

    private String patientLid;

    private String orderType;

    private String operationType;

    private String operationTypeName;

    private String operationItemName;

    private String operatorId;

    private String operatorName;

    private String operationResult;

    private String careType;

    private String operationEvaluation;

    private String reviewerId;

    private String reviewerName;

    private Date reviewerTime;

    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;

    private String orderTypeName;

    private String careTypeName;

    private Date operationTime;
    
    // Author :jia_yanqing
    // Date : 2013/1/17 13:33
    // [BUG]0013379 ADD BEGIN
    private String operationId;
    // [BUG]0013379 ADD END
    
    // Author :jia_yanqing
    // Date : 2013/6/28 16:33
    // [BUG]0033848 ADD BEGIN
    private String orderStatusCode;

    private String orderStatusName;
    
    private String executeDeptCode;

    private String executeDeptName;
    // [BUG]0033848 ADD END

    private String createby;

    private String updateby;

    private String deleteby;
    
    private String orgCode;
    
    private String orgName;
    
    @Id
    public BigDecimal getOperationSn()
    {
        return this.operationSn;
    }

    public void setOperationSn(BigDecimal operationSn)
    {
        this.operationSn = operationSn;
    }

    @Column(name = "VISIT_SN", nullable = false, precision = 22, scale = 0)
    public BigDecimal getVisitSn()
    {
        return this.visitSn;
    }

    public void setVisitSn(BigDecimal visitSn)
    {
        this.visitSn = visitSn;
    }

    @Column(name = "DOCUMENT_SN", precision = 22, scale = 0)
    public BigDecimal getDocumentSn()
    {
        return this.documentSn;
    }

    public void setDocumentSn(BigDecimal documentSn)
    {
        this.documentSn = documentSn;
    }

    @Column(name = "ORDER_SN", precision = 22, scale = 0)
    public BigDecimal getOrderSn()
    {
        return this.orderSn;
    }

    public void setOrderSn(BigDecimal orderSn)
    {
        this.orderSn = orderSn;
    }

    @Column(name = "PATIENT_SN", nullable = false, precision = 22, scale = 0)
    public BigDecimal getPatientSn()
    {
        return this.patientSn;
    }

    public void setPatientSn(BigDecimal patientSn)
    {
        this.patientSn = patientSn;
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

    @Column(name = "PATIENT_LID", nullable = false, length = 12)
    public String getPatientLid()
    {
        return this.patientLid;
    }

    public void setPatientLid(String patientLid)
    {
        this.patientLid = patientLid;
    }

    @Column(name = "ORDER_TYPE", length = 6)
    public String getOrderType()
    {
        return this.orderType;
    }

    public void setOrderType(String orderType)
    {
        this.orderType = orderType;
    }

    @Column(name = "OPERATION_TYPE", length = 12)
    public String getOperationType()
    {
        return this.operationType;
    }

    public void setOperationType(String operationType)
    {
        this.operationType = operationType;
    }

    @Column(name = "OPERATION_TYPE_NAME", length = 64)
    public String getOperationTypeName()
    {
        return this.operationTypeName;
    }

    public void setOperationTypeName(String operationTypeName)
    {
        this.operationTypeName = operationTypeName;
    }

    @Column(name = "OPERATION_ITEM_NAME", length = 64)
    public String getOperationItemName()
    {
        return this.operationItemName;
    }

    public void setOperationItemName(String operationItemName)
    {
        this.operationItemName = operationItemName;
    }

    @Column(name = "OPERATOR_ID", length = 24)
    public String getOperatorId()
    {
        return this.operatorId;
    }

    public void setOperatorId(String operatorId)
    {
        this.operatorId = operatorId;
    }

    @Column(name = "OPERATOR_NAME", length = 100)
    public String getOperatorName()
    {
        return this.operatorName;
    }

    public void setOperatorName(String operatorName)
    {
        this.operatorName = operatorName;
    }

    @Column(name = "OPERATION_RESULT", length = 256)
    public String getOperationResult()
    {
        return this.operationResult;
    }

    public void setOperationResult(String operationResult)
    {
        this.operationResult = operationResult;
    }

    @Column(name = "CARE_TYPE", length = 64)
    public String getCareType()
    {
        return this.careType;
    }

    public void setCareType(String careType)
    {
        this.careType = careType;
    }

    @Column(name = "OPERATION_EVALUATION", length = 4000)
    public String getOperationEvaluation()
    {
        return this.operationEvaluation;
    }

    public void setOperationEvaluation(String operationEvaluation)
    {
        this.operationEvaluation = operationEvaluation;
    }

    @Column(name = "REVIEWER_ID", length = 12)
    public String getReviewerId()
    {
        return this.reviewerId;
    }

    public void setReviewerId(String reviewerId)
    {
        this.reviewerId = reviewerId;
    }

    @Column(name = "REVIEWER_NAME", length = 100)
    public String getReviewerName()
    {
        return this.reviewerName;
    }

    public void setReviewerName(String reviewerName)
    {
        this.reviewerName = reviewerName;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "REVIEWER_TIME", length = 7)
    public Date getReviewerTime()
    {
        return this.reviewerTime;
    }

    public void setReviewerTime(Date reviewerTime)
    {
        this.reviewerTime = reviewerTime;
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

    @Column(name = "ORDER_TYPE_NAME", length = 40)
    public String getOrderTypeName()
    {
        return this.orderTypeName;
    }

    public void setOrderTypeName(String orderTypeName)
    {
        this.orderTypeName = orderTypeName;
    }

    @Column(name = "CARE_TYPE_NAME", length = 40)
    public String getCareTypeName()
    {
        return this.careTypeName;
    }

    public void setCareTypeName(String careTypeName)
    {
        this.careTypeName = careTypeName;
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

    // Author :jia_yanqing
    // Date : 2013/1/17 13:33
    // [BUG]0013379 ADD BEGIN
    @Column(name = "OPERATION_ID", length = 30)
    public String getOperationId()
    {
        return operationId;
    }

    public void setOperationId(String operationId)
    {
        this.operationId = operationId;
    }
    // [BUG]0013379 ADD END

    // Author :jia_yanqing
    // Date : 2013/6/28 16:33
    // [BUG]0033848 ADD BEGIN
    @Column(name = "ORDER_STATUS_CODE", length = 12)
    public String getOrderStatusCode()
    {
        return orderStatusCode;
    }

    public void setOrderStatusCode(String orderStatusCode)
    {
        this.orderStatusCode = orderStatusCode;
    }

    @Column(name = "ORDER_STATUS_NAME", length = 192)
    public String getOrderStatusName()
    {
        return orderStatusName;
    }

    public void setOrderStatusName(String orderStatusName)
    {
        this.orderStatusName = orderStatusName;
    }

    @Column(name = "EXECUTE_DEPT_CODE", length = 12)
    public String getExecuteDeptCode()
    {
        return executeDeptCode;
    }

    public void setExecuteDeptCode(String executeDeptCode)
    {
        this.executeDeptCode = executeDeptCode;
    }

    @Column(name = "EXECUTE_DEPT_NAME", length = 192)
    public String getExecuteDeptName()
    {
        return executeDeptName;
    }

    public void setExecuteDeptName(String executeDeptName)
    {
        this.executeDeptName = executeDeptName;
    }
    // [BUG]0033848 ADD END
    @Column(name = "CREATEBY", length = 24)
    public String getCreateby()
    {
        return this.createby;
    }

    public void setCreateby(String createby)
    {
        this.createby = createby;
    }
    
    @Column(name = "UPDATEBY", length = 12)
    public String getUpdateby()
    {
        return this.updateby;
    }

    public void setUpdateby(String updateby)
    {
        this.updateby = updateby;
    }
    
    @Column(name = "DELETEBY", length = 12)
    public String getDeleteby()
    {
        return this.deleteby;
    }

    public void setDeleteby(String deleteby)
    {
        this.deleteby = deleteby;
    }

    @Column(name = "ORG_CODE", length = 50)
    public String getOrgCode()
    {
        return orgCode;
    }

    public void setOrgCode(String orgCode)
    {
        this.orgCode = orgCode;
    }

    @Column(name = "ORG_NAME", length = 50)
    public String getOrgName()
    {
        return orgName;
    }

    public void setOrgName(String orgName)
    {
        this.orgName = orgName;
    }
    
}
