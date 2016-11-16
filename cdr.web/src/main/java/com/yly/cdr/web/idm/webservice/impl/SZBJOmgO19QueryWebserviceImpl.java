package com.yly.cdr.web.idm.webservice.impl;

import org.apache.commons.lang3.StringUtils;
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
			logger.info("\n=====检查申请单查询xml条件内容为:\n"+xmlContent);
			HL7V2AndXMLHelper helper = new HL7V2AndXMLHelper();
		    String v2Msg = helper.buildV2Message(xmlContent,v2Id,action,msgType);
		    
		    v2Msg = v2Msg.replaceAll("\\\\S\\\\", "^");
		    logger.info("\n=====检查申请单查询条件xml转V2消息后内容为:\n"+v2Msg);
			//2.调用HIS的webservice，传V2参数，查询申请单信息
		    String resultV2 = "";
		    if(StringUtils.isEmpty(v2Msg)){
		    	resultV2 = "=====检查申请单查询条件xml转V2发生异常！";
		    	logger.error(resultV2);
		    	return resultV2;
		    }else{
		    	resultV2 = helper.callWebservice(AppSettings.getConfig("getPushResult"), v2Msg,"examApplication");
		    }
		    
		    logger.info("\n=====检查申请单查询后返回的申请单V2消息内容为：\n"+resultV2);
		    if(StringUtils.isEmpty(resultV2)){
		    	resultV2 = "\n=====调用第三方系统的ws发生异常，返回数据为空！\n";
		    	logger.error(resultV2);
		    	return resultV2;
		    }else{
		    	 //3.将返回的V2的信息转成普通xml   
			    result = helper.buildXML(resultV2, v2Id,action,msgType);
		    }
		    logger.info("\n=====返回的申请单V2消息转xml内容为：\n"+result);
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		return result;
	}
	
}
