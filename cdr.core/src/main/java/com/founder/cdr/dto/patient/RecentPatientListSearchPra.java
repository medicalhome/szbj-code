package com.founder.cdr.dto.patient;

import java.util.List;

public class RecentPatientListSearchPra
{
    // 就诊类型
    private List visitTypes;

    // 患者门诊域
    private List outpatientDomains;

    // 患者 住院域
    private List inpatientDomains;

    // 开始日期
    private String beginDateText;

    // 结束日期
    private String endDateText;

    // $Author:wu_jianfeng
    // $Date:2012/9/4 10:42
    // $[BUG]0009345 ADD BEGIN
    // 患者列表中住院，最近，检索TAB页都只显示登录用户所在科室的患者
    // 科室ID
    private List deptIds;

    // $[BUG]0009345 ADD END

    // $Author:wu_jianfeng
    // $Date:2012/9/18 10:42
    // $[BUG]0009752 ADD BEGIN
    // 访问控制
    // 医生ID
    private String doctorId;

    // $[BUG]0009752 ADD END
    
    private String patientName;
    
    private String outpatientNo;
    
    private String inpatientNo;
    
    private String visitNoFlag;
    
    private String visitNo;
    
    private String deptName;
    
    private String visitStatusCodeDischarge;
    
    private String deptId;
    
    private String wardId;
    // $Author:chang_xuewen
	// $Date : 2013/12/18 14:10
	// $[BUG]0040770 ADD BEGIN
    private String orgCode;
    private String patientEId;
    // $[BUG]0040770 ADD END
    public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getWardId() {
		return wardId;
	}

	public void setWardId(String wardId) {
		this.wardId = wardId;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public List getVisitTypes()
    {
        return visitTypes;
    }

    public void setVisitTypes(List visitTypes)
    {
        this.visitTypes = visitTypes;
    }

    public List getOutpatientDomains()
    {
        return outpatientDomains;
    }

    public void setOutpatientDomains(List outpatientDomains)
    {
        this.outpatientDomains = outpatientDomains;
    }

    public List getInpatientDomains()
    {
        return inpatientDomains;
    }

    public void setInpatientDomains(List inpatientDomains)
    {
        this.inpatientDomains = inpatientDomains;
    }

    public String getBeginDateText()
    {
        return beginDateText;
    }

    public void setBeginDateText(String beginDateText)
    {
        this.beginDateText = beginDateText;
    }

    public String getEndDateText()
    {
        return endDateText;
    }

    public void setEndDateText(String endDateText)
    {
        this.endDateText = endDateText;
    }

    // $Author:wu_jianfeng
    // $Date:2012/9/4 10:42
    // $[BUG]0009345 ADD BEGIN
    // 患者列表中住院，最近，检索TAB页都只显示登录用户所在科室的患者
    public List getDeptIds()
    {
        return deptIds;
    }

    public void setDeptIds(List deptIds)
    {
        this.deptIds = deptIds;
    }

    // $[BUG]0009345 ADD END

    // $Author:wu_jianfeng
    // $Date:2012/9/18 10:42
    // $[BUG]0009752 ADD BEGIN
    // 访问控制
    public String getDoctorId()
    {
        return doctorId;
    }

    public void setDoctorId(String doctorId)
    {
        this.doctorId = doctorId;
    }
    // $[BUG]0009752 ADD END

    public String getPatientName()
    {
        return patientName;
    }

    public void setPatientName(String patientName)
    {
        this.patientName = patientName;
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

    public String getVisitNoFlag()
    {
        return visitNoFlag;
    }

    public void setVisitNoFlag(String visitNoFlag)
    {
        this.visitNoFlag = visitNoFlag;
    }

    public String getVisitNo()
    {
        return visitNo;
    }

    public void setVisitNo(String visitNo)
    {
        this.visitNo = visitNo;
    }

    public String getDeptName()
    {
        return deptName;
    }

    public void setDeptName(String deptName)
    {
        this.deptName = deptName;
    }

    public String getVisitStatusCodeDischarge()
    {
        return visitStatusCodeDischarge;
    }

    public void setVisitStatusCodeDischarge(String visitStatusCodeDischarge)
    {
        this.visitStatusCodeDischarge = visitStatusCodeDischarge;
    }

	public String getPatientEId() {
		return patientEId;
	}

	public void setPatientEId(String patientEId) {
		this.patientEId = patientEId;
	}

	
    
}
