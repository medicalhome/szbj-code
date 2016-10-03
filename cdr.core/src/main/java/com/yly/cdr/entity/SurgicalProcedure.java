package com.yly.cdr.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import com.founder.fasf.core.util.daohelper.annotation.Generator;

import java.io.Serializable;

@Entity
@Table(name = "SURGICAL_PROCEDURE", uniqueConstraints = @UniqueConstraint(columnNames = "ORDER_SN"))
public class SurgicalProcedure implements Serializable
{

    private BigDecimal procedureSn;

    private BigDecimal visitSn;

    private BigDecimal anesthesiaSn;

    private BigDecimal patientSn;

    private String patientDomain;

    private String patientLid;

    private String operationName;

    private String operatingRoom;

    private Date operationDate;

    private String surgicalDept;

    private String difficulty;

    private Date startTime;

    private Date endTime;

    private String workload;

    private String healingGrade;

    private String surgerySite;

    private String operationMethod;

    private BigDecimal operationTimes;

    private String intervenor;

    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;

    private BigDecimal orderSn;

    private String operationCode;

    private String surgicalDeptName;

    private String healingGradeName;

    private String surgerySiteCode;

    private String operationMethodName;
    
    private String orgCode;
    
    private String orgName;

    @Id
    @GeneratedValue(generator = "guid-generator")
    @Generator(name = "guid-generator", strategy = "guid")
    public BigDecimal getProcedureSn()
    {
        return this.procedureSn;
    }

    public void setProcedureSn(BigDecimal procedureSn)
    {
        this.procedureSn = procedureSn;
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

    @Column(name = "ANESTHESIA_SN", precision = 22, scale = 0)
    public BigDecimal getAnesthesiaSn()
    {
        return this.anesthesiaSn;
    }

    public void setAnesthesiaSn(BigDecimal anesthesiaSn)
    {
        this.anesthesiaSn = anesthesiaSn;
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

    @Column(name = "OPERATION_NAME", length = 20)
    public String getOperationName()
    {
        return this.operationName;
    }

    public void setOperationName(String operationName)
    {
        this.operationName = operationName;
    }

    @Column(name = "OPERATING_ROOM", nullable = false, length = 20)
    public String getOperatingRoom()
    {
        return this.operatingRoom;
    }

    public void setOperatingRoom(String operatingRoom)
    {
        this.operatingRoom = operatingRoom;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "OPERATION_DATE", nullable = false, length = 7)
    public Date getOperationDate()
    {
        return this.operationDate;
    }

    public void setOperationDate(Date operationDate)
    {
        this.operationDate = operationDate;
    }

    @Column(name = "SURGICAL_DEPT", nullable = false, length = 20)
    public String getSurgicalDept()
    {
        return this.surgicalDept;
    }

    public void setSurgicalDept(String surgicalDept)
    {
        this.surgicalDept = surgicalDept;
    }

    @Column(name = "DIFFICULTY", length = 20)
    public String getDifficulty()
    {
        return this.difficulty;
    }

    public void setDifficulty(String difficulty)
    {
        this.difficulty = difficulty;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "START_TIME", nullable = false, length = 7)
    public Date getStartTime()
    {
        return this.startTime;
    }

    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "END_TIME", nullable = false, length = 7)
    public Date getEndTime()
    {
        return this.endTime;
    }

    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }

    @Column(name = "WORKLOAD", length = 20)
    public String getWorkload()
    {
        return this.workload;
    }

    public void setWorkload(String workload)
    {
        this.workload = workload;
    }

    @Column(name = "HEALING_GRADE", length = 20)
    public String getHealingGrade()
    {
        return this.healingGrade;
    }

    public void setHealingGrade(String healingGrade)
    {
        this.healingGrade = healingGrade;
    }

    @Column(name = "SURGERY_SITE", length = 20)
    public String getSurgerySite()
    {
        return this.surgerySite;
    }

    public void setSurgerySite(String surgerySite)
    {
        this.surgerySite = surgerySite;
    }

    @Column(name = "OPERATION_METHOD", length = 20)
    public String getOperationMethod()
    {
        return this.operationMethod;
    }

    public void setOperationMethod(String operationMethod)
    {
        this.operationMethod = operationMethod;
    }

    @Column(name = "OPERATION_TIMES", precision = 22, scale = 0)
    public BigDecimal getOperationTimes()
    {
        return this.operationTimes;
    }

    public void setOperationTimes(BigDecimal operationTimes)
    {
        this.operationTimes = operationTimes;
    }

    @Column(name = "INTERVENOR", length = 20)
    public String getIntervenor()
    {
        return this.intervenor;
    }

    public void setIntervenor(String intervenor)
    {
        this.intervenor = intervenor;
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

    @Column(name = "ORDER_SN", unique = true, precision = 22, scale = 0)
    public BigDecimal getOrderSn()
    {
        return this.orderSn;
    }

    public void setOrderSn(BigDecimal orderSn)
    {
        this.orderSn = orderSn;
    }

    @Column(name = "OPERATION_CODE", length = 20)
    public String getOperationCode()
    {
        return this.operationCode;
    }

    public void setOperationCode(String operationCode)
    {
        this.operationCode = operationCode;
    }

    @Column(name = "SURGICAL_DEPT_NAME", length = 40)
    public String getSurgicalDeptName()
    {
        return this.surgicalDeptName;
    }

    public void setSurgicalDeptName(String surgicalDeptName)
    {
        this.surgicalDeptName = surgicalDeptName;
    }

    @Column(name = "HEALING_GRADE_NAME", length = 40)
    public String getHealingGradeName()
    {
        return this.healingGradeName;
    }

    public void setHealingGradeName(String healingGradeName)
    {
        this.healingGradeName = healingGradeName;
    }

    @Column(name = "SURGERY_SITE_CODE", length = 20)
    public String getSurgerySiteCode()
    {
        return this.surgerySiteCode;
    }

    public void setSurgerySiteCode(String surgerySiteCode)
    {
        this.surgerySiteCode = surgerySiteCode;
    }

    @Column(name = "OPERATION_METHOD_NAME", length = 40)
    public String getOperationMethodName()
    {
        return this.operationMethodName;
    }

    public void setOperationMethodName(String operationMethodName)
    {
        this.operationMethodName = operationMethodName;
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
