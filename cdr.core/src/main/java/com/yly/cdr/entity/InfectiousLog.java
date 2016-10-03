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
@Table(name = "EXTERNAL_SEND_LOG")
public class InfectiousLog implements Serializable {
	private BigDecimal sendLogSn;
	private BigDecimal sendReportSn;
	private Date SendDate;
	private String SendPersonCode;
	private String SendPersonName;
	private Date createTime;
	private Date updateTime;
	private BigDecimal updateCount;
	private String deleteFlag;
	private Date deleteTime;
	
	@Id
	@GeneratedValue(generator = "native-generator")
	@Generator(name = "native-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "SEQ_EXTERNAL_SEND_RECORD") })
	public BigDecimal getSendLogSn() {
		return sendLogSn;
	}
	public void setSendLogSn(BigDecimal sendLogSn) {
		this.sendLogSn = sendLogSn;
	}
	
	@Column(name = "send_report_sn", length = 12)
	public BigDecimal getSendReportSn() {
		return sendReportSn;
	}
	public void setSendReportSn(BigDecimal sendReportSn) {
		this.sendReportSn = sendReportSn;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "Send_Date", length = 12)
	public Date getSendDate() {
		return SendDate;
	}
	public void setSendDate(Date sendDate) {
		SendDate = sendDate;
	}
	
	@Column(name = "Send_Person_Code", length = 12)
	public String getSendPersonCode() {
		return SendPersonCode;
	}
	public void setSendPersonCode(String sendPersonCode) {
		SendPersonCode = sendPersonCode;
	}
	
	@Column(name = "Send_Person_Name", length = 12)
	public String getSendPersonName() {
		return SendPersonName;
	}
	public void setSendPersonName(String sendPersonName) {
		SendPersonName = sendPersonName;
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

}
