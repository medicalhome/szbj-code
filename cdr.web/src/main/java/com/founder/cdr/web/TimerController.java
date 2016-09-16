/**
 * Copyright (c) 2012—2012 Founder International Co.Ltd. All rights reserved.
 */
package com.founder.cdr.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.founder.cdr.core.AppSettings;
import com.founder.cdr.core.Constants;
import com.founder.cdr.dto.AccessControlDto;
import com.founder.cdr.dto.TimerAndInpatientDto;
import com.founder.cdr.service.InpatientService;
import com.founder.cdr.service.TimerService;
import com.founder.cdr.util.StringUtils;
import com.founder.cdr.web.loginValidation.AccessControl;
import com.founder.cdr.web.loginValidation.LoginUserDetails;
import com.founder.fasf.web.paging.PagingContext;
import com.founder.fasf.web.paging.PagingContextHolder;

/**
 * 用于时间轴视图操作
 * @author jin_peng
 * @version 1.0, 2012/05/23
 *
 */
@Controller
@RequestMapping("/timer")
public class TimerController
{
    @Autowired
    private TimerService timerService;

    @Autowired
    private AppSettings appSettings;
    
    // $Author : tong_meng
    // $Date : 2013/10/08 11:00$
    // [BUG]0037154 ADD BEGIN
    @Autowired
    private InpatientService inpatientService;
    // [BUG]0037154 ADD END

