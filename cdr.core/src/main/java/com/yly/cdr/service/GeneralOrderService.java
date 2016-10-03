/**
 * Copyright
 */
package com.yly.cdr.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.yly.cdr.entity.GeneralOrder;

/**
 * 其他医嘱
 * @version 1.0, 2012/5/10, 17:30
 * 
 * @author tong_meng
 * @since 1.0
 */

public interface GeneralOrderService
{
    @Transactional
    Object selectGeneralOrder(String patientDomain, String patientLid,
            String orderLid, String orderSeqNum, String orderType);

    @Transactional
    void saveGeneralOrderList(List<Object> generalOrderList);

    @Transactional
    void updateGeneralOederList(List<Object> generalOrderList);

}
