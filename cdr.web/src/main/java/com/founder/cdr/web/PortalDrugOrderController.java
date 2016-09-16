package com.founder.cdr.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.founder.cdr.dto.ChartDto;
import com.founder.cdr.dto.DrugOrderDto;
import com.founder.cdr.dto.MedicationOrderDto;
import com.founder.cdr.dto.NonDrugOrderDto;
import com.founder.cdr.dto.VisitDateDto;
import com.founder.cdr.dto.drug.DispensingRecordDto;
import com.founder.cdr.dto.drug.DrugListSearchPra;
import com.founder.cdr.dto.order.NonDrugOrderListSearchPra;
import com.founder.cdr.entity.CareOrder;
import com.founder.cdr.entity.ConsultationOrder;
import com.founder.cdr.entity.Diagnosis;
import com.founder.cdr.entity.GeneralOrder;
import com.founder.cdr.entity.HerbalFormula;
import com.founder.cdr.entity.NursingOperation;
import com.founder.cdr.entity.Patient;
import com.founder.cdr.entity.PatientCrossIndex;
import com.founder.cdr.entity.Prescription;
import com.founder.cdr.entity.ProcedureOrder;
import com.founder.cdr.entity.TreatmentOrder;
import com.founder.cdr.service.DiagnosisService;
import com.founder.cdr.service.DrugListService;
import com.founder.cdr.service.NonDrugOrderService;
import com.founder.cdr.service.PatientService;
import com.founder.cdr.util.DateUtils;
import com.founder.cdr.util.StringUtils;
import com.founder.cdr.web.loginValidation.LoginUserDetails;
import com.founder.cdr.web.util.MutexesOrderHelper;
import com.founder.cdr.web.util.PagingHelper;
import com.founder.fasf.web.paging.PagingContext;
import com.founder.fasf.web.paging.PagingContextHolder;

/**
 * 用于药物医嘱的视图控制类
 * @author wang_yanbo 2014/7/11
 *
 */
@Controller
@RequestMapping("/portalOrder")
public class PortalDrugOrderController {
    private static Logger logger = LoggerFactory.getLogger(PortalDrugOrderController.class);

    @Autowired
    private DrugListService DrugListService;

    @Autowired
    private DiagnosisService diagnosisService;
    
    @Autowired
    private MutexesOrderHelper mutexesOrderHelper;
    
    @Autowired
    private PatientService patientService;
    
    @Autowired
    CommonController controller;

