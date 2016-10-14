package com.yly.cdr.web.idm.webservice.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.cxf.frontend.ClientProxyFactoryBean;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.founder.hie.message.data.MessageModel;
import com.yly.cdr.batch.processor.MessageParserWrapper;
import com.yly.cdr.batch.processor.V2MessageProcessor;
import com.yly.cdr.entity.Message;
import com.yly.cdr.web.idm.webservice.SZBJExamApplicationWebservice;
import com.yly.cdr.web.idm.webservice.SZBJGetExamApplicationWebservice;

public class SZBJExamApplicationWebserviceImpl implements SZBJExamApplicationWebservice {
	
	private static final Logger logger = LoggerFactory.getLogger(SZBJExamApplicationWebserviceImpl.class);
	
	@Override
	public String examApplicationQuery(String conditionXml) {
		String result = "";
		try{
			//1.调用HIS的webservice，传V2参数，查询申请单信息
			ClientProxyFactoryBean factory = new ClientProxyFactoryBean();
	        factory.getOutInterceptors().add(new LoggingOutInterceptor());
	        factory.setServiceClass(SZBJGetExamApplicationWebservice.class);
	        factory.setAddress("http://localhost:8081/cdr/webservices/SZBJGetExamApplicationWebservice?WSDL");
	        SZBJGetExamApplicationWebservice client = (SZBJGetExamApplicationWebservice) factory.create();
	       
	        //2.将查询条件xml转成V2查询条件   xml->v2
	        String applyNo = "123";
	        String V2Xml = "111";
			String resultV2 = "";
			resultV2 = client.getExamApplication(V2Xml);
			
	        //3.将返回的V2的信息转成普通xml   
			//3.1 将V2 ——> map
	        Message msg = new Message();
	        msg.setContent(conditionXml.replaceAll("\n", "\r"));
	        
	        V2MessageProcessor processor = new V2MessageProcessor();
	        processor.setMessageId("omg_o19");
	        Map map = processor.getV2Map(msg);
	        
	        //3.2 将map ——> xml
	        MessageModel model = new MessageModel();
	        model.setContent(map);
	        
	        //json样例，后期读取json文件
	        StringBuffer bf1 = new StringBuffer();
	        bf1.append(" { \"patientLid\" : {").append("\n");
	        bf1.append("     \"desc\" : \"患者id\",").append("\n");
	        bf1.append("     \"path\" : \"/operationType/patient/PatientId\",").append("\n");
	        bf1.append("     \"type\" : \"string\"").append("\n");
	        bf1.append("  }}");
	        
	        //保留：读取的json文件
	        processor.setMessageId("omg_o19_xml");
	        String json = processor.getJson(msg);
	        
	        //xml样例，后期读取xml模板
	        StringBuffer bf2 = new StringBuffer();
	        bf2.append("<?xml version='1.0' encoding='UTF-8'?>").append("\n");
	        bf2.append("<operationType>").append("\n");
	        bf2.append("  <patient>").append("\n");
	        bf2.append("    <PatientId>病人ID</PatientId>").append("\n");
	        bf2.append("  </patient>").append("\n");
	        bf2.append("</operationType>").append("\n");
	        
	        String templateXml =  bf2.toString();
	        result = MessageParserWrapper.map2Xml(json, templateXml, "HL7_V3_MESSAGE", model);
		}catch(Exception e){
			
		}
		return result;
	}
	
}
