package com.founder.cdr.hl7.dto;

import java.util.Date;


public class LabResults extends BaseDto
{
    /**
     * 检验代码
     */
    private String labResultCode;
    /**
     * 检验名称
     */
    private String labResultName;
    /**
     * 检验名称-血型
     */
    private String labResultNameBlood;
    /**
     * 检验单位
     */
    private String labResultUnit;
    // add by li_shenggen begin 2014/11/12
    /**
     * 验血日期
     */    
    private String labResultDate;
    // end

    public String getLabResultCode()
    {
        return labResultCode;
    }

    public String getLabResultName()
    {
        return labResultName;
    }

    public String getLabResultUnit()
    {
        return labResultUnit;
    }
    
    public String getLabResultNameBlood()
    {
        return labResultNameBlood;
    }

	public String getLabResultDate() {
		return labResultDate;
	}

	public void setLabResultDate(String labResultDate) {
		this.labResultDate = labResultDate;
	}

	@Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("LabResults [labResultCode=");
        builder.append(labResultCode);
        builder.append(", labResultName=");
        builder.append(labResultName);
        builder.append(", labResultUnit=");
        builder.append(labResultUnit);
        builder.append(", labResultNameBlood=");
        builder.append(labResultNameBlood);
        builder.append(", labResultDate=");
        builder.append(labResultDate);
        builder.append("]");
        return builder.toString();
    }
    
    
}
