package com.yly.cdr.batch.processor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.founder.hie.message.data.AbstractMessage;
import com.founder.hie.message.data.HL7V2Message;
import com.founder.hie.message.processor.HL7V2MessageProcessor;
import com.founder.hie.rce.util.FileUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.founder.hie.message.data.MessageModel;
import com.founder.hie.message.factory.MessageFactory;
import com.founder.hie.message.schema.MessageSchemaDefinition;
import com.yly.cdr.batch.core.ServiceMapper;
import com.yly.cdr.batch.exception.message.MessageProcessException;
import com.yly.cdr.core.Constants;
import com.yly.cdr.entity.Message;
import com.yly.cdr.hl7.dto.BaseDto;
import com.yly.cdr.util.DictionaryUtils;
import com.yly.cdr.util.MapToBean;
import com.yly.cdr.util.PropertiesUtils;
import com.yly.cdr.util.SpringBeanFactory;
import com.yly.cdr.util.StringUtils;
import com.yly.hl7.maphandler.FileJsonMapHandler;
/**
 * 定义使用的ItemProcessor
 * @author wen_ruichao
 * @version 1.0
 */
public class V2MessageProcessor implements ItemProcessor<Message, BaseDto>
{

    private static final Logger logger = LoggerFactory.getLogger(V2MessageProcessor.class);

    /**
     * 消息ID（根据消息ID，实例化对应Dto）
     */
    private String messageId;
    
    private FileJsonMapHandler fileJsonMapHandler;
    
    private ServiceMapper serviceMapper;

    public void setMessageId(String messageId)
    {
        this.messageId = messageId;
    }

    /**
     * 消息-对象映射
     * @param message 消息对象
     * @return dto对象
     * @exception MessageProcessException
     */
    @SuppressWarnings("unchecked")
	public BaseDto process(Message message) throws Exception
    {
        logger.trace("process()");

        //获取service id
		Map map = getV2Map(message);		
        String serviceId = getServiceId(messageId,map);
        map=updateV2Map(map);
        logger.debug("map:{}", map);
        if(serviceId == null || serviceId.equals("")){
        	logger.debug("V2消息服务id映射失败！！");
        }
        message.setServiceId(serviceId);
        serviceMapper = (ServiceMapper)SpringBeanFactory.getBean("serviceMapper");
        String beanId = serviceMapper.getMessageId(serviceId);
        message.setVid(beanId);
        BaseDto baseDto = BaseDto.class.cast(Class.forName(
                PropertiesUtils.getValue("package.dto") + "."
                    + beanId.toLowerCase() + "." + beanId).newInstance());
        baseDto.setMessage(message);     
        try
        {
        	MapToBean.toJavaBean(baseDto,map);
        	
        }
        catch (Exception e)
        {
            logger.error("消息内容转换dto对象异常：{}", e.getMessage());
            throw new MessageProcessException(messageId, e);
        }
        return baseDto;
    }
    
    /**
     * 消息-map转换
     * @param message 消息对象
     * @return map对象
     * @throws IOException 
     * @throws JsonMappingException 
     * @throws JsonParseException 
     * @exception MessageProcessException
     */
    private Map getV2Map(Message message) throws JsonParseException, JsonMappingException, IOException{
    	fileJsonMapHandler = new FileJsonMapHandler();
    	fileJsonMapHandler.setMessageId(messageId);
    	ObjectMapper mapper = new ObjectMapper();
    	String jsonContent = fileJsonMapHandler.getJsonContent();
    	if(StringUtils.isEmpty(jsonContent)){
    		logger.error("V2消息配置文件加载失败。。。。。。");
    		throw new NullPointerException("V2配置文件未找到。。。。。。");
    	}
		Map schema = mapper.readValue(jsonContent, HashMap.class);
		MessageSchemaDefinition msd = new MessageSchemaDefinition();
		msd.setSchema(schema);
		MessageModel model = MessageFactory.parseMessage("HL7_V2_MESSAGE", message.getContent(),msd);
		if(model==null || model.getContent().size()<1){
    		logger.error("V2消息解析失败。。。。。。");
    		throw new NullPointerException("V2消息解析失败。。。。。。");
    	}
        /*AbstractMessage abstractMessage1 = MessageFactory.generateMessage("HL7_V2_MESSAGE", model,msd);
        HL7V2MessageProcessor hl7V2MessageProcessor = new HL7V2MessageProcessor();
//        msd.setMessageType("OMG_O19");
        msd.setMessageType("ORG_O20");
        msd.setVersionNumber("2.4");
        HL7V2MessageHandle hl7V2MessageHandle = new HL7V2MessageHandle();
        FileUtils.writeToFile(hl7V2MessageHandle.buildV2Message(model,msd), new File("/a.txt"));*/
        return model.getContent();
    }

