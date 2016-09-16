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
import com.founder.cdr.dao.AllergicHistoryDao;
import com.founder.cdr.dao.CommonDao;
import com.founder.cdr.dao.ConsultationRequestDao;
import com.founder.cdr.dto.AllergicHistoryDto;
import com.founder.cdr.dto.ConsultationRequestDto;
import com.founder.cdr.dto.consultation.ConsultationListSearchPra;
import com.founder.cdr.dto.risk.RiskListSearchPra;
import com.founder.cdr.entity.ConsultationOrder;
import com.founder.cdr.entity.ConsultationRequest;
import com.founder.cdr.entity.MedicalVisit;
import com.founder.cdr.service.CommonService;
import com.founder.cdr.service.ConsultationService;
import com.founder.fasf.core.util.daohelper.entity.EntityDao;
import com.founder.fasf.web.paging.PagingContext;

@Component
public class ConsultationServiceImpl implements ConsultationService
{

    @Autowired
    private EntityDao entityDao;

    @Autowired
    private CommonDao commonDao;
    
    @Autowired
    private ConsultationRequestDao consultationRequestDao;

    @Override
    @Transactional
    public ConsultationRequest getConsulationRequest(String patientDomain,
            String patientLid, String requestNo, String orgCode)
    {
        ConsultationRequest MV = null;
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("patientDomain", patientDomain);
        condition.put("patientLid", patientLid);
        condition.put("requestNo", requestNo);
        condition.put("orgCode", orgCode);
        condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
        List consultationRequest = new ArrayList<ConsultationRequest>();
        consultationRequest = entityDao.selectByCondition(
                ConsultationRequest.class, condition);
        if (consultationRequest != null && !consultationRequest.isEmpty())
        {
            MV = (ConsultationRequest) consultationRequest.get(0);
        }
        return MV;
    }

	@Override
	@Transactional
	public ConsultationRequestDto[] selectConsultationRequestList(
			String patientSn,
			ConsultationListSearchPra consultationListSearchPra,
			PagingContext pagingContext) {
    	ConsultationRequestDto[] consultationRequestResult = consultationRequestDao.selectConsultationRequestListSearch(
                patientSn, consultationListSearchPra, pagingContext);
		return consultationRequestResult;
	}
}
