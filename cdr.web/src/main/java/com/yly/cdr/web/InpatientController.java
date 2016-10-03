/**
 * Copyright (c) 2012—2012 Founder International Co.Ltd. All rights reserved.
 */
package com.yly.cdr.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.founder.fasf.web.paging.PagingContext;
import com.founder.fasf.web.paging.PagingContextHolder;
import com.yly.cdr.core.AppSettings;
import com.yly.cdr.core.Constants;
import com.yly.cdr.dto.AccessControlDto;
import com.yly.cdr.dto.TimerAndInpatientDto;
import com.yly.cdr.dto.VisitDateDto;
import com.yly.cdr.dto.lab.LabResultItemDto;
import com.yly.cdr.dto.visit.VisitListSearchPra;
import com.yly.cdr.entity.LabResult;
import com.yly.cdr.service.CommonService;
import com.yly.cdr.service.InpatientService;
import com.yly.cdr.service.LabService;
import com.yly.cdr.util.DateUtils;
import com.yly.cdr.util.ObjectUtils;
import com.yly.cdr.util.StringUtils;
import com.yly.cdr.web.loginValidation.AccessControl;
import com.yly.cdr.web.loginValidation.LoginUserDetails;

/**
 * 用于住院视图操作
 * @author jin_peng
 * @version 1.0, 2012/05/29
 *
 */
@Controller
@RequestMapping("/inpatient")
public class InpatientController
{

    @Autowired
    private LabService labService;

    @Autowired
    private InpatientService inpatientService;

    @Autowired
    private CommonService commonService;