	private String getServiceId(String msgId, Map mp) throws Exception{
		getPatientEid(mp);
    	String result = null;
    	if("ADT_A04".equals(msgId)){
    		result = "BS001";
    		mp.put("triggerEventCode", Constants.NEW_MESSAGE_FLAG);
    	} 
    	else if("ADT_A01".equals(msgId)){
    		result = "BS310";
    		mp.put("newUpFlag", Constants.NEW_MESSAGE_FLAG);
    	}
    	else if("ADT_A11".equals(msgId)){
    		String visitType=(String)mp.get("visitType");
    		if("O".equals(visitType)){
    			result = "BS001";
    			mp.put("triggerEventCode", Constants.CANCEL_MESSAGE_FLAG);
    		}
    		else if ("I".equals(visitType)){
    			result = "BS313";
    			mp.put("triggerEvent", Constants.CANCEL_MESSAGE_FLAG);
    		}
    	}
    	else if("ADT_A16".equals(msgId)){
    		result = "BS313";
    		mp.put("triggerEvent", Constants.REGISTER_MESSAGE_FLAG);
    	}
    	else if("ADT_A03".equals(msgId)){
    		result = "BS313";
    		mp.put("triggerEvent", Constants.NEW_MESSAGE_FLAG);
    	}
    	else if("ADT_A25".equals(msgId)){
    		result = "BS313";
    		mp.put("triggerEvent", Constants.CANCEL_REGI_MESSAGE_FLAG);
    	}
    	else if("ADT_A10".equals(msgId)){
    		result = "BS316";
    		mp.put("triggerEvent", Constants.NEW_MESSAGE_FLAG);
    		mp.put("transferFlag", Constants.TRANSFER_IN_FLAG);
    	}
    	else if("ADT_A02".equals(msgId)){
    		result = "BS352";
//    		mp.put("newUpFlg", Constants.UPDATE_MESSAGE_FLAG);
    		mp.put("newUpFlg", Constants.NEW_MESSAGE_FLAG);
    		mp.put("orderTypeCode", Constants.TRANSFER_HISTORY_TRANS_ANSWER);
    	}
    	else if("ADT_A42".equals(msgId)){
    		result = "BS352";
//    		mp.put("newUpFlg", Constants.UPDATE_MESSAGE_FLAG);
    		mp.put("newUpFlg", Constants.NEW_MESSAGE_FLAG);
    		mp.put("orderTypeCode", Constants.TRANSFER_HISTORY_TRANS_BED);
    		
    		String bed_status = (String)mp.get("bed_status");
    		if("B".equals(bed_status)){
    			mp.put("orderTypeCode", Constants.ORDER_TYPE_TRANSFER);
    		}
    		else if("C".equals(bed_status)){
    			mp.put("orderTypeCode", Constants.ORDER_TYPE_TRANSFER);
    			mp.put("newUpFlg", Constants.DELETE_MESSAGE_FLAG);
    		}
    		
    	}
    	else if("ADT_A31".equals(msgId)){
    		adta31MapModify(mp);
    		result = "BS301";
    	}
    	else if("OMP_O09".equals(msgId)){
//    		result = "BS005";
    		result = ompo09Modify(mp);
    	}
    	else if("OMG_O19".equals(msgId)){
    		String triggerEvent = (String) mp.get("triggerEventCode");
    		String orderStatus = null;
    		if(Constants.V2_DOC_CANCEL_MESSAGE_FLAG.equals(triggerEvent)){
    			//撤销检查申请 CA
    			result = "BS005";
    		} else if(Constants.V2_EXEC_CANCEL_MESSAGE_FLAG.equals(triggerEvent)){
    			//撤销检查执行 OC
    			result = "BS004";
    			orderStatus = Constants.ORDER_STATUS_CANCELED;
    		} else if(Constants.V2_NEW_MESSAGE_FLAG.equals(triggerEvent) || Constants.V2_UPDATE_MESSAGE_FLAG.equals(triggerEvent)){//NW开立，RU更新
    			//开立检查申请 NW
    			result = "BS002";		
    		} 
			List orderList = getOrderListFromMap(mp, result);
    		setOrderTypeAndName(orderList, Constants.EXAMINATION_ORDER, "检查医嘱");
    		setOrderStatus(orderList, orderStatus);
    	}
    	else if("ORG_O20".equals(msgId)){   		
    		String triggerEvent = (String) mp.get("triggerEvent");
    		String sender = (String) mp.get("sender");
    		String orderStatus = null;
    		if(Constants.V2_CONFIRM_OR_CHARGE_MESSAGE_FLAG.equals(triggerEvent)){
    			if("NIS".equals(sender)){
        			//检查医嘱确认 OK
        			result = "BS004";
        			orderStatus = Constants.ORDER_STATUS_CONFIRMED;
    			} else/* if("HIS".equals(sender))*/{
    				//检查收费 OK
    				result = "BS002";
    			}
    		} else if(Constants.V2_RETURN_MESSAGE_FLAG.equals(triggerEvent)){
    			//撤销检查退费 CR
    			result = "BS005";
    		} else if(Constants.V2_EXEC_MESSAGE_FLAG.equals(triggerEvent)){
    			//确认检查执行 OR
    			result = "BS004";
    			List orderList = (List) mp.get("orderStatusInf");
    			Map order = (Map) orderList.get(0);
    			String os = (String) order.get("orderStatus");
    			if("SC".equals(os)){
    				//已到检
    				orderStatus = Constants.ORDER_STATUS_EXAM_TO_CHECK;
    			} else if ("IP".equals(os)){
    				//检查已完成
    				orderStatus = Constants.ORDER_STATUS_EXAM_FINISH;
    			} else if("CM".equals(os)){
//    				logger.debug("ORG^O20消息，ORC-5=CM时，为写报告状态，不对此做处理");
    				// 写报告
    				orderStatus = Constants.ORDER_STATUS_WRITING_REPORT;
    			} else {
    				logger.debug("ORG^O20消息，ORC-5={}，无对应状态，视为检查已完成", os);
    				orderStatus = Constants.ORDER_STATUS_EXAM_FINISH;
    			}
    		}
			List orderList = getOrderListFromMap(mp, result);
    		setOrderTypeAndName(orderList, Constants.EXAMINATION_ORDER, "检查医嘱");
    		setOrderStatus(orderList, orderStatus);
    	}
    	else if("OML_O21".equals(msgId)){
    		String newUpFlag = (String) mp.get("newUpFlg");
    		if(Constants.V2_NEW_MESSAGE_FLAG.equals(newUpFlag) || Constants.V2_UPDATE_MESSAGE_FLAG.equals(newUpFlag)){
    			//开立检验申请 NW
    			result = "BS006";
    		} else if(Constants.V2_DOC_CANCEL_MESSAGE_FLAG.equals(newUpFlag)){
    			//撤销检验申请 CA
    			result = "BS005";
    		}
    		omlo21Modify(mp);
    		List orderList = getOrderListFromMap(mp, result);
    		setOrderTypeAndName(orderList, Constants.LAB_ORDER, "检验医嘱");
    	}
    	else if("ORM_O01".equals(msgId)){
    		result = ormo01Modify(mp);
    	}
    	else if("ORR_O02".equals(msgId)){
    		result = "BS004";
    		String newUpFlag = (String) mp.get("newUpFlag");
    		List orderList = getOrderListFromMap(mp, result);
    		if(Constants.V2_EXEC_MESSAGE_FLAG.equals(newUpFlag)){
    			setOrderStatus(orderList, Constants.ORDER_STATUS_PROCEDURE_FINISH);
    		}else if(Constants.V2_CONFIRM_OR_CHARGE_MESSAGE_FLAG.equals(newUpFlag)){
    			setOrderStatus(orderList, Constants.ORDER_STATUS_CONFIRMED);
    		}
    		setOrderTypeAndName(orderList, Constants.PROCEDURE_ORDER, "手术类医嘱");
    	}
    	else if("ORL_O22".equals(msgId)){
    		String sender = (String) mp.get("sender");
    		String newUpFlag = (String) mp.get("newUpFlag");
    		String orderStatus = null; 
    		if(Constants.V2_CONFIRM_OR_CHARGE_MESSAGE_FLAG.equals(newUpFlag)){
    			if("NIS".equals(sender)){
    				//检验医嘱确认场景 OK
        			result = "BS004";
        			orderStatus = Constants.ORDER_STATUS_CONFIRMED;
    			} else/* if("HIS".equals(sender))*/{
    				//检验收费场景 OK
    				result = "BS006";
    			}
    		}else if(Constants.V2_RETURN_MESSAGE_FLAG.equals(newUpFlag)){
    			//撤销检验退费 CR
    			result = "BS005";
    		} else if(Constants.V2_EXEC_MESSAGE_FLAG.equals(newUpFlag)){
    			//确认检验执行 OR
    			result = "BS004";
    			List orderList = (List) mp.get("orderStatusInf");
    			Map order = (Map) orderList.get(0);
    			String os = (String) order.get("orderStatus"); 
    			if("SC".equals(os)){
    				//标本已签收（条码录入）
    				orderStatus = Constants.ORDER_STATUS_LAB_SAMPLE_SIGNED;
    			} else if ("IP".equals(os)){
    				//标本已采集（条码签收）
    				orderStatus = Constants.ORDER_STATUS_LAB_SAMPLE_COLLECTED;
    			} else if("CM".equals(os)){
    				//检验已完成
    				orderStatus = Constants.ORDER_STATUS_LAB_FINISH;
    			} else if("OR".equals(os)){
//    				logger.error("ORL^O22消息，ORC-5=OR时，为下载状态，不对此做处理");
    				// 下载条码
    				orderStatus = Constants.ORDER_STATUS_DOWNLOAD;
    			} else {
    				logger.error("ORL^O22消息，ORC-5={}，无此状态", os);
    			}
    		} else if(Constants.V2_EXEC_CANCEL_MESSAGE_FLAG.equals(newUpFlag)){
    			//撤销检验执行 OC
    			result = "BS004";
    			orderStatus = Constants.ORDER_STATUS_CANCELED;
    		}
			List orderList = getOrderListFromMap(mp, result);
			setOrderTypeAndName(orderList, Constants.LAB_ORDER, "检验医嘱");
			setOrderStatus(orderList, orderStatus);
			orlo22Modify(mp);
    	}
    	else if("ORU_ZVS".equals(msgId)){
    		// 生命体征
    		result = "BS315";
    		oruzvsModify(mp);
    	}
    	else if("ORU_R01".equals(msgId)){
    		// 检查报告已审核
    		result = "BS004";
    		orur01Modify(mp);
    	}
    	else if("OUL_R21".equals(msgId)){
    		// 检验报告已审核
    		result = "BS004";
    		oulr21Modify(mp, result);
    	}
    	else if("MFN_M01".equals(msgId)){
    		result = mfnm01Modify(mp);
    	}
    	else if ("RAS_O17".equals(msgId)){
    		// 确认医嘱执行
    		result = raso17Modify(mp);
    	}
    	else if("ADT_A54".equals(msgId)){
    		result = "BS359";
    		List<Map<String,String>> doctors = (List<Map<String, String>>)mp.get("doctors");
    		for(Map<String,String> doctor : doctors){
    			doctor.put("titleTypeName", "主管医师");
    			doctor.put("action", Constants.PATIENT_IN_CHARGE_CHANGE);
    		}
    		String patientD = (String) mp.get("domainId");
			String value = getPatientDomainValue(patientD);
			mp.put("domainId", value);
    		
    	}
    	// 摆药，发药，退药
    	else if ("RGV_O15".equals(msgId)){
    		result = rgvo15Modify(mp);
    	}
    	
    	//bs001中有patients这个list，将其中的域id值进行转换
    	if("BS001".equals(result)){
    		setPatientsListDomainValue(mp, "patients");
    	}
    	
    	return result;
    }
    
    

