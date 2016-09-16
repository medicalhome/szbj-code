package com.founder.cdr.hl7.dto;

import org.hibernate.validator.constraints.NotEmpty;

import com.founder.cdr.hl7.dto.poorin200901uv14.GroupPOORIN200901UV14;

public class Orders extends BaseDto
{
    /**
     * 项目序号
     */
    private String sequence;

    /**
     * 医嘱号
     */
    private String orderLid;
    
    /**
     * 医嘱序号
     */
    private String orderSeq;

    /**
     * 医嘱的类型
     */
    @NotEmpty(message = "{Orders.orderType}")
    private String orderType;

    /**
     * 医嘱的类型名称
     */
    private String orderTypeName;

    /**
     * 药物编码
     */
    private String drugCode;

    /**
     * 药物名称
     */
    private String drugName;

    /**
     * 撤销或停止时间
     */
    private String cancelOrStopTime;

    /**
     * 撤销或停止人ID
     */
    private String cancelOrStopPerson;

    /**
     * 撤销或停止人姓名
     */
    private String cancelOrStopPersonName;

    // Begin guo_hongyan
    /**
     * 执行时间
     */
    private String beginTime;

    /**
     * 执行人编码
     */
    private String operatorId;

    /**
     * 审核时间
     */
    private String reviewerTime;

    /**
     * 审核人ID
     */
    private String reviewerId;

    /**
     * 审核人姓名
     */
    private String reviewerName;

    /**
     * 执行人姓名
     */
    private String operatorName;

    /**
     * 执行科室ID
     */
    private String execDept;

    /**
     * 执行科室名称
     */
    private String execDeptName;

    /**
     * 执行结果
     */
    private String execResult;

    // End guo_hongyan
    
    // add by jin_peng
    /**
     * 申请单号
     */
    private String requestNo;
    /**
     * 标本条码号
     */
    private String sampleCode;
    /**
     * 医嘱状态
     */
    //@NotEmpty(message = "{Orders.orderStatus}")
    private String orderStatus;
    /**
     * 医嘱状态名称
     */
    private String orderStatusName;


	// end jin_peng
    /**
     * 互斥医嘱号
     */
    private String mutexesOrderLid;
    /**
     * 互斥医嘱类型编码
     */
    private String mutexesOrderType;
    /**
     * 互斥医嘱类型名称
     */
    private String mutexesOrderTypeName;
    /**
     * 报告号
     */
    private String reportNo;

    public String getSequence()
    {
        return sequence;
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

    public String getDrugCode()
    {
        return drugCode;
    }

    public String getDrugName()
    {
        return drugName;
    }

    public String getCancelOrStopTime()
    {
        return cancelOrStopTime;
    }

    public String getCancelOrStopPerson()
    {
        return cancelOrStopPerson;
    }

    public String getCancelOrStopPersonName()
    {
        return cancelOrStopPersonName;
    }

    public String getBeginTime()
    {
        return beginTime;
    }

    public String getOperatorId()
    {
        return operatorId;
    }

    public String getOperatorName()
    {
        return operatorName;
    }

    public String getReviewerTime()
    {
        return reviewerTime;
    }

    public String getReviewerId()
    {
        return reviewerId;
    }

    public String getReviewerName()
    {
        return reviewerName;
    }

    public String getExecDept()
    {
        return execDept;
    }

    public String getExecDeptName()
    {
        return execDeptName;
    }

    public String getExecResult()
    {
        return execResult;
    }

    public String getSampleCode()
    {
        return sampleCode;
    }

    public String getOrderStatus()
    {
        return orderStatus;
    }
    
    public String getRequestNo()
    {
        return requestNo;
    }

    public String getOrderStatusName()
    {
        return orderStatusName;
    }
    
    public String getMutexesOrderLid()
    {
        return mutexesOrderLid;
    }

    public String getMutexesOrderType()
    {
        return mutexesOrderType;
    }

    public String getMutexesOrderTypeName()
    {
        return mutexesOrderTypeName;
    }
    
    public String getReportNo()
    {
        return reportNo;
    }

    public String getOrderSeq() {
		return orderSeq;
	}
    
    public void setOrderStatusName(String orderStatusName) {
		this.orderStatusName = orderStatusName;
	}

	// $Author :chang_xuewen
    // $Date : 2013/06/05 16:00$
    // [BUG]0033373 MODIFY BEGIN
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Orders [orderLid=");
        builder.append(orderLid);
        builder.append(", orderSeq=");
        builder.append(orderSeq);
        builder.append(", requestNo=");
        builder.append(requestNo);
        builder.append(", reportNo=");
        builder.append(reportNo);
        builder.append("]");
        return builder.toString();
    }
    // [BUG]0033373 MODIFY END

}
