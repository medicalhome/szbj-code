package com.founder.cdr.hl7.dto;


public class ConsultationDept extends BaseDto
{
    /**
     * 参与会诊科室ID
     */
    private String consultationDept;
    /**
     * 参与科室名称
     */
    private String consultationDeptName;
    /**
     * 会诊医生编码
     */
    private String consultationPerson;
    /**
     * 会诊医生姓名
     */
    private String consultationPersonName;

    /**
     * 参与会诊医院ID
     */
    private String consultationOrgCode;
    /**
     * 参与会诊医院名称
     */
    private String consultationOrgName;  
    public String getConsultationPerson()
    {
        return consultationPerson;
    }

    public String getConsultationPersonName()
    {
        return consultationPersonName;
    }

    public String getConsultationDept()
    {
        return consultationDept;
    }

    public String getConsultationDeptName()
    {
        return consultationDeptName;
    }

	public String getConsultationOrgCode() {
		return consultationOrgCode;
	}

	public void setConsultationOrgCode(String consultationOrgCode) {
		this.consultationOrgCode = consultationOrgCode;
	}

	public String getConsultationOrgName() {
		return consultationOrgName;
	}

	public void setConsultationOrgName(String consultationOrgName) {
		this.consultationOrgName = consultationOrgName;
	}
    
}
