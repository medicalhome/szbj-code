package com.founder.cdr.hl7.dto;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

public class VerifyDispensing extends BaseDto
{
    /**
     * 处方信息
     */
    @NotEmpty(message = "{VerifyDispensing.prescription}")
    private List<Prescription> prescription;
    /**
     * 域ID
     */
    @NotEmpty(message = "{VerifyDispensing.patientDomain}")
    private String patientDomain;
    /**
     * 患者ID
     */
    @NotEmpty(message = "{VerifyDispensing.patientLid}")
    private String patientLid;
    /**
     * 就诊号
     */
    private String outPatientNo;
    /**
     * 患者姓名
     */
    private String patientName;
    /**
     * 就诊次数
     */
    @NotEmpty(message = "{VerifyDispensing.visitTimes}")
    private String visitTimes;

    public List<Prescription> getPrescription()
    {
        return prescription;
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

    public String getPatientName()
    {
        return patientName;
    }

    public String getVisitTimes()
    {
        return visitTimes;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("VerifyDispensing [prescription=");
        builder.append(prescription);
        builder.append(", patientDomain=");
        builder.append(patientDomain);
        builder.append(", patientLid=");
        builder.append(patientLid);
        builder.append(", visitTimes=");
        builder.append(visitTimes);
        builder.append("]");
        return builder.toString();
    }
    
    
}
