package com.founder.cdr.web.idm.webservice;

import javax.jws.WebService;

@WebService
public interface BS102Webservice {

	/**
	 * BS102住院召回信息上传服务
	 * @param headerXml
	 * @param contentXml
	 * @return
	 */
	public String sendMsgBS102Webservice(String headerXml, String contentXml);
}
