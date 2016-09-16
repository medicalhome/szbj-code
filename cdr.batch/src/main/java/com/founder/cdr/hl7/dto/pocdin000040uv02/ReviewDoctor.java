package com.founder.cdr.hl7.dto.pocdin000040uv02;

import org.hibernate.validator.constraints.NotEmpty;

public class ReviewDoctor
{
    /**
     * 审核日期
     */
  //  @NotEmpty(message = "{POCDIN000040UV02.reviewDate}")
    private String reviewDate;

    /**
     * 审核医生代码
     */
    private String reviewDoctor;

    /**
     * 审核医生姓名
     */
    // $Author:tong_meng
    // $Date:2012/10/16 12:57
    // $[BUG]0010423 DELETE BEGIN
    /* @NotEmpty(message = "{POCDIN000040UV02.reviewDoctorName}") */
    // $[BUG]0010423 DELETE END
    private String reviewDoctorName;

    public String getReviewDate()
    {
        return reviewDate;
    }

    public String getReviewDoctor()
    {
        return reviewDoctor;
    }

    public String getReviewDoctorName()
    {
        return reviewDoctorName;
    }

}
