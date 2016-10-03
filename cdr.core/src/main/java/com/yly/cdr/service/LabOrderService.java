package com.yly.cdr.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.yly.cdr.entity.LabOrder;

public interface LabOrderService
{
    /**
     * 判断检验医嘱表是否存在此医嘱
     * @param requestNo
     * @return
     */
    @Transactional
    public LabOrder getOrderPresence(String patientDomain, String patientLid,
            String requestNo, String orgCode, BigDecimal visitSn);

    /**
     * 根据申请单号，找到关联的信息。
     * @param requestNo
     * @return
     */
    @Transactional
    public List<LabOrder> getRequestListByRequestNo(String requestNo);

    // $Author :jin_peng
    // $Date : 2012/9/25 14:46$
    // [BUG]0009863 ADD BEGIN
    /**
     * 根据申请单号，找到关联的信息。
     * @param requestNo 申请单号
     * @param patientLid 患者本地ID
     * @param patientDomain 域ID
     * @return 申请单对应的检验医嘱
     */
    @Transactional
    public List<Object> getRequestList(String requestNo, String patientLid,
            String patientDomain, String orgCode, BigDecimal visitSn);

    // [BUG]0009863 ADD END
    
    // Author :jia_yanqing
    // Date : 2014/1/17 11:00
    // [BUG]0041915 ADD BEGIN
    /**
     * 根据医嘱号，患者本地ID，域ID找到关联的医嘱信息。
     * @param orderLid 医嘱号
     * @param patientLid 患者本地ID
     * @param patientDomain 域ID
     */
    @Transactional
    public List<Object> getOrderList(String orderLid, String patientLid,
            String patientDomain);
    // [BUG]0041915 ADD END

    /**
     * 根据申请单号，找到关联的信息。
     * @param requestNo
     * @param patientLid 患者本地ID
     * @param patientDomain 域ID
     * @return
     */
    @Transactional
    public List<LabOrder> getRequestListByRequestNoList(
            List<String> requestNoList, String patientLid, String patientDomain, String orgCode, BigDecimal visitSn);
}
