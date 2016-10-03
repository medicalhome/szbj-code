package com.yly.cdr.batch.core;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DeadlockLoserDataAccessException;
import org.springframework.dao.DuplicateKeyException;

import com.founder.fasf.core.exception.DBConcurrencyException;
import com.yly.cdr.batch.exception.database.DatabaseException;
import com.yly.cdr.batch.exception.message.MessageBusinessException;
import com.yly.cdr.batch.exception.message.MessageIntegrityException;
import com.yly.cdr.batch.writer.BusinessWriter;
import com.yly.cdr.hl7.dto.BaseDto;
import com.yly.cdr.service.CommonService;
import com.yly.cdr.util.ExceptionUtils;
import com.yly.cdr.util.PropertiesUtils;
import com.yly.cdr.util.ValidatorUtils;

/**
 * 消息Writer
 * 
 * @author wen_ruichao
 * @version 1.0
 */
public class MessageWriter implements ItemWriter<BaseDto>
{

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private static final String DEFAULT_GROUP_PREFIX = "Group";

    private static final String DEFAULT_WRITER_SUFFIX = "Writer";

    @Autowired
    private BeanFactory beanFactory;
    
    @Autowired
    private CommonService commonService;
    
    private @Value("${batch.retry.skipTimes2}")
    long skipTimes2 = 50;
    
    private @Value("${batch.retry.totaltime}")
    long skiptotaltime = 18000000;

    /**
     * 消息ID（根据消息ID，调用对应的业务Writer）
     */
    private String messageId;
    
    private Long times;
    
    private Long totaltime;

    public void setMessageId(String messageId)
    {
        this.messageId = messageId;
    }

    public void setTimes(Long times)
    {
        this.times = times;
    }

    public void setTotaltime(Long totaltime)
    {
        this.totaltime = totaltime;
    }



    /**
     * 
     * @param baseDtos 
     * @exception 在write方法中抛出RuntimeException，来调用reader中close()结束当前job。
     */
    public void write(List<? extends BaseDto> baseDtos) throws Exception
    {
        logger.trace("write()");
        for (BaseDto baseDto : baseDtos)
        {
            doWrite(baseDto);
        }
    }

    /**
     * 根据消息ID调用不同Writer
     * 
     * @param baseDto
     * @exception 抛出RuntimeException异常
     */
    @SuppressWarnings("unchecked")
    private void doWrite(BaseDto baseDto) throws Exception
    {
        logger.debug("id={},messageId={}",baseDto.getMessage().getId(),messageId);
        String groupName = PropertiesUtils.getValue("package.dto") + "."
            + messageId.toLowerCase() + "." + DEFAULT_GROUP_PREFIX + messageId;
        if (!ValidatorUtils.validate(baseDto, Class.forName(groupName),
                baseDto.getMessage().getServiceId()))
        {
            integrate(baseDto);
        }

        String beanId = messageId.toLowerCase() + DEFAULT_WRITER_SUFFIX;
        BusinessWriter<BaseDto> businessMessageWriter = (BusinessWriter<BaseDto>) beanFactory.getBean(beanId);
        if (businessMessageWriter.validate(baseDto))
        {
            boolean flag=commonService.checkFlag(times,totaltime, skipTimes2, skiptotaltime);
            // 自定义的dto数据校验通过
            if (businessMessageWriter.checkDependency(baseDto, flag))
            {
                // 依赖存在
                try
                {
                    businessMessageWriter.saveOrUpdate(baseDto);
                }
                /** Added by XuDengfeng, 2012/09/01 */
                catch (DBConcurrencyException ce)
                {
                    throw ce;
                }
                /** End, 2012/09/01 */
                // $Author :wu_biao
                // $Date : 2013/03/18
                // BUG14525 ADD BEGIN
                catch (DuplicateKeyException e)
                {
                    logger.error("唯一性约束异常:\r\n{}",
                            ExceptionUtils.getStackTrace(e));
                    throw new DuplicateKeyException(messageId, e);
                }
                // $Author :wu_biao
                // $Date : 2013/04/23 
                // BUG31649 ADD BEGIN
                catch (DeadlockLoserDataAccessException ce)
                {
                    throw ce;
                }
                // BUG31649 ADD END
                // BUG14525 ADD END
                catch (Exception e)
                {
                    logger.error("数据库异常:\r\n{}",
                            ExceptionUtils.getStackTrace(e));
                    throw new DatabaseException(messageId, e);
                }
            }
            else
            {
                // 依赖不存在
                throw new MessageBusinessException(messageId); // 跳过当前消息，继续执行下一个消息。
            }
        }
        else
        {
            // 自定义的dto数据校验未通过
            integrate(baseDto);
        }
    }

    /**
     * 
     * @param baseDto
     * @throws MessageIntegrityException
     */
    private void integrate(BaseDto baseDto) throws Exception
    {
        // 跳过当前消息，继续执行下一个消息
        throw new MessageIntegrityException(messageId);
    }

    public long getSkipTimes2()
    {
        return skipTimes2;
    }

    public void setSkipTimes2(long skipTimes2)
    {
        this.skipTimes2 = skipTimes2;
    }

    public long getSkiptotaltime()
    {
        return skiptotaltime;
    }

    public void setSkiptotaltime(long skiptotaltime)
    {
        this.skiptotaltime = skiptotaltime;
    }
    
    
    
}