	private Map updateV2Map(Map map){
    	
    	String patientDomain=(String)map.get("patientDomain");
    	if(patientDomain != "" && patientDomain != null){
    		String value = getPatientDomainValue(patientDomain);
    		map.put("patientDomain", value);
    	}
	
    	String visitType=(String)map.get("visitType");
    	visitType = visitType == null ? (String) map.get("visitTypeCode") : visitType;
    	String visitTypeValue = null;
    	visitTypeValue = getVisitTypeValue(visitType);
    	map.put("visitType", visitTypeValue);
    	map.put("visitTypeCode", visitTypeValue);
		return map;
    	
    }
    /**
     * 对adta31取出的map做处理
     * */
    private void adta31MapModify(Map map){
    	List diagnosis = (List) map.get("diagnosis");
    	
    	for(Object obj : diagnosis){
    		Map mainDiagnosis = (Map) obj;
    		String isMainDiagnosis = (String) mainDiagnosis.get("isMainDiagnosis"); 

        	if(isMainDiagnosis != null && !isMainDiagnosis.equals("")){
        		if(isMainDiagnosis.equals(Constants.MAIN_DIAGNOSIS)){
        			isMainDiagnosis = "true";    		
    	    	} else {
    	    		isMainDiagnosis = "false";
    	    	}	
        	}
        	mainDiagnosis.put("isMainDiagnosis", isMainDiagnosis);
        	// 港大实时消息未改造完，诊断时间位置与消息定义文档不同，两个节点中有值的作为诊断时间
        	String diagnosisDate = (String) mainDiagnosis.get("diagnosisDate");
        	if(StringUtils.isEmpty(diagnosisDate)){
        		diagnosisDate = (String) mainDiagnosis.get("diagnosisDate2");
        		mainDiagnosis.put("diagnosisDate", diagnosisDate);
        	}
    	}
    	
//    	map.put("newUpFlg", Constants.NEW_MESSAGE_FLAG);
    }
    
