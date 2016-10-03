package com.yly.hl7.dto;

import java.util.Date;
import java.util.List;

public class POCDHD000040
{
    /**
     * 患者EMPI ID
     */
    private String patientEid;
    
    /**
     * 影像号/住院号/门诊号
     */
    private String inpatientNo;
    private String outpatientNo;
    private String imageNo;
    /**
     * 联系电话-号码
     */
    private String telephoneNo;
    /**
     * 患者姓名
     */
    private String patientName;
    /**
     * 性别代码
     */
    private String genderCode;
    /**
     * 出生日期
     */
    private Date birthDate;
    /**
     * 报告日期
     */
    private Date reportDate;
    /**
     * 报告医生代码
     */
    private String reporter;
    /**
     * 报告医生姓名
     */
    private String reporterName;
    /**
     * 审核日期
     */
    private Date reviewDate;
    /**
     * 审核医生代码
     */
    private String reviewer;
    /**
     * 审核医生姓名
     */
    private String reviewerName;
    /**
     * 就诊类别
     */
    private String visitType;
    /**
     * 就诊时间
     */
    private Date visitDate;
    /**
     * 检查报告单-编号
     */
    private String reportNo;
    /**
     * 检查项目代码/检查项目名称
     */
    private String examItemCode;
    private String examItemName;
    /**
     * 检查报告备注
     */
    private String reportMemo;
    /**
     * 检查报告结果-客观所见
     */
    private String objectivelySeen;
    /**
     * 检查报告结果-主管所见
     */
    private String subjectiveSee;
    /**
     * 检查方法代码/检查方法名称
     */
    private String examMethod;
    /**
     * 
     */
    private String examMethodName;
    /**
     * 检查部位代码/检查部位名称
     */
    private String examSite;
    private String examSiteName;
    /**
     * 检查日期
     */
    private Date examDate;
    /**
     * 检查医生代码
     */
    private String examDoctor;
    /**
     * 检查医生名称
     */
    private String examDoctorName;
    /**
     * 检查科室代码
     */
    private String examDept;
    /**
     * 检查科室
     */
    private String examDeptName;
    /**
     * 检查申请单-编号
     */
    private String requestNo;
    
    /**
     * 诊断列表
     */
    private List<Diagnosis> diagnosises;
    
    /**
     * 诊断类型
     */
    private String diagnosisType;
    /**
     * 诊断日期
     */
    private Date diagnosisDate;
    /**
     * 诊断顺位(从属关系)代码
     */
    private String diagnosisSeq;
    /**
     * 疾病代码:疾病名称
     */
    private String disease;
    private String diseaseName;

