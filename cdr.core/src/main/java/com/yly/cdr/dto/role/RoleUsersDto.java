package com.yly.cdr.dto.role;

/**
 * ggfw迁移
 * @author yu_yzh
 * */
public class RoleUsersDto {

		private String searchRoleId;
//		private String
		
		
		private String userId;
		private String userName;
		private String sex;
		private String sexName;
		private String deptCode;
		private String deptName;
		private String mobile;
		
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
		public String getSex() {
			return sex;
		}
		public void setSex(String sex) {
			this.sex = sex;
		}
		public String getSexName() {
			return sexName;
		}
		public void setSexName(String sexName) {
			this.sexName = sexName;
		}
		public String getDeptCode() {
			return deptCode;
		}
		public void setDeptCode(String deptCode) {
			this.deptCode = deptCode;
		}
		public String getDeptName() {
			return deptName;
		}
		public void setDeptName(String deptName) {
			this.deptName = deptName;
		}
		public String getMobile() {
			return mobile;
		}
		public void setMobile(String mobile) {
			this.mobile = mobile;
		}
		public String getSearchRoleId() {
			return searchRoleId;
		}
		public void setSearchRoleId(String searchRoleId) {
			this.searchRoleId = searchRoleId;
		}
}
