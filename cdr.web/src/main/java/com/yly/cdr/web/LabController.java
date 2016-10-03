/**
 * Copyright (c) 2012—2012 Founder International Co.Ltd. All rights reserved.
 */
package com.yly.cdr.web;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.util.ShapeUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.xslt.XsltView;

import com.founder.fasf.web.paging.PagingContext;
import com.founder.fasf.web.paging.PagingContextHolder;
import com.yly.cdr.core.AppSettings;
import com.yly.cdr.core.Constants;
import com.yly.cdr.dto.AuditLogDto;
import com.yly.cdr.dto.LabDto;
import com.yly.cdr.dto.MorphologyDto;
import com.yly.cdr.dto.OrderStepDto;
import com.yly.cdr.dto.lab.CompositeItemDto;
import com.yly.cdr.dto.lab.LabListSearchParameters;
import com.yly.cdr.dto.lab.LabReportParameter;
import com.yly.cdr.dto.lab.LabResultItemDto;
import com.yly.cdr.entity.AntibacterialDrug;
import com.yly.cdr.entity.ClinicalDocument;
import com.yly.cdr.entity.Diagnosis;
import com.yly.cdr.entity.LabOrder;
import com.yly.cdr.entity.LabResult;
import com.yly.cdr.entity.LabResultCompositeItem;
import com.yly.cdr.entity.LabResultItem;
import com.yly.cdr.entity.MedicalImaging;
import com.yly.cdr.entity.MedicalVisit;
import com.yly.cdr.entity.Patient;
import com.yly.cdr.entity.PatientCrossIndex;
import com.yly.cdr.service.CommonService;
import com.yly.cdr.service.DiagnosisService;
import com.yly.cdr.service.DocService;
import com.yly.cdr.service.LabService;
import com.yly.cdr.service.PatientService;
import com.yly.cdr.service.VisitService;
import com.yly.cdr.util.DateUtils;
import com.yly.cdr.util.FileUtils;
import com.yly.cdr.util.StringUtils;
import com.yly.cdr.web.loginValidation.LoginUserDetails;
import com.yly.cdr.web.util.AuditProcessHelper;
import com.yly.cdr.web.util.MutexesOrderHelper;
import com.yly.cdr.web.util.PagingHelper;

/**
 * 用于检验的视图控制类
 * 
 * @author jin_peng
 *
 */
@Controller
@RequestMapping("/lab")
public class LabController implements ApplicationContextAware
{
    private static Logger logger = LoggerFactory.getLogger(LabController.class);

    @Autowired
    private LabService labService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private VisitService visitService;

    @Autowired
    private DocService docService;

    @Autowired
    private DiagnosisService diagnosisService;

    @Autowired
    private MutexesOrderHelper mutexesOrderHelper;

    @Autowired
    private AuditProcessHelper auditProcessHelper;

    /**
     * 原系统ID-LIS检验
     */
    private static final String LIS_LAB = "BS319";

    /**
     * 原系统ID-LIS微生物
     */
    private static final String LIS_MICROBE = "BS354";

    /**
     * 原系统ID-LIS形态学
     */
    private static final String LIS_MORPHOLOGY = "BS355";

    /**
     * 时间格式
     */
    private static final String DATE_FORMAT = "yyyyMMdd";
    
    /*
     * #检验、检验报告前台显示是否是PDF,0否1是
     */
//    private static final String LAB_PDF_SHOW = "1";
    
    private ApplicationContext context;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException
    {
        this.context = applicationContext;
    }

    /**
     * V05-501:检验列表
     * 显示检验列表信息画面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/list_{patientSn}_{orderExecId}")
    public ModelAndView list(WebRequest request,
            @PathVariable("patientSn") String patientSn, HttpSession session,
            @PathVariable("orderExecId") String orderExecIds)
    {
        String[] labExecArr = orderExecIds.split(",");
        // 初始化pagingContext
        PagingContext pagingContext = PagingContextHolder.getPagingContext();
        // 定义页面检索元素DTO
        LabListSearchParameters labListSearchParameters = new LabListSearchParameters();
        // 医嘱开立科室
        String orderDept = request.getParameter("orderDept");

        // 如果是时间轴视图或住院视图运行到此则传入已定义好的参数dto
        if (request.getAttribute("commonParameters", WebRequest.SCOPE_REQUEST) != null)
        {
            labListSearchParameters = (LabListSearchParameters) request.getAttribute(
                    "commonParameters", WebRequest.SCOPE_REQUEST);
        }
        // $Author:chang_xuewen
    	// $Date : 2013/12/25 10:30
    	// $[BUG]0040770 ADD BEGIN
        String orgCode = Constants.OPTION_SELECT.equalsIgnoreCase(request.getParameter("orgCodeFlag")) ? null : request.getParameter("orgCodeFlag");
        // $[BUG]0040770 ADD END
        // 是否调用session
        String senSave = request.getParameter("senSave");
        if ("true".equals(senSave))
        {
            labListSearchParameters = (LabListSearchParameters) session.getAttribute("LabListSearchParameters");
        }
        else
        {
            
        	// 执行科室下拉框中选择的value值为-1时按照null处理
            String labDept = request.getParameter("labDept");
            if (Constants.OPTION_SELECT.equalsIgnoreCase(labDept))
            {
                labDept = null;
            }
            
            // 医嘱执行分类
            String ordExecId = null;
            if (labExecArr[0].isEmpty())
            {
            	ordExecId = null;
            }
            else if (labExecArr.length == 1)
            {
            	ordExecId = labExecArr[0];
            }
            /*
             * $Author: yu_yzh
             * $Date: 2015/8/18 10:41
             * 排除项目目前不用，由于港大对项目分类更细，会包括多个小分类类型
             * MODIFY BEGIN
             * */
            /*// 目前没有其他，有其他选项时设定排除项目
            else if (labExecArr.length > 1)
            {
                labListSearchParameters.setNotInLabDepts(getDeptString(labExecArr));
            }*/
            else if(labExecArr.length > 1){
            	labListSearchParameters.setOrderExecList(arrayToList(labExecArr));
//            	Constants.ORDER_EXEC_BLOOD_ALL.length();
            }
            // MODIFY END

