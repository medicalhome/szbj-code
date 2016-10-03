package com.yly.cdr.hl7.dto.prpain400001uv03;

import org.hibernate.validator.constraints.NotEmpty;

import com.yly.cdr.hl7.dto.BaseDto;

public class PRPAIN400001UV03 extends BaseDto
{
    /**
     * 触发事件
     */
    @NotEmpty(message="{PRPAIN400001UV03.triggerEvent}")
    private String triggerEvent;
    /**
     * 就诊次数
     */
    //@NotEmpty(message = "{PRPAIN400001UV03.visitTimes}")
    private String visitTimes;

    /**
     * 入科/出科
     */
    //@NotEmpty(message = "{PRPAIN400001UV03.transferFlag}")
    private String transferFlag;

    /**
     * 域ID
     */
    @NotEmpty(message = "{PRPAIN400001UV03.patientDomain}")
    private String patientDomain;

    /**
     * 门诊患者ID
     */
    private String patientLid;
    // $Author :chang_xuewen
    // $Date : 2014/1/22 10:30$
    // [BUG]0042086 MODIFY BEGIN
    /**
     * 医疗机构代码
     */
    /*@NotEmpty(message = "{PRPAIN400001UV03.orgCode}")*/
    private String orgCode;

	/**
     * 医疗机构名称
     */
    /*@NotEmpty(message = "{PRPAIN400001UV03.orgName}")*/
    private String orgName;
    
	// [BUG]0042086 MODIFY END
    /**
     * 入科执行人ID
     */
    private String inExecPersonId;

    /**
     * 入科执行人姓名
     */
    private String inExecPersonName;

    /**
     * 出科执行人ID
     */
    private String outExecPersonId;

    /**
     * 出科执行人姓名
     */
    private String outExecPersonName;

    /**
     * 执行时间
     */
    @NotEmpty(message = "{PRPAIN400001UV03.execTime}")
    private String execTime;

    /**
     * 转入/转出科室ID
     */
    private String deptId;

    /**
     * 转入/转出科室名称
     */
    private String deptName;

    /**
     * 转入/转出病区ID
     */
    private String wardsId;

    /**
     * 转入/转出病区名称
     */
    private String wardsName;

    /**
     * 转入/转出床号
     */
    private String bedNo;

    /**
     * 出科去向编码
     */
    private String destinationCode;

    /**
     * 出科去向名称 
     */
    private String destinationName;
    //add by lishenggen begin
    /**
     * 就诊流水号 
     */
    @NotEmpty(message = "{PRPAIN400001UV03.visitOrdNo}")    
    private String visitOrdNo;
    /**
     * 就诊类型 
     */
    @NotEmpty(message = "{PRPAIN400001UV03.visitTypeCode}")
    private String visitTypeCode;
    
    /**
     * 住院医生编号 
     */    
    private String residentDoctor;
    /**
     * 住院医生名称 
     */   
    private String residentDoctorName;
    /**
     * 主治医生编号
     */   
    private String attendingDoctor;
    /**
     * 主治医生名称
     */   
    private String attendingDoctorName;
    /**
     * 主任医生编号
     */   
    private String deptDirector;
    /**
     * 主任医生名称 
     */   
    private String deptDirectorName;
    // end
    
    
    public String getVisitOrdNo() {
		return visitOrdNo;
	}

	public String getResidentDoctor() {
		return residentDoctor;
	}

	public String getResidentDoctorName() {
		return residentDoctorName;
	}

	public String getAttendingDoctor() {
		return attendingDoctor;
	}

	public String getAttendingDoctorName() {
		return attendingDoctorName;
	}

	public String getDeptDirector() {
		return deptDirector;
	}

	public String getDeptDirectorName() {
		return deptDirectorName;
	}

	public void setVisitOrdNo(String visitOrdNo) {
		this.visitOrdNo = visitOrdNo;
	}

    public String getVisitTypeCode() {
		return visitTypeCode;
	}

	public void setVisitTypeCode(String visitTypeCode) {
		this.visitTypeCode = visitTypeCode;
	}

	public String getVisitTimes()
    {
        return visitTimes;
    }

    public String getTransferFlag()
    {
        return transferFlag;
    }

    public String getPatientDomain()
    {
        return patientDomain;
    }

    public String getPatientLid()
    {
        return patientLid;
    }

    public String getOrgCode()
    {
        return orgCode;
    }

    public String getOrgName()
    {
        return orgName;
    }
    
    public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public void setTriggerEvent(String triggerEvent) {
		this.triggerEvent = triggerEvent;
	}
	
    public String getInExecPersonId()
    {
        return inExecPersonId;
    }

    public String getInExecPersonName()
    {
        return inExecPersonName;
    }

    public String getOutExecPersonId()
    {
        return outExecPersonId;
    }

    public String getOutExecPersonName()
    {
        return outExecPersonName;
    }

    public String getExecTime()
    {
        return execTime;
    }

    public String getDeptId()
    {
        return deptId;
    }

    public String getDeptName()
    {
        return deptName;
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

    public String getDestinationCode()
    {
        return destinationCode;
    }

    public String getDestinationName()
    {
        return destinationName;
    }

    public String getTriggerEvent() {
		return triggerEvent;
	}
    

	public String toString()
    {
        StringBuilder sb = new StringBuilder("{\n");
        sb.append("message content:").append(getMessage().getContent()).append(
                "\n");
        sb.append("triggerEvent:").append(getTriggerEvent()).append("\n");
        sb.append("visitTimes:").append(getVisitTimes()).append("\n");
        sb.append("patientDomain:").append(getPatientDomain()).append("\n");
        //sb.append("transferFlag:").append(getTransferFlag()).append("\n");
        sb.append("outpatientLid:").append(getPatientLid()).append("\n");
        sb.append("inExecPersonId:").append(getInExecPersonId()).append("\n");
        sb.append("inExecPersonName:").append(getInExecPersonName()).append(
                "\n");
        //sb.append("outExecPersonId:").append(getOutExecPersonId()).append("\n");
        //sb.append("outExecPersonName:").append(getOutExecPersonName()).append("\n");
        sb.append("execTime:").append(getExecTime()).append("\n");
        sb.append("deptId:").append(getDeptId()).append("\n");
        sb.append("wardsId:").append(getWardsId()).append("\n");
        sb.append("bedNo:").append(getBedNo()).append("\n");
        sb.append("visitOrdNo:").append(getVisitOrdNo()).append("\n");
        sb.append("visitTypeCode:").append(getVisitTypeCode()).append("\n").append(
                "}");

        return sb.toString();
    }

	
}
