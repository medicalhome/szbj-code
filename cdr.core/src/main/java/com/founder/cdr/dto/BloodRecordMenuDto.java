package com.founder.cdr.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.founder.cdr.entity.BloodRecord;
import com.founder.cdr.entity.ExamResult;

/**
 * @date 2014/03/20  
 * @author yang_jianbo
 */
public class BloodRecordMenuDto
{
	private String menuSn;
	private String requestSn;
	private String visitSn;
	private String patientSn;
	private String menuLid;
	private String orgcode;
	private String orgName;
	private String patientDomain;
	private String requestBloodAboCode;
	private String requestBloodAboName;
	private String requestBloodRhCode;
	private String requestBloodRhName;
	private String recheckBloodAboCode;
	private String recheckBloodAboName;
	private String recheckBloodRhCode;
	private String recheckBloodRhName;
	private String bloodSpecialRequestCode;
	private String bloodSpecialRequestName;
	private String crossMatchResult;
	private String matchBloodPersonName;
	private Date matchBloodDate;
	private String assignedPersonName;
	private Date sendBloodDate;
	private String playingEntityName;
	private Date playingDate;
	private String irregularImmuneResult;
	private String remark;
	private String createby;
	private Date createTime;
	private String updateby;
	private Date updateTime;
	private BigDecimal updateCount;
	private String deleteby;
	private Date deleteTime;
	private String deleteFlag;
	
	private List<BloodRecord> bloodRecord;
	private List<ExamResult> examResult;
	public String getMenuSn() {
		return menuSn;
	}
	public void setMenuSn(String menuSn) {
		this.menuSn = menuSn;
	}
	public String getRequestSn() {
		return requestSn;
	}
	public void setRequestSn(String requestSn) {
		this.requestSn = requestSn;
	}
	public String getVisitSn() {
		return visitSn;
	}
	public void setVisitSn(String visitSn) {
		this.visitSn = visitSn;
	}
	public String getPatientSn() {
		return patientSn;
	}
	public void setPatientSn(String patientSn) {
		this.patientSn = patientSn;
	}
	public String getMenuLid() {
		return menuLid;
	}
	public void setMenuLid(String menuLid) {
		this.menuLid = menuLid;
	}
	public String getOrgcode() {
		return orgcode;
	}
	public void setOrgcode(String orgcode) {
		this.orgcode = orgcode;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getPatientDomain() {
		return patientDomain;
	}
	public void setPatientDomain(String patientDomain) {
		this.patientDomain = patientDomain;
	}
	public String getRequestBloodAboCode() {
		return requestBloodAboCode;
	}
	public void setRequestBloodAboCode(String requestBloodAboCode) {
		this.requestBloodAboCode = requestBloodAboCode;
	}
	public String getRequestBloodAboName() {
		return requestBloodAboName;
	}
	public void setRequestBloodAboName(String requestBloodAboName) {
		this.requestBloodAboName = requestBloodAboName;
	}
	public String getRequestBloodRhCode() {
		return requestBloodRhCode;
	}
	public void setRequestBloodRhCode(String requestBloodRhCode) {
		this.requestBloodRhCode = requestBloodRhCode;
	}
	public String getRequestBloodRhName() {
		return requestBloodRhName;
	}
	public void setRequestBloodRhName(String requestBloodRhName) {
		this.requestBloodRhName = requestBloodRhName;
	}
	public String getRecheckBloodAboCode() {
		return recheckBloodAboCode;
	}
	public void setRecheckBloodAboCode(String recheckBloodAboCode) {
		this.recheckBloodAboCode = recheckBloodAboCode;
	}
	public String getRecheckBloodAboName() {
		return recheckBloodAboName;
	}
	public void setRecheckBloodAboName(String recheckBloodAboName) {
		this.recheckBloodAboName = recheckBloodAboName;
	}
	public String getRecheckBloodRhCode() {
		return recheckBloodRhCode;
	}
	public void setRecheckBloodRhCode(String recheckBloodRhCode) {
		this.recheckBloodRhCode = recheckBloodRhCode;
	}
	public String getRecheckBloodRhName() {
		return recheckBloodRhName;
	}
	public void setRecheckBloodRhName(String recheckBloodRhName) {
		this.recheckBloodRhName = recheckBloodRhName;
	}
	public String getBloodSpecialRequestCode() {
		return bloodSpecialRequestCode;
	}
	public void setBloodSpecialRequestCode(String bloodSpecialRequestCode) {
		this.bloodSpecialRequestCode = bloodSpecialRequestCode;
	}
	public String getBloodSpecialRequestName() {
		return bloodSpecialRequestName;
	}
	public void setBloodSpecialRequestName(String bloodSpecialRequestName) {
		this.bloodSpecialRequestName = bloodSpecialRequestName;
	}
	public String getCrossMatchResult() {
		return crossMatchResult;
	}
	public void setCrossMatchResult(String crossMatchResult) {
		this.crossMatchResult = crossMatchResult;
	}
	public String getMatchBloodPersonName() {
		return matchBloodPersonName;
	}
	public void setMatchBloodPersonName(String matchBloodPersonName) {
		this.matchBloodPersonName = matchBloodPersonName;
	}
	public Date getMatchBloodDate() {
		return matchBloodDate;
	}
	public void setMatchBloodDate(Date matchBloodDate) {
		this.matchBloodDate = matchBloodDate;
	}
	public String getAssignedPersonName() {
		return assignedPersonName;
	}
	public void setAssignedPersonName(String assignedPersonName) {
		this.assignedPersonName = assignedPersonName;
	}
	public Date getSendBloodDate() {
		return sendBloodDate;
	}
	public void setSendBloodDate(Date sendBloodDate) {
		this.sendBloodDate = sendBloodDate;
	}
	public String getPlayingEntityName() {
		return playingEntityName;
	}
	public void setPlayingEntityName(String playingEntityName) {
		this.playingEntityName = playingEntityName;
	}
	
	public Date getPlayingDate() {
		return playingDate;
	}
	public void setPlayingDate(Date playingDate) {
		this.playingDate = playingDate;
	}
	public String getIrregularImmuneResult() {
		return irregularImmuneResult;
	}
	public void setIrregularImmuneResult(String irregularImmuneResult) {
		this.irregularImmuneResult = irregularImmuneResult;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreateby() {
		return createby;
	}
	public void setCreateby(String createby) {
		this.createby = createby;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getUpdateby() {
		return updateby;
	}
	public void setUpdateby(String updateby) {
		this.updateby = updateby;
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
	public String getDeleteby() {
		return deleteby;
	}
	public void setDeleteby(String deleteby) {
		this.deleteby = deleteby;
	}
	public Date getDeleteTime() {
		return deleteTime;
	}
	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}
	public String getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public List<BloodRecord> getBloodRecord() {
		return bloodRecord;
	}
	public void setBloodRecord(List<BloodRecord> bloodRecord) {
		this.bloodRecord = bloodRecord;
	}
	public List<ExamResult> getExamResult() {
		return examResult;
	}
	public void setExamResult(List<ExamResult> examResult) {
		this.examResult = examResult;
	}
	
}
