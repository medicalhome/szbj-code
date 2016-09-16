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
import com.founder.cdr.entity.CodeChargeItem;
import com.founder.cdr.entity.MedicalImaging;
import com.founder.cdr.entity.MedicalVisit;
import com.founder.cdr.entity.Prescription;
import com.founder.cdr.entity.TreatmentOrder;
import com.founder.cdr.service.CommonService;
import com.founder.cdr.service.ExamService;
import com.founder.cdr.service.TreatmentService;
import com.founder.cdr.util.StringUtils;
import com.founder.fasf.core.util.daohelper.entity.EntityDao;
import com.founder.fasf.web.paging.PagingContext;

@Component
public class TreatmentServiceImpl implements TreatmentService
{
   

    @Autowired
    private EntityDao entityDao;

    @Autowired
    private CommonService commonService;

    @Override
    @Transactional
    public List<Object> selectMedicalVisitById(Map<String, Object> map)
    {
        return entityDao.selectByCondition(MedicalVisit.class, map);
    }

    @Override
    @Transactional
    public TreatmentOrder getOrder(String patientDomain, String patientLid,
            String orderLid, String orgCode, BigDecimal visitSn, Integer orderSeqNum)
    {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("patientDomain", patientDomain);
        condition.put("patientLid", patientLid);
        condition.put("orderLid", orderLid);
        condition.put("orgCode", orgCode);
        condition.put("visitSn", visitSn);
//        condition.put("orderSeqnum",orderSeqNum);
        condition.put("deleteFlag", "0");
        List result = new ArrayList<TreatmentOrder>();
        result = entityDao.selectByCondition(TreatmentOrder.class, condition);
        if(result.size()!=0)
        {
            return (TreatmentOrder) result.get(0);            
        }
        return null;
    }



	@Override
	public MedicalVisit mediVisit(String patientDomain, String patientLid,
			Integer visitTimes) {
		
		return null;
	}


	@Override
	public List<Object> selectTreatmentOrderById(Map<String, Object> map) {
		
		return null;
	}

	@Override
	public void updateTreatmentOrderReportSn(BigDecimal reportSn,
			String patientDomain, String patientLid, List<String> orderLidList) {
		
		
	}

	@Override
	public List<Object> selectTreatmentByCondition(Map<String, Object> map) {
		
		return null;
	}

    @Override
    @Transactional
    public CodeChargeItem getSpecification(String itemCode)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("itemCode", itemCode);
        map.put("deleteFlag", Constants.NO_DELETE_FLAG);
        List result = new ArrayList<CodeChargeItem>();
        result = entityDao.selectByCondition(CodeChargeItem.class, map);
        CodeChargeItem chargeItem = null;
        if (result != null && result.size() != 0)
            chargeItem = (CodeChargeItem) result.get(0);
        return chargeItem;
    }
    
    // Author:jia_yanqing
    // Date:2013/3/4 15:40
    // [BUG]0014140 AND BEGIN
    @Override
    @Transactional
    public void deleteForReturnFee(List<Prescription> prescriptionListForReturnFee,
            List<TreatmentOrder> treatmentOrderListForReturnFee)
    {
        // 对退费后的处方关联的医嘱执行物理删除操作
        commonService.deleteList(treatmentOrderListForReturnFee);
        
        // 对退费后的处方执行物理删除操作
        commonService.deleteList(prescriptionListForReturnFee);
        
    }
    // [BUG]0014140 AND END
}
