package com.yly.cdr.hl7.dto.pocdin000040uv02;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.yly.cdr.hl7.dto.BaseDto;
import com.yly.cdr.hl7.dto.ExamItemMessage;
import com.yly.cdr.hl7.dto.ExaminationResult;
import com.yly.cdr.util.DateUtils;

public class POCDIN000040UV02 extends BaseDto
{
    /**
     * 检查报告root
     */
    @NotEmpty(message = "{POCDIN000040UV02.examReportRoot}")
    private String examReportRoot;
    /**
     * 报告号
     */
    @NotEmpty(message = "{POCDIN000040UV02.examReportLid}")
    private String examReportLid;

    /**
     * 版本号
     */
    @NotEmpty(message = "{POCDIN000040UV02.versionNo}")
    private String versionNo;

    // $Author:wei_peng
    // $Date:2012/9/25 13:45
    // $[BUG]0010017 DELETE BEGIN
    /**
     * 门诊患者ID
     */
     @NotEmpty(message = "{POCDIN000040UV02.patientLid}")
     private String patientLid;
    // $[BUG]0010017 DELETE END

    /**
     * 域ID
     */
    @NotEmpty(message = "{POCDIN000040UV02.patientDomain}")
    private String patientDomain;

    /**
     * 就诊号
     */
//    @NotEmpty(message = "{POCDIN000040UV02.medicalVisitNo}")
    private String medicalVisitNo;

    /**
     * 影像号
     */
//    @NotEmpty(message = "{POCDIN000040UV02.moviePhoneNo}")
//    private String moviePhoneNo;

    /**
     * 联系电话
     */
    private String telephone;

    /**
     * 患者姓名
     */
//    @NotEmpty(message = "{POCDIN000040UV02.patientName}")
    private String patientName;

    /**
     * 性别编码
     */
//    @NotEmpty(message = "{POCDIN000040UV02.genderCode}")
    private String genderCode;

    /**
     * 性别编码
     */
//    @NotEmpty(message = "{POCDIN000040UV02.genderName}")
    private String genderName;
    
    /**
     * 病区
     */
    private String wardName;
    
    /**
     * 病区编码
     * */
    private String wardId;
    
    /**
     * 床号
     */
    private String bedNo;

    /**
     * 出生日期
     */
    private String birthDate;

    /**
     * 文档管理机构名称
     */
    private String documentsAdmin;

    /**
     * 申请时间
     */
    private String requestDate;

    /**
     * 申请医生Id
     */
    private String requestDoctor;

    /**
     * 申请医生姓名
     */
    private String requestDoctorName;

    /**
     * 申请科室ID
     */
    private String requestDept;

    /**
     * 申请科室名称
     */
    private String requestDeptName;

    // $Author :jin_peng
    // $Date : 2012/09/20 14:47$
    // [BUG]0009691 DELETE BEGIN
    /**
     * 医嘱号
     */
    // @NotEmpty(message = "{POCDIN000040UV02.orderLid}")
    private List<String> orderLid;

    // [BUG]0009691 DELETE END
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
    
    // $Author:wei_peng
    // $Date:2012/9/25 13:45
    // $[BUG]0010017 DELETE BEGIN
    /**
     * 就诊
     */
    // private List<MedicalVisit> medicalVisit;
    // $[BUG]0010017 DELETE END

    /**
     * 报告内容
     */
    private List<ExaminationResult> reports;

    /**
     * 临床资料
     */
    private String clinicalData;

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
     * 召回原因
     */
    private String withdrawReason;

    /**
     * 检查类型
     */
    private String examType;
    
    /**
     * 执行科室
     * @return
     */
    private String examDept;

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
    @NotEmpty(message = "{POCDIN000040UV02.documentName}")
    private String documentName;

    /**
     * 报告人List
     */
    private List<ReportDoctor> reportDoctors;

    /**
     * 审核人List
     */
    private List<ReviewDoctor> reviewDoctors;

    /**
     * 电子签章时间
     */
    private String signTime;
    /**
     * 就诊次数
     */
    private String visitTimes;
    /**
     * 年龄
     */
    private String age;
    
    /**
     * 年龄单位
     * */
    private String ageUnit;
    
    /**
     * 检查项目编码
     */
    private String itemCode;
    
