package com.yly.cdr.hl7.dto.pocdin000040uv04;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.yly.cdr.hl7.dto.BaseDto;
import com.yly.cdr.hl7.dto.Diagnosis;
import com.yly.cdr.hl7.dto.MorphologyReportItem;
import com.yly.cdr.hl7.dto.MorphologySample;
import com.yly.cdr.hl7.dto.Reporter;
import com.yly.cdr.util.DateUtils;

public class POCDIN000040UV04 extends BaseDto
{
    /**
     * 系统源标识
     */
    private String sourceSystemId;

    /**
     * 报告号
     */
    @NotEmpty(message = "{POCDIN000040UV04.reportNo}")
    private String reportNo;

    /**
     * 文档类别编码
     */
    private String documentType;

    /**
     * 文档类别名称
     */
    private String documentTypeName;

    /**
     * 文档标题
     */
    private String title;

    /**
     * 文档生效日期
     */
    private String documentEffectDate;

    /**
     * 操作版本
     */
    @NotEmpty(message = "{POCDIN000040UV04.versionNumber}")
    private String versionNumber;

    /**
     * 域ID
     */
    @NotEmpty(message = "{POCDIN000040UV04.patientDomain}")
    private String patientDomain;

    /**
     * 患者本地LID
     */
    @NotEmpty(message = "{POCDIN000040UV04.patientLid}")
    private String patientLid;

    /**
     * 就诊号
     */
    private String outpatientNo;

    /**
     * 病区编码
     */
    private String wardsNo;
    /**
     * 病区名称
     */
    private String wardsName;
    /**
     * 床位号
     */
    private String bedNo;

    /**
     * 联系电话
     */
    private String telephone;

    /**
     * 病人姓名
     */
    @NotEmpty(message = "{POCDIN000040UV04.patientName}")
    private String patientName;

    /**
     * 性别编码
     */
    @NotEmpty(message = "{POCDIN000040UV04.genderCode}")
    private String genderCode;

    /**
     * 性别名称
     */
    @NotEmpty(message = "{POCDIN000040UV04.genderName}")
    private String genderName;

    /**
     * 出生日期
     */
    @NotEmpty(message = "{POCDIN000040UV04.birthDate}")
    private String birthDate;

    /**
     * 院区编码
     */
    private String hospitalWardsId;

    /**
     * 院区名称
     */
    private String hospitalWardsName;

    /**
     * 报告人
     */
    @NotEmpty(message = "{POCDIN000040UV04.reporter}")
    private List<Reporter> reporter;

    /**
     * 电子签章时间
     */
    private String signTime;

    /**
     * 电子签章号
     */
    private String signatureId;

    /**
     * 审核日期
     */
    //@NotEmpty(message = "{POCDIN000040UV04.reviewTime}")
    private String reviewTime;

    /**
     * 审核者编码
     */
    //@NotEmpty(message = "{POCDIN000040UV04.reviewPerson}")
    private String reviewPerson;

    /**
     * 审核者姓名
     */
    //@NotEmpty(message = "{POCDIN000040UV04.reviewPersonName}")
    private String reviewPersonName;

    /**
     * 送检时间
     */
    private String submittingDate;

    /**
     * 送检医生编码
     */
    private String submittingPersonId;

    /**
     * 送检医生姓名
     */
    private String submittingPersonName;

    // $Author:tong_meng
    // $Date:2013/12/03 11:00
    // [BUG]0040270 ADD BEGIN
    /**
     * 医疗机构代码
     */
    //@NotEmpty(message = "{POCDIN000040UV04.orgCode}")
    private String orgCode;
    
    /**
     * 医疗机构名称
     */
    //@NotEmpty(message = "{POCDIN000040UV04.orgName}")
    private String orgName;
    // [BUG]0040270 ADD END

    /**
     * 送检科室编码
     */
    private String sendDeptId;

    /**
     * 送检科室名称
     */
    private String sendDeptName;

    /**
     * 检验医生编码
     */
    private String testerId;

    /**
     * 检验医生姓名
     */
    private String testerName;

    /**
     * 检验科室编码
     */
    @NotEmpty(message = "{POCDIN000040UV04.labDept}")
    private String labDept;

    /**
     * 检验科室名称
     */
    @NotEmpty(message = "{POCDIN000040UV04.labDeptName}")
    private String labDeptName;

    /**
     * 申请时间
     */
    @NotEmpty(message = "{POCDIN000040UV04.requestTime}")
    private String requestTime;

    /**
     * 申请科室编码
     */
    @NotEmpty(message = "{POCDIN000040UV04.requestDeptCode}")
    private String requestDeptCode;

