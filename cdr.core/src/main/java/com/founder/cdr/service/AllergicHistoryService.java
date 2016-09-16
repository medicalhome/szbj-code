/**
 /* Copyright
 */
package com.founder.cdr.service;

import org.springframework.transaction.annotation.Transactional;

import com.founder.cdr.dto.AllergicHistoryDto;
import com.founder.cdr.dto.risk.RiskListSearchPra;
import com.founder.fasf.web.paging.PagingContext;

/**
 * 
 * @author huang_jieyu
 */

public interface AllergicHistoryService
{
    /**
    *
    *[FUN]过敏/不良反应列表
    *@version 1.0, 2012/03/12
    *@author 黄洁玉
    *@since 1.0
    *
    */
    @Transactional
    AllergicHistoryDto[] selectAllergyList(String patientSn,
            PagingContext pagingContext);

    @Transactional
    AllergicHistoryDto[] selectAllergyList(String patientSn,
            RiskListSearchPra riskListSearchPra, PagingContext pagingContext);

}
