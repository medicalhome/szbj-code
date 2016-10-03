/**
 * Copyright (c) 2012—2012 Founder International Co.Ltd. All rights reserved.
 */
package com.yly.cdr.web;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

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

import com.founder.fasf.web.paging.PagingContext;
import com.founder.fasf.web.paging.PagingContextHolder;
import com.yly.cdr.core.Constants;
import com.yly.cdr.dto.NonDrugOrderDto;
import com.yly.cdr.dto.PhysicalExaminationDto;
import com.yly.cdr.dto.ProcedureRequestDto;
import com.yly.cdr.dto.SurgicalProcedureDto;
import com.yly.cdr.dto.procedure.ProcedureListSearchPra;
import com.yly.cdr.entity.Anesthesia;
import com.yly.cdr.entity.AnesthesiaEvent;
import com.yly.cdr.entity.NursingOperation;
import com.yly.cdr.entity.ProcedureOrder;
import com.yly.cdr.entity.ProcedureParticipants;
import com.yly.cdr.entity.ProcedureRecord;
import com.yly.cdr.entity.SurgicalProcedure;
import com.yly.cdr.service.NonDrugOrderService;
import com.yly.cdr.service.SurgicalProcedureService;
import com.yly.cdr.util.StringUtils;
import com.yly.cdr.web.loginValidation.LoginUserDetails;
import com.yly.cdr.web.util.MutexesOrderHelper;
import com.yly.cdr.web.util.PagingHelper;

/**
 * 用于手术的视图控制类
 * 
 * @author xu_dengfeng
 *
 */
@Controller
@RequestMapping("/procedure")
public class ProcedureController
{
    private static Logger logger = LoggerFactory.getLogger(ProcedureController.class);

    @Autowired
    private SurgicalProcedureService SurgicalProcedureService;
    
    @Autowired
    private NonDrugOrderService unMedOrdService;
    
