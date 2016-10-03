package com.yly.cdr.batch.core;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.yly.cdr.util.ExceptionUtils;

@Component
public class MessagePool
{
    private static final Logger logger = LoggerFactory.getLogger(MessagePool.class);

    private LinkedBlockingQueue<Hl7Message> queue;

    private @Value("${batch.queue.maxsize}")
    int maxsize = 5;

    private long lastInputTime = 0;

    private long lastOutputTime = 0;

    private LinkedList<Hl7Message> skipQueue;

    // $Author :wu_biao
    // $Date : 2013/03/13
    // 警告通知框架 ADD BEGIN
    private LinkedList<Hl7Message> reWsSendQueue;

    // 警告通知框架 ADD END

    // $Author :jin_peng
    // $Date : 2014/02/11
    // 对外应用服务 ADD BEGIN
    private LinkedList<Hl7Message> reExternalSendQueue;

    // 对外应用服务 ADD END

    @Autowired(required = false)
    private MessageStore messageStore;

    private @Value("${batch.retry.interval}")
    long interval = 10000;

    private @Value("${batch.retry.interval2}")
    long interval2 = 100000;

    private @Value("${batch.retry.skipTimes}")
    long skipTimes = 10;

    private @Value("${batch.retry.skipTimes2}")
    long skipTimes2 = 50;

    // [BUG]14801 ADD BEGIN
    private @Value("${batch.retry.totaltime}")
    long totaltime = 18000000;

    // [BUG]14801 ADD END
    private @Value("${batch.retry.histotaltime}")
    long histotaltime = 18000000;

    // modify by jin_peng
    private @Value("${external.skipTimesInit}")
    long skipTimesInitExternal = 10;

    private @Value("${external.skipTimesExtra}")
    long skipTimesExtraExternal = 15;

    private @Value("${external.intervalTime}")
    long intervalTimeExternal = 120000;

    private @Value("${external.intervalExtraTime}")
    long intervalExtraTimeExternal = 480000;

    private @Value("${external.totalTime}")
    long totalTimeExternal = 3600000;

    private boolean quit = false;

    private String batchId;

    public MessagePool()
    {
        queue = new LinkedBlockingQueue<Hl7Message>(maxsize);
        skipQueue = new LinkedList<Hl7Message>();
        // $Author :wu_biao
        // $Date : 2013/03/13
        // 警告通知框架 ADD BEGIN
        reWsSendQueue = new LinkedList<Hl7Message>();
        // 警告通知框架 ADD END

        // $Author :jin_peng
        // $Date : 2014/02/11
        // 对外应用服务 ADD BEGIN
        reExternalSendQueue = new LinkedList<Hl7Message>();

        // 对外应用服务 ADD END
        this.lastInputTime = System.currentTimeMillis();
    }

    @Transactional
    public void addMessage(Hl7Message message) throws Exception
    {
        if (this.quit)
            throw new IllegalStateException("系统已经停止。");

        if (!message.isShutdownCommand())
        {
            Long mesc = System.currentTimeMillis();
            messageStore.saveMessage(message);
            Long mescend = System.currentTimeMillis();
            logger.debug("消息存储时间是{}", mescend - mesc);
        }
        else
            this.quit = true;

        long now = System.currentTimeMillis();
        logger.debug("queue.put间隔时间是{}", (now - lastInputTime));
        Long mesc1 = System.currentTimeMillis();
        queue.put(message);
        Long mescend1 = System.currentTimeMillis();
        logger.debug("消息放入队列时间是{}", mescend1 - mesc1);
        this.lastInputTime = System.currentTimeMillis();
    }

    public Hl7Message getMessage() throws Exception
    {
        Hl7Message obj = queue.take();
        this.lastOutputTime = System.currentTimeMillis();
        return obj;
    }

