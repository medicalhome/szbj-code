package com.yly.cdr.hl7.dto;

import org.hibernate.validator.constraints.NotEmpty;


public class PhysicalExaminationDto extends BaseDto
{
    /**
     * 项目类型编码
     */
    @NotEmpty(message = "{PhysicalExaminationDto.itemTypeCode}")
    private String itemTypeCode;
    /**
     * 项目类型名称
     */
    @NotEmpty(message = "{PhysicalExaminationDto.itemTypeName}")
    private String itemTypeName;
    /**
     * 测量时间
     */
    private String physicalTime;
    /**
     * 测量单位
     */
    private String itemResultUnit;
    /**
     * 测量结果
     */
    private String itemResult;
    /**
     * 测量部位编码
     */
    private String measureSite;
    /**
     * 测量部位名称
     */
    private String measureSiteName;
    /**
     * 舒张压
     */
    private String diastolicPressure;
    /**
     * 舒张压单位
     */
    private String diastolicPressureUnit;
    /**
     * 收缩压
     */
    private String systolicPressure;
    /**
     * 收缩压单位
     */
    private String systolicPressureUnit;

    public String getItemTypeCode()
    {
        return itemTypeCode;
    }

    public String getItemTypeName()
    {
        return itemTypeName;
    }

    public String getPhysicalTime()
    {
        return physicalTime;
    }

    public String getItemResultUnit()
    {
        return itemResultUnit;
    }

    public String getItemResult()
    {
        return itemResult;
    }

    public String getMeasureSite()
    {
        return measureSite;
    }

    public String getMeasureSiteName()
    {
        return measureSiteName;
    }

    public String getDiastolicPressure()
    {
        return diastolicPressure;
    }

    public String getDiastolicPressureUnit()
    {
        return diastolicPressureUnit;
    }

    public String getSystolicPressure()
    {
        return systolicPressure;
    }

    public String getSystolicPressureUnit()
    {
        return systolicPressureUnit;
    }
}
