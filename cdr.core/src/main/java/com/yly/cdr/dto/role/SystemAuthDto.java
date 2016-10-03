package com.yly.cdr.dto.role;

public class SystemAuthDto {
	private String systemAuthId;
	private String occupationType;
	private String authType;
	private String memo;
	
	public String getSystemAuthId() {
		return systemAuthId;
	}

	public void setSystemAuthId(String systemAuthId) {
		this.systemAuthId = systemAuthId;
	}

	public String getOccupationType() {
		return occupationType;
	}

	public void setOccupationType(String occupationType) {
		this.occupationType = occupationType;
	}

	public String getAuthType() {
		return authType;
	}

	public void setAuthType(String authType) {
		this.authType = authType;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
}
