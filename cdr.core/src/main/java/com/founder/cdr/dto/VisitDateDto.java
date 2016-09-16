package com.founder.cdr.dto;

import java.math.BigDecimal;
import java.util.Date;

public class VisitDateDto
{
    // $Author :tong_meng
    // $Date : 2012/11/27 13:24$
    // [BUG]0011923 MODIFY BEGIN
    private BigDecimal visitSn;

    // [BUG]0011923 MODIFY END

    private String visitDate;
    
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
