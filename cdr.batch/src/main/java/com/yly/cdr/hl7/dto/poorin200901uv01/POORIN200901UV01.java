package com.yly.cdr.hl7.dto.poorin200901uv01;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.yly.cdr.hl7.dto.BaseDto;
import com.yly.cdr.hl7.dto.MedicalVisit;
import com.yly.cdr.hl7.dto.RequestDto;
import com.yly.cdr.util.DateUtils;

public class POORIN200901UV01 extends BaseDto
{
    /**
     * 新增更新标志
     */
    @NotEmpty(message = "{POORIN200901UV01.newUpFlg}")
    private String newUpFlg;

    /**
     * 域代码
     */
    @NotEmpty(message = "{POORIN200901UV01.patientDomain}")
    private String patientDomain;

    /**
     * 门诊患者ID
     */
    @NotEmpty(message = "{POORIN200901UV01.patientLid}")
    private String patientLid;

    /**
     * 病房号
     */
    private String admissionWards;

    /**
     * 病房名称
     */
    private String admissionWardsName;

    /**
     * 病床号
     */
    private String admissionBedNo;

    /**
     * 注册日期
     */
    private String efficDate;

    /**
     * 身份证号
     */
    private String credentialNo;

    /**
     * 患者姓名
     */
    private String patientName;

    /**
     * 联系电话
     */
    private String telephone;

    /**
     * 移动电话
     */
    private String mobile;

    /**
     * 性别
     */
    private String genderCode;

    /**
     * 出生日期
     */
    private String birthTime;

    /**
     * 家庭住址
     */
    private String addrHs;

    /**
     * 家庭住址
     */
    private String addrCs;

    /**
     * 家庭住址
     */
    private String addrZs;

    /**
     * 工作单位代码
     */
    private String workPlaceId;

    /**
     * 工作单位名称
     */
    private String workPlace;

    // $Author :chang_xuewen
    // $Date : 2014/1/22 10:30$
    // [BUG]0042086 MODIFY BEGIN   
    // $Author:tong_meng
    // $Date:2013/12/03 11:00
    // [BUG]0040270 ADD BEGIN
    // 医疗机构代码
    /*@NotEmpty(message = "{POORIN200901UV01.orgCode}")*/
    private String orgCode;
    
	// 医疗机构名称
    /*@NotEmpty(message = "{POORIN200901UV01.orgName}")*/
    private String orgName;
    public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
    // [BUG]0040270 ADD END
    // [BUG]0042086 MODIFY END   
    /**
     * 开医嘱时间
     */
    private String orderTime;

    /**
     * 开医嘱医生
     */
    // @NotEmpty(message = "{POORIN200901UV01.requestDoctor}")
    private String requestDoctor;

    /**
     * 开医嘱医生姓名
     */
    // @NotEmpty(message = "{POORIN200901UV01.requestDoctorName}")
    private String requestDoctorName;

    /**
     * 申请科室编码
     */
    // @NotEmpty(message = "{POORIN200901UV01.requestDept}")
    private String requestDept;

    /**
     * 申请科室名称
     */
    // @NotEmpty(message = "{POORIN200901UV01.requestDeptName}")
    private String requestDeptName;

    /**
     * 申请原因
     * */ 
    private String requestReason; 
    
    /**
     * 录入时间from
     */
    private String transcriberFromDate;

    /**
     * 录入时间To
     */
    private String transcriberToDate;

    /**
     * 录入人ID
     */
    private String transcriberId;

    /**
     * 录入人姓名
     */
    private String transcriberName;

    /**
     * 医生信息
     */
    private String doctorInfo;

    /**
     * 确认时间
     */
    private String confirmTime;

    /**
     * 确认人标识
     */
    private String confirmPerson;

    /**
     * 确认人姓名
     */
    private String confirmPersonName;

    /**
     * 体格检验项目
     */
    private String physicalItems;

    /**
     * 检验申请单
     */
    @NotEmpty(message = "{POORIN200901UV01.requestList}")
    private List<RequestDto> requestList;

    /**
     * 就诊
     */
    private List<MedicalVisit> visitList;

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

    public String getAdmissionWards()
    {
        return admissionWards;
    }

    public String getAdmissionWardsName()
    {
        return admissionWardsName;
    }

    public String getAdmissionBedNo()
    {
        return admissionBedNo;
    }

    public String getEfficDate()
    {
        return efficDate;
    }

    public String getCredentialNo()
    {
        return credentialNo;
    }

    public String getPatientName()
    {
        return patientName;
    }

    public String getTelephone()
    {
        return telephone;
    }

    public String getMobile()
    {
        return mobile;
    }

    public String getGenderCode()
    {
        return genderCode;
    }

    public String getBirthTime()
    {
        return birthTime;
    }

    public String getAddrHs()
    {
        return addrHs;
    }

    public String getAddrCs()
    {
        return addrCs;
    }

    public String getAddrZs()
    {
        return addrZs;
    }

    public String getWorkPlaceId()
    {
        return workPlaceId;
    }

    public String getWorkPlace()
    {
        return workPlace;
    }

    public String getOrgCode()
    {
        return orgCode;
    }

    public String getOrgName()
    {
        return orgName;
    }

    public String getOrderTime()
    {
        return orderTime;
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

    public String getTranscriberFromDate()
    {
        return transcriberFromDate;
    }

    public String getTranscriberToDate()
    {
        return transcriberToDate;
    }

    public String getTranscriberId()
    {
        return transcriberId;
    }

    public String getTranscriberName()
    {
        return transcriberName;
    }

    public String getDoctorInfo()
    {
        return doctorInfo;
    }

    public String getConfirmTime()
    {
        return confirmTime;
    }

    public String getConfirmPerson()
    {
        return confirmPerson;
    }

    public String getConfirmPersonName()
    {
        return confirmPersonName;
    }

    public String getPhysicalItems()
    {
        return physicalItems;
    }

    public List<RequestDto> getRequestList()
    {
        return requestList;
    }

    public List<MedicalVisit> getVisitList()
    {
        return visitList;
    }

    public void setVisitList(List<MedicalVisit> visitList)
    {
        this.visitList = visitList;
    }

    public String getRequsetReason(){
    	return requestReason;
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
        builder.append("POORIN200901UV01 [newUpFlg=");
        builder.append(newUpFlg);
        builder.append(", patientDomain=");
        builder.append(patientDomain);
        builder.append(", patientLid=");
        builder.append(patientLid);
        builder.append(", requestList=");
        builder.append(requestList);
        builder.append(", visitList=");
        builder.append(visitList);
        builder.append("]");
        return builder.toString();
    }
    
}
