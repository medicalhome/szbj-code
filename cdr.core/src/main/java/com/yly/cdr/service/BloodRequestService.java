package com.yly.cdr.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.yly.cdr.entity.BloodLabResult;
import com.yly.cdr.entity.BloodRequest;
import com.yly.cdr.entity.BloodSpecialRequest;
import com.yly.cdr.entity.MedicalVisit;

public interface BloodRequestService
{

    @Transactional
    public MedicalVisit visitResult(String patientDomain, String patientLid,
            Integer visitTimes);

    // $Author :jin_peng
    // $Date : 2012/08/24 15:59$
    // [BUG]0009149 MODIFY BEGIN
    // 应通过输血申请单号检索
    @Transactional
    public BloodRequest getAdvicePresence(BigDecimal visitSn, String patientDomain,
            String patientLid, String requestNo, String orgCode);

    // [BUG]0009149 MODIFY END

    @Transactional
    public List<BloodSpecialRequest> getSpecialRequest(BigDecimal requestSn);

    @Transactional
    public List<BloodLabResult> getBloodLabResultByRequestSn(
            BigDecimal requestSn);

    /**
     * 保存用血申请和血液特殊要求
     * @param requestResult
     * @param speReqquestList
     * @param bloodLabResultList
     */
    @Transactional
    public void saveBlood(BloodRequest requestResult,
            List<Object> speReqquestList, List<Object> bloodLabResultList);

}
