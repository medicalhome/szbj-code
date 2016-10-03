package com.yly.cdr.hl7.dto;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.yly.cdr.hl7.dto.Diagnosis;
import com.yly.cdr.hl7.dto.Histories;
import com.yly.cdr.hl7.dto.LabResults;
import com.yly.cdr.hl7.dto.MedicalVisit;
import com.yly.cdr.hl7.dto.SpecialRequest;

public class BloodRequestBatch extends BaseDto
{
    /**
     * 申请单号
     */
    @NotEmpty(message="{BloodRequestBatch.requestNo}")
    private String requestNo;
    /**
     * 医嘱号
     */
    private String orderLid;
    /**
     * 备注
     */
    private String description;
    /**
     * 申请时间
     */
    @NotEmpty(message="{BloodRequestBatch.requestDate}")
    private String requestDate;
    /**
     * 紧急程度代码
     */
    // $Author:tong_meng
    // $Date:2012/12/24 16:00
    // [BUG]0012519 DELETE BEGIN
    // @NotEmpty(message="{BloodRequestBatch.urgency}")
    // [BUG]0012519 DELETE END
    private String urgency;
    /**
     * 紧急程度名称
     */
    // $Author:tong_meng
    // $Date:2012/12/24 16:00
    // [BUG]0012519 DELETE BEGIN
    // @NotEmpty(message="{BloodRequestBatch.urgencyName}")
    // [BUG]0012519 DELETE END
    private String urgencyName;
    /**
     * 申请血量
     */
    @NotEmpty(message="{BloodRequestBatch.amount}")
    private String amount;
    /**
     * 申请血量单位
     */
    @NotEmpty(message="{BloodRequestBatch.unit}")
    private String unit;
    /**
     * 用血时间
     */
    @NotEmpty(message="{BloodRequestBatch.transfusionDate}")
    private String transfusionDate;
    /**
     * 域ID
     */
    @NotEmpty(message="{BloodRequestBatch.patientDomain}")
    private String patientDomain;
    /**
     * 患者本地ID
     */
    @NotEmpty(message="{BloodRequestBatch.patientLid}")
    private String patientLid;
    /**
     * 病房号
     */
    private String wardsNo;
    /**
     * 病房号名
     */
    private String wardsName;
    /**
     * 床号
     */
    private String bedNo;
    /**
     * 联系电话
     */
    private String telephone;
    /**
     * 病人隐私级别代码
     */
    private String privacyCode;
    /**
     * 病人隐私级别名称
     */
    private String privacyName;
    /**
     * 身份证号
     */
    private String passportId;
    /**
     * 献血证号
     */
    private String donateBloodId;
    /**
     * 姓名
     */
    private String patientName;
    /**
     * 性别代码
     */
    private String genderCode;
    /**
     * 出生日期
     */
    private String birthDay;
    /**
     * 年龄
     */
    private String age;
    /**
     * 地址第一部分
     */
    private String firstContactAddr;
    /**
     * 地址第二部分
     */
    private String secondContactAddr;
    /**
     * 邮政编码
     */
    private String addrZipCode;
    /**
     * 婚姻状况类别代码
     */
    private String maritalStatus;
    /**
     * 出生地第一部分
     */
    private String birthPlaceSta;
    /**
     * 出生地第二部分
     */
    private String birthPlaceCty;
    /**
     * 出生地邮政编码
     */
    private String birthZipCode;
    /**
     * 病人费别
     */
    private String patientFee;
    /**
     * 血液品种代码
     */
  //  @NotEmpty(message="{BloodRequestBatch.bloodClass}")
    private String bloodClass;
    /**
     * 血液品种名称
     */
  //  @NotEmpty(message="{BloodRequestBatch.bloodClassName}")
    private String bloodClassName;
    /**
     * 血液特殊要求
     */
    private List<SpecialRequest> specialRequest;
    /**
     * 申请医师ID
     */
    @NotEmpty(message="{BloodRequestBatch.requestPerson}")
    private String requestPerson;
    /**
     * 申请医师姓名 
     */
    @NotEmpty(message="{BloodRequestBatch.requestPersonName}")
    private String requestPersonName;
    /**
     * 申请科室 
     */
    @NotEmpty(message="{BloodRequestBatch.requestDept}")
    private String requestDept;
    /**
     * 申请科室名称
     */
    @NotEmpty(message="{BloodRequestBatch.requestDeptName}")
    private String requestDeptName;
    /**
     * 诊断
     */
    private List<Diagnosis> diagnosis;
    /**
     * 输血目的代码
     */
  //  @NotEmpty(message="{BloodRequestBatch.bloodReasonCode}")
    private String bloodReasonCode;
    /**
     * 输血目的名称
     */
    private String bloodReasonName;
    /**
     * 病史
     */
    private List<Histories> histories;
    /**
     * 检验项目
     */
    private List<LabResults> labResults;
    /**
     * 就诊
     */
    private List<MedicalVisit> medicalVisit;
    /**
     * 确认人ID
     */
    private String confirmPerson;
    /**
     * 确认人姓名
     */
    private String confirmPersonName;
    /**
     * 确认时间
     */
    private String confirmTime;
    /**
     * ABO血型编码
     */
  //  @NotEmpty(message="{BloodRequestBatch.bloodABOCode}")
    private String bloodABOCode;
    /**
     * ABO血型名称
     */
  //  @NotEmpty(message="{BloodRequestBatch.bloodABOName}")
    private String bloodABOName;
    /**
     * Rh血型编码
     */
  //  @NotEmpty(message="{BloodRequestBatch.bloodRhCode}")
    private String bloodRhCode;
    /**
     * Rh血型名称
     */
 //   @NotEmpty(message="{BloodRequestBatch.bloodRhName}")
    private String bloodRhName;
    // $Author :chang_xuewen
    // $Date : 2014/1/22 10:30$
    // [BUG]0042086 MODIFY BEGIN   
    /**
     * 医疗机构编码
     */
    /*@NotEmpty(message="{message.organizationCode}")*/
    private String organizationCode;

