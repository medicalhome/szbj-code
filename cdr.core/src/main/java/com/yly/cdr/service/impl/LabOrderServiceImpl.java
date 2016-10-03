package com.yly.cdr.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.founder.fasf.core.util.daohelper.entity.EntityDao;
import com.yly.cdr.core.Constants;
import com.yly.cdr.dao.LabDao;
import com.yly.cdr.entity.LabOrder;
import com.yly.cdr.service.LabOrderService;

@Component
public class LabOrderServiceImpl implements LabOrderService
{
    @Autowired
    private LabDao labDao;

    @Autowired
    private EntityDao entityDao;

    /**
     * 判断检验医嘱表是否存在此医嘱
     * @param requestNo
     * @return
     */
    @Transactional
    public LabOrder getOrderPresence(String patientDomain, String patientLid,
            String requestNo, String orgCode, BigDecimal visitSn)
    {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("patientDomain", patientDomain);
        condition.put("patientLid", patientLid);
        condition.put("orderLid", requestNo);
        condition.put("orgCode", orgCode);
        condition.put("visitSn", visitSn);
        condition.put("deleteFlag", "0");
        List result = new ArrayList<LabOrder>();
        result = entityDao.selectByCondition(LabOrder.class, condition);
        if (result == null || result.isEmpty())
        {
            return null;
        }
        else
        {
            return (LabOrder) result.get(0);
        }
    }

    /**
     * 根据申请单号，找到关联的信息。
     */
    @Override
    @Transactional
    public List<LabOrder> getRequestListByRequestNo(String requestNo)
    {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("requestNo", requestNo);
        condition.put("deleteFlag", "0");
        List result = new ArrayList<LabOrder>();
        result = entityDao.selectByCondition(LabOrder.class, condition);
        return result;
    }

    @Override
    @Transactional
    public List<LabOrder> getRequestListByRequestNoList(
            List<String> requestNoList, String patientLid, String patientDomain, String orgCode, BigDecimal visitSn)
    {
        return labDao.selectRequestByRequestNo(requestNoList,patientLid,patientDomain,orgCode,visitSn);
    }

    // $Author :jin_peng
    // $Date : 2012/9/25 14:46$
    // [BUG]0009863 ADD BEGIN
    /**
     * 根据申请单号，找到关联的信息。
     * @param requestNo 申请单号
     * @param patientLid 患者本地ID
     * @param patientDomain 域ID
     * @return 申请单对应的检验医嘱
     */
    @Transactional
    public List<Object> getRequestList(String requestNo, String patientLid,
            String patientDomain, String orgCode, BigDecimal visitSn)
    {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("requestNo", requestNo);
        condition.put("patientLid", patientLid);
        condition.put("patientDomain", patientDomain);
        condition.put("orgCode", orgCode);
        condition.put("visitSn", visitSn);
        condition.put("deleteFlag", Constants.NO_DELETE_FLAG);

        List<Object> result = entityDao.selectByCondition(LabOrder.class,
                condition);

        return result;
    }
    // [BUG]0009863 ADD END
    
    // Author :jia_yanqing
    // Date : 2014/1/17 11:00
    // [BUG]0041915 ADD BEGIN
    /**
     * 根据医嘱号，患者本地ID，域ID找到关联的医嘱信息。
     * @param requestNo 医嘱号
     * @param patientLid 患者本地ID
     * @param patientDomain 域ID
     * @return 医嘱号对应的检验医嘱
     */
    @Transactional
    public List<Object> getOrderList(String orderLid, String patientLid,
            String patientDomain)
    {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("orderLid", orderLid);
        condition.put("patientLid", patientLid);
        condition.put("patientDomain", patientDomain);
        condition.put("deleteFlag", "0");

        List<Object> result = entityDao.selectByCondition(LabOrder.class,
                condition);

        return result;
    }
    // [BUG]0041915 ADD END
}
