/**
 * [FUN]A01-029 过敏和生理状态信息
 * 
 * @version 1.0, 2012/04/09  20:23:00
 
 * @author wu_jianfeng
 * @since 1.0
*/

package com.founder.cdr.hl7.dto.poobin000001uv01;

import com.founder.cdr.hl7.dto.BaseDto;

public class AllergicHistoryDto extends BaseDto
{
    // $Author :tong_meng
    // $Date : 2012/9/26 9:30$
    // [BUG]0009965 ADD BEGIN
    /**
     * 过敏诊断编码
     */
    private String allergenDiagnosticsCode;

    /**
     * 过敏诊断名称
     */
    private String allergenDiagnosticsName;

    // [BUG]0009965 ADD END
    /**
     * 过敏症状
     */
    private String allergicSymptom;

    /**
     * 过敏症状代码
     */
    private String symptomCode;

    /**
     * 过敏源代码
     */
    private String allergenCode;

    /**
     * 过敏源名称
     */
    private String allergicSource;

    /**
     * 过敏病情状态代码
     */
    private String allergicCondition;

    /**
     * 过敏严重性代码
     */
    private String seriousness;

    /**
     * 过敏严重性名称
     */
    private String seriousnessName;

    /**
     * 过敏开始日期
     */
    private String allergicStartDate;

    /**
     * 过敏症状停止日期
     */
    private String allergicStopDate;

    /**
     * 过敏类型
     */
    private String allergicType;

    /**
     * 过敏类型名称
     */
    private String allergicTypeName;

    /**
     * 是否家族史
     */
    private String familyHistoryFlag;

    /**
     * 备注
     */
    private String memo;

    public String getAllergenDiagnosticsCode()
    {
        return allergenDiagnosticsCode;
    }

    public String getAllergenDiagnosticsName()
    {
        return allergenDiagnosticsName;
    }

    public String getAllergicSymptom()
    {
        return allergicSymptom;
    }

    public String getSymptomCode()
    {
        return symptomCode;
    }

    public String getAllergenCode()
    {
        return allergenCode;
    }

    public String getAllergicSource()
    {
        return allergicSource;
    }

    public String getSeriousnessName()
    {
        return seriousnessName;
    }

    public String getAllergicCondition()
    {
        return allergicCondition;
    }

    public String getSeriousness()
    {
        return seriousness;
    }

    public String getAllergicStartDate()
    {
        return allergicStartDate;
    }

    public String getAllergicStopDate()
    {
        return allergicStopDate;
    }

    public String getAllergicType()
    {
        return allergicType;
    }

    public String getAllergicTypeName()
    {
        return allergicTypeName;
    }

    public String getFamilyHistoryFlag()
    {
        return familyHistoryFlag;
    }

    public String getMemo()
    {
        return memo;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("AllergicHistoryDto [allergenDiagnosticsCode=");
        builder.append(allergenDiagnosticsCode);
        builder.append("]");
        return builder.toString();
    }
    
    

}
