/**
 * 会诊列表
 * @author jia_yanqing 
 * @date 2012/04/23
 */
package com.founder.cdr.service;

import org.springframework.transaction.annotation.Transactional;

import com.founder.cdr.dto.ConsultationRequestDto;
import com.founder.cdr.dto.consultation.ConsultationListSearchPra;
import com.founder.cdr.entity.ConsultationRequest;
import com.founder.fasf.web.paging.PagingContext;

public interface ConsultationService
{
    @Transactional
    public ConsultationRequest getConsulationRequest(String patientDomain,
            String patientLid, String requestNo, String orgCode);
    
    @Transactional
    ConsultationRequestDto[] selectConsultationRequestList(String patientSn,
    		ConsultationListSearchPra consultationListSearchPra, PagingContext pagingContext);
}
