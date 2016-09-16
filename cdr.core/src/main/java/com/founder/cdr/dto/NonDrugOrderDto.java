package com.founder.cdr.dto;

import java.util.Date;

/**
 * 
 * [FUN]V05-101非药物医嘱列表
 * @version 1.0, 2012/03/12  
 * @author 金鹏
 * @since 1.0
 * 
 */
public class NonDrugOrderDto
{
	// 诊疗医嘱
    public static final int TREATMENT_ORDER = 1;

    // 护理医嘱
    public static final int CARE_ORDER = 2;

    // 手术医嘱
    public static final int PROCEDURE_ORDER = 3;

    // 检查医嘱
    public static final int EXAMINATION_ORDER = 4;

    // 检验医嘱
    public static final int LAB_ORDER = 5;

    // 检验医嘱
    public static final int NURSING_OPERATION = 6;

    // 其他医嘱
    public static final int GENERAL_ORDER = 6;

    // 会诊医嘱
    public static final int CONSULTATION_ORDER = 7;
    
   //患者域
  	private String patientDomain; 
  	
  //就诊类型
  	private String visitTypeCode; 

    // 病区ID
    private String wardsId;

    private String wardName;

    // 医嘱编号
    private String orderSn;

    // 医嘱类型
    private String orderType;

    // 医嘱名称
    private String orderName;

    // 医嘱类型名称
    private String orderTypeName;

    // 开嘱时间
    private Date orderTime;

    // 取消时间
    private Date cancelTime;

    // 开嘱科室
    private String orderDept;

    private String orderDeptName;

    // 开嘱人
    private String orderPerson;

    // 开嘱人姓名
    private String orderPersonName;

    // 确认人
    private String confirmPerson;

    // 确认人姓名
    private String confirmPersonName;

    // 执行科室
    private String execDept;

    private String execDeptName;

    // 患者编号
    private String patientSn;

    // 长期或临时
    private String temporaryFlag;

    // 确认时间
    private Date confirmTime;

    // 取消人
    private String cancelPerson;

    // 取消人名称
    private String cancelPersonName;

    // 医嘱标识
    private String orderFlag;

    // 字符类型 开嘱开始时间
    private String orderStrStartTime;

    // 字符类型 结束时间
    private String orderStrEndTime;

    // 字符类型 确认时间
    private String conStrfirmTime;

    // 字符类型 取消时间
    private String cancelStrTime;

    // 字符类型 医嘱标题
    private String orderTitle;

    // 字符类型 医嘱详细路径
    private String orderPath;
    
    private String orgCode;
    private String orgName;

    public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrderType()
    {
        return orderType;
    }

    public void setOrderType(String orderType)
    {
        this.orderType = orderType;
    }

    public String getOrderName()
    {
        return orderName;
    }

    public void setOrderName(String orderName)
    {
        this.orderName = orderName;
    }

    public String getOrderTypeName()
    {
        return orderTypeName;
    }

    public void setOrderTypeName(String orderTypeName)
    {
        this.orderTypeName = orderTypeName;
    }

    public Date getOrderTime()
    {
        return orderTime;
    }

    public void setOrderTime(Date orderTime)
    {
        this.orderTime = orderTime;
    }

    public Date getCancelTime()
    {
        return cancelTime;
    }

    public void setCancelTime(Date cancelTime)
    {
        this.cancelTime = cancelTime;
    }

    public String getOrderDept()
    {
        return orderDept;
    }

    public void setOrderDept(String orderDept)
    {
        this.orderDept = orderDept;
    }

    public String getOrderPerson()
    {
        return orderPerson;
    }

    public void setOrderPerson(String orderPerson)
    {
        this.orderPerson = orderPerson;
    }

    public String getConfirmPerson()
    {
        return confirmPerson;
    }

    public void setConfirmPerson(String confirmPerson)
    {
        this.confirmPerson = confirmPerson;
    }

    public String getExecDept()
    {
        return execDept;
    }

    public void setExecDept(String execDept)
    {
        this.execDept = execDept;
    }

    public String getPatientSn()
    {
        return patientSn;
    }

