package com.founder.cdr.dto.patient;

import java.util.Date;
import java.util.List;

public class PatientListSearchPra
{
    // 就诊类型
    private List visitTypes;

    // 患者域
    private List patientDomains;

    // 就诊号
    private String visitNo;

    // 门诊号
    private String outpatientNo;

    // 住院号
    private String inpatientNo;

    // 影像号
    private String imagingNo;

    // 患者姓名
    private String patientName;

    // 性别代码
    private String genderCode;

    // 就诊开始日期
    private String visitStartDate;

    // 就诊结束日期起
    private String visitEndDate;

    // 就诊状态
    private List visitStatus;

    // 患者域条件(患者检索列表使用)
    private String patientDomainStr;

    // 患者域条件(患者检索列表使用)
    private String patientEId;
    
    // 就诊类型(患者检索列表使用)
    private String visitTypeStr;

    // 员工ID
    private String doctorId;

    // 科室ID
    private List deptIds;

    // 就诊状态出院
    private String visitStatusDischarge;

    // 就诊类型门诊
    private String visitTypeOutpatient;

    // 就诊类型住院
    private String visitTypeInpatient;

    // 诊断疾病名称
    private String diseaseName;

    // 患者就诊年龄段开始年龄
    private String baseAge;

    // $Author:wu_jianfeng
    // $Date : 2013/04/15 14:10
    // $[BUG]0015064 ADD BEGIN

    // 就诊状态在院
    private String visitStatusInHospital;

    // $[BUG]0015064 ADD END

    // $Author:jin_peng
    // $Date : 2013/11/07 13:41
    // $[BUG]0039035 ADD BEGIN
    private String dischargeBedNo;
    
    private String deptName;

    // $[BUG]0039035 ADD END
    
    private String deptId;
    
    // $Author:chang_xuewen
    // $Date : 2013/12/31 16:10
    // $[BUG]0040993 ADD BEGIN
    private String staff;
    // $[BUG]0040993 ADD END
	
	// $Author:chang_xuewen
 	// $Date : 2013/12/18 14:10
 	// $[BUG]0040770 ADD BEGIN
    private String orgCode;
    // $[BUG]0040770 ADD END
    
    //预约日期
    private String  requestStartDate;
    
    private String requestEndDate;
    //普通号患者
    private String normalPatient;
        
    // 身份证号
    private String idNumber;
	
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
    
    public String getStaff() {
		return staff;
	}

	public void setStaff(String staff) {
		this.staff = staff;
	}
    
    public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	private String dischargeWardId;
    
    public String getDischargeWardId() {
		return dischargeWardId;
	}

	public void setDischargeWardId(String dischargeWardId) {
		this.dischargeWardId = dischargeWardId;
	}

	// 出院病区名称
    private String dischargeWardName;
    
    // 住院域
    private List<String> inpatientDomains;
    
    // 门诊域
    private List<String> outpatientDomains;

    public String getPatientDomainStr()
    {
        return patientDomainStr;
    }

    public void setPatientDomainStr(String patientDomainStr)
    {
        this.patientDomainStr = patientDomainStr;
    }

    public List getPatientDomains()
    {
        return patientDomains;
    }

    public void setPatientDomains(List patientDomains)
    {
        this.patientDomains = patientDomains;
    }

    public String getPatientEId() {
		return patientEId;
	}

	public void setPatientEId(String patientEId) {
		this.patientEId = patientEId;
	}

	public String getVisitNo()
    {
        return visitNo;
    }

