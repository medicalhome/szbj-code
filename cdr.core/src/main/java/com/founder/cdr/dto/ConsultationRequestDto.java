package com.founder.cdr.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 *[FUN]会诊申请列表
 *@date 2014/07/14
 *@author li_shenggen
 *@since 1.0
 *
 */
public class ConsultationRequestDto
{
    private BigDecimal requestSn;

    private BigDecimal visitSn;

    private BigDecimal orderSn;

    private String requestNo;

    private String consultationType;
    private String consultationTypeName;

    private String reason;

    private String requirements;

    private String chiefCompliant;

    private String requestDoctorName;

    private Date requestTime;

    private String memo;

    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;

    private String consultationPlace;

    private String patientDomain;

    private String patientLid;

    private String consultationDeptId;

    private String consultationDeptName;

    private String requestDoctorId;

    private String requestStatusCode;

    private String requestStatusName;

    private String consultationPersonId;

    private String consultationPersonName;

    private String createby;

    private String updateby;

    private String deleteby;
    
    private String orgCode;
    
    private String orgName;
    /*
     * 添加会诊申请科室代码
     */   
    private String orderDeptId;
    /*
     * 添加会诊申请科室名称
     */
    private String orderDeptName;
    /*
     * 设置紧急程度编码
     */
    private String urgencyCode;
    /*
     * 设置紧急程度名称
     */
    
    private String urgencyName;
    private BigDecimal patientSn;
    
	public String getConsultationTypeName() {
		return consultationTypeName;
	}
	public void setConsultationTypeName(String consultationTypeName) {
		this.consultationTypeName = consultationTypeName;
	}
	public BigDecimal getPatientSn() {
		return patientSn;
	}
	public void setPatientSn(BigDecimal patientSn) {
		this.patientSn = patientSn;
	}
	public String getUrgencyCode() {
		return urgencyCode;
	}
	public void setUrgencyCode(String urgencyCode) {
		this.urgencyCode = urgencyCode;
	}
	public String getUrgencyName() {
		return urgencyName;
	}
	public void setUrgencyName(String urgencyName) {
		this.urgencyName = urgencyName;
	}
	public BigDecimal getRequestSn() {
		return requestSn;
	}
	public void setRequestSn(BigDecimal requestSn) {
		this.requestSn = requestSn;
	}
	public BigDecimal getVisitSn() {
		return visitSn;
	}
	public void setVisitSn(BigDecimal visitSn) {
		this.visitSn = visitSn;
	}
	public BigDecimal getOrderSn() {
		return orderSn;
	}
	public void setOrderSn(BigDecimal orderSn) {
		this.orderSn = orderSn;
	}
	public String getRequestNo() {
		return requestNo;
	}
	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}
	public String getConsultationType() {
		return consultationType;
	}
	public void setConsultationType(String consultationType) {
		this.consultationType = consultationType;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getRequirements() {
		return requirements;
	}
	public void setRequirements(String requirements) {
		this.requirements = requirements;
	}
	public String getChiefCompliant() {
		return chiefCompliant;
	}
	public void setChiefCompliant(String chiefCompliant) {
		this.chiefCompliant = chiefCompliant;
	}
	public String getRequestDoctorName() {
		return requestDoctorName;
	}
	public void setRequestDoctorName(String requestDoctorName) {
		this.requestDoctorName = requestDoctorName;
	}
	public Date getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public BigDecimal getUpdateCount() {
		return updateCount;
	}
	public void setUpdateCount(BigDecimal updateCount) {
		this.updateCount = updateCount;
	}
	public String getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public Date getDeleteTime() {
		return deleteTime;
	}
	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}
	public String getConsultationPlace() {
		return consultationPlace;
	}
	public void setConsultationPlace(String consultationPlace) {
		this.consultationPlace = consultationPlace;
	}
	public String getPatientDomain() {
		return patientDomain;
	}
	public void setPatientDomain(String patientDomain) {
		this.patientDomain = patientDomain;
	}
	public String getPatientLid() {
		return patientLid;
	}
	public void setPatientLid(String patientLid) {
		this.patientLid = patientLid;
	}
	public String getConsultationDeptId() {
		return consultationDeptId;
	}
	public void setConsultationDeptId(String consultationDeptId) {
		this.consultationDeptId = consultationDeptId;
	}
	public String getConsultationDeptName() {
		return consultationDeptName;
	}
	public void setConsultationDeptName(String consultationDeptName) {
		this.consultationDeptName = consultationDeptName;
	}
	public String getRequestDoctorId() {
		return requestDoctorId;
	}
	public void setRequestDoctorId(String requestDoctorId) {
		this.requestDoctorId = requestDoctorId;
	}
	public String getRequestStatusCode() {
		return requestStatusCode;
	}
	public void setRequestStatusCode(String requestStatusCode) {
		this.requestStatusCode = requestStatusCode;
	}
	public String getRequestStatusName() {
		return requestStatusName;
	}
	public void setRequestStatusName(String requestStatusName) {
		this.requestStatusName = requestStatusName;
	}
	public String getConsultationPersonId() {
		return consultationPersonId;
	}
	public void setConsultationPersonId(String consultationPersonId) {
		this.consultationPersonId = consultationPersonId;
	}
	public String getConsultationPersonName() {
		return consultationPersonName;
	}
	public void setConsultationPersonName(String consultationPersonName) {
		this.consultationPersonName = consultationPersonName;
	}
	public String getCreateby() {
		return createby;
	}
	public void setCreateby(String createby) {
		this.createby = createby;
	}
	public String getUpdateby() {
		return updateby;
	}
	public void setUpdateby(String updateby) {
		this.updateby = updateby;
	}
	public String getDeleteby() {
		return deleteby;
	}
	public void setDeleteby(String deleteby) {
		this.deleteby = deleteby;
	}
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
	public String getOrderDeptId() {
		return orderDeptId;
	}
	public void setOrderDeptId(String orderDeptId) {
		this.orderDeptId = orderDeptId;
	}
	public String getOrderDeptName() {
		return orderDeptName;
	}
	public void setOrderDeptName(String orderDeptName) {
		this.orderDeptName = orderDeptName;
	}
}
