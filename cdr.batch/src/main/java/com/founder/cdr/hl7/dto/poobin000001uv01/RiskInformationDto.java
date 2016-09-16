/**
 * [FUN]A01-029 过敏和生理状态信息
 * 
 * @version 1.0, 2012/04/09  20:23:00
 
 * @author wu_jianfeng
 * @since 1.0
*/

package com.founder.cdr.hl7.dto.poobin000001uv01;

import com.founder.cdr.hl7.dto.BaseDto;

public class RiskInformationDto extends BaseDto
{
    /**
     * 危险性代码
     */
    private String riskCode;

    /**
     * 危险性名称
     */
    private String riskName;

    /**
     * 失效日期
     */
    private String expirationDate;

    /**
     * 信息来源
     */
    private String infoSource;

    public String getRiskCode()
    {
        return riskCode;
    }

    public String getRiskName()
    {
        return riskName;
    }

    public String getExpirationDate()
    {
        return expirationDate;
    }

    public String getInfoSource()
    {
        return infoSource;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("RiskInformationDto [riskCode=");
        builder.append(riskCode);
        builder.append("]");
        return builder.toString();
    }
    
}
