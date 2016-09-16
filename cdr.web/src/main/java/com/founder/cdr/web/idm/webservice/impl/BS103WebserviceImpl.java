package com.founder.cdr.web.idm.webservice.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.founder.cdr.web.idm.webservice.BS103Webservice;
/**
 * BS103退号信息上传服务
 * @param headerXml
 * @param contentXml
 * @return
 */
public class BS103WebserviceImpl implements BS103Webservice {
	
	private static final Logger logger = LoggerFactory.getLogger(BS103WebserviceImpl.class);

	@Override
	public String sendMsgBS103Webservice(String headerXml, String contentXml) {

		logger.debug("sendMsgBS103Webservice-BS103退号信息上传服务");
		return "1";
	}
	
}
