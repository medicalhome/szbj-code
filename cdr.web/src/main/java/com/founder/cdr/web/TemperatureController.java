package com.founder.cdr.web;

import java.math.BigDecimal;
import java.util.ArrayList;
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

import com.founder.cdr.core.AppSettings;
import com.founder.cdr.core.Constants;
import com.founder.cdr.dto.AccessControlDto;
import com.founder.cdr.dto.TimerAndInpatientDto;
import com.founder.cdr.dto.lab.LabResultItemDto;
import com.founder.cdr.entity.LabResult;
import com.founder.cdr.entity.Patient;
import com.founder.cdr.entity.PatientCrossIndex;
import com.founder.cdr.service.CommonService;
import com.founder.cdr.service.InpatientService;
import com.founder.cdr.service.LabService;
import com.founder.cdr.service.PatientService;
import com.founder.cdr.util.DateUtils;
import com.founder.cdr.util.ObjectUtils;
import com.founder.cdr.util.StringUtils;
import com.founder.cdr.web.loginValidation.AccessControl;
import com.founder.cdr.web.loginValidation.LoginUserDetails;
import com.founder.fasf.web.paging.PagingContext;
import com.founder.fasf.web.paging.PagingContextHolder;
/**
 * 用于EMR3.0体温单
 * @author wang_yanbo
 * @version 1.0, 2014/08/20
 *
 */
@Controller
@RequestMapping("/temp")
public class TemperatureController {


    @Autowired
    private LabService labService;

    @Autowired
    private InpatientService inpatientService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private PatientService patientService;
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
    	//获取患者信息
    	Patient patient = patientService.getPatient(patientSn);
    	String age = DateUtils.caluAge(patient.getBirthDate(),new Date());
    	// 根据域ID，患者Lid从患者交叉索引查询患者相关信息
    	String patientDomain = request.getParameter("patientDomain");
    	String patientId = request.getParameter("patientId");
    	Map<String, Object> condition = new HashMap<String, Object>();
    	condition.put("patientDomain", patientDomain);
    	condition.put("patientLid", patientId);
    	PatientCrossIndex patientCrossIndex = patientService.getPatientCrossIndexByCondition(condition);
        // 获取共同分页对象
        PagingContext pagingContext = PagingContextHolder.getPagingContext();
        // logger.debug("显示第{}页的数据。", pagingContext.getPageNo());

        // 设置每页显示条数
        String visitRowsCntStr = AppSettings.getConfig(Constants.CFG_TIMER_INPATIENT_ROWS_COUNT);
        int visitRowsCnt = Integer.parseInt(visitRowsCntStr);
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

            // 就诊内部序列号
            visitSn = disDateAndVisitSn[1];

            // 住院日期
            visitDate = disDateAndVisitSn[2];
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

        // 当点击查询按钮时按查询条件查询记录开始序号
        if (StringUtils.isEmpty(pagingKindFlag))
        {
            actuallyNum = inpatientService.selectStartNumber(patientSn,
                    inpatientType, inpatientStartDate, inpatientEndDate,
                    visitSn, Constants.EXIT_INPATIENT, orgCode);

            pageNo = actuallyNum;
        }

        // 住院次数查询
        visitTimesEtcList = inpatientService.selectVisitTimes(patientSn,
                inpatientType, Constants.EXIT_INPATIENT, orgCode);

        // 查询住院记录总数
        totalCount = inpatientService.selectTotalCnt(patientSn, inpatientType,
                inpatientStartDate, inpatientEndDate, dischargeDate,
                Constants.EXIT_INPATIENT, orgCode);     
        

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
                AccessControl.useACL(), pagingKindFlag, visitRowsCnt, userSn, orgCode, null);

        ModelAndView mav = new ModelAndView();

        // 权限控制类 
        mav.addObject("accessControl", accessControlDto);
        // 权限控制开关
        mav.addObject("useACLFlag", AccessControl.useACL());
        // 将病区去掉在界面上显示，其他不变
        StringUtils.cutString(inpatientAllList.get(0).get(0), "dischargeWardName", "病区", "");
        mav.addObject("patientDomain", patientDomain);
     	mav.addObject("patientId", patientId);
        // 患者信息
     	mav.addObject("patient", patient);
     	mav.addObject("age", age);
     	mav.addObject("patientCrossIndex", patientCrossIndex);
        // 将住院视图页面所需所有信息对象装入上下文
        mav.addObject("inpatientList", inpatientAllList.get(0).get(0));
        mav.addObject("diagnosisList", inpatientAllList.get(1));
        mav.addObject("drugOrderList", inpatientAllList.get(2));
        mav.addObject("examinationList", inpatientAllList.get(3));
        mav.addObject("labList", inpatientAllList.get(4));
        mav.addObject("procedureList", inpatientAllList.get(5));
        mav.addObject("proceduredocList", inpatientAllList.get(19));
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
        mav.addObject("painAssessmentMergedList", inpatientAllList.get(13));
        // 将三测单图形显示内容对象装入上下文
        mav.addObject("temperatureList",
                getXhData(inpatientAllList.get(14).get(0)));
        mav.addObject("bloodPressureLowList",
                getXhData(inpatientAllList.get(15).get(0)));
        mav.addObject("bloodPressureHighList",
                getXhData(inpatientAllList.get(16).get(0)));
        mav.addObject("pulseList", getXhData(inpatientAllList.get(17).get(0)));
        mav.addObject("longTermDrugOrderList", inpatientAllList.get(18));

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

        mav.setViewName("portal/temp/temperature");

        return mav;
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
