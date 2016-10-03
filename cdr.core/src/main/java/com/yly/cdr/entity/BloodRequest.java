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
@Table(name = "BLOOD_REQUEST")
public class BloodRequest implements Serializable
{

    private BigDecimal requestSn;

    private BigDecimal visitSn;

    private BigDecimal patientSn;

    private String patientDomain;

    private String patientLid;

    private String requestNo;

    private BigDecimal quantity;

    private Date transfusionDate;

    private Date requestDate;

    private String description;

    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;

    private String orderLid;

    private String requestPersonName;

    private String bloodAboCode;

    private String bloodAboName;

    private String bloodRhCode;

    private String bloodRhName;

    private String bloodClassCode;

    private String bloodClassName;

    private String requestDeptId;

    private String requestDeptName;

    private String bloodReasonCode;

    private String bloodReasonName;

    private String orderStatusCode;

    private String orderStatusName;

    private String requestPersonId;

    private String urgencyCode;

    private String urgencyName;

    private String confirmPerson;

    private String confirmPersonName;

    private Date confirmTime;

    private String quantityUnit;

    private String pregnancyFlag;

    private String pregnancyCount;

    private String childbirthFlag;

    private String childbirthCount;

    private String historyFlag;

    private String adverseHistoryFlag;

    private String newbornFlag;

    private String otherDiseaseFlag;

    private String otherDiseaseName;

    private String selfBloodFlag;
    
    private String clinicalDiagnosis;
    
    private String orgCode;
    
    private String orgName;
    
    private String createby;
    
    private String updateby;
    
    private String deleteby;
    // add by li_shenggen begin 2014/11/13 11:13
    /**
     * 是否交叉配血
     */
    private String crossMatchBloodFlag;
    
    /**
     * 交叉配血名称
     */
    private String crossMatchBloodName;
    
    /**
     * 是否备用
     */
    private String reserveFlag;
    
    /**
     * 备用名称
     */
    private String reserveName;
    
    /**
     * 是否干细胞移植后循环
     */
    private String stemCellTransplantFlag;
    
    /**
     * 干细胞移植后循环名称
     */
    private String stemCellTransplantName;
    // end
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

    @Column(name = "VISIT_SN", nullable = false, precision = 22, scale = 0)
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

    @Column(name = "PATIENT_LID", nullable = false, length = 20)
    public String getPatientLid()
    {
        return this.patientLid;
    }

    public void setPatientLid(String patientLid)
    {
        this.patientLid = patientLid;
    }

    @Column(name = "REQUEST_NO", length = 20)
    public String getRequestNo()
    {
        return this.requestNo;
    }

    public void setRequestNo(String requestNo)
    {
        this.requestNo = requestNo;
    }

    @Column(name = "QUANTITY", precision = 22, scale = 0)
    public BigDecimal getQuantity()
    {
        return this.quantity;
    }

    public void setQuantity(BigDecimal quantity)
    {
        this.quantity = quantity;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "TRANSFUSION_DATE", length = 7)
    public Date getTransfusionDate()
    {
        return this.transfusionDate;
    }

    public void setTransfusionDate(Date transfusionDate)
    {
        this.transfusionDate = transfusionDate;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "REQUEST_DATE", length = 7)
    public Date getRequestDate()
    {
        return this.requestDate;
    }

    public void setRequestDate(Date requestDate)
    {
        this.requestDate = requestDate;
    }

    @Column(name = "DESCRIPTION", length = 1200)
    public String getDescription()
    {
        return this.description;
    }

