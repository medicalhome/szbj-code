package com.yly.cdr.web.idm.webservice;

import javax.jws.WebService;

@WebService
public interface SZBJGetPushResultWebservice {

	/**
	 * 模拟调用HIS系统的webservice，获取申请单信息
	 * @param conditionXml
	 * @return
	 */
	public String getV2Result(String V2Content);
	
	public String getXmlResult(String XmlContent);
	
	public String getExamApplication(String V2Condition);
}
