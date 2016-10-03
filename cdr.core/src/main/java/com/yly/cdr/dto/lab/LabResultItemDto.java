package com.yly.cdr.dto.lab;

import java.util.Date;

public class LabResultItemDto
{
    private String itemCode;

    private String labResultCompositeItemSn;

    private String itemNameCn;

    private String itemNameEn;

    private String itemUnit;

    private String itemValue;

    private String lowValue;

    private String highValue;

    private String warnLowValue;

    private String warnHighValue;

    private String normalFlag;

    private String qualitativeResults;

    private String displayOrder;

    private String reportMemo;

    private String resultHighLowFlagStr;

    private Date testDate;

    private String normalRefValueText;

    public String getNormalRefValueText()
    {
        return normalRefValueText;
    }

    public void setNormalRefValueText(String normalRefValueText)
    {
        this.normalRefValueText = normalRefValueText;
    }

    public String getItemCode()
    {
        return itemCode;
    }

    public void setItemCode(String itemCode)
    {
        this.itemCode = itemCode;
    }

    public String getLabResultCompositeItemSn()
    {
        return labResultCompositeItemSn;
    }

    public void setLabResultCompositeItemSn(String labResultCompositeItemSn)
    {
        this.labResultCompositeItemSn = labResultCompositeItemSn;
    }

    public String getItemNameCn()
    {
        return itemNameCn;
    }

    public void setItemNameCn(String itemNameCn)
    {
        this.itemNameCn = itemNameCn;
    }

    public String getItemNameEn()
    {
        return itemNameEn;
    }

    public void setItemNameEn(String itemNameEn)
    {
        this.itemNameEn = itemNameEn;
    }

    public String getItemUnit()
    {
        return itemUnit;
    }

    public void setItemUnit(String itemUnit)
    {
        this.itemUnit = itemUnit;
    }

    public String getItemValue()
    {
        return itemValue;
    }

    public void setItemValue(String itemValue)
    {
        this.itemValue = itemValue;
    }

    public String getLowValue()
    {
        return lowValue;
    }

    public void setLowValue(String lowValue)
    {
        this.lowValue = lowValue;
    }

    public String getHighValue()
    {
        return highValue;
    }

    public void setHighValue(String highValue)
    {
        this.highValue = highValue;
    }

    public String getWarnLowValue()
    {
        return warnLowValue;
    }

    public void setWarnLowValue(String warnLowValue)
    {
        this.warnLowValue = warnLowValue;
    }

    public String getWarnHighValue()
    {
        return warnHighValue;
    }

    public void setWarnHighValue(String warnHighValue)
    {
        this.warnHighValue = warnHighValue;
    }

    public String getNormalFlag()
    {
        return normalFlag;
    }

    public void setNormalFlag(String normalFlag)
    {
        this.normalFlag = normalFlag;
    }

    public String getQualitativeResults()
    {
        return qualitativeResults;
    }

    public void setQualitativeResults(String qualitativeResults)
    {
        this.qualitativeResults = qualitativeResults;
    }

    public String getDisplayOrder()
    {
        return displayOrder;
    }

    public void setDisplayOrder(String displayOrder)
    {
        this.displayOrder = displayOrder;
    }

    public String getReportMemo()
    {
        return reportMemo;
    }

    public void setReportMemo(String reportMemo)
    {
        this.reportMemo = reportMemo;
    }

    public String getResultHighLowFlagStr()
    {
        return resultHighLowFlagStr;
    }

    public void setResultHighLowFlagStr(String resultHighLowFlagStr)
    {
        this.resultHighLowFlagStr = resultHighLowFlagStr;
    }

    public Date getTestDate()
    {
        return testDate;
    }

    public void setTestDate(Date testDate)
    {
        this.testDate = testDate;
    }

}
