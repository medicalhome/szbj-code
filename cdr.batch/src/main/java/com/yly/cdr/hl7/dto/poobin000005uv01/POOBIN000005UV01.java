package com.yly.cdr.hl7.dto.poobin000005uv01;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.yly.cdr.hl7.dto.BaseDto;
import com.yly.cdr.hl7.dto.NursingOperationDto;
import com.yly.cdr.util.DateUtils;

public class POOBIN000005UV01 extends BaseDto
{
    // Author :jia_yanqing
    // Date : 2013/1/17 13:33
    // [BUG]0013379 ADD BEGIN
    /**
     * 新增更新操作
     */
    @NotEmpty(message = "{POOBIN000005UV01.newUpFlag}")
    private String newUpFlag;
    // [BUG]0013379 ADD BEGIN
    
    /**
     * 域ID
     */
    @NotEmpty(message = "{POOBIN000005UV01.patientDomain}")
    private String patientDomain;

    /**
     * 患者本地ID
     */
    @NotEmpty(message = "{POOBIN000005UV01.patientLid}")
    private String patientLid;

    /**
     * 就诊号
     */
    private String outpatientNo;

    /**
     * 就诊次数
     */
//    @NotEmpty(message = "{POOBIN000005UV01.visitTime}")
    private String visitTime;

    /**
     * 就诊类别
     * */
    private String visitType;
    
    
    /**
     * 病区编码
     */
    private String admissionWards;

    /**
     * 病区名称
     */
    private String admissionWardsName;

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
     * 病人科室编码
     */
    private String deptCode;

    /**
     * 病人科室名称
     */
    private String deptName;

    /**
     * 护理操作
     */
    @NotEmpty(message = "{POOBIN000005UV01.nursingOperation}")
    private List<NursingOperationDto> nursingOperation;

    // Author :jia_yanqing
    // Date : 2013/1/17 13:33
    // [BUG]0013379 ADD BEGIN
    /**
     * 操作码
     */
    @NotEmpty(message = "{POOBIN000005UV01.operationId}")
    private String operationId;
    // [BUG]0013379 ADD END
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
    
    /*
     * $Author: yu_yzh
     * $Date: 2015/8/10
     * 就诊流水号
     * ADD BEGIN
     * */
    /**
     * 就诊流水号
     * */
    private String visitOrdNo;
    
    public String getVisitOrdNo() {
		return visitOrdNo;
	}

	public void setVisitOrdNo(String visitOrdNo) {
		this.visitOrdNo = visitOrdNo;
	}
    // ADD END


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

    public String getVisitTime()
    {
        return visitTime;
    }

    public String getVisitType(){
    	return visitType;
    }
    
    public String getAdmissionWards()
    {
        return admissionWards;
    }

    public String getAdmissionWardsName()
    {
        return admissionWardsName;
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

    public String getDeptCode()
    {
        return deptCode;
    }

    public String getDeptName()
    {
        return deptName;
    }

    public List<NursingOperationDto> getNursingOperation()
    {
        return nursingOperation;
    }

    // Author :jia_yanqing
    // Date : 2013/1/17 13:33
    // [BUG]0013379 ADD BEGIN
    public String getOperationId()
    {
        return operationId;
    }

    public String getNewUpFlag()
    {
        return newUpFlag;
    }
    // [BUG]0013379 ADD END
    
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

    // $Author :jin_peng
    // $Date : 2013/10/28 13:40$
    // [BUG]0038604 ADD BEGIN
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("MessageId=");
        builder.append(getMessage().getId()+",");
        builder.append("Datetime=");
        builder.append(DateUtils.dateToString(getMessage().getDatetime(),
                null) + ",");
        builder.append("POOBIN000005UV01 [newUpFlag=");
        builder.append(newUpFlag);
        builder.append(", patientDomain=");
        builder.append(patientDomain);
        builder.append(", patientLid=");
        builder.append(patientLid);
        builder.append(", outpatientNo=");
        builder.append(outpatientNo);
        builder.append(", visitTime=");
        builder.append(visitTime);
        builder.append(", organizationCode=");
        builder.append(organizationCode);
        builder.append(", operationId=");
        builder.append(operationId);
        builder.append(", admissionWards=");
        builder.append(admissionWards);
        builder.append(", admissionWardsName=");
        builder.append(admissionWardsName);
        builder.append(", bedNo=");
        builder.append(bedNo);
        builder.append(", deptCode=");
        builder.append(deptCode);
        builder.append(", deptName=");
        builder.append(deptName);
        builder.append(", visitOrdNo=");
        builder.append(visitOrdNo);
        builder.append(", visitType=");
        builder.append(visitType);
        builder.append("]");
        return builder.toString();
    }
    // [BUG]0038604 ADD END
}
