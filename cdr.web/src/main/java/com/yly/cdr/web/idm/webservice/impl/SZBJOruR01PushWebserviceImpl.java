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
			logger.info("\n=====检查报告推送xml内容为:\n"+xmlContent);
			HL7V2AndXMLHelper helper = new HL7V2AndXMLHelper();
		    String v2Msg = helper.buildV2Message(xmlContent,v2Id,action,msgType);
		    String resultV2 = "";
	    	logger.info("\n=====xml转V2消息后内容为:\n"+v2Msg);
		    if(StringUtils.isEmpty(v2Msg)){
		    	resultV2 = "=====心电适配器提供的xml转V2发生异常！";
		    	logger.error(resultV2);
		    	return resultV2;
		    }else{
		    	//2.调用HIS的webservice，传V2参数，查询申请单信息
		    	resultV2 = helper.callWebservice(AppSettings.getConfig("getPushResult"), v2Msg,"V2");
		    }
		    logger.info("\n=====报告推送后返回的结果V2消息内容为：\n"+resultV2);
		    if(StringUtils.isEmpty(resultV2)){
		    	resultV2 = "\n=====调用第三方系统的ws发生异常，返回数据为空！\n";
		    	logger.error(resultV2);
		    	return resultV2;
		    }else{
		    	 //3.将返回的V2的信息转成普通xml   
			    result = helper.buildXML(resultV2, v2Id, action, msgType);
		    }
		    logger.info("\n=====报告推送后返回的结果V2消息转xml内容为：\n"+result);
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		return result;
	}

}
