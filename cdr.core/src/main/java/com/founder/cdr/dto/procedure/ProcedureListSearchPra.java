package com.founder.cdr.dto.procedure;

import com.founder.cdr.dto.CommonParameters;

public class ProcedureListSearchPra extends CommonParameters
{
    // 手术名称
    private String operationName;

    // 手术开始日期
    private String operationDateFromStr;

    // 手术结束日期
    private String operationDateToStr;

    // 执行科室
    private String surgicalDept;

    // 主刀医生
    private String zhudao;

    // 麻醉医生
    private String mazui;

    // 手术等级
    private String procedureLevel;

    // 工作量
    private String workload;

    // 愈合等级
    private String healingGrade;
    
    // 医疗机构
    private String orgCode;

    public String getOperationName()
    {
        return operationName;
    }

    public void setOperationName(String operationName)
    {
        this.operationName = operationName;
    }

    public String getOperationDateFromStr()
    {
        return operationDateFromStr;
    }

    public void setOperationDateFromStr(String operationDateFromStr)
    {
        this.operationDateFromStr = operationDateFromStr;
    }

    public String getOperationDateToStr()
    {
        return operationDateToStr;
    }

    public void setOperationDateToStr(String operationDateToStr)
    {
        this.operationDateToStr = operationDateToStr;
    }

    public String getSurgicalDept()
    {
        return surgicalDept;
    }

    public void setSurgicalDept(String surgicalDept)
    {
        this.surgicalDept = surgicalDept;
    }

    public String getZhudao()
    {
        return zhudao;
    }

    public void setZhudao(String zhudao)
    {
        this.zhudao = zhudao;
    }

    public String getMazui()
    {
        return mazui;
    }

    public void setMazui(String mazui)
    {
        this.mazui = mazui;
    }

    public String getProcedureLevel()
    {
        return procedureLevel;
    }

    public void setProcedureLevel(String procedureLevel)
    {
        this.procedureLevel = procedureLevel;
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

    public String getOrgCode()
    {
        return orgCode;
    }

    public void setOrgCode(String orgCode)
    {
        this.orgCode = orgCode;
    }
    

}
