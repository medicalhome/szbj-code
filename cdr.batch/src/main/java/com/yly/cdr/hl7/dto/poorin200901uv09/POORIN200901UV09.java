package com.yly.cdr.hl7.dto.poorin200901uv09;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.yly.cdr.hl7.dto.BaseDto;
import com.yly.cdr.hl7.dto.Orders;
import com.yly.cdr.util.DateUtils;

public class POORIN200901UV09 extends BaseDto
{
	/**
     * 发送系统
     */
	private String sender;
	
    /**
     * 触发事件
     */
    @NotEmpty(message = "{POORIN200901UV09.triggerEvent}")
    private String triggerEvent;

    /**
     * 域ID
     */
//    @NotEmpty(message = "{POORIN200901UV09.patientDomain}")
    private String patientDomain;

    /**
     * 门诊系统患者ID
     */
    @NotEmpty(message = "{POORIN200901UV09.patientLid}")
    private String patientLid;

    /**
     * 门诊号
     */
    private String outpatientNo;

    /**
     * 住院号
     */
    private String admissionNo;

    /**
     * 病区码
     */
    private String wardsCode;

    /**
     * 病区名称
     */
    private String wardsName;

    /**
     * 床号
     */
    private String bedNo;

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
     * 医嘱项信息
     */
    @NotEmpty(message = "{POORIN200901UV09.order}")
    private List<Orders> order;

    /**
     * 就诊次数
     */
    //@NotEmpty(message = "{POORIN200901UV09.visitTimes}")
    private String visitTimes;

    /**
     * 就诊状态
     */
    private String visitStatus;
    /**
     * 执行科室 编码
     */
    private String execDept;
    /**
     * 执行科室名称
     */
    private String execDeptName;
    // $Author :chang_xuewen
    // $Date : 2014/1/22 10:30$
    // [BUG]0042086 MODIFY BEGIN
    // $Author :chang_xuewen
    // $Date : 2013/12/03 11:00$
    // [BUG]0040271 ADD BEGIN
    /*@NotEmpty(message = "{POORIN200901UV09.orgCode}")*/
    private String orgCode;
	/*@NotEmpty(message = "{POORIN200901UV09.orgName}")*/
	private String orgName;
	// [BUG]0040271 ADD END
	
	/**
     * 就诊流水号
     */
//    @NotEmpty(message = "{POORIN200901UV09.visitOrdNo}")
    private String visitOrdNo;
    
	/**
     * 就诊类型
     */
//    @NotEmpty(message = "{POORIN200901UV09.visitType}")
    private String visitType;
    
	/**
     * 就诊类型名称
     */
//    @NotEmpty(message = "{POORIN200901UV09.visitTypeName}")
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
	
	
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	// [BUG]0042086 MODIFY END
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

    public String getPatientLid()
    {
        return patientLid;
    }

    public String getOutpatientNo()
    {
        return outpatientNo;
    }

    public String getAdmissionNo()
    {
        return admissionNo;
    }

    public String getWardsCode()
    {
        return wardsCode;
    }

    public String getWardsName()
    {
        return wardsName;
    }

    public String getBedNo()
    {
        return bedNo;
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

    public List<Orders> getOrder()
    {
        return order;
    }

    public void setOrder(List<Orders> order)
    {
        this.order = order;
    }

    public String getVisitTimes()
    {
        return visitTimes;
    }

    public String getVisitStatus()
    {
        return visitStatus;
    }
    
    public String getExecDept()
    {
        return execDept;
    }

    public String getExecDeptName()
    {
        return execDeptName;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("MessageId=");
        builder.append(getMessage().getId() + ",");
        builder.append("Datetime=");
        builder.append(DateUtils.dateToString(getMessage().getDatetime(), null) + ",");
        builder.append("POORIN200901UV09 [triggerEvent=");
        builder.append(triggerEvent);
        builder.append(", sender=");
        builder.append(sender);
        builder.append(", patientDomain=");
        builder.append(patientDomain);
        builder.append(", patientLid=");
        builder.append(patientLid);
        builder.append(", outpatientNo=");
        builder.append(outpatientNo);
        builder.append(", admissionNo=");
        builder.append(admissionNo);
        builder.append(", order=");
        builder.append(order);
        builder.append(", visitTimes=");
        builder.append(visitTimes);
        builder.append(", visitStatus=");
        builder.append(visitStatus);
        builder.append("]");
        return builder.toString();
    }

	public String getSender() {
		return sender;
	}
}
