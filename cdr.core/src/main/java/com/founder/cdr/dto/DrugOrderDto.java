package com.founder.cdr.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * [FUN]V05-101药物医嘱列表
 * @version 1.0, 2012/03/12  
 * @author 张彬
 * @since 1.0
 * 
 */
public class DrugOrderDto
{
    // 用药内部序列号
    private BigDecimal administrationSn;

    // 护理序列号
    private BigDecimal operationSn;

    // 患者序列号
    private BigDecimal patientSn;

    // 医嘱号
    private BigDecimal orderSn;

    // 药物名
    private String medicineName;

    // 用药途径
    private String medicineForm;

    // 次剂量
    private String dosage;

    // 次剂量单位
    private String dosageUnit;

    // 执行人
    private String operatorName;

    // 执行人ID
    private String operatorId;

    // 用药时间
    private Date medicineTime;

    // 床号
    private String bedNo;

    // 科室
    private String dept;

    // 科室名称
    private String deptName;

    public BigDecimal getAdministrationSn()
    {
        return administrationSn;
    }

    public void setAdministrationSn(BigDecimal administrationSn)
    {
        this.administrationSn = administrationSn;
    }

    public BigDecimal getOperationSn()
    {
        return operationSn;
    }

    public void setOperationSn(BigDecimal operationSn)
    {
        this.operationSn = operationSn;
    }

    public BigDecimal getPatientSn()
    {
        return patientSn;
    }

    public void setPatientSn(BigDecimal patientSn)
    {
        this.patientSn = patientSn;
    }

    public BigDecimal getOrderSn()
    {
        return orderSn;
    }

    public void setOrderSn(BigDecimal orderSn)
    {
        this.orderSn = orderSn;
    }

    public String getMedicineName()
    {
        return medicineName;
    }

    public void setMedicineName(String medicineName)
    {
        this.medicineName = medicineName;
    }

    public String getMedicineForm()
    {
        return medicineForm;
    }

    public void setMedicineForm(String medicineForm)
    {
        this.medicineForm = medicineForm;
    }

    public String getDosage()
    {
        return dosage;
    }

    public void setDosage(String dosage)
    {
        this.dosage = dosage;
    }

    public String getDosageUnit()
    {
        return dosageUnit;
    }

    public void setDosageUnit(String dosageUnit)
    {
        this.dosageUnit = dosageUnit;
    }

    public String getOperatorName()
    {
        return operatorName;
    }

    public void setOperatorName(String operatorName)
    {
        this.operatorName = operatorName;
    }

    public String getOperatorId()
    {
        return operatorId;
    }

    public void setOperatorId(String operatorId)
    {
        this.operatorId = operatorId;
    }

    public Date getMedicineTime()
    {
        return medicineTime;
    }

    public void setMedicineTime(Date medicineTime)
    {
        this.medicineTime = medicineTime;
    }

    public String getBedNo()
    {
        return bedNo;
    }

    public void setBedNo(String bedNo)
    {
        this.bedNo = bedNo;
    }

    public String getDept()
    {
        return dept;
    }

    public void setDept(String dept)
    {
        this.dept = dept;
    }

    public String getDeptName()
    {
        return deptName;
    }

    public void setDeptName(String deptName)
    {
        this.deptName = deptName;
    }

}
