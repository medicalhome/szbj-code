/**
 * Copryright
 */
package com.founder.cdr.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.founder.cdr.entity.BloodDeliveryOrder;
import com.founder.cdr.entity.BloodRequest;

/**
 * @version 1.0, 2012/4/28, 17:30
 * 
 * @author tong_meng
 * @since 1.0
 */

public interface BloodSpecialRequestService
{
    /**
     * 查询用血申请单中的数据
     * @param requestNo 
     */
    @Transactional
    public List<BloodRequest> selectBloodRequest(String requestNo,
            String patientDomain, String patientLid,String orgCode);

    /**
     * 
     * 查询取血单中的数据
     * @param orderLid
     * @return
     */
    @Transactional
    public List<BloodDeliveryOrder> selectBloodDeliveryOrder(
            BigDecimal requestSn, String obtainRequestNo, String patientDomain,String orgCode);

}
