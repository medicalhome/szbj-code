package com.founder.cdr.hl7.dto;

import org.hibernate.validator.constraints.NotEmpty;

public class MainDiagnosis extends BaseDto
{
    /**
     * 主诊断号
     */
    private String diagnosisNo;
    /**
     * 诊断类别编码
     */
//    @NotEmpty(message = "{MainDiagnosis.diagnosisType}")
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
    // $Author :chang_xuewen
    // $Date : 2013/8/29 14:00
    // $[BUG]0036738 DELETE BEGIN
    /*@NotEmpty(message = "{MainDiagnosis.diseaseCode}")*/
    // $[BUG]0036738 DELETE END
    private String diseaseCode;
    /**
     * 诊断结果-病症名称
     */
//    @NotEmpty(message = "{MainDiagnosis.diseaseName}")
    private String diseaseName;
    /**
     * 诊断时间
     */
//    @NotEmpty(message = "{MainDiagnosis.diagnosisDate}")
    private String diagnosisDate;
    /**
     * 诊断医生编码
     */
//    @NotEmpty(message = "{MainDiagnosis.diagnosisDoctor}")
    private String diagnosisDoctor;
    /**
     * 诊断医生名称
     */
//    @NotEmpty(message = "{MainDiagnosis.diagnosisDoctorName}")
    private String diagnosisDoctorName;
    /**
     * 诊断科室编码
     */
//    @NotEmpty(message = "{MainDiagnosis.diagnosticDept}")
    private String diagnosticDept;
    /**
     * 诊断科室名称
     */
//    @NotEmpty(message = "{MainDiagnosis.diagnosticDeptName}")
    private String diagnosticDeptName;
    /**
     * 是否传染病
     */
    private String infectFlag;
    // $Author :chang_xuewen
    // $Date : 2013/8/1 17:00
    // $[BUG]0035553 ADD BEGIN
    /**
     * 是否主诊断
     */
//    @NotEmpty(message = "{MainDiagnosis.isMainDiagnosis}")
    private String isMainDiagnosis;   

	/**
     * 次诊断
     */
    /*private List<MinorDiagnosis> minorDiagnosis;*/
    
	/**
     * 诊断序号
     */
    private String serialnumber;
    
    public String getIsMainDiagnosis() {
		return isMainDiagnosis;
	}
    // $[BUG]0035553 ADD END
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

    /*public List<MinorDiagnosis> getMinorDiagnosis()
    {
        return minorDiagnosis;
    }*/

    public String getSerialnumber(){
    	return serialnumber;
    }
    
    
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("MainDiagnosis [diagnosisNo=");
        builder.append(diagnosisNo);
        builder.append(", uncertainFlag=");
        builder.append(uncertainFlag);
        builder.append(", diagnosisType=");
        builder.append(diagnosisType);
        builder.append(", diagnosisTypeName=");
        builder.append(diagnosisTypeName);
        builder.append(", diseaseCode=");
        builder.append(diseaseCode);
        builder.append(", diseaseName=");
        builder.append(diseaseName);
        builder.append(", infectFlag=");
        builder.append(infectFlag);
        builder.append(", isMainDiagnosis=");
        builder.append(isMainDiagnosis);
//        builder.append(", minorDiagnosis=");
//        builder.append(minorDiagnosis);
        builder.append(", serialnumber=");
        builder.append(serialnumber);
        builder.append("]");
        return builder.toString();
    }    
    
}
