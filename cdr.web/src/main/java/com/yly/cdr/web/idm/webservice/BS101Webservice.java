package com.yly.cdr.web.idm.webservice;

import javax.jws.WebService;

@WebService
public interface BS101Webservice {

	/**
	 * BS101患者医护人员信息上传服务
	 * @param headerXml
	 * @param contentXml
	 * @return
	 */
	public String sendMsgBS101Webservice(String headerXml, String contentXml);
}
