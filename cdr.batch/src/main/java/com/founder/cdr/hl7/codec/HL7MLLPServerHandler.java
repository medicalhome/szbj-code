package com.founder.cdr.hl7.codec;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.uhn.hl7v2.AcknowledgmentCode;
import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;

import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.Segment;
import ca.uhn.hl7v2.model.Type;
import ca.uhn.hl7v2.model.Varies;
import ca.uhn.hl7v2.model.primitive.ID;
import ca.uhn.hl7v2.model.v24.datatype.CWE;
import ca.uhn.hl7v2.model.v24.group.OMP_O09_ORDER;
import ca.uhn.hl7v2.model.v24.group.RAS_O17_ORDER;
import ca.uhn.hl7v2.model.v24.message.OMP_O09;
import ca.uhn.hl7v2.model.v24.message.RAS_O17;
import ca.uhn.hl7v2.model.v24.segment.MSH;
import ca.uhn.hl7v2.model.v24.segment.ORC;
import ca.uhn.hl7v2.parser.Parser;

import com.founder.cdr.batch.core.Config;
import com.founder.cdr.batch.core.Hl7Message;
import com.founder.cdr.batch.core.MessagePool;
import com.founder.cdr.core.Constants;
import com.founder.cdr.hl7.v2Model.message.SplitedOMP_O09;
import com.founder.cdr.hl7.v2Model.message.SplitedRAS_O17;
import com.founder.cdr.util.ExceptionUtils;
import com.founder.cdr.util.SpringBeanFactory;
 
public class HL7MLLPServerHandler extends SimpleChannelInboundHandler<Object> 
{
	private static final Logger logger = LoggerFactory.getLogger(HL7MLLPServerHandler.class);
	
    private MessagePool pool;
	
    private static HapiContext context;
    
    private static Parser p;
    
