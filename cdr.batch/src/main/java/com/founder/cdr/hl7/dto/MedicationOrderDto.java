package com.founder.cdr.hl7.dto;

import java.math.BigDecimal;
import java.util.List;

public class MedicationOrderDto extends OrderDto
{
    /**
     * 医嘱内部序列号
     */
    private BigDecimal orderSn;
    
    /**
     * 医嘱序号
     */
    private String orderSeqnum;
    
    /**
     * 本地医嘱号
     */
    //@NotEmpty(message="{MedicationOrderDto.orderLid}")
    private String orderLid;
    /**
     * 医嘱类别代码
     */
    private String orderType;
    /**
     * 医嘱类别名称
     */
    private String orderTypeName;
    /**
     * 药物使用-频率
     */
    private String medicineFrenquency;
    /**
     * 药物使用-频率名称
     */
    private String medicineFrenquencyName;
    /**
     * 用药途径代码（用法）
     */
    private String routeCode;
    /**
     * 用药途径名称（用法描述）
     */
    private String routeName;
    /**
     * 药物使用-次剂量（剂量）
     */
    private String dosage;
    /**
     * 药物使用-次剂量单位（剂量单位）
     */
    private String dosageUnit;
    /**
     * 药物使用-总剂量（用量/数量）
     */
    private String totalDosage;
    /**
     * 药物使用-总剂量单位（用量/数量单位）
     */
    private String totalDosageUnit;
    /**
     * 药物编码
     */
    private String drugCode;
    /**
     * 药物名称
     */
    private String drugName;
    /**
     * 开嘱时间
     */
    private String orderTime;
    /**
     * 医嘱开立人ID
     */
    private String orderPerson;
    /**
     * 医嘱开立人姓名
     */
    private String orderPersonName;
    /**
     * 医嘱开立科室ID
     */
    private String orderDept;
    /**
     * 医嘱开立科室
     */
    private String orderDeptName;
    /**
     * 父医嘱号
     */
    private String parentOrderId;
    /**
     * 互斥医嘱类型编码
     */
    private String mutexesOrderType;
    /**
     * 互斥医嘱类型名称
     */
    private String mutexesOrderTypeName;
    /**
     * 是否皮试
     */
    private String skinTestFlag;
    /**
     * 是否加急
     */
    private String urgency;
    /**
     * 是否适应
     */
    private String adaptive;
    /**
     * 嘱托
     */
    private String comments;
    
    /**
     * 医嘱时间类型编码
     */
    private String ordExecTypeCode;
//    /**
//     * 中药配方
//     */
    private List<HerbalFormulaDto> herbalFormula;
//    /**
//     * 病人原病区编码
//     */
//    private String formerWardName;
//    /**
//     * 病人原病区名称
//     */
//    private String formerWard;
//    /**
//     * 病人原科室编码
//     */
//    private String formerDept;
//    /**
//     * 病人原科室名称
//     */
//    private String formerDeptName;
    /**
     * 药物包装序号
     */
    private String serialNo;
    /**
     * 医生确认时间
     */
    private String doctorConfirmTime;
    /**
     * 医生确认人编码
     */
    private String doctorConfirmPerson;
    /**
     * 医生确认人姓名
     */
    private String doctorConfirmPersonName;
    /**
     * 医嘱开始时间
     */
    private String orderStartTime;
    /**
     * 医嘱结束时间
     */
    private String orderEndTime;
    /**
     * 天数
     */
    private String repeatNumber;
    /**
     * 次剂量描述
     */
    private String dosageDescription;
    /**
     * 总药量描述
     */
    private String totalDosageDescription;
    /**
     * 领药量
     */
    private String obtain;
    /**
     * 领药量单位
     */
    private String obtainUnit;
    /**
     * 领药量描述
     */
    private String obtainDescription;
    /**
     * 执行科室ID
     */
    private String execDept;
    /**
     * 执行科室名称
     */
    private String execDeptName;
    /**
     * 处方类型
     */
    private String prescriptionType;
    /**
     * 处方类型名称
     */
    private String prescriptionTypeName;
    /**
     * 药物医保类型编码
     */
    private String medicalInsuranceType;
    /**
     * 药物医保类型名称
     */
    private String medicalInsuranceTypeName;
    /**
     * 是否药观
     */
    private String medViewFlag;
    /**
     * 药物标识编码
     */
    private String drugType;
    
    /**
     * 长期/临时标识
     * */
    private String temporaryFlag;
    
