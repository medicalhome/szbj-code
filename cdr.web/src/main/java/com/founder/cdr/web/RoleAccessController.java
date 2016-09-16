package com.founder.cdr.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.founder.cdr.dto.role.AddUsersDto;
import com.founder.cdr.dto.role.RoleUsersDto;
import com.founder.cdr.dto.role.SystemAuthDto;
import com.founder.cdr.dto.role.SystemRoleDto;
import com.founder.cdr.entity.SystemAuth;
import com.founder.cdr.entity.SystemDefaultAuth;
import com.founder.cdr.entity.SystemRole;
import com.founder.cdr.service.RoleAccessService;
import com.founder.cdr.util.DateUtils;
import com.founder.cdr.web.util.PagingHelper;
import com.founder.cdr.web.util.StringEscapeEditor;
import com.founder.fasf.web.paging.PagingContext;
import com.founder.fasf.web.paging.PagingContextHolder;

/**
 * ggfw迁移
 * @author yu_yzh
 * */

@Controller
public class RoleAccessController {

/*	@Autowired
	RoleAccessService roleAccessService;
	@Autowired
	TreeService treeService;
	@Autowired
	PersonAccessService personAccessService;
	@Autowired
	DictEditService dictEditService;*/
	
	@Autowired
	RoleAccessService roleAccessService;
	
	private static Logger logger = LoggerFactory.getLogger(RoleAccessController.class);
	
	//初始化页面
	@RequestMapping("role/initRoleAuthList")
	public ModelAndView  initRoleAccessList(){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("role/roleAccess");
		return mav;
	}
	
	/**
	 * 加载角色列表
	 * */
	@RequestMapping("role/loadRoleList")
	public ModelAndView loadRoleList(SystemRoleDto systemRoleDto) throws Exception{
		ModelAndView mav = new ModelAndView();
		PagingContext pagingContext = PagingContextHolder.getPagingContext();
		List<SystemRoleDto> roleList = roleAccessService.selectRoleList(systemRoleDto, pagingContext);
		PagingHelper.initPaging(pagingContext);
		List<Object> resultList = new ArrayList<Object>();
		Map<String,Object> totle =new HashMap<String,Object>();
		//检索内容为空
		if(roleList == null || roleList.size() == 0){
			logger.debug("没有检索对象");
			//页面设置为0
			pagingContext.setPageNo(0);
		} else {	//否则将检索对象置入pagingContext中
  
			pagingContext.setRows(roleList.toArray());
		}

		totle.put("_total", pagingContext.getTotalRowCnt());
		resultList.add(totle);
		resultList.addAll(roleList);

		mav.addObject("roleList", roleList);
		mav.addObject("resultSet", resultList.toArray());
		mav.addObject("pagingContext", pagingContext);
		if(systemRoleDto != null){
			mav.addObject("systemRoleDto", systemRoleDto);
		}
		mav.setViewName("role/roleAccess");
		return mav;
	}
	
	/**
	 * 角色人员列表
	 * */
	@RequestMapping("role/editRoleUsers")
	public ModelAndView showEditRoleUsers(SystemRoleDto systemRoleDto){

		ModelAndView mav = new ModelAndView();
		PagingContext pagingContext = PagingContextHolder.getPagingContext();
		SystemRole role = roleAccessService.selectSystemRole(systemRoleDto.getSearchRoleId());
		//检索角色ID为searchRoleId的人员
		List<RoleUsersDto> userList = roleAccessService.selectRoleUsers(systemRoleDto, pagingContext);
		PagingHelper.initPaging(pagingContext);
		
		List<Object> resultList = new ArrayList<Object>();
		Map<String,Object> totle =new HashMap<String,Object>();
		
		//检索内容为空
		if(userList == null || userList.size() == 0){
			logger.debug("edit没有检索对象");
			//页面设置为0
			pagingContext.setPageNo(0);
		} else {	//否则将检索对象置入pagingContext中
			pagingContext.setRows(userList.toArray());
		}
		
        // 加载pagingContext对象
        if(userList !=null)
        {
            pagingContext.setRows(userList.toArray());
        }

		totle.put("_total", pagingContext.getTotalRowCnt());
		resultList.add(totle);
		resultList.addAll(userList);

		mav.addObject("userList", userList);
		mav.addObject("resultSet", resultList.toArray());
		logger.debug("resultList size " + resultList.size());
		
		mav.addObject("pagingContext", pagingContext);
		if(role != null){
			mav.addObject("role", role);
		}
		mav.addObject("systemRoleDto", systemRoleDto);
		mav.setViewName("role/editRoleUsers");
		return mav;
	}
	
