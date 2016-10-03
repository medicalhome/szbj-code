package com.yly.cdr.batch.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.ClassPathResource;

import com.yly.cdr.batch.QueueNamePropertyDefiner;
import com.yly.cdr.core.Constants;
import com.yly.cdr.util.DataMigrationUtils;
import com.yly.cdr.util.PropertiesUtils;

public class BatchApplication
{
	private final Logger logger = LoggerFactory.getLogger(BatchApplication.class);
	
    private static final String PARAM_DISABLE_RECEIVER = "-disableReceiver";

    private static final String PARAM_DISABLE_CHECKER = "-disableChecker";

    private static final String PARAM_DISABLE_RETRY = "-disableRetry";

    private static final String PARAM_DISABLE_CONSOLE = "-disableConsole";

    // $Author :wu_biao
    // $Date : 2013/03/13
    // 警告通知框架 ADD BEGIN
    private static final String PARAM_DISABLE_REWSSEND = "-disableReWsSend";

    private static final String PARAM_DISABLE_INITMESP = "-disableInitMesP";

    private static final String PARAM_DISABLE_DISPATCHER = "-disableDispatcher";

    // 警告通知框架 ADD END

    // $Author :jin_peng
    // $Date : 2014/02/12
    // 对外应用服务 ADD BEGIN
    private static final String PARAM_DISABLE_REEXTERNAL_SEND = "-disableReExternalSend";

    // 对外应用服务 ADD END

    //
    private static final String PARAM_SHOW_SJQY_IN_CREATEBY = "-showSJQY";
    
    public GenericApplicationContext initAppContext()
    {
        // 由于共通框架要求ApplicationContext对象必须实现BeanDefinitionRegistry接口
        // 所以，不能直接使用ClassPathXmlApplicationContext或者FileSystemXmlApplicationContext
        GenericApplicationContext ctx = new GenericApplicationContext();
        XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(ctx);
        xmlReader.loadBeanDefinitions(new ClassPathResource(
                "META-INF/spring/cdr-core-context.xml"));
        xmlReader.loadBeanDefinitions(new ClassPathResource(
                "META-INF/spring/cdr-data-context.xml"));
        xmlReader.loadBeanDefinitions(new ClassPathResource(
                "META-INF/spring/cdr-app-context.xml"));
        xmlReader.loadBeanDefinitions(new ClassPathResource(
                "META-INF/spring/cdr-batch-context.xml"));
        // $Author :wu_biao
        // $Date : 2012/10/10 14:44$
        // [BUG]9449 ADD BEGIN
        xmlReader.loadBeanDefinitions(new ClassPathResource(
                "META-INF/spring/cdr-cache-context.xml"));
        // xmlReader.loadBeanDefinitions(new
        // ClassPathResource("META-INF/spring/cdr-quartz-context.xml"));
        // [BUG]9449 ADD END
        ctx.refresh();

        return ctx;
    }

    public void initMessagePool(GenericApplicationContext ctx,
            String queueName, String date)
    {
        MessagePool pool = ctx.getBean(MessagePool.class);
        pool.setBatchId(queueName);
        pool.loadQueueMessage(date);
    }

    public void startDispatcher(GenericApplicationContext ctx, String queueName)
    {
        MessageDispatcher dispatcher = ctx.getBean(MessageDispatcher.class);
        dispatcher.setName("MessageDispatcher");
        dispatcher.start();
    }

    public void startReceiver(GenericApplicationContext ctx, String queueName)
    {
        MessageReceiver receiver = null;
        receiver = ctx.getBean(MessageReceiver.class);
        receiver.setName("MessageReceiver");
        receiver.setQueueName(queueName);
        receiver.start();
    }

    public void startV2Receiver(GenericApplicationContext ctx, String queueName)
    {
    	
    	HL7MessageServer receiver = null;
        receiver = ctx.getBean(HL7MessageServer.class);
        receiver.setName("MessageReceiver");
        try{
        	int port = Integer.parseInt(queueName);
        	Config.setConfig(Constants.SOCKET_SERVER_PORT_KEY, queueName);
        	receiver.setPort(port);
        	receiver.start();
        }catch(NumberFormatException e){
        	logger.debug("第二个参数应为Server的端口号，应为数值型，实际传输为：{}",queueName);
        	logger.error("程序启动异常：{}",e.getMessage());
        	e.printStackTrace();
        }
    }

    public void startRetry(GenericApplicationContext ctx, String queueName)
    {
        MessageRetryThread retry = ctx.getBean(MessageRetryThread.class);
        retry.setName("MessageRetryThread");
        retry.start();
    }

    // $Author :wu_biao
    // $Date : 2013/03/13
    // 警告通知框架 ADD BEGIN
    public void startReWsSend(GenericApplicationContext ctx, String queueName)
    {
        MessageReWsSendThread reWsSend = ctx.getBean(MessageReWsSendThread.class);
        reWsSend.setName("MessageReWsSendThread");
        reWsSend.start();
    }

    // 警告通知框架 ADD END

    // $Author :jin_peng
    // $Date : 2014/02/12
    // 对外应用服务 ADD BEGIN
    public void startReExternalSend(GenericApplicationContext ctx,
            String queueName)
    {
        MessageReExternalSendThread reWsSend = ctx.getBean(MessageReExternalSendThread.class);
        reWsSend.setName("MessageReExternalSendThread");
        reWsSend.start();
    }

    // 对外应用服务 ADD END

    public void startHealthCheck(GenericApplicationContext ctx, String queueName)
    {
        HealthCheck checker = ctx.getBean(HealthCheck.class);
        checker.setName("HealthCheck");
        checker.start();
    }

