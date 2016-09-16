package com.founder.cdr.dao;

import com.founder.cdr.dto.ConsultationRequestDto;
import com.founder.cdr.dto.consultation.ConsultationListSearchPra;
import com.founder.fasf.core.util.daohelper.annotation.Arguments;
import com.founder.fasf.web.paging.PagingContext;

public interface ConsultationRequestDao
{

    /**
    *
    *[FUN]會診列表
    *@version 1.0, 2014/07/14
    *@author li_shenggen
    *@since 1.0
    *
    */
    @Arguments({ "patientSn", "consultationListSearchPra" })
    public ConsultationRequestDto[] selectConsultationRequestListSearch(String patientSn,
    		ConsultationListSearchPra consultationListSearchPra, PagingContext pagingContext);

}
