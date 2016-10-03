package com.yly.cdr.dao.order;

import java.util.List;

import com.founder.fasf.core.util.daohelper.annotation.Arguments;
import com.founder.fasf.web.paging.PagingContext;
import com.yly.cdr.dto.NonDrugOrderDto;
import com.yly.cdr.dto.VisitDateDto;
import com.yly.cdr.dto.order.NonDrugOrderListSearchPra;
import com.yly.cdr.entity.NursingOperation;

public interface NonDrugOrderDao
{

    @Arguments({ "patientSn", "orderStrStartTime", "orderStrEndTime",
            "orderType", "orderDept", "orderPerson", "execDept", "orderName" })
    public List<NonDrugOrderDto> selectAllOrder(String patientSn,
            String orderStrStartTime, String orderStrEndTime, String orderType,
            String orderDept, String orderPerson, String execDept,
            String orderName, PagingContext pagingContext);

    @Arguments({ "patientSn", "nonDrugOrderPra" })
    public List<NonDrugOrderDto> selectAllOrderOb(String patientSn,
            NonDrugOrderListSearchPra nonDrugOrderPra,
            PagingContext pagingContext);
    
    @Arguments({ "patientSn", "nonDrugOrderPra" ,"flag"})
    public List<NonDrugOrderDto> selectAllOrderOb(String patientSn,
            NonDrugOrderListSearchPra nonDrugOrderPra,String flag,
            PagingContext pagingContext);

    @Arguments({ "orderSn" })
    public List<NursingOperation> selectNursingOperationByCondition(
            String orderSn);

    // $Author:bin_zhang
    // $Date:2012/9/28 10:42
    // $[BUG]BUG0010082 ADD BEGIN
    @Arguments({ "visitSn" })
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
    @Arguments({ "visitSn" })
    public List<NonDrugOrderDto> selectOrder(String visitSn,PagingContext pagingContext);
    // $[BUG]0032417 ADD BEGIN
    

    // $Author :jin_peng
    // $Date : 2012/11/22 13:34$
    // [BUG]0011026 MODIFY BEGIN
    // $Author :tong_meng
    // $Date : 2012/9/29 17:22$
    // [BUG]0010913 MODIFY BEGIN
    @Arguments({ "patientSn" })
    public List<VisitDateDto> selectVisitDate(String patientSn);
    // [BUG]0010913 MODIFY END
    // [BUG]0011026 MODIFY END
}
