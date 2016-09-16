package com.founder.cdr.hl7.dto;

import org.hibernate.validator.constraints.NotEmpty;


public class SampleContent extends BaseDto
{
   
    /**
     * 医嘱号
     */
    @NotEmpty(message = "{Sample.orderNo}")
    private String orderNo;
   
    public String getOrderNo()
    {
        return orderNo;
    }
    
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("SampleContent [");
        builder.append(", orderNo=");
        builder.append(orderNo);
       
        builder.append("]");
        return builder.toString();
    }
}
