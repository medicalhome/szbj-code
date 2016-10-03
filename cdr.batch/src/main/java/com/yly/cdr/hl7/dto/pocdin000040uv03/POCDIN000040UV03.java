package com.yly.cdr.hl7.dto.pocdin000040uv03;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.yly.cdr.hl7.dto.BaseDto;
import com.yly.cdr.hl7.dto.Diagnosis;
import com.yly.cdr.hl7.dto.InfectiousMessageContent;
import com.yly.cdr.util.DateUtils;

public class POCDIN000040UV03 extends BaseDto
{
    /**
     * 文档适用范围编码
     */
    private String realmCode;

    /**
     * 文档信息模型类别-标识符
     */
    private String typeId;

    /**
     * 病历号
     */
    @NotEmpty(message = "{POCDIN000040UV03.documentLid}")
    private String documentLid;

    /**
     * 文档类别
     */
    private String documentType;

    /**
     * 文档类别名称
     */
    private String documentTypeName;

    /**
     * 文档名称
     */
    //@NotEmpty(message = "{POCDIN000040UV03.documentName}")
    private String documentName;

    /**
     * 文档生效日期
     */
    private String effectiveTime;

    /**
     * 文档密级编码
     */
    private String confidentialityCode;

    /**
     * 文档语言编码
     */
    private String languageCode;

    /**
     * 文档版本
     */
    @NotEmpty(message = "{POCDIN000040UV03.versionNumber}")
    private String versionNumber;

    /**
     * 域ID
     */
    private String patientDomain;

    /**
     * 患者本地ID
     */
    //@NotEmpty(message = "{POCDIN000040UV03.patientLid}")
    private String patientLid;

    /**
     * 就诊号
     */
    private String outPatientNo;

    /**
     * 文档作者-填写时间
     */
    private String writeTime;

    /**
     * 文档作者-医生编码
     */
    private String documentAuthor;

    /**
     * 文档作者-医生姓名(上报医生姓名)
     */
    private String documentAuthorName;

	/**
     * 电子签章时间
     */
    private String signTime;

    /**
     * 电子签章ID
     */
    private String signatureId;

    /**
     * 就诊次数
     */
    private String visitTimes;

    /**
     * 接口服务ID
     */
    private String serviceId;

    /**
     * 文档修改者ID
     */
    private String documentModifier;

    /**
     * 文档修改者名称
     */
    private String documentModifierName;

    /**
     * 文档修改时间
     */
    private String modifyTime;

    // $Author :tong_meng
    // $Date : 2013/10/10 11:00$
    // [BUG]0037754 ADD BEGIN
    /**
     * 关联医嘱号（仅限于EP手术操作记录报告）
     */
    private List<String> orderLids;
    // [BUG]0037754 ADD END
    
    /**
     * CDA图片二进制信息（含有图片的CDA）
     */
    private String imageText;

    /**
     * CDA图片提示信息（含有图片的CDA）
     */
    private String prompt;

    /**
     * CDA图片后缀名（含有图片的CDA）
     */
    private String imageExtension;

    /**
     * 文档审核者集合
     */
    private List<ReviewPersonDto> reviewPersons;
    
    /**
     * 医疗机构编码
     */
    //@NotEmpty(message = "{message.organizationCode}")
    private String organizationCode;
    /**
     * 医疗机构名称
     */
    //@NotEmpty(message = "{message.organizationName}")
    private String organizationName;
    
    /**
     * 就诊科室
     */
    private String visitDept;
    
    /**
     * 就诊科室编码BS335
     * */
    private String visitDeptBS335;
    
    /**
     * 病区id
     */
    private String wardCode;
    
    /**
     * 病区ID BS335
     * */
    private String wardCodeBS335;
    
	/**
     * 病区
     */
    private String wardNo;
    
    /**
     * 床号
     */
    private String bedNo;
    /**
     * BS335病区
     * */
    private String wardNoBS335;
    /**
     * BS335床号
     * */
    private String bedNoBS335;
    
    
    /**
     * 性别编码
     */
    private String genderCode;
    
	/**
     * 性别
     */
    private String genderName;
    
    /**
     * 年龄
     */
    private String age;
    
    /**
     * BS335年龄
     * */
    private String ageBS335;
    
    /**
     * 出生日期
     */
    private String birthDay;
    
    /**
     * 文档作者科室(上报医生科室)
     */
    
    private String patientName;
    
    private String visitDeptName;
    
	private String ChiefPsychiatrist;
    
    private List<InfectiousMessageContent> inContentList;
    
    /**
     * 科室名称
     * */
    private String visitDeptNameBS335;
    
    /**
     * 病历分类名称
     */
    private String medicalTypeName;    
    
    /**
     * 审核标记
     */
    private String legalStatus;
    
    /**
     * 申请单类型编码
     */
    private String requestCode;
    
    /**
     * 会诊申请单编号
     */
    private String requestNum;
    
    /**
     * 就诊流水号
     */
//    @NotEmpty(message = "{POCDIN000040UV03.visitOrdNo}")
    private String visitOrdNo;
    
    /**
     * 就诊类型
     */
//    @NotEmpty(message = "{POCDIN000040UV03.visitTypeCode}")
    private String visitTypeCode;
    /**
     * 入院记录类型编码
     */
    private String inRecordType;
    /**
     * 出院诊断
     */
    private List<Diagnosis> diagnosis;
    
