package com.yly.cdr.util.mq;

/**
 * MQ Session信息
 * @author wen_ruichao
 */
public class MQSession
{
    private String hostname = "localhost";

    private String channel;

    private int port = 1414;

    private int ccsid = 1381;

    public String getHostname()
    {
        return hostname;
    }

    public void setHostname(String hostname)
    {
        this.hostname = hostname;
    }

    public String getChannel()
    {
        return channel;
    }

    public void setChannel(String channel)
    {
        this.channel = channel;
    }

    public int getPort()
    {
        return port;
    }

    public void setPort(int port)
    {
        this.port = port;
    }

    public int getCcsid()
    {
        return ccsid;
    }

    public void setCcsid(int ccsid)
    {
        this.ccsid = ccsid;
    }
}