	/**
     * 医疗机构名称
     */
    /*@NotEmpty(message="{message.organizationName}")*/
    private String organizationName;

    public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
    // [BUG]0042086 MODIFY END   
    public String getRequestNo()
    {
        return requestNo;
    }

    public String getOrderLid()
    {
        return orderLid;
    }

    public String getDescription()
    {
        return description;
    }

    public String getRequestDate()
    {
        return requestDate;
    }

    public String getUrgency()
    {
        return urgency;
    }

    public String getUrgencyName()
    {
        return urgencyName;
    }

    public String getAmount()
    {
        return amount;
    }

    public String getUnit()
    {
        return unit;
    }

    public String getTransfusionDate()
    {
        return transfusionDate;
    }

    public String getPatientDomain()
    {
        return patientDomain;
    }

    public String getPatientLid()
    {
        return patientLid;
    }

    public String getWardsNo()
    {
        return wardsNo;
    }

    public String getWardsName()
    {
        return wardsName;
    }

    public String getBedNo()
    {
        return bedNo;
    }

    public String getTelephone()
    {
        return telephone;
    }

    public String getPrivacyCode()
    {
        return privacyCode;
    }

    public String getPrivacyName()
    {
        return privacyName;
    }

    public String getPassportId()
    {
        return passportId;
    }

    public String getDonateBloodId()
    {
        return donateBloodId;
    }

    public String getPatientName()
    {
        return patientName;
    }

    public String getGenderCode()
    {
        return genderCode;
    }

    public String getBirthDay()
    {
        return birthDay;
    }

    public String getAge()
    {
        return age;
    }

    public String getFirstContactAddr()
    {
        return firstContactAddr;
    }

    public String getSecondContactAddr()
    {
        return secondContactAddr;
    }

    public String getAddrZipCode()
    {
        return addrZipCode;
    }

    public String getMaritalStatus()
    {
        return maritalStatus;
    }

    public String getBirthPlaceSta()
    {
        return birthPlaceSta;
    }

    public String getBirthPlaceCty()
    {
        return birthPlaceCty;
    }

    public String getBirthZipCode()
    {
        return birthZipCode;
    }

    public String getPatientFee()
    {
        return patientFee;
    }

    public String getBloodClass()
    {
        return bloodClass;
    }

    public List<SpecialRequest> getSpecialRequest()
    {
        return specialRequest;
    }

    public String getRequestPerson()
    {
        return requestPerson;
    }

    public String getRequestPersonName()
    {
        return requestPersonName;
    }

    public String getRequestDept()
    {
        return requestDept;
    }

    public String getRequestDeptName()
    {
        return requestDeptName;
    }

    public List<Diagnosis> getDiagnosis()
    {
        return diagnosis;
    }

    public List<Histories> getHistories()
    {
        return histories;
    }

    public List<LabResults> getLabResults()
    {
        return labResults;
    }

    public List<MedicalVisit> getMedicalVisit()
    {
        return medicalVisit;
    }

    public String getConfirmPerson()
    {
        return confirmPerson;
    }

    public String getConfirmPersonName()
    {
        return confirmPersonName;
    }

    public String getConfirmTime()
    {
        return confirmTime;
    }

    public String getBloodABOCode()
    {
        return bloodABOCode;
    }

    public String getBloodABOName()
    {
        return bloodABOName;
    }

    public String getBloodRhCode()
    {
        return bloodRhCode;
    }

    public String getBloodRhName()
    {
        return bloodRhName;
    }

    public String getBloodClassName()
    {
        return bloodClassName;
    }

    public String getBloodReasonCode()
    {
        return bloodReasonCode;
    }

    public String getBloodReasonName()
    {
        return bloodReasonName;
    }
    
    public String getOrganizationCode() {
		return organizationCode;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	@Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("BloodRequestBatch [requestNo=");
        builder.append(requestNo);
        builder.append(", organizationCode=");
        builder.append(organizationCode);
        builder.append(", orderLid=");
        builder.append(orderLid);
        builder.append(", patientDomain=");
        builder.append(patientDomain);
        builder.append(", patientLid=");
        builder.append(patientLid);
        builder.append(", specialRequest=");
        builder.append(specialRequest);
        builder.append(", diagnosis=");
        builder.append(diagnosis);
        builder.append(", histories=");
        builder.append(histories);
        builder.append(", labResults=");
        builder.append(labResults);
        builder.append(", medicalVisit=");
        builder.append(medicalVisit);
        builder.append("]");
        return builder.toString();
    }

}
