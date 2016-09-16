package com.founder.cdr.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.founder.cdr.core.Constants;
import com.founder.cdr.dao.RoleAccessDao;
import com.founder.cdr.dto.role.AddUsersDto;
import com.founder.cdr.dto.role.RoleUsersDto;
import com.founder.cdr.dto.role.SystemAuthDto;
import com.founder.cdr.dto.role.SystemRoleDto;
import com.founder.cdr.entity.SystemAuth;
import com.founder.cdr.entity.SystemDefaultAuth;
import com.founder.cdr.entity.SystemRole;
import com.founder.cdr.entity.SystemRoleAuth;
import com.founder.cdr.entity.SystemUserRole;
import com.founder.cdr.service.RoleAccessService;
import com.founder.fasf.core.util.daohelper.entity.EntityDao;
import com.founder.fasf.web.paging.PagingContext;
/**
 * ggfw迁移
 * @author yu_yzh
 * */
@Component
public class RoleAccessServiceImpl implements RoleAccessService {

	@Autowired
	RoleAccessDao roleAccessDao;
	
	@Autowired
    EntityDao entityDao;
	
	
	@Override
	public SystemRole selectSystemRole(String systemRoleId) {
		// TODO Auto-generated method stub
		return (SystemRole) entityDao.selectById(SystemRole.class, systemRoleId);
	}
	/**
	 * 查询角色列表
	 * */
	@Override
	public List<SystemRoleDto> selectRoleList(SystemRoleDto systemRoleDto,
			PagingContext pagingContext) {
		String roleName = "";
		int occuType = -1;
		if(systemRoleDto != null) {
			String searchRoleName = systemRoleDto.getSearchRoleName();
			String searchOccuType = systemRoleDto.getSearchOccuType();
			roleName = searchRoleName != null && !searchRoleName.equals("") ? searchRoleName : roleName;
			occuType = searchOccuType != null && !searchOccuType.equals("") ? Integer.valueOf(searchOccuType) : occuType;
		}
		List<SystemRoleDto> roleList = roleAccessDao.selectRoleList(roleName, occuType, pagingContext);
		return roleList;
	}

	@Override
	public SystemRole saveAddedRole(SystemRoleDto systemRoleDto) {
		String roleName = systemRoleDto.getRoleName();
		String occupationType = systemRoleDto.getOccupationType();
		int occu = Integer.valueOf(occupationType);
		String memo = systemRoleDto.getMemo();
		Date date = new Date();
		String roleId = "" + date.getTime();
		
		SystemRole role = new SystemRole();
		role.setRoleName(roleName);
		role.setSystemRoleId(roleId);
		role.setOccupationType(new BigDecimal(occu));
		role.setMemo(memo);
		role.setCreateTime(date);
		role.setUpdateTime(date);
		role.setDeleteFlag(Constants.NO_DELETE_FLAG);
		role.setUpdateCount(new BigDecimal(1));
		entityDao.insert(role);
		
		List<SystemDefaultAuth> defaultAuth = this.selectDefaultAuth();
		for(SystemDefaultAuth sda : defaultAuth){
			if(sda.getOccupationType().equals(occupationType) || sda.getOccupationType().equals("2")){
				String authId = sda.getSystemAuthId();
				SystemRoleAuth sra = new SystemRoleAuth();
				sra.setSystemRoleId(roleId);
				sra.setSystemAuthId(authId);
				sra.setCreateTime(date);
				sra.setDeleteTime(date);
				sra.setDeleteFlag(Constants.NO_DELETE_FLAG);
				sra.setUpdateCount(new BigDecimal(1));
				entityDao.insert(sra);
			}		
		}
		return role;
	}

	@Override
	public SystemRole initEditedRole() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 保存角色编辑信息
	 * */
	@Override
	public void saveEditedRole(SystemRoleDto systemRoleDto) {
		String roleName = systemRoleDto.getRoleName();
		String roleId = systemRoleDto.getRoleId();
		String occupationType = systemRoleDto.getOccupationType();
		String memo = systemRoleDto.getMemo();
		
		SystemRole sr = (SystemRole)entityDao.selectById(SystemRole.class, roleId);
		sr.setRoleName(roleName);
		int occu = Integer.valueOf(occupationType);
		if(occu < 0) occu = 1;
		sr.setOccupationType(new BigDecimal(occu));
		sr.setMemo(memo);
		sr.setUpdateTime(new Date());
//		sr.setUpdateCount(sr.getUpdateCount().add(new BigDecimal(1)));
		
		entityDao.update(sr);

	}

