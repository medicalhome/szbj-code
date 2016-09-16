/**
 * [FUN]V03-001患者列表
 * 
 * @version 1.0, 2012/03/12  20:23:00
 
 * @author wu_jianfeng
 * @since 1.0
*/
package com.founder.cdr.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class PatientDto
{
    private String patientSn;

    private String patientEid;

    private String patientName;

    private String genderCode;

    private Date birthDate;

    private String marriageStatus;

    private String nationCode;

    private String birthPlace;

    private String occupationCode;

    private String educationDegree;

    private BigDecimal visitSn;

    private String patientLid;

    private String visitDeptId;

    private String visitTypeCode;

    private Date visitDate;

    private Date dischargeDate;

    // $Author:tong_meng
    // $Date : 2012/10/08 14:10
    // $[BUG]0039034 ADD BEGIN
    // 出院病区
    private String dischargeWardName;

    // 出院床号
    private String dischargeBedNo;
    // $[BUG]0039034 ADD END
    
    private String admissionBedNo;

    private String age;

    private String genderName;

    private String marriageStatusName;

    private String nationName;

    private String nationalityName;

    private String occupationName;

    private String educationDegreeName;

    private String bloodAboName;

    private String bloodRhName;

    // $Author:tong_meng
    // $Date : 2012/10/08 14:10
    // $[BUG]0010038 ADD BEGIN
    private String admissionWardName;

    private String visitDeptName;
    
    // $[BUG]0010038 ADD END

    // $Author:tong_meng
    // $Date : 2012/10/08 14:10
    // $[BUG]0039622 ADD BEGIN
    // 出院科室
    private String dischargeDeptName;
    
    // 患者域ID
    private String patientDomain;
    // $[BUG]0039622 ADD END
    
    private String outpatientNo;

    private String inpatientNo;
    // $Author:chang_xuewen
    // $Date : 2013/12/17 14:10
    // $[BUG]0040770 ADD BEGIN
    private String orgCode;
    // $[BUG]0040770 ADD END
    
    // $Author:du_xiaolan
    // $Date : 2015/03/18
    // [记录一个人多张卡的门诊号拼成JSON]ADD BEGIN
    private String outpatientNoJson;
    // ADD END
    
 // $Author:du_xiaolan
    // $Date : 2015/03/18
    // [记录一个人多张卡的门诊号拼成JSON]ADD BEGIN
    private String inpatientNoJson;
    // ADD END
    
    // $Author:du_xiaolan
    // $Date : 2015/03/18
    // [记录一个人是否有多张卡]ADD BEGIN
    private String cardFlag;
    // ADD END

    public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOutpatientNo()
    {
        return outpatientNo;
    }

    public void setOutpatientNo(String outpatientNo)
    {
        this.outpatientNo = outpatientNo;
    }

    public String getInpatientNo()
    {
        return inpatientNo;
    }

    public void setInpatientNo(String inpatientNo)
    {
        this.inpatientNo = inpatientNo;
    }

    public String getPatientSn()
    {
        return patientSn;
    }

    public void setPatientSn(String patientSn)
    {
        this.patientSn = patientSn;
    }

    public String getPatientEid()
    {
        return patientEid;
    }

    public void setPatientEid(String patientEid)
    {
        this.patientEid = patientEid;
    }

    public String getPatientName()
    {
        return patientName;
    }

    public void setPatientName(String patientName)
    {
        this.patientName = patientName;
    }

    public String getGenderCode()
    {
        return genderCode;
    }

    public void setGenderCode(String genderCode)
    {
        this.genderCode = genderCode;
    }

    public Date getBirthDate()
    {
        return birthDate;
    }

    public void setBirthDate(Date birthDate)
    {
        this.birthDate = birthDate;
    }

    public String getMarriageStatus()
    {
        return marriageStatus;
    }

    public void setMarriageStatus(String marriageStatus)
    {
        this.marriageStatus = marriageStatus;
    }

    public String getNationCode()
    {
        return nationCode;
    }

    public void setNationCode(String nationCode)
    {
        this.nationCode = nationCode;
    }

    public String getBirthPlace()
    {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace)
    {
        this.birthPlace = birthPlace;
    }

    public String getOccupationCode()
    {
        return occupationCode;
    }

    public void setOccupationCode(String occupationCode)
    {
        this.occupationCode = occupationCode;
    }

    public String getEducationDegree()
    {
        return educationDegree;
    }

    public void setEducationDegree(String educationDegree)
    {
        this.educationDegree = educationDegree;
    }

    public BigDecimal getVisitSn()
    {
        return visitSn;
    }

    public void setVisitSn(BigDecimal visitSn)
    {
        this.visitSn = visitSn;
    }

    public String getPatientLid()
    {
        return patientLid;
    }

    public void setPatientLid(String patientLid)
    {
        this.patientLid = patientLid;
    }

    public String getVisitDeptId()
    {
        return visitDeptId;
    }

    public void setVisitDeptId(String visitDeptId)
    {
        this.visitDeptId = visitDeptId;
    }

    public String getVisitTypeCode()
    {
        return visitTypeCode;
    }

    public void setVisitTypeCode(String visitTypeCode)
    {
        this.visitTypeCode = visitTypeCode;
    }

    public Date getVisitDate()
    {
        return visitDate;
    }

    public void setVisitDate(Date visitDate)
    {
        this.visitDate = visitDate;
    }

    public Date getDischargeDate()
    {
        return dischargeDate;
    }

    public void setDischargeDate(Date dischargeDate)
    {
        this.dischargeDate = dischargeDate;
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

    public String getAdmissionBedNo()
    {
        return admissionBedNo;
    }

    public void setAdmissionBedNo(String admissionBedNo)
    {
        this.admissionBedNo = admissionBedNo;
    }

    public String getAge()
    {
        return age;
    }

    public void setAge(String age)
    {
        this.age = age;
    }

    public String getGenderName()
    {
        return genderName;
    }

    public void setGenderName(String genderName)
    {
        this.genderName = genderName;
    }

    public String getMarriageStatusName()
    {
        return marriageStatusName;
    }

    public void setMarriageStatusName(String marriageStatusName)
    {
        this.marriageStatusName = marriageStatusName;
    }

    public String getNationName()
    {
        return nationName;
    }

    public void setNationName(String nationName)
    {
        this.nationName = nationName;
    }

    public String getNationalityName()
    {
        return nationalityName;
    }

    public void setNationalityName(String nationalityName)
    {
        this.nationalityName = nationalityName;
    }

    public String getOccupationName()
    {
        return occupationName;
    }

    public void setOccupationName(String occupationName)
    {
        this.occupationName = occupationName;
    }

    public String getEducationDegreeName()
    {
        return educationDegreeName;
    }

    public void setEducationDegreeName(String educationDegreeName)
    {
        this.educationDegreeName = educationDegreeName;
    }

    public String getBloodAboName()
    {
        return bloodAboName;
    }

    public void setBloodAboName(String bloodAboName)
    {
        this.bloodAboName = bloodAboName;
    }

    public String getBloodRhName()
    {
        return bloodRhName;
    }

    public void setBloodRhName(String bloodRhName)
    {
        this.bloodRhName = bloodRhName;
    }

    public String getAdmissionWardName()
    {
        return admissionWardName;
    }

    public void setAdmissionWardName(String admissionWardName)
    {
        this.admissionWardName = admissionWardName;
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

    public String getPatientDomain()
    {
        return patientDomain;
    }

    public void setPatientDomain(String patientDomain)
    {
        this.patientDomain = patientDomain;
    }

	public String getOutpatientNoJson() {
		return outpatientNoJson;
	}

	public void setOutpatientNoJson(String outpatientNoJson) {
		this.outpatientNoJson = outpatientNoJson;
	}

	public String getInpatientNoJson() {
		return inpatientNoJson;
	}

	public void setInpatientNoJson(String inpatientNoJson) {
		this.inpatientNoJson = inpatientNoJson;
	}

	public String getCardFlag() {
		return cardFlag;
	}

	public void setCardFlag(String cardFlag) {
		this.cardFlag = cardFlag;
	}
}
