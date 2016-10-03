package com.yly.cdr.hl7.dto;


public class MorphologyReportResult extends BaseDto
{
    /**
     * 显示序号
     */
    private String displayOrder;
    /**
     * 项目编码
     */
    private String itemCode;
    /**
     * 项目名称
     */
    private String itemNameCn;
    /**
     * 检验结果值1
     */
    private String itemValueOne;
    /**
     * 检验结果单位
     */
    private String itemUnit;
    /**
     * 检验结果值2
     */
    private String itemValueTwo;
    /**
     * 校正值
     */
    private String correctMom;
    /**
     * 正常值参考值
     */
    private String normalRefValueText;
    /**
     * 风险值
     */
    private String testerRiskValue;
    /**
     * 年龄风险值
     */
    private String ageRiskValue;
    /**
     * 风险截断值
     */
    private String riskCutoffValue;
    /**
     * 正常低值
     */
    private String lowValue;
    /**
     * 正常高值
     */
    private String highValue;
    /**
     * 骨髓片平均值
     */
    private String averageValue;
    /**
     * 骨髓片+/-SD
     */
    private String sdValue;

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

    public String getItemValueOne()
    {
        return itemValueOne;
    }

    public String getItemUnit()
    {
        return itemUnit;
    }

    public String getItemValueTwo()
    {
        return itemValueTwo;
    }

    public String getCorrectMom()
    {
        return correctMom;
    }

    public String getNormalRefValueText()
    {
        return normalRefValueText;
    }

    public String getTesterRiskValue()
    {
        return testerRiskValue;
    }

    public String getAgeRiskValue()
    {
        return ageRiskValue;
    }

    public String getRiskCutoffValue()
    {
        return riskCutoffValue;
    }

    public String getLowValue()
    {
        return lowValue;
    }

    public String getHighValue()
    {
        return highValue;
    }

    public String getAverageValue()
    {
        return averageValue;
    }

    public String getSdValue()
    {
        return sdValue;
    }
}
