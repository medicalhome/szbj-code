package com.founder.cdr.dto.risk;

public class RiskListSearchPra
{
    // 过敏开始日期
    private String allergicStartDate;

    // 过敏结束日期
    private String allergicStopDate;

    // 过敏严重性
    private String seriousness;

    public String getAllergicStartDate()
    {
        return allergicStartDate;
    }

    public void setAllergicStartDate(String allergicStartDate)
    {
        this.allergicStartDate = allergicStartDate;
    }

    public String getAllergicStopDate()
    {
        return allergicStopDate;
    }

    public void setAllergicStopDate(String allergicStopDate)
    {
        this.allergicStopDate = allergicStopDate;
    }

    public String getSeriousness()
    {
        return seriousness;
    }

    public void setSeriousness(String seriousness)
    {
        this.seriousness = seriousness;
    }
}
