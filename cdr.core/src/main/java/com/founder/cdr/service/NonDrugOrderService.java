package com.founder.cdr.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.founder.cdr.dto.NonDrugOrderDto;
import com.founder.cdr.dto.VisitDateDto;
import com.founder.cdr.dto.order.NonDrugOrderListSearchPra;
import com.founder.cdr.entity.NursingOperation;
import com.founder.fasf.web.paging.PagingContext;

public interface NonDrugOrderService
{

    @Transactional
    public List<NonDrugOrderDto> selectAllOrder(String patientSn,
            String orderStrStartTime, String orderStrEndTime, String orderType,
            String orderDept, String orderPerson, String execDept,
            String orderName, PagingContext pagingContext);

    @Transactional
    public List<NonDrugOrderDto> selectAllOrder(String patientSn,
            NonDrugOrderListSearchPra nonDrugOrderPra,
            PagingContext pagingContext);
    
    @Transactional
    public List<NonDrugOrderDto> selectAllOrder(String patientSn,
            NonDrugOrderListSearchPra nonDrugOrderPra,String flag,
            PagingContext pagingContext);

    @Transactional
    public Object selectOrders(int orderFlag, String orderSn);

    @Transactional
    public Object selectOrdersByCondition(int orderFlag, Map<String, Object> map);

    @Transactional
    public List<NursingOperation> selectNursingOperationByCondition(
            String orderSn);

    // $Author:bin_zhang
    // $Date:2012/9/28 10:42
    // $[BUG]BUG0010082 ADD BEGIN
    @Transactional
    public List<NonDrugOrderDto> selectOrder(String visitSn);

    // $[BUG]BUG0010082 ADD END
    
    // $Author:chang_xuewen
    // $Date:2013/07/01 17:10
    // $[BUG]0032417 ADD BEGIN
    /**
     * 增加pagingContext满足门诊视图翻页需求
     * @param visitSn
     * @param pagingContext
     * @return
     */
    @Transactional
    public List<NonDrugOrderDto> selectOrder(String visitSn,PagingContext pagingContext);
    // $[BUG]0032417 ADD BEGIN

    // $Author :jin_peng
    // $Date : 2012/11/22 13:34$
    // [BUG]0011026 MODIFY BEGIN
    // $Author :tong_meng
    // $Date : 2012/9/29 14:45$
    // [BUG]0010038 ADD BEGIN
    @Transactional
    public List<VisitDateDto> selectVisitDate(String patientSn);
    // [BUG]0010038 ADD END
    // [BUG]0011026 MODIFY END
}
