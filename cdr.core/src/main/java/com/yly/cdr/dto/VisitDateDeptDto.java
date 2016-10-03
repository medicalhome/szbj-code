package com.yly.cdr.dto;

import java.math.BigDecimal;

public class VisitDateDeptDto
{

    private BigDecimal visitSn;

    private String visitDate;
    
    private String visitDateDept;
    
    public String getVisitDateDept() {
		return visitDateDept;
	}

	public void setVisitDateDept(String visitDateDept) {
		this.visitDateDept = visitDateDept;
	}

	private String orgCode;

    public String getVisitDate()
    {
        return visitDate;
    }

    public void setVisitDate(String visitDate)
    {
        this.visitDate = visitDate;
    }

    public BigDecimal getVisitSn()
    {
        return visitSn;
    }

    public void setVisitSn(BigDecimal visitSn)
    {
        this.visitSn = visitSn;
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
