package com.yly.cdr.hl7.dto.pocdin000040uv02;

import org.hibernate.validator.constraints.NotEmpty;

public class ExaminingDoctor
{
    /**
     * 检查日期
     */
    @NotEmpty(message = "{ExaminingDoctor.examinationDate}")
    private String examinationDate;

    /**
     * 检查医生代码
     */
    private String examiningDoctor;

    /**
     * 检查医生姓名
     */
    // $Author:tong_meng
    // $Date:2013/8/22
    // [BUG]0036486 MODIFY BEGIN
    /*@NotEmpty(message = "{ExaminingDoctor.examiningDoctorName}")*/
    // [BUG]0036486 MODIFY END
    private String examiningDoctorName;

    /**
     * 检查科室代码
     */
    private String examinationDept;

    /**
     * 检查科室
     */
    private String examinationDeptName;

    public String getExaminationDate()
    {
        return examinationDate;
    }

    public String getExaminingDoctor()
    {
        return examiningDoctor;
    }

    public String getExaminingDoctorName()
    {
        return examiningDoctorName;
    }

    public String getExaminationDept()
    {
        return examinationDept;
    }

    public String getExaminationDeptName()
    {
        return examinationDeptName;
    }

}
