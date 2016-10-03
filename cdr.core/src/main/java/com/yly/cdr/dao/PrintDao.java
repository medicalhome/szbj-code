package com.yly.cdr.dao;

import java.util.List;

import com.founder.fasf.core.util.daohelper.annotation.Arguments;
import com.yly.cdr.dto.VisitDateDeptDto;

public interface PrintDao
{
    @Arguments({"sysDate", "patientSn", "visitTypeCode" })
    public List<VisitDateDeptDto> selectPrintVisitDate(String sysDate, String patientSn,String visitTypeCode);
}
