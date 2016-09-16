// $Author:bin_zhang
// $Date : 2012/10/11 15:10
// $[BUG]0010244 ADD BEGIN
package com.founder.cdr.dto;

import java.util.Date;

public class PatientFavDto
{
    private String patientSn;

    private String patientFavSn;

    private String patientEid;

    private String outpatientNo;

    private String inpatientNo;

    private String patientName;

    private String genderName;

    private String genderCode;

    private String age;

    private Date visitDate;

    private Date birthDate;
    
    private String memo;
    
    // $Author:tong_meng 
    // $Date : 2013/11/07 10:10
    // $[BUG]0039034 ADD BEGIN
    // 住院患者病区
    private String admissionWardName;
    
    //住院患者床号
    private String admissionBedNo;
    
    // 出院病区
    private String dischargeWardName;

    // 出院床号
    private String dischargeBedNo;
    // $[BUG]0039034 ADD END
    
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

    public String getPatientName()
    {
        return patientName;
    }

    public void setPatientName(String patientName)
    {
        this.patientName = patientName;
    }

    public String getGenderName()
    {
        return genderName;
    }

    public void setGenderName(String genderName)
    {
        this.genderName = genderName;
    }

    public String getGenderCode()
    {
        return genderCode;
    }

    public void setGenderCode(String genderCode)
    {
        this.genderCode = genderCode;
    }

    public String getAge()
    {
        return age;
    }

    public void setAge(String age)
    {
        this.age = age;
    }

    public Date getVisitDate()
    {
        return visitDate;
    }

    public void setVisitDate(Date visitDate)
    {
        this.visitDate = visitDate;
    }

    public Date getBirthDate()
    {
        return birthDate;
    }

    public void setBirthDate(Date birthDate)
    {
        this.birthDate = birthDate;
    }

    public String getPatientFavSn()
    {
        return patientFavSn;
    }

    public void setPatientFavSn(String patientFavSn)
    {
        this.patientFavSn = patientFavSn;
    }

    public String getMemo()
    {
        return memo;
    }

    public void setMemo(String memo)
    {
        this.memo = memo;
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

// $[BUG]0010244 ADD END
