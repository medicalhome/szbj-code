/**
 * Copyright (c) 2012—2012 Founder International Co.Ltd. All rights reserved.
 */
package com.founder.cdr.web;

import java.io.File;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

import com.founder.cdr.core.AppSettings;
import com.founder.cdr.core.Constants;
import com.founder.cdr.dto.ExamCVISDto;
import com.founder.cdr.dto.ExamListDto;
import com.founder.cdr.dto.OrderStepDto;
import com.founder.cdr.dto.exam.ExamListSearchParameters;
import com.founder.cdr.dto.exam.WithdrawRecordDto;
import com.founder.cdr.dto.lab.CompositeItemDto;
import com.founder.cdr.dto.lab.LabResultItemDto;
import com.founder.cdr.entity.AntibacterialDrug;
import com.founder.cdr.entity.ClinicalDocument;
import com.founder.cdr.entity.Diagnosis;
import com.founder.cdr.entity.ExaminationOrder;
import com.founder.cdr.entity.ExaminationResultDetail;
import com.founder.cdr.entity.LabResult;
import com.founder.cdr.entity.LabResultCompositeItem;
import com.founder.cdr.entity.MedicalImaging;
import com.founder.cdr.entity.MedicalVisit;
import com.founder.cdr.entity.Patient;
import com.founder.cdr.service.CommonService;
import com.founder.cdr.service.DocService;
import com.founder.cdr.service.ExamService;
import com.founder.cdr.util.DateUtils;
import com.founder.cdr.util.FileUtils;
import com.founder.cdr.util.StringUtils;
import com.founder.cdr.web.loginValidation.LoginUserDetails;
import com.founder.cdr.web.util.MutexesOrderHelper;
import com.founder.cdr.web.util.PagingHelper;
import com.founder.fasf.web.paging.PagingContext;
import com.founder.fasf.web.paging.PagingContextHolder;

/**
 * 用于检查的视图控制类
 * 
 * @author xu_dengfeng
 *
 */
@Controller
@RequestMapping("/exam")
public class ExamController implements ApplicationContextAware
{
    private static Logger logger = LoggerFactory.getLogger(ExamController.class);

    @Autowired
    private ExamService examService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private DocService docService;
    
    @Autowired
    private MutexesOrderHelper mutexesOrderHelper;

    private static Map<String, String> fieldAndMethodMap = new HashMap<String, String>();
    
    private ApplicationContext context;
    
    /*
     * #检验、检验报告前台显示是否是PDF,0否1是
     */
//    private static final String EXAM_PDF_SHOW = "1";
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException
    {
        this.context = applicationContext;
    }
    static
    {
        fieldAndMethodMap.put("erImagingFinding", "setErImagingFinding");
        fieldAndMethodMap.put("erImagingConclusion", "setErImagingConclusion");
        fieldAndMethodMap.put("eiImagingFinding", "setEiImagingFinding");
        fieldAndMethodMap.put("eiImagingConclusion", "setEiImagingConclusion");
    }

