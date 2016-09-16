package com.founder.cdr.hl7.dto.poorin200901uv06;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.founder.cdr.hl7.dto.BaseDto;
import com.founder.cdr.hl7.dto.ConsultationDept;
import com.founder.cdr.hl7.dto.MedicalVisit;

public class POORIN200901UV06 extends BaseDto
{
    /**
     * 新增标志
     */
    @NotEmpty(message = "{POORIN200901UV06.newUpFlag}")
    private String newUpFlag;

    /**
     * 本地医嘱号
     */
    private String orderLid;

    /**
     * 会诊申请编号
     */
    @NotEmpty(message = "{POORIN200901UV06.requestNo}")
    private String requestNo;

    /**
     * 会诊申请类型
     */
    // @NotEmpty(message="{POORIN200901UV06.consultationType}")
    private String consultationType;

    /**
     * 会诊原因
     */
    @NotEmpty(message = "{POORIN200901UV06.reason}")
    private String reason;

    /**
     * 会诊申请时间
     */
    @NotEmpty(message = "{POORIN200901UV06.requestTime}")
    private String requestTime;

    /**
     * 紧急程度编码
     */
    private String urgencyCode;

    /**
     * 紧急程度名称
     */
    @NotEmpty(message = "{POORIN200901UV06.urgencyName}")
    private String urgencyName;

    /**
     * 域代码
     */
    @NotEmpty(message = "{POORIN200901UV06.patientDomain}")
    private String patientDomain;

    /**
     * 门诊号
     */
    @NotEmpty(message = "{POORIN200901UV06.patientLid}")
    private String patientLid;

    /**
     * 姓名
     */
    @NotEmpty(message = "{POORIN200901UV06.patientName}")
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
     * 参与会诊科室
     */
    private List<ConsultationDept> consultationDept;

    /**
     * 申请人ID
     */
    @NotEmpty(message = "{POORIN200901UV06.requestDoctor}")
    private String requestDoctor;

    /**
     * 申请人姓名
     */
    @NotEmpty(message = "{POORIN200901UV06.requestDoctorName}")
    private String requestDoctorName;

    /**
     * 申请科室ID
     */
    @NotEmpty(message = "{POORIN200901UV06.orderDept}")
    private String orderDept;

    /**
     * 申请科室名称
     */
    @NotEmpty(message = "{POORIN200901UV06.orderDeptName}")
    private String orderDeptName;

    /**
     * 会诊科室ID
     */
    private String consultationPlace;

    /**
     * 会诊科室名称
     */
    @NotEmpty(message = "{POORIN200901UV06.consultationPlaceName}")
    private String consultationPlaceName;

    /**
     * 就诊
     */
    private List<MedicalVisit> visit;

    /**
     * 本地医嘱编码
     */
    @NotEmpty(message = "{POORIN200901UV06.orderCode}")
    private String orderCode;

    /**
     * 本地医嘱名称
     */
    private String orderName;
    // $Author :chang_xuewen
    // $Date : 2014/1/22 10:30$
    // [BUG]0042086 MODIFY BEGIN 
    /**
     * 医疗机构代码
     */
    /*@NotEmpty(message = "{POORIN200901UV06.orgCode}")*/
    private String orgCode;

	/**
     * 医疗机构名称
     */
    /*@NotEmpty(message = "{POORIN200901UV06.orgName}")*/
	private String orgName;
    /**
     * 会诊申请类型名称
     */
    /*@NotEmpty(message = "{POORIN200901UV06.consultationTypeName}")*/
    private String consultationTypeName;    
    
    
    public String getConsultationTypeName() {
		return consultationTypeName;
	}

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

    public String getOrderLid()
    {
        return orderLid;
    }

    public String getRequestNo()
    {
        return requestNo;
    }

    public String getConsultationType()
    {
        return consultationType;
    }

    public String getReason()
    {
        return reason;
    }

    public String getRequestTime()
    {
        return requestTime;
    }

    public String getUrgencyCode()
    {
        return urgencyCode;
    }

    public String getUrgencyName()
    {
        return urgencyName;
    }

    public String getPatientDomain()
    {
        return patientDomain;
    }

    public String getPatientLid()
    {
        return patientLid;
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

    public List<ConsultationDept> getConsultationDept()
    {
        return consultationDept;
    }

    public String getRequestDoctor()
    {
        return requestDoctor;
    }

    public String getRequestDoctorName()
    {
        return requestDoctorName;
    }

    public String getOrderDept()
    {
        return orderDept;
    }

    public String getOrderDeptName()
    {
        return orderDeptName;
    }

    public String getConsultationPlace()
    {
        return consultationPlace;
    }

    public String getConsultationPlaceName()
    {
        return consultationPlaceName;
    }

    public List<MedicalVisit> getVisit()
    {
        return visit;
    }

    public String getOrderCode()
    {
        return orderCode;
    }

    public String getOrderName()
    {
        return orderName;
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
