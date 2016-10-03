package com.yly.cdr.dao;

import java.util.List;
import java.util.Map;

import com.founder.fasf.core.util.daohelper.annotation.Arguments;
import com.founder.fasf.web.paging.PagingContext;
import com.yly.cdr.dto.iam.UserListDto;
import com.yly.cdr.entity.CodePerson;
import com.yly.cdr.entity.CodeValue;
import com.yly.cdr.entity.SystemAuth;


public interface UserManagerDao {

	/***
	 * 账户列表
	 * 
	 * @param userListDto
	 * @param pageContext
	 * @return
	 */
	@Arguments({"userid","userName","userDept","userState","authSys","postCode","inPost","personType","hidden"})
	public List<UserListDto> selectUserList(String userid, String userName,
			String userDept, String userState,String authSys,String postCode,String inPost,String personType,String hidden,PagingContext pageContext);
	
//	/**
//	 * 查询部门下挂的人员
//	 * @return
//	 */
//	public List<UserListDto> selectDeptUser();
//	
//	/***
//	 *  已启用账户列表
//	 * 
//	 * @param userListDto
//	 * @param pageContext
//	 * @return
//	 */
//	@Arguments({"userid","userName","userDept","postCode","inPost","personType"})
//	public List<UserListDto> selectStartedUserList(String userid, String userName,
//			String userDept, String postCode,String inPost,String personType, PagingContext pageContext);
//	
//	/***
//	 *  已启用的并选择了公共服务系统的账户列表
//	 * 
//	 * @param userListDto
//	 * @param pageContext
//	 * @return
//	 */
//	@Arguments({"userid","userName","userDept","postCode","inPost","personType"})
//	public List<UserListDto> selectStartedCommonUserList(String userid, String userName,
//			String userDept, String postCode,String inPost,String personType, PagingContext pageContext);
//	
//	/***
//	 *   停用到期账户
//	 * @return
//	 */
//	public int updateStatusIfEnd();
//	
//	/***
//	 * 
//	 *  查询因到期应该停用的账户列表
//	 * @return
//	 */
//	public List<IamAccountInfo>  selectEndUsers();
//	
//	/***
//	 *  查询用户ID+用户名列表(权限设置)
//	 */
//	@Arguments({"userNo"})
//	public UserListDto selectSetAuthUsers(String userNo);
	
	/***
	 * 
	 *  查询部门字典
	 * @return
	 */
	@Arguments({"name"})
	public List<CodePerson>  selectDeptDict(String name);
	
	/***
     * 
     *  查询部门code
     * @return
     */
    @Arguments({"name"})
    public List<CodePerson>  selectDeptCodeByDeptName(String name);
	
//	/***
//	 * 
//	 *  查询系统列表
//	 * @return
//	 */
//	public List<IamSysInfo>  selectSysInfo();
//	
//	/***
//	 * 
//	 *  查询职务字典
//	 * @return
//	 */
//	public List<TDictTitle>  selectPostDict();
//	
//	/***
//	 * 
//	 *  查询在岗状态字典
//	 * @return
//	 */
//	public List<DictEmploymentStatus>  selectInPostDict();
//	
//	/***
//	 * 
//	 *  查询人员类型字典
//	 * @return
//	 */
//	public List<DictEmployeeType>  selectPersonTypeDict();
//	
//	/***
//	 *  根据用户名查询用户信息
//	 */
//	@Arguments({"userNo"})
//	public List<Map> selectUserByUserNo(String userNo);
//
//	@Arguments({ "searchSysID","searchSysName", "searchCateCode", "searchSupId" })
//	public Map[] selectSystemReg(String searchSysID,String searchSysName, String searchCateCode,
//			String searchSupId, PagingContext pagingContext);
    /**
     * 查询字典
     * @return
     */
    @Arguments({"dictCode"})
    public List<CodeValue>  selectCodeDict(String dictCode);
    
    /**
     * 查询用户信息
     * @return
     */
    @Arguments({"userNo"})
	public UserListDto selectUserByNo(String userNo);
    
    /**
     * 查询用户信息
     * @return
     */
    @Arguments({"userNo"})
    public List<SystemAuth> selectRoleAuthByUserNo(String userNo);

    /**
	 * 获取缺省访问权限设定数据
	 * @param type
	 * @param area
	 * @return
	 */
	@Arguments({"type","area"})
	public List<SystemAuth> selectDefaultAccess(int type, int area);

	 /**
	  * 获取个人权限设定数据
	  * @param userNo
	  * @return
	 */
	@Arguments({"userNo"})
	public List<Map> selectPersonActlAuth(String userNo);
    
}
