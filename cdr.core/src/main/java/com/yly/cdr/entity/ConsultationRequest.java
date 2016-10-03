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
import javax.persistence.UniqueConstraint;

import com.founder.fasf.core.util.daohelper.annotation.Generator;
import com.founder.fasf.core.util.daohelper.annotation.Parameter;

@Entity
@Table(name = "CONSULTATION_REQUEST", uniqueConstraints = @UniqueConstraint(columnNames = "ORDER_SN"))
public class ConsultationRequest implements Serializable
{

    private BigDecimal requestSn;

    private BigDecimal visitSn;

    private BigDecimal orderSn;

    private String requestNo;

    private String consultationType;

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
     * 添加患者内部序列号
     */
    private BigDecimal patientSn;
    /*
     * 设置紧急程度编码
     */
    private String urgencyCode;
    /*
     * 设置紧急程度名称
     */
    private String urgencyName;
    
    /*
     * 设置会诊医院ID
     */
    private String consultationOrgCode;
    /*
     * 设置会诊医院名称
     */
    private String consultationOrgName;
    /*
     * 设置会诊申请类型名称
     */
    private String consultationTypeName;
    /*
     * 设置医嘱类别id
     */
    private String orderCode;
    /*
     * 设置医嘱类别名称
     */
    private String orderName;
    
    
    public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public String getConsultationTypeName() {
		return consultationTypeName;
	}

	public void setConsultationTypeName(String consultationTypeName) {
		this.consultationTypeName = consultationTypeName;
	}

	@Id
    @GeneratedValue(generator = "native-generator")
    @Generator(name = "native-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "SEQ_REQUEST") })
    public BigDecimal getRequestSn()
    {
        return this.requestSn;
    }

    public void setRequestSn(BigDecimal requestSn)
    {
        this.requestSn = requestSn;
    }


	public BigDecimal getPatientSn() {
		return patientSn;
	}

	public void setPatientSn(BigDecimal patientSn) {
		this.patientSn = patientSn;
	}

	@Column(name = "VISIT_SN", nullable = false, precision = 22, scale = 0)
    public BigDecimal getVisitSn()
    {
        return this.visitSn;
    }

    public void setVisitSn(BigDecimal visitSn)
    {
        this.visitSn = visitSn;
    }

    @Column(name = "ORDER_SN", precision = 22, scale = 0)
    public BigDecimal getOrderSn()
    {
        return this.orderSn;
    }

    public void setOrderSn(BigDecimal orderSn)
    {
        this.orderSn = orderSn;
    }

    @Column(name = "REQUEST_NO", nullable = false, length = 20)
    public String getRequestNo()
    {
        return this.requestNo;
    }

    public void setRequestNo(String requestNo)
    {
        this.requestNo = requestNo;
    }

    @Column(name = "CONSULTATION_TYPE", length = 6)
    public String getConsultationType()
    {
        return this.consultationType;
    }

    public void setConsultationType(String consultationType)
    {
        this.consultationType = consultationType;
    }

    @Column(name = "REASON")
    public String getReason()
    {
        return this.reason;
    }

    public void setReason(String reason)
    {
        this.reason = reason;
    }

    @Column(name = "REQUIREMENTS")
    public String getRequirements()
    {
        return this.requirements;
    }

    public void setRequirements(String requirements)
    {
        this.requirements = requirements;
    }

    @Column(name = "CHIEF_COMPLIANT")
    public String getChiefCompliant()
    {
        return this.chiefCompliant;
    }

    public void setChiefCompliant(String chiefCompliant)
    {
        this.chiefCompliant = chiefCompliant;
    }

    @Column(name = "REQUEST_DOCTOR_NAME", length = 40)
    public String getRequestDoctorName()
    {
        return this.requestDoctorName;
    }

    public void setRequestDoctorName(String requestDoctorName)
    {
        this.requestDoctorName = requestDoctorName;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "REQUEST_TIME", length = 7)
    public Date getRequestTime()
    {
        return this.requestTime;
    }

    public void setRequestTime(Date requestTime)
    {
        this.requestTime = requestTime;
    }

    @Column(name = "MEMO", length = 200)
    public String getMemo()
    {
        return this.memo;
    }

    public void setMemo(String memo)
    {
        this.memo = memo;
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

    @Column(name = "CONSULTATION_PLACE", length = 100)
    public String getConsultationPlace()
    {
        return this.consultationPlace;
    }

    public void setConsultationPlace(String consultationPlace)
    {
        this.consultationPlace = consultationPlace;
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

    @Column(name = "CONSULTATION_DEPT_ID", length = 60)
    public String getConsultationDeptId()
    {
        return this.consultationDeptId;
    }

    public void setConsultationDeptId(String consultationDeptId)
    {
        this.consultationDeptId = consultationDeptId;
    }

    @Column(name = "CONSULTATION_DEPT_NAME")
    public String getConsultationDeptName()
    {
        return this.consultationDeptName;
    }

    public void setConsultationDeptName(String consultationDeptName)
    {
        this.consultationDeptName = consultationDeptName;
    }

    @Column(name = "REQUEST_DOCTOR_ID", length = 12)
    public String getRequestDoctorId()
    {
        return this.requestDoctorId;
    }

    public void setRequestDoctorId(String requestDoctorId)
    {
        this.requestDoctorId = requestDoctorId;
    }

    @Column(name = "REQUEST_STATUS_CODE", length = 12)
    public String getRequestStatusCode()
    {
        return this.requestStatusCode;
    }

    public void setRequestStatusCode(String requestStatusCode)
    {
        this.requestStatusCode = requestStatusCode;
    }

    @Column(name = "REQUEST_STATUS_NAME", length = 40)
    public String getRequestStatusName()
    {
        return this.requestStatusName;
    }

    public void setRequestStatusName(String requestStatusName)
    {
        this.requestStatusName = requestStatusName;
    }

    @Column(name = "CONSULTATION_PERSON_ID", length = 60)
    public String getConsultationPersonId()
    {
        return this.consultationPersonId;
    }

    public void setConsultationPersonId(String consultationPersonId)
    {
        this.consultationPersonId = consultationPersonId;
    }

    @Column(name = "CONSULTATION_PERSON_NAME")
    public String getConsultationPersonName()
    {
        return this.consultationPersonName;
    }

    public void setConsultationPersonName(String consultationPersonName)
    {
        this.consultationPersonName = consultationPersonName;
    }
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
    
    @Column(name = "ORG_CODE", length = 12)
    public String getOrgCode()
    {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode)
    {
        this.orgCode = orgCode;
    }
    
    @Column(name = "ORG_NAME", length = 12)
    public String getOrgName()
    {
        return this.orgName;
    }

    public void setOrgName(String orgName)
    {
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

	public String getConsultationOrgCode() {
		return consultationOrgCode;
	}

	public void setConsultationOrgCode(String consultationOrgCode) {
		this.consultationOrgCode = consultationOrgCode;
	}

	public String getConsultationOrgName() {
		return consultationOrgName;
	}

	public void setConsultationOrgName(String consultationOrgName) {
		this.consultationOrgName = consultationOrgName;
	}

}
