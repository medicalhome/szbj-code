/**
 * Copyright (c) 2012—2012 Founder International Co.Ltd. All rights reserved.
 */
package com.founder.cdr.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
import com.founder.cdr.dto.DiagnosisDto;
import com.founder.cdr.dto.LabDto;
import com.founder.cdr.dto.diagnosis.DiagnosisListSearchPra;
import com.founder.cdr.service.DiagnosisService;
import com.founder.cdr.util.StringUtils;
import com.founder.cdr.web.loginValidation.LoginUserDetails;
import com.founder.cdr.web.util.PagingHelper;
import com.founder.fasf.web.paging.PagingContext;
import com.founder.fasf.web.paging.PagingContextHolder;

/**
 * 用于诊断的视图控制类
 * 
 * @author liu_jingyang
 *
 */
@Controller
@RequestMapping("/diagnosis")
public class DiagnosisController
{
    private static Logger logger = LoggerFactory.getLogger(DiagnosisController.class);

    @Autowired
    private DiagnosisService diagnosisService;

    /**
     * V05-401:诊断列表
     * 显示诊断列表信息画面
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
        DiagnosisListSearchPra diagnosisListSearchPra = new DiagnosisListSearchPra();

        // 如果是时间轴视图或住院视图运行到此则传入已定义好的参数dto
        if (request.getAttribute("commonParameters", WebRequest.SCOPE_REQUEST) != null)
        {
            diagnosisListSearchPra = (DiagnosisListSearchPra) request.getAttribute(
                    "commonParameters", WebRequest.SCOPE_REQUEST);
        }

        // 是否调用SESSION
        String senSave = request.getParameter("senSave");
        if ("true".equals(senSave))
        {
            diagnosisListSearchPra = (DiagnosisListSearchPra) session.getAttribute("DiagnosisListSearchPra");
        }
        else
        {
            // 诊断类型
            String diagnosisType = request.getParameter("diagnosisType");
            // 疾病名称
            String diseaseName = request.getParameter("diseaseName");
            // 科室
            String diagnosticDept = request.getParameter("diagnosticDept");
            // 医生
            String diagnosisDoctor = request.getParameter("diagnosisDoctor");
            // 诊断开始日期
            String diagnosisDateFromStr = request.getParameter("diagnosisDateFromStr");
            // 诊断结束日期
            String diagnosisDateToStr = request.getParameter("diagnosisDateToStr");
            // diagnosisDate1="2990-11-11";
            // 是否主要诊断
            String mainDiagnosisFlag = request.getParameter("mainDiagnosisFlag");
            // 是否待查
            String uncertainFlag = request.getParameter("uncertainFlag");
            // 是否传染
            String contagiousFlag = request.getParameter("contagiousFlag");
            // 院区
            String orgCode = request.getParameter("orgCode");
            
            // 处理-1
            diagnosisListSearchPra.setContagiousFlag(Constants.OPTION_SELECT.equalsIgnoreCase(contagiousFlag)?null:contagiousFlag);
            diagnosisListSearchPra.setDiagnosisDateFromStr(diagnosisDateFromStr);
            diagnosisListSearchPra.setDiagnosisDateToStr(diagnosisDateToStr);
            diagnosisListSearchPra.setDiagnosisDoctorName(diagnosisDoctor);
            diagnosisListSearchPra.setDiagnosisTypeCode(Constants.OPTION_SELECT.equalsIgnoreCase(diagnosisType)?null:diagnosisType);
            diagnosisListSearchPra.setDiagnosticDeptId(Constants.OPTION_SELECT.equalsIgnoreCase(diagnosticDept)?null:diagnosticDept);
            diagnosisListSearchPra.setDiseaseName(diseaseName);
            diagnosisListSearchPra.setUncertainFlag(Constants.OPTION_SELECT.equalsIgnoreCase(uncertainFlag)?null:uncertainFlag);
            diagnosisListSearchPra.setMainDiagnosisFlag(Constants.OPTION_SELECT.equalsIgnoreCase(mainDiagnosisFlag)?null:mainDiagnosisFlag);
            // $Author :tong_meng
            // $Date : 2013/12/23 10:50$
            // [BUG]0041102 ADD BEGIN
            diagnosisListSearchPra.setOrgCode(Constants.OPTION_SELECT.equalsIgnoreCase(orgCode)?null:orgCode);
            // [BUG]0041102 ADD END
            session.setAttribute("DiagnosisListSearchPra",
                    diagnosisListSearchPra);
        }
        // 设置页面初始每页显示条目数,pagingContext默认值是10，(其他参数也可以修正，会影响检索结果)可根据需要修正。
        // Author:jia_yanqing
        // Date : 2013/1/11 15:30
        // [BUG]0012699 ADD BEGIN
        LoginUserDetails userDetails = (LoginUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String diagnosisRowsCntStr = userDetails.getUserSettings().getRowsPerPage();
        // [BUG]0012699 ADD END
        pagingContext.setRowCnt(Integer.parseInt(diagnosisRowsCntStr));
        // 调用业务层逻辑查询分页控制时对象集合，pagingContext返回值中会覆写totalRowCnt totalPageCnt
        DiagnosisDto[] list_paging = null;

        logger.debug(
                "检索条件：诊断类型={}, 疾病名称={}, 科室={},医生={},诊断开始日期={},诊断结束日期={}，是否待查={},是否传染={}",
                new Object[] { diagnosisListSearchPra.getDiagnosisTypeCode(),
                        diagnosisListSearchPra.getDiseaseName(),
                        diagnosisListSearchPra.getDiagnosticDeptId(),
                        diagnosisListSearchPra.getDiagnosisDoctorName(),
                        diagnosisListSearchPra.getDiagnosisDateFromStr(),
                        diagnosisListSearchPra.getDiagnosisDateToStr(),
                        diagnosisListSearchPra.getUncertainFlag(),
                        diagnosisListSearchPra.getContagiousFlag(),
                        diagnosisListSearchPra.getMainDiagnosisFlag() });
        
        // Author: tong_meng
        // Date : 2013/6/4 15:30
        // [BUG]0033455 ADD BEGIN 
        ModelAndView mav = new ModelAndView();
        String gotoType = request.getParameter("gotoType");
        String diagnosisSn = request.getParameter("diagnosisSn");
        String special = request.getParameter("special");
        if("part".equals(gotoType))
        {
            int showLeftRowCnt = Integer.parseInt(Constants.getDETAIL_TABS_COUNT());
            pagingContext.setRowCnt(showLeftRowCnt);
            list_paging = diagnosisService.selectDiagnosisList(patientSn, diagnosisListSearchPra, pagingContext);
            mav.addObject("pageNo",pagingContext.getPageNo());
            mav.setViewName("/diagnosis/diagnosisDetailMenuPartChart");
        }
        else if("diagnosis".equals(gotoType)||"diagnosis_timer".equals(gotoType)) 
        {
            int showLeftRowCnt = Integer.parseInt(Constants.getDETAIL_TABS_COUNT());
            pagingContext.setRowCnt(showLeftRowCnt);
            boolean overflowFlag = true;
            
            if(StringUtils.isEmpty(special))
                list_paging = diagnosisService.selectDiagnosisList(patientSn, diagnosisListSearchPra, pagingContext);
            else
                list_paging = diagnosisService.selectDiagnosisList(patientSn, diagnosisListSearchPra, null);
            
            for (int count = 0; count < showLeftRowCnt - list_paging.length; )
            {
                list_paging = Arrays.copyOf(list_paging, list_paging.length + 1);
                list_paging[list_paging.length - 1] = new DiagnosisDto();
                overflowFlag = false;
            }
            if(overflowFlag)
                mav.addObject("overflow", "auto");
            else
                mav.addObject("overflow", "hidden");
            mav.addObject("diagnosisListSearchPra", diagnosisListSearchPra);
            mav.addObject("diagnosisSn", diagnosisSn);
            mav.setViewName("/diagnosis/diagnosisDetailMenuPart");
        }
        else
        {
            list_paging = diagnosisService.selectDiagnosisList(patientSn, diagnosisListSearchPra, pagingContext);
            // 迁移到指定页面
            mav.setViewName("/diagnosis/diagnosisList");
        }
        // 设置每行显示查询对象
        mav.addObject("diagnosisList", list_paging);
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

        mav.addObject("pagingContext", pagingContext);
        // 加载pagingContext对象
        pagingContext.setRows(list_paging);
        mav.addObject("pagingContext", pagingContext);
        logger.debug(
                ":当前总页数={},当前总条数={}",
                new Object[] { pagingContext.getTotalPageCnt(),
                        pagingContext.getTotalRowCnt() });
        // 加载页面检索项目
        mav.addObject("patientSn", patientSn);

        mav.addObject("diagnosisListSearchPra", diagnosisListSearchPra);
        // 动态加载检索框
        String conditionValue = request.getParameter("conditionValue");
        mav.addObject("conditionValue", conditionValue==null?"off":conditionValue);
        // 标识符清空
        mav.addObject("senSave", null);
        // 根据不同界面显示不同detail的连接
        mav.addObject("gotoType", gotoType);
        
        return mav;
    }

    // $Author :bin_zhang
    // $Date : 2012/9/3 11:23$
    // [BUG]0007972 ADD BEGIN
    /**
     * V05-402:诊断记录详细
     * 显示诊断记录详细信息画面
     * 
     * @param diagnosisSn
     * @return
     * @throws Exception
     */
    @RequestMapping("/detail_{diagnosisSn}")
    public ModelAndView detail(@PathVariable("diagnosisSn") String diagnosisSn)
            throws Exception
    {
        // 诊断详细取得
        DiagnosisDto diagnosisDetail = diagnosisService.selectOneDiagnosis(diagnosisSn);
        // $Author :bin_zhang
        // $Date : 2012/9/3 11:23$
        // [BUG]0007972 MODIFY BEGIN
        String diagnosisTypeCode = diagnosisDetail.getDiagnosisTypeCode();
        String visitSn = diagnosisDetail.getVisitSn().toString();
        List<DiagnosisDto> diagnosisCiDetail = new ArrayList<DiagnosisDto>();
        if (diagnosisTypeCode != null && !diagnosisTypeCode.trim().isEmpty()
            && visitSn != null && !visitSn.trim().isEmpty())
            diagnosisCiDetail = diagnosisService.selectCiDiagnosis(visitSn,
                    diagnosisTypeCode);
        ModelAndView mav = new ModelAndView();
        mav.addObject("mainDiagnosis", Constants.MAIN_DIAGNOSIS);
        mav.addObject("diagnosisCiDetail", diagnosisCiDetail);
        // [BUG]0007972 MODIFY end
        mav.addObject("diagnosisDetail", diagnosisDetail);
        mav.setViewName("/diagnosis/diagnosisDetail");
        return mav;
    }

}
