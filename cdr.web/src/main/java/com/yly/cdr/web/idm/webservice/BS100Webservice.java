package com.yly.cdr.web.idm.webservice;

import javax.jws.WebService;

@WebService
public interface BS100Webservice {

	/**
	 * BS020医嘱撤销和停止上传服务
	 * @param headerXml
	 * @param contentXml
	 * @return
	 */
	public String sendMsgBS100Webservice(String headerXml, String contentXml);
}
