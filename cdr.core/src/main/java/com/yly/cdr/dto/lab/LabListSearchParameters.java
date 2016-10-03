package com.yly.cdr.dto.lab;

import java.util.List;

import com.yly.cdr.dto.CommonParameters;

public class LabListSearchParameters extends CommonParameters
{
    private String labDept;

    private String itemName;

    private String testDateFrom;

    private String testDateTo;

    private String testerName;

    private String reporterName;

    private String reportDateFrom;

    private String reportDateTo;

    private String sourceSystemId;

    private List<String> notInLabDepts;
    
    /*
     * $Author: yu_yzh
     * $Date: 2015/8/18 10:02
     * 添加检索分类列表 ADD BEGIN
     * */
    private List<String> orderExecList;
    
	public List<String> getOrderExecList() {
		return orderExecList;
	}

	public void setOrderExecList(List<String> orderExecList) {
		this.orderExecList = orderExecList;
	}
    // ADD END
    //申请时间
    private String requestDateFrom;

    private String requestDateTo;
    
    // Author:jin_peng
    // Date : 2014/1/2 15:58
    // [BUG]0041392 ADD BEGIN
    // 医嘱开立人
    private String orderPersonName;
    
    // 医嘱开立科室
    private String orderDept;
    
    // [BUG]0041392 ADD END

	private String orgCode;
	
	//就诊次数
    private String visitTimes;
    
    private String viewId;
    
    // 医嘱执行分类
    private String orderExecId;
    
    public String getOrderExecId() {
		return orderExecId;
	}

	public void setOrderExecId(String orderExecId) {
		this.orderExecId = orderExecId;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
    public List<String> getNotInLabDepts()
    {
        return notInLabDepts;
    }

    public void setNotInLabDepts(List<String> notInLabDepts)
    {
        this.notInLabDepts = notInLabDepts;
    }

    public String getLabDept()
    {
        return labDept;
    }

    public void setLabDept(String labDept)
    {
        this.labDept = labDept;
    }

    public String getItemName()
    {
        return itemName;
    }

    public void setItemName(String itemName)
    {
        this.itemName = itemName;
    }

    public String getTestDateFrom()
    {
        return testDateFrom;
    }

    public void setTestDateFrom(String testDateFrom)
    {
        this.testDateFrom = testDateFrom;
    }

    public String getTestDateTo()
    {
        return testDateTo;
    }

    public void setTestDateTo(String testDateTo)
    {
        this.testDateTo = testDateTo;
    }

    public String getTesterName()
    {
        return testerName;
    }

    public void setTesterName(String testerName)
    {
        this.testerName = testerName;
    }

    public String getReporterName()
    {
        return reporterName;
    }

    public void setReporterName(String reporterName)
    {
        this.reporterName = reporterName;
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

    public String getSourceSystemId()
    {
        return sourceSystemId;
    }

    public void setSourceSystemId(String sourceSystemId)
    {
        this.sourceSystemId = sourceSystemId;
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

	public String getViewId() {
		return viewId;
	}

	public void setViewId(String viewId) {
		this.viewId = viewId;
	}


}
