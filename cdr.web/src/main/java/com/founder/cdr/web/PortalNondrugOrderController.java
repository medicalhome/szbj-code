/**
 * Copyright (c) 2012—2012 Founder International Co.Ltd. All rights reserved.
 */
package com.founder.cdr.web;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.founder.cdr.core.Constants;
import com.founder.cdr.dto.NonDrugOrderDto;
import com.founder.cdr.dto.VisitDateDto;
import com.founder.cdr.dto.order.NonDrugOrderListSearchPra;
import com.founder.cdr.entity.CareOrder;
import com.founder.cdr.entity.ConsultationOrder;
import com.founder.cdr.entity.GeneralOrder;
import com.founder.cdr.entity.NursingOperation;
import com.founder.cdr.entity.Patient;
import com.founder.cdr.entity.PatientCrossIndex;
import com.founder.cdr.entity.ProcedureOrder;
import com.founder.cdr.entity.TreatmentOrder;
import com.founder.cdr.service.NonDrugOrderService;
import com.founder.cdr.service.PatientService;
import com.founder.cdr.util.DateUtils;
import com.founder.cdr.util.StringUtils;
import com.founder.cdr.web.loginValidation.LoginUserDetails;
import com.founder.cdr.web.util.MutexesOrderHelper;
import com.founder.cdr.web.util.PagingHelper;
import com.founder.fasf.web.paging.PagingContext;
import com.founder.fasf.web.paging.PagingContextHolder;

/**
 * 用于非药物医嘱的视图控制类
 * 
 * @author jinpeng
 *
 */
@Controller
@RequestMapping("/portalOrder")
public class PortalNondrugOrderController
{
    private static Logger logger = LoggerFactory.getLogger(PortalNondrugOrderController.class);

    @Autowired
    private NonDrugOrderService unMedOrdService;
    
    @Autowired
    private PatientService patientService;
    
    @Autowired
    private MutexesOrderHelper mutexesOrderHelper;
    
    @Autowired
    CommonController controller;

