package com.yly.cdr.hl7.dto.prpain400001uv04;

import org.hibernate.validator.constraints.NotEmpty;

import com.yly.cdr.hl7.dto.BaseDto;
import com.yly.cdr.util.DateUtils;


public class PRPAIN400001UV04 extends BaseDto
{
    /**
     * 触发事件
     */
    @NotEmpty(message="{PRPAIN400001UV04.triggerEvent}")
    private String triggerEvent;
    /**
     * 入/出留观标志
     */
    @NotEmpty(message="{PRPAIN400001UV04.inOutFlag}")
    private String inOutFlag;
    /**
     * 就诊次数
     */
    @NotEmpty(message="{PRPAIN400001UV04.visitTimes}")
    private String visitTimes;
    /**
     * 入/出留观时间
     */
    @NotEmpty(message="{PRPAIN400001UV04.execTime}")
    private String execTime;
    /**
     * 域代码
     */
    @NotEmpty(message="{PRPAIN400001UV04.patientDomain}")
    private String patientDomain;
    /**
     * 患者本地ID
     */
    @NotEmpty(message="{PRPAIN400001UV04.patientLid}")
    private String patientLid;
    /**
     * 门诊号
     */
    private String outPatientNo;
    /**
     * 病情分级编码
     */
    private String gradingCode;
    /**
     * 病情分级名称
     */
    private String gradingName;
    /**
     * 出留观去向编码
     */
    private String destinationCode;
    /**
     * 出留观去向名称
     */
    private String destinationName;
    /**
     * 出留观方式编码
     */
    private String viewRoomOutWayCode;
    /**
     * 出留观方式名称
     */
    private String viewRoomOutWayName;
    /**
     * 出留观医生标识
     */
    private String fromExecPersonId;
    /**
     * 出留观医生姓名
     */
    private String fromExecPersonName;
    /**
     * 出留观分区ID
     */
    private String fromWards;
    /**
     * 出留观病区名称
     */
    private String fromWardsName;
    /**
     * 出留观床位号
     */
    private String fromBed;
    /**
     * 病人意识编码
     */
    private String patientLocCode;
    /**
     * 病人意识名称
     */
    private String patientLocName;
    /**
     * 入留观医生标识
     */
    private String toExecPersonId;
    /**
     * 入留观医生姓名
     */
    private String toExecPersonName;
    /**
     * 入留观病区ID
     */
    private String toWards;
    /**
     * 入留观病区名称
     */
    private String toWardsName;
    /**
     * 入留观床位号
     */
    private String toBed;
    // $Author :chang_xuewen
    // $Date : 2014/1/22 10:30$
    // [BUG]0042086 MODIFY BEGIN
    /**
     * 医疗机构编码 
     */
    /*@NotEmpty(message="{message.organizationCode}")*/
    private String organizationCode;

	/**
     * 医疗机构名称
     */
    /*@NotEmpty(message="{message.organizationName}")*/
    private String organizationName;
    public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
    // [BUG]0042086 MODIFY BEGIN

    public String getInOutFlag()
    {
        return inOutFlag;
    }

    public String getVisitTimes()
    {
        return visitTimes;
    }

    public String getExecTime()
    {
        return execTime;
    }

    public String getPatientDomain()
    {
        return patientDomain;
    }

    public String getPatientLid()
    {
        return patientLid;
    }

    public String getOutPatientNo()
    {
        return outPatientNo;
    }

    public String getGradingCode()
    {
        return gradingCode;
    }

    public String getGradingName()
    {
        return gradingName;
    }

    public String getDestinationCode()
    {
        return destinationCode;
    }
    
    public String getDestinationName()
    {
        return destinationName;
    }

    public String getViewRoomOutWayCode()
    {
        return viewRoomOutWayCode;
    }

    public String getViewRoomOutWayName()
    {
        return viewRoomOutWayName;
    }

    public String getFromExecPersonId()
    {
        return fromExecPersonId;
    }

    public String getFromExecPersonName()
    {
        return fromExecPersonName;
    }

    public String getFromWards()
    {
        return fromWards;
    }

    public String getFromWardsName()
    {
        return fromWardsName;
    }

    public String getFromBed()
    {
        return fromBed;
    }

    public String getPatientLocCode()
    {
        return patientLocCode;
    }

    public String getPatientLocName()
    {
        return patientLocName;
    }

    public String getToExecPersonId()
    {
        return toExecPersonId;
    }

    public String getToExecPersonName()
    {
        return toExecPersonName;
    }

    public String getToWards()
    {
        return toWards;
    }

    public String getToWardsName()
    {
        return toWardsName;
    }

    public String getToBed()
    {
        return toBed;
    }

    public String getTriggerEvent()
    {
        return triggerEvent;
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
        builder.append("PRPAIN400001UV04 [triggerEvent=");
        builder.append(triggerEvent);
        builder.append(", organizationCode=");
        builder.append(organizationCode);
        builder.append(", inOutFlag=");
        builder.append(inOutFlag);
        builder.append(", visitTimes=");
        builder.append(visitTimes);
        builder.append(", execTime=");
        builder.append(execTime);
        builder.append(", patientDomain=");
        builder.append(patientDomain);
        builder.append(", patientLid=");
        builder.append(patientLid);
        builder.append("]");
        return builder.toString();
    }
    
}
