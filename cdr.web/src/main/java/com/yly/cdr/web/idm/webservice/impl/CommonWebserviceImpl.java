package com.yly.cdr.web.idm.webservice.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yly.cdr.web.idm.webservice.CommonWebservice;
/**
 * 江苏公用WS服务
 * @param headerXml
 * @param contentXml
 * @return
 */
public class CommonWebserviceImpl implements CommonWebservice {
	
	private static final Logger logger = LoggerFactory.getLogger(CommonWebserviceImpl.class);

	@Override
	public String putMsg(String headerXml, String contentXml) {

		logger.debug("江苏公用ws服务！");
		return "1";
	}
	
}
