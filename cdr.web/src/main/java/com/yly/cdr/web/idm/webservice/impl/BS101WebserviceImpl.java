package com.yly.cdr.web.idm.webservice.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yly.cdr.web.idm.webservice.BS101Webservice;
/**
 * BS101患者医护人员信息上传服务
 * @param headerXml
 * @param contentXml
 * @return
 */
public class BS101WebserviceImpl implements BS101Webservice {
	
	private static final Logger logger = LoggerFactory.getLogger(BS101WebserviceImpl.class);

	@Override
	public String sendMsgBS101Webservice(String headerXml, String contentXml) {

		logger.debug("sendMsgBS101Webservice-BS101患者医护人员信息上传服务");
		return "1";
	}
	
}
