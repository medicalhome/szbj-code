package com.yly.cdr.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.ClassPathResource;

/**
 * CDR Batch的入口
 * 
 * @author xu_dengfeng
 */
public class MainApp
{
    private static Logger logger = LoggerFactory.getLogger(MainApp.class);
    
    private static final String MESSAGE_ID = "messageId";

    private static final String JOB_ID = "job";
    
    public static GenericApplicationContext initAppContext()
    {
        // 由于共通框架要求ApplicationContext对象必须实现BeanDefinitionRegistry接口
        // 所以，不能直接使用ClassPathXmlApplicationContext或者FileSystemXmlApplicationContext
        GenericApplicationContext ctx = new GenericApplicationContext();
        XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(ctx);
        xmlReader.loadBeanDefinitions(new ClassPathResource("cdr-batch-context.xml"));
        xmlReader.loadBeanDefinitions(new ClassPathResource("cdr-core-context.xml"));
        xmlReader.loadBeanDefinitions(new ClassPathResource("cdr-data-context.xml"));
        xmlReader.loadBeanDefinitions(new ClassPathResource("cdr-dao-context.xml"));
        xmlReader.loadBeanDefinitions(new ClassPathResource("cdr-app-context.xml"));
        xmlReader.loadBeanDefinitions(new ClassPathResource("cdr-cache-context.xml"));
        ctx.refresh();
        
        return ctx;
    }
    
    public static void main(String[] args)
    {
        if(args == null || args.length < 1)
        {
            logger.error("参数个数不正确！请检查命令行参数。");
            System.exit(-1);
        }
        
        // 功能ID
        String functionId = args[0];
        // 作业ID
//        String batchId = args[1];
        // 并行线程数
//        String threadNum = args[2];
        
        GenericApplicationContext c = MainApp.initAppContext();
        JobLauncher launcher = (JobLauncher) c.getBean("jobLauncher");
        
        JobParametersBuilder builder = new JobParametersBuilder();
        builder.addString(MainApp.MESSAGE_ID, functionId);
//        builder.addString("BATCH_ID", batchId);
//        builder.addString("THREAD_NUMBER", threadNum);
        try
        {
            launcher.run((Job) c.getBean(MainApp.JOB_ID), builder.toJobParameters());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
