package com.yly.cdr.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.founder.fasf.core.util.daohelper.annotation.Generator;
import com.founder.fasf.core.util.daohelper.annotation.Parameter;

@Entity
@Table(name = "MEDICAL_VISIT")
public class MedicalVisit implements Serializable
{

    private BigDecimal visitSn;

    private BigDecimal patientSn;

    private String patientDomain;

    private String patientLid;

    private BigDecimal visitTimes;
    
    private String visitOrdNo;

    private Date registeredDate;

    private String registeredDoctorId;

    private String registeredDoctorName;

    private Date visitDate;

    private String visitDoctorId;

    private String visitDoctorName;

    private String registeredSequence;

    private String admissionDoctorId;

    private String admissionDoctorName;

    private String residentDoctorId;

    private String residentDoctorName;

    private String attendingDoctorId;

    private String attendingDoctorName;

    private String deptDirectorId;

    private String deptDirectorName;

    private String admissionWardId;

    private String admissionBedNo;

    private Date dischargeDate;

    private String dischargeWardId;

    private String dischargeBedNo;

    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;

    private String patientAge;

    private String visitTypeName;

    private String registeredDeptId;

    private String registeredDeptName;

    private String visitDeptId;

    private String visitDeptName;

    private String registrationClassCode;

    private String registrationClassName;

    private String registrationMethodCode;

    private String registrationMethodName;

    private String registeredWayCode;

    private String registeredWayName;

    private String visitStatusName;

    private String dischargeDeptId;

    private String dischargeDeptName;

    private String dischargeStatusCode;

    private String dischargeStatusName;

    private String visitStatusCode;

    private String visitTypeCode;

    private String admissionWardName;

    private String dischargeWardName;

    private String admitingSourceCode;

    private String admitingSourceName;

    private String urgentLevelCode;

    private String urgentLevelName;

    private String registeredTimeIntervalCode;

    private String registeredTimeIntervalName;

    // $Author :tong_meng
    // $Date : 2012/10/30 11:30$
    // [BUG]0010809 MODIFY BEGIN
    private String chargeClass;

    private String chargeClassName;

    // [BUG]0010809 MODIFY END

    private BigDecimal actualFee;
    
    private BigDecimal receivableFee;
    
    private String examFlag;
    
    // Author :jia_yanqing
    // Date : 2013/5/28 13:30
    // [BUG]0033056 ADD BEGIN
    private String nativeplaceCode;
    private String nativeplaceName;
    // [BUG]0033056 ADD END
    private String createby;

    private String updateby;

    private String deleteby;

    // Author :tong_meng
    // Date : 2013/11/26 14:49
    // [BUG]0040006 ADD BEGIN
    private Date entranceTime;
    // [BUG]0040006 ADD END
    
    // $Author:tong_meng
    // $Date:2013/12/03 11:00
    // [BUG]0040270 ADD BEGIN
    // 医疗机构代码
    private String orgCode;
    // 医疗机构名称
    private String orgName;
    // [BUG]0040270 ADD END
    
