package com.founder.cdr.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.founder.cdr.dto.iam.UserListDto;

import com.founder.cdr.entity.CodePerson;
import com.founder.cdr.entity.CodeValue;
import com.founder.cdr.entity.SystemAuth;
import com.founder.cdr.entity.SystemAuthDto;
import com.founder.fasf.web.paging.PagingContext;




public interface UserManagerService {
	
	/***
	 *  加载部门字典
	 * @return
	 */
	@Transactional
	public List<CodePerson> selectDeptDict(String name);
	
	/***
     *  根据deptName读取deptCode
     * @return
     */
	@Transactional
    public List<CodePerson> selectDeptCodeByDeptName(String name);
	
	/***
	 * 加载字典
	 * @return
	 */
	@Transactional
    public List<CodeValue> selectCodeDict(String dictCode);
	
	/***
	 *  加载职务字典
	 * @return
	 */
//	public List<TDictTitle> selectPostDict();
	
	/***
	 *  加载在岗状态字典
	 * @return
	 */
//	public List<DictEmploymentStatus> selectInPostDict();
	
	/***
	 *  加载人员类型字典
	 * @return
	 */
//	public List<DictEmployeeType> selectPersonTypeDict();
	
	/***
	 *  加载系统列表
	 * @return
	 */
//	public List<IamSysInfo> selectSysInfo();
	
	/***
	 *  检索列表
	 * @param userListDto
	 * @return
	 */
	public List<UserListDto> userList(UserListDto userListDto,PagingContext pageContext);
	
	/***
	 *  检索列表
	 * @param userNo
	 * @return
	 */
	
	public UserListDto selectUserByNo(String userNo);
	
	/**
	 * 初始化个人权限
	 * @param userNo
	 * @return
	 */
	
	List<SystemAuthDto> selectPersonAuthList(String userNo);
	
	/**
	 * 默认权限
	 * @param type
	 * @param area
	 * @return
	 */
	List<SystemAuth> getDefaultAccess(int type, int area);
	
//	
//	/***
//	 *  修改账户状态，启用 或 停用  启用：1 ；停用：2
//	 * @param userListDto
//	 * @return
//	 */
//	public void updateStates(UserListDto userListDto,String state);
	
	/***
	 *  重置密码
	 * @param userListDto
	 */
	public void updatePasswd(UserListDto userListDto,int reType,int riType);

	/***
	 * 保存个人设定权限
	 * @param userNo
	 * @param authList
	 */
	public void saveAuthList(String userNo, List<String> authList);

	/**
	 * 更改密码
	 * @param userId 用户id
	 * @param userName 用户名
	 * @param 新密码的md5值
	 * */
	public boolean changeUserPassword(String userId, String userName, String newPasswordMD5);
	
//	/***
//	 *  根据用户Id删除用户
//	 * @param userListDto
//	 */
//	public void deleteUser(UserListDto userListDto);
//	
//	/***
//	 *  已启用账户列表
//	 * @param userListDto
//	 * @return
//	 */
//	public List<UserListDto> startedUserList(UserListDto userListDto,PagingContext pageContext);
//	
//	/***
//     * 已启用的并选择了公共服务系统的账户列表
//     * 
//     * @param userListDto
//     * @return
//     */
//    public List<UserListDto> startedAndCommonUserList(UserListDto userListDto,
//            PagingContext pageContext);
//	
//	
//	/***
//	 *  到期账户停用
//	 */
//	public void stopUsersIfEnd();
//	
//	/***
//	 * 
//	 *  查询因到期应该停用的账户列表
//	 * @return
//	 */
//	public List<IamAccountInfo>  selectEndUsers();
//	
//	/***
//	 *  查询账户权限
//	 */
//	public List<String> selectUserAuthorities(String[] userIds);
//	
//	/***
//	 *  保存账户权限
//	 */
//	public int saveAccountAuthorities(TreeDto treeDto);
//	
//	/***
//	 *  查询用户列表(权限设置)
//	 */
//	public List<UserListDto> searchSelectedUsers(String[] userIds);
//	
//	/***
//	 *  发送账户更新通知
//	 */
//	public void sendMQMsgForAccount(List<IamAccountInfo> accountLst,
//			String action, String targetSysCode);
//
//	public Map[] selectSystemReg(SystemRegisterDto systemRegisterDto,
//			PagingContext pagingContext);
}
