package com.founder.cdr.tool;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.apache.cxf.frontend.ClientProxyFactoryBean;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.founder.cdr.core.Constants;
import com.founder.cdr.util.PropertiesUtils;
import com.founder.commsrv.message.webservice.SendEmailWebservice;
import com.founder.commsrv.message.webservice.SendMsgByMobileNosWebservice;
import com.founder.commsrv.message.webservice.SendMsgByUserNosWebservice;
import com.founder.commsrv.rule.webservice.CheckMsgRuleWebservice;
import com.founder.commsrv.rule.webservice.RuleCheckWebservice;



/**
 * 调用webservice
 * 
 * @author jinpeng
 *
 */
@Component
public class BatchCallWebservice
{
    private static final Logger logger = LoggerFactory.getLogger(BatchCallWebservice.class);

    private static String title;

    private static String screen;

    private static String wsAddress;

    private static String sysIdy;

    private static String businessIdy;

    private static String sysIdw;

    private static String businessIdw;

    private static String wsAddressByEmployee;

    private static String wsAddressByMobile;

    private static String wsAddressByRule;
    
    private static String wsAddressByCommonRule;

    //Author:yu_yzh
    //江苏人民 短信接口改造
    private static RPCServiceClient serviceClient;    
	private static Options options;
	private static EndpointReference targetEPR;    
	
	private static String jsMobileUrl; 
	private static String jsAxisAction;
	
	private static String jsInRecName;
	private static String jsInSmsType;
	private static String jsInPwd;
	
	private static final String orgJsCode = "46600083-8";
//	private static final String orgBdCode = "";
	
	private static String orgCode;
	
    //
    
    // private static Properties warnMailMessageProp;

    /**
     * 静态加载方法
     */
    static
    {
        // 加载参数配置文件
        initConfig();

        // 初始化成员变量
        setStaticPublicField();
    }

    /**
     * 构造方法
     */
    public BatchCallWebservice()
    {

    }

    private static void initConfig()
    {
        /*
         * warnMailMessageProp = new Properties();
         * 
         * try { warnMailMessageProp.load(new ClassPathResource(
         * "setting.properties").getInputStream()); } catch (IOException ioe) {
         * logger.error("警告通知功能发送邮件或短信时相应参数配置文件读取出错...");
         * 
         * ioe.printStackTrace();
         * 
         * throw new RuntimeException(ioe); }
         */
    }

    private static void setStaticPublicField()
    {
        title = getWarningPropertiesValue("title");

        screen = getWarningPropertiesValue("screen");

        wsAddress = getWarningPropertiesValue("wsAddress");

        sysIdy = getWarningPropertiesValue("sysIdy");

        businessIdy = getWarningPropertiesValue("businessIdy");

        sysIdw = getWarningPropertiesValue("sysIdw");

        businessIdw = getWarningPropertiesValue("businessIdw");

        wsAddressByEmployee = getWarningPropertiesValue("wsAddressByEmployee");

        wsAddressByMobile = getWarningPropertiesValue("wsAddressByMobile");

        wsAddressByRule = getWarningPropertiesValue("wsAddressByRule");
        
        wsAddressByCommonRule = getWarningPropertiesValue("wsAddressByCommonRule");
        
        //Author: yu_yzh
        //江苏人民短信接口改造
    	jsMobileUrl = getWarningPropertiesValue("jsMobileUrl"); 
    	jsAxisAction = getWarningPropertiesValue("jsAxisAction"); 
    	
    	jsInRecName = getWarningPropertiesValue("jsInRecName"); 
    	jsInSmsType = getWarningPropertiesValue("jsInSmsType"); 
    	jsInPwd = getWarningPropertiesValue("jsInPwd"); 
    	
    	orgCode = getWarningPropertiesValue("orgCode");
        //
        
    }

    private static String initParams(String key)
    {
        /*
         * if (warnMailMessageProp != null) { return
         * warnMailMessageProp.getProperty(key).trim(); } else { return null; }
         */

        return PropertiesUtils.getValue(key).trim();
    }

    public static String getWarningPropertiesValue(String key)
    {
        return initParams(key);
    }

    public void sendMailTo(String toAddress, String ccAddress,
            String bccAddress, String content, String[] attachFileName,
            Byte[] fileFlow, String[] file64Str)
    {
        ClientProxyFactoryBean factory = new ClientProxyFactoryBean();

        factory.getOutInterceptors().add(new LoggingOutInterceptor());

        factory.setServiceClass(SendEmailWebservice.class);

        factory.setAddress(wsAddress);

        SendEmailWebservice client = (SendEmailWebservice) factory.create();

        client.sendEmail(toAddress, content, title, ccAddress, bccAddress,
                fileFlow, file64Str, attachFileName, screen);
    }

