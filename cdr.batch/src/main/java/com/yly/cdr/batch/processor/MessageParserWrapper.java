package com.yly.cdr.batch.processor;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.founder.hie.message.data.AbstractMessage;
import com.founder.hie.message.data.MessageModel;
import com.founder.hie.message.factory.MessageFactory;
import com.founder.hie.message.schema.MessageSchemaDefinition;


public class MessageParserWrapper {

	/**
	 * XML转换为MessageModel对象
	 * @param schemaContent
	 * @param xmlContent
	 * @param msgType
	 * @return
	 * @throws EvaluationException
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static MessageModel xml2Map(String schemaContent, String xmlContent,String msgType) {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> schema = null;
		try {
			schema = mapper.readValue(schemaContent, HashMap.class);
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
		xmlContent = xmlContent.replaceAll("[\\x00-\\x08\\x0b-\\x0c\\x0e-\\x1f]", "");
		MessageModel model = MessageFactory.parseMessage(msgType, xmlContent, msd);
		return model;
	}
	
	/**
	 * MessageModel对象转换为XML
	 * @param schemaContent
	 * @param templateContent
	 * @param msgType
	 * @param model
	 * @return
	 * @throws EvaluationException
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static String map2Xml(String schemaContent,String templateContent,String msgType,MessageModel model){
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> schema = null;
		try {
			schema = mapper.readValue(schemaContent, HashMap.class);
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
		msd.setTemplate(templateContent);
		AbstractMessage ret = MessageFactory.generateMessage(msgType, model, msd);
		return ret.getMessage().toString();
	}
}
