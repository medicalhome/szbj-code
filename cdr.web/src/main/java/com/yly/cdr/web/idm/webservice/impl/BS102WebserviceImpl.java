package com.yly.cdr.web.idm.webservice.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yly.cdr.web.idm.webservice.BS102Webservice;
/**
 * BS102住院召回信息上传服务
 * @param headerXml
 * @param contentXml
 * @return
 */
public class BS102WebserviceImpl implements BS102Webservice {
	
	private static final Logger logger = LoggerFactory.getLogger(BS102WebserviceImpl.class);

	@Override
	public String sendMsgBS102Webservice(String headerXml, String contentXml) {

		logger.debug("sendMsgBS102Webservice-BS102出院召回信息服务");
		return "1";
	}
	
}
