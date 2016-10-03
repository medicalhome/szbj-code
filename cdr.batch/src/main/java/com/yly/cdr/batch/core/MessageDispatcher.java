package com.yly.cdr.batch.core;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.yly.cdr.util.ExceptionUtils;

@Component
public class MessageDispatcher extends Thread implements BeanFactoryAware
{
    private final static Logger logger = LoggerFactory.getLogger(MessageDispatcher.class);

    private boolean quit = false;
    
    private @Value("${batch.dispatcherPool.corePoolSize}")
    int corePoolSize = 5;
    
    private @Value("${batch.dispatcherPool.maximumPoolSize}")
    int maximumPoolSize = 5;
    
    @Autowired
    private MessagePool pool;

    private ThreadPoolExecutor executor;

    private BlockingQueue<Runnable> tasks;

    private BeanFactory beanFactory;

    public MessageDispatcher()
    {
        tasks = new LinkedBlockingQueue<Runnable>(8);
        executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, 5, TimeUnit.SECONDS, tasks,
                new ThreadPoolExecutor.CallerRunsPolicy());
    }

    public MessagePool getMessagePool()
    {
        return this.pool;
    }

    @Override
    public void run()
    {
        logger.debug("start MessageDispatcher");
        registShutdownHook();

        while (!quit)
        {
            logger.debug("start MessageDispatcher logic");
            Hl7Message message = null;
            try
            {
                message = this.pool.getMessage();
                if (quit)
                    message = null;

                if (quit)
                    logger.info("MessageDispatcher停止。");
                else if (message != null)
                {
                    if (message.isShutdownCommand())
                    {
                        this.quit = true;
                    }
                    else
                    {
                        JobRunner jobRunner = beanFactory.getBean(JobRunner.class);
                        jobRunner.setMessage(message);
                        executor.execute(jobRunner);
                    }
                }
                else
                {
                    sleep();
                }
            }
            catch (Exception e)
            {
                // e.printStackTrace();
                logger.error("任务分发异常：\r\n{}", ExceptionUtils.getStackTrace(e));
            }
        }
        logger.info("系统即将停止");
        sleep();
        System.exit(0);
    }

    private void sleep()
    {
        try
        {
            Thread.sleep(10000);
        }
        catch (InterruptedException ie)
        {
            ie.printStackTrace();
        }
    }

    private void registShutdownHook()
    {
        Runtime.getRuntime().addShutdownHook(new Thread()
        {

            @Override
            public void run()
            {
                terminate();
            }
        });
    }

    public void terminate()
    {
        this.quit = true;
    }

    public void setMessagePool(MessagePool pool)
    {
        this.pool = pool;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException
    {
        this.beanFactory = beanFactory;
    }

    public int getCorePoolSize()
    {
        return corePoolSize;
    }

    public void setCorePoolSize(int corePoolSize)
    {
        this.corePoolSize = corePoolSize;
    }

    public int getMaximumPoolSize()
    {
        return maximumPoolSize;
    }

    public void setMaximumPoolSize(int maximumPoolSize)
    {
        this.maximumPoolSize = maximumPoolSize;
    }    
}
