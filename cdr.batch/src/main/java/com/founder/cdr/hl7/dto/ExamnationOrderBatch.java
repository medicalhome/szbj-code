package com.founder.cdr.hl7.dto;

import java.util.List;
import org.hibernate.validator.constraints.NotEmpty;
import com.founder.cdr.hl7.dto.MedicalVisit;
import com.founder.cdr.hl7.dto.ExamOrderItem;

public class ExamnationOrderBatch extends OrderDto
{
    /**
     * 检查申请日期
     */
    private String requestDate;
    /**
     * 检查单号
     */
    private String requestNo;
    /**
     * 患者ID
     */
    private String patientId;
    /**
     * 患者姓名
     */
    private String patientName;
    /**
     * 患者域代码
     */
    @NotEmpty(message="{ExamnationOrderBatch.patientDomain}")
    private String patientDomain;
    /**
     * 性别代码
     */
    private String genderCode;
    /**
     * 出生日期
     */
    private String birthDate;
    /**
     * 检查申请人ID
     */
    private String requestDoctor;
    /**
     * 检查申请人姓名
     */
    private String requestDoctorName;
    /**
     * 申请科室ID
     */
    private String requestDept;
    /**
     * 申请科室
     */
    private String requestDeptName;
    /**
     * 标本要求具体内容
     */
    private String textNote;
    /**
     * 检查申请注意事项
     */
    private String examNotice;
    /**
     * 就诊
     */
    private List<MedicalVisit> medicalVisit;
    /**
     * 执行科室ID
     */
    private String executiveDept;
    /**
     * 执行科室
     */
    private String executiveDeptName;
    /**
     * 标本号
     */
    private String specimenid;
    /**
     * 标本类别代码
     */
    private String specimenCode;
    /**
     * 住院号
     */
    private String admissionNo;
    /**
     * 门诊号
     */
    private String visitNo;
    /**
     * 检查医嘱
     */
    private List<ExamOrderItem> ExamOrderItem;
    
 
    /**
     * 医嘱时间
     */
    private String orderTime;
    /**
     * 医嘱类别名称
     */
    private String orderTypeName;
    /**
     * 医嘱类别代码
     */
    private String orderType;
    /**
     * 医嘱确认人ID
     */
    private String confirmPerson;
    /**
     * 医嘱确认人
     */
    private String confirmPersonName;
    /**
     * 确认时间
     */
    private String confirmTime;
    

    public String getRequestDate()
    {
        return requestDate;
    }

    public String getRequestNo()
    {
        return requestNo;
    }

    public String getPatientId()
    {
        return patientId;
    }

    public String getOrderTime()
    {
        return orderTime;
    }

    public String getPatientDomain()
    {
        return patientDomain;
    }

    public String getPatientName()
    {
        return patientName;
    }

    public String getGenderCode()
    {
        return genderCode;
    }

    public String getBirthDate()
    {
        return birthDate;
    }

    public String getRequestDoctor()
    {
        return requestDoctor;
    }

    public String getRequestDoctorName()
    {
        return requestDoctorName;
    }

    public String getRequestDept()
    {
        return requestDept;
    }

    public String getRequestDeptName()
    {
        return requestDeptName;
    }

    public String getTextNote()
    {
        return textNote;
    }

    public String getExamNotice()
    {
        return examNotice;
    }

    public List<MedicalVisit> getMedicalVisit()
    {
        return medicalVisit;
    }

    public String getExecutiveDept()
    {
        return executiveDept;
    }

    public String getExecutiveDeptName()
    {
        return executiveDeptName;
    }

    public String getSpecimenid()
    {
        return specimenid;
    }

    public String getSpecimenCode()
    {
        return specimenCode;
    }

    public String getAdmissionNo()
    {
        return admissionNo;
    }

    public String getVisitNo()
    {
        return visitNo;
    }

    public List<ExamOrderItem> getExamOrderItem()
    {
        return ExamOrderItem;
    }

    public String getOrderTypeName()
    {
        return orderTypeName;
    }

    public String getOrderType()
    {
        return orderType;
    }

    public String getConfirmPerson()
    {
        return confirmPerson;
    }

    public String getConfirmPersonName()
    {
        return confirmPersonName;
    }

    public String getConfirmTime()
    {
        return confirmTime;
    }

    
}
