package com.yly.cdr.hl7.dto.prpain402001uv01;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.yly.cdr.hl7.dto.BaseDto;
import com.yly.cdr.hl7.dto.Diagnosis;
import com.yly.cdr.util.DateUtils;

public class PRPAIN402001UV01 extends BaseDto
{
    /**
     * 新增更新标志
     */
    @NotEmpty(message = "{PRPAIN402001UV01.newUpFlag}")
    private String newUpFlag;

    /**
     * 住院次数
     */
//    @NotEmpty(message = "{PRPAIN402001UV01.visitTimes}")
    private String visitTimes;

    /**
     * 就诊流水号
     */
    @NotEmpty(message = "{PRPAIN402001UV01.visitOrdNo}")
    private String visitOrdNo;
    
    /**
     * 就诊类别编码
     */
    @NotEmpty(message = "{PRPAIN402001UV01.visitType}")
    private String visitType;

    /**
     * 就诊类别名称
     */
    private String visitTypeName;
    
    /**
     * 入院时间
     */
    private String visitDate;

    /**
     * 入院状态代码
     */
    private String visitStatus;

    /**
     * 入院状态名称
     */
    private String visitStatusName;

    /**
     * 域代码
     */
    @NotEmpty(message = "{PRPAIN402001UV01.patientDomain}")
    private String patientDomain;

    /**
     * 门诊号
     */
    private String patientLid;

    /**
     * 病区号
     */
    private String admissionWards;

    /**
     * 病区名称
     */
    private String admissionWardsName;

    /**
     * 病床号
     */
    private String admissionBedNo;

    /**
     * 姓名
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
     * 年龄
     */
    private String age;
    
    // $Author :chang_xuewen
    // $Date : 2014/1/22 10:30$
    // [BUG]0042086 MODIFY BEGIN
    // $Author:tong_meng
    // $Date:2013/12/03 11:00
    // [BUG]0040270 ADD BEGIN
    /**
     * 医疗机构编码
     */
    /*@NotEmpty(message = "{PRPAIN402001UV01.orgCode}")*/
    private String orgCode;
    
	/**
     * 医疗机构名称
     */
    /*@NotEmpty(message = "{PRPAIN402001UV01.orgName}")*/
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
     * 入院医生编号
     */
    private String admissionDoctor;

    /**
     * 入院医生姓名
     */
    private String admissionDoctorName;

    /**
     * 入院护士编号
     */
    private String admissionNurse;

    /**
     * 入院护士姓名
     */
    private String admissionNurseName;

    /**
     * 住院医生编号
     */
    private String residentDoctor;

    /**
     * 住院医生姓名
     */
    private String residentDoctorName;
    
    /**
     * 主任医生编号
     */
    private String deptDoctor;

    /**
     * 主任医生姓名
     */
    private String deptDoctorName;


    /**
     * 主治医生编号
     */
    private String attendingDoctor;

    /**
     * 主治医生姓名
     */
    private String attendingDoctorName;

    /**
     * 科室号
     */
    private String visitDept;

    /**
     * 科室名称
     */
    private String visitDeptName;

    /**
     * 入院类别
     */
    private String admitingSourceCode;

    /**
     * 入院类别名称
     */
    private String admitingSourceName;

	/**
     * 诊断
     */
    private List<Diagnosis> diagnosis;

    /**
     * 费用类别编码
     */
    private String chargeClass;

    /**
     * 费用类别名称
     */
    private String chargeClassName;
    
    // Author :jia_yanqing
    // Date : 2013/5/28 13:30
    // [BUG]0033056 ADD BEGIN
    /**
     * 籍贯编码
     */
    private String nativeplaceCode;
    
    /**
     * 籍贯名称
     */
    private String nativeplaceName;
    // [BUG]0033056 ADD END

    /**
     * 患者EMPI号
     */
//    @NotEmpty(message = "{PRPAIN402001UV01.patientEid}")
    private String patientEid;
    
    private String babyFlag;

    
    public String getNewUpFlag()
    {
        return newUpFlag;
    }

    public String getVisitTimes()
    {
        return visitTimes;
    }

    public String getVisitDate()
    {
        return visitDate;
    }

    public String getVisitStatus()
    {
        return visitStatus;
    }

    public String getVisitStatusName()
    {
        return visitStatusName;
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

    public String getAge()
    {
        return age;
    }

    public String getOrgCode()
    {
        return orgCode;
    }

    public String getOrgName()
    {
        return orgName;
    }

    public String getAdmissionDoctor()
    {
        return admissionDoctor;
    }

    public String getAdmissionDoctorName()
    {
        return admissionDoctorName;
    }

    public String getAdmissionNurse()
    {
        return admissionNurse;
    }

    public String getAdmissionNurseName()
    {
        return admissionNurseName;
    }

    public String getVisitDept()
    {
        return visitDept;
    }

    public String getVisitDeptName()
    {
        return visitDeptName;
    }

    public String getAdmitingSourceCode()
    {
        return admitingSourceCode;
    }

    public String getAdmitingSourceName()
    {
        return admitingSourceName;
    }

    public String getResidentDoctor()
    {
        return residentDoctor;
    }

    public String getResidentDoctorName()
    {
        return residentDoctorName;
    }
    
    public String getDeptDoctor()
    {
        return deptDoctor;
    }

    public String getDeptDoctorName()
    {
        return deptDoctorName;
    }

    public String getAttendingDoctor()
    {
        return attendingDoctor;
    }

    public String getAttendingDoctorName()
    {
        return attendingDoctorName;
    }

    public List<Diagnosis> getDiagnosis()
    {
        return diagnosis;
    }

    public String getChargeClass()
    {
        return chargeClass;
    }

    public String getChargeClassName()
    {
        return chargeClassName;
    }
    
    // Author :jia_yanqing
    // Date : 2013/5/28 13:30
    // [BUG]0033056 ADD BEGIN
    public String getNativeplaceCode()
    {
        return nativeplaceCode;
    }

    public String getNativeplaceName()
    {
        return nativeplaceName;
    }

    public String getVisitOrdNo()
    {
        return visitOrdNo;
    }
    
    public String getVisitType()
    {
        return visitType;
    }
    
    public String getVisitTypeName()
    {
        return visitTypeName;
    }

    // [BUG]0033056 ADD END

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("MessageId=");
        builder.append(getMessage().getId()+",");
        builder.append("Datetime=");
        builder.append(DateUtils.dateToString(getMessage().getDatetime(),null) + ",");
        builder.append("PRPAIN402001UV01 [newUpFlag=");
        builder.append(newUpFlag);
        builder.append(", visitTimes=");
        builder.append(visitTimes);
        builder.append(", visitOrdNo=");
        builder.append(visitOrdNo);
        builder.append(", visitType=");
        builder.append(visitType);
        builder.append(", visitTypeName=");
        builder.append(visitTypeName);
        builder.append(", patientDomain=");
        builder.append(patientDomain);
        builder.append(", patientLid=");
        builder.append(patientLid);
        builder.append(", diagnosis=");
        builder.append(diagnosis);
        builder.append("]");
        return builder.toString();
    }

	public void setNewUpFlag(String newUpFlag) {
		this.newUpFlag = newUpFlag;
	}

	public String getPatientEid() {
		return patientEid;
	}

	public String getBabyFlag() {
		return babyFlag;
	}

}