            // $Author:chang_xuewen
        	// $Date : 2013/12/25 10:30
        	// $[BUG]0040770 ADD BEGIN
            labListSearchParameters.setOrgCode(orgCode);
            // $[BUG]0040770 ADD END
            labListSearchParameters.setLabDept(labDept);
            labListSearchParameters.setItemName(request.getParameter("itemName"));
            labListSearchParameters.setTesterName(request.getParameter("testerName"));
            labListSearchParameters.setTestDateFrom(request.getParameter("testDateFrom"));
            labListSearchParameters.setTestDateTo(request.getParameter("testDateTo"));
            labListSearchParameters.setReporterName(request.getParameter("reporterName"));
            labListSearchParameters.setReportDateFrom(request.getParameter("reportDateFrom"));
            labListSearchParameters.setReportDateTo(request.getParameter("reportDateTo"));
            labListSearchParameters.setOrderExecId(ordExecId);
            labListSearchParameters.setSourceSystemId(null);
//            labListSearchParameters.setSourceSystemId(request.getParameter("sourceSystemId") == "" ? null
//                    : request.getParameter("sourceSystemId"));
//            String leftTopTitle = request.getParameter("leftTopTitle");
//            if ("常规检验".equals(leftTopTitle))
//            {
//                labListSearchParameters.setSourceSystemId(Constants.SOURCE_LAB);
//            }
//            else if ("形态学".equals(leftTopTitle))
//            {
//                labListSearchParameters.setSourceSystemId(Constants.SOURCE_MORPHOLOGY);
//            }
           
            // Author:jin_peng
            // Date : 2014/1/2 15:58
            // [BUG]0041392 ADD BEGIN
            // 医嘱开立人
            labListSearchParameters.setOrderPersonName(request.getParameter("orderPersonName"));
            // 医嘱开立科室
            labListSearchParameters.setOrderDept(Constants.OPTION_SELECT.equalsIgnoreCase(orderDept) ? null
                    : orderDept);

