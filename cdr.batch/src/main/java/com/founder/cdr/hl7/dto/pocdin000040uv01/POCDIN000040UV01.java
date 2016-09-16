package com.founder.cdr.hl7.dto.pocdin000040uv01;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.founder.cdr.hl7.dto.BaseDto;
import com.founder.cdr.hl7.dto.LabReport;
import com.founder.cdr.hl7.dto.LabSample;
import com.founder.cdr.hl7.dto.Diagnosis;
import com.founder.cdr.util.DateUtils;

public class POCDIN000040UV01 extends BaseDto
{
    /**
     * 报告号
     */
    @NotEmpty(message = "{POCDIN000040UV01.reportNo}")
    private String reportNo;

    /**
     * 文档操作版本
     */
    @NotEmpty(message = "{POCDIN000040UV01.versionNumber}")
    private String versionNumber;

    /**
     * 患者本地id
     */
    @NotEmpty(message = "{POCDIN000040UV01.patientLid}")
    private String patientLid;

    /**
     * 就诊次数
     */
  //  @NotEmpty(message = "{POCDIN000040UV01.visitTimes}")
    private String visitTimes;

    /**
     * 域id
     */
    @NotEmpty(message = "{POCDIN000040UV01.patientDomain}")
    private String patientDomain;

    //wang.meng 普通报告用
    /**
     * 病区
     */
//    private String wardsCode;
    
    //wang.meng 微生物报告用
    /**
     * 病房号
     */
    private String wardsNo;
    
    /**
     * 病房名称
     */
    private String wardsName;
    
    /**
     * 床号
     */
    private String bedNo;

    /**
     * 患者姓名
     */
    private String patientName;

    /**
     * 性别
     */
    private String genderCode;

    /**
     * 出生日期
     */
    private String birthDate;

    /**
     * 报告日期
     */
    private String reportDate;

    /**
     * 报告人
     */
    private String reporterId;

    /**
     * 报告人姓名
     */
    private String reporterName;

    /**
     * 审核日期
     */
    private String reviewDate;

    /**
     * 审核人
     */
    private String reviewerId;

    /**
     * 审核人姓名
     */
    private String reviewerName;

    /**
     * 召回时间
     */
    private String withDrawDate;

    /**
     * 召回人
     */
    private String withDrawPerson;

    /**
     * 召回人姓名
     */
    private String withDrawPersonName;

    /**
     * 执行科室
     */
    private String labDept;

    /**
     * 执行科室名称
     */
    private String labDeptName;

    /**
     * 执行科室BS354微生物报告
     */
    private String labDeptBS354;

    /**
     * 执行科室名称BS354微生物报告
     */
    private String labDeptNameBS354;
    
    /**
     * 医嘱编号
     */
    private List<String> orderNo;

    /**
     * 就诊类型
     */
    @NotEmpty(message = "{POCDIN000040UV01.visitType}")
    private String visitType;

    /**
     * 就诊日期
     */
    private String visitDate;

    /**
     * 就诊科室
     */
    @NotEmpty(message = "{POCDIN000040UV01.visitDept}")
    private String visitDept;

    /**
     * 就诊科室名称
     */
    @NotEmpty(message = "{POCDIN000040UV01.visitDeptName}")
    private String visitDeptName;

    /**
     * 备注
     */
    private String reportMemo;

    /**
     * 技术备注
     */
    private String techMemo;

    /**
     * 表现现象
     */
    private String surface;

    /**
     * 报告内容
     */
    private List<LabReport> report;

    /**
     * 标本内容
     */
    private List<LabSample> sample;

    /**
     * 诊断
     */
    private List<Diagnosis> diagnosises;

    /**
     * 召回时间
     */
    // private String withdrawTime;
    /**
     * 召回人
     */
    // private String withdrawPerson;
    /**
     * 召回人姓名
     */
    // private String withdrawPersonName;
    /**
     * 召回原因
     */
    private String withdrawReason;

    /**
     * 电子签章号
     */
    private String signatureId;

    /**
     * 文档类别
     */
    private String documentType;

    /**
     * 文档类型名称
     */
    private String documentTypeName;

    /**
     * 文档名称
     */
    private String documentName;

    /**
     * 电子签章时间
     */
    private String signTime;

    /**
     * 申请时间
     */
    private String requestTime;

    /**
     * 送检医生ID
     */
    private String applyPerson;

    /**
     * 送检医生姓名
     */
    private String applyPersonName;

    /**
     * 评语内容
     */
    private String comment;

    /**
     * 结果描述
     */
    private String descriptionResult;

    /**
     * 检验医生id
     */
    private String testerId;

    /**
     * 检验医生姓名
     */
    private String testerName;

    /**
     * 系统源id
     */
    private String sourceSystemId;

    // $Author :jin_peng
    // $Date : 2012/10/16 14:37$
    // [BUG]0010366 ADD BEGIN
    /**
     * 报告类型编码
     */
    private String reportTypeCode;

    /**
     * 报告类型名称
     */
    private String reportTypeName;

    // [BUG]0010366 ADD END
    
    /**
     *  医疗组织机构代码 
     */
    private String orgCode;
    
    /**
     *  医疗组织机构名称 
     */
    private String orgName;
    
    /**
     * 	就诊号
     */
    private String medicalNo;
    
    /**
     *  性别名称
     */
    private String genderName;
    
    /**
     * 病房号
     */
    private String roomNo;
    
    /**
     * 方法
     */
    private String method;
    
    /**
     * 就诊流水号
     */
    @NotEmpty(message = "{POCDIN000040UV01.visitOrdNo}")
    private String visitOrdNo;
    
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
    
    

    public String getGenderName() {
		return genderName;
	}

