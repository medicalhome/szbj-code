package com.founder.cdr.hl7.dto.porxin010370uv02;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.founder.cdr.hl7.dto.BaseDto;
import com.founder.cdr.hl7.dto.Prescription;
import com.founder.cdr.hl7.dto.VerifyDispensing;
import com.founder.cdr.util.DateUtils;

public class PORXIN010370UV02 extends BaseDto
{
    /**
     * 新增标志
     */
    @NotEmpty(message = "{PORXIN010370UV02.newUpFlag}")
    private String newUpFlag;
    
    /**
     * 域代码
     */
    @NotEmpty(message = "{PORXIN010370UV02.patientDomain}")
    private String patientDomain;
    
    /**
     * 患者Lid
     */
    @NotEmpty(message = "{PORXIN010370UV02.patientLid}")
    private String patientLid;
    // $Author :chang_xuewen
    // $Date : 2014/1/22 10:30$
    // [BUG]0042086 MODIFY BEGIN
    /**
     * 医疗机构代码
     */
    /*@NotEmpty(message = "{PORXIN010370UV02.orgCode}")*/
    private String orgCode;

	/**
     * 医疗机构名称
     */
    /*@NotEmpty(message = "{PORXIN010370UV02.orgName}")*/
    private String orgName;
    public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
    // [BUG]0042086 MODIFY END
    /**
     * 就诊次数
     */
    //@NotEmpty(message = "{PORXIN010370UV02.visitTimes}")
    private String visitTimes;
    
    // Author: yu_yzh
	// Date: 2016/3/1 17:45
	// [BUG] 0064315
	// MODIFY BEGIN
    /**
     * 就诊类别
     */
//    @NotEmpty(message="{PORXIN010370UV02.visitType}")
    private String visitType;
    
    /**
     * 就诊流水号
     */
//    @NotEmpty(message="{PORXIN010370UV02.visitOrdNo}")
    private String visitOrdNo;
    // [BUG] 0064315
	// MODIFY END
    /**
     * 处方或摆药单
     */
    @NotEmpty(message = "{PORXIN010370UV02.prescription}")
    private List<Prescription> prescription;

/*    public String getVisitType() {
		return visitType;
	}

	public void setVisitType(String visitType) {
		this.visitType = visitType;
	}

	public String getVisitOrdNo() {
		return visitOrdNo;
	}

	public void setVisitOrdNo(String visitOrdNo) {
		this.visitOrdNo = visitOrdNo;
	}*/

	public String getNewUpFlag()
    {
        return newUpFlag;
    }
    
    public String getPatientDomain()
    {
        return patientDomain;
    }

    public void setPatientDomain(String patientDomain)
    {
        this.patientDomain = patientDomain;
    }

    public String getPatientLid()
    {
        return patientLid;
    }

    public String getOrgCode()
    {
        return orgCode;
    }

    public String getOrgName()
    {
        return orgName;
    }

    public void setPatientLid(String patientLid)
    {
        this.patientLid = patientLid;
    }

    public String getVisitTimes()
    {
        return visitTimes;
    }

    public void setVisitTimes(String visitTimes)
    {
        this.visitTimes = visitTimes;
    }

    public List<Prescription> getPrescription()
    {
        return prescription;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("MessageId=");
        builder.append(getMessage().getId() + ",");
        builder.append("Datetime=");
        builder.append(DateUtils.dateToString(getMessage().getDatetime(), null)
            + ",");
        builder.append("PORXIN010370UV02 [newUpFlag=");
        builder.append(newUpFlag);
        builder.append(", patientDomain=");
        builder.append(patientDomain);
        builder.append(", patientLid=");
        builder.append(patientLid);
        builder.append(", visitTimes=");
        builder.append(visitTimes);
        builder.append(", prescription=");
        builder.append(prescription);
        builder.append("]");
        return builder.toString();
    }

	public String getVisitType() {
		return visitType;
	}

	public void setVisitType(String visitType) {
		this.visitType = visitType;
	}

	public String getVisitOrdNo() {
		return visitOrdNo;
	}

	public void setVisitOrdNo(String visitOrdNo) {
		this.visitOrdNo = visitOrdNo;
	}

}
