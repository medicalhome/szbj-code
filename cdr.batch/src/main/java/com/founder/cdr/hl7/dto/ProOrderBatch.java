package com.founder.cdr.hl7.dto;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

public class ProOrderBatch extends BaseDto
{
    /**
     * 申请单号
     */
    private String requestNo;
    /**
     * 医嘱号
     */
    private String orderLid;
    /**
     * 手术名称
     */
    private String operationName;
    /**
     * 手术名称代码
     */
    private String operationNameCode;
    /**
     * 手术等级代码
     */
    private String operationLevel;
    /**
     * 手术等级名称
     */
    private String operationLevelName;
    /**
     * 手术性质代码
     */
    private String operationKind;
    /**
     * 手术性质名称
     */
    private String operationKindName;
    /**
     * 麻醉编码
     */
    private String anesthesiaCode;
    /**
     * 麻醉名称
     */
    private String anesthesiaName;
    /**
     * 描述
     */
    private String descript;
    /**
     * 申请日期
     */
    private String requestDate;
    /**
     * 患者本地ID
     */
    @NotEmpty(message = "{ProOrderBatch.patientLid}")
    private String patientLid;
    /**
     * 域ID
     */
    @NotEmpty(message = "{ProOrderBatch.domainID}")
    private String domainID;
    /**
     * 患者姓名
     */
    private String patientName;
    /**
     * 性别代码
     */
    private String genderCode;
    /**
     * 出生日期
     */
    private String birthday;
    /**
     * 年龄
     */
    private String age;
    /**
     * 申请人编号
     */
    private String requestPersonId;
    /**
     * 申请人姓名
     */
    private String requestPersonName;
    /**
     * 申请部门名称
     */
    private String requestDeptName;
    /**
     * 申请部门编号
     */
    private String requestDeptNo;
    /**
     * 执行科室编号
     */
    private String executeDeptNo;
    /**
     * 执行科室名称
     */
    private String executeDeptName;
    /**
     * 建议时间
     */
    private String surgicalTime;
    /**
     * 预计手术用时
     */
    private String userTime;
    
    /**
     * 手术台
     */
    private String operationTable;
    /**
     * 台次
     */
    private String tableSeq;
    /**
     * 是否计划
     */
    private String planFlag;
    /**
     * 非计划原因
     */
    private String noPlanReason;
    /**
     * 确认时间
     */
    private String configTime;
    
    /**
     * 病区编码
     */
    private String wardsId;
    
    // $Author :tong_meng
    // $Date : 2012/9/26 9:30$
    // [BUG]0009897 ADD BEGIN
    /**
     * 病区名称
     */
    private String wardsName;
    // [BUG]0009897 ADD END
    /**
     * 房间号
     */
    private String roomNo;
    /**
     * 就诊
     */
    private List<MedicalVisit> medicaVisit;
    /**
     * 床号
     */
    private String bedNo;
    /**
     * 注意事项
     */
    private String noticeItems;
    /**
     * 审核日期
     */
    private String verifyTime;
    /**
     * 审核人编号
     */
    private String verifyPersonID;
    /**
     * 审核人姓名
     */
    private String verifyPersonName;
    /**
     * 是否皮试
     */
    private String skinTestFlag;
    /**
     * 是否加急
     */
    private String urgency;
    /**
     * 是否药观
     */
    private String medViewFlag;
    // $Author :chang_xuewen
    // $Date : 2014/1/22 10:30$
    // [BUG]0042086 MODIFY BEGIN   
    /**
     * 医疗机构编码
     */                           
    /*@NotEmpty(message = "{message.organizationCode}")*/
    private String organizationCode;

	/**
     * 医疗机构名称
     */
    /*@NotEmpty(message = "{message.organizationName}")*/
    private String organizationName;
    /**
     * 手术医师编码
     */
    private String operatorCode;
    /**
     * 手术医师名称
     */
    private String operatorName;
    /**
     * 指导者编码
     */
    private String directorCode;
    /**
     * 指导者名称
     */
    private String directorName;
    /**
     * 助手1编码
     */
    private String assistant1Code;
    /**
     * 助手1名称
     */
    private String assistant1Name;
    /**
     * 助手2编码
     */
    private String assistant2Code;
    /**
     * 助手1名称
     */
    private String assistant2Name;
    /**
     * 助手3编码
     */
    private String assistant3Code;
    /**
     * 助手3名称
     */
    private String assistant3Name;
    /**
     * 洗手护士编码
     */
    private String scrubNurseCode;
    /**
     * 洗手护士名称
     */
    private String scrubNurseName;
    /**
     * 巡回护士编码
     */
    private String circulatingNurseCode;
    /**
     * 巡回护士名称
     */
    private String circulatingNurseName;
    /**
     * 手术费用
     */
    private BigDecimal charge;
    
