/**
 * 公共控制类，用于跳转到公共的界面
 * 
 */
package com.yly.cdr.web;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.yly.cdr.core.AppSettings;
import com.yly.cdr.core.Constants;

/**
 * 
 * @author tong_meng
 * 
 */
@Controller
@RequestMapping("/common")
public class CommonController {
	private static Logger logger = LoggerFactory
			.getLogger(CommonController.class);

	/**
	 * 多个tab页时，跳转到公共的tab页
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/commonTab")
	public ModelAndView operationList(WebRequest request) {
		String width = request.getParameter("width");
		ModelAndView mav = new ModelAndView();
		mav.addObject("width", width);
		mav.setViewName("/commonTab");
		return mav;
	}

	/**
	 * 医嘱闭环 江苏人民医院 参数增加就诊类型编码
	 * 
	 * @param patientDomain
	 *            域ID
	 * @param orderType
	 *            （住院）医嘱类型
	 * @param orderCode
	 *            （住院）医嘱编码
	 * @param orderLid
	 *            （住院）[参数]医嘱号
	 * @param visitTypeCode
	 *            （门诊）对应的就诊类别
	 * @param routeCode
	 *            （门诊）用药途径
	 * @param type
	 *            （门诊）[参数]类型 1：输血 2：输液
	 * @param uniqueCode
	 *            （门诊）[参数]输血对应取血单号，输液对应处方号
	 * @param mav
	 * @return
	 * @throws Exception
	 */
	public Map<Object, Object> showClosedCycle(String patientDomain,
			Map<String, String> zyMap, Map<String, String> mzMap,
			ModelAndView mav, String visitTypeCode) throws Exception {
		Map<Object, Object> resultMap = new HashMap<Object, Object>();
		// 读取配置文件closedCycle.properties,某些指定医嘱类型才能看到"医嘱跟踪"的链接
		boolean showClosedCycle = false;
		/*
		 * Properties prop = new Properties(); prop.load(new
		 * ClassPathResource("closedCycle.properties").getInputStream());
		 */

		Map<String, String> otMap = new HashMap<String, String>();
		Map<String, String> rcMap = new HashMap<String, String>();
		Map<String, String> ocMap = new HashMap<String, String>();
		// 医嘱类型编码
		String orderType = AppSettings.getConfig("orderType");
		String[] otArr = orderType.split(",");
		for (int i = 0; i < otArr.length; i++) {
			otMap.put(otArr[i], otArr[i]);
		}
		// 医嘱编码
		String orderCode = AppSettings.getConfig("orderCode");
		String[] oiArr = orderCode.split(",");
		for (int j = 0; j < oiArr.length; j++) {
			String[] sArr = oiArr[j].split("@");
			ocMap.put(sArr[0], sArr[1]);
		}
		// 用药途径
		String routeCode = AppSettings.getConfig("routeCode");
		String[] rcArr = routeCode.split(",");
		for (int k = 0; k < rcArr.length; k++) {
			rcMap.put(rcArr[k], rcArr[k]);
		}

		String linkUrl = null;
		// 优先判断是否为住院
		// if("02".equalsIgnoreCase(patientDomain)){
		if (Constants.lstDomainInPatient().contains(patientDomain)
				&& Constants.lstVisitTypeInPatient().contains(visitTypeCode)) {
			if (zyMap.containsKey("bloodReOrResult")) {
				showClosedCycle = true;
				linkUrl = AppSettings.getConfig("zyLinkUrl").replace("{param}",
						zyMap.get("orderLid"));
			} else {
				// 其他医嘱(非药物性医嘱)，在配置文件所配置的范围内
				if (otMap.containsKey(zyMap.get("orderType"))) {
					// 1. 需要特殊处理的，有特定医嘱编码
					if (ocMap.containsKey(zyMap.get("orderType"))) {
						if (ocMap.get(zyMap.get("orderType")).contains(
								zyMap.get("orderCode"))) {
							showClosedCycle = true;
						}
					}
					// 2. 整体不需要特殊特定医嘱编码
					else {
						showClosedCycle = true;
					}
					linkUrl = AppSettings.getConfig("zyLinkUrl").replace(
							"{param}", zyMap.get("orderLid"));
					// linkUrl =
					// "http://10.8.4.215/order/?orderno"+zyMap.get("orderLid");

				}
			}
		}
		// 门诊中的急诊才涉及闭环链接，且用药途径要包含在所列范围内
		// else
		// if("01".equalsIgnoreCase(patientDomain)&&"02".equalsIgnoreCase(mzMap.get("visitTypeCode"))){
		// Author: duxiaolan
        // Date : 2014/11/21
        // jiangsu
		else if (Constants.lstDomainOutPatient().contains(patientDomain)
				&& Constants.lstVisitTypeInPatient().contains(visitTypeCode)
				&& Constants.VISIT_TYPE_CODE_EMERGENCY.equals(visitTypeCode)) {
			if ("1".equalsIgnoreCase(mzMap.get("type"))) { // //输血类型
				showClosedCycle = true;
			} else if ("2".equalsIgnoreCase(mzMap.get("type"))
					&& rcMap.containsKey(mzMap.get("routeCode"))) { // 输液类型且必须包含在所列范围内
				showClosedCycle = true;
			}
			linkUrl = AppSettings.getConfig("mzLinkUrl")
					.replace("{param1}", mzMap.get("type"))
					.replace("{param2}", mzMap.get("uniqueCode"));
			// linkUrl =
			// "http://10.8.4.215:5555/order/?type="+mzMap.get("type")+"&uniquecode="+mzMap.get("uniqueCode");
		}
		resultMap.put("showClosedCycle", showClosedCycle);
		resultMap.put("linkUrl", linkUrl);
		return resultMap;
	}
	
//	/**
//	 * 医嘱闭环 北大人民医院
//	 * 
//	 * @param patientDomain
//	 *            域ID，01：门诊 02：住院
//	 * @param orderType
//	 *            （住院）医嘱类型
//	 * @param orderCode
//	 *            （住院）医嘱编码
//	 * @param orderLid
//	 *            （住院）[参数]医嘱号
//	 * @param visitTypeCode
//	 *            （门诊）对应的就诊类别
//	 * @param routeCode
//	 *            （门诊）用药途径
//	 * @param type
//	 *            （门诊）[参数]类型 1：输血 2：输液
//	 * @param uniqueCode
//	 *            （门诊）[参数]输血对应取血单号，输液对应处方号
//	 * @param mav
//	 * @return
//	 * @throws Exception
//	 */
//	public Map<Object, Object> showClosedCycle(String patientDomain,
//			Map<String, String> zyMap, Map<String, String> mzMap,
//			ModelAndView mav) throws Exception {
//		Map<Object, Object> resultMap = new HashMap<Object, Object>();
//		// 读取配置文件closedCycle.properties,某些指定医嘱类型才能看到"医嘱跟踪"的链接
//		boolean showClosedCycle = false;
//		/*
//		 * Properties prop = new Properties(); prop.load(new
//		 * ClassPathResource("closedCycle.properties").getInputStream());
//		 */
//
//		Map<String, String> otMap = new HashMap<String, String>();
//		Map<String, String> rcMap = new HashMap<String, String>();
//		Map<String, String> ocMap = new HashMap<String, String>();
//		// 医嘱类型编码
//		String orderType = AppSettings.getConfig("orderType");
//		String[] otArr = orderType.split(",");
//		for (int i = 0; i < otArr.length; i++) {
//			otMap.put(otArr[i], otArr[i]);
//		}
//		// 医嘱编码
//		String orderCode = AppSettings.getConfig("orderCode");
//		String[] oiArr = orderCode.split(",");
//		for (int j = 0; j < oiArr.length; j++) {
//			String[] sArr = oiArr[j].split("@");
//			ocMap.put(sArr[0], sArr[1]);
//		}
//		// 用药途径
//		String routeCode = AppSettings.getConfig("routeCode");
//		String[] rcArr = routeCode.split(",");
//		for (int k = 0; k < rcArr.length; k++) {
//			rcMap.put(rcArr[k], rcArr[k]);
//		}
//
//		String linkUrl = null;
//		// 优先判断是否为住院
//		if("02".equalsIgnoreCase(patientDomain)){
//		
//			if (zyMap.containsKey("bloodReOrResult")) {
//				showClosedCycle = true;
//				linkUrl = AppSettings.getConfig("zyLinkUrl").replace("{param}",
//						zyMap.get("orderLid"));
//			} else {
//				// 其他医嘱(非药物性医嘱)，在配置文件所配置的范围内
//				if (otMap.containsKey(zyMap.get("orderType"))) {
//					// 1. 需要特殊处理的，有特定医嘱编码
//					if (ocMap.containsKey(zyMap.get("orderType"))) {
//						if (ocMap.get(zyMap.get("orderType")).contains(
//								zyMap.get("orderCode"))) {
//							showClosedCycle = true;
//						}
//					}
//					// 2. 整体不需要特殊特定医嘱编码
//					else {
//						showClosedCycle = true;
//					}
//					linkUrl = AppSettings.getConfig("zyLinkUrl").replace(
//							"{param}", zyMap.get("orderLid"));
//					// linkUrl =
//					// "http://10.8.4.215/order/?orderno"+zyMap.get("orderLid");
//
//				}
//			}
//		}
//		// 门诊中的急诊才涉及闭环链接，且用药途径要包含在所列范围内
//
//		else  if("01".equalsIgnoreCase(patientDomain)&&"02".equalsIgnoreCase(mzMap.get("visitTypeCode"))){
//			if ("1".equalsIgnoreCase(mzMap.get("type"))) { // //输血类型
//				showClosedCycle = true;
//			} else if ("2".equalsIgnoreCase(mzMap.get("type"))
//					&& rcMap.containsKey(mzMap.get("routeCode"))) { // 输液类型且必须包含在所列范围内
//				showClosedCycle = true;
//			}
//			linkUrl = AppSettings.getConfig("mzLinkUrl")
//					.replace("{param1}", mzMap.get("type"))
//					.replace("{param2}", mzMap.get("uniqueCode"));
//			// linkUrl =
//			// "http://10.8.4.215:5555/order/?type="+mzMap.get("type")+"&uniquecode="+mzMap.get("uniqueCode");
//		}
//		resultMap.put("showClosedCycle", showClosedCycle);
//		resultMap.put("linkUrl", linkUrl);
//		return resultMap;
//	}
}