            // [BUG]0041392 ADD END
            session.setAttribute("LabListSearchParameters",
                    labListSearchParameters);
        }
        // 设置页面初始每页显示条目数,pagingContext默认值是10，(其他参数也可以修正，会影响检索结果)可根据需要修正。
        // Author:jia_yanqing
        // Date : 2013/1/11 15:30
        // [BUG]0012699 ADD BEGIN
        LoginUserDetails userDetails = (LoginUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String examRowsCntStr = userDetails.getUserSettings().getRowsPerPage();
        // [BUG]0012699 ADD END
        int examRowsCnt = Integer.parseInt(examRowsCntStr);
        pagingContext.setRowCnt(examRowsCnt);
        // 调用业务层逻辑查询分页控制时对象集合，pagingContext返回值中会覆写totalRowCnt totalPageCnt
        LabDto[] labList = null;
        logger.debug("检索条件：检验项目名称{" + labListSearchParameters.getItemName()
            + "}，检验医生{" + labListSearchParameters.getTesterName() + "}，报告医生{"
            + labListSearchParameters.getReporterName() + "}");

        // 设置页面左上角的文字标识标记
        String leftTopTitle = "检验";
//        if (!labDeptsArr[0].isEmpty()
//            && labListSearchParameters.getLabDept() != null)
//        {
//            switch (Integer.parseInt(labListSearchParameters.getLabDept()))
//            {
//            case Constants.LAB_DEPT_SERUM:
//                leftTopTitle = "血清";
//                break;
//            case Constants.LAB_DEPT_GANGRENE:
//                leftTopTitle = "生化";
//                break;
//            case Constants.LAB_DEPT_IMMUNITY:
//                leftTopTitle = "免疫";
//                break;
//            }
//        }
//        else if (labListSearchParameters.getNotInLabDepts() != null)
//        {
//            leftTopTitle = "其他";
//        }
        if (Constants.ORDER_EXEC_LIS.equals(labListSearchParameters.getLabDept()))
        {
            leftTopTitle = "常规检验";
        }
        else if (Constants.ORDER_EXEC_MORPHOLOGY.equals(labListSearchParameters.getLabDept()))
        {
            leftTopTitle = "形态学";
        }
        else if (!StringUtils.isEmpty(request.getParameter("leftTopTitle")))
        {
            leftTopTitle = request.getParameter("leftTopTitle");
        }
        String signatureUrl = AppSettings.getConfig(Constants.SIGNATURE_URL);
        ModelAndView mav = new ModelAndView();
        mav.addObject("signatureUrl", signatureUrl);

        // Author: tong_meng
        // Date : 2013/6/4 15:30
        // [BUG]0033455 ADD BEGIN
        String gotoType = request.getParameter("gotoType");
        String orderSn = request.getParameter("orderSn");
        String special = request.getParameter("special");
        if ("part".equals(gotoType))
        {
            int showLeftRowCnt = Integer.parseInt(Constants.getDETAIL_TABS_COUNT());
            pagingContext.setRowCnt(showLeftRowCnt);
            labList = labService.selectAllLabList(patientSn,
                    labListSearchParameters, pagingContext);
            mav.addObject("pageNo", pagingContext.getPageNo());
            mav.setViewName("/lab/labOrderDetailMenuPartChart");
        }
        else if ("lab".equals(gotoType)
            || "lab_timer".equalsIgnoreCase(gotoType))
        {
            int showLeftRowCnt = Integer.parseInt(Constants.getDETAIL_TABS_COUNT());
            pagingContext.setRowCnt(showLeftRowCnt);
            boolean overflowFlag = true;

            if ("undefined".equals(special))
                labList = labService.selectAllLabList(patientSn,
                        labListSearchParameters, pagingContext);
            else
                labList = labService.selectAllLabList(patientSn,
                        labListSearchParameters, null);

            for (int count = 0; count < showLeftRowCnt - labList.length;)
            {
                labList = Arrays.copyOf(labList, labList.length + 1);
                labList[labList.length - 1] = new LabDto();
                overflowFlag = false;
            }

            if (overflowFlag)
                mav.addObject("overflow", "auto");
            else
                mav.addObject("overflow", "hidden");

            mav.addObject("labListSearchParameters", labListSearchParameters);
            mav.addObject("orderSn", orderSn);
            mav.setViewName("/lab/labOrderDetailMenuPart");
        }
        else
        {
            labList = labService.selectAllLabList(patientSn,
                    labListSearchParameters, pagingContext);
            // 迁移到指定页面
            mav.setViewName("/lab/labList");
        }
        // 页面初始化分页页码显示列表
        PagingHelper.initPaging(pagingContext);
        // 设置每行显示查询对象
        mav.addObject("labList", labList);
        if (labList == null || labList.length == 0)
        {
            logger.debug("没有检索对象！");
            // 页面显示页面数赋值0
            pagingContext.setPageNo(0);
        }
        // [BUG]0033455 ADD END

        // 加载pagingContext对象
        pagingContext.setRows(labList);
        mav.addObject("pagingContext", pagingContext);
        logger.debug("：当前总页数={}, 当前总条数={},当前页数={}", new Object[] {
                pagingContext.getTotalPageCnt(),
                pagingContext.getTotalRowCnt(), pagingContext.getPageNo() });
        // 加载页面检索项目
        mav.addObject("labListSearchParameters", labListSearchParameters);
        mav.addObject("patientSn", patientSn);
        // 加载页面检索项目
        String conditionValue = request.getParameter("conditionValue");
        mav.addObject("conditionValue", conditionValue == null ? "off"
                : conditionValue);
        // 标识符清空
        mav.addObject("senSave", null);
        // 设置左上角的的标题信息
        mav.addObject("leftTopTitle", leftTopTitle);
        // $Author:chang_xuewen
    	// $Date : 2013/12/25 10:30
    	// $[BUG]0040770 ADD BEGIN
        mav.addObject("orgCode", orgCode);
        // $[BUG]0040770 ADD END
        // 根据不同界面显示不同detail的连接
        mav.addObject("gotoType", gotoType);

        return mav;
    }

    /**
     * V05-502:检验报告详细
     * 显示检验报告详细信息画面
     * 
     * @param reportSn
     * @return
     * @throws Exception
     */
    @RequestMapping("/detail")
    public ModelAndView detail(HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
        String labReportSn = request.getParameter("labReportSn");
        String compositeItemSn = request.getParameter("compositeItemSn");
        String patientSn = request.getParameter("patientSn");
        String orderSn = request.getParameter("orderSn");
        String inOutDomain = null;
        String visitType = null;
        ModelAndView mav = new ModelAndView();
        
        // 就诊信息
        MedicalVisit mv = null;
        // 检验医嘱
        LabOrder labOrder = labService.selectLabRequest(orderSn);
        // 设置检验医嘱
        mav.addObject("labOrder", labOrder);
        mav.addObject("patientSn", patientSn);

        List<String> labReportParams = new ArrayList<String>();
        List<LabReportParameter> labReportParameters = new ArrayList<LabReportParameter>();
        if (labOrder != null)
        {
        	// 患者门诊住院状态查询
            mv = (MedicalVisit)commonService.selectById(MedicalVisit.class, labOrder.getVisitSn());
            // 门诊住院状态
            if(Constants.lstDomainOutPatient().contains(mv.getPatientDomain())
            		&& Constants.lstVisitTypeOutPatient().contains(mv.getVisitTypeCode())){
            	inOutDomain = Constants.PATIENT_DOMAIN_OUTPATIENT;
            }else{
            	inOutDomain = Constants.PATIENT_DOMAIN_INPATIENT;
            }
            visitType = mv.getVisitTypeCode();
            
            List<LabResult> labResults = labService.selectLabReports(orderSn);
            if (labResults.size() == 1)
            {
                LabReportParameter labReportParam = new LabReportParameter();
                labReportParam.setReportDate(DateUtils.dateToString(
                        labResults.get(0).getReportDate(), DATE_FORMAT));
                labReportParam.setLabReportParam(labResults.get(0).getLabReportSn().toString()
                    + labOrder.getItemCode());
                labReportParam.setWithdrawFlag(labResults.get(0).getWithdrawFlag() == null ? ""
                        : labResults.get(0).getWithdrawFlag().toString());
                labReportParam.setLISLAB(LIS_LAB.equals(labResults.get(0).getCreateby()));
//                labReportParam.setLISLAB(false);
                labReportParameters.add(labReportParam);
                labReportParams.add("{\"labReportSn\":\""
                    + labResults.get(0).getLabReportSn().toString()
                    + "\",\"itemCode\":\"" + labOrder.getItemCode()
                    + "\",\"compositeItemSn\":\"\","
                    + "\"LISLAB\":\"" + LIS_LAB.equals(labResults.get(0).getCreateby()) +"\"" +"}");
//                    + "\"LISLAB\":\"" + false +"\"" +"}");
            }
            else if (labResults.size() > 1)
            {
                for (LabResult labResult : labResults)
                {
                    LabReportParameter labReportParam = new LabReportParameter();
                    labReportParam.setReportDate(DateUtils.dateToString(
                            labResult.getReportDate(), DATE_FORMAT));
                    labReportParam.setLabReportParam(labResult.getLabReportSn().toString());
                    labReportParam.setWithdrawFlag(labResult.getWithdrawFlag() == null ? ""
                            : labResult.getWithdrawFlag().toString());
                    labReportParam.setLISLAB(LIS_LAB.equals(labResult.getCreateby()));
                    labReportParameters.add(labReportParam);
                    labReportParams.add("{\"labReportSn\":\""
                        + labResult.getLabReportSn().toString()
                        + "\",\"itemCode\":\"\",\"compositeItemSn\":\"\","
                        + "\"LISLAB\":\"" + LIS_LAB.equals(labResult.getCreateby()) +"\"" +"}");
                }
            }
            // 设置互斥医嘱信息
            BigDecimal mutexesOrderSn = labOrder.getMutexesOrderSn();
            String mutexesOrderNoType = labOrder.getMutexesOrderNoType();
            mutexesOrderHelper.setMutexesOrder(mav, mutexesOrderSn,
                    mutexesOrderNoType);
            // 设置医嘱闭环信息
            List<OrderStepDto> orderStepDtos = labService.selectLabOrderStepsForGD(labOrder, visitType);
            mav.addObject("orderStepDtos",
                    StringUtils.getJsonStrings(orderStepDtos));
            boolean cancelLab = false;
            if(Constants.VISIT_TYPE_CODE_INPATIENT.equals(visitType)){
            	for(OrderStepDto osd : orderStepDtos){
            		if(Constants.ORDER_STATUS_CANCELED.equals(osd.getOrderStatusCode())){
            			cancelLab = true;
            			break;
            		}
            	}
            }
            mav.addObject("cancelLab", cancelLab);
        }
        else
        {
            LabResult labResult = labService.selectReportById(labReportSn);
            LabReportParameter labReportParam = new LabReportParameter();
            labReportParam.setReportDate(DateUtils.dateToString(
                    labResult.getReportDate(), DATE_FORMAT));
            labReportParam.setLabReportParam(labReportSn + compositeItemSn);
            labReportParam.setWithdrawFlag(labResult.getWithdrawFlag() == null ? ""
                    : labResult.getWithdrawFlag().toString());
            labReportParam.setLISLAB(LIS_LAB.equals(labResult.getCreateby()));
            labReportParameters.add(labReportParam);
            labReportParams.add("{\"labReportSn\":\"" + labReportSn
                + "\",\"itemCode\":\"\",\"compositeItemSn\":\""
                + compositeItemSn + "\","
                + "\"LISLAB\":\"" + LIS_LAB.equals(labResult.getCreateby()) +"\"" +"}");
            // 设置医嘱闭环信息
            mav.addObject("orderStepDtos", new ArrayList<String>());
        }
        // 设置报告参数-用于脚本遍历
        mav.addObject("labReportParams", labReportParams);
        // 设置报告参数-用于视图遍历
        mav.addObject("labReportParameters", labReportParameters);
        // 门诊住院状态
        mav.addObject("patientDomain",inOutDomain);
        
        mav.addObject("visitType", visitType);
        
        mav.setViewName("/lab/labOrderAndReportDetail");

        return mav;
    }

    /**
     * HIS系统中集成的检验报告URL链接功能
     * @return
     * @throws Exception
     */
    @RequestMapping("/labReportForHIS")
    public ModelAndView labReportForHIS(HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
    	// 就诊信息
        MedicalVisit mv = null;
        String inOutDomain = null;
        ModelAndView mav = new ModelAndView();
        String labReportLid = request.getParameter("labReportLid");
        String labItemCode = request.getParameter("labItemCode");
        String reportTypeCode = request.getParameter("reportTypeCode");
        //$Author :chang_xuewen
        // $Date : 2014/01/27 10:00$
        // [BUG]0041864 ADD BEGIN
        String orgCode = request.getParameter("orgCode");
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("labReportLid", labReportLid);
        if(StringUtils.isEmpty(reportTypeCode))
        {
            condition.put("sourceSystemId", Constants.SOURCE_LAB);
        }
        else
        {
            condition.put("sourceSystemId", Constants.SOURCE_MICROBE);
            condition.put("reportTypeCode", reportTypeCode);
        }
        condition.put("orgCode", orgCode);
        // [BUG]0041864 ADD END
        condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
        LabResult labResult = labService.selectLabResultByCondition(condition);
        LabOrder labOrder = null;
        List<OrderStepDto> orderStepDtos = new ArrayList<OrderStepDto>();
        if (labResult == null)
        {
            mav.addObject("noSuchReport", true);
        }
        else
        {
            mav.addObject("noSuchReport", false);
            mav.addObject("patientSn", labResult.getPatientSn());
            labOrder = labService.selectLabOrderByReportAndItemCode(
                    labResult.getLabReportSn().toString(), labItemCode);
            if (labOrder != null)
            {
            	// 患者门诊住院状态查询
                mv = (MedicalVisit)commonService.selectById(MedicalVisit.class, labOrder.getVisitSn());
                // 门诊住院状态
                if(Constants.lstDomainOutPatient().contains(mv.getPatientDomain())
                		&& Constants.lstVisitTypeOutPatient().contains(mv.getVisitTypeCode())){
                	inOutDomain = Constants.PATIENT_DOMAIN_OUTPATIENT;
                }else{
                	inOutDomain = Constants.PATIENT_DOMAIN_INPATIENT;
                }
                // 设置互斥医嘱信息
                BigDecimal mutexesOrderSn = labOrder.getMutexesOrderSn();
                String mutexesOrderNoType = labOrder.getMutexesOrderNoType();
                mutexesOrderHelper.setMutexesOrder(mav, mutexesOrderSn,
                        mutexesOrderNoType);
                // 设置医嘱闭环信息
                orderStepDtos = labService.selectLabOrderSteps(labOrder,inOutDomain);
            }
        }
        mav.addObject("orderStepDtos",
                StringUtils.getJsonStrings(orderStepDtos));
        mav.addObject("labOrder", labOrder);
        mav.addObject("labResult", labResult);
        mav.addObject("itemCode", labItemCode);
        mav.setViewName("/lab/labReportDetailForHIS");
        return mav;
    }

    /**
     * V05-502:检验报告详细
     * 显示检验报告详细信息画面
     * 
     * @param reportSn
     * @return
     * @throws Exception
     */
    @RequestMapping("/reportDetail")
    public ModelAndView reportDetail(HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
        String labReportSn = request.getParameter("labReportSn");
        String itemCode = request.getParameter("itemCode");
        String compositeItemSn = request.getParameter("compositeItemSn");
        String patientSn = request.getParameter("patientSn");
        String isOtherTabFlag = request.getParameter("isOtherTabFlag");
        // 当前检验报告的详细信息
        LabResult labResult = labService.selectReportById(labReportSn);
        String sourceSystemId = labResult.getCreateby();

        ModelAndView mav = new ModelAndView();
        mav.addObject("isOtherTabFlag", Boolean.parseBoolean(isOtherTabFlag));
       //检验报告显示PDF
    	if(Boolean.parseBoolean(Constants.IF_LAB_PDF_SHOW) && !Boolean.parseBoolean(isOtherTabFlag)){
        	String documentSn = labResult.getDocumentSn().toString();
        	// 病理学检查报告
            // 根据病历文书内部序列号查找病历文书信息
            ClinicalDocument doc = docService.selectDocDetail(documentSn == null ? ""
                    : documentSn);
            mav.addObject("doc", doc);
        	Map<String, Object> condition = new HashMap<String, Object>();
            condition.put("documentSn", documentSn);
            condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
            List<Object> medicalImagingList = commonService.selectByCondition(
                    MedicalImaging.class, condition);
            if (medicalImagingList != null && medicalImagingList.size() > 0)
            {
                MedicalImaging medicalImaging = (MedicalImaging) medicalImagingList.get(0);
                // 文档类型设定
                mav.addObject("docType", medicalImaging.getImageFormat());
//                if (medicalImaging.getImageContent() != null)
                String filePath = medicalImaging.getFilePath();
//                String filePath = Constants.IMAGE_CONTENT_URL + medicalImaging.getImagingSn() + "." + medicalImaging.getImageFormat();
                if(FileUtils.isFileExsit(filePath))
                {
                    Random r = new Random();
                    mav.addObject("docImagePath",
                            "../doc/image_" + medicalImaging.getImagingSn()
//                    		"../doc/image_" + medicalImaging.getImagingSn() + "." + medicalImaging.getImageFormat()
                                + "_.html?" + r.nextInt());
                }
            }
            mav.setViewName("doc/clinicalDocumentDetail");
            return mav;
        }
        
        List<LabResultCompositeItem> compositeItems = new ArrayList<LabResultCompositeItem>();
        if (itemCode != null && !itemCode.trim().isEmpty())
        {
            LabResultCompositeItem labResultComp = labService.selectCompItemByCodeAndSn(
                    itemCode, labReportSn);
            if (labResultComp != null)
            {
                compositeItems.add(labResultComp);
            }
            else
            {
                List<LabResultCompositeItem> compItems = labService.selectCompItemBySn(labReportSn);
                if (compItems.size() > 0)
                {
                    compositeItems.addAll(compItems);
                }
            }
        }
        else if (compositeItemSn != null && !compositeItemSn.trim().isEmpty())
        {
            LabResultCompositeItem labResultComp = labService.selectComItemById(compositeItemSn);
            if (labResultComp != null)
            {
                compositeItems.add(labResultComp);
            }
        }
        else
        {
            List<LabResultCompositeItem> compItems = labService.selectCompItemBySn(labReportSn);
            if (compItems.size() > 0)
            {
                compositeItems.addAll(compItems);
            }
        }

        if (LIS_LAB.equals(sourceSystemId))
        {
            // 影像图片内部序列号集合
            List<String> labImageSns = commonService.selectImageSns(labReportSn);
            List<CompositeItemDto> compositeItemList = new ArrayList<CompositeItemDto>();
            for (LabResultCompositeItem compositeItem : compositeItems)
            {
                // 当前检验报告对应的检验项目集合的信息
                List<LabResultItemDto> resultItemList = labService.selectLabResult(compositeItem.getCompositeItemSn().toString());
                for (LabResultItemDto labResultItem : resultItemList)
                {
                    labResultItem.setResultHighLowFlagStr(StringUtils.compareItemValue(
                            labResultItem.getHighValue(),
                            labResultItem.getItemValue(),
                            labResultItem.getLowValue()));
                }
                CompositeItemDto compositeItemDto = new CompositeItemDto();
                compositeItemDto.setItemName(compositeItem.getItemName());
                compositeItemDto.setLabResultItemList(resultItemList);
                compositeItemList.add(compositeItemDto);
            }

            // $Author:jin_peng
            // $Date:2013/09/23 15:50
            // [BUG]0037540 ADD BEGIN
            // 敏感信息审计内容存储
            LoginUserDetails userDetails = (LoginUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            String itemWhere = auditProcessHelper.joinItemWhere(compositeItems);

            String auditRuleWhere = auditProcessHelper.joinAuditLogWhere();

            String systemTime = DateUtils.dateToString(
                    DateUtils.getSystemTime(), DateUtils.PATTERN_MINUS_DATETIME);

            labService.insertLabAuditLog(itemWhere, auditRuleWhere,
                    userDetails.getSequenceNo(), labResult.getPatientLid(),
                    labResult.getPatientDomain(),
                    Constants.AUDIT_DATA_CONTENT_LAB,
                    labResult.getLabReportLid(), labResult.getLabReportSn(),
                    systemTime, labResult.getOrgCode(), labResult.getOrgName());

            mav.addObject("accountLogSn", userDetails.getSequenceNo());
            mav.addObject("labReportLid", labResult.getLabReportLid());
            mav.addObject("operationTime", systemTime);

            // [BUG]0037540 ADD END

            mav.addObject("labResult", labResult);
            mav.addObject("labImageSns", labImageSns);
            mav.addObject("compositeItemList", compositeItemList);
            // 设置患者内部序列号
            mav.addObject("patientSn", patientSn);

            mav.setViewName("/lab/lisReportDetail");
        }
        else if (LIS_MICROBE.equals(sourceSystemId))
        {
            // 微生物报告仅关联一个大项信息
            LabResultCompositeItem compositeItem = new LabResultCompositeItem();
            if (compositeItems.size() > 0)
                compositeItem = compositeItems.get(0);
            // 获取病人信息
            Patient patient = patientService.getPatient(patientSn);
            // 获取患者最近一次就诊，用于计算年龄
            MedicalVisit medicalVisit = visitService.getLastMedicalVisit(patientSn);

            // 获取病案号（文档内部序列号）
            String documentLid = null;
            ClinicalDocument clinicalDocument = docService.selectDocById(labResult.getDocumentSn() == null ? ""
                    : labResult.getDocumentSn().toString());
            if (clinicalDocument != null)
                documentLid = clinicalDocument.getDocumentLid();

            Map<String, Object> condition = new HashMap<String, Object>();
            // 获取诊断信息集合，并拼接为字符串
            List<Diagnosis> diagnosises = diagnosisService.selectDiagnosisByVisitSn(labResult.getVisitSn() == null ? ""
                    : labResult.getVisitSn().toString());
            String diagnosis = "";
            for (int i = 0; i < diagnosises.size(); i++)
            {
                if (i != 0)
                    diagnosis += "、";
                diagnosis += diagnosises.get(i).getDiseaseName();
            }
            // 检验报告关联的就诊
            MedicalVisit relatedMedicalVisit = visitService.selectMedicalVisitById(labResult.getVisitSn() == null ? ""
                    : labResult.getVisitSn().toString());
            // 获取检验具体结果项及其关联的抗菌药物集合
            Map<LabResultItemDto, List<AntibacterialDrug>> labItems = new LinkedHashMap<LabResultItemDto, List<AntibacterialDrug>>();
            // 获取检验具体项结果集合
            List<LabResultItemDto> labResultItems = labService.selectLabResult(compositeItem.getCompositeItemSn() == null ? ""
                    : compositeItem.getCompositeItemSn().toString());
            // 循环遍历具体项结果集合
            for (int i = 0; i < labResultItems.size(); i++)
            {
                LabResultItemDto labResultItem = labResultItems.get(i);
                condition.clear();
                // 获取该检验项对应的抗菌药物集合
                condition.put("itemCode", labResultItem.getItemCode());
                condition.put("labResultCompositeItemSn",
                        labResultItem.getLabResultCompositeItemSn());
                condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
                List<Object> drugs = commonService.selectByCondition(
                        AntibacterialDrug.class, condition);
                List<AntibacterialDrug> antDrugs = new ArrayList<AntibacterialDrug>();
                for (int j = 0; j < drugs.size(); j++)
                {
                    antDrugs.add((AntibacterialDrug) drugs.get(j));
                }
                labItems.put(labResultItem, antDrugs);
            }
            mav.addObject("patient", patient);
            mav.addObject("age", DateUtils.caluAge(patient.getBirthDate(),
                    medicalVisit == null ? null : medicalVisit.getVisitDate()));
            mav.addObject("documentLid", documentLid);
            mav.addObject("labResult", labResult);
            mav.addObject("compositeItem", compositeItem);
            mav.addObject("diagnosis", diagnosis);
            mav.addObject("labItems", labItems);
            mav.addObject("relatedMedicalVisit", relatedMedicalVisit);

            mav.setViewName("/lab/microbeReportDetail");
        }
        else if (LIS_MORPHOLOGY.equals(sourceSystemId))
        {
            // 形态学报告仅关联一个大项信息
            LabResultCompositeItem compositeItem = new LabResultCompositeItem();
            if (compositeItems.size() > 0)
                compositeItem = compositeItems.get(0);
            // 形态学报告页面显示改为xsl，add by lishenggen 2014/12/15 15:25 begin
            // 根据病历文书内部序列号查找病历文书信息
            ClinicalDocument doc = docService.selectDocDetail(labResult.getDocumentSn() == null ? ""
                    : labResult.getDocumentSn().toString());
            mav.addObject("doc", doc);
            // 文档服务接口ID
            String serviceId = doc.getServiceId();
            // 如果对应的样式或xml文件为空，则转到错误页面。
            if (serviceId == null || serviceId.isEmpty()
                || doc.getDocumentCda() == null || doc.getDocumentCda().isEmpty())
            {
                mav.setViewName("doc/clinicalDocumentDetail");
                return mav;
            }
            
            // 判断现有样式表中是否含有对应的serviceId
            String path = request.getSession().getServletContext().getRealPath("/");
            File xslFolder = new File(path + "WEB-INF/xsls");
            File[] xsls = xslFolder.listFiles();
            // 样式表存在标志
            Boolean xslExsist = false;
            // 遍历系统中存在的样式表
            for (File xsl : xsls)
            {
                if (xsl.getName().equals(serviceId + ".xsl"))
                {
                    xslExsist = true;
                    break;
                }
            }
            // 如不存在对应的样式表则跳转到错误页面
            if (!xslExsist)
            {
                mav.setViewName("doc/clinicalDocumentDetail");
                return mav;
            }
            // 创建xslt类型的视图对象（由spring提供），并设置视图相关参数。
            XsltView view = new XsltView();
            view.setUrl("/WEB-INF/xsls/" + serviceId + ".xsl");
            view.setSourceKey("cda");
            view.setApplicationContext(this.context);
            Reader reader = new StringReader(doc.getDocumentCda());
            mav.addObject("cda", reader);
            //mav.setView(view);
            // end 
            
            
            
/*            MedicalImaging medicalImaging = labService.selectLabImage(
                    labReportSn, Constants.IMAGE_CONTENT);
            // 判断是否存在形态学报告图片
            if (medicalImaging != null
                && medicalImaging.getImageContent() != null)
            {
                Random r = new Random();
                // 设置形态学报告图片链接
                mav.addObject("reportImagePath", "../lab/image_" + labReportSn
                    + "_" + Constants.IMAGE_CONTENT + ".html?" + r.nextInt());
                mav.addObject("reportImageFlag", true);
            }
            // 形态学报告图片不存在时则为历史数据，使用历史数据显示。
            else
            {
                // 通过报告内部序列号查询历史数据信息
                MorphologyDto morphology = labService.selectMorphologyReport(labReportSn);
                // 历史报告的分类标记存在
                if (morphology != null
                    && morphology.getReportTypeCode() != null
                    && !morphology.getReportTypeCode().isEmpty())
                {
                    // 如果为HLA分型报告，则需查看其是否有图片存在
                    if (Constants.MORPHOLOGY_ITEM_CODE_HLV.equals(morphology.getReportTypeCode()))
                    {
                        MedicalImaging partMedicalImaging = labService.selectLabImage(
                                labReportSn, Constants.MEDICAL_IMAGE);
                        // 如图片存在将图片链接返回到页面
                        if (partMedicalImaging != null
                            && partMedicalImaging.getImageContent() != null)
                        {
                            Random r = new Random();
                            // 设置微生物报告图片链接
                            mav.addObject("reportImagePath", "../lab/image_"
                                + labReportSn + "_" + Constants.MEDICAL_IMAGE
                                + ".html?" + r.nextInt());
                            mav.addObject("reportImageFlag", true);
                        }
                    }
                    Map<String, Object> condition = new HashMap<String, Object>();
                    condition.put("labResultCompositeItemSn",
                            compositeItem.getCompositeItemSn());
                    condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
                    // 检验结果项信息集合
                    List<Object> morphologyResults = labService.selectLabResultItemByCondition(condition);
                    // 病人信息
                    Patient patient = patientService.getPatient(patientSn);
                    // 获取患者最近一次就诊，用于计算年龄
                    MedicalVisit medicalVisit = visitService.getLastMedicalVisit(patientSn);
                    // $Author:wei_peng
                    // $Date:2012/12/07 15:00
                    // [BUG]0012204 ADD BEGIN
                    // 如果为血清学筛查报告，则进行其中值的调整
                    adjustValues(morphology, morphologyResults);
                    // [BUG]0012204 ADD END
                    mav.addObject("compositeItem", compositeItem);
                    mav.addObject("patient", patient);
                    mav.addObject("age", DateUtils.caluAge(
                            patient.getBirthDate(), medicalVisit == null ? null
                                    : medicalVisit.getVisitDate()));
                    mav.addObject("morphology", morphology);
                    mav.addObject("morphologyResults", morphologyResults);
                }
            }*/
            mav.setView(view);
            //mav.setViewName("/lab/morphologyReportDetail");
        }
        else
        {
            PrintWriter out = response.getWriter();
            out.write("reportFormatError");
            out.close();
            return null;
        }
        return mav;
    }

    /**
     * 触发敏感信息消息发送机制
     * @param request 页面请求上下文对象
     * @return
     * @throws Exception
     */
    @RequestMapping("/reportAuditSend")
    public ModelAndView reportAuditSend(HttpServletRequest request)
            throws Exception
    {
        String accountLogSn = request.getParameter("accountLogSn");
        String labReportLid = request.getParameter("labReportLid");
        String operationTime = request.getParameter("operationTime");

        List<AuditLogDto> auditDtoList = commonService.selectAuditLog(
                StringUtils.strToBigDecimal(accountLogSn, "登陆系统日志内部序列号"),
                labReportLid, operationTime);

        if (auditDtoList != null && !auditDtoList.isEmpty())
        {
            AuditLogDto auditLogDto = auditDtoList.get(0);

            // 设置相应查询条件
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("patientLid", auditLogDto.getPatientLid());
            map.put("patientDomain", auditLogDto.getPatientDomain());
            map.put("orgCode", auditLogDto.getOrgCode());
            map.put("deleteFlag", Constants.NO_DELETE_FLAG);

            // 根据相应查询条件查询患者交叉索引对象
            List<Object> patientCrossIndexList = commonService.selectByCondition(
                    PatientCrossIndex.class, map);

            PatientCrossIndex pci = (PatientCrossIndex) patientCrossIndexList.get(0);

            // 发送敏感信息审计消息
            auditProcessHelper.auditSendMessage(pci, auditLogDto);
        }

        return null;
    }

    // 如果为血清学筛查报告，则进行其中值的调整
    private void adjustValues(MorphologyDto morphology,
            List<Object> morphologyResults)
    {
        if (Constants.MORPHOLOGY_ITEM_CODE_SEROLOGY.equals(morphology.getReportTypeCode()))
        {
            for (int i = 0; i < morphologyResults.size(); i++)
            {
                LabResultItem labResultItem = (LabResultItem) morphologyResults.get(i);
                if ("10".equals(labResultItem.getItemCode())
                    || "11".equals(labResultItem.getItemCode())
                    || "12".equals(labResultItem.getItemCode()))
                {
                    if (!StringUtils.isEmpty(labResultItem.getNormalRefValueText())
                        && StringUtils.isNumber(labResultItem.getNormalRefValueText()))
                    {
                        labResultItem.setNormalRefValueText("1:"
                            + labResultItem.getNormalRefValueText());
                    }
                    if (!StringUtils.isEmpty(labResultItem.getAgeRiskValue())
                        && StringUtils.isNumber(labResultItem.getAgeRiskValue()))
                    {
                        labResultItem.setAgeRiskValue("1:"
                            + labResultItem.getAgeRiskValue());
                    }
                    if (!StringUtils.isEmpty(labResultItem.getRiskValue())
                        && StringUtils.isNumber(labResultItem.getRiskValue()))
                    {
                        labResultItem.setRiskValue("1:"
                            + labResultItem.getRiskValue());
                    }
                }
            }
        }
    }

    /**
     * 获取数据库中的微生物检验报告的图片，并写出图片到页面
     * @param labReportSn
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/image_{labReportSn}_{imageTypeFlag}")
    public ModelAndView selectImage(
            @PathVariable("labReportSn") String labReportSn,
            @PathVariable("imageTypeFlag") String imageTypeFlag,
            HttpServletResponse response) throws Exception
    {
        // 设置页面不缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        MedicalImaging medicalImaging = new MedicalImaging();
        // 查询数据库中的图片
        medicalImaging = labService.selectLabImage(labReportSn, imageTypeFlag);

        if (medicalImaging.getImageContent() != null)
        {
            // 获取并写出图片流
            OutputStream out = response.getOutputStream();
            out.write(medicalImaging.getImageContent());
            out.flush();
            out.close();
        }
        return null;
    }

    /**
     * V05-503:检验报告比对
     * 显示多次检验报告曲线对比画面
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping("/compare")
    public ModelAndView compare(WebRequest request, HttpSession session)
            throws Exception
    {
        // $Author :jin_peng
        // $Date : 2013/12/19 09:51$
        // [BUG]0040598 MODIFY BEGIN
        LoginUserDetails userDetails = (LoginUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String rowsPerPageLab = userDetails.getUserSettings().getRowsPerPageLab();
        int rowsPerPageLabCount = Integer.parseInt(rowsPerPageLab);

        // 初始化pagingContext
        PagingContext labResultPagingContext = PagingContextHolder.getPagingContext();
        labResultPagingContext.setRowCnt(rowsPerPageLabCount);

        // [BUG]0040598 MODIFY END

        String patientSn = request.getParameter("patientSn");
        String itemCode = request.getParameter("itemCode");
        String itemName = request.getParameter("itemName");

        // 是否显示图片标识
        boolean showLegendFlag = true;

        // 图片访问路径
        String legendUrl = "";

        List<LabResultItemDto> relatedResultItemList = flipSideList(labService.selectRelatedLabResult(
                patientSn, itemCode, itemName, labResultPagingContext));

        // 设置曲线的标题
        String title = itemName;

        // 创建对比图形数据源对象
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        // 判断是否显示图片并设置数据值 检验项目如果有检验描述性结果就没数据
        if (relatedResultItemList != null)
        {
            for (LabResultItemDto labResultItem : relatedResultItemList)
            {
                // $Author :tong_meng
                // $Date : 2013/1/7 11:06$
                // [BUG]0012698 MODIFY BEGIN
                labResultItem.setResultHighLowFlagStr(StringUtils.compareItemValue(
                        labResultItem.getHighValue() == null ? null
                                : labResultItem.getHighValue(),
                        labResultItem.getItemValue(),
                        labResultItem.getLowValue() == null ? null
                                : labResultItem.getLowValue()));
                // [BUG]0012698 MODIFY END
            }
            for (LabResultItemDto labResultItem : relatedResultItemList)
            {
                if (StringUtils.isEmpty(labResultItem.getQualitativeResults()) == false)
                {
                    showLegendFlag = false;
                    break;
                }
                // if(StringUtils.isEmpty(labResultItem.getItemValue())&&labResultItem.getTestDate()!=null)
                // {
                // dataset.addValue(null, title,
                // dateToStr(labResultItem.getTestDate()));
                // }
                // else if(labResultItem.getTestDate()!=null)
                // {
                // dataset.addValue(Double.parseDouble(labResultItem.getItemValue()),
                // title, dateToStr(labResultItem.getTestDate()));
                // }
                if (!StringUtils.isEmpty(labResultItem.getItemValue())
                    && labResultItem.getTestDate() != null)
                {
                    dataset.addValue(
                            Double.parseDouble(labResultItem.getItemValue()),
                            title, dateToStr(labResultItem.getTestDate()));
                }
                if (!StringUtils.isEmpty(labResultItem.getHighValue())
                    && labResultItem.getTestDate() != null)
                {
                    dataset.addValue(
                            Double.parseDouble(labResultItem.getHighValue()),
                            "正常高值", dateToStr(labResultItem.getTestDate()));
                }
                if (!StringUtils.isEmpty(labResultItem.getLowValue())
                    && labResultItem.getTestDate() != null)
                {
                    dataset.addValue(
                            Double.parseDouble(labResultItem.getLowValue()),
                            "正常低值", dateToStr(labResultItem.getTestDate()));
                }
            }
        }
        if (relatedResultItemList.size() == 0 || relatedResultItemList == null)
        {
            logger.debug("没有检索对象！");
            // 页面显示页面数赋值0
            labResultPagingContext.setPageNo(0);
        }
        if (relatedResultItemList != null)
        {
            labResultPagingContext.setRows(relatedResultItemList.toArray());
        }
        if (showLegendFlag == true)
        {
            legendUrl = request.getContextPath()
                + this.getLegendUrl(dataset, itemName, session);
        }

        // 页面初始化分页页码显示列表
        PagingHelper.initPaging(labResultPagingContext);

        ModelAndView mav = new ModelAndView();
        mav.addObject("legendUrl", legendUrl);
        mav.addObject("showLegendFlag", showLegendFlag);
        mav.addObject("relatedResultItemList", relatedResultItemList);
        mav.addObject("rowsPerPageLabCount", rowsPerPageLabCount);
        mav.addObject("patientSn", patientSn);
        mav.addObject("itemCode", itemCode);
        mav.addObject("itemName", itemName);
        mav.addObject("labResultPagingContext", labResultPagingContext);
        mav.setViewName("/lab/labReportCompare");

        return mav;
    }

    /**
     * 检验项目内容翻转另一面存储
     * @param labResultItems 检验项目相关内容集合
     * @return 处理完成内容
     */
    private List<LabResultItemDto> flipSideList(
            List<LabResultItemDto> labResultItems)
    {
        List<LabResultItemDto> listResFlipOver = new ArrayList<LabResultItemDto>();

        if (labResultItems != null && !labResultItems.isEmpty())
        {
            for (int i = labResultItems.size() - 1; i >= 0; i--)
            {
                listResFlipOver.add(labResultItems.get(i));
            }
        }

        return listResFlipOver;
    }

    private String getLegendUrl(DefaultCategoryDataset dataset,
            String itemName, HttpSession session)
    {
        // 删除生成的曲线图文件 TODO 服务器关闭后图片应删除
        if (session != null)
        {
            session.removeAttribute("JFreeChart_Deleter");
        }

        // 创建曲线图对象
        JFreeChart chart = ChartFactory.createLineChart(itemName, null, null,
                dataset, PlotOrientation.VERTICAL, true, true, true);

        // 设置标题和底部标题
        chart.setTitle(new TextTitle(itemName, new Font("微软雅黑", Font.PLAIN, 12)));
        chart.getLegend().setItemFont(new Font("微软雅黑", Font.PLAIN, 10));
        chart.setAntiAlias(true);

        // 取得图标类型对象
        CategoryPlot plot = chart.getCategoryPlot();

        // 设置网格线
        plot.setRangeGridlinePaint(Color.white);
        plot.setBackgroundPaint(new Color(206, 213, 217));

        // 设置横坐标
        CategoryAxis categoryAxis = plot.getDomainAxis();
        categoryAxis.setTickLabelFont(new Font("微软雅黑", Font.BOLD, 7));
        categoryAxis.setLowerMargin(0);
        categoryAxis.setUpperMargin(0);
        categoryAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
        categoryAxis.setMaximumCategoryLabelWidthRatio(0.18f);
        categoryAxis.setMaximumCategoryLabelLines(3);

        // 设置纵坐标
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setAutoRangeIncludesZero(true);
        rangeAxis.setUpperMargin(0.20);
        rangeAxis.setTickLabelFont(new Font("微软雅黑", Font.PLAIN, 9));
        // rangeAxis.setLabelAngle(Math.PI / 2.0);

        // 设置曲线节点属性
        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        // renderer.setBaseShapesVisible(true);
        renderer.setDrawOutlines(true);
        renderer.setUseFillPaint(true);
        // renderer.setBaseItemLabelsVisible (true);
        renderer.setBaseFillPaint(Color.blue);
        renderer.setBaseItemLabelFont(new Font("黑体", Font.PLAIN, 8));
        // 设置三条曲线的颜色
        renderer.setSeriesPaint(0, Color.blue);
        renderer.setSeriesPaint(1, Color.yellow);
        renderer.setSeriesPaint(2, Color.yellow);
        renderer.setSeriesItemLabelsVisible(0, true);
        renderer.setSeriesItemLabelsVisible(1, false);
        renderer.setSeriesItemLabelsVisible(2, false);
        renderer.setSeriesShapesVisible(0, true);
        renderer.setSeriesShapesVisible(1, false);
        renderer.setSeriesShapesVisible(2, false);
        renderer.setSeriesShape(0, ShapeUtilities.createDiamond(2.5F));

        // 取得图片生成文件名和文件路径
        String fileName = "";
        try
        {
            fileName = ServletUtilities.saveChartAsPNG(chart, 580, 280, null,
                    session);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        String legendUrl = "/DisplayChart?filename=" + fileName;
        return legendUrl;
    }

    private String dateToStr(Date date)
    {
        String str = null;
        SimpleDateFormat sdf = null;
        try
        {
            if (date != null)
            {
                sdf = new SimpleDateFormat("yy/MM/dd HH:mm");
                str = sdf.format(date);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return str;
    }

    // private Date strToDate(String str)
    // {
    // Date date = null;
    // SimpleDateFormat sdf = null;
    // try
    // {
    // if(str != null && !"".equals(str))
    // {
    // sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    // date = sdf.parse(str);
    // }
    // }
    // catch(Exception ex)
    // {
    // ex.printStackTrace();
    // }
    // return date;
    // }
    /**
     * 生成科室查询集合工具类
     * @param examinationDepts
     * @return
     */
    private List<String> getDeptString(String[] notInLabDepts)
    {
        List<String> result = new ArrayList<String>();
        for (int i = 0; i < notInLabDepts.length; i++)
        {
            result.add(notInLabDepts[i]);
        }
        return result;
    }

    private List<String> arrayToList(String[] s){
    	List<String> list = Arrays.asList(s);
    	return list;
    }
    
}
