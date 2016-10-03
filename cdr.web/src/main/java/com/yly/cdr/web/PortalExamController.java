package com.yly.cdr.web;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.founder.fasf.web.paging.PagingContext;
import com.founder.fasf.web.paging.PagingContextHolder;
import com.yly.cdr.core.AppSettings;
import com.yly.cdr.core.Constants;
import com.yly.cdr.dto.ExamListDto;
import com.yly.cdr.dto.exam.ExamListSearchParameters;
import com.yly.cdr.entity.MedicalImaging;
import com.yly.cdr.entity.Patient;
import com.yly.cdr.entity.PatientCrossIndex;
import com.yly.cdr.service.CommonService;
import com.yly.cdr.service.ExamService;
import com.yly.cdr.service.PatientService;
import com.yly.cdr.service.PortalService;
import com.yly.cdr.util.DateUtils;
import com.yly.cdr.util.FileUtils;
import com.yly.cdr.util.StringUtils;
import com.yly.cdr.web.util.MutexesOrderHelper;
import com.yly.cdr.web.util.PagingHelper;

/**
 * 用于检查的视图控制类
 * 
 * @author wang.meng
 *
 */
@Controller
@RequestMapping("/portalExam")
public class PortalExamController {

	 private static Logger logger = LoggerFactory.getLogger(ExamController.class);

	    @Autowired
	    private ExamService examService;
	    
	    @Autowired
	    private PatientService patientService;
	    
	    @Autowired
	    private PortalService portalService;

	    @Autowired
	    private CommonService commonService;

	    @Autowired
	    private MutexesOrderHelper mutexesOrderHelper;

	    private static Map<String, String> fieldAndMethodMap = new HashMap<String, String>();

	    static
	    {
	        fieldAndMethodMap.put("erImagingFinding", "setErImagingFinding");
	        fieldAndMethodMap.put("erImagingConclusion", "setErImagingConclusion");
	        fieldAndMethodMap.put("eiImagingFinding", "setEiImagingFinding");
	        fieldAndMethodMap.put("eiImagingConclusion", "setEiImagingConclusion");
	    }
	    
