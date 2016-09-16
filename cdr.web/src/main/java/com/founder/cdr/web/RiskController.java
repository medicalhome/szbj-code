/**
 * Copyright (c) 2012��2012 Founder International Co.Ltd. All rights reserved.
 */

package com.founder.cdr.web;

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
import com.founder.cdr.dto.AllergicHistoryDto;
import com.founder.cdr.dto.risk.RiskListSearchPra;
import com.founder.cdr.service.AllergicHistoryService;
import com.founder.cdr.web.loginValidation.LoginUserDetails;
import com.founder.cdr.web.util.PagingHelper;
import com.founder.fasf.web.paging.PagingContext;
import com.founder.fasf.web.paging.PagingContextHolder;

/**
 * 
 * 
 * @author xu_dengfeng
 *
 */
@Controller
@RequestMapping("/risk")
public class RiskController
{
    private static Logger logger = LoggerFactory.getLogger(RiskController.class);

    @Autowired
    private AllergicHistoryService allergicHistoryService;

    /**
     * 过敏/不良反应列表
     * 显示过敏/不良反应列表信息画面
     * @param request
     * @return
     */

    @RequestMapping("/allergy_{patientSn}")
    public ModelAndView list(WebRequest request,
            @PathVariable("patientSn") String patientSn, HttpSession session)
    {
        // 初始化pagingContext
        PagingContext pagingContext = PagingContextHolder.getPagingContext();
        // 定义页面检索元素DTO
        RiskListSearchPra riskListSearchPra = new RiskListSearchPra();
        // 是否调用SESSION
        String senSave = request.getParameter("senSave");
        if ("true".equals(senSave))
        {
            riskListSearchPra = (RiskListSearchPra) session.getAttribute("riskListSearchPra");
        }
        else
        {
            // 过敏开始日期
            String allergicStartDate = request.getParameter("allergicStartDate");
            // 过敏停止日期
            String allergicStopDate = request.getParameter("allergicStopDate");
            // 过敏严重性
            String seriousness = request.getParameter("seriousness");

            riskListSearchPra.setAllergicStartDate(allergicStartDate);
            riskListSearchPra.setAllergicStopDate(allergicStopDate);
            // 下拉框未选择时处理
            riskListSearchPra.setSeriousness(Constants.OPTION_SELECT.equalsIgnoreCase(seriousness) ? null
                    : seriousness);
            session.setAttribute("riskListSearchPra", riskListSearchPra);
        }

        // 设置页面初始每页显示条目数,pagingContext默认值是10，(其他参数也可以修正，会影响检索结果)可根据需要修正。
        // Author:jia_yanqing
        // Date : 2013/1/11 15:30
        // [BUG]0012699 ADD BEGIN
        LoginUserDetails userDetails = (LoginUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String riskRowsCntStr = userDetails.getUserSettings().getRowsPerPage();
        // [BUG]0012699 ADD END
        pagingContext.setRowCnt(Integer.parseInt(riskRowsCntStr));

        // 调用业务层逻辑查询分页控制时对象集合，pagingContext返回值中会覆写totalRowCnt totalPageCnt
        AllergicHistoryDto[] list_paging = allergicHistoryService.selectAllergyList(
                patientSn, riskListSearchPra, pagingContext);
        logger.debug("检索条件：过敏开始日期{},过敏停止日期{},过敏严重性{}",
                new Object[] { riskListSearchPra.getAllergicStartDate(),
                        riskListSearchPra.getAllergicStopDate(),
                        riskListSearchPra.getSeriousness() });
        // 检索对象为空check
        if (list_paging == null || list_paging.length == 0)
        {
            logger.debug("没有检索对象!");
            // 页面显示页面数赋值0
            pagingContext.setPageNo(0);
        }

        // 页面初始化分页页码显示列表
        PagingHelper.initPaging(pagingContext);

        ModelAndView mav = new ModelAndView();
        // 设置每行显示查询对象
        mav.addObject("riskList", list_paging);
        // 加载pagingContext对象
        pagingContext.setRows(list_paging);
        mav.addObject("pagingContext", pagingContext);
        logger.debug("：当前总页数={}, 当前总条数={},当前页数={}", new Object[] {
                pagingContext.getTotalPageCnt(),
                pagingContext.getTotalRowCnt(), pagingContext.getPageNo() });

        // 加载页面检索项目
        mav.addObject("riskListSearchPra", riskListSearchPra);
        mav.addObject("patientSn", patientSn);

        // 标识符清空
        mav.addObject("senSave", null);
        // 迁移到指定页面
        mav.setViewName("risk/allergyList");
        return mav;
    }
}