    @Autowired
    private MutexesOrderHelper mutexesOrderHelper;
    /**
     * V05-701:手术列表
     * 显示手术列表信息画面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/list_{patientSn}")
    public ModelAndView list(WebRequest request,
            @PathVariable("patientSn") String patientSn, HttpSession session)
    {
        // 初始化pagingContext
        PagingContext pagingContext = PagingContextHolder.getPagingContext();
        // 定义页面检索元素DTO
        ProcedureListSearchPra procedureListSearchPra = new ProcedureListSearchPra();

        // 如果是时间轴视图或住院视图运行到此则传入已定义好的参数dto
        if (request.getAttribute("commonParameters", WebRequest.SCOPE_REQUEST) != null)
        {
            procedureListSearchPra = (ProcedureListSearchPra) request.getAttribute(
                    "commonParameters", WebRequest.SCOPE_REQUEST);
        }

        // 是否调用SESSION
        String senSave = request.getParameter("senSave");
        if ("true".equals(senSave))
        {
            procedureListSearchPra = (ProcedureListSearchPra) session.getAttribute("ProcedureListSearchPra");
        }
        else
        {
            // 手术列表
            String operationName = request.getParameter("operationName");
            String operationDateFromStr = request.getParameter("operationDateFromStr");
            String operationDateToStr = request.getParameter("operationDateToStr");
            String surgicalDept = request.getParameter("surgicalDept");
            String zhudao = request.getParameter("zhudao");
//            String mazui = request.getParameter("mazui");
//            String procedureLevel = request.getParameter("procedureLevel");
            String workload = request.getParameter("workload");
//            String healingGrade = request.getParameter("healingGrade");
            String orgCode = request.getParameter("orgCode");

            procedureListSearchPra.setOperationName(operationName);
            procedureListSearchPra.setOperationDateFromStr(operationDateFromStr);
            procedureListSearchPra.setOperationDateToStr(operationDateToStr);
            // 下拉框未选择时处理
            procedureListSearchPra.setSurgicalDept(Constants.OPTION_SELECT.equalsIgnoreCase(surgicalDept) ? null
                    : surgicalDept);
            procedureListSearchPra.setZhudao(zhudao);
//            procedureListSearchPra.setMazui(mazui);
//            procedureListSearchPra.setProcedureLevel(Constants.OPTION_SELECT.equalsIgnoreCase(procedureLevel) ? null
//                    : procedureLevel);
            procedureListSearchPra.setWorkload(workload);
//            procedureListSearchPra.setHealingGrade(Constants.OPTION_SELECT.equalsIgnoreCase(healingGrade) ? null
//                    : healingGrade);
            procedureListSearchPra.setOrgCode(Constants.OPTION_SELECT.equalsIgnoreCase(orgCode)?null:orgCode);
            session.setAttribute("ProcedureListSearchPra",
                    procedureListSearchPra);
        }
        // 设置页面初始每页显示条目数,pagingContext默认值是10，(其他参数也可以修正，会影响检索结果)可根据需要修正。
        // Author:jia_yanqing
        // Date : 2013/1/11 15:30
        // [BUG]0012699 ADD BEGIN
        LoginUserDetails userDetails = (LoginUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String procedureRowsCntStr = userDetails.getUserSettings().getRowsPerPage();
        // [BUG]0012699 ADD END
        pagingContext.setRowCnt(Integer.parseInt(procedureRowsCntStr));
        // 调用业务层逻辑查询分页控制时对象集合，pagingContext返回值中会覆写totalRowCnt totalPageCnt
        //SurgicalProcedureDto[] list_paging = null;
        ProcedureRequestDto[] list_paging = null;
        logger.debug(
                "检索条件：手术名称={}, 手术日期={}, 手术科室={}, 手术医生={}, 手术台={}, 医疗机构={}",
                new Object[] { procedureListSearchPra.getOperationName(),
                        procedureListSearchPra.getOperationDateFromStr(),
                        procedureListSearchPra.getOperationDateToStr(),
                        procedureListSearchPra.getSurgicalDept(),
                        procedureListSearchPra.getZhudao(),
//                        procedureListSearchPra.getMazui(),
//                        procedureListSearchPra.getProcedureLevel(),
                        procedureListSearchPra.getWorkload(),
//                        procedureListSearchPra.getHealingGrade(),
                        procedureListSearchPra.getOrgCode() });
        
        // Author: tong_meng
        // Date : 2013/6/4 15:30
        // [BUG]0033455 ADD BEGIN 
        ModelAndView mav = new ModelAndView();
        String gotoType = request.getParameter("gotoType");
        String procedureSn = request.getParameter("procedureSn");
        String special = request.getParameter("special");
        if("part".equals(gotoType))
        {
            int showLeftRowCnt = Integer.parseInt(Constants.getDETAIL_TABS_COUNT());
            pagingContext.setRowCnt(showLeftRowCnt);
//            list_paging = SurgicalProcedureService.selectSurgical(patientSn, procedureListSearchPra, pagingContext);
            list_paging = SurgicalProcedureService.selectProcedureList(patientSn, procedureListSearchPra, pagingContext);
            mav.addObject("pageNo",pagingContext.getPageNo());
            mav.setViewName("/procedure/procedureDetailMenuPartChart");
        }
        else if("procedure".equals(gotoType)||"procedure_timer".equals(gotoType)) 
        {
            int showLeftRowCnt = Integer.parseInt(Constants.getDETAIL_TABS_COUNT());
            pagingContext.setRowCnt(showLeftRowCnt);
            boolean overflowFlag = true;
            
            if(StringUtils.isEmpty(special))
//                list_paging = SurgicalProcedureService.selectSurgical(patientSn, procedureListSearchPra, pagingContext);
            	list_paging = SurgicalProcedureService.selectProcedureList(patientSn, procedureListSearchPra, pagingContext);
            else
//                list_paging = SurgicalProcedureService.selectSurgical(patientSn, procedureListSearchPra, null);
            	list_paging = SurgicalProcedureService.selectProcedureList(patientSn, procedureListSearchPra, null);

            for (int count = 0; count < showLeftRowCnt - list_paging.length; )
            {
                list_paging = Arrays.copyOf(list_paging, list_paging.length + 1);
//                list_paging[list_paging.length - 1] = new SurgicalProcedureDto();
                list_paging[list_paging.length - 1] = new ProcedureRequestDto();
                overflowFlag = false;
            }
            if(overflowFlag)
                mav.addObject("overflow", "auto");
            else
                mav.addObject("overflow", "hidden");
            mav.addObject("procedureListSearchPra", procedureListSearchPra);
            mav.addObject("procedureSn", procedureSn);
            mav.setViewName("/procedure/procedureDetailMenuPart");
        }
        else
        {
//            list_paging = SurgicalProcedureService.selectSurgical(patientSn, procedureListSearchPra, pagingContext);
            list_paging = SurgicalProcedureService.selectProcedureList(patientSn, procedureListSearchPra, pagingContext);
            // 迁移到指定页面
            mav.setViewName("/procedure/procedureList");
        }
        // 设置每行显示查询对象
        mav.addObject("Procedure", list_paging);
        // [BUG]0033455 ADD END 
        
        
        // 检索对象为空check
        if (list_paging == null || list_paging.length == 0)
        {
            logger.debug("没有检索对象!");
            // 页面显示页面数赋值0
            pagingContext.setPageNo(0);
        }

        // 页面初始化分页页码显示列表
        PagingHelper.initPaging(pagingContext);

        // 加载pagingContext对象
        pagingContext.setRows(list_paging);

        mav.addObject("pagingContext", pagingContext);
        logger.debug(
                ":当前总页数={},当前总条数={}",
                new Object[] { pagingContext.getTotalPageCnt(),
                        pagingContext.getTotalRowCnt() });
        // 加载页面检索项目
        mav.addObject("patientSn", patientSn);
        mav.addObject("procedureListSearchPra", procedureListSearchPra);
        // 动态加载检索框
        String conditionValue = request.getParameter("conditionValue");
        mav.addObject("conditionValue", conditionValue == null ? "off"
                : conditionValue);
        // 标识符清空
        mav.addObject("senSave", null);
        // 迁移到指定页面
        // 根据不同界面显示不同detail的连接
        mav.addObject("gotoType", gotoType);

        return mav;
    }
    
    // Author:chang_xuewen
    // Date : 2013/10/25 13:59
    // [BUG]0038443 ADD BEGIN
    /**
     * V05-701:手术列表-住院视图
     * 显示手术列表信息画面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/getMainList")
    public ModelAndView getMainList(WebRequest request, HttpSession session)
    {
    	ModelAndView mav = new ModelAndView();
    	mav.addObject("overflow", "auto");
    	mav.setViewName("/procedure/procedureDocDetailMenuPart");
    	return mav;
    }
    
    /**
     * V05-701:手术列表-住院视图
     * 显示手术列表信息画面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/inpatientList_{patientSn}")
    public ModelAndView inpatientList(WebRequest request,
            @PathVariable("patientSn") String patientSn, HttpSession session)
    {
        // 初始化pagingContext
        PagingContext pagingContext = PagingContextHolder.getPagingContext();
        // 定义页面检索元素DTO
        ProcedureListSearchPra procedureListSearchPra = new ProcedureListSearchPra();

        // 如果是时间轴视图或住院视图运行到此则传入已定义好的参数dto
        if (request.getAttribute("commonParameters", WebRequest.SCOPE_REQUEST) != null)
        {
            procedureListSearchPra = (ProcedureListSearchPra) request.getAttribute(
                    "commonParameters", WebRequest.SCOPE_REQUEST);
        }

        // 是否调用SESSION
        String senSave = request.getParameter("senSave");
        if ("true".equals(senSave))
        {
            procedureListSearchPra = (ProcedureListSearchPra) session.getAttribute("ProcedureListSearchPra");
        }
        else
        {
            // 手术列表
            String operationName = request.getParameter("operationName");
            String inpatientDate = request.getParameter("inpatientDate");

            procedureListSearchPra.setOperationName(operationName);
            procedureListSearchPra.setInpatientDate(inpatientDate);
            session.setAttribute("ProcedureListSearchPra",
                    procedureListSearchPra);
        }
        // 设置页面初始每页显示条目数,pagingContext默认值是10，(其他参数也可以修正，会影响检索结果)可根据需要修正。
        // Author:jia_yanqing
        // Date : 2013/1/11 15:30
        // [BUG]0012699 ADD BEGIN
        LoginUserDetails userDetails = (LoginUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String procedureRowsCntStr = userDetails.getUserSettings().getRowsPerPage();
        // [BUG]0012699 ADD END
        pagingContext.setRowCnt(Integer.parseInt(procedureRowsCntStr));
        // 调用业务层逻辑查询分页控制时对象集合，pagingContext返回值中会覆写totalRowCnt totalPageCnt
        ProcedureOrder[] list_paging = null;
        logger.debug(
                "检索条件：手术名称={}, 手术日期={}, 手术科室={}, 主刀={}, 麻醉={}, 手术等级={}, 工作量={}, 治愈等级={}",
                new Object[] { procedureListSearchPra.getOperationName(),
                        procedureListSearchPra.getOperationDateFromStr(),
                        procedureListSearchPra.getOperationDateToStr(),
                        procedureListSearchPra.getSurgicalDept(),
                        procedureListSearchPra.getZhudao(),
                        procedureListSearchPra.getMazui(),
                        procedureListSearchPra.getProcedureLevel(),
                        procedureListSearchPra.getWorkload(),
                        procedureListSearchPra.getHealingGrade() });
        
        // Author: tong_meng
        // Date : 2013/6/4 15:30
        // [BUG]0033455 ADD BEGIN 
        ModelAndView mav = new ModelAndView();
        String gotoType = request.getParameter("gotoType");
        String orderSn = request.getParameter("procedureSn");
        String special = request.getParameter("special");
        if("procedure_doc".equals(gotoType)) 
        {
            int showLeftRowCnt = Integer.parseInt(Constants.getDETAIL_TABS_COUNT());
            pagingContext.setRowCnt(showLeftRowCnt);
            boolean overflowFlag = true;
            
            if(StringUtils.isEmpty(special))
                list_paging = SurgicalProcedureService.selectProcedureListInpatientView(patientSn, procedureListSearchPra, pagingContext);
            else
                list_paging = SurgicalProcedureService.selectProcedureListInpatientView(patientSn, procedureListSearchPra, null);

            /*for (int count = 0; count < showLeftRowCnt - list_paging.length; )
            {
                list_paging = Arrays.copyOf(list_paging, list_paging.length + 1);
                list_paging[list_paging.length - 1] = new ProcedureOrder();
                overflowFlag = false;
            }*/
            if(overflowFlag)
                mav.addObject("overflow", "auto");
            else
                mav.addObject("overflow", "hidden");
            mav.addObject("procedureListSearchPra", procedureListSearchPra);
            mav.addObject("viewFlag",true);
            mav.addObject("orderSn", orderSn);
            mav.setViewName("/procedure/procedureDetailMenuPartChart");
        }       
        // 设置每行显示查询对象
        mav.addObject("Procedure", list_paging);
        // [BUG]0033455 ADD END 
        
        
        // 检索对象为空check
        if (list_paging == null || list_paging.length == 0)
        {
            logger.debug("没有检索对象!");
            // 页面显示页面数赋值0
            pagingContext.setPageNo(0);
        }

        // 页面初始化分页页码显示列表
        PagingHelper.initPaging(pagingContext);

        // 加载pagingContext对象
        pagingContext.setRows(list_paging);

        mav.addObject("pagingContext", pagingContext);
        logger.debug(
                ":当前总页数={},当前总条数={}",
                new Object[] { pagingContext.getTotalPageCnt(),
                        pagingContext.getTotalRowCnt() });
        // 加载页面检索项目
        mav.addObject("patientSn", patientSn);
        mav.addObject("procedureListSearchPra", procedureListSearchPra);
        // 动态加载检索框
        String conditionValue = request.getParameter("conditionValue");
        mav.addObject("conditionValue", conditionValue == null ? "off"
                : conditionValue);
        // 标识符清空
        mav.addObject("senSave", null);
        // 迁移到指定页面

        return mav;
    }
    // [BUG]0038443 ADD END

    /**
     * V05-702:手术详细
     * 显示手术详细信息画面
     * 
     * @param reportSn
     * @return
     * @throws Exception
     */
    @RequestMapping("/procedure_{orderSn}")
    public ModelAndView detail(@PathVariable("orderSn") String orderSn,
            WebRequest request) throws Exception
    {
//        String p11 = "";
//        String p22 = "";
//        String p33 = "";
//        String p77 = "";
//        SurgicalProcedure sp = this.SurgicalProcedureService.selectProc(procedureSn);
//
//        // $Author :tong_meng
//        // $Date : 2012/10/25 10:00$
//        // [BUG]0010694 ADD BEGIN
//        ProcedureOrder procedureOrder = SurgicalProcedureService.getProcedureOrder(StringUtils.BigDecimalToStr(sp.getOrderSn()));
//        // [BUG]0010694 ADD END
//
//        BigDecimal aneSn = sp.getAnesthesiaSn();
//        Anesthesia ane = this.SurgicalProcedureService.selectAnesthesia(aneSn.toString());
//        List<ProcedureParticipants> pp = this.SurgicalProcedureService.selectProparti(procedureSn);
//        for (int i = 0; i < pp.size(); i++)
//        {
//            // 麻醉医生
//            if (pp.get(i).getParticipantIdentity().equals("31"))
//            {
//                p77 += pp.get(i).getParticipantName() + ",";
//
//            }
//            // 主刀医生
//            if (pp.get(i).getParticipantIdentity().equals("11"))
//            {
//                p11 += pp.get(i).getParticipantName() + ",";
//            }
//            // 助1
//            if (pp.get(i).getParticipantIdentity().equals("12"))
//            {
//                p22 += pp.get(i).getParticipantName() + ",";
//            }
//            // 助2
//            if (pp.get(i).getParticipantIdentity().equals("13"))
//            {
//                p33 += pp.get(i).getParticipantName() + ",";
//            }
//        }
//        if (!p77.equals(""))
//        {
//            p77 = p77.substring(0, p77.length() - 1);
//        }
//        if (!p11.equals(""))
//        {
//            p11 = p11.substring(0, p11.length() - 1);
//        }
//        if (!p22.equals(""))
//        {
//            p22 = p22.substring(0, p22.length() - 1);
//        }
//        if (!p33.equals(""))
//        {
//            p33 = p33.substring(0, p33.length() - 1);
//        }
//        List<AnesthesiaEvent> ae = this.SurgicalProcedureService.selectAnesEvent(aneSn.toString());
//        PhysicalExaminationDto[] pe = this.SurgicalProcedureService.selectPhyExamination(aneSn.toString());
//        ModelAndView mav = new ModelAndView();
//        mav.addObject("sp", sp);
//        mav.addObject("po", procedureOrder);
//        mav.addObject("p11", p11);
//        mav.addObject("p22", p22);
//        mav.addObject("p33", p33);
//        mav.addObject("p77", p77);
//        mav.addObject("ane", ane);
//        mav.addObject("ae", ae);
//        mav.addObject("pe", pe);
//        mav.setViewName("/procedure/procedureDetail");
//        return mav;
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
     * V05-703:手术申请详细
     * 显示手术申请记录详细信息画面
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping("/request_{orderSn}")
    public ModelAndView requestDetail(@PathVariable("orderSn") String orderSn)
            throws Exception
    {
        ProcedureOrder pp = this.SurgicalProcedureService.getProcedureOrder(orderSn);
        ModelAndView mav = new ModelAndView();
        mav.addObject("pp", pp);
        mav.setViewName("/procedure/procedureRequestDetail");
        return mav;
    }

    /**
     * V05-704:手术操作列表
     * 显示手术操作列表信息画面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/operation_{procedureSn}")
    public ModelAndView operationList(
            @PathVariable("procedureSn") String procedureSn) throws Exception
    {
        List<ProcedureRecord> pr = this.SurgicalProcedureService.getProcedureList(procedureSn);
        ModelAndView mav = new ModelAndView();
        mav.addObject("pr", pr);
        mav.setViewName("/procedure/procedureOperationList");
        return mav;
    }
}
