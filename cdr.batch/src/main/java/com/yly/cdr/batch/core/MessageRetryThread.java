package com.yly.cdr.batch.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.yly.cdr.core.Constants;
import com.yly.cdr.util.ExceptionUtils;

@Component
public class MessageRetryThread extends Thread implements BeanFactoryAware
{
    private final static Logger logger = LoggerFactory.getLogger(MessageRetryThread.class);

    private boolean quit = false;

    @Autowired
    private MessagePool pool;

    private BeanFactory beanFactory;
    
    private @Value("${batch.retry.sleepTime}")
    long skipTimes = 1000;
    

    @Override
    public void run()
    {
        logger.debug("start MessageRetryThread");
        // registShutdownHook();

        while (!quit)
        {
            logger.debug("start");
            Hl7Message message = null;
            try
            {
                message = this.pool.getSkippedMessage();
                if (this.quit)
                    break;
                else if (message != null)
                {
                    JobRunner jobRunner = beanFactory.getBean(JobRunner.class);
                    jobRunner.setMessage(message);
                    jobRunner.setRetryAccess(Constants.ACCESS_WS);
                    jobRunner.run();
                }
                else
                {
                    sleep();
                }
            }
            catch (Exception e)
            {
                // e.printStackTrace();
                logger.error("重试任务异常：{}",e.getMessage());
            }
        }
        logger.info("MessageRetryThread停止。");
    }

    private void sleep()
    {
        try
        {
            Thread.sleep(skipTimes);
        }
        catch (InterruptedException ie)
        {
            ie.printStackTrace();
        }
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException
    {
        this.beanFactory = beanFactory;
    }

    public void terminate()
    {
        this.quit = true;
    }

    public long getSkipTimes()
    {
        return skipTimes;
    }

    public void setSkipTimes(long skipTimes)
    {
        this.skipTimes = skipTimes;
    }
    
    

}
