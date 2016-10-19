package com.yly.cdr.processor;

import com.alibaba.fastjson.JSON;
import com.founder.hie.message.data.MessageModel;
import com.founder.hie.message.schema.MessageSchemaDefinition;
import com.founder.hie.rce.util.FileUtils;
import com.google.common.collect.Maps;
import com.yly.cdr.batch.processor.HL7V2MessageHandle;
import com.yly.cdr.batch.processor.MessageParserWrapper;
import com.yly.cdr.util.SimpleXMLUtil;
import org.codehaus.jackson.map.ObjectMapper;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.junit.Test;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * 类描述
 * Created by  on 2016/10/15.
 */

public class HL7V2MessageHandleTest {
    @Test
    public void buildV2MessageTest() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        String schemaContent = loadFile(classLoader.getResource("omg_o19/omg_o19_xml.json"));

        //xml -> map
        String xmlContent =  loadFile(classLoader.getResource("omg_o19/omg_o19.xml"));
        String msgType = "HL7_V3_MESSAGE";
        MessageModel messageModel = MessageParserWrapper.xml2Map(schemaContent,xmlContent,msgType);
        System.out.println(messageModel.getContent().size());
        System.out.println(JSON.toJSONString(messageModel));

        //map -> v2
        String v2SchemaContent = loadFile(classLoader.getResource("omg_o19/omg_o19_v2.json"));
        HL7V2MessageHandle hl7V2MessageHandle = new HL7V2MessageHandle();
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> v2schema = mapper.readValue(v2SchemaContent, HashMap.class);
        MessageSchemaDefinition msd = new MessageSchemaDefinition();
        msd.setSchema(v2schema);
        msd.setMessageType("OMG_O19");
        msd.setVersionNumber("2.4");
        FileUtils.writeToFile(hl7V2MessageHandle.buildV2Message(messageModel, msd), new File("/a.txt"));
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

    @Test
    public void testXml() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        String schemaContent = /*"";//*/loadFile(classLoader.getResource("omg_o19/omg_o19_xml.json"));
        String xmlContent = loadFile(classLoader.getResource("omg_o19/omg_o19.xml"));
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> schemaMap = mapper.readValue(schemaContent, HashMap.class);
        Map<String,Object> rootMap = Maps.newHashMap();
        Document doc = DocumentHelper.parseText(xmlContent);
        SimpleXMLUtil.initMapWithSchema(rootMap,schemaMap,doc);
    }
}