    public String getPatientEid()
    {
        return patientEid;
    }
    public void setPatientEid(String patientEid)
    {
        this.patientEid = patientEid;
    }
    public String getInpatientNo()
    {
        return inpatientNo;
    }
    public void setInpatientNo(String inpatientNo)
    {
        this.inpatientNo = inpatientNo;
    }
    public String getOutpatientNo()
    {
        return outpatientNo;
    }
    public void setOutpatientNo(String outpatientNo)
    {
        this.outpatientNo = outpatientNo;
    }
    public String getImageNo()
    {
        return imageNo;
    }
    public void setImageNo(String imageNo)
    {
        this.imageNo = imageNo;
    }
    public String getTelephoneNo()
    {
        return telephoneNo;
    }
    public void setTelephoneNo(String telephoneNo)
    {
        this.telephoneNo = telephoneNo;
    }
    public String getPatientName()
    {
        return patientName;
    }
    public void setPatientName(String patientName)
    {
        this.patientName = patientName;
    }
    public String getGenderCode()
    {
        return genderCode;
    }
    public void setGenderCode(String genderCode)
    {
        this.genderCode = genderCode;
    }
    public Date getBirthDate()
    {
        return birthDate;
    }
    public void setBirthDate(Date birthDate)
    {
        this.birthDate = birthDate;
    }
    public Date getReportDate()
    {
        return reportDate;
    }
    public void setReportDate(Date reportDate)
    {
        this.reportDate = reportDate;
    }
    public String getReporter()
    {
        return reporter;
    }
    public void setReporter(String reporter)
    {
        this.reporter = reporter;
    }
    public String getReporterName()
    {
        return reporterName;
    }
    public void setReporterName(String reporterName)
    {
        this.reporterName = reporterName;
    }
    public Date getReviewDate()
    {
        return reviewDate;
    }
    public void setReviewDate(Date reviewDate)
    {
        this.reviewDate = reviewDate;
    }
    public String getReviewer()
    {
        return reviewer;
    }
    public void setReviewer(String reviewer)
    {
        this.reviewer = reviewer;
    }
    public String getReviewerName()
    {
        return reviewerName;
    }
    public void setReviewerName(String reviewerName)
    {
        this.reviewerName = reviewerName;
    }
    public String getVisitType()
    {
        return visitType;
    }
    public void setVisitType(String visitType)
    {
        this.visitType = visitType;
    }
    public Date getVisitDate()
    {
        return visitDate;
    }
    public void setVisitDate(Date visitDate)
    {
        this.visitDate = visitDate;
    }
    public String getReportNo()
    {
        return reportNo;
    }
    public void setReportNo(String reportNo)
    {
        this.reportNo = reportNo;
    }
    public String getExamItemCode()
    {
        return examItemCode;
    }
    public void setExamItemCode(String examItemCode)
    {
        this.examItemCode = examItemCode;
    }
    public String getExamItemName()
    {
        return examItemName;
    }
    public void setExamItemName(String examItemName)
    {
        this.examItemName = examItemName;
    }
    public String getReportMemo()
    {
        return reportMemo;
    }
    public void setReportMemo(String reportMemo)
    {
        this.reportMemo = reportMemo;
    }
    public String getObjectivelySeen()
    {
        return objectivelySeen;
    }
    public void setObjectivelySeen(String objectivelySeen)
    {
        this.objectivelySeen = objectivelySeen;
    }
    public String getSubjectiveSee()
    {
        return subjectiveSee;
    }
    public void setSubjectiveSee(String subjectiveSee)
    {
        this.subjectiveSee = subjectiveSee;
    }
    public String getExamMethod()
    {
        return examMethod;
    }
    public void setExamMethod(String examMethod)
    {
        this.examMethod = examMethod;
    }
    public String getExamMethodName()
    {
        return examMethodName;
    }
    public void setExamMethodName(String examMethodName)
    {
        this.examMethodName = examMethodName;
    }
    public String getExamSite()
    {
        return examSite;
    }
    public void setExamSite(String examSite)
    {
        this.examSite = examSite;
    }
    public String getExamSiteName()
    {
        return examSiteName;
    }
    public void setExamSiteName(String examSiteName)
    {
        this.examSiteName = examSiteName;
    }
    public Date getExamDate()
    {
        return examDate;
    }
    public void setExamDate(Date examDate)
    {
        this.examDate = examDate;
    }
    public String getExamDoctor()
    {
        return examDoctor;
    }
    public void setExamDoctor(String examDoctor)
    {
        this.examDoctor = examDoctor;
    }
    public String getExamDoctorName()
    {
        return examDoctorName;
    }
    public void setExamDoctorName(String examDoctorName)
    {
        this.examDoctorName = examDoctorName;
    }
    public String getExamDept()
    {
        return examDept;
    }
    public void setExamDept(String examDept)
    {
        this.examDept = examDept;
    }
    public String getExamDeptName()
    {
        return examDeptName;
    }
    public void setExamDeptName(String examDeptName)
    {
        this.examDeptName = examDeptName;
    }
    public String getRequestNo()
    {
        return requestNo;
    }
    public void setRequestNo(String requestNo)
    {
        this.requestNo = requestNo;
    }
    
    public List<Diagnosis> getDiagnosises()
    {
        return diagnosises;
    }
    public void setDiagnosises(List<Diagnosis> diagnosises)
    {
        this.diagnosises = diagnosises;
    }
    public String getDiagnosisType()
    {
        return diagnosisType;
    }
    public void setDiagnosisType(String diagnosisType)
    {
        this.diagnosisType = diagnosisType;
    }
    public Date getDiagnosisDate()
    {
        return diagnosisDate;
    }
    public void setDiagnosisDate(Date diagnosisDate)
    {
        this.diagnosisDate = diagnosisDate;
    }
    public String getDiagnosisSeq()
    {
        return diagnosisSeq;
    }
    public void setDiagnosisSeq(String diagnosisSeq)
    {
        this.diagnosisSeq = diagnosisSeq;
    }
    public String getDisease()
    {
        return disease;
    }
    public void setDisease(String disease)
    {
        this.disease = disease;
    }
    public String getDiseaseName()
    {
        return diseaseName;
    }
    public void setDiseaseName(String diseaseName)
    {
        this.diseaseName = diseaseName;
    }
}