	/**
	 * V05-601:检查列表 显示检查列表信息画面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/examList_{patientSn}_")
	public ModelAndView examList(WebRequest request, 
			 @PathVariable("patientSn") String patientSn,HttpSession session)
			throws Exception {
		// 初始化pagingContext
		PagingContext pagingContext = PagingContextHolder.getPagingContext();
		//获取患者信息
		Patient patient = patientService.getPatient(patientSn);
		String age =DateUtils.caluAge(patient.getBirthDate(),new Date());
		// 根据域ID，患者Lid从患者交叉索引查询患者相关信息
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("patientDomain", request.getParameter("pd"));
        condition.put("patientLid", request.getParameter("pi"));
        PatientCrossIndex patientCrossIndex = patientService.getPatientCrossIndexByCondition(condition);
		// 定义页面检索元素DTO
		ExamListSearchParameters examListSearchParameters = new ExamListSearchParameters();
		String examinationTypes = request.getParameter("examinationTypes");
		//增加冠脉造影和诊疗介入
//		examinationTypes = null == examinationTypes ? "CT,MR,DX,XA,CR,RF,MG,NM,PT,BMD,IO,PX,MA,US,IVUS,CD,EC,ES,TG,OP,ECG,DSA": examinationTypes;
//		examinationTypes =Constants.OPTION_SELECT.equalsIgnoreCase(examinationTypes) ? "CT,MR,DX,XA,CR,RF,MG,NM,PT,BMD,IO,PX,MA,US,IVUS,CD,EC,ES,TG,OP,ECG,DSA": examinationTypes;
		// 目前只显示心电的检查
		examinationTypes = examinationTypes == null ? Constants.ORDER_EXEC_ECG_ALL : examinationTypes;
		if(examinationTypes != null && !Constants.OPTION_SELECT.equalsIgnoreCase(examinationTypes)){
			String[] examinationTypesArr = examinationTypes.split(",");
			if (examinationTypesArr != null && !examinationTypesArr[0].isEmpty()) {
				examListSearchParameters.setExaminationTypes(getStringList(examinationTypesArr));
			}
		}
		
		//就诊次数
		examListSearchParameters.setVisitTimes(request.getParameter("vt"));
		// 如果是时间轴视图或住院视图运行到此则传入已定义好的参数dto
		if (request.getAttribute("commonParameters", WebRequest.SCOPE_REQUEST) != null) {
			examListSearchParameters = (ExamListSearchParameters) request
					.getAttribute("commonParameters", WebRequest.SCOPE_REQUEST);
		}

		// 是否调用session
		String senSave = request.getParameter("senSave");
		if ("true".equals(senSave)) {
			examListSearchParameters = (ExamListSearchParameters) session
					.getAttribute("ExamListSearchParameters");
		} else {
			// 申请日期From
			examListSearchParameters.setRequestDateFrom(request
					.getParameter("requestDateFrom"));
			// 申请 日期To
			examListSearchParameters.setRequestDateTo(request
					.getParameter("requestDateTo"));
			// 检查项目名
			examListSearchParameters.setExaminationItem(request
					.getParameter("examinationItem"));
			session.setAttribute("ExamListSearchParameters",
					examListSearchParameters);
		}
		// 设置页面初始每页显示条目数,pagingContext默认值是10，(其他参数也可以修正，会影响检索结果)可根据需要修正。
		pagingContext.setRowCnt(10);
		// 调用业务层逻辑查询分页控制时对象集合，pagingContext返回值中会覆写totalRowCnt totalPageCnt
		ExamListDto[] list_paging = null;
		logger.debug("检索条件：检查项目名{"
				+ examListSearchParameters.getExaminationItem() + "},检查部位{"
				+ examListSearchParameters.getExaminationRegion() + "},检查医生{"
				+ examListSearchParameters.getExaminingDoctor() + "},检查科室{"
				+ examListSearchParameters.getExaminationDept() + "}");

		ModelAndView mav = new ModelAndView();
		String gotoType = request.getParameter("gotoType");
		list_paging = portalService.selectPortalExamListSearch(patientSn,
					examListSearchParameters, pagingContext);
			// 迁移到指定页面
		mav.setViewName("portal/portalExam/portalExamList");
//		}
		// 设置每行显示查询对象
		mav.addObject("examList", list_paging);

		// 检索对象为空check
		if (list_paging == null || list_paging.length == 0) {
			logger.debug("没有检索对象!");
			// 页面显示页面数赋值0
			pagingContext.setPageNo(0);
		}

		// 页面初始化分页页码显示列表
		PagingHelper.initPaging(pagingContext);
		// 设置页面左上角的文字标识标记
		String leftTopTitle = "检查";
		// 获取影像中心URL地址
		String imageCenterUrl = AppSettings
				.getConfig(Constants.IMAGE_CENTER_URL);
		String signatureUrl = AppSettings.getConfig(Constants.SIGNATURE_URL);

		if (list_paging != null && list_paging.length > 0) {
			replaceStrInFileds(list_paging, fieldAndMethodMap);
		}
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
		mav.addObject("examinationTypes", examinationTypes);
		mav.addObject("patientSn", patientSn);
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
    /*@RequestMapping("/detail")
    public ModelAndView detail(HttpServletRequest request) throws Exception
    {
        String reportSn = request.getParameter("examReportSn");
        String patientSn = request.getParameter("patientSn");
        String examinationRegion = request.getParameter("examinationRegion");
        String examinationItem = request.getParameter("examinationItem");
        String itemSn = request.getParameter("itemSn");
        String orderSn = request.getParameter("orderSn");
        String withdrawFlag = request.getParameter("withdrawFlag");
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
        ExamCVISDto examCVISDto = new ExamCVISDto();
        List<ExaminationResultDetail> examCVISItem = null;
        List<ExaminationResultDetail> examParentItem = new ArrayList<ExaminationResultDetail>();
        List<ExaminationResultDetail> examChildItem = new ArrayList<ExaminationResultDetail>();
        // 如果是冠脉造影 、冠脉造影及介入手术部分||超声
        if (Constants.EXAM_ITEMCLASS_ECHOCARDIOGRAPHY.equals(itemClass)
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
        }
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
        }
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
            List<OrderStepDto> orderStepDtos = examService.selectExamOrderSteps(examOrderDetail,null);
            mav.addObject("orderStepDtos",
                    StringUtils.getJsonStrings(orderStepDtos));
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
        mav.addObject("relatedExamDetail", relatedExamDetail);
        mav.addObject("examOrderDetail", examOrderDetail);
        mav.addObject("examImageSns", examImageSns);
        mav.addObject("examCVIS", examCVISDto);
        mav.addObject("examParentItem", examParentItem);
        mav.addObject("examChildItem", examChildItem);
        // Author: chang_xuewen
        // Date : 2013/8/1 15:30
        // [BUG]0035345 ADD BEGIN
        // 设置reportSn+itemSn替换空的orderSn
        mav.addObject("reportSn", reportSn);
        mav.addObject("itemSn", itemSn);
        // [BUG]0035345 ADD END
        mav.setViewName("/portal/portalExam/portalExamDetail");

        return mav;
    }*/
    
	@RequestMapping("/detail")
    public ModelAndView detail2(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView();
		String reportSn = request.getParameter("examReportSn");
		BigDecimal documentSn = examService.selectDocumentSnByResultSn(reportSn);
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
                if(FileUtils.isFileExsit(filePath))
                {
                    Random r = new Random();
					mav.addObject("docImagePath", "../doc/image_" + medicalImaging.getImagingSn() + "_.html?" + r.nextInt());
                }
            }
        }
		mav.addObject("portalFlag", "true");
		mav.setViewName("doc/clinicalDocumentDetail");
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

}
