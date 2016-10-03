/**
 * Copyright (c) 2012—2012 Founder International Co.Ltd. All rights reserved.
 */
package com.yly.cdr.web;

import java.io.File;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.founder.fasf.core.util.daohelper.entity.EntityDao;
import com.founder.fasf.web.paging.PagingContext;
import com.founder.fasf.web.paging.PagingContextHolder;
import com.founder.sqlparser.util.StringUtil;
import com.yly.cdr.core.AppSettings;
import com.yly.cdr.core.Constants;
import com.yly.cdr.dto.AccessControlDto;
import com.yly.cdr.dto.BloodDeliveryOrderDto;
import com.yly.cdr.dto.BloodRecordMenuDto;
import com.yly.cdr.dto.BloodRequestAndRecordDto;
import com.yly.cdr.dto.BloodRequestDto;
import com.yly.cdr.dto.DiagnosisDto;
import com.yly.cdr.dto.ExamListDto;
import com.yly.cdr.dto.KeyValueDto;
import com.yly.cdr.dto.LabDto;
import com.yly.cdr.dto.MedicationOrderDto;
import com.yly.cdr.dto.NonDrugOrderDto;
import com.yly.cdr.dto.PatientCrossIndexDto;
import com.yly.cdr.dto.PatientFolderMemoDto;
import com.yly.cdr.dto.VisitDateDto;
import com.yly.cdr.dto.VisitDetailDto;
import com.yly.cdr.dto.VisitIndexListDto;
import com.yly.cdr.dto.VisitListDto;
import com.yly.cdr.dto.drug.DrugListSearchPra;
import com.yly.cdr.dto.exam.ExamListSearchParameters;
import com.yly.cdr.dto.exam.WithdrawRecordDto;
import com.yly.cdr.dto.lab.LabListSearchParameters;
import com.yly.cdr.dto.visit.VisitDiagnosisDto;
import com.yly.cdr.dto.visit.VisitIndexListSearchPra;
import com.yly.cdr.dto.visit.VisitListSearchPra;
import com.yly.cdr.entity.BloodRecord;
import com.yly.cdr.entity.BloodRecordMenu;
import com.yly.cdr.entity.BloodRecordSpecialRequest;
import com.yly.cdr.entity.BloodRequest;
import com.yly.cdr.entity.ClinicalDocument;
import com.yly.cdr.entity.Diagnosis;
import com.yly.cdr.entity.ExamResult;
import com.yly.cdr.entity.MedicalVisit;
import com.yly.cdr.entity.MedicationOrder;
import com.yly.cdr.entity.Patient;
import com.yly.cdr.entity.SystemAccountAuth;
import com.yly.cdr.entity.SystemMenu;
import com.yly.cdr.entity.SystemRoleAuth;
import com.yly.cdr.entity.SystemUserRole;
import com.yly.cdr.entity.TransferHistory;
import com.yly.cdr.entity.UserFeedback;
import com.yly.cdr.service.AccessControlService;
import com.yly.cdr.service.CommonService;
import com.yly.cdr.service.DiagnosisService;
import com.yly.cdr.service.DocService;
import com.yly.cdr.service.DrugListService;
import com.yly.cdr.service.ExamService;
import com.yly.cdr.service.LabService;
import com.yly.cdr.service.NonDrugOrderService;
import com.yly.cdr.service.PatientFavFolderService;
import com.yly.cdr.service.PatientService;
import com.yly.cdr.service.RoleService;
import com.yly.cdr.service.SystemService;
import com.yly.cdr.service.UserManagerService;
import com.yly.cdr.service.VisitService;
import com.yly.cdr.util.DateUtils;
import com.yly.cdr.util.ResetPwUtil;
import com.yly.cdr.util.StringUtils;
import com.yly.cdr.web.loginValidation.AccessControl;
import com.yly.cdr.web.loginValidation.LoginUserDetails;
import com.yly.cdr.web.userSettings.UserSettings;
import com.yly.cdr.web.util.PagingHelper;
import com.yly.cdr.web.visitIndexSort.SettingUrl;

/**
* 就诊相关的视图控制类
* 
* @author xu_dengfeng
*
*/
@Controller
@RequestMapping("/visit")
public class VisitController
{
    private static Logger logger = LoggerFactory.getLogger(VisitController.class);

    @Autowired
    private VisitService visitService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private DrugListService drugListService;

    @Autowired
    private DiagnosisService diagnosisService;

    @Autowired
    private LabService labService;

    @Autowired
    private ExamService examService;

    @Autowired
    private SystemService systemService;

    @Autowired
    private DocService docService;

    @Autowired
    private NonDrugOrderService unMedOrdService;

    @Autowired
    private PatientFavFolderService patientFolderService;
    
    @Autowired 
    private UserManagerService userManagerService;
    
    @Autowired
    EntityDao entityDao;
    
    @Autowired
    CommonController controller;
    
    @Autowired
    AccessControlService accessControlService;
    
    @Autowired
    RoleService roleService;

    public static final String LINK_BINGAN = AppSettings.getConfig("LINK_BINGAN");
    
