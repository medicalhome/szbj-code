package com.founder.cdr.hl7.dto.poorin200901uv02;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.founder.cdr.hl7.dto.BaseDto;

public class ExaminationApplication extends BaseDto
{
    /**
     * 申请单编号
     */
    private String requestNo;

    /**
     * 申请日期
     */
    private String requestDate;

    // $Author :jin_peng
    // $Date : 2012/9/18 16:18$
    // [BUG]0009718 DELETE BEGIN
    /**
     * 医嘱类型代码
     */
    // @NotEmpty(message="{ExaminationApplication.orderType}")
    private String orderType;

    /**
     * 医嘱类型名称
     */
    // @NotEmpty(message="{ExaminationApplication.orderTypeName}")
    private String orderTypeName;

    /**
     * 执行科室ID
     */
    // @NotEmpty(message="{ExaminationApplication.executiveDept}")
    private String executiveDept;

    /**
     * 执行科室名称
     */
    // @NotEmpty(message="{ExaminationApplication.executiveDeptName}")
    private String executiveDeptName;

    // [BUG]0009718 DELETE END

    // $Author :tong_meng
    // $Date : 2012/7/2 10:33$
    // [BUG]0007418 ADD BEGIN
    /**
     * 标本号
     */
    private String sampleNo;
    // [BUG]0007418 ADD END
    
    /**
     * 标本类型
     */
    private String sampleType;

    /**
     * 标本要求
     */
    private String sampleRequirement;

    /**
     * 注意事项
     */
    private String examNotice;

    // $Author :jin_peng
    // $Date : 2012/9/18 16:18$
    // [BUG]0009718 DELETE BEGIN
    /**
     * 检查医嘱
     */
    // @NotEmpty(message="{ExaminationApplication.examinationOrderDtos}")
    private List<ExaminationOrderDto> examinationOrderDtos;

    // [BUG]0009718 DELETE END
    
    private String requestDetails;

    /**
     * 检查费用
     * */
    private String charge;
    
    /*
     * v2消息中obx段
     * */
    private List<Obxs> obxs;
    
    
    public List<Obxs> getObxs() {
		return obxs;
	}

	public String getRequestNo()
    {
        return requestNo;
    }

    public String getRequestDate()
    {
        return requestDate;
    }

    public String getOrderType()
    {
        return orderType;
    }

    public String getOrderTypeName()
    {
        return orderTypeName;
    }

    public String getExecutiveDept()
    {
        return executiveDept;
    }

    public String getExecutiveDeptName()
    {
        return executiveDeptName;
    }

    public String getSampleNo()
    {
        return sampleNo;
    }

    public String getSampleType()
    {
        return sampleType;
    }

    public String getSampleRequirement()
    {
        return sampleRequirement;
    }

    public String getExamNotice()
    {
        return examNotice;
    }

    public List<ExaminationOrderDto> getExaminationOrderDtos()
    {
        return examinationOrderDtos;
    }
    
    public String getRequestDetails()
    {
        return requestDetails;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("ExaminationApplication [requestNo=");
        builder.append(requestNo);
        builder.append(", orderType=");
        builder.append(orderType);
        builder.append(", orderTypeName=");
        builder.append(orderTypeName);
        builder.append(", examinationOrderDtos=");
        builder.append(examinationOrderDtos);
        builder.append("]");
        return builder.toString();
    }

	public String getCharge() {
		return charge;
	}

	public void setCharge(String charge) {
		this.charge = charge;
	}

}
