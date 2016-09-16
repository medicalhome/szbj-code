package com.founder.cdr.dto.role;

public class AddUsersDto {
	private String addSearchRoleId;
	private String addSearchUserNo;
	private String addSearchUserName;
	private String addSearchDeptCode;
	private int pageNo;
	public String getAddSearchUserNo() {
		return addSearchUserNo;
	}
	public void setAddSearchUserNo(String addSearchUserNo) {
		this.addSearchUserNo = addSearchUserNo;
	}
	public String getAddSearchUserName() {
		return addSearchUserName;
	}
	public void setAddSearchUserName(String addSearchUserName) {
		this.addSearchUserName = addSearchUserName;
	}
	public String getAddSearchDeptCode() {
		return addSearchDeptCode;
	}
	public void setAddSearchDeptCode(String addSearchDeptCode) {
		this.addSearchDeptCode = addSearchDeptCode;
	}
	public String getAddSearchRoleId() {
		return addSearchRoleId;
	}
	public void setAddSearchRoleId(String addSearchRoleId) {
		this.addSearchRoleId = addSearchRoleId;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	
	
}
