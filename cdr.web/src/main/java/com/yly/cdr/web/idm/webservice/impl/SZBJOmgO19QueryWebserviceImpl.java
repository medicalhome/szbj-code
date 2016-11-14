package com.yly.cdr.web.idm.webservice.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yly.cdr.core.AppSettings;
import com.yly.cdr.web.idm.webservice.SZBJOmgO19QueryWebservice;
import com.yly.cdr.web.util.HL7V2AndXMLHelper;

public class SZBJOmgO19QueryWebserviceImpl implements SZBJOmgO19QueryWebservice {
	
	private static final Logger logger = LoggerFactory.getLogger(SZBJOmgO19QueryWebserviceImpl.class);
	
	@Override
	public String examApplicationQuery(String xmlContent) {
		String result = "";
		try{
			String v2Id = "omg_o19";
			String action = "q";
			String msgType = "HL7_V3_MESSAGE";
			//1.将查询条件xml转成V2查询条件   xml->v2    
			HL7V2AndXMLHelper helper = new HL7V2AndXMLHelper();
		    String v2Msg = helper.buildV2Message(xmlContent,v2Id,action,msgType);
		    
			//2.调用HIS的webservice，传V2参数，查询申请单信息
		    String resultV2 = helper.callWebservice(AppSettings.getConfig("getPushResult"), v2Msg,"V2");
			
	        //3.将返回的V2的信息转成普通xml   
		    result = helper.buildXML(resultV2, v2Id,action,msgType);
		    
		}catch(Exception e){
			
		}
		return result;
	}
	
}