    /**
     * 对omlo21取出的map做处理
     * */
    private void omlo21Modify(Map map){
    	/*
    	 * 因为V2结构原因，labOrderDto不能从消息中嵌套在requestList中取出，
    	 * 现labOrderDto与requestList处在平级，再将labOrderDto内容放入requestList中
    	 * */
    	List requestLists = (List) map.get("requestList");

    	List labOrderDtos = (List) map.get("labOrderDto");
    	
    	for(int i = 0; i < requestLists.size(); i++){
    		Map requestList = (Map) requestLists.get(i);
    		Map labOrderDto = (Map) labOrderDtos.get(i);
    		List labItems = (List) labOrderDto.get("labItem");
    		Map labItem = (Map) labItems.get(0);
    		labOrderDto.putAll(labItem);
    		labOrderDto.remove("labItem");
    		List lab = new ArrayList();
    		lab.add(labOrderDto);
    		requestList.put("labOrderDto", lab);
    	}
    	
    	map.remove("labOrderDto");

    	//把内部的visitType转换
    	List visitLists = (List) map.get("visitList");
    	Map visitList = (Map) visitLists.get(0);
    	String visitType=(String)visitList.get("visitType");
    	String visitTypeValue = getVisitTypeValue(visitType);
    	visitList.put("visitType", visitTypeValue);
    }
    /**
     * 对ormo01取出的map做处理
     * */
    private String ormo01Modify(Map mp) {
    	String result = null;
    	String triggerEvent = (String) mp.get("newUpFlag");
    	
		if(Constants.V2_NEW_MESSAGE_FLAG.equals(triggerEvent)){
			// 开立手术申请
			result = "BS007";
			mp.put("newUpFlag", Constants.NEW_MESSAGE_FLAG);
		}else if (Constants.V2_UPDATE_MESSAGE_FLAG.equals(triggerEvent)){
			// 更新
			result = "BS007";
			mp.put("newUpFlag", Constants.UPDATE_MESSAGE_FLAG);
		}else if (Constants.V2_DOC_CANCEL_MESSAGE_FLAG.equals(triggerEvent)){
			// 撤销手术申请
			result = "BS005";
			List orderList = getOrderListFromMap(mp, result);
    		setOrderTypeAndName(orderList, Constants.PROCEDURE_ORDER, "手术类医嘱");
    		mp.put("newUpFlag", Constants.V2_DOC_CANCEL_MESSAGE_FLAG);
		}else if (Constants.V2_EXEC_CANCEL_MESSAGE_FLAG.equals(triggerEvent)){
			// 手术室撤销手术
			result = "BS004";
			List orderList = getOrderListFromMap(mp, result);
    		setOrderTypeAndName(orderList, Constants.PROCEDURE_ORDER, "手术类医嘱");
			mp.put("newUpFlag", Constants.V2_EXEC_CANCEL_MESSAGE_FLAG);
			setOrderStatus(orderList, Constants.ORDER_STATUS_CANCELED);
		}    	
    	
    	List medicaVisit = (List) mp.get("medicaVisit");
    	//就诊map中的visitType转换
    	Map map = (Map)medicaVisit.get(0);
    	String visitType=(String)map.get("visitType");
    	String visitTypeValue = getVisitTypeValue(visitType);
    	map.put("visitType", visitTypeValue);
    	List proOrders = (List) mp.get("proOrders");
    	Map proOrderBatch = (Map)proOrders.get(0);
    	proOrderBatch.put("medicaVisit", medicaVisit);
    	String domainID = (String)proOrderBatch.get("domainID");
    	proOrderBatch.put("domainID", getPatientDomainValue(domainID));
    	mp.remove("medicaVisit");
    	
    	// 港大zop段分隔符特殊处理
    	if(Boolean.valueOf(Constants.GD_STUPID_ZOP_SEPARATOR) && "BS007".equals(result)){
    		List proOrdersList = (List) mp.get("proOrders");
    		Map proOrdersMap = null;
    		if(proOrdersList != null && !proOrdersList.isEmpty()){
    			proOrdersMap = (Map) proOrdersList.get(0);
    		}
    		if(proOrdersMap != null){
    			String operatorList = (String) proOrdersMap.get("operatorCode");
    			String[] ss = operatorList.split("#");
    			if(ss != null){
    				if(ss.length > 0){
    					proOrdersMap.put("operatorCode", ss[0]);
    				}
    				if(ss.length > 1){
    					proOrdersMap.put("assistant1Code", ss[1]);
    				}
    				if(ss.length > 2){
    					proOrdersMap.put("assistant2Code", ss[2]);
    				}
    				if(ss.length > 3){
    					proOrdersMap.put("assistant3Code", ss[3]);
    				}
    			}
    			
    			String operationNameCode = (String) proOrdersMap.get("operationNameCode");
    			ss = operationNameCode.split("#");
    			proOrdersMap.put("operationNameCode", ss[0]);
    			proOrdersMap.put("operationName", ss[1]);
    		}
    		
    	}
    	
		return result;
	}
    
    /**
     * 消息中不发医嘱类别，根据消息进行判断并设置医嘱类别及名称
     * */
    private void setOrderTypeAndName(List orders, String orderType, String orderTypeName){
    	if(orders == null) return;
    	for(Object obj : orders){
    		Map order = (Map) obj;
    		order.put("orderType", orderType);
    		order.put("orderTypeName", orderTypeName);
    	}
    }
    
    /**
     * 不同服务id中的医嘱list名称不同，根据服务id取出list
     * */
    private List getOrderListFromMap(Map map, String serviceId){
    	if(serviceId == null) return null;
    	List orders = null;
    	if(serviceId.equals("BS004")){
    		orders = (List)map.get("orderStatusInf");
    	} else if (serviceId.equals("BS005")){
    		orders = (List)map.get("order");
    	} else if (serviceId.equals("BS006")){
    		orders = (List)map.get("requestList") ;
    	} else if (serviceId.equals("BS002")){
    		orders = (List)map.get("examinationApplications");
    	}else if (serviceId.equals("BS007")){
    		orders = (List)map.get("proOrders");
    	}
    	return orders;
    }
    
    /**
     * visitType映射
     * */
    private String getVisitTypeValue(String v){
    	if(v == null) return null;
/*    	String visitTypeValue = null;
    	if(v != "" && v != null){
    		if("O".equals(v))
    		{
    			visitTypeValue = "01";
    		}
    		else if("I".equals(v))
    		{
    			visitTypeValue = "03";
    		}
    		else if("T".equals(v))
    		{
    			visitTypeValue = "0401";
    		}
    	}
    	return visitTypeValue;*/
    	// 使用港大的就诊类别I,O,T，不做修改
    	return v;
    }
    
    /**
     * patientDomain映射
     * */
    private String getPatientDomainValue(String v){
    	
    	String value = null;
    	if(v != null){
    		if("T".equals(v)){
    			value = "04";
    		} else {
    			value = "01";
    		}
    	}
    	return value;
    }
    
