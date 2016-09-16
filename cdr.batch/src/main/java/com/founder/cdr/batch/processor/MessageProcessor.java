package com.founder.cdr.batch.processor;

import java.lang.reflect.Field;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.founder.cdr.batch.exception.message.MessageProcessException;
import com.founder.cdr.entity.Message;
import com.founder.cdr.hl7.dto.BaseDto;
import com.founder.cdr.util.PropertiesUtils;
import com.founder.hl7.Hl7v3XmlProcessor;
import com.founder.hl7.maphandler.XPathMapHandler;

/**
 * 定义使用的ItemProcessor
 * @author wen_ruichao
 * @version 1.0
 */
public class MessageProcessor implements ItemProcessor<Message, BaseDto>
{

    private static final Logger logger = LoggerFactory.getLogger(MessageProcessor.class);

    /**
     * 消息ID（根据消息ID，实例化对应Dto）
     */
    private String messageId;

    public void setMessageId(String messageId)
    {
        this.messageId = messageId;
    }

    /**
     * 消息-对象映射
     * @param message 消息对象
     * @return dto对象
     * @exception MessageProcessException
     */
    public BaseDto process(Message message) throws Exception
    {
        logger.trace("process()");

        BaseDto baseDto = BaseDto.class.cast(Class.forName(
                PropertiesUtils.getValue("package.dto") + "."
                    + messageId.toLowerCase() + "." + messageId).newInstance());
        baseDto.setMessage(message);

        XPathMapHandler xPathMapHandler = XPathMapHandler.class.cast(Class.forName(
                PropertiesUtils.getValue("handler.XPathMapHandler")).newInstance());
        xPathMapHandler.setMessageId(messageId);

        Hl7v3XmlProcessor<BaseDto> builder = new Hl7v3XmlProcessor<BaseDto>();
        builder.setXPathMapHandler(xPathMapHandler);
        try
        {
            builder.parse(message.getContent(), baseDto);
            // cda消息service_id未设定，从新设定
            if(StringUtils.isEmpty(message.getServiceId())){
	            Class<?> cls = baseDto.getClass();
	            Object objVal;
	            Field fl = cls.getDeclaredField("serviceId");
				fl.setAccessible(true);
				objVal = fl.get(baseDto);
				message.setServiceId(objVal.toString());
            }
            //Author:jia_yanqing
            //Date:2013/2/26 11:09
            //[BUG]0014124 MODIFY BEGIN
            //logger.debug("process message: \n{} \n to dto: \n {}.",
            //       message.getContent(), baseDto);
            //[BUG]0014124 MODIFY END
        }
        catch (Exception e)
        {
            logger.error("消息内容转换dto对象异常：{}", e.getMessage());
            throw new MessageProcessException(messageId, e);
        }
        return baseDto;
    }
}
