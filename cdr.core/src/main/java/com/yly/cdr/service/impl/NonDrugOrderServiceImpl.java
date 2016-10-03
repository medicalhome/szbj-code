package com.yly.cdr.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.founder.fasf.core.util.daohelper.entity.EntityDao;
import com.founder.fasf.web.paging.PagingContext;
import com.yly.cdr.dao.order.NonDrugOrderDao;
import com.yly.cdr.dto.NonDrugOrderDto;
import com.yly.cdr.dto.VisitDateDto;
import com.yly.cdr.dto.order.NonDrugOrderListSearchPra;
import com.yly.cdr.entity.CareOrder;
import com.yly.cdr.entity.ConsultationOrder;
import com.yly.cdr.entity.ExaminationOrder;
import com.yly.cdr.entity.GeneralOrder;
import com.yly.cdr.entity.LabOrder;
import com.yly.cdr.entity.NursingOperation;
import com.yly.cdr.entity.ProcedureOrder;
import com.yly.cdr.entity.TreatmentOrder;
import com.yly.cdr.service.NonDrugOrderService;

@Component
public class NonDrugOrderServiceImpl implements NonDrugOrderService
{
    @Autowired
    private NonDrugOrderDao nonDrugOrderDao;

    @Autowired
    private EntityDao entityDao;

    @Transactional
    public List<NonDrugOrderDto> selectAllOrder(String patientSn,
            String orderStrStartTime, String orderStrEndTime, String orderType,
            String orderDept, String orderPerson, String execDept,
            String orderName, PagingContext pagingContext)
    {
        return nonDrugOrderDao.selectAllOrder(patientSn, orderStrStartTime,
                orderStrEndTime, orderType, orderDept, orderPerson, execDept,
                orderName, pagingContext);
    }

    @Transactional
    public List<NonDrugOrderDto> selectAllOrder(String patientSn,
            NonDrugOrderListSearchPra nonDrugOrderPra,
            PagingContext pagingContext)
    {
        return nonDrugOrderDao.selectAllOrderOb(patientSn, nonDrugOrderPra,
                pagingContext);
    }
    
    @Transactional
    public List<NonDrugOrderDto> selectAllOrder(String patientSn,
            NonDrugOrderListSearchPra nonDrugOrderPra,String flag,
            PagingContext pagingContext)
    {
        return nonDrugOrderDao.selectAllOrderOb(patientSn, nonDrugOrderPra,flag,
                pagingContext);
    }


    @Transactional
    public Object selectOrders(int orderFlag, String orderSn)
    {
        Object obj = null;
        switch (orderFlag)
        {
        case NonDrugOrderDto.TREATMENT_ORDER:
            // 查询诊疗医嘱详细
            obj = entityDao.selectById(TreatmentOrder.class, orderSn);
            break;
        case NonDrugOrderDto.CARE_ORDER:
            // 查询护理医嘱详细
            obj = entityDao.selectById(CareOrder.class, orderSn);
            break;
        case NonDrugOrderDto.PROCEDURE_ORDER:
            // 查询手术医嘱详细
            obj = entityDao.selectById(ProcedureOrder.class, orderSn);
            break;
        case NonDrugOrderDto.EXAMINATION_ORDER:
            // 查询检查医嘱详细
            obj = entityDao.selectById(ExaminationOrder.class, orderSn);
            break;
        case NonDrugOrderDto.LAB_ORDER:
            // 查询检验医嘱详细
            obj = entityDao.selectById(LabOrder.class, orderSn);
            break;
        case NonDrugOrderDto.GENERAL_ORDER:
            // 查询其他医嘱详细
            obj = entityDao.selectById(GeneralOrder.class, orderSn);
            break;
        case NonDrugOrderDto.CONSULTATION_ORDER:
            // 查询会诊医嘱详细
            obj = entityDao.selectById(ConsultationOrder.class, orderSn);
            break;
        default:
            break;
        }
        return obj;
    }

    @Transactional
    public Object selectOrdersByCondition(int orderFlag, Map<String, Object> map)
    {
        Object obj = null;
        switch (orderFlag)
        {
        case NonDrugOrderDto.NURSING_OPERATION:
            // 查询护理诊疗操作列表
            obj = entityDao.selectByCondition(NursingOperation.class, map);
            break;
        default:
            break;
        }
        return obj;
    }

    // Get NursingOperation
    @Transactional
    public List<NursingOperation> selectNursingOperationByCondition(
            String orderSn)
    {
        return nonDrugOrderDao.selectNursingOperationByCondition(orderSn);
    }

    // $Author:bin_zhang
    // $Date:2012/9/28 10:42
    // $[BUG]BUG0010082 ADD BEGIN
    @Override
    @Transactional
    public List<NonDrugOrderDto> selectOrder(String visitSn)
    {
        List<NonDrugOrderDto> NonDrug = nonDrugOrderDao.selectOrder(visitSn);
        return NonDrug;
    }

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
    @Override
    @Transactional
    public List<NonDrugOrderDto> selectOrder(String visitSn,PagingContext pagingContext)
    {
        List<NonDrugOrderDto> NonDrug = nonDrugOrderDao.selectOrder(visitSn,pagingContext);
        return NonDrug;
    }
    // $[BUG]0032417 ADD BEGIN

    // $Author :jin_peng
    // $Date : 2012/11/22 13:34$
    // [BUG]0011026 MODIFY BEGIN
    // $Author :tong_meng
    // $Date : 2012/9/29 14:45$
    // [BUG]0010038 ADD BEGIN
    @Override
    @Transactional
    public List<VisitDateDto> selectVisitDate(String patientSn)
    {
        // $Author :tong_meng
        // $Date : 2012/9/29 17:22$
        // [BUG]0010913 MODIFY BEGIN
        return getActuallyVisitDate(nonDrugOrderDao.selectVisitDate(patientSn));
        // [BUG]0010913 MODIFY END
    }

    // [BUG]0010038 ADD END
    // [BUG]0011026 MODIFY END

    // $Author :jin_peng
    // $Date : 2012/11/22 13:34$
    // [BUG]0011026 ADD BEGIN
    /**
     * 针对同一天多次就诊情况处理就诊日期显示方式
     * @param visitDateDtoList 就诊日期集合
     * @return 处理完成的就诊日期集合
     */
    private List<VisitDateDto> getActuallyVisitDate(
            List<VisitDateDto> visitDateDtoList)
    {
        List<VisitDateDto> temp = new ArrayList<VisitDateDto>();
        List<String> visitDates = new ArrayList<String>();

        for (VisitDateDto visitDateDto : visitDateDtoList)
        {
            visitDates.add(visitDateDto.getVisitDate());
        }

        for (VisitDateDto visitDateDto : visitDateDtoList)
        {
            String visitDateFullStr = visitDateDto.getVisitDate();

            // 针对同一天多次就诊情况确定就诊日期显示方式
            if (visitDateFullStr.indexOf("(1)") != -1)
            {
                String visitDateStr = visitDateFullStr.substring(0,
                        visitDateFullStr.length() - 3);
                String compareStr = visitDateStr + "(2)";

                if (!visitDates.contains(compareStr))
                {
                    visitDateDto.setVisitDate(visitDateStr);
                }
                else
                {
                    visitDateDto.setVisitDate(visitDateFullStr);
                }

                temp.add(visitDateDto);
            }
            else
            {
                temp.add(visitDateDto);
            }
        }

        return temp;
    }
    // [BUG]0011026 ADD END
}
