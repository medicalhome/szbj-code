package com.yly.cdr.hl7.dto;


public class Histories extends BaseDto
{
    /**
     * 病史代码
     */
    private String historyCode;
    /**
     * 病史名称
     */
    private String history;
    /**
     * 病史次数
     */
    private String historyTime;

    public String getHistoryCode()
    {
        return historyCode;
    }

    public String getHistory()
    {
        return history;
    }

    public String getHistoryTime()
    {
        return historyTime;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Histories [historyCode=");
        builder.append(historyCode);
        builder.append(", history=");
        builder.append(history);
        builder.append(", historyTime=");
        builder.append(historyTime);
        builder.append("]");
        return builder.toString();
    }
    
    
}