    public static final String IS_SCAN = AppSettings.getConfig("IS_SCAN");
    /**
     * V05-101:就诊列表
     * 显示就诊列表信息画面
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
        VisitListSearchPra visitListSearchPra = new VisitListSearchPra();
        // 是否调用SESSION
        String senSave = request.getParameter("senSave");
        if ("true".equals(senSave))
        {
            visitListSearchPra = (VisitListSearchPra) session.getAttribute("VisitListSearchPra");
        }
        else
        {
            // 就诊日期From
            String visitDateFromStr = request.getParameter("visitDateFrom");
            // 就诊日期To
            String visitDateToStr = request.getParameter("visitDateTo");
            // 就诊类型
            String visitType = request.getParameter("visitType");
            // 出院日期From
            String dischargeDateFromStr = request.getParameter("dischargeDateFrom");
            // 出院日期To
            String dischargeDateToStr = request.getParameter("dischargeDateTo");
            // 就诊医生
            String visitDoctorName = request.getParameter("visitDoctorName");
            // 就诊号别
            String registrationClass = request.getParameter("registrationClass");
            // 科室
            String visitDept = request.getParameter("visitDept");
            // $Author:tong_meng
            // $Date : 2013/12/23 14:54
            // $[BUG]11399 ADD BEGIN
            // 院区
            String orgCode = request.getParameter("orgCode");
            visitListSearchPra.setOrgCode(Constants.OPTION_SELECT.equalsIgnoreCase(orgCode) ? null
                    : orgCode);
            // $[BUG]11399 ADD END

            visitListSearchPra.setDischargeDateFromStr(dischargeDateFromStr);
            visitListSearchPra.setDischargeDateToStr(dischargeDateToStr);
            // 下拉框未选择时处理
            visitListSearchPra.setRegistrationClassCode(Constants.OPTION_SELECT.equalsIgnoreCase(registrationClass)?null:registrationClass);
            visitListSearchPra.setVisitDateFromStr(visitDateFromStr);
            visitListSearchPra.setVisitDateToStr(visitDateToStr);
            visitListSearchPra.setVisitDeptId(Constants.OPTION_SELECT.equalsIgnoreCase(visitDept)?null:visitDept);
            visitListSearchPra.setVisitDoctorName(visitDoctorName);
            visitListSearchPra.setVisitTypeCode(Constants.OPTION_SELECT.equalsIgnoreCase(visitType)?null:visitType);
            // $Author:bin_zhang
            // $Date : 2012/11/20 14:10
            // $[BUG]11399 ADD BEGIN
            visitListSearchPra.setVisitTypeInpatientCode(Constants.lstVisitTypeInPatient());
            // $[BUG]11399 ADD END
            session.setAttribute("VisitListSearchPra", visitListSearchPra);
        }
        // $Author:wei_peng
        // $Date:2013/01/18 14:57
        // $[BUG]0013495 ADD BEGIN
        String sysDate = DateUtils.dateToString(DateUtils.getSystemTime(),
                "yyyy-MM-dd");
        visitListSearchPra.setSysDate(sysDate);
        // $[BUG]0013495 ADD END
        
        // 设置页面初始每页显示条目数,pagingContext默认值是10，(其他参数也可以修正，会影响检索结果)可根据需要修正。
        // Author:jia_yanqing
        // Date : 2013/1/11 15:30
        // [BUG]0012699 ADD BEGIN
        LoginUserDetails userDetails = (LoginUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String visitRowsCntStr = userDetails.getUserSettings().getRowsPerPage();
        // [BUG]0012699 ADD END
        pagingContext.setRowCnt(Integer.parseInt(visitRowsCntStr));
        // 调用业务层逻辑查询分页控制时对象集合，pagingContext返回值中会覆写totalRowCnt totalPageCnt
        VisitListDto[] list_paging = visitService.selectVisitList(patientSn,
                visitListSearchPra, pagingContext);
        // $Author:bin_zhang
        // $Date : 2012/11/20 14:10
        // $[BUG]11399 ADD BEGIN
        List<VisitListDto> visit_list=new ArrayList<VisitListDto>();
        if(list_paging!=null)
        {
             visit_list = Arrays.asList(list_paging);
        }
        // 获得分页的visitSn集合
        List<String> Inpatient_visitSns = new ArrayList<String>();
        for (VisitListDto visitList : visit_list)
        {
            if (Constants.lstVisitTypeInPatient().contains(
                    visitList.getVisitTypeCode()))
            {
                Inpatient_visitSns.add(visitList.getVisitSn());
            }
        }
        if (!Inpatient_visitSns.isEmpty())
        {
            List<MedicalVisit> mvs = visitService.getInpatientVisitDoctors(Inpatient_visitSns);
            if(mvs != null && !mvs.isEmpty())
            {
                for (VisitListDto visitList : visit_list)
                {
                    for (MedicalVisit mv : mvs)
                    {
                        if (visitList.getVisitSn().equals(
                                mv.getVisitSn().toString()))
                        {
                            visitList.setVisitDoctorName(mv.getVisitDoctorName());
                        }
                    }
                }
            }
        }
        // $[BUG]11399 ADD END
        logger.debug(
                "检索条件：就诊开始日期={}, 就诊结束日期={}, 就诊类型={},就诊科室={},入院开始日期={},入院结束日期={}，就诊医生姓名={},就诊号别={}",
                new Object[] { visitListSearchPra.getVisitDateFromStr(),
                        visitListSearchPra.getVisitDateToStr(),
                        visitListSearchPra.getVisitTypeCode(),
                        visitListSearchPra.getVisitDeptId(),
                        visitListSearchPra.getDischargeDateFromStr(),
                        visitListSearchPra.getDischargeDateToStr(),
                        visitListSearchPra.getVisitDoctorName(),
                        visitListSearchPra.getRegistrationClassCode() });
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
        mav.addObject("visitList", list_paging);
        // 加载pagingContext对象
        pagingContext.setRows(list_paging);
        mav.addObject("pagingContext", pagingContext);
        logger.debug(
                ":当前总页数={},当前总条数={}",
                new Object[] { pagingContext.getTotalPageCnt(),
                        pagingContext.getTotalRowCnt() });
        // 加载页面检索项目
        mav.addObject("patientSn", patientSn);
        mav.addObject("visitListSearchPra", visitListSearchPra);
        // 动态加载检索框
        String conditionValue = request.getParameter("conditionValue");
        mav.addObject("conditionValue", conditionValue==null?"off":conditionValue);
        mav.addObject("VISIT_TYPE_CODE_PHYSICALEXAM", Constants.VISIT_TYPE_CODE_PHYSICALEXAM);
        mav.addObject("VISIT_TYPE_CODE_PHYSICALEXAM_BOSS", Constants.VISIT_TYPE_CODE_PHYSICALEXAM_BOSS);
        mav.addObject("VISIT_TYPE_CODE_EMERGENCY_ST", Constants.VISIT_TYPE_CODE_EMERGENCY_ST);
        mav.addObject("VISIT_TYPE_CODE_INPATIENT_TR", Constants.VISIT_TYPE_CODE_INPATIENT_TR);
        // 标识符清空
        mav.addObject("senSave", null);
        // 迁移到指定页面
        mav.setViewName("/visit/visitList");
        return mav;
    }

    /**
     * V05-102:就诊记录详细
     * 显示就诊记录详细信息画面
     * 
     * @param visitSn
     * @return
     * @throws Exception
     */
    @RequestMapping("/detail_{visitSn}")
    public ModelAndView detail(WebRequest request,@PathVariable("visitSn") String visitSn)
            throws Exception
    {
    	ModelAndView mav = new ModelAndView();
         // 详细标志
        String flag = request.getParameter("flag");
        // 就诊详细取得
        VisitDetailDto visitDetail = visitService.selectVisitDetail(visitSn);
        // 转科转床
        if(visitDetail==null)
        {
            visitDetail=new  VisitDetailDto();
        }
        List<TransferHistory> transferResult = visitService.selectTransferHistory(
                visitDetail.getPatientSn().toString(), visitSn);
        // 用血申请单
        BloodRequestDto[] bloodReResult = visitService.selectBoodRequest(
                visitDetail.getPatientSn().toString(), visitSn);
        /*
         * for(int i=0;i<bloodReResult.length;i++){ List<BloodSpecialRequest>
         * bsr=bloodReResult[i].getRequestSn(); }
         */
        // $Author:wei_peng
        // $Date:2012/10/25 14:52
        // [BUG]0010732 MODIFY BEGIN
        List<BloodDeliveryOrderDto[]> bloodReOrResultList = new ArrayList<BloodDeliveryOrderDto[]>();
        
        Map<Object,Object> backMap = new HashMap<Object,Object>();
        if(bloodReResult != null && bloodReResult.length != 0)
        {
            for (BloodRequestDto bloodRequest : bloodReResult)
            {
                // 取血单aaa
                BloodDeliveryOrderDto[] bloodReOrResult = visitService.selectBoodDeOrder(
                        visitSn, bloodRequest.getRequestSn().toString());
                if (bloodReOrResult != null && bloodReOrResult.length != 0)
                {
                    bloodReOrResultList.add(bloodReOrResult);
                    
                    //如果存在取血单，需要传递取血单号作为参数，获取医嘱闭环链接
                    boolean showClosedCycle = false;
                    // 医嘱闭环外部链接显示开关
                    if(Constants.COMM_INTERFACE.equals(Constants.ORDER_CLOSED_LOOP)){
	                    for(BloodDeliveryOrderDto dto : bloodReOrResult){
	                    	//取就诊类别
	                    	Map<String, Object> cond = new HashMap<String,Object>();
	                    	cond.put("visitSn", visitSn);
	                    	List<Object> list = entityDao.selectByCondition(MedicalVisit.class, cond);
	                    	MedicalVisit mv = (MedicalVisit)list.get(0);
	                    	
	                    	//有取血单才会有闭环信息
	                    	Map<Object,Object> map = new HashMap<Object,Object>();
	                    	if(Constants.lstVisitTypeOutPatient().contains(mv.getVisitTypeCode())
	                    			&& Constants.lstDomainOutPatient().contains(mv.getPatientDomain())
	                    			&& Constants.VISIT_TYPE_CODE_EMERGENCY.equals(mv.getVisitTypeCode())){  //门诊中的急诊有闭环
	                        	Map<String,String> mzMap = new HashMap<String,String>();
	                        	mzMap.put("visitTypeCode", mv.getVisitTypeCode());
	                        	mzMap.put("routeCode", null);
	                        	mzMap.put("type", "1");
	                        	mzMap.put("uniqueCode", bloodRequest.getOrderLid());
	                        	map = controller.showClosedCycle(dto.getPatientDomain(), null, mzMap, mav,mv.getVisitTypeCode());
	                        	if("true".equalsIgnoreCase(map.get("showClosedCycle").toString())){
	                        		showClosedCycle = true;
	                        	}
	                        	backMap.put(dto.getOrderLid(), map.get("linkUrl"));
	                    	}else if(Constants.lstVisitTypeInPatient().contains(mv.getVisitTypeCode())
	                    			&& Constants.lstDomainInPatient().contains(mv.getPatientDomain())
	                    			){  //住院，就诊的住院都应该有
	                    		Map<String,String> zyMap = new HashMap<String,String>();
	                	        zyMap.put("orderType", null);
	                	        zyMap.put("orderCode", null);
	                	        zyMap.put("orderLid", bloodRequest.getOrderLid());
	                	        zyMap.put("bloodReOrResult", "yes");
	                	        map = new HashMap<Object,Object>();
	                	        map = controller.showClosedCycle(dto.getPatientDomain(),zyMap,null,mav,mv.getVisitTypeCode());
	                	        if("true".equalsIgnoreCase(map.get("showClosedCycle").toString())){
	                        		showClosedCycle = true;
	                        	}
	                        	backMap.put(dto.getOrderLid(), map.get("linkUrl"));
	                    	}
	                    }
                    }
                    if(backMap!=null&&backMap.size()!=0){
                    	mav.addObject("showClosedCycle", showClosedCycle);
                    	mav.addObject("backMap", backMap);
                    }
                }
            }
        }
        
        // 输血申请单和输血记录单信息 added by yang_jianbo @ 2014-3-17
        List<BloodRequestAndRecordDto> requestAndRecordList = new ArrayList<BloodRequestAndRecordDto>();
        //
        Map<String, Object> requestMap = new HashMap<String, Object>();
        requestMap.put("patientSn", visitDetail.getPatientSn().toString());
        requestMap.put("visitSn", visitSn);
		List<Object> requestList = entityDao.selectByCondition(BloodRequest.class, requestMap);
		List<Object> menuList = new ArrayList<Object>();
        for(int i=0;i<requestList.size();i++){
        	BloodRequestAndRecordDto dto = new BloodRequestAndRecordDto();
        	BloodRequest req = new BloodRequest();
        	req = (BloodRequest)requestList.get(i);
        	
        	dto.setRequestNo(req.getRequestNo());
        	dto.setBloodClassName(req.getBloodClassName());
        	dto.setBloodReasonName(req.getBloodReasonName());
        	dto.setQuantity(req.getQuantity());
        	dto.setQuantityUnit(req.getQuantityUnit());
        	dto.setClinicalDiagnosis(req.getClinicalDiagnosis());
        	
        	 Map<String, Object> map = new HashMap<String, Object>();
        	 map.put("requestSn", req.getRequestSn());
        	 map.put("visitSn", visitSn);
        	 map.put("deleteFlag", "0");
        	 menuList = entityDao.selectByCondition(BloodRecordMenu.class, map);
        	 List<BloodRecordMenuDto>  brm = new ArrayList<BloodRecordMenuDto>();
        	 if(menuList.size()>0){
        		//循环取输血记录单外层信息
            	 for(int j=0;j<menuList.size();j++){
            		 BloodRecordMenu menu = new BloodRecordMenu();
            		 BloodRecordMenuDto menuDto = new BloodRecordMenuDto();
            		 menu = (BloodRecordMenu)menuList.get(j);
            		 
            		 menuDto.setRequestBloodAboName(menu.getRequestBloodAboName());
            		 menuDto.setRequestBloodRhName(menu.getRequestBloodRhName());
            		 menuDto.setRecheckBloodAboName(menu.getRecheckBloodAboName());
            		 menuDto.setRecheckBloodRhName(menu.getRequestBloodRhName());
            		 menuDto.setRemark(menu.getRemark());
            		 
            		 //循环输血记录单内层信息
            		 map = new HashMap<String, Object>();
            		 map.put("menuSn", menu.getMenuSn());
            		 map.put("deleteFlag", "0");
             		 List<Object> recordList = entityDao.selectByCondition(BloodRecord.class, map);
                 	 List<BloodRecord> record = new ArrayList<BloodRecord>();
             		 for(int k=0;k<recordList.size();k++){
             			BloodRecord br = new BloodRecord();
             			br = (BloodRecord)recordList.get(k);
             			if(k==0){
             				menuDto.setCrossMatchResult(br.getCrossMatchResult());
             				menuDto.setMatchBloodPersonName(br.getMatchBloodPersonName());
             				menuDto.setMatchBloodDate(br.getMatchBloodDate());
             				menuDto.setAssignedPersonName(br.getAssignedPersonName());
             				menuDto.setSendBloodDate(br.getSendBloodDate());
             				menuDto.setPlayingEntityName(br.getPlayingEntityName());
             				menuDto.setPlayingDate(br.getPlayingDate());
             			}
             			record.add(br);
             		 }
             		 menuDto.setBloodRecord(record);
             		 
             		 map = new HashMap<String, Object>();
             		 map.put("menuSn", menu.getMenuSn());
             		 map.put("deleteFlag", "0");
            		 List<Object> resultList = entityDao.selectByCondition(ExamResult.class, map);
            		 List<ExamResult> result = new ArrayList<ExamResult>();
             		 for(int o=0;o<resultList.size();o++){
             			 ExamResult er = new ExamResult();
             			 er = (ExamResult)resultList.get(o);
             			 if("不规则抗体筛查".equalsIgnoreCase(er.getItemName())){
             				menuDto.setIrregularImmuneResult(er.getItemResult());
             			 }
             			result.add(er);
             		 }
             		 menuDto.setExamResult(result);
             		 
             		 StringBuffer bf = new StringBuffer();
                     String bloodSpecialRequest = "";
                     map = new HashMap<String, Object>();
                     map.put("menuSn", menu.getMenuSn());
                     map.put("deleteFlag", "0");
            		 List<Object> specialList = entityDao.selectByCondition(BloodRecordSpecialRequest.class, map);
            		 for(int p=0;p<specialList.size();p++){
            			 BloodRecordSpecialRequest special = new BloodRecordSpecialRequest();
            			 special = (BloodRecordSpecialRequest)specialList.get(p);
            			 bf.append(special.getSpecialRequestName()).append(",");
            		 }
                     
                     if(!StringUtils.isEmpty(bf.toString())){
                    	bloodSpecialRequest = bf.toString().substring(0, bf.toString().length()-1);
                     }
                     menuDto.setBloodSpecialRequestName(bloodSpecialRequest);
             		 
            		 brm.add(menuDto);
            	 }
            	 dto.setRecordMenu(brm);
            	 requestAndRecordList.add(dto);
        	 }
        }
        
        //如果输血记录单未到达，就不应该再前端显示输血记录单
        if(menuList.size()==0){
        	requestAndRecordList = new ArrayList<BloodRequestAndRecordDto>();
        }
        
        // [BUG]0010732 MODIFY END
        // 召回信息
        WithdrawRecordDto[] withdrawRecordDto = visitService.selectWithdrawRecord(visitSn);
        
        mav.addObject("visitDetail", visitDetail);
        mav.addObject("transferResult", transferResult);
        mav.addObject("bloodReResult", bloodReResult);
        mav.addObject("bloodReOrResultList", bloodReOrResultList);
        mav.addObject("requestAndRecordList", requestAndRecordList);
        mav.addObject("withdrawRecordDto", withdrawRecordDto);
        mav.addObject("flag",flag);
        mav.setViewName("/visit/visitDetail");
        return mav;
    }
    
