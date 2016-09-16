package com.founder.cdr.hl7.dto;

public class SubExamItemMessage extends BaseDto
{
    private String itemCode;
    
    private String itemName;
    
    private String itemText;
    
    private String pqItemTextValue;
    
    private String pqItemTextUnit;
    
    private String text;

    
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
    
    public String getPqItemTextValue() {
		return pqItemTextValue;
	}

	public String getPqItemTextUnit() {
		return pqItemTextUnit;
	}

	public String getText() {
		return text;
	}

	@Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("SubExamItemMessage [");
        builder.append("itemCode=");
        builder.append(itemCode).append(", ");
        builder.append("itemName=");
        builder.append(itemName).append(", ");
        builder.append("itemText=");
        builder.append(itemText).append(", ");
        builder.append("]");
        return builder.toString();
    }
}
