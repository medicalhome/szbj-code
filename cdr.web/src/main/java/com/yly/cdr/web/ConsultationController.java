package com.yly.cdr.web;

import java.io.File;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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

import com.founder.fasf.web.paging.PagingContext;
import com.founder.fasf.web.paging.PagingContextHolder;
import com.founder.sqlparser.util.StringUtil;
import com.yly.cdr.core.Constants;
import com.yly.cdr.dto.ConsultationRequestDto;
import com.yly.cdr.dto.consultation.ConsultationDept;
import com.yly.cdr.dto.consultation.ConsultationListSearchPra;
import com.yly.cdr.entity.ClinicalDocument;
import com.yly.cdr.entity.ConsultationRequest;
import com.yly.cdr.entity.MedicalVisit;
import com.yly.cdr.entity.Patient;
import com.yly.cdr.service.CommonService;
import com.yly.cdr.service.ConsultationService;
import com.yly.cdr.service.DocService;
import com.yly.cdr.service.PatientService;
import com.yly.cdr.util.DateUtils;
import com.yly.cdr.web.loginValidation.LoginUserDetails;
import com.yly.cdr.web.util.PagingHelper;

/**
 * 会诊申请记录
 * 
 * @author li_shenggen
 * @date 2014/7/14 14:58
 *
 */
@Controller
@RequestMapping("/consultation")
public class ConsultationController implements ApplicationContextAware
{
    private static Logger logger = LoggerFactory.getLogger(ConsultationController.class);
    
    @Autowired
    private ConsultationService consultationService;
    
    @Autowired
    private PatientService patientService;
    
    @Autowired
    private CommonService commonService;
    
    @Autowired
    private DocService docService;    
    
