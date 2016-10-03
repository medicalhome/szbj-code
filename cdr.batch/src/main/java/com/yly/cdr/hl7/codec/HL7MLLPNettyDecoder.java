package com.yly.cdr.hl7.codec;

import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;

public class HL7MLLPNettyDecoder extends DelimiterBasedFrameDecoder
{
    protected static Logger logger = LoggerFactory.getLogger(HL7MLLPNettyDecoder.class);
    
    private static final int MAX_FRAME_LENGTH = Integer.MAX_VALUE;
    private final HL7MLLPConfig config;
    
    public HL7MLLPNettyDecoder()
    {
        this(new HL7MLLPConfig());
    }

    public HL7MLLPNettyDecoder(HL7MLLPConfig config)
    {
        super(MAX_FRAME_LENGTH, true, Unpooled.copiedBuffer(new char[] { config.getEndByte1(), config.getEndByte2() }, Charset.defaultCharset()));
        this.config = config;
    }
    
    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf buffer) throws Exception
    {
        ByteBuf buf = (ByteBuf) super.decode(ctx, buffer);
        if (buf != null)
        {
            int pos = buf.bytesBefore((byte) config.getStartByte());
            if (pos >= 0)
            {
                ByteBuf msg = buf.readerIndex(pos + 1).slice();
                logger.debug("Message ends with length {}", msg.readableBytes());
                return config.isProduceString() ? asString(msg) : asByteArray(msg);
            }
            else
            {
                throw new DecoderException("Did not find start byte " + (int) config.getStartByte());
            }
        }
        // Message not complete yet - return null to be called again
        logger.debug("No complete messages yet at position {}", buffer.readableBytes());
        return null;
    }

    private byte[] asByteArray(ByteBuf msg)
    {
        byte[] bytes = new byte[msg.readableBytes()];
        msg.getBytes(0, bytes);
        if (config.isConvertLFtoCR())
        {
            for (int i = 0; i < bytes.length; i++)
            {
                if (bytes[i] == (byte) '\n')
                {
                    bytes[i] = (byte) '\r';
                }
            }
        }
        return bytes;
    }

    private String asString(ByteBuf msg)
    {
        String s = msg.toString(config.getCharset());
        if (config.isConvertLFtoCR())
        {
            return s.replace('\n', '\r');
        }
        return s;
    }
}
