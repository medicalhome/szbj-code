package com.founder.hie.message.processor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.parser.CustomModelClassFactory;
import ca.uhn.hl7v2.parser.DefaultModelClassFactory;
import ca.uhn.hl7v2.parser.ModelClassFactory;
import ca.uhn.hl7v2.util.ReflectionUtil;
import ca.uhn.hl7v2.util.Terser;

import com.founder.hie.message.data.AbstractMessage;
import com.founder.hie.message.data.HL7V2Message;
import com.founder.hie.message.data.HL7V3Constant;
import com.founder.hie.message.data.MessageModel;
import com.founder.hie.message.exception.MessageException;
import com.founder.hie.message.factory.ValueHandlerFactory;
import com.founder.hie.message.handler.IValueHandler;
import com.founder.hie.message.schema.MessageSchemaDefinition;
import com.founder.hie.message.terser.MessageTerser;
import com.founder.hie.message.util.SchemaUtil;

public class HL7V2MessageProcessor extends AbstractMessageProcessor
{
    // 递归处理消息方式开始
    @Override
    public AbstractMessage generate(MessageModel message, MessageSchemaDefinition msd)
    {
        if (message != null && msd != null && msd.getMessageType() != null && msd.getVersionNumber() != null)
        {
            try
            {
                // 生成必要的消息头MSH
                ModelClassFactory modelClassFactory = new CustomModelClassFactory();
                Class<? extends Message> messageClass = modelClassFactory.getMessageClass(msd.getMessageType(), msd.getVersionNumber(), false);
                Message messageTerser = ReflectionUtil.instantiateMessage(messageClass, modelClassFactory);
                // String xml=msd.getTemplate();
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
                // 生成开始
                Map<String, Object> contentMap = message.getContent();
                Map<String, Object> schemaJson = msd.getSchema();
                if (contentMap == null || schemaJson == null)
                {
                    return null;
                }
                fillNode(messageTerser, schemaJson, contentMap);
                HL7V2Message outMessage = new HL7V2Message(messageTerser, msd);

                return outMessage;
            }
            catch (HL7Exception e)
            {
                logger.error("初始化MSH出错,原因可能是消息类型和版本号不对应", msd.getMessageType() + msd.getVersionNumber(), e.toString());
            }
            catch (MessageException e)
            {
                logger.error(getStackTrace(e));
            }
        }
        return null;
    }

    @Override
    public String getMessageType()
    {
        return "HL7_V2_MESSAGE";
    }

    /**
     * 
     * @param node
     * @param schema
     * @param nsc
     * @param content
     * @return
     * @throws MessageException
     */
    @SuppressWarnings("unchecked")
    private boolean fillNode(Message messageTerser, Object schemaJson, Object contentMap) throws MessageException
    {
        if (messageTerser != null && schemaJson instanceof Map && contentMap instanceof Map)
        {
            String key = null;
            Object value = null;
            for (Entry<String, Object> entity : ((Map<String, Object>) schemaJson).entrySet())
            {
                key = entity.getKey();
                value = ((Map<String, Object>) contentMap).get(key);
                Map<String, Object> rule = (Map<String, Object>) entity.getValue();
                processWritingSchema(rule, messageTerser, value);
            }

            return true;
        }

        return false;
    }

