package com.founder.cdr.hl7.dto.poorin200901uv03;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.founder.cdr.hl7.dto.OrderDto;
import com.founder.cdr.hl7.dto.ProOrderBatch;
import com.founder.cdr.util.DateUtils;

public class POORIN200901UV03 extends OrderDto
{
    /**
     * 触发事件
     */
    @NotEmpty(message = "{POORIN200901UV03.newUpFlag}")
    private String newUpFlag;

    /**
     * 手术申请医嘱
     */
    @NotEmpty(message = "{POORIN200901UV03.proOrders}")
    private List<ProOrderBatch> proOrders;

    public List<ProOrderBatch> getProOrders()
    {
        return proOrders;
    }

    public String getNewUpFlag()
    {
        return newUpFlag;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("MessageId=");
        builder.append(getMessage().getId()+",");
        builder.append("Datetime=");
        builder.append(DateUtils.dateToString(getMessage().getDatetime(),
                null) + ",");
        builder.append("POORIN200901UV03 [newUpFlag=");
        builder.append(newUpFlag);
        builder.append(", proOrders=");
        builder.append(proOrders);
        builder.append("]");
        return builder.toString();
    }
}