    //wang.meng
    //检查患者是否有就诊信息
    @RequestMapping("/check")
    public ModelAndView check(WebRequest request,
            HttpServletResponse response) throws Exception
    {
    	String visible =null;
    	String patientSn = request.getParameter("patientSn");
        String sysDate = DateUtils.dateToString(DateUtils.getSystemTime(),
                "yyyy-MM-dd");
        // 获取有效的门诊就诊条数
        int outCount = visitService.getOutCount(patientSn,sysDate,Constants.VISIT_TYPE_CODE_INPATIENT);
        // 获取有效的住院就诊条数
        int inCount = visitService.getInCount(patientSn, Constants.VISIT_TYPE_CODE_INPATIENT);
        if(outCount>0 || inCount>0){
        	visible="1";
        }else{
        	visible="0";
        }
        String result = "{\"visible\":\"" + visible+"\"}";
        PrintWriter out = response.getWriter();
        // 将反馈时间信息写回到页面
        out.write(result);
        out.close();
        return null;
    }

    // $Author : wu_jianfeng
    // $Date : 2012/09/28 17:49$
    // [BUG]0010107 MODIFY BEGIN
    // 就诊索引视图修改
    /**
     * 就诊索引视图
     * @param patientSn 患者序列号
     * @return
     * @throws Exception
     */
    @RequestMapping("/{patientSn}")
    public ModelAndView mainView(@PathVariable("patientSn") String patientSn,
            HttpSession session) throws Exception
    {
        ModelAndView mav = new ModelAndView();
        session.setAttribute("patientSn", patientSn);

        // 获取登陆用户名
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUserDetails userDetails = (LoginUserDetails) authentication.getPrincipal();
        String username = userDetails.getDisplayUsername();
        String userSn = userDetails.getUsername();

        // $Author:wu_jianfeng
        // $Date : 2012/12/21 14:10
        // $[BUG]0012967 MODIFY BEGIN
        // 得到系统设置信息
        UserSettings userSettings = userDetails.getUserSettings();

        // 得到患者信息
        Patient patient = patientService.getPatient(patientSn);
        
        if(patient == null)
        {
            patient = new Patient();
        }

        // 门诊域
        List outpatientDomains = Constants.lstDomainOutPatient();;

        // 住院域
        List inpatientDomains = Constants.lstDomainInPatient();;

        List patientEids = new ArrayList();
        String patientEid = patient.getPatientEid();
        if (patientEid != null && !patientEid.trim().isEmpty())
            patientEids.add(patientEid);

        // $Author:wu_biao
        // $Date:2012/9/18 10:42
        // $[BUG]BUG0009594 MODIFY BEGIN
        List<PatientCrossIndexDto> pciList_out = new ArrayList();
        List<PatientCrossIndexDto> pciList_in = new ArrayList();
        if (patientEids.size() != 0)
        {
            // $Author:chang_xuewen
            // $Date : 2013/12/18 14:10
            // $[BUG]0040770 MODIFY BEGIN
            pciList_out = patientService.selectPatientCrossIndex(patientEids,
                    outpatientDomains, Constants.OUTPATIENT_CONDITION_TAG, null);
            pciList_in = patientService.selectPatientCrossIndex(patientEids,
                    inpatientDomains, Constants.INPATIENT_CONDITION_TAG, null);
            // $[BUG]0040770 MODIFY END
        }
        // $[BUG]BUG0009594 MODIFY END
        String outpatientNo = null;
        String inpatientNo = null;

        if (pciList_out != null && pciList_out.size() != 0)
            outpatientNo = pciList_out.get(0).getOutpatientNo();
        if (pciList_in != null && pciList_in.size() != 0)
            inpatientNo = pciList_in.get(0).getInpatientNo();

        if (outpatientNo != null && "".equals(outpatientNo.trim()))
        {
            outpatientNo = null;
        }
        if (inpatientNo != null && "".equals(inpatientNo.trim()))
        {
            inpatientNo = null;
        }
        
        mav.addObject("outpatientNo", outpatientNo);
        mav.addObject("inpatientNo", inpatientNo);
        
        // Author:jia_yanqing
        // Date : 2012/12/24 17:21
        // [BUG]0012701 ADD BEGIN
        String sysDate = DateUtils.dateToString(DateUtils.getSystemTime(),
                "yyyy-MM-dd");
        // 获取有效的门诊就诊条数
//        int outCount = visitService.getOutCount(patientSn,sysDate, "03");
        int outCount = visitService.getOutCount(patientSn, sysDate, Constants.VISIT_TYPE_CODE_INPATIENT);
        // 获取有效的住院就诊条数
//        int inCount = visitService.getInCount(patientSn, "03");
        int inCount = visitService.getInCount(patientSn, Constants.VISIT_TYPE_CODE_INPATIENT);
        mav.addObject("out_count", outCount);
        mav.addObject("in_count", inCount);
        // [BUG]0012701 ADD END

        // 得到患者警告信息
        String patientAlerts = patientService.getPatientAlerts(patientSn);

        // 获取患者的最近的就诊，用于计算年龄
        MedicalVisit medicalVisit = visitService.getLastMedicalVisit(patientSn);
        // $Author:bin_zhang
        // $Date : 2013/1/4 17:21
        // $[BUG]0012875 ADD BEGIN
        List<PatientFolderMemoDto> patientFolderMemo = patientFolderService.selectPatientFolderMemo(userSn, patientSn);
        mav.addObject("patientFolderMemo", patientFolderMemo);
        // $[BUG]0012875 ADD END
        String inpatientType = userDetails.getInpatientType();
        if(null!=inpatientType && !inpatientType.equals("") && Constants.obtainViewTypesMap().containsKey(userDetails.getInpatientType())){
        	mav.addObject("viewType", Constants.obtainViewTypesMap().get(inpatientType));
        	mav.addObject("sceneType", Constants.obtainSceneTypesMap().get("05"));
        	if(null != userDetails.getSceneType() && !userDetails.getSceneType().equals("") && Constants.obtainSceneTypesMap().containsKey(userDetails.getSceneType())){
            	mav.addObject("sceneType", Constants.obtainSceneTypesMap().get(userDetails.getSceneType()));       		
        	}
        }else{
        	mav.addObject("viewType", "visitIndexView");
        }
       // mav.addObject("viewType", "visitIndexView");
        
        //Added by yang_jianbo@2014-4-18  判断用户是否存在pdf打印权限  A0047为打印权限ID
        //有限判断该用户对应的角色是否存在
        boolean showPrinter = false;
        /**
         * 查询访问控制权限的并集
         */
        List<String> actlAuthList = accessControlService.getUserAuths(userSn);
        if(actlAuthList.size()>0){
        	for(String auth:actlAuthList){
        		if(auth.equals(Constants.PRINT_AUTH_ID)){
        			showPrinter = true;
        			break;
        		}
        	}
        }
/*        List<SystemUserRole> roleList  = roleService.selectSystemRoleNo(userSn);
        if(roleList.size()>0){
        	for(SystemUserRole role : roleList){
        		//根据角色NO和权限ID判断是否存在对应的记录
        		List<SystemRoleAuth> authList =  roleService.selectSystemRoleAuth(role.getSystemRoleId(),Constants.PRINT_AUTH_ID);
        		if(authList.size()>0){
        			showPrinter = true;
        			break;
        		}
        	}
        }*/
        
    	List<SystemAccountAuth> accountList = accessControlService.selectSystemAccountAuth(Constants.PRINT_AUTH_ID, userSn);
    	if(accountList.size()>0){
         	showPrinter = true;
        }
        mav.addObject("showPrinter", showPrinter);
        mav.addObject("patientSn", patientSn);
        mav.addObject("age", DateUtils.caluAge(patient.getBirthDate(),
                medicalVisit == null ? null : medicalVisit.getVisitDate()));
        mav.addObject("patient", patient);
        mav.addObject("patientAlerts", patientAlerts);
        mav.addObject("visitIndexViewDisplay",
                userSettings.getVisitIndexViewSettings().getShowView());
        mav.addObject("outpatientViewDisplay",
                userSettings.getOutpatientViewSettings().getShowView());
        mav.addObject("inpatientViewDisplay",
                userSettings.getInpatientViewSettings().getShowView());
        mav.addObject("timerViewDisplay",
                userSettings.getTimerViewSettings().getShowView());
        mav.addObject("normalViewDisplay",
                userSettings.getNormalViewSettings().getShowView());
        // $[BUG]0012967 MODIFY END
        // [BUG]12309 ADD END
        mav.addObject("username", username);

        // $Author :jin_peng
        // $Date : 2012/09/13 17:49$
        // [BUG]0009712 ADD BEGIN
        // 传入用户id
        mav.addObject("userSn", userSn);
        // [BUG]0009712 ADD END
        
        mav.addObject("hidePatientFlag",
                StringUtils.isEmpty(userDetails.getHidePatientFlag()) ? "false"
                        : userDetails.getHidePatientFlag());
        
       
       //   $Author :yang_mingjie
       //   $Date : 2014/08/11 8:49$
       //   为医院定制化logo图片
       //   [BUG]0046046 ADD BEGIN  
        String mainBgPic = Constants.COMPANY_MAIN_PIC;
        //web服务启动时，如果properties目录下存在定制的图片，会按照用户配置的路径复制到项目中，读取项目
        if(!StringUtils.isEmpty(Constants.HOSPITAL_MAIN_PIC)){
			// 获取cdr项目所在的根路径
			String webPath = new File(this.getClass().getResource("/").getPath()).getAbsolutePath();
			// 处理windows与linux之间的差异
			if (webPath.contains("WEB-INF")) {
				webPath = webPath.substring(0, webPath.indexOf("WEB-INF"));
			}

			String picPath = Constants.HOSPITAL_PIC_FOLDER + "/"
					+ Constants.HOSPITAL_MAIN_PIC;

			File file = new File(webPath + picPath);
			if (file.exists() && file.isFile()) {
				mainBgPic  = picPath;
			}
		}
        mav.addObject("mainBgPic",mainBgPic);
        // $[BUG]0046046 ADD END
        
        mav.setViewName("/visit/mainView");
        return mav;
    }

    // [BUG]0010107 MODIFY END

