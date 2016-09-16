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

import com.founder.fasf.core.util.daohelper.annotation.Generator;
import com.founder.fasf.core.util.daohelper.annotation.Parameter;

import java.io.Serializable;

@Entity
@Table(name = "WARNING_RECORD")
public class WarningRecord implements Serializable
{

    private BigDecimal warningRecordSn;

    private BigDecimal labReportSn;

    private String triggerEvent;
    
    private Date reportDate;

    private String uncomQueueCompleted;

    private String crisisQueueCompleted;

    private String uncomMailCompleted;

    private String crisisMailCompleted;

    private String uncomMobileCompleted;

    private String crisisMobileCompleted;

    private String ruleCompleted;

    private String warningSuccess;

    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;

    @Id
    @GeneratedValue(generator = "native-generator")
    @Generator(name = "native-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "SEQ_WARNING_RECORD") })
    public BigDecimal getWarningRecordSn()
    {
        return this.warningRecordSn;
    }

    public void setWarningRecordSn(BigDecimal warningRecordSn)
    {
        this.warningRecordSn = warningRecordSn;
    }

    @Column(name = "LAB_REPORT_SN", nullable = false, precision = 22, scale = 0)
    public BigDecimal getLabReportSn()
    {
        return this.labReportSn;
    }

    public void setLabReportSn(BigDecimal labReportSn)
    {
        this.labReportSn = labReportSn;
    }

    @Column(name = "TRIGGER_EVENT", length = 20)
    public String getTriggerEvent()
    {
        return this.triggerEvent;
    }

    public void setTriggerEvent(String triggerEvent)
    {
        this.triggerEvent = triggerEvent;
    }

    @Column(name = "UNCOM_QUEUE_COMPLETED", length = 1)
    public String getUncomQueueCompleted()
    {
        return uncomQueueCompleted;
    }

    public void setUncomQueueCompleted(String uncomQueueCompleted)
    {
        this.uncomQueueCompleted = uncomQueueCompleted;
    }

    @Column(name = "CRISIS_QUEUE_COMPLETED", length = 1)
    public String getCrisisQueueCompleted()
    {
        return crisisQueueCompleted;
    }

    public void setCrisisQueueCompleted(String crisisQueueCompleted)
    {
        this.crisisQueueCompleted = crisisQueueCompleted;
    }

    @Column(name = "UNCOM_MAIL_COMPLETED", length = 1)
    public String getUncomMailCompleted()
    {
        return uncomMailCompleted;
    }

    public void setUncomMailCompleted(String uncomMailCompleted)
    {
        this.uncomMailCompleted = uncomMailCompleted;
    }

    @Column(name = "CRISIS_MAIL_COMPLETED", length = 1)
    public String getCrisisMailCompleted()
    {
        return crisisMailCompleted;
    }

    public void setCrisisMailCompleted(String crisisMailCompleted)
    {
        this.crisisMailCompleted = crisisMailCompleted;
    }

    @Column(name = "UNCOM_MOBILE_COMPLETED", length = 1)
    public String getUncomMobileCompleted()
    {
        return uncomMobileCompleted;
    }

    public void setUncomMobileCompleted(String uncomMobileCompleted)
    {
        this.uncomMobileCompleted = uncomMobileCompleted;
    }

    @Column(name = "RULE_COMPLETED", length = 1)
    public String getRuleCompleted()
    {
        return ruleCompleted;
    }

    public void setRuleCompleted(String ruleCompleted)
    {
        this.ruleCompleted = ruleCompleted;
    }

    @Column(name = "CRISIS_MOBILE_COMPLETED", length = 1)
    public String getWarningSuccess()
    {
        return warningSuccess;
    }

    public void setWarningSuccess(String warningSuccess)
    {
        this.warningSuccess = warningSuccess;
    }

    @Column(name = "CRISIS_MOBILE_COMPLETED", length = 1)
    public String getCrisisMobileCompleted()
    {
        return crisisMobileCompleted;
    }

    public void setCrisisMobileCompleted(String crisisMobileCompleted)
    {
        this.crisisMobileCompleted = crisisMobileCompleted;
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
    
    @Temporal(TemporalType.DATE)
    @Column(name = "REPORT_DATE", length = 7)
    public Date getReportDate()
    {
        return this.reportDate;
    }

    public void setReportDate(Date reportDate)
    {
        this.reportDate = reportDate;
    }
}
