package com.yly.cdr.web.idm.webservice.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yly.cdr.core.AppSettings;
import com.yly.cdr.web.idm.webservice.SZBJOruR01PushWebservice;
import com.yly.cdr.web.util.HL7V2AndXMLHelper;

/**
 * 检验报告推送
 * @author Administrator
 *
 */
public class SZBJOruR01PushWebserviceImpl implements SZBJOruR01PushWebservice {
	
	private static final Logger logger = LoggerFactory.getLogger(SZBJOruR01PushWebserviceImpl.class);
	
	@Override
	public String examReportPush(String xmlContent) {
		String result = "";
		try{
			String v2Id = "oru_r01";
			String action = "p";
			String msgType = "HL7_V3_MESSAGE";
			//1.将查询条件xml转成V2查询条件   xml->v2    
			HL7V2AndXMLHelper helper = new HL7V2AndXMLHelper();
		    String v2Msg = helper.buildV2Message(xmlContent,v2Id,action,msgType);
		    String resultV2 = "";
		    if(StringUtils.isEmpty(v2Msg)){
		    	logger.error("心电适配器提供的xml转V2发生异常！");
		    }else{
		    	//2.调用HIS的webservice，传V2参数，查询申请单信息
		    	resultV2 = helper.callWebservice(AppSettings.getConfig("examV2Ws"), v2Msg);
		    }
		    
		    if(StringUtils.isEmpty(resultV2)){
		    	logger.error("调用第三方系统的ws发生异常，返回数据为空！");
		    }
	        //3.将返回的V2的信息转成普通xml   
		    result = helper.buildXML(resultV2, v2Id, action, msgType);
		    
		}catch(Exception e){
			
		}
		return result;
	}

}