    // $Author : wu_jianfeng
    // $Date : 2012/09/28 17:49$
    // [BUG]0010107 ADD BEGIN
    // 就诊索引视图修改
    /**
     * 就诊索引视图内容部分
     * @param patientSn 患者序列号
     * @return
     * @throws Exception
     */
    @RequestMapping("/visitIndexViewPart_{patientSn}")
    public ModelAndView viewIndexViewPart(WebRequest request,
            @PathVariable("patientSn") String patientSn) throws Exception
    {
        // 患都内部序列号
        if (StringUtil.isEmpty(patientSn))
        {
            logger.error("缺少患者内部序列号参数!");
        }

        int cols = Integer.parseInt(Constants.VISIT_INDEX_VIEW_COLS);
        int rows = Integer.parseInt(Constants.VISIT_INDEX_VIEW_ROWS);
        int total = cols * rows;
        // 初始化pagingContext
        // PagingContext pagingContext = PagingContextHolder.getPagingContext();
        // 定义页面检索元素DTO
        VisitIndexListSearchPra visitListSearchPra = new VisitIndexListSearchPra();
        // 就诊类型
        String visitType = request.getParameter("visitType");
        // 医疗机构编码
        String orgCode = request.getParameter("orgCode");

        // 诊断名称
        String diagnosisName = request.getParameter("diagnosisName");
        
        // 诊断列表
        List<VisitDiagnosisDto> list = diagnosisService.selectMainDiagnosisByPatientSn(patientSn);
        List<String> diagnosisNameList = new ArrayList<String>();
        for(int i = 0; i < list.size(); i++){
        	diagnosisNameList.add(list.get(i).getDiseaseName());
        }
        
        // $Author:wu_jianfeng
        // $Date : 2012/11/26 14:10
        // $[BUG]0011511 ADD BEGIN
        String visitDept = request.getParameter("visitDept");
        String[] visitDeptStrs = request.getParameterValues("visitDept[]");
        List<String> visitDepts = new ArrayList<String>();
        if (visitDept != null)
            visitDepts.add(visitDept);
        if (visitDeptStrs != null && visitDeptStrs.length != 0)
        {
            for (String dept : visitDeptStrs)
                visitDepts.add(dept);
        }
        // $[BUG]0011511 ADD END

        // 当前日期
        // $Author:bin_zhang
        // $Date : 2012/10/17 14:10
        // $[BUG]0010403 ADD BEGIN
        String sysDate = DateUtils.dateToString(DateUtils.getSystemTime(),
                "yyyy-MM-dd");
        // $[BUG]0010403 ADD END
        // $Author:wu_jianfeng
        // $Date : 2012/11/26 14:10
        // $[BUG]0011511 DELETE BEGIN
        // if (Constants.OPTION_SELECT.equalsIgnoreCase(visitDept))
        // {
        // visitDept = null;
        // }
        // $[BUG]0011511 DELETE END

        // 下拉框未选择时处理
        visitListSearchPra.setVisitTypeCode(Constants.OPTION_SELECT.equalsIgnoreCase(visitType) ? null
                : visitType);
        visitListSearchPra.setDiagnosisName(Constants.OPTION_SELECT.equalsIgnoreCase(diagnosisName) ? null
                : diagnosisName);
        
        visitListSearchPra.setOrgCode(Constants.OPTION_SELECT.equalsIgnoreCase(orgCode) ? null
                : orgCode);
        visitListSearchPra.setVisitDepts(visitDepts);
        // $Author:bin_zhang
        // $Date : 2012/10/17 14:10
        // $[BUG]0010403 ADD BEGIN
        visitListSearchPra.setSysDate(sysDate);
        // $[BUG]0010403 ADD END
        // 就诊类型
        String pageNo = request.getParameter("pageNo");
        if (StringUtil.isEmpty(pageNo))
        {
            pageNo = "1";
        }

        visitListSearchPra.setVisitTypeInpatientCode(Constants.lstVisitTypeInPatient());

        // 调用业务层逻辑查询分页控制时对象集合，pagingContext返回值中会覆写totalRowCnt totalPageCnt
        List<VisitIndexListDto> allVisitIndexs = visitService.selectVisitIndexList(
                patientSn, visitListSearchPra);

        // $Author:wu_jianfeng
        // $Date : 2012/11/20 14:10
        // $[BUG]0011694 DELETE BEGIN
        // VisitIndexSort viSort = new VisitIndexSort(allVisitIndexs,
        // "DeptComparator");
        // $[BUG]0011694 DELETE END
        // 默认是按日期降序排列
        List<VisitIndexListDto> allSortVisitIndexs = allVisitIndexs;

        // pagingContext.setRows(allVisitIndexs.toArray());
        // // 设置页面初始每页显示条目数,pagingContext默认值是16，(其他参数也可以修正，会影响检索结果)可根据需要修正。
        // pagingContext.setRowCnt(total);

        // 检索对象为空check
        if (allSortVisitIndexs == null || allSortVisitIndexs.size() == 0)
        {
            logger.debug("没有检索对象!");
            // 页面显示页面数赋值0
            // pagingContext.setPageNo(0);
            pageNo = "1";
        }

        List<VisitIndexListDto> pageVisitList = new ArrayList<VisitIndexListDto>();
        
        int allSize = 0;

        if(allSortVisitIndexs != null && !allSortVisitIndexs.isEmpty())
        {
            allSize = allSortVisitIndexs.size();
            int fromPageSize = total * (Integer.parseInt(pageNo) - 1);
            int toPageSize = total * Integer.parseInt(pageNo);
            if (allSize <= toPageSize)
            {
                for (int index = fromPageSize; index < allSize; index++)
                    pageVisitList.add(allSortVisitIndexs.get(index));
                for (int count = 0; count < toPageSize - allSize; count++)
                    pageVisitList.add(null);
            }
            else
            {
                for (int index = fromPageSize; index < toPageSize; index++)
                    pageVisitList.add(allSortVisitIndexs.get(index));
            }
        }

        // 获得分页的visitSn集合
        List<String> visitSns = new ArrayList<String>();
        for (VisitIndexListDto visitIndex : pageVisitList)
        {
            if (visitIndex != null)
                visitSns.add(visitIndex.getVisitSn());
        }

        // $Author:bin_zhang
        // $Date : 2012/11/20 14:10
        // $[BUG]11399 ADD BEGIN

        // 获得分页的visitSn集合
        List<String> Inpatient_visitSns = new ArrayList<String>();
        for (VisitIndexListDto visitList : pageVisitList)
        {
            if (visitList != null)
                if (Constants.lstVisitTypeInPatient().contains(visitList.getVisitTypeCode()))
                {
                    Inpatient_visitSns.add(visitList.getVisitSn());
                }

        }
        if (!Inpatient_visitSns.isEmpty())
        {
            List<MedicalVisit> mvs = visitService.getInpatientVisitDoctors(Inpatient_visitSns);
            
            if(mvs != null && !mvs.isEmpty())
            {
                for (VisitIndexListDto visitList : pageVisitList)
                {
                    if (visitList != null)
                        for (MedicalVisit mv : mvs)
                        {
                            if (visitList.getVisitSn().equals(
                                    mv.getVisitSn().toString()))
                            {
                                visitList.setVisitDoctorName(mv.getVisitDoctorName());
                            }
                        }
                }
            }
        }
        // $[BUG]11399 ADD END

        // $Author:wu_jianfeng
        // $Date : 2012/10/15 13:26
        // $[BUG]0010400 MODIFY BEGIN
        // 访问控制实现
        // 得到登录用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUserDetails userDetails = (LoginUserDetails) authentication.getPrincipal();
        String userSn = userDetails.getUsername();

        AccessControlDto aclAuths = null;
        if (AccessControl.useACL())
        {
            AccessControl acl = userDetails.getAccessControl();
            aclAuths = acl.getAccessControlAuths();
        }

        // 访问控制权限开启
        if (AccessControl.useACL())
        {
            // 如果查看诊断信息权限
            if (aclAuths.getClinicalInfoAuth01())
            {
                // 得到诊断信息
                if (visitSns.size() != 0)
                {
                    Map<String, String> vdMap = diagnosisService.selectVisitDiagnosis(visitSns);
                    
                    if(vdMap != null && !vdMap.isEmpty())
                    {
                        for (VisitIndexListDto visitIndex : pageVisitList)
                        {
                            if (visitIndex != null)
                            {
                                if (vdMap.containsKey(visitIndex.getVisitSn()))
                                    visitIndex.setMainDiagnosis(vdMap.get(visitIndex.getVisitSn()));
                            }
                        }
                    }
                }
            }
        }
        else
        {
            // 得到诊断信息
            if (visitSns.size() != 0)
            {
                Map<String, String> vdMap = diagnosisService.selectVisitDiagnosis(visitSns);
                
                if(vdMap != null && !vdMap.isEmpty())
                {
                    for (VisitIndexListDto visitIndex : pageVisitList)
                    {
                        if (visitIndex != null)
                        {
                            if (vdMap.containsKey(visitIndex.getVisitSn()))
                                visitIndex.setMainDiagnosis(vdMap.get(visitIndex.getVisitSn()));
                        }
                    }
                }
            }
        }
        // $[BUG]0010400 MODIFY END

        // $Author:wu_jianfeng
        // $Date : 2012/11/20 13:26
        // $[BUG]0011694 ADD BEGIN
        List<KeyValueDto> aclVisitDepts = visitService.getVisitDepts(patientSn,
                visitListSearchPra.getVisitTypeInpatientCode(),
                visitListSearchPra.getSysDate());
        // $[BUG]0011694 ADD END

        // $Author:tong_meng 
        // $Date : 2013/11/07 10:10
        // $[BUG]0039034 ADD BEGIN
        // 将病区去掉在界面上显示，其他不变
        StringUtils.cutString(pageVisitList, "dischargeWardName", "病区", "");
        StringUtils.cutString(pageVisitList, "dischargeBedNo", "床", "");
        // $[BUG]0039034 ADD END

        ModelAndView mav = new ModelAndView();
        mav.addObject("aclAuths", aclAuths);
        mav.addObject("accessContorl", AccessControl.useACL());
        mav.addObject("patientSn", patientSn);
        mav.addObject("pageNo", pageNo);
        mav.addObject("cols", cols);
        mav.addObject("rows", rows);
        mav.addObject("allSize", allSize);
        mav.addObject("visitListSearchPra", visitListSearchPra);
        mav.addObject("VISIT_TYPE_CODE_OUTPATIENT",
                Constants.VISIT_TYPE_CODE_OUTPATIENT);
        mav.addObject("VISIT_TYPE_CODE_INPATIENT_TR",
                Constants.VISIT_TYPE_CODE_INPATIENT_TR);
        mav.addObject("VISIT_TYPE_CODE_INPATIENT",
                Constants.VISIT_TYPE_CODE_INPATIENT);
        mav.addObject("VISIT_TYPE_CODE_PHYSICALEXAM",
                Constants.VISIT_TYPE_CODE_PHYSICALEXAM);
        mav.addObject("VISIT_TYPE_CODE_PHYSICALEXAM_BOSS",
                Constants.VISIT_TYPE_CODE_PHYSICALEXAM_BOSS);
        mav.addObject("VISIT_STATUS_DISCHARGE_HOSPITAL",
                Constants.VISIT_STATUS_DISCHARGE_HOSPITAL);
        mav.addObject("VISIT_STATUS_OUT_HOSPITAL",
                Constants.VISIT_STATUS_OUT_HOSPITAL);
        mav.addObject("VISIT_TYPE_CODE_EMERGENCY",
                Constants.VISIT_TYPE_CODE_EMERGENCY);
        mav.addObject("VISIT_TYPE_CODE_EMERGENCY_ST",
                Constants.VISIT_TYPE_CODE_EMERGENCY_ST);
        // 设置每行显示查询对象
        mav.addObject("visitList", pageVisitList);
        mav.addObject("visitDepts", aclVisitDepts);
        mav.addObject("diagnosisNameList", diagnosisNameList);
        mav.addObject("diagnosisName", diagnosisName);
        // mav.addObject("pagingContext", pagingContext);
        mav.setViewName("/visit/visitIndexViewPart");
        return mav;
    }

    // [BUG]0010107 ADD END

    /**
     * V05-001:临床信息集成视图
     * 按临床信息模式集成显示患者信息
     * @param domainId 域ID
     * @param patientLid 患者Lid
     * @return
     * @throws Exception
     */
    @RequestMapping("/{domainId}_{patientLid}")
    public ModelAndView mainView(@PathVariable("domainId") String domainId,
            @PathVariable("patientLid") String patientLid) throws Exception
    {
        ModelAndView mav = new ModelAndView();
        String patientSn = patientService.getPatientSn(domainId, patientLid);
        mav.addObject("patientSn", patientSn);
        mav.setViewName("/visit/mainView");
        return mav;
    }