    public Hl7Message getSkippedMessage()
    {
        long realgaptime = interval;
        Hl7Message msg = null;
        if (!this.skipQueue.isEmpty())
        {
            // [BUG]14801 ADD BEGIN
            try
            {
                logger.debug("取消息前skipQueue.size={}", skipQueue.size());
                msg = this.skipQueue.get(0);
                logger.debug("取消息位置{}", skipQueue.indexOf(msg));
                long now = System.currentTimeMillis();

                long lv = msg.getLongProperty(Hl7Message.HEADER_LAST_HANDLE_TIME);
                // 判断处理次数，当超过一定值时调正处理时间间隔

                long times1 = msg.getLongProperty(Hl7Message.HEADER_HANDLE_TIMES);
                if (times1 - skipTimes > 0)
                {
                    realgaptime = interval2;
                }
                long gaptime = now - lv;
                logger.debug("data{}单次间隔{}ms", msg.getUuid(), gaptime);
                logger.debug("data{}判断是否第{}次retry", msg.getUuid(), times1);
                if (gaptime > realgaptime)
                // $Author :wu_biao
                // $Date : 2012/11/15 14:44$
                // [BUG]batch性能改善 ADD BEGIN
                {
                    logger.debug("删除位置{}", skipQueue.indexOf(msg));
                    this.skipQueue.remove(msg);
                    logger.debug("删除消息后skipQueue.size={}", skipQueue.size());
                    long times = msg.getLongProperty(Hl7Message.HEADER_HANDLE_TIMES);
                    msg.setLongProperty(Hl7Message.HEADER_HANDLE_TIMES,
                            times + 1);
                    long totaltime1 = msg.getLongProperty(Hl7Message.HEADER_TOTAL_HANDLE_TIME)
                        + gaptime;
                    msg.setLongProperty(Hl7Message.HEADER_TOTAL_HANDLE_TIME,
                            totaltime1);
                    logger.debug("data{}从skip队列清除，start {}times process",
                            msg.getUuid(), times1);
                    logger.debug("data{}累计处理间隔时间{}MS", msg.getUuid(),
                            totaltime1);
                }
                else
                {
                    logger.debug("data{}时间间隔不够，再等待", msg.getUuid());
                    return null;
                }
                // [BUG]14801 ADD END
            }
            catch (JMSException e)
            {
                e.printStackTrace();
                if (msg != null)
                {
                    logger.error("data{}skip处理出现异常{}", msg.getUuid(),
                            e.getMessage());
                }
            }
            catch (Exception e)
            {
                // e.printStackTrace();
                logger.error("skip重试任务异常：{}", e.getMessage());
            }
        }
        return msg;
    }

    // modify by jin_peng
    // $Author :wu_biao
    // $Date : 2013/03/13
    // 警告通知框架 ADD BEGIN
    public Hl7Message getReWsSendMessage()
    {
        Hl7Message msg = null;
        if (!this.reWsSendQueue.isEmpty())
        {
            try
            {
                msg = this.reWsSendQueue.getFirst();

                // 获取系统now时间
                long now = System.currentTimeMillis();

                // 获取上次获取消息处理时间
                long lastTime = msg.getLongProperty(Hl7Message.HEADER_LAST_HANDLE_SEND_TIME);

                // 获取retry处理次数
                long retryTimes = msg.getLongProperty(Hl7Message.HEADER_HANDLE_SEND_TIMES);

                // 获取retry消息运行总时间
                long retryTotalTime = msg.getLongProperty(Hl7Message.HEADER_TOTAL_HANDLE_SEND_TIME);

                // 设置每次retry时间间隔
                long intervalTime = intervalTimeExternal;

                if (retryTimes > skipTimesInitExternal)
                {
                    intervalTime = intervalExtraTimeExternal;
                }

                long gapTime = now - lastTime;

                if (gapTime > intervalTime)
                {
                    logger.debug("警告通知retry获取消息成功");

                    this.reWsSendQueue.remove(msg);
                    logger.debug("警告通知retry消息队列中剩余size={}"
                        + reWsSendQueue.size());

                    // 设置消息retry次数
                    msg.setLongProperty(Hl7Message.HEADER_HANDLE_SEND_TIMES,
                            retryTimes + 1);

                    // 设置消息retry已运行总时间mill second
                    msg.setLongProperty(
                            Hl7Message.HEADER_TOTAL_HANDLE_SEND_TIME,
                            retryTotalTime + gapTime);

                    logger.debug(
                            "警告通知retry data{}从retry队列清除，start {}times process",
                            msg.getUuid(), retryTimes);
                    logger.debug("警告通知retry data{}累计处理总时间{}MS", msg.getUuid(),
                            retryTotalTime);
                }
                else
                {
                    logger.debug("警告通知retry data{}时间间隔不够，再等待", msg.getUuid());

                    return null;
                }
            }
            catch (JMSException e)
            {
                e.printStackTrace();

                if (msg != null)
                {
                    logger.error("警告通知retry data{}处理出现异常{}", msg.getUuid(),
                            e.getMessage());
                }
            }
            catch (Exception e)
            {
                logger.error("警告通知retry重试任务异常：{}", e.getMessage());
            }
        }
        return msg;
    }

