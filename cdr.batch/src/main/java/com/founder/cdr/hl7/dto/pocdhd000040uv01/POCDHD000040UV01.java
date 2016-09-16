package com.founder.cdr.hl7.dto.pocdhd000040uv01;

import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.founder.cdr.hl7.dto.AnesthesiaDoctor;
import com.founder.cdr.hl7.dto.ApparatusDoctor;
import com.founder.cdr.hl7.dto.BaseDto;
import com.founder.cdr.hl7.dto.ContinuousMedication;
import com.founder.cdr.hl7.dto.Diagnosis;
import com.founder.cdr.hl7.dto.InOutDoseMessage;
import com.founder.cdr.hl7.dto.InfectiousMessageContent;
import com.founder.cdr.hl7.dto.InterruptMedication;
import com.founder.cdr.hl7.dto.OperateDoctor;
import com.founder.cdr.hl7.dto.PhysicalExaminationItem;
import com.founder.cdr.hl7.dto.TourDoctor;
import com.founder.cdr.hl7.dto.TransfuseMessage;
import com.founder.cdr.util.DateUtils;

public class POCDHD000040UV01 extends BaseDto
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
    @NotEmpty(message = "{POCDHD000040UV01.documentLid}")
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
    //@NotEmpty(message = "{POCDHD000040UV01.documentName}")
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
    @NotEmpty(message = "{POCDHD000040UV01.versionNumber}")
    private String versionNumber;

    /**
     * 域ID
     */
    private String patientDomain;

    /**
     * 患者本地ID
     */
    //@NotEmpty(message = "{POCDHD000040UV01.patientLid}")
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
     * 病区id
     */
    private String wardCode;
    
	/**
     * 病区
     */
    private String wardNo;
    
    /**
     * 床号
     */
    private String bedNo;
    
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
//    @NotEmpty(message = "{POCDHD000040UV01.visitOrdNo}")
    private String visitOrdNo;
    
    /**
     * 就诊类型
     */
//    @NotEmpty(message = "{POCDHD000040UV01.visitTypeCode}")
    private String visitTypeCode;
    
    /**
     * 出院诊断
     */
    private List<Diagnosis> diagnosis;
    
    
    
    
    
    //begin at 2015/04/01 16:11
    /**
     * 麻醉中西医标识
     */
    private String chineseAnesthesia;
    /**
     * ASA麻醉评分编码
     */
    private String anesthesiaGradeCode;
    
    /**
     * ASA麻醉评分名称
     */
    private String anesthesiaGradeName;
    /**
     * 实施手术编码
     */
	private String operateCode;
    /**
     * 实施手术名称
     */
	private String operateName;
    /**
     * 术毕情况及去向
     */
	private String operateOverGone;
    /**
     * 术后镇痛
     */
	private String operateAnalgesia;  
    /**
     * 麻醉方式代码
     */	
	private String anesthesiaMethod;  
    /**
     * 麻醉方式名称
     */	
	private String anesthesiaMethodName;  
    /**
     * 麻醉科室代码
     */	    
	private String anesthesiaDept;  
    /**
     * 麻醉科室名称
     */	    
	private String anesthesiaDeptName;  
    /**
     * 麻醉准备时间
     */
	private String prepareTime;
    /**
     * 麻醉开始时间
     */
	private String startTime;
    /**
     * 麻醉结束时间
     */
	private String endTime;   
    /**
     * 麻醉工作量
     */
	private String workload;    
    /**
     * 麻醉医生
     */
	private List<AnesthesiaDoctor> anesthesiaDoctorList;
    /**
     * 手术医生
     */
	private List<OperateDoctor> operateDoctorList;
    /**
     * 手术开始时间
     */
	private String operateStartTime;
    /**
     * 手术结束时间
     */
	private String operateEndTime;
    /**
     * 巡回护士
     */
	private List<TourDoctor> tourDoctorList;
    /**
     * 器械护士
     */
	private List<ApparatusDoctor> apparatusDoctorList;
    /**
     * 体格检查项目
     */
	private List<PhysicalExaminationItem> physicalExaminationItemList;
    /**
     * 连续用药信息
     */
	private List<ContinuousMedication> continuousMedicationList;
 
    /**
     * 输液信息
     */
	private List<TransfuseMessage> transfuseList;
    /**
     * 间断用药信息
     */
	private List<InterruptMedication> interruptMedicationList;
    /**
     * 入出量信息
     */
	private List<InOutDoseMessage> inOutDoseMessageList;
    
    
    public List<AnesthesiaDoctor> getAnesthesiaDoctorList() {
		return anesthesiaDoctorList;
	}

	public List<OperateDoctor> getOperateDoctorList() {
		return operateDoctorList;
	}

	public String getOperateStartTime() {
		return operateStartTime;
	}

	public String getOperateEndTime() {
		return operateEndTime;
	}

	public List<TourDoctor> getTourDoctorList() {
		return tourDoctorList;
	}

	public List<ApparatusDoctor> getApparatusDoctorList() {
		return apparatusDoctorList;
	}

	public List<PhysicalExaminationItem> getPhysicalExaminationItemList() {
		return physicalExaminationItemList;
	}

	public List<ContinuousMedication> getContinuousMedicationList() {
		return continuousMedicationList;
	}

	public List<TransfuseMessage> getTransfuseList() {
		return transfuseList;
	}

	public List<InterruptMedication> getInterruptMedicationList() {
		return interruptMedicationList;
	}

	public List<InOutDoseMessage> getInOutDoseMessageList() {
		return inOutDoseMessageList;
	}

	public String getChineseAnesthesia() {
		return chineseAnesthesia;
	}

	public String getAnesthesiaGradeCode() {
		return anesthesiaGradeCode;
	}

	public String getAnesthesiaGradeName() {
		return anesthesiaGradeName;
	}

	public String getOperateCode() {
		return operateCode;
	}

	public String getOperateName() {
		return operateName;
	}

	public String getOperateOverGone() {
		return operateOverGone;
	}

	public String getOperateAnalgesia() {
		return operateAnalgesia;
	}

	public String getAnesthesiaMethod() {
		return anesthesiaMethod;
	}

	public String getAnesthesiaMethodName() {
		return anesthesiaMethodName;
	}

	public String getAnesthesiaDept() {
		return anesthesiaDept;
	}

	public String getAnesthesiaDeptName() {
		return anesthesiaDeptName;
	}

	public String getPrepareTime() {
		return prepareTime;
	}

	public String getStartTime() {
		return startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public String getWorkload() {
		return workload;
	}

	public List<Diagnosis> getDiagnosis() {
		return diagnosis;
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

	public String getBirthDay() {
		return birthDay;
	}

    
    public String getBedNo() {
		return bedNo;
	}

	public String getWardNo() {
		return wardNo;
	}

	public String getVisitDeptName() {
		return visitDeptName;
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
    //

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

    public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
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
		str.append("POCDHD000040UV01 [documentLid=");
		str.append(documentLid);
		str.append(", organizationCode=");
        str.append(organizationCode);
		str.append(", orderlid=");
		str.append(orderLids.get(0));
		str.append(", patientDomain=");
		str.append(patientDomain);
		str.append(", patientLid=");
		str.append(patientLid);
		str.append(", visitOrdNo=");
		str.append(visitOrdNo);
		str.append(", visitTypeCode=");
		str.append(visitTypeCode+"]");
		return str.toString();
	}
	// [BUG]0033373 MODIFY END

}