    /**
     * 显示住院视图列表
     * @param request 上下文对象
     * @param patientSn 患者内部序列号
     * @return mav 数据视图对象
     */
    @RequestMapping("/list_{patientSn}")
    public ModelAndView list(WebRequest request, HttpSession session,
            @PathVariable("patientSn") String patientSn)throws Exception
    {
        // 获取共同分页对象
        PagingContext pagingContext = PagingContextHolder.getPagingContext();
        // logger.debug("显示第{}页的数据。", pagingContext.getPageNo());

        // 设置每页显示条数
        String visitRowsCntStr = AppSettings.getConfig(Constants.CFG_TIMER_INPATIENT_ROWS_COUNT);
        int visitRowsCnt = Integer.parseInt(visitRowsCntStr);

        // $Author :jin_peng
        // $Date : 2012/11/06 13:48$
        // [BUG]0011026 MODIFY BEGIN
        // $Author :jin_peng
        // $Date : 2012/09/28 13:48$
        // [BUG]0010132 MODIFY BEGIN
        // 获取住院类型编码
        String inpatientType = Constants.VISIT_TYPE_CODE_INPATIENT;

        // 获取页面住院开始时间
        String inpatientStartDate = request.getParameter("inpatientStartDate");
        // 获取页面就诊内部序列号，为了在就诊改变时显示不同颜色
        String visitSnChange = request.getParameter("visitSnChange");

        // 获取页面住院结束时间
        String inpatientEndDate = request.getParameter("inpatientEndDate");

        // 出院日期和就诊内部序列号串
        String dischargeDateAndVisitSn = request.getParameter("dischargeDateAndVisitSn");

        // 出院日期
        String dischargeDate = null;

        // 就诊内部序列号
        String visitSn = null;

        // 住院日期
        String visitDate = null;

        // 判断住院日期下拉框未选则内容时进行处理
        if (!Constants.OPTION_SELECT.equalsIgnoreCase(dischargeDateAndVisitSn)
            && !StringUtils.isEmpty(dischargeDateAndVisitSn))
        {
            String[] disDateAndVisitSn = dischargeDateAndVisitSn.split(";");

            // 出院日期
            dischargeDate = disDateAndVisitSn[0];
            if(StringUtils.isEmpty(dischargeDate)){
            	dischargeDate = DateUtils.dateToString(new Date(), DateUtils.PATTERN_MINUS_DATE);
            }
            // 就诊内部序列号
            visitSn = disDateAndVisitSn[1];

            // 住院日期
            visitDate = disDateAndVisitSn[2];
        }
                // 如果画面选择出院日期小于当前住院日期，默认为住院日期
        if(!StringUtils.isEmpty(inpatientEndDate) && !StringUtils.isEmpty(visitDate)){
        	if(DateUtils.stringToDate(inpatientEndDate,DateUtils.PATTERN_MINUS_DATE).before(
            		DateUtils.stringToDate(visitDate,DateUtils.PATTERN_MINUS_DATE))){
            	inpatientEndDate = visitDate;
            }
        }
        
        // 如果画面选择入院日期大于当前出院日期，默认为出院日期
        if(!StringUtils.isEmpty(inpatientStartDate) && !StringUtils.isEmpty(dischargeDate)){
        	if(DateUtils.stringToDate(inpatientStartDate,DateUtils.PATTERN_MINUS_DATE).after(
            		DateUtils.stringToDate(dischargeDate,DateUtils.PATTERN_MINUS_DATE))){
            	inpatientStartDate = dischargeDate;
            }
        }
        // 获取页面翻页标识参数(向前，向后)
        String pagingKindFlag = request.getParameter("pagingKindFlag");

        // 当开始结束都存在时需记录最初开始记录号
        String actuallyNumPaging = request.getParameter("actuallyNumPaging");

        // 实际开始记录号
        int actuallyNum = Integer.parseInt(StringUtils.isEmpty(actuallyNumPaging) ? "0"
                : actuallyNumPaging);
        // 记录开始条数
        int rowNumStart = 0;
        // 记录结束条数
        int rowNumEnd = 0;
        // 记录总数
        int totalCount = 0;
        // 住院次数集合
        List<TimerAndInpatientDto> visitTimesEtcList = null;

        // 页面传来分页记录的起始页
        int pageNo = pagingContext.getPageNo();
        
        String tempcode = request.getParameter("orgCodeFlag");      
        String orgCode = Constants.OPTION_SELECT.equalsIgnoreCase(tempcode) ? null : tempcode;

        // 住院次数查询
        visitTimesEtcList = inpatientService.selectVisitTimes(patientSn,
                inpatientType, Constants.EXIT_INPATIENT, orgCode);

        List<TimerAndInpatientDto> visitLine = getVisitLine(visitTimesEtcList);
        
        int[] startAndEndPosition = getStartAndEndPosition(visitLine, visitDate, dischargeDate, inpatientStartDate, inpatientEndDate);
        int startP = startAndEndPosition[0];
        int endP = startAndEndPosition[1]; 
        
        // 当点击查询按钮时按查询条件查询记录开始序号
        if (StringUtils.isEmpty(pagingKindFlag))
        {
//            actuallyNum = inpatientService.selectStartNumber(patientSn,
//                    inpatientType, inpatientStartDate, inpatientEndDate,
//                    visitSn, Constants.EXIT_INPATIENT, orgCode);
//            pageNo = actuallyNum;
        	pageNo = startP;
        }
        
        // 查询住院记录总数
//        totalCount = inpatientService.selectTotalCnt(patientSn, inpatientType,
//                inpatientStartDate, inpatientEndDate, dischargeDate,
//                Constants.EXIT_INPATIENT, orgCode);     
        totalCount = visitLine.size();
        

        // $Author :jin_peng
        // $Date : 2013/04/16 16:56$
        // [BUG]0015132 MODIFY BEGIN
        // 当起始页号小于0时使起始记录号始终保持1，结束记录号则根据实际起始记录号进行计算得出
        if (pageNo <= 0)
        {
            rowNumStart = 1;

            rowNumEnd = pageNo + visitRowsCnt - 1;
        }
        else
        {
            // 设置分页起始记录和结束记录
            rowNumStart = pageNo;

            rowNumEnd = rowNumStart + visitRowsCnt - 1;
        }

        // [BUG]0015132 MODIFY END

        // 当结束记录号大于总记录数时将进行截断操作，截断到总记录数，此限时针对住院开始日期和住院结束日期都存在的情况
        if (rowNumEnd > totalCount)
        {
            rowNumEnd = totalCount;
        }

        LoginUserDetails userDetails = (LoginUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AccessControlDto accessControlDto = new AccessControlDto();
        if (AccessControl.useACL())
        {
            accessControlDto = userDetails.getAccessControl().getAccessControlAuths();
        }
        //获取用户sn
        String userSn = getUserSn();
        
        // 获取时间轴页面显示的信息对象集合
        List<List<List<TimerAndInpatientDto>>> inpatientAllList = inpatientService.selectInpatientList(
                patientSn, inpatientType, inpatientStartDate, visitSnChange,
                rowNumStart, rowNumEnd, accessControlDto,
                AccessControl.useACL(), pagingKindFlag, visitRowsCnt, userSn, orgCode, visitLine);

        ModelAndView mav = new ModelAndView();

        // 权限控制类 
        mav.addObject("accessControl", accessControlDto);
        // 权限控制开关
        mav.addObject("useACLFlag", AccessControl.useACL());
        
        // $Author:tong_meng 
        // $Date : 2013/11/07 10:10
        // $[BUG]0039034 ADD BEGIN
        // 将病区去掉在界面上显示，其他不变
        StringUtils.cutString(inpatientAllList.get(0).get(0), "dischargeWardName", "病区", "");
        // $[BUG]0039034 ADD END
        
        // 将住院视图页面所需所有信息对象装入上下文
        mav.addObject("inpatientList", inpatientAllList.get(0).get(0));
        mav.addObject("diagnosisList", inpatientAllList.get(1));
        mav.addObject("drugOrderList", inpatientAllList.get(2));
        mav.addObject("examinationList", inpatientAllList.get(3));
        mav.addObject("labList", inpatientAllList.get(4));
        mav.addObject("procedureList", inpatientAllList.get(5));
        // $Author :chang_xuewen
	    // $Date : 2013/10/25 11:00$
	    // [BUG]0038443 ADD BEGIN
        mav.addObject("proceduredocList", inpatientAllList.get(19));
	    // [BUG]0038443 ADD END
        mav.addObject("documenationList", inpatientAllList.get(6));
        mav.addObject("noDrugOrderList", inpatientAllList.get(7));
        mav.addObject("existsFlag",
                inpatientAllList.get(0).get(0).get(0).getExistsFlag());
        mav.addObject(
                "fCount",
                inpatientAllList.get(0).get(0).get(0).getActuallyDisplayForwardCount());
        mav.addObject(
                "bCount",
                inpatientAllList.get(0).get(0).get(0).getActuallyDisplayBackwardCount());
        mav.addObject("vstSn",
                inpatientAllList.get(0).get(0).get(0).getVisitSn());

        // 将三测单除图形外内容对象装入上下文
        mav.addObject("defacateList", inpatientAllList.get(8));
        mav.addObject("inputList", inpatientAllList.get(9));
        mav.addObject("peeList", inpatientAllList.get(10));
        mav.addObject("weightList", inpatientAllList.get(11));
        mav.addObject("breatheList", inpatientAllList.get(12).get(0));
        // $Author:jin_peng
        // $Date:203/01/04 10:38
        // $[BUG]0012992 MODIFY BEGIN
        // 返回合并后的疼痛指标内容
        // $Author:wei_peng
        // $Date:2012/9/29 10:16
        // $[BUG]0010038 ADD BEGIN
        mav.addObject("painAssessmentMergedList", inpatientAllList.get(13));
        // $[BUG]0010038 ADD END
        // $[BUG]0012992 MODIFY END

        // 将三测单图形显示内容对象装入上下文
        mav.addObject("temperatureList",
                getXhData(inpatientAllList.get(14).get(0)));
        mav.addObject("bloodPressureLowList",
                getXhData(inpatientAllList.get(15).get(0)));
        mav.addObject("bloodPressureHighList",
                getXhData(inpatientAllList.get(16).get(0)));
        mav.addObject("pulseList", getXhData(inpatientAllList.get(17).get(0)));

        // $Author :jin_peng
        // $Date : 2012/10/10 18:28$
        // [BUG]0010239 ADD BEGIN
        mav.addObject("longTermDrugOrderList", inpatientAllList.get(18));
        // [BUG]0010239 ADD END

        // $Author:jin_peng
        // $Date:203/01/04 10:38
        // $[BUG]0012992 DELETE BEGIN
        // $Author:jin_peng
        // $Date:2012/12/28 17:01
        // $[BUG]0012992 ADD BEGIN
        // 疼痛指标（活动）
        // mav.addObject("painAssessmentActiveList", inpatientAllList.get(19));

        // $[BUG]0012992 ADD END
        // $[BUG]0012992 DELETE END

        // 将页面需要的其他参数转入上下文
        mav.addObject("patientSn", patientSn);
        mav.addObject("visitSnChange", visitSnChange);
        mav.addObject("inpatientStartDate", inpatientStartDate);
        mav.addObject("inpatientEndDate", inpatientEndDate);
        mav.addObject("dischargeDateAndVisitSn", dischargeDateAndVisitSn);
        mav.addObject("pagingContext", pagingContext);
        mav.addObject("rowNumStart", rowNumStart);
        mav.addObject("rowNumEnd", rowNumEnd);
        mav.addObject("visitRowsCnt", visitRowsCnt);
        mav.addObject("totalCount", totalCount);
        mav.addObject("visitTimesEtcList", visitTimesEtcList);
        mav.addObject("actuallyNum", actuallyNum);
        mav.addObject("dischargeDate", dischargeDate);
        mav.addObject("visitDate", visitDate);
        mav.addObject("userSn", userSn);
        // $Author:chang_xuewen
    	// $Date : 2013/12/23 14:10
    	// $[BUG]0040770 ADD BEGIN
        mav.addObject("orgCode", orgCode);
        // $[BUG]0040770 ADD END

        // $Author :jin_peng
        // $Date : 2012/12/20 16:52$
        // [BUG]0012702 ADD BEGIN
        // 根据曲线数据判断对应光标的显示形态
        mav.addObject(
                "tempcursor",
                !ObjectUtils.isNullList(inpatientAllList.get(12).get(0)) ? "pointer"
                        : "text");
        mav.addObject(
                "bloodcursor",
                !ObjectUtils.isNullList(inpatientAllList.get(13).get(0))
                    || !ObjectUtils.isNullList(inpatientAllList.get(14).get(0)) ? "pointer"
                        : "text");
        mav.addObject(
                "pulsecursor",
                !ObjectUtils.isNullList(inpatientAllList.get(15).get(0)) ? "pointer"
                        : "text");

        // [BUG]0012702 ADD END

        // [BUG]0010132 MODIFY END
        // [BUG]0011026 MODIFY END

        // $Author:wu_jianfeng
        // $Date : 2012/12/21 14:10
        // $[BUG]0012967 ADD BEGIN
        mav.addObject("viewSettings",
                userDetails.getUserSettings().getInpatientViewSettings());
        // $[BUG]0012967 ADD END

        mav.setViewName("/inpatient/inpatientView");

        return mav;
    }
    
    private List<TimerAndInpatientDto> getVisitLine(List<TimerAndInpatientDto> visitTimesEtcList){
    	List<TimerAndInpatientDto> visitLine = new ArrayList<TimerAndInpatientDto>();
    	for(int i = visitTimesEtcList.size() - 1; i >= 0; i--){
    		TimerAndInpatientDto visit = visitTimesEtcList.get(i);
    		Date visitDate = visit.getVisitDate();
    		Date dischargeDate = visit.getDischargeDate() == null ? new Date() : visit.getDischargeDate();
    		int days = DateUtils.getDaysBetween(visitDate, dischargeDate);
    		for(int j = 0; j <= days; j++){
    			TimerAndInpatientDto visitDay = new TimerAndInpatientDto();
    			visitDay.setDischargeDate(dischargeDate);
    			visitDay.setInpatientDays(j + 1 + "");
    			visitDay.setVisitDate(visitDate);
    			visitDay.setVisitSn(visit.getVisitSn());
    			visitDay.setVisitTimes(visit.getVisitTimes());
    			visitDay.setDischargeWardName(visit.getDischargeWardName());
    			visitDay.setDischargeBedNo(visit.getDischargeBedNo());
    			visitDay.setEntranceTime(visit.getEntranceTime());
    			Calendar ca = Calendar.getInstance();
    			ca.setTime(visitDate);
    			ca.add(Calendar.DATE, j);
    			Date inpatientDate = ca.getTime();
    			visitDay.setInpatientDate(inpatientDate);
    			visitLine.add(visitDay);
    		}
    	}
    	return visitLine;
    }
    
    private int[] getStartAndEndPosition(List<TimerAndInpatientDto> visitLine, String visit, 
    		String discharge, String start, String end){
    	int[] result = new int[2];
    	if(StringUtils.isEmpty(visit) && StringUtils.isEmpty(discharge) &&
    			StringUtils.isEmpty(start) && StringUtils.isEmpty(end)){
    		result[0] = 1;
    		result[1] = visitLine.size();
    		return result;
    	}
    	Date startDate, endDate;
    	if(StringUtils.isEmpty(visit)){
    		visit = DateUtils.dateToString(visitLine.get(0).getVisitDate(), DateUtils.PATTERN_MINUS_DATE);
    	}
    	if(StringUtils.isEmpty(discharge)){
    		discharge = DateUtils.dateToString(visitLine.get(visitLine.size() - 1).getDischargeDate(), DateUtils.PATTERN_MINUS_DATE);
    	}
    	String startSource = start;
    	if(StringUtils.isEmpty(start) || start.compareTo(visit) < 0){
    		start = visit;
    	}
    	if(StringUtils.isEmpty(end) || end.compareTo(discharge) > 0){
    		end = discharge;
    	}
    	String formatStyle = start.contains("/") ? DateUtils.PATTERN_SLASH_DATE : DateUtils.PATTERN_MINUS_DATE;
    	startDate = DateUtils.stringToDate(start, formatStyle);
    	endDate = DateUtils.stringToDate(end, formatStyle);
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(endDate);
    	cal.add(Calendar.DATE, 1);
    	endDate = cal.getTime();
    	int i = 0;
    	int startP = -1;
    	int endP = -1;
    	for(TimerAndInpatientDto visitL : visitLine){
    		i++;
    		if(visitL.getInpatientDate().getTime() >= startDate.getTime() 
    				&& visitL.getInpatientDate().getTime() <= endDate.getTime()){
    			endP = i;
    			if(startP == -1){
    				startP = i;
    			}
    		}
    	}
    	if(StringUtils.isEmpty(startSource)){
    		int endP1 = visitLine.size() - startP + 1;
    		int startP1 = visitLine.size() - endP + 1;
    		startP = startP1;
    		endP = endP1;
    	}
		result[0] = startP;
		result[1] = endP;
    	return result;
    }
    
	//$Author :chang_xuewen
	//$Date : 2013/12/30 14:50$
	//[BUG]0040770 ADD BEGIN
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
        // 获取住院类型编码
        String inpatientType = Constants.VISIT_TYPE_CODE_INPATIENT;
        // 住院次数集合
        List<TimerAndInpatientDto> visitTimesEtcList = null;
        
        // 住院次数查询
        visitTimesEtcList = inpatientService.selectVisitTimes(patientSn,
                inpatientType, Constants.EXIT_INPATIENT, orgCode);

        ModelAndView mav = new ModelAndView();
        mav.addObject("visitTimesEtcList", visitTimesEtcList);
        mav.addObject("orgCode", orgCode);

        mav.setViewName("/inpatient/condition");

        return mav;
    }
    //[BUG]0040770 ADD END