    /**
     * V05-001:临床信息集成视图
     * 按临床信息模式集成显示患者信息
     * @param patientSn 患者序列号
     * @return
     * @throws Exception
     */
    @RequestMapping("/part_{patientSn}")
    public ModelAndView mainViewPart(WebRequest request,
            @PathVariable("patientSn") String patientSn) throws Exception
    {
        // 患都内部序列号
        if (StringUtil.isEmpty(patientSn))
        {
            logger.error("缺少患者内部序列号参数!");
        }

        PagingContext pagingContext = PagingContextHolder.getPagingContext();

        // 设置就诊显示的记录数
        String visitListRowsCntStr = AppSettings.getConfig(Constants.CFG_VISITLIST_ROWS_COUNT);
        int visitListRowsCnt = Integer.parseInt(visitListRowsCntStr);
        pagingContext.setRowCnt(visitListRowsCnt);

        // $Author:wu_jianfeng
        // $Date : 2012/10/25 14:10
        // $[BUG]0010778 MODIFY BEGIN
        VisitListSearchPra visitListSearchPra = new VisitListSearchPra();
        String sysDate = DateUtils.dateToString(DateUtils.getSystemTime(),
                "yyyy-MM-dd");
        visitListSearchPra.setSysDate(sysDate);
        visitListSearchPra.setVisitTypeInpatientCode(Constants.lstVisitTypeInPatient());
        visitListSearchPra.setPatientSn(patientSn);
        // 得到就诊记录信息
        VisitListDto[] visitList = visitService.selectVisitList(
                visitListSearchPra, pagingContext);
        // $[BUG]0010778 MODIFY END

        // $Author:bin_zhang
        // $Date : 2012/11/20 14:10
        // $[BUG]11399 ADD BEGIN
        List<VisitListDto> visit_list=new  ArrayList<VisitListDto>();
        if(visitList !=null)
        {
         visit_list = Arrays.asList(visitList);
        }
        // 获得分页的visitSn集合
        List<String> Inpatient_visitSns = new ArrayList<String>();
        for (VisitListDto vList : visit_list)
        {
            if (visitList != null)
                if (Constants.lstVisitTypeInPatient().contains(vList.getVisitTypeCode()))
                {
                    Inpatient_visitSns.add(vList.getVisitSn());
                }

        }
        if (!Inpatient_visitSns.isEmpty())
        {
            List<MedicalVisit> mvs = visitService.getInpatientVisitDoctors(Inpatient_visitSns);
            
            if(mvs != null && !mvs.isEmpty())
            {
                for (VisitListDto vList : visit_list)
                {
                    if (visitList != null)
                        for (MedicalVisit mv : mvs)
                        {
                            if (vList.getVisitSn().equals(
                                    mv.getVisitSn().toString()))
                            {
                                vList.setVisitDoctorName(mv.getVisitDoctorName());
                            }
                        }
                }
            }
        }
        // $[BUG]11399 ADD END

        List<VisitListDto> pageVisitList = new ArrayList<VisitListDto>(
                visit_list);
        if(visitList !=null)
        {
        for (int count = 0; count < visitListRowsCnt - visitList.length; count++)
            pageVisitList.add(null);
        }

        // 得到就诊列表记录总条数
        int visitTotalRowCount=0;
        if( pagingContext!=null)
        {
           visitTotalRowCount = pagingContext.getTotalRowCnt();
        }

        // 设置诊断显示的记录数
        String diagnosisListRowsCntStr = AppSettings.getConfig(Constants.CFG_DIAGNOSISLIST_ROWS_COUNT);
        int diagnosisListRowsCnt = Integer.parseInt(diagnosisListRowsCntStr);
        pagingContext.setRowCnt(diagnosisListRowsCnt);

        // 得到诊断信息
        List<DiagnosisDto> diagnosisList = diagnosisService.selectAllDiagnosis(
                patientSn, null, null, null, null, null, null, null, null, "1",
                pagingContext);
        
        List<DiagnosisDto> pageDiagnosisList = new ArrayList<DiagnosisDto>();
        if(diagnosisList !=null)
        {
                pageDiagnosisList = new ArrayList<DiagnosisDto>(
                diagnosisList);

        for (int count = 0; count < diagnosisListRowsCnt - diagnosisList.size(); count++)
            pageDiagnosisList.add(null);
        }

        // 得到诊断列表记录总条数
        int diagnosisTotalRowCount = pagingContext.getTotalRowCnt();

        // 设置药物显示的记录数
        String drugListRowsCntStr = AppSettings.getConfig(Constants.CFG_DRUGLIST_ROWS_COUNT);
        int drugListRowsCnt = Integer.parseInt(drugListRowsCntStr);
        pagingContext.setRowCnt(drugListRowsCnt);
        /*
         * $Author :bin_zhang $Date : 2012/10/25 13:03$ [BUG]0010500 ADD BEGIN
         */
        // 得到药物信息
        // $Author:binzhang
        // $Date : 2012/12/14 
        // $[BUG]0012510 MODIFY BEGIN
        DrugListSearchPra drugListSearchPra=new DrugListSearchPra();
        drugListSearchPra.setTemporaryFlag( Constants.LONG_TERM_FLAG);
        
        // 得到登录用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUserDetails userDetails = (LoginUserDetails) authentication.getPrincipal();
        String userSn = userDetails.getUsername();
        
        MedicationOrderDto[] longDrugList = drugListService.selectDrugList(
                patientSn, drugListSearchPra, userSn, pagingContext);
      // $[BUG]0012510 MODIFY END
        
        List<MedicationOrderDto> pageLongDrugList = null;
        
        if(longDrugList != null && longDrugList.length != 0)
        {
            pageLongDrugList = new ArrayList<MedicationOrderDto>(
                    Arrays.asList(longDrugList));
        }
        else
        {
            pageLongDrugList = new ArrayList<MedicationOrderDto>();
        }
        /*
         * $Author :bin_zhang $Date : 2012/10/25 13:03$ [BUG]11602 MODIFY BEGIN
         */
        // 得到长期药物信息列表记录总条数
        int longDrugTotalRowCount = pagingContext.getTotalRowCnt();
        /* [BUG]11602 MODIFY END */
        if( longDrugList!=null)
        {
        for (int count = 0; count < drugListRowsCnt - longDrugList.length; count++)
            pageLongDrugList.add(null);
        }
        // $Author:binzhang
        // $Date : 2012/12/14 
        // $[BUG]0012510 MODIFY BEGIN
        drugListSearchPra.setTemporaryFlag(Constants.TEMPORARY_FLAG);
        MedicationOrderDto[] shortDrugList = drugListService.selectDrugList(
                patientSn,drugListSearchPra, userSn, pagingContext);
       // $[BUG]0012510 MODIFY END
        
        
        List<MedicationOrderDto> pageShortDrugList = null;
        
        if(shortDrugList != null && shortDrugList.length != 0)
        {
            pageShortDrugList = new ArrayList<MedicationOrderDto>(
                    Arrays.asList(shortDrugList));
        }
        else
        {
            pageShortDrugList = new ArrayList<MedicationOrderDto>();
        }
        /*
         * $Author :bin_zhang $Date : 2012/10/25 13:03$ [BUG]11602 MODIFY BEGIN
         */
        // 得到短期药物信息列表记录总条数
        int shortDrugTotalRowCount = pagingContext.getTotalRowCnt();
        /* [BUG]11602 MODIFY END */
        if(shortDrugList !=null)
        {
        for (int count = 0; count < drugListRowsCnt - shortDrugList.length; count++)
            pageShortDrugList.add(null);
        }
        /*
         * [BUG]0010500 ADD END
         */

        // 设置检查显示的记录数
        String examListRowsCntStr = AppSettings.getConfig(Constants.CFG_EXAMLIST_ROWS_COUNT);
        int examListRowsCnt = Integer.parseInt(examListRowsCntStr);
        pagingContext.setRowCnt(examListRowsCnt);

        // 得到检查信息
        ExamListSearchParameters examListSearchPra = new ExamListSearchParameters();
        ExamListDto[] examList = examService.selectExamListSearch(patientSn,
                examListSearchPra, pagingContext);
        
        List<ExamListDto> pageExamList = null;
        
        if(examList != null && examList.length != 0)
        {
            pageExamList = new ArrayList<ExamListDto>(
                    Arrays.asList(examList));
        }
        else
        {
            pageExamList = new ArrayList<ExamListDto>();
        }
        if(examList !=null)
        {
        for (int count = 0; count < examListRowsCnt - examList.length; count++)
            pageExamList.add(null);
        }

        // 得到检查列表记录总条数
        int examTotalRowCount = pagingContext.getTotalRowCnt();

        // 设置检验显示的记录数
        String labListRowsCntStr = AppSettings.getConfig(Constants.CFG_LABLIST_ROWS_COUNT);
        int labListRowsCnt = Integer.parseInt(labListRowsCntStr);
        pagingContext.setRowCnt(labListRowsCnt);

        // 得到检验信息
        LabListSearchParameters labListSearchParameters = new LabListSearchParameters();
        LabDto[] labList = labService.selectAllLabList(patientSn,
                labListSearchParameters, pagingContext);
        
        List<LabDto> pageLabList = null;
        
        if(labList != null && labList.length != 0)
        {
            pageLabList = new ArrayList<LabDto>(Arrays.asList(labList));
        }
        else
        {
            pageLabList = new ArrayList<LabDto>();
        }
        if(labList !=null)
        {
        for (int count = 0; count < labListRowsCnt - labList.length; count++)
            pageLabList.add(null);
        }

        // 得到检验列表记录总条数
        int labTotalRowCount = pagingContext.getTotalRowCnt();
        
        /*
         * $Author :bin_zhang $Date : 2012/10/25 13:03$ [BUG]0010500 ADD BEGIN
         */
        // 访问控制实现
        AccessControlDto aclAuths = null;
        if (AccessControl.useACL())
        {
            AccessControl acl = userDetails.getAccessControl();
            aclAuths = acl.getAccessControlAuths();
        }

        ModelAndView mav = new ModelAndView();
        mav.addObject("aclAuths", aclAuths);
        mav.addObject("useACL", AccessControl.useACL());
        /* [BUG]0010500 ADD END */
        mav.addObject("patientSn", patientSn);
        mav.addObject("visitList", pageVisitList);
        mav.addObject("diagnosisList", pageDiagnosisList);
        mav.addObject("drugLongList", pageLongDrugList);
        mav.addObject("drugShortList", pageShortDrugList);
        mav.addObject("examList", pageExamList);
        mav.addObject("labList", pageLabList);

        // 设置各业务列表的每页显示条数及总记录条数，为在页面判断是否显示更多链接
        mav.addObject("visitRowCount", visitListRowsCnt);
        mav.addObject("visitTotalRowCount", visitTotalRowCount);
        mav.addObject("diagnosisRowCount", diagnosisListRowsCnt);
        mav.addObject("diagnosisTotalRowCount", diagnosisTotalRowCount);
        mav.addObject("drugRowCount", drugListRowsCnt);
        mav.addObject("longDrugTotalRowCount", longDrugTotalRowCount);
        mav.addObject("shortDrugTotalRowCount", shortDrugTotalRowCount);
        mav.addObject("examRowCount", examListRowsCnt);
        mav.addObject("examTotalRowCount", examTotalRowCount);
        mav.addObject("labRowCount", labListRowsCnt);
        mav.addObject("labTotalRowCount", labTotalRowCount);

        // $Author:wu_jianfeng
        // $Date : 2012/12/21 14:10
        // $[BUG]0012967 ADD BEGIN
        mav.addObject("viewSettings", userDetails.getUserSettings().getNormalViewSettings());
        // $[BUG]0012967 ADD END

        mav.setViewName("/visit/mainViewPart");
        return mav;
    }

