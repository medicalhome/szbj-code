package com.yly.cdr.hl7.dto.poorin200901uv07;

import org.hibernate.validator.constraints.NotEmpty;

import com.yly.cdr.hl7.dto.BaseDto;
import com.yly.cdr.util.DateUtils;

public class POORIN200901UV07 extends BaseDto
{
    /**
     * 域ID
     */
    @NotEmpty(message = "{POORIN200901UV07.patientDomain}")
    private String patientDomain;

    /**
     * 门诊号(患者本地ID)
     */
    @NotEmpty(message = "{POORIN200901UV07.patientLid}")
    private String patientLid;

    /**
     * 就诊次数
     */
    @NotEmpty(message = "{POORIN200901UV07.visitTimes}")
    private String visitTimes;

    /**
     * 输血申请单号
     */
    private String requestNo;

    /**
     * 取血单号
     */
    private String orderLid;

    /**
     * 本地医嘱号
     */
    private String orderLids;

    /**
     * 取血次数
     */
    private String orderTimes;

    /**
     * 取血量
     */
    @NotEmpty(message = "{POORIN200901UV07.quantity}")
    private String quantity;

    /**
     * 取血单位
     */
    @NotEmpty(message = "{POORIN200901UV07.unit}")
    private String unit;

    /**
     * 患者姓名
     */
    private String patientName;

    /**
     * 患者姓别代码
     */
    private String patientGender;

    /**
     * 患者年龄
     */
    private String patientAge;

    /**
     * 科室代码
     */
    private String orderDept;

    /**
     * 科室名称
     */
    private String orderDeptName;

    /**
     * 床号
     */
    private String bedNo;

    /**
     * 病区编号
     */
    private String wardCode;

    /**
     * 病区名称
     */
    private String wardName;

    /**
     * 输血目的代码
     */
    private String transfusionReason;

    /**
     * 输血目的名称
     */
    private String transfusionReasonName;

    /**
     * 成份代码
     */
    @NotEmpty(message = "{POORIN200901UV07.transfusionComponentsCode}")
    private String transfusionComponentsCode;

    /**
     * 成份名称
     */
    @NotEmpty(message = "{POORIN200901UV07.transfusionComponents}")
    private String transfusionComponents;

    /**
     * 取血时间
     */
    @NotEmpty(message = "{POORIN200901UV07.deliveryTime}")
    private String deliveryTime;

    /**
     * 申请医生id
     */
    @NotEmpty(message = "{POORIN200901UV07.requestDoctorId}")
    private String requestDoctorId;

    /**
     * 申请医生姓名
     */
    @NotEmpty(message = "{POORIN200901UV07.requestDoctorName}")
    private String requestDoctorName;

    /**
     * 送单护士id
     */
    private String applicationDenderId;

    /**
     * 送单护士姓名
     */
    private String applicationDenderName;

    /**
     * 接收人id
     */
    private String applicationReceiverId;

    /**
     * 接收人姓名
     */
    private String applicationReceiverName;

    /**
     * 新增标志
     */
    @NotEmpty(message = "{POORIN200901UV07.newUpFlag}")
    private String newUpFlag;
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
    public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
    // [BUG]0042086 MODIFY END  
    public String getPatientDomain()
    {
        return patientDomain;
    }

    public String getVisitTimes()
    {
        return visitTimes;
    }

    public String getPatientLid()
    {
        return patientLid;
    }

    public String getRequestNo()
    {
        return requestNo;
    }

    public String getOrderLid()
    {
        return orderLid;
    }

    public String getOrderLids()
    {
        return orderLids;
    }

    public String getOrderTimes()
    {
        return orderTimes;
    }

    public String getQuantity()
    {
        return quantity;
    }

    public String getUnit()
    {
        return unit;
    }

    public String getPatientName()
    {
        return patientName;
    }

    public String getPatientGender()
    {
        return patientGender;
    }

    public String getPatientAge()
    {
        return patientAge;
    }

    public String getOrderDept()
    {
        return orderDept;
    }

    public String getOrderDeptName()
    {
        return orderDeptName;
    }

    public String getBedNo()
    {
        return bedNo;
    }

    public String getWardName()
    {
        return wardName;
    }

    public String getWardCode()
    {
        return wardCode;
    }

    public String getTransfusionReason()
    {
        return transfusionReason;
    }

    public String getTransfusionReasonName()
    {
        return transfusionReasonName;
    }

    public void setTransfusionReasonName(String transfusionReasonName)
    {
        this.transfusionReasonName = transfusionReasonName;
    }

    public String getTransfusionComponentsCode()
    {
        return transfusionComponentsCode;
    }

    public String getTransfusionComponents()
    {
        return transfusionComponents;
    }

    public String getDeliveryTime()
    {
        return deliveryTime;
    }

    public String getRequestDoctorId()
    {
        return requestDoctorId;
    }

    public String getRequestDoctorName()
    {
        return requestDoctorName;
    }

    public String getApplicationDenderId()
    {
        return applicationDenderId;
    }

    public String getApplicationDenderName()
    {
        return applicationDenderName;
    }

    public String getApplicationReceiverId()
    {
        return applicationReceiverId;
    }

    public String getApplicationReceiverName()
    {
        return applicationReceiverName;
    }

    public String getNewUpFlag()
    {
        return newUpFlag;
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
        builder.append("POORIN200901UV07 [newUpFlag=");
        builder.append(newUpFlag);
        builder.append(", patientDomain=");
        builder.append(patientDomain);
        builder.append(", patientLid=");
        builder.append(patientLid);
        builder.append(", visitTimes=");
        builder.append(visitTimes);
        builder.append(", orderLid=");
        builder.append(orderLid);
        builder.append(", organizationCode=");
        builder.append(organizationCode);
        builder.append("]");
        return builder.toString();
    }
    
}