    /**
     * 显示时间轴列表
     * @param request 上下文对象
     * @param patientSn 患者内部序列号
     * @return mav 数据视图对象
     */
    @RequestMapping("/list_{patientSn}")
    public ModelAndView list(WebRequest request,
            @PathVariable("patientSn") String patientSn)
    {
        // 获取共同分页对象
        PagingContext pagingContext = PagingContextHolder.getPagingContext();
        // logger.debug("显示第{}页的数据。", pagingContext.getPageNo());

        // 设置每页显示条数
        String visitRowsCntStr = appSettings.getConfig(Constants.CFG_TIMER_INPATIENT_ROWS_COUNT);
        int visitRowsCnt = Integer.parseInt(visitRowsCntStr);
        // pagingContext.setPerPageCnt(visitRowsCnt);
        // pagingContext.setRowCnt(visitRowsCnt);

        // 获取住院类型编码并设置住院类型编码到时间轴视图
        String inpatientType = Constants.VISIT_TYPE_CODE_INPATIENT;

        // 获取页面传来的就诊时间参数
        String visitDate = request.getParameter("visitDate");

        // 记录总条数
        int totalCnt = 0;
        // 记录开始条数
        int rowNumStart = 0;
        // 记录结束条数
        int rowNumEnd = 0;
        // 获取分页和总条数信息
        String totalCntStr = request.getParameter("totalCnt");
        String pagingFlag = request.getParameter("pagingFlag");
        // 判断设置分页总条数
        if (totalCntStr != null)
        {
            totalCnt = Integer.parseInt(totalCntStr);
        }
        else
        {
            totalCnt = timerService.selectTotalCnt(patientSn, null);
        }
        // 页面传来分页记录的起始页
        int pageNo = pagingContext.getPageNo();
        // 设置分页起始记录和结束记录
        if (pageNo < 0)
        {
            rowNumStart = 1;
        }
        else
        {
            rowNumStart = pageNo;
        }
        rowNumEnd = pageNo + visitRowsCnt - 1;
        // 如果有就诊时间查询条件，更新分页起始记录和结束记录
        if (visitDate != null && !visitDate.isEmpty()
            && !"true".equals(pagingFlag))
        {
            rowNumStart = timerService.selectTotalCnt(patientSn, visitDate) + 1;
            rowNumEnd = rowNumStart + visitRowsCnt - 1;
        }
        // 结束记录溢出判断
        if (rowNumEnd > totalCnt)
        {
            rowNumEnd = totalCnt;
        }
        LoginUserDetails userDetails = (LoginUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AccessControlDto accessControlDto = new AccessControlDto();
        if (AccessControl.useACL())
        {
            accessControlDto = userDetails.getAccessControl().getAccessControlAuths();
        }
        //获取用户sn
        String userSn = userDetails.getUsername();
        
        // 获取时间轴页面显示的信息对象集合
        List<List<List<TimerAndInpatientDto>>> timerAllList = timerService.selectTimerList(
                patientSn, visitDate, rowNumStart, rowNumEnd, accessControlDto,
                AccessControl.useACL(), userSn);

        ModelAndView mav = new ModelAndView();
        mav.addObject("accessControl", accessControlDto);
        mav.addObject("useACLFlag", AccessControl.useACL());
        mav.addObject("medicalList", timerAllList.get(0).get(0));
        mav.addObject("diagnosisList", timerAllList.get(1));
        mav.addObject("drugOrderList", timerAllList.get(2));
        mav.addObject("examinationList", timerAllList.get(3));
        mav.addObject("labList", timerAllList.get(4));
        mav.addObject("procedureList", timerAllList.get(5));
        mav.addObject("documenationList", timerAllList.get(6));
        mav.addObject("noDrugOrderList", timerAllList.get(7));
        mav.addObject("existsFlag",
                timerAllList.get(0).get(0).get(0).getExistsFlag());
        mav.addObject("patientSn", patientSn);
        mav.addObject("visitDate", visitDate);
        mav.addObject("inpatientType", inpatientType);
        mav.addObject("pagingContext", pagingContext);
        mav.addObject("rowNumStart", rowNumStart);
        mav.addObject("rowNumEnd", rowNumEnd);
        mav.addObject("totalCnt", totalCnt);
        mav.addObject("visitRowsCnt", visitRowsCnt);

        // $Author:wu_jianfeng
        // $Date : 2012/12/21 14:10
        // $[BUG]0012967 ADD BEGIN
        mav.addObject("viewSettings", userDetails.getUserSettings().getTimerViewSettings());
        // $[BUG]0012967 ADD END

        mav.setViewName("/timer/timer");

        return mav;
    }

    // $Author :jin_peng
    // $Date : 2012/11/05 13:54$
    // [BUG]0011016 ADD BEGIN
    /**
     * 验证时间轴页面查询条件查询是否存记录
     * @param request 上下文对象
     * @param patientSn 患者内部序列号
     * @param response 页面响应对象
     */
    @RequestMapping("/validationList_{patientSn}")
    public ModelAndView validationList(WebRequest request,
            @PathVariable("patientSn") String patientSn,
            HttpServletResponse response)
    {
        // 获取就诊日期
        String visitDate = request.getParameter("visitDate");

        // 查询在此就诊日期后是否存在记录
        int count = timerService.selectValidationTotalCnt(patientSn, visitDate);

        // 声明输出流对象
        PrintWriter out = null;

        try
        {
            // 获取输出流对象
            out = response.getWriter();

            // 输出记录数
            out.print(count);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            // 清除输出对象
            if (out != null)
            {
                out.flush();
                out.close();
            }
        }

        return null;
    }
    // [BUG]0011016 ADD END
    
    
    // $Author : tong_meng
    // $Date : 2013/10/08 11:00$
    // [BUG]0037154 ADD BEGIN
    @RequestMapping("/getMedicalOrderSn_{patientSn}")
    public ModelAndView getMedicalOrderSn(WebRequest request,
            HttpServletResponse response)
    {
        /*// 药物类型
        String type = request.getParameter("type");
        // 药物长期标志
        String longTempFlag = request.getParameter("longTempFlag");
        // 取消医嘱状态
        String cancelOrderStatus = request.getParameter("cancelOrderStatus");*/
        // 就诊内部序列号
        String visitSn = request.getParameter("visitSn");
        // 就诊日期
        String inpatientDate = request.getParameter("inpatientDate");
        
        LoginUserDetails userDetails = (LoginUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userSn = userDetails.getUsername();

        // 获取当前就诊日期的长期药物医嘱列表
        List<TimerAndInpatientDto> timerAndInpatientDtos  = inpatientService.selectSpecificallyLongDrug(
                StringUtils.strToBigDecimal(visitSn, "就诊内部序列号"), inpatientDate,
                userSn);
        
        BigDecimal orderSn = null ;
        
        if (!timerAndInpatientDtos.isEmpty()
            && timerAndInpatientDtos.size() != 0)
        {
            // 取出第一条
            orderSn = timerAndInpatientDtos.get(0).getOrderSn();
        }
        
        
        // 声明输出流对象
        PrintWriter out = null;

        try
        {
            // 获取输出流对象
            out = response.getWriter();

            // 输出记录数
            out.print(orderSn);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            // 清除输出对象
            if (out != null)
            {
                out.flush();
                out.close();
            }
        }
        
        return null;
    }
    // [BUG]0037154 ADD END
}