	public String getMedicalNo() {
		return medicalNo;
	}

	public String getDocumentType()
    {
        return documentType;
    }

    public String getDocumentName()
    {
        return documentName;
    }

    public String getSignTime()
    {
        return signTime;
    }

    public String getReportNo()
    {
        return reportNo;
    }

    public String getVersionNumber()
    {
        return versionNumber;
    }
    
    public void setVersionNumber(String versionNumber)
    {
        this.versionNumber = versionNumber;
    }

    public String getPatientLid()
    {
        return patientLid;
    }

    public String getVisitTimes()
    {
        return visitTimes;
    }

    public String getPatientDomain()
    {
        return patientDomain;
    }

    
//    public String getWardsCode() {
//		return wardsCode;
//	}
//
//	public void setWardsCode(String wardsCode) {
//		this.wardsCode = wardsCode;
//	}

	public String getWardsNo()
    {
        return wardsNo;
    }

    public String getBedNo()
    {
        return bedNo;
    }

    public String getPatientName()
    {
        return patientName;
    }

    public String getGenderCode()
    {
        return genderCode;
    }

    public String getBirthDate()
    {
        return birthDate;
    }

    public String getReportDate()
    {
        return reportDate;
    }

    public String getReporterId()
    {
        return reporterId;
    }

    public String getReporterName()
    {
        return reporterName;
    }

    public String getReviewDate()
    {
        return reviewDate;
    }

    public String getReviewerId()
    {
        return reviewerId;
    }

    public String getReviewerName()
    {
        return reviewerName;
    }

    public String getWithDrawDate()
    {
        return withDrawDate;
    }

    public String getWithDrawPerson()
    {
        return withDrawPerson;
    }

    public String getWithDrawPersonName()
    {
        return withDrawPersonName;
    }

    public String getLabDept()
    {
        return labDept;
    }

    public String getLabDeptName()
    {
        return labDeptName;
    }

    public List<String> getOrderNo()
    {
        return orderNo;
    }

    public String getVisitType()
    {
        return visitType;
    }

    public String getVisitDate()
    {
        return visitDate;
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

    public String getTechMemo()
    {
        return techMemo;
    }

    public String getSurface()
    {
        return surface;
    }

    public List<LabReport> getReport()
    {
        return report;
    }

    public List<LabSample> getSample()
    {
        return sample;
    }

    public List<Diagnosis> getDiagnosises()
    {
        return diagnosises;
    }

    // public String getWithdrawTime()
    // {
    // return withdrawTime;
    // }

    // public String getWithdrawPerson()
    // {
    // return withdrawPerson;
    // }
    //
    // public String getWithdrawPersonName()
    // {
    // return withdrawPersonName;
    // }

    public String getWithdrawReason()
    {
        return withdrawReason;
    }

    public String getSignatureId()
    {
        return signatureId;
    }

    public String getRequestTime()
    {
        return requestTime;
    }

    public String getApplyPerson()
    {
        return applyPerson;
    }

    public String getApplyPersonName()
    {
        return applyPersonName;
    }

    public String getComment()
    {
        return comment;
    }

    public String getDescriptionResult()
    {
        return descriptionResult;
    }

    public String getTesterId()
    {
        return testerId;
    }

    public String getTesterName()
    {
        return testerName;
    }

    public String getSourceSystemId()
    {
        return sourceSystemId;
    }

    public String getDocumentTypeName()
    {
        return documentTypeName;
    }

    // $Author :jin_peng
    // $Date : 2012/10/16 14:37$
    // [BUG]0010366 ADD BEGIN
    public String getReportTypeCode()
    {
        return reportTypeCode;
    }

    public String getReportTypeName()
    {
        return reportTypeName;
    }
    // [BUG]0010366 ADD END

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


	public String getWardsName() {
		return wardsName;
	}

	public void setWardsName(String wardsName) {
		this.wardsName = wardsName;
	}

	public String getRoomNo() {
		return roomNo;
	}

	public String getMethod() {
		return method;
	}
	
	public String getVisitOrdNo() {
		return visitOrdNo;
	}
	public String getImageText() {
		return imageText;
	}

	public void setImageText(String imageText) {
		this.imageText = imageText;
	}

	public String getPrompt() {
		return prompt;
	}

	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

	public String getImageExtension() {
		return imageExtension;
	}

	public void setImageExtension(String imageExtension) {
		this.imageExtension = imageExtension;
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
        builder.append("POCDIN000040UV01 [reportNo=");
        builder.append(reportNo);
        builder.append(", versionNumber=");
        builder.append(versionNumber);
        builder.append(", patientLid=");
        builder.append(patientLid);
        builder.append(", visitTimes=");
        builder.append(visitTimes);
        builder.append(", patientDomain=");
        builder.append(patientDomain);
        builder.append(", orgCode=");
        builder.append(orgCode);
        builder.append(", orgName=");
        builder.append(orgName);
        builder.append(", orderNo=");
        builder.append(orderNo);
        builder.append(", report=");
        builder.append(report);
        builder.append(", sample=");
        builder.append(sample);
        builder.append(", diagnosises=");
        builder.append(diagnosises);
        builder.append(", roomNo=");
        builder.append(roomNo);
        builder.append(", method=");
        builder.append(method);
        builder.append(", visitOrdNo=");
        builder.append(visitOrdNo);
        builder.append("]");
        return builder.toString();
    }

	public String getLabDeptBS354() {
		return labDeptBS354;
	}

	public String getLabDeptNameBS354() {
		return labDeptNameBS354;
	}

	public void setLabDept(String labDept) {
		this.labDept = labDept;
	}

	public void setLabDeptName(String labDeptName) {
		this.labDeptName = labDeptName;
	}
    
}
