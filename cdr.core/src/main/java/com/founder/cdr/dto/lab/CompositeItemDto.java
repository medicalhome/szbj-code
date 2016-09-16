package com.founder.cdr.dto.lab;

import java.util.List;

public class CompositeItemDto
{
    // 检验项目名称
    private String itemName;
    // 检验小项集合
    private List<LabResultItemDto> labResultItemList;
    
    public String getItemName()
    {
        return itemName;
    }
    public void setItemName(String itemName)
    {
        this.itemName = itemName;
    }
    public List<LabResultItemDto> getLabResultItemList()
    {
        return labResultItemList;
    }
    public void setLabResultItemList(List<LabResultItemDto> labResultItemList)
    {
        this.labResultItemList = labResultItemList;
    }
}
