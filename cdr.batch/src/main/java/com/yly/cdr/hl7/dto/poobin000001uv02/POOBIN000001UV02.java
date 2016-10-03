package com.yly.cdr.hl7.dto.poobin000001uv02;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.yly.cdr.hl7.dto.BaseDto;
import com.yly.cdr.hl7.dto.MainDiagnosis;
import com.yly.cdr.util.DateUtils;

public class POOBIN000001UV02 extends BaseDto
{
    /**
     * 新增更新标志
     */
    @NotEmpty(message = "{POOBIN000001UV02.newUpFlg}")
    private String newUpFlg;

    /**
     * 域ID
     */
    @NotEmpty(message = "{POOBIN000001UV02.patientDomain}")
    private String patientDomain;

    /**
     * 患者ID
     */
    @NotEmpty(message = "{POOBIN000001UV02.patientLid}")
    private String patientLid;

    /**
     * 就诊号
     */
    private String outPatientNo;

    /**
     * 病区码
     */
    private String wardId;

    /**
     * 病区名
     */
    private String wardName;

    /**
     * 床位号
     */
    private String bedNo;

    /**
     * 病人姓名
     */
    @NotEmpty(message = "{POOBIN000001UV02.patientName}")
    private String patientName;

    /**
     * 病人科室编码
     */
    //@NotEmpty(message = "{POOBIN000001UV02.deptId}")
    private String deptId;

    /**
     * 病人科室名称
     */
    //@NotEmpty(message = "{POOBIN000001UV02.deptName}")
    private String deptName;

    /**
     * 医疗机构代码
     */
    //@NotEmpty(message = "{POOBIN000001UV02.orgCode}")
    private String orgCode;
    /**
     * 医疗机构名称
     */
    //@NotEmpty(message = "{POOBIN000001UV02.orgName}")
    private String orgName;
    
    /**
     * 就诊次数
     */
//    @NotEmpty(message = "{POOBIN000001UV02.visitTimes}")
    private String visitTimes;

    /**
     * 就诊流水号
     */
    @NotEmpty(message = "{POOBIN000001UV02.visitOrdNo}")
    private String visitOrdNo;
    
    /**
     * 就诊类别编码
     */
    @NotEmpty(message = "{POOBIN000001UV02.visitType}")
    private String visitType;
    
    /**
     * 主诊断
     */
//    @NotEmpty(message = "{POOBIN000001UV02.mainDiagnosis}")
    private List<MainDiagnosis> diagnosis;

    public String getNewUpFlg()
    {
        return newUpFlg;
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

    public String getWardId()
    {
        return wardId;
    }

    public String getWardName()
    {
        return wardName;
    }

    public String getBedNo()
    {
        return bedNo;
    }

    public String getPatientName()
    {
        return patientName;
    }

    public String getDeptId()
    {
        return deptId;
    }

    public String getDeptName()
    {
        return deptName;
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

    public String getVisitTimes()
    {
        return visitTimes;
    }

    public List<MainDiagnosis> getDiagnosis() {
		return diagnosis;
	}
    
    public String getVisitOrdNo()
    {
        return visitOrdNo;
    }
    
    public String getVisitType()
    {
        return visitType;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("MessageId=");
        builder.append(getMessage().getId() + ",");
        builder.append("Datetime=");
        builder.append(DateUtils.dateToString(getMessage().getDatetime(),
                null) + ",");
        builder.append("POOBIN000001UV02 [newUpFlg=");
        builder.append(newUpFlg);
        builder.append(", patientDomain=");
        builder.append(patientDomain);
        builder.append(", patientLid=");
        builder.append(patientLid);
        builder.append(", outPatientNo=");
        builder.append(outPatientNo);
        builder.append(", patientName=");
        builder.append(patientName);
        builder.append(", orgCode=");
        builder.append(orgCode);
        builder.append(", orgName=");
        builder.append(orgName);
        builder.append(", visitType=");
        builder.append(visitType);
        builder.append(", visitTimes=");
        builder.append(visitTimes);
        builder.append(", visitOrdNo=");
        builder.append(visitOrdNo);
        builder.append(", mainDiagnosis=");
        builder.append(diagnosis);
        builder.append("]");
        return builder.toString();
    }
}
