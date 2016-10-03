package com.yly.cdr.hl7.dto.porxin010370uv01;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.yly.cdr.hl7.dto.MedicationOrderDto;

public class PrescriptionDto
{
    /**
     * 开方时间
     */
    private String prescriptionTime;

    /**
     * 处方号
     */
    // @NotEmpty(message="{PrescriptionDto.prescriptionNo}")
    private String prescriptionNo;

    /**
     * 处方类别
     */
    // @NotEmpty(message="{PrescriptionDto.prescriptionClass}")
    private String prescriptionClass;

    /**
     * 处方类别名称
     */
    private String prescriptionClassName;

    /**
     * 开方医生ID
     */
    private String prescriptionDoctorId;

    /**
     * 开方医生姓名
     */
    private String prescriptionDoctorName;

    /**
     * 开方科室ID
     */
    private String prescriptionDept;

    /**
     * 开方科室名称
     */
    private String prescriptionDeptName;

    /**
     * 审核人ID
     */
    private String reviewPerson;

    /**
     * 审核人姓名
     */
    private String reviewPersonName;

    /**
     * 审核时间
     */
    private String reviewTime;

    /**
     * 用药医嘱
     */
    private List<MedicationOrderDto> medicineOrder;

    /**
     *已付费标识 
     */
    private String paidFlag;

    public String getPaidFlag()
    {
        return paidFlag;
    }

    public List<MedicationOrderDto> getMedicineOrder()
    {
        return medicineOrder;
    }

    public String getPrescriptionTime()
    {
        return prescriptionTime;
    }

    public void setPrescriptionTime(String prescriptionTime)
    {
        this.prescriptionTime = prescriptionTime;
    }

    public String getPrescriptionNo()
    {
        return prescriptionNo;
    }

    public void setPrescriptionNo(String prescriptionNo)
    {
        this.prescriptionNo = prescriptionNo;
    }

    public String getPrescriptionClass()
    {
        return prescriptionClass;
    }

    public void setPrescriptionClass(String prescriptionClass)
    {
        this.prescriptionClass = prescriptionClass;
    }

    public String getPrescriptionClassName()
    {
        return prescriptionClassName;
    }

    public void setPrescriptionClassName(String prescriptionClassName)
    {
        this.prescriptionClassName = prescriptionClassName;
    }

    public String getPrescriptionDoctorId()
    {
        return prescriptionDoctorId;
    }

    public void setPrescriptionDoctorId(String prescriptionDoctorId)
    {
        this.prescriptionDoctorId = prescriptionDoctorId;
    }

    public String getPrescriptionDoctorName()
    {
        return prescriptionDoctorName;
    }

    public void setPrescriptionDoctorName(String prescriptionDoctorName)
    {
        this.prescriptionDoctorName = prescriptionDoctorName;
    }

    public String getPrescriptionDept()
    {
        return prescriptionDept;
    }

    public void setPrescriptionDept(String prescriptionDept)
    {
        this.prescriptionDept = prescriptionDept;
    }

    public String getPrescriptionDeptName()
    {
        return prescriptionDeptName;
    }

    public void setPrescriptionDeptName(String prescriptionDeptName)
    {
        this.prescriptionDeptName = prescriptionDeptName;
    }

    public String getReviewPerson()
    {
        return reviewPerson;
    }

    public void setReviewPerson(String reviewPerson)
    {
        this.reviewPerson = reviewPerson;
    }

    public String getReviewPersonName()
    {
        return reviewPersonName;
    }

    public void setReviewPersonName(String reviewPersonName)
    {
        this.reviewPersonName = reviewPersonName;
    }

    public String getReviewTime()
    {
        return reviewTime;
    }

    public void setReviewTime(String reviewTime)
    {
        this.reviewTime = reviewTime;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("PrescriptionDto [prescriptionNo=");
        builder.append(prescriptionNo);
        builder.append(", medicineOrder=");
        builder.append(medicineOrder);
        builder.append("]");
        return builder.toString();
    }


}
