package com.yly.cdr.web.idm.webservice;

import javax.jws.WebService;

/**
 * 检查报告推送
 * @author yly
 *
 */
@WebService
public interface SZBJOruR01PushWebservice {

	/**
	 * 为心电系统提供检查报告推送
	 * @param conditionXml
	 * @return
	 */
	public String examReportPush(String conditionXml);
}
