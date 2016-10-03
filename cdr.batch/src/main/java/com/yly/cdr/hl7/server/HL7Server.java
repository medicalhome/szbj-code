package com.yly.cdr.hl7.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yly.cdr.batch.core.HL7MessageServer;
import com.yly.cdr.util.PropertiesUtils;

public class HL7Server
{
	private final static Logger logger = LoggerFactory.getLogger(HL7Server.class);
//    private ChannelGroup group = new DefaultChannelGroup("hl7server", ImmediateEventExecutor.INSTANCE);
    private EventLoopGroup workerGroup = new NioEventLoopGroup();
    private EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    private Channel channel;
    
    private int port = 18087;
    
    public HL7Server() {
		super();
	}
    
	public HL7Server(int port) {
		super();
		this.port = port;
	}


	public ChannelFuture start()
    {
        ServerBootstrap boot = new ServerBootstrap();
        boot.group(workerGroup, bossGroup).channel(NioServerSocketChannel.class);
        boot.childHandler(new ServerInitializerFactory());
        //按照batch启动时传的第二个参数作为端口
        //port = Integer.parseInt(PropertiesUtils.getValue("server.port"));
        logger.debug("Server 端口号为：{}",port);
        ChannelFuture f = boot.bind(new InetSocketAddress(port)).syncUninterruptibly();
        channel = f.channel();
        return f;
    }

    public void destroy()
    {
        if (channel != null)
            channel.close();
        workerGroup.shutdownGracefully();
        logger.debug("Server 已关闭。。。。。。");
    }
}
