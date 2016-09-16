package com.founder.cdr.dto;

public class PatientCrossIndexDto
{
    private String patientEid;

    private String patientDomain;

    private String outpatientNo;

    private String inpatientNo;
    // $Author:chang_xuewen
	// $Date : 2013/12/18 14:10
	// $[BUG]0040770 ADD BEGIN
    private String orgCode;
    // $[BUG]0040770 ADD END
    public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getPatientEid()
    {
        return patientEid;
    }

    public void setPatientEid(String patientEid)
    {
        this.patientEid = patientEid;
    }

    public String getPatientDomain()
    {
        return patientDomain;
    }

    public void setPatientDomain(String patientDomain)
    {
        this.patientDomain = patientDomain;
    }

    public String getOutpatientNo()
    {
        return outpatientNo;
    }

    public void setOutpatientNo(String outpatientNo)
    {
        this.outpatientNo = outpatientNo;
    }

    public String getInpatientNo()
    {
        return inpatientNo;
    }

    public void setInpatientNo(String inpatientNo)
    {
        this.inpatientNo = inpatientNo;
    }
}
