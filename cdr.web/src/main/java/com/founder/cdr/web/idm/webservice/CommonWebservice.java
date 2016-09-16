package com.founder.cdr.web.idm.webservice;

import javax.jws.WebService;

@WebService
public interface CommonWebservice {

	/**
	 * 江苏公用WS服务
	 * @param headerXml
	 * @param contentXml
	 * @return
	 */
	public String putMsg(String headerXml, String contentXml);
}
