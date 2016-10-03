package com.yly.cdr.web.mq.hl7.dto;

import java.util.List;

public class RowOperation
{
    private String root_action;
    
    private List<Item> item;
    
    public List<Item> getItem()
    {
        return item;
    }

    public void setItem(List<Item> item)
    {
        this.item = item;
    }

    public String getRoot_action()
    {
        return root_action;
    }

    public void setRoot_action(String root_action)
    {
        this.root_action = root_action;
    }
    
}