    /**
     * 分离获取三测单图形显示内容
     * @param beanList 装有三测单图形显示内容的对象集合
     * @return 三测单图形显示内容
     */
    private List<String> getXhData(List<TimerAndInpatientDto> beanList)
    {
        List<String> strList = new ArrayList<String>();

        if (beanList != null && !beanList.isEmpty())
        {
            for (TimerAndInpatientDto t : beanList)
            {
                // 分离出三测单图形显示内容,装入双重字符串内容避免页面js获取后自动转换成数值类型进行运算
                strList.add("\"" + t.getXhData() + "\"");
            }

            return strList;
        }

        return strList;
    }

    // $Author:jin_peng
    // $Date:2012/12/25 16:49
    // $[BUG]0012698 ADD BEGIN
    /**
     * 显示住院视图列表中具体诊断信息
     * @param request 上下文对象
     * @return mav 数据视图对象
     */
    @RequestMapping("/SpecificallyDiagnosis")
    public ModelAndView inpatientSpecificallyDiagnosis(WebRequest request)
    {
        //用户sn
        String userSn = getUserSn();
    	
        String inpatientDate = request.getParameter("inpatientDate");

        BigDecimal visitSn = StringUtils.strToBigDecimal(
                request.getParameter("visitSn"), "就诊内部序列号");

        List<TimerAndInpatientDto> specificallyDiagnosisList = inpatientService.selectSpecificallyDiagnosisList(
                visitSn, inpatientDate, userSn);

        ModelAndView mav = new ModelAndView();

        mav.addObject("specificallyDiagnosisList", specificallyDiagnosisList);
        // $Author:tong_meng
        // $Date:2013/07/17 15:00
        // $[BUG]0033455 ADD BEGIN
        mav.addObject("inpatientDate", inpatientDate);
        mav.addObject("visitSn", visitSn);
        // $[BUG]0033455 ADD END
        mav.addObject("firstDivWidth", specificallyDiagnosisList != null
            && specificallyDiagnosisList.size() > 1 ? "49%" : "99%");

        mav.setViewName("/inpatient/specificallyDiagnosis");

        return mav;
    }

