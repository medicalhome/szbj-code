package com.yly.cdr.hl7.dto.poorin200901uv14;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.yly.cdr.hl7.dto.BaseDto;
import com.yly.cdr.hl7.dto.Orders;
import com.yly.cdr.util.DateUtils;

public class POORIN200901UV14 extends BaseDto
{
    /**
     * 新增更新标志
     */
    @NotEmpty(message = "{POORIN200901UV14.newUpFlag}")
    private String newUpFlag;

    /**
     * 域ID
     */
//    @NotEmpty(message = "{POORIN200901UV14.patientDomain}")
    private String patientDomain;

    // $Author :jin_peng
    // $Date : 2013/10/12 12:57$
    // [BUG]0037984 MODIFY BEGIN
    /**
     * 患者ID
     */
    // @NotEmpty(message = "{POORIN200901UV14.patientLid}")
    private String patientLid;

    /**
     * 就诊号
     */
    private String visitNo;

    // [BUG]0037984 MODIFY END

    /**
     * 操作日期
     */
//    @NotEmpty(message = "{POORIN200901UV14.operationDate}")
    private String operationDate;

    /**
     * 操作人编码
     */
//    @NotEmpty(message = "{POORIN200901UV14.operationCode}")
    private String operationCode;

    /**
     * 操作人姓名
     */
    private String operationName;

    /**
     * 执行科室编码
     */
//    @NotEmpty(message = "{POORIN200901UV14.execDept}")
    private String execDept;

    /**
     * 执行科室名称
     */
//    @NotEmpty(message = "{POORIN200901UV14.execDeptName}")
    private String execDeptName;

    /**
     * 医嘱状态信息
     */
    private List<Orders> orderStatusInf;

    // $Author :jin_peng
    // $Date : 2013/10/12 12:57$
    // [BUG]0037984 MODIFY BEGIN
    // 暂时不验证就诊次数非空
    /**
     * 就诊次数
     */
    // @NotEmpty(message = "{POORIN200901UV14.visitTimes}")
    private String visitTimes;
    
    /**
     * 就诊类别编码
     */
//    @NotEmpty(message = "{POORIN200901UV14.visitTypeCode}")
    private String visitTypeCode;
    
    /**
     * 就诊流水号
     */
//    @NotEmpty(message = "{POORIN200901UV14.visitOrdNo}")
    private String visitOrdNo;

    // [BUG]0037984 MODIFY END

    /**
     * 发送者
     */
    @NotEmpty(message = "{POORIN200901UV14.sender}")
    private String sender;
    // $Author :chang_xuewen
    // $Date : 2014/1/22 10:30$
    // [BUG]0042086 MODIFY BEGIN
    /**
     *  医疗组织机构代码 
     */
    /*@NotEmpty(message = "{POORIN200901UV14.orgCode}")*/
    private String orgCode;

	/**
     *  医疗组织机构名称 
     */
    /*@NotEmpty(message = "{POORIN200901UV14.orgName}")*/
    private String orgName;
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

    public String getPatientDomain()
    {
        return patientDomain;
    }

    public String getPatientLid()
    {
        return patientLid;
    }

    public String getOperationDate()
    {
        return operationDate;
    }

    public String getOperationCode()
    {
        return operationCode;
    }

    public String getOperationName()
    {
        return operationName;
    }

    public String getExecDept()
    {
        return execDept;
    }

    public String getExecDeptName()
    {
        return execDeptName;
    }

    public List<Orders> getOrderStatusInf()
    {
        return orderStatusInf;
    }

    public String getVisitTimes()
    {
        return visitTimes;
    }

    public String getSender()
    {
        return sender;
    }

    public String getVisitNo()
    {
        return visitNo;
    }
    

    public String getVisitTypeCode() {
		return visitTypeCode;
	}

	public String getVisitOrdNo() {
		return visitOrdNo;
	}

	// $Author :chang_xuewen
    // $Date : 2013/06/05 16:00$
    // [BUG]0033373 MODIFY BEGIN
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("MessageId=");
        builder.append(getMessage().getId() + ",");
        builder.append("Datetime=");
        builder.append(DateUtils.dateToString(getMessage().getDatetime(), null)
            + ",");
        builder.append("POORIN200901UV14 [newUpFlag=");
        builder.append(newUpFlag);
        builder.append(", patientDomain=");
        builder.append(patientDomain);
        builder.append(", patientLid=");
        builder.append(patientLid);
        builder.append(", orgCode=");
        builder.append(orgCode);
        builder.append(", orgName=");
        builder.append(orgName);
        builder.append(", orderStatusInf=");
        builder.append(orderStatusInf);
        builder.append(", visitTimes=");
        builder.append(visitTimes);
        builder.append(", visitTypeCode=");
        builder.append(visitTypeCode);
        builder.append(", visitOrdNo=");
        builder.append(visitOrdNo);
        builder.append(", visitNo=");
        builder.append(visitNo);
        builder.append("]");
        return builder.toString();
    }
    // [BUG]0033373 MODIFY END

    public String getOrgCode()
    {
        return orgCode;
    }

    public String getOrgName()
    {
        return orgName;
    }

    public void setPatientDomain(String patientDomain){
    	this.patientDomain = patientDomain;
    }
}
