package com.yly.cdr.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.founder.fasf.web.paging.PagingContext;
import com.yly.cdr.entity.CodeChargeItem;
import com.yly.cdr.entity.MedicalVisit;
import com.yly.cdr.entity.Prescription;
import com.yly.cdr.entity.TreatmentOrder;

public interface TreatmentService
{

    @Transactional
    public MedicalVisit mediVisit(String patientDomain, String patientLid,
            Integer visitTimes);

    @Transactional
    public List<Object> selectMedicalVisitById(Map<String, Object> map);

    @Transactional
    public List<Object> selectTreatmentOrderById(Map<String, Object> map);

    @Transactional
    public TreatmentOrder getOrder(String patientDomain, String patientLid,
            String orderLid, String orgCode, BigDecimal visitSn, Integer orderSeqNum);

    @Transactional
    public void updateTreatmentOrderReportSn(BigDecimal reportSn,
            String patientDomain, String patientLid, List<String> orderLidList);

    @Transactional
    public List<Object> selectTreatmentByCondition(Map<String, Object> map);

    // $Author :tong_meng
    // $Date : 2013/1/16 14:00$
    // [BUG]0013110 MODIFY BEGIN
    @Transactional
    public CodeChargeItem getSpecification(String itemCode);
    // [BUG]0013110 MODIFY END
    
    // Author:jia_yanqing
    // Date:2013/3/4 15:40
    // [BUG]0014140 AND BEGIN
    /**
     * 逻辑删除已退费的诊疗及处方
     * @param prescriptionList
     * @param treatmentOrderList
     */
    @Transactional
    public void deleteForReturnFee(List<Prescription> prescriptionListForReturnFee,
            List<TreatmentOrder> treatmentOrderListForReturnFee);
    // [BUG]0014140 AND END
}