    /**
     * 
     * @param node
     * @param rule
     * @param nsc
     * @param content
     * @return
     * @throws MessageException
     */
    private void processWritingSchema(Map<String, Object> rule, Message messageTerser, Object value) throws MessageException
    {
        if (messageTerser != null && rule instanceof Map && value != null)
        {
            @SuppressWarnings("unchecked")
            Map<String, Map<String, Object>> child = (Map<String, Map<String, Object>>) rule.get(HL7V3Constant.SCHEMA_KEY_CHILD);
            String multiplicity = (String) rule.get(HL7V3Constant.SCHEMA_KEY_MULTIPLICITY);
            String path = (String) rule.get(HL7V3Constant.SCHEMA_KEY_PATH);
            boolean hasChild = hasChild(rule);
            if (SchemaUtil.isSingleNode(multiplicity))
            {
                Object valuee = null;
                if (value instanceof Collection)
                {
                    Object[] contentList = ((Collection<?>) value).toArray();
                    if (contentList.length > 0)
                    {
                        valuee = contentList[0];
                    }
                }
                else
                {
                    valuee = value;
                }
                if (valuee != null)
                {
                    if (hasChild)
                    {
                        fillNode(messageTerser, child, valuee);
                    }
                    setNodeValue(messageTerser, valuee, rule, 0);
                }
            }
            else
            {
                if (value instanceof Collection)
                {
                    Object[] contentList = ((Collection<?>) value).toArray();
                    int contentCountLength = contentList.length;
                    for (int i = 0; i < contentCountLength; i++)
                    {
                        if (hasChild)
                        {
                            fillNodee(messageTerser, child, contentList[i], path, i);
                        }
                        else
                        {
                        	setNodeValue(messageTerser, contentList[i], rule, i);
                        }
                    }
                }
                else
                {
                	logger.error("path:[" + path + "]. Can not get content from message:[" + value + "]");
//                  throw new MessageException("path:[" + path + "]. Can not get content from message:[" + value + "]");
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private boolean fillNodee(Message messageTerser, Object schemaJson, Object contentMap, String pathPartent, int i) throws MessageException
    {
        if (messageTerser != null && schemaJson instanceof Map && contentMap instanceof Map)
        {
            String key = null;
            Object value = null;
            for (Entry<String, Object> entity : ((Map<String, Object>) schemaJson).entrySet())
            {
                key = entity.getKey();
                value = ((Map<String, Object>) contentMap).get(key);
                Map<String, Object> rule = (Map<String, Object>) entity.getValue();
                String path = rule.get(HL7V3Constant.SCHEMA_KEY_PATH).toString();
                if (!isRightPath(path) || !isRightPath(pathPartent))
                {
                    return false;
                }
                String[] pathPartentArray = pathPartent.replaceFirst("/", "").split("/");
                String[] pathArray = path.replaceFirst("/", "").split("/");
                int ii = 0;
                for (int k = 0; k < pathPartentArray.length; k++)
                {
                    if (pathPartentArray[k].contains("("))
                    {
                        pathPartentArray[k] = pathPartentArray[k].toString().split("\\(")[0];
                    }
                    if (pathArray[k].contains("("))
                    {
                        String[] chilePathSplits = pathArray[k].split("\\(");
                        if (pathPartentArray[k].equals(chilePathSplits[0]))
                        {
                            ii++;
                        }
                    }
                    else
                    {
                        if (pathPartentArray[k].equals(pathArray[k]))
                        {
                            ii++;
                        }
                    }
                }
                if (!pathPartentArray[pathPartentArray.length - 1].contains("("))
                {
                    pathPartent = pathPartent + "(" + i + ")";
                }
                if (ii == pathPartentArray.length)
                {
                    rule.remove(HL7V3Constant.SCHEMA_KEY_PATH);
                    rule.put(HL7V3Constant.SCHEMA_KEY_PATH, pathPartent + "/" + pathArray[pathArray.length - 1]);
                    processWritingSchema(rule, messageTerser, value);
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 
     * @param node
     * @param value
     * @param rule
     * @return
     */
    private boolean setNodeValue(Message messageTerser, Object value, Map<String, Object> rule, int ii)
    {
        String format = (String) rule.get(HL7V3Constant.SCHEMA_KEY_FORMAT);
        String type = (String) rule.get(HL7V3Constant.SCHEMA_KEY_TYPE);
        IValueHandler valueHandler = ValueHandlerFactory.getValueHandler(type);
        String expressionStr = (String) rule.get(HL7V3Constant.SCHEMA_KEY_EXPRESSION);
        String paths = (String) rule.get(HL7V3Constant.SCHEMA_KEY_PATH);
        // if (StringUtils.isNotEmpty(expressionStr))
        // {
        // SimpleExpressionManager sem = SimpleExpressionManager.getInstance();
        // Map<String, Object> context = new HashMap<String, Object>();
        // value = sem.execExpression(expressionStr, context);
        // }

        String retValue = value != null ? value.toString() : "";

        if (valueHandler != null)
        {
            retValue = valueHandler.getWritingValue(value, format);
        }
        Terser terser = new Terser(messageTerser);
        
//      观察路径下标拼接结果  
/* 		int j = -1;
        String str = "CCC";
        String c = "(\\/[A-Za-z0-9]*)\\(([0-9]*)\\)";
        Pattern pp = Pattern.compile(c);
        Matcher mm = pp.matcher(paths);
        while (mm.find())
        {
            str = mm.group(1);
            j = Integer.parseInt(mm.group(2));

        }
        if (j > 0)
        {
            for (int i = 0; i < j; i++)
            {
                String string = str + "(" + i + ")" + "-1";
                try
                {
                    terser.set(string, " ");
                }
                catch (HL7Exception e)
                {
                    String pathStr = "terser.get(+" + string + ")" + "--" + "terser.set(" + string + ", ' ')";
                    logger.error("terser取值错误或者设置值有误，请联系管理员" + pathStr, pathStr, e.toString());
                }
            }
        }*/
        if (ii > 0)
        {
            paths = paths + "(" + ii + ")";
        }
        try
        {
            terser.set(paths, retValue);
        }
        catch (HL7Exception e)
        {
            logger.error("terser设置值有误，请联系管理员" + paths + "--" + value.toString(), paths + "--" + value.toString(), e.toString());
        }
        return true;
    }

    // private boolean isNeedCopy(int nodeCount, int contentCount)
    // {
    // return (nodeCount == 1 && contentCount >= nodeCount);
    // }

    public MessageModel parse(AbstractMessage message)
    {
        if (message != null)
        {
            String orgMessage = (String) message.getMessage();
            MessageSchemaDefinition msd = message.getMessageSchemaDef();
            if (StringUtils.isEmpty(orgMessage) || msd == null)
            {
                return null;
            }

            Map<String, Object> schema = msd.getSchema();

            if (schema == null)
            {
                return null;
            }
            HapiContext context = new DefaultHapiContext();
            context.setModelClassFactory(new DefaultModelClassFactory());
            Message messagee = null;
            if (orgMessage != null)
            {
                try
                {
                    messagee = (Message) context.getPipeParser().parse(orgMessage);
                }
                catch (HL7Exception e)
                {
                    e.printStackTrace();
                }
            }
            Terser terser = new Terser(messagee);
            MessageModel gm = new MessageModel();
            gm.setContent(getNodeContent(messagee, schema, terser));
            return gm;
        }

        return null;
    }

    /**
     * 
     * @param node
     * @param schema
     * @param nsc
     * @return
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> getNodeContent(Message messagee, Object schema, Terser terser)
    {
        try
        {
            if (!messagee.isEmpty() && schema instanceof Map)
            {
                Map<String, Object> mapResult = new HashMap<String, Object>();
                String key = null;
                Object value = null;
                for (Entry<String, Object> entity : ((Map<String, Object>) schema).entrySet())
                {
                    key = entity.getKey();
                    Map<String, Object> rule = (Map<String, Object>) entity.getValue();
                    value = processReadingSchema(rule, messagee, terser);
                    mapResult.put(key, value);
                }
                return mapResult;
            }
        }
        catch (HL7Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * v2消息使用Terser方式取值 Date：2015.4.28
     */
    private Object getValue(String xpathStr, Terser terser, Map<String, Object> rule)
    {
        MessageTerser messageTerser = new MessageTerser(terser);
        // terser.toString();
        // int a=terser.getFinder().getRoot().getNames().length;
        // List<String> b=new ArrayList<String>();
        // for(int i=0;i<a;i++){
        // b.add(terser.getFinder().getRoot().getNames()[i]);
        // }System.out.println(b);
        Object valueresult = null;
        try
        {
            valueresult = messageTerser.getValue(xpathStr);
        }
        catch (HL7Exception e)
        {
            logger.error("path表达式错误：表达式格式有误。" + xpathStr, xpathStr, e.toString());
        }
        String format = (String) rule.get(HL7V3Constant.SCHEMA_KEY_FORMAT);
        String type = (String) rule.get(HL7V3Constant.SCHEMA_KEY_TYPE);
        IValueHandler valueHandler = ValueHandlerFactory.getValueHandler(type);
        Object value = valueresult;
        if (valueHandler != null)
        {
            value = valueHandler.getReadingValue(value, type, format);
        }
        return value != null ? value : (String) rule.get(HL7V3Constant.SCHEMA_KEY_DEFAULT_VALUE);
    }

    /**
     * 根据path路径来判断是否是组结构长度
     * @throws HL7Exception 
     * */
    public int getListSize(String path, Message message, Terser terser) throws HL7Exception
    {
        MessageTerser messageTerser = new MessageTerser(message, terser);
        return messageTerser.getArrayCount(path);
    }

    /**
     * 
     * @param node
     * @param rule
     * @param nsc
     * @return
     * @throws HL7Exception 
     */
    @SuppressWarnings("unchecked")
    private Object processReadingSchema(Map<String, Object> rule, Message messagee, Terser terser) throws HL7Exception
    {
        if (!messagee.isEmpty() && rule instanceof Map)
        {
            String priorityValue = (String) rule.get(HL7V3Constant.SCHEMA_KEY_PRIORITY_VALUE);
            if (StringUtils.isEmpty(priorityValue))
            {
                Map<String, Map<String, Object>> child = (Map<String, Map<String, Object>>) rule.get(HL7V3Constant.SCHEMA_KEY_CHILD);
                boolean hasChild = hasChild(rule);
                String multiplicity = (String) rule.get(HL7V3Constant.SCHEMA_KEY_MULTIPLICITY);
                String xpathStr = (String) rule.get(HL7V3Constant.SCHEMA_KEY_PATH);
                // Object nodeValue = StringUtils.isEmpty(xpathStr) ? null:
                // getValue(xpathStr,terser,rule);
                if (SchemaUtil.isSingleNode(multiplicity))
                {
                    if (hasChild)
                    {
                        return getNodeContent(messagee, child, terser);
                    }
                    // 直接返回值
                    return StringUtils.isEmpty(xpathStr) ? null : getValue(xpathStr, terser, rule);
                }
                else
                {
                    List<Object> listResult = new ArrayList<Object>();
                    // 判断字段有个数组
                    int lenth = getListSize(xpathStr, messagee, terser);
                    Object[] array = new Object[lenth];
                    // 通过lenth判断长度为大于1的进行拼接，如果是为1的不进行拼接；
                    if (lenth == 1)
                    {
                        if (hasChild)
                        {
                            listResult.add(getNodeContentNoChild(xpathStr, child, messagee, terser));
                        }
                        else
                        {
                            array[1] = getValue(xpathStr, terser, rule);
                            listResult.add(array[1]);
                        }
                    }
                    if (lenth > 1)
                    {
                        for (int i = 0; i < lenth; i++)
                        {
                            if (hasChild)
                            {
                                listResult.add(getNodeContentchild(xpathStr, child, messagee, terser, i));
                            }
                            else
                            {
                                array[i] = getValue(xpathStr, terser, rule);
                                listResult.add(array[i]);
                            }
                        }
                    }
                    if(lenth == 0)
                    {
                    	logger.error(xpathStr + "路径为空");
                    }
                    return listResult;
                }
            }
            else
            {
                return priorityValue;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getNodeContentNoChild(String node, Object schema, Message messagee, Terser terser) throws HL7Exception
    {
        if (node != null && schema instanceof Map)
        {
            Map<String, Object> mapResult = new HashMap<String, Object>();
            String key = null;
            Object value = null;
            for (Entry<String, Object> entity : ((Map<String, Object>) schema).entrySet())
            {
                key = entity.getKey();
                Map<String, Object> rule = (Map<String, Object>) entity.getValue();
                String chilePath = rule.get(HL7V3Constant.SCHEMA_KEY_PATH).toString();
                if (!isRightPath(chilePath) || !isRightPath(node))
                {
                    return null;
                }
                value = processReadingSchemaChild(rule, messagee, terser);
                mapResult.put(key, value);
            }
            return mapResult;
        }
        return null;
    }

    /**
     * 截取path路径,进行添加下标处理，下标从0开始
     * @throws HL7Exception 
     * */
    @SuppressWarnings("unchecked")
    public Map<String, Object> getNodeContentchild(String node, Object schema, Message messagee, Terser terser, int j) throws HL7Exception
    {
        if (node != null && schema instanceof Map)
        {
            Map<String, Object> mapResult = new HashMap<String, Object>();
            String key = null;
            Object value = null;
            for (Entry<String, Object> entity : ((Map<String, Object>) schema).entrySet())
            {
                key = entity.getKey();
                Map<String, Object> rule = (Map<String, Object>) entity.getValue();
                String chilePath = rule.get(HL7V3Constant.SCHEMA_KEY_PATH).toString();
                if (!isRightPath(chilePath) || !isRightPath(node))
                {
                    return null;
                }
                String[] chilePathSplit = chilePath.replaceFirst("/", "").split("/");
                String[] parentNode = node.replaceFirst("/", "").split("/");
                int ii = 0;
                for (int i = 0; i < parentNode.length; i++)
                {
                    if (parentNode[i].contains("["))
                    {
                        parentNode[i] = parentNode[i].toString().split("\\[")[0];
                    }
                    if (chilePathSplit[i].contains("["))
                    {
                        String[] chilePathSplits = chilePathSplit[i].split("\\[");
                        if (parentNode[i].equals(chilePathSplits[0]))
                        {
                            ii++;
                        }
                    }
                    else
                    {
                        if (parentNode[i].equals(chilePathSplit[i]))
                        {
                            ii++;
                        }

                    }
                }
                if (!parentNode[parentNode.length - 1].contains("["))
                {
                    node = node + "[" + j + "]";
                }
                if (ii == parentNode.length)
                {
                    rule.remove(HL7V3Constant.SCHEMA_KEY_PATH);
                    rule.put(HL7V3Constant.SCHEMA_KEY_PATH, node + "/" + chilePathSplit[chilePathSplit.length - 1]);
                    value = processReadingSchemaChild(rule, messagee, terser);
                }
                else
                {
                    // 对于简单的消息 /OBX 和 /OBX-3进行下标拼写node
                    if (parentNode.length <= 1)
                    {
                    	// $Author :shen_zening
                    	// $Date : 2015/11/4
                    	// [BUG]0061920 MODIFY BEGIN
                    	String[] str;
                    	if(chilePathSplit.length <= 1){
                    		str = chilePathSplit;
                    	}else{
                    		str = chilePathSplit[0].split(parentNode[0]);
                    	}
                        rule.remove(HL7V3Constant.SCHEMA_KEY_PATH);
                        if(chilePathSplit.length <= 1){
                        	rule.put(HL7V3Constant.SCHEMA_KEY_PATH, node + str[0]);
                        }else{
                        	rule.put(HL7V3Constant.SCHEMA_KEY_PATH, node + str[1]);
                        }
                        // [BUG]0061920 MODIFY END  
                        value = processReadingSchemaChild(rule, messagee, terser);
                        // 拼接好下标的只是去查找值，继续回置为原始path
                        rule.remove(HL7V3Constant.SCHEMA_KEY_PATH);
                        rule.put(HL7V3Constant.SCHEMA_KEY_PATH, chilePath);
                    }
                    else
                    {
                        return null;
                    }

                }
                mapResult.put(key, value);
            }
            return mapResult;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private Object processReadingSchemaChild(Map<String, Object> rule, Message messagee, Terser terser) throws HL7Exception
    {
        if (!messagee.isEmpty() && rule instanceof Map)
        {
            String priorityValue = (String) rule.get(HL7V3Constant.SCHEMA_KEY_PRIORITY_VALUE);
            if (StringUtils.isEmpty(priorityValue))
            {
                Map<String, Map<String, Object>> child = (Map<String, Map<String, Object>>) rule.get(HL7V3Constant.SCHEMA_KEY_CHILD);
                boolean hasChild = hasChild(rule);
                String multiplicity = (String) rule.get(HL7V3Constant.SCHEMA_KEY_MULTIPLICITY);
                String xpathStr = (String) rule.get(HL7V3Constant.SCHEMA_KEY_PATH);
                if (SchemaUtil.isSingleNode(multiplicity))
                {
                    if (hasChild)
                    {
                        return getNodeContent(messagee, child, terser);
                    }
                    // 直接返回值
                    return StringUtils.isEmpty(xpathStr) ? null : getValue(xpathStr, terser, rule);
                }
                else
                {
                    List<Object> listResult = new ArrayList<Object>();
                    int lenth = getListSize(xpathStr, messagee, terser);
                    Object[] array = new Object[lenth];
                    if(lenth > 0)
                    {
                    	for (int i = 0; i < lenth; i++)
                    	{
                    		if (hasChild)
                    		{
                    			listResult.add(getNodeContentchild(xpathStr, child, messagee, terser, i));
                    		}
                    		else
                    		{
                    			array[i] = getValue(xpathStr, terser, rule);
                    			listResult.add(array[i]);
                    		}
                    	}
                    }else{
                    	logger.error(xpathStr + "路径存在异常");
                    }
                    return listResult;
                }
            }
            else
            {
                return priorityValue;
            }
        }
        return null;
    }

    // 封装paht不是以"/"开头的非法验证
    public boolean isRightPath(String path)
    {
        String regularExpression = "(\\/\\S*)";
        Pattern pattern = Pattern.compile(regularExpression);
        Matcher matcher = pattern.matcher(path);
        boolean isRightPath = matcher.matches();
        return isRightPath;
    }

    /**
     * 
     * @param rule
     * @return
     */
    @SuppressWarnings("unchecked")
    private boolean hasChild(Map<String, Object> rule)
    {
        // rule.get(HL7V3Constant.SCHEMA_KEY_TYPE);
        Map<String, Map<String, Object>> child = (Map<String, Map<String, Object>>) rule.get(HL7V3Constant.SCHEMA_KEY_CHILD);

        if (child != null)
        {
            return true;
        }

        return false;
    }

    /**
     * 
     * @param node
     * @param rule
     * @return
     */
    private Object getNodeValue(String node, Map<String, Object> rule)
    {
        String format = (String) rule.get(HL7V3Constant.SCHEMA_KEY_FORMAT);
        String type = (String) rule.get(HL7V3Constant.SCHEMA_KEY_TYPE);
        IValueHandler valueHandler = ValueHandlerFactory.getValueHandler(type);
        Object value = "";
        // node.getTextContent().;

        if (valueHandler != null)
        {
            value = valueHandler.getReadingValue(value, type, format);
        }

        return value != null ? value : (String) rule.get(HL7V3Constant.SCHEMA_KEY_DEFAULT_VALUE);
    }

    @Override
    protected boolean verify(Object message, MessageSchemaDefinition msd)
    {
        return true;
    }
}