    /**
     * 显示住院视图列表中具体长期药物医嘱信息
     * @param request 上下文对象
     * @return mav 数据视图对象
     */
    @RequestMapping("/specificallyLongDrug")
    public ModelAndView inpatientSpecificallyLongDrug(WebRequest request)
    {
        String inpatientDate = request.getParameter("inpatientDate");

        BigDecimal visitSn = StringUtils.strToBigDecimal(
                request.getParameter("visitSn"), "就诊内部序列号");
        //用户sn
        String userSn = getUserSn();
        List<TimerAndInpatientDto> specificallyLongDrugList = inpatientService.selectSpecificallyLongDrug(
                visitSn, inpatientDate, userSn);
        ModelAndView mav = new ModelAndView();

        mav.addObject("specificallyLongDrugList", specificallyLongDrugList);
        // $Author:tong_meng
        // $Date:2013/07/17 15:00
        // $[BUG]0033455 ADD BEGIN
        mav.addObject("inpatientDate", inpatientDate);
        mav.addObject("visitSn", visitSn);
        // $[BUG]0033455 ADD END
        mav.addObject("firstDivWidth", specificallyLongDrugList != null
            && specificallyLongDrugList.size() > 1 ? "49%" : "99%");
        mav.setViewName("/inpatient/specificallyLongDrug");
        return mav;
    }

