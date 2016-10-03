package com.yly.cdr.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.yly.cdr.service.PortalService;

/**
 * 用于EMR3.0等 共通方法分发
 * 
 * @author wang.meng
 *
 */
@Controller
@RequestMapping("/cdr")
public class PortalController {

	 	@Autowired
	 	private PortalExamController portalExamController;
	 	
	 	@Autowired
	 	private PortalLabController portalLabController;
	 	
		@Autowired
	 	private PortalDocController portalDocController;
		
		@Autowired
	 	private PortalDrugOrderController portalDrugOrderController;
		
		@Autowired
	 	private PortalNondrugOrderController portalNondrugOrderController;
	 
		@Autowired
		private TemperatureController temperatureController;
	 	/*@Autowired 
	 	private InfusionController infusionController;*/
	    
	    @Autowired
	    private PortalService portalService;

	    
    @RequestMapping("/portal")
	public ModelAndView list(WebRequest request, HttpSession session)
			throws Exception {
    	ModelAndView mav = new ModelAndView();
		String viewId = request.getParameter("vi");
		String patientSn = request.getParameter("ps");
//		String styleId = request.getParameter("styleId");
		mav.addObject("patientSn", patientSn);
		/**
		 * V001:检查，V002:检验，V003:微生物检验，V004:手麻加访视（BS327, BS329, BS379, BS401），V005:病历文书所有，
		 * V006:药品医嘱，V007:非药品医嘱，V008:体温单,V009: 输液量统计
		 */
		if("V001" .equals(viewId)){
			return portalExamController.examList(request, patientSn,session);
		}
		if("V002" .equals(viewId)){
			return portalLabController.list(request, patientSn,session);
		}
		if("V003" .equals(viewId)){
			return portalLabController.list(request, patientSn,session);
		}
		if("V004" .equals(viewId)){
			return portalDocController.list(request, patientSn,session);
		}
		if("V005" .equals(viewId)){
			return portalDocController.list(request, patientSn,session);
		}
		if("V006" .equals(viewId)){
			return portalDrugOrderController.list(request, patientSn,session);
		}
		if("V007" .equals(viewId)){
			return portalNondrugOrderController.list( patientSn,request,session);
		}
		if("V008" .equals(viewId)){
			return temperatureController.list(request,session,patientSn);
		}
		/*if("V009" .equals(viewId)){
			return infusionController.list(request, patientSn,session);
		}*/
		return mav;
    }
}
