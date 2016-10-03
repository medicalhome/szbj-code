package com.yly.cdr.dto;

import java.util.Date;

/**
 * V05-703:手术申请详细
 * author:黄洁玉
 * @return
 */

public class ProcedureRequestDto
{
    // 申请单内部序列号
    private String requestSn;

    // 就诊内部序列号
    private String visitSn;

    // 医嘱内部序列号
    private String orderSn;

    // 申请单编号
    private String requestNo;

    // 手术名称
    private String operationName;

    // 手术名称代码
    private String operationCode;

    // 手术等级
    private String procedureLevel;

    // 执行时间
    private Date planExecTime;

    // 注意事项
    private String precautions;

    // 描述
    private String description;

    // 执行科室
    private String execDept;

    // 申请机构(科室)
    private String orderDept;

    // 申请医生
    private String requestPerson;

    // 申请日期
    private Date requestDate;
    
    // 申请日期
    private Date orderTime;

    // 申请原因
    private String requestReason;
    
    // 手术性质名称
    private String operationPropertyName;
    
    // 麻醉方式名称
    private String anesthesiaName;
    
    // 手术台
    private String operationTable;
    
    // 手术医师名称
    private String operatorName;
    
    // 医疗机构
    private String orgCode;

    public String getRequestSn()
    {
        return requestSn;
    }

    public void setRequestSn(String requestSn)
    {
        this.requestSn = requestSn;
    }

    public String getVisitSn()
    {
        return visitSn;
    }

    public void setVisitSn(String visitSn)
    {
        this.visitSn = visitSn;
    }

    public String getOrderSn()
    {
        return orderSn;
    }

    public void setOrderSn(String orderSn)
    {
        this.orderSn = orderSn;
    }

    public String getRequestNo()
    {
        return requestNo;
    }

    public void setRequestNo(String requestNo)
    {
        this.requestNo = requestNo;
    }

    public String getOperationName()
    {
        return operationName;
    }

    public void setOperationName(String operationName)
    {
        this.operationName = operationName;
    }

    public String getOperationCode()
    {
        return operationCode;
    }

    public void setOperationCode(String operationCode)
    {
        this.operationCode = operationCode;
    }

    public String getProcedureLevel()
    {
        return procedureLevel;
    }

    public void setProcedureLevel(String procedureLevel)
    {
        this.procedureLevel = procedureLevel;
    }

    public Date getPlanExecTime()
    {
        return planExecTime;
    }

    public void setPlanExecTime(Date planExecTime)
    {
        this.planExecTime = planExecTime;
    }

    public String getPrecautions()
    {
        return precautions;
    }

    public void setPrecautions(String precautions)
    {
        this.precautions = precautions;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getExecDept()
    {
        return execDept;
    }

    public void setExecDept(String execDept)
    {
        this.execDept = execDept;
    }

    public String getOrderDept()
    {
        return orderDept;
    }

    public void setOrderDept(String orderDept)
    {
        this.orderDept = orderDept;
    }

    public String getRequestPerson()
    {
        return requestPerson;
    }

    public void setRequestPerson(String requestPerson)
    {
        this.requestPerson = requestPerson;
    }

    public Date getRequestDate()
    {
        return requestDate;
    }

    public void setRequestDate(Date requestDate)
    {
        this.requestDate = requestDate;
    }

    public String getRequestReason()
    {
        return requestReason;
    }

    public void setRequestReason(String requestReason)
    {
        this.requestReason = requestReason;
    }

	public String getOperationPropertyName() {
		return operationPropertyName;
	}

	public void setOperationPropertyName(String operationPropertyName) {
		this.operationPropertyName = operationPropertyName;
	}

	public String getAnesthesiaName() {
		return anesthesiaName;
	}

	public void setAnesthesiaName(String anesthesiaName) {
		this.anesthesiaName = anesthesiaName;
	}

	public String getOperationTable() {
		return operationTable;
	}

	public void setOperationTable(String operationTable) {
		this.operationTable = operationTable;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
    
}
