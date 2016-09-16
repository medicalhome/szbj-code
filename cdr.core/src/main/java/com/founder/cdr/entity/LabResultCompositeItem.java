package com.founder.cdr.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "LAB_RESULT_COMPOSITE_ITEM")
public class LabResultCompositeItem implements Serializable
{

    private BigDecimal compositeItemSn;

    private BigDecimal labReportSn;

    private String itemCode;

    private String itemName;

    private String testMethod;

    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;

    private String warnFlag;

    private String warnFlagName;

    private String testMethodDesc;

    private String preparationMethod;

    private String createby;

    private String updateby;

    private String deleteby;
   @Id
    public BigDecimal getCompositeItemSn()
    {
        return this.compositeItemSn;
    }

    public void setCompositeItemSn(BigDecimal compositeItemSn)
    {
        this.compositeItemSn = compositeItemSn;
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

    @Column(name = "ITEM_CODE", length = 480)
    public String getItemCode()
    {
        return this.itemCode;
    }

    public void setItemCode(String itemCode)
    {
        this.itemCode = itemCode;
    }

    public String getItemName()
    {
        return this.itemName;
    }

    public void setItemName(String itemName)
    {
        this.itemName = itemName;
    }

    @Column(name = "TEST_METHOD", length = 180)
    public String getTestMethod()
    {
        return this.testMethod;
    }

    public void setTestMethod(String testMethod)
    {
        this.testMethod = testMethod;
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

    @Column(name = "WARN_FLAG", length = 12)
    public String getWarnFlag()
    {
        return this.warnFlag;
    }

    public void setWarnFlag(String warnFlag)
    {
        this.warnFlag = warnFlag;
    }

    @Column(name = "WARN_FLAG_NAME", length = 12)
    public String getWarnFlagName()
    {
        return this.warnFlagName;
    }

    public void setWarnFlagName(String warnFlagName)
    {
        this.warnFlagName = warnFlagName;
    }

    @Column(name = "TEST_METHOD_DESC")
    public String getTestMethodDesc()
    {
        return this.testMethodDesc;
    }

    public void setTestMethodDesc(String testMethodDesc)
    {
        this.testMethodDesc = testMethodDesc;
    }

    @Column(name = "PREPARATION_METHOD", length = 600)
    public String getPreparationMethod()
    {
        return this.preparationMethod;
    }

    public void setPreparationMethod(String preparationMethod)
    {
        this.preparationMethod = preparationMethod;
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
