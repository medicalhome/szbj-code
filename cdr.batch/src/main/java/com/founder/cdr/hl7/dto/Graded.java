package com.founder.cdr.hl7.dto;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.founder.cdr.hl7.dto.GradedItems;

public class Graded extends BaseDto
{
    /**
     * 评分记录编码
     */
    @NotEmpty(message = "{Graded.gradedRecordCode}")
    private String gradedRecordCode;

    /**
     * 评分记录时间
     */
    @NotEmpty(message = "{Graded.gradedRecordTime}")
    private String gradedRecordTime;

    /**
     * 域ID
     */
    @NotEmpty(message = "{Graded.patientDomain}")
    private String patientDomain;

    /**
     * 门诊系统患者ID
     */
    @NotEmpty(message = "{Graded.patientLid}")
    private String patientLid;

    /**
     * 就诊次数
     */
    @NotEmpty(message = "{Graded.visitTimes}")
    private String visitTimes;

    /**
     * 患者姓名
     */
    private String patientName;

    /**
     * 性别标识
     */
    private String genderCode;

    /**
     * 出生日期
     */
    private String birthday;

    /**
     * 年龄
     */
    private String age;

    /**
     * 记录医生ID
     */
    @NotEmpty(message = "{Graded.recordDoctorID}")
    private String recordDoctorID;

    /**
     * 记录医生姓名
     */
    @NotEmpty(message = "{Graded.recordDoctorName}")
    private String recordDoctorName;

    /**
     * 科室ID
     */
    @NotEmpty(message = "{Graded.deptNO}")
    private String deptNO;

    /**
     * 科室名称
     */
    @NotEmpty(message = "{Graded.deptName}")
    private String deptName;

    /**
     * 评分详细
     */
    @NotEmpty(message = "{Graded.gradedItems}")
    private List<GradedItems> gradedItems;

    public String getGradedRecordCode()
    {
        return gradedRecordCode;
    }

    public String getGradedRecordTime()
    {
        return gradedRecordTime;
    }

    public String getPatientDomain()
    {
        return patientDomain;
    }

    public String getPatientLid()
    {
        return patientLid;
    }

    public String getVisitTimes()
    {
        return visitTimes;
    }

    public String getPatientName()
    {
        return patientName;
    }

    public String getGenderCode()
    {
        return genderCode;
    }

    public String getBirthday()
    {
        return birthday;
    }

    public String getAge()
    {
        return age;
    }

    public String getRecordDoctorID()
    {
        return recordDoctorID;
    }

    public String getRecordDoctorName()
    {
        return recordDoctorName;
    }

    public String getDeptNO()
    {
        return deptNO;
    }

    public String getDeptName()
    {
        return deptName;
    }

    public List<GradedItems> getGradedItems()
    {
        return gradedItems;
    }
    
    // Author :jia_yanqing
    // Date : 2013/06/08 11:30
    // [BUG]0033506 ADD BEGIN
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Graded [gradedRecordCode=");
        builder.append(gradedRecordCode);
        builder.append(", gradedRecordTime=");
        builder.append(gradedRecordTime);
        builder.append(", patientDomain=");
        builder.append(patientDomain);
        builder.append(", patientLid=");
        builder.append(patientLid);
        builder.append(", visitTimes=");
        builder.append(visitTimes);
        builder.append(", recordDoctorID=");
        builder.append(recordDoctorID);
        builder.append(", recordDoctorName=");
        builder.append(recordDoctorName);
        builder.append(", deptNO=");
        builder.append(deptNO);
        builder.append(", deptName=");
        builder.append(deptName);
        builder.append(", gradedItems=");
        builder.append(gradedItems);
        builder.append("]");
        return builder.toString();
    }
    // [BUG]0033506 ADD END
}
