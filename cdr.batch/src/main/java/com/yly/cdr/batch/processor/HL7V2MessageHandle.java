package com.yly.cdr.batch.processor;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.parser.CustomModelClassFactory;
import ca.uhn.hl7v2.parser.ModelClassFactory;
import ca.uhn.hl7v2.util.ReflectionUtil;
import ca.uhn.hl7v2.util.Terser;
import com.founder.hie.message.data.HL7V3Constant;
import com.founder.hie.message.data.MessageModel;
import com.founder.hie.message.exception.MessageException;
import com.founder.hie.message.factory.ValueHandlerFactory;
import com.founder.hie.message.handler.IValueHandler;
import com.founder.hie.message.schema.MessageSchemaDefinition;
import com.founder.hie.message.util.SchemaUtil;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 类描述
 * Created by masong1 on 2016/10/12.
 */

public class HL7V2MessageHandle {
    private static final Logger logger = LoggerFactory.getLogger(HL7V2MessageHandle.class);

    public String buildV2Message(MessageModel message, MessageSchemaDefinition msd){
        if(message != null && msd != null && msd.getMessageType() != null && msd.getVersionNumber() != null){
            try {
                ModelClassFactory modelClassFactory = new CustomModelClassFactory();
                Class<? extends Message> messageClass = modelClassFactory.getMessageClass(msd.getMessageType(), msd.getVersionNumber(), false);
                Message messageTerser = ReflectionUtil.instantiateMessage(messageClass, modelClassFactory);
                //build start
                Terser terser = new Terser(messageTerser);
                terser.set("MSH-1", "|");
                terser.set("MSH-2", "^~\\&");
                terser.set("MSH-3", msd.getSenderApplication());
                terser.set("MSH-4", msd.getSenderFacility());
                terser.set("MSH-5", msd.getReceiverApplication());
                terser.set("MSH-6", msd.getReceiverFacility());
                Date date = new Date();
                SimpleDateFormat datefromat = new SimpleDateFormat("yyyyMMddHHmmss");
                terser.set("MSH-7", datefromat.format(date));
                terser.set("MSH-8", "");
                terser.set("MSH-9", msd.getMessageType());
                terser.set("MSH-12", msd.getVersionNumber());
                Map<String, Object> contentMap = message.getContent();
                Map<String, Object> schemaMap = msd.getSchema();
                if(contentMap == null || schemaMap == null){
                    return null;
                }
                fillNode(messageTerser, schemaMap, contentMap);
                return messageTerser.encode();
            } catch (HL7Exception e) {
                logger.error("初始化HL7出错,原c因可能是消息类型和版本号不对应", msd.getMessageType() + msd.getVersionNumber(), e.toString());
            } catch (Exception e) {
                logger.error("An error has occurred when build V2Message .",e);
            }
        }
        return null;
    }

    private boolean fillNode(Message messageTerser, Object schemaMap, Object contentMap) throws MessageException {
        if(messageTerser != null && schemaMap instanceof Map && contentMap instanceof Map){
            String key = null;
            Object value = null;
            for(Map.Entry<String, Object> entity : ((Map<String, Object>)schemaMap).entrySet()) {
                key = entity.getKey();
                value = ((Map<String, Object>) contentMap).get(key);
                Map<String, Object> rule = (Map<String, Object>) entity.getValue();
                processWritingSchema(messageTerser, rule, value);
            }
            return true;
        }
        return false;
    }

    private void processWritingSchema(Message messageTerser, Map<String, Object> rule, Object value) throws MessageException {
        if(messageTerser != null && value != null){
            Map<String, Map<String, Object>> child = (Map<String, Map<String, Object>>) rule.get(HL7V3Constant.SCHEMA_KEY_CHILD);
            String multiplicity = (String) rule.get(HL7V3Constant.SCHEMA_KEY_MULTIPLICITY);
            String path = (String) rule.get(HL7V3Constant.SCHEMA_KEY_PATH);
            boolean hasChild = hasChild(rule);
            if(SchemaUtil.isSingleNode(multiplicity)){
                Object valuee = null;
                if(value instanceof Collection){
                    Object[] contentList = ((Collection<?>) value).toArray();
                    if(contentList.length > 0){
                        valuee = contentList[0];
                    }
                } else{
                    valuee = value;
                }
                if(valuee != null){
                    if(hasChild){
                        fillNode(messageTerser, child, valuee);
                    }
                    setNodeValue(messageTerser, valuee, rule, 0);
                }
            } else{
                if(value instanceof Collection){
                    Object[] contentList = ((Collection<?>) value).toArray();
                    int contentCountLength = contentList.length;
                    for(int i = 0; i < contentCountLength; i++){
                        if(hasChild){
                            fillNodee(messageTerser, child, contentList[i], path, i);
                        } else{
                            setNodeValue(messageTerser, contentList[i], rule, i);
                        }
                    }
                } else{
                    logger.error("path:[" + path + "]. Can not get content from message:[" + value + "]");
                }
            }
        }
    }

