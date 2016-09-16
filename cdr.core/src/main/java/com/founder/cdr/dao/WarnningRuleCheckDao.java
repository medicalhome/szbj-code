package com.founder.cdr.dao;

import com.founder.fasf.core.util.daohelper.annotation.Arguments;

public interface WarnningRuleCheckDao
{
    // 传染病规则检查
	  @Arguments({ "patientLid", "disearseName", "dayCount","reportDate" })
    public  int selectIsHaveWarned(String patientLid,String disearseName,int dayCount,String reportDate);
}