    /**
     * V05-311:非药物医嘱列表
     * 非药物医嘱列表信息画面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/list_{patientSn}")
    public ModelAndView list(@PathVariable("patientSn") String patientSn,
            WebRequest request, HttpSession session)
    {
        // 初始化pagingContext
        PagingContext pagingContext = PagingContextHolder.getPagingContext();
      //获取患者信息
  		Patient patient = patientService.getPatient(patientSn);
  		String age =DateUtils.caluAge(patient.getBirthDate(),new Date());
      		// 根据域ID，患者Lid从患者交叉索引查询患者相关信息
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("patientDomain", request.getParameter("patientDomain"));
        condition.put("patientLid", request.getParameter("patientId"));
        PatientCrossIndex patientCrossIndex = patientService.getPatientCrossIndexByCondition(condition);
        // 定义页面检索元素DTO
        NonDrugOrderListSearchPra nonDrugOrderPra = new NonDrugOrderListSearchPra();
      //就诊次数
        nonDrugOrderPra.setVisitTimes(request.getParameter("visitTimes"));
        // 如果是时间轴视图或住院视图运行到此则传入已定义好的参数dto
        if (request.getAttribute("commonParameters", WebRequest.SCOPE_REQUEST) != null)
        {
            nonDrugOrderPra = (NonDrugOrderListSearchPra) request.getAttribute(
                    "commonParameters", WebRequest.SCOPE_REQUEST);
        }

        // 是否调用SESSION
        String senSave = request.getParameter("senSave");
        if ("true".equals(senSave))
        {
            nonDrugOrderPra = (NonDrugOrderListSearchPra) session.getAttribute("NonDrugOrderPra");
        }
        else
        {
            // 获取表单提交查询条件
            String orderPerson = request.getParameter("orderPerson");
            String orderName = request.getParameter("orderName");
            String orderStrStartTime = request.getParameter("orderStrStartTime");
            String orderStrEndTime = request.getParameter("orderStrEndTime");
            String orderType = request.getParameter("orderType");
            String orderDept = request.getParameter("orderDept");
            String execDept = request.getParameter("execDept");                 
            String orgCode = request.getParameter("orgCodeFlag");          

            // $Author :jin_peng
            // $Date : 2012/11/22 13:34$
            // [BUG]0011026 MODIFY BEGIN
            // $Author :tong_meng
            // $Date : 2012/9/29 14:45$
            // [BUG]0010038 ADD BEGIN
            // 就诊内部序列号
            String visitDateSn = request.getParameter("visitDateSn");
            // 患者的就诊日期集合
            List<VisitDateDto> visitDateList = unMedOrdService.selectVisitDate(patientSn);
            // [BUG]0010038 ADD END

            // 开嘱日期
            nonDrugOrderPra.setOrderStrStartTime(orderStrStartTime);
            nonDrugOrderPra.setOrderStrEndTime(orderStrEndTime);
            // 医嘱类型 下拉框中选择的value值为-1时按照null处理
            nonDrugOrderPra.setOrderType(Constants.OPTION_SELECT.equalsIgnoreCase(orderType) ? null
                    : orderType);
            // 开嘱科室
            nonDrugOrderPra.setOrderDept(Constants.OPTION_SELECT.equalsIgnoreCase(orderDept) ? null
                    : orderDept);
            // 开嘱人
            nonDrugOrderPra.setOrderPerson(orderPerson);
            // 执行科室
            nonDrugOrderPra.setExecDept(Constants.OPTION_SELECT.equalsIgnoreCase(execDept) ? null
                    : execDept);
            // 项目名称
            nonDrugOrderPra.setOrderName(orderName);
            // $Author :tong_meng
            // $Date : 2012/9/29 14:45$
            // [BUG]0010038 ADD BEGIN
            nonDrugOrderPra.setVisitDateSn(Constants.OPTION_SELECT.equalsIgnoreCase(visitDateSn) ? null
                    : visitDateSn);
            nonDrugOrderPra.setVisitDateList(visitDateList);
            // [BUG]0010038 ADD END
            nonDrugOrderPra.setOrgCode(Constants.OPTION_SELECT.equalsIgnoreCase(orgCode) ? null
                    : orgCode);
            session.setAttribute("NonDrugOrderPra", nonDrugOrderPra);
        }

        // [BUG]0011026 MODIFY END

        // 设置页面初始每页显示条目数,pagingContext默认值是10，(其他参数也可以修正，会影响检索结果)可根据需要修正。
        // Author:jia_yanqing
        // Date : 2013/1/11 15:30
        // [BUG]0012699 ADD BEGIN
        LoginUserDetails userDetails = (LoginUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String nondrugRowsCntStr = userDetails.getUserSettings().getRowsPerPage();
        // [BUG]0012699 ADD END
        pagingContext.setRowCnt(Integer.parseInt(nondrugRowsCntStr));
        // 查询分页控制时对象集合
        List<NonDrugOrderDto> orderList = null;
        logger.debug("检索条件：开嘱日期={}, 医嘱类型={}, 开嘱科室={},开嘱人={},执行科室={},项目名称={}",
                new Object[] {});
        
        // Author: tong_meng
        // Date : 2013/6/4 15:30
        // [BUG]0033455 ADD BEGIN 
        ModelAndView mav = new ModelAndView();
        String gotoType = request.getParameter("gotoType");
        String orderSn = request.getParameter("orderSn");
        String special = request.getParameter("special");
        if("part".equals(gotoType))
        {
            int showLeftRowCnt = Integer.parseInt(Constants.getDETAIL_TABS_COUNT());
            pagingContext.setRowCnt(showLeftRowCnt);
            orderList = unMedOrdService.selectAllOrder(patientSn, nonDrugOrderPra, pagingContext);
            mav.addObject("pageNo",pagingContext.getPageNo());
            mav.setViewName("/order/nonDrugDetailMenuPartChart");
        }
        else if("nondrug".equals(gotoType)) 
        {
            int showLeftRowCnt = Integer.parseInt(Constants.getDETAIL_TABS_COUNT());
            pagingContext.setRowCnt(showLeftRowCnt);
            boolean overflowFlag = true;
            
            if(StringUtils.isEmpty(special))
                orderList = unMedOrdService.selectAllOrder(patientSn, nonDrugOrderPra, pagingContext);
            else
                orderList = unMedOrdService.selectAllOrder(patientSn, nonDrugOrderPra, null);
            
            for (int count = 0; count < showLeftRowCnt - orderList.size(); )
            {
                orderList.add(null);
                overflowFlag = false;
            }
            if(overflowFlag)
                mav.addObject("overflow", "auto");
            else
                mav.addObject("overflow", "hidden");
            mav.addObject("nonDrugOrderPra", nonDrugOrderPra);
            mav.addObject("orderSn", orderSn);
            mav.setViewName("/order/nonDrugDetailMenuPart");
        }
        else if("nondrug_timer".equals(gotoType)) 
        {
            int showLeftRowCnt = Integer.parseInt(Constants.getDETAIL_TABS_COUNT());
            pagingContext.setRowCnt(showLeftRowCnt);
            boolean overflowFlag = true;
            
            if(StringUtils.isEmpty(special))
                orderList = unMedOrdService.selectAllOrder(patientSn, nonDrugOrderPra, "inp",pagingContext);
            else
                orderList = unMedOrdService.selectAllOrder(patientSn, nonDrugOrderPra, "inp", null);
            
            for (int count = 0; count < showLeftRowCnt - orderList.size(); )
            {
                orderList.add(null);
                overflowFlag = false;
            }
            if(overflowFlag)
                mav.addObject("overflow", "auto");
            else
                mav.addObject("overflow", "hidden");
            mav.addObject("nonDrugOrderPra", nonDrugOrderPra);
            mav.addObject("orderSn", orderSn);
            mav.setViewName("/order/nonDrugDetailMenuPart");
        }
        else
        {
            orderList = unMedOrdService.selectAllOrder(patientSn, nonDrugOrderPra, "inp", pagingContext );
            // 迁移到指定页面
            mav.setViewName("/portal/portalOrder/portalNonDrugOrderList");
        }
        // 设置每行显示查询对象
        mav.addObject("orderList", orderList);
        // [BUG]0033455 ADD END 
        
        // 检索对象为空check
        if (orderList == null || orderList.size() == 0)
        {
            logger.debug("没有检索对象!");
            // 页面显示页面数赋值0
            pagingContext.setPageNo(0);
        }
        
        if(orderList !=null)
        {
        for (NonDrugOrderDto nonDrugOrderDto : orderList)
        {
            if(nonDrugOrderDto == null)
                continue;
            switch (Integer.parseInt(nonDrugOrderDto.getOrderFlag()))
            {
            case NonDrugOrderDto.TREATMENT_ORDER:
                // 设置诊疗医嘱详细访问路径
                nonDrugOrderDto.setOrderPath("../order/treatment_"
                    + nonDrugOrderDto.getOrderSn() + ".html");
                break;
            case NonDrugOrderDto.CARE_ORDER:
                // 设置护理医嘱详细访问路径
                nonDrugOrderDto.setOrderPath("../order/care_"
                    + nonDrugOrderDto.getOrderSn() + ".html");
                break;
            case NonDrugOrderDto.PROCEDURE_ORDER:
                // 设置手术医嘱详细访问路径
                nonDrugOrderDto.setOrderPath("../order/procedure_"
                    + nonDrugOrderDto.getOrderSn() + ".html");
                break;
            case NonDrugOrderDto.GENERAL_ORDER:
                // 设置其他医嘱详细访问路径
                nonDrugOrderDto.setOrderPath("../order/general_"
                    + nonDrugOrderDto.getOrderSn() + ".html");
                break;
            case NonDrugOrderDto.CONSULTATION_ORDER:
                // 设置会诊医嘱详细访问路径
                nonDrugOrderDto.setOrderPath("../order/consultation_"
                    + nonDrugOrderDto.getOrderSn() + ".html");
                break;

            default:
                break;
            }
        }
        }

        // 页面初始化分页页码显示列表
        PagingHelper.initPaging(pagingContext);

        mav.addObject("pagingContext", pagingContext);
        logger.debug("：当前总页数={}, 当前总条数={},当前页数={}", new Object[] {
                pagingContext.getTotalPageCnt(),
                pagingContext.getTotalRowCnt(), pagingContext.getPageNo() });
        mav.addObject("orderList", orderList);
        mav.addObject("patientSn", patientSn);
        mav.addObject("nonDrugOrderPra", nonDrugOrderPra);

        // 动态加载检索框
        String conditionValue = request.getParameter("conditionValue");
        mav.addObject("conditionValue", conditionValue == null ? "off"
                : conditionValue);
        // 标识符清空
        mav.addObject("senSave", null);
     // 患者信息
 		mav.addObject("patient", patient);
 		mav.addObject("age", age);
 		mav.addObject("patientCrossIndex", patientCrossIndex);
        // 根据不同界面显示不同detail的连接
        mav.addObject("gotoType", gotoType);

        return mav;
    }

    /**
     * V05-312:诊疗医嘱详细
     * 诊疗医嘱详细画面
     * 
     * @param orderSn
     * @return
     * @throws Exception
     */
    @RequestMapping("/treatment_{orderSn}")
    public ModelAndView treatmentDetail(
            @PathVariable("orderSn") String orderSn)
            throws Exception
    {
        ModelAndView mav = new ModelAndView();
        TreatmentOrder treatmentOrder = (TreatmentOrder) unMedOrdService.selectOrders(
                NonDrugOrderDto.TREATMENT_ORDER, orderSn);

        // Author: wu_jianfeng
        // Date : 2013/3/12 15:30
        // [BUG]0014531 ADD BEGIN
        BigDecimal mutexesOrderSn = treatmentOrder.getMutexesOrderSn();
        String mutexesOrderNoType = treatmentOrder.getMutexesOrderNoType();
        mutexesOrderHelper.setMutexesOrder(mav, mutexesOrderSn,
                mutexesOrderNoType);
        // [BUG]0014531 ADD END

        List<NursingOperation> nursingOperation = unMedOrdService.selectNursingOperationByCondition(orderSn);
        mav.addObject("nursingOperation", nursingOperation);
        mav.addObject("treatmentOrder", treatmentOrder);
        
        //住院————其他医嘱 added by yang_jianbo@2014-4-4
        // 医嘱闭环外部链接显示开关
        if(Constants.COMM_INTERFACE.equals(Constants.ORDER_CLOSED_LOOP)){
	        if(Constants.lstDomainInPatient().contains(treatmentOrder.getPatientDomain())){
	        	 Map<String,String> zyMap = new HashMap<String,String>();
	             zyMap.put("orderType", treatmentOrder.getOrderType());
	             zyMap.put("orderCode", null);
	             zyMap.put("orderLid", treatmentOrder.getOrderLid());
	             
	             Map<Object,Object> map = new HashMap<Object,Object>();
	             map = controller.showClosedCycle(treatmentOrder.getPatientDomain(), zyMap,null, mav,null);
	             if(map!=null){
	             	mav.addObject("showClosedCycle", map.get("showClosedCycle"));
	             	mav.addObject("linkUrl", map.get("linkUrl"));
	             }
	        }
        }
        mav.setViewName("/order/treatmentOrderDetail");

        return mav;
    }

