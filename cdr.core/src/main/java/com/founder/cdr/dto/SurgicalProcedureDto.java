package com.founder.cdr.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * [FUN]V05-101手术列表
 * @version 1.0, 2012/03/15 
 * @author 张彬
 * @since 1.0
 * 
 */
public class SurgicalProcedureDto
{

    private BigDecimal procedureSn;

    private BigDecimal visitSn;

    private BigDecimal requestSn;

    private BigDecimal anesthesiaSn;

    private BigDecimal patientSn;

    private String operationName;// 手术名称

    private String operatingRoom;// 手术间

    private Date operationDate;// 手术日期

    private String surgicalDept;// 执行科室

    private String surgicalDeptName;// 执行科室

    private String difficulty;// 系数

    private Date startTime;// 开始时间

    private Date endTime;// 结束时间

    private String workload;// 工作量

    private String healingGrade;// 愈合等级

    private String healingGradeName;// 愈合等级

    private Integer participantSn;

    private String participantIdentity;// 参与者身份

    private String participantId;

    private String zhudao;// 主刀医生

    private String zhuy;// 助1

    private String zhue;// 助2

    private String mazui;// 麻醉医生

    private Integer orderSn;

    private String procedureLevel;// 手术等级
    
    private String procedureLevelName;// 手术等级名称
	
	private String orgCode; //医疗机构

    public BigDecimal getProcedureSn()
    {
        return procedureSn;
    }

    public void setProcedureSn(BigDecimal procedureSn)
    {
        this.procedureSn = procedureSn;
    }

    public BigDecimal getVisitSn()
    {
        return visitSn;
    }

    public void setVisitSn(BigDecimal visitSn)
    {
        this.visitSn = visitSn;
    }

    public BigDecimal getRequestSn()
    {
        return requestSn;
    }

    public void setRequestSn(BigDecimal requestSn)
    {
        this.requestSn = requestSn;
    }

    public BigDecimal getAnesthesiaSn()
    {
        return anesthesiaSn;
    }

    public void setAnesthesiaSn(BigDecimal anesthesiaSn)
    {
        this.anesthesiaSn = anesthesiaSn;
    }

    public BigDecimal getPatientSn()
    {
        return patientSn;
    }

    public void setPatientSn(BigDecimal patientSn)
    {
        this.patientSn = patientSn;
    }

    public String getOperationName()
    {
        return operationName;
    }

    public void setOperationName(String operationName)
    {
        this.operationName = operationName;
    }

    public String getOperatingRoom()
    {
        return operatingRoom;
    }

    public void setOperatingRoom(String operatingRoom)
    {
        this.operatingRoom = operatingRoom;
    }

    public Date getOperationDate()
    {
        return operationDate;
    }

    public void setOperationDate(Date operationDate)
    {
        this.operationDate = operationDate;
    }

    public String getSurgicalDept()
    {
        return surgicalDept;
    }

    public void setSurgicalDept(String surgicalDept)
    {
        this.surgicalDept = surgicalDept;
    }

    public String getDifficulty()
    {
        return difficulty;
    }

    public void setDifficulty(String difficulty)
    {
        this.difficulty = difficulty;
    }

    public Date getStartTime()
    {
        return startTime;
    }

    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }

    public Date getEndTime()
    {
        return endTime;
    }

    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }

    public String getWorkload()
    {
        return workload;
    }

    public void setWorkload(String workload)
    {
        this.workload = workload;
    }

    public String getHealingGrade()
    {
        return healingGrade;
    }

    public void setHealingGrade(String healingGrade)
    {
        this.healingGrade = healingGrade;
    }

    public Integer getParticipantSn()
    {
        return participantSn;
    }

    public void setParticipantSn(Integer participantSn)
    {
        this.participantSn = participantSn;
    }

    public String getParticipantIdentity()
    {
        return participantIdentity;
    }

    public void setParticipantIdentity(String participantIdentity)
    {
        this.participantIdentity = participantIdentity;
    }

    public String getParticipantId()
    {
        return participantId;
    }

    public void setParticipantId(String participantId)
    {
        this.participantId = participantId;
    }

    public String getZhudao()
    {
        return zhudao;
    }

    public void setZhudao(String zhudao)
    {
        this.zhudao = zhudao;
    }

    public String getZhuy()
    {
        return zhuy;
    }

    public void setZhuy(String zhuy)
    {
        this.zhuy = zhuy;
    }

    public String getZhue()
    {
        return zhue;
    }

    public void setZhue(String zhue)
    {
        this.zhue = zhue;
    }

    public String getMazui()
    {
        return mazui;
    }

    public void setMazui(String mazui)
    {
        this.mazui = mazui;
    }

    public Integer getOrderSn()
    {
        return orderSn;
    }

    public void setOrderSn(Integer orderSn)
    {
        this.orderSn = orderSn;
    }

    public String getProcedureLevel()
    {
        return procedureLevel;
    }

    public void setProcedureLevel(String procedureLevel)
    {
        this.procedureLevel = procedureLevel;
    }

    public String getSurgicalDeptName()
    {
        return surgicalDeptName;
    }

    public void setSurgicalDeptName(String surgicalDeptName)
    {
        this.surgicalDeptName = surgicalDeptName;
    }

    public String getHealingGradeName()
    {
        return healingGradeName;
    }

    public void setHealingGradeName(String healingGradeName)
    {
        this.healingGradeName = healingGradeName;
    }

    public String getProcedureLevelName()
    {
        return procedureLevelName;
    }

    public void setProcedureLevelName(String procedureLevelName)
    {
        this.procedureLevelName = procedureLevelName;
    }
	
	public String getOrgCode()
    {
        return orgCode;
    }

    public void setOrgCode(String orgCode)
    {
        this.orgCode = orgCode;
    }

}