    public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
    // [BUG]0042086 MODIFY END   	
    public String getOrderLid()
    {
        return orderLid;
    }

    public String getRequestNo()
    {
        return requestNo;
    }

    public String getOperationName()
    {
        return operationName;
    }

    public String getOperationNameCode()
    {
        return operationNameCode;
    }

    public String getOperationLevel()
    {
        return operationLevel;
    }

    public String getOperationLevelName()
    {
        return operationLevelName;
    }

    public String getAnesthesiaCode()
    {
        return anesthesiaCode;
    }

    public String getAnesthesiaName()
    {
        return anesthesiaName;
    }

    public String getDescript()
    {
        return descript;
    }

    public String getRequestDate()
    {
        return requestDate;
    }

    public String getPatientLid()
    {
        return patientLid;
    }

    public String getDomainID()
    {
        return domainID;
    }

    public String getPatientName()
    {
        return patientName;
    }

    public String getGenderCode()
    {
        return genderCode;
    }

    public String getBirthday()
    {
        return birthday;
    }

    public String getAge()
    {
        return age;
    }

    public String getRequestPersonId()
    {
        return requestPersonId;
    }

    public String getRequestPersonName()
    {
        return requestPersonName;
    }

    public String getRequestDeptName()
    {
        return requestDeptName;
    }

    public String getRequestDeptNo()
    {
        return requestDeptNo;
    }

    public String getExecuteDeptNo()
    {
        return executeDeptNo;
    }

    public String getExecuteDeptName()
    {
        return executeDeptName;
    }

    public String getSurgicalTime()
    {
        return surgicalTime;
    }

    public String getWardsId()
    {
        return wardsId;
    }

    public String getWardsName()
    {
        return wardsName;
    }

    public String getRoomNo()
    {
        return roomNo;
    }

    public List<MedicalVisit> getMedicaVisit()
    {
        return medicaVisit;
    }

    public String getBedNo()
    {
        return bedNo;
    }

    public String getNoticeItems()
    {
        return noticeItems;
    }

    public String getVerifyTime()
    {
        return verifyTime;
    }

    public String getVerifyPersonID()
    {
        return verifyPersonID;
    }

    public String getVerifyPersonName()
    {
        return verifyPersonName;
    }

    public String getSkinTestFlag()
    {
        return skinTestFlag;
    }

    public String getUrgency()
    {
        return urgency;
    }

    public String getMedViewFlag()
    {
        return medViewFlag;
    }
    
    public String getOrganizationCode() {
		return organizationCode;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public String getOperationKind() {
		return operationKind;
	}

	public String getOperationKindName() {
		return operationKindName;
	}

	public String getUserTime() {
		return userTime;
	}

	public String getOperationTable() {
		return operationTable;
	}

	public String getTableSeq() {
		return tableSeq;
	}

	public String getPlanFlag() {
		return planFlag;
	}

	public String getConfigTime() {
		return configTime;
	}

	public String getOperatorCode() {
		return operatorCode;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public String getDirectorCode() {
		return directorCode;
	}

	public String getDirectorName() {
		return directorName;
	}

	public String getAssistant1Code() {
		return assistant1Code;
	}

	public String getAssistant1Name() {
		return assistant1Name;
	}

	public String getAssistant2Code() {
		return assistant2Code;
	}

	public String getAssistant2Name() {
		return assistant2Name;
	}

	public String getAssistant3Code() {
		return assistant3Code;
	}

	public String getAssistant3Name() {
		return assistant3Name;
	}

	public String getScrubNurseCode() {
		return scrubNurseCode;
	}

	public String getScrubNurseName() {
		return scrubNurseName;
	}

	public String getCirculatingNurseCode() {
		return circulatingNurseCode;
	}

	public String getCirculatingNurseName() {
		return circulatingNurseName;
	}

	public String getNoPlanReason() {
		return noPlanReason;
	}

	public BigDecimal getCharge() {
		return charge;
	}

	public void setCharge(BigDecimal charge) {
		this.charge = charge;
	}
	
	@Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("ProOrderBatch [requestNo=");
        builder.append(requestNo);
        builder.append(", orderLid=");
        builder.append(orderLid);
        builder.append(", organizationCode=");
        builder.append(organizationCode);
        builder.append(", patientLid=");
        builder.append(patientLid);
        builder.append(", domainID=");
        builder.append(domainID);
        builder.append(", medicaVisit=");
        builder.append(medicaVisit);
        builder.append("]");
        return builder.toString();
    }
}
