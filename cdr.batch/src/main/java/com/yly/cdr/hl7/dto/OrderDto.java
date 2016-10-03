package com.yly.cdr.hl7.dto;

import com.yly.cdr.core.Constants;

/**
 * OrderDto
 * 存放医嘱的一些通用属性和一些共通方法
 * @author wujf
 *
 */
public class OrderDto extends BaseDto
{    
    /**
     * 根据医嘱的频率，得到医嘱的长期临时标识
     * @param freq
     * @return 医嘱的长期临时标识
     */    
    public String getTemporaryFlag(String freq)
    {
        //如果没有频率，或者没有频率为空
        if(freq == null || freq.trim().isEmpty())
            return Constants.TEMPORARY_FLAG;
        else if(freq.equals(Constants.TEMPORARY_FLAG_TEXT))
            return Constants.TEMPORARY_FLAG;
        else
            return Constants.LONG_TERM_FLAG;               
    }
    
}