    /**
     * V05-313:护理医嘱详细
     * 护理医嘱详细画面
     * 
     * @param orderSn
     * @return
     * @throws Exception
     */
    @RequestMapping("/care_{orderSn}")
    public ModelAndView careDetail(@PathVariable("orderSn") String orderSn) throws Exception
    {
        ModelAndView mav = new ModelAndView();
        CareOrder careOrder = (CareOrder) unMedOrdService.selectOrders(
                NonDrugOrderDto.CARE_ORDER, orderSn);
        List<NursingOperation> nursingOperation = unMedOrdService.selectNursingOperationByCondition(orderSn);

        // Author: wu_jianfeng
        // Date : 2013/3/12 15:30
        // [BUG]0014531 ADD BEGIN
        BigDecimal mutexesOrderSn = careOrder.getMutexesOrderSn();
        String mutexesOrderNoType = careOrder.getMutexesOrderNoType();
        mutexesOrderHelper.setMutexesOrder(mav, mutexesOrderSn,
                mutexesOrderNoType);
        // [BUG]0014531 ADD END

        //住院————其他医嘱 added by yang_jianbo@2014-4-4
        // 医嘱闭环外部链接显示开关
        if(Constants.COMM_INTERFACE.equals(Constants.ORDER_CLOSED_LOOP)){
	        if(Constants.lstDomainInPatient().contains(careOrder.getPatientDomain())){
		        mav.addObject("nursingOperation", nursingOperation);
		        mav.addObject("careOrder", careOrder);
		        Map<String,String> zyMap = new HashMap<String,String>();
		        zyMap.put("orderType", careOrder.getOrderType());
		        zyMap.put("orderCode", careOrder.getOrderCode());
		        zyMap.put("orderLid", careOrder.getOrderLid());
		        
		        Map<Object,Object> map = new HashMap<Object,Object>();
		        map = controller.showClosedCycle(careOrder.getPatientDomain(), zyMap,null,mav,null);
		        if(map!=null){
	             	mav.addObject("showClosedCycle", map.get("showClosedCycle"));
	             	mav.addObject("linkUrl", map.get("linkUrl"));
	            }
	        }
        }
        mav.setViewName("/order/careOrderDetail");
        return mav;
    }

