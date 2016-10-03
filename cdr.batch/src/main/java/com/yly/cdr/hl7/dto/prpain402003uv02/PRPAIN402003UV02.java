package com.yly.cdr.hl7.dto.prpain402003uv02;

import org.hibernate.validator.constraints.NotEmpty;

import com.yly.cdr.hl7.dto.BaseDto;
import com.yly.cdr.util.DateUtils;

public class PRPAIN402003UV02 extends BaseDto
{
    /**
     * 触发事件
     */
    @NotEmpty(message = "{PRPAIN402003UV02.triggerEvent}")
    private String triggerEvent;

    /**
     * 就诊次数
     */
    //@NotEmpty(message = "{PRPAIN402003UV02.visitTimes}")
    private String visitTimes;

    /**
     * 出院时间
     */
//    @NotEmpty(message = "{PRPAIN402003UV02.dischargeDate}")
    private String dischargeDate;

    /**
     * 住院天数
     */
    private String residentDayCount;

    /**
     * 出院状态代码
     */
    private String dischargeStatus;

    /**
     * 出院状态名称
     */
    private String dischargeStatusName;

    /**
     * 患者本地ID
     */
    @NotEmpty(message = "{PRPAIN402003UV02.patientLid}")
    private String patientLid;

    /**
     * 域ID
     */
    @NotEmpty(message = "{PRPAIN402003UV02.patientDomain}")
    private String patientDomain;

    /**
     * 病区
     */
    //@NotEmpty(message = "{PRPAIN402003UV02.dischargeWards}")
    private String dischargeWards;

    /**
     * 病区名称
     */
//    @NotEmpty(message = "{PRPAIN402003UV02.dischargeWardsName}")
    private String dischargeWardsName;

    /**
     * 床号
     */
    //@NotEmpty(message = "{PRPAIN402003UV02.dischargeBedNo}")
    private String dischargeBedNo;

    /**
     * 患者姓名
     */
    private String patientName;

    /**
     * 性别
     */
    private String genderCode;

    /**
     * 出生日期 
     */
    private String birthDate;

    /**
     * 年龄
     */
    private String age;

    /**
     * 出生地
     */
    private String birthPlace;

    /**
     * 出院科室
     */
    //@NotEmpty(message = "{PRPAIN402003UV02.dischargeDept}")
    private String dischargeDept;

    /**
     * 出院科室名称
     */
    /*@NotEmpty(message = "{PRPAIN402003UV02.dischargeDeptName}")*/
    private String dischargeDeptName;

    /**
     * 医师
     */
    private String attendingDoctor;

    /**
     * 医生姓名
     */
    private String attendingDoctorName;
    
    // $Author :chang_xuewen
    // $Date : 2014/1/22 10:30$
    // [BUG]0042086 MODIFY BEGIN
    /**
     * 医疗机构代码
     */
    /*@NotEmpty(message = "{PRPAIN402003UV02.orgCode}")*/
    private String orgCode;

	/**
     * 医疗机构名称
     */
    /*@NotEmpty(message = "{PRPAIN402003UV02.orgName}")*/
    private String orgName;

	/**
     * 就诊流水号
     */
    @NotEmpty(message = "{PRPAIN402003UV02.visitOrdNo}")
    private String visitOrdNo;
    
	/**
     * 就诊类型
     */
    @NotEmpty(message = "{PRPAIN402003UV02.visitType}")
    private String visitType;
    

    public String getVisitOrdNo() {
		return visitOrdNo;
	}

	public void setVisitOrdNo(String visitOrdNo) {
		this.visitOrdNo = visitOrdNo;
	}

	public String getVisitType() {
		return visitType;
	}

	public void setVisitType(String visitType) {
		this.visitType = visitType;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
    // [BUG]0042086 MODIFY BEGIN
    public String getTriggerEvent()
    {
        return triggerEvent;
    }

    public String getVisitTimes()
    {
        return visitTimes;
    }

    public String getDischargeDate()
    {
        return dischargeDate;
    }

    public String getResidentDayCount()
    {
        return residentDayCount;
    }

    public String getDischargeStatus()
    {
        return dischargeStatus;
    }

    public String getDischargeStatusName()
    {
        return dischargeStatusName;
    }

    public String getPatientLid()
    {
        return patientLid;
    }

    public String getPatientDomain()
    {
        return patientDomain;
    }

    public String getDischargeWards()
    {
        return dischargeWards;
    }

    public String getDischargeWardsName()
    {
        return dischargeWardsName;
    }

    public String getDischargeBedNo()
    {
        return dischargeBedNo;
    }

    public String getPatientName()
    {
        return patientName;
    }

    public String getGenderCode()
    {
        return genderCode;
    }

    public String getBirthDate()
    {
        return birthDate;
    }

    public String getAge()
    {
        return age;
    }

    public String getBirthPlace()
    {
        return birthPlace;
    }

    public String getDischargeDept()
    {
        return dischargeDept;
    }

    public String getDischargeDeptName()
    {
        return dischargeDeptName;
    }

    public String getAttendingDoctor()
    {
        return attendingDoctor;
    }

    public String getAttendingDoctorName()
    {
        return attendingDoctorName;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("MessageId=");
        builder.append(getMessage().getId()+",");
        builder.append("Datetime=");
        builder.append(DateUtils.dateToString(getMessage().getDatetime(),null) + ",");
        builder.append("PRPAIN402003UV02 [triggerEvent=");
        builder.append(triggerEvent);
        builder.append(", visitTimes=");
        builder.append(visitTimes);
        builder.append(", patientLid=");
        builder.append(patientLid);
        builder.append(", patientDomain=");
        builder.append(patientDomain);
        builder.append(", orgCode=");
        builder.append(orgCode);
        builder.append(", orgName=");
        builder.append(orgName);
        builder.append(", visitOrdNo=");
        builder.append(visitOrdNo);
        builder.append(", visitType=");
        builder.append(visitType);
        builder.append("]");
        return builder.toString();
    }

    public String getOrgCode()
    {
        return orgCode;
    }

    public String getOrgName()
    {
        return orgName;
    }
    
}
