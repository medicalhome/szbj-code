package com.founder.cdr.hl7.dto.repcin000070uv01;

import java.util.List;
import com.founder.cdr.util.DateUtils;

import org.hibernate.validator.constraints.NotEmpty;

import com.founder.cdr.hl7.dto.BaseDto;
import com.founder.cdr.hl7.dto.Graded;

public class REPCIN000070UV01 extends BaseDto
{
    /**
     * 新增标志
     */
    @NotEmpty(message = "{REPCIN000070UV01.newUpFlag}")
    private String newUpFlag;

    /**
     * 评分信息
     */
    @NotEmpty(message = "{REPCIN000070UV01.graded}")
    private List<Graded> graded;
    
    // $Author :chang_xuewen
    // $Date : 2014/1/22 10:30$
    // [BUG]0042086 MODIFY BEGIN
    /*@NotEmpty(message = "{REPCIN000070UV01.orgCode}")*/
    private String orgCode;
    
    /*@NotEmpty(message = "{REPCIN000070UV01.orgName}")*/
    private String orgName;
    // [BUG]0042086 MODIFY END

    public String getOrgCode() {
		return orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public String getNewUpFlag()
    {
        return newUpFlag;
    }

    public List<Graded> getGraded()
    {
        return graded;
    }
    
    // Author :jia_yanqing
    // Date : 2013/06/08 11:30
    // [BUG]0033506 ADD BEGIN
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("MessageId=");
        builder.append(getMessage().getId()+",");
        builder.append("Datetime=");
        builder.append(DateUtils.dateToString(getMessage().getDatetime(),
                null) + ",");
        builder.append("REPCIN000070UV01 [newUpFlag=");
        builder.append(newUpFlag);
        builder.append(", graded=");
        builder.append(graded);
        builder.append(", orgCode=");
        builder.append(orgCode);
        builder.append("]");
        return builder.toString();
    }
    // [BUG]0033506 ADD END
}