    /**
     * v2消息和cdr内部bean结构不一致，先把数据取出，然后置入相应的位置
     * */
    private void orlo22Modify(Map map){
    	List requestLists = (List) map.get("requestList");
    	List labOrderDtos = (List) map.get("labOrderDto");
    	for(int i = 0; i < requestLists.size(); i++){
    		Map requestList = (Map) requestLists.get(i);
    		Map labOrderDto = (Map) labOrderDtos.get(i);
    		List lab = new ArrayList();
    		lab.add(labOrderDto);
    		requestList.put("labOrderDto", lab);
    		List chargeList = (List) requestList.get("chargeList");
    		if(chargeList != null && !chargeList.isEmpty()){
    			Map charge = (Map) chargeList.get(0);    			
    			requestList.putAll(charge);
    		}
    	}    	
    }
    /**
     * 给医嘱list中的所有医嘱map设置医嘱执行状态值
     * */
    private void setOrderStatus(List orders, String orderStatus){
    	if(orders == null) return;
    	for(Object obj : orders){
    		Map order = (Map) obj;
    		order.put("orderStatus", orderStatus);
    	}
    }
    
    /**
     * 部分json中，存在包含域id的list，对域id的值做转换
     * */
    private void setPatientsListDomainValue(Map map, String listName){
    	List list = (List) map.get(listName);
    	if(list != null && !list.isEmpty()){
    		for(Object o : list){
    			Map m = (Map) o;
    			String patientD = (String) m.get("patientDomain");
    			String value = getPatientDomainValue(patientD);
    			m.put("patientDomain", value);
    		}
    	}
    }
    
    /**
     * 设置交互类型和操作码
     * */
    private void oruzvsModify(Map map){
    	map.put("newUpFlag", Constants.NEW_MESSAGE_FLAG);
    	String patientLid = (String) map.get("patientLid");
    	String time = (String) map.get("time");
    	String operationId = patientLid + time;
    	map.put("operationId", operationId);
    }
    
    /**
     * 检查报告已审核，设置map中的值。申请单号可能多个，分别取出做处理
     * */
    private void orur01Modify(Map map){
    	map.put("newUpFlag", "new");
    	Map<String, Object> requestNos = (Map<String, Object>) map.get("requestNos");
    	Collection<Object> nos = requestNos.values();
    	List<Map> orderStatusInf = new ArrayList<Map>();
    	for(Object obj : nos){
    		if(obj == null) continue;
    		else {
    			String no = (String) obj;
    			// 申请单号用分号分隔
    			String[] noss = no.split(";");
    			for(String requestNo : noss){
        			Map<String, Object> order = new HashMap<String, Object>();
            		order.put("requestNo", no);
            		order.put("orderStatus", Constants.ORDER_STATUS_EXAM_RESULT_REVIEWED);
            		orderStatusInf.add(order);
    			}
    		}
    	}
    	setOrderTypeAndName(orderStatusInf, Constants.EXAMINATION_ORDER, "检查医嘱");
    	map.put("orderStatusInf", orderStatusInf);
    }
    
    /**
     * @return 返回字典对应的serviceId
     * */
    private String mfnm01Modify(Map map){
    	String identifier = (String) map.get("identifier");
    	String msgId = null;
    	String listName = null;
    	String specialName = null;
    	if("Department".equals(identifier)){// 科室字典
    		msgId = Constants.DOMAIN_DEPARTMENT;
    		listName = "Dept";
    	} else if("Employee".equals(identifier)){ // 人员字典
    		msgId = Constants.DOMAIN_STAFF;
    		listName = "Person";
    	} else if("DeptStat".equals(identifier)){ // 病区字典
    		msgId = Constants.DOMAIN_WARD;
    		listName = "WardDictionary";
    	} else if("Drug".equals(identifier)){ // 药品字典
    		msgId = Constants.DOMAIN_DRUG_DICTIONARY;
    		listName = "Drug";
    	} else if("ICD10".equals(identifier)){ // ICD10
    		msgId = Constants.DOMAIN_ICD_OUTPATIENT;
    		listName = "EmergencyTreatment";
    	} else if("yz_order_item".equals(identifier)){ // 医嘱字典
    		msgId = Constants.DOMAIN_ORDER;
    		listName = "codeOrderItemDto";
    	} else if("".equals(identifier)){// 用药途径
    		msgId = Constants.DOMAIN_SUPPLY_ROUTE;
    		listName = "supplyRoute";
    		specialName = "codeValues";
    	} else if("LABSAMPLE".equals(identifier)){ //标本类别
    		msgId = Constants.DOMAIN_SAMPLE_TYPE;
    		listName = "samplesType";
    		specialName = "codeValues";
    	} else if("yz_supply".equals(identifier)){ //频率字典
    		msgId = Constants.DOMAIN_MEDICINE_FREQ;
    		listName = "medicineFreq";
    		specialName = "codeValues";
    	} else if("Undrug".equals(identifier)){
    		String classCode = (String) map.get("classCode");
    		if(Constants.GD_EXAM_ORDER.equals(classCode)){
    			msgId = Constants.DOMAIN_EXAM_ITEM;
    			specialName = "ExamItem";
    		} else {
    			msgId = Constants.DOMAIN_LAB_ITEM;
    			specialName = "labItems";
    		}
    		listName = "labOrExam";
    	}
    	
    	List list = (List) map.get(listName);
    	Map dic = (Map) list.get(0);
    	String newUpFlag = (String) dic.get("newUpFlag");
    	if(Constants.V2_DIC_NEW_FLAG.equals(newUpFlag)){// 字典新增
    		newUpFlag = Constants.INSERT_MESSAGE_FLAG;
    	} else if(Constants.V2_DIC_UPDATE_FLAG.equals(newUpFlag) 
    			|| Constants.V2_DIC_ENABLE_FLAG.equals(newUpFlag)){ // 字典更新，字典恢复有效
    		newUpFlag = Constants.UPDATE_MESSAGE_FLAG;
    	} else if(Constants.V2_DIC_DELETE_FLAG.equals(newUpFlag) 
    			|| Constants.V2_DIC_DISABLE_FLAG.equals(newUpFlag)){// 字典删除，字典失效
    		newUpFlag = Constants.DELETE_MESSAGE_FLAG;
    	}
    	dic.put("newUpFlag", newUpFlag);   	
    	dic.put("versionNo", "0");
    	map.put("msgId", msgId);
    	if(specialName != null){
    		map.put(specialName, list);
    	}
    	return msgId;
    }
    
