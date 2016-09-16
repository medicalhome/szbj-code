package com.founder.cdr.hl7.dto.bs360;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.founder.cdr.hl7.dto.BaseDto;
import com.founder.cdr.hl7.dto.BloodRecordDto;
import com.founder.cdr.hl7.dto.BloodRecordSpecialRequestDto;
import com.founder.cdr.hl7.dto.ExamResultDto;

public class BS360  extends BaseDto {
	/**
	 * 操作
	 */
	@NotEmpty(message = "{BS360.action}")
	private String action;
	
	/**
	 * 输血记录单ID
	 */
	@NotEmpty(message = "{BS360.menuLid}")
	private String menuLid;
	
	 /**
     * 域ID
     */
    @NotEmpty(message = "{BS360.domainLid}")
    private String domainLid;
    
    /**
     * 患者ID
     */
    @NotEmpty(message = "{BS360.patientLid}")
    private String patientLid;
    
    /**
	 * 患者名称
	 */
    @NotEmpty(message = "{BS360.patientName}")
	private String patientName;
    
    /**
     * 就诊次数
     */
    //@NotEmpty(message = "{BS360.times}")
    private String times;
    
    /**
     * 就诊流水号
     */
    @NotEmpty(message = "{BS360.visitOrdNo}")
    private String visitOrdNo;
    
    /**
     * 就诊类型
     */
    @NotEmpty(message = "{BS360.visitTypeCode}")
    private String visitTypeCode;

    /**
     * 医疗机构编码
     */
    private String hospitalCode;    
    		
    /**
     * 医疗机构名称
     */
    private String hospitalName;

	/**
	 * 输血申请单ID
	 */
	private String bloodRequestLid;
    
	/**
	 *申请ABO血型编码 
	 */
//    @NotEmpty(message = "{BS360.requestBloodABOCode}")
	private String requestBloodABOCode;
    
	/**
	 * 申请ABO血型名称
	 */
//    @NotEmpty(message = "{BS360.requestBloodABOName}")
	private String requestBloodABOName;
    
	/**
	 * 申请RH血型编码 
	 */
//    @NotEmpty(message = "{BS360.requestBloodRHCode}")
	private String requestBloodRHCode;
    
	/**
	 * 申请RH血型名称
	 */
//    @NotEmpty(message = "{BS360.requestBloodRHName}")
	private String requestBloodRHName;
    
    /**
	 *复检ABO血型编码 
	 */
//    @NotEmpty(message = "{BS360.recheckBloodABOCode}")
	private String recheckBloodABOCode;
	
	/**
	 * 复检ABO血型名称				
	 */
//    @NotEmpty(message = "{BS360.recheckBloodABOName}")
	private String recheckBloodABOName;
	
	/**
	 *复检RH血型编码				 
	 */
//    @NotEmpty(message = "{BS360.recheckBloodRHCode}")
	private String recheckBloodRHCode;
	
	/**
	 *复检RH血型名称 
	 */
//    @NotEmpty(message = "{BS360.recheckBloodRHName}")
	private String recheckBloodRHName;

    /**
	 * 血液特殊要求
	 */
	private List<BloodRecordSpecialRequestDto> specialRequests;
	
	private String BloodSpecialRequestName;
	
	/**
	 * 输血记录信息
	 */
    @NotEmpty(message = "{BS360.bloodRecord}")
	private List<BloodRecordDto> bloodRecords;	
    	
	/**
	 * 其他检查结果
	 */
	private List<ExamResultDto> examResults;

	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 申请单生效日期
	 */
	private String effectiveTime;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getMenuLid() {
		return menuLid;
	}

	public void setMenuLid(String menuLid) {
		this.menuLid = menuLid;
	}

	public String getDomainLid() {
		return domainLid;
	}

	public void setDomainLid(String domainLid) {
		this.domainLid = domainLid;
	}

	public String getPatientLid() {
		return patientLid;
	}

	public void setPatientLid(String patientLid) {
		this.patientLid = patientLid;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getTimes() {
		return times;
	}

	public void setTimes(String times) {
		this.times = times;
	}

	public String getHospitalCode() {
		return hospitalCode;
	}

	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public String getBloodRequestLid() {
		return bloodRequestLid;
	}

	public void setBloodRequestLid(String bloodRequestLid) {
		this.bloodRequestLid = bloodRequestLid;
	}

	public String getRequestBloodABOCode() {
		return requestBloodABOCode;
	}

	public void setRequestBloodABOCode(String requestBloodABOCode) {
		this.requestBloodABOCode = requestBloodABOCode;
	}

	public String getRequestBloodABOName() {
		return requestBloodABOName;
	}

	public void setRequestBloodABOName(String requestBloodABOName) {
		this.requestBloodABOName = requestBloodABOName;
	}

	public String getRequestBloodRHCode() {
		return requestBloodRHCode;
	}

	public void setRequestBloodRHCode(String requestBloodRHCode) {
		this.requestBloodRHCode = requestBloodRHCode;
	}

	public String getRequestBloodRHName() {
		return requestBloodRHName;
	}

	public void setRequestBloodRHName(String requestBloodRHName) {
		this.requestBloodRHName = requestBloodRHName;
	}

	public String getRecheckBloodABOCode() {
		return recheckBloodABOCode;
	}

	public void setRecheckBloodABOCode(String recheckBloodABOCode) {
		this.recheckBloodABOCode = recheckBloodABOCode;
	}

	public String getRecheckBloodABOName() {
		return recheckBloodABOName;
	}

	public void setRecheckBloodABOName(String recheckBloodABOName) {
		this.recheckBloodABOName = recheckBloodABOName;
	}

	public String getRecheckBloodRHCode() {
		return recheckBloodRHCode;
	}

	public void setRecheckBloodRHCode(String recheckBloodRHCode) {
		this.recheckBloodRHCode = recheckBloodRHCode;
	}

	public String getRecheckBloodRHName() {
		return recheckBloodRHName;
	}

	public void setRecheckBloodRHName(String recheckBloodRHName) {
		this.recheckBloodRHName = recheckBloodRHName;
	}

	public String getBloodSpecialRequestName() {
		return BloodSpecialRequestName;
	}

	public void setBloodSpecialRequestName(String bloodSpecialRequestName) {
		BloodSpecialRequestName = bloodSpecialRequestName;
	}

	public List<BloodRecordSpecialRequestDto> getSpecialRequests() {
		return specialRequests;
	}

	public void setBloodSpecialRequests(
			List<BloodRecordSpecialRequestDto> specialRequests) {
		this.specialRequests = specialRequests;
	}

	public List<BloodRecordDto> getBloodRecords() {
		return bloodRecords;
	}

	public void setBloodRecords(List<BloodRecordDto> bloodRecords) {
		this.bloodRecords = bloodRecords;
	}

	public List<ExamResultDto> getExamResults() {
		return examResults;
	}

	public void setExamResults(List<ExamResultDto> examResults) {
		this.examResults = examResults;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getEffectiveTime() {
		return effectiveTime;
	}

	public void setEffectiveTime(String effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	public String getVisitOrdNo() {
		return visitOrdNo;
	}

	public void setVisitOrdNo(String visitOrdNo) {
		this.visitOrdNo = visitOrdNo;
	}

	public String getVisitTypeCode() {
		return visitTypeCode;
	}

	public void setVisitTypeCode(String visitTypeCode) {
		this.visitTypeCode = visitTypeCode;
	}
	
}
