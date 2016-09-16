package com.founder.cdr.dto;

import java.util.Date;

public class VisitDetailDto
{
    // 患都内部序列号
    private Integer patientSn;

    // $Author:bin_zhang
    // $Date : 2012/12/28 
    // $[BUG]0012870 ADD BEGIN
   // 就诊类别
    private String visitTypeCode;
  // $[BUG]0012870 ADD END
    private String visitTypeName;

    // 就诊次数
    private String visitTimes;

    // 入院医生姓名
    private String admissionDoctorName;

    // 住院医生ID
    private String residentDoctorId;

    // 住院医生姓名
    private String residentDoctorName;

    // 入院日期
    private Date visitDate;

    // 入院科室
    private String visitDeptName;

    // 入院病房Id
    private String admissionWardId;

    // 入院床号
    private String admissionBedNo;

    // 主治医生姓名
    private String attendingDoctorName;

    // 科室主任姓名
    private String deptDirectorName;

    // 出院日期
    private Date dischargeDate;

    // 出院科室
    private String dischargeDeptName;

    // 出院病房ID
    private String dischargeWardId;

    // 出院状态
    private String dischargeStatusName;

    // 就诊状态名称
    private String visitStatusName;

    // 入院病房
    private String admissionWardName;

    // 出院病房
    private String dischargeWardName;

    // 就诊状态编码
    private String visitStatusCode;

    // $Author :bin_zhang
    // $Date : 2012/9/04 17:23$
    // [BUG]0009334 ADD BEGIN
    // 就诊号别
    private String registrationClassName;

    // [BUG]0009334 ADD END

    // 紧急程度名称
    private String urgentLevelName;

    // $Author:bin_zhang
    // $Date:2012/9/28 10:42
    // $[BUG]BUG0010082 ADD BEGIN
    private String visitDoctorName;

    private String visitSn;

    // $[BUG]BUG0010082 ADD END

    private String chargeClassName;
    
    private String dischargeBedNo;
    // $Author :tong_meng
    // $Date : 2013/12/23 10:50$
    // [BUG]0041102 ADD BEGIN
    private String orgCode;
    private String orgName;
    // [BUG]0041102 ADD END

    public String getChargeClassName()
    {
        return chargeClassName;
    }

    public void setChargeClassName(String chargeClassName)
    {
        this.chargeClassName = chargeClassName;
    }

    public String getVisitStatusCode()
    {
        return visitStatusCode;
    }

    public void setVisitStatusCode(String visitStatusCode)
    {
        this.visitStatusCode = visitStatusCode;
    }

    public Integer getPatientSn()
    {
        return patientSn;
    }

    public void setPatientSn(Integer patientSn)
    {
        this.patientSn = patientSn;
    }

    public String getVisitTimes()
    {
        return visitTimes;
    }

    public void setVisitTimes(String visitTimes)
    {
        this.visitTimes = visitTimes;
    }

    public String getAdmissionDoctorName()
    {
        return admissionDoctorName;
    }

    public void setAdmissionDoctorName(String admissionDoctorName)
    {
        this.admissionDoctorName = admissionDoctorName;
    }

    public String getResidentDoctorId()
    {
        return residentDoctorId;
    }

    public void setResidentDoctorId(String residentDoctorId)
    {
        this.residentDoctorId = residentDoctorId;
    }

    public String getResidentDoctorName()
    {
        return residentDoctorName;
    }

    public void setResidentDoctorName(String residentDoctorName)
    {
        this.residentDoctorName = residentDoctorName;
    }

    public Date getVisitDate()
    {
        return visitDate;
    }

    public void setVisitDate(Date visitDate)
    {
        this.visitDate = visitDate;
    }

    public String getAdmissionWardId()
    {
        return admissionWardId;
    }

    public void setAdmissionWardId(String admissionWardId)
    {
        this.admissionWardId = admissionWardId;
    }

    public String getAdmissionBedNo()
    {
        return admissionBedNo;
    }

