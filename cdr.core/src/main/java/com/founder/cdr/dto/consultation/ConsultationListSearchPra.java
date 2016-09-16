package com.founder.cdr.dto.consultation;

public class ConsultationListSearchPra {
	// 申请开始日期
	private String consultationStartDate;

	// 申请结束日期
	private String consultationStopDate;

	// 会诊申请类型
	private String consultationRequestType;

	// 会诊申请科室
	private String consultationRequestDept;

	// 医疗机构
	private String orgCode;

	public String getConsultationStartDate() {
		return consultationStartDate;
	}

	public void setConsultationStartDate(String consultationStartDate) {
		this.consultationStartDate = consultationStartDate;
	}

	public String getConsultationStopDate() {
		return consultationStopDate;
	}

	public void setConsultationStopDate(String consultationStopDate) {
		this.consultationStopDate = consultationStopDate;
	}

	public String getConsultationRequestType() {
		return consultationRequestType;
	}

	public void setConsultationRequestType(String consultationRequestType) {
		this.consultationRequestType = consultationRequestType;
	}

	public String getConsultationRequestDept() {
		return consultationRequestDept;
	}

	public void setConsultationRequestDept(String consultationRequestDept) {
		this.consultationRequestDept = consultationRequestDept;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
}
