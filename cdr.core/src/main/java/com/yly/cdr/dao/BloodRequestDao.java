package com.yly.cdr.dao;

import com.founder.fasf.core.util.daohelper.annotation.Arguments;
import com.yly.cdr.dto.BloodRequestDto;

public interface BloodRequestDao
{

    /**
     * [FUN]V05-101用血申请单
     * @version 1.0, 2012/06/18 
     * @author 张彬
     * @since 1.0
     * 取血单
     */
    @Arguments({ "visitSn", "patientSn" })
    public BloodRequestDto[] selectBloodReResult(String visitSn,
            String patientSn);
}
