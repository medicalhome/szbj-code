/**
 * Copyright (c) 2012—2012 Founder International Co.Ltd. All rights reserved.
 */
package com.founder.cdr.web;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

import com.founder.cdr.core.AppSettings;
import com.founder.cdr.core.Constants;
import com.founder.cdr.dto.LabDto;
import com.founder.cdr.dto.MorphologyDto;
import com.founder.cdr.dto.OrderStepDto;
import com.founder.cdr.dto.lab.CompositeItemDto;
import com.founder.cdr.dto.lab.LabListSearchParameters;
import com.founder.cdr.dto.lab.LabReportParameter;
import com.founder.cdr.dto.lab.LabResultItemDto;
import com.founder.cdr.entity.AntibacterialDrug;
import com.founder.cdr.entity.ClinicalDocument;
import com.founder.cdr.entity.Diagnosis;
import com.founder.cdr.entity.LabOrder;
import com.founder.cdr.entity.LabResult;
import com.founder.cdr.entity.LabResultCompositeItem;
import com.founder.cdr.entity.LabResultItem;
import com.founder.cdr.entity.MedicalImaging;
import com.founder.cdr.entity.MedicalVisit;
import com.founder.cdr.entity.Patient;
import com.founder.cdr.entity.PatientCrossIndex;
import com.founder.cdr.service.CommonService;
import com.founder.cdr.service.DiagnosisService;
import com.founder.cdr.service.DocService;
import com.founder.cdr.service.LabService;
import com.founder.cdr.service.PatientService;
import com.founder.cdr.service.PortalService;
import com.founder.cdr.service.VisitService;
import com.founder.cdr.util.DateUtils;
import com.founder.cdr.util.StringUtils;
import com.founder.cdr.web.loginValidation.LoginUserDetails;
import com.founder.cdr.web.util.AuditProcessHelper;
import com.founder.cdr.web.util.MutexesOrderHelper;
import com.founder.cdr.web.util.PagingHelper;
import com.founder.fasf.web.paging.PagingContext;
import com.founder.fasf.web.paging.PagingContextHolder;

/**
 * 用于检验的视图控制类
 * 
 * @author jin_peng
 *
 */
@Controller
@RequestMapping("/portalLab")
public class PortalLabController
{
    private static Logger logger = LoggerFactory.getLogger(PortalLabController.class);

    @Autowired
    private LabService labService;
    
    @Autowired
    private PortalService portalService;
    
    @Autowired
    private CommonService commonService;
    
    @Autowired
    private MutexesOrderHelper mutexesOrderHelper;
    
    @Autowired
    private AuditProcessHelper auditProcessHelper;
    
    @Autowired
    private PatientService patientService;
    
    @Autowired
    private VisitService visitService;
    
    @Autowired
    private DocService docService;
    
    @Autowired
    private DiagnosisService diagnosisService;
    
    /**
     * 时间格式
     */
    private static final String DATE_FORMAT = "yyyyMMdd";
    
    /**
     * 原系统ID-LIS检验
     */
    private static final String LIS_LAB = "S008";
    
    /**
     * 原系统ID-LIS微生物
     */
    private static final String LIS_MICROBE = "S009";
    
    /**
     * 原系统ID-LIS形态学
     */
    private static final String LIS_MORPHOLOGY = "S010";
    
