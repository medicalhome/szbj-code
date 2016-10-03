package com.yly.cdr.hl7.dto;

import java.util.List;

public class ExamItemMessage extends BaseDto
{
    private String itemCode;
    
    private String itemName;
    
    private String itemText;
    
    private String pqItemTextValue;
    
    private String pqItemTextUnit;
    
    private List<SubExamItemMessage> subItemMessages;

    public String getItemCode()
    {
        return itemCode;
    }

    public String getItemName()
    {
        return itemName;
    }

    public String getItemText()
    {
        return itemText;
    }

    public List<SubExamItemMessage> getSubItemMessages()
    {
        return subItemMessages;
    }
    
    public String getPqItemTextValue() {
		return pqItemTextValue;
	}

	public String getPqItemTextUnit() {
		return pqItemTextUnit;
	}


	@Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("ExamItemMessage [");
        builder.append("itemCode=");
        builder.append(itemCode).append(", ");
        builder.append("itemName=");
        builder.append(itemName).append(", ");
        builder.append("itemText=");
        builder.append(itemText).append(", ");
        builder.append("subItemMessages=");
        builder.append(subItemMessages);
        builder.append("]");
        return builder.toString();
    }
}