    public void setAdmissionBedNo(String admissionBedNo)
    {
        this.admissionBedNo = admissionBedNo;
    }

    public String getAttendingDoctorName()
    {
        return attendingDoctorName;
    }

    public void setAttendingDoctorName(String attendingDoctorName)
    {
        this.attendingDoctorName = attendingDoctorName;
    }

    public String getDeptDirectorName()
    {
        return deptDirectorName;
    }

    public void setDeptDirectorName(String deptDirectorName)
    {
        this.deptDirectorName = deptDirectorName;
    }

    public Date getDischargeDate()
    {
        return dischargeDate;
    }

    public void setDischargeDate(Date dischargeDate)
    {
        this.dischargeDate = dischargeDate;
    }

    public String getDischargeWardId()
    {
        return dischargeWardId;
    }

    public void setDischargeWardId(String dischargeWardId)
    {
        this.dischargeWardId = dischargeWardId;
    }

    public String getVisitTypeName()
    {
        return visitTypeName;
    }

    public void setVisitTypeName(String visitTypeName)
    {
        this.visitTypeName = visitTypeName;
    }

    public String getVisitDeptName()
    {
        return visitDeptName;
    }

    public void setVisitDeptName(String visitDeptName)
    {
        this.visitDeptName = visitDeptName;
    }

    public String getDischargeDeptName()
    {
        return dischargeDeptName;
    }

    public void setDischargeDeptName(String dischargeDeptName)
    {
        this.dischargeDeptName = dischargeDeptName;
    }

    public String getDischargeStatusName()
    {
        return dischargeStatusName;
    }

    public void setDischargeStatusName(String dischargeStatusName)
    {
        this.dischargeStatusName = dischargeStatusName;
    }

    public String getVisitStatusName()
    {
        return visitStatusName;
    }

    public void setVisitStatusName(String visitStatusName)
    {
        this.visitStatusName = visitStatusName;
    }

    public String getAdmissionWardName()
    {
        return admissionWardName;
    }

    public void setAdmissionWardName(String admissionWardName)
    {
        this.admissionWardName = admissionWardName;
    }

    public String getDischargeWardName()
    {
        return dischargeWardName;
    }

    public void setDischargeWardName(String dischargeWardName)
    {
        this.dischargeWardName = dischargeWardName;
    }

    // $Author :bin_zhang
    // $Date : 2012/9/04 17:23$
    // [BUG]0009334 ADD BEGIN
    public String getRegistrationClassName()
    {
        return registrationClassName;
    }

    public void setRegistrationClassName(String registrationClassName)
    {
        this.registrationClassName = registrationClassName;
    }

    // [BUG]0009334 ADD END
    public String getUrgentLevelName()
    {
        return urgentLevelName;
    }

    public void setUrgentLevelName(String urgentLevelName)
    {
        this.urgentLevelName = urgentLevelName;
    }

    // $Author:bin_zhang
    // $Date:2012/9/28 10:42
    // $[BUG]BUG0010082 ADD BEGIN
    public String getVisitDoctorName()
    {
        return visitDoctorName;
    }

    public void setVisitDoctorName(String visitDoctorName)
    {
        this.visitDoctorName = visitDoctorName;
    }

    public String getVisitSn()
    {
        return visitSn;
    }

    public void setVisitSn(String visitSn)
    {
        this.visitSn = visitSn;
    }
    // $Author:bin_zhang
    // $Date : 2012/12/28 
    // $[BUG]0012870 ADD BEGIN
    public String getVisitTypeCode()
    {
        return visitTypeCode;
    }

    public void setVisitTypeCode(String visitTypeCode)
    {
        this.visitTypeCode = visitTypeCode;
    }
    // $[BUG]0012870 ADD END


    // $[BUG]BUG0010082 ADD END

    public String getDischargeBedNo()
    {
        return dischargeBedNo;
    }
    
    public void setDischargeBedNo(String dischargeBedNo)
    {
        this.dischargeBedNo = dischargeBedNo;
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
