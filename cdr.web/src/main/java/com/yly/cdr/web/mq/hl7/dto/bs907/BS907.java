package com.yly.cdr.web.mq.hl7.dto.bs907;

import java.util.List;

import com.yly.cdr.web.mq.hl7.dto.BodyContent;
import com.yly.cdr.web.mq.hl7.dto.HeadContent;

public class BS907
{
    private List<HeadContent> head;
    
    private List<BodyContent> body;

    public List<HeadContent> getHead()
    {
        return head;
    }

    public void setHead(List<HeadContent> head)
    {
        this.head = head;
    }

    public List<BodyContent> getBody()
    {
        return body;
    }

    public void setBody(List<BodyContent> body)
    {
        this.body = body;
    }
    
}
