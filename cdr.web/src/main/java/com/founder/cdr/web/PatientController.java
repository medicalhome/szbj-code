/**
 * Copyright (c) 2012—2012 Founder International Co.Ltd. All rights reserved.
 */
package com.founder.cdr.web;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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

import com.founder.cdr.core.AppSettings;
import com.founder.cdr.core.Constants;
import com.founder.cdr.dto.AccessControlDto;
import com.founder.cdr.dto.PatientAppointDto;
import com.founder.cdr.dto.PatientCrossIndexDto;
import com.founder.cdr.dto.PatientDto;
import com.founder.cdr.dto.PatientFavDto;
import com.founder.cdr.dto.PatientFavFolderDto;
import com.founder.cdr.dto.patient.PatientListAdvanceSearchPra;
import com.founder.cdr.dto.patient.PatientListSearchPra;
import com.founder.cdr.dto.patient.RecentPatientListSearchPra;
import com.founder.cdr.dto.patientFav.PatientFavListSearchPra;
import com.founder.cdr.dto.visit.LastVisitSearchPra;
import com.founder.cdr.entity.MedicalVisit;
import com.founder.cdr.entity.PatientFav;
import com.founder.cdr.entity.PatientFavFolder;
import com.founder.cdr.service.PatientFavFolderService;
import com.founder.cdr.service.PatientService;
import com.founder.cdr.service.VisitService;
import com.founder.cdr.util.DateUtils;
import com.founder.cdr.util.StringUtils;
import com.founder.cdr.web.loginValidation.AccessControl;
import com.founder.cdr.web.loginValidation.LoginUserDetails;
import com.founder.cdr.web.util.PagingHelper;
import com.founder.fasf.web.paging.PagingContext;
import com.founder.fasf.web.paging.PagingContextHolder;
import com.founder.sqlparser.util.StringUtil;

/**
 *  * [FUN]V03-001患者列表  * [FUN]V04-001患者详细  *
 * 
 * @version 1.0, 2012/03/12  20:23:00
 * @version 1.1, 2012/06/04  13:18:00    * @author wu_jianfeng, wen_ruichao  * @since
 *          1.0
 */
@Controller
@RequestMapping("/patient")
public class PatientController {
	private static Logger logger = LoggerFactory
			.getLogger(PatientController.class);

	private static final String IS_PORTION = "1";

	@Autowired
	private PatientFavFolderService patientFavFolderService;

	@Autowired
	private PatientService patientService;

	@Autowired
	private VisitService visitService;

