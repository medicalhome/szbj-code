package com.yly.cdr.util;

import com.founder.hie.message.data.HL7V3Constant;
import com.founder.hie.message.util.SchemaUtil;
import org.dom4j.Document;
import org.dom4j.Node;

import javax.xml.xpath.XPath;
import java.util.Map;

/**
 * 类描述
 * Created by on 2016/10/15.
 */

public class SimpleXMLUtil {

    public static void initMapWithSchema(Map<String,Object> rootMap,Object schemaMap,Document document){
        if(rootMap!=null && schemaMap instanceof Map) {
            for(Map.Entry<String, Object> entity : ((Map<String, Object>)schemaMap).entrySet()) {
                String key = entity.getKey();
                Map<String, Object> rule = (Map<String, Object>) entity.getValue();
                String multiplicity = (String) rule.get(HL7V3Constant.SCHEMA_KEY_MULTIPLICITY);
//                boolean hasChild = hasChild(rule);
                String xpath = (String) rule.get(HL7V3Constant.SCHEMA_KEY_PATH);
                if(SchemaUtil.isSingleNode(multiplicity)){
                    rootMap.put(key,getElementValue(document, xpath));
                }else {

                }
            }
        }
    }

    private static String getElementValue(Document doc, String xpathString){
        return doc.selectSingleNode(xpathString).getText();
    }

    private static boolean hasChild(Map<String, Object> rule){
        Map<String, Map<String, Object>> child = (Map<String, Map<String, Object>>) rule.get(HL7V3Constant.SCHEMA_KEY_CHILD);
        if(child != null){
            return true;
        }
        return false;
    }
}
