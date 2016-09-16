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
import com.founder.fasf.core.util.daohelper.annotation.Parameter;

import java.io.Serializable;

@Entity
@Table(name = "WITHDRAW_RECORD")
public class WithdrawRecord implements Serializable
{

    private BigDecimal withdrawSn;

    private String withdrawPerson;

    private String withdrawPersonName;

    private Date withdrawTime;

    private String withdrawReason;

    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;

    private BigDecimal reportSn;

    private BigDecimal refType;

    private BigDecimal visitSn;
    
    private String createby;
    
    private String updateby;
    
    private String deleteby;

    @Id
    @GeneratedValue(generator = "native-generator")
    @Generator(name = "native-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "SEQ_RECORD") })
    public BigDecimal getWithdrawSn()
    {
        return this.withdrawSn;
    }

    public void setWithdrawSn(BigDecimal withdrawSn)
    {
        this.withdrawSn = withdrawSn;
    }

    @Column(name = "WITHDRAW_PERSON", length = 20)
    public String getWithdrawPerson()
    {
        return this.withdrawPerson;
    }

    public void setWithdrawPerson(String withdrawPerson)
    {
        this.withdrawPerson = withdrawPerson;
    }

    @Column(name = "WITHDRAW_PERSON_NAME", length = 56)
    public String getWithdrawPersonName()
    {
        return this.withdrawPersonName;
    }

    public void setWithdrawPersonName(String withdrawPersonName)
    {
        this.withdrawPersonName = withdrawPersonName;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "WITHDRAW_TIME", length = 7)
    public Date getWithdrawTime()
    {
        return this.withdrawTime;
    }

    public void setWithdrawTime(Date withdrawTime)
    {
        this.withdrawTime = withdrawTime;
    }

    @Column(name = "WITHDRAW_REASON", length = 512)
    public String getWithdrawReason()
    {
        return this.withdrawReason;
    }

    public void setWithdrawReason(String withdrawReason)
    {
        this.withdrawReason = withdrawReason;
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

    @Column(name = "REPORT_SN", precision = 22, scale = 0)
    public BigDecimal getReportSn()
    {
        return this.reportSn;
    }

    public void setReportSn(BigDecimal reportSn)
    {
        this.reportSn = reportSn;
    }

    @Column(name = "REF_TYPE", precision = 22, scale = 0)
    public BigDecimal getRefType()
    {
        return this.refType;
    }

    public void setRefType(BigDecimal refType)
    {
        this.refType = refType;
    }

    @Column(name = "VISIT_SN", precision = 22, scale = 0)
    public BigDecimal getVisitSn()
    {
        return visitSn;
    }

    public void setVisitSn(BigDecimal visitSn)
    {
        this.visitSn = visitSn;
    }
    @Column(name = "CREATEBY", length = 12)
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

}
