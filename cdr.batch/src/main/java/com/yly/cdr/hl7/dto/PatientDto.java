package com.yly.cdr.hl7.dto;

public class PatientDto extends BaseDto
{
    /**
     * 域ID
     */
    private String patientDomain;

    /**
     * 患者姓名
     */
    private String patientName;

    /**
     * 门诊患者ID
     */
    private String patientLid;

    /**
     * 性别代码
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
    // $Author :chang_xuewen
    // $Date : 2014/1/22 10:30$
    // [BUG]0042086 MODIFY BEGIN
    // $Author:tong_meng
    // $Date:2013/12/03 11:00
    // [BUG]0040270 ADD BEGIN
    /**
     * 医疗机构代码 
     */
    private String orgCode;
    
	/**
     * 医疗机构名称
     */
    private String orgName;
    // [BUG]0040270 ADD END
    public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
    // [BUG]0042086 MODIFY BEGIN
    public String getPatientDomain()
    {
        return patientDomain;
    }

    public String getPatientName()
    {
        return patientName;
    }

    public String getPatientLid()
    {
        return patientLid;
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

    public String getOrgCode()
    {
        return orgCode;
    }

    public String getOrgName()
    {
        return orgName;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("PatientDto [patientDomain=");
        builder.append(patientDomain);
        builder.append(", patientName=");
        builder.append(patientName);
        builder.append(", patientLid=");
        builder.append(patientLid);
        builder.append(", genderCode=");
        builder.append(genderCode);
        builder.append(", birthDate=");
        builder.append(birthDate);
        builder.append(", age=");
        builder.append(age);
        builder.append(", orgCode=");
        builder.append(orgCode);
        builder.append(", orgName=");
        builder.append(orgName);
        builder.append("]");
        return builder.toString();
    }

}
