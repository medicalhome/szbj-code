package com.founder.cdr.hl7.dto;

public class WarningLabReportResult extends BaseDto
{   
    /**
     * 项目代码
     */
    private String itemCode;
    
    /**
     * 检验数值
     */
    private String itemNumValue;
    
    /**
     * 检验字符值
     */
    private String itemStrValue;
    
    /**
     * 单位
     */
    private String itemUnit;
    
    /**
     * 检验结果状态
     */
    private String checkStatus;
    
    /**
     * 检验结果信息
     */
    private String checkMessage;
    
    /**
     * 处理建议
     */
    private String suggestion;

    
    /**
     * 科室
     */
    private String itemDept;
    
    
    public String getItemDept() {
		return itemDept;
	}

	public void setItemDept(String itemDept) {
		this.itemDept = itemDept;
	}

	public String getItemCode()
    {
        return itemCode;
    }

    public String getItemNumValue()
    {
        return itemNumValue;
    }

    public void setItemNumValue(String itemNumValue)
    {
        this.itemNumValue = itemNumValue;
    }

    public String getItemStrValue()
    {
        return itemStrValue;
    }

    public void setItemStrValue(String itemStrValue)
    {
        this.itemStrValue = itemStrValue;
    }

    public String getItemUnit()
    {
        return itemUnit;
    }

    public void setItemUnit(String itemUnit)
    {
        this.itemUnit = itemUnit;
    }

    public String getCheckStatus()
    {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus)
    {
        this.checkStatus = checkStatus;
    }

    public String getCheckMessage()
    {
        return checkMessage;
    }

    public void setCheckMessage(String checkMessage)
    {
        this.checkMessage = checkMessage;
    }

    public void setItemCode(String itemCode)
    {
        this.itemCode = itemCode;
    }

	public String getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}

}
