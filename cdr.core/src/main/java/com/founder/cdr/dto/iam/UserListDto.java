package com.founder.cdr.dto.iam;

import java.util.Date;

public class UserListDto {
	
	/***
	 *  查询条件
	 */
	private String userIds;
	private String searchUserId;
	private String searchUserName;
	private String searchDept;
	private String searchState;
	private String searchAuthSys;
	private String searchPost;
	private String searchPersonType;
	private String searchInPost;
	
	public String getSearchAuthSys() {
		return searchAuthSys;
	}
	public void setSearchAuthSys(String searchAuthSys) {
		this.searchAuthSys = searchAuthSys;
	}
	
	private int pageNo=1;
	
	
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	/**
	 * 用户ID
	 */
	private String userNo;
	/**
	 * 密码
	 */
	private String passWD;
	/**
	 *  用户名
	 */
	private String userName;
	/**
	 *  性别编码
	 */
	private int sex;
	/**
	 *  性别描述
	 */
	private String sexName;
	/**
	 *  电子邮件
	 */
	private String email;
	/**
	 *  手机
	 */
	private String mobile;
	/**
	 *  部门编码
	 */
	private String deptCode;
	/**
	 *  部门名称
	 */
	private String deptName;
	/**
	 *  职务编码
	 */
	private String groupCd;
	/**
	 *  职务名称
	 */
	private String postName;
	/**
	 *  状态
	 */
	private int status;
	/**
	 *  状态名称
	 */
	private String statusName;
	/***
	 *  在岗状态
	 */
	private String employmentStatusCd;
	/***
	 *  在岗状态字典name
	 */
	private String inPostStatus;
	/***
	 *  人员类型
	 */
	private String employeeTypeCd;
	/***
	 *  人员类型name
	 */
	private String personType;
	/***
	 *  入职日期
	 */
	private String entryDate;
	
	/***
	 *  备注
	 */
	private String  memo;
	/**
	 *  删除标记
	 */
	private int deleteFlag;
	/**
	 *  创建者
	 */
	private String createBy;
	/**
	 *  创建日期
	 */
	private Date createDt;
	/**
	 *  更新者
	 */
	private String updateBy;
	/**
	 *  更新时间
	 */
	private Date updateDt;
	
	/**
	 *  在岗状态
	 */
	private String  posiClass;
	
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getUserIds() {
		return userIds;
	}
	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}
	public String getSearchUserId() {
		return searchUserId;
	}
	public void setSearchUserId(String searchUserId) {
		this.searchUserId = searchUserId;
	}
	public String getSearchUserName() {
		return searchUserName;
	}
	public void setSearchUserName(String searchUserName) {
		this.searchUserName = searchUserName;
	}
	public String getSearchDept() {
		return searchDept;
	}
	public void setSearchDept(String searchDept) {
		this.searchDept = searchDept;
	}
	public String getSearchState() {
		return searchState;
	}
	public void setSearchState(String searchState) {
		this.searchState = searchState;
	}
	
	
	
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getPassWD() {
		return passWD;
	}
	public void setPassWD(String passWD) {
		this.passWD = passWD;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public int getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getCreateDt() {
		return createDt;
	}
	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public Date getUpdateDt() {
		return updateDt;
	}
	public void setUpdateDt(Date updateDt) {
		this.updateDt = updateDt;
	}
	public String getPersonType() {
		return personType;
	}
	public void setPersonType(String personType) {
		this.personType = personType;
	}
	public String getEntryDate() {
		return entryDate;
	}
	public void setEntryDate(String entryDate) {
		this.entryDate = entryDate;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getSexName() {
		return sexName;
	}
	public void setSexName(String sexName) {
		this.sexName = sexName;
	}
	public String getGroupCd() {
		return groupCd;
	}
	public void setGroupCd(String groupCd) {
		this.groupCd = groupCd;
	}
	public String getPostName() {
		return postName;
	}
	public void setPostName(String postName) {
		this.postName = postName;
	}
	public String getEmploymentStatusCd() {
		return employmentStatusCd;
	}
	public void setEmploymentStatusCd(String employmentStatusCd) {
		this.employmentStatusCd = employmentStatusCd;
	}
	public String getInPostStatus() {
		return inPostStatus;
	}
	public void setInPostStatus(String inPostStatus) {
		this.inPostStatus = inPostStatus;
	}
	public String getEmployeeTypeCd() {
		return employeeTypeCd;
	}
	public void setEmployeeTypeCd(String employeeTypeCd) {
		this.employeeTypeCd = employeeTypeCd;
	}
	public String getSearchPost() {
		return searchPost;
	}
	public void setSearchPost(String searchPost) {
		this.searchPost = searchPost;
	}
	public String getSearchPersonType() {
		return searchPersonType;
	}
	public void setSearchPersonType(String searchPersonType) {
		this.searchPersonType = searchPersonType;
	}
	public String getSearchInPost() {
		return searchInPost;
	}
	public void setSearchInPost(String searchInPost) {
		this.searchInPost = searchInPost;
	}
	public String getPosiClass() {
		return posiClass;
	}
	public void setPosiClass(String posiClass) {
		this.posiClass = posiClass;
	}
}
