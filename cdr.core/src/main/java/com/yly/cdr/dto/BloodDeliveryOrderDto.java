package com.yly.cdr.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * [FUN]V05-101
 * @version 1.0, 2012/06/18  
 * @author 张彬
 * @since 1.0
 * 
 */
public class BloodDeliveryOrderDto
{
    private BigDecimal orderSn;

    private BigDecimal requestSn;

    private String patientDomain;

    private String orderLid;

    private String patientName;

    private String patientGenderName;

    private String patientAge;

    private String orderDeptName;

    private String bedNo;

    private String diagnoseName;

    private String transfusionReasonName;

    private String transfusionComponents;

    private Date deliveryTime;

    private BigDecimal deliveryQuality;

    private String deliveryUnit;

    private String requestDoctorName;

    private String appSenderName;

    private String appReceiverName;

    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;

    private String requestNo;

    private String wardName;

    // $Author :tong_meng
    // $Date : 2012/10/13 10:33$
    // [BUG]0010391 ADD BEGIN
    private BigDecimal bloodDeliveryCount;

    // [BUG]0010391 ADD END

    // $Author:wei_peng
    // $Date:2012/10/25 15:09
    // [BUG]0010732 ADD BEGIN
    private String bloodClassName;

    private String bloodReasonName;

    private String quantity;

    private String quantityUnit;

    // [BUG]0010732 ADD END
    
    private String clinicalDiagnosis;

    public BigDecimal getOrderSn()
    {
        return orderSn;
    }

    public void setOrderSn(BigDecimal orderSn)
    {
        this.orderSn = orderSn;
    }

    public BigDecimal getRequestSn()
    {
        return requestSn;
    }

    public void setRequestSn(BigDecimal requestSn)
    {
        this.requestSn = requestSn;
    }

    public String getPatientDomain()
    {
        return patientDomain;
    }

    public void setPatientDomain(String patientDomain)
    {
        this.patientDomain = patientDomain;
    }

    public String getOrderLid()
    {
        return orderLid;
    }

    public void setOrderLid(String orderLid)
    {
        this.orderLid = orderLid;
    }

    public String getPatientName()
    {
        return patientName;
    }

    public void setPatientName(String patientName)
    {
        this.patientName = patientName;
    }

    public String getPatientAge()
    {
        return patientAge;
    }

    public void setPatientAge(String patientAge)
    {
        this.patientAge = patientAge;
    }

    public String getBedNo()
    {
        return bedNo;
    }

    public void setBedNo(String bedNo)
    {
        this.bedNo = bedNo;
    }

    public String getTransfusionReasonName()
    {
        return transfusionReasonName;
    }

    public void setTransfusionReasonName(String transfusionReasonName)
    {
        this.transfusionReasonName = transfusionReasonName;
    }

    public String getTransfusionComponents()
    {
        return transfusionComponents;
    }

    public void setTransfusionComponents(String transfusionComponents)
    {
        this.transfusionComponents = transfusionComponents;
    }

    public Date getDeliveryTime()
    {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime)
    {
        this.deliveryTime = deliveryTime;
    }

    public BigDecimal getDeliveryQuality()
    {
        return deliveryQuality;
    }

    public void setDeliveryQuality(BigDecimal deliveryQuality)
    {
        this.deliveryQuality = deliveryQuality;
    }

    public String getDeliveryUnit()
    {
        return deliveryUnit;
    }

    public void setDeliveryUnit(String deliveryUnit)
    {
        this.deliveryUnit = deliveryUnit;
    }

    public String getRequestDoctorName()
    {
        return requestDoctorName;
    }

    public void setRequestDoctorName(String requestDoctorName)
    {
        this.requestDoctorName = requestDoctorName;
    }

    public String getAppSenderName()
    {
        return appSenderName;
    }

    public void setAppSenderName(String appSenderName)
    {
        this.appSenderName = appSenderName;
    }

    public String getAppReceiverName()
    {
        return appReceiverName;
    }

    public void setAppReceiverName(String appReceiverName)
    {
        this.appReceiverName = appReceiverName;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public Date getUpdateTime()
    {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }

    public BigDecimal getUpdateCount()
    {
        return updateCount;
    }

    public void setUpdateCount(BigDecimal updateCount)
    {
        this.updateCount = updateCount;
    }

    public String getDeleteFlag()
    {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag)
    {
        this.deleteFlag = deleteFlag;
    }

    public Date getDeleteTime()
    {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime)
    {
        this.deleteTime = deleteTime;
    }

    public String getRequestNo()
    {
        return requestNo;
    }

    public void setRequestNo(String requestNo)
    {
        this.requestNo = requestNo;
    }

    public String getOrderDeptName()
    {
        return orderDeptName;
    }

    public void setOrderDeptName(String orderDeptName)
    {
        this.orderDeptName = orderDeptName;
    }

    public String getPatientGenderName()
    {
        return patientGenderName;
    }

    public void setPatientGenderName(String patientGenderName)
    {
        this.patientGenderName = patientGenderName;
    }

    public String getDiagnoseName()
    {
        return diagnoseName;
    }

    public void setDiagnoseName(String diagnoseName)
    {
        this.diagnoseName = diagnoseName;
    }

    public String getWardName()
    {
        return wardName;
    }

    public void setWardName(String wardName)
    {
        this.wardName = wardName;
    }

    public BigDecimal getBloodDeliveryCount()
    {
        return bloodDeliveryCount;
    }

    public void setBloodDeliveryCount(BigDecimal bloodDeliveryCount)
    {
        this.bloodDeliveryCount = bloodDeliveryCount;
    }

    public String getBloodClassName()
    {
        return bloodClassName;
    }

    public void setBloodClassName(String bloodClassName)
    {
        this.bloodClassName = bloodClassName;
    }

    public String getBloodReasonName()
    {
        return bloodReasonName;
    }

    public void setBloodReasonName(String bloodReasonName)
    {
        this.bloodReasonName = bloodReasonName;
    }

    public String getQuantity()
    {
        return quantity;
    }

    public void setQuantity(String quantity)
    {
        this.quantity = quantity;
    }

    public String getQuantityUnit()
    {
        return quantityUnit;
    }

    public void setQuantityUnit(String quantityUnit)
    {
        this.quantityUnit = quantityUnit;
    }

    public String getClinicalDiagnosis()
    {
        return clinicalDiagnosis;
    }

    public void setClinicalDiagnosis(String clinicalDiagnosis)
    {
        this.clinicalDiagnosis = clinicalDiagnosis;
    }

}
