package com.yly.cdr.dao;

import com.founder.fasf.core.util.daohelper.annotation.Arguments;
import com.yly.cdr.dto.BloodDeliveryOrderDto;

public interface BloodDeliveryOrderDao
{
    /**
     * [FUN]V05-101就诊列表
     * @version 1.0, 2012/03/12  
     * @author 郭红燕
     * @since 1.0
     * 取血单
     */
    @Arguments({ "visitSn", "requestSn" })
    public BloodDeliveryOrderDto[] selectBoodDeOrder(String visitSn,
            String requestSn);

}