    // 警告通知框架 ADD END

    // modify by jin_peng
    // $Author :jin_peng
    // $Date : 2014/02/11
    // 对外应用服务 ADD BEGIN
    public Hl7Message getReExternalSendMessage()
    {
        Hl7Message msg = null;
        if (!this.reExternalSendQueue.isEmpty())
        {
            try
            {
                msg = this.reExternalSendQueue.getFirst();

                // 获取系统now时间
                long now = System.currentTimeMillis();

                // 获取上次获取消息处理时间
                long lastTime = msg.getLongProperty(Hl7Message.HEADER_EXTERNAL_LAST_HANDLE_TIME);

                // 获取retry处理次数
                long retryTimes = msg.getLongProperty(Hl7Message.HEADER_HANDLE_EXTERNAL_SEND_TIMES);

                // 获取retry消息运行总时间
                long retryTotalTime = msg.getLongProperty(Hl7Message.HEADER_EXTERNAL_TOTAL_HANDLE_TIME);

                // 设置每次retry时间间隔
                long intervalTime = intervalTimeExternal;

                if (retryTimes > skipTimesInitExternal)
                {
                    intervalTime = intervalExtraTimeExternal;
                }

                long gapTime = now - lastTime;

                if (gapTime > intervalTime)
                {
                    logger.debug("对外服务retry获取消息成功");

                    this.reExternalSendQueue.remove(msg);
                    logger.debug("对外服务retry消息队列中剩余size={}"
                        + reExternalSendQueue.size());

                    // 设置消息retry次数
                    msg.setLongProperty(
                            Hl7Message.HEADER_HANDLE_EXTERNAL_SEND_TIMES,
                            retryTimes + 1);

                    // 设置消息retry已运行总时间mill second
                    msg.setLongProperty(
                            Hl7Message.HEADER_EXTERNAL_TOTAL_HANDLE_TIME,
                            retryTotalTime + gapTime);

                    logger.debug(
                            "对外服务retry data{}从retry队列清除，start {}times process",
                            msg.getUuid(), retryTimes);
                    logger.debug("对外服务retry data{}累计处理总时间{}MS", msg.getUuid(),
                            retryTotalTime);
                }
                else
                {
                    logger.debug("对外服务retry data{}时间间隔不够，再等待", msg.getUuid());

                    return null;
                }
            }
            catch (JMSException e)
            {
                e.printStackTrace();

                if (msg != null)
                {
                    logger.error("对外服务retry data{}处理出现异常{}", msg.getUuid(),
                            e.getMessage());
                }
            }
            catch (Exception e)
            {
                logger.error("对外服务retry重试任务异常：{}", e.getMessage());
            }
        }

        return msg;
    }

