package com.founder.cdr.dto.diagnosis;

import com.founder.cdr.dto.CommonParameters;

/**
 * 
 * [FUN]V05-101就诊列表
 * @version 1.0, 2012/05/12  
 * @author 张彬
 * @since 1.0
 * 
 */
public class DiagnosisListSearchPra extends CommonParameters
{
    // 诊断类型
    private String diagnosisTypeCode;

    // 诊断类型名
    private String diagnosisTypeName;

    // 诊断名称
    private String diseaseName;

    // 诊断科室
    private String diagnosticDeptName;

    private String diagnosticDeptId;

    // 诊断医生
    private String diagnosisDoctorName;

    // 诊断日期
    private String diagnosisDateFromStr;

    private String diagnosisDateToStr;

    // 是否主要诊断
    private String mainDiagnosisFlag;

    // 是否待查
    private String uncertainFlag;

    // 是否传染
    private String contagiousFlag;
    // $Author :tong_meng
    // $Date : 2013/12/23 10:50$
    // [BUG]0041102 ADD BEGIN
    // 院区code
    private String orgCode;
    // 院区name
    private String orgName;
    // [BUG]0041102 ADD END
    public String getDiagnosisTypeCode()
    {
        return diagnosisTypeCode;
    }

    public void setDiagnosisTypeCode(String diagnosisTypeCode)
    {
        this.diagnosisTypeCode = diagnosisTypeCode;
    }

    public String getDiagnosisTypeName()
    {
        return diagnosisTypeName;
    }

    public void setDiagnosisTypeName(String diagnosisTypeName)
    {
        this.diagnosisTypeName = diagnosisTypeName;
    }

    public String getDiseaseName()
    {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName)
    {
        this.diseaseName = diseaseName;
    }

    public String getDiagnosticDeptName()
    {
        return diagnosticDeptName;
    }

    public void setDiagnosticDeptName(String diagnosticDeptName)
    {
        this.diagnosticDeptName = diagnosticDeptName;
    }

    public String getDiagnosticDeptId()
    {
        return diagnosticDeptId;
    }

    public void setDiagnosticDeptId(String diagnosticDeptId)
    {
        this.diagnosticDeptId = diagnosticDeptId;
    }

    public String getDiagnosisDoctorName()
    {
        return diagnosisDoctorName;
    }

    public void setDiagnosisDoctorName(String diagnosisDoctorName)
    {
        this.diagnosisDoctorName = diagnosisDoctorName;
    }

    public String getDiagnosisDateFromStr()
    {
        return diagnosisDateFromStr;
    }

    public void setDiagnosisDateFromStr(String diagnosisDateFromStr)
    {
        this.diagnosisDateFromStr = diagnosisDateFromStr;
    }

    public String getDiagnosisDateToStr()
    {
        return diagnosisDateToStr;
    }

    public void setDiagnosisDateToStr(String diagnosisDateToStr)
    {
        this.diagnosisDateToStr = diagnosisDateToStr;
    }

    public String getUncertainFlag()
    {
        return uncertainFlag;
    }

    public void setUncertainFlag(String uncertainFlag)
    {
        this.uncertainFlag = uncertainFlag;
    }

    public String getContagiousFlag()
    {
        return contagiousFlag;
    }

    public void setContagiousFlag(String contagiousFlag)
    {
        this.contagiousFlag = contagiousFlag;
    }

    public String getMainDiagnosisFlag()
    {
        return mainDiagnosisFlag;
    }

    public void setMainDiagnosisFlag(String mainDiagnosisFlag)
    {
        this.mainDiagnosisFlag = mainDiagnosisFlag;
    }

    public String getOrgCode()
    {
        return orgCode;
    }

    public void setOrgCode(String orgCode)
    {
        this.orgCode = orgCode;
    }

    public String getOrgName()
    {
        return orgName;
    }

    public void setOrgName(String orgName)
    {
        this.orgName = orgName;
    }

}
