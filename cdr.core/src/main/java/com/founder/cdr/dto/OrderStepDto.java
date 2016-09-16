package com.founder.cdr.dto;

import java.util.Date;

public class OrderStepDto implements Comparable<OrderStepDto>
{
    /**
     * 医嘱状态编码
     */
    private String orderStatusCode;
    /**
     * 医嘱状态名称
     */
    private String orderStatusName;
    /**
     * 执行时间
     */
    private Date executeTime;
    /**
     * 执行人姓名
     */
    private String executePersonName;
    /**
     * 执行科室名称
     */
    private String executeDeptName;
    /**
     * 医嘱状态节点需要显示的特殊信息
     */
    private String statusContent;
    
    public String getOrderStatusName()
    {
        return orderStatusName;
    }
    public void setOrderStatusName(String orderStatusName)
    {
        this.orderStatusName = orderStatusName;
    }
    public Date getExecuteTime()
    {
        return executeTime;
    }
    public void setExecuteTime(Date executeTime)
    {
        this.executeTime = executeTime;
    }
    public String getExecutePersonName()
    {
        return executePersonName;
    }
    public void setExecutePersonName(String executePersonName)
    {
        this.executePersonName = executePersonName;
    }
    public String getExecuteDeptName()
    {
        return executeDeptName;
    }
    public void setExecuteDeptName(String executeDeptName)
    {
        this.executeDeptName = executeDeptName;
    }
    public String getOrderStatusCode()
    {
        return orderStatusCode;
    }
    public void setOrderStatusCode(String orderStatusCode)
    {
        this.orderStatusCode = orderStatusCode;
    }
    public String getStatusContent()
    {
        return statusContent;
    }
    public void setStatusContent(String statusContent)
    {
        this.statusContent = statusContent;
    }
    
    
    @Override
    public int compareTo(OrderStepDto orderStep)
    {
        if(executeTime == null){
            return -1;
        }else if(orderStep.getExecuteTime() == null){
            return 1;
        }else{
            long time = executeTime.getTime() - orderStep.getExecuteTime().getTime();
            if(time > 0)
                return 1;
            else if(time < 0)
                return -1;
            else
                return 0;            
        }
    }
    
    @Override
    public int hashCode()
    {
        return (int)executeTime.getTime();
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if(obj instanceof OrderStepDto)
        {
            OrderStepDto orderStep = (OrderStepDto)obj;
            if(executeTime != null && orderStep.getExecuteTime() != null){
                return executeTime.getTime() == orderStep.getExecuteTime().getTime();                
            }else if(executeTime == null && orderStep.getExecuteTime() == null){
                return true;
            }else{
                return false;
            }
        }
        return false;
    }
    
    
    
}
