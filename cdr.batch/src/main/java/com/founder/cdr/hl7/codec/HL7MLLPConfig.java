package com.founder.cdr.hl7.codec;

import java.nio.charset.Charset;

import com.founder.cdr.core.Constants;

public class HL7MLLPConfig
{
//    private Charset charset = Charset.defaultCharset();
    private Charset charset = Charset.forName(Constants.V2_MESSAGE_FORMAT);
    
    private boolean convertLFtoCR = true;

    // HL7 MLLP start and end markers
    private char startByte = 0x0b; // 11 decimal

    private char endByte1 = 0x1c; // 28 decimal

    private char endByte2 = 0x0d; // 13 decimal

    private boolean produceString = true;

    public Charset getCharset()
    {
        return charset;
    }

    public void setCharset(Charset charset)
    {
        this.charset = charset;
    }

    public boolean isConvertLFtoCR()
    {
        return convertLFtoCR;
    }

    public void setConvertLFtoCR(boolean convertLFtoCR)
    {
        this.convertLFtoCR = convertLFtoCR;
    }

    public char getStartByte()
    {
        return startByte;
    }

    public void setStartByte(char startByte)
    {
        this.startByte = startByte;
    }

    public char getEndByte1()
    {
        return endByte1;
    }

    public void setEndByte1(char endByte1)
    {
        this.endByte1 = endByte1;
    }

    public char getEndByte2()
    {
        return endByte2;
    }

    public void setEndByte2(char endByte2)
    {
        this.endByte2 = endByte2;
    }

    public boolean isProduceString()
    {
        return produceString;
    }

    public void setProduceString(boolean produceString)
    {
        this.produceString = produceString;
    }
}