    /**
     * 修改map中值的位置，并返回服务id
     * @return 返回对应的服务ID
     * @throws Exception 
     * */
    private String ompo09Modify(Map map) throws Exception{
    	String serviceId = null;
    	String orderType = (String) map.get("orderType");
    	String visitType = (String) map.get("visitType");
    	String newUpFlag = (String) map.get("newUpFlag");
    	
    	// 住院根据orderStatus区分场景
    	if(Constants.VISIT_TYPE_CODE_INPATIENT.equals(visitType)){
    		String orderStatus = (String) map.get("orderStatus");
    		if("2".equals(orderStatus)){
    			// 开立医嘱
    			newUpFlag = Constants.V2_NEW_MESSAGE_FLAG;
    			map.put("newUpFlag", newUpFlag);
    			map.put("triggerEvent", newUpFlag);
    			map.put("triggerEventCode", newUpFlag);
    			map.put("newUpFlg", newUpFlag);
    		} else if("3".equals(orderStatus)){
    			// 确认医嘱
    			newUpFlag = Constants.V2_CONFIRM_OR_CHARGE_MESSAGE_FLAG;
    			map.put("newUpFlag", newUpFlag);
    			map.put("triggerEvent", newUpFlag);
    			map.put("triggerEventCode", newUpFlag);
    			map.put("newUpFlg", newUpFlag);
    		} else if("5".equals(orderStatus)){
    			// 停止长期医嘱
    			newUpFlag = Constants.STOP_MESSAGE_FLAG;
    			map.put("newUpFlag", newUpFlag);
    			map.put("triggerEvent", newUpFlag);
    			map.put("triggerEventCode", newUpFlag);
    			map.put("newUpFlg", newUpFlag);
    		} else if("6".equals(orderStatus)){
    			// 撤销临时医嘱
    			newUpFlag = Constants.CANCEL_MESSAGE_FLAG;
    			map.put("newUpFlag", newUpFlag);    			
    			map.put("triggerEvent", newUpFlag);
    			map.put("triggerEventCode", newUpFlag);
    			map.put("newUpFlg", newUpFlag);
    		} else {
    			throw new Exception("ORC-5=" + orderStatus + "，无法识别消息场景，不做处理");
    		}
    	}
    	// 住院医嘱有确认消息
    	if(Constants.V2_CONFIRM_OR_CHARGE_MESSAGE_FLAG.equals(newUpFlag)){
    		serviceId = "BS004";
        	orderType = (String) map.get("orderType");
        	orderType = changeOrderType(orderType);
        	List orderList = getOrderListFromMap(map, serviceId);
        	for(int i = 0; i < orderList.size(); i ++){
        		Map order = (Map) orderList.get(i);   		
        		order.put("orderType", orderType);
        		order.put("orderStatus", Constants.ORDER_STATUS_CONFIRMED);
        	}
    	}
    	else if(Constants.CANCEL_MESSAGE_FLAG.equals(newUpFlag)
    			|| Constants.STOP_MESSAGE_FLAG.equals(newUpFlag)
    			|| Constants.V2_CANCEL_MESSAGE_FLAG.equals(newUpFlag)){
    		serviceId = "BS005";
    		orderType = changeOrderType(orderType);
    		String orderTypeName = DictionaryUtils.getNameFromDictionary(Constants.DOMAIN_ORDER_TYPE, orderType);
    		List orderList = getOrderListFromMap(map, serviceId);
    		for(int i = 0; i < orderList.size(); i++){
    			Map order = (Map) orderList.get(i);
    			order.put("orderType", orderType);
    			order.put("orderTypeName", orderTypeName);
    		}
    	} 
    	else if(Constants.VISIT_TYPE_CODE_OUTPATIENT.equals(visitType) && ("P".equals(orderType) || "PCC".equals(orderType) || "PCZ".equals(orderType))){
    		//处方ok。门诊可能一个消息中包含多个药品，ORC-4相同。住院中一个药品作为一个处方
        	serviceId = "BS302";
        	List medicationOrderBatchList = (List) map.get("medicationOrderBatch");
        	List prescriptionsList = (List) map.get("prescriptions");
        	/*
        	 * 消息适配器不支持medicationOrderBatch嵌套在prescriptions中取出
        	 * 并且prescriptions处方信息实际上只有一个，消息映射到Map的时候生成多份
        	 * 门诊一条消息一个处方号，急诊一个消息中可能有多个处方，将药品按照处方号分组放入相应处方中
        	 * */
        	if(Constants.VISIT_TYPE_CODE_INPATIENT.equals(visitType)){
//        		for(int i = 0; i < prescriptionsList.size(); i++){
//        			Map prescriptions = (Map) prescriptionsList.get(i);
//        			List mo = new ArrayList();
//        			mo.add(medicationOrderBatchList.get(i));
//        			prescriptions.put("medicineOrder", mo);
//        		}
        	} else {
            	if(prescriptionsList.size() >= 1){
//            		Map prescriptions = (Map) prescriptionsList.get(0);
//            		prescriptionsList.removeAll(prescriptionsList);
//            		prescriptionsList.add(prescriptions);
//            		if(prescriptions != null){
//            			prescriptions.put("medicineOrder", medicationOrderBatchList);
//            		}
            		Map prescriptionMedicineOrderMap = new HashMap();
            		for(Object pres : prescriptionsList){
            			Map prescriptions = (Map) pres;
            			String preNo = (String) prescriptions.get("prescriptionNo");
            			prescriptionMedicineOrderMap.put(preNo, prescriptions);
            		}
            		for(Object obj : medicationOrderBatchList){
            			Map mOrder = (Map) obj;
            			String preNo = (String) mOrder.get("prescriptionNo");
            			Map prescriptions = (Map) prescriptionMedicineOrderMap.get(preNo);
            			if(prescriptions == null) continue;
            			else {
            				List mOrderList = (List) prescriptions.get("medicineOrder");
            				if(mOrderList == null){
            					mOrderList = new ArrayList();
            					prescriptions.put("medicineOrder", mOrderList);
            				}
            				mOrderList.add(mOrder);
            			}
            		}
            		prescriptionsList.clear();
            		prescriptionsList.addAll(prescriptionMedicineOrderMap.values());
            	}
        	}
        	for(int i = 0; i < medicationOrderBatchList.size(); i++){
        		Map m = (Map) medicationOrderBatchList.get(i);
        		if(m != null){
        			m.put("orderType", Constants.MEDICATION_ORDER);
        			m.put("orderTypeName", DictionaryUtils.getNameFromDictionary(
        					Constants.DOMAIN_ORDER_TYPE, Constants.MEDICATION_ORDER));
        		}
        	}
    	}
    	//港大住院用药医嘱
		else if(Constants.VISIT_TYPE_CODE_INPATIENT.equals(visitType) && ("P".equals(orderType) || "PCC".equals(orderType) || "PCZ".equals(orderType))){
			// P西药，PCC中草药，PCZ中成药
			serviceId = "BS311";
			List medicationOrderBatchList = (List) map.get("medicationOrderBatch");
			for(int i = 0; i < medicationOrderBatchList.size(); i++){
        		Map m = (Map) medicationOrderBatchList.get(i);
        		if(m != null){
        			m.put("orderType", Constants.MEDICATION_ORDER);
        			m.put("orderTypeName", DictionaryUtils.getNameFromDictionary(
        					Constants.DOMAIN_ORDER_TYPE, Constants.MEDICATION_ORDER));
        		}
        	}
		}
    	else if("UZ".equals(orderType)){
			// 治疗ok
			serviceId = "BS303";
			List treatmentItemList = (List) map.get("treatmentItem");
			for(int i = 0; i < treatmentItemList.size(); i++){
        		Map m = (Map) treatmentItemList.get(i);
        		if(m != null){
        			m.put("orderType", Constants.TREATMENT_ORDER);
        			m.put("orderTypeName", "");
        		}
        	}
		}
		else if("UN".equals(orderType)){
			// 护理ok
			serviceId = "BS304";
			List careOrderBatchList = (List) map.get("careOrderBatch");
			for(int i = 0; i < careOrderBatchList.size(); i++){
        		Map m = (Map) careOrderBatchList.get(i);
        		if(m != null){
        			m.put("orderType", Constants.CARE_ORDER);
        			m.put("orderTypeName", "");
        		}
        	}
		}
		else if("UC".equals(orderType)){
			// 检查ok
			serviceId = "BS002";
			orderType = Constants.EXAMINATION_ORDER;
			String orderTypeName = DictionaryUtils.getNameFromDictionary(Constants.DOMAIN_ORDER_TYPE, Constants.EXAMINATION_ORDER);
			List examinationApplicationsList = (List) map.get("examinationApplications");
			List examinationOrderDtosList = (List) map.get("examinationOrderDtos");
			for(int i = 0; i < examinationApplicationsList.size(); i++){
				Map examinationApplications = (Map) examinationApplicationsList.get(i);
				List examinationOrderDtos = new ArrayList();
				examinationOrderDtos.add((Map) examinationOrderDtosList.get(i));
				examinationApplications.put("examinationOrderDtos", examinationOrderDtos);
				examinationApplications.put("orderType", orderType);
				examinationApplications.put("orderTypeName", orderTypeName);
			}
		}
		else if("UL".equals(orderType)){
			// 检验ok
			serviceId = "BS006";
			orderType = Constants.LAB_ORDER;
			String orderTypeName = DictionaryUtils.getNameFromDictionary(Constants.DOMAIN_ORDER_TYPE, Constants.LAB_ORDER);
			List requestListList = (List) map.get("requestList");
			List labOrderDtoList = (List) map.get("labOrderDto");
			for(int i = 0; i < requestListList.size(); i++){
				Map requestList = (Map) requestListList.get(i);
				requestList.put("orderType", orderType);
				requestList.put("orderTypeName", orderTypeName);
				Map labOrderDto = (Map) labOrderDtoList.get(i);
				List _labOrderDto = new ArrayList();
				_labOrderDto.add(labOrderDto);
				requestList.put("labOrderDto", _labOrderDto);				
			}
		}
		else if("UO".equals(orderType)){
			// 手术ok
			serviceId = "BS007";
/*			orderType = Constants.PROCEDURE_ORDER;
			String orderTypeName = DictionaryUtils.getNameFromDictionary(Constants.DOMAIN_ORDER_TYPE, Constants.PROCEDURE_ORDER);*/
			List pv1 = (List) map.get("PV1");
			List pid = (List) map.get("PID");
			List proOrdersList = (List) map.get("proOrders");
			List medeicalVisit = (List) map.get("medicaVisit");
			for(int i = 0; i < proOrdersList.size(); i++){
				Map proOrders = (Map) proOrdersList.get(i);
				if(proOrders != null){
					proOrders.putAll((Map) pv1.get(0));
					proOrders.putAll((Map) pid.get(0));
					proOrders.put("medicaVisit", medeicalVisit);
					String domainID = (String) proOrders.get("domainID");
					domainID = getPatientDomainValue(domainID);
					proOrders.put("domainID", domainID);
				}
			}
		}
		else {
			// 其他医嘱ok
			serviceId = "BS305";
			List pv1 = (List) map.get("PV1");
			String patientDomain = (String) ((Map) pv1.get(0)).get("patientDomain");
			// 转换域Id
			((Map) pv1.get(0)).put("patientDomain", getPatientDomainValue(patientDomain));
			List pid = (List) map.get("PID");
			List otherOrderList = (List) map.get("otherOrder");
			List orderItemsList = (List) map.get("OrderItems");
			for(int i = 0; i < otherOrderList.size(); i++){
				Map otherOrder = (Map) otherOrderList.get(i);
				if(otherOrder != null){
					otherOrder.putAll((Map) pv1.get(0));
					otherOrder.putAll((Map) pid.get(0));
					List list = new ArrayList();
					Map orderItem = (Map) orderItemsList.get(i);
					list.add(orderItem);
					otherOrder.put("OrderItems", list);
				}
			}
		}   	
    	return serviceId;
    }
    
