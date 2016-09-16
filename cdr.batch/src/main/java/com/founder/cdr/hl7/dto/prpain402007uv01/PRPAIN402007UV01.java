package com.founder.cdr.hl7.dto.prpain402007uv01;

import org.hibernate.validator.constraints.NotEmpty;

import com.founder.cdr.hl7.dto.BaseDto;
import com.founder.cdr.util.DateUtils;

public class PRPAIN402007UV01 extends BaseDto
{
    /**
     * 触发事件
     */
    @NotEmpty(message = "{PRPAIN402007UV01.triggerEvent}")
    private String triggerEvent;

    /**
     * 域ID
     */
    @NotEmpty(message = "{PRPAIN402007UV01.patientDomain}")
    private String patientDomain;

    /**
     * 患者ID
     */
    @NotEmpty(message = "{PRPAIN402007UV01.patientLid}")
    private String patientLid;

    /**
     * 就诊号
     */
    private String outpatientNo;

    /**
     * 病区编码
     */
    @NotEmpty(message = "{PRPAIN402007UV01.wardsId}")
    private String wardsId;

    /**
     * 病区名称
     */
    @NotEmpty(message = "{PRPAIN402007UV01.wardsName}")
    private String wardsName;

    /**
     * 床位号
     */
    @NotEmpty(message = "{PRPAIN402007UV01.bedNo}")
    private String bedNo;

    /**
     * 科室编码
     */
    @NotEmpty(message = "{PRPAIN402007UV01.deptId}")
    private String deptId;

    /**
     * 科室名称
     */
    @NotEmpty(message = "{PRPAIN402007UV01.deptName}")
    private String deptName;

    /**
     * 病人名称
     */
    private String patientName;

    /**
     * 性别编码
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
     * 召回日期
     */
    @NotEmpty(message = "{PRPAIN402007UV01.withdrawTime}")
    private String withdrawTime;

    /**
     * 召回原因
     */
    private String withdrawReason;

    /**
     * 召回操作者编码
     */
    @NotEmpty(message = "{PRPAIN402007UV01.withdrawPerson}")
    private String withdrawPerson;

    /**
     * 召回操作者名称
     */
    @NotEmpty(message = "{PRPAIN402007UV01.withdrawPersonName}")
    private String withdrawPersonName;

    /**
     * 就诊次数
     */
    //@NotEmpty(message = "{PRPAIN402007UV01.visitTimes}")
    private String visitTimes;
    // $Author :chang_xuewen
    // $Date : 2014/1/22 10:30$
    // [BUG]0042086 MODIFY BEGIN
    /**
     * 医疗机构编码
     */
    /*@NotEmpty(message = "{message.organizationCode}")*/
    private String organizationCode;

	/**
     * 医疗机构名称
     */
    /*@NotEmpty(message = "{message.organizationName}")*/
    private String organizationName;
    // [BUG]0042086 MODIFY END
	/**
     * 就诊流水号
     */
    @NotEmpty(message = "{PRPAIN402007UV01.visitOrdNo}")
    private String visitOrdNo;
    
	/**
     * 就诊类型
     */
    @NotEmpty(message = "{PRPAIN402007UV01.visitType}")
    private String visitType;
    
	/**
     * 就诊类型名称
     */
    @NotEmpty(message = "{PRPAIN402007UV01.visitTypeName}")
    private String visitTypeName;    

    public String getVisitTypeName() {
		return visitTypeName;
	}

	public void setVisitTypeName(String visitTypeName) {
		this.visitTypeName = visitTypeName;
	}

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
	
	
    public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

    
    public String getTriggerEvent()
    {
        return triggerEvent;
    }

    public String getPatientDomain()
    {
        return patientDomain;
    }

    public String getPatientLid()
    {
        return patientLid;
    }

    public String getOutpatientNo()
    {
        return outpatientNo;
    }

    public String getWardsId()
    {
        return wardsId;
    }

    public String getWardsName()
    {
        return wardsName;
    }

    public String getBedNo()
    {
        return bedNo;
    }

    public String getDeptId()
    {
        return deptId;
    }

    public String getDeptName()
    {
        return deptName;
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

    public String getWithdrawTime()
    {
        return withdrawTime;
    }

    public String getWithdrawReason()
    {
        return withdrawReason;
    }

    public String getWithdrawPerson()
    {
        return withdrawPerson;
    }

    public String getWithdrawPersonName()
    {
        return withdrawPersonName;
    }

    public String getVisitTimes()
    {
        return visitTimes;
    }
    
    public String getOrganizationCode() {
		return organizationCode;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	@Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("MessageId=");
        builder.append(getMessage().getId()+",");
        builder.append("Datetime=");
        builder.append(DateUtils.dateToString(getMessage().getDatetime(),null) + ",");
        builder.append("PRPAIN402007UV01 [triggerEvent=");
        builder.append(triggerEvent);
        builder.append(", patientDomain=");
        builder.append(patientDomain);
        builder.append(", patientLid=");
        builder.append(patientLid);
        builder.append(", visitTimes=");
        builder.append(visitTimes);
        builder.append(", organizationCode=");
        builder.append(organizationCode);
        builder.append("]");
        return builder.toString();
    }

}
