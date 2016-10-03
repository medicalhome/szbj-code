package com.yly.cdr.batch.core;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.yly.cdr.core.Constants;
import com.yly.cdr.hl7.dto.BaseDto;
import com.yly.cdr.util.ExceptionUtils;

//$Author :wu_biao
// $Date : 2013/03/13 
// 警告通知框架 ADD BEGIN
@Component
public class MessageReWsSendThread extends Thread implements BeanFactoryAware
{
    private final static Logger logger = LoggerFactory.getLogger(MessageRetryThread.class);

    private boolean quit = false;

    @Autowired
    private MessagePool pool;

    private BeanFactory beanFactory;
    
    private @Value("${batch.retry.sleepTime}")
    long skipTimes = 1000;
    
    private @Value("${warning.retry.sleepTime}")
    long sleepTime = 60000;

    @Override
    public void run()
    {
        logger.debug("start MessageReWsSendThread");
        // registShutdownHook();

        while (!quit)
        {
            logger.debug("start");
            Hl7Message message = null;
            try
            {
                message = this.pool.getReWsSendMessage();
                if (this.quit)
                    break;
                else if (message != null)
                {
                    JobRunner jobRunner = beanFactory.getBean(JobRunner.class);
                    jobRunner.setMessage(message);
                    jobRunner.setRetryAccess(Constants.ACCESS_WS);
//                    List<BaseDto> chrunk =jobRunner.runInternal1();
//                    jobRunner.runInternal3(chrunk);
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
                logger.error("重试任务异常：\r\n{}", ExceptionUtils.getStackTrace(e));
            }
        }
        logger.info("MessageReWsSendThread停止。");
    }

    private void sleep()
    {
        try
        {
            Thread.sleep(sleepTime);
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
    // 警告通知框架 ADD END
}
