package com.yly.hl7.dto;

import com.yly.hl7.annotation.XmlTarget;

public class Diagnosis 
{
	//<!--诊断顺位（从属关系）代码(HR55.02.057.03)-->
	@XmlTarget("/priorityNumber/@value")
	private String priorityNumber;
	
	//<!--诊断类别代码-->
	@XmlTarget("/observationDx/code[@codeSystem='2.16.840.1.113883.2.23.11.5502.20']/@code")
	private String code;

	//<!--诊断类别-->
	@XmlTarget("/observationDx/code[@codeSystem='2.16.840.1.113883.2.23.11.5502.20']/displayName/@value")
	private String name;

	//<!--诊断日期-->
	@XmlTarget("/observationDx/effectiveTime/any/@value")
	private String effectiveTime;

	//<!--疾病代码-->
	@XmlTarget("/observationDx/value/@code")
	private String diseaseCode;

	//<!--疾病名称-->
	@XmlTarget("/observationDx/value/displayName/@value")
	private String diseaseName;

	public String getPriorityNumber() {
		return priorityNumber;
	}

	public void setPriorityNumber(String priorityNumber) {
		this.priorityNumber = priorityNumber;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEffectiveTime() {
		return effectiveTime;
	}

	public void setEffectiveTime(String effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	public String getDiseaseCode() {
		return diseaseCode;
	}

	public void setDiseaseCode(String diseaseCode) {
		this.diseaseCode = diseaseCode;
	}

	public String getDiseaseName() {
		return diseaseName;
	}

	public void setDiseaseName(String diseaseName) {
		this.diseaseName = diseaseName;
	}

	@Override
	public String toString() 
	{
		StringBuilder sb = new StringBuilder();
		sb.append("诊断顺位:");
		sb.append(priorityNumber);
		sb.append(",诊断类别代码:");
		sb.append(code);
		sb.append(",诊断类别:");
		sb.append(name);
		sb.append(",诊断日期:");
		sb.append(effectiveTime);
		sb.append(",疾病代码:");
		sb.append(diseaseCode);
		sb.append(",疾病名称:");
		sb.append(diseaseName);
		
		return sb.toString();
	}
}