    private synchronized void createHapi() {
    	 context = new DefaultHapiContext();
		 p = context.getGenericParser();
    }
	
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception
    {
    	SocketAddress sa = ctx.channel().remoteAddress();
    	logger.debug("连接端：{}", sa.toString());
    	
        // 产生ACK消息
    	Message ack = null;
    	Message hapiMsg = null;
        if(msg == null)
            ack = null;
        else{
        	try{
        		logger.debug("开始获取下一条消息..............");
        		boolean showMessageInLog = Boolean.valueOf(Constants.SHOW_MESSAGE_IN_LOG);
        		if(showMessageInLog){
            		logger.debug("消息内容：\n{}", msg);
            		logger.debug("消息内容End....................");
        		}        			
        		if(context == null){
        			createHapi();
        		}
        		pool = (MessagePool)SpringBeanFactory.getBean("messagePool");
        		
        		//V2消息
        		if(msg.toString().startsWith("MSH")){
        			String msgString = msg.toString();
        			//将消息中的\n换成\r。消息解析器只根据\r来分段
        			// HL7MLLPConfig中convertLFtoCR设置为true已替换\n成\r
//        			msgString = replaceRN(msgString);
        			hapiMsg = p.parse(msgString);
        			MSH msh = (MSH) hapiMsg.get("MSH");
            		String modelType = msh.getMessageType().getMessageType()+"_"+msh.getMessageType().getMsg2_TriggerEvent();
            		ack = hapiMsg.generateACK();
            		if("CDA_ZDA".equals(modelType) || "ADT_ZV3".equals(modelType)){
            			Segment s;
            			Type[] tArr;
            			if("CDA_ZDA".equals(modelType)){// CDA文档           				
            				s = (Segment) hapiMsg.get("ZDA");
            				tArr = s.getField(8);
            			} else {// EMPI
            				s = (Segment) hapiMsg.get("ZV3");
            				tArr = s.getField(1);
            			}           			
            			Varies v = (Varies) tArr[0];
            			String str = v.getData().toString();
            			// base64编码中可能包含导致解码错误的字符串, 需要去除
            			str = replaceUselessStringFromBase64(str);
            			byte[] afterDecode = Base64.decodeBase64(str);
            			// 设置编码格式
            			Object obj = new String(afterDecode, Constants.BASE64_FORMAT);
            			// V3消息解析
            			pool.addMessage(constructMessage(obj));
            		} else if("OMP_O09".equals(modelType)){
            			// OMP^O09消息中，多个种类的医嘱可能会在同一个消息里，按照医嘱类别将OMP^O09消息拆分
            			OMP_O09 ompo09 = (OMP_O09) hapiMsg;
            			List<OMP_O09> splitMessageList = splitOMP_O09(ompo09);
            			for(OMP_O09 o : splitMessageList){
            				if(o != null && !o.isEmpty()){
            					pool.addMessage(constructMessage(o));
            				}
            			}
            		}  else if("RAS_O17".equals(modelType)){
            			// 同OMP^O09
            			RAS_O17 raso17 = (RAS_O17) hapiMsg;
            			List<RAS_O17> splitMessageList = splitRAS_O17(raso17);
            			for(RAS_O17 o : splitMessageList){
            				if(o != null && !o.isEmpty()){
            					pool.addMessage(constructMessage(o));
            				}
            			}
            		} else{
            			// 普通V2消息
            			pool.addMessage(constructMessage(hapiMsg));
            		}
            		ChannelFuture f = ctx.channel().writeAndFlush(ack.getMessage().toString());
        		} else {
        			// V3消息
        			pool.addMessage(constructMessage((Object) msg));
        			ChannelFuture f = ctx.channel().writeAndFlush("V3 OK");
        		}
        		logger.debug("成功获取消息。。。。。。");
        	}catch (HL7Exception e){
        		logger.error("消息转换失败：\r\n{}",ExceptionUtils.getStackTrace(e));
        		ChannelFuture f = ctx.channel().writeAndFlush(e.getMessage());
        	}catch (Exception e){
        		logger.error("消息接收保存失败：\r\n{}",ExceptionUtils.getStackTrace(e));
        		ack = hapiMsg.generateACK(AcknowledgmentCode.AE, new HL7Exception(e));
        		ChannelFuture f = ctx.channel().writeAndFlush("NG");
        	}
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx)
    {
        ctx.flush();
    }
    
    /**
     * 构建V2的message
     * @param message
     * @return
     * @throws Exception
     */
    private Hl7Message constructMessage(Message message) throws Exception
    {
        Hl7Message result = new Hl7Message();
        //取得Server的端口
        result.setQueueName(Config.getConfig(Constants.SOCKET_SERVER_PORT_KEY));
        result.setMsgMode("V2");
        MSH msh = (MSH) message.get("MSH");
    	result.setMessageType(msh.getMessageType().getMessageType()+"_"+msh.getMessageType().getMsg2_TriggerEvent());
    	result.setServiceId(msh.getMessageType().getMessageType()+"_"+msh.getMessageType().getMsg2_TriggerEvent());
        Calendar date = GregorianCalendar.getInstance();
        result.setJMSTimestamp(date.getTimeInMillis());
        result.setText(message.getMessage().toString());
        return result;
    }
    
    /**
     * 构建Message V3
     * @param message
     * @return
     * @throws Exception
     */
    private Hl7Message constructMessage(Object message) throws Exception
    {
        Hl7Message result = new Hl7Message();
        //result.setQueueName("18088");
        result.setQueueName(Config.getConfig(Constants.SOCKET_SERVER_PORT_KEY));
        result.setMsgMode("V3");
    	if(message.toString().indexOf("PRPA_IN201301UV02") > -1){
    		result.setMessageType("PRPAIN201302UV01");
        	result.setServiceId("BS512");
    	}else if(message.toString().indexOf("PRPA_IN201302UV02") > -1){
    		result.setMessageType("PRPAIN201302UV02");
        	result.setServiceId("BS513");
    	}else if(message.toString().indexOf("PRPA_IN201303UV02") > -1){
    		result.setMessageType("PRPAIN201302UV03");
        	result.setServiceId("BS514");
    	}else if(message.toString().indexOf("extension=\"BS320\"") >-1){//检查报告
    		result.setMessageType("POCDIN000040UV02");
        	result.setServiceId("BS320");
    	}else if(message.toString().indexOf("extension=\"BS366\"") >-1){
    		result.setMessageType("POCDIN000040UV02");
        	result.setServiceId("BS366");
    	}else if(message.toString().indexOf("extension=\"BS368\"") >-1){
    		result.setMessageType("POCDIN000040UV02");
        	result.setServiceId("BS368");
    	}else if(message.toString().indexOf("extension=\"BS369\"") >-1){
    		result.setMessageType("POCDIN000040UV02");
        	result.setServiceId("BS369");
    	}else if(message.toString().indexOf("extension=\"BS319\"") >-1){//检验报告
    		result.setMessageType("POCDIN000040UV01");
        	result.setServiceId("BS319");
    	}else if(message.toString().indexOf("extension=\"BS354\"") >-1){
    		result.setMessageType("POCDIN000040UV01");
        	result.setServiceId("BS354");
    	}else if(message.toString().indexOf("extension=\"BS322\"") >-1){//病例文书
    		result.setMessageType("POCDIN000040UV03");
        	result.setServiceId("BS322");
    	}else if(message.toString().indexOf("extension=\"BS323\"") >-1){
    		result.setMessageType("POCDIN000040UV03");
        	result.setServiceId("BS323");
    	}else if(message.toString().indexOf("extension=\"BS324\"") >-1){
    		result.setMessageType("POCDIN000040UV03");
        	result.setServiceId("BS324");
    	}else if(message.toString().indexOf("extension=\"BS325\"") >-1){
    		result.setMessageType("POCDIN000040UV03");
        	result.setServiceId("BS325");
    	}else if(message.toString().indexOf("extension=\"BS326\"") >-1){
    		result.setMessageType("POCDIN000040UV03");
        	result.setServiceId("BS326");
    	}else if(message.toString().indexOf("extension=\"BS327\"") >-1){
    		result.setMessageType("POCDIN000040UV03");
        	result.setServiceId("BS327");
    	}else if(message.toString().indexOf("extension=\"BS328\"") >-1){
    		result.setMessageType("POCDIN000040UV03");
        	result.setServiceId("BS328");
    	}else if(message.toString().indexOf("extension=\"BS329\"") >-1){
    		result.setMessageType("POCDIN000040UV03");
        	result.setServiceId("BS329");
    	}else if(message.toString().indexOf("extension=\"BS379\"") >-1){
    		result.setMessageType("POCDIN000040UV03");
        	result.setServiceId("BS379");
    	}else if(message.toString().indexOf("extension=\"BS331\"") >-1){
    		result.setMessageType("POCDIN000040UV03");
        	result.setServiceId("BS331");
    	}else if(message.toString().indexOf("extension=\"BS332\"") >-1){
    		result.setMessageType("POCDIN000040UV03");
        	result.setServiceId("BS332");
    	}else if(message.toString().indexOf("extension=\"BS333\"") >-1){
    		result.setMessageType("POCDIN000040UV03");
        	result.setServiceId("BS333");
    	}else if(message.toString().indexOf("extension=\"BS334\"") >-1){
    		result.setMessageType("POCDIN000040UV03");
        	result.setServiceId("BS334");
    	}else if(message.toString().indexOf("extension=\"BS335\"") >-1){
    		result.setMessageType("POCDIN000040UV03");
        	result.setServiceId("BS335");
    	}else if(message.toString().indexOf("extension=\"BS336\"") >-1){
    		result.setMessageType("POCDIN000040UV03");
        	result.setServiceId("BS336");
    	}else if(message.toString().indexOf("extension=\"BS337\"") >-1){
    		result.setMessageType("POCDIN000040UV03");
        	result.setServiceId("BS337");
    	}else if(message.toString().indexOf("extension=\"BS338\"") >-1){
    		result.setMessageType("POCDIN000040UV03");
        	result.setServiceId("BS338");
    	}else if(message.toString().indexOf("extension=\"BS340\"") >-1){
    		result.setMessageType("POCDIN000040UV03");
        	result.setServiceId("BS340");
    	}else if(message.toString().indexOf("extension=\"BS341\"") >-1){
    		result.setMessageType("POCDIN000040UV03");
        	result.setServiceId("BS341");
    	}else if(message.toString().indexOf("extension=\"BS342\"") >-1){
    		result.setMessageType("POCDIN000040UV03");
        	result.setServiceId("BS342");
    	}else if(message.toString().indexOf("extension=\"BS343\"") >-1){
    		result.setMessageType("POCDIN000040UV03");
        	result.setServiceId("BS343");
    	}else if(message.toString().indexOf("extension=\"BS344\"") >-1){
    		result.setMessageType("POCDIN000040UV03");
        	result.setServiceId("BS344");
    	}else if(message.toString().indexOf("extension=\"BS345\"") >-1){
    		result.setMessageType("POCDIN000040UV03");
        	result.setServiceId("BS345");
    	}else if(message.toString().indexOf("extension=\"BS347\"") >-1){
    		result.setMessageType("POCDIN000040UV03");
        	result.setServiceId("BS347");
    	}else if(message.toString().indexOf("extension=\"BS348\"") >-1){
    		result.setMessageType("POCDIN000040UV03");
        	result.setServiceId("BS348");
    	}else if(message.toString().indexOf("extension=\"BS349\"") >-1){//入院护理评估信息服务
    		result.setMessageType("POCDIN000040UV03");
        	result.setServiceId("BS349");
    	}else if(message.toString().indexOf("extension=\"BS350\"") >-1){
    		result.setMessageType("POCDIN000040UV03");
        	result.setServiceId("BS350");
    	}else if(message.toString().indexOf("extension=\"BS351\"") >-1){
    		result.setMessageType("POCDIN000040UV03");
        	result.setServiceId("BS351");
    	}else if(message.toString().indexOf("extension=\"BS356\"") >-1){
    		result.setMessageType("POCDIN000040UV03");
        	result.setServiceId("BS356");
    	}else if(message.toString().indexOf("extension=\"BS357\"") >-1){
    		result.setMessageType("POCDIN000040UV03");
        	result.setServiceId("BS357");
    	}else if(message.toString().indexOf("extension=\"BS358\"") >-1){
    		result.setMessageType("POCDIN000040UV03");
        	result.setServiceId("BS358");
    	}else if(message.toString().indexOf("extension=\"BS360\"") >-1){
    		result.setMessageType("POCDIN000040UV03");
        	result.setServiceId("BS360");
    	}else if(message.toString().indexOf("extension=\"BS370\"") >-1){
    		result.setMessageType("POCDIN000040UV03");
        	result.setServiceId("BS370");
    	}else if(message.toString().indexOf("extension=\"BS371\"") >-1){
    		result.setMessageType("POCDIN000040UV03");
        	result.setServiceId("BS371");
    	}else if(message.toString().indexOf("extension=\"BS372\"") >-1){
    		result.setMessageType("POCDIN000040UV03");
        	result.setServiceId("BS372");
    	}else if(message.toString().indexOf("extension=\"BS373\"") >-1){
    		result.setMessageType("POCDIN000040UV03");
        	result.setServiceId("BS373");
    	}else if(message.toString().indexOf("extension=\"BS374\"") >-1){
    		result.setMessageType("POCDIN000040UV03");
        	result.setServiceId("BS374");
    	}else if(message.toString().indexOf("extension=\"BS375\"") >-1){
    		result.setMessageType("POCDIN000040UV03");
        	result.setServiceId("BS375");
    	}else if(message.toString().indexOf("extension=\"BS376\"") >-1){
    		result.setMessageType("POCDIN000040UV03");
        	result.setServiceId("BS376");
    	}else if(message.toString().indexOf("extension=\"BS377\"") >-1){
    		result.setMessageType("POCDIN000040UV03");
        	result.setServiceId("BS377");
    	}else if(message.toString().indexOf("extension=\"BS378\"") >-1){
    		result.setMessageType("POCDIN000040UV03");
        	result.setServiceId("BS378");
    	}else if(message.toString().indexOf("extension=\"BS380\"") >-1){// 首次病程记录信息服务
    		result.setMessageType("POCDIN000040UV03");
        	result.setServiceId("BS380");
    	}else if(message.toString().indexOf("extension=\"BS381\"") >-1){ //?
    		result.setMessageType("POCDIN000040UV03");
        	result.setServiceId("BS381");
    	}else if(message.toString().indexOf("extension=\"BS382\"") >-1){//接班记录信息服务
    		result.setMessageType("POCDIN000040UV03");
        	result.setServiceId("BS382");
    	}else if(message.toString().indexOf("extension=\"BS383\"") >-1){//?
    		result.setMessageType("POCDIN000040UV03");
        	result.setServiceId("BS383");
    	}else if(message.toString().indexOf("extension=\"BS384\"") >-1){//?
    		result.setMessageType("POCDIN000040UV03");
        	result.setServiceId("BS384");
    	}else if(message.toString().indexOf("extension=\"BS385\"") >-1){//抢救记录信息服务
    		result.setMessageType("POCDIN000040UV03");
        	result.setServiceId("BS385");
    	}else if(message.toString().indexOf("extension=\"BS386\"") >-1){//术后首次记录信息服务
    		result.setMessageType("POCDIN000040UV03");
        	result.setServiceId("BS386");
    	}else if(message.toString().indexOf("extension=\"BS390\"") >-1){//?
    		result.setMessageType("POCDIN000040UV03");
        	result.setServiceId("BS390");
    	}else if(message.toString().indexOf("extension=\"BS401\"") >-1){//麻醉记录单附页
    		result.setMessageType("POCDIN000040UV03");
        	result.setServiceId("BS401");
    	}else if(message.toString().indexOf("extension=\"BS400\"") >-1){//分娩记录信息服务
    		result.setMessageType("POCDIN000040UV03");
        	result.setServiceId("BS400");
    	}else if(message.toString().indexOf("extension=\"BS403\"") >-1){//新生儿评估单信息服务
    		result.setMessageType("POCDIN000040UV03");
        	result.setServiceId("BS403");
    	}else if(message.toString().indexOf("extension=\"BS410\"") >-1){//护理计划信息服务
    		result.setMessageType("POCDIN000040UV03");
        	result.setServiceId("BS410");
    	}else if(message.toString().indexOf("extension=\"BS408\"") >-1){//产科首次病程记录信息服务
    		result.setMessageType("POCDIN000040UV03");
        	result.setServiceId("BS408");
    	}else if(message.toString().indexOf("extension=\"BS405\"") >-1){//插管记录信息服务
    		result.setMessageType("POCDIN000040UV03");
        	result.setServiceId("BS405");
    	}else if(message.toString().indexOf("extension=\"BS406\"") >-1){//有创操作记录信息服务
    		result.setMessageType("POCDIN000040UV03");
        	result.setServiceId("BS406");
    	}else if(message.toString().indexOf("extension=\"BS418\"") >-1){//产后记录信息服务
    		result.setMessageType("POCDIN000040UV03");
        	result.setServiceId("BS418");
    	}else if(message.toString().indexOf("extension=\"BS412\"") >-1){//产钳手术记录信息服务
    		result.setMessageType("POCDIN000040UV03");
        	result.setServiceId("BS412");
    	}else if(message.toString().indexOf("extension=\"BS392\"") >-1){//剖宫产手术记录单信息服务
    		result.setMessageType("POCDIN000040UV03");
        	result.setServiceId("BS392");
    	}else if(message.toString().indexOf("extension=\"BS420\"") >-1){//手术风险评估表信息服务
    		result.setMessageType("POCDIN000040UV03");
        	result.setServiceId("BS420");
    	}else if(message.toString().indexOf("extension=\"BS413\"") >-1){//胎头吸引手术记录信息服务
    		result.setMessageType("POCDIN000040UV03");
        	result.setServiceId("BS413");
    	}else if(message.toString().indexOf("extension=\"BS417\"") >-1){//心脏介入诊疗手术记录信息服务
    		result.setMessageType("POCDIN000040UV03");
        	result.setServiceId("BS417");
    	}else if(message.toString().indexOf("extension=\"BS421\"") >-1){//重大手术特殊手术备案表信息服务
    		result.setMessageType("POCDIN000040UV03");
        	result.setServiceId("BS421");
    	}else if(message.toString().indexOf("extension=\"BS422\"") >-1){//输血不良反应信息反馈表信息服务
    		result.setMessageType("POCDIN000040UV03");
        	result.setServiceId("BS422");
    	}else if(message.toString().indexOf("extension=\"BS419\"") >-1){//临床药师用药史和药物整合记录表信息服务
    		result.setMessageType("POCDIN000040UV03");
        	result.setServiceId("BS419");
    	}
    	//TODO 加其他的病例文书
    	else{
    		logger.debug("没有找到服务ID................................");
    		result.setMessageType("POCDIN000040UV03");
        	result.setServiceId("");
    	}
        Calendar date = GregorianCalendar.getInstance();
        result.setJMSTimestamp(date.getTimeInMillis());
        result.setText(message.toString());
        return result;
    }
    
    private String replaceUselessStringFromBase64(String base64){
    	// 字符串 \X0A\和\X0D\。\r \n回车和换行符的16进制编码 在base64中需过滤掉，否则会导致解码后大量乱码，生成错误的XML
    	String[] useless = {"\\\\X..\\\\"}; 
    	String replaced = base64;
    	for(String s : useless){
    		replaced = replaced.replaceAll(s, "");
    	}
    	return replaced;
    }
    
    private String replaceRN(String msg){
    	String target = "\n";
    	String replacement = "\r";
    	return msg.replaceAll(target, replacement);
    }
    
    /**
     * 按照医嘱类别将ORDER段分类，若不在需要拆分的医嘱之内，作为其他医嘱处理.
     * 用^相连的几项作为同一类处理
     * */
    private String getType(String in){
		String key = "other";
		outer: for(String s : Constants.OMPO09_ORDER_TYPES){
			String[] ss = s.split("\\^");
			for(String sss : ss){
				if(sss.equals(in)){
					key = s;
					break outer;
				}
			}
		}
		return key;
    }
    
    /**
     * @param ompo09 原始的消息
     * @return 按照医嘱类别拆分后的消息列表
     * */
    private List<OMP_O09> splitOMP_O09(OMP_O09 ompo09) throws HL7Exception{
    	// 就诊类别
    	String visitType = ompo09.getPATIENT().getPATIENT_VISIT().getPV1().getPatientClass().getValue();
    	// 原消息中的所有ORDER段
    	List<OMP_O09_ORDER> orderAll = ompo09.getORDERAll();
    	// 拆分后的消息列表
    	List<OMP_O09> splitMessageList = new ArrayList<OMP_O09>();
    	// ORDER段分类MAP
    	Map<String, List<OMP_O09_ORDER>> splitMessageMap = new HashMap<String, List<OMP_O09_ORDER>>();
    	
		for(int i = 0; i < orderAll.size(); i++){
			OMP_O09_ORDER order = orderAll.get(i);
			ORC orc = order.getORC();
			CWE orc_25 = orc.getOrc25_OrderStatusModifier();
			// 医嘱类别
			String orderType = orc_25.getCwe1_Identifier().getValue();	
			String type = getType(orderType);
			// 过滤掉OMP^O09消息中的检查，检验，手术。因为会有单独的申请单消息发送，不做重复处理
			if(Constants.GD_EXAM_ORDER.equals(orderType) || Constants.GD_LAB_ORDER.equals(orderType) 
					|| Constants.GD_PROCEDURE_ORDER.equals(orderType)){
				continue;
			}
			// 住院医嘱的消息场景根据orc-5判断，2开立，3确认，5停止，6撤销
			ID orc_5 = orc.getOrc5_OrderStatus();
			String orderStatus = orc_5.getValue();
			// Author: yu_yzh
			// Date: 2016/3/2 14:53
			// [BUG]0067233 
			// MODIFY BEGIN
			// 门诊医嘱的消息场景根据orc-1判断，NW开立，RU更新，CA撤销
			ID orc_1 = orc.getOrc1_OrderControl();
			String orderControl = orc_1.getValue();
			String splitFlag;
			if(Constants.VISIT_TYPE_CODE_OUTPATIENT.equals(visitType)){
				splitFlag = orderControl;
			} else {
				splitFlag = orderStatus;
			}
			// 拆分医嘱时，住院根据orc-25-1,orc-5来分类，门诊根据orc-25-1,orc-1来分类
/*			String key = type + orderStatus + orderControl;*/
			String key = type + splitFlag;
			//  [BUG]0067233 
			// MODIFY END
			List<OMP_O09_ORDER> otherOrderList;
			if((otherOrderList = splitMessageMap.get(key)) == null){
				otherOrderList = new ArrayList<OMP_O09_ORDER>();
				splitMessageMap.put(key, otherOrderList);
			}
			otherOrderList.add(order);			
		}
		// 新建消息，将相同类别的ORDER段放入同一消息中
		for(List<OMP_O09_ORDER> orders : splitMessageMap.values()){
			SplitedOMP_O09 o = new SplitedOMP_O09(ompo09);
			o.insertORDERALL(orders);
			splitMessageList.add(o);
		}	
		return splitMessageList;
    }
    
    /**
     * @param raso17 原始的消息
     * @return 按照医嘱类别拆分后的消息列表
     * */
    private List<RAS_O17> splitRAS_O17(RAS_O17 raso17) throws HL7Exception{
    	// 原消息中的所有ORDER段
    	List<RAS_O17_ORDER> orderAll = raso17.getORDERAll();
    	// 拆分后的消息列表
    	List<RAS_O17> splitMessageList = new ArrayList<RAS_O17>();
    	// ORDER段分类MAP
    	Map<String, List<RAS_O17_ORDER>> splitMessageMap = new HashMap<String, List<RAS_O17_ORDER>>();
 
    	for(int i = 0; i < orderAll.size(); i++){
			RAS_O17_ORDER order = orderAll.get(i);
			ORC orc = order.getORC();
			CWE orc_25 = orc.getOrc25_OrderStatusModifier();
			// 医嘱类别
			String orderType = orc_25.getCwe1_Identifier().getValue();
			String key = getType(orderType);
			List<RAS_O17_ORDER> orderList;
			if((orderList = splitMessageMap.get(key)) == null){
				orderList = new ArrayList<RAS_O17_ORDER>();
				splitMessageMap.put(key, orderList);
			}
			orderList.add(order);			
		}
		// 新建消息，将相同类别的ORDER段放入同一消息中
		for(List<RAS_O17_ORDER> orders : splitMessageMap.values()){
			SplitedRAS_O17 o = new SplitedRAS_O17(raso17);
			o.insertORDERALL(orders);
			splitMessageList.add(o);
		}	
    	return splitMessageList;
    }
}
