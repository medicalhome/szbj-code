package com.founder.cdr.dao;

import java.util.List;

import com.founder.cdr.dto.VisitDateDeptDto;
import com.founder.fasf.core.util.daohelper.annotation.Arguments;

public interface PrintDao
{
    @Arguments({"sysDate", "patientSn", "visitTypeCode" })
    public List<VisitDateDeptDto> selectPrintVisitDate(String sysDate, String patientSn,String visitTypeCode);
}
