package com.founder.cdr.hl7.dto.poorin200901uv05;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.founder.cdr.hl7.dto.BaseDto;
import com.founder.cdr.util.DateUtils;

public class POORIN200901UV05 extends BaseDto
{
    /**
     * 域ID
     */
    @NotEmpty(message = "{POORIN200901UV05.patientDomain}")
    private String patientDomain;

    /**
     * 门诊患者ID
     */
    @NotEmpty(message = "{POORIN200901UV05.patientLid}")
    private String patientLid;

    /**
     * 就诊次数
     */
 //   @NotEmpty(message = "{POORIN200901UV05.visitTimes}")
    private String visitTimes;

    /**
     * 医嘱内容
     */
    @NotEmpty(message = "{POORIN200901UV05.careOrderBatch}")
    private List<CareOrderBatch> careOrderBatch;

    /**
     * 触发事件
     */
    @NotEmpty(message = "{POORIN200901UV05.triggerEventCode}")
    private String triggerEventCode;

    /**
     * 病区id
     */
    private String wardsId;

    /**
     * 病區名稱
     */
    private String wardsName;

    /**
     * 床号
     */
    private String bedNo;

    /**
     * 确认人ID
     */
    private String confirmPerson;

    /**
     * 确认人姓名
     */
    private String confirmPersonName;

    /**
     * 确认时间
     */
    private String confirmTime;
    // $Author :chang_xuewen
    // $Date : 2014/1/22 10:30$
    // [BUG]0042086 MODIFY BEGIN 
    /**
     * 医疗机构编码
     */
    /*@NotEmpty(message = "{POORIN200901UV05.orgCode}")*/
    private String orgCode;

	/**
     * 医疗机构名称
     */
    /*@NotEmpty(message = "{POORIN200901UV05.orgName}")*/
    private String orgName;
    
    /**
     * 医嘱组套编码
     */
    private String orderSetCode;
    
    /**
     * 医嘱描述
     */
    private String orderDescribe;
    
    /**
     * 就诊流水号
     */
    @NotEmpty(message = "{POORIN200901UV05.visitOrdNo}")
    private String visitOrdNo;
    
    /**
     * 就诊类别编码
     */
    @NotEmpty(message = "{POORIN200901uv05.visitType}")
    private String visitType;
    /**
     * 就诊类别名称
     */
   // @NotEmpty(message = "{POORIN200901uv05.visitTypeName}")
    private String visitTypeName;

    
    
    public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
    // [BUG]0042086 MODIFY END 
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

    public List<CareOrderBatch> getCareOrderBatch()
    {
        return careOrderBatch;
    }

    public String getTriggerEventCode()
    {
        return triggerEventCode;
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

    public String getConfirmPerson()
    {
        return confirmPerson;
    }

    public String getConfirmPersonName()
    {
        return confirmPersonName;
    }

    public String getConfirmTime()
    {
        return confirmTime;
    }

    public String getOrgCode()
    {
        return orgCode;
    }

    public String getOrgName()
    {
        return orgName;
    }

    public String getOrderSetCode() {
		return orderSetCode;
	}

	public String getOrderDescribe() {
		return orderDescribe;
	}


	public String getVisitOrdNo() {
		return visitOrdNo;
	}

	public String getVisitType() {
		return visitType;
	}


	public String getVisitTypeName() {
		return visitTypeName;
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
        builder.append("POORIN200901UV05 [triggerEventCode=");
        builder.append(triggerEventCode);
        builder.append(", patientDomain=");
        builder.append(patientDomain);
        builder.append(", patientLid=");
        builder.append(patientLid);
        builder.append(", visitTimes=");
        builder.append(visitTimes);
        builder.append(", orgCode=");
        builder.append(orgCode);
        builder.append(", orgName=");
        builder.append(orgName);
        builder.append(", careOrderBatch=");
        builder.append(careOrderBatch);
        builder.append(", orderSetCode=");
        builder.append(orderSetCode);
        builder.append(", orderDescribe");
        builder.append(orderDescribe);
        builder.append(", visitOrdNo=");
        builder.append(visitOrdNo);
        builder.append(", visitType=");
        builder.append(visitType);
        builder.append(", visitTypeName=");
        builder.append(visitTypeName);
        builder.append("]");
        return builder.toString();
    }


	
}
