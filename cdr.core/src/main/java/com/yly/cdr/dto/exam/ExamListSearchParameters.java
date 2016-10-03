package com.yly.cdr.dto.exam;

import java.util.List;

import com.yly.cdr.dto.CommonParameters;

public class ExamListSearchParameters extends CommonParameters
{
    
	// 检查项目名
    private String examinationItem;

    // 检查部位
    private String examinationRegion;

    // 检查医生
    private String examiningDoctor;

    // 检查科室
    private String examinationDept;

    // 报告日期From
    private String reportDateFrom;

    // 报告日期To
    private String reportDateTo;

    // 检查日期From
    private String examinationDateFrom;

    // 检查日期To
    private String examinationDateTo;
    
    //wang.meng
    //申请日期from
    private String requestDateFrom;
 
    //申请日期to
    private String requestDateTo;
    
    //就诊次数
    private String visitTimes;
    
    //end

    // 检查科室类型集合
    private List<String> examinationTypes;

    // 检查子菜单标识号
    private String menuNum;
    
    // Author:jin_peng
    // Date : 2014/1/2 15:58
    // [BUG]0041392 ADD BEGIN
    // 医嘱开立人
    private String orderPersonName;
    
    // 医嘱开立科室
    private String orderDept;
    
    // [BUG]0041392 ADD END
	
	// 医疗机构编码
    private String orgCode;

    public String getMenuNum()
    {
        return menuNum;
    }

    public void setMenuNum(String menuNum)
    {
        this.menuNum = menuNum;
    }

    public String getExaminationItem()
    {
        return examinationItem;
    }

    public void setExaminationItem(String examinationItem)
    {
        this.examinationItem = examinationItem;
    }

    public String getExaminationRegion()
    {
        return examinationRegion;
    }

    public void setExaminationRegion(String examinationRegion)
    {
        this.examinationRegion = examinationRegion;
    }

    public String getExaminingDoctor()
    {
        return examiningDoctor;
    }

    public void setExaminingDoctor(String examiningDoctor)
    {
        this.examiningDoctor = examiningDoctor;
    }

    public String getExaminationDept()
    {
        return examinationDept;
    }

    public void setExaminationDept(String examinationDept)
    {
        this.examinationDept = examinationDept;
    }

    public String getReportDateFrom()
    {
        return reportDateFrom;
    }

    public void setReportDateFrom(String reportDateFrom)
    {
        this.reportDateFrom = reportDateFrom;
    }

    public String getReportDateTo()
    {
        return reportDateTo;
    }

    public void setReportDateTo(String reportDateTo)
    {
        this.reportDateTo = reportDateTo;
    }

    public String getExaminationDateFrom()
    {
        return examinationDateFrom;
    }

    public void setExaminationDateFrom(String examinationDateFrom)
    {
        this.examinationDateFrom = examinationDateFrom;
    }

    public String getExaminationDateTo()
    {
        return examinationDateTo;
    }

    public void setExaminationDateTo(String examinationDateTo)
    {
        this.examinationDateTo = examinationDateTo;
    }

    public List<String> getExaminationTypes()
    {
        return examinationTypes;
    }

    public void setExaminationTypes(List<String> examinationTypes)
    {
        this.examinationTypes = examinationTypes;
    }

    public String getOrderPersonName()
    {
        return orderPersonName;
    }

    public void setOrderPersonName(String orderPersonName)
    {
        this.orderPersonName = orderPersonName;
    }

    public String getOrderDept()
    {
        return orderDept;
    }

    public void setOrderDept(String orderDept)
    {
        this.orderDept = orderDept;
    }
	
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getRequestDateFrom() {
		return requestDateFrom;
	}

	public void setRequestDateFrom(String requestDateFrom) {
		this.requestDateFrom = requestDateFrom;
	}

	public String getRequestDateTo() {
		return requestDateTo;
	}

	public void setRequestDateTo(String requestDateTo) {
		this.requestDateTo = requestDateTo;
	}

	public String getVisitTimes() {
		return visitTimes;
	}

	public void setVisitTimes(String visitTimes) {
		this.visitTimes = visitTimes;
	}

}
