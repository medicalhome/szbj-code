package com.founder.cdr.hl7.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;

import com.founder.cdr.hl7.codec.HL7MLLPNettyDecoder;
import com.founder.cdr.hl7.codec.HL7MLLPNettyEncoder;
import com.founder.cdr.hl7.codec.HL7MLLPServerHandler;

public class ServerInitializerFactory extends ChannelInitializer<Channel>
{
    @Override
    protected void initChannel(Channel ch) throws Exception
    {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new HL7MLLPNettyEncoder());
        pipeline.addLast(new HL7MLLPNettyDecoder());
        pipeline.addLast(new HL7MLLPServerHandler());
    }

}
