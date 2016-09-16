package com.founder.cdr.batch.core;

import io.netty.channel.ChannelFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.founder.cdr.hl7.server.HL7Server;
import com.founder.cdr.util.ExceptionUtils;

@Component
public class HL7MessageServer extends Thread
{
    private final static Logger logger = LoggerFactory.getLogger(HL7MessageServer.class);

    private int port = 18087; 

	public void setPort(int port) {
		this.port = port;
	}

	@Override
    public void run()
    {
        logger.debug("start server................");
        try{
        	final HL7Server server = new HL7Server(port);
            ChannelFuture f = server.start();
            Runtime.getRuntime().addShutdownHook(new Thread()
            {
                @Override
                public void run()
                {
                    server.destroy();
                }
            });
            f.channel().closeFuture().syncUninterruptibly();
            }
            catch (Exception e1)
            {
                // e1.printStackTrace();
                logger.error("接收消息异常：\r\n{}", ExceptionUtils.getStackTrace(e1));
               
        }
        logger.info("server停止................");
    }

    
}