    public List<Diagnosis> getDiagnosis() {
		return diagnosis;
	}

    /**
     * 入院记录诊断
     */
    private List<Diagnosis> inDiagnosis;
    
    public List<Diagnosis> getInDiagnosis() {
		return inDiagnosis;
	}
    
	public String getRequestCode() {
		return requestCode;
	}

	public String getRequestNum() {
		return requestNum;
	}

	public String getLegalStatus() {
		return legalStatus;
	}

	public String getWardCode() {
		return wardCode;
	}
    
	public String getWardCodeBS335(){
		return wardCodeBS335;
	}
	
    public String getMedicalTypeName() {
		return medicalTypeName;
	}

	public String getGenderCode() {
		return genderCode;
	}

    public String getGenderName() {
		return genderName;
	}

	public String getAge() {
		return age;
	}

	public String getAgeBS335(){
		return ageBS335;
	}
	
	public String getBirthDay() {
		return birthDay;
	}

    
    public String getBedNo() {
		return bedNo;
	}

	public String getWardNo() {
		return wardNo;
	}

	public String getWardNoBS335(){
		return wardNoBS335;
	}
	
	public String getBedNoBS335(){
		return bedNoBS335;
	}
	
	public String getVisitDeptName() {
		return visitDeptName;
	}

	public String getVisitDeptNameBS335(){
		return visitDeptNameBS335;
	}
	
    public List<InfectiousMessageContent> getInContentList() {
		return inContentList;
	}

    public String getPatientName() {
		return patientName;
	}

	public String getChiefPsychiatrist() {
		return ChiefPsychiatrist;
	}

	public String getVisitDept() {
		return visitDept;
	}

	public String getVisitDeptBS335(){
		return visitDeptBS335;
	}

	public List<ReviewPersonDto> getReviewPersons()
    {
        return reviewPersons;
    }

    public String getDocumentModifier()
    {
        return documentModifier;
    }

    public String getDocumentModifierName()
    {
        return documentModifierName;
    }

    public String getModifyTime()
    {
        return modifyTime;
    }

    public List<String> getOrderLids()
    {
        return orderLids;
    }

    public String getRealmCode()
    {
        return realmCode;
    }

    public String getTypeId()
    {
        return typeId;
    }

    public String getDocumentLid()
    {
        return documentLid;
    }

    public String getDocumentType()
    {
        return documentType;
    }

    public String getDocumentTypeName()
    {
        return documentTypeName;
    }

    public String getDocumentName()
    {
        return documentName;
    }

    public String getEffectiveTime()
    {
        return effectiveTime;
    }

    public String getConfidentialityCode()
    {
        return confidentialityCode;
    }

    public String getLanguageCode()
    {
        return languageCode;
    }

    public String getVersionNumber()
    {
        return versionNumber;
    }

    public String getPatientDomain()
    {
        return patientDomain;
    }

    public String getPatientLid()
    {
        return patientLid;
    }

    public String getOutPatientNo()
    {
        return outPatientNo;
    }

    public String getWriteTime()
    {
        return writeTime;
    }

    public String getDocumentAuthor()
    {
        return documentAuthor;
    }

    public String getDocumentAuthorName()
    {
        return documentAuthorName;
    }

    public String getSignTime()
    {
        return signTime;
    }

    public String getSignatureId()
    {
        return signatureId;
    }

    public String getVisitTimes()
    {
        return visitTimes;
    }

    public String getServiceId()
    {
        return serviceId;
    }

    public String getImageText()
    {
        return imageText;
    }

    public String getPrompt()
    {
        return prompt;
    }

    public String getImageExtension()
    {
        return imageExtension;
    }
    
    public String getOrganizationCode() {
        return organizationCode;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationCode(String organizationCode)
    {
        this.organizationCode = organizationCode;
    }

    public void setOrganizationName(String organizationName)
    {
        this.organizationName = organizationName;
    }

    public String getVisitOrdNo() {
		return visitOrdNo;
	}

	public String getVisitTypeCode() {
		return visitTypeCode;
	}

	public String getInRecordType() {
		return inRecordType;
	}

	public void setInRecordType(String inRecordType) {
		this.inRecordType = inRecordType;
	}

	// $Author :chang_xuewen
    // $Date : 2013/06/05 16:00$
    // [BUG]0033373 MODIFY BEGIN
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("MessageId=");
		str.append(getMessage().getId()+",");
		str.append("Datetime=");
		str.append(DateUtils.dateToString(getMessage().getDatetime(),
                null) + ",");
		str.append("POCDIN000040UV03 [documentLid=");
		str.append(documentLid);
		str.append(", organizationCode=");
        str.append(organizationCode);
		str.append(", patientDomain=");
		str.append(patientDomain);
		str.append(", patientLid=");
		str.append(patientLid);
		str.append(", inRecordType=");
		str.append(inRecordType);
		str.append(", outPatientNo=");
		str.append(outPatientNo);
		str.append(", visitOrdNo=");
		str.append(visitOrdNo);
		str.append(", visitTypeCode=");
		str.append(visitTypeCode);
		str.append(", visitTimes=");
		str.append(visitTimes+"]");
		return str.toString();
	}
	// [BUG]0033373 MODIFY END

}
