package com.yly.cdr.batch.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HealthCheck extends Thread
{
    private final static Logger logger = LoggerFactory.getLogger(HealthCheck.class);

    private boolean quit = false;

    @Autowired
    private MessagePool pool;

    private @Value("${batch.healthcheck.inputIdle}")
    long inputIdle = 86400000;

    private @Value("${batch.healthcheck.outputIdle}")
    long outputIdle = 120000;

    @Override
    public void run()
    {
        boolean result = true;
        while (!quit)
        {
            logger.debug("start HealthCheck logic");

            // 检查是否一天都没有输入或者存在100秒没有处理的消息
            result = pool.checkLive(inputIdle, outputIdle);
            if (!result)
            {
                logger.error("系统运行异常，请检查MQ服务器、数据库或Batch处理程序是否正常。");
                // TODO 追加发送邮件的代码
            }

            try
            {
                Thread.sleep(10000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        logger.info("HealthCheck停止。");
    }

    public void terminate()
    {
        this.quit = true;
    }

    public long getInputIdle()
    {
        return inputIdle;
    }

    public void setInputIdle(long inputIdle)
    {
        this.inputIdle = inputIdle;
    }

    public long getOutputIdle()
    {
        return outputIdle;
    }

    public void setOutputIdle(long outputIdle)
    {
        this.outputIdle = outputIdle;
    }

}