    public void setDescription(String description)
    {
        this.description = description;
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

    @Column(name = "ORDER_LID", length = 12)
    public String getOrderLid()
    {
        return this.orderLid;
    }

    public void setOrderLid(String orderLid)
    {
        this.orderLid = orderLid;
    }

    @Column(name = "REQUEST_PERSON_NAME", length = 192)
    public String getRequestPersonName()
    {
        return this.requestPersonName;
    }

    public void setRequestPersonName(String requestPersonName)
    {
        this.requestPersonName = requestPersonName;
    }

    @Column(name = "BLOOD_ABO_CODE", length = 10)
    public String getBloodAboCode()
    {
        return this.bloodAboCode;
    }

    public void setBloodAboCode(String bloodAboCode)
    {
        this.bloodAboCode = bloodAboCode;
    }

    @Column(name = "BLOOD_ABO_NAME", length = 240)
    public String getBloodAboName()
    {
        return this.bloodAboName;
    }

    public void setBloodAboName(String bloodAboName)
    {
        this.bloodAboName = bloodAboName;
    }

    @Column(name = "BLOOD_RH_CODE", length = 10)
    public String getBloodRhCode()
    {
        return this.bloodRhCode;
    }

    public void setBloodRhCode(String bloodRhCode)
    {
        this.bloodRhCode = bloodRhCode;
    }

    @Column(name = "BLOOD_RH_NAME", length = 300)
    public String getBloodRhName()
    {
        return this.bloodRhName;
    }

    public void setBloodRhName(String bloodRhName)
    {
        this.bloodRhName = bloodRhName;
    }

    @Column(name = "BLOOD_CLASS_CODE", length = 10)
    public String getBloodClassCode()
    {
        return this.bloodClassCode;
    }

    public void setBloodClassCode(String bloodClassCode)
    {
        this.bloodClassCode = bloodClassCode;
    }

    @Column(name = "BLOOD_CLASS_NAME", length = 300)
    public String getBloodClassName()
    {
        return this.bloodClassName;
    }

    public void setBloodClassName(String bloodClassName)
    {
        this.bloodClassName = bloodClassName;
    }

    @Column(name = "REQUEST_DEPT_ID", length = 14)
    public String getRequestDeptId()
    {
        return this.requestDeptId;
    }

    public void setRequestDeptId(String requestDeptId)
    {
        this.requestDeptId = requestDeptId;
    }

    @Column(name = "REQUEST_DEPT_NAME", length = 192)
    public String getRequestDeptName()
    {
        return this.requestDeptName;
    }

    public void setRequestDeptName(String requestDeptName)
    {
        this.requestDeptName = requestDeptName;
    }

    @Column(name = "BLOOD_REASON_CODE", length = 10)
    public String getBloodReasonCode()
    {
        return this.bloodReasonCode;
    }

    public void setBloodReasonCode(String bloodReasonCode)
    {
        this.bloodReasonCode = bloodReasonCode;
    }

    @Column(name = "BLOOD_REASON_NAME", length = 300)
    public String getBloodReasonName()
    {
        return this.bloodReasonName;
    }

    public void setBloodReasonName(String bloodReasonName)
    {
        this.bloodReasonName = bloodReasonName;
    }

    @Column(name = "ORDER_STATUS_CODE", length = 12)
    public String getOrderStatusCode()
    {
        return this.orderStatusCode;
    }

    public void setOrderStatusCode(String orderStatusCode)
    {
        this.orderStatusCode = orderStatusCode;
    }

    @Column(name = "ORDER_STATUS_NAME", length = 40)
    public String getOrderStatusName()
    {
        return this.orderStatusName;
    }

    public void setOrderStatusName(String orderStatusName)
    {
        this.orderStatusName = orderStatusName;
    }

    @Column(name = "REQUEST_PERSON_ID", length = 20)
    public String getRequestPersonId()
    {
        return this.requestPersonId;
    }

    public void setRequestPersonId(String requestPersonId)
    {
        this.requestPersonId = requestPersonId;
    }

    @Column(name = "URGENCY_CODE", length = 6)
    public String getUrgencyCode()
    {
        return this.urgencyCode;
    }

    public void setUrgencyCode(String urgencyCode)
    {
        this.urgencyCode = urgencyCode;
    }

    @Column(name = "URGENCY_NAME", length = 40)
    public String getUrgencyName()
    {
        return this.urgencyName;
    }

    public void setUrgencyName(String urgencyName)
    {
        this.urgencyName = urgencyName;
    }

    @Column(name = "CONFIRM_PERSON", length = 12)
    public String getConfirmPerson()
    {
        return this.confirmPerson;
    }

    public void setConfirmPerson(String confirmPerson)
    {
        this.confirmPerson = confirmPerson;
    }

    @Column(name = "CONFIRM_PERSON_NAME", length = 192)
    public String getConfirmPersonName()
    {
        return this.confirmPersonName;
    }

    public void setConfirmPersonName(String confirmPersonName)
    {
        this.confirmPersonName = confirmPersonName;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "CONFIRM_TIME", length = 7)
    public Date getConfirmTime()
    {
        return this.confirmTime;
    }

    public void setConfirmTime(Date confirmTime)
    {
        this.confirmTime = confirmTime;
    }

    @Column(name = "QUANTITY_UNIT", length = 20)
    public String getQuantityUnit()
    {
        return this.quantityUnit;
    }

    public void setQuantityUnit(String quantityUnit)
    {
        this.quantityUnit = quantityUnit;
    }

    @Column(name = "PREGNANCY_FLAG", length = 10)
    public String getPregnancyFlag()
    {
        return this.pregnancyFlag;
    }

    public void setPregnancyFlag(String pregnancyFlag)
    {
        this.pregnancyFlag = pregnancyFlag;
    }

    @Column(name = "PREGNANCY_COUNT", length = 10)
    public String getPregnancyCount()
    {
        return this.pregnancyCount;
    }

    public void setPregnancyCount(String pregnancyCount)
    {
        this.pregnancyCount = pregnancyCount;
    }

    @Column(name = "CHILDBIRTH_FLAG", length = 10)
    public String getChildbirthFlag()
    {
        return this.childbirthFlag;
    }

    public void setChildbirthFlag(String childbirthFlag)
    {
        this.childbirthFlag = childbirthFlag;
    }

    @Column(name = "CHILDBIRTH_COUNT", length = 10)
    public String getChildbirthCount()
    {
        return this.childbirthCount;
    }

    public void setChildbirthCount(String childbirthCount)
    {
        this.childbirthCount = childbirthCount;
    }

    @Column(name = "ADVERSE_HISTORY_FLAG", length = 10)
    public String getAdverseHistoryFlag()
    {
        return this.adverseHistoryFlag;
    }

    public void setAdverseHistoryFlag(String adverseHistoryFlag)
    {
        this.adverseHistoryFlag = adverseHistoryFlag;
    }

    @Column(name = "HISTORY_FLAG", length = 10)
    public String getHistoryFlag()
    {
        return this.historyFlag;
    }

    public void setHistoryFlag(String historyFlag)
    {
        this.historyFlag = historyFlag;
    }

    @Column(name = "NEWBORN_FLAG", length = 10)
    public String getNewbornFlag()
    {
        return newbornFlag;
    }

    public void setNewbornFlag(String newbornFlag)
    {
        this.newbornFlag = newbornFlag;
    }

    @Column(name = "OTHER_DISEASE_FLAG", length = 10)
    public String getOtherDiseaseFlag()
    {
        return otherDiseaseFlag;
    }

    public void setOtherDiseaseFlag(String otherDiseaseFlag)
    {
        this.otherDiseaseFlag = otherDiseaseFlag;
    }

    @Column(name = "OTHER_DISEASE_NAME", length = 300)
    public String getOtherDiseaseName()
    {
        return otherDiseaseName;
    }

    public void setOtherDiseaseName(String otherDiseaseName)
    {
        this.otherDiseaseName = otherDiseaseName;
    }

    @Column(name = "SELF_BLOOD_FLAG", length = 10)
    public String getSelfBloodFlag()
    {
        return selfBloodFlag;
    }

    public void setSelfBloodFlag(String selfBloodFlag)
    {
        this.selfBloodFlag = selfBloodFlag;
    }

    @Column(name = "CLINICAL_DIAGNOSIS", length = 150)
    public String getClinicalDiagnosis()
    {
        return clinicalDiagnosis;
    }

    public void setClinicalDiagnosis(String clinicalDiagnosis)
    {
        this.clinicalDiagnosis = clinicalDiagnosis;
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
    
    @Column(name = "ORG_CODE", length = 50)
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	@Column(name = "ORG_NAME", length = 50)
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getCrossMatchBloodFlag() {
		return crossMatchBloodFlag;
	}

	public void setCrossMatchBloodFlag(String crossMatchBloodFlag) {
		this.crossMatchBloodFlag = crossMatchBloodFlag;
	}

	public String getCrossMatchBloodName() {
		return crossMatchBloodName;
	}

	public void setCrossMatchBloodName(String crossMatchBloodName) {
		this.crossMatchBloodName = crossMatchBloodName;
	}

	public String getReserveFlag() {
		return reserveFlag;
	}

	public void setReserveFlag(String reserveFlag) {
		this.reserveFlag = reserveFlag;
	}

	public String getReserveName() {
		return reserveName;
	}

	public void setReserveName(String reserveName) {
		this.reserveName = reserveName;
	}

	public String getStemCellTransplantFlag() {
		return stemCellTransplantFlag;
	}

	public void setStemCellTransplantFlag(String stemCellTransplantFlag) {
		this.stemCellTransplantFlag = stemCellTransplantFlag;
	}

	public String getStemCellTransplantName() {
		return stemCellTransplantName;
	}

	public void setStemCellTransplantName(String stemCellTransplantName) {
		this.stemCellTransplantName = stemCellTransplantName;
	}

}