	/**
	 * 初始化角色信息
	 * */
	@RequestMapping("role/editRoleInfo")
	public ModelAndView initEditRoleInfo(SystemRoleDto systemRoleDto){
		ModelAndView mav = new ModelAndView();
		SystemRole role = roleAccessService.selectSystemRole(systemRoleDto.getSearchRoleId());
		mav.addObject("role", role);
		mav.setViewName("role/editRoleInfo");
		return mav;
	}
	
	/**
	 * 保存角色信息
	 * */
	@RequestMapping("role/saveRoleInfo")
	public ModelAndView editRoleInfo(SystemRoleDto systemRoleDto){
		ModelAndView mav = new ModelAndView();
		roleAccessService.saveEditedRole(systemRoleDto);
		SystemRole role = roleAccessService.selectSystemRole(systemRoleDto.getRoleId());
		mav.addObject("role", mav);
		mav.setViewName("role/editRoleInfo");
		return mav;
	}
	
	/**
	 * 编辑角色的描述信息及该角色下人员
	 * */
	@RequestMapping("role/editRoleInfoAndUsers")
	public ModelAndView showEditRoleInfoAndUsers(SystemRoleDto systemRoleDto){
		logger.debug("role/editRoleInfoAndUsers");
		ModelAndView mav = new ModelAndView();
		PagingContext pagingContext = PagingContextHolder.getPagingContext();
		SystemRole role = roleAccessService.selectSystemRole(systemRoleDto.getSearchRoleId());
		//检索角色ID为searchRoleId的人员
		List<RoleUsersDto> userList = roleAccessService.selectRoleUsers(systemRoleDto, pagingContext);
		PagingHelper.initPaging(pagingContext);
		
		List<Object> resultList = new ArrayList<Object>();
		Map<String,Object> totle =new HashMap<String,Object>();
		
		//检索内容为空
		if(userList == null || userList.size() == 0){
			logger.debug("edit没有检索对象");
			//页面设置为0
			pagingContext.setPageNo(0);
		} else {	//否则将检索对象置入pagingContext中
//			logger.debug("userList size " + userList.size());
			logger.debug("test");
			pagingContext.setRows(userList.toArray());
		}
		
        // 加载pagingContext对象
        if(userList !=null)
        {
            pagingContext.setRows(userList.toArray());
        }

		totle.put("_total", pagingContext.getTotalRowCnt());
		resultList.add(totle);
		resultList.addAll(userList);

		mav.addObject("userList", userList);
		mav.addObject("resultSet", resultList.toArray());
		logger.debug("resultList size " + resultList.size());
		
		mav.addObject("pagingContext", pagingContext);
		if(role != null){
			mav.addObject("role", role);
			logger.debug(role.getRoleName());
		}
		mav.addObject("systemRoleDto", systemRoleDto);
		mav.setViewName("role/editRoleInfoAndUsers");
		return mav;
	}
	
	/**
	 * 添加新角色
	 * */
	@RequestMapping("role/addNewRole")
	public ModelAndView addNewRole(SystemRoleDto systemRoleDto){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("role/addNewRole");
		return mav;
	}
	
	/**
	 * 保存新角色信息
	 * */
	@RequestMapping("role/saveNewRole")
	public ModelAndView saveNewRole(SystemRoleDto systemRoleDto){
		ModelAndView mav = new ModelAndView();
		roleAccessService.saveAddedRole(systemRoleDto);
		
		mav.setViewName("role/addNewRole");
		return mav;
	}
	
