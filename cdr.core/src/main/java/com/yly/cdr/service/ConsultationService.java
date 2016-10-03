/**
 * 会诊列表
 * @author jia_yanqing 
 * @date 2012/04/23
 */
package com.yly.cdr.service;

import org.springframework.transaction.annotation.Transactional;

import com.founder.fasf.web.paging.PagingContext;
import com.yly.cdr.dto.ConsultationRequestDto;
import com.yly.cdr.dto.consultation.ConsultationListSearchPra;
import com.yly.cdr.entity.ConsultationRequest;

public interface ConsultationService
{
    @Transactional
    public ConsultationRequest getConsulationRequest(String patientDomain,
            String patientLid, String requestNo, String orgCode);
    
    @Transactional
    ConsultationRequestDto[] selectConsultationRequestList(String patientSn,
    		ConsultationListSearchPra consultationListSearchPra, PagingContext pagingContext);
}
