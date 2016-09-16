package com.founder.cdr.dto.role;

/**
 * ggfw迁移
 * @author yu_yzh
 * */
public class SystemRoleDto {
	
	private String searchRoleName;
	private String searchOccuType/*searchPosiType*/;
	private String searchUserNo;
	private String searchUserName;
	private String searchDeptCode;
	private String searchRoleId;
	
	
	private String roleId;
	private String roleName;
	private String occupationType;
	private String memo;
	private String userId;
	private String userName;
	private String userDeptCode;
	private int pageNo=1;
	private String deleteUserIds;
	private String addUserIds;
	private String authIds;
	
	
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public String getSearchRoleName() {
		return searchRoleName;
	}
	public void setSearchRoleName(String searchRoleName) {
		this.searchRoleName = searchRoleName;
	}
	public String getSearchOccuType() {
		return searchOccuType/*searchPosiType*/;
	}
	public void setSearchOccuType(String searchOccuType/*searchPosiType*/) {
		this.searchOccuType/*searchPosiType*/ = searchOccuType/*searchPosiType*/;
	}
	
	
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getOccupationType() {
		return occupationType;
	}
	public void setOccupationType(String occupationType) {
		this.occupationType = occupationType;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getSearchUserNo() {
		return searchUserNo;
	}
	public void setSearchUserNo(String searchUserNo) {
		this.searchUserNo = searchUserNo;
	}
	public String getSearchUserName() {
		return searchUserName;
	}
	public void setSearchUserName(String searchUserName) {
		this.searchUserName = searchUserName;
	}
	public String getSearchDeptCode() {
		return searchDeptCode;
	}
	public void setSearchDeptCode(String searchDeptCode) {
		this.searchDeptCode = searchDeptCode;
	}
	public String getSearchRoleId() {
		return searchRoleId;
	}
	public void setSearchRoleId(String searchRoleId) {
		this.searchRoleId = searchRoleId;
	}
	public String getDeleteUserIds() {
		return deleteUserIds;
	}
	public void setDeleteUserIds(String deleteUserIds) {
		this.deleteUserIds = deleteUserIds;
	}
	public String getAddUserIds() {
		return addUserIds;
	}
	public void setAddUserIds(String addUserIds) {
		this.addUserIds = addUserIds;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserDeptCode() {
		return userDeptCode;
	}
	public void setUserDeptCode(String userDeptCode) {
		this.userDeptCode = userDeptCode;
	}
	public String getAuthIds() {
		return authIds;
	}
	public void setAuthIds(String authIds) {
		this.authIds = authIds;
	}
}