	/**
	 * 保存编辑角色信息
	 * */
	@RequestMapping("role/saveEditedRole")
	public ModelAndView saveEditedRole(SystemRoleDto systemRoleDto){
		
		roleAccessService.saveEditedRole(systemRoleDto);
		
		ModelAndView mav = new ModelAndView();
		PagingContext pagingContext = PagingContextHolder.getPagingContext();
		SystemRole role = roleAccessService.selectSystemRole(systemRoleDto.getSearchRoleId());
		List<RoleUsersDto> userList = roleAccessService.selectRoleUsers(systemRoleDto, pagingContext);
		PagingHelper.initPaging(pagingContext);
		
		List<Object> resultList = new ArrayList<Object>();
		Map<String,Object> totle =new HashMap<String,Object>();
		
		//检索内容为空
		if(userList == null || userList.size() == 0){
			logger.debug("edit没有检索对象");
			//页面设置为0
			pagingContext.setPageNo(0);
		} else {	//否则将检索对象置入pagingContext中
//			logger.debug("userList size " + userList.size());
			logger.debug("test");
			pagingContext.setRows(userList.toArray());
		}
		
        // 加载pagingContext对象
        if(userList !=null)
        {
            pagingContext.setRows(userList.toArray());
        }

		totle.put("_total", pagingContext.getTotalRowCnt());
		resultList.add(totle);
		resultList.addAll(userList);

		mav.addObject("userList", userList);
		mav.addObject("resultSet", resultList.toArray());
		logger.debug("resultList size " + resultList.size());
		
		mav.addObject("pagingContext", pagingContext);
		if(role != null){
			mav.addObject("role", role);
			logger.debug(role.getRoleName());
		}
		mav.addObject("systemRoleDto", systemRoleDto);
		mav.setViewName("role/editRoleInfoAndUsers");
		return mav;
	}
	
	/**
	 * 删除角色下的人员	
	 * */
	@RequestMapping("role/deleteRoleUsers")
	public ModelAndView deleteRoleUsers(SystemRoleDto systemRoleDto){
//		ModelAndView mav = new ModelAndView();
		
		roleAccessService.deleteRoleUsers(systemRoleDto);
		systemRoleDto.setSearchRoleId(systemRoleDto.getRoleId());
		return showEditRoleInfoAndUsers(systemRoleDto);

	}
	
	/**
	 * 删除角色
	 * */
	@RequestMapping("role/deleteRole")
	public ModelAndView deleteRole(@RequestParam("roleId")String roleId){
		ModelAndView mav = new ModelAndView();
		roleAccessService.deleteRole(roleId);
		mav.setViewName("role/roleAccess");
		return mav;
	}
	
	/**
	 * 显示添加角色人员页面
	 * */
	@RequestMapping("role/addUsersPage")
	public ModelAndView addUsersPage(AddUsersDto addUsersDto){
		ModelAndView mav = new ModelAndView();
		PagingContext pagingContext = PagingContextHolder.getPagingContext();
		SystemRole role = roleAccessService.selectSystemRole(addUsersDto.getAddSearchRoleId());
		//去除已经在该角色下的用户
		List<RoleUsersDto> userList = roleAccessService.selectUsers(addUsersDto, pagingContext);
//		SystemRoleDto systemRoleDto = new SystemRoleDto();
//		systemRoleDto.setSearchDeptCode(addUsersDto.getAddSearchDeptCode());
//		systemRoleDto.setSearchRoleId(addUsersDto.getAddSearchRoleId());
//		systemRoleDto.setSearchUserNo(addUsersDto.getAddSearchUserNo());
//		systemRoleDto.setSearchUserName(addUsersDto.getAddSearchUserName());
//		List<RoleUsersDto> roleUserList = roleAccessService.selectRoleUsers(
//				systemRoleDto, pagingContext);
//		
//		List<RoleUsersDto> oldList = new ArrayList<RoleUsersDto>(userList);
//		for(RoleUsersDto r1 : userList){
//			for(RoleUsersDto r2 : roleUserList){
//				
//			}
//		}
		
		PagingHelper.initPaging(pagingContext);
		
		List<Object> resultList = new ArrayList<Object>();
		Map<String,Object> totle =new HashMap<String,Object>();
		
		//检索内容为空
		if(userList == null || userList.size() == 0){
			logger.debug("edit没有检索对象");
			//页面设置为0
			pagingContext.setPageNo(0);
		} else {	//否则将检索对象置入pagingContext中
//			logger.debug("userList size " + userList.size());
			logger.debug("test");
			pagingContext.setRows(userList.toArray());
		}

		totle.put("_total", pagingContext.getTotalRowCnt());
		resultList.add(totle);
		resultList.addAll(userList);

		mav.addObject("userList", userList);
		mav.addObject("resultSet", resultList.toArray());
		logger.debug("resultList size " + resultList.size());
		
		mav.addObject("pagingContext", pagingContext);
		if(role != null){
			mav.addObject("role", role);
			logger.debug(role.getRoleName());
		}
		mav.addObject("addUsersDto", addUsersDto);
		mav.setViewName("role/addUsers");
		return mav;
	}
	
