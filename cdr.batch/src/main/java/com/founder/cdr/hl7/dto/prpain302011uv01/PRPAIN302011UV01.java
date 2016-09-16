package com.founder.cdr.hl7.dto.prpain302011uv01;

import org.hibernate.validator.constraints.NotEmpty;

import com.founder.cdr.hl7.dto.BaseDto;
import com.founder.cdr.util.DateUtils;

public class PRPAIN302011UV01 extends BaseDto
{
    /**
     * 新增更新标志
     */
    @NotEmpty(message = "{PRPAIN302011UV01.newUpFlg}")
    private String newUpFlg;

    /**
     * 住院次数
     */
//    @NotEmpty(message = "{PRPAIN302011UV01.visitTimes}")
    private String visitTimes;

    /**
     * 转科转床区分
     */
    @NotEmpty(message = "{PRPAIN302011UV01.orderTypeCode}")
    private String orderTypeCode;
    
    /**
     * 就诊类型
     */
    @NotEmpty(message = "{PRPAIN302011UV01.visitTypeCode}")
    private String visitTypeCode;
    
    /**
     * 就诊流水号
     */
    @NotEmpty(message = "{PRPAIN302011UV01.visitOrdNo}")
    private String visitOrdNo;
    
    /**
     * 现房间编码
     */
//    @NotEmpty(message = "{PRPAIN302011UV01.toRoomCode}")
    private String toRoomCode;
    
    /**
     * 现房间号
     */
//    @NotEmpty(message = "{PRPAIN302011UV01.toRoomNo}")
    private String toRoomNo;
    
    /**
     * 原房间编码
     */
//    @NotEmpty(message = "{PRPAIN302011UV01.fromRoomCode}")
    private String fromRoomCode;
    
    /**
     * 原房间号
     */
//    @NotEmpty(message = "{PRPAIN302011UV01.fromRoomNo}")
    private String fromRoomNo;
    /**
     * 医嘱号
     */
    private String orderLid;
    // $Author :chang_xuewen
    // $Date : 2014/1/22 10:30$
    // [BUG]0042086 MODIFY BEGIN
    /**
     * 医疗机构代码
     */
    /*@NotEmpty(message = "{PRPAIN302011UV01.orgCode}")*/
    private String orgCode;
    
	/**
     * 医疗机构名称
     */
    /*@NotEmpty(message = "{PRPAIN302011UV01.orgName}")*/
    private String orgName;
    public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
    // [BUG]0042086 MODIFY BEGIN
    /**
     * 住院标志
     */
    private String hospitalFlag;

    /**
     * 域ID
     */
    @NotEmpty(message = "{PRPAIN302011UV01.patientDomain}")
    private String patientDomain;

    /**
     * 门诊系统患者ID
     */
    @NotEmpty(message = "{PRPAIN302011UV01.patientLid}")
    private String patientLid;

    /**
     * 转入时间
     */
    @NotEmpty(message = "{PRPAIN302011UV01.toTime}")
    private String toTime;

    /**
     * 现床位号
     */
    @NotEmpty(message = "{PRPAIN302011UV01.toBed}")
    private String toBed;

    /**
     * 现科室ID
     */
    @NotEmpty(message = "{PRPAIN302011UV01.toDept}")
    private String toDept;

    /**
     * 现科室
     */
//    @NotEmpty(message = "{PRPAIN302011UV01.toDeptName}")
    private String toDeptName;

    /**
     * 现病区编码
     */
    @NotEmpty(message = "{PRPAIN302011UV01.toWards}")
    private String toWards;

    /**
     * 现病区编码
     */
//    @NotEmpty(message = "{PRPAIN302011UV01.toWardsName}")
    private String toWardsName;

    /**
     * 住院时间
     */
    private String visitDate;

    /**
     * 原床位号
     */
//    @NotEmpty(message = "{PRPAIN302011UV01.fromBed}")
    private String fromBed;

    
	/**
     * 原科室ID
     */
//    @NotEmpty(message = "{PRPAIN302011UV01.fromDept}")
    private String fromDept;

    /**
     * 原科室
     */
//    @NotEmpty(message = "{PRPAIN302011UV01.fromDeptName}")
    private String fromDeptName;

    /**
     * 原病区编码
     */
//    @NotEmpty(message = "{PRPAIN302011UV01.fromWards}")
    private String fromWards;

    /**
     * 原病区名称
     */
//    @NotEmpty(message = "{PRPAIN302011UV01.fromWardsName}")
    private String fromWardsName;

    public String getNewUpFlg()
    {
        return newUpFlg;
    }
    
    public void setNewUpFlg(String newUpFlg)
    {
        this.newUpFlg = newUpFlg;
    }

    public String getVisitTimes()
    {
        return visitTimes;
    }

    public String getOrderLid()
    {
        return orderLid;
    }

    public String getOrgCode()
    {
        return orgCode;
    }

    public String getOrgName()
    {
        return orgName;
    }

    public String getHospitalFlag()
    {
        return hospitalFlag;
    }

    public String getPatientDomain()
    {
        return patientDomain;
    }

    public String getPatientLid()
    {
        return patientLid;
    }

    public String getToTime()
    {
        return toTime;
    }

    public String getToBed()
    {
        return toBed;
    }

    public String getToDept()
    {
        return toDept;
    }

    public String getToDeptName()
    {
        return toDeptName;
    }

    public String getToWards()
    {
        return toWards;
    }

    public String getVisitDate()
    {
        return visitDate;
    }

    public String getFromBed()
    {
        return fromBed;
    }

    public String getFromDept()
    {
        return fromDept;
    }

    public String getFromDeptName()
    {
        return fromDeptName;
    }

    public String getFromWards()
    {
        return fromWards;
    }
    public void setFromBed(String fromBed) {
		this.fromBed = fromBed;
	}

	public void setFromDept(String fromDept) {
		this.fromDept = fromDept;
	}

	public void setFromWards(String fromWards) {
		this.fromWards = fromWards;
	}
    public String getToWardsName()
    {
        return toWardsName;
    }

    public String getFromWardsName()
    {
        return fromWardsName;
    }

    public String getVisitTypeCode() {
		return visitTypeCode;
	}

	public String getVisitOrdNo() {
		return visitOrdNo;
	}

	public String getToRoomCode() {
		return toRoomCode;
	}

	public String getToRoomNo() {
		return toRoomNo;
	}

	public String getFromRoomCode() {
		return fromRoomCode;
	}

	public String getFromRoomNo() {
		return fromRoomNo;
	}
	public void setFromRoomNo(String fromRoomNo) {
		this.fromRoomNo = fromRoomNo;
	}
	
	public String getOrderTypeCode() {
		return orderTypeCode;
	}

	@Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("MessageId=");
        builder.append(getMessage().getId()+",");
        builder.append("Datetime=");
        builder.append(DateUtils.dateToString(getMessage().getDatetime(),null) + ",");
        builder.append("PRPAIN302011UV01 [newUpFlg=");
        builder.append(newUpFlg);
        builder.append(", visitTimes=");
        builder.append(visitTimes);
        builder.append(", orderLid=");
        builder.append(orderLid);
        builder.append(", patientDomain=");
        builder.append(patientDomain);
        builder.append(", patientLid=");
        builder.append(patientLid);
        builder.append(", toTime=");
        builder.append(toTime);
        builder.append(", orderLid=");
        builder.append(orderLid);
        builder.append(", visitTypeCode=");
        builder.append(visitTypeCode);
        builder.append(", visitOrdNo=");
        builder.append(visitOrdNo);
        builder.append("]");
        return builder.toString();
    }
    
}