    // $Author:wei_peng
    // $Date:2012/10/19 15:51
    // [BUG]0010418 DELETE BEGIN
    /**
     * V05-314:检查医嘱详细
     * 检查医嘱详细画面
     * 
     * @param orderSn
     * @return
     * @throws Exception
     */
    /*
     * @RequestMapping("/exam_{orderSn}") public ModelAndView
     * examDetail(@PathVariable("orderSn") String orderSn) throws Exception {
     * ModelAndView mav = new ModelAndView(); ExaminationOrder examinationOrder
     * = (ExaminationOrder)unMedOrdService.selectOrders(NonDrugOrderDto.
     * EXAMINATION_ORDER, orderSn); List<NursingOperation> nursingOperation =
     * unMedOrdService.selectNursingOperationByCondition(orderSn);
     * mav.addObject("examinationOrder",examinationOrder);
     * mav.addObject("nursingOperation",nursingOperation);
     * mav.setViewName("/order/examOrderDetail"); return mav; }
     */

    /**
     * V05-314:检验医嘱详细
     * 检验医嘱详细画面
     * 
     * @param orderSn
     * @return
     * @throws Exception
     */
    /*
     * @RequestMapping("/lab_{orderSn}") public ModelAndView
     * labDetail(@PathVariable("orderSn") String orderSn) throws Exception {
     * ModelAndView mav = new ModelAndView(); LabOrder labOrder =
     * (LabOrder)unMedOrdService.selectOrders(NonDrugOrderDto.LAB_ORDER,
     * orderSn); List<NursingOperation> nursingOperation =
     * unMedOrdService.selectNursingOperationByCondition(orderSn);
     * mav.addObject("nursingOperation",nursingOperation);
     * mav.addObject("labOrder",labOrder);
     * mav.setViewName("/order/labOrderDetail"); return mav; }
     */
    // [BUG]0010418 DELETE END