    // $Author :chang_xuewen
    // $Date : 2013/12/03 11:00$
    // [BUG]0040271 ADD BEGIN
    private String orgCode;
	public String getOrgCode() {
		return orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	private String orgName;
	// [BUG]0040271 ADD END
    

    public BigDecimal getOrderSn()
    {
        return orderSn;
    }

    public void setOrderSn(BigDecimal orderSn)
    {
        this.orderSn = orderSn;
    }

    public String getOrderLid()
    {
        return orderLid;
    }

    public String getOrderType()
    {
        return orderType;
    }

    public String getOrderTypeName()
    {
        return orderTypeName;
    }

    public String getMedicineFrenquency()
    {
        return medicineFrenquency;
    }

    public String getMedicineFrenquencyName()
    {
        return medicineFrenquencyName;
    }

    public String getRouteCode()
    {
        return routeCode;
    }

    public String getRouteName()
    {
        return routeName;
    }

    public String getDosage()
    {
        return dosage;
    }

    public String getDosageUnit()
    {
        return dosageUnit;
    }

    public String getTotalDosage()
    {
        return totalDosage;
    }

    public String getTotalDosageUnit()
    {
        return totalDosageUnit;
    }

    public String getDrugCode()
    {
        return drugCode;
    }

    public String getOrderTime()
    {
        return orderTime;
    }

    public String getOrderPerson()
    {
        return orderPerson;
    }

    public String getOrderPersonName()
    {
        return orderPersonName;
    }

    public String getOrderDept()
    {
        return orderDept;
    }
    
    public String getOrderDeptName()
    {
        return orderDeptName;
    }

    public String getParentOrderId()
    {
        return parentOrderId;
    }

    public String getMutexesOrderType()
    {
        return mutexesOrderType;
    }

    public String getMutexesOrderTypeName()
    {
        return mutexesOrderTypeName;
    }

    public String getSkinTestFlag()
    {
        return skinTestFlag;
    }

    public String getUrgency()
    {
        return urgency;
    }

    public String getAdaptive()
    {
        return adaptive;
    }

    public String getComments()
    {
        return comments;
    }

    public List<HerbalFormulaDto> getHerbalFormula()
    {
        return herbalFormula;
    }

//    public String getFormerWardName()
//    {
//        return formerWardName;
//    }
//
//    public String getFormerWard()
//    {
//        return formerWard;
//    }
//
//    public String getFormerDept()
//    {
//        return formerDept;
//    }
//
//    public String getFormerDeptName()
//    {
//        return formerDeptName;
//    }

    public String getSerialNo()
    {
        return serialNo;
    }

    public String getDoctorConfirmPerson()
    {
        return doctorConfirmPerson;
    }

    public String getDoctorConfirmPersonName()
    {
        return doctorConfirmPersonName;
    }

    public String getDoctorConfirmTime()
    {
        return doctorConfirmTime;
    }

    public String getOrderStartTime()
    {
        return orderStartTime;
    }

    public String getOrderEndTime()
    {
        return orderEndTime;
    }
    
    public String getRepeatNumber()
    {
        return repeatNumber;
    }

    public String getDosageDescription()
    {
        return dosageDescription;
    }

    public String getTotalDosageDescription()
    {
        return totalDosageDescription;
    }

    public String getObtain()
    {
        return obtain;
    }

    public String getObtainUnit()
    {
        return obtainUnit;
    }

    public String getObtainDescription()
    {
        return obtainDescription;
    }

    public String getExecDept()
    {
        return execDept;
    }

    public String getExecDeptName()
    {
        return execDeptName;
    }

    public String getPrescriptionType()
    {
        return prescriptionType;
    }

    public String getPrescriptionTypeName()
    {
        return prescriptionTypeName;
    }

    public String getMedicalInsuranceType()
    {
        return medicalInsuranceType;
    }

    public String getMedicalInsuranceTypeName()
    {
        return medicalInsuranceTypeName;
    }

    public String getMedViewFlag()
    {
        return medViewFlag;
    }

    public String getDrugType()
    {
        return drugType;
    }

    public String getOrderSeqnum() {
		return orderSeqnum;
	}

	public String getDrugName() {
		return drugName;
	}

	public String getOrdExecTypeCode() {
		return ordExecTypeCode;
	}

	@Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        // $Author :tong_meng
        // $Date : 2012/10/17 14:00$
        // [BUG]0015092 DELETE BEGIN
        /*builder.append("MessageId=");
        builder.append(getMessage().getId()+",");*/
        // [BUG]0015092 DELETE END
        builder.append("MedicationOrderDto [orderSn=");
        builder.append(orderSn);
        builder.append(", orderLid=");
        builder.append(orderLid);
        builder.append(", orderType=");
        builder.append(orderType);
        builder.append(", orderTypeName=");
        builder.append(orderTypeName);
        builder.append(", herbalFormula=");
//      builder.append(herbalFormula);
        builder.append("]");
        return builder.toString();
    }

	public String getTemporaryFlag() {
		return temporaryFlag;
	}

}
