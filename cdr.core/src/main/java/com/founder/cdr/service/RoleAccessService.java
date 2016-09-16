package com.founder.cdr.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.founder.cdr.dto.role.AddUsersDto;
import com.founder.cdr.dto.role.RoleUsersDto;
import com.founder.cdr.dto.role.SystemAuthDto;
import com.founder.cdr.dto.role.SystemRoleDto;
import com.founder.cdr.entity.SystemAuth;
import com.founder.cdr.entity.SystemDefaultAuth;
import com.founder.cdr.entity.SystemRole;
import com.founder.fasf.web.paging.PagingContext;


/**
 * 角色权限管理服务接口
 * @author yu_yzh
 * */
@Service
public interface RoleAccessService {

	/**
	 * 根据systemRoleId获取SystemRole对象
	 * @param systemRoleId 角色id 
	 * @return system_role表实体类对象
	 * */
	@Transactional
	public SystemRole selectSystemRole(String systemRoleId);
	
	/**
	 * 读取角色列表
	 * @param systemRoleDto
	 * @param pagingContext
	 * @return 角色列表
	 * */
	@Transactional
	public List<SystemRoleDto> selectRoleList(SystemRoleDto systemRoleDto, PagingContext pagingContext);
	
	/**
	 * 保存新建的角色
	 * 
	 * */	
	public SystemRole saveAddedRole(SystemRoleDto systemRoleDto);
	
	/**
	 * 初始化编辑角色
	 * 
	 * */
	public SystemRole initEditedRole(/*SystemRoleDto systemRoleDto*/);
	
	/**
	 * 保存角色信息
	 * 
	 * */
	public void saveEditedRole(SystemRoleDto systemRoleDto);
	
	/**
	 * 初始化角色权限设定
	 * */
	public List<?> selectInitRoleAuth(String roleId);
	
	/**
	 * 删除角色
	 * */
	public void deleteRole(String roleId);
	
	/**
	 * 保存权限设定
	 * */
	public void saveRoleAuth(SystemRoleDto systemRoleDto);
	
	/**
	 * 按照检索条件取该角色下人员
	 * */
	public List<RoleUsersDto> selectRoleUsers(SystemRoleDto systemRoleDto, PagingContext pagingContext);
	
	/**
	 * 删除角色下的人员
	 * */
	public void deleteRoleUsers(SystemRoleDto systemRoleDto);
	
	/**
	 * 添加角色下的人员
	 * */
	public void addRoleUsers(SystemRoleDto systemRoleDto);
	
	/**
	 * 获取人员列表，不包括已经在该角色下的人员
	 * */
	public List<RoleUsersDto> selectUsers(AddUsersDto addUsersDto, PagingContext pagingContext);
	
	/**
	 * 获取角色权限
	 * */
	public List<SystemAuthDto> selectRoleAuth(SystemRoleDto systemRoleDto);
	
	/**
	 * 获取缺省权限
	 * */
	public List<SystemDefaultAuth> selectDefaultAuth();
	
	/**
	 * 获取角色所能拥有的所有权限
	 * */
	public List<SystemAuthDto> selectRoleAllAuth(SystemRoleDto systemRoleDto);
	
	/**
	 * 删除角色下的所有权限
	 * */
	public void deleteRoleAllAuth(SystemRoleDto systemRoleDto);
}
