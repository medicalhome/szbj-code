package com.founder.cdr.entity;

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
@Table(name = "EXTERNAL_SEND_REPORT")
public class InfectiousContent implements Serializable {

	private BigDecimal reportContentSn;
	private String sendFlag;
	private String spreadWay;
	private String infectiousName;
	private String reportContentItemCode;
	private String reportContentItemName;
	private BigDecimal businessSn;
	private Date createTime;
	private Date updateTime;
	private BigDecimal updateCount;
	private String deleteFlag;
	private Date deleteTime;
	private String medicalType;
	private Date sendDate;
	private Date sendOthersDate;
	private String sendOthersFlag;

	private String infectResult;

	@Id
	@GeneratedValue(generator = "native-generator")
	@Generator(name = "native-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "SEQ_REPORT_CONTENT") })
	public BigDecimal getReportContentSn() {
		return this.reportContentSn;
	}

	public void setReportContentSn(BigDecimal reportContentSn) {
		this.reportContentSn = reportContentSn;
	}

	@Column(name = "REPORT_CONTENT_ITEM_CODE", length = 180)
	public String getReportContentItemCode() {
		return reportContentItemCode;
	}

	public void setReportContentItemCode(String reportContentItemCode) {
		this.reportContentItemCode = reportContentItemCode;
	}

	@Column(name = "BUSINESS_SN", length = 192)
	public BigDecimal getBusinessSn() {
		return this.businessSn;
	}

	public void setBusinessSn(BigDecimal businessSn) {
		this.businessSn = businessSn;
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

	@Column(name = "INFECTIOUS_NAME", length = 7)
	public String getInfectiousName() {
		return infectiousName;
	}

	public void setInfectiousName(String infectiousName) {
		this.infectiousName = infectiousName;
	}

	@Column(name = "send_flag", length = 7)
	public String getSendFlag() {
		return sendFlag;
	}

	public void setSendFlag(String sendFlag) {
		this.sendFlag = sendFlag;
	}

	@Column(name = "spread_way", length = 7)
	public String getSpreadWay() {
		return spreadWay;
	}

	public void setSpreadWay(String spreadWay) {
		this.spreadWay = spreadWay;
	}

	@Column(name = "report_content_item_name", length = 7)
	public String getReportContentItemName() {
		return reportContentItemName;
	}

	public void setReportContentItemName(String reportContentItemName) {
		this.reportContentItemName = reportContentItemName;
	}
	

	@Column(name = "medical_type", length = 7)
	public String getMedicalType() {
		return medicalType;
	}

	public void setMedicalType(String medicalType) {
		this.medicalType = medicalType;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "send_date", length = 7)
	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "send_others_date", length = 7)
	public Date getSendOthersDate() {
		return sendOthersDate;
	}

	public void setSendOthersDate(Date sendOthersDate) {
		this.sendOthersDate = sendOthersDate;
	}

	@Column(name = "send_others_flag", length = 7)
	public String getSendOthersFlag() {
		return sendOthersFlag;
	}

	public void setSendOthersFlag(String sendOthersFlag) {
		this.sendOthersFlag = sendOthersFlag;
	}

	@Column(name = "infect_result", length = 7)
	public String getInfectResult() {
		return infectResult;
	}

	public void setInfectResult(String infectResult) {
		this.infectResult = infectResult;
	}

}