    /**
     * 检查项目名称
     */
    private String itemName;
    
    /**
     * 手术时间
     */
    private String operationDate;
    
    /**
     * 手术间Id
     */
    private String operationRoomId;
    
    /**
     * 手术间名称
     */
    private String operationRoomName;
    
    /**
     * 手术状态
     */
    private String operationStatus;
    
    /**
     * 术者信息
     */
    private List<OperatorMessage> operatorDoctors;
    
    /**
     * 助手信息
     */
    private List<AssistantMessage> assistantDoctors;
    
    /**
     * 具体项目信息
     */
    private List<ExamItemMessage> itemMessages;

    /**
     * 客观所见
     */
 //   @NotEmpty(message = "{POCDIN000040UV02.ObjectiveText}") 
    private String ObjectiveText;
    
    /**
     * 主观所见
     */
  //  @NotEmpty(message = "{POCDIN000040UV02.SubjectiveText}")
    private String SubjectiveText;
    
    /**
     * 医疗机构编码
     */
//    @NotEmpty(message = "{POCDIN000040UV02.orgCode}")
    private String orgCode;
    
    /**
     * 医疗机构名称
     */
//    @NotEmpty(message = "{POCDIN000040UV02.orgName}")
    private String orgName;
    
    /**
     * 就诊类型代码
     */
    @NotEmpty(message = "{POCDIN000040UV02.visitTypeCode}")
    private String visitTypeCode;
    
    /**
     * 就诊类型名称
     */
    
    private String visitTypeName;
    
    /**
     * 就诊流水号
     */
  //  @NotEmpty(message = "{POCDIN000040UV02.visitOrdNo}")
    private String visitOrdNo;
    
    /**
     * 病理编号
     */
    private String pathlogyNo;
    
    /**
     * HP检验项目编码
     */
    private String hpCode;
    
    /**
     * HP检验项目值
     */
    private String hpValue;
    
    public String getExamReportRoot()
    {
        return examReportRoot;
    }
    
