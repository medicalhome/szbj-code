package com.founder.cdr.hl7.dto.poorin200901uv08;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.founder.cdr.hl7.dto.BaseDto;
import com.founder.cdr.hl7.dto.MedicationOrderDto;
import com.founder.cdr.util.DateUtils;

public class POORIN200901UV08 extends BaseDto
{
    /**
     * 患者ID
     */
    @NotEmpty(message = "{POORIN200901UV08.patientLid}")
    private String patientLid;

    /**
     * 门诊号
     */
    private String outpatientNo;

    /**
     * 域ID
     */
    @NotEmpty(message = "{POORIN200901UV08.patientDomain}")
    private String patientDomain;

    /**
     * 病区ID
     */
    private String wardsId;

    /**
     * 床号
     */
    private String bedNo;

    /**
     * 医嘱内容
     */
    @NotEmpty(message = "{POORIN200901UV08.medicationOrderBatch}")
    private List<MedicationOrderDto> medicationOrderBatch;

    /**
     * 就诊次数
     */
//    @NotEmpty(message = "{POORIN200901UV08.visitTimes}")
    private String visitTimes;
    
    /**
     * 就诊流水号
     */
    @NotEmpty(message = "{POORIN200901UV08.visitOrdNo}")
    private String visitOrdNo;
    
    /**
     * 就诊类型
     */
    @NotEmpty(message = "{POORIN200901UV08.visitTypeCode}")
    private String visitTypeCode;

    /**
     * 医嘱组套编码
     */
    private String orderSetCode;
    
    /**
     * 医嘱描述
     */
    private String orderDescribe;
    
    /**
     * 医嘱开立科室ID
     */
    private String orderDept;
    /**
     * 医嘱开立科室
     */
    private String orderDeptName;
    
    /**
     * 确认护士编码
     */
//    @NotEmpty(message = "{POORIN200901UV08.nurseConfirmPerson}")
    private String nurseConfirmPerson;

    /**
     * 确认护士姓名
     */
//    @NotEmpty(message = "{POORIN200901UV08.nurseConfirmPersonName}")
    private String nurseConfirmPersonName;

    /**
     * 护士确认时间
     */
//    @NotEmpty(message = "{POORIN200901UV08.nurseConfirmTime}")
    private String nurseConfirmTime;

    /**
     * 触发事件
     */
    @NotEmpty(message = "{POORIN200901UV08.triggerEventCode}")
    private String triggerEventCode;
    
    /*@NotEmpty(message = "{POORIN200901UV08.orgCode}")*/
    private String orgCode;
    /*@NotEmpty(message = "{POORIN200901UV08.orgName}")*/
    private String orgName;

    public String getOrgCode() {
		return orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public String getPatientLid()
    {
        return patientLid;
    }

    public String getOutpatientNo()
    {
        return outpatientNo;
    }

    public String getPatientDomain()
    {
        return patientDomain;
    }

    public String getWardsId()
    {
        return wardsId;
    }

    public String getBedNo()
    {
        return bedNo;
    }

    public List<MedicationOrderDto> getMedicationOrderBatch()
    {
        return medicationOrderBatch;
    }

    public String getVisitTimes()
    {
        return visitTimes;
    }

    public String getNurseConfirmPerson()
    {
        return nurseConfirmPerson;
    }

    public String getNurseConfirmPersonName()
    {
        return nurseConfirmPersonName;
    }

    public String getNurseConfirmTime()
    {
        return nurseConfirmTime;
    }

    public String getTriggerEventCode()
    {
        return triggerEventCode;
    }

    public String getVisitOrdNo() {
		return visitOrdNo;
	}

	public String getVisitTypeCode() {
		return visitTypeCode;
	}

	public String getOrderSetCode() {
		return orderSetCode;
	}

	public String getOrderDescribe() {
		return orderDescribe;
	}
	 public String getOrderDept()
	    {
	        return orderDept;
	    }
	    
	    public String getOrderDeptName()
	    {
	        return orderDeptName;
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
        builder.append("POORIN200901UV08 [triggerEventCode=");
        builder.append(triggerEventCode);
        builder.append(", patientLid=");
        builder.append(patientLid);
        builder.append(", outpatientNo=");
        builder.append(outpatientNo);
        builder.append(", patientDomain=");
        builder.append(patientDomain);
        builder.append(", medicationOrderBatch=");
        builder.append(medicationOrderBatch);
        builder.append(", visitTimes=");
        builder.append(visitTimes);
        builder.append(", visitOrdNo=");
        builder.append(visitOrdNo);
        builder.append(", visitTypeCode=");
        builder.append(visitTypeCode);
        builder.append("]");
        return builder.toString();
    }


}
