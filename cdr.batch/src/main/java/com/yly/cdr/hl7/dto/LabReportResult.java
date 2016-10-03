package com.yly.cdr.hl7.dto;

import java.util.List;

public class LabReportResult extends BaseDto
{   
    /**
     * 显示顺序
     */
    private String displayOrder;
    /**
     * 项目代码
     */
    private String itemCode;
    /**
     * 项目中文名称
     */
    private String itemNameCn;
    /**
     * 项目英文名称
     */
    private String itemNameEn;
    /**
     * 备注
     */
    private String reportMemo;
    /**
     * 检验结果
     */
    private String qualitativeResults;
    /**
     * 项目值
     */
    private String itemValuePQ;
    /**
     * 项目单位
     */
    private String itemUnitPQ;
    /**
     * 项目值
     */
    private String itemValueSTAndSC;
    /**
     * 项目单位
     */
    private String itemUnitSC;
    /**
     * 正常标志
     */
    private String normalFlag;
    /**
     * 正常低值
     */
    private String lowValue;
    /**
     * 正常高值
     */
    private String highValue;
    /**
     * 警告值低值
     */
    private String warnLowValue;
    /**
     * 警告值高值
     */
    private String warnHighValue;
    /**
     * 检验结果(微生物)
     */
    private String labResult;
    /**
     * 抗菌药物信息
     */
    private List<PreventAllergyDrug> preventAllergyDrug;
    /**
     * 正常标志名称
     */
    private String normalFlagName;
    /**
     * 危险标志
     */
    private String warnFlag;
    /**
     * 危险标志名称
     */
    private String warnFlagName;
    /**
     * 微生物培养报告项目代码
     */
    private String itemCodeForCultivate;
    
    public String getDisplayOrder()
    {
        return displayOrder;
    }

    public String getItemCode()
    {
        return itemCode;
    }

    public String getItemNameCn()
    {
        return itemNameCn;
    }

    public String getItemNameEn()
    {
        return itemNameEn;
    }

    public String getReportMemo()
    {
        return reportMemo;
    }

    public String getQualitativeResults()
    {
        return qualitativeResults;
    }

    public String getItemValuePQ()
    {
        return itemValuePQ;
    }

    public String getItemUnitPQ()
    {
        return itemUnitPQ;
    }

    public String getItemValueSTAndSC()
    {
        return itemValueSTAndSC;
    }

    public String getItemUnitSC()
    {
        return itemUnitSC;
    }

    public String getNormalFlag()
    {
        return normalFlag;
    }

    public String getLowValue()
    {
        return lowValue;
    }

    public String getHighValue()
    {
        return highValue;
    }

    public String getWarnLowValue()
    {
        return warnLowValue;
    }

    public String getWarnHighValue()
    {
        return warnHighValue;
    }

    public String getLabResult()
    {
        return labResult;
    }

    public List<PreventAllergyDrug> getPreventAllergyDrug()
    {
        return preventAllergyDrug;
    }

    public String getNormalFlagName()
    {
        return normalFlagName;
    }

    public String getWarnFlag()
    {
        return warnFlag;
    }

    public String getWarnFlagName()
    {
        return warnFlagName;
    }
    
    public String getItemCodeForCultivate()
    {
        return itemCodeForCultivate;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("LabReportResult [itemCode=");
        builder.append(itemCode);
        builder.append(", itemCodeForCultivate=");
        builder.append(itemCodeForCultivate);
        builder.append("]");
        return builder.toString();
    }
    
    
    
}