    // 对外应用服务 ADD END

    public boolean checkLive(long inputIdleTime, long outputIdleTime)
    {
        long now = System.currentTimeMillis();
        if (this.lastInputTime + inputIdleTime < now)
        {
            logger.warn("超过{}分钟没有消息接入！", inputIdleTime / 60000);
            return false;
        }

        boolean isFull = queue.size() == maxsize;
        if (isFull && (lastOutputTime + outputIdleTime < now))
        {
            logger.warn("消息队列已满，但是超过{}分钟没有消息被处理！", outputIdleTime / 60000);
            return false;
        }
        else
            return true;
    }

    public void removeMessage(Hl7Message message, String flag)
    {
        this.messageStore.removeMessage(message, flag);
    }

    public void removeMessageExternal(Hl7Message message, String flag)
    {
        this.messageStore.removeMessageExternal(message, flag);
    }

    public void backupErrorMessage(Hl7Message message, String reason)
            throws Exception
    {
        messageStore.saveError(message, reason);
    }

    public long purchaseResource() throws Exception
    {
        return 1;
    }

    public void releaseResource(long value)
    {
    }

    public long getInterval()
    {
        return interval;
    }

    public void setInterval(long interval)
    {
        this.interval = interval;
    }

    public void loadQueueMessage(String date)
    {
        logger.debug("start loadQueueMessage");
        assert (this.batchId != null);
        // $Author :wu_biao
        // $Date : 2013/03/13
        // 警告通知框架 ADD BEGIN
        List<Hl7Message> list = messageStore.retrieveMessages(this.batchId,
                "0", date);
        // 警告通知框架 ADD END
        // Modified by XuDengfeng, 2012-08-24 for Queue full error
        if (list != null)
        {
            for (Hl7Message message : list)
            {
                try
                {
                    message.setParamsExternal("1");
                    message.setIsRestartRetry("1");
                    queue.put(message);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                    System.exit(-1);
                    logger.error("queue.put,error{}", e.getMessage());
                }
            }
        }
        logger.debug("end loadQueueMessage,queue.size={}", queue.size());
        // End 2012-08-24
        // $Author :wu_biao
        // $Date : 2013/03/13
        // 警告通知框架 ADD BEGIN
        // List<Hl7Message> list1 = messageStore.retrieveMessages(this.batchId,
        // "5");
        // if (list1 != null)
        // {
        // for (Hl7Message message : list1)
        // {
        // message.setReWsSflag("1");
        // reWsSendQueue.add(message);
        // }
        // }
        // 警告通知框架 ADD END

    }

    public void skipMessage(Hl7Message message)
    {
        try
        {
            long times = message.getLongProperty(Hl7Message.HEADER_HANDLE_TIMES);
            if (times == -1)
            {
                message.setLongProperty(Hl7Message.HEADER_HANDLE_TIMES, 1);
            }

            boolean addflg = true;

            if (times - skipTimes2 > 0)
            {
                removeMessage(message, "4");
                addflg = false;
                logger.debug("data{}处理次数超过{},被置成4", message.getUuid(),
                        skipTimes2);
            }
            if (addflg)
            {
                long intervalTotalTime = histotaltime;

                long totaltime1 = message.getLongProperty(Hl7Message.HEADER_TOTAL_HANDLE_TIME);
                if (totaltime1 == -1)
                {
                    message.setLongProperty(
                            Hl7Message.HEADER_TOTAL_HANDLE_TIME, 0);
                }
                // logger.debug("data{}累计处理间隔时间{}MS",
                // message.getUuid(),totaltime1);
                if (!isHisService(message.getServiceId()))
                {
                    intervalTotalTime = totaltime;
                }

                if (totaltime1 > intervalTotalTime)
                {
                    removeMessage(message, "4");
                    addflg = false;
                    logger.debug("data{}累计超过时间{}MS,被置成4", message.getUuid(),
                            intervalTotalTime);
                }
            }
            if (addflg)
            {
                long now = System.currentTimeMillis();
                message.setLongProperty(Hl7Message.HEADER_LAST_HANDLE_TIME, now);
                logger.debug("放入消息前kipQueue.size={}", skipQueue.size());
                this.skipQueue.addLast(message);
                logger.debug("放入位置{}", skipQueue.indexOf(message));
                logger.debug("放入消息后kipQueue.size={}", skipQueue.size());
                logger.debug("消息{}被放入skip队列，等待retry处理", message.getUuid());
            }
        }
        catch (JMSException e)
        {
            e.printStackTrace();
            logger.error("消息{}放入skip队列前赋值出现异常！", message.getUuid());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.error("消息{}放入skip队列出现异常！{}", message.getUuid(),
                    e.getMessage());
        }

    }