    private String changeOrderType(String inType){
    	String outType;
		if("UC".equals(inType)){//检查
			outType = Constants.EXAMINATION_ORDER;
		} else if("UL".equals(inType)){//检验
			outType = Constants.LAB_ORDER;
		} else if("UO".equals(inType)){//手术
			outType = Constants.PROCEDURE_ORDER;
		} else if("UZ".equals(inType)){//治疗
			outType = Constants.TREATMENT_ORDER;
		} else if("UN".equals(inType)){//护理
			outType = Constants.CARE_ORDER;
		} else if("P".equals(inType) || "PCC".equals(inType) || "PCZ".equals(inType)){//P西药，PCC中草药，PCZ中成药
			outType = Constants.MEDICATION_ORDER;
		} else {// 其他
			outType = Constants.GENERAL_ORDER;
		}
    	return outType;
    }
    
    /*
     * 医嘱确认发OMP^O09 ORC-5=3，同时也会发RAS^O17
     * */
    private String raso17Modify(Map map) throws Exception{
    	String serviceId = "BS004";
    	String orderType = (String) map.get("orderType");
    	orderType = changeOrderType(orderType);
    	String newUpFlag = (String) map.get("newUpFlag");
    	String orderStatus = null;
    	if(Constants.V2_CONFIRM_OR_CHARGE_MESSAGE_FLAG.equals(newUpFlag)){
    		// 医嘱已确认
    		orderStatus = Constants.ORDER_STATUS_CONFIRMED;
    	} else {
    		throw new Exception("ORC-1=" + newUpFlag + "，无法识别消息场景，不做处理");
    	}
    	
/*    	if(Constants.MEDICATION_ORDER.equals(orderType)){
    		// 用药已开始
    		orderStatus = Constants.ORDER_STATUS_MEDICATION_START;
    	} else if(Constants.CARE_ORDER.equals(orderType)){
    		// 护理已完成
    		orderStatus = Constants.ORDER_STATUS_CARE_FINISH;
    	} else if(Constants.TREATMENT_ORDER.equals(orderType)){
    		// 治疗已完成
    		orderStatus = Constants.ORDER_STATUS_TREATMENT_FINISH;
    	} else if(Constants.EXAMINATION_ORDER.equals(orderType)){
    		// 检查已完成
    		orderStatus = Constants.ORDER_STATUS_EXAM_FINISH;
    	} else if(Constants.LAB_ORDER.equals(orderType)){
    		// 检验已完成
    		orderStatus = Constants.ORDER_STATUS_LAB_FINISH;
    	} else if(Constants.PROCEDURE_ORDER.equals(orderType)){
    		// 手术已执行
    		orderStatus = Constants.ORDER_STATUS_PROCEDURE_FINISH;
    	}*/
    	
    	List orderList = getOrderListFromMap(map, serviceId);
    	for(int i = 0; i < orderList.size(); i ++){
    		Map order = (Map) orderList.get(i);   		
    		order.put("orderType", orderType);
    		order.put("orderStatus", orderStatus);
    	}
    	return serviceId;
    }
    
