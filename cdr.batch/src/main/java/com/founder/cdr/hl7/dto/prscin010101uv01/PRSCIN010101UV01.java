package com.founder.cdr.hl7.dto.prscin010101uv01;

import org.hibernate.validator.constraints.NotEmpty;

import com.founder.cdr.hl7.dto.BaseDto;


public class PRSCIN010101UV01 extends BaseDto
{
    /**
     * 新增标志
     */
    @NotEmpty(message = "{PRSCIN010101UV01.newUpFlag}")
    private String newUpFlag;
    /**
     * 预约号
     */
    private String appointmentNo;
    /**
     * 预约时间
     */
    @NotEmpty(message = "{PRSCIN010101UV01.appointmentTime}")
    private String appointmentTime;
    /**
     * 域ID
     */
    @NotEmpty(message = "{PRSCIN010101UV01.patientDomain}")
    private String patientDomain;
    /**
     * 患者本地号
     */
    @NotEmpty(message = "{PRSCIN010101UV01.patientLid}")
    private String patientLid;
    /**
     * 就诊次数
     */
    @NotEmpty(message = "{PRSCIN010101UV01.visitTime}")
    private String visitTime;
    /**
     * 预约设备编码
     */
    private String appDeviceCode;
    /**
     * 预约设备名称
     */
    private String appDeviceName;
    /**
     * 预约员编码
     */
    @NotEmpty(message = "{PRSCIN010101UV01.appPerformerCode}")
    private String appPerformerCode;
    /**
     * 预约员名称
     */
    @NotEmpty(message = "{PRSCIN010101UV01.appPerformerName}")
    private String appPerformerName;
    /**
     * 执行科室编码
     */
    @NotEmpty(message = "{PRSCIN010101UV01.execDeptCode}")
    private String execDeptCode;
    /**
     * 执行科室名称
     */
    private String execDeptName;
    /**
     * 申请单号
     */
    private String requestNo;
    /**
     * 医嘱号
     */
    private String orderLid;
    
    // 因检查预约消息提供方还未升级到添加预约排序号版本，先注释掉该字段非空验证
    /**
     * 预约排序号
     */
    //@NotEmpty(message = "{PRSCIN010101UV01.appOrderNo}")
    private String appOrderNo;
    
    // $Author :chang_xuewen
    // $Date : 2014/1/22 10:30$
    // [BUG]0042086 MODIFY BEGIN
    /**
     * 医疗机构编码
     */
    /*@NotEmpty(message = "{PRSCIN010101UV01.orgCode}")*/
    private String orgCode;
    
	/**
     * 医嘱机构名称
     */
    /*@NotEmpty(message = "{PRSCIN010101UV01.orgName}")*/
    private String orgName;
    public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
    // [BUG]0042086 MODIFY END

    public String getNewUpFlag()
    {
        return newUpFlag;
    }

    public String getAppointmentNo()
    {
        return appointmentNo;
    }

    public String getAppointmentTime()
    {
        return appointmentTime;
    }

    public String getPatientDomain()
    {
        return patientDomain;
    }

    public String getPatientLid()
    {
        return patientLid;
    }

    public String getVisitTime()
    {
        return visitTime;
    }

    public String getAppDeviceCode()
    {
        return appDeviceCode;
    }

    public String getAppDeviceName()
    {
        return appDeviceName;
    }

    public String getAppPerformerCode()
    {
        return appPerformerCode;
    }

    public String getAppPerformerName()
    {
        return appPerformerName;
    }

    public String getExecDeptCode()
    {
        return execDeptCode;
    }

    public String getExecDeptName()
    {
        return execDeptName;
    }

    public String getRequestNo()
    {
        return requestNo;
    }

    public String getOrderLid()
    {
        return orderLid;
    }

    public String getAppOrderNo()
    {
        return appOrderNo;
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
        builder.append("PRSCIN010101UV01 [newUpFlag=");
        builder.append(newUpFlag);
        builder.append(", appointmentNo=");
        builder.append(appointmentNo);
        builder.append(", patientDomain=");
        builder.append(patientDomain);
        builder.append(", patientLid=");
        builder.append(patientLid);
        builder.append(", visitTime=");
        builder.append(visitTime);
        builder.append(", requestNo=");
        builder.append(requestNo);
        builder.append(", orderLid=");
        builder.append(orderLid);
        builder.append(", appOrderNo=");
        builder.append(appOrderNo);
        builder.append(", orgCode=");
        builder.append(orgCode);
        builder.append(", orgName=");
        builder.append(orgName);
        builder.append("]");
        return builder.toString();
    }
    
}