    // modify by jin_peng
    // $Author :wu_biao
    // $Date : 2013/03/13
    // 警告通知框架 ADD BEGIN
    public void reWsSendMessage(Hl7Message message)
    {
        try
        {
            long times = message.getLongProperty(Hl7Message.HEADER_HANDLE_SEND_TIMES);
            if (times == -1)
            {
                message.setLongProperty(Hl7Message.HEADER_HANDLE_SEND_TIMES, 1);
            }

            boolean addflg = true;

            if (times > skipTimesExtraExternal)
            {
                addflg = false;
                this.removeMessage(message, "5");
                logger.debug("警告通知 data{}累计超过设置最大次数{},被置成5", message.getUuid(),
                        skipTimesExtraExternal);
                return;
            }

            long totalTime = message.getLongProperty(Hl7Message.HEADER_TOTAL_HANDLE_SEND_TIME);

            if (totalTime == -1)
            {
                message.setLongProperty(
                        Hl7Message.HEADER_TOTAL_HANDLE_SEND_TIME, 0);
            }

            if (totalTime > totalTimeExternal)
            {
                addflg = false;
                this.removeMessage(message, "5");
                logger.debug("警告通知 data{}累计超过时间{}MS,被置成5", message.getUuid(),
                        totalTimeExternal);
                return;
            }

            if (addflg)
            {
                long now = System.currentTimeMillis();
                message.setLongProperty(
                        Hl7Message.HEADER_LAST_HANDLE_SEND_TIME, now);

                message.setReWsSflag("1");
                this.reWsSendQueue.add(message);

                logger.debug("警告通知放入消息队列后queue.size={}", reWsSendQueue.size());
                logger.debug("消息{}被放入sendSkip队列，等待reWsSend处理",
                        message.getUuid());
            }

        }
        catch (JMSException e)
        {
            e.printStackTrace();
            logger.error("消息{}放入sendSkip队列前赋值出现异常！", message.getUuid());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.error("消息{}放入sendSkip队列出现异常！{}", message.getUuid(),
                    e.getMessage());
        }

    }

    // 警告通知框架 ADD END