    /**
     * V05-315:手术医嘱详细
     * 手术医嘱详细画面
     * 
     * @param orderSn
     * @return
     * @throws Exception
     */
    @RequestMapping("/procedure_{orderSn}")
    public ModelAndView procedureDetail(@PathVariable("orderSn") String orderSn)
            throws Exception
    {
        ModelAndView mav = new ModelAndView();
        ProcedureOrder procedureOrder = (ProcedureOrder) unMedOrdService.selectOrders(
                NonDrugOrderDto.PROCEDURE_ORDER, orderSn);
        List<NursingOperation> nursingOperation = unMedOrdService.selectNursingOperationByCondition(orderSn);

        // Author: wu_jianfeng
        // Date : 2013/3/12 15:30
        // [BUG]0014531 ADD BEGIN
        BigDecimal mutexesOrderSn = procedureOrder.getMutexesOrderSn();
        String mutexesOrderNoType = procedureOrder.getMutexesOrderNoType();
        mutexesOrderHelper.setMutexesOrder(mav, mutexesOrderSn,
                mutexesOrderNoType);
        // [BUG]0014531 ADD END

        mav.addObject("nursingOperation", nursingOperation);
        mav.addObject("procedureOrder", procedureOrder);
        mav.setViewName("/order/procedureOrderDetail");
        return mav;
    }