    public void setPatientSn(String patientSn)
    {
        this.patientSn = patientSn;
    }

    public String getOrderSn()
    {
        return orderSn;
    }

    public void setOrderSn(String orderSn)
    {
        this.orderSn = orderSn;
    }

    public String getWardsId()
    {
        return wardsId;
    }

    public void setWardsId(String wardsId)
    {
        this.wardsId = wardsId;
    }

    public String getTemporaryFlag()
    {
        return temporaryFlag;
    }

    public void setTemporaryFlag(String temporaryFlag)
    {
        this.temporaryFlag = temporaryFlag;
    }

    public Date getConfirmTime()
    {
        return confirmTime;
    }

    public void setConfirmTime(Date confirmTime)
    {
        this.confirmTime = confirmTime;
    }

    public String getCancelPerson()
    {
        return cancelPerson;
    }

    public void setCancelPerson(String cancelPerson)
    {
        this.cancelPerson = cancelPerson;
    }

    public String getOrderStrStartTime()
    {
        return orderStrStartTime;
    }

    public void setOrderStrStartTime(String orderStrStartTime)
    {
        this.orderStrStartTime = orderStrStartTime;
    }

    public String getOrderStrEndTime()
    {
        return orderStrEndTime;
    }

    public void setOrderStrEndTime(String orderStrEndTime)
    {
        this.orderStrEndTime = orderStrEndTime;
    }

    public String getConStrfirmTime()
    {
        return conStrfirmTime;
    }

    public void setConStrfirmTime(String conStrfirmTime)
    {
        this.conStrfirmTime = conStrfirmTime;
    }

    public String getCancelStrTime()
    {
        return cancelStrTime;
    }

    public void setCancelStrTime(String cancelStrTime)
    {
        this.cancelStrTime = cancelStrTime;
    }

    public static int getTreatmentOrder()
    {
        return TREATMENT_ORDER;
    }

    public static int getCareOrder()
    {
        return CARE_ORDER;
    }

    public static int getProcedureOrder()
    {
        return PROCEDURE_ORDER;
    }

    public static int getExaminationOrder()
    {
        return EXAMINATION_ORDER;
    }

    public static int getLabOrder()
    {
        return LAB_ORDER;
    }

    public String getCancelPersonName()
    {
        return cancelPersonName;
    }

    public void setCancelPersonName(String cancelPersonName)
    {
        this.cancelPersonName = cancelPersonName;
    }

    public String getOrderFlag()
    {
        return orderFlag;
    }

    public void setOrderFlag(String orderFlag)
    {
        this.orderFlag = orderFlag;
    }

    public String getOrderTitle()
    {
        return orderTitle;
    }

    public void setOrderTitle(String orderTitle)
    {
        this.orderTitle = orderTitle;
    }

    public String getOrderPath()
    {
        return orderPath;
    }

    public void setOrderPath(String orderPath)
    {
        this.orderPath = orderPath;
    }

    public String getOrderPersonName()
    {
        return orderPersonName;
    }

    public void setOrderPersonName(String orderPersonName)
    {
        this.orderPersonName = orderPersonName;
    }

    public String getConfirmPersonName()
    {
        return confirmPersonName;
    }

    public void setConfirmPersonName(String confirmPersonName)
    {
        this.confirmPersonName = confirmPersonName;
    }

    public String getWardName()
    {
        return wardName;
    }

    public void setWardName(String wardName)
    {
        this.wardName = wardName;
    }

    public String getOrderDeptName()
    {
        return orderDeptName;
    }

    public void setOrderDeptName(String orderDeptName)
    {
        this.orderDeptName = orderDeptName;
    }

    public String getExecDeptName()
    {
        return execDeptName;
    }

    public void setExecDeptName(String execDeptName)
    {
        this.execDeptName = execDeptName;
    }

	public String getPatientDomain() {
		return patientDomain;
	}

	public void setPatientDomain(String patientDomain) {
		this.patientDomain = patientDomain;
	}

	public String getVisitTypeCode() {
		return visitTypeCode;
	}

	public void setVisitTypeCode(String visitTypeCode) {
		this.visitTypeCode = visitTypeCode;
	}

}
