package com.founder.cdr.batch.core;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.founder.cdr.hl7.dto.BaseDto;
import com.founder.cdr.util.StringUtils;

@Component
public class MessageWriteLog
{

    private static ServiceMapper serviceMapper;

    static
    {
        serviceMapper = new ServiceMapper();
    }

    protected static final Logger logger = LoggerFactory.getLogger(MessageWriteLog.class);

    public void writeLog(List<? extends BaseDto> baseDtos,String reason)
    {
        logger.trace("writeLog()");      
        for (BaseDto baseDto : baseDtos)
        {
            doWriteLog(baseDto, reason);
        }
    }

    /**
     * 根据消息ID调用不同Writer
     * 
     * @param baseDto
     * @exception 抛出RuntimeException异常
     */
    private void doWriteLog(BaseDto baseDto,String reason) 
    {
     // Add by jin_peng 2013.04.09
        String logNameValue = baseDto.getMessage().getServiceId().toUpperCase();
        Logger validateLogger = null;

        if (!StringUtils.isEmpty(logNameValue))
        {
            validateLogger = LoggerFactory.getLogger(logNameValue);
            logger.debug("id={},logname={}",baseDto.getMessage().getId(),validateLogger);
        }
        if("sucess".equals(reason))
        {
            validateLogger.debug("Message:{},sucess",baseDto);  
            return;
        }   
        if(StringUtils.isEmpty(baseDto.getMessage().getContent()))
        { 
            validateLogger.error("Message:{},error:{}",baseDto,baseDto.getMessage().getVid()+" ["+"id="+baseDto.getMessage().getId()+"]"+reason);
            return;
        }
        validateLogger.error("Message:{},error:{}",baseDto,reason);
        // End by jin_peng 2013.04.09

    }
}
