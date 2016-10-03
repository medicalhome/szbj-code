package com.yly.cdr.hl7.server;

import com.yly.cdr.hl7.codec.HL7MLLPNettyDecoder;
import com.yly.cdr.hl7.codec.HL7MLLPNettyEncoder;
import com.yly.cdr.hl7.codec.HL7MLLPServerHandler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;

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
