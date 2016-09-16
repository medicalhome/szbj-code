package com.founder.cdr.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import com.founder.fasf.core.util.daohelper.annotation.Generator;

import java.io.Serializable;

@Entity
@Table(name = "ADMINISTRATION_RECORD")
public class AdministrationRecord implements Serializable
{

    private BigDecimal administrationSn;

    private BigDecimal operationSn;

    private BigDecimal orderSn;

    private BigDecimal patientSn;

    private String patientDomain;

    private String patientLid;

    private String dept;

    private String bedNo;

    private String medicineCode;

    private String medicineClass;

    private String medicineTypeCode;

    private String medicineType;

    private String medicineName;

    private String medicineSpecification;

    private String dosage;

    private String dosageUnit;

    private String medicineFormCode;

    private String medicineForm;

    private String instructions;

    private Date medicineTime;

    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;

    private String deptName;

    @Id
    @GeneratedValue(generator = "guid-generator")
    @Generator(name = "guid-generator", strategy = "guid")
    public BigDecimal getAdministrationSn()
    {
        return this.administrationSn;
    }

    public void setAdministrationSn(BigDecimal administrationSn)
    {
        this.administrationSn = administrationSn;
    }

    @Column(name = "OPERATION_SN", nullable = false, precision = 22, scale = 0)
    public BigDecimal getOperationSn()
    {
        return this.operationSn;
    }

    public void setOperationSn(BigDecimal operationSn)
    {
        this.operationSn = operationSn;
    }

    @Column(name = "ORDER_SN", precision = 22, scale = 0)
    public BigDecimal getOrderSn()
    {
        return this.orderSn;
    }

    public void setOrderSn(BigDecimal orderSn)
    {
        this.orderSn = orderSn;
    }

    @Column(name = "PATIENT_SN", nullable = false, precision = 22, scale = 0)
    public BigDecimal getPatientSn()
    {
        return this.patientSn;
    }

    public void setPatientSn(BigDecimal patientSn)
    {
        this.patientSn = patientSn;
    }

    @Column(name = "PATIENT_DOMAIN", nullable = false, length = 6)
    public String getPatientDomain()
    {
        return this.patientDomain;
    }

    public void setPatientDomain(String patientDomain)
    {
        this.patientDomain = patientDomain;
    }

    @Column(name = "PATIENT_LID", nullable = false, length = 12)
    public String getPatientLid()
    {
        return this.patientLid;
    }

    public void setPatientLid(String patientLid)
    {
        this.patientLid = patientLid;
    }

    @Column(name = "DEPT", length = 6)
    public String getDept()
    {
        return this.dept;
    }

    public void setDept(String dept)
    {
        this.dept = dept;
    }

    @Column(name = "BED_NO", length = 12)
    public String getBedNo()
    {
        return this.bedNo;
    }

    public void setBedNo(String bedNo)
    {
        this.bedNo = bedNo;
    }

    @Column(name = "MEDICINE_CODE", length = 32)
    public String getMedicineCode()
    {
        return this.medicineCode;
    }

    public void setMedicineCode(String medicineCode)
    {
        this.medicineCode = medicineCode;
    }

    @Column(name = "MEDICINE_CLASS", length = 32)
    public String getMedicineClass()
    {
        return this.medicineClass;
    }

    public void setMedicineClass(String medicineClass)
    {
        this.medicineClass = medicineClass;
    }

    @Column(name = "MEDICINE_TYPE_CODE", length = 32)
    public String getMedicineTypeCode()
    {
        return this.medicineTypeCode;
    }

    public void setMedicineTypeCode(String medicineTypeCode)
    {
        this.medicineTypeCode = medicineTypeCode;
    }

    @Column(name = "MEDICINE_TYPE", nullable = false, length = 32)
    public String getMedicineType()
    {
        return this.medicineType;
    }

    public void setMedicineType(String medicineType)
    {
        this.medicineType = medicineType;
    }

    @Column(name = "MEDICINE_NAME", nullable = false, length = 56)
    public String getMedicineName()
    {
        return this.medicineName;
    }

    public void setMedicineName(String medicineName)
    {
        this.medicineName = medicineName;
    }

    @Column(name = "MEDICINE_SPECIFICATION", length = 64)
    public String getMedicineSpecification()
    {
        return this.medicineSpecification;
    }

    public void setMedicineSpecification(String medicineSpecification)
    {
        this.medicineSpecification = medicineSpecification;
    }

    @Column(name = "DOSAGE", length = 32)
    public String getDosage()
    {
        return this.dosage;
    }

    public void setDosage(String dosage)
    {
        this.dosage = dosage;
    }

    @Column(name = "DOSAGE_UNIT", length = 32)
    public String getDosageUnit()
    {
        return this.dosageUnit;
    }

    public void setDosageUnit(String dosageUnit)
    {
        this.dosageUnit = dosageUnit;
    }

    @Column(name = "MEDICINE_FORM_CODE", length = 32)
    public String getMedicineFormCode()
    {
        return this.medicineFormCode;
    }

    public void setMedicineFormCode(String medicineFormCode)
    {
        this.medicineFormCode = medicineFormCode;
    }

    @Column(name = "MEDICINE_FORM", length = 32)
    public String getMedicineForm()
    {
        return this.medicineForm;
    }

    public void setMedicineForm(String medicineForm)
    {
        this.medicineForm = medicineForm;
    }

    @Column(name = "INSTRUCTIONS", length = 256)
    public String getInstructions()
    {
        return this.instructions;
    }

    public void setInstructions(String instructions)
    {
        this.instructions = instructions;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "MEDICINE_TIME", nullable = false, length = 7)
    public Date getMedicineTime()
    {
        return this.medicineTime;
    }

    public void setMedicineTime(Date medicineTime)
    {
        this.medicineTime = medicineTime;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "CREATE_TIME", nullable = false, length = 7)
    public Date getCreateTime()
    {
        return this.createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "UPDATE_TIME", nullable = false, length = 7)
    public Date getUpdateTime()
    {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }

    @Column(name = "UPDATE_COUNT", nullable = false, precision = 22, scale = 0)
    public BigDecimal getUpdateCount()
    {
        return this.updateCount;
    }

    public void setUpdateCount(BigDecimal updateCount)
    {
        this.updateCount = updateCount;
    }

    @Column(name = "DELETE_FLAG", nullable = false, length = 1)
    public String getDeleteFlag()
    {
        return this.deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag)
    {
        this.deleteFlag = deleteFlag;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DELETE_TIME", length = 7)
    public Date getDeleteTime()
    {
        return this.deleteTime;
    }

    public void setDeleteTime(Date deleteTime)
    {
        this.deleteTime = deleteTime;
    }

    @Column(name = "DEPT_NAME", length = 192)
    public String getDeptName()
    {
        return this.deptName;
    }

    public void setDeptName(String deptName)
    {
        this.deptName = deptName;
    }

}