    public int sendMessageByEmployee(int ywFlag, String employeeId,
            String content)
    {
        String sysId = null;

        String businessId = null;

        if (ywFlag == 0)
        {
            sysId = sysIdy;

            businessId = businessIdy;
        }
        else
        {
            sysId = sysIdw;

            businessId = businessIdw;
        }

        ClientProxyFactoryBean factory = new ClientProxyFactoryBean();

        factory.getOutInterceptors().add(new LoggingOutInterceptor());

        factory.setServiceClass(SendMsgByUserNosWebservice.class);

        factory.setAddress(wsAddressByEmployee);

        SendMsgByUserNosWebservice client = (SendMsgByUserNosWebservice) factory.create();

        int completedFlag = client.sendMessageByUserNos(employeeId, content,
                sysId, businessId);

        return completedFlag;
    }

    public int sendMessageByMobileNo(int ywFlag, String mobileNo, String content)
    {
        String sysId = null;

        String businessId = null;

        if (ywFlag == 0)
        {
            sysId = sysIdy;

            businessId = businessIdy;
        }
        else
        {
            sysId = sysIdw;

            businessId = businessIdw;
        }
       
        ClientProxyFactoryBean factory = new ClientProxyFactoryBean();

        factory.getOutInterceptors().add(new LoggingOutInterceptor());

        factory.setServiceClass(SendMsgByMobileNosWebservice.class);

        factory.setAddress(wsAddressByMobile);

        SendMsgByMobileNosWebservice client = (SendMsgByMobileNosWebservice) factory.create();

        int completedFlag = client.sendMessageByMobileNos(mobileNo, content,
                sysId, businessId);

        return completedFlag;
    }
    
    //北大人民医院
    public int sendMessageNo(String mobileNo, String content)
    {
        String sysId = null;

        String businessId = null;
        
        sysId = sysIdy;

        businessId = "015";

        ClientProxyFactoryBean factory = new ClientProxyFactoryBean();

        factory.getOutInterceptors().add(new LoggingOutInterceptor());

        factory.setServiceClass(SendMsgByMobileNosWebservice.class);

        factory.setAddress(wsAddressByMobile);

        SendMsgByMobileNosWebservice client = (SendMsgByMobileNosWebservice) factory.create();

        int completedFlag = client.sendMessageByMobileNos(mobileNo, content,
                sysId, businessId);

        return completedFlag;
    }
    //

    //Author: yu_yzh
    //Date: 2015/5/6
    //江苏人民短信接口改造 CODE BEGIN
 
    public Object sendMessageNew(String mobileNo, String content) throws AxisFault{
    	Object obj;
    	if(orgCode.equals(orgJsCode)){
    		obj = sendMessageNoJS(mobileNo, content);
    	} else {
    		obj = new Integer(sendMessageNo(mobileNo, content));
    	}
    	return obj;
    }
    
//    public Object sendMessageNew(String mobileNo, String content, String org) throws AxisFault{
//    	Object obj;
//    	if(org.equals(orgJsCode)){
//    		obj = sendMessageNoJS(mobileNo, content);
//    	} else {
//    		obj = new Integer(sendMessageNo(mobileNo, content));
//    	}
//    	return obj;
//    }
    
    public String sendMessageNoJS(String inMobile,String inContent) throws AxisFault {
    	return sendMessageNoJS(inMobile, inContent, jsInRecName, jsInSmsType, jsInPwd);
    }
    
    public String sendMessageNoJS(String inMobile,String inContent
			,String inRecName,String inSmsType,String inPwd) throws AxisFault {
    	if(serviceClient == null){
    		createAxisClient();
    	}    	
    	OMFactory fc = OMAbstractFactory.getOMFactory();
	    OMNamespace omNs = fc.createOMNamespace("http://jsph.net/", "Send");
	    OMElement method = fc.createOMElement("Send",omNs);
	    
	    OMElement paramMobile = fc.createOMElement("_mobilePhones",omNs);
		OMElement paramContent = fc.createOMElement("_content",omNs);
		OMElement paramRecName = fc.createOMElement("_reveicerName",omNs);
		OMElement paramSmsType = fc.createOMElement("_smsTpye",omNs);
		OMElement paramPwd = fc.createOMElement("_pass",omNs);
		
		paramMobile.setText(inMobile);
		paramContent.setText(inContent);
		paramRecName.setText(inRecName);
		paramSmsType.setText(inSmsType);
		paramPwd.setText(inPwd);
		
		method.addChild(paramMobile);
		method.addChild(paramContent);
		method.addChild(paramRecName);
		method.addChild(paramSmsType);
		method.addChild(paramPwd);
		method.build();
		
		OMElement re = serviceClient.sendReceive(method);
		re = re.getFirstElement();
		return re.getText();
    }
	private synchronized void createAxisClient() throws AxisFault {
		if (serviceClient == null) {
			String mobileUrl = jsMobileUrl;
			String axisAction = jsAxisAction;
			serviceClient = new RPCServiceClient();
		    options = new Options();
		    targetEPR = new EndpointReference(mobileUrl);
		    options.setAction(axisAction);
		    options.setTo(targetEPR);
		    serviceClient.setOptions(options);
		}
	}
	//江苏人民短信接口改造 CODE END   
    