    /**
     * 显示住院视图列表中具体临时药物医嘱信息
     * @param request 上下文对象
     * @return mav 数据视图对象
     */
    @RequestMapping("/specificallyTempDrug")
    public ModelAndView inpatientSpecificallyTempDrug(WebRequest request)
    {
        String inpatientDate = request.getParameter("inpatientDate");

        BigDecimal visitSn = StringUtils.strToBigDecimal(
                request.getParameter("visitSn"), "就诊内部序列号");
        //用户sn
        String userSn = getUserSn();
        List<TimerAndInpatientDto> specificallyTempDrugList = inpatientService.selectSpecificallyTempDrug(
                visitSn, inpatientDate, userSn);

        ModelAndView mav = new ModelAndView();

        mav.addObject("specificallyTempDrugist", specificallyTempDrugList);
        // $Author:tong_meng
        // $Date:2013/07/17 15:00
        // $[BUG]0033455 ADD BEGIN
        mav.addObject("inpatientDate", inpatientDate);
        mav.addObject("visitSn", visitSn);
        // $[BUG]0033455 ADD END
        mav.addObject("firstDivWidth", specificallyTempDrugList != null
            && specificallyTempDrugList.size() > 1 ? "49%" : "99%");
        mav.setViewName("/inpatient/specificallyTempDrug");

        return mav;
    }