    public void setVisitNo(String visitNo)
    {
        this.visitNo = visitNo;
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

    public String getImagingNo()
    {
        return imagingNo;
    }

    public void setImagingNo(String imagingNo)
    {
        this.imagingNo = imagingNo;
    }

    public String getPatientName()
    {
        return patientName;
    }

    public void setPatientName(String patientName)
    {
        this.patientName = patientName;
    }

    public String getGenderCode()
    {
        return genderCode;
    }

    public void setGenderCode(String genderCode)
    {
        this.genderCode = genderCode;
    }

    public String getVisitStartDate()
    {
        return visitStartDate;
    }

    public void setVisitStartDate(String visitStartDate)
    {
        this.visitStartDate = visitStartDate;
    }

    public String getVisitEndDate()
    {
        return visitEndDate;
    }

    public void setVisitEndDate(String visitEndDate)
    {
        this.visitEndDate = visitEndDate;
    }

    public List getVisitTypes()
    {
        return visitTypes;
    }

    public void setVisitTypes(List visitTypes)
    {
        this.visitTypes = visitTypes;
    }

    public List getVisitStatus()
    {
        return visitStatus;
    }

    public void setVisitStatus(List visitStatus)
    {
        this.visitStatus = visitStatus;
    }

    public String getVisitTypeStr()
    {
        return visitTypeStr;
    }

    public void setVisitTypeStr(String visitTypeStr)
    {
        this.visitTypeStr = visitTypeStr;
    }

    public String getDoctorId()
    {
        return doctorId;
    }

    public void setDoctorId(String doctorId)
    {
        this.doctorId = doctorId;
    }

    public List getDeptIds()
    {
        return deptIds;
    }

    public void setDeptIds(List deptIds)
    {
        this.deptIds = deptIds;
    }

    public String getVisitStatusDischarge()
    {
        return visitStatusDischarge;
    }

    public void setVisitStatusDischarge(String visitStatusDischarge)
    {
        this.visitStatusDischarge = visitStatusDischarge;
    }

    public String getVisitTypeOutpatient()
    {
        return visitTypeOutpatient;
    }

    public void setVisitTypeOutpatient(String visitTypeOutpatient)
    {
        this.visitTypeOutpatient = visitTypeOutpatient;
    }

    public String getVisitTypeInpatient()
    {
        return visitTypeInpatient;
    }

    public void setVisitTypeInpatient(String visitTypeInpatient)
    {
        this.visitTypeInpatient = visitTypeInpatient;
    }

    public String getDiseaseName()
    {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName)
    {
        this.diseaseName = diseaseName;
    }

    public String getBaseAge()
    {
        return baseAge;
    }

    public void setBaseAge(String baseAge)
    {
        this.baseAge = baseAge;
    }

    public String getVisitStatusInHospital()
    {
        return visitStatusInHospital;
    }

    public void setVisitStatusInHospital(String visitStatusInHospital)
    {
        this.visitStatusInHospital = visitStatusInHospital;
    }

    public String getDischargeBedNo()
    {
        return dischargeBedNo;
    }

    public void setDischargeBedNo(String dischargeBedNo)
    {
        this.dischargeBedNo = dischargeBedNo;
    }

    public String getDeptName()
    {
        return deptName;
    }

    public void setDeptName(String deptName)
    {
        this.deptName = deptName;
    }

    public String getDischargeWardName()
    {
        return dischargeWardName;
    }

    public void setDischargeWardName(String dischargeWardName)
    {
        this.dischargeWardName = dischargeWardName;
    }

    public List<String> getInpatientDomains()
    {
        return inpatientDomains;
    }

    public void setInpatientDomains(List<String> inpatientDomains)
    {
        this.inpatientDomains = inpatientDomains;
    }

    public List<String> getOutpatientDomains()
    {
        return outpatientDomains;
    }

    public void setOutpatientDomains(List<String> outpatientDomains)
    {
        this.outpatientDomains = outpatientDomains;
    }

	public String getRequestStartDate() {
		return requestStartDate;
	}

	public void setRequestStartDate(String requestStartDate) {
		this.requestStartDate = requestStartDate;
	}

	public String getRequestEndDate() {
		return requestEndDate;
	}

	public void setRequestEndDate(String requestEndDate) {
		this.requestEndDate = requestEndDate;
	}

	public String getNormalPatient() {
		return normalPatient;
	}

	public void setNormalPatient(String normalPatient) {
		this.normalPatient = normalPatient;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

}
