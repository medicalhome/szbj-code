package com.founder.cdr.hl7.dto.repcin003130uv01;

import org.hibernate.validator.constraints.NotEmpty;

import com.founder.cdr.hl7.dto.BaseDto;
import com.founder.cdr.util.DateUtils;

public class REPCIN003130UV01 extends BaseDto
{
    /**
     * 触发事件
     */
    @NotEmpty(message = "{REPCIN003130UV01.triggerEvent}")
    private String triggerEvent;

    /**
     * 转入床号
     */
    @NotEmpty(message = "{REPCIN003130UV01.toBed}")
    private String toBed;

    /**
     * 转入时间
     */
    @NotEmpty(message = "{REPCIN003130UV01.execTime}")
    private String execTime;

    /**
     * 域ID
     */
    @NotEmpty(message = "{REPCIN003130UV01.patientDomain}")
    private String patientDomain;

    /**
     * 门诊系统患者ID
     */
    @NotEmpty(message = "{REPCIN003130UV01.patientLid}")
    private String patientLid;

    /**
     * 就诊次数
     */
    @NotEmpty(message = "{REPCIN003130UV01.visitTimes}")
    private String visitTimes;

    /**
     * 转出病区
     */
    @NotEmpty(message = "{REPCIN003130UV01.fromWards}")
    private String fromWards;

    /**
     * 转出病区名称
     */
    @NotEmpty(message = "{REPCIN003130UV01.fromWardsName}")
    private String fromWardsName;

    /**
     * 转出床号
     */
    @NotEmpty(message = "{REPCIN003130UV01.fromBed}")
    private String fromBed;

    /**
     * 转出科室
     */
    @NotEmpty(message = "{REPCIN003130UV01.fromDept}")
    private String fromDept;

    /**
     * 转出科室名称
     */
    @NotEmpty(message = "{REPCIN003130UV01.fromDeptName}")
    private String fromDeptName;

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
     * 应答护士
     */
    @NotEmpty(message = "{REPCIN003130UV01.execPerson}")
    private String execPerson;

    /**
     * 应答护士姓名
     */
    @NotEmpty(message = "{REPCIN003130UV01.execPersonName}")
    private String execPersonName;

    /**
     * 转入科室
     */
    @NotEmpty(message = "{REPCIN003130UV01.toDept}")
    private String toDept;

    /**
     * 转入科室名称
     */
    @NotEmpty(message = "{REPCIN003130UV01.toDeptName}")
    private String toDeptName;

    /**
     * 转入病区
     */
    @NotEmpty(message = "{REPCIN003130UV01.toWards}")
    private String toWards;

    /**
     * 转入病区名称
     */
    @NotEmpty(message = "{REPCIN003130UV01.toWardsName}")
    private String toWardsName;

    /**
     * 医嘱号
     */
    private String orderLid;
    // $Author :chang_xuewen
    // $Date : 2014/1/22 10:30$
    // [BUG]0042086 MODIFY BEGIN
    /**
     * 医疗机构代码
     */
   /* @NotEmpty(message = "{REPCIN003130UV01.orgCode}")*/
    private String orgCode;
    
	/**
     * 医疗机构名称
     */
   /* @NotEmpty(message = "{REPCIN003130UV01.orgName}")*/
    private String orgName;
    
    public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
    // [BUG]0042086 MODIFY END
    public String getTriggerEvent()
    {
        return triggerEvent;
    }

    public String getToBed()
    {
        return toBed;
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

    public String getVisitTimes()
    {
        return visitTimes;
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

    public String getFromDept()
    {
        return fromDept;
    }

    public String getFromDeptName()
    {
        return fromDeptName;
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

    public String getExecPerson()
    {
        return execPerson;
    }

    public String getExecPersonName()
    {
        return execPersonName;
    }

    public String getToDept()
    {
        return toDept;
    }

    public String getToDeptName()
    {
        return toDeptName;
    }

    public String getToWards()
    {
        return toWards;
    }

    public String getToWardsName()
    {
        return toWardsName;
    }

    public String getOrderLid()
    {
        return orderLid;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("MessageId=");
        builder.append(getMessage().getId()+",");
        builder.append("Datetime=");
        builder.append(DateUtils.dateToString(getMessage().getDatetime(),null) + ",");
        builder.append("REPCIN003130UV01 [triggerEvent=");
        builder.append(triggerEvent);
        builder.append(", execTime=");
        builder.append(execTime);
        builder.append(", patientDomain=");
        builder.append(patientDomain);
        builder.append(", patientLid=");
        builder.append(patientLid);
        builder.append(", orgCode=");
        builder.append(orgCode);
        builder.append(", orgName=");
        builder.append(orgName);
        builder.append(", visitTimes=");
        builder.append(visitTimes);
        builder.append(", orderLid=");
        builder.append(orderLid);
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
