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
@Table(name = "BLOOD_DELIVERY_ORDER")
public class BloodDeliveryOrder implements Serializable
{

    private BigDecimal orderSn;

    private BigDecimal requestSn;

    private String orderLid;

    private String patientName;

    private String patientAge;

    private String bedNo;

    private String transfusionComponents;

    private Date deliveryTime;

    private BigDecimal deliveryQuality;

    private String requestDoctorId;

    private String applicationSenderId;

    private String applicationReceiverId;

    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;

    private String deliveryUnit;

    private String requestDoctorName;

    private String appSenderName;

    private String appReceiverName;

    private String patientDomain;

    private String patientGenderCode;

    private String patientGenderName;

    private String orderDeptId;

    private String orderDeptName;

    private String diagnoseCode;

    private String diagnoseName;

    private String transfusionReasonCode;

    private String transfusionReasonName;

    private String wardId;

    private String wardName;

    private BigDecimal bloodDeliveryCount;
    
    private String orgCode;
    
    private String orgName;

    private String createby;

    private String updateby;

    private String deleteby;

    @Id
    @GeneratedValue(generator = "native-generator")
    @Generator(name = "native-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "SEQ_ORDER") })
    public BigDecimal getOrderSn()
    {
        return this.orderSn;
    }

    public void setOrderSn(BigDecimal orderSn)
    {
        this.orderSn = orderSn;
    }

    @Column(name = "REQUEST_SN", nullable = false, precision = 22, scale = 0)
    public BigDecimal getRequestSn()
    {
        return this.requestSn;
    }

    public void setRequestSn(BigDecimal requestSn)
    {
        this.requestSn = requestSn;
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

    @Column(name = "PATIENT_NAME", length = 200)
    public String getPatientName()
    {
        return this.patientName;
    }

    public void setPatientName(String patientName)
    {
        this.patientName = patientName;
    }

    @Column(name = "PATIENT_AGE", length = 15)
    public String getPatientAge()
    {
        return this.patientAge;
    }

    public void setPatientAge(String patientAge)
    {
        this.patientAge = patientAge;
    }

    @Column(name = "BED_NO", length = 20)
    public String getBedNo()
    {
        return this.bedNo;
    }

    public void setBedNo(String bedNo)
    {
        this.bedNo = bedNo;
    }

    @Column(name = "TRANSFUSION_COMPONENTS", length = 192)
    public String getTransfusionComponents()
    {
        return this.transfusionComponents;
    }

    public void setTransfusionComponents(String transfusionComponents)
    {
        this.transfusionComponents = transfusionComponents;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DELIVERY_TIME", length = 7)
    public Date getDeliveryTime()
    {
        return this.deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime)
    {
        this.deliveryTime = deliveryTime;
    }

    @Column(name = "DELIVERY_QUALITY", precision = 22, scale = 0)
    public BigDecimal getDeliveryQuality()
    {
        return this.deliveryQuality;
    }

    public void setDeliveryQuality(BigDecimal deliveryQuality)
    {
        this.deliveryQuality = deliveryQuality;
    }

    @Column(name = "REQUEST_DOCTOR_ID", length = 20)
    public String getRequestDoctorId()
    {
        return this.requestDoctorId;
    }

    public void setRequestDoctorId(String requestDoctorId)
    {
        this.requestDoctorId = requestDoctorId;
    }

    @Column(name = "APPLICATION_SENDER_ID", length = 20)
    public String getApplicationSenderId()
    {
        return this.applicationSenderId;
    }

    public void setApplicationSenderId(String applicationSenderId)
    {
        this.applicationSenderId = applicationSenderId;
    }

    @Column(name = "APPLICATION_RECEIVER_ID", length = 20)
    public String getApplicationReceiverId()
    {
        return this.applicationReceiverId;
    }

    public void setApplicationReceiverId(String applicationReceiverId)
    {
        this.applicationReceiverId = applicationReceiverId;
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

    @Column(name = "DELIVERY_UNIT", length = 20)
    public String getDeliveryUnit()
    {
        return this.deliveryUnit;
    }

    public void setDeliveryUnit(String deliveryUnit)
    {
        this.deliveryUnit = deliveryUnit;
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

    @Column(name = "APP_SENDER_NAME", length = 40)
    public String getAppSenderName()
    {
        return this.appSenderName;
    }

    public void setAppSenderName(String appSenderName)
    {
        this.appSenderName = appSenderName;
    }

    @Column(name = "APP_RECEIVER_NAME", length = 40)
    public String getAppReceiverName()
    {
        return this.appReceiverName;
    }

    public void setAppReceiverName(String appReceiverName)
    {
        this.appReceiverName = appReceiverName;
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

    @Column(name = "PATIENT_GENDER_CODE", length = 12)
    public String getPatientGenderCode()
    {
        return this.patientGenderCode;
    }

    public void setPatientGenderCode(String patientGenderCode)
    {
        this.patientGenderCode = patientGenderCode;
    }

    @Column(name = "PATIENT_GENDER_NAME", length = 192)
    public String getPatientGenderName()
    {
        return this.patientGenderName;
    }

    public void setPatientGenderName(String patientGenderName)
    {
        this.patientGenderName = patientGenderName;
    }

    @Column(name = "ORDER_DEPT_ID", length = 12)
    public String getOrderDeptId()
    {
        return this.orderDeptId;
    }

    public void setOrderDeptId(String orderDeptId)
    {
        this.orderDeptId = orderDeptId;
    }

    @Column(name = "ORDER_DEPT_NAME", length = 192)
    public String getOrderDeptName()
    {
        return this.orderDeptName;
    }

    public void setOrderDeptName(String orderDeptName)
    {
        this.orderDeptName = orderDeptName;
    }

    @Column(name = "DIAGNOSE_CODE", length = 120)
    public String getDiagnoseCode()
    {
        return this.diagnoseCode;
    }

    public void setDiagnoseCode(String diagnoseCode)
    {
        this.diagnoseCode = diagnoseCode;
    }

    @Column(name = "DIAGNOSE_NAME", length = 192)
    public String getDiagnoseName()
    {
        return this.diagnoseName;
    }

    public void setDiagnoseName(String diagnoseName)
    {
        this.diagnoseName = diagnoseName;
    }

    @Column(name = "TRANSFUSION_REASON_CODE", length = 12)
    public String getTransfusionReasonCode()
    {
        return this.transfusionReasonCode;
    }

    public void setTransfusionReasonCode(String transfusionReasonCode)
    {
        this.transfusionReasonCode = transfusionReasonCode;
    }

    @Column(name = "TRANSFUSION_REASON_NAME", length = 192)
    public String getTransfusionReasonName()
    {
        return this.transfusionReasonName;
    }

    public void setTransfusionReasonName(String transfusionReasonName)
    {
        this.transfusionReasonName = transfusionReasonName;
    }

    @Column(name = "WARD_ID", length = 12)
    public String getWardId()
    {
        return this.wardId;
    }

    public void setWardId(String wardId)
    {
        this.wardId = wardId;
    }

    @Column(name = "WARD_NAME", length = 192)
    public String getWardName()
    {
        return this.wardName;
    }

    public void setWardName(String wardName)
    {
        this.wardName = wardName;
    }

    @Column(name = "BLOOD_DELIVERY_COUNT", precision = 22, scale = 0)
    public BigDecimal getBloodDeliveryCount()
    {
        return bloodDeliveryCount;
    }

    public void setBloodDeliveryCount(BigDecimal bloodDeliveryCount)
    {
        this.bloodDeliveryCount = bloodDeliveryCount;
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

}
