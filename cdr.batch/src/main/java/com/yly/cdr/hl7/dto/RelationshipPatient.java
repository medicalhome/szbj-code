package com.yly.cdr.hl7.dto;

import org.hibernate.validator.constraints.NotEmpty;


public class RelationshipPatient extends BaseDto
{
    /**
     * 时间戳
     */
    private String timestamp;
    /**
     * 患者EMPI标识
     */
    private String patientEid;
    /**
     * 住院号
     */
    private String inpatientNo;
    /**
     * 门诊号
     */
    private String outpatientNo;
    /**
     * 域ID(门诊、住院)
     */
    @NotEmpty(message = "{RelationshipPatient.patientDomain}")
    private String patientDomain;
    /**
     * 患者ID
     */
    @NotEmpty(message = "{RelationshipPatient.patientLid}")
    private String patientLid;
    /**
     * 影像号
     */
    private String imageNo;
    /**
     * 影像号
     */
    private String insuranceCardNo;
    // $Author :chang_xuewen
    // $Date : 2014/1/22 10:30$
    // [BUG]0042086 MODIFY BEGIN
    /**
     * 医疗机构代码
     */
    /*@NotEmpty(message = "{RelationshipPatient.orgCode}")*/
    private String orgCode;
    
	/**
     * 医疗机构名称
     */
    /*@NotEmpty(message = "{RelationshipPatient.orgName}")*/
    private String orgName;

    public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
    
    // [BUG]0042086 MODIFY END
    public String getTimestamp()
    {
        return timestamp;
    }

    public String getPatientEid()
    {
        return patientEid;
    }

    public String getInpatientNo()
    {
        return inpatientNo;
    }

    public String getOutpatientNo()
    {
        return outpatientNo;
    }

    public String getPatientDomain()
    {
        return patientDomain;
    }

    public String getPatientLid()
    {
        return patientLid;
    }

    public String getImageNo()
    {
        return imageNo;
    }

    public String getInsuranceCardNo()
    {
        return insuranceCardNo;
    }

    public String getOrgCode()
    {
        return orgCode;
    }

    public String getOrgName()
    {
        return orgName;
    }
    
}