    public String checkMsg(String ruleType, String msg)
    {
        ClientProxyFactoryBean factory = new ClientProxyFactoryBean();

        factory.getOutInterceptors().add(new LoggingOutInterceptor());

        factory.setServiceClass(CheckMsgRuleWebservice.class);

        factory.setAddress(wsAddressByRule);

        logger.debug("开始工厂创建客户端。。。");
        CheckMsgRuleWebservice client = (CheckMsgRuleWebservice) factory.create();
        logger.debug("结束工厂创建客户端。。。");

        logger.debug("开始客户端警告通知调用。。。");
        String resultMsg = client.checkMsg(ruleType, msg);
        logger.debug("结束客户端警告通知调用。。。");

        return resultMsg;
    }

    public String ruleCheck(String msg)
    {
        ClientProxyFactoryBean factory = new ClientProxyFactoryBean();

        factory.getOutInterceptors().add(new LoggingOutInterceptor());

        factory.setServiceClass(RuleCheckWebservice.class);

        factory.setAddress(wsAddressByCommonRule);

        logger.debug("开始工厂创建客户端。。。");
        RuleCheckWebservice client = (RuleCheckWebservice) factory.create();
        logger.debug("结束工厂创建客户端。。。");

        logger.debug("开始客户端警告通知调用。。。");
        String resultMsg = client.ruleCheck(msg);
        logger.debug("结束客户端警告通知调用。。。");

        return resultMsg;
    }

    private String getResultTest(String v)
    {
        String result = "<?xml version='1.0' encoding='UTF-8'?>" + " <msg>"
            + " <msgId>0</msgId>" + " <msgName>规则校验信息条件数据</msgName>"
            + " <sourceSysCode>xxx</sourceSysCode>"
            + " <targetSysCode>xxx</targetSysCode>"
            + " <createTime>20090101121330</createTime>"
            + " <reportNo>报告号</reportNo>" + " <visitTimes>就诊次数</visitTimes>"
            + " <patientDomain>患者域Id</patientDomain>"
            + " <patientLid>患者Id</patientLid>"
            + " <patientName>cdr</patientName>"
            + " <genderCode>患者性别</genderCode>"
            + " <visitDept>1000008</visitDept>" + " <report>" + " <component>"
            + " <labItemCode>888822</labItemCode>" + " <reportResult>"
            + " <component>" + " <itemCode>H0131</itemCode>"
            + " <itemNumValue>500</itemNumValue>"
            + " <itemStrValue></itemStrValue>" + " <itemUnit>g/L</itemUnit>"
            + " </component>" + " </reportResult>" + " </component>"
            + " </report>" + " </msg>";

        return result;
    }

    private String getResultCommonTest(String v)
    {    // $Author :yang_mingjie
    	// $Date : 2014/06/26 10:09$
    	// [BUG]0045630 MODIFY BEGIN 
        String result = "<?xml version='1.0' encoding='UTF-8'?>" + " <msg>"
                + " <msgId>0</msgId>" + " <msgName>规则校验信息条件数据</msgName>"
                + " <sourceSysCode>xxx</sourceSysCode>"
                + " <targetSysCode>xxx</targetSysCode>"
                + " <createTime>20090101121330</createTime>"
                + " <serviceId>BS358</serviceId>" + " <visitDept>科室id</visitDept>"
                + " <roleGroupType>InfectionGroup</roleGroupType>"
                + " <hospitalCode>"
                + Constants.HOSPITAL_CODE + "</hospitalCode>"
                + "<hospitalName>"
                + Constants.HOSPITAL_NAME + "</hospitalName>" + "<row>"
                + " <rowKey>1</rowKey>" + " <factType>InfectionRoute</factType>"
                + " <item>" + " <itemName>diagnosis</itemName>"
                + " <value>风疹</value>" + " </item>" + " </row> " + 
                " <row>"
                + " <rowKey>2</rowKey>" + 
                " <factType>InfectionRoute</factType>"
                + " <item>" + 
                " <itemName>diagnosis</itemName>"
                + " <value>霍乱</value>" + 
                " </item>" + 
                " </row> "+
                "<row>"+
                "<rowKey>3</rowKey>"+
                "<factType>InfectionRoute</factType>"+
                "<item>"+
                    "<itemName>diagnosis</itemName>"+
                    "<value>霍乱</value>"+
                "</item>"+
            "</row>"+
                " </msg>";

        return result;
      //[BUG]0045630 MODIFY END
    }

    public static void main(String[] args)
    {
        BatchCallWebservice ws = new BatchCallWebservice();

//         ws.sendMessageByMobileNo(0, "15010730154", "test");

        // ws.sendMailTo("jin_peng@founder.com", "", "", "test", null, null,
        // null);

        // String msg = ws.getResultTest("test");

        // String resultMsg = ws.checkMsg("EmergencyGroup", msg); //
        // EmergencyGroup

        String msg = ws.getResultCommonTest("test");

        String resultMsg = ws.ruleCheck(msg);

        System.out.println(resultMsg);
    }

}
