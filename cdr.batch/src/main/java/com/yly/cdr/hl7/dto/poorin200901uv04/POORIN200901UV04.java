package com.yly.cdr.hl7.dto.poorin200901uv04;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.yly.cdr.hl7.dto.BaseDto;
import com.yly.cdr.hl7.dto.BloodRequestBatch;
import com.yly.cdr.util.DateUtils;

public class POORIN200901UV04 extends BaseDto
{
    /**
     * 新增标志
     */
    @NotEmpty(message = "{POORIN200901UV04.newUpFlag}")
    private String newUpFlag;

    /**
     * 用血申请
     */
    @NotEmpty(message = "{POORIN200901UV04.bloodRequest}")
    private List<BloodRequestBatch> bloodRequest;

    public String getNewUpFlag()
    {
        return newUpFlag;
    }

    public List<BloodRequestBatch> getBloodRequest()
    {
        return bloodRequest;
    }

    public void setBloodRequest(List<BloodRequestBatch> bloodRequest)
    {
        this.bloodRequest = bloodRequest;
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
        builder.append("POORIN200901UV04 [newUpFlag=");
        builder.append(newUpFlag);
        builder.append(", bloodRequest=");
        builder.append(bloodRequest);
        builder.append("]");
        return builder.toString();
    }

}