	/**
	 * 添加角色人员
	 * */
	@RequestMapping("role/addUsers")
	public ModelAndView addUsers(SystemRoleDto systemRoleDto){
		roleAccessService.addRoleUsers(systemRoleDto);
		
		ModelAndView mav = new ModelAndView();
		PagingContext pagingContext = PagingContextHolder.getPagingContext();
		SystemRole role = roleAccessService.selectSystemRole(systemRoleDto.getSearchRoleId());
		List<RoleUsersDto> userList = roleAccessService.selectRoleUsers(systemRoleDto, pagingContext);
		PagingHelper.initPaging(pagingContext);
		
		List<Object> resultList = new ArrayList<Object>();
		Map<String,Object> totle =new HashMap<String,Object>();
		
		//检索内容为空
		if(userList == null || userList.size() == 0){
			logger.debug("edit没有检索对象");
			//页面设置为0
			pagingContext.setPageNo(0);
		} else {	//否则将检索对象置入pagingContext中
//			logger.debug("userList size " + userList.size());
			logger.debug("test");
			pagingContext.setRows(userList.toArray());
		}
		
        // 加载pagingContext对象
        if(userList !=null)
        {
            pagingContext.setRows(userList.toArray());
        }

		totle.put("_total", pagingContext.getTotalRowCnt());
		resultList.add(totle);
		resultList.addAll(userList);

		mav.addObject("userList", userList);
		mav.addObject("resultSet", resultList.toArray());
		logger.debug("resultList size " + resultList.size());
		
		mav.addObject("pagingContext", pagingContext);
		if(role != null){
			mav.addObject("role", role);
			logger.debug(role.getRoleName());
		}
		mav.addObject("systemRoleDto", systemRoleDto);
		mav.setViewName("role/editRoleUsers");
		return mav;
	}
	