    /**
     * V05-301:药物医嘱列表
     * 药物医嘱列表信息画面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/duty_{patientSn}")
    public ModelAndView list(WebRequest request,
            @PathVariable("patientSn") String patientSn, HttpSession session)
    {
        // 初始化pagingContext
        PagingContext pagingContext = PagingContextHolder.getPagingContext();
        //获取患者信息
  		Patient patient = patientService.getPatient(patientSn);
  		String age =DateUtils.caluAge(patient.getBirthDate(),new Date());
      	// 根据域ID，患者Lid从患者交叉索引查询患者相关信息
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("patientDomain", request.getParameter("patientDomain"));
        condition.put("patientLid", request.getParameter("patientId"));
        PatientCrossIndex patientCrossIndex = patientService.getPatientCrossIndexByCondition(condition);
        // 定义页面检索元素DTO
        DrugListSearchPra drugListSearchPra = new DrugListSearchPra();
      //就诊次数
        drugListSearchPra.setVisitTimes(request.getParameter("visitTimes"));
        // 如果是时间轴视图或住院视图运行到此则传入已定义好的参数dto
        if (request.getAttribute("commonParameters", WebRequest.SCOPE_REQUEST) != null)
        {
            drugListSearchPra = (DrugListSearchPra) request.getAttribute(
                    "commonParameters", WebRequest.SCOPE_REQUEST);
        }

        // 是否调用SESSION
        String senSave = request.getParameter("senSave");
        if ("true".equals(senSave))
        {
            drugListSearchPra = (DrugListSearchPra) session.getAttribute("DrugListSearchPra");
        }
        else
        {
            // //ToDo直接将MAP数据转成DTO
            // Map<String, String[]> parameterMap=request.getParameterMap();
            // if(!parameterMap.isEmpty())
            // {
            // PagingHelper.parameterValueGet(parameterMap,"approvedDrugName");
            //
            // }
            // 药物通用名
            String approvedDrugName = request.getParameter("approvedDrugName");

            // $Author :tong_meng
            // $Date : 2012/9/26 16:45$
            // [BUG]0010038 MODIFY BEGIN
            // 药物类型
            String medicineType = request.getParameter("medicineType");
            // [BUG]0010038 MODIFY END

            // 开医嘱人
            String orderPersonName = request.getParameter("orderPersonName");
            // 开嘱日期
            String orderTimeFrom = request.getParameter("orderTimeFrom");
            String orderTimeTo = request.getParameter("orderTimeTo");
            // 停嘱时间
            String stopTimeFrom = request.getParameter("stopTimeFrom");
            String stopTimeTo = request.getParameter("stopTimeTo");
            // 科室
            String orderDept = request.getParameter("orderDept");

            // $Author :jin_peng
            // $Date : 2012/11/22 13:34$
            // [BUG]0011026 MODIFY BEGIN
            // $Author :tong_meng
            // $Date : 2012/9/26 16:45$
            // [BUG]0010038 ADD BEGIN
            // 就诊内部序列号
            String visitDateSn = request.getParameter("visitDateSn");
            // 患者的就诊日期集合
            List<VisitDateDto> visitDateList = DrugListService.selectVisitDate(patientSn);
            // 就诊类型
            String visitType = request.getParameter("visitType");
            // 诊断类型
            //String diagnoseType = request.getParameter("diagnoseType");
            // 疾病名称
            String diseaseName = request.getParameter("diseaseName");

            // $Author :tong_meng
            // $Date : 2012/10/23 10:00$
            // [BUG]0009621 ADD BEGIN
            // 长期临时
            String temporaryFlag = request.getParameter("temporaryFlag");
            // [BUG]0009621 ADD END
            // [BUG]0010038 ADD END
            // 医疗机构
            String orgCode = request.getParameter("orgCodeFlag"); 
            drugListSearchPra.setApprovedDrugName(approvedDrugName);
            // 下拉框中选择的value值为-1时按照null处理
            drugListSearchPra.setOrderDept(Constants.OPTION_SELECT.equalsIgnoreCase(orderDept)?null:orderDept);
            drugListSearchPra.setMedicineType(Constants.OPTION_SELECT.equalsIgnoreCase(medicineType)?null:medicineType);
            drugListSearchPra.setOrderTimeFrom(orderTimeFrom);
            drugListSearchPra.setOrderTimeTo(orderTimeTo);
            drugListSearchPra.setStopTimeFrom(stopTimeFrom);
            drugListSearchPra.setStopTimeTo(stopTimeTo);
            drugListSearchPra.setOrderPersonName(orderPersonName);
            drugListSearchPra.setVisitType(Constants.OPTION_SELECT.equalsIgnoreCase(visitType)?null:visitType);
            drugListSearchPra.setDiseaseName(Constants.OPTION_SELECT.equals(diseaseName)?null:diseaseName);
            drugListSearchPra.setVisitDateSn(Constants.OPTION_SELECT.equalsIgnoreCase(visitDateSn)?null:visitDateSn);
            drugListSearchPra.setVisitDateList(visitDateList);
            drugListSearchPra.setTemporaryFlag(Constants.OPTION_SELECT.equalsIgnoreCase(temporaryFlag)?null:temporaryFlag);
            drugListSearchPra.setOrgCode(Constants.OPTION_SELECT.equalsIgnoreCase(orgCode)?null:orgCode);
            session.setAttribute("DrugListSearchPra", drugListSearchPra);

        }

        // [BUG]0011026 MODIFY END

        // 设置页面初始每页显示条目数,pagingContext默认值是10，(其他参数也可以修正，会影响检索结果)可根据需要修正。
        // Author:jia_yanqing
        // Date : 2013/1/11 15:30
        // [BUG]0012699 ADD BEGIN
        LoginUserDetails userDetails = (LoginUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String drugRowsCntStr = userDetails.getUserSettings().getRowsPerPage();
        //获取用户sn
        String userSn = userDetails.getUsername();
        // [BUG]0012699 ADD END
        pagingContext.setRowCnt(Integer.parseInt(drugRowsCntStr));
        // 调用业务层逻辑查询分页控制时对象集合，pagingContext返回值中会覆写totalRowCnt totalPageCnt
        MedicationOrderDto[] list_paging = null;
        
        ModelAndView mav = new ModelAndView();
        // Author: tong_meng
        // Date : 2013/6/4 15:30
        // [BUG]0033455 ADD BEGIN 
        String gotoType = request.getParameter("gotoType");
        String orderSn = request.getParameter("orderSn");
        String special = request.getParameter("special");
        if("part".equals(gotoType))
        {
            int showLeftRowCnt = Integer.parseInt(Constants.getDETAIL_TABS_COUNT());
            pagingContext.setRowCnt(showLeftRowCnt);
            list_paging = DrugListService.selectDrugList(patientSn, drugListSearchPra, userSn, pagingContext);
            mav.addObject("pageNo",pagingContext.getPageNo());
            mav.setViewName("/drug/drugDetailViewMenuPartChart");
        }
        else if("drug".equals(gotoType) ||"drug_timer".equals(gotoType)) 
        {
            int showLeftRowCnt = Integer.parseInt(Constants.getDETAIL_TABS_COUNT());
            pagingContext.setRowCnt(showLeftRowCnt);
            boolean overflowFlag = true;
            
            if(StringUtils.isEmpty(special))
                list_paging = DrugListService.selectDrugList(patientSn, drugListSearchPra, userSn, pagingContext);
            else
                list_paging = DrugListService.selectDrugList(patientSn, drugListSearchPra, userSn, null);

            for (int count = 0; count < showLeftRowCnt - list_paging.length; )
            {
                list_paging = Arrays.copyOf(list_paging, list_paging.length + 1);
                list_paging[list_paging.length - 1] = new MedicationOrderDto();
                overflowFlag = false;
            }
            
            if(overflowFlag)
                mav.addObject("overflow", "auto");
            else
                mav.addObject("overflow", "hidden");
            mav.addObject("drugListSearchPra", drugListSearchPra);
            mav.addObject("orderSn", orderSn);
            mav.setViewName("/drug/drugDetailViewMenuPart");
        }
        else
        {
            list_paging = DrugListService.selectDrugList(patientSn, drugListSearchPra, userSn, pagingContext);
            // 迁移到指定页面
            mav.setViewName("/portal/portalOrder/portalDrugOrderList");
        }
        // 设置每行显示查询对象
        mav.addObject("drugList", list_paging);
        // [BUG]0033455 ADD END
        
        
        // logger.debug("检索条件：药物通用名={}, 开嘱日期={}, 药物类型={},停嘱时间={},开医嘱人={},科室={}",
        // new Object[] {
        // drugListSearchPra.getApprovedDrugName(),
        // drugListSearchPra.getOrderTimeFrom() + "-"
        // + drugListSearchPra.getOrderTimeTo(),
        // drugListSearchPra.getMedicineTypeName(),
        // drugListSearchPra.getStopTimeFrom() + "-"
        // + drugListSearchPra.getStopTimeTo(),
        // drugListSearchPra.getOrderPersonName(),
        // drugListSearchPra.getOrderDept() });
        // 检索对象为空check
        if (list_paging == null || list_paging.length == 0)
        {
            logger.debug("没有检索对象!");
            // 页面显示页面数赋值0
            pagingContext.setPageNo(0);
        }
 
        // 页面初始化分页页码显示列表
        PagingHelper.initPaging(pagingContext);

        // 加载pagingContext对象
        pagingContext.setRows(list_paging);
        mav.addObject("pagingContext", pagingContext);
        logger.debug("：当前总页数={}, 当前总条数={},当前页数={}", new Object[] {
                pagingContext.getTotalPageCnt(),
                pagingContext.getTotalRowCnt(), pagingContext.getPageNo() });
        // 加载页面检索项目
        mav.addObject("drugListSearchPra", drugListSearchPra);
        mav.addObject("patientSn", patientSn);
        // 动态加载检索框
        String conditionValue = request.getParameter("conditionValue");
        mav.addObject("conditionValue", conditionValue==null?"off":conditionValue);
        // 根据不同界面显示不同detail的连接
        mav.addObject("gotoType", gotoType);
        // 标识符清空
        mav.addObject("senSave", null);
        mav.addObject("patient", patient);
 		mav.addObject("age", age);
        mav.addObject("patientCrossIndex", patientCrossIndex);
        return mav;
    }

    /**
     * V05-304:住院药物医嘱详细
     * 住院药物医嘱详细画面
     * 
     * @param orderSn
     * @return
     * @throws Exception
     */
    @RequestMapping("/detail_{orderSn}")
    public ModelAndView detail(@PathVariable("orderSn") String orderSn,
            WebRequest request) throws Exception
    {
    	// 获取用户sn信息
        LoginUserDetails userDetails = (LoginUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userSn = userDetails.getUsername();

        // 根据点击事件携带的参数取值DTO对象
        // 详细标志
        String  flag = request.getParameter("flag");
        // 用药医嘱
        MedicationOrderDto dro = this.DrugListService.selectDrugList(orderSn, userSn);
        // 处方信息
        Prescription pre = this.DrugListService.selectPrescriptionOb(orderSn, userSn);
        // 发药记录  排除hide_drug表信息
        List<DispensingRecordDto> List_dis = this.DrugListService.selectDispensingList(orderSn, userSn);
        // 草药配方
        List<HerbalFormula> hf = this.DrugListService.selectHerbal(orderSn);
        // 执行记录
        DrugOrderDto[] list_ad = DrugListService.selectRecord(orderSn);
        // 关联诊断记录
        List<Diagnosis> List_dia = diagnosisService.selectDiagnosisByVisitSn(dro.getVisitSn().toString());
        // 诊断记录拼接
        String diagnosis = "";
        for (Diagnosis dia : List_dia)
        {
            diagnosis += dia.getDiseaseName() + "、";
        }

        ModelAndView mav = new ModelAndView();
        // Author: wu_jianfeng
        // Date : 2013/3/12 15:30
        // [BUG]0014531 ADD BEGIN        
        BigDecimal mutexesOrderSn = dro.getMutexesOrderSn();        
        String mutexesOrderNoType = dro.getMutexesOrderNoType();   
        mutexesOrderHelper.setMutexesOrder(mav, mutexesOrderSn, mutexesOrderNoType);

        BigDecimal parentOrderSn = dro.getParentOrderSn();
        MedicationOrderDto parentDrugOrder = new MedicationOrderDto(); 
        if(parentOrderSn != null)
        {
            parentDrugOrder = this.DrugListService.selectDrugList(parentOrderSn.toString(),userSn);
        }   
        mav.addObject("parentDrugOrder", parentDrugOrder);
        // [BUG]0014531 ADD END
            
        // 页面元素的加载
        mav.addObject("drugOrder", dro);
        mav.addObject("Prescription", pre);
        mav.addObject("Dispensing", List_dis);
        mav.addObject("list_ad", list_ad);
        mav.addObject("hf", hf);
        mav.addObject("diagnosis", diagnosis.length() != 0 ?diagnosis.substring(0, diagnosis.length() - 1):diagnosis);
        mav.addObject("flag",flag);
        
        //added by yang_jianbo@2014-4-3  为住院和门诊加入医惠的闭环链接
        //住院
        Map<Object,Object> map = new HashMap<Object,Object>();
        // 医嘱闭环外部链接显示开关
        if(Constants.COMM_INTERFACE.equals(Constants.ORDER_CLOSED_LOOP)){
	        if(Constants.lstVisitTypeInPatient().contains(dro.getVisitTypeCode())
	    			&& Constants.lstDomainInPatient().contains(dro.getPatientDomain())){
	        	 Map<String,String> zyMap = new HashMap<String,String>();
	             zyMap.put("orderType", dro.getOrderType());
	             zyMap.put("orderCode", null);
	             zyMap.put("orderLid", dro.getOrderLid());
	             
	             map = controller.showClosedCycle(dro.getPatientDomain(), zyMap,null,mav,dro.getVisitTypeCode());
	        }
	        //门诊做医嘱闭环链接，仅限于急诊(就诊类别visitTypeCode=02)、药物医嘱，用药途径在properties的列举范围内
	        else if(Constants.lstVisitTypeOutPatient().contains(dro.getVisitTypeCode())
	    			&& Constants.lstDomainOutPatient().contains(dro.getPatientDomain())
	    			&& Constants.VISIT_TYPE_CODE_EMERGENCY.equals(dro.getVisitTypeCode())
	        		){
	        	 Map<String,String> mzMap = new HashMap<String,String>();
	        	 mzMap.put("visitTypeCode", dro.getVisitTypeCode());
	        	 mzMap.put("routeCode", dro.getRouteCode());
	        	 mzMap.put("type", "2");
	        	 mzMap.put("uniqueCode", dro.getOrderLid());   //处方号不传，传医嘱号
	        	 map = controller.showClosedCycle(dro.getPatientDomain(), null,mzMap,mav,dro.getVisitTypeCode());
	        }
        }
        if(map!=null){
        	mav.addObject("showClosedCycle", map.get("showClosedCycle"));
        	mav.addObject("linkUrl", map.get("linkUrl"));
        }
        
        mav.setViewName("/drug/drugOrderDetail");
        return mav;
    }

    @RequestMapping("/pieChart_{patientSn}")
    public ModelAndView pieChart(@PathVariable("patientSn") String patientSn,
            WebRequest request) throws Exception
    {
    	// 获取用户sn信息
        LoginUserDetails userDetails = (LoginUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userSn = userDetails.getUsername();
        
        int displayPieTotal = DrugListService.selectDrugChartTotal(patientSn,userSn);

        List<String> displayPieContent = ChartDto.getPieDisplayContent(DrugListService.selectDrugChartPie(patientSn,userSn));

        // 页面元素的加载
        ModelAndView mav = new ModelAndView();

        mav.addObject("displayPieTotal", displayPieTotal);

        mav.addObject("displayPieContent", displayPieContent);

        mav.setViewName("/drug/drugOrderPieChart");

        return mav;
    }

    @RequestMapping("/trendChart_{patientSn}")
    public ModelAndView trendChart(@PathVariable("patientSn") String patientSn,
            WebRequest request) throws Exception
    {
    	// 获取用户sn信息
        LoginUserDetails userDetails = (LoginUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userSn = userDetails.getUsername();
        
        // 初始化pagingContext
        PagingContext pagingContext = PagingContextHolder.getPagingContext();

        String approvedDrugName = request.getParameter("approvedDrugName");

        List<ChartDto> displayTrendContent = DrugListService.selectDrugChartTrend(
                patientSn, approvedDrugName, userSn, pagingContext);

        List<String> displayTrendList = new ArrayList<String>();

        if (displayTrendContent != null && !displayTrendContent.isEmpty())
        {
            for (ChartDto c : displayTrendContent)
            {
                // 分离出趋势图显示内容
                displayTrendList.add("\"" + c.getOrderTime() + ";"
                    + c.getTotalDosage() + ";" + c.getDosage() + ";"
                    + c.getTotalDosageUnit() + ";" + c.getDosageUnit() + "\"");
            }
        }

        // 页面元素的加载
        ModelAndView mav = new ModelAndView();

        mav.addObject("displayTrendList", displayTrendList);

        mav.addObject("approvedDrugName", approvedDrugName);

        mav.addObject("patientSn", patientSn);

        mav.addObject("pagingContext", pagingContext);

        mav.setViewName("/drug/drugOrderTrendChart");

        return mav;
    }
    
}
