package com.founder.cdr.hl7.dto;

import java.util.ArrayList;
import java.util.List;

public class MSBaseDto<T extends CodeValueDto> extends BaseDto
{

    /*
     * 术语的消息ID
     */
    public String getMessageId()
    {
        return "";
    }

    /**
     * 术语的消息记录集合 
     * @return
     */
    public List<T> getMesageRecords()
    {
        return new ArrayList<T>();
    }

    /**
     * 版本号
     */
    public String getMessageVersionNo()
    {
        return "";
    }
}