    /**
     * V05-601:检查列表
     * 显示检查列表信息画面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/list_{patientSn}_{examinationTypes}")
    public ModelAndView list(WebRequest request,
            @PathVariable("patientSn") String patientSn, HttpSession session,
            @PathVariable("examinationTypes") String examinationTypes)
            throws Exception
    {
        // 初始化pagingContext
        PagingContext pagingContext = PagingContextHolder.getPagingContext();
        // 定义页面检索元素DTO
        ExamListSearchParameters examListSearchParameters = new ExamListSearchParameters();
        // 子菜单选择标识
        String menuNum = request.getParameter("menuNum");
        // 医嘱开立科室
        String orderDept = request.getParameter("orderDept");

        // 根据医嘱执行分类区分个检查项目
        String[] examinationTypesArr = examinationTypes.split(",");
        if (examinationTypesArr != null && !examinationTypesArr[0].isEmpty())
        {
            examListSearchParameters.setExaminationTypes(getStringList(examinationTypesArr));
        }

        // 如果是时间轴视图或住院视图运行到此则传入已定义好的参数dto
        if (request.getAttribute("commonParameters", WebRequest.SCOPE_REQUEST) != null)
        {
            examListSearchParameters = (ExamListSearchParameters) request.getAttribute(
                    "commonParameters", WebRequest.SCOPE_REQUEST);
        }

        // 是否调用session
        String senSave = request.getParameter("senSave");
        if ("true".equals(senSave))
        {
            examListSearchParameters = (ExamListSearchParameters) session.getAttribute("ExamListSearchParameters");
        }
        else
        {
            // 下拉框中选择的value值为-1时按照null处理
            String examinationDept = request.getParameter("examinationDept");
            String orgCode = request.getParameter("orgCode");
            // 检查日期From
            examListSearchParameters.setExaminationDateFrom(request.getParameter("examinationDateFrom"));
            // 检查日期To
            examListSearchParameters.setExaminationDateTo(request.getParameter("examinationDateTo"));
            // 检查科室
            examListSearchParameters.setExaminationDept(Constants.OPTION_SELECT.equalsIgnoreCase(examinationDept) ? null
                    : examinationDept);
            // 检查项目名
            examListSearchParameters.setExaminationItem(request.getParameter("examinationItem"));
            // 检查部位
            examListSearchParameters.setExaminationRegion(request.getParameter("examinationRegion"));
            // 检查医生
            examListSearchParameters.setExaminingDoctor(request.getParameter("examiningDoctor"));
            // 报告日期From
            examListSearchParameters.setReportDateFrom(request.getParameter("reportDateFrom"));
            // 报告日期To
            examListSearchParameters.setReportDateTo(request.getParameter("reportDateTo"));
            // 医疗机构编码
            examListSearchParameters.setOrgCode(Constants.OPTION_SELECT.equalsIgnoreCase(orgCode) ? null
                    : orgCode);
            // 子菜单标识号
            examListSearchParameters.setMenuNum(menuNum);
            // Author:jin_peng
            // Date : 2014/1/2 15:58
            // [BUG]0041392 ADD BEGIN
            // 医嘱开立人
            examListSearchParameters.setOrderPersonName(request.getParameter("orderPersonName"));
            // 医嘱开立科室
            examListSearchParameters.setOrderDept(Constants.OPTION_SELECT.equalsIgnoreCase(orderDept) ? null
                    : orderDept);

            // [BUG]0041392 ADD END
            session.setAttribute("ExamListSearchParameters",
                    examListSearchParameters);
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
        ExamListDto[] list_paging = null;
        logger.debug("检索条件：检查项目名{"
            + examListSearchParameters.getExaminationItem() + "},检查部位{"
            + examListSearchParameters.getExaminationRegion() + "},检查医生{"
            + examListSearchParameters.getExaminingDoctor() + "},检查科室{"
            + examListSearchParameters.getExaminationDept() + "}");

        // Author: tong_meng
        // Date : 2013/6/4 15:30
        // [BUG]0033455 ADD BEGIN
        ModelAndView mav = new ModelAndView();
        String gotoType = request.getParameter("gotoType");
        String orderSn = request.getParameter("orderSn");
        String special = request.getParameter("special");
        if ("part".equals(gotoType))
        {
            int showLeftRowCnt = Integer.parseInt(Constants.getDETAIL_TABS_COUNT());
            pagingContext.setRowCnt(showLeftRowCnt);
            list_paging = examService.selectExamListSearch(patientSn,
                    examListSearchParameters, pagingContext);
            mav.addObject("pageNo", pagingContext.getPageNo());
            mav.setViewName("/exam/examDetailViewMenuPartChart");
        }
        else if ("exam".equals(gotoType) || "exam_timer".equals(gotoType))
        {
            int showLeftRowCnt = Integer.parseInt(Constants.getDETAIL_TABS_COUNT());
            pagingContext.setRowCnt(showLeftRowCnt);
            boolean overflowFlag = true;

            if ("undefined".equals(special))
                list_paging = examService.selectExamListSearch(patientSn,
                        examListSearchParameters, pagingContext);
            else
                list_paging = examService.selectExamListSearch(patientSn,
                        examListSearchParameters, null);

            for (int count = 0; count < showLeftRowCnt - list_paging.length;)
            {
                list_paging = Arrays.copyOf(list_paging, list_paging.length + 1);
                list_paging[list_paging.length - 1] = new ExamListDto();
                overflowFlag = false;
            }

            if (overflowFlag)
                mav.addObject("overflow", "auto");
            else
                mav.addObject("overflow", "hidden");

            mav.addObject("examListSearchParameters", examListSearchParameters);
            mav.addObject("orderSn", orderSn);
            mav.setViewName("/exam/examDetailViewMenuPart");
        }
        else
        {
            list_paging = examService.selectExamListSearch(patientSn,
                    examListSearchParameters, pagingContext);
            // 迁移到指定页面
            mav.setViewName("exam/examList");
        }
        // 设置每行显示查询对象
        mav.addObject("examList", list_paging);
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
        // 设置页面左上角的文字标识标记
        String leftTopTitle = "检查";

        if (menuNum != null && !menuNum.isEmpty())
        {
            switch (Integer.parseInt(menuNum))
            {
            case 1:
                leftTopTitle = "放射";
                break;
            case 2:
                leftTopTitle = "超声";
                break;
            case 3:
                leftTopTitle = "病理";
                break;
            case 4:
                leftTopTitle = "内窥镜";
                break;
            case 5:
                leftTopTitle = "其他";
                break;
            }
        }
        // $Author:wei_peng
        // $Date:2012/9/4 16:50
        // $[BUG]0009249 ADD BEGIN
        // 获取影像中心URL地址
        String imageCenterUrl = AppSettings.getConfig(Constants.IMAGE_CENTER_URL);
        // $[BUG]0009249 ADD END
        String signatureUrl = AppSettings.getConfig(Constants.SIGNATURE_URL);

        // Author:jia_yanqing
        // Date:2012/12/25 12:10
        // [BUG]0012739 ADD BEGIN
        if (list_paging != null && list_paging.length > 0)
        {
            replaceStrInFileds(list_paging, fieldAndMethodMap);
        }
        // [BUG]0012739 ADD END

        // 设置影像中心URL地址
        mav.addObject("imageCenterUrl", imageCenterUrl);
        // 设置签章服务器URL地址
        mav.addObject("signatureUrl", signatureUrl);
        // 加载pagingContext对象
        pagingContext.setRows(list_paging);
        mav.addObject("pagingContext", pagingContext);
        logger.debug("：当前总页数={}, 当前总条数={},当前页数={}", new Object[] {
                pagingContext.getTotalPageCnt(),
                pagingContext.getTotalRowCnt(), pagingContext.getPageNo() });
        // 加载页面检索项目
        mav.addObject("ExamListSearchParameters", examListSearchParameters);
        mav.addObject("patientSn", patientSn);
        // 动态加载检索框
        String conditionValue = request.getParameter("conditionValue");
        mav.addObject("conditionValue", conditionValue == null ? "off"
                : conditionValue);
        // 标识符清空
        mav.addObject("senSave", null);
        // 设置是否来自导航栏请求的识别标志
        mav.addObject("leftTopTitle", leftTopTitle);
        // 根据不同界面显示不同detail的连接
        mav.addObject("gotoType", gotoType);

        return mav;
    }

    /**
     * V05-602:检查报告详细   检查-关联信息   检查申请单
     * 显示检查报告详细信息画面
     * 
     * @param reportSn
     * @param patientSn
     * @param examinationRegion
     * @param examinationItem
     * @param itemSn
     * @return
     * @throws Exception
     */
    @RequestMapping("/detail")
    public ModelAndView detail(HttpServletRequest request) throws Exception
    {
        String reportSn = request.getParameter("examReportSn");
        String patientSn = request.getParameter("patientSn");
        String examinationRegion = request.getParameter("examinationRegion");
        String examinationItem = request.getParameter("examinationItem");
        String itemSn = request.getParameter("itemSn");
        String orderSn = request.getParameter("orderSn");
        String withdrawFlag = request.getParameter("withdrawFlag");
        String inOutDomain = null;
        // 就诊信息
        MedicalVisit mv = null;
        // 检查详细信息
        ExamListDto[] examDetail = null;
        // 检查-关联信息
        ExamListDto[] relatedExamDetail = null;
        // 检查申请单
        ExaminationOrder examOrderDetail = null;
        // $Author :tong_meng
        // $Date : 2013/5/28 17:59$
        // [BUG]0015170 ADD BEGIN
        String itemClass = request.getParameter("itemClass");
        // 就诊类别
        String visitType = null;
        
        // 检查报告从cda解析显示，去掉冠脉造影 、冠脉造影及介入手术部分，超声 
        // delete by lishenggen 2015/1/7/ 14:44
        
/*        ExamCVISDto examCVISDto = new ExamCVISDto();
        List<ExaminationResultDetail> examCVISItem = null;
        List<ExaminationResultDetail> examParentItem = new ArrayList<ExaminationResultDetail>();
        List<ExaminationResultDetail> examChildItem = new ArrayList<ExaminationResultDetail>();*/
        // 如果是冠脉造影 、冠脉造影及介入手术部分||超声
/*        if (Constants.EXAM_ITEMCLASS_ECHOCARDIOGRAPHY.equals(itemClass)
            || Constants.EXAM_ITEMCLASS_CORONARY_ANGIOGRAPHY.equals(itemClass)
            || Constants.EXAM_ITEMCLASS_CORONARY_ANGIOGRAPHY_OPRERATER.equals(itemClass))
        {
            examCVISDto = examService.selectExamCVISDetail(patientSn,
                    itemClass, itemSn, reportSn);
            examCVISItem = examService.selectExamCVISItem(itemSn, itemClass,
                    examCVISDto.getExamResultProcedureSn());
            if (!examCVISItem.isEmpty())
            {
                for (ExaminationResultDetail examResultItem : examCVISItem)
                {
                    if (StringUtils.isEmpty(examResultItem.getParentExamResultDetailSn()))
                    {
                        examParentItem.add(examResultItem);
                    }
                    else
                    {
                        examChildItem.add(examResultItem);
                    }
                }
            }
        }*/
        // 其他
        // [BUG]0015170 ADD BEGIN
        if (!StringUtils.isEmpty(reportSn))
        {
            examDetail = examService.selectExamDetail(reportSn, itemSn);
        }
        // Author:jin_peng
        // Date:2013/01/29 15:57
        // [BUG]0013746 MODIFY BEGIN
        // 因sql中已存在相应判断，所以去除此处判断。
        if (!StringUtils.isEmpty(examinationRegion)
            || !StringUtils.isEmpty(examinationItem))
        {
            relatedExamDetail = examService.selectRelatedExam(patientSn,
                    examinationRegion, examinationItem, orderSn);
        }
        // [BUG]0013729 MODIFY END
        if (!StringUtils.isEmpty(orderSn))
        {
            examOrderDetail = examService.selectExamOrderDetail(orderSn);
            // 患者门诊住院状态查询
            mv = (MedicalVisit)commonService.selectById(MedicalVisit.class, examOrderDetail.getVisitSn());
            // 门诊住院状态
            if(Constants.lstDomainOutPatient().contains(mv.getPatientDomain())
            		&& Constants.lstVisitTypeOutPatient().contains(mv.getVisitTypeCode())){
            	inOutDomain = Constants.PATIENT_DOMAIN_OUTPATIENT;
            }else{
            	inOutDomain = Constants.PATIENT_DOMAIN_INPATIENT;
            }
            
            visitType = mv.getVisitTypeCode();
        }else{
        	inOutDomain = Constants.PATIENT_DOMAIN_INPATIENT;
        }
        // 门诊住院状态
/*      if(Constants.lstDomainOutPatient().contains(mv.getPatientDomain())
        		&& Constants.lstVisitTypeOutPatient().contains(mv.getVisitTypeCode())){
        	inOutDomain = Constants.PATIENT_DOMAIN_OUTPATIENT;
        }else{
        	inOutDomain = Constants.PATIENT_DOMAIN_INPATIENT;
        }*/
        // 影像图片内部序列号集合
        List<String> examImageSns = commonService.selectImageSns(reportSn);

        ModelAndView mav = new ModelAndView();
        mav.addObject("withdrawFlag", withdrawFlag);
        // Author:jia_yanqing
        // Date:2012/12/25 12:10
        // [BUG]0012739 ADD BEGIN
        // 将检查项目与检查报告中影像学结论，影像学表现的换行符替换成<br>
        if (examDetail != null && examDetail.length > 0)
        {
            replaceStrInFileds(examDetail, fieldAndMethodMap);
        }
        if (relatedExamDetail != null && relatedExamDetail.length > 0)
        {
            replaceStrInFileds(relatedExamDetail, fieldAndMethodMap);
        }

        // [BUG]0012739 ADD END

        // Author: wu_jianfeng
        // Date : 2013/3/12 15:30
        // [BUG]0014531 ADD BEGIN
        if (examOrderDetail != null)
        {
            BigDecimal mutexesOrderSn = examOrderDetail.getMutexesOrderSn();
            String mutexesOrderNoType = examOrderDetail.getMutexesOrderNoType();
            mutexesOrderHelper.setMutexesOrder(mav, mutexesOrderSn,
                    mutexesOrderNoType);
//            List<OrderStepDto> orderStepDtos = examService.selectExamOrderSteps(examOrderDetail,inOutDomain);
            List<OrderStepDto> orderStepDtos = examService.selectExamOrderStepsForGD(examOrderDetail);
            mav.addObject("orderStepDtos",
                    StringUtils.getJsonStrings(orderStepDtos));
            boolean cancelExam = false;
            if(Constants.VISIT_TYPE_CODE_OUTPATIENT.equals(visitType)){
            	for(OrderStepDto osd : orderStepDtos){
            		if(Constants.ORDER_STATUS_CANCELED.equals(osd.getOrderStatusCode())){
            			cancelExam = true;
            			break;
            		}
            	}
            }
            mav.addObject("cancelExam", cancelExam);            
            itemClass = examOrderDetail.getItemClass();
            mav.addObject("ecgOrPathology", itemClass == null || !(Constants.ORDER_EXEC_ULTRASONIC_ALL.contains(itemClass) 
            		|| Constants.ORDER_EXEC_ERADIATION_ALL.contains(itemClass) || Constants.ORDER_EXEC_ENDOSCOPE_ALL.contains(itemClass)));

        }
        else
        {
            mav.addObject("orderStepDtos", new ArrayList<String>());
        }
        // [BUG]0014531 ADD END
        // 设置影像中心URL地址
        mav.addObject("imageCenterUrl",
                AppSettings.getConfig(Constants.IMAGE_CENTER_URL));
        mav.addObject("examDetail", examDetail);
        mav.addObject("itemClass", itemClass);
        mav.addObject("relatedExamDetail", relatedExamDetail);
        mav.addObject("examOrderDetail", examOrderDetail);
        mav.addObject("examImageSns", examImageSns);
/*      mav.addObject("examCVIS", examCVISDto);
        mav.addObject("examParentItem", examParentItem);
        mav.addObject("examChildItem", examChildItem);*/
        // Author: chang_xuewen
        // Date : 2013/8/1 15:30
        // [BUG]0035345 ADD BEGIN
        // 设置reportSn+itemSn替换空的orderSn
        mav.addObject("reportSn", reportSn);
        mav.addObject("itemSn", itemSn);
        // [BUG]0035345 ADD END
        // 门诊住院状态
        mav.addObject("patientDomain",inOutDomain);
        mav.addObject("visitType", visitType);
        
        mav.setViewName("/exam/examDetail");

        return mav;
    }
    /**
     * 检查报告详细
     * 显示检查报告详细信息画面
     * 
     * @param reportSn
     * @return
     * @throws Exception
     */
    @RequestMapping("/examReportDetail")
    public ModelAndView reportDetail(HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
        String documentSn = request.getParameter("documentSn");
        String dataSourceType = request.getParameter("dataSourceType");

        ModelAndView mav = new ModelAndView();

        if (dataSourceType != null && !dataSourceType.isEmpty())
        {
            // 病理学检查报告
            // 根据病历文书内部序列号查找病历文书信息
            ClinicalDocument doc = docService.selectDocDetail(documentSn == null ? ""
                    : documentSn);
            mav.addObject("doc", doc);
            // 文档服务接口ID
            String serviceId = doc.getServiceId();
            
            if(Boolean.parseBoolean(Constants.IF_EXAM_PDF_SHOW)){
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
                    String filePath = medicalImaging.getFilePath();
//                    String filePath = Constants.IMAGE_CONTENT_URL + medicalImaging.getImagingSn() + "." + medicalImaging.getImageFormat();
//                    if (medicalImaging.getImageContent() != null)
                    if(FileUtils.isFileExsit(filePath))
                    {
                        Random r = new Random();
                        mav.addObject("docImagePath",

                                "../doc/image_" + medicalImaging.getImagingSn()
                        		
//                        		"../doc/image_" + medicalImaging.getImagingSn() + "." + medicalImaging.getImageFormat()

                                    + "_.html?" + r.nextInt());
                    }
                }
                mav.setViewName("doc/clinicalDocumentDetail");
                return mav;
            }
            
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
                if (xsl.getName().equals(serviceId + "_" +dataSourceType + ".xsl"))//页面模板如：BS368_S019.xsl
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
            view.setUrl("/WEB-INF/xsls/" + serviceId + "_" +dataSourceType + ".xsl");
            view.setSourceKey("cda");
            view.setApplicationContext(this.context);
            Reader reader = new StringReader(doc.getDocumentCda());
            mav.addObject("cda", reader);
            mav.setView(view);
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

    // 将检查项目与检查报告中影像学结论，影像学表现的换行符替换成<br>
    private void replaceStrInFileds(Object[] objects,
            Map<String, String> fieldAndMethodMap) throws NoSuchFieldException,
            IllegalAccessException, NoSuchMethodException,
            InvocationTargetException
    {
        for (Object obj : objects)
        {
            Iterator<Entry<String, String>> iterator = fieldAndMethodMap.entrySet().iterator();
            while (iterator.hasNext())
            {
                Entry<String, String> entry = iterator.next();
                String fieldName = entry.getKey();
                String methodName = entry.getValue();

                Class<?> objClass = obj.getClass();
                Field field = objClass.getDeclaredField(fieldName);
                field.setAccessible(true);
                String fieldString = (String) field.get(obj);

                Method method = objClass.getMethod(methodName, String.class);
                method.invoke(obj, StringUtils.replaceStr(fieldString));
            }
        }
    }

    /**
     *V05-602:召回信息
     * 
     * @param reportSn
     * @return
     * @throws Exception 
     */
    @RequestMapping("/withdraw_{reportSn}")
    public ModelAndView withdraw(@PathVariable("reportSn") String reportSn)
            throws Exception
    {
        WithdrawRecordDto[] withdrawRecordDto = examService.selectWithdrawRecord(reportSn);
        ModelAndView mav = new ModelAndView();
        mav.addObject("withdrawRecordDto", withdrawRecordDto);
        mav.setViewName("/exam/withdrawRecord");
        return mav;
    }

    /**
     * 生成科室查询集合工具类
     * @param examinationDepts
     * @return
     */
    private List<String> getStringList(String[] stringArr)
    {
        List<String> result = new ArrayList<String>();
        for (int i = 0; i < stringArr.length; i++)
        {
            result.add(stringArr[i]);
        }
        return result;
    }

    /**
     * 页面显示图片
     * @param imageSn 图片内部序列号
     * @return 返回图片流
     * @throws Exception
     */
    @RequestMapping("/image_{imageSn}")
    public ModelAndView selectMedicalImage(
            @PathVariable("imageSn") String imageSn,
            HttpServletResponse response) throws Exception
    {
        MedicalImaging image = examService.selectImageBySn(imageSn);

        byte[] data = image.getImageContent();
        // 获取并写出图片流
        if (data != null)
        {
            OutputStream out = response.getOutputStream();
            out.write(data);
            out.flush();
            out.close();
        }
        return null;
    }

    // 图片缩放方法
    /*
     * private byte[] getScaleImage(String suffix, byte[] data) throws
     * IOException { double nw = 350; double nh = 200; BufferedImage bis =
     * ImageIO.read(new ByteArrayInputStream(data)); int w = bis.getWidth(); int
     * h = bis.getHeight(); double sx = nw / w; double sy = nh / h;
     * AffineTransform transform = new AffineTransform();
     * transform.setToScale(sx, sy); AffineTransformOp ato = new
     * AffineTransformOp(transform, null); BufferedImage bid = new
     * BufferedImage((int) nw, (int) nh, BufferedImage.TYPE_3BYTE_BGR);
     * ato.filter(bis, bid); ByteArrayOutputStream baos = new
     * ByteArrayOutputStream(); ImageIO.write(bid, suffix, baos); return
     * baos.toByteArray(); }
     */

}
