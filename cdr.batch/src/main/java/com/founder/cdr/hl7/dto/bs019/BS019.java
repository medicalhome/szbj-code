package com.founder.cdr.hl7.dto.bs019;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.founder.cdr.hl7.dto.BaseDto;
import com.founder.cdr.hl7.dto.SampleContent;
import com.founder.cdr.util.DateUtils;

public class BS019 extends BaseDto
{
    /**
     * 医嘱号信息内容
     */
    private List<SampleContent> sampleContent;
    
    //@NotEmpty(message = "{BS019.orgCode}")
    private String orgCode;
    //@NotEmpty(message = "{BS019.orgName}")
    private String orgName;
//    /**
//     * 操作类型
//     */
//    @NotEmpty(message = "{Sample.newUpFlag}")
//    private String newUpFlag;
    /**
     * 标本条码号
     */
    @NotEmpty(message = "{Sample.sampleNo}")
    private String sampleNo;
    /**
     * 域ID
     */
    @NotEmpty(message = "{Sample.patientDomain}")
    private String patientDomain;
    
    /**
     * 就诊次数
     */
//    @NotEmpty(message = "{Sample.visitTime}")
    private String visitTime;
    /**
     * 患者ID
     */
    @NotEmpty(message = "{Sample.patientLid}")
    private String patientLid;
    /**
     * 就诊流水号
     */
    @NotEmpty(message = "{Sample.visitOrdNo}")
    private String visitOrdNo;
    
    /**
     * 就诊类别编码
     */
    @NotEmpty(message = "{Sample.visitType}")
    private String visitType;
    /**
     * 执行科室编码
     */
    private String execDeptCode;
    /**
     * 执行科室名称
     */
    private String execDeptName;

//    public String getNewUpFlag()
//    {
//        return newUpFlag;
//    }

    public String getSampleNo()
    {
        return sampleNo;
    }

    public String getPatientDomain()
    {
        return patientDomain;
    }

    public String getExecDeptCode()
    {
        return execDeptCode;
    }

    public String getExecDeptName()
    {
        return execDeptName;
    }
    public String getOrgCode() {
		return orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public List<SampleContent> getSampleContent()
    {
        return sampleContent;
    }
    
    public String getVisitTime() {
		return visitTime;
	}

	public String getPatientLid() {
		return patientLid;
	}

	public String getVisitOrdNo() {
		return visitOrdNo;
	}

	public String getVisitType() {
		return visitType;
	}

	@Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("MessageId=");
        builder.append(getMessage().getId() + ",");
        builder.append("Datetime=");
        builder.append(DateUtils.dateToString(getMessage().getDatetime(), null) + ",");
        builder.append("BS019 [sampleContent=");
        builder.append(", sampleNo=");
        builder.append(sampleNo);
        builder.append(", patientDomain=");
        builder.append(patientDomain);
        builder.append(", patientLid=");
        builder.append(patientLid);
        builder.append(", visitOrdNo=");
        builder.append(visitOrdNo);
        builder.append(", visitType=");
        builder.append(visitType);
        builder.append(", execDeptCode=");
        builder.append(execDeptCode);
        builder.append(", execDeptName=");
        builder.append(execDeptName);
        builder.append(sampleContent);
        builder.append("]");
        return builder.toString();
    }
}
