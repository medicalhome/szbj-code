package com.founder.cdr.hl7.dto;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.founder.cdr.hl7.dto.OrderItems;

public class OtherOrder extends BaseDto
{
    /**
     * 域ID
     */
    @NotEmpty(message = "{OtherOrder.patientDomain}")
    private String patientDomain;
    /**
     * 门诊系统患者ID
     */
    @NotEmpty(message = "{OtherOrder.patientLid}")
    private String patientLid;
    /**
     * 床号
     */
    private String bedNo;
    // $Author :tong_meng
    // $Date : 2012/9/17 11:30$
    // [BUG]0009718 ADD BEGIN
    /**
     * 患者姓名
     */
//    @NotEmpty(message = "{OtherOrder.patientName}")
    private String patientName;
    /**
     * 性别代码
     */
//    @NotEmpty(message = "{OtherOrder.genderCode}")
    private String genderCode;
    /**
     * 出生日期
     */
//    @NotEmpty(message = "{OtherOrder.birthday}")
    // [BUG]0009718 ADD END
    private String birthday;
    /**
     * 年龄
     */
    private String age;
    // $Author :tong_meng
    // $Date : 2012/9/17 11:30$
    // [BUG]0009718 ADD BEGIN
    /**
     * 确认时间(护士)
     */
   // @NotEmpty(message = "{OtherOrder.nurseConfirmTime}")
    private String nurseConfirmTime;
    /**
     * 确认人标识(护士)
     */
   // @NotEmpty(message = "{OtherOrder.nurseConfirmPerson}")
    private String nurseConfirmPerson;
    /**
     * 确认人姓名(护士)
     */
   // @NotEmpty(message = "{OtherOrder.nurseConfirmPersonName}")
    private String nurseConfirmPersonName;
    // [BUG]0009718 ADD END
    /**
     * 医嘱项信息
     */
    @NotEmpty(message = "{OtherOrder.OrderItems}")
    private List<OrderItems> OrderItems;
    /**
     * 就诊次数
     */
  //  @NotEmpty(message = "{OtherOrder.visitTimes}")
    private String visitTimes;
    /**
     * 患者所在科室标识
     */
    private String deptNo;
    /**
     * 患者所在科室名称
     */
    private String deptName;
    /**
     * 患者所在病区ID
     */
    private String wardNo;
    /**
     * 患者所在病区名
     */
    private String wardName;
    /**
     * 医嘱描述
     */
    private String orderDescibe;
    /**
     * 医嘱组套编码
     */
    private String orderSetCode;
    /**
     * 就诊流水号
     */
    @NotEmpty(message = "{OtherOrder.visitOrdNo}")
    private String visitOrdNo;
    
    /**
     * 就诊类别编码
     */
    @NotEmpty(message = "{OtherOrder.visitType}")
    private String visitType;
    /**
     * 就诊类别名称
     */
    //@NotEmpty(message = "{OtherOrder.visitTypeName}")
    private String visitTypeName;
 
    
    public String getWardNo()
    {
        return wardNo;
    }

    public String getWardName()
    {
        return wardName;
    }

    public String getDeptNo()
    {
        return deptNo;
    }

    public String getDeptName()
    {
        return deptName;
    }

    public String getPatientDomain()
    {
        return patientDomain;
    }

    public String getPatientLid()
    {
        return patientLid;
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

    public String getBirthday()
    {
        return birthday;
    }

    public String getAge()
    {
        return age;
    }

    public List<OrderItems> getOrderItems()
    {
        return OrderItems;
    }

    public String getNurseConfirmTime()
    {
        return nurseConfirmTime;
    }

    public String getNurseConfirmPerson()
    {
        return nurseConfirmPerson;
    }

    public String getNurseConfirmPersonName()
    {
        return nurseConfirmPersonName;
    }

    public String getVisitTimes()
    {
        return visitTimes;
    }

    public String getOrderDescibe() {
		return orderDescibe;
	}

	public String getOrderSetCode() {
		return orderSetCode;
	}

	public String getVisitOrdNo() {
		return visitOrdNo;
	}

	public String getVisitType() {
		return visitType;
	}

	public void setOrderDescibe(String orderDescibe) {
		this.orderDescibe = orderDescibe;
	}


	@Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("OtherOrder [patientDomain=");
        builder.append(patientDomain);
        builder.append(", patientLid=");
        builder.append(patientLid);
        builder.append(", OrderItems=");
        builder.append(OrderItems);
        builder.append(", visitTimes=");
        builder.append(visitTimes);
        builder.append(", orderDescibe=");
        builder.append(orderDescibe);
        builder.append(", orderSetCode=");
        builder.append(orderSetCode);
        builder.append(", visitOrdNo=");
        builder.append(visitOrdNo);
        builder.append(", visitType=");
        builder.append(visitType);
        builder.append("]");
        return builder.toString();
    }

    
}