    /**
     * 申请科室名称
     */
    @NotEmpty(message = "{POCDIN000040UV04.requestDeptName}")
    private String requestDeptName;

    /**
     * 医嘱号
     */
    private List<String> orderLid;

    /**
     * 就诊次数
     */
    //@NotEmpty(message = "{POCDIN000040UV04.visitTimes}")
    private String visitTimes;

    /**
     * 就诊类别编码
     */
    private String visitType;

    /**
     * 就诊流水号
     */
    @NotEmpty(message = "{POCDIN000040UV04.visitOrdNo}")
    private String visitOrdNo;
    
    /**
     * 就诊科室
     */
    private String visitDept;

    /**
     * 就诊科室名称
     */
    private String visitDeptName;

    /**
     * 报告备注
     */
    private String reportMemo;

    /**
     * 技术备注
     */
    private String technicalNote;

    /**
     * 报告号
     */
    private String mainInformationNo;

    /**
     * 实验室编号
     */
    private String laboratoryNo;

    /**
     * 优先级
     */
    private String priority;

    /**
     * 提供者名称
     */
    private String supplierName;

    /**
     * 性别编码
     */
    private String supplierGender;

    /**
     * 性别名称
     */
    private String supplierGenderName;

    /**
     * 提供者年龄
     */
    private String supplierAge;

    /**
     * 月经时间(末次月经)
     */
    private String lastMenstrualPeriod;

    /**
     * 绝经
     */
    private String menopause;

    /**
     * 预产年龄
     */
    private String expectedAge;

    /**
     * 胎儿数
     */
    private String fetalNumber;

    /**
     * 孕周计算基于
     */
    private String gestationalCalculationBased;
    /**
     * 细胞分裂状态编码
     */
    private String cellDevisionStatus;
    /**
     * 细胞分裂状态名称
     */    
    private String cellDevisionName;
    /**
     * 细胞分裂值
     */
    private String cellDevisionValue;
    /**
     * 基因编码
     */
    private String geneCode;
    /**
     * 基因名称
     */
    private String geneName;
    /**
     * 基因值
     */
    private String geneValue;
    /**
     * 图片二进制信息
     */
    @NotEmpty(message = "{POCDIN000040UV04.imageText}")
    private String imageText;

    /**
     * 图片后缀名
     */
    @NotEmpty(message = "{POCDIN000040UV04.imageExtension}")
    private String imageExtension;

    /**
     * 提示信息
     */
    private String prompt;

    /**
     * 检验报告条目
     */
    private List<MorphologyReportItem> morphologyReportItem;

    /**
     * 标本及其图像信息
     */
    @NotEmpty(message = "{POCDIN000040UV04.morphologySample}")
    private List<MorphologySample> morphologySample;

    /**
     * 诊断信息
     */
    private List<Diagnosis> diagnosis;

    /**
     * 药观编码
     */
    private String medicalObservedCode;

    /**
     * 药观名称
     */
    private String medicalObservedName;
    
    /**
     * 形态学报告类型编码
     */
    @NotEmpty(message = "{POCDIN000040UV04.morphologyReportTypeCode}")
    private String morphologyReportTypeCode;
    
    /**
     * 形态学报告类型名称
     */
    @NotEmpty(message = "{POCDIN000040UV04.morphologyReportTypeName}")
    private String morphologyReportTypeName;

    public String getVisitOrdNo() {
		return visitOrdNo;
	}

	public void setVisitOrdNo(String visitOrdNo) {
		this.visitOrdNo = visitOrdNo;
	}

	public String getMorphologyReportTypeCode() {
		return morphologyReportTypeCode;
	}

	public String getMorphologyReportTypeName() {
		return morphologyReportTypeName;
	}

	public String getSourceSystemId()
    {
        return sourceSystemId;
    }

    public String getReportNo()
    {
        return reportNo;
    }

    public String getDocumentType()
    {
        return documentType;
    }

    public String getDocumentTypeName()
    {
        return documentTypeName;
    }

    public String getTitle()
    {
        return title;
    }