    /**
     * V05-315:其他医嘱详细
     * 其他医嘱详细画面
     * 
     * @param orderSn
     * @return
     * @throws Exception
     */
    @RequestMapping("/general_{orderSn}")
    public ModelAndView generalDetail(@PathVariable("orderSn") String orderSn)
            throws Exception
    {
        ModelAndView mav = new ModelAndView();
        GeneralOrder generalOrder = (GeneralOrder) unMedOrdService.selectOrders(
                NonDrugOrderDto.GENERAL_ORDER, orderSn);
        mav.addObject("generalOrder", generalOrder);
        List<NursingOperation> nursingOperation = unMedOrdService.selectNursingOperationByCondition(orderSn);

        // Author: wu_jianfeng
        // Date : 2013/3/12 15:30
        // [BUG]0014531 ADD BEGIN
        BigDecimal mutexesOrderSn = generalOrder.getMutexesOrderSn();
        String mutexesOrderNoType = generalOrder.getMutexesOrderNoType();
        mutexesOrderHelper.setMutexesOrder(mav, mutexesOrderSn,
                mutexesOrderNoType);
        // [BUG]0014531 ADD END

        mav.addObject("nursingOperation", nursingOperation);
        
        //住院————其他医嘱 added by yang_jianbo@2014-4-4
        // 医嘱闭环外部链接显示开关
        if(Constants.COMM_INTERFACE.equals(Constants.ORDER_CLOSED_LOOP)){
	        if(Constants.lstDomainInPatient().contains(generalOrder.getPatientDomain())){
		        Map<String,String> zyMap = new HashMap<String,String>();
		        zyMap.put("orderType", generalOrder.getOrderTypeCode());
		        zyMap.put("orderCode", generalOrder.getOrderCode());
		        zyMap.put("orderLid", generalOrder.getOrderLid());
		        
		        Map<Object,Object> map = new HashMap<Object,Object>();
		        map = controller.showClosedCycle(generalOrder.getPatientDomain(),zyMap,null,mav,null);
		        if(map!=null){
	             	mav.addObject("showClosedCycle", map.get("showClosedCycle"));
	             	mav.addObject("linkUrl", map.get("linkUrl"));
	            }
	        }
        }
        mav.setViewName("/order/generalOrderDetail");
        return mav;
    }

    /**
     * V05-315:会诊医嘱详细
     * 会诊医嘱详细画面
     * 
     * @param orderSn
     * @return
     * @throws Exception
     */
    @RequestMapping("/consultation_{orderSn}")
    public ModelAndView consultationDetail(
            @PathVariable("orderSn") String orderSn) throws Exception
    {
        ModelAndView mav = new ModelAndView();
        ConsultationOrder consultationOrder = (ConsultationOrder) unMedOrdService.selectOrders(
                NonDrugOrderDto.CONSULTATION_ORDER, orderSn);
        List<NursingOperation> nursingOperation = unMedOrdService.selectNursingOperationByCondition(orderSn);

        // Author: wu_jianfeng
        // Date : 2013/3/12 15:30
        // [BUG]0014531 ADD BEGIN
        BigDecimal mutexesOrderSn = consultationOrder.getMutexesOrderSn();
        String mutexesOrderNoType = consultationOrder.getMutexesOrderNoType();
        mutexesOrderHelper.setMutexesOrder(mav, mutexesOrderSn,
                mutexesOrderNoType);
        // [BUG]0014531 ADD END

        mav.addObject("nursingOperation", nursingOperation);
        mav.addObject("consultationOrder", consultationOrder);
        mav.setViewName("/order/consultationOrderDetail");
        return mav;
    }
}