    public void startConsole(GenericApplicationContext ctx)
    {
        TelnetConsole console = new TelnetConsole();
        try
        {
            console.init();
            console.start();

            MessageReceiver receiver = ctx.getBean(MessageReceiver.class);
            receiver.terminate();

            HealthCheck checker = ctx.getBean(HealthCheck.class);
            checker.terminate();

            MessageRetryThread retry = ctx.getBean(MessageRetryThread.class);
            retry.terminate();

            MessageDispatcher dispatcher = ctx.getBean(MessageDispatcher.class);
            dispatcher.terminate();
            dispatcher.getMessagePool().terminate();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void usage()
    {
        System.out.println("Usage:");
        StringBuilder sb = new StringBuilder();
        sb.append("java -cp %CLASSPATH com.yly.cdr.batch.core.BatchApplication ");
        sb.append("[");
        sb.append(PARAM_DISABLE_RECEIVER);
        sb.append("]");
        sb.append("[");
        sb.append(PARAM_DISABLE_RETRY);
        sb.append("]");
        sb.append("[");
        sb.append(PARAM_DISABLE_CHECKER);
        sb.append("]");
        sb.append("[");
        sb.append(PARAM_DISABLE_CHECKER);
        sb.append("]");
        sb.append("队列名称");

        System.out.println(sb.toString());
    }

    public static void main(String[] args)
    {
        if (args == null || args.length < 1)
        {
            System.err.println("参数个数不正确！请检查命令行参数。");
            BatchApplication.usage();

            System.exit(-1);
        }

        String queueName = args[args.length - 1];
        String date = args[args.length - 2];
        if ("date".equals(date))
        {
            date = null;
        }
        boolean disableReceiver = true;
        boolean disableChecker = true;
        boolean disableV2Receiver = false;
        boolean disableRetry = false;
        boolean disableConsole = true;
        // $Author :wu_biao
        // $Date : 2013/03/13
        // 警告通知框架 ADD BEGIN
        boolean disableReWsSend = true;

        boolean disableinitMesP = false;
        // 警告通知框架 ADD END
        boolean disableDispatcher = false;

        // $Author :jin_peng
        // $Date : 2014/02/12
        // 对外应用服务 ADD BEGIN
        boolean disableReExternalSend = true;

        // 对外应用服务 ADD END

        // 设置日志文件文件名中队列名变量,参考logback.xml
        QueueNamePropertyDefiner.setQueueName(queueName);

        for (int i = args.length - 3; i >= 0; i--)
        {
            String param = args[i];
            if (PARAM_DISABLE_RECEIVER.equalsIgnoreCase(param))
                disableReceiver = true;
            else if (PARAM_DISABLE_CHECKER.equalsIgnoreCase(param))
                disableChecker = true;
            else if (PARAM_DISABLE_RETRY.equalsIgnoreCase(param))
                disableRetry = true;
            else if (PARAM_DISABLE_CONSOLE.equalsIgnoreCase(param))
                disableConsole = true;
            // $Author :wu_biao
            // $Date : 2013/03/13
            // 警告通知框架 ADD BEGIN
            else if (PARAM_DISABLE_REWSSEND.equalsIgnoreCase(param))
                disableReWsSend = true;
            else if (PARAM_DISABLE_INITMESP.equalsIgnoreCase(param))
                disableinitMesP = true;
            // 警告通知框架 ADD END
            else if (PARAM_DISABLE_DISPATCHER.equalsIgnoreCase(param))
                disableDispatcher = true;
            // $Author :jin_peng
            // $Date : 2014/02/12
            // 对外应用服务 ADD BEGIN
            else if (PARAM_DISABLE_REEXTERNAL_SEND.equalsIgnoreCase(param))
                disableReExternalSend = true;
            // 对外应用服务 ADD END
            /*
             * Author： yu_yzh
             * Date: 2015/11/13
             * 数据迁移标志 ADD BEGIN
             * */ 
            else if (PARAM_SHOW_SJQY_IN_CREATEBY.equals(param))
            	DataMigrationUtils.setDataMigration("SJQY-");
            // 数据迁移标志 ADD END
        }

        BatchApplication app = new BatchApplication();
        GenericApplicationContext ctx = app.initAppContext();
        // Modified by XuDengfeng, 2012-08-24 for Queue full error
        if (!disableDispatcher)
            app.startDispatcher(ctx, queueName);
        if (!disableRetry)
            app.startRetry(ctx, queueName);
        if (!disableinitMesP)
           // app.initMessagePool(ctx, queueName, date);
        // end 2012-08-24
        if (!disableV2Receiver)
            app.startV2Receiver(ctx, queueName);
        if (!disableReceiver)
            app.startReceiver(ctx, queueName);
        if (!disableChecker)
            app.startHealthCheck(ctx, queueName);
        // $Author :wu_biao
        // $Date : 2013/03/13
        // 警告通知框架 ADD BEGIN
        String sendDefault = PropertiesUtils.getValue("sendMessage.default");
        if ("1".equals(sendDefault))
        {
            if (!disableReWsSend)
                app.startReWsSend(ctx, queueName);
        }
        // 警告通知框架 ADD END
        // $Author :jin_peng
        // $Date : 2014/02/12
        // 对外应用服务 ADD BEGIN
        String sendExternal = PropertiesUtils.getValue("sendMessage.external");
        if ("1".equals(sendExternal))
        {
            if (!disableReExternalSend)
                app.startReExternalSend(ctx, queueName);
        }
        // 对外应用服务 ADD END
        if (!disableConsole)
            app.startConsole(ctx);
    }
}
