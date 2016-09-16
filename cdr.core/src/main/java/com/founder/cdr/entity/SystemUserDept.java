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
@Table(name = "SYSTEM_USER_DEPT")
public class SystemUserDept implements Serializable
{

    private BigDecimal userDeptSn;

    private String userId;

    private String deptCode;

    private BigDecimal sortNo;

    private String patientDomain;

    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;

    @Id
    @GeneratedValue(generator = "guid-generator")
    @Generator(name = "guid-generator", strategy = "guid")
    public BigDecimal getUserDeptSn()
    {
        return this.userDeptSn;
    }

    public void setUserDeptSn(BigDecimal userDeptSn)
    {
        this.userDeptSn = userDeptSn;
    }

    @Column(name = "USER_ID", nullable = false, length = 12)
    public String getUserId()
    {
        return this.userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    @Column(name = "DEPT_CODE", nullable = false)
    public String getDeptCode()
    {
        return this.deptCode;
    }

    public void setDeptCode(String deptCode)
    {
        this.deptCode = deptCode;
    }

    @Column(name = "SORT_NO", precision = 22, scale = 0)
    public BigDecimal getSortNo()
    {
        return this.sortNo;
    }

    public void setSortNo(BigDecimal sortNo)
    {
        this.sortNo = sortNo;
    }

    @Column(name = "PATIENT_DOMAIN", nullable = false, length = 12)
    public String getPatientDomain()
    {
        return this.patientDomain;
    }

    public void setPatientDomain(String patientDomain)
    {
        this.patientDomain = patientDomain;
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