	/**
	 * V04-001: 患者详细信息 患者人口学等基本信息显示
	 * 
	 * @param patientSn
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/{sn}")
	public ModelAndView detail(@PathVariable("sn") String patientSn)
			throws Exception {
		ModelAndView mav = new ModelAndView();
		logger.debug("获取患者{}的信息。", patientSn);

		// $Author:tong_meng
		// $Date : 2012/09/19 13:51
		// $[BUG]0009814 ADD BEGIN
		// 获取登陆用户名
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		LoginUserDetails userDetails = (LoginUserDetails) authentication
				.getPrincipal();
		AccessControlDto aclAuths = null;
		if (AccessControl.useACL()) {
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
		mav.setViewName("/patient/patientDetail");
		return mav;
	}

	/**
	 * V03-001:患者信息集成视图 按临床信息模式集成显示患者信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/index")
	public ModelAndView mainView(WebRequest request) throws Exception {
		String loginFlag = request.getParameter("loginFlag");

		// $Author :jin_peng
		// $Date : 2012/11/09 17:51$
		// [BUG]0010795 MODIFY BEGIN
		// 集成登陆时传入域id
		String patientDomain = request.getParameter("patientDomain");
		String orgCode = request.getParameter("orgCode");

		ModelAndView mav = new ModelAndView();
		mav.addObject("loginFlag", loginFlag);
		mav.addObject("patientDomain", patientDomain);
		mav.addObject("orgCode", orgCode);
		mav.setViewName("/patient/mainView");

		// [BUG]0010795 MODIFY END

		return mav;
	}

	/**
	 * 门诊/住院医生下拉列表局部刷新
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/displayStaff")
	public ModelAndView display(WebRequest request) throws Exception {
		// 得到登录用户信息
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		LoginUserDetails userDetails = (LoginUserDetails) authentication
				.getPrincipal();
		// 访问控制实现
		AccessControlDto aclAuths = null;
		if (AccessControl.useACL()) {
			AccessControl acl = userDetails.getAccessControl();
			aclAuths = acl.getAccessControlAuths();
		}

		ModelAndView mav = new ModelAndView();
		// String loginFlag = request.getParameter("flag");
		// loginFlag = "false";
		// mav.addObject("loginFlag", loginFlag);
		mav.addObject("useACL", AccessControl.useACL());
		mav.addObject("aclAuths", aclAuths);
		mav.addObject("deptIds", userDetails.getDeptIds());

		/*
		 * ModelAndView mav = new ModelAndView(); PatientListSearchPra
		 * patientListSearchPra = new PatientListSearchPra();
		 * mav.addObject("patientListSearchPra", patientListSearchPra);
		 */
		mav.setViewName("/patient/displayStaff");
		return mav;
	}

	/**
	 * 患者列表中检索tab页科室下拉列表局部刷新
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/displayDept")
	public ModelAndView displayDept(String tab) throws Exception {
		// 得到登录用户信息
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		LoginUserDetails userDetails = (LoginUserDetails) authentication
				.getPrincipal();
		// 访问控制实现
		AccessControlDto aclAuths = null;
		if (AccessControl.useACL()) {
			AccessControl acl = userDetails.getAccessControl();
			aclAuths = acl.getAccessControlAuths();
		}

		ModelAndView mav = new ModelAndView();
		// String loginFlag = request.getParameter("flag");
		// loginFlag = "false";
		// mav.addObject("loginFlag", loginFlag);
		mav.addObject("useACL", AccessControl.useACL());
		mav.addObject("aclAuths", aclAuths);
		mav.addObject("deptIds", userDetails.getDeptIds());
		if (!StringUtil.isEmpty(tab)) {
			if ("out".equals(tab)) {
				mav.setViewName("/patient/displayOutDept");
			}
			if ("in".equals(tab)) {
				mav.setViewName("/patient/displayInDept");
			}
			if ("pl".equals(tab)) {
				mav.setViewName("/patient/displayDept");
			}
			if ("recent".equals(tab)) {
				mav.setViewName("/patient/displayRecentDept");
			}
		}
		/*
		 * ModelAndView mav = new ModelAndView(); PatientListSearchPra
		 * patientListSearchPra = new PatientListSearchPra();
		 * mav.addObject("patientListSearchPra", patientListSearchPra);
		 * mav.setViewName("/patient/displayDept");
		 */
		return mav;
	}

	/**
	 * 患者列表中tab页病区下拉列表局部刷新
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/displayWard")
	public ModelAndView displayWard(String tab) throws Exception {

		ModelAndView mav = new ModelAndView();
		if (!StringUtil.isEmpty(tab)) {
			if ("recent".equals(tab)) {
				mav.setViewName("/patient/displayRecentWard");
			}
			if ("in".equals(tab)) {
				mav.setViewName("/patient/displayInWard");
			}
			if ("pl".equals(tab)) {
				mav.setViewName("/patient/displayWard");
			}
		}
		return mav;
	}

	/**
	 * V03-001:患者列表 可通过搜索等方式显示门诊患者列表信息
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list_outpatient")
	public ModelAndView outpatientList(WebRequest request, HttpSession session)
			throws Exception {
		PagingContext pagingContext = PagingContextHolder.getPagingContext();
		logger.debug("显示第{}页的数据。", pagingContext.getPageNo());
		ModelAndView mav = new ModelAndView();

		String patientListRowsCntStr = AppSettings
				.getConfig(Constants.CFG_PATIENTLIST_ROWS_COUNT);
		int patientListRowsCnt = Integer.parseInt(patientListRowsCntStr);
		pagingContext.setRowCnt(patientListRowsCnt);

		PatientListSearchPra patientListSearchPra = new PatientListSearchPra();

		// 获取用户对象, 设置就诊医生ID
		LoginUserDetails userDetails = (LoginUserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		String userId = userDetails.getUsername();
		patientListSearchPra.setDoctorId(userId);

		// $Author:wu_jianfeng
		// $Date : 2012/12/05 14:10
		// $[BUG]0011228 MODIFY BEGIN
		// 设置用户所属的科室
		List<String> deptIds = userDetails.getDeptIds();
		patientListSearchPra.setDeptIds(deptIds);
		// $[BUG]0011228 MODIFY END

		// 门诊列表按就诊类型是门诊，取就诊日期是当天的数据
		// List<String> visitTypes = new ArrayList<String>();
		// visitTypes.add(Constants.VISIT_TYPE_CODE_OUTPATIENT);
		// // $Author:chang_xuewen
		// // $Date : 2013/7/16 10:10
		// // $[BUG]0034790 ADD BEGIN
		// visitTypes.add(Constants.VISIT_TYPE_CODE_EMERGENCY);
		// // $[BUG]0034790 ADD END
		// // $Author:wei_peng
		// // $Date: 2013/9/10 13:42
		// // $[BUG]0037109 ADD BEGIN
		// visitTypes.add(Constants.VISIT_TYPE_CODE_PHYSICALEXAM);
		// $[BUG]0037109 ADD END
		// 根据配置的就诊类型区分设定就诊类型
		List<String> visitTypes = Constants.lstVisitTypeOutPatient();
		patientListSearchPra.setVisitTypes(visitTypes);

		String todayText = DateUtils.dateToString(new java.util.Date(),
				DateUtils.PATTERN_MINUS_DATE);
		patientListSearchPra.setVisitStartDate(todayText);
		patientListSearchPra.setVisitEndDate(todayText);

		// $Author:jin_peng
		// $Date : 2013/11/07 13:41
		// $[BUG]0039035 MODIFY BEGIN
		// $Author:wu_jianfeng
		// $Date : 2012/09/12 14:10
		// $[BUG]0009752 MODIFY BEGIN
		String outpatientNo = request.getParameter("outpatient_no");
		String patientEId = request.getParameter("patientEId");
		String patientName = request.getParameter("outpatient_name");
		String portion = request.getParameter("portion");
		String visitDeptId = request.getParameter("deptId");
		// $Author:chang_xuewen
		// $Date : 2013/12/18 14:10
		// $[BUG]0040770 ADD BEGIN
		String orgCode = request.getParameter("orgCode");
		// $[BUG]0040770 ADD END

		// $Author:jin_peng
		// $Date : 2014/01/07 10:48
		// $[BUG]0041436 ADD BEGIN
		if (Constants.OPTION_SELECT.equals(orgCode)) {
			orgCode = null;
		}

		// $[BUG]0041436 ADD END

		if (Constants.OPTION_SELECT.equals(visitDeptId)) {
			visitDeptId = null;
		}
		patientListSearchPra.setDeptId(visitDeptId);

		patientListSearchPra.setOutpatientNo(outpatientNo);
		patientListSearchPra.setPatientEId(patientEId);
		patientListSearchPra.setPatientName(patientName);
		/* patientListSearchPra.setDeptName(deptName); */
		// $Author:chang_xuewen
		// $Date : 2013/12/18 14:10
		// $[BUG]0040770 ADD BEGIN
		patientListSearchPra.setOrgCode(orgCode);
		// $[BUG]0040770 ADD END

		// 患者域
		// List<String> patientDomains = new ArrayList<String>();
		// patientDomains.add(Constants.PATIENT_DOMAIN_OUTPATIENT);
		// 根据配置的域类型设定域id
		List<String> patientDomains = Constants.lstDomainOutPatient();

		patientListSearchPra.setPatientDomains(patientDomains);

		// 访问控制实现
		List<PatientDto> outpatientList = new ArrayList<PatientDto>();
		if (AccessControl.useACL()) {
			AccessControl acl = userDetails.getAccessControl();
			AccessControlDto aclAuths = acl.getAccessControlAuths();
			mav.addObject("aclAuths", aclAuths);
			// 可以访问我的患者信息，可以访问本科室门诊患者信息以及可以访问全院患者信息
			// 如果访问本科室门诊患者信息，要求所属的科室不能为空
			if (aclAuths.getPatientScopeAuth01()
					|| aclAuths.getPatientScopeAuth05()
					|| (aclAuths.getPatientScopeAuth02() && deptIds.size() != 0)) {
				Date startDate = DateUtils.getSystemTime();
				System.out.println("patientDao_selectOutpatientByACLCondition"
						+ ": "
						+ DateUtils.dateToString(startDate,
								"yyyy/MM/dd HH:mm:ss") + "  start");

				outpatientList = patientService.selectOutpatientByCondition(
						patientListSearchPra, aclAuths, pagingContext);

				Date endDate = DateUtils.getSystemTime();
				System.out.println("patientDao_selectOutpatientByACLCondition"
						+ ": "
						+ DateUtils
								.dateToString(endDate, "yyyy/MM/dd HH:mm:ss")
						+ "  end");

			}

		} else
			outpatientList = patientService.selectOutpatientByCondition(
					patientListSearchPra, pagingContext);
		// $[BUG]0009752 MODIFY END
		// $[BUG]0039035 MODIFY END

		List<String> patientEids = new ArrayList<String>();
		if (outpatientList != null && outpatientList.size() != 0) {

			for (PatientDto patient : outpatientList) {
				String patientEid = patient.getPatientEid();
				if (patientEid != null
						&& StringUtils.isEmpty(patientEid.trim()) == false)
					patientEids.add(patientEid);
			}
		}

		List<PatientCrossIndexDto> pciList = new ArrayList<PatientCrossIndexDto>();
		if (patientEids.size() != 0)
			// $Author:chang_xuewen
			// $Date : 2013/12/18 14:10
			// $[BUG]0040770 MODIFY BEGIN
			pciList = patientService
					.selectPatientCrossIndex(patientEids, patientDomains,
							Constants.OUTPATIENT_CONDITION_TAG, orgCode);

		// $[BUG]0040770 MODIFY END
		if (outpatientList != null && outpatientList.size() != 0) {
			for (PatientDto patient : outpatientList) {
				if (patient != null) {
					String outEidTemp = "";
					patient.setCardFlag("0");
					String outpatientNoJson = "{";
					int outpatientCountTemp = 0;
					patient.setAge(DateUtils.caluAge(patient.getBirthDate(),
							patient.getVisitDate()));
					for (PatientCrossIndexDto pci : pciList) {
						if (pci.getPatientEid().equals(patient.getPatientEid())) {
							if (outEidTemp.equals(pci.getPatientEid())) {
								outpatientCountTemp++;
								patient.setCardFlag("1");
								outpatientNoJson += ",\'门诊号"
										+ outpatientCountTemp + "\':\'"
										+ pci.getOutpatientNo() + "\'";
								continue;
							} else {
								outpatientCountTemp = 0;
								if (!StringUtil.isEmpty(patientListSearchPra.getOutpatientNo())) {
									patient.setOutpatientNo(patientListSearchPra
											.getOutpatientNo());
								} else {
									patient.setOutpatientNo(pci.getOutpatientNo());
								}
								outpatientNoJson += "\'门诊号\':\'"
										+ pci.getOutpatientNo() + "\'";
							}
						}
						outEidTemp = pci.getPatientEid();

					}
					outpatientNoJson += "}";
					logger.debug("获取outpatientNoJson" + outpatientNoJson);
					patient.setOutpatientNoJson(outpatientNoJson);
				}
			}
		}
		mav.addObject("outpatientList", outpatientList);
		if (outpatientList != null) {
			pagingContext.setRows(outpatientList.toArray());
		}

		mav.addObject("patientListSearchPra", patientListSearchPra);
		// $Author:chang_xuewen
		// $Date : 2013/12/18 14:10
		// $[BUG]0040770 ADD BEGIN
		mav.addObject("orgCode", orgCode);
		// $[BUG]0040770 ADD END
		if (IS_PORTION.equals(portion)) {
			mav.setViewName("/patient/outpatientDetail");
		} else {
			mav.addObject("useACL", AccessControl.useACL());
			mav.addObject("deptIds", userDetails.getDeptIds());
			mav.setViewName("/patient/outpatientList");
		}

		// 检索对象为空check
		Object[] rows = pagingContext.getRows();
		if (rows == null || rows.length == 0) {
			logger.debug("没有检索对象!");
			// 页面显示页面数赋值0
			pagingContext.setPageNo(0);
			pagingContext.setTotalPageCnt(0);
		}

		// 页面初始化分页页码显示列表
		PagingHelper.initPaging(pagingContext);
		mav.addObject("pagingContext", pagingContext);
		return mav;
	}

	/**
	 * V03-001:患者列表 可通过搜索等方式显示住院患者列表信息
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list_inpatient")
	public ModelAndView inpatientList(WebRequest request, HttpSession session)
			throws Exception {
		PagingContext pagingContext = PagingContextHolder.getPagingContext();
		logger.debug("显示第{}页的数据。", pagingContext.getPageNo());
		ModelAndView mav = new ModelAndView();

		String patientListRowsCntStr = AppSettings
				.getConfig(Constants.CFG_PATIENTLIST_ROWS_COUNT);
		int patientListRowsCnt = Integer.parseInt(patientListRowsCntStr);
		pagingContext.setRowCnt(patientListRowsCnt);

		PatientListSearchPra inpatientListSearchPra = new PatientListSearchPra();

		LoginUserDetails userDetails = (LoginUserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		String userId = userDetails.getUsername();

		// $Author:wu_jianfeng
		// $Date : 2012/12/05 14:10
		// $[BUG]0011228 MODIFY BEGIN
		// 设置用户所属的科室
		List<String> deptIds = userDetails.getDeptIds();
		inpatientListSearchPra.setDeptIds(deptIds);
		// $[BUG]0011228 MODIFY END

		// $Author:tong_meng
		// $Date : 2012/09/12 18:10
		// $[BUG]0011030 ADD BEGIN
		inpatientListSearchPra.setDoctorId(userId);
		// $[BUG]0011030 ADD END
		// 获取在院编码
		String inHospitalCode = Constants.VISIT_STATUS_IN_HOSPITAL;
		List<String> visitStatusList = new ArrayList<String>();
		visitStatusList.add(inHospitalCode);
		inpatientListSearchPra.setVisitStatus(visitStatusList);

		List<String> visitTypes = Constants.lstVisitTypeInPatient();
		inpatientListSearchPra.setVisitTypes(visitTypes);
		// inpatientListSearchPra.setPatientDomainStr(Constants.PATIENT_DOMAIN_INPATIENT);

		// $Author:jin_peng
		// $Date : 2013/11/07 13:41
		// $[BUG]0039035 MODIFY BEGIN
		// $Author:wu_jianfeng
		// $Date : 2012/09/12 14:10
		// $[BUG]0009752 MODIFY BEGIN
		String inpatientNo = request.getParameter("inpatient_no");
		String patientName = request.getParameter("inpatient_name");
		String bedNo = request.getParameter("inpatient_bed_no");
		String portion = request.getParameter("portion");
		/*
		 * String inpatientDeptName =
		 * request.getParameter("inpatient_dept_name");
		 */
		String deptId = request.getParameter("deptId");
		// $Author:chang_xuewen
		// $Date : 2013/12/18 14:10
		// $[BUG]0040770 ADD BEGIN
		String orgCode = request.getParameter("orgCode");
		if(orgCode != null && "undefined".equals(orgCode)){
			orgCode = null;
		}
		String patientEId = request.getParameter("patientEId");
		// $[BUG]0040770 ADD END

		// $Author:jin_peng
		// $Date : 2014/01/07 10:48
		// $[BUG]0041436 ADD BEGIN
		if (Constants.OPTION_SELECT.equals(orgCode)) {
			orgCode = null;
		}

		// $[BUG]0041436 ADD END
		// 下拉框未选择时处理
		if (Constants.OPTION_SELECT.equalsIgnoreCase(deptId)) {
			deptId = null;
		}
		String wardId = request.getParameter("wardId");
		if (Constants.OPTION_SELECT.equalsIgnoreCase(wardId)) {
			wardId = null;
		}
		inpatientListSearchPra.setDeptId(deptId);
		inpatientListSearchPra.setDischargeWardId(wardId);
		inpatientListSearchPra.setInpatientNo(inpatientNo);
		inpatientListSearchPra.setPatientName(patientName);
		inpatientListSearchPra.setDischargeBedNo(bedNo);
		/* inpatientListSearchPra.setDeptName(inpatientDeptName); */
		// $Author:chang_xuewen
		// $Date : 2013/12/18 14:10
		// $[BUG]0040770 ADD BEGIN
		inpatientListSearchPra.setOrgCode(orgCode);
		inpatientListSearchPra.setPatientEId(patientEId);
		// $[BUG]0040770 ADD END

		// 患者域
		List<String> patientDomains = Constants.lstDomainInPatient();

		inpatientListSearchPra.setPatientDomains(patientDomains);

		// 访问控制实现
		List<PatientDto> inpatientList = new ArrayList<PatientDto>();
		if (AccessControl.useACL()) {
			AccessControl acl = userDetails.getAccessControl();
			AccessControlDto aclAuths = acl.getAccessControlAuths();
			mav.addObject("aclAuths", aclAuths);
			// $Author:tong_meng
			// $Date : 2012/09/12 18:10
			// $[BUG]0011030 ADD BEGIN
			// 住院列表只适用可以访问本科室住院患者和可以访问全院患者信息
			// 如果可以访问全院患者信息
			// 如果可以访问本科室住院患者，要求该账户所属的科室不能为空
			if (aclAuths.getPatientScopeAuth01()
					|| aclAuths.getPatientScopeAuth05()
					|| (aclAuths.getPatientScopeAuth03() && inpatientListSearchPra
							.getDeptIds().size() != 0))
				inpatientList = patientService.selectInpatientByCondition(
						inpatientListSearchPra, aclAuths, pagingContext);
			// $[BUG]0011030 ADD END
		} else
			inpatientList = patientService.selectInpatientByCondition(
					inpatientListSearchPra, pagingContext);
		// $[BUG]0009752 MODIFY END
		// $[BUG]0039035 MODIFY END

		List<String> patientEids = new ArrayList<String>();
		if (inpatientList != null) {
			for (PatientDto patient : inpatientList) {
				String patientEid = patient.getPatientEid();
				if (patientEid != null
						&& StringUtils.isEmpty(patientEid.trim()) == false)
					patientEids.add(patientEid);
			}
		}

		List<PatientCrossIndexDto> pciList = new ArrayList();
		if (patientEids.size() != 0)
			// $Author:chang_xuewen
			// $Date : 2013/12/18 14:10
			// $[BUG]0040770 MODIFY BEGIN
			pciList = patientService.selectPatientCrossIndex(patientEids,
					patientDomains, Constants.INPATIENT_CONDITION_TAG, orgCode);
		// $[BUG]0040770 MODIFY END
		if (inpatientList != null) {
			for (PatientDto patient : inpatientList) {
				if (patient != null) {
					String inEidTemp = "";
					patient.setCardFlag("0");
					String inpatientNoJson = "{";
					int inpatientCountTemp = 0;
					patient.setAge(DateUtils.caluAge(patient.getBirthDate(),
							patient.getVisitDate()));
					for (PatientCrossIndexDto pci : pciList) {
						if (pci.getPatientEid().equals(patient.getPatientEid())) {
							if (inEidTemp.equals(pci.getPatientEid())) {
								inpatientCountTemp++;
								patient.setCardFlag("1");
								inpatientNoJson += ",\'住院号"
										+ inpatientCountTemp + "\':\'"
										+ pci.getInpatientNo() + "\'";
								continue;
							} else {
								inpatientCountTemp = 0;
								if (!StringUtil.isEmpty(inpatientListSearchPra.getInpatientNo())) {
									patient.setInpatientNo(inpatientListSearchPra
											.getInpatientNo());
								} else {
									patient.setInpatientNo(pci.getInpatientNo());
								}
								inpatientNoJson += "\'住院号\':\'"
										+ pci.getInpatientNo() + "\'";
							}
						}
						inEidTemp = pci.getPatientEid();

					}
					// patientNoJson=patientNoJson+"\'";
					inpatientNoJson += "}";
					logger.debug("获取inpatientNoJson" + inpatientNoJson);
					patient.setInpatientNoJson(inpatientNoJson);
				}
			}
		}

		// $Author:tong_meng
		// $Date : 2013/11/07 10:10
		// $[BUG]0039034 ADD BEGIN
		// 将病区去掉在界面上显示，其他不变
		StringUtils.cutString(inpatientList, "dischargeWardName", "病区", "");
		// $[BUG]0039034 ADD END
		// $Author:chang_xuewen
		// $Date : 2013/12/18 14:10
		// $[BUG]0040770 ADD BEGIN
		mav.addObject("orgCode", orgCode);
		// $[BUG]0040770 ADD END
		mav.addObject("inpatientList", inpatientList);
		if (inpatientList != null) {
			pagingContext.setRows(inpatientList.toArray());
		}
		mav.setViewName("/patient/inpatientList");

		mav.addObject("inpatientListSearchPra", inpatientListSearchPra);

		if (IS_PORTION.equals(portion)) {
			mav.setViewName("/patient/inpatientDetail");
		} else {
			mav.addObject("useACL", AccessControl.useACL());
			mav.addObject("deptIds", userDetails.getDeptIds());
			mav.setViewName("/patient/inpatientList");
		}

		// 检索对象为空check
		Object[] rows = pagingContext.getRows();
		if (rows == null || rows.length == 0) {
			logger.debug("没有检索对象!");
			// 页面显示页面数赋值0
			pagingContext.setPageNo(0);
			pagingContext.setTotalPageCnt(0);
		}

		// 页面初始化分页页码显示列表
		PagingHelper.initPaging(pagingContext);
		mav.addObject("pagingContext", pagingContext);
		return mav;
	}

	/**
	 * V03-001:患者列表 可通过搜索等方式显示最近患者列表信息
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list_recent")
	public ModelAndView recentPatientList(WebRequest request,
			HttpSession session) throws Exception {
		PagingContext pagingContext = PagingContextHolder.getPagingContext();
		logger.debug("显示第{}页的数据。", pagingContext.getPageNo());
		ModelAndView mav = new ModelAndView();

		String patientListRowsCntStr = AppSettings
				.getConfig(Constants.CFG_PATIENTLIST_ROWS_COUNT);
		int patientListRowsCnt = Integer.parseInt(patientListRowsCntStr);
		pagingContext.setRowCnt(patientListRowsCnt);

		RecentPatientListSearchPra recentListSearchPra = new RecentPatientListSearchPra();

		// 门诊域
		List outpatientDomains = Constants.lstDomainOutPatient();
		recentListSearchPra.setOutpatientDomains(outpatientDomains);

		// 住院域
		List<String> inpatientDomains = Constants.lstDomainInPatient();
		recentListSearchPra.setInpatientDomains(inpatientDomains);

		// 取当前日期的前一个月
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -7);
		String beginDateText = DateUtils.dateToString(calendar.getTime(),
				DateUtils.PATTERN_MINUS_DATE);
		recentListSearchPra.setBeginDateText(beginDateText);

		String todayText = DateUtils.dateToString(new java.util.Date(),
				DateUtils.PATTERN_MINUS_DATE);
		recentListSearchPra.setEndDateText(todayText);

		// $Author:wu_jianfeng
		// $Date:2012/9/4 10:42
		// $[BUG]0009345 ADD BEGIN
		// 患者列表中住院，最近，检索TAB页都只显示登录用户所在科室的患者
		LoginUserDetails userDetails = (LoginUserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		String userId = userDetails.getUsername();

		// $Author:wu_jianfeng
		// $Date : 2012/12/05 14:10
		// $[BUG]0011228 MODIFY BEGIN
		// 设置用户所属的科室
		List<String> deptIds = userDetails.getDeptIds();
		recentListSearchPra.setDeptIds(deptIds);
		// $[BUG]0011228 MODIFY END

		// $[BUG]0009345 ADD END

		List<PatientDto> recentPatientList = new ArrayList<PatientDto>();

		// $Author:jin_peng
		// $Date : 2013/11/07 13:41
		// $[BUG]0039035 MODIFY BEGIN
		// $Author:wu_jianfeng
		// $Date : 2012/09/12 14:10
		// $[BUG]0009752 MODIFY BEGIN
		recentListSearchPra
				.setVisitStatusCodeDischarge(Constants.VISIT_STATUS_DISCHARGE_HOSPITAL);

		String recentVisitNo = request.getParameter("recent_visit_no");
		String recentpatientName = request.getParameter("recentpatient_name");
		String recentPatientDomain = request
				.getParameter("recent_patient_domain");
		String portion = request.getParameter("portion");
		// String recentDeptName =
		// request.getParameter("recentpatient_dept_name");
		String deptId = request.getParameter("deptId");
		// $Author:chang_xuewen
		// $Date : 2013/12/18 14:10
		// $[BUG]0040770 ADD BEGIN
		String orgCode = request.getParameter("orgCode");
		if(orgCode != null && "undefined".equals(orgCode)){
			orgCode = null;
		}
		String patientEId = request.getParameter("patientEId");
		// $[BUG]0040770 ADD END

		// $Author:jin_peng
		// $Date : 2014/01/07 10:48
		// $[BUG]0041436 ADD BEGIN
		if (Constants.OPTION_SELECT.equals(orgCode)) {
			orgCode = null;
		}

		// $[BUG]0041436 ADD END

		// 下拉框未选择时处理
		if (Constants.OPTION_SELECT.equalsIgnoreCase(deptId)) {
			deptId = null;
		}
		String wardId = request.getParameter("wardId");
		if (Constants.OPTION_SELECT.equalsIgnoreCase(wardId)) {
			wardId = null;
		}
		recentListSearchPra.setDeptId(deptId);
		recentListSearchPra.setWardId(wardId);
		recentListSearchPra.setPatientName(recentpatientName);
		recentListSearchPra.setPatientEId(patientEId);
		recentListSearchPra.setVisitNoFlag(recentPatientDomain);
		recentListSearchPra.setVisitNo(recentVisitNo);
		// recentListSearchPra.setDeptName(recentDeptName);
		// $Author:chang_xuewen
		// $Date : 2013/12/18 14:10
		// $[BUG]0040770 ADD BEGIN
		recentListSearchPra.setOrgCode(orgCode);
		// $[BUG]0040770 ADD END

		if (Constants.PATIENT_DOMAIN_INPATIENT.equals(recentPatientDomain)) {
			recentListSearchPra.setInpatientNo(recentVisitNo);
		} else {
			recentListSearchPra.setOutpatientNo(recentVisitNo);
		}

		// 访问控制实现
		recentListSearchPra.setDoctorId(userId);
		if (AccessControl.useACL()) {
			AccessControl acl = userDetails.getAccessControl();
			AccessControlDto aclAuths = acl.getAccessControlAuths();
			mav.addObject("aclAuths", aclAuths);
			// 最近列表只适用可以访问我的患者信息, 可以访问本科室门诊患者信息
			// 如果可以访问全院患者信息, 可以访问出院患者信息
			// 如果可以访问本科室门诊患者，要求该账户所属的科室不能为空
			if (aclAuths.getPatientScopeAuth01()
					|| (aclAuths.getPatientScopeAuth02() && deptIds.size() != 0)
					|| aclAuths.getPatientScopeAuth05()
					|| aclAuths.getPatientScopeAuth06())
				recentPatientList = patientService
						.selectRecentPatientByCondition(recentListSearchPra,
								aclAuths, pagingContext);
		} else
			recentPatientList = patientService.selectRecentPatientByCondition(
					recentListSearchPra, pagingContext);

		// $[BUG]0039035 MODIFY END

		List<String> patientEids = new ArrayList<String>();
		for (PatientDto patient : recentPatientList) {
			String patientEid = patient.getPatientEid();
			if (patientEid != null
					&& StringUtils.isEmpty(patientEid.trim()) == false)
				patientEids.add(patientEid);
		}

		List<PatientCrossIndexDto> pciList_out = new ArrayList();
		List<PatientCrossIndexDto> pciList_in = new ArrayList();
		if (patientEids.size() != 0) {
			// $Author:chang_xuewen
			// $Date : 2013/12/18 14:10
			// $[BUG]0040770 MODIFY BEGIN
			pciList_out = patientService.selectPatientCrossIndex(patientEids,
					outpatientDomains, Constants.OUTPATIENT_CONDITION_TAG,
					orgCode);
			pciList_in = patientService.selectPatientCrossIndex(patientEids,
					inpatientDomains, Constants.INPATIENT_CONDITION_TAG,
					orgCode);
			// $[BUG]0040770 MODIFY END
		}

		for (PatientDto patient : recentPatientList) {
			if (patient != null) {
				String outEidTemp = "";
				String inEidTemp = "";
				patient.setCardFlag("0");
				String outpatientNoJson = "{";
				String inpatientNoJson = "{";
				patient.setAge(DateUtils.caluAge(patient.getBirthDate(),
						patient.getVisitDate()));
				int outpatientCountTemp = 0;
				int inpatientCountTemp = 0;
				// for (PatientCrossIndexDto pci : pciList_out) {
				// if (pci.getPatientEid().equals(patient.getPatientEid()))
				// patient.setOutpatientNo(pci.getOutpatientNo());
				// }
				// for (PatientCrossIndexDto pci : pciList_in) {
				// if (pci.getPatientEid().equals(patient.getPatientEid()))
				// patient.setInpatientNo(pci.getInpatientNo());
				// }
				for (PatientCrossIndexDto pci : pciList_out) {
					if (pci.getPatientEid().equals(patient.getPatientEid())) {
						if (outEidTemp.equals(pci.getPatientEid())) {
							outpatientCountTemp++;
							patient.setCardFlag("1");
							outpatientNoJson += ",\'门诊号" + outpatientCountTemp
									+ "\':\'" + pci.getOutpatientNo() + "\'";
							continue;
						} else {
							outpatientCountTemp = 0;
							if (!StringUtil.isEmpty(recentListSearchPra.getOutpatientNo())
									&& recentListSearchPra.getVisitNoFlag()
											.equals("01")) {
								patient.setOutpatientNo(recentListSearchPra
										.getOutpatientNo());
							} else {
								patient.setOutpatientNo(pci.getOutpatientNo());
							}
							outpatientNoJson += "\'门诊号\':\'"
									+ pci.getOutpatientNo() + "\'";
						}
					}
					outEidTemp = pci.getPatientEid();

				}

				outpatientNoJson += "}";
				logger.debug("获取outpatientNoJson" + outpatientNoJson);
				patient.setOutpatientNoJson(outpatientNoJson);

				// 检索患者的住院交叉索引信息，得到患者的住院号

				for (PatientCrossIndexDto pci : pciList_in) {
					if (pci.getPatientEid().equals(patient.getPatientEid())) {
						if (inEidTemp.equals(pci.getPatientEid())) {
							inpatientCountTemp++;
							patient.setCardFlag("1");
							inpatientNoJson += ",\'住院号" + inpatientCountTemp
									+ "\':\'" + pci.getInpatientNo() + "\'";
							continue;
						} else {
							inpatientCountTemp = 0;
							if (!StringUtil.isEmpty(recentListSearchPra.getInpatientNo())
									&& recentListSearchPra.getVisitNoFlag()
											.equals("02")) {
								patient.setInpatientNo(recentListSearchPra
										.getInpatientNo());
							} else {
								patient.setInpatientNo(pci.getInpatientNo());
							}

							inpatientNoJson += "\'住院号\':\'"
									+ pci.getInpatientNo() + "\'";
						}
					}
					inEidTemp = pci.getPatientEid();

				}

				inpatientNoJson += "}";
				logger.debug("获取inpatientNoJson" + inpatientNoJson);
				patient.setInpatientNoJson(inpatientNoJson);

			}
		}
		// $Author:tong_meng
		// $Date : 2013/11/07 10:10
		// $[BUG]0039034 ADD BEGIN
		// 将病区去掉在界面上显示，其他不变
		StringUtils.cutString(recentPatientList, "dischargeWardName", "病区", "");
		// $[BUG]0039034 ADD END
		mav.addObject("recentPatientList", recentPatientList);
		// $Author:chang_xuewen
		// $Date : 2013/12/18 14:10
		// $[BUG]0040770 ADD BEGIN
		mav.addObject("orgCode", orgCode);
		// $[BUG]0040770 ADD END
		if (recentPatientList != null) {
			pagingContext.setRows(recentPatientList.toArray());
		}
		mav.addObject("recentListSearchPra", recentListSearchPra);

		if (IS_PORTION.equals(portion)) {
			mav.setViewName("/patient/recentPatientDetail");
		} else {
			mav.addObject("useACL", AccessControl.useACL());
			mav.addObject("deptIds", userDetails.getDeptIds());
			mav.setViewName("/patient/recentPatientList");
		}

		// 检索对象为空check
		Object[] rows = pagingContext.getRows();
		if (rows == null || rows.length == 0) {
			logger.debug("没有检索对象!");
			// 页面显示页面数赋值0
			pagingContext.setPageNo(0);
			pagingContext.setTotalPageCnt(0);
		}

		// 页面初始化分页页码显示列表
		PagingHelper.initPaging(pagingContext);
		mav.addObject("pagingContext", pagingContext);
		return mav;
	}

	/**
	 * V03-001:患者列表 可通过搜索等方式显示最近患者列表信息
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	// $Author:wu_jianfeng
	// $Date : 2013/1/4 14:10
	// $[BUG]0012706 MODIFY BEGIN
	// 患者列表中的高级检索点击之后可以直接放在检索底下，不要弹出一个新框
	@RequestMapping("/list_patient")
	public ModelAndView list(WebRequest request, HttpSession session)
			throws Exception {
		// 得到登录用户信息
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		LoginUserDetails userDetails = (LoginUserDetails) authentication
				.getPrincipal();
		// 访问控制实现
		AccessControlDto aclAuths = null;
		if (AccessControl.useACL()) {
			AccessControl acl = userDetails.getAccessControl();
			aclAuths = acl.getAccessControlAuths();
		}

		ModelAndView mav = new ModelAndView();
		String loginFlag = request.getParameter("flag");
		// loginFlag = "false";
		mav.addObject("loginFlag", loginFlag);
		mav.addObject("useACL", AccessControl.useACL());
		mav.addObject("aclAuths", aclAuths);
		mav.addObject("deptIds", userDetails.getDeptIds());
		if ("false".equals(loginFlag)) {
			mav.setViewName("/patient/patientListView");
		} else
			mav.setViewName("/patient/patientList");

		return mav;
	}

	@RequestMapping("/list_patient_detail")
	public ModelAndView list_detail(WebRequest request, HttpSession session)
			throws Exception {
		PagingContext pagingContext = PagingContextHolder.getPagingContext();
		logger.debug("显示第{}页的数据。", pagingContext.getPageNo());
		ModelAndView mav = new ModelAndView();

		// 每页记录数
		String patientListRowsCntStr = AppSettings
				.getConfig(Constants.CFG_PATIENTLIST_ROWS_COUNT);
		int patientListRowsCnt = Integer.parseInt(patientListRowsCntStr);
		pagingContext.setRowCnt(patientListRowsCnt);

		PatientListSearchPra patientListSearchPra = new PatientListSearchPra();

		List<PatientDto> patientList = new ArrayList<PatientDto>();

		// 得到门诊域集合
		List outpatientDomains = Constants.lstDomainOutPatient();

		// 得到住院域集合
		List inpatientDomains = Constants.lstDomainInPatient();

		String postbackEvent = request.getParameter("postbackEvent");
		// $Author:chang_xuewen
		// $Date : 2013/12/18 14:10
		// $[BUG]0040770 ADD BEGIN
		String orgCode = null;
		// $[BUG]0040770 ADD END
		if ("paging".equals(postbackEvent)) {
			PatientListSearchPra sessionPra = (PatientListSearchPra) session
					.getAttribute("PatientListSearchPra");

			if (sessionPra != null) {
				patientListSearchPra = sessionPra;
				logger.debug(
						"检索条件：域代码={}, 门诊号={}, 住院号={}, 影像号={}, 患者姓名={}, 疾病名称={},就诊年龄起始年={},性别代码={}, 就诊开始日期={}, 就诊结束日期={}, 就诊类型={}, 科室ID={}, 用户ID={},科室名称={},出院床号={},出院病区名称={},患者EId={}",
						new Object[] {
								patientListSearchPra.getPatientDomainStr(),
								patientListSearchPra.getOutpatientNo(),
								patientListSearchPra.getInpatientNo(),
								patientListSearchPra.getImagingNo(),
								patientListSearchPra.getPatientName(),
								patientListSearchPra.getDiseaseName(),
								patientListSearchPra.getBaseAge(),
								patientListSearchPra.getGenderCode(),
								patientListSearchPra.getVisitStartDate(),
								patientListSearchPra.getVisitEndDate(),
								patientListSearchPra.getVisitTypeStr(),
								patientListSearchPra.getDeptIds(),
								patientListSearchPra.getDoctorId(),
								// patientListSearchPra.getDeptName(),
								patientListSearchPra.getDischargeBedNo(),
								patientListSearchPra.getDischargeWardName(),
								patientListSearchPra.getPatientEId() });
				// 检查就诊检索条件是否为空
				boolean visitInfoIsEmpty = false;
				// $Author:wu_jianfeng
				// $Date:2012/9/4 10:42
				// $[BUG]0009345 ADD BEGIN
				// 患者列表中住院，最近，检索TAB页都只显示登录用户所在科室的患者
				// $Author:wu_jianfeng
				// $Date : 2012/09/12 14:10
				// $[BUG]0010052 MODIFY BEGIN
				// 访问控制实现
				if (AccessControl.useACL())
					if (StringUtils.isEmpty(patientListSearchPra
							.getVisitStartDate())
							&& StringUtils.isEmpty(patientListSearchPra
									.getVisitEndDate())
							&& patientListSearchPra.getVisitTypeStr() == null
							&& patientListSearchPra.getBaseAge() == null)
						visitInfoIsEmpty = true;
					else {
						if (StringUtils.isEmpty(patientListSearchPra
								.getVisitStartDate())
								&& StringUtils.isEmpty(patientListSearchPra
										.getVisitEndDate())
								&& patientListSearchPra.getVisitTypeStr() == null
								&& patientListSearchPra.getBaseAge() == null
								&& patientListSearchPra.getDeptIds().isEmpty())
							visitInfoIsEmpty = true;
					}
				// $[BUG]0010052 MODIFY END
				// $[BUG]0009345 ADD END

				// 检查患者EMPI检索条件是否为空
				boolean empiInfoIsEmpty = false;
				if (patientListSearchPra.getPatientDomainStr() == null
						&& patientListSearchPra.getOutpatientNo() == null
						&& patientListSearchPra.getInpatientNo() == null
						&& patientListSearchPra.getImagingNo() == null)
					empiInfoIsEmpty = true;

				// $Author:wu_jianfeng
				// $Date : 2012/09/12 14:10
				// $[BUG]0009752 MODIFY BEGIN
				// 访问控制实现
				LoginUserDetails userDetails = (LoginUserDetails) SecurityContextHolder
						.getContext().getAuthentication().getPrincipal();
				if (AccessControl.useACL()) {
					AccessControl acl = userDetails.getAccessControl();
					AccessControlDto aclAuths = acl.getAccessControlAuths();
					// 检索列表只适用可以访问我的患者信息, 可以访问本科室门诊患者信息
					// 如果可以访问全院患者信息, 可以访问出院患者信息
					// 如果可以访问本科室门诊患者，要求该账户所属的科室不能为空
					if (aclAuths.getPatientScopeAuth01()
							|| (aclAuths.getPatientScopeAuth02() && patientListSearchPra
									.getDeptIds().size() != 0)
							|| (aclAuths.getPatientScopeAuth03() && patientListSearchPra
									.getDeptIds().size() != 0)
							|| aclAuths.getPatientScopeAuth05()
							|| aclAuths.getPatientScopeAuth06())
						patientList = patientService.selectPatientByCondition(
								patientListSearchPra, aclAuths,
								visitInfoIsEmpty, empiInfoIsEmpty,
								pagingContext);
				} else
					patientList = patientService.selectPatientByCondition(
							patientListSearchPra, visitInfoIsEmpty,
							empiInfoIsEmpty, pagingContext);
				// $[BUG]0009752 MODIFY END
			}
		} else {
			// 将页面上的检索条件赋给检索对象 BEGIN
			// 患者域
			String patientDomainStr = request.getParameter("patientDomainStr");
			// 下拉框未选择时处理
			if (Constants.OPTION_SELECT.equalsIgnoreCase(patientDomainStr)) {
				patientDomainStr = null;
			}
			List<String> patientDomains = new ArrayList<String>();
			if (patientDomainStr != null) {
				for (String patientDomain : patientDomainStr.split(",")) {
					// 根据选择门诊，住院区分不同，设定domain信息
					if (Constants.PATIENT_DOMAIN_OUTPATIENT
							.equals(patientDomain)) {
						patientDomains.addAll(Constants.lstDomainOutPatient());
					} else {
						patientDomains.addAll(Constants.lstDomainInPatient());
					}
				}
			}

			// 患者就诊号
			String visitNo = request.getParameter("visitNo");
			// 患者姓名
			String patientName = request.getParameter("patientName");
			// 性别
			String genderCode = request.getParameter("gender");
			// 疾病名称
			String diseaseName = request.getParameter("diseaseName");
			// 就诊年龄段起始时间
			String baseAge = request.getParameter("baseAge");
			// 下拉框未选择时处理
			if (Constants.OPTION_SELECT.equalsIgnoreCase(baseAge)) {
				baseAge = null;
			}
			// 下拉框未选择时处理
			if (Constants.OPTION_SELECT.equalsIgnoreCase(genderCode)) {
				genderCode = null;
			}
			// 就诊开始日期
			String visitStartDate = request.getParameter("visitStartDate");
			// 就诊结束日期
			String visitEndDate = request.getParameter("visitEndDate");
			// 就诊类型
			String visitTypeStr = request.getParameter("visitTypeStr");
			// 下拉框未选择时处理
			if (Constants.OPTION_SELECT.equalsIgnoreCase(visitTypeStr)) {
				visitTypeStr = null;
			}
			List<String> visitTypes = new ArrayList<String>();
			if (visitTypeStr != null)
				visitTypes.add(visitTypeStr);

			// 就诊或住院科室名称
			String deptId = request.getParameter("deptName");
			// 下拉框未选择时处理
			if (Constants.OPTION_SELECT.equalsIgnoreCase(deptId)) {
				deptId = null;
			}
			// 住院病区名称
			String wardId = request.getParameter("wardName");
			// 下拉框未选择时处理
			if (Constants.OPTION_SELECT.equalsIgnoreCase(wardId)) {
				wardId = null;
			}
			// 住院床号
			String bedNo = request.getParameter("bedNo");
			// 病人EID
			String patientEId = request.getParameter("patientEId");

			// $Author:chang_xuewen
			// $Date : 2013/12/18 14:10
			// $[BUG]0040770 ADD BEGIN
			orgCode = request.getParameter("orgCodeFlag");
			// $[BUG]0040770 ADD END

			// $Author:jin_peng
			// $Date : 2014/01/07 10:48
			// $[BUG]0041436 ADD BEGIN
			if (Constants.OPTION_SELECT.equals(orgCode)) {
				orgCode = null;
			}

			// $[BUG]0041436 ADD END

			if (patientDomainStr != null) {
				for (String patientDomain : patientDomainStr.split(",")) {
					if (patientDomain
							.equals(Constants.PATIENT_DOMAIN_OUTPATIENT)) {
						patientListSearchPra.setOutpatientNo(visitNo);
					}
					// else if
					// (patientDomain.equals(Constants.PATIENT_DOMAIN_PHYSICAL_EXAM))
					// {
					// patientListSearchPra.setOutpatientNo(visitNo);
					// }
					else if (patientDomain
							.equals(Constants.PATIENT_DOMAIN_INPATIENT)) {
						patientListSearchPra.setInpatientNo(visitNo);
					}
					// else if
					// (patientDomain.equals(Constants.PATIENT_DOMAIN_IMAGE))
					// {
					// patientListSearchPra.setImagingNo(visitNo);
					// }
				}
			}

			// $Author:chang_xuewen
			// $Date : 2013/12/18 14:10
			// $[BUG]0040770 ADD BEGIN
			patientListSearchPra.setOrgCode(orgCode);
			// $[BUG]0040770 ADD END
			// $Author:chang_xuewen
			// $Date : 2013/12/31 16:10
			// $[BUG]0040993 ADD BEGIN
			String staff = request.getParameter("staff");
			// $[BUG]0040993 ADD END

			// $Author:chang_xuewen
			// $Date : 2013/12/31 16:10
			// $[BUG]0040993 ADD BEGIN
			patientListSearchPra.setStaff(Constants.OPTION_SELECT
					.equalsIgnoreCase(staff) ? null : staff);
			// $[BUG]0040993 ADD END

			// $Author:wu_jianfeng
			// $Date : 2013/04/15 14:10
			// $[BUG]0015064 MODIFY BEGIN
			patientListSearchPra
					.setVisitStatusDischarge(Constants.VISIT_STATUS_DISCHARGE_HOSPITAL);
			/*// 根据选择的就诊类型判断门诊还是住院
			if (Constants.lstVisitTypeInPatient().contains(visitTypeStr)) {
				patientListSearchPra.setVisitTypeInpatient(visitTypeStr);
			} else {
				patientListSearchPra.setVisitTypeOutpatient(visitTypeStr);
			}*/
			patientListSearchPra.setVisitTypeInpatient(Constants.VISIT_TYPE_CODE_INPATIENT);
			patientListSearchPra
					.setVisitStatusInHospital(Constants.VISIT_STATUS_IN_HOSPITAL);
			// $[BUG]0015064 MODIFY END

			// 设置患者EMPI检索条件
			patientListSearchPra.setPatientDomainStr(patientDomainStr);
			patientListSearchPra.setPatientDomains(patientDomains);
			patientListSearchPra.setVisitNo(visitNo);
			patientListSearchPra.setPatientEId(patientEId);
			// 设置患者检索条件
			patientListSearchPra.setPatientName(patientName);
			patientListSearchPra.setGenderCode(genderCode);
			// 设置就诊检索条件
			patientListSearchPra.setVisitStartDate(visitStartDate);
			patientListSearchPra.setVisitEndDate(visitEndDate);
			patientListSearchPra.setVisitTypeStr(visitTypeStr);
			patientListSearchPra.setVisitTypes(visitTypes);
			patientListSearchPra.setBaseAge(baseAge);
			patientListSearchPra.setDischargeBedNo(bedNo);
			patientListSearchPra.setDischargeWardId(wardId);
			patientListSearchPra.setDeptId(deptId);
			patientListSearchPra.setOutpatientDomains(outpatientDomains);
			patientListSearchPra.setInpatientDomains(inpatientDomains);

			// 设置诊断检索条件
			patientListSearchPra.setDiseaseName(diseaseName);

			LoginUserDetails userDetails = (LoginUserDetails) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			String userId = userDetails.getUsername();

			List<String> deptIds = userDetails.getDeptIds();
			patientListSearchPra.setDeptIds(deptIds);
			patientListSearchPra.setDoctorId(userId);
			
			// Author: yu_yzh
			// Date: 2016/3/2 10:34
			// 新增检索条件，身份证号
			// [BUG]0063885
			// ADD BEGIN
			String idNumber = request.getParameter("idNumber");
			patientListSearchPra.setIdNumber(idNumber);
			// [BUG]0063885
			// ADD END

			session.setAttribute("PatientListSearchPra", patientListSearchPra);
			
			logger.debug(
					"检索条件：域代码={}, 门诊号={}, 住院号={}, 影像号={}, 患者姓名={}, 疾病名称={},就诊年龄起始年={},性别代码={}, 就诊开始日期={}, 就诊结束日期={}, 就诊类型={}, 科室ID={}, 用户ID={},科室名称={},出院床号={},出院病区名称={},患者EId={},身份证号={}",
					new Object[] { patientListSearchPra.getPatientDomainStr(),
							patientListSearchPra.getOutpatientNo(),
							patientListSearchPra.getInpatientNo(),
							patientListSearchPra.getImagingNo(),
							patientListSearchPra.getPatientName(),
							patientListSearchPra.getDiseaseName(),
							patientListSearchPra.getBaseAge(),
							patientListSearchPra.getGenderCode(),
							patientListSearchPra.getVisitStartDate(),
							patientListSearchPra.getVisitEndDate(),
							patientListSearchPra.getVisitTypeStr(),
							patientListSearchPra.getDeptIds(),
							patientListSearchPra.getDoctorId(),
							patientListSearchPra.getDeptName(),
							patientListSearchPra.getDischargeBedNo(),
							patientListSearchPra.getDischargeWardName(),
							patientListSearchPra.getPatientEId(),
							patientListSearchPra.getIdNumber() });
			// 将页面上的检索条件赋给检索对象 END

			// 检查就索条件中是否包含就诊的检索条件
			boolean visitInfoIsEmpty = false;

			if (AccessControl.useACL()) {
				if (StringUtils.isEmpty(patientListSearchPra
						.getVisitStartDate())
						&& StringUtils.isEmpty(patientListSearchPra
								.getVisitEndDate())
						&& patientListSearchPra.getVisitTypeStr() == null
						&& patientListSearchPra.getBaseAge() == null)
					visitInfoIsEmpty = true;
			} else {
				if (StringUtils.isEmpty(patientListSearchPra
						.getVisitStartDate())
						&& StringUtils.isEmpty(patientListSearchPra
								.getVisitEndDate())
						&& patientListSearchPra.getVisitTypeStr() == null
						&& patientListSearchPra.getBaseAge() == null
						&& patientListSearchPra.getDeptIds().isEmpty())
					visitInfoIsEmpty = true;
			}

			// 检查就索条件中是否包含EMPI的检索条件
			boolean empiInfoIsEmpty = false;
			if (patientListSearchPra.getPatientDomainStr() == null
					&& patientListSearchPra.getOutpatientNo() == null
					&& patientListSearchPra.getInpatientNo() == null
					&& patientListSearchPra.getImagingNo() == null)
				empiInfoIsEmpty = true;

			if (AccessControl.useACL()) {
				AccessControl acl = userDetails.getAccessControl();
				AccessControlDto aclAuths = acl.getAccessControlAuths();
				// 检索列表只适用可以访问我的患者信息, 可以访问本科室门诊患者信息
				// 如果可以访问全院患者信息, 可以访问出院患者信息
				// 如果可以访问本科室门诊患者，要求该账户所属的科室不能为空
				if (aclAuths.getPatientScopeAuth01()
						|| (aclAuths.getPatientScopeAuth02() && patientListSearchPra
								.getDeptIds().size() != 0)
						|| (aclAuths.getPatientScopeAuth03() && patientListSearchPra
								.getDeptIds().size() != 0)
						|| aclAuths.getPatientScopeAuth05()
						|| aclAuths.getPatientScopeAuth06())
					patientList = patientService.selectPatientByCondition(
							patientListSearchPra, aclAuths, visitInfoIsEmpty,
							empiInfoIsEmpty, pagingContext);
			} else
				patientList = patientService.selectPatientByCondition(
						patientListSearchPra, visitInfoIsEmpty,
						empiInfoIsEmpty, pagingContext);
		}

		// 得到患者列表的empi id集合
		List<String> patientEids = new ArrayList<String>();
		if (patientList != null) {
			for (PatientDto patient : patientList) {
				String patientEid = patient.getPatientEid();
				if (patientEid != null
						&& StringUtils.isEmpty(patientEid.trim()) == false)
					patientEids.add(patientEid);
			}
		}

		// 根据患者的empi id集合以及域，查找患者门诊和住院的交叉索引信息
		List<PatientCrossIndexDto> pciList_out = new ArrayList();
		List<PatientCrossIndexDto> pciList_in = new ArrayList();
		if (patientEids.size() != 0) {
			// $Author:chang_xuewen
			// $Date : 2013/12/18 14:10
			// $[BUG]0040770 MODIFY BEGIN
			// 门诊交叉索引信息
			pciList_out = patientService.selectPatientCrossIndex(patientEids,
					outpatientDomains, Constants.OUTPATIENT_CONDITION_TAG,
					orgCode);
			// 住院交叉索引信息
			pciList_in = patientService.selectPatientCrossIndex(patientEids,
					inpatientDomains, Constants.INPATIENT_CONDITION_TAG,
					orgCode);
			// $[BUG]0040770 MODIFY END
		}
		if (patientList != null) {
			for (PatientDto patient : patientList) {
				if (patient != null) {
					String outEidTemp = "";
					String inEidTemp = "";
					patient.setCardFlag("0");
					String outpatientNoJson = "{";
					String inpatientNoJson = "{";
					patient.setAge(DateUtils.caluAge(patient.getBirthDate(),
							patient.getVisitDate()));
					int outpatientCountTemp = 0;
					int inpatientCountTemp = 0;
					// 检索患者的门诊交叉索引信息，得到患者的门诊号

					for (PatientCrossIndexDto pci : pciList_out) {
						if (pci.getPatientEid().equals(patient.getPatientEid())) {
							if (outEidTemp.equals(pci.getPatientEid())) {
								outpatientCountTemp++;
								patient.setCardFlag("1");
								outpatientNoJson += ",\'门诊号"
										+ outpatientCountTemp + "\':\'"
										+ pci.getOutpatientNo() + "\'";
								continue;
							} else {
								outpatientCountTemp = 0;
								if (!StringUtil.isEmpty(patientListSearchPra.getOutpatientNo())
										&& patientListSearchPra
												.getPatientDomainStr().equals(
														"01")) {
									patient.setOutpatientNo(patientListSearchPra
											.getOutpatientNo());
								} else {
									patient.setOutpatientNo(pci
											.getOutpatientNo());
								}
								outpatientNoJson += "\'门诊号\':\'"
										+ pci.getOutpatientNo() + "\'";
							}
						}
						outEidTemp = pci.getPatientEid();

					}
					outpatientNoJson += "}";
					logger.debug("获取outpatientNoJson" + outpatientNoJson);
					patient.setOutpatientNoJson(outpatientNoJson);

					// 检索患者的住院交叉索引信息，得到患者的住院号

					for (PatientCrossIndexDto pci : pciList_in) {
						if (pci.getPatientEid().equals(patient.getPatientEid())) {
							if (inEidTemp.equals(pci.getPatientEid())) {
								inpatientCountTemp++;
								patient.setCardFlag("1");
								inpatientNoJson += ",\'住院号"
										+ inpatientCountTemp + "\':\'"
										+ pci.getInpatientNo() + "\'";
								continue;
							} else {
								inpatientCountTemp = 0;
								if (!StringUtil.isEmpty(patientListSearchPra.getInpatientNo())
										&& patientListSearchPra
												.getPatientDomainStr().equals(
														"02")) {
									patient.setInpatientNo(patientListSearchPra
											.getInpatientNo());
								} else {
									patient.setInpatientNo(pci.getInpatientNo());
								}
								inpatientNoJson += "\'住院号\':\'"
										+ pci.getInpatientNo() + "\'";
							}
						}
						inEidTemp = pci.getPatientEid();

					}
					// patientNoJson=patientNoJson+"\'";
					inpatientNoJson += "}";
					logger.debug("获取inpatientNoJson" + inpatientNoJson);
					patient.setInpatientNoJson(inpatientNoJson);
				}
			}
		}

		// $Author:tong_meng
		// $Date : 2013/11/07 10:10
		// $[BUG]0039034 ADD BEGIN
		// 将病区去掉在界面上显示，其他不变
		StringUtils.cutString(patientList, "dischargeWardName", "病区", "");
		// $[BUG]0039034 ADD END

		mav.addObject("patientList", patientList);
		// $Author:chang_xuewen
		// $Date : 2013/12/18 14:10
		// $[BUG]0040770 ADD BEGIN
		mav.addObject("orgCode", orgCode);
		// $[BUG]0040770 ADD END
		if (patientList != null) {
			pagingContext.setRows(patientList.toArray());
		}
		mav.setViewName("/patient/patientListDetail");

		// 检索对象为空check
		Object[] rows = pagingContext.getRows();
		if (rows == null || rows.length == 0) {
			logger.debug("没有检索对象!");
			// 页面显示页面数赋值0
			pagingContext.setPageNo(0);
			pagingContext.setTotalPageCnt(0);
		}

		// 页面初始化分页页码显示列表
		PagingHelper.initPaging(pagingContext);
		mav.addObject("pagingContext", pagingContext);

		mav.addObject("searchType", "normal");
		return mav;
	}

	@RequestMapping("/list_patient_ad_detail")
	public ModelAndView list_ad_detail(WebRequest request, HttpSession session) {
		PagingContext pagingContext = PagingContextHolder.getPagingContext();
		logger.debug("显示第{}页的数据。", pagingContext.getPageNo());
		ModelAndView mav = new ModelAndView();

		String patientListRowsCntStr = AppSettings
				.getConfig(Constants.CFG_PATIENTLIST_ROWS_COUNT);
		int patientListRowsCnt = Integer.parseInt(patientListRowsCntStr);
		pagingContext.setRowCnt(patientListRowsCnt);

		PatientListAdvanceSearchPra patientListAdvanceSearchPra = new PatientListAdvanceSearchPra();
		List<PatientDto> patientList = new ArrayList<PatientDto>();

		String postbackEvent = request.getParameter("postbackEvent");

		if ("paging".equals(postbackEvent)) {
			PatientListAdvanceSearchPra asSessionPra = (PatientListAdvanceSearchPra) session
					.getAttribute("PatientListAdvanceSearchPra");
			if (asSessionPra != null) {
				patientListAdvanceSearchPra = asSessionPra;

				LoginUserDetails userDetails = (LoginUserDetails) SecurityContextHolder
						.getContext().getAuthentication().getPrincipal();

				if (AccessControl.useACL()) {
					AccessControl acl = userDetails.getAccessControl();
					AccessControlDto aclAuths = acl.getAccessControlAuths();
					patientList = patientService.selectPatientByCondition(
							patientListAdvanceSearchPra, aclAuths,
							pagingContext);
					int total_count = patientService.selectPatientListTotalCnt(
							patientListAdvanceSearchPra, aclAuths);
					pagingContext.setTotalRowCnt(total_count);
					double totalPage_count = (double) (total_count)
							/ pagingContext.getPerPageCnt();
					pagingContext.setTotalPageCnt((int) (Math
							.ceil(totalPage_count)));
				} else {
					patientList = patientService.selectPatientByCondition(
							patientListAdvanceSearchPra, pagingContext);
					int total_count = patientService
							.selectPatientListTotalCnt(patientListAdvanceSearchPra);
					pagingContext.setTotalRowCnt(total_count);
					double totalPage_count = (double) (total_count)
							/ pagingContext.getPerPageCnt();
					pagingContext.setTotalPageCnt((int) (Math
							.ceil(totalPage_count)));
				}
			}
		} else {
			patientListAdvanceSearchPra.setVisitSql(request
					.getParameter("visit"));
			patientListAdvanceSearchPra.setPatientSql(request
					.getParameter("patient"));
			patientListAdvanceSearchPra.setDiagnosisSql(request
					.getParameter("diagnosis"));
			patientListAdvanceSearchPra.setEmpiSql(request.getParameter("pci"));
			patientListAdvanceSearchPra.setLabItemSql(request
					.getParameter("lab_result_item"));

			LoginUserDetails userDetails = (LoginUserDetails) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			String userId = userDetails.getUsername();

			List<String> deptIds = userDetails.getDeptIds();
			patientListAdvanceSearchPra.setDeptIds(deptIds);
			patientListAdvanceSearchPra.setDoctorId(userId);
			session.setAttribute("PatientListAdvanceSearchPra",
					patientListAdvanceSearchPra);

			if (AccessControl.useACL()) {
				AccessControl acl = userDetails.getAccessControl();
				AccessControlDto aclAuths = acl.getAccessControlAuths();
				patientListAdvanceSearchPra
						.setVisitStatusDischarge(Constants.VISIT_STATUS_DISCHARGE_HOSPITAL);
				patientListAdvanceSearchPra
						.setVisitTypeInpatient(Constants.VISIT_TYPE_CODE_INPATIENT);
				patientListAdvanceSearchPra
						.setVisitTypeOutpatient(Constants.VISIT_TYPE_CODE_OUTPATIENT);
				patientList = patientService.selectPatientByCondition(
						patientListAdvanceSearchPra, aclAuths, pagingContext);
				int total_count = patientService.selectPatientListTotalCnt(
						patientListAdvanceSearchPra, aclAuths);
				pagingContext.setTotalRowCnt(total_count);
				double totalPage_count = (double) (total_count)
						/ pagingContext.getPerPageCnt();
				pagingContext
						.setTotalPageCnt((int) (Math.ceil(totalPage_count)));
			} else {
				patientList = patientService.selectPatientByCondition(
						patientListAdvanceSearchPra, pagingContext);
				int total_count = patientService
						.selectPatientListTotalCnt(patientListAdvanceSearchPra);
				pagingContext.setTotalRowCnt(total_count);
				double totalPage_count = (double) (total_count)
						/ pagingContext.getPerPageCnt();
				pagingContext
						.setTotalPageCnt((int) (Math.ceil(totalPage_count)));
			}
		}
		// 得到患者列表的empi id集合
		List<String> patientEids = new ArrayList<String>();
		for (PatientDto patient : patientList) {
			String patientEid = patient.getPatientEid();
			if (patientEid != null
					&& StringUtils.isEmpty(patientEid.trim()) == false)
				patientEids.add(patientEid);
		}

		// 得到门诊域集合
		List<String> outpatientDomains = Constants.lstDomainOutPatient();

		// 得到住院域集合
		List<String> inpatientDomains = Constants.lstDomainInPatient();

		// 根据患者的empi id集合以及域，查找患者门诊和住院的交叉索引信息
		List<PatientCrossIndexDto> pciList_out = new ArrayList<PatientCrossIndexDto>();
		List<PatientCrossIndexDto> pciList_in = new ArrayList<PatientCrossIndexDto>();
		if (patientEids.size() != 0) {
			// 门诊交叉索引信息
			pciList_out = patientService
					.selectPatientCrossIndex(patientEids, outpatientDomains,
							Constants.OUTPATIENT_CONDITION_TAG, null);
			// 住院交叉索引信息
			pciList_in = patientService.selectPatientCrossIndex(patientEids,
					inpatientDomains, Constants.INPATIENT_CONDITION_TAG, null);
		}

		for (PatientDto patient : patientList) {
			if (patient != null) {
				patient.setAge(DateUtils.caluAge(patient.getBirthDate(),
						patient.getVisitDate()));
				// 检索患者的门诊交叉索引信息，得到患者的门诊号
				for (PatientCrossIndexDto pci : pciList_out) {
					if (pci.getPatientEid().equals(patient.getPatientEid()))
						patient.setOutpatientNo(pci.getOutpatientNo());
				}
				// 检索患者的住院交叉索引信息，得到患者的住院号
				for (PatientCrossIndexDto pci : pciList_in) {
					if (pci.getPatientEid().equals(patient.getPatientEid()))
						patient.setInpatientNo(pci.getInpatientNo());
				}
			}
		}

		mav.addObject("patientList", patientList);
		if (patientList != null) {
			pagingContext.setRows(patientList.toArray());
		}
		mav.setViewName("/patient/patientListDetail");

		// 检索对象为空check
		Object[] rows = pagingContext.getRows();
		if (rows == null || rows.length == 0) {
			logger.debug("没有检索对象!");
			// 页面显示页面数赋值0
			pagingContext.setPageNo(0);
			pagingContext.setTotalPageCnt(0);
		}

		// 页面初始化分页页码显示列表
		PagingHelper.initPaging(pagingContext);
		mav.addObject("pagingContext", pagingContext);
		mav.addObject("searchType", "advance");
		return mav;
	}

	@RequestMapping("/list_patient_empty_detail")
	public ModelAndView list_empty_detail(WebRequest request,
			HttpSession session) throws Exception {
		PagingContext pagingContext = PagingContextHolder.getPagingContext();
		logger.debug("显示第{}页的数据。", pagingContext.getPageNo());
		ModelAndView mav = new ModelAndView();

		// 每页记录数
		String patientListRowsCntStr = AppSettings
				.getConfig(Constants.CFG_PATIENTLIST_ROWS_COUNT);
		int patientListRowsCnt = Integer.parseInt(patientListRowsCntStr);
		pagingContext.setRowCnt(patientListRowsCnt);

		List<PatientDto> patientList = new ArrayList<PatientDto>();

		mav.addObject("patientList", patientList);
		if (patientList != null) {
			pagingContext.setRows(patientList.toArray());
		}
		mav.setViewName("/patient/patientListDetail");

		// 检索对象为空check
		Object[] rows = pagingContext.getRows();
		if (rows == null || rows.length == 0) {
			logger.debug("没有检索对象!");
			// 页面显示页面数赋值0
			pagingContext.setPageNo(0);
			pagingContext.setTotalPageCnt(0);
		}

		// 页面初始化分页页码显示列表
		PagingHelper.initPaging(pagingContext);
		mav.addObject("pagingContext", pagingContext);
		mav.addObject("queryFlag", "0");
		return mav;
	}

	// $[BUG]0012706 MODIFY END

	// $Author:wu_jianfeng
	// $Date : 2012/10/09 13:10
	// $[BUG]0010244 ADD BEGIN
	@RequestMapping("/list_patientfav")
	public ModelAndView patientFavlist(WebRequest request, HttpSession session)
			throws Exception {
		ModelAndView mav = new ModelAndView();
		PagingContext pagingContext = PagingContextHolder.getPagingContext();

		// 得到登录用户的ID
		LoginUserDetails userDetails = (LoginUserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		String userId = userDetails.getUsername();

		// 回发事件，根据事件名称，处理相应操作
		String postbackEvent = request.getParameter("postbackEvent");
		if (postbackEvent == null)
			postbackEvent = "";

		// 是否登录
		String loginFlag = request.getParameter("flag");

		List<PatientFavFolderDto> patientFavFolderDtos = new ArrayList<PatientFavFolderDto>();

		// 创建文件夹
		if (postbackEvent.equals("create_folder")) {
			// 文件夹名称
			String folderName = request.getParameter("folderName");
			if (!StringUtils.isEmpty(folderName)) {
				PatientFavFolder favFolder = new PatientFavFolder();
				favFolder.setFolderName(folderName);
				favFolder.setCreateUserId(userId);
				Date systemDate = DateUtils.getSystemTime();
				favFolder.setCreateTime(systemDate);
				favFolder.setUpdateTime(systemDate);
				favFolder.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
				favFolder.setDeleteFlag(Constants.NO_DELETE_FLAG);
				patientFavFolderService.createPatientFavFolder(favFolder);
			}
		}
		// 创建子文件夹
		else if (postbackEvent.equals("create_child_folder")) {
			// 文件夹名称
			String folderName = request.getParameter("folderName");
			String parentFolderSn = request.getParameter("parentFolderSn");
			if (!StringUtils.isEmpty(folderName)) {
				PatientFavFolder favFolder = new PatientFavFolder();
				favFolder.setFolderName(folderName);
				favFolder.setCreateUserId(userId);
				favFolder.setParentFolderSn(new BigDecimal(parentFolderSn));
				Date systemDate = DateUtils.getSystemTime();
				favFolder.setCreateTime(systemDate);
				favFolder.setUpdateTime(systemDate);
				favFolder.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
				favFolder.setDeleteFlag(Constants.NO_DELETE_FLAG);
				patientFavFolderService.createPatientFavFolder(favFolder);
			}
		}
		// 删除文件夹
		else if (postbackEvent.equals("delete_folder")) {
			// 文件夹唯一标识
			String folderId = request.getParameter("selectedFolder");
			if (!StringUtils.isEmpty(folderId))
				patientFavFolderService.deletePatientFavFolder(folderId);
		}

		// 读取文件夹信息
		List<PatientFavFolder> patientFavFolders = patientFavFolderService
				.selectPatientFavFolders(userId);
		// 递归创建文件夹父子关系
		for (PatientFavFolder patientFavFolder : patientFavFolders) {
			if (patientFavFolder.getParentFolderSn() == null) {
				PatientFavFolderDto patinetFavFolderDto = new PatientFavFolderDto();
				patinetFavFolderDto.setFolderSn(patientFavFolder.getFolderSn());
				patinetFavFolderDto.setFolderName(patientFavFolder
						.getFolderName());
				setChildrenFolders(patientFavFolders, patinetFavFolderDto);
				patientFavFolderDtos.add(patinetFavFolderDto);
			}
		}

		mav.addObject("patientFavFolders", patientFavFolderDtos);

		// 是否登录
		mav.addObject("loginFlag", loginFlag);
		mav.setViewName("/patient/patientFavList");

		return mav;
	}

	// 递归设置子文件夹
	private void setChildrenFolders(List<PatientFavFolder> patientFavFolders,
			PatientFavFolderDto patientFavFolderDto) {
		List<PatientFavFolderDto> patientFavFolderDtos = new ArrayList<PatientFavFolderDto>();
		for (PatientFavFolder patientFavFolder : patientFavFolders) {
			if (patientFavFolderDto
					.getFolderSn()
					.toString()
					.equals(patientFavFolder.getParentFolderSn() == null ? ""
							: patientFavFolder.getParentFolderSn().toString())) {
				PatientFavFolderDto patinetFavFolderDto = new PatientFavFolderDto();
				patinetFavFolderDto.setFolderSn(patientFavFolder.getFolderSn());
				patinetFavFolderDto.setFolderName(patientFavFolder
						.getFolderName());
				setChildrenFolders(patientFavFolders, patinetFavFolderDto);
				patientFavFolderDtos.add(patinetFavFolderDto);
			}
		}
		patientFavFolderDto.setChildrenFolders(patientFavFolderDtos);
	}

	@RequestMapping("/list_loadpatientfav_{patientSn}")
	public ModelAndView loadPatientFav(WebRequest request, HttpSession session,
			@PathVariable("patientSn") String patientSn) {
		ModelAndView mav = new ModelAndView();
		// 得到登录用户的ID
		LoginUserDetails userDetails = (LoginUserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		String userId = userDetails.getUsername();
		String folderName = request.getParameter("folderName");
		String postbackEvent = request.getParameter("postbackEvent");
		String parentFolderSn = request.getParameter("parentFolderSn");

		if (!StringUtils.isEmpty(folderName)) {
			PatientFavFolder favFolder = new PatientFavFolder();
			favFolder.setFolderName(folderName);
			favFolder.setCreateUserId(userId);
			Date systemDate = DateUtils.getSystemTime();
			favFolder.setCreateTime(systemDate);
			favFolder.setUpdateTime(systemDate);
			favFolder.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
			favFolder.setDeleteFlag(Constants.NO_DELETE_FLAG);
			if ("create_child_folder".equals(postbackEvent)) {
				favFolder.setParentFolderSn(new BigDecimal(parentFolderSn));
			}
			patientFavFolderService.createPatientFavFolder(favFolder);
		}

		List<PatientFavFolderDto> allPatientFolderDtos = new ArrayList<PatientFavFolderDto>();

		// 得到该登录用户所有已建的文件夹
		List<PatientFavFolder> allPatientFolders = patientFavFolderService
				.selectPatientFavFolders(userId);

		// 递归创建文件夹父子关系
		for (PatientFavFolder patientFavFolder : allPatientFolders) {
			if (patientFavFolder.getParentFolderSn() == null) {
				PatientFavFolderDto patinetFavFolderDto = new PatientFavFolderDto();
				patinetFavFolderDto.setFolderSn(patientFavFolder.getFolderSn());
				patinetFavFolderDto.setFolderName(patientFavFolder
						.getFolderName());
				setChildrenFolders(allPatientFolders, patinetFavFolderDto);
				allPatientFolderDtos.add(patinetFavFolderDto);
			}
		}

		List<PatientFavFolder> patientFolders = patientFavFolderService
				.selectPatientFavFolders(userId, patientSn);

		mav.addObject("patientFolders", patientFolders);
		mav.addObject("allPatientFolderDtos", allPatientFolderDtos);
		mav.setViewName("/patient/loadPatientFolder");
		return mav;
	}

	@RequestMapping("/patientFolder_{patientSn}")
	public ModelAndView patientFolderList(WebRequest request,
			@PathVariable("patientSn") String patientSn) throws Exception {
		ModelAndView mav = new ModelAndView();

		if (StringUtils.isEmpty(patientSn))
			logger.error("患者内部序列号不能为空");

		String postbackEvent = request.getParameter("postbackEvent");
		if (StringUtils.isEmpty(postbackEvent))
			postbackEvent = "";

		if (postbackEvent.equals("save_folder")) {
			String folderIdsStr = request.getParameter("folderIds");
			String pfmemo = request.getParameter("pfmemo");
			String[] folderIds = folderIdsStr.split(",");
			List<PatientFav> patientFavs = new ArrayList<PatientFav>();
			for (String folderId : folderIds) {
				if (StringUtils.isEmpty(folderId)) {
					logger.error("患者内部序列号和文件夹内部序列号不能为空");
				}
				PatientFav patientFav = new PatientFav();
				patientFav.setFolderSn(new BigDecimal(folderId));
				patientFav.setPatientSn(new BigDecimal(patientSn));
				Date systemDate = DateUtils.getSystemTime();
				patientFav.setCreateTime(systemDate);
				patientFav.setUpdateTime(systemDate);
				patientFav.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
				patientFav.setDeleteFlag(Constants.NO_DELETE_FLAG);
				// $Author:bin_zhang
				// $Date : 2012/12/25 15:10
				// $[BUG]0012700 ADD BEGIN
				patientFav.setMemo(pfmemo);
				// $[BUG]0012700 ADD END
				patientFavs.add(patientFav);
			}
			patientFavFolderService.insertPatientFavs(patientFavs);
			mav.setViewName("/patient/afterSavedPatientFav");
		} else
			mav.setViewName("/patient/patientFolder");

		LoginUserDetails userDetails = (LoginUserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		String userId = userDetails.getUsername();

		List<PatientFavFolderDto> allPatientFolderDtos = new ArrayList<PatientFavFolderDto>();

		// 得到该登录用户所有已建的文件夹
		List<PatientFavFolder> allPatientFolders = patientFavFolderService
				.selectPatientFavFolders(userId);

		// 递归创建文件夹父子关系
		for (PatientFavFolder patientFavFolder : allPatientFolders) {
			if (patientFavFolder.getParentFolderSn() == null) {
				PatientFavFolderDto patinetFavFolderDto = new PatientFavFolderDto();
				patinetFavFolderDto.setFolderSn(patientFavFolder.getFolderSn());
				patinetFavFolderDto.setFolderName(patientFavFolder
						.getFolderName());
				setChildrenFolders(allPatientFolders, patinetFavFolderDto);
				allPatientFolderDtos.add(patinetFavFolderDto);
			}
		}

		List<PatientFavFolder> patientFolders = patientFavFolderService
				.selectPatientFavFolders(userId, patientSn);

		mav.addObject("patientFolders", patientFolders);
		mav.addObject("allPatientFolderDtos", allPatientFolderDtos);

		mav.addObject("patientSn", patientSn);
		return mav;
	}

	@RequestMapping("/patientListAdvanceSearch")
	public ModelAndView patientListAdvanceSearch(WebRequest request)
			throws Exception {
		ModelAndView mav = new ModelAndView();

		mav.setViewName("/patient/patientListAdvanceSearch");

		return mav;
	}

	// $Author:bin_zhang
	// $Date : 2012/12/07
	// $[BUG]0012293 ADD BEGIN
	@RequestMapping("/list_pfpatientlist_{folderSn}")
	public ModelAndView patientFavPatientList(WebRequest request,
			@PathVariable("folderSn") String folderSn, HttpSession session)
			throws Exception {
		ModelAndView mav = new ModelAndView();
		PagingContext pagingContext = PagingContextHolder.getPagingContext();

		// 是否登录
		String loginFlag = request.getParameter("flag");

		String postbackEvent = request.getParameter("postbackEvent");
		if (StringUtils.isEmpty(postbackEvent))
			postbackEvent = "";

		List<PatientFavDto> patients = new ArrayList<PatientFavDto>();
		if (postbackEvent.equals("search_folder")) {
			// 文件夹唯一标识
			String folderId = folderSn;
			// 姓名
			String patientName = request.getParameter("patientName");
			// 性别
			String gender = request.getParameter("gender");

			// 下拉框未选择时处理
			if (Constants.OPTION_SELECT.equalsIgnoreCase(gender)) {
				gender = null;
			}

			logger.debug("显示第{}页的数据。", pagingContext.getPageNo());

			String patientFavListRowsCntStr = AppSettings
					.getConfig(Constants.CFG_PATIENTLIST_ROWS_COUNT);
			int patientFavListRowsCnt = Integer
					.parseInt(patientFavListRowsCntStr);
			pagingContext.setRowCnt(patientFavListRowsCnt);

			// 初始化检索条件
			PatientFavListSearchPra patientFavListSearchPra = new PatientFavListSearchPra();
			if (!StringUtils.isEmpty(folderId)) {
				patientFavListSearchPra.setFolderSn(folderId);
				patientFavListSearchPra.setGenderCode(gender);
				patientFavListSearchPra.setPatientName(patientName);
				// 得到文件及中的患者信息
				patients = patientFavFolderService.selectPatientFavs(
						patientFavListSearchPra, pagingContext);
			}
			if (patients != null) {
				pagingContext.setRows(patients.toArray());
			}

			mav.addObject("folderId", folderId);
			mav.addObject("patientFavListSearchPra", patientFavListSearchPra);

			// 检索对象为空check
			Object[] rows = pagingContext.getRows();
			if (rows == null || rows.length == 0) {
				logger.debug("没有检索对象!");
				// 页面显示页面数赋值0
				pagingContext.setPageNo(0);
				pagingContext.setTotalPageCnt(0);
			}

			// 页面初始化分页页码显示列表
			PagingHelper.initPaging(pagingContext);

			mav.addObject("pagingContext", pagingContext);
			mav.addObject("patientList", patients);
		}
		// 点击删除患者
		else if ("delete_patients".equals(postbackEvent)) {
			// 删除收藏夹的患者
			String patientFavIds = request.getParameter("patientFavIds");
			if (!StringUtils.isEmpty(patientFavIds)) {
				String[] ids = patientFavIds.split(",");
				patientFavFolderService.deletePatients(ids);
			}

			// 根据条件，重新进行检索
			// 文件夹唯一标识
			String folderId = folderSn;
			// 姓名
			String patientName = request.getParameter("patientName");
			// 性别
			String gender = request.getParameter("gender");

			// 下拉框未选择时处理
			if (Constants.OPTION_SELECT.equalsIgnoreCase(gender)) {
				gender = null;
			}

			pagingContext.setPageNo(1);
			logger.debug("显示第{}页的数据。", pagingContext.getPageNo());

			String patientFavListRowsCntStr = AppSettings
					.getConfig(Constants.CFG_PATIENTLIST_ROWS_COUNT);
			int patientFavListRowsCnt = Integer
					.parseInt(patientFavListRowsCntStr);
			pagingContext.setRowCnt(patientFavListRowsCnt);

			// 初始化检索条件
			PatientFavListSearchPra patientFavListSearchPra = new PatientFavListSearchPra();
			if (!StringUtils.isEmpty(folderId)) {
				patientFavListSearchPra.setFolderSn(folderId);
				patientFavListSearchPra.setGenderCode(gender);
				patientFavListSearchPra.setPatientName(patientName);
				// 得到文件及中的患者信息
				patients = patientFavFolderService.selectPatientFavs(
						patientFavListSearchPra, pagingContext);
			}
			if (patients != null) {
				pagingContext.setRows(patients.toArray());
			}
			mav.addObject("pagingContext", pagingContext);
			mav.addObject("folderId", folderId);
			mav.addObject("patientFavListSearchPra", patientFavListSearchPra);
		}

		// 如果是删除，或者检索，查询患者其他的信息
		if ("delete_patients".equals(postbackEvent)
				|| postbackEvent.equals("search_folder")) {
			// 得到所有的患者列表sn集合
			List<String> patientIds = new ArrayList<String>();

			// 得到患者列表的empi id集合
			List<String> patientEids = new ArrayList<String>();
			for (PatientFavDto patient : patients) {
				String patientEid = patient.getPatientEid();
				if (patientEid != null
						&& StringUtils.isEmpty(patientEid.trim()) == false)
					patientEids.add(patientEid);
				patientIds.add(patient.getPatientSn());
			}

			// 得到最近的就诊信息
			List<MedicalVisit> lastVisits = new ArrayList<MedicalVisit>();
			if (patientIds.size() != 0) {
				LastVisitSearchPra searchPra = new LastVisitSearchPra();
				searchPra.setPatientIds(patientIds);
				lastVisits = visitService.selectLastMedicalVisits(searchPra);
			}

			// 得到门诊域集合
			List<String> outpatientDomains = Constants.lstDomainOutPatient();

			// 得到住院域集合
			List<String> inpatientDomains = Constants.lstDomainInPatient();

			// 根据患者的empi id集合以及域，查找患者门诊和住院的交叉索引信息
			List<PatientCrossIndexDto> pciList_out = new ArrayList();
			List<PatientCrossIndexDto> pciList_in = new ArrayList();
			if (patientEids.size() != 0) {
				// 门诊交叉索引信息
				pciList_out = patientService.selectPatientCrossIndex(
						patientEids, outpatientDomains,
						Constants.OUTPATIENT_CONDITION_TAG, null);
				// 住院交叉索引信息
				pciList_in = patientService.selectPatientCrossIndex(
						patientEids, inpatientDomains,
						Constants.INPATIENT_CONDITION_TAG, null);
			}

			for (PatientFavDto patient : patients) {
				if (patient != null) {
					String outEidTemp = "";
					String inEidTemp = "";
					patient.setCardFlag("0");
					String outpatientNoJson = "{";
					String inpatientNoJson = "{";
					int outpatientCountTemp = 0;
					int inpatientCountTemp = 0;
					patient.setAge(DateUtils.caluAge(patient.getBirthDate(),
							patient.getVisitDate()));
					// 检索患者的门诊交叉索引信息，得到患者的门诊号
					for (PatientCrossIndexDto pci : pciList_out) {
						if (pci.getPatientEid().equals(patient.getPatientEid())) {
							if (outEidTemp.equals(pci.getPatientEid())) {
								outpatientCountTemp++;
								patient.setCardFlag("1");
								outpatientNoJson += ",\'门诊号"
										+ outpatientCountTemp + "\':\'"
										+ pci.getOutpatientNo() + "\'";
								continue;
							} else {
								outpatientCountTemp = 0;
								patient.setOutpatientNo(pci.getOutpatientNo());
								outpatientNoJson += "\'门诊号\':\'"
										+ pci.getOutpatientNo() + "\'";
							}
						}
						outEidTemp = pci.getPatientEid();
					}
					outpatientNoJson += "}";
					logger.debug("获取outpatientNoJson" + outpatientNoJson);
					patient.setOutpatientNoJson(outpatientNoJson);
					// 检索患者的住院交叉索引信息，得到患者的住院号
					for (PatientCrossIndexDto pci : pciList_in) {
						if (pci.getPatientEid().equals(patient.getPatientEid())) {
							if (inEidTemp.equals(pci.getPatientEid())) {
								inpatientCountTemp++;
								patient.setCardFlag("1");
								inpatientNoJson += ",\'住院号"
										+ inpatientCountTemp + "\':\'"
										+ pci.getInpatientNo() + "\'";
								continue;
							} else {
								inpatientCountTemp = 0;
								patient.setInpatientNo(pci.getInpatientNo());
								inpatientNoJson += "\'住院号\':\'"
										+ pci.getInpatientNo() + "\'";
							}
						}
						inEidTemp = pci.getPatientEid();

					}
					inpatientNoJson += "}";
					logger.debug("获取inpatientNoJson" + inpatientNoJson);
					patient.setInpatientNoJson(inpatientNoJson);
					// 检索该患者的最近就诊信息
					for (MedicalVisit visit : lastVisits) {
						String visitPatientSn = visit.getPatientSn().toString();
						String patientSn = patient.getPatientSn();
						if (visitPatientSn.equals(patientSn))
							patient.setVisitDate(visit.getVisitDate());
					}
				}
			}
		}
		// $Author: wu_jianfeng
		// $Date : 2013/02/25
		// $[BUG]0011609 ADD BEGIN
		else {
			mav.addObject("queryFlag", "0");
		}
		// $[BUG]0011609 ADD END

		// 检索对象为空check
		Object[] rows = pagingContext.getRows();
		if (rows == null || rows.length == 0) {
			logger.debug("没有检索对象!");
			// 页面显示页面数赋值0
			pagingContext.setPageNo(0);
			pagingContext.setTotalPageCnt(0);
		}

		// 页面初始化分页页码显示列表
		PagingHelper.initPaging(pagingContext);

		// $Author:tong_meng
		// $Date : 2013/11/07 10:10
		// $[BUG]0039034 ADD BEGIN
		// 将病区去掉在界面上显示，其他不变
		StringUtils.cutString(patients, "dischargeWardName", "病区", "");
		// $[BUG]0039034 ADD END

		mav.addObject("pagingContext", pagingContext);
		mav.addObject("patientList", patients);
		// 是否登录
		mav.addObject("loginFlag", loginFlag);
		mav.setViewName("/patient/patientFavPatientList");
		return mav;
	}

	// $[BUG]0012293 ADD END

	@RequestMapping("/list_patientappoint")
	public ModelAndView patientAppointlist(WebRequest request,
			HttpSession session) throws Exception {
		PagingContext pagingContext = PagingContextHolder.getPagingContext();
		logger.debug("显示第{}页的数据。", pagingContext.getPageNo());
		ModelAndView mav = new ModelAndView();

		String patientListRowsCntStr = AppSettings
				.getConfig(Constants.CFG_PATIENTLIST_ROWS_COUNT);
		int patientListRowsCnt = Integer.parseInt(patientListRowsCntStr);
		pagingContext.setRowCnt(patientListRowsCnt);

		PatientListSearchPra patientListSearchPra = new PatientListSearchPra();

		// 获取用户对象, 设置就诊医生ID
		LoginUserDetails userDetails = (LoginUserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		String userId = userDetails.getUsername();
		patientListSearchPra.setDoctorId(userId);
		// 设置用户所属的科室
		List<String> deptIds = userDetails.getDeptIds();
		patientListSearchPra.setDeptIds(deptIds);

		String patientName = request.getParameter("outpatient_name");
		String portion = request.getParameter("portion");
		String requestStartDate = request.getParameter("requestStartDate");
		String requestEndDate = request.getParameter("requestEndDate");
		String normalPatient = request.getParameter("normalPatient");
		if (requestStartDate == null && requestEndDate == null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			requestStartDate = sdf.format(new Date());
		}
		patientListSearchPra.setRequestStartDate(requestStartDate);
		patientListSearchPra.setRequestEndDate(requestEndDate);
		patientListSearchPra.setPatientName(patientName);
		patientListSearchPra.setNormalPatient(normalPatient);

		List<PatientAppointDto> patientAppointList = new ArrayList<PatientAppointDto>();
		// 访问控制实现
		if (AccessControl.useACL()) {
			AccessControl acl = userDetails.getAccessControl();
			AccessControlDto aclAuths = acl.getAccessControlAuths();
			mav.addObject("aclAuths", aclAuths);
			// 可以访问我的患者信息，可以访问本科室门诊患者信息以及可以访问全院患者信息
			// 如果访问本科室门诊患者信息，要求所属的科室不能为空
			if (aclAuths.getPatientScopeAuth05()) {
				patientAppointList = patientService
						.selectPatientAppointByCondition(patientListSearchPra,
								aclAuths, pagingContext);
			} else {
				patientAppointList = patientService
						.selectPatientAppointByCondition(patientListSearchPra,
								pagingContext);
			}
		} else
			patientAppointList = patientService
					.selectPatientAppointByCondition(patientListSearchPra,
							pagingContext);

		if (patientAppointList != null && patientAppointList.size() != 0) {
			for (PatientAppointDto patient : patientAppointList) {
				if (patient != null) {
					patient.setAge(DateUtils.caluAge(patient.getBirthDate(),
							patient.getVisitDate()));
				}
			}
		}
		mav.addObject("patientAppointList", patientAppointList);
		if (patientAppointList != null) {
			pagingContext.setRows(patientAppointList.toArray());
		}

		mav.addObject("patientListSearchPra", patientListSearchPra);
		if (IS_PORTION.equals(portion)) {
			mav.setViewName("/patient/patientAppointDetail");
		} else {
			mav.setViewName("/patient/patientAppointList");
		}

		// 检索对象为空check
		Object[] rows = pagingContext.getRows();
		if (rows == null || rows.length == 0) {
			logger.debug("没有检索对象!");
			// 页面显示页面数赋值0
			pagingContext.setPageNo(0);
			pagingContext.setTotalPageCnt(0);
		}

		// 页面初始化分页页码显示列表
		PagingHelper.initPaging(pagingContext);
		mav.addObject("pagingContext", pagingContext);
		return mav;
	}
}
