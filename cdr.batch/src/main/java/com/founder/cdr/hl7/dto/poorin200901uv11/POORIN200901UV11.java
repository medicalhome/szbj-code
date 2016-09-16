package com.founder.cdr.hl7.dto.poorin200901uv11;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.founder.cdr.hl7.dto.BaseDto;
import com.founder.cdr.hl7.dto.Orders;

public class POORIN200901UV11 extends BaseDto
{
    /**
     * 新增更新标志
     */
    @NotEmpty(message = "{POORIN200901UV11.newUpFlag}")
    private String newUpFlag;

    /**
     * 域ID
     */
    @NotEmpty(message = "{POORIN200901UV11.patientDomain}")
    private String patientDomain;

    /**
     * 门诊系统患者ID(patient_id)
     */
    @NotEmpty(message = "{POORIN200901UV11.patientLid}")
    private String patientLid;

    /**
     * 患者姓名
     */
    private String patientName;

    /**
     * 性别代码
     */
    private String genderCode;

    /**
     * 医嘱信息
     */
    @NotEmpty(message = "{POORIN200901UV11.orderInfo}")
    private List<Orders> orderInfo;

    /**
     * 就诊次数
     */
    @NotEmpty(message = "{POORIN200901UV11.visitTimes}")
    private String visitTimes;

    public String getNewUpFlag()
    {
        return newUpFlag;
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

    public String getGenderCode()
    {
        return genderCode;
    }

    public List<Orders> getOrderInfo()
    {
        return orderInfo;
    }

    public String getVisitTimes()
    {
        return visitTimes;
    }
}
