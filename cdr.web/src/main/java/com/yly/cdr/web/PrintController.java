/**
 * Copyright (c) 2012—2012 Founder International Co.Ltd. All rights reserved.
 */
package com.yly.cdr.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
import com.yly.cdr.dto.PatientCrossIndexDto;
import com.yly.cdr.dto.PatientDto;
import com.yly.cdr.dto.PatientFavDto;
import com.yly.cdr.dto.PatientFavFolderDto;
import com.yly.cdr.dto.TimerAndInpatientDto;
import com.yly.cdr.dto.VisitDateDeptDto;
import com.yly.cdr.dto.VisitDateDto;
import com.yly.cdr.dto.patient.PatientListAdvanceSearchPra;
import com.yly.cdr.dto.patient.PatientListSearchPra;
import com.yly.cdr.dto.patient.RecentPatientListSearchPra;
import com.yly.cdr.dto.patientFav.PatientFavListSearchPra;
import com.yly.cdr.dto.visit.LastVisitSearchPra;
import com.yly.cdr.entity.MedicalVisit;
import com.yly.cdr.entity.PatientFav;
import com.yly.cdr.entity.PatientFavFolder;
import com.yly.cdr.service.PatientFavFolderService;
import com.yly.cdr.service.PatientService;
import com.yly.cdr.service.PrintService;
import com.yly.cdr.service.VisitService;
import com.yly.cdr.util.DateUtils;
import com.yly.cdr.util.StringUtils;
import com.yly.cdr.web.loginValidation.AccessControl;
import com.yly.cdr.web.loginValidation.LoginUserDetails;
import com.yly.cdr.web.util.PagingHelper;

/**
 * [FUN]打印
 * 
 * @version 1.0, 2014/04/09  10:27:00
 * @author li_shenggen
 * @since 1.0
*/
@Controller
@RequestMapping("/print")
public class PrintController
{
    private static Logger logger = LoggerFactory.getLogger(PrintController.class);

    private static final String IS_PORTION = "1";

    @Autowired
    private PatientFavFolderService patientFavFolderService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private VisitService visitService;
    @Autowired
    private PrintService printService;
    
    /**
     * V04-001: 患者详细信息
     * 患者人口学等基本信息显示
     * 
     * @param patientSn
     * @return
     * @throws Exception
     */
    @RequestMapping("/{sn}")
    public ModelAndView detail(WebRequest request,@PathVariable("sn") String patientSn)
            throws Exception
    {
        LoginUserDetails userDetails = (LoginUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userSn = userDetails.getUsername();
        String visitDateSn = request.getParameter("visitDateSn");
        String visitType = "01";
        //visitType = request.getParameter("visitType");
        String dischargeDateAndVisitSn = request.getParameter("dischargeDateAndVisitSn");
        // 获取页面住院开始时间
        String inpatientStartDate = request.getParameter("startDate");

        // 获取页面住院结束时间
        String inpatientEndDate = request.getParameter("endDate");
        String orgCode = request.getParameter("orgCode");
        if (Constants.OPTION_SELECT.equals(orgCode))
        {
            orgCode = null;
        }
        String sysDate = DateUtils.dateToString(DateUtils.getSystemTime(),
                "yyyy-MM-dd");
        // 患者的就诊日期集合
        List<VisitDateDeptDto> visitDateList = printService.selectPrintVisitDate(sysDate,patientSn, Constants.VISIT_TYPE_CODE_INPATIENT);

        // 获取住院类型编码
        String inpatientType = Constants.VISIT_TYPE_CODE_INPATIENT;
        // 住院次数集合
        List<TimerAndInpatientDto> visitTimesEtcList = null;
        // 最近一次住院就诊内部序列号
        String vstSn = null;        
        // 住院次数查询
        visitTimesEtcList = printService.selectVisitTimes(patientSn,
                inpatientType, Constants.EXIT_INPATIENT, orgCode);
        if(visitTimesEtcList.size() > 0 && visitTimesEtcList != null){
        	vstSn = visitTimesEtcList.get(0).getVisitSn().toString();
        }
        ModelAndView mav = new ModelAndView();
        mav.addObject("visitDateList", visitDateList);
        mav.addObject("visitTimesEtcList", visitTimesEtcList); 
        mav.addObject("vstSn", vstSn);
        mav.addObject("userSn", userSn);
        mav.addObject("patientSn", patientSn);
        mav.addObject("visitType", visitType);
        mav.addObject("visitDateSn", visitDateSn);
        mav.addObject("dischargeDateAndVisitSn", dischargeDateAndVisitSn);
        mav.addObject("inpatientStartDate", inpatientStartDate);
        mav.addObject("inpatientEndDate", inpatientEndDate);
        logger.debug("获取患者{}的信息。", patientSn);

/*        // $Author:tong_meng
        // $Date : 2012/09/19 13:51
        // $[BUG]0009814 ADD BEGIN
        // 获取登陆用户名
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUserDetails userDetails = (LoginUserDetails) authentication.getPrincipal();
        AccessControlDto aclAuths = null;
        if (AccessControl.useACL())
        {
            AccessControl acl = userDetails.getAccessControl();
            aclAuths = acl.getAccessControlAuths();
        }
        mav.addObject("loginUserAclAuths", aclAuths);
        mav.addObject("accessContorl", AccessControl.useACL());
        // $[BUG]0009814 ADD END

        mav.addObject("patient", patientService.getPatient(patientSn));
        mav.addObject("patientAddresses",
                patientService.selectPatientAddresses(patientSn));
        mav.addObject("patientContacts",
                patientService.selectPatientContacts(patientSn));
        mav.addObject("patientCredentials",
                patientService.selectPatientCredentials(patientSn));
        mav.addObject("socialInsurance",
                patientService.getSocialInsurance(patientSn));
        mav.addObject("riskInformations",
                patientService.selectRiskInformations(patientSn));
        mav.setViewName("/print/printCondition");*/
        mav.setViewName("/print/printCondition");
        return mav;
    }

}
