package com.yly.cdr.web.idm.webservice;

import javax.jws.WebService;

@WebService
public interface SZBJExamApplicationWebservice {

	/**
	 * 为心电系统提供检查申请单查询
	 * @param conditionXml
	 * @return
	 */
	public String examApplicationQuery(String conditionXml);
}