    public String getDocumentEffectDate()
    {
        return documentEffectDate;
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

    public String getOutpatientNo()
    {
        return outpatientNo;
    }

    public String getWardsNo()
    {
        return wardsNo;
    }

    public String getWardsName() {
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

    public String getPatientName()
    {
        return patientName;
    }

    public String getGenderCode()
    {
        return genderCode;
    }

    public String getGenderName()
    {
        return genderName;
    }

    public String getBirthDate()
    {
        return birthDate;
    }

    public String getHospitalWardsId()
    {
        return hospitalWardsId;
    }

    public String getHospitalWardsName()
    {
        return hospitalWardsName;
    }

    public List<Reporter> getReporter()
    {
        return reporter;
    }

    public String getSignTime()
    {
        return signTime;
    }

    public String getSignatureId()
    {
        return signatureId;
    }

    public String getReviewTime()
    {
        return reviewTime;
    }

    public String getReviewPerson()
    {
        return reviewPerson;
    }

    public String getReviewPersonName()
    {
        return reviewPersonName;
    }

    public String getSubmittingDate()
    {
        return submittingDate;
    }

    public String getSubmittingPersonId()
    {
        return submittingPersonId;
    }

    public String getOrgCode()
    {
        return orgCode;
    }

    public String getOrgName()
    {
        return orgName;
    }
    
    public void setOrgCode(String orgCode)
    {
        this.orgCode = orgCode;
    }

    public void setOrgName(String orgName)
    {
        this.orgName = orgName;
    }

    public String getSubmittingPersonName()
    {
        return submittingPersonName;
    }

    public String getSendDeptId()
    {
        return sendDeptId;
    }

    public String getSendDeptName()
    {
        return sendDeptName;
    }

    public String getTesterId()
    {
        return testerId;
    }

    public String getTesterName()
    {
        return testerName;
    }

    public String getLabDept()
    {
        return labDept;
    }

    public String getLabDeptName()
    {
        return labDeptName;
    }

    public String getRequestTime()
    {
        return requestTime;
    }

    public String getRequestDeptCode()
    {
        return requestDeptCode;
    }

    public String getRequestDeptName()
    {
        return requestDeptName;
    }

    public List<String> getOrderLid()
    {
        return orderLid;
    }

    public String getVisitTimes()
    {
        return visitTimes;
    }

    public String getVisitType()
    {
        return visitType;
    }

    public String getVisitDept()
    {
        return visitDept;
    }

    public String getVisitDeptName()
    {
        return visitDeptName;
    }

    public String getReportMemo()
    {
        return reportMemo;
    }

    public String getTechnicalNote()
    {
        return technicalNote;
    }

    public String getMainInformationNo()
    {
        return mainInformationNo;
    }

    public String getLaboratoryNo()
    {
        return laboratoryNo;
    }

    public String getPriority()
    {
        return priority;
    }

    public String getSupplierName()
    {
        return supplierName;
    }

    public String getSupplierGender()
    {
        return supplierGender;
    }

    public String getSupplierGenderName()
    {
        return supplierGenderName;
    }

    public String getSupplierAge()
    {
        return supplierAge;
    }

    public String getLastMenstrualPeriod()
    {
        return lastMenstrualPeriod;
    }

    public String getMenopause()
    {
        return menopause;
    }

    public String getExpectedAge()
    {
        return expectedAge;
    }

    public String getFetalNumber()
    {
        return fetalNumber;
    }

    public String getGestationalCalculationBased()
    {
        return gestationalCalculationBased;
    }

    public String getImageText()
    {
        return imageText;
    }

    public String getImageExtension()
    {
        return imageExtension;
    }

    public String getPrompt()
    {
        return prompt;
    }

    public List<MorphologyReportItem> getMorphologyReportItem()
    {
        return morphologyReportItem;
    }

    public List<MorphologySample> getMorphologySample()
    {
        return morphologySample;
    }

    public List<Diagnosis> getDiagnosis()
    {
        return diagnosis;
    }

    public String getMedicalObservedCode()
    {
        return medicalObservedCode;
    }

    public String getMedicalObservedName()
    {
        return medicalObservedName;
    }
    
    public String getCellDevisionStatus() {
		return cellDevisionStatus;
	}

	public String getCellDevisionName() {
		return cellDevisionName;
	}

	public String getCellDevisionValue() {
		return cellDevisionValue;
	}

	public String getGeneCode() {
		return geneCode;
	}

	public String getGeneName() {
		return geneName;
	}

	public String getGeneValue() {
		return geneValue;
	}

	@Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("MessageId=");
        builder.append(getMessage().getId()+",");
        builder.append("Datetime=");
        builder.append(DateUtils.dateToString(getMessage().getDatetime(),
                null) + ",");
        builder.append("POCDIN000040UV04 [sourceSystemId=");
        builder.append(sourceSystemId);
        builder.append(", reportNo=");
        builder.append(reportNo);
        builder.append(", patientLid=");
        builder.append(patientLid);
        builder.append(", visitTimes=");
        builder.append(visitTimes);
        builder.append(", orderLid=");
        builder.append(orderLid);
        builder.append(", diagnosis=");
        builder.append(diagnosis);
        builder.append(", orgCode=");
        builder.append(orgCode);
        builder.append(", orgName=");
        builder.append(orgName);
        builder.append("]");
        return builder.toString();
    }
}