    // modify by jin_peng
    // $Author :jin_peng
    // $Date : 2014/02/11
    // 对外应用服务 ADD BEGIN
    public void reExternalSendMessage(Hl7Message message)
    {
        try
        {
            long times = message.getLongProperty(Hl7Message.HEADER_HANDLE_EXTERNAL_SEND_TIMES);
            if (times == -1)
            {
                message.setLongProperty(
                        Hl7Message.HEADER_HANDLE_EXTERNAL_SEND_TIMES, 1);
            }

            boolean addflg = true;

            if (times > skipTimesExtraExternal)
            {
                addflg = false;
                this.removeMessageExternal(message, "4");
                logger.debug("对外服务 data{}累计超过设置最大次数{},被置成4", message.getUuid(),
                        skipTimesExtraExternal);
                return;
            }

            long totalTime = message.getLongProperty(Hl7Message.HEADER_EXTERNAL_TOTAL_HANDLE_TIME);

            if (totalTime == -1)
            {
                message.setLongProperty(
                        Hl7Message.HEADER_EXTERNAL_TOTAL_HANDLE_TIME, 0);
            }

            if (totalTime > totalTimeExternal)
            {
                addflg = false;
                this.removeMessageExternal(message, "4");
                logger.debug("对外服务 data{}累计超过时间{}MS,被置成4", message.getUuid(),
                        totalTimeExternal);
                return;
            }

            if (addflg)
            {
                long now = System.currentTimeMillis();
                message.setLongProperty(
                        Hl7Message.HEADER_EXTERNAL_LAST_HANDLE_TIME, now);

                this.reExternalSendQueue.add(message);

                logger.debug("对外服务放入消息队列后queue.size={}",
                        reExternalSendQueue.size());
                logger.debug("消息{}被放入sendSkip队列，等待reExternalSend处理",
                        message.getUuid());
            }

        }
        catch (JMSException e)
        {
            e.printStackTrace();
            logger.error("消息{}放入sendSkipExternal队列前赋值出现异常！", message.getUuid());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.error("消息{}放入sendSkipExternal队列出现异常！{}", message.getUuid(),
                    e.getMessage());
        }

    }

    // 对外应用服务 ADD END

    private boolean isHisService(String paraService)
    {
        if (paraService.equals("BS001") || paraService.equals("BS306")
            || paraService.equals("BS302") || paraService.equals("BS002")
            || paraService.equals("BS006") || paraService.equals("BS007")
            || paraService.equals("BS008") || paraService.equals("BS303")
            || paraService.equals("BS005") || paraService.equals("BS307")
            || paraService.equals("BS308") || paraService.equals("BS309")
            || paraService.equals("BS310") || paraService.equals("BS311")
            || paraService.equals("BS312") || paraService.equals("BS352")
            || paraService.equals("BS313") || paraService.equals("BS321")
            || paraService.equals("BS304") || paraService.equals("BS305")
            || paraService.equals("BS358"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void terminate()
    {
        if (this.queue.size() == 0)
        {
            try
            {
                long lock = this.purchaseResource();
                this.addMessage(getShutdownMessage());
                this.releaseResource(lock);
            }
            catch (Exception e)
            {
                // e.printStackTrace();
                logger.error("停止MessagePool异常：\r\n{}",
                        ExceptionUtils.getStackTrace(e));
            }
        }
        this.quit = true;
        logger.info("MessagePool停止接收新的消息。");
    }

    private Hl7Message getShutdownMessage() throws Exception
    {
        Hl7Message msg = new Hl7Message();
        msg.setShutdownCommand();
        msg.setText("shutdown");

        return msg;
    }

    public String getBatchId()
    {
        return batchId;
    }

    public void setBatchId(String batchId)
    {
        this.batchId = batchId;
    }

    public long getSkipTimes()
    {
        return skipTimes;
    }

    public void setSkipTimes(long skipTimes)
    {
        this.skipTimes = skipTimes;
    }

    public long getSkipTimes2()
    {
        return skipTimes2;
    }

    public void setSkipTimes2(long skipTimes2)
    {
        this.skipTimes2 = skipTimes2;
    }

    public long getInterval2()
    {
        return interval2;
    }

    public void setInterval2(long interval2)
    {
        this.interval2 = interval2;
    }

    public int getMaxsize()
    {
        return maxsize;
    }

    public void setMaxsize(int maxsize)
    {
        this.maxsize = maxsize;
    }

    public long getTotaltime()
    {
        return totaltime;
    }

    public void setTotaltime(long totaltime)
    {
        this.totaltime = totaltime;
    }

    public long getHistotaltime()
    {
        return histotaltime;
    }

    public void setHistotaltime(long histotaltime)
    {
        this.histotaltime = histotaltime;
    }

}
