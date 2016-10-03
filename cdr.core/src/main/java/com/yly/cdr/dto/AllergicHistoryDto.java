package com.yly.cdr.dto;

import java.util.Date;

/**
 *
 *[FUN]过敏/不良反应列表
 *@version 1.0, 2012/03/12
 *@author 黄洁玉
 *@since 1.0
 *
 */
public class AllergicHistoryDto
{
    // 过敏不良反应内部序列号
    private Integer allergicSn;

    // 患者序列号
    private String patientSn;

    // 过敏症状代码
    private String symptomCode;

    // 过敏症状
    private String allergicSymptom;

    // 过敏源代码
    private String allergenCode;

    // 过敏源名称
    private String allergicSource;

    // 过敏病情状况代码
    private String allergicCondition;

    // 过敏严重性代码
    private String seriousness;

    // 过敏开始时间
    private Date allergicStartDate;

    // 过敏症状停止时间
    private Date allergicStopDate;

    private String allergicConditionName;

    private String seriousnessName;

    private String allergicTypeName;

    public Integer getAllergicSn()
    {
        return allergicSn;
    }

    public void setAllergicSn(Integer allergicSn)
    {
        this.allergicSn = allergicSn;
    }

    public String getPatientSn()
    {
        return patientSn;
    }

    public void setPatientSn(String patientSn)
    {
        this.patientSn = patientSn;
    }

    public String getAllergicSymptom()
    {
        return allergicSymptom;
    }

    public void setAllergicSymptom(String allergicSymptom)
    {
        this.allergicSymptom = allergicSymptom;
    }

    public String getSymptomCode()
    {
        return symptomCode;
    }

    public void setSymptomCode(String symptomCode)
    {
        this.symptomCode = symptomCode;
    }

    public String getAllergenCode()
    {
        return allergenCode;
    }

    public void setAllergenCode(String allergenCode)
    {
        this.allergenCode = allergenCode;
    }

    public String getAllergicSource()
    {
        return allergicSource;
    }

    public void setAllergicSource(String allergicSource)
    {
        this.allergicSource = allergicSource;
    }

    public String getAllergicCondition()
    {
        return allergicCondition;
    }

    public void setAllergicCondition(String allergicCondition)
    {
        this.allergicCondition = allergicCondition;
    }

    public String getSeriousness()
    {
        return seriousness;
    }

    public void setSeriousness(String seriousness)
    {
        this.seriousness = seriousness;
    }

    public Date getAllergicStartDate()
    {
        return allergicStartDate;
    }

    public void setAllergicStartDate(Date allergicStartDate)
    {
        this.allergicStartDate = allergicStartDate;
    }

    public Date getAllergicStopDate()
    {
        return allergicStopDate;
    }

    public void setAllergicStopDate(Date allergicStopDate)
    {
        this.allergicStopDate = allergicStopDate;
    }

    public String getAllergicConditionName()
    {
        return allergicConditionName;
    }

    public void setAllergicConditionName(String allergicConditionName)
    {
        this.allergicConditionName = allergicConditionName;
    }

    public String getSeriousnessName()
    {
        return seriousnessName;
    }

    public void setSeriousnessName(String seriousnessName)
    {
        this.seriousnessName = seriousnessName;
    }

    public String getAllergicTypeName()
    {
        return allergicTypeName;
    }

    public void setAllergicTypeName(String allergicTypeName)
    {
        this.allergicTypeName = allergicTypeName;
    }

}
