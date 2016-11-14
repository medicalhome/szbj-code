package com.yly.cdr.web.idm.webservice;

import javax.jws.WebService;

@WebService
public interface SZBJGetPushXmlResultWebservice {

	/**
	 * 模拟调用HIS系统的webservice，获取申请单信息
	 * @param conditionXml
	 * @return
	 */
	public String getXmlResult(String xmlContent);
}
