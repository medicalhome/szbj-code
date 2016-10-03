package com.yly.cdr.hl7.dto.poorin200901uv12;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.yly.cdr.hl7.dto.OrderDto;
import com.yly.cdr.hl7.dto.TreatmentItem;
import com.yly.cdr.util.DateUtils;

public class POORIN200901UV12 extends OrderDto
{
    /**
     * 新增更新标志
     */
    @NotEmpty(message = "{POORIN200901UV12.newUpFlg}")
    private String newUpFlg;

    /**
     * 域ID
     */
    @NotEmpty(message = "{POORIN200901UV12.patientDomain}")
    private String patientDomain;

    /**
     * 患者本地Lid
     */
    @NotEmpty(message = "{POORIN200901UV12.patientLid}")
    private String patientLid;

    /**
     * 就诊号
     */
    private String outpatientNo;

    /**
     * 病人当前病区编码
     */
    private String wards;

    /**
     * 病人当前病区名称
     */
    private String wardsName;

    /**
     * 床位号
     */
    private String bedNo;

    /**
     * 患者姓名
     */
    private String patientName;

    /**
     * 性别代码
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
     * 病人当前科室编码
     */
    private String dept;

    /**
     * 病人当前科室名称
     */
    private String deptName;

    /**
     * 护士确认人
     */
    private String nurseConfirmPerson;

    /**
     * 护士确认姓名
     */
    private String nurseConfirmPersonName;

    /**
     * 护士确认时间
     */
    private String nurseConfirmTime;

    /**
     * 诊疗项目
     */
    private List<TreatmentItem> treatmentItem;

    /**
     * 就诊次数
     */
//    @NotEmpty(message = "{POORIN200901UV12.visitTimes}")
    private String visitTimes;
    // $Author :chang_xuewen
    // $Date : 2014/1/22 10:30$
    // [BUG]0042086 MODIFY BEGIN
    /**
     * 医疗机构代码
     */
    /*@NotEmpty(message = "{POORIN200901UV12.orgCode}")*/
    private String orgCode;

	/**
     * 医疗机构名称
     */
    /*@NotEmpty(message = "{POORIN200901UV12.orgName}")*/
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
    @NotEmpty(message = "{POORIN200901UV12.visitOrdNo}")
    private String visitOrdNo;
    
    /**
     * 就诊类别编码
     */
    @NotEmpty(message = "{POORIN200901UV12.visitType}")
    private String visitType;
    /**
     * 就诊类别名称
     */
    //@NotEmpty(message = "{POORIN200901UV12.visitTypeName}")
    private String visitTypeName;
    
    
    public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
    // [BUG]0042086 MODIFY BEGIN
    public String getNewUpFlg()
    {
        return newUpFlg;
    }

    public String getPatientDomain()
    {
        return patientDomain;
    }

    public String getPatientLid()
    {
        return patientLid;
    }

    public String getOutpatientNo()
    {
        return outpatientNo;
    }

    public String getWards()
    {
        return wards;
    }

    public String getWardsName()
    {
        return wardsName;
    }

    public String getBedNo()
    {
        return bedNo;
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

    public String getDept()
    {
        return dept;
    }

    public String getDeptName()
    {
        return deptName;
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

    public List<TreatmentItem> getTreatmentItem()
    {
        return treatmentItem;
    }

    public String getVisitTimes()
    {
        return visitTimes;
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
        builder.append("POORIN200901UV12 [newUpFlg=");
        builder.append(newUpFlg);
        builder.append(", patientDomain=");
        builder.append(patientDomain);
        builder.append(", patientLid=");
        builder.append(patientLid);
        builder.append(", orgCode=");
        builder.append(orgCode);
        builder.append(", orgName=");
        builder.append(orgName);
        builder.append(", treatmentItem=");
        builder.append(treatmentItem);
        builder.append(", visitTimes=");
        builder.append(visitTimes);
        builder.append(", orderSetCode=");
        builder.append(orderSetCode);
        builder.append(", orderDescribe=");
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