	@Override
	public List<?> selectInitRoleAuth(String roleId) {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * 删除角色
	 * */
	@Override
	public void deleteRole(String roleId) {
		// TODO Auto-generated method stub
		SystemRole role = (SystemRole) entityDao.selectById(SystemRole.class, roleId);
//		//检索该角色设定的权限
//		List<Map> oList = roleAccessDao.selectRoleAuth(roleId);
//
//		//清除角色下的权限
//		Map<String,Object> condition = new HashMap<String ,Object>();
//		condition.put("systemRoleId", roleId);
//		for(Map map:oList){
//			String authId = (String)map.get("systemAuthId");
//
//			condition.put("systemAuthId", authId);
//			SystemRoleAuth actlRoleAuth = (SystemRoleAuth)entityDao.selectByCondition(SystemRoleAuth.class, condition).get(0);
//			entityDao.delete(actlRoleAuth);
//		}
//		//检索角色下的人员
//		condition.clear();
//		condition.put("systemRoleId", roleId);
//		List<Object> uList = entityDao.selectByCondition(SystemUserRole.class, condition);
//		//清除该角色下的人员
//		for(Object obj : uList){
//			SystemUserRole sur = (SystemUserRole) obj;
//			entityDao.delete(sur);
//		}
		//清除该角色
//		entityDao.delete(role);

		role.setDeleteFlag(Constants.DELETE_FLAG);
		entityDao.update(role);
	}

	/**
	 * 保存角色权限
	 * */
	@Override
	public void saveRoleAuth(SystemRoleDto systemRoleDto) {
		String roleId = systemRoleDto.getRoleId();
		String[] authIds = systemRoleDto.getAuthIds() == null ? null : systemRoleDto.getAuthIds().split(",");
		
		if(authIds != null && authIds.length > 0){
//			Map<String, Object> condition = new HashMap<String, Object>();
//			condition.put("systemRoleId", roleId);
//			List<Object> list = entityDao.selectByCondition(SystemRoleAuth.class, condition);
//			for(Object obj : list){
//				SystemRoleAuth sra = (SystemRoleAuth) obj;
//				entityDao.delete(sra);
//			}
			deleteRoleAllAuth(systemRoleDto);
			Date date = new Date();
			List<SystemRoleAuth> l = new ArrayList<SystemRoleAuth>();
			for(String authId : authIds){
				SystemRoleAuth sra = new SystemRoleAuth();
				sra.setCreateTime(date);
//				sra.setDeleteTime(date);
				sra.setDeleteFlag(Constants.NO_DELETE_FLAG);
				sra.setSystemAuthId(authId);
				sra.setSystemRoleId(roleId);
				sra.setUpdateTime(date);
				sra.setUpdateCount(new BigDecimal(1));
				l.add(sra);
//				entityDao.insert(sra);
			}
			entityDao.insertAll(l.toArray(new SystemRoleAuth[l.size()]));
		}

	}

	/**
	 * 查找角色下的用户
	 * */
	@Override
	public List<RoleUsersDto> selectRoleUsers(SystemRoleDto systemRoleDto, PagingContext pagingContext) {
		//searchRoleId为空则返回所有用户
		String roleId = systemRoleDto.getSearchRoleId() != null ? systemRoleDto.getSearchRoleId() : "";
		String userNo = systemRoleDto.getSearchUserNo() != null ? systemRoleDto.getSearchUserNo() : "";
		String userName = systemRoleDto.getSearchUserName() != null ? systemRoleDto.getSearchUserName() : "";
		String userDeptCode = systemRoleDto.getSearchDeptCode() != null ? systemRoleDto.getSearchDeptCode() : "";
		
		if(userDeptCode.equals("-1")){
			userDeptCode = "";
		}
		String hidden = getHiddenUserId();
		
		List<RoleUsersDto> userList = roleAccessDao.selectRoleUsers(roleId, userNo, userName, userDeptCode, hidden, pagingContext);
		return userList;
	}
	/**
	 * 删除角色下的用户
	 * */
	@Override
	public void deleteRoleUsers(SystemRoleDto systemRoleDto) {
		// TODO Auto-generated method stub
		String[] deleteUserIds = systemRoleDto.getDeleteUserIds() == null ? null : systemRoleDto.getDeleteUserIds().split(",");
		String roleId = systemRoleDto.getRoleId();

		if(deleteUserIds != null && deleteUserIds.length > 0){
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("systemRoleId", roleId);
			for(String userId : deleteUserIds){
				condition.put("userId", userId);
				List<Object> l = entityDao.selectByCondition(SystemUserRole.class, condition);
				SystemUserRole sur = (SystemUserRole) l.get(0);
				entityDao.delete(sur);

			}
		}
	}
	/**
	 * 新增角色下的用户
	 * */
	@Override
	public void addRoleUsers(SystemRoleDto systemRoleDto) {
		// TODO Auto-generated method stub
		String[] addUserIds = systemRoleDto.getAddUserIds() == null ? null : systemRoleDto.getAddUserIds().split(",");
		String roleId = systemRoleDto.getRoleId();
		
		//查询角色下是否已存在该用户
		List<String> users = new ArrayList<String>();
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("systemRoleId", roleId);
//		List<Object> userRoleList = entityDao.selectByCondition(SystemUserRole.class, condition);
		for(String userId : addUserIds){
			condition.put("userId", userId);
			List<Object> list = entityDao.selectByCondition(SystemUserRole.class, condition);
			if(list == null || list.isEmpty()){
				users.add(userId);
			}
		}
		//将角色下没有的用户插入的数据库中
		for(String userId : users){
			SystemUserRole sur = new SystemUserRole();
			sur.setSystemRoleId(roleId);
			sur.setUserId(userId);
			Date date = new Date();
			sur.setCreateTime(date);
			sur.setDeleteFlag(Constants.NO_DELETE_FLAG);
			sur.setUpdateTime(date);
			sur.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
			entityDao.insert(sur);
		}
	}
	@Override
	public List<RoleUsersDto> selectUsers(AddUsersDto addUsersDto, PagingContext pagingContext) {
		String roleId = addUsersDto.getAddSearchRoleId();
		String userId = addUsersDto.getAddSearchUserNo();
		String userName = addUsersDto.getAddSearchUserName();
		String userDeptCode = addUsersDto.getAddSearchDeptCode();
		userId = userId == null ? "" : userId;
		userName = userName == null ? "" : userName;
		userDeptCode = userDeptCode == null ? "" : userDeptCode;
		userDeptCode = userDeptCode.equals("-1") ? "" : userDeptCode;
//		List<RoleUsersDto> list = roleAccessDao.selectUsers(userId, userName, userDeptCode, pagingContext);
		String hidden = getHiddenUserId();
		List<RoleUsersDto> list = roleAccessDao.selectUsersWithoutRoleUsers(roleId, userId, userName, userDeptCode, hidden, pagingContext);
		return list;
	}
	
	/**
	 * 获取角色权限
	 * */
	@Override
	public List<SystemAuthDto> selectRoleAuth(SystemRoleDto systemRoleDto) {
		String roleId = systemRoleDto.getSearchRoleId();
		List<SystemAuthDto> list = roleAccessDao.selectRoleAuth(roleId);
		return list;
	}
	@Override
	public List<SystemDefaultAuth> selectDefaultAuth() {
		List<Object> list = entityDao.selectAll(SystemDefaultAuth.class);
		List<SystemDefaultAuth> l = new ArrayList<SystemDefaultAuth>();
		for(Object obj : list){
			l.add((SystemDefaultAuth) obj);
		}
		return l;
	}
	
	@Override
	public List<SystemAuthDto> selectRoleAllAuth(SystemRoleDto systemRoleDto) {
		String occu = systemRoleDto.getOccupationType();
		List<SystemAuthDto> list = roleAccessDao.selectRoleAllAuth(occu);
		return list;
	}
	@Override
	public void deleteRoleAllAuth(SystemRoleDto systemRoleDto) {
		String roleId = systemRoleDto.getRoleId();
		roleAccessDao.deleteRoleAllAuth(roleId);
		
	}

	private String getHiddenUserId(){
		String hidden = Constants.HIDDEN_ADMIN_ACCOUNT;
		if(hidden != null && !hidden.isEmpty()){
			String[] hiddens = hidden.split("\\|");
			StringBuilder news = new StringBuilder();
			for(String s : hiddens){
				news.append("'" + s + "',");
			}
			hidden = news.substring(1, news.length() - 2);
		}
		return hidden;
	}
	
	
}
