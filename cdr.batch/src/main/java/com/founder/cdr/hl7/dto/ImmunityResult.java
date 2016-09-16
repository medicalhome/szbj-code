package com.founder.cdr.hl7.dto;

public class ImmunityResult extends BaseDto
{
    /**
     * 显示顺序
     */
    private String displayOrder;
    /**
     * 检测项编码
     */
    private String itemCode;
    /**
     * 检测项名称
     */
    private String itemNameCn;
    /**
     * 备注
     */
    private String reportMemo;
    /**
     * 检测结果
     */
    private String qualitativeResults;
    /**
     * 检测结果单位
     */
    private String qualitativeResultsUnit;
    /**
     * 参考信息
     */
    private String references;
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
    public String getReportMemo()
    {
        return reportMemo;
    }
    public String getQualitativeResults()
    {
        return qualitativeResults;
    }
    public String getQualitativeResultsUnit()
    {
        return qualitativeResultsUnit;
    }
    public String getReferences()
    {
        return references;
    }
    
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("ImmunityResult [itemCode=");
        builder.append(itemCode);
        builder.append("]");
        return builder.toString();
    }
    
}
