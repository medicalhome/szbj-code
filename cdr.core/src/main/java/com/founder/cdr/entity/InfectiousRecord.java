package com.founder.cdr.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Table;

@Entity
@Table(name = "EXTERNAL_SEND_BUSINESS")
public class InfectiousRecord  implements Serializable
{
    private BigDecimal businessSn;
    private String patientDomain;
    private String patientLid;
    private String businessNo;
    private String businessType;
    private String businessTypeName;
    private String wardCode;
    private String wardNo;
    private String bedNo;
    private String patientName;
    private Date reportDate;
    private Date createTime;
    private Date updateTime;
    private BigDecimal updateCount;
    private String deleteFlag;
    private Date deleteTime;
    private String orgCode;
    private String orgName;
    private String visitTimes;
    private String medicalRecordNo;
    private String genderCode;
    private String genderName;
    private String age;
    private Date birthDay;
    private String reportDoctorCode;
    private String reportDoctorName;
    private String visitDeptCode;
    private String visitDeptName;
    private String sourceSystemId;
    private String legalStatus;
    private String reportTypeCode;
    private String reportTypeName;
    // Author: jia_yanqing
    // Date: 2014/4/1 10:00
    // [BUG]0043368 ADD BEGIN
    private Date modifyDate;
    // [BUG]0043368 ADD END

    @Id
    public BigDecimal getBusinessSn() {
        return businessSn;
    }

    public void setBusinessSn(BigDecimal businessSn) {
        this.businessSn = businessSn;
    }
    
    @Column(name = "ward_code", length = 128)
    public String getWardCode() {
        return wardCode;
    }

    public void setWardCode(String wardCode) {
        this.wardCode = wardCode;
    }

    
    @Column(name = "business_type_name", length = 128)
    public String getBusinessTypeName() {
        return businessTypeName;
    }

    public void setBusinessTypeName(String businessTypeName) {
        this.businessTypeName = businessTypeName;
    }
    
    @Column(name = "ward_no", length = 12)
    public String getWardNo() {
        return wardNo;
    }

    public void setWardNo(String wardNo) {
        this.wardNo = wardNo;
    }

    @Column(name = "bed_no", length = 12)
    public String getBedNo() {
        return bedNo;
    }

    public void setBedNo(String bedNo) {
        this.bedNo = bedNo;
    }

    @Column(name = "patient_name", length = 12)
    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "report_date", nullable = false, length = 7)
    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    @Column(name = "PATIENT_DOMAIN", length = 6)
    public String getPatientDomain() {
        return patientDomain;
    }

    public void setPatientDomain(String patientDomain) {
        this.patientDomain = patientDomain;
    }

    @Column(name = "PATIENT_LID", length = 50)
    public String getPatientLid() {
        return patientLid;
    }

    public void setPatientLid(String patientLid) {
        this.patientLid = patientLid;
    }

    @Column(name = "BUSINESS_NO", length = 128)
    public String getBusinessNo() {
        return businessNo;
    }

    public void setBusinessNo(String businessNo) {
        this.businessNo = businessNo;
    }

    @Column(name = "BUSINESS_TYPE", length = 1)
    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    @Column(name = "ORG_CODE", length = 1)
    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    @Column(name = "ORG_NAME", length = 1)
    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "CREATE_TIME", nullable = false, length = 7)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "update_time", nullable = false, length = 7)
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Column(name = "UPDATE_COUNT", nullable = false, precision = 22, scale = 0)
    public BigDecimal getUpdateCount() {
        return updateCount;
    }

    public void setUpdateCount(BigDecimal updateCount) {
        this.updateCount = updateCount;
    }

    @Column(name = "DELETE_FLAG", nullable = false, length = 1)
    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DELETE_TIME", length = 7)
    public Date getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }
    
    @Column(name = "VISIT_TIMES", length = 7)
    public String getVisitTimes() {
        return visitTimes;
    }

    public void setVisitTimes(String visitTimes) {
        this.visitTimes = visitTimes;
    }

    @Column(name = "gender_name", length = 7)
    public String getGenderName() {
        return genderName;
    }

    public void setGenderName(String genderName) {
        this.genderName = genderName;
    }

    @Column(name = "age", length = 7)
    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "birth_day", length = 7)
    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    @Column(name = "report_doctor_name", length = 7)
    public String getReportDoctorName() {
        return reportDoctorName;
    }

    public void setReportDoctorName(String reportDoctorName) {
        this.reportDoctorName = reportDoctorName;
    }

    @Column(name = "medical_record_no", length = 7)
    public String getMedicalRecordNo() {
        return medicalRecordNo;
    }

    public void setMedicalRecordNo(String medicalRecordNo) {
        this.medicalRecordNo = medicalRecordNo;
    }

    @Column(name = "gender_code", length = 7)
    public String getGenderCode() {
        return genderCode;
    }

    public void setGenderCode(String genderCode) {
        this.genderCode = genderCode;
    }

    @Column(name = "visit_dept_code", length = 7)
    public String getVisitDeptCode() {
        return visitDeptCode;
    }

    public void setVisitDeptCode(String visitDeptCode) {
        this.visitDeptCode = visitDeptCode;
    }

    @Column(name = "visit_dept_name", length = 7)
    public String getVisitDeptName() {
        return visitDeptName;
    }

    public void setVisitDeptName(String visitDeptName) {
        this.visitDeptName = visitDeptName;
    }

    @Column(name = "report_doctor_code", length = 7)
    public String getReportDoctorCode() {
        return reportDoctorCode;
    }

    public void setReportDoctorCode(String reportDoctorCode) {
        this.reportDoctorCode = reportDoctorCode;
    }
    
    @Column(name = "source_system_id", length = 7)
    public String getSourceSystemId() {
        return sourceSystemId;
    }

    public void setSourceSystemId(String sourceSystemId) {
        this.sourceSystemId = sourceSystemId;
    }

    @Column(name = "legal_status", length = 7)
    public String getLegalStatus() {
        return legalStatus;
    }

    public void setLegalStatus(String legalStatus) {
        this.legalStatus = legalStatus;
    }
    
    @Column(name = "report_type_code", length = 7)
    public String getReportTypeCode() {
        return reportTypeCode;
    }

    public void setReportTypeCode(String reportTypeCode) {
        this.reportTypeCode = reportTypeCode;
    }
    
    @Column(name = "report_type_name", length = 7)
    public String getReportTypeName() {
        return reportTypeName;
    }

    public void setReportTypeName(String reportTypeName) {
        this.reportTypeName = reportTypeName;
    }

    // Author: jia_yanqing
    // Date: 2014/4/1 10:00
    // [BUG]0043368 ADD BEGIN
    @Temporal(TemporalType.DATE)
    @Column(name = "modify_date", length = 7)
    public Date getModifyDate()
    {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate)
    {
        this.modifyDate = modifyDate;
    }
    // [BUG]0043368 ADD END

}