    private boolean fillNodee(Message messageTerser, Object schemaJson, Object contentMap, String pathPartent, int i) throws MessageException {
        if(messageTerser != null && schemaJson instanceof Map && contentMap instanceof Map){
            String key = null;
            Object value = null;
            for(Map.Entry<String, Object> entity : ((Map<String, Object>) schemaJson).entrySet()){
                key = entity.getKey();
                value = ((Map<String, Object>) contentMap).get(key);
                Map<String, Object> rule = (Map<String, Object>) entity.getValue();
                String path = rule.get(HL7V3Constant.SCHEMA_KEY_PATH).toString();
                /*if(!isRightPath(path) || !isRightPath(pathPartent)){
                    return false;
                }
                String[] pathPartentArray = pathPartent.replaceFirst("/", "").split("/");
                String[] pathArray = path.replaceFirst("/", "").split("/");
                int ii = 0;
                for(int k = 0; k < pathPartentArray.length; k++){
                    if(pathPartentArray[k].contains("(")){
                        pathPartentArray[k] = pathPartentArray[k].toString().split("\\(")[0];
                    }
                    if(pathArray[k].contains("(")){
                        String[] chilePathSplits = pathArray[k].split("\\(");
                        if(pathPartentArray[k].equals(chilePathSplits[0])){
                            ii++;
                        }
                    } else{
                        if(pathPartentArray[k].equals(pathArray[k])){
                            ii++;
                        }
                    }
                }
                if(!pathPartentArray[pathPartentArray.length - 1].contains("(")){
                    pathPartent = pathPartent + "(" + i + ")";
                }*/
                path = path.replace("#",i+"");
//                if(ii == pathPartentArray.length){
//                    rule.remove(HL7V3Constant.SCHEMA_KEY_PATH);
//                    rule.put(HL7V3Constant.SCHEMA_KEY_PATH, path);
                Map temPMap = Maps.newHashMap(rule);
                temPMap.remove(HL7V3Constant.SCHEMA_KEY_PATH);
                temPMap.put(HL7V3Constant.SCHEMA_KEY_PATH, path);

                    processWritingSchema(messageTerser, temPMap, value);
//                }
            }
            return true;
        }
        return false;
    }

    // 封装path不是以"/"开头的非法验证
    public boolean isRightPath(String path){
        String regularExpression = "(\\/\\S*)";
        Pattern pattern = Pattern.compile(regularExpression);
        Matcher matcher = pattern.matcher(path);
        boolean isRightPath = matcher.matches();
        return isRightPath;
    }

    private boolean hasChild(Map<String, Object> rule){
        Map<String, Map<String, Object>> child = (Map<String, Map<String, Object>>) rule.get(HL7V3Constant.SCHEMA_KEY_CHILD);
        if(child != null){
            return true;
        }
        return false;
    }

    private boolean setNodeValue(Message messageTerser, Object value, Map<String, Object> rule, int ii){
        String format = (String) rule.get(HL7V3Constant.SCHEMA_KEY_FORMAT);
        String type = (String) rule.get(HL7V3Constant.SCHEMA_KEY_TYPE);
        IValueHandler valueHandler = ValueHandlerFactory.getValueHandler(type);
        String paths = (String) rule.get(HL7V3Constant.SCHEMA_KEY_PATH);

        String retValue = value != null ? value.toString() : "";

        if(valueHandler != null){
            retValue = valueHandler.getWritingValue(value, format);
        }
        Terser terser = new Terser(messageTerser);

        if(ii > 0){
            paths = paths + "(" + ii + ")";
        }
        paths = "/."+paths.substring(paths.indexOf("/")+1);
        try {
            terser.set(paths, retValue);
        } catch (HL7Exception e){
            logger.error("terser设置值有误，请修改" + paths + "--" + value.toString(), paths + "--" + value.toString(), e.toString());
        }
        return true;
    }
}
