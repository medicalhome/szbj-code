package com.yly.cdr.web.idm.webservice.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yly.cdr.web.idm.webservice.BS100Webservice;
/**
 * BS100医嘱撤销和停止上传服务
 * @param headerXml
 * @param contentXml
 * @return
 */
public class BS100WebserviceImpl implements BS100Webservice {
	
	private static final Logger logger = LoggerFactory.getLogger(BS100WebserviceImpl.class);

	@Override
	public String sendMsgBS100Webservice(String headerXml, String contentXml) {

		logger.debug("sendMsgBS100Webservice-BS100医嘱撤销和停止上传服务");
		return "1";
	}
	
}
