package com.founder.cdr.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.founder.cdr.core.Constants;
import com.founder.cdr.entity.BloodLabResult;
import com.founder.cdr.entity.BloodRequest;
import com.founder.cdr.entity.BloodSpecialRequest;
import com.founder.cdr.entity.MedicalVisit;
import com.founder.cdr.service.BloodRequestService;
import com.founder.cdr.service.CommonService;
import com.founder.fasf.core.util.daohelper.entity.EntityDao;

@Component
public class BloodRequestServiceImpl implements BloodRequestService
{
    @Autowired
    private EntityDao entityDao;

    @Autowired
    private CommonService commonService;

    /**
     * 就诊表查询
     * @param patientDomain
     * @param patientLid
     * @param visitTimes
     * @return
     */
    @Transactional
    public MedicalVisit visitResult(String patientDomain, String patientLid,
            Integer visitTimes)
    {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("patientDomain", patientDomain);
        condition.put("patientLid", patientLid);
        condition.put("visitTimes", visitTimes);
        condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
        List medicalVisitResult = new ArrayList<MedicalVisit>();
        medicalVisitResult = entityDao.selectByCondition(MedicalVisit.class,
                condition);
        if (medicalVisitResult == null || medicalVisitResult.isEmpty())
        {
            return null;
        }
        else
        {
            return (MedicalVisit) medicalVisitResult.get(0);
        }
    }

    /**
     *  
     */
    @Transactional
    public BloodRequest getAdvicePresence(BigDecimal visitSn, String patientDomain,
            String patientLid, String requestNo, String orgCode)
    {

        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("patientDomain", patientDomain);
        condition.put("visitSn", visitSn);
        condition.put("patientLid", patientLid);
        // $Author :jin_peng
        // $Date : 2012/08/24 15:59$
        // [BUG]0009149 MODIFY BEGIN
        // 应通过输血申请单号检索
        condition.put("requestNo", requestNo);
        // [BUG]0009149 MODIFY END
        condition.put("orgCode", orgCode);
        condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
        List result = new ArrayList<BloodRequest>();
        result = entityDao.selectByCondition(BloodRequest.class, condition);
        if (result == null || result.isEmpty())
        {
            return null;
        }
        else
        {
            return (BloodRequest) result.get(0);
        }
    }

    @Override
    @Transactional
    public List<BloodSpecialRequest> getSpecialRequest(BigDecimal requestSn)
    {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("requestSn", requestSn);
        condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
        List result = new ArrayList<BloodSpecialRequest>();
        result = entityDao.selectByCondition(BloodSpecialRequest.class,
                condition);
        return result;
    }

    @Transactional
    public List<BloodLabResult> getBloodLabResultByRequestSn(
            BigDecimal requestSn)
    {
        List<BloodLabResult> bloodLabResultList = new ArrayList<BloodLabResult>();
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("requestSn", requestSn);
        condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
        List<Object> list = entityDao.selectByCondition(BloodLabResult.class,
                condition);
        for (Object object : list)
        {
            bloodLabResultList.add((BloodLabResult) object);
        }
        return bloodLabResultList;
    }

    /**
     * 保存用血申请和血液特殊要求
     */
    @Override
    @Transactional
    public void saveBlood(BloodRequest bloodRequest,
            List<Object> speReqquestList, List<Object> bloodLabResultList)
    {
        // 保存用血申请
        commonService.save(bloodRequest);
        commonService.saveList(speReqquestList);
        commonService.saveList(bloodLabResultList);
    }
}
