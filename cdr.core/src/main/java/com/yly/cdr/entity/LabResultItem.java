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
@Table(name = "LAB_RESULT_ITEM")
public class LabResultItem implements Serializable
{

    private String itemCode;

    private BigDecimal labResultCompositeItemSn;

    private String itemNameCn;

    private String itemNameEn;

    private String itemUnit;

    private String itemValue;

    private BigDecimal lowValue;

    private BigDecimal highValue;

    private BigDecimal warnLowValue;

    private BigDecimal warnHighValue;

    private String normalFlag;

    private String qualitativeResults;

    private BigDecimal displayOrder;

    private String reportMemo;

    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;

    private String normalRefValueText;

    private BigDecimal averageValue;

    private BigDecimal standardDeviation;

    private String normalFlagName;

    private String warnFlag;

    private String warnFlagName;

    private String riskValue;

    private String ageRiskValue;

    private BigDecimal correctMom;
    
    private String uncommonFlag;
    
    private String crisisFlag;
    
    private String createby;

    private String updateby;

    private String deleteby;

    @Id
    public String getItemCode()
    {
        return this.itemCode;
    }

    public void setItemCode(String itemCode)
    {
        this.itemCode = itemCode;
    }

    @Id
    @GeneratedValue(generator = "guid-generator")
    @Generator(name = "guid-generator", strategy = "guid")
    public BigDecimal getLabResultCompositeItemSn()
    {
        return this.labResultCompositeItemSn;
    }

    public void setLabResultCompositeItemSn(BigDecimal labResultCompositeItemSn)
    {
        this.labResultCompositeItemSn = labResultCompositeItemSn;
    }

    @Column(name = "ITEM_NAME_CN", length = 300)
    public String getItemNameCn()
    {
        return this.itemNameCn;
    }

    public void setItemNameCn(String itemNameCn)
    {
        this.itemNameCn = itemNameCn;
    }

    @Column(name = "ITEM_NAME_EN", length = 150)
    public String getItemNameEn()
    {
        return this.itemNameEn;
    }

    public void setItemNameEn(String itemNameEn)
    {
        this.itemNameEn = itemNameEn;
    }

    @Column(name = "ITEM_UNIT", length = 56)
    public String getItemUnit()
    {
        return this.itemUnit;
    }

    public void setItemUnit(String itemUnit)
    {
        this.itemUnit = itemUnit;
    }

    @Column(name = "ITEM_VALUE", length = 300)
    public String getItemValue()
    {
        return this.itemValue;
    }

    public void setItemValue(String itemValue)
    {
        this.itemValue = itemValue;
    }

    @Column(name = "LOW_VALUE", precision = 14, scale = 4)
    public BigDecimal getLowValue()
    {
        return this.lowValue;
    }

    public void setLowValue(BigDecimal lowValue)
    {
        this.lowValue = lowValue;
    }

    @Column(name = "HIGH_VALUE", precision = 14, scale = 4)
    public BigDecimal getHighValue()
    {
        return this.highValue;
    }

    public void setHighValue(BigDecimal highValue)
    {
        this.highValue = highValue;
    }

    @Column(name = "WARN_LOW_VALUE", precision = 14, scale = 4)
    public BigDecimal getWarnLowValue()
    {
        return this.warnLowValue;
    }

    public void setWarnLowValue(BigDecimal warnLowValue)
    {
        this.warnLowValue = warnLowValue;
    }

    @Column(name = "WARN_HIGH_VALUE", precision = 14, scale = 4)
    public BigDecimal getWarnHighValue()
    {
        return this.warnHighValue;
    }

    public void setWarnHighValue(BigDecimal warnHighValue)
    {
        this.warnHighValue = warnHighValue;
    }

    @Column(name = "NORMAL_FLAG", length = 12)
    public String getNormalFlag()
    {
        return this.normalFlag;
    }

    public void setNormalFlag(String normalFlag)
    {
        this.normalFlag = normalFlag;
    }

    @Column(name = "QUALITATIVE_RESULTS", length = 1200)
    public String getQualitativeResults()
    {
        return this.qualitativeResults;
    }

    public void setQualitativeResults(String qualitativeResults)
    {
        this.qualitativeResults = qualitativeResults;
    }

    @Id
    @Column(name = "DISPLAY_ORDER", precision = 22, scale = 0)
    public BigDecimal getDisplayOrder()
    {
        return this.displayOrder;
    }

    public void setDisplayOrder(BigDecimal displayOrder)
    {
        this.displayOrder = displayOrder;
    }

    @Column(name = "REPORT_MEMO", length = 300)
    public String getReportMemo()
    {
        return this.reportMemo;
    }

    public void setReportMemo(String reportMemo)
    {
        this.reportMemo = reportMemo;
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

    @Column(name = "NORMAL_REF_VALUE_TEXT", length = 1200)
    public String getNormalRefValueText()
    {
        return this.normalRefValueText;
    }

    public void setNormalRefValueText(String normalRefValueText)
    {
        this.normalRefValueText = normalRefValueText;
    }

    @Column(name = "AVERAGE_VALUE", precision = 10, scale = 4)
    public BigDecimal getAverageValue()
    {
        return this.averageValue;
    }

    public void setAverageValue(BigDecimal averageValue)
    {
        this.averageValue = averageValue;
    }

    @Column(name = "STANDARD_DEVIATION", precision = 10, scale = 4)
    public BigDecimal getStandardDeviation()
    {
        return this.standardDeviation;
    }

    public void setStandardDeviation(BigDecimal standardDeviation)
    {
        this.standardDeviation = standardDeviation;
    }

    @Column(name = "NORMAL_FLAG_NAME", length = 12)
    public String getNormalFlagName()
    {
        return this.normalFlagName;
    }

    public void setNormalFlagName(String normalFlagName)
    {
        this.normalFlagName = normalFlagName;
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

    @Column(name = "RISK_VALUE", length = 10)
    public String getRiskValue()
    {
        return this.riskValue;
    }

    public void setRiskValue(String riskValue)
    {
        this.riskValue = riskValue;
    }

    @Column(name = "AGE_RISK_VALUE", length = 10)
    public String getAgeRiskValue()
    {
        return this.ageRiskValue;
    }

    public void setAgeRiskValue(String ageRiskValue)
    {
        this.ageRiskValue = ageRiskValue;
    }

    @Column(name = "CORRECT_MOM", precision = 10, scale = 4)
    public BigDecimal getCorrectMom()
    {
        return this.correctMom;
    }

    public void setCorrectMom(BigDecimal correctMom)
    {
        this.correctMom = correctMom;
    }
    
    @Column(name = "UNCOMMON_FLAG", length = 1)
    public String getUncommonFlag()
    {
        return uncommonFlag;
    }

    public void setUncommonFlag(String uncommonFlag)
    {
        this.uncommonFlag = uncommonFlag;
    }

    @Column(name = "CRISIS_FLAG", length = 1)
    public String getCrisisFlag()
    {
        return crisisFlag;
    }

    public void setCrisisFlag(String crisisFlag)
    {
        this.crisisFlag = crisisFlag;
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