	/**
	 * 初始化角色权限编辑
	 * */
	@RequestMapping("role/initRoleAuth")
	public ModelAndView initRoleAuth(SystemRoleDto systemRoleDto){
		ModelAndView mav = new ModelAndView();
		SystemRole role = roleAccessService.selectSystemRole(systemRoleDto.getSearchRoleId());
		List<SystemAuthDto> roleAuthList = roleAccessService.selectRoleAuth(systemRoleDto);
		systemRoleDto.setOccupationType(role.getOccupationType().toString());
		List<SystemAuthDto> allAuth = roleAccessService.selectRoleAllAuth(systemRoleDto);
		
		//缺省权限
		List<SystemDefaultAuth> defaultAuthList = roleAccessService.selectDefaultAuth();
		
		//患者范围
		List<SystemAuthDto> patientRange = new ArrayList<SystemAuthDto>();
		//患者基本信息
		List<SystemAuthDto> patientInfo = new ArrayList<SystemAuthDto>();
		//临床信息
		List<SystemAuthDto> clinicalInfo = new ArrayList<SystemAuthDto>();
		
		outer : for(SystemAuthDto auth : allAuth){
			if(auth.getAuthType().equals("0")) {//患者范围
				patientRange.add(auth);
			} else if(auth.getAuthType().equals("1")) {//患者基本信息
				patientInfo.add(auth);
			} else if(auth.getAuthType().equals("2")) {//临床信息
				clinicalInfo.add(auth);
			}
			
			for(SystemDefaultAuth sda : defaultAuthList){//缺省权限标识
				if(sda.getSystemAuthId().equals(auth.getSystemAuthId())){
					auth.setSystemAuthId("o_" + auth.getSystemAuthId());
					continue outer;
				}
			}
			
			for(SystemAuthDto roleAuth : roleAuthList){//用户权限标识
				if(roleAuth.getSystemAuthId().equals(auth.getSystemAuthId())){
					auth.setSystemAuthId("y_" + auth.getSystemAuthId());
					continue outer;
				}
			}
		}
				
		mav.addObject("role", role);
		mav.addObject("patientRange", patientRange);
		mav.addObject("patientInfo", patientInfo);
		mav.addObject("clinicalInfo", clinicalInfo);
		mav.addObject("roleAuthList", roleAuthList);
		mav.setViewName("role/setRoleAuth");
		return mav;
	}
	
	/**
	 * 保存角色权限
	 * */
	@RequestMapping("role/saveRoleAuth")
	public ModelAndView saveRoleAuth(SystemRoleDto systemRoleDto){
		systemRoleDto.setAuthIds(systemRoleDto.getAuthIds().replaceAll("o_", "").replaceAll("y_", ""));
		roleAccessService.saveRoleAuth(systemRoleDto);
		ModelAndView mav = new ModelAndView();
		SystemRole role = roleAccessService.selectSystemRole(systemRoleDto.getSearchRoleId());
		List<SystemAuthDto> roleAuthList = roleAccessService.selectRoleAuth(systemRoleDto);
		systemRoleDto.setOccupationType(role.getOccupationType().toString());
		List<SystemAuthDto> allAuth = roleAccessService.selectRoleAllAuth(systemRoleDto);
		
		//缺省权限
		List<SystemDefaultAuth> defaultAuthList = roleAccessService.selectDefaultAuth();
		
		//患者范围
		List<SystemAuthDto> patientRange = new ArrayList<SystemAuthDto>();
		//患者基本信息
		List<SystemAuthDto> patientInfo = new ArrayList<SystemAuthDto>();
		//临床信息
		List<SystemAuthDto> clinicalInfo = new ArrayList<SystemAuthDto>();
		
		for(SystemAuthDto auth : allAuth){
			if(auth.getAuthType().equals("0")) {//患者范围
				patientRange.add(auth);
			} else if(auth.getAuthType().equals("1")) {//患者基本信息
				patientInfo.add(auth);
			} else if(auth.getAuthType().equals("2")) {//临床信息
				clinicalInfo.add(auth);
			}
			
			for(SystemDefaultAuth sda : defaultAuthList){//缺省权限标识
				if(sda.getSystemAuthId().equals(auth.getSystemAuthId())){
					auth.setSystemAuthId("o_" + auth.getSystemAuthId());
					break;
				}
			}
			
			for(SystemAuthDto roleAuth : roleAuthList){//用户权限标识
				if(roleAuth.getSystemAuthId().equals(auth.getSystemAuthId())){
					auth.setSystemAuthId("y_" + auth.getSystemAuthId());
					break;
				}
			}
			
		}
				
		mav.addObject("role", role);
		mav.addObject("patientRange", patientRange);
		mav.addObject("patientInfo", patientInfo);
		mav.addObject("clinicalInfo", clinicalInfo);
		mav.addObject("roleAuthList", roleAuthList);
		mav.setViewName("role/setRoleAuth");
		return mav;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
//		binder.registerCustomEditor(String.class, new StringEscapeEditor(true, false, false));
	}
	
	
}
