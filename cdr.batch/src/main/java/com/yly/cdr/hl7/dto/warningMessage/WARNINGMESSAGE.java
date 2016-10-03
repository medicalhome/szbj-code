package com.yly.cdr.hl7.dto.warningMessage;

import java.util.List;

import com.yly.cdr.hl7.dto.BaseDto;
import com.yly.cdr.hl7.dto.WarningMessageLabReport;

public class WARNINGMESSAGE extends BaseDto
{
    private String msgId;
    
    private String msgName;
    
    private String sourceSysCode;
    
    private String targetSysCode;
    
    private String createTime;
    
    private String reportNo;
    
    private String visitTimes;
    
    private String patientDomain;
    
    private String patientLid;
    
    private String patientName;
    
    private String genderCode;
    
    private String age;
    
    private String visitDate;
    
    private String visitDept;
    
    private String visitDeptName;
    
    private String orgCode;
    
    private String orgName;
    
    private String wardsNo;
    
    private String bedNo;
    
    private String telcom;
    
    private String warningType;
    
    private String reportLink;
    
    /** 报告时间 */
    private String reportDate;
    
    /** 报告确认时间 */
    private String reviewDate;
    
    private String requestTime;
    
    private String applyPerson;
    
    private String applyPersonName;
    
    private List<WarningMessageLabReport> report;

    public String getMsgId()
    {
        return msgId;
    }

    public void setMsgId(String msgId)
    {
        this.msgId = msgId;
    }

    public String getMsgName()
    {
        return msgName;
    }

    public void setMsgName(String msgName)
    {
        this.msgName = msgName;
    }

    public String getSourceSysCode()
    {
        return sourceSysCode;
    }

    public void setSourceSysCode(String sourceSysCode)
    {
        this.sourceSysCode = sourceSysCode;
    }

    public String getTargetSysCode()
    {
        return targetSysCode;
    }

    public void setTargetSysCode(String targetSysCode)
    {
        this.targetSysCode = targetSysCode;
    }

    public String getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(String createTime)
    {
        this.createTime = createTime;
    }

    public String getReportNo()
    {
        return reportNo;
    }

    public void setReportNo(String reportNo)
    {
        this.reportNo = reportNo;
    }

    public String getVisitTimes()
    {
        return visitTimes;
    }

    public void setVisitTimes(String visitTimes)
    {
        this.visitTimes = visitTimes;
    }

    public String getPatientDomain()
    {
        return patientDomain;
    }

    public void setPatientDomain(String patientDomain)
    {
        this.patientDomain = patientDomain;
    }

    public String getPatientLid()
    {
        return patientLid;
    }

    public void setPatientLid(String patientLid)
    {
        this.patientLid = patientLid;
    }

    public String getPatientName()
    {
        return patientName;
    }

    public void setPatientName(String patientName)
    {
        this.patientName = patientName;
    }

    public String getGenderCode()
    {
        return genderCode;
    }

    public void setGenderCode(String genderCode)
    {
        this.genderCode = genderCode;
    }

    public List<WarningMessageLabReport> getReport()
    {
        return report;
    }

    public void setReport(List<WarningMessageLabReport> report)
    {
        this.report = report;
    }

    public String getAge()
    {
        return age;
    }

    public void setAge(String age)
    {
        this.age = age;
    }

    public String getVisitDate()
    {
        return visitDate;
    }

    public void setVisitDate(String visitDate)
    {
        this.visitDate = visitDate;
    }

    public String getVisitDept()
    {
        return visitDept;
    }

    public void setVisitDept(String visitDept)
    {
        this.visitDept = visitDept;
    }

    public String getVisitDeptName()
    {
        return visitDeptName;
    }

    public void setVisitDeptName(String visitDeptName)
    {
        this.visitDeptName = visitDeptName;
    }

    public String getWardsNo()
    {
        return wardsNo;
    }

    public void setWardsNo(String wardsNo)
    {
        this.wardsNo = wardsNo;
    }

    public String getBedNo()
    {
        return bedNo;
    }

    public void setBedNo(String bedNo)
    {
        this.bedNo = bedNo;
    }

    public String getTelcom()
    {
        return telcom;
    }

    public void setTelcom(String telcom)
    {
        this.telcom = telcom;
    }

    public String getWarningType()
    {
        return warningType;
    }

    public void setWarningType(String warningType)
    {
        this.warningType = warningType;
    }

    public String getReportLink()
    {
        return reportLink;
    }

    public void setReportLink(String reportLink)
    {
        this.reportLink = reportLink;
    }

    public String getRequestTime()
    {
        return requestTime;
    }

    public void setRequestTime(String requestTime)
    {
        this.requestTime = requestTime;
    }

    public String getApplyPerson()
    {
        return applyPerson;
    }

    public void setApplyPerson(String applyPerson)
    {
        this.applyPerson = applyPerson;
    }

    public String getApplyPersonName()
    {
        return applyPersonName;
    }

    public void setApplyPersonName(String applyPersonName)
    {
        this.applyPersonName = applyPersonName;
    }

    public String getOrgCode()
    {
        return orgCode;
    }

    public void setOrgCode(String orgCode)
    {
        this.orgCode = orgCode;
    }

    public String getOrgName()
    {
        return orgName;
    }

    public void setOrgName(String orgName)
    {
        this.orgName = orgName;
    }

	public String getReportDate() {
		return reportDate;
	}

	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}

	public String getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(String reviewDate) {
		this.reviewDate = reviewDate;
	}
    
}