    /**
     * V05-001:临床信息集成视图左侧菜单
     * @param patientSn 患者序列号
     * @return
     * @throws Exception
     */
    @RequestMapping("/menuPart_{patientSn}")
    public ModelAndView mainViewMenuPart(WebRequest request,
            @PathVariable("patientSn") String patientSn) throws Exception
    {
        if (StringUtil.isEmpty(patientSn))
        {
            logger.error("缺少患者内部序列号参数!");
        }   
        ModelAndView mav = new ModelAndView();
         
        // $Author:wang_yanbo
        // $Date : 2014/07/24 15:05
        // $[BUG]0046529 MODIFY BEGIN
        // 病案文档链接
        String[] urls = LINK_BINGAN.split(";");
        String[] rulnames = IS_SCAN.split(";");
        List<SettingUrl> urllist = geturl(urls,rulnames,request);
        mav.addObject("urllist", urllist);
        // $[BUG]0046529 MODIFY END
        mav.addObject("patientSn", patientSn);
        // 得到登录用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUserDetails userDetails = (LoginUserDetails) authentication.getPrincipal();
        String userSn = userDetails.getUsername();

        // $Author:bin_zhang
        // $Date : 2012/09/18 13:26
        // $[BUG]0009791 MODIFY BEGIN
        // 访问控制实现
        AccessControlDto aclAuths = null;
        if (AccessControl.useACL())
        {
            AccessControl acl = userDetails.getAccessControl();
            aclAuths = acl.getAccessControlAuths();
        }
        // $[BUG]0009791 MODIFY END
        UserSettings userSetting = userDetails.getUserSettings();
        String displayMenus = userSetting.getDisplayMenus();
        if(displayMenus == "-1")
            displayMenus = ""; 

        // 得到系统所有的菜单
        List<SystemMenu> systemMenus = systemService.getAllSystemMenu();
        String allMenus = "";
        
        if(systemMenus != null && !systemMenus.isEmpty())
        {
            for (SystemMenu menu : systemMenus)
            {
                if (allMenus != "")
                    allMenus += ",";
                allMenus += menu.getMenuCode();
            }
        }  
        if(displayMenus == null)
            displayMenus = allMenus;
        
        String userId = userDetails.getUsername();
        boolean isSystemAdmin = systemService.isSystemAdmin(userId);
        
        mav.addObject("isSystemAdmin", isSystemAdmin);
        mav.addObject("displayMenus", displayMenus);
        mav.addObject("allMenus", allMenus);
        // $Author:bin_zhang
        // $Date : 2012/09/18 13:26
        // $[BUG]0009791 MODIFY BEGIN
        // 访问控制实现
        mav.addObject("aclAuths", aclAuths);
        mav.addObject("useACL", AccessControl.useACL());
        // $[BUG]0009791 MODIFY END
        mav.setViewName("/visit/mainViewMenuPart");
        return mav;
    }

	// $Author:bin_zhang
    // $Date:2012/9/28 10:42
    // $[BUG]BUG0010082 ADD BEGIN
    
    /**
     * V05-001:临床信息集成视图
     * 按门诊信息模式集成显示患者信息
     * @param visitSn 患者序列号
     * @return
     * @throws Exception
     */
    @RequestMapping("/outpatientViewPart")
    public ModelAndView outpatientViewPart(WebRequest request, HttpSession session) throws Exception
    {
        String patientSn = request.getParameter("patientSn");
        String visitSn = request.getParameter("visitSn");
        String orgCode = request.getParameter("orgCode");

        if (Constants.OPTION_SELECT.equals(orgCode))
        {
            orgCode = null;
        }

        if (StringUtil.isEmpty(visitSn) && StringUtil.isEmpty(patientSn))
            logger.error("就诊内部序列号和患者内部序列号参数不能同时为空!");
        
        VisitDetailDto visitDetail = new VisitDetailDto();
        List<VisitDateDto> visitDates = new ArrayList<VisitDateDto>();

        // $Author:wu_jianfeng
        // $Date : 2012/10/25 14:10
        // $[BUG]0010778 MODIFY BEGIN
        VisitListSearchPra visitListSearchPra = new VisitListSearchPra();
        // 如果就诊Sn不为空，按照就诊Sn，得到患者信息
        if (!StringUtil.isEmpty(visitSn))
        {
            visitDetail = visitService.selectVisitDetail(visitSn);
            
            if(visitDetail != null)
            {
                patientSn = visitDetail.getPatientSn().toString();
            }
            
            visitListSearchPra.setPatientSn(patientSn);
            String sysDate = DateUtils.dateToString(DateUtils.getSystemTime(),
                    "yyyy-MM-dd");
            visitListSearchPra.setSysDate(sysDate);
            visitListSearchPra.setVisitTypeInpatientCode(Constants.lstVisitTypeInPatient());
            visitListSearchPra.setOrgCode(orgCode);
            visitDates = visitService.getOutpatientVisitDateList(visitListSearchPra);
        }
        else
        {
            visitListSearchPra.setPatientSn(patientSn);
            String sysDate = DateUtils.dateToString(DateUtils.getSystemTime(),
                    "yyyy-MM-dd");
            visitListSearchPra.setSysDate(sysDate);
            visitListSearchPra.setVisitTypeInpatientCode(Constants.lstVisitTypeInPatient());
            visitListSearchPra.setOrgCode(orgCode);
            visitDates = visitService.getOutpatientVisitDateList(visitListSearchPra);
            if (visitDates != null && visitDates.size() != 0)
            {
                visitSn = visitDates.get(0).getVisitSn().toString();
                visitDetail = visitService.selectVisitDetail(visitSn);
            }
        }
        // $[BUG]0010778 MODIFY END
        // $Author:chang_xuewen
        // $Date : 2013/05/16 15:20
        // $[BUG]0032417 MODIFY BEGIN        
        savePara(patientSn,visitSn);
        // $[BUG]0032417 MODIFY END

        // 得到登录用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUserDetails userDetails = (LoginUserDetails) authentication.getPrincipal();

        AccessControlDto aclAuths = null;
        if (AccessControl.useACL())
        {
            AccessControl acl = userDetails.getAccessControl();
            aclAuths = acl.getAccessControlAuths();
        }

        // 通过患者序列号获取患者详细信息
        Patient patient = patientService.getPatient(patientSn);

        String diagnosisMainStr = "";
        String diagnosisCiStr = "";

        if (!StringUtils.isEmpty(visitSn))
        {
            // 得到主诊断和次要诊断信息（逗号分隔）
            List<Diagnosis> diagnosisList = diagnosisService.selectDiagnosisByVisitSn(visitSn);
            
            if(diagnosisList != null && !diagnosisList.isEmpty())
            {
                for (Diagnosis diagnosis : diagnosisList)
                {
                    // Author :jia_yanqing
                    // Date : 2012/12/26 16:00
                    // [BUG]0012797 MODIFY BEGIN
                    String diseaseName = diagnosis.getDiseaseName();
                    String certain = diagnosis.getUncertainFlag();

                    if (diagnosis.getMainDiagnosisFlag().equals(
                            Constants.MAIN_DIAGNOSIS))
                    {
                        if (diagnosisMainStr != "")
                            diagnosisMainStr += "，";
                        if (!StringUtils.isEmpty(diseaseName))
                            diagnosisMainStr += diseaseName;
                        if(Constants.CERTAIN_DB.equals(certain))
                            diagnosisMainStr += "？";
                    }
                    else if (diagnosis.getMainDiagnosisFlag().equals(
                            Constants.MINOR_DIAGNOSIS))
                    {
                        if (diagnosisCiStr != "")
                            diagnosisCiStr += "，";
                        if (!StringUtils.isEmpty(diseaseName))
                            diagnosisCiStr += diseaseName;
                        if(Constants.CERTAIN_DB.equals(certain))
                            diagnosisCiStr += "？";
                    }
                    // [BUG]0012797 MODIFY END
                }
            }
        }

        ModelAndView mav = new ModelAndView();
        mav.addObject("patient", patient);
        mav.addObject("patientSn", patientSn);
        mav.addObject("visitDetail", visitDetail);
        mav.addObject("currentVisitSn", visitDetail.getVisitSn());
        mav.addObject("diagnosisMain", diagnosisMainStr);
        mav.addObject("diagnosisCi", diagnosisCiStr);
        mav.addObject("visitDates", visitDates);
        mav.addObject("orgCode", orgCode);
        mav.addObject("loginUserAclAuths", aclAuths);
        mav.addObject("accessContorl", AccessControl.useACL());
        // 设置影像中心URL地址
        mav.addObject("imageCenterUrl", AppSettings.getConfig(Constants.IMAGE_CENTER_URL));
        // 设置签章服务器URL地址
        mav.addObject("signatureUrl", AppSettings.getConfig(Constants.SIGNATURE_URL));

        // $Author:wu_jianfeng
        // $Date : 2012/12/21 14:10
        // $[BUG]0012967 ADD BEGIN
        mav.addObject("viewSettings", userDetails.getUserSettings().getOutpatientViewSettings());
        // $[BUG]0012967 ADD END
      
        mav.setViewName("/outpatient/outpatientView");
        return mav;
    }

    @RequestMapping("/obtainVisitDateCondition")
    public ModelAndView obtainVisitDateCondition(WebRequest request)
            throws Exception
    {
        String patientSn = request.getParameter("patientSn");
        String orgCode = request.getParameter("orgCode");

        if (Constants.OPTION_SELECT.equals(orgCode))
        {
            orgCode = null;
        }

        VisitListSearchPra visitListSearchPra = new VisitListSearchPra();

        visitListSearchPra.setPatientSn(patientSn);
        String sysDate = DateUtils.dateToString(DateUtils.getSystemTime(),
                "yyyy-MM-dd");
        visitListSearchPra.setSysDate(sysDate);
        visitListSearchPra.setVisitTypeInpatientCode(Constants.lstVisitTypeInPatient());
        visitListSearchPra.setOrgCode(orgCode);

        List<VisitDateDto> visitDates = new ArrayList<VisitDateDto>();
        visitDates = visitService.getOutpatientVisitDateList(visitListSearchPra);

        ModelAndView mav = new ModelAndView();
        mav.addObject("visitDates", visitDates);
        mav.addObject("orgCode", orgCode);

        mav.setViewName("/outpatient/condition");

        return mav;
    }

