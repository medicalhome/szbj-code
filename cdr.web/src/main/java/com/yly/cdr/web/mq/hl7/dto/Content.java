package com.yly.cdr.web.mq.hl7.dto;

import java.util.List;

public class Content
{
    private String tableName;
    
    private List<RowOperation> row;

    public String getTableName()
    {
        return tableName;
    }

    public void setTableName(String tableName)
    {
        this.tableName = tableName;
    }

    public List<RowOperation> getRow()
    {
        return row;
    }

    public void setRow(List<RowOperation> row)
    {
        this.row = row;
    }
    
}
