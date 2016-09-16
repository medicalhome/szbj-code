package com.founder.cdr.dto.visit;

import java.util.List;

public class VisitIndexListSearchPra
{
    // 就诊日期
    private String visitDateFromStr;

    private String visitDateToStr;

    // 就诊类别
    private String visitTypeCode;

    // $Author:wu_jianfeng
    // $Date : 2012/11/26 14:10
    // $[BUG]0011511 MODIFY BEGIN
    // 入院(就诊)科室代码
    private List<String> visitDepts;
    // $[BUG]0011511 MODIFY END

    // 入院出院日期
    private String dischargeDateFromStr;

    private String dischargeDateToStr;

    // 就诊医生
    private String visitDoctorName;

    // 就诊号别
    private String registrationClassCode;

    private List visitTypeInpatientCode;
    // $Author:bin_zhang
    // $Date : 2012/10/17 14:10
    // $[BUG]0010403 ADD BEGIN
    // 当前日期
    private String sysDate;
    // $[BUG]0010403 ADD END
    
    // 医疗机构编码
    private String orgCode;
    
    // 主诊断疾病名称
    private String diagnosisName;
    
    public List getVisitTypeInpatientCode()
    {
        return visitTypeInpatientCode;
    }

    public void setVisitTypeInpatientCode(List visitTypeInpatientCode)
    {
        this.visitTypeInpatientCode = visitTypeInpatientCode;
    }

    public String getVisitDateFromStr()
    {
        return visitDateFromStr;
    }

    public void setVisitDateFromStr(String visitDateFromStr)
    {
        this.visitDateFromStr = visitDateFromStr;
    }

    public String getVisitDateToStr()
    {
        return visitDateToStr;
    }

    public void setVisitDateToStr(String visitDateToStr)
    {
        this.visitDateToStr = visitDateToStr;
    }

    

    public String getDischargeDateFromStr()
    {
        return dischargeDateFromStr;
    }

    public void setDischargeDateFromStr(String dischargeDateFromStr)
    {
        this.dischargeDateFromStr = dischargeDateFromStr;
    }

    public String getDischargeDateToStr()
    {
        return dischargeDateToStr;
    }

    public void setDischargeDateToStr(String dischargeDateToStr)
    {
        this.dischargeDateToStr = dischargeDateToStr;
    }

    public String getVisitDoctorName()
    {
        return visitDoctorName;
    }

    public void setVisitDoctorName(String visitDoctorName)
    {
        this.visitDoctorName = visitDoctorName;
    }

    public String getVisitTypeCode()
    {
        return visitTypeCode;
    }

    public void setVisitTypeCode(String visitTypeCode)
    {
        this.visitTypeCode = visitTypeCode;
    }

    public String getRegistrationClassCode()
    {
        return registrationClassCode;
    }

    public void setRegistrationClassCode(String registrationClassCode)
    {
        this.registrationClassCode = registrationClassCode;
    }

    public String getSysDate()
    {
        return sysDate;
    }

    public void setSysDate(String sysDate)
    {
        this.sysDate = sysDate;
    }

    // $Author:wu_jianfeng
    // $Date : 2012/11/26 14:10
    // $[BUG]0011511 ADD BEGIN
    public List<String> getVisitDepts()
    {
        return visitDepts;
    }

    public void setVisitDepts(List<String> visitDepts)
    {
        this.visitDepts = visitDepts;
    }
    // $[BUG]0011511 ADD END

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getDiagnosisName() {
		return diagnosisName;
	}

	public void setDiagnosisName(String diagnosisName) {
		this.diagnosisName = diagnosisName;
	}

}