    // $Author:chang_xuewen
    // $Date : 2013/05/16 15:20
    // $[BUG]0032417 ADD BEGIN
    //以下5个业务方法不会单独调用，专用于载入门诊试图后加载，
    //因此条件信息保存后赋值
    String patient_sn = null;
    String visit_sn = null;
    private void savePara(String patientSn,String visitSn){
    	patient_sn = patientSn;
    	visit_sn = visitSn;    	
    }     
    /**
     * 
     */
    @RequestMapping("/drugPart")
    public ModelAndView drugPart(WebRequest request, HttpSession session) throws Exception
    {
    	// 初始化pagingContext
        PagingContext pagingContext_drug = PagingContextHolder.getPagingContext();
        // 药物信息
        MedicationOrder[] drugList = null;
        String patientSn = patient_sn;
        String visitSn = visit_sn;
        
        if (StringUtil.isEmpty(visitSn) && StringUtil.isEmpty(patientSn))
            logger.error("就诊内部序列号和患者内部序列号参数不能同时为空!");
        
        VisitDetailDto visitDetail = new VisitDetailDto();

        // 得到登录用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUserDetails userDetails = (LoginUserDetails) authentication.getPrincipal();
        String userSn = userDetails.getUsername();
        AccessControlDto aclAuths = null;
        if (AccessControl.useACL())
        {
            AccessControl acl = userDetails.getAccessControl();
            aclAuths = acl.getAccessControlAuths();
        }
        // 设置每页显示条目数,目前要求药物大于50条时进行分页显示
        pagingContext_drug.setRowCnt(Integer.parseInt(Constants.ROWCNT_PER_PAGE_YW));
        // 得到药物信息
        drugList = drugListService.selectDrugListVist(visitSn, userSn, pagingContext_drug);
        if (drugList == null || drugList.length == 0)
        {
            logger.debug("没有检索对象！");
            // 页面显示页面数赋值0
            pagingContext_drug.setPageNo(0);
        }
        // 页面初始化分页页码显示列表
        PagingHelper.initPaging(pagingContext_drug);
        
        ModelAndView mav = new ModelAndView();
        mav.addObject("drugList", drugList);
        mav.addObject("visitDetail", visitDetail);
        mav.addObject("loginUserAclAuths", aclAuths);
        mav.addObject("accessContorl", AccessControl.useACL());
        mav.addObject("viewSettings", userDetails.getUserSettings().getOutpatientViewSettings());
        // 加载pagingContext对象  
        pagingContext_drug.setRows(drugList);
        //增加分页对象到页面
        mav.addObject("pagingContext_drug", pagingContext_drug);
        logger.debug("药物医嘱相关：当前总页数={}, 当前总条数={},当前页数={},每页显示数量={}", new Object[] {
        		pagingContext_drug.getTotalPageCnt(),
        		pagingContext_drug.getTotalRowCnt(), pagingContext_drug.getPageNo(),
        		pagingContext_drug.getRowCnt()});
        mav.setViewName("/outpatient/drugPart");
        return mav;
    }
    
    /**
     * 
     */
    @RequestMapping("/examPart")
    public ModelAndView examPart(WebRequest request, HttpSession session) throws Exception
    {
    	// 初始化pagingContext
        PagingContext pagingContext_exam = PagingContextHolder.getPagingContext();
        // 检查信息
        ExamListDto[] examList = null;
        String patientSn = patient_sn;
        String visitSn = visit_sn;
        
        if (StringUtil.isEmpty(visitSn) && StringUtil.isEmpty(patientSn))
            logger.error("就诊内部序列号和患者内部序列号参数不能同时为空!");
        
        VisitDetailDto visitDetail = new VisitDetailDto();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUserDetails userDetails = (LoginUserDetails) authentication.getPrincipal();
        AccessControlDto aclAuths = null;
        if (AccessControl.useACL())
        {
            AccessControl acl = userDetails.getAccessControl();
            aclAuths = acl.getAccessControlAuths();
        }
        // $[BUG]0010778 MODIFY END
        // 设置每页显示条目数,目前要求药物大于50条时进行分页显示
        pagingContext_exam.setRowCnt(Integer.parseInt(Constants.ROWCNT_PER_PAGE_YW));
        // 检查信息获取
        examList = examService.selectExamListSearch(visitSn, pagingContext_exam);
        if (examList == null || examList.length == 0)
        {
            logger.debug("没有检索对象！");
            // 页面显示页面数赋值0
            pagingContext_exam.setPageNo(0);
        }
        // 页面初始化分页页码显示列表
        PagingHelper.initPaging(pagingContext_exam);
        // $Author:wei_peng
        // $Date:2013/3/22 10:12
        // [BUG]0012739 ADD BEGIN
        if(examList != null && examList.length > 0)
        {
            for(ExamListDto examDto:examList)
            {
                examDto.setErImagingFinding(StringUtils.replaceStr(examDto.getErImagingFinding()));
                examDto.setErImagingConclusion(StringUtils.replaceStr(examDto.getErImagingConclusion()));
                examDto.setEiImagingFinding(StringUtils.replaceStr(examDto.getEiImagingFinding()));
                examDto.setEiImagingConclusion(StringUtils.replaceStr(examDto.getEiImagingConclusion()));
            }
        }
        // [BUG]0012739 ADD END
        ModelAndView mav = new ModelAndView();
        // 设置影像中心URL地址
        mav.addObject("imageCenterUrl", AppSettings.getConfig(Constants.IMAGE_CENTER_URL));
        // 设置签章服务器URL地址
        mav.addObject("signatureUrl", AppSettings.getConfig(Constants.SIGNATURE_URL));
        mav.addObject("examList", examList);
        mav.addObject("visitDetail", visitDetail);
        mav.addObject("loginUserAclAuths", aclAuths);
        mav.addObject("accessContorl", AccessControl.useACL());
        mav.addObject("viewSettings", userDetails.getUserSettings().getOutpatientViewSettings());
        pagingContext_exam.setRows(examList);
        mav.addObject("pagingContext_exam", pagingContext_exam);
        logger.debug("检查医嘱相关：当前总页数={}, 当前总条数={},当前页数={},每页显示数量={}", new Object[] {
        		pagingContext_exam.getTotalPageCnt(),
        		pagingContext_exam.getTotalRowCnt(), pagingContext_exam.getPageNo(),
        		pagingContext_exam.getRowCnt()});
        mav.setViewName("/outpatient/examPart");
        return mav;
    }
    
    /**
     * 
     */
    @RequestMapping("/labPart")
    public ModelAndView labPart(WebRequest request, HttpSession session) throws Exception
    {
    	// 初始化pagingContext
        PagingContext pagingContext_lab = PagingContextHolder.getPagingContext();
        // 检验信息
        LabDto[] labList = null;
        String patientSn = patient_sn;
        String visitSn = visit_sn;
        if (StringUtil.isEmpty(visitSn) && StringUtil.isEmpty(patientSn))
            logger.error("就诊内部序列号和患者内部序列号参数不能同时为空!");
        
        VisitDetailDto visitDetail = new VisitDetailDto();

     // 得到登录用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUserDetails userDetails = (LoginUserDetails) authentication.getPrincipal();
        AccessControlDto aclAuths = null;
        if (AccessControl.useACL())
        {
            AccessControl acl = userDetails.getAccessControl();
            aclAuths = acl.getAccessControlAuths();
        }
        // $[BUG]0010778 MODIFY END
        pagingContext_lab.setRowCnt(Integer.parseInt(Constants.ROWCNT_PER_PAGE_YW));
        // 检验信息获取
        labList = labService.selectLabList(visitSn,pagingContext_lab);
        if (labList == null || labList.length == 0)
        {
            logger.debug("没有检索对象！");
            // 页面显示页面数赋值0
            pagingContext_lab.setPageNo(0);
        }
        // 页面初始化分页页码显示列表
        PagingHelper.initPaging(pagingContext_lab);
        
        ModelAndView mav = new ModelAndView();
        // 设置签章服务器URL地址
        mav.addObject("signatureUrl", AppSettings.getConfig(Constants.SIGNATURE_URL));
        mav.addObject("labList", labList);
        mav.addObject("visitDetail", visitDetail);
        mav.addObject("loginUserAclAuths", aclAuths);
        mav.addObject("accessContorl", AccessControl.useACL());
        mav.addObject("viewSettings", userDetails.getUserSettings().getOutpatientViewSettings());
        pagingContext_lab.setRows(labList);
        mav.addObject("pagingContext_lab", pagingContext_lab);
        logger.debug("检验医嘱相关：当前总页数={}, 当前总条数={},当前页数={},每页显示数量={}", new Object[] {
        		pagingContext_lab.getTotalPageCnt(),
        		pagingContext_lab.getTotalRowCnt(), pagingContext_lab.getPageNo(),
        		pagingContext_lab.getRowCnt()});
        mav.setViewName("/outpatient/labPart");
        return mav;
        
    }
    
    /**
     * 
     */
    @RequestMapping("/docPart")
    public ModelAndView docPart(WebRequest request, HttpSession session) throws Exception
    {
    	// 初始化pagingContext
        PagingContext pagingContext_doc = PagingContextHolder.getPagingContext();
        // 病历信息
        List<ClinicalDocument> docList = new ArrayList<ClinicalDocument>();
        String patientSn = patient_sn;
        String visitSn = visit_sn;
        
        if (StringUtil.isEmpty(visitSn) && StringUtil.isEmpty(patientSn))
            logger.error("就诊内部序列号和患者内部序列号参数不能同时为空!");
        
        VisitDetailDto visitDetail = new VisitDetailDto();

     // 得到登录用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUserDetails userDetails = (LoginUserDetails) authentication.getPrincipal();
        AccessControlDto aclAuths = null;
        if (AccessControl.useACL())
        {
            AccessControl acl = userDetails.getAccessControl();
            aclAuths = acl.getAccessControlAuths();
        }
        // $[BUG]0010778 MODIFY END
        pagingContext_doc.setRowCnt(Integer.parseInt(Constants.ROWCNT_PER_PAGE_YW));
        // 病历信息获得
        docList = docService.selectDoc(visitSn,pagingContext_doc);
        if (docList == null || docList.size()==0)
        {
            logger.debug("没有检索对象！");
            // 页面显示页面数赋值0
            pagingContext_doc.setPageNo(0);
        }
        // 页面初始化分页页码显示列表
        PagingHelper.initPaging(pagingContext_doc);
        
        ModelAndView mav = new ModelAndView();
        mav.addObject("docList", docList);
        mav.addObject("visitDetail", visitDetail);
        mav.addObject("loginUserAclAuths", aclAuths);
        mav.addObject("accessContorl", AccessControl.useACL());
        mav.addObject("viewSettings", userDetails.getUserSettings().getOutpatientViewSettings());
        if(docList !=null)
        {
            pagingContext_doc.setRows(docList.toArray());
        }
        mav.addObject("pagingContext_doc", pagingContext_doc);
        logger.debug("病历文书相关：当前总页数={}, 当前总条数={},当前页数={},每页显示数量={}", new Object[] {
        		pagingContext_doc.getTotalPageCnt(),
        		pagingContext_doc.getTotalRowCnt(), pagingContext_doc.getPageNo(),
                pagingContext_doc.getRowCnt()});
        mav.setViewName("/outpatient/docPart");
        return mav;
    }
    
