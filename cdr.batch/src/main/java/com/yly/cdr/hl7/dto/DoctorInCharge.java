package com.yly.cdr.hl7.dto;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 主管医生信息
 */
public class DoctorInCharge
{
	/**
	 * 操作类型
	 */
//	@NotEmpty(message = "{BS359.action}")
	private String action;
	
	/**
     * 主管医生ID
     */
//    @NotEmpty(message = "{BS359.doctorId}")
    private String doctorId;
    
    /**
     * 主管医生名称
     */
//    @NotEmpty(message = "{BS359.doctorName}")
    private String doctorName;
    
    /**
     * 接管病人时间
     */
//    @NotEmpty(message = "{BS359.takeoverTime}")
    private String takeoverTime;
    
    /**
     * 退出时间
     */
    private String exitTime;
    
    /**
     * 职称类别名称
     */
//    @NotEmpty(message = "{BS359.titleTypeName}")
    private String titleTypeName;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getTakeoverTime() {
		return takeoverTime;
	}

	public void setTakeoverTime(String takeoverTime) {
		this.takeoverTime = takeoverTime;
	}

	public String getExitTime() {
		return exitTime;
	}

	public void setExitTime(String exitTime) {
		this.exitTime = exitTime;
	}

	public String getTitleTypeName() {
		return titleTypeName;
	}

	public void setTitleTypeName(String titleTypeName) {
		this.titleTypeName = titleTypeName;
	}
    
}
