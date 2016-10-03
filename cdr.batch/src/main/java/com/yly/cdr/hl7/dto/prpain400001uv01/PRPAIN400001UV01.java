package com.yly.cdr.hl7.dto.prpain400001uv01;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.yly.cdr.hl7.dto.BaseDto;
import com.yly.cdr.hl7.dto.PatientDto;
import com.yly.cdr.util.DateUtils;

public class PRPAIN400001UV01 extends BaseDto
{
    /**
     * 域代码
     */
    @NotEmpty(message = "{PRPAIN400001UV01.patientDomain}")
    private String patientDomain;

    /**
     * 患者本地ID(门诊号）
     */
    @NotEmpty(message = "{PRPAIN400001UV01.patientLid}")
    private String patientLid;

    /**
     * 就诊次数
     */
//    @NotEmpty(message = "{PRPAIN400001UV01.visitTimes}")
    private String visitTimes;
    
    /**
     * 就诊流水号
     */
    @NotEmpty(message = "{PRPAIN400001UV01.visitOrdNo}")
    private String visitOrdNo;

    /**
     * 就诊类别编码
     */
    @NotEmpty(message = "{PRPAIN400001UV01.visitType}")
    private String visitType;

    /**
     * 就诊类别名称
     */
    private String visitTypeName;

    /**
     * 挂号日期
     */
    //@NotEmpty(message = "{PRPAIN400001UV01.registeredDate}")
    private String registeredDate;

    /**
     * 挂号科室
     */
    //@NotEmpty(message = "{PRPAIN400001UV01.registeredDept}")
    private String registeredDept;

    /**
     * 挂号科室名称
     */
    //@NotEmpty(message = "{PRPAIN400001UV01.registeredDeptName}")
    private String registeredDeptName;

    /**
     * 挂号医生编码
     */
    private String registeredDoctor;

    /**
     * 挂号医生姓名
     */
    private String registeredDoctorName;

    /**
     * 费用类别
     */
    private String insuranceType;

    /**
     * 费用类别名称
     */
    private String insuranceTypeName;

    /**
     * 就诊号别代码
     */
    // $Author :jia_yanqing
    // $Date : 2012/10/12 17:10$
    // [BUG]10373 MODIFY BEGIN
    // @NotEmpty(message = "{PRPAIN400001UV01.registrationClass}")
    private String registrationClass;

    /**
     * 就诊号别名称
     */
    private String registrationClassName;

    /**
     * 挂号方式
     */
    // @NotEmpty(message = "{PRPAIN400001UV01.registeredWay}")
    private String registeredWay;

    /**
     * 挂号方式名称
     */
    private String registeredWayName;

    /**
     * 就诊序号
     */
    private String registeredSequence;

    /**
     * 触发事件代码
     */
    @NotEmpty(message = "{PRPAIN400001UV01.triggerEventCode}")
    private String triggerEventCode;

    // $Author:wei_peng
    // $Date:2012/10/19 17:41
    // [BUG]0010597 ADD BEGIN
    /*
     * 所挂时段编码
     */
    private String registeredTimeIntervalCode;

    /**
     * 所挂时段名称
     */
    private String registeredTimeIntervalName;

    // [BUG]0010597 ADD END
    /**
     * 患者基本信息
     */
    private List<PatientDto> patients;
    
    /**
     * 就诊日期
     */
    //@NotEmpty(message = "{PRPAIN400001UV01.visitDate}")
    private String visitDate;
    
    /**
     * 患者EMPI号
     */
//    @NotEmpty(message = "{PRPAIN400001UV01.patientEid}")
    private String patientEid;
    
    public String getRegisteredTimeIntervalCode()
    {
        return registeredTimeIntervalCode;
    }

    public String getRegisteredTimeIntervalName()
    {
        return registeredTimeIntervalName;
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

    public String getVisitType()
    {
        return visitType;
    }

    public String getRegisteredDate()
    {
        return registeredDate;
    }

    public String getRegisteredDept()
    {
        return registeredDept;
    }

    public String getRegisteredDeptName()
    {
        return registeredDeptName;
    }

    public String getRegisteredDoctor()
    {
        return registeredDoctor;
    }

    public String getRegisteredDoctorName()
    {
        return registeredDoctorName;
    }

    public String getInsuranceType()
    {
        return insuranceType;
    }

    public String getInsuranceTypeName()
    {
        return insuranceTypeName;
    }

    public String getRegistrationClass()
    {
        return registrationClass;
    }

    public String getRegistrationClassName()
    {
        return registrationClassName;
    }

    public String getRegisteredWay()
    {
        return registeredWay;
    }

    public String getRegisteredWayName()
    {
        return registeredWayName;
    }

    public String getRegisteredSequence()
    {
        return registeredSequence;
    }

    public String getTriggerEventCode()
    {
        return triggerEventCode;
    }

    public List<PatientDto> getPatients()
    {
        return patients;
    }

    public String getVisitTypeName()
    {
        return visitTypeName;
    }

    public String getVisitDate()
    {
        return visitDate;
    }
    
    public String getVisitOrdNo()
    {
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
        builder.append("PRPAIN400001UV01 [triggerEventCode=");
        builder.append(triggerEventCode);
        builder.append(", patientDomain=");
        builder.append(patientDomain);
        builder.append(", patientLid=");
        builder.append(patientLid);
        builder.append(", visitTimes=");
        builder.append(visitTimes);
        builder.append(", visitOrdNo=");
        builder.append(visitOrdNo);
        builder.append(", visitType=");
        builder.append(visitType);
        builder.append(", visitTypeName=");
        builder.append(visitTypeName);
        builder.append(", patients=");
        builder.append(patients);
        builder.append("]");
        return builder.toString();
    }

	public String getPatientEid() {
		return patientEid;
	}
    
 }
