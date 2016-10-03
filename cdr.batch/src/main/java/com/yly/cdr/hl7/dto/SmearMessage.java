package com.yly.cdr.hl7.dto;


public class SmearMessage extends BaseDto
{
    /**
     * 显示序号
     */
    private String sequenceNumber;
    /**
     * 涂片结果编码
     */
    private String smearResultCode;
    /**
     * 涂片结果名称
     */
    private String smearResultName;
    /**
     * 备注 
     */
    private String remark;

    public String getSequenceNumber()
    {
        return sequenceNumber;
    }

    public String getSmearResultCode()
    {
        return smearResultCode;
    }

    public String getSmearResultName()
    {
        return smearResultName;
    }

    public String getRemark()
    {
        return remark;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("SmearMessage [smearResultCode=");
        builder.append(smearResultCode);
        builder.append("]");
        return builder.toString();
    }
    
}
