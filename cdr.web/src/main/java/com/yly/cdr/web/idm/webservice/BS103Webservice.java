package com.yly.cdr.web.idm.webservice;

import javax.jws.WebService;

@WebService
public interface BS103Webservice {

	/**
	 * BS103退号信息上传服务
	 * @param headerXml
	 * @param contentXml
	 * @return
	 */
	public String sendMsgBS103Webservice(String headerXml, String contentXml);
}