    /**
     * 
     */
    @RequestMapping("/otherPart")
    public ModelAndView otherPart(WebRequest request, HttpSession session) throws Exception
    {
    	// 初始化pagingContext
        PagingContext pagingContext_other = PagingContextHolder.getPagingContext();
        // 其他医嘱信息
        List<NonDrugOrderDto> orderList = new ArrayList<NonDrugOrderDto>();
        String patientSn = patient_sn;
        String visitSn = visit_sn;
        if (StringUtil.isEmpty(visitSn) && StringUtil.isEmpty(patientSn))
            logger.error("就诊内部序列号和患者内部序列号参数不能同时为空!");
        
        VisitDetailDto visitDetail = new VisitDetailDto();
     // 得到登录用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUserDetails userDetails = (LoginUserDetails) authentication.getPrincipal();
        AccessControlDto aclAuths = null;
        if (AccessControl.useACL())
        {
            AccessControl acl = userDetails.getAccessControl();
            aclAuths = acl.getAccessControlAuths();
        }
        // $[BUG]0010778 MODIFY END
        pagingContext_other.setRowCnt(Integer.parseInt(Constants.ROWCNT_PER_PAGE_YW));
        // 其他医嘱信息获得
        orderList = unMedOrdService.selectOrder(visitSn,pagingContext_other);
        if (orderList == null || orderList.size()==0)
        {
            logger.debug("没有检索对象！");
            // 页面显示页面数赋值0
            pagingContext_other.setPageNo(0);
        }
        
        if(orderList != null && !orderList.isEmpty())
        {
            for (NonDrugOrderDto nonDrugOrderDto : orderList)
            {
                switch (Integer.parseInt(nonDrugOrderDto.getOrderFlag()))
                {
                case NonDrugOrderDto.TREATMENT_ORDER:
                    // 设置诊疗医嘱详细访问路径
                    nonDrugOrderDto.setOrderPath("../order/treatment_"
                        + nonDrugOrderDto.getOrderSn() + ".html?flag=tabs");
                    break;
                case NonDrugOrderDto.CARE_ORDER:
                    // 设置护理医嘱详细访问路径
                    nonDrugOrderDto.setOrderPath("../order/care_"
                        + nonDrugOrderDto.getOrderSn() + ".html?flag=tabs");
                    break;
                case NonDrugOrderDto.PROCEDURE_ORDER:
                    // 设置手术医嘱详细访问路径
                    nonDrugOrderDto.setOrderPath("../order/procedure_"
                        + nonDrugOrderDto.getOrderSn() + ".html?flag=tabs");
                    break;
                // case NonDrugOrderDto.EXAMINATION_ORDER:
                // //设置检查医嘱详细访问路径
                // nonDrugOrderDto.setOrderPath("../order/exam_"+nonDrugOrderDto.getOrderSn()+".html");
                // break;
                // case NonDrugOrderDto.LAB_ORDER:
                // //设置检验医嘱详细访问路径
                // nonDrugOrderDto.setOrderPath("../order/lab_"+nonDrugOrderDto.getOrderSn()+".html");
                // break;
                case NonDrugOrderDto.GENERAL_ORDER:
                    // 设置其他医嘱详细访问路径
                    nonDrugOrderDto.setOrderPath("../order/general_"
                        + nonDrugOrderDto.getOrderSn() + ".html?flag=tabs");
                    break;
                case NonDrugOrderDto.CONSULTATION_ORDER:
                    // 设置会诊医嘱详细访问路径
                    nonDrugOrderDto.setOrderPath("../order/consultation_"
                        + nonDrugOrderDto.getOrderSn() + ".html?flag=tabs");
                    break;

                default:
                    break;
                }
            }
        }
        
        // 页面初始化分页页码显示列表
        PagingHelper.initPaging(pagingContext_other);
        
        // $[BUG]0032417 MODIFY END
        ModelAndView mav = new ModelAndView();
        if(orderList!=null)
        {
            pagingContext_other.setRows(orderList.toArray());
        }
        mav.addObject("orderList", orderList);  
        mav.addObject("visitDetail", visitDetail);
        mav.addObject("loginUserAclAuths", aclAuths);
        mav.addObject("accessContorl", AccessControl.useACL());
        mav.addObject("viewSettings", userDetails.getUserSettings().getOutpatientViewSettings());
        mav.addObject("pagingContext_other", pagingContext_other);
        logger.debug("其他医嘱相关：当前总页数={}, 当前总条数={},当前页数={},每页显示数量={}", new Object[] {
        		pagingContext_other.getTotalPageCnt(),
        		pagingContext_other.getTotalRowCnt(), pagingContext_other.getPageNo(),
        		pagingContext_other.getRowCnt()});
        // $[BUG]0032417 MODIFY END
        mav.setViewName("/outpatient/otherPart");
        return mav;
    }
    // $[BUG]0032417 ADD END
    

    // $[BUG]BUG0010082 ADD END

    // $Author:wei_peng
    // $Date:2012/10/11 14:22
    // $[BUG]0010276 ADD BEGIN
    /**
     * 用户意见反馈栏的用户反馈信息
     * @return
     * @throws Exception
     */
    @RequestMapping("/feedback")
    public ModelAndView feedback(WebRequest request) throws Exception
    {
        // 获取用ID和名称
        String userId = request.getParameter("userId");
        String userName = request.getParameter("userName");
        List<UserFeedback> userFeedbacks = null;
        String[] feedbackAdmins = Constants.FEEDBACK_ADMINS.split(",");
        if (Arrays.asList(feedbackAdmins).contains(userId))
        {
            // 获取用户反馈意见集合
            userFeedbacks = visitService.selectUserFeedbacks(null, null);
        }
        else
        {
            // 获取用户反馈意见集合
            userFeedbacks = visitService.selectUserFeedbacks(userId, null);
        }
        ModelAndView mav = new ModelAndView();
        // 设置用户反馈意见集合
        mav.addObject("userFeedbacks", userFeedbacks);
        // 设置用户ID
        mav.addObject("userId", userId);
        // 设置用户名称
        mav.addObject("userName", userName);
        // 跳转到用户反馈页面
        mav.setViewName("/visit/feedback");
        return mav;
    }

    /**
     * 将用户反馈的信息保存到数据库
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/feedbackCommit")
    public ModelAndView feedbackCommit(WebRequest request,
            HttpServletResponse response) throws Exception
    {
        // 用户反馈意见内容
        String feedbackContent = request.getParameter("feedbackContent");
        // 用户ID
        String userId = request.getParameter("userId");
        // 用户名称
        String userName = request.getParameter("userName");
        // 用户反馈父记录内部序列号
        String parentFeedbackSn = request.getParameter("parentFeedbackSn");
        // 用户反馈意见创建时间
        Date commitDate = new Date();

        UserFeedback userFeedback = new UserFeedback();
        // 用户ID
        userFeedback.setUserId(userId);
        // 用户反馈意见内容
        userFeedback.setContent(feedbackContent);
        // 用户名称
        userFeedback.setUserName(userName);
        // 用户反馈意见父记录
        if (StringUtils.isEmpty(parentFeedbackSn))
        {
            userFeedback.setParentFeedbackSn(null);
        }
        else
        {
            userFeedback.setParentFeedbackSn(new BigDecimal(parentFeedbackSn));
        }
        userFeedback.setReplyCount(new BigDecimal("0"));
        userFeedback.setCreateTime(commitDate);
        userFeedback.setUpdateTime(commitDate);
        userFeedback.setDeleteFlag(Constants.NO_DELETE_FLAG);
        userFeedback.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
        // 保存用户反馈意见
        BigDecimal replyCount = visitService.saveUserFeedback(userFeedback);
        String result = "{\"commitDate\":\""
            + DateUtils.dateToString(commitDate, "yyyy年MM月dd日HH:mm")
            + "\",\"feedbackSn\":\"" + userFeedback.getFeedbackSn()
            + "\",\"replyCount\":\"" + replyCount + "\"}";
        PrintWriter out = response.getWriter();
        // 将反馈时间信息写回到页面
        out.write(result);
        out.close();
        return null;
    }

    // $[BUG]0010276 ADD END

    /**
     * 查询对应的反馈记录所有的回复信息
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/feedbackReplays")
    public ModelAndView feedbackReplays(WebRequest request) throws Exception
    {
        // 用户反馈父记录内部序列号
        String parentFeedbackSn = request.getParameter("parentFeedbackSn");
        // 用户ID
        String userId = request.getParameter("userId");
        // 用户名称
        String userName = request.getParameter("userName");

        List<UserFeedback> userFeedbackReplys = visitService.selectUserFeedbacks(
                null, parentFeedbackSn);

        ModelAndView mav = new ModelAndView();
        // 设置用户反馈父记录内部序列号
        mav.addObject("parentFeedbackSn", parentFeedbackSn);
        // 设置用户反馈意见回复集合
        mav.addObject("userFeedbackReplys", userFeedbackReplys);
        // 设置用户ID
        mav.addObject("userId", userId);
        // 设置用户名称
        mav.addObject("userName", userName);
        // 跳转到用户反馈页面
        mav.setViewName("/visit/feedbackReplys");
        return mav;
    }
    private List<SettingUrl> geturl(String[] urls, String[] rulnames,
    		WebRequest request) {
        //通过页面传值获取住院号和门诊号
        String inpatientNo = request.getParameter("inpatientNo");
        String outpatientNo = request.getParameter("outpatientNo");
       
    	List<SettingUrl> urllist = new ArrayList<SettingUrl>();
    	String id = null;
    		 for(int i=0;i<urls.length;i++){
        		 id = urls[i].substring(urls[i].indexOf("{")+1, urls[i].lastIndexOf("}"));
        		 SettingUrl seturl = new SettingUrl();
        		 if("inpatientNo".equals(id)){
						// $Author:wang_yanbo
						// $Date:2014/08/08 14:22
						// $[BUG]0046864 ADD BEGIN
        			 if(inpatientNo != null && inpatientNo != ""){
        				seturl.setUrl(urls[i].replace("{inpatientNo}", inpatientNo));
         				seturl.setId(inpatientNo);
        			 }
        		 }
        		 if("outpatientNo".equals(id)){
        			 if(outpatientNo != null && outpatientNo != ""){
        				seturl.setUrl(urls[i].replace("{outpatientNo}", outpatientNo));
          				seturl.setId(outpatientNo);
        			 }
        			
        		 }
             	seturl.setUrlid(rulnames[i].split(",")[0]);
             	seturl.setUrlname(rulnames[i].split(",")[1]);
             	seturl.setScan(rulnames[i].split(",")[2]);
             	urllist.add(seturl);
             }
		return urllist;
	}
    
    /**
     * 修改账户的密码
     * */
    @RequestMapping("/changePsw")
    public ModelAndView changePsw(WebRequest request, HttpServletResponse response) throws Exception{
    	ModelAndView mav = new ModelAndView();
		mav.setViewName("visit/changePsw");
		return mav;   	
    }
    
    /**
     * 确认账户的密码
     * */
    @RequestMapping(value = "/checkPsw", method = RequestMethod.POST)
    public void checkPsw(WebRequest request, HttpServletResponse response) throws Exception{
    	LoginUserDetails userDetails = (LoginUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	String oldPassword = request.getParameter("oldPassword");
    	String p = userDetails.getPassword();
    	String oldPasswordMd5 = ResetPwUtil.md5(oldPassword);
		response.setCharacterEncoding("utf-8");
		response.getWriter().write("" + p.equalsIgnoreCase(oldPasswordMd5));
		response.getWriter().flush();
    }
    
    /**
     * 修改账户的密码
     * */
    @RequestMapping(value = "/changePswAfterChecked", method = RequestMethod.POST)
    public void changePswAfterChecked(WebRequest request, HttpServletResponse response) throws Exception{
    	LoginUserDetails userDetails = (LoginUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	String userId = userDetails.getUsername();
    	String userName = userDetails.getDisplayUsername();
    	String newPassword = request.getParameter("newPassword");
    	String newPasswordMD5 = ResetPwUtil.md5(newPassword);
    	boolean flag = userManagerService.changeUserPassword(userId, userName, newPasswordMD5);
    	response.setCharacterEncoding("utf-8");
		response.getWriter().write("" + flag);
		response.getWriter().flush();
    }
}