    private ApplicationContext context;  
    
    
    /**
     * 会诊申请列表
     * 显示会诊申请列表信息画面
     * @param request
     * @return
     */
    @RequestMapping("/consultation_{patientSn}")
    public ModelAndView list(WebRequest request,
            @PathVariable("patientSn") String patientSn, HttpSession session)
    {
        // 初始化pagingContext
        PagingContext pagingContext = PagingContextHolder.getPagingContext();
        
        
        // 定义页面检索元素DTO
        ConsultationListSearchPra consultationListSearchPra = new ConsultationListSearchPra();
        // 定义页面检索元素DTO
       // RiskListSearchPra riskListSearchPra = new RiskListSearchPra();
        // 是否调用SESSION
        String senSave = request.getParameter("senSave");
        if ("true".equals(senSave))
        {
        	consultationListSearchPra = (ConsultationListSearchPra) session.getAttribute("consultationListSearchPra");
        }
        else
        {
            // 申请开始日期
            String consultationStartDate = request.getParameter("consultationStartDate");
            // 申请停止日期
            String consultationStopDate = request.getParameter("consultationStopDate");
            // 会诊申请类型
            String consultationRequestType = request.getParameter("consultationRequestType");
            // 会诊申请科室
            String consultationRequestDept = request.getParameter("consultationRequestDept");
            // 医疗机构
            String orgCode = Constants.OPTION_SELECT.equalsIgnoreCase(request.getParameter("orgCode")) ? null : request.getParameter("orgCode");
            consultationListSearchPra.setConsultationStartDate(consultationStartDate);
            consultationListSearchPra.setConsultationStopDate(consultationStopDate);
            // 会诊申请类型下拉框未选择时处理
            consultationListSearchPra.setConsultationRequestType(Constants.OPTION_SELECT.equalsIgnoreCase(consultationRequestType) ? null
                    : consultationRequestType);
            // 会诊申请科室下拉框未选择时处理
            consultationListSearchPra.setConsultationRequestDept(Constants.OPTION_SELECT.equalsIgnoreCase(consultationRequestDept) ? null
                    : consultationRequestDept);
            //医疗机构
            consultationListSearchPra.setOrgCode(orgCode);
            session.setAttribute("consultationListSearchPra", consultationListSearchPra);
        }

        // 设置页面初始每页显示条目数,pagingContext默认值是10，(其他参数也可以修正，会影响检索结果)可根据需要修正。
        LoginUserDetails userDetails = (LoginUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String consultationRowsCntStr = userDetails.getUserSettings().getRowsPerPage();
        pagingContext.setRowCnt(Integer.parseInt(consultationRowsCntStr));

        // 调用业务层逻辑查询分页控制时对象集合，pagingContext返回值中会覆写totalRowCnt totalPageCnt
        ConsultationRequestDto[] list_paging = consultationService.selectConsultationRequestList(patientSn, consultationListSearchPra, pagingContext);
        logger.debug("检索条件：申请开始日期{},申请结束日期{},会诊类型{}",
                new Object[] { consultationListSearchPra.getConsultationStartDate(),
        		consultationListSearchPra.getConsultationStopDate(),
        		consultationListSearchPra.getConsultationRequestType() });
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
        mav.addObject("consultationList", list_paging);
        // 加载pagingContext对象
        pagingContext.setRows(list_paging);
        mav.addObject("pagingContext", pagingContext);
        logger.debug("：当前总页数={}, 当前总条数={},当前页数={}", new Object[] {
                pagingContext.getTotalPageCnt(),
                pagingContext.getTotalRowCnt(), pagingContext.getPageNo() });

        // 加载页面检索项目
        mav.addObject("consultationListSearchPra", consultationListSearchPra);
        mav.addObject("patientSn", patientSn);

        // 标识符清空
        mav.addObject("senSave", null);
        // 迁移到指定页面
        mav.setViewName("consultation/consultationList");
        return mav;
    }
    /**
     * 会诊记录详细
     * @throws Exception
     */
    @RequestMapping("/detail")
    public ModelAndView detail(HttpServletRequest request) throws Exception
    {
        String requestNo = request.getParameter("requestNo");
        String patientSn = request.getParameter("patientSn");
        String patientLid = request.getParameter("patientLid");
        String patientDomain = request.getParameter("patientDomain");
        String visitSn = request.getParameter("visitSn");
        String orgName = request.getParameter("orgName");
        String orgCode = request.getParameter("orgCode");
/*        String examinationRegion = request.getParameter("examinationRegion");
        String examinationItem = request.getParameter("examinationItem");
        String itemSn = request.getParameter("itemSn");
        String orderSn = request.getParameter("orderSn");
        String withdrawFlag = request.getParameter("withdrawFlag");*/
        ModelAndView mav = new ModelAndView();
        mav.addObject("requestNo", requestNo);
        // 得到患者信息
        Patient patient = patientService.getPatient(patientSn);
        if(patient == null)
        {
            patient = new Patient();
        }
        mav.addObject("patient", patient);
        //患者年龄计算开始
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("patientSn",patientSn);
        condition.put("visitSn",visitSn);
        
        MedicalVisit MV = null;
        List visitResult = new ArrayList<MedicalVisit>();
        visitResult=commonService.selectByCondition(MedicalVisit.class, condition);
        if (visitResult != null && !visitResult.isEmpty())
        {
            MV = (MedicalVisit) visitResult.get(0);
        }
        String age = DateUtils.caluAge(patient.getBirthDate(),
        		MV.getVisitDate());
        //患者年龄计算结束
        mav.addObject("age", age);
        mav.addObject("orgName", orgName);
        
        
        //获取会诊申请记录开始
        ConsultationRequest consultationRequest = consultationService.getConsulationRequest(
                patientDomain, patientLid, requestNo, orgCode);
        
        mav.addObject("consultationRequest", consultationRequest);
        //获取会诊申请记录结束
        
        //获取应邀科室信息开始
        List<ConsultationDept> consultationDeptList = new ArrayList<ConsultationDept>();
        if(consultationRequest != null){
        	
        	String consultationOrgName = consultationRequest.getConsultationOrgName();
        	String consultationDeptName = consultationRequest.getConsultationDeptName();
        	String consultationPersonName = consultationRequest.getConsultationPersonName();
        	if(!StringUtil.isEmpty(consultationOrgName)){
        		String[] orgNameArr = consultationOrgName.split(",");
        		String[] deptNameArr = consultationDeptName.split(",");
        		String[] persontNameArr = consultationPersonName.split(",");
        		if(orgNameArr.length == deptNameArr.length && deptNameArr.length == persontNameArr.length){
            		for(int i=0;i<orgNameArr.length;i++){
            			ConsultationDept cd = new ConsultationDept();
            			cd.setConsultationOrgName(orgNameArr[i]);
            			cd.setConsultationDeptName(deptNameArr[i]);
            			cd.setConsultationPersonName(persontNameArr[i]);
            			consultationDeptList.add(cd);
            		}        			
        		}
        	}
        }
        mav.addObject("consultationDeptList", consultationDeptList);
        //获取应邀科室信息结束
        
        // 根据会诊申请单编号，患者内部序列号，会诊申请单号获取文档ID(documentLid)
        List docList = new ArrayList<ClinicalDocument>();
        docList = docService.selectDocByRequestNo(patientSn,visitSn,requestNo);
        String documentSnJson = append2Json(docList);
        mav.addObject("documentSnJson", documentSnJson);
        //mav.addObject("docList", docList);
        mav.setViewName("/consultation/consultationDetail");
        return mav;
    }
    
    /**
     * documentSn转换为json格式
     * @param docList
     * @return json
     */
    public String append2Json(List docList){  
        StringBuffer sb = new StringBuffer("[");  
        String str = "";
        for(int i=0;i<docList.size();i++){  
        	ClinicalDocument cd = (ClinicalDocument)docList.get(i); 
            String documentSn = cd.getDocumentSn().toString();
            
            //最后一个元素的右边大括号'}'后不添加','  
            if(i==docList.size()-1){  
                sb.append("{\"documentSn\":\"");  
                sb.append(documentSn);   
                sb.append("\"");  
                sb.append("}");
                sb.append("]");  
                str = sb.toString().replaceAll("\"", "%22");//避免js获取此字符串时被双引号(")截断  
                return str;
            } 
            sb.append("{\"documentSn\":\"");  
            sb.append(documentSn);  
            sb.append("\"");  
            sb.append("},");  
 
        }  

        return str;
    }
    /**
     * 局部调用会诊记录详细
     * @throws Exception
     */
    @RequestMapping("/recordDetail")
    public ModelAndView recordDetail(HttpServletRequest request) throws Exception
    {
 /*     String requestNo = request.getParameter("requestNo");
        String patientSn = request.getParameter("patientSn");
        String patientLid = request.getParameter("patientLid");
        String patientDomain = request.getParameter("patientDomain");
        String visitSn = request.getParameter("visitSn");
        String orgName = request.getParameter("orgName");
        String orgCode = request.getParameter("orgCode");*/
        String documentSn = request.getParameter("documentSn");
        ModelAndView mav = new ModelAndView();
        // 根据病历文书内部序列号查找病历文书信息
        ClinicalDocument doc = docService.selectDocDetail(documentSn);
        mav.addObject("doc", doc);
        String serviceId = doc.getServiceId();
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
        mav.setView(view);      
        
        //mav.setViewName("/consultation/consultationDetail");

        return mav;
    }
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException
    {
        this.context = applicationContext;
    }
}