    /**
     * V05-501:检验列表
     * 显示检验列表信息画面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/list_{patientSn}_{labDepts}")
    public ModelAndView list(WebRequest request,
            @PathVariable("patientSn") String patientSn, HttpSession session)
    {
        // 初始化pagingContext
        PagingContext pagingContext = PagingContextHolder.getPagingContext();
       //获取患者信息
  		Patient patient = patientService.getPatient(patientSn);
  		String age =DateUtils.caluAge(patient.getBirthDate(),new Date());
  		// 根据域ID，患者Lid从患者交叉索引查询患者住院号相关信息
	    Map<String, Object> condition = new HashMap<String, Object>();
	    condition.put("patientDomain", request.getParameter("patientDomain"));
	    condition.put("patientLid", request.getParameter("patientId"));
	    PatientCrossIndex patientCrossIndex = patientService.getPatientCrossIndexByCondition(condition);
        // 定义页面检索元素DTO
        LabListSearchParameters labListSearchParameters = new LabListSearchParameters();
        //就诊次数
        labListSearchParameters.setVisitTimes(request.getParameter("visitTimes"));
        // 如果是时间轴视图或住院视图运行到此则传入已定义好的参数dto
        if (request.getAttribute("commonParameters", WebRequest.SCOPE_REQUEST) != null)
        {
            labListSearchParameters = (LabListSearchParameters) request.getAttribute(
                    "commonParameters", WebRequest.SCOPE_REQUEST);
        }
        // 是否调用session
        String senSave = request.getParameter("senSave");
        if ("true".equals(senSave))
        {
            labListSearchParameters = (LabListSearchParameters) session.getAttribute("LabListSearchParameters");
        }
        else
        {
        	labListSearchParameters.setViewId(request.getParameter("viewId"));
            labListSearchParameters.setItemName(request.getParameter("itemName"));
            labListSearchParameters.setRequestDateFrom(request.getParameter("requestDateFrom"));
            labListSearchParameters.setRequestDateTo(request.getParameter("requestDateTo"));
            if("V002".equals(request.getParameter("viewId"))){
            	labListSearchParameters.setSourceSystemId(LIS_LAB);
            }else if("V003".equals(request.getParameter("viewId"))){
            	labListSearchParameters.setSourceSystemId(LIS_MICROBE);
            }
            
            
            session.setAttribute("LabListSearchParameters",
                    labListSearchParameters);
        }
        // 设置页面初始每页显示条目数,pagingContext默认值是10，(其他参数也可以修正，会影响检索结果)可根据需要修正。
        pagingContext.setRowCnt(10);
        // 调用业务层逻辑查询分页控制时对象集合，pagingContext返回值中会覆写totalRowCnt totalPageCnt
        LabDto[] labList = null;
        logger.debug("检索条件：检验项目名称{" + labListSearchParameters.getItemName()
            + "}，检验医生{" + labListSearchParameters.getTesterName() + "}，报告医生{"
            + labListSearchParameters.getReporterName() + "}");       
        String signatureUrl = AppSettings.getConfig(Constants.SIGNATURE_URL);
        ModelAndView mav = new ModelAndView();
        mav.addObject("signatureUrl", signatureUrl);

        // Author: tong_meng
        // Date : 2013/6/4 15:30
        // [BUG]0033455 ADD BEGIN
        String gotoType = request.getParameter("gotoType");
        labList = portalService.selectPortalLabList(patientSn,
                labListSearchParameters, pagingContext);
        // 迁移到指定页面
        mav.setViewName("/portal/portalLab/portalLabList");
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
        // 设置页面左上角的文字标识标记
        String leftTopTitle = "";
        if("V002".equals(labListSearchParameters.getViewId())){
        	leftTopTitle = "检验报告";
        }else if("V003".equals(labListSearchParameters.getViewId())){
        	leftTopTitle = "微生物检验";
        }
      // 设置是否来自导航栏请求的识别标志
        mav.addObject("leftTopTitle", leftTopTitle);
     // 患者信息
 		mav.addObject("patient", patient);
 		mav.addObject("age", age);
 		mav.addObject("patientCrossIndex", patientCrossIndex);
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

        ModelAndView mav = new ModelAndView();
        // 检验医嘱
        LabOrder labOrder = labService.selectLabRequest(orderSn);
        // 设置检验医嘱
        mav.addObject("labOrder", labOrder);
        mav.addObject("patientSn", patientSn);

        List<String> labReportParams = new ArrayList<String>();
        List<LabReportParameter> labReportParameters = new ArrayList<LabReportParameter>();
        if (labOrder != null)
        {
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
                labReportParameters.add(labReportParam);
                labReportParams.add("{\"labReportSn\":\""
                    + labResults.get(0).getLabReportSn().toString()
                    + "\",\"itemCode\":\"" + labOrder.getItemCode()
                    + "\",\"compositeItemSn\":\"\"}");
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
                    labReportParameters.add(labReportParam);
                    labReportParams.add("{\"labReportSn\":\""
                        + labResult.getLabReportSn().toString()
                        + "\",\"itemCode\":\"\",\"compositeItemSn\":\"\"}");
                }
            }
            // 设置互斥医嘱信息
            BigDecimal mutexesOrderSn = labOrder.getMutexesOrderSn();
            String mutexesOrderNoType = labOrder.getMutexesOrderNoType();
            mutexesOrderHelper.setMutexesOrder(mav, mutexesOrderSn,
                    mutexesOrderNoType);
            // 设置医嘱闭环信息
            List<OrderStepDto> orderStepDtos = labService.selectLabOrderSteps(labOrder,null);
            mav.addObject("orderStepDtos",
                    StringUtils.getJsonStrings(orderStepDtos));
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
            labReportParameters.add(labReportParam);
            labReportParams.add("{\"labReportSn\":\"" + labReportSn
                + "\",\"itemCode\":\"\",\"compositeItemSn\":\""
                + compositeItemSn + "\"}");
            // 设置医嘱闭环信息
            mav.addObject("orderStepDtos", new ArrayList<String>());
        }
        // 设置报告参数-用于脚本遍历
        mav.addObject("labReportParams", labReportParams);
        // 设置报告参数-用于视图遍历
        mav.addObject("labReportParameters", labReportParameters);

        mav.setViewName("/portal/portalLab/portalLabDetail");

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
        // 当前检验报告的详细信息
        LabResult labResult = labService.selectReportById(labReportSn);
        String sourceSystemId = labResult.getSourceSystemId();

        ModelAndView mav = new ModelAndView();

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

            mav.setViewName("/portal/portalLab/portalLisReportDetail");
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
            Map<LabResultItemDto, List<AntibacterialDrug>> labItems = new HashMap<LabResultItemDto, List<AntibacterialDrug>>();
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

            mav.setViewName("/portal/portalLab/portalMicrobeReportDetail");
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
}