    /**
     * 显示住院视图列表中具体检查信息
     * @param request 上下文对象
     * @return mav 数据视图对象
     */
    @RequestMapping("/specificallyExam")
    public ModelAndView inpatientSpecificallyExam(WebRequest request)
    {
        String inpatientDate = request.getParameter("inpatientDate");

        BigDecimal visitSn = StringUtils.strToBigDecimal(
                request.getParameter("visitSn"), "就诊内部序列号");
        //用户sn
        String userSn = getUserSn();
        List<TimerAndInpatientDto> specificallyExamList = inpatientService.selectSpecificallyExam(
                visitSn, inpatientDate, userSn);

        // $Author :tong_meng
        // $Date : 2013/1/11 13:49$
        // [BUG]0013311 ADD BEGIN
        // 将部分字段中的\x000d\替换为</br>
        for (TimerAndInpatientDto timerAndInpatientDto : specificallyExamList)
        {
            timerAndInpatientDto.setEiImagingConclusion(org.apache.commons.lang3.StringUtils.replace(
                    timerAndInpatientDto.getEiImagingConclusion(), "\\x000d\\",
                    "</br>"));
            timerAndInpatientDto.setEiImagingFinding(org.apache.commons.lang3.StringUtils.replace(
                    timerAndInpatientDto.getEiImagingFinding(), "\\x000d\\",
                    "</br>"));
            timerAndInpatientDto.setErImagingConclusion(org.apache.commons.lang3.StringUtils.replace(
                    timerAndInpatientDto.getErImagingConclusion(), "\\x000d\\",
                    "</br>"));
            timerAndInpatientDto.setErImagingFinding(org.apache.commons.lang3.StringUtils.replace(
                    timerAndInpatientDto.getErImagingFinding(), "\\x000d\\",
                    "</br>"));
        }
        // [BUG]0013311 ADD END

        ModelAndView mav = new ModelAndView();
        mav.addObject("specificallyExamList", specificallyExamList);
        // $Author:tong_meng
        // $Date:2013/07/17 15:00
        // $[BUG]0033455 ADD BEGIN
        mav.addObject("inpatientDate", inpatientDate);
        mav.addObject("visitSn", visitSn);
        // $[BUG]0033455 ADD END
        mav.addObject("firstDivWidth", specificallyExamList != null
            && specificallyExamList.size() > 1 ? "49%" : "99%");
        mav.setViewName("/inpatient/specificallyExam");

        return mav;
    }

