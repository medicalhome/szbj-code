package com.yly.cdr.dto.exam;

import java.util.Date;

public class WithdrawRecordDto
{
    // 就诊号
    private String visitSn;

    // 召回人姓名
    private String withdrawPersonName;

    // 召回时间
    private Date withdrawTime;

    // 召回原因
    private String withdrawReason;

    public String getWithdrawPersonName()
    {
        return withdrawPersonName;
    }

    public void setWithdrawPersonName(String withdrawPersonName)
    {
        this.withdrawPersonName = withdrawPersonName;
    }

    public Date getWithdrawTime()
    {
        return withdrawTime;
    }

    public void setWithdrawTime(Date withdrawTime)
    {
        this.withdrawTime = withdrawTime;
    }

    public String getWithdrawReason()
    {
        return withdrawReason;
    }

    public void setWithdrawReason(String withdrawReason)
    {
        this.withdrawReason = withdrawReason;
    }

    public String getVisitSn()
    {
        return visitSn;
    }

    public void setVisitSn(String visitSn)
    {
        this.visitSn = visitSn;
    }

}
