package com.yly.cdr.hl7.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class HL7MLLPNettyEncoder extends MessageToByteEncoder<Object>
{
    private final HL7MLLPConfig config;

    public HL7MLLPNettyEncoder()
    {
        this(new HL7MLLPConfig());
    }

    public HL7MLLPNettyEncoder(HL7MLLPConfig config)
    {
        this.config = config;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object message, ByteBuf byteBuf) throws Exception
    {
        if (message == null)
        {
            throw new IllegalArgumentException("Message to be encoded is null");
        }
        else if (message instanceof Exception)
        {
            // we cannot handle exceptions
            throw (Exception) message;
        }

        byte[] body;
        if (message instanceof String)
        {
            body = ((String) message).getBytes(config.getCharset());
        }
        else if (message instanceof byte[])
        {
            body = (byte[]) message;
        }
//        else if (message instanceof Message)
//        {
//            body = ((Message) message).encode().getBytes(config.getCharset());
//        }
        else
        {
            throw new IllegalArgumentException("The message to encode is not a supported type: " + message.getClass().getCanonicalName());
        }
        byteBuf.writeByte(config.getStartByte());
        byteBuf.writeBytes(body);
        byteBuf.writeByte(config.getEndByte1());
        byteBuf.writeByte(config.getEndByte2());
    }
}