    /**
     * 显示住院视图列表中具体检验信息
     * @param request 上下文对象
     * @return mav 数据视图对象
     */
    @RequestMapping("/specificallyLab")
    public ModelAndView inpatientSpecificallyLab(WebRequest request)
    {
        String inpatientDate = request.getParameter("inpatientDate");

        BigDecimal visitSn = StringUtils.strToBigDecimal(
                request.getParameter("visitSn"), "就诊内部序列号");
        //用户sn
        String userSn = getUserSn();
        List<TimerAndInpatientDto> specificallyLabList = inpatientService.selectSpecificallyLab(
                visitSn, inpatientDate, userSn);

        // 检验源系统集合
        List<TimerAndInpatientDto> timerAndInpatientDtoList = null;
        // 检验源系统map
        Map<TimerAndInpatientDto, List<TimerAndInpatientDto>> timerAndInpatientDtoMap = new HashMap<TimerAndInpatientDto, List<TimerAndInpatientDto>>();
        // 检验具体项目集合
        List<LabResultItemDto> labResultItems = new ArrayList<LabResultItemDto>();
        // 微生物报告集合
        List<Object> labResults = new ArrayList<Object>();
        /*
         * // 微生物检验集合 List<Map<LabResult, String>> labResultList = new
         * ArrayList<Map<LabResult,String>>();
         */

        Map<LabResult, List<LabResultItemDto>> map = null;

        for (TimerAndInpatientDto timerAndInpatientDto : specificallyLabList)
        {
            if (!StringUtils.isEmpty(StringUtils.BigDecimalToStr(timerAndInpatientDto.getCompositeItemSn())))
            {
                if (timerAndInpatientDto.getSourceSystemId().equals(
                        Constants.SOURCE_LAB))
                {
                    List<LabResultItemDto> LabResultItemDtoList = labService.selectLabResult(StringUtils.BigDecimalToStr(timerAndInpatientDto.getCompositeItemSn()));
                    timerAndInpatientDtoList = new ArrayList<TimerAndInpatientDto>();
                    for (LabResultItemDto labResultItemDto : LabResultItemDtoList)
                    {
                        TimerAndInpatientDto timerAndInpatient;
                        try
                        {
                            timerAndInpatient = (TimerAndInpatientDto) timerAndInpatientDto.clone();
                            timerAndInpatient.setItemNameCn(labResultItemDto.getItemNameCn());
                            timerAndInpatient.setResultHighLowFlagStr(StringUtils.compareItemValue(
                                    labResultItemDto.getHighValue() == null ? null
                                            : labResultItemDto.getHighValue(),
                                    labResultItemDto.getItemValue(),
                                    labResultItemDto.getLowValue() == null ? null
                                            : labResultItemDto.getLowValue()));
                            timerAndInpatient.setItemValue(labResultItemDto.getItemValue());

                            // $Author :tong_meng
                            // $Date : 2013/1/21 18:00$
                            // [BUG]0013311 MODIFY BEGIN
                            if (!StringUtils.isEmpty(labResultItemDto.getLowValue()))
                                timerAndInpatient.setLowValue(Double.parseDouble(labResultItemDto.getLowValue()));
                            else
                                timerAndInpatient.setLowValue(null);

                            if (!StringUtils.isEmpty(labResultItemDto.getHighValue()))
                                timerAndInpatient.setHighValue(Double.parseDouble(labResultItemDto.getHighValue()));
                            else
                                timerAndInpatient.setHighValue(null);

                            if (!StringUtils.isEmpty(labResultItemDto.getWarnHighValue()))
                                timerAndInpatient.setWarnHighValue(Double.parseDouble(labResultItemDto.getWarnHighValue()));
                            else
                                timerAndInpatient.setWarnHighValue(null);

                            if (!StringUtils.isEmpty(labResultItemDto.getWarnLowValue()))
                                timerAndInpatient.setWarnLowValue(Double.parseDouble(labResultItemDto.getWarnLowValue()));
                            else
                                timerAndInpatient.setWarnLowValue(null);

                            timerAndInpatient.setItemUnit(labResultItemDto.getItemUnit());
                            timerAndInpatient.setNormalRefValueText(labResultItemDto.getNormalRefValueText());
                            // [BUG]0013311 MODIFY END

                            timerAndInpatient.setQualitativeResults(labResultItemDto.getQualitativeResults());
                            timerAndInpatient.setReportMemo(labResultItemDto.getReportMemo());
                            timerAndInpatientDtoList.add(timerAndInpatient);
                        }
                        catch (CloneNotSupportedException e)
                        {
                            e.printStackTrace();
                            break;
                        }
                    }
                    timerAndInpatientDtoMap.put(timerAndInpatientDto,
                            timerAndInpatientDtoList);
                }
                else
                {
                    map = new HashMap<LabResult, List<LabResultItemDto>>();
                    labResultItems = labService.selectLabResult(StringUtils.BigDecimalToStr(timerAndInpatientDto.getCompositeItemSn()));
                    Map<String, Object> condition = new HashMap<String, Object>();
                    condition.put("labReportSn",
                            timerAndInpatientDto.getLabReportSn());
                    condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
                    labResults = commonService.selectByCondition(
                            LabResult.class, condition);
                    map.put((labResults == null || labResults.isEmpty()) ? null
                            : (LabResult) labResults.get(0), labResultItems);
                }
            }
        }
        ModelAndView mav = new ModelAndView();
        mav.addObject("specificallyLabList", specificallyLabList);
        mav.addObject("map", map);
        // $Author:tong_meng
        // $Date:2013/07/17 15:00
        // $[BUG]0033455 ADD BEGIN
        mav.addObject("inpatientDate", inpatientDate);
        mav.addObject("visitSn", visitSn);
        // $[BUG]0033455 ADD END
        mav.addObject("timerAndInpatientDtoMap", timerAndInpatientDtoMap);
        mav.addObject("firstDivWidth", specificallyLabList != null
            && specificallyLabList.size() > 1 ? "49%" : "99%");

        mav.setViewName("/inpatient/specificallyLab");

        return mav;
    }

    /**
     * 显示住院视图列表中具体手术信息
     * @param request 上下文对象
     * @return mav 数据视图对象
     */
    @RequestMapping("/specificallyProcedure")
    public ModelAndView inpatientSpecificallyProcedure(WebRequest request)
    {
        String inpatientDate = request.getParameter("inpatientDate");

        BigDecimal visitSn = StringUtils.strToBigDecimal(
                request.getParameter("visitSn"), "就诊内部序列号");
        //用户sn
        String userSn = getUserSn();
        List<TimerAndInpatientDto> specificallyProcedureList = inpatientService.selectSpecificallyProcedure(
                visitSn, inpatientDate, userSn);

        ModelAndView mav = new ModelAndView();
        mav.addObject("specificallyProcedureList", specificallyProcedureList);
        // $Author:tong_meng
        // $Date:2013/07/17 15:00
        // $[BUG]0033455 ADD BEGIN
        mav.addObject("inpatientDate", inpatientDate);
        mav.addObject("visitSn", visitSn);
        // $[BUG]0033455 ADD END
        mav.addObject("firstDivWidth", specificallyProcedureList != null
            && specificallyProcedureList.size() > 1 ? "49%" : "99%");
        mav.setViewName("/inpatient/specificallyProcedure");

        return mav;
    }
    
