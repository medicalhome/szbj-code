package com.founder.cdr.hl7.dto;

import java.util.List;


public class WarningDetail extends BaseDto
{
    /**
     * 电话
     */
    private String telephone;
    /**
     * 通知时段
     */
    private String weekdays;
    /**
     * 邮件
     */
    private String email;
    /**
     * 系统ID
     */
    private List<String> systemId;

    public String getTelephone()
    {
        return telephone;
    }

    public String getWeekdays()
    {
        return weekdays;
    }

    public String getEmail()
    {
        return email;
    }

    public List<String> getSystemId()
    {
        return systemId;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("WarningDetail [telephone=");
        builder.append(telephone);
        builder.append(", weekdays=");
        builder.append(weekdays);
        builder.append(", email=");
        builder.append(email);
        builder.append(", systemId=");
        builder.append(systemId);
        builder.append("]");
        return builder.toString();
    }

}
