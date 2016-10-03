package com.yly.cdr.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 
 * [FUN]V05-101检验列表
 * @version 1.0, 2012/03/14  
 * @author 金鹏
 * @since 1.0
 * 
 */
public class PrescriptionDto
{
    /**
     * 患者EMPI ID
     */
    private String patientEid;

    /**
     * 开方时间
     */
    private String prescriptionTime;

    /**
     * 处方号
     */
    private String prescriptionNo;

    /**
     * 处方类别
     */
    private String prescriptionClass;

    /**
     * 处方类型
     */
    private String prescriptionType;

    /**
     * 开方医生ID
     */
    private String prescriptionDoctorId;

    /**
     * 审核人ID
     */
    private String reviewPerson;

    /**
     * 审核时间
     */
    private String reviewTime;

    /**
     * 就诊ID
     */
    private String visitSn;

    /**
     * 就诊次数
     */
    private String visitTime;

    public String getPatientEid()
    {
        return patientEid;
    }

    public void setPatientEid(String patientEid)
    {
        this.patientEid = patientEid;
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

    public String getPrescriptionType()
    {
        return prescriptionType;
    }

    public void setPrescriptionType(String prescriptionType)
    {
        this.prescriptionType = prescriptionType;
    }

    public String getPrescriptionDoctorId()
    {
        return prescriptionDoctorId;
    }

    public void setPrescriptionDoctorId(String prescriptionDoctorId)
    {
        this.prescriptionDoctorId = prescriptionDoctorId;
    }

    public String getReviewPerson()
    {
        return reviewPerson;
    }

    public void setReviewPerson(String reviewPerson)
    {
        this.reviewPerson = reviewPerson;
    }

    public String getReviewTime()
    {
        return reviewTime;
    }

    public void setReviewTime(String reviewTime)
    {
        this.reviewTime = reviewTime;
    }

    public String getVisitSn()
    {
        return visitSn;
    }

    public void setVisitSn(String visitSn)
    {
        this.visitSn = visitSn;
    }

    public String getVisitTime()
    {
        return visitTime;
    }

    public void setVisitTime(String visitTime)
    {
        this.visitTime = visitTime;
    }

}