    // $Author :chang_xuewen
    // $Date : 2013/10/24 11:00$
    // [BUG]0038443 ADD BEGIN
    /**
     * 显示住院视图列表中具体手术相关病历文书信息
     * @param request 上下文对象
     * @return mav 数据视图对象
     */
    @RequestMapping("/specificallyProceduredoc")
    public ModelAndView inpatientSpecificallyProceduredoc(WebRequest request)
    {
        String inpatientDate = request.getParameter("inpatientDate");

        BigDecimal visitSn = StringUtils.strToBigDecimal(
                request.getParameter("visitSn"), "就诊内部序列号");
        //用户sn
        String userSn = getUserSn();
        List<TimerAndInpatientDto> specificallyProceduredocList = inpatientService.selectSpecificallyProceduredoc(
                visitSn, inpatientDate, userSn);

        ModelAndView mav = new ModelAndView();
        mav.addObject("specificallyProceduredocList", specificallyProceduredocList);
        // $Author:tong_meng
        // $Date:2013/07/17 15:00
        // $[BUG]0033455 ADD BEGIN
        mav.addObject("inpatientDate", inpatientDate);
        mav.addObject("visitSn", visitSn);
        // $[BUG]0033455 ADD END
        mav.addObject("firstDivWidth", specificallyProceduredocList != null
            && specificallyProceduredocList.size() > 1 ? "49%" : "99%");
        mav.setViewName("/inpatient/specificallyProceduredoc");

        return mav;
    }
    // [BUG]0038443 ADD END
    
    /**
     * 显示住院视图列表中具体病例文书信息
     * @param request 上下文对象
     * @return mav 数据视图对象
     */
    @RequestMapping("/specificallyDocument")
    public ModelAndView inpatientSpecificallyDocument(WebRequest request)
    {
        String inpatientDate = request.getParameter("inpatientDate");

        BigDecimal visitSn = StringUtils.strToBigDecimal(
                request.getParameter("visitSn"), "就诊内部序列号");
        //用户sn
        String userSn = getUserSn();
        List<TimerAndInpatientDto> specificallyDocumentList = inpatientService.selectSpecificallyDocument(
                visitSn, inpatientDate, userSn);

        ModelAndView mav = new ModelAndView();
        // $Author:tong_meng
        // $Date:2013/07/17 15:00
        // $[BUG]0033455 ADD BEGIN
        mav.addObject("inpatientDate", inpatientDate);
        mav.addObject("visitSn", visitSn);
        // $[BUG]0033455 ADD END
        mav.addObject("specificallyDocumentList", specificallyDocumentList);
        mav.addObject("firstDivWidth", specificallyDocumentList != null
            && specificallyDocumentList.size() > 1 ? "49%" : "99%");
        mav.setViewName("/inpatient/specificallyDocument");

        return mav;
    }

    /**
     * 显示住院视图列表中具体非药物医嘱信息
     * @param request 上下文对象
     * @return mav 数据视图对象
     */
    @RequestMapping("/specificallyNoneDrug")
    public ModelAndView inpatientSpecificallyNoneDrug(WebRequest request)
    {
        String inpatientDate = request.getParameter("inpatientDate");

        BigDecimal visitSn = StringUtils.strToBigDecimal(
                request.getParameter("visitSn"), "就诊内部序列号");
        //用户sn
        String userSn = getUserSn();
        List<TimerAndInpatientDto> specificallyNoneDrugList = inpatientService.selectSpecificallyNoneDrug(
                visitSn, inpatientDate, userSn);

        ModelAndView mav = new ModelAndView();
        mav.addObject("specificallyNoneDrugList", specificallyNoneDrugList);
        // $Author:tong_meng
        // $Date:2013/07/17 15:00
        // $[BUG]0033455 ADD BEGIN
        mav.addObject("inpatientDate", inpatientDate);
        mav.addObject("visitSn", visitSn);
        // $[BUG]0033455 ADD END
        mav.addObject("firstDivWidth", specificallyNoneDrugList != null
            && specificallyNoneDrugList.size() > 1 ? "49%" : "99%");

        mav.setViewName("/inpatient/specificallyNoneDrug");

        return mav;
    }
    // $[BUG]0012698 ADD END
    
    /**
     * 返回用户sn
     * @author chang_xuewen
     * @return
     */
    private String getUserSn(){
    	//获取用户sn
    	LoginUserDetails userDetails = (LoginUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	String userSn = userDetails.getUsername();
    	return userSn;
    }

}