    public String getVisitTimes()
    {
        return visitTimes;
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

    public String getExamReportLid()
    {
        return examReportLid;
    }

    public String getVersionNo()
    {
        return versionNo;
    }

    public String getPatientDomain()
    {
        return patientDomain;
    }

//    public String getMoviePhoneNo()
//    {
//        return moviePhoneNo;
//    }

    public String getTelephone()
    {
        return telephone;
    }

    public String getPatientName()
    {
        return patientName;
    }

    public String getWardName()
    {
        return wardName;
    }

    public String getBedNo()
    {
        return bedNo;
    }

    public String getGenderCode()
    {
        return genderCode;
    }

    public String getBirthDate()
    {
        return birthDate;
    }

    public String getDocumentsAdmin()
    {
        return documentsAdmin;
    }

    public String getRequestDate()
    {
        return requestDate;
    }

    public String getRequestDoctor()
    {
        return requestDoctor;
    }

    public String getRequestDoctorName()
    {
        return requestDoctorName;
    }

    public String getRequestDept()
    {
        return requestDept;
    }

    public String getRequestDeptName()
    {
        return requestDeptName;
    }

    public List<String> getOrderLid()
    {
        return orderLid;
    }

    public List<ExaminationResult> getReports()
    {
        return reports;
    }

    public String getClinicalData()
    {
        return clinicalData;
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

    public String getWithdrawReason()
    {
        return withdrawReason;
    }

    public String getExamType()
    {
        return examType;
    }

    public String getExamDept()
    {
        return examDept;
    }

    public String getSignatureId()
    {
        return signatureId;
    }

    public String getDocumentTypeName()
    {
        return documentTypeName;
    }

    public String getMedicalVisitNo()
    {
        return medicalVisitNo;
    }

    public List<ReportDoctor> getReportDoctors()
    {
        return reportDoctors;
    }

    public List<ReviewDoctor> getReviewDoctors()
    {
        return reviewDoctors;
    }

    public String getAge()
    {
        return age;
    }

    public String getItemCode()
    {
        return itemCode;
    }

    public String getItemName()
    {
        return itemName;
    }

    public String getOperationDate()
    {
        return operationDate;
    }

    public String getOperationRoomId()
    {
        return operationRoomId;
    }

    public String getOperationRoomName()
    {
        return operationRoomName;
    }

    public String getOperationStatus()
    {
        return operationStatus;
    }

    public List<OperatorMessage> getOperatorDoctors()
    {
        return operatorDoctors;
    }

    public List<AssistantMessage> getAssistantDoctors()
    {
        return assistantDoctors;
    }

    public List<ExamItemMessage> getItemMessages()
    {
        return itemMessages;
    }

    public String getObjectiveText()
    {
        return ObjectiveText;
    }

    public String getSubjectiveText()
    {
        return SubjectiveText;
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

    public String getVisitTypeCode() {
		return visitTypeCode;
	}

	public String getVisitTypeName() {
		return visitTypeName;
	}

	public String getVisitOrdNo() {
		return visitOrdNo;
	}

	public String getPatientLid() {
		return patientLid;
	}
	
	public String getPathlogyNo() {
		return pathlogyNo;
	}

	public String getGenderName() {
		return genderName;
	}

	public String getHpCode() {
		return hpCode;
	}

	public String getHpValue() {
		return hpValue;
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
        builder.append(DateUtils.dateToString(getMessage().getDatetime(),null) + ",");
        builder.append("POCDIN000040UV02 [");
        builder.append("examReportLid=");
        builder.append(examReportLid).append(", ");
        builder.append("versionNo=");
        builder.append(versionNo).append(", ");
        builder.append("patientDomain=");
        builder.append(patientDomain).append(", ");
        builder.append("medicalVisitNo=");
        builder.append(medicalVisitNo).append(", ");   
        builder.append("telephone=");
        builder.append(telephone).append(", ");
        builder.append("patientName=");
        builder.append(patientName).append(", ");
        builder.append("genderCode=");
        builder.append(genderCode).append(", ");
        builder.append("genderName=");
        builder.append(genderName).append(", ");
        builder.append("birthDate=");
        builder.append(birthDate).append(", ");
        builder.append("documentsAdmin=");
        builder.append(documentsAdmin).append(", ");
        builder.append("requestDate=");
        builder.append(requestDate).append(", ");
        builder.append("requestDoctor=");
        builder.append(requestDoctor).append(", ");
        builder.append("requestDoctorName=");
        builder.append(requestDoctorName).append(", ");
        builder.append("requestDept=");
        builder.append(requestDept).append(", ");
        builder.append("requestDeptName=");
        builder.append(requestDeptName).append(", ");
        builder.append("orderLid=");
        builder.append(orderLid).append(", ");
        builder.append("reports=");
        builder.append(reports).append(", ");
        builder.append("clinicalData=");
        builder.append(clinicalData).append(", ");
        builder.append("withDrawDate=");
        builder.append(withDrawDate).append(", ");
        builder.append("withDrawPerson=");
        builder.append(withDrawPerson).append(", ");
        builder.append("withDrawPersonName=");
        builder.append(withDrawPersonName).append(", ");
        builder.append("withdrawReason=");
        builder.append(withdrawReason).append(", ");
        builder.append("examDept=");
        builder.append(examDept).append(", ");
        builder.append("signatureId=");
        builder.append(signatureId).append(", ");
        builder.append("documentType=");
        builder.append(documentType).append(", ");
        builder.append("documentName=");
        builder.append(documentName).append(", ");
        builder.append("signTime=");
        builder.append(signTime).append(", ");
        builder.append("orgCode=");
        builder.append(orgCode).append(", ");
        builder.append("orgName=");
        builder.append(orgName).append(" ");
        builder.append("visitTypeCode=");
        builder.append(visitTypeCode).append(", ");
        builder.append("visitTypeName=");
        builder.append(visitTypeName).append(", ");
        builder.append("visitOrdNo=");
        builder.append(visitOrdNo).append(" ");
        builder.append("]");
        return builder.toString();
    }

    // $Author :jin_peng
    // $Date : 2012/10/16 18:08$
    // [BUG]0010434 ADD BEGIN
    public void setVersionNo(String versionNo)
    {
        this.versionNo = versionNo;
    }

	public String getAgeUnit() {
		return ageUnit;
	}

	public String getWardId() {
		return wardId;
	}

    // [BUG]0010434 ADD END
}
