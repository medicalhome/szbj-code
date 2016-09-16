package com.founder.cdr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.founder.cdr.dao.AllergicHistoryDao;
import com.founder.cdr.dto.AllergicHistoryDto;
import com.founder.cdr.dto.risk.RiskListSearchPra;
import com.founder.cdr.service.AllergicHistoryService;
import com.founder.fasf.web.paging.PagingContext;

/**
 * 
 * @author huang_jieyu
 */

@Component
public class AllergicHistoryServiceImpl implements AllergicHistoryService
{

    @Autowired
    private AllergicHistoryDao allergicHistoryDao;

    @Transactional
    public AllergicHistoryDto[] selectAllergyList(String patientSn,
            PagingContext pagingContext)
    {
        AllergicHistoryDto[] allergyResult = allergicHistoryDao.selectAllergyList(
                patientSn, pagingContext);

        return allergyResult;
    }

    // 搜索
    @Transactional
    public AllergicHistoryDto[] selectAllergyList(String patientSn,
            RiskListSearchPra riskListSearchPra, PagingContext pagingContext)
    {

        AllergicHistoryDto[] allergyResult = allergicHistoryDao.selectAllergyListSearch(
                patientSn, riskListSearchPra, pagingContext);

        return allergyResult;
    }

}
