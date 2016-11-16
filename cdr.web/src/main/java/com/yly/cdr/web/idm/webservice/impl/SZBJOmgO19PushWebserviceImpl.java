package com.yly.cdr.web.idm.webservice.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yly.cdr.core.AppSettings;
import com.yly.cdr.web.idm.webservice.SZBJOmgO19PushWebservice;
import com.yly.cdr.web.util.HL7V2AndXMLHelper;
/**
 * 检查申请单推送，推送为V2消息
 * @author Administrator
 *
 */
public class SZBJOmgO19PushWebserviceImpl implements SZBJOmgO19PushWebservice {
	
	private static final Logger logger = LoggerFactory.getLogger(SZBJOmgO19PushWebserviceImpl.class);
	
	@Override
	public String examApplicationPush(String V2Content) {
		String result = "";
		String resultXml= "";
		try{
			String v2Id = "omg_o19";
			String action = "p";
			String msgType = "HL7_V3_MESSAGE";
			//1.V2检查申请单消息转化成xml    
			logger.info("\n=====检查申请单推送V2消息内容为:\n"+V2Content);
			HL7V2AndXMLHelper helper = new HL7V2AndXMLHelper();
			result = helper.buildXML(V2Content, v2Id, action,msgType);
			logger.info("\n=====检查申请单查推送件V2消息转XML后内容为:\n"+result);
			if(StringUtils.isEmpty(result)){
				resultXml = "=====检查申请单推送时V2消息转XML发生异常！";
		    	logger.error(resultXml);
		    	return resultXml;
			}else{
				//2.调用心电系统的webservice，将检查申请单xml数据传送过去，获取返回结果
				resultXml = helper.callWebservice(AppSettings.getConfig("getPushResult"), result,"XML");
			}
			logger.info("\n=====检查申请单推送后返回的XML数据内容为：\n"+resultXml);
			 
			if(StringUtils.isEmpty(resultXml)){
				resultXml = "\n=====调用心电系统的ws发生异常，返回数据为空！\n";
		    	logger.error(resultXml);
		    	return resultXml;
			}else{
				//3.将返回的xml结果转化成V2结果   
				result = helper.buildV2Message(resultXml,v2Id,action,msgType);
			}
			 logger.info("\n=====返回结果XML转V2消息内容为：\n"+result);
		    
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		return result;
	}

}
