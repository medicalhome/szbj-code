package com.yly.cdr.web.idm.webservice;

import javax.jws.WebService;

/**
 * 检查申请单推送
 * @author yly
 *
 */
@WebService
public interface SZBJOmgO19PushWebservice {

	/**
	 * 为心电系统提供检查申请单查询
	 * @param conditionXml
	 * @return
	 */
	public String examApplicationPush(String conditionXml);
}
