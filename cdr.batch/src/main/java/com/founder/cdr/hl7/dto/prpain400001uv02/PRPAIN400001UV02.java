package com.founder.cdr.hl7.dto.prpain400001uv02;

import org.hibernate.validator.constraints.NotEmpty;

import com.founder.cdr.hl7.dto.BaseDto;

public class PRPAIN400001UV02 extends BaseDto
{
    /**
     * 就诊号别
     */
    private String visitType;

    /**
     * 就诊时间
     */
    private String visitDate;

    /**
     * 域代码
     */
    @NotEmpty(message = "{PRPAIN400001UV02.patientDomain}")
    private String patientDomain;

    /**
     * 门诊号
     */
    private String patientLid;

    /**
     * 患者姓名
     */
    private String patientName;

    /**
     * 患者科室
     */
    private String visitDept;

    /**
     * 就诊次数
     */
    @NotEmpty(message = "{PRPAIN400001UV02.visitTimes}")
    private String visitTimes;

    /**
     * 就诊执行状态
     */
    private String visitStatus;

    /**
     * 就诊医生代码
     */
    private String visitDoctor;

    /**
     * 就诊医生姓名
     */
    private String visitDoctorName;

    /**
     * 新增更新标志
     */
    private String newUpFlg;

    public String getVisitType()
    {
        return visitType;
    }

    public String getVisitDate()
    {
        return visitDate;
    }

    public String getPatientDomain()
    {
        return patientDomain;
    }

    public String getPatientLid()
    {
        return patientLid;
    }

    public String getPatientName()
    {
        return patientName;
    }

    public String getVisitDept()
    {
        return visitDept;
    }

    public String getVisitTimes()
    {
        return visitTimes;
    }

    public String getVisitStatus()
    {
        return visitStatus;
    }

    public String getVisitDoctor()
    {
        return visitDoctor;
    }

    public String getVisitDoctorName()
    {
        return visitDoctorName;
    }

    public String getNewUpFlg()
    {
        return newUpFlg;
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder("{\n");
        sb.append("message content:").append(getMessage().getContent()).append(
                "\n");
        sb.append("patientDomain:").append(getPatientDomain()).append("\n");
        sb.append("visitTimes:").append(getVisitTimes()).append("\n");
        sb.append("visitType:").append(getVisitType()).append("\n");
        sb.append("visitDate:").append(getVisitDate()).append("\n");
        sb.append("visitNo:").append(getPatientLid()).append("\n");
        sb.append("patientName:").append(getPatientName()).append("\n");
        sb.append("visitDept:").append(getVisitDept()).append("\n");
        sb.append("visitStatus:").append(getVisitStatus()).append("\n");
        sb.append("visitDoctor:").append(getVisitDoctor()).append("\n");
        sb.append("visitDoctorName:").append(getVisitDoctorName()).append("\n");
        sb.append("NewUpFlg:").append(getNewUpFlg()).append("\n");

        return sb.toString();
    }

}
