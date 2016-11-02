package com.yly.cdr.web.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.cxf.frontend.ClientProxyFactoryBean;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.founder.hie.message.data.MessageModel;
import com.founder.hie.message.factory.MessageFactory;
import com.founder.hie.message.schema.MessageSchemaDefinition;
import com.yly.cdr.batch.processor.HL7V2MessageHandle;
import com.yly.cdr.batch.processor.MessageParserWrapper;
import com.yly.cdr.util.StringUtils;
import com.yly.cdr.web.idm.webservice.SZBJGetExamApplicationWebservice;

public class HL7V2AndXMLHelper {
	ClassLoader classLoader = getClass().getClassLoader();
	
	public String buildV2Message(String xmlContent, String v2Id, String action, String msgType) throws Exception{
		//1. xml ——> map
		String serviceId = File.separator + v2Id + "_" + action;
		String xmlJson = "szbjMessages" + serviceId + serviceId + "_xml.json";
		String schemaContent = loadFile(classLoader.getResource(xmlJson));
		MessageModel messageModel = MessageParserWrapper.xml2Map(schemaContent,xmlContent,msgType);
		
		//2. map ——> V2
		String V2Json = "szbjMessages" + serviceId +serviceId + "_v2.json";
		String v2SchemaContent = loadFile(classLoader.getResource(V2Json));
        HL7V2MessageHandle hl7V2MessageHandle = new HL7V2MessageHandle();
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> v2schema = mapper.readValue(v2SchemaContent, HashMap.class);
        MessageSchemaDefinition msd = new MessageSchemaDefinition();
        msd.setSchema(v2schema);
        msd.setMessageType(v2Id.toUpperCase());
        msd.setVersionNumber("2.4");
        return hl7V2MessageHandle.buildV2Message(messageModel, msd);
	}
	
	public String callWebservice(String url,String params) throws Exception{
		ClientProxyFactoryBean factory = new ClientProxyFactoryBean();
        factory.getOutInterceptors().add(new LoggingOutInterceptor());
        factory.setServiceClass(SZBJGetExamApplicationWebservice.class);
        factory.setAddress(url);
        SZBJGetExamApplicationWebservice client = (SZBJGetExamApplicationWebservice) factory.create();
        return client.getExamApplication(params);
	}
	
	public String buildXML(String v2Content, String v2Id,String msgType) throws Exception{
		//1. V2 ——> map
        ObjectMapper mapper = new ObjectMapper();
        String V2Json = "szbjMessages" + File.separator + v2Id + File.separator + v2Id + "_v2.json";
    	String jsonContent = loadFile(classLoader.getResource(V2Json));
    	if(StringUtils.isEmpty(jsonContent)){
    		//logger.error("V2消息配置文件加载失败。。。。。。");
    		throw new NullPointerException("V2配置文件未找到。。。。。。");
    	}
		Map schema = null;
		try {
			schema = mapper.readValue(jsonContent, HashMap.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MessageSchemaDefinition msd = new MessageSchemaDefinition();
		msd.setSchema(schema);
		MessageModel model = MessageFactory.parseMessage("HL7_V2_MESSAGE", v2Content,msd);
		if(model==null || model.getContent().size()<1){
    		//logger.error("V2消息解析失败。。。。。。");
    		throw new NullPointerException("V2消息解析失败。。。。。。");
    	}
        
		//2. map ——> xml
		String xmlJson = "szbjMessages" + File.separator + v2Id + File.separator + v2Id + "_xml.json";
		String templateXml = "szbjMessages" + File.separator + v2Id + File.separator + v2Id + ".xml";
        return MessageParserWrapper.map2Xml(xmlJson, templateXml, "HL7_V3_MESSAGE", model);
	}
	
	private static String loadFile(URL url) throws Exception {
        if (url == null)
            return null;
        InputStream is = url.openStream();
        if (is == null) {
            return null;
        } else {
            String result = loadStream(is);
            is.close();
            return result;
        }
    }
	
    private static String loadStream(InputStream is) throws Exception {
        BufferedInputStream bis = new BufferedInputStream(is);
        InputStreamReader isr = new InputStreamReader(bis, "UTF-8");
        char buffer[] = new char[4096];
        StringBuilder sb = new StringBuilder();
        int size = 0;
        do {
            size = isr.read(buffer);
            if (size >= 0) {
                sb.append(buffer, 0, size);
            } else {
                isr.close();
                bis.close();
                return sb.toString();
            }
        } while (true);
    }
    
    
}
