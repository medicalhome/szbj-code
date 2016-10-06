package com.yly.cdr.web.idm.webservice.impl;

import org.apache.cxf.frontend.ClientProxyFactoryBean;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yly.cdr.web.idm.webservice.ExamApplicationWebservice;
import com.yly.cdr.web.idm.webservice.GetExamApplicationWebservice;
import com.yly.commsrv.message.webservice.SendEmailWebservice;

public class ExamApplicationWebserviceImpl implements ExamApplicationWebservice {
	
	private static final Logger logger = LoggerFactory.getLogger(ExamApplicationWebserviceImpl.class);

	@Override
	public String examApplicationQuery(String conditionXml) {
		String result = "";
		try{
			//1.调用HIS的webservice，传V2参数，查询申请单信息
			ClientProxyFactoryBean factory = new ClientProxyFactoryBean();
	        factory.getOutInterceptors().add(new LoggingOutInterceptor());
	        factory.setServiceClass(SendEmailWebservice.class);
	        factory.setAddress("");
	        GetExamApplicationWebservice client = (GetExamApplicationWebservice) factory.create();
	        
	        //2.将查询条件xml转成查询条件V2
	        String applyNo = "123";
	        
	        String V2Xml = "111";
	        result = client.getExamApplication(V2Xml);
	        
	        //3.result为V2的信息，接着讲V2信息转成普通xml
	        
	        
		}catch(Exception e){
			
		}
		return result;
	}
	
}
