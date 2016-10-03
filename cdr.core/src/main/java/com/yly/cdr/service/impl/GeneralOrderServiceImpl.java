package com.yly.cdr.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.founder.fasf.core.util.daohelper.entity.EntityDao;
import com.yly.cdr.core.Constants;
import com.yly.cdr.entity.ConsultationOrder;
import com.yly.cdr.entity.GeneralOrder;
import com.yly.cdr.service.GeneralOrderService;

@Component
public class GeneralOrderServiceImpl implements GeneralOrderService
{

    @Autowired
    private EntityDao entityDao;

    /**
     * 查询其他医嘱表中的记录
     */
    @Override
    @Transactional
    public Object selectGeneralOrder(String patientDomain,
            String patientLid, String orderLid, String orderSeqNum, String orderType)
    {
    	List result = null;
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("patientDomain", patientDomain);
        condition.put("patientLid", patientLid);
        condition.put("orderLid", orderLid);
        // 港大没有医嘱序号
//        condition.put("orderSeqnum", orderSeqNum);
        if(Constants.CONSULTATION_ORDER.equals(orderType)){
        	result = entityDao.selectByCondition(ConsultationOrder.class, condition);
        }else{
        	result = entityDao.selectByCondition(GeneralOrder.class, condition);
        }
        
        if (result == null || result.size() == 0)
            return null;
        return result.get(0);
    }

    @Override
    @Transactional
    public void saveGeneralOrderList(List<Object> generalOrderList)
    {
        for(int i=0; i<generalOrderList.size(); i++){
        	 entityDao.insert(generalOrderList.get(i));
        }

    }

   @Override
   @Transactional
    public void updateGeneralOederList(List<Object> generalOrderList)
    {
	   for(int i=0; i<generalOrderList.size(); i++){
      	 entityDao.update(generalOrderList.get(i));
      }
    }

}
