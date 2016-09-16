/**
 * [FUN]A01-029 过敏和生理状态信息
 * 
 * @version 1.0, 2012/04/09  20:23:00
 
 * @author wu_jianfeng
 * @since 1.0
*/

package com.founder.cdr.hl7.dto.poobin000001uv01;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.founder.cdr.hl7.dto.BaseDto;
import com.founder.cdr.hl7.dto.PatientDto;
import com.founder.cdr.util.DateUtils;

public class POOBIN000001UV01 extends BaseDto
{
    /**
     * 域代码
     */
    @NotEmpty(message = "{POOBIN000001UV01.patientDomain}")
    private String patientDomain;

    /**
     * 患者本地ID(门诊号)
     */
    @NotEmpty(message = "{POOBIN000001UV01.patientLid}")
    private String patientLid;

    /**
     * 触发事件代码
     */
    @NotEmpty(message = "{POOBIN000001UV01.triggerEventCode}")
    private String triggerEventCode;

    /**
     * 过敏/不良反应
     */
    private List<AllergicHistoryDto> allergicHistories;

    /**
     * 危险性信息
     */
    private List<RiskInformationDto> riskInformations;

    /**
     * 患者基本信息
     */
    private List<PatientDto> patients;
    
    //@NotEmpty(message = "{POOBIN000001UV01.orgCode}")
    private String orgCode;
    //@NotEmpty(message = "{POOBIN000001UV01.orgName}")
    private String orgName;

    public String getOrgCode() {
		return orgCode;
	}

	public String getOrgName() {
		return orgName;
	}
	
	public void setOrgCode(String orgCode)
    {
        this.orgCode = orgCode;
    }

    public void setOrgName(String orgName)
    {
        this.orgName = orgName;
    }

    public String getPatientDomain()
    {
        return patientDomain;
    }

    public String getPatientLid()
    {
        return patientLid;
    }

    public String getTriggerEventCode()
    {
        return triggerEventCode;
    }

    public List<AllergicHistoryDto> getAllergicHistories()
    {
        return allergicHistories;
    }

    public List<RiskInformationDto> getRiskInformations()
    {
        return riskInformations;
    }

    public List<PatientDto> getPatients()
    {
        return patients;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("MessageId=");
        builder.append(getMessage().getId()+",");
        builder.append("Datetime=");
        builder.append(DateUtils.dateToString(getMessage().getDatetime(),null) + ",");
        builder.append("POOBIN000001UV01 [triggerEventCode=");
        builder.append(triggerEventCode);
        builder.append(", patientDomain=");
        builder.append(patientDomain);
        builder.append(", patientLid=");
        builder.append(patientLid);
        builder.append(", allergicHistories=");
        builder.append(allergicHistories);
        builder.append(", riskInformations=");
        builder.append(riskInformations);
        builder.append(", patients=");
        builder.append(patients);
        builder.append("]");
        return builder.toString();
    }

    
}