    private String rgvo15Modify(Map map){
    	/*
    	 * bean中prescription处方段[drugs药品段]
    	 * Map中的drugs若放在prescription中无法取出。prescription数组中的一条对应drugs中的一条。
    	 * prescription和drugs中也有无法直接取出来的内容，放入detail中，再取出来置入prescription和drugs中。
    	 * */
    	String serviceId = "BS307";
    	List prescriptionList = (List) map.get("prescription");
    	List drugsList = (List) map.get("drugs");
    	String patientLid = (String) map.get("patientLid");
    	
    	String visitType = (String) map.get("visitType");
    	if(Constants.VISIT_TYPE_CODE_INPATIENT.equals(visitType)){
        	for(int i = 0; i < prescriptionList.size(); i++){
        		Map prescription = (Map) prescriptionList.get(i);
        		Map drugMap = (Map) drugsList.get(i);
        		// 门诊住院 医嘱号位置不同，做特殊处理
        		String prescriptionNo = (String) prescription.get("prescriptionNo2");
        		String orderLid = (String) drugMap.get("orderId2");
        		prescription.put("prescriptionNo", prescriptionNo);
        		drugMap.put("orderId", orderLid);
        	}
    	}
    	
    	for(int i = 0; i < prescriptionList.size(); i++){
    		Map prescription = (Map) prescriptionList.get(i);
    		prescription.put("receivePersonId", patientLid);
    		
    		List detail = (List) prescription.get("detail");
    		prescription.putAll((Map) detail.get(0));
    		prescription.remove("detail");
    		
    		List drugs = new ArrayList();
    		prescription.put("drugs", drugs);
    		Map drugMap = (Map) drugsList.get(i);
    		List drugDetail = (List) drugMap.get("detail");
    		drugMap.putAll((Map) drugDetail.get(0));
    		drugs.add(drugMap);
    		drugMap.remove("detail");
    	}
    	// 把处方号相同的prescription合并
    	Map<String, Map> mergedPrescriptionMap = new HashMap<String, Map>();
    	for(int i = 0; i < prescriptionList.size(); i++){
    		Map prescription = (Map) prescriptionList.get(i);
    		String prescriptionNo = (String) prescription.get("prescriptionNo");
    		Map p = mergedPrescriptionMap.get(prescriptionNo);
    		if(p == null){
    			p = prescription;
    			mergedPrescriptionMap.put(prescriptionNo, p);
    		} else {
    			List drugs = (List) prescription.get("drugs");
    			List drugs2 = (List) p.get("drugs");
    			drugs2.addAll(drugs);
    		}
    	}
    	List prescription = new ArrayList();
    	prescription.addAll(mergedPrescriptionMap.values());
    	map.put("prescription", prescription);
     	return serviceId;
    }
    
    private void oulr21Modify(Map map, String result){
    	map.put("newUpFlag", Constants.NEW_MESSAGE_FLAG);
		List orderList = getOrderListFromMap(map, result);
		if(orderList != null && !orderList.isEmpty()){
			List<Object> unique = new ArrayList<Object>();
			for(Object o : orderList){
				if(!unique.contains(o)){
					unique.add(o);
				}
			}
			orderList.clear();
			orderList.addAll(unique);
			setOrderTypeAndName(orderList, Constants.LAB_ORDER, "检验医嘱");
			setOrderStatus(orderList, Constants.ORDER_STATUS_LAB_RESULT_REVIEWED);
		}
    }
    
    private void getPatientEid(Map map){
    	List pid_3 = (List) map.get("pid3");
    	if(pid_3 == null) return;
    	Map pid3 = (Map) pid_3.get(0);
    	Map<String, String> list = new HashMap<String, String>();
    	list.put((String) pid3.get("p11"), (String) pid3.get("p12"));
    	list.put((String) pid3.get("p21"), (String) pid3.get("p22"));
    	list.put((String) pid3.get("p31"), (String) pid3.get("p32"));
    	list.put((String) pid3.get("p41"), (String) pid3.get("p42"));
    	list.put((String) pid3.get("p51"), (String) pid3.get("p52"));
    	list.put((String) pid3.get("p61"), (String) pid3.get("p62"));
    	
    	Iterator<Entry<String, String>> i = list.entrySet().iterator();
    	while(i.hasNext()){
    		Entry<String, String> e = i.next();
    		if("UPID".equals(e.getKey())){
    			map.put("patientEid", e.getValue());
    			break;
    		}
    	}
    }
}
