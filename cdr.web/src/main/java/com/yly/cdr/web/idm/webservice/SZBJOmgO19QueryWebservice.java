package com.yly.cdr.web.idm.webservice;

import javax.jws.WebService;

/**
 * 检查申请单查询
 * @author yly
 *
 */
@WebService
public interface SZBJOmgO19QueryWebservice {

	/**
	 * 为心电系统提供检查申请单查询
	 * @param conditionXml
	 * @return
	 */
	public String examApplicationQuery(String conditionXml);
}
