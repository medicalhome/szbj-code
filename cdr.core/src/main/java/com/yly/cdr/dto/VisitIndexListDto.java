package com.yly.cdr.dto;

import java.math.BigDecimal;
import java.util.Date;

public class VisitIndexListDto
{

    // 就诊内部序列号
    private String visitSn;

    // 就诊类别
    private String visitTypeName;

    // 就诊类别代码
    private String visitTypeCode;

    // 就诊次数
    private BigDecimal visitTimes;

    // 就诊医生
    private String visitDoctorName;

    // 就诊(入院)日期
    private Date visitDate;

    // 入院(就诊)科室代码
    private String visitDeptId;

    // 入院(就诊)科室名称
    private String visitDeptName;

    // 就诊状态
    private String visitStatusName;

    private String registrationClassName;

    // 主要诊断信息
    private String mainDiagnosis;

    // 出院时间
    private Date dischargeDate;

    // 就诊状态代码
    private String visitStatusCode;
    
    // $Author:tong_meng 
    // $Date : 2013/11/07 10:10
    // $[BUG]0039034 ADD BEGIN
    // 患者域ID
    private String patientDomain;
    
    // 住院患者病区
    private String admissionWardName;
    
    //住院患者床号
    private String admissionBedNo;
    
    // 出院病区
    private String dischargeWardName;

    // 出院床号
    private String dischargeBedNo;
    // $[BUG]0039034 ADD END
    
    // $Author:tong_meng 
    // $Date : 2013/11/19 13:10
    // $[BUG]0039622 ADD BEGIN
    // 出院科室
    private String dischargeDeptName;
    // $[BUG]0039622 ADD END
    
    // 医疗机构编码
    private String orgCode;

    public String getVisitSn()
    {
        return visitSn;
    }

    public void setVisitSn(String visitSn)
    {
        this.visitSn = visitSn;
    }

    public String getVisitTypeName()
    {
        return visitTypeName;
    }

    public void setVisitTypeName(String visitTypeName)
    {
        this.visitTypeName = visitTypeName;
    }

    public BigDecimal getVisitTimes()
    {
        return visitTimes;
    }

    public void setVisitTimes(BigDecimal visitTimes)
    {
        this.visitTimes = visitTimes;
    }

    public String getVisitDoctorName()
    {
        return visitDoctorName;
    }

    public void setVisitDoctorName(String visitDoctorName)
    {
        this.visitDoctorName = visitDoctorName;
    }

    public Date getVisitDate()
    {
        return visitDate;
    }

    public void setVisitDate(Date visitDate)
    {
        this.visitDate = visitDate;
    }

    public String getVisitDeptId()
    {
        return visitDeptId;
    }

    public void setVisitDeptId(String visitDeptId)
    {
        this.visitDeptId = visitDeptId;
    }

    public String getVisitDeptName()
    {
        return visitDeptName;
    }

    public void setVisitDeptName(String visitDeptName)
    {
        this.visitDeptName = visitDeptName;
    }

    public String getVisitStatusName()
    {
        return visitStatusName;
    }

    public void setVisitStatusName(String visitStatusName)
    {
        this.visitStatusName = visitStatusName;
    }

    public String getRegistrationClassName()
    {
        return registrationClassName;
    }

    public void setRegistrationClassName(String registrationClassName)
    {
        this.registrationClassName = registrationClassName;
    }

    public String getMainDiagnosis()
    {
        return mainDiagnosis;
    }

    public void setMainDiagnosis(String mainDiagnosis)
    {
        this.mainDiagnosis = mainDiagnosis;
    }

    public String getVisitTypeCode()
    {
        return visitTypeCode;
    }

    public void setVisitTypeCode(String visitTypeCode)
    {
        this.visitTypeCode = visitTypeCode;
    }

    public Date getDischargeDate()
    {
        return dischargeDate;
    }

    public void setDischargeDate(Date dischargeDate)
    {
        this.dischargeDate = dischargeDate;
    }

    public String getVisitStatusCode()
    {
        return visitStatusCode;
    }

    public void setVisitStatusCode(String visitStatusCode)
    {
        this.visitStatusCode = visitStatusCode;
    }

    public String getPatientDomain()
    {
        return patientDomain;
    }

    public void setPatientDomain(String patientDomain)
    {
        this.patientDomain = patientDomain;
    }

    public String getAdmissionWardName()
    {
        return admissionWardName;
    }

    public void setAdmissionWardName(String admissionWardName)
    {
        this.admissionWardName = admissionWardName;
    }

    public String getAdmissionBedNo()
    {
        return admissionBedNo;
    }

    public void setAdmissionBedNo(String admissionBedNo)
    {
        this.admissionBedNo = admissionBedNo;
    }

    public String getDischargeWardName()
    {
        return dischargeWardName;
    }

    public void setDischargeWardName(String dischargeWardName)
    {
        this.dischargeWardName = dischargeWardName;
    }

    public String getDischargeBedNo()
    {
        return dischargeBedNo;
    }

    public void setDischargeBedNo(String dischargeBedNo)
    {
        this.dischargeBedNo = dischargeBedNo;
    }

    public String getDischargeDeptName()
    {
        return dischargeDeptName;
    }

    public void setDischargeDeptName(String dischargeDeptName)
    {
        this.dischargeDeptName = dischargeDeptName;
    }

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

}
