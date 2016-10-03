package com.yly.cdr.hl7.dto.porxin010370uv01;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.yly.cdr.hl7.dto.BaseDto;
import com.yly.cdr.util.DateUtils;

public class PORXIN010370UV01 extends BaseDto
{
    /**
     * 触发事件
     */
    @NotEmpty(message = "{PORXIN010370UV01.triggerEvent}")
    private String triggerEvent;

    /**
     * 域ID
     */
    @NotEmpty(message = "{PORXIN010370UV01.patientDomain}")
    private String patientDomain;

    /**
     * 患者姓名
     */
    private String patientName;

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

    /**
     * 个人关系代码
     */
    private String relationshipCode;

    /**
     * 个人关系名称
     */
    private String relationshipName;

    /**
     * 代办人身份证号
     */
    private String credentialNo;

    /**
     * 代办人姓名
     */
    private String contactName;

    /**
     * 保险类别代码
     */
    private String insuranceClass;

    /**
     * 保险类别名称
     */
    private String insuranceClassName;

    /**
     * 保险机构号
     */
    private String insuranceOrganization;

    /**
     * 就诊次数
     */
//    @NotEmpty(message = "{PORXIN010370UV01.visitTime}")
    private String visitTime;

    /**
     * 就诊科室
     */
    private String visitDept;

    /**
     * 就诊科室名称
     */
    private String visitDeptName;

    /**
     * 患者ID
     */
    @NotEmpty(message = "{PORXIN010370UV01.patientLid}")
    private String patientLid;

    /**
     * 门诊号
     */
    private String outPatientNo;

    /**
     * 病人身份编码
     */
    private String patientIdentityCode;

    /**
     * 病人身份名称
     */
    private String patientIdentityName;

    /**
     * 处方
     */
    //@NotEmpty(message = "{PORXIN010370UV01.prescriptions}")
    private List<PrescriptionDto> prescriptions;
    
    // $Author :chang_xuewen
    // $Date : 2014/1/22 10:30$
    // [BUG]0042086 MODIFY BEGIN
    // $Author :chang_xuewen
    // $Date : 2013/12/03 11:00$
    // [BUG]0040271 ADD BEGIN
    /*@NotEmpty(message = "{PORXIN010370UV01.orgCode}")*/
    private String orgCode;
    /*@NotEmpty(message = "{PORXIN010370UV01.orgName}")*/
	private String orgName;
	// [BUG]0040271 ADD END
    // [BUG]0042086 MODIFY END

	/**
	 * 就诊类别
	 * */
	private String visitType;
	/**
	 * 就诊流水号
	 * */
	private String visitOrdNo;
	
	/**
	 * 病区编码
	 * */
	private String wardsId;
	
	/**
	 * 病区名称
	 * */
	private String wardsName;
	
	/**
	 * 床号
	 * */
	private String bedNo;
	
    public List<PrescriptionDto> getPrescriptions()
    {
        return prescriptions;
    }

    public String getOrgCode() {
		return orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public String getTriggerEvent()
    {
        return triggerEvent;
    }

    public String getPatientDomain()
    {
        return patientDomain;
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

    public String getRelationshipCode()
    {
        return relationshipCode;
    }

    public String getRelationshipName()
    {
        return relationshipName;
    }

    public String getCredentialNo()
    {
        return credentialNo;
    }

    public String getContactName()
    {
        return contactName;
    }

    public String getInsuranceClass()
    {
        return insuranceClass;
    }

    public String getInsuranceClassName()
    {
        return insuranceClassName;
    }

    public String getInsuranceOrganization()
    {
        return insuranceOrganization;
    }

    public String getVisitTime()
    {
        return visitTime;
    }

    public String getVisitDept()
    {
        return visitDept;
    }

    public String getVisitDeptName()
    {
        return visitDeptName;
    }

    public String getPatientLid()
    {
        return patientLid;
    }

    public String getOutPatientNo()
    {
        return outPatientNo;
    }

    public String getPatientIdentityCode()
    {
        return patientIdentityCode;
    }

    public String getPatientIdentityName()
    {
        return patientIdentityName;
    }

    public String getVisitType(){
    	return visitType;
    }
    
    public String getVisitOrdNo(){
    	return visitOrdNo;
    }
    
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("MessageId=");
        builder.append(getMessage().getId()+",");
        builder.append("Datetime=");
        builder.append(DateUtils.dateToString(getMessage().getDatetime(),
                null) + ",");
        builder.append("PORXIN010370UV01 [triggerEvent=");
        builder.append(triggerEvent);
        builder.append(", patientDomain=");
        builder.append(patientDomain);
        builder.append(", visitTime=");
        builder.append(visitTime);
        builder.append(", patientLid=");
        builder.append(patientLid);
        builder.append(", outPatientNo=");
        builder.append(outPatientNo);
        builder.append(", prescriptions=");
        builder.append(prescriptions);
        builder.append("]");
        return builder.toString();
    }

	public String getWardsId() {
		return wardsId;
	}

	public String getWardsName() {
		return wardsName;
	}

	public String getBedNo() {
		return bedNo;
	}


}
