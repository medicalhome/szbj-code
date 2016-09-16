package com.founder.cdr.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.founder.cdr.core.Constants;
import com.founder.cdr.entity.BloodDeliveryOrder;
import com.founder.cdr.entity.BloodRequest;
import com.founder.cdr.service.BloodSpecialRequestService;
import com.founder.fasf.core.util.daohelper.entity.EntityDao;

@Component
public class BloodSpecialRequestServiceImpl implements
        BloodSpecialRequestService
{
    @Autowired
    private EntityDao entityDao;

    @Override
    @Transactional
    public List<BloodRequest> selectBloodRequest(String requestNo,
            String patientDomain, String patientLid,String orgCode)
    {
        Map<String, Object> condition = new HashMap<String, Object>();
        // $Author :jin_peng
        // $Date : 2012/08/13 11:23$
        // [BUG]0008712 MODIFY BEGIN
        condition.put("requestNo", requestNo);
        // [BUG]0008712 MODIFY END
        condition.put("patientDomain", patientDomain);
        condition.put("patientLid", patientLid);
        condition.put("orgCode", orgCode);
        condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
        List result = entityDao.selectByCondition(BloodRequest.class, condition);
        return result;
    }

    @Override
    @Transactional
    public List<BloodDeliveryOrder> selectBloodDeliveryOrder(
            BigDecimal requestSn, String obtainRequestNo, String patientDomain,String orgCode)
    {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("requestSn", requestSn);
        // $Author :jin_peng
        // $Date : 2012/08/13 11:23$
        // [BUG]0008712 MODIFY BEGIN
        // 再通过取血单号唯一确定一条取血单记录
        condition.put("orderLid", obtainRequestNo);
        // [BUG]0008712 MODIFY END
        condition.put("patientDomain", patientDomain);
        condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
        condition.put("orgCode", orgCode);
        List result = entityDao.selectByCondition(BloodDeliveryOrder.class,
                condition);
        return result;
    }
}
