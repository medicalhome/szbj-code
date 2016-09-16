package com.founder.cdr.dao;

import java.util.List;
import java.util.Map;

import com.founder.cdr.dto.role.RoleUsersDto;
import com.founder.cdr.dto.role.SystemAuthDto;
import com.founder.cdr.dto.role.SystemRoleDto;
import com.founder.fasf.core.util.daohelper.annotation.Arguments;
import com.founder.fasf.web.paging.PagingContext;

/**
 * ggfw迁移
 * @author yu_yzh
 * */
public interface RoleAccessDao {

	/**
	 * 读取角色列表
	 * @param roleName 角色名
	 * @param occuType 角色类别(医生，护士)
	 * */
	@Arguments({"roleName", "occuType"})
	public List<SystemRoleDto> selectRoleList(String roleName, int occuType, PagingContext pagingContext);
	
	/**
	 *  取得该角色下的人员
	 * @param roleId
	 * @return
	 */
	@Arguments({"roleId", "userNo", "userName", "userDeptCode", "hidden"})
	public List<RoleUsersDto> selectRoleUsers(String roleId, String userNo, String userName, String userDeptCode, String hidden, PagingContext pagingContext);
	
	/**
	 * 取得职业类型所有的权限
	 * */
	@Arguments("occu")
	public List<SystemAuthDto> selectRoleAllAuth(String occu);
	/**
	 *  取得该角色下的权限
	 * @param roleId
	 * @return
	 */
	@Arguments("roleId")
	public List<SystemAuthDto> selectRoleAuth(String roleId);
	
	
	
	
	/**
	 * 查询人员所在角色的权限
	 * @param userNo
	 * @return
	 */
//	@Arguments({"userNo"})
//	public List<ActlAuth> selectRoleAuthByUserNo(String userNo);
	
	/**
	 * 删除角色下的人员
	 * */
//	@Arguments({"roleId, userId"})
//	public void deleteRoleUser(String roleId, String userId);
	
	/**
	 * 读取人员列表
	 * */
	@Arguments({"userId", "userName", "userDeptCode"})
	public List<RoleUsersDto> selectUsers(String userId, String userName, String userDeptCode, PagingContext pagingContext);
	
	/**
	 * 读取人员列表
	 * */
	@Arguments({"roleId", "userId", "userName", "userDeptCode", "hidden"})
	public List<RoleUsersDto> selectUsersWithoutRoleUsers(String roleId, String userId, String userName, String userDeptCode, String hidden, PagingContext pagingContext);
	
	
	/**
	 * 删除角色下的所有权限
	 * */
	@Arguments("roleId")
	public void deleteRoleAllAuth(String roleId);
	
	
	
}
