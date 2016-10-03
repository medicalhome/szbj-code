package com.yly.cdr.hl7.dto;

import org.hibernate.validator.constraints.NotEmpty;


public class MinorDiagnosis extends BaseDto
{
    /**
     * 次诊断号
     */
    private String diagnosisNo;
    /**
     * 诊断类别编码
     */
    private String diagnosisType;
    /**
     * 诊断类别名称
     */
    private String diagnosisTypeName;
    /**
     * 是否待查
     */
    private String uncertainFlag;
    /**
     * 诊断结果-疾病编码
     */
    private String diseaseCode;
    /**
     * 诊断结果-疾病名称
     */
    private String diseaseName;
    /**
     * 诊断时间
     */
    private String diagnosisDate;
    /**
     * 诊断医生编码
     */
    private String diagnosisDoctor;
    /**
     * 诊断医生姓名
     */
    private String diagnosisDoctorName;
    /**
     * 诊断科室编码
     */
    private String diagnosticDept;
    /**
     * 诊断科室名称
     */
    private String diagnosticDeptName;
    
    /**
     * 是否传染病
     */
    private String infectFlag;

    public String getDiagnosisNo()
    {
        return diagnosisNo;
    }

    public String getDiagnosisType()
    {
        return diagnosisType;
    }

    public String getDiagnosisTypeName()
    {
        return diagnosisTypeName;
    }

    public String getUncertainFlag()
    {
        return uncertainFlag;
    }

    public String getDiseaseCode()
    {
        return diseaseCode;
    }

    public String getDiseaseName()
    {
        return diseaseName;
    }

    public String getDiagnosisDate()
    {
        return diagnosisDate;
    }

    public String getDiagnosisDoctor()
    {
        return diagnosisDoctor;
    }

    public String getDiagnosisDoctorName()
    {
        return diagnosisDoctorName;
    }

    public String getDiagnosticDept()
    {
        return diagnosticDept;
    }

    public String getDiagnosticDeptName()
    {
        return diagnosticDeptName;
    }
    
    public String getInfectFlag()
    {
        return infectFlag;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("MinorDiagnosis [diagnosisNo=");
        builder.append(diagnosisNo);
        builder.append(", diagnosisType=");
        builder.append(diagnosisType);
        builder.append(", diagnosisTypeName=");
        builder.append(diagnosisTypeName);
        builder.append(", uncertainFlag=");
        builder.append(uncertainFlag);
        builder.append(", diseaseCode=");
        builder.append(diseaseCode);
        builder.append(", diseaseName=");
        builder.append(diseaseName);
        builder.append(", infectFlag=");
        builder.append(infectFlag);
        builder.append("]");
        return builder.toString();
    }
    
}
