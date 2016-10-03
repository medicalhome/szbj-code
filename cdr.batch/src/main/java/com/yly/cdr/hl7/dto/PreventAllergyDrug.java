package com.yly.cdr.hl7.dto;


public class PreventAllergyDrug extends BaseDto
{
    /**
     * 显示序号
     */
    private String sequenceNo;
    /**
     * 抗菌药物编码
     */
    private String drugCode;
    /**
     * 抗菌药物名称
     */
    private String drugNameCn;
    /**
     * 抗菌药物英文名称
     */
    private String drugNameEn;
    /**
     * 折点
     */
    private String kickpoint;
    /**
     * KB
     */
    private String kb;
    /**
     * MIC
     */
    private String mic;
    /**
     * 敏感度
     */
    private String sensitivity;

    public String getSequenceNo()
    {
        return sequenceNo;
    }

    public String getDrugCode()
    {
        return drugCode;
    }

    public String getDrugNameCn()
    {
        return drugNameCn;
    }

    public String getDrugNameEn()
    {
        return drugNameEn;
    }

    public String getKickpoint()
    {
        return kickpoint;
    }

    public String getKb()
    {
        return kb;
    }

    public String getMic()
    {
        return mic;
    }

    public String getSensitivity()
    {
        return sensitivity;
    }
}