    @Id
    @GeneratedValue(generator = "native-generator")
    @Generator(name = "native-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "SEQ_VISIT") })
    public BigDecimal getVisitSn()
    {
        return this.visitSn;
    }

    public void setVisitSn(BigDecimal visitSn)
    {
        this.visitSn = visitSn;
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

    @Column(name = "VISIT_TIMES", nullable = false, precision = 22, scale = 0)
    public BigDecimal getVisitTimes()
    {
        return this.visitTimes;
    }

    public void setVisitTimes(BigDecimal visitTimes)
    {
        this.visitTimes = visitTimes;
    }
    
    @Column(name = "VISIT_ORD_NO", nullable = false, length=50)
    public String getVisitOrdNo()
    {
        return this.visitOrdNo;
    }

    public void setVisitOrdNo(String visitOrdNo)
    {
        this.visitOrdNo = visitOrdNo;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "REGISTERED_DATE", length = 7)
    public Date getRegisteredDate()
    {
        return this.registeredDate;
    }

    public void setRegisteredDate(Date registeredDate)
    {
        this.registeredDate = registeredDate;
    }

    @Column(name = "REGISTERED_DOCTOR_ID", length = 12)
    public String getRegisteredDoctorId()
    {
        return this.registeredDoctorId;
    }

    public void setRegisteredDoctorId(String registeredDoctorId)
    {
        this.registeredDoctorId = registeredDoctorId;
    }

    @Column(name = "REGISTERED_DOCTOR_NAME", length = 192)
    public String getRegisteredDoctorName()
    {
        return this.registeredDoctorName;
    }

    public void setRegisteredDoctorName(String registeredDoctorName)
    {
        this.registeredDoctorName = registeredDoctorName;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "VISIT_DATE", length = 7)
    public Date getVisitDate()
    {
        return this.visitDate;
    }

    public void setVisitDate(Date visitDate)
    {
        this.visitDate = visitDate;
    }

    @Column(name = "VISIT_DOCTOR_ID", length = 12)
    public String getVisitDoctorId()
    {
        return this.visitDoctorId;
    }

    public void setVisitDoctorId(String visitDoctorId)
    {
        this.visitDoctorId = visitDoctorId;
    }

    @Column(name = "VISIT_DOCTOR_NAME", length = 192)
    public String getVisitDoctorName()
    {
        return this.visitDoctorName;
    }

    public void setVisitDoctorName(String visitDoctorName)
    {
        this.visitDoctorName = visitDoctorName;
    }

    @Column(name = "REGISTERED_SEQUENCE", length = 10)
    public String getRegisteredSequence()
    {
        return this.registeredSequence;
    }

    public void setRegisteredSequence(String registeredSequence)
    {
        this.registeredSequence = registeredSequence;
    }

    @Column(name = "ADMISSION_DOCTOR_ID", length = 12)
    public String getAdmissionDoctorId()
    {
        return this.admissionDoctorId;
    }

    public void setAdmissionDoctorId(String admissionDoctorId)
    {
        this.admissionDoctorId = admissionDoctorId;
    }

    @Column(name = "ADMISSION_DOCTOR_NAME", length = 192)
    public String getAdmissionDoctorName()
    {
        return this.admissionDoctorName;
    }

    public void setAdmissionDoctorName(String admissionDoctorName)
    {
        this.admissionDoctorName = admissionDoctorName;
    }

    @Column(name = "RESIDENT_DOCTOR_ID", length = 12)
    public String getResidentDoctorId()
    {
        return this.residentDoctorId;
    }

    public void setResidentDoctorId(String residentDoctorId)
    {
        this.residentDoctorId = residentDoctorId;
    }

    @Column(name = "RESIDENT_DOCTOR_NAME", length = 192)
    public String getResidentDoctorName()
    {
        return this.residentDoctorName;
    }

    public void setResidentDoctorName(String residentDoctorName)
    {
        this.residentDoctorName = residentDoctorName;
    }

    @Column(name = "ATTENDING_DOCTOR_ID", length = 12)
    public String getAttendingDoctorId()
    {
        return this.attendingDoctorId;
    }

    public void setAttendingDoctorId(String attendingDoctorId)
    {
        this.attendingDoctorId = attendingDoctorId;
    }

    @Column(name = "ATTENDING_DOCTOR_NAME", length = 192)
    public String getAttendingDoctorName()
    {
        return this.attendingDoctorName;
    }

    public void setAttendingDoctorName(String attendingDoctorName)
    {
        this.attendingDoctorName = attendingDoctorName;
    }

    @Column(name = "DEPT_DIRECTOR_ID", length = 12)
    public String getDeptDirectorId()
    {
        return this.deptDirectorId;
    }

    public void setDeptDirectorId(String deptDirectorId)
    {
        this.deptDirectorId = deptDirectorId;
    }

    @Column(name = "DEPT_DIRECTOR_NAME", length = 192)
    public String getDeptDirectorName()
    {
        return this.deptDirectorName;
    }

    public void setDeptDirectorName(String deptDirectorName)
    {
        this.deptDirectorName = deptDirectorName;
    }

    @Column(name = "ADMISSION_WARD_ID", length = 12)
    public String getAdmissionWardId()
    {
        return this.admissionWardId;
    }

    public void setAdmissionWardId(String admissionWardId)
    {
        this.admissionWardId = admissionWardId;
    }

    @Column(name = "ADMISSION_BED_NO", length = 20)
    public String getAdmissionBedNo()
    {
        return this.admissionBedNo;
    }

    public void setAdmissionBedNo(String admissionBedNo)
    {
        this.admissionBedNo = admissionBedNo;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DISCHARGE_DATE", length = 7)
    public Date getDischargeDate()
    {
        return this.dischargeDate;
    }

    public void setDischargeDate(Date dischargeDate)
    {
        this.dischargeDate = dischargeDate;
    }

    @Column(name = "DISCHARGE_WARD_ID", length = 12)
    public String getDischargeWardId()
    {
        return this.dischargeWardId;
    }

    public void setDischargeWardId(String dischargeWardId)
    {
        this.dischargeWardId = dischargeWardId;
    }

    @Column(name = "DISCHARGE_BED_NO", length = 20)
    public String getDischargeBedNo()
    {
        return this.dischargeBedNo;
    }

    public void setDischargeBedNo(String dischargeBedNo)
    {
        this.dischargeBedNo = dischargeBedNo;
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

    @Column(name = "PATIENT_AGE", length = 50)
    public String getPatientAge()
    {
        return this.patientAge;
    }

    public void setPatientAge(String patientAge)
    {
        this.patientAge = patientAge;
    }

    @Column(name = "VISIT_TYPE_NAME", length = 192)
    public String getVisitTypeName()
    {
        return this.visitTypeName;
    }

    public void setVisitTypeName(String visitTypeName)
    {
        this.visitTypeName = visitTypeName;
    }

    @Column(name = "REGISTERED_DEPT_ID", length = 12)
    public String getRegisteredDeptId()
    {
        return this.registeredDeptId;
    }

    public void setRegisteredDeptId(String registeredDeptId)
    {
        this.registeredDeptId = registeredDeptId;
    }

    @Column(name = "REGISTERED_DEPT_NAME", length = 192)
    public String getRegisteredDeptName()
    {
        return this.registeredDeptName;
    }

    public void setRegisteredDeptName(String registeredDeptName)
    {
        this.registeredDeptName = registeredDeptName;
    }

    @Column(name = "VISIT_DEPT_ID", length = 12)
    public String getVisitDeptId()
    {
        return this.visitDeptId;
    }

    public void setVisitDeptId(String visitDeptId)
    {
        this.visitDeptId = visitDeptId;
    }

    @Column(name = "VISIT_DEPT_NAME", length = 192)
    public String getVisitDeptName()
    {
        return this.visitDeptName;
    }

    public void setVisitDeptName(String visitDeptName)
    {
        this.visitDeptName = visitDeptName;
    }

    @Column(name = "REGISTRATION_CLASS_CODE", length = 12)
    public String getRegistrationClassCode()
    {
        return this.registrationClassCode;
    }

    public void setRegistrationClassCode(String registrationClassCode)
    {
        this.registrationClassCode = registrationClassCode;
    }

    @Column(name = "REGISTRATION_CLASS_NAME", length = 192)
    public String getRegistrationClassName()
    {
        return this.registrationClassName;
    }

    public void setRegistrationClassName(String registrationClassName)
    {
        this.registrationClassName = registrationClassName;
    }

    @Column(name = "REGISTRATION_METHOD_CODE", length = 12)
    public String getRegistrationMethodCode()
    {
        return this.registrationMethodCode;
    }

    public void setRegistrationMethodCode(String registrationMethodCode)
    {
        this.registrationMethodCode = registrationMethodCode;
    }

    @Column(name = "REGISTRATION_METHOD_NAME", length = 192)
    public String getRegistrationMethodName()
    {
        return this.registrationMethodName;
    }

    public void setRegistrationMethodName(String registrationMethodName)
    {
        this.registrationMethodName = registrationMethodName;
    }

    @Column(name = "REGISTERED_WAY_CODE", length = 6)
    public String getRegisteredWayCode()
    {
        return this.registeredWayCode;
    }

    public void setRegisteredWayCode(String registeredWayCode)
    {
        this.registeredWayCode = registeredWayCode;
    }

    @Column(name = "REGISTERED_WAY_NAME", length = 192)
    public String getRegisteredWayName()
    {
        return this.registeredWayName;
    }

    public void setRegisteredWayName(String registeredWayName)
    {
        this.registeredWayName = registeredWayName;
    }

    @Column(name = "VISIT_STATUS_NAME", length = 192)
    public String getVisitStatusName()
    {
        return this.visitStatusName;
    }

    public void setVisitStatusName(String visitStatusName)
    {
        this.visitStatusName = visitStatusName;
    }

    @Column(name = "DISCHARGE_DEPT_ID", length = 12)
    public String getDischargeDeptId()
    {
        return this.dischargeDeptId;
    }

    public void setDischargeDeptId(String dischargeDeptId)
    {
        this.dischargeDeptId = dischargeDeptId;
    }

    @Column(name = "DISCHARGE_DEPT_NAME", length = 192)
    public String getDischargeDeptName()
    {
        return this.dischargeDeptName;
    }

    public void setDischargeDeptName(String dischargeDeptName)
    {
        this.dischargeDeptName = dischargeDeptName;
    }

    @Column(name = "DISCHARGE_STATUS_CODE", length = 12)
    public String getDischargeStatusCode()
    {
        return this.dischargeStatusCode;
    }

    public void setDischargeStatusCode(String dischargeStatusCode)
    {
        this.dischargeStatusCode = dischargeStatusCode;
    }

    @Column(name = "DISCHARGE_STATUS_NAME", length = 192)
    public String getDischargeStatusName()
    {
        return this.dischargeStatusName;
    }

    public void setDischargeStatusName(String dischargeStatusName)
    {
        this.dischargeStatusName = dischargeStatusName;
    }

    @Column(name = "VISIT_STATUS_CODE", length = 12)
    public String getVisitStatusCode()
    {
        return this.visitStatusCode;
    }

    public void setVisitStatusCode(String visitStatusCode)
    {
        this.visitStatusCode = visitStatusCode;
    }

    @Column(name = "VISIT_TYPE_CODE", nullable = false, length = 12)
    public String getVisitTypeCode()
    {
        return this.visitTypeCode;
    }

    public void setVisitTypeCode(String visitTypeCode)
    {
        this.visitTypeCode = visitTypeCode;
    }

    @Column(name = "ADMISSION_WARD_NAME", length = 192)
    public String getAdmissionWardName()
    {
        return this.admissionWardName;
    }

    public void setAdmissionWardName(String admissionWardName)
    {
        this.admissionWardName = admissionWardName;
    }

    @Column(name = "DISCHARGE_WARD_NAME", length = 192)
    public String getDischargeWardName()
    {
        return this.dischargeWardName;
    }

    public void setDischargeWardName(String dischargeWardName)
    {
        this.dischargeWardName = dischargeWardName;
    }

    @Column(name = "ADMITING_SOURCE_CODE", length = 20)
    public String getAdmitingSourceCode()
    {
        return this.admitingSourceCode;
    }

    public void setAdmitingSourceCode(String admitingSourceCode)
    {
        this.admitingSourceCode = admitingSourceCode;
    }

    @Column(name = "ADMITING_SOURCE_NAME", length = 64)
    public String getAdmitingSourceName()
    {
        return this.admitingSourceName;
    }

    public void setAdmitingSourceName(String admitingSourceName)
    {
        this.admitingSourceName = admitingSourceName;
    }

    @Column(name = "URGENT_LEVEL_CODE", length = 12)
    public String getUrgentLevelCode()
    {
        return urgentLevelCode;
    }

    public void setUrgentLevelCode(String urgentLevelCode)
    {
        this.urgentLevelCode = urgentLevelCode;
    }

    @Column(name = "URGENT_LEVEL_NAME", length = 32)
    public String getUrgentLevelName()
    {
        return urgentLevelName;
    }

    public void setUrgentLevelName(String urgentLevelName)
    {
        this.urgentLevelName = urgentLevelName;
    }

    @Column(name = "REGISTERED_TIME_INTERVAL_CODE", length = 12)
    public String getRegisteredTimeIntervalCode()
    {
        return registeredTimeIntervalCode;
    }

    public void setRegisteredTimeIntervalCode(String registeredTimeIntervalCode)
    {
        this.registeredTimeIntervalCode = registeredTimeIntervalCode;
    }

    @Column(name = "REGISTERED_TIME_INTERVAL_NAME", length = 20)
    public String getRegisteredTimeIntervalName()
    {
        return registeredTimeIntervalName;
    }

    public void setRegisteredTimeIntervalName(String registeredTimeIntervalName)
    {
        this.registeredTimeIntervalName = registeredTimeIntervalName;
    }

    @Column(name = "CHARGE_CLASS", length = 6)
    public String getChargeClass()
    {
        return chargeClass;
    }

    public void setChargeClass(String chargeClass)
    {
        this.chargeClass = chargeClass;
    }

    @Column(name = "CHARGE_CLASS_NAME", length = 200)
    public String getChargeClassName()
    {
        return chargeClassName;
    }

    public void setChargeClassName(String chargeClassName)
    {
        this.chargeClassName = chargeClassName;
    }

    @Column(name = "ACTUAL_FEE", precision = 14, scale = 4)
    public BigDecimal getActualFee()
    {
        return this.actualFee;
    }

    public void setActualFee(BigDecimal actualFee)
    {
        this.actualFee = actualFee;
    }

    @Column(name = "RECEIVABLE_FEE", precision = 14, scale = 4)
    public BigDecimal getReceivableFee()
    {
        return this.receivableFee;
    }

    public void setReceivableFee(BigDecimal receivableFee)
    {
        this.receivableFee = receivableFee;
    }

    @Column(name = "EXAM_FLAG", length = 12)
    public String getExamFlag()
    {
        return examFlag;
    }

    public void setExamFlag(String examFlag)
    {
        this.examFlag = examFlag;
    }

    // Author :jia_yanqing
    // Date : 2013/5/28 13:30
    // [BUG]0033056 ADD BEGIN
    @Column(name = "NATIVEPLACE_CODE", length = 20)
    public String getNativeplaceCode()
    {
        return nativeplaceCode;
    }

    public void setNativeplaceCode(String nativeplaceCode)
    {
        this.nativeplaceCode = nativeplaceCode;
    }

    @Column(name = "NATIVEPLACE_NAME", length = 256)
    public String getNativeplaceName()
    {
        return nativeplaceName;
    }

    public void setNativeplaceName(String nativeplaceName)
    {
        this.nativeplaceName = nativeplaceName;
    }
    // [BUG]0033056 ADD END
    @Column(name = "CREATEBY", length = 12)
    public String getCreateby()
    {
        return this.createby;
    }

    public void setCreateby(String createby)
    {
        this.createby = createby;
    }
    
    @Column(name = "UPDATEBY", length = 12)
    public String getUpdateby()
    {
        return this.updateby;
    }

    public void setUpdateby(String updateby)
    {
        this.updateby = updateby;
    }
    
    @Column(name = "DELETEBY", length = 12)
    public String getDeleteby()
    {
        return this.deleteby;
    }

    public void setDeleteby(String deleteby)
    {
        this.deleteby = deleteby;
    }
    
   @Temporal(TemporalType.DATE)
    @Column(name = "ENTRANCE_TIME", length = 7)
    public Date getEntranceTime()
    {
        return entranceTime;
    }

    public void setEntranceTime(Date entranceTime)
    {
        this.entranceTime = entranceTime;
    }

    @Column(name = "ORG_CODE", length = 50)
    public String getOrgCode()
    {
        return orgCode;
    }

    public void setOrgCode(String orgCode)
    {
        this.orgCode = orgCode;
    }

    @Column(name = "ORG_NAME", length = 50)
    public String getOrgName()
    {
        return orgName;
    }

    public void setOrgName(String orgName)
    {
        this.orgName = orgName;
    }

}
