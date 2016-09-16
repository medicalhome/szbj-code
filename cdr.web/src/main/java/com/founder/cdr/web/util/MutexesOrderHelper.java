package com.founder.cdr.web.util;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.founder.cdr.dto.MedicationOrderDto;
import com.founder.cdr.dto.NonDrugOrderDto;
import com.founder.cdr.entity.CareOrder;
import com.founder.cdr.entity.ConsultationOrder;
import com.founder.cdr.entity.ExaminationOrder;
import com.founder.cdr.entity.GeneralOrder;
import com.founder.cdr.entity.LabOrder;
import com.founder.cdr.entity.MedicationOrder;
import com.founder.cdr.entity.ProcedureOrder;
import com.founder.cdr.entity.TreatmentOrder;
import com.founder.cdr.service.CommonService;
import com.founder.cdr.service.DrugListService;
import com.founder.cdr.service.ExamService;
import com.founder.cdr.service.LabService;
import com.founder.cdr.service.NonDrugOrderService;
import com.founder.cdr.web.loginValidation.LoginUserDetails;

@Component
public class MutexesOrderHelper
{
    @Autowired
    private DrugListService drugListService;

    @Autowired
    private NonDrugOrderService unMedOrdService;

    @Autowired
    private LabService labService;

    @Autowired
    private ExamService examService;

    @Autowired
    private CommonService commonService;

    /**
     * 将关联的互斥医嘱及其内部序列号保存到视图对象
     * @param mav 页面视图对象
     * @param mutexesOrderSn 互斥医嘱内部序列号
     * @param mutexesOrderNoType 互斥医嘱类型
     */
    public void setMutexesOrder(ModelAndView mav,
            BigDecimal mutexesOrderSn, String mutexesOrderNoType)
    {
        String mutexesOrderTypeStr = "";
        String mutexesOrderName = "";
        // Author: chang_xuewen
        // Date : 2013/5/20 16:10
        // [BUG]0015197 ADD BEGIN          
        //获取用户sn
        LoginUserDetails userDetails = (LoginUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userSn = userDetails.getUsername();
        // [BUG]0015197 ADD BEGIN
        // 目前互斥医嘱有8个医嘱，药物医嘱，护理医嘱，其他医嘱，诊疗医嘱，检查医嘱，检验医嘱，会诊医嘱，手术医嘱
        if (mutexesOrderSn != null)
        {
            Class<?> orderClass = commonService.getOrderClass(mutexesOrderNoType);
            if(MedicationOrder.class.equals(orderClass))
            {
            	// Author: chang_xuewen
                // Date : 2013/5/20 16:10
                // [BUG]0015197 ADD BEGIN
                MedicationOrderDto mutexesDrugOrder = drugListService.selectDrugList(mutexesOrderSn.toString(), userSn);
                // [BUG]0015197 ADD BEGIN
                mutexesOrderTypeStr = "drugorder";
                mutexesOrderName = mutexesDrugOrder.getApprovedDrugName();
            }
            else if(CareOrder.class.equals(orderClass))
            {
                CareOrder mutexesCareOrder = (CareOrder) unMedOrdService.selectOrders(
                        NonDrugOrderDto.CARE_ORDER, mutexesOrderSn.toString());
                mutexesOrderTypeStr = "careorder";
                mutexesOrderName = mutexesCareOrder.getOrderName();
            }
            else if(GeneralOrder.class.equals(orderClass))
            {
                GeneralOrder mutexesGeneralOrder = (GeneralOrder) unMedOrdService.selectOrders(
                        NonDrugOrderDto.GENERAL_ORDER,
                        mutexesOrderSn.toString());
                mutexesOrderTypeStr = "generalorder";
                mutexesOrderName = mutexesGeneralOrder.getOrderName();
            }
            else if(TreatmentOrder.class.equals(orderClass))
            {
                TreatmentOrder mutexesTreatmentOrder = (TreatmentOrder) unMedOrdService.selectOrders(
                        NonDrugOrderDto.TREATMENT_ORDER,
                        mutexesOrderSn.toString());
                mutexesOrderTypeStr = "treatmentorder";
                mutexesOrderName = mutexesTreatmentOrder.getItemName();
            }
            else if(ConsultationOrder.class.equals(orderClass))
            {
                ConsultationOrder mutexesConsultationOrder = (ConsultationOrder) unMedOrdService.selectOrders(
                        NonDrugOrderDto.CONSULTATION_ORDER,
                        mutexesOrderSn.toString());
                mutexesOrderTypeStr = "consultationorder";
                mutexesOrderName = mutexesConsultationOrder.getOrderName();
            }
            else if(ProcedureOrder.class.equals(orderClass))
            {
                ProcedureOrder mutexesProcedureOrder = (ProcedureOrder) unMedOrdService.selectOrders(
                        NonDrugOrderDto.PROCEDURE_ORDER,
                        mutexesOrderSn.toString());
                mutexesOrderTypeStr = "procedureorder";
                mutexesOrderName = mutexesProcedureOrder.getOrderName();
            }
            else if(ExaminationOrder.class.equals(orderClass))
            {
                ExaminationOrder mutexesExamOrder = examService.selectExamOrderDetail(mutexesOrderSn.toString());
                mutexesOrderTypeStr = "examorder";
                mutexesOrderName = mutexesExamOrder.getItemName();
            }
            else if(LabOrder.class.equals(orderClass))
            {
                LabOrder mutexesLabOrder = labService.selectLabRequest(mutexesOrderSn.toString());
                mutexesOrderTypeStr = "laborder";
                mutexesOrderName = mutexesLabOrder.getItemName();
            }
        }
        mav.addObject("mutexesOrderType", mutexesOrderTypeStr);
        mav.addObject("mutexesOrderName", mutexesOrderName);
        mav.addObject("mutexesOrderSn", mutexesOrderSn);
    }

}
