package com.yly.cdr.hl7.dto.poorin200901uv13;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.yly.cdr.hl7.dto.BaseDto;
import com.yly.cdr.hl7.dto.OtherOrder;
import com.yly.cdr.util.DateUtils;

public class POORIN200901UV13 extends BaseDto
{
    /**
     * 新增标志
     */
    @NotEmpty(message = "{POORIN200901UV13.newUpFlag}")
    private String newUpFlag;

    /**
     * 其他医嘱
     */
    @NotEmpty(message = "{POORIN200901UV13.otherOrder}")
    private List<OtherOrder> otherOrder;
    // $Author :chang_xuewen
    // $Date : 2014/1/22 10:30$
    // [BUG]0042086 MODIFY BEGIN
    /**
     * 医疗机构代码
     */
    /*@NotEmpty(message = "{POORIN200901UV13.orgCode}")*/
    private String orgCode;
    
    /**
     * 医疗机构名称
     */
    /*@NotEmpty(message = "{POORIN200901UV13.orgName}")*/
    private String orgName;

    public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	// [BUG]0042086 MODIFY END
	public String getNewUpFlag()
    {
        return newUpFlag;
    }

    public List<OtherOrder> getOtherOrder()
    {
        return otherOrder;
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
        builder.append("POORIN200901UV13 [newUpFlag=");
        builder.append(newUpFlag);
        builder.append(", orgCode=");
        builder.append(orgCode);
        builder.append(", orgName=");
        builder.append(orgName);
        builder.append(", otherOrder=");
        builder.append(otherOrder);
        builder.append("]");
        return builder.toString();
    }

    public String getOrgCode()
    {
        return orgCode;
    }

    public String getOrgName()
    {
        return orgName;
    }

}
