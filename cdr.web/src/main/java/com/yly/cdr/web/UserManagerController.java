package com.yly.cdr.web;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.founder.fasf.web.paging.PagingContext;
import com.founder.fasf.web.paging.PagingContextHolder;
import com.yly.cdr.core.Constants;
import com.yly.cdr.dto.iam.UserListDto;
import com.yly.cdr.entity.CodePerson;
import com.yly.cdr.entity.CodeValue;
import com.yly.cdr.entity.SystemAuth;
import com.yly.cdr.entity.SystemAuthDto;
import com.yly.cdr.service.UserManagerService;
import com.yly.cdr.web.loginValidation.LoginUserDetails;
import com.yly.cdr.web.util.PagingHelper;



@Controller
@RequestMapping("/iam")
public class UserManagerController implements ApplicationContextAware{
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	private static Logger logger = LoggerFactory.getLogger(UserManagerController.class);
	
	@Autowired
	private UserManagerService userManagerService;
	
//	@Autowired
//	SystemSettingsManagerService sysSetting;
	
//	@Autowired
//	SystemRegisterService systemRegisterService;
	
//	@Autowired
//	TreeService treeService;
	
	/***
	 *  统一身份验证中账户列表
	 * @param userListDto
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping("/searchAccountTable")
	public ModelAndView searchAccountTable(UserListDto userListDto) throws Exception{
		ModelAndView mav = new ModelAndView();
		
		PagingContext pagingContext = PagingContextHolder.getPagingContext();
		LoginUserDetails userDetails = (LoginUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    String docRowsCntStr = userDetails.getUserSettings().getRowsPerPage();
	    int labRowsCnt = Integer.parseInt(docRowsCntStr);
	    pagingContext.setRowCnt(labRowsCnt);
	    int showLeftRowCnt = Integer.parseInt(Constants.getDETAIL_TABS_COUNT());
        pagingContext.setRowCnt(showLeftRowCnt);
		
	    if(Constants.OPTION_SELECT.equals(userListDto.getSearchPersonType())){
	    	userListDto.setSearchPersonType(null);
	    }
	    if(Constants.OPTION_SELECT.equals(userListDto.getSearchInPost())){
	    	userListDto.setSearchInPost(null);
	    }
	    
	    
		//根据deptName查询deptCode
		List<CodePerson> deptList= userManagerService.selectDeptCodeByDeptName(userListDto.getSearchDept());
		if(deptList.size()==1){
	        userListDto.setSearchDept(deptList.get(0).getCode());
		}
		List<UserListDto> userList = userManagerService.userList(userListDto, pagingContext);
		 PagingHelper.initPaging(pagingContext);
		
		List<Object> resultList = new ArrayList<Object>();
		Map<String,Object> totle =new HashMap<String,Object>();
		
		   // 检索对象为空check
        if (userList == null || userList.size() == 0)
        {
            logger.debug("没有检索对象!");
            // 页面显示页面数赋值0
            pagingContext.setPageNo(0);
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
		mav.addObject("pagingContext", pagingContext);
		if (userListDto != null) {
			mav.addObject("userListDto",userListDto);
		}
		mav.setViewName("/manage/userManager");
		return mav;
	}
	
	/***
	 * 修改账户状态  1：启用
	 * @param states
	 * @param userListDto
	 * @return
	 * @throws Exception
	 */
//	@RequestMapping(value="/iam/enableAccount")
//	public ModelAndView enableAccount(@RequestParam("states")String states, UserListDto userListDto)throws Exception{
//		ModelAndView mav = new ModelAndView();
//		this.updateStates(mav, states, userListDto);
//		mav.setViewName("springJsonView");
//		return mav;
//	}
	
	/***
	 * 修改账户状态 2：停用
	 * @param states
	 * @param userListDto
	 * @return
	 * @throws Exception
	 */
//	@RequestMapping(value="/iam/disableAccount")
//	public ModelAndView disableAccount(@RequestParam("states")String states, UserListDto userListDto)throws Exception{
//		ModelAndView mav = new ModelAndView();
//		this.updateStates(mav, states, userListDto);
//		mav.setViewName("springJsonView");
//		return mav;
//	}
	
//	private void updateStates(ModelAndView mav, String states, UserListDto userListDto){
//		if(userListDto!=null && userListDto.getUserIds()!=null){
//			userManagerService.updateStates(userListDto, states);
//			mav.addObject("resultSet", 1);
//		}else{
//			mav.addObject("resultSet", 0);
//		}
//	}
	
//	/***
//	 *  重置密码
//	 * @param userListDto
//	 * 
//	 * @return
//	 * @throws Exception
//	 */
//	@ResponseBody
//	@RequestMapping(value="/resetPW")
//	public ModelAndView resetPW(UserListDto userListDto)throws Exception{
//		ModelAndView mav = new ModelAndView();
//
//		if(userListDto!=null && userListDto.getUserIds()!=null){
//	        // 重置密码分两种，0：默认生成和用户Id相同；1：生成随机密码；
//	        // 邮件提醒方式有四种，0：不提醒；1：短信提醒；2：邮件提醒；3：短信 + 邮件提醒；
//			int reType = Constants.RESETPW_TYPE;
//			int riType = Constants.RIMIND_TYPE;
//			userManagerService.updatePasswd(userListDto, reType, riType);
//			mav.addObject("resultSet",1);
//		}else{
//			mav.addObject("resultSet",0);
//		}
//		
//	
//		return mav;
//	}
	
	
	
	
	/***
	 *  重置密码
	 * @param userListDto
	 * 
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value="/resetPW")
	public ModelAndView resetPW(UserListDto userListDto)throws Exception{
		ModelAndView mav = new ModelAndView();
		if(userListDto!=null && userListDto.getUserIds()!=null){
	        // 重置密码分两种，0：默认生成和用户Id相同；1：生成随机密码；
	        // 邮件提醒方式有四种，0：不提醒；1：短信提醒；2：邮件提醒；3：短信 + 邮件提醒；
			int reType = Constants.RESETPW_TYPE;
			int riType = Constants.RIMIND_TYPE;
			userManagerService.updatePasswd(userListDto, reType, riType);
			
		}
		return mav;
	
	}
	
	
	/***
	 *   账户管理-个人权限设定
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/setPersonAuth")
    public ModelAndView setPersonAuth(WebRequest request)throws Exception{
		ModelAndView mav = new ModelAndView();
		String userNo = request.getParameter("userNo");
	

		//用户信息
		UserListDto userInfo = userManagerService.selectUserByNo(userNo);
		
		String posiClass = userInfo.getPosiClass();
		int type=0;
		
		//初始化个人权限
		List<SystemAuthDto> list = userManagerService.selectPersonAuthList(userNo);
		
		if("护".equals(posiClass)){
			type=1;
		}
		
		//缺省权限
		//患者范围
		List<SystemAuth> patientRange = userManagerService.getDefaultAccess(type,0);
		//患者基本信息
		List<SystemAuth> patientInfo = userManagerService.getDefaultAccess(type,1);
		//临床信息
		List<SystemAuth> clinicalInfo = userManagerService.getDefaultAccess(type,2);
		
		
		for(SystemAuth pr:patientRange){
			for(SystemAuthDto auth:list){
				if(auth.getAuthId().equals(pr.getSystemAuthId())){
					pr.setSystemAuthId("y_"+pr.getSystemAuthId());
				}else if(auth.getAuthId().substring(2, auth.getAuthId().length()).equals(pr.getSystemAuthId())){
					pr.setSystemAuthId("o_"+pr.getSystemAuthId());
				}
			}
		}
		
		for(SystemAuth pi:patientInfo){
			for(SystemAuthDto auth:list){
				if(auth.getAuthId().equals(pi.getSystemAuthId())){
					pi.setSystemAuthId("y_"+pi.getSystemAuthId());
				}else if(auth.getAuthId().substring(2, auth.getAuthId().length()).equals(pi.getSystemAuthId())){
					pi.setSystemAuthId("o_"+pi.getSystemAuthId());
				}
			}
		}
		
		for(SystemAuth ci:clinicalInfo){
			for(SystemAuthDto auth:list){
				if(auth.getAuthId().equals(ci.getSystemAuthId())){
					ci.setSystemAuthId("y_"+ci.getSystemAuthId());
				}else if(auth.getAuthId().substring(2, auth.getAuthId().length()).equals(ci.getSystemAuthId())){
					ci.setSystemAuthId("o_"+ci.getSystemAuthId());
				}
			}
		}
		
		
		
		mav.addObject("authList", list);
		mav.addObject("userInfo", userInfo);
		mav.addObject("patientRange", patientRange);
		mav.addObject("patientInfo", patientInfo);
		mav.addObject("clinicalInfo", clinicalInfo);
		mav.setViewName("/manage/setPersonAuth");	
		return mav;	
	}
	
	
	/***
	 *   账户管理-保存权限设定
	 * 
	 * @param userNo
	 * @param authIds
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/saveAuth")
    public ModelAndView saveAuth(String userNo,String authIds)throws Exception{
		ModelAndView mav = new ModelAndView();
		String[] authIdArray = authIds.split(",");
		List<String> authList = new ArrayList<String>(Arrays.asList(authIdArray));
		
//		String[] patientRangeViews = request.getParameterValues("patientRangeViews");
//		String[] patientInfoViews = request.getParameterValues("patientInfoViews");
//		String[] clinicalInfoViews = request.getParameterValues("clinicalInfoViews");
//		String userNo = request.getParameter("userNo");
//		String authIds = request.getParameter("authIds");
//		List<String> authList = new ArrayList<String>();
//		if(patientRangeViews!=null){
//			authList.addAll(new ArrayList<String>(Arrays.asList(patientRangeViews)));
//		}
//		if(patientInfoViews!=null){
//			authList.addAll(new ArrayList<String>(Arrays.asList(patientInfoViews)));
//		}
//		if(clinicalInfoViews!=null){
//			authList.addAll(new ArrayList<String>(Arrays.asList(clinicalInfoViews)));
//		}
		for(int i=0;i<authList.size();i++){
			if(authList.get(i).substring(0, 2).equals("y_"))
			{
				
				authList.set(i, authList.get(i).substring(2, authList.get(i).length()));
			}
		}
//		
		userManagerService.saveAuthList(userNo,authList);
//		
		
		
		mav.setViewName("manage/setPersonAuth");
		return mav;
		
	}
	
	/***
	 *  设置权限
	 * @param userListDto
	 * @return
	 * @throws Exception
	 */
//	@RequestMapping(value="/iam/resetAuth",method=RequestMethod.POST)
//	public ModelAndView resetAuth(UserListDto userListDto)throws Exception{
//		ModelAndView mav = new ModelAndView();
//		mav.addObject("userIds", userListDto.getUserIds());
//		String[] userIds = userListDto.getUserIds().split(",");
//		List<UserListDto> userLst = userManagerService.searchSelectedUsers(userIds);
//		mav.addObject("userLst", userLst);
//		
//		List<String> checkedNodes = userManagerService.selectUserAuthorities(userIds);
//		StringBuilder selectedSystems = new StringBuilder();
//		if(checkedNodes != null && checkedNodes.size() > 0){
//			for(String str:checkedNodes){
//				selectedSystems.append(str);
//				selectedSystems.append(",");
//			}
//			mav.addObject("checkIds", selectedSystems.toString().substring(0, selectedSystems.length()-1));
//		}else{
//			mav.addObject("checkIds","");
//		}
//		
//		List<SystemRegisterDto> sysCate = systemRegisterService.selectSystemRegCate();
//		mav.addObject("sysCate", sysCate);
//		List<SystemRegisterDto> sysSupplier = systemRegisterService.selectSystemRegSupplier();
//		mav.addObject("sysSupplier", sysSupplier);
//		mav.setViewName("iam/userSetAuth");
//		return mav;
//	}
	
//	@RequestMapping("/iam/searchAuthTable")
//	public ModelAndView searchSystemTable(SystemRegisterDto systemRegisterDto) throws Exception{
//		ModelAndView mav = new ModelAndView();
//		PagingContext pagingContext = PagingContextHolder.getPagingContext();
//		Map[] systemReg = userManagerService.selectSystemReg(systemRegisterDto, pagingContext);
//		Map[] hm = new HashMap[systemReg.length+1];
//		for(int i = 0; i < systemReg.length; i++) hm[i]=systemReg[i];
//		Map total = new HashMap();
//		total.put("_total", pagingContext.getTotalRowCnt());
//		hm[systemReg.length] = total;
//		mav.addObject("resultSet", hm);
//		mav.setViewName("springJsonView");
//		return mav;
//	}
	
	/***
	 *  保存权限
	 * @param treeDto
	 * @return
	 * @throws Exception
	 */
//	@RequestMapping("/iam/saveAuthorities")
//	public ModelAndView saveAccountAuthorities(TreeDto treeDto) throws Exception {
//		ModelAndView mav = new ModelAndView();
//		int result = userManagerService.saveAccountAuthorities(treeDto);
//		if (result == 1) {
//			mav.addObject("resultSet", 1);
//		} else if (result == 2){
//			mav.addObject("resultSet", 2);
//		}
//		else {
//			mav.addObject("resultSet", 0);
//		}
//		
//		List<String> checkedNodes = userManagerService.selectUserAuthorities(treeDto.getIds());
//		StringBuilder selectedSystems = new StringBuilder();
//		if(checkedNodes != null && checkedNodes.size() > 0){
//			for(String str:checkedNodes){
//				selectedSystems.append(str);
//				selectedSystems.append(",");
//			}
//			mav.addObject("checkIds", selectedSystems.toString().substring(0, selectedSystems.length()-1));
//		}else{
//			mav.addObject("checkIds","");
//		}
//		mav.setViewName("springJsonView");
//		return mav;
//	}
	
	/***
	 *   账户管理-公共查询方法
	 * @param mav
	 * @param userSearchDto
	 * @param messages
	 */
//	private void commonSearch(ModelAndView mav, UserListDto userListDto,
//			List<String> messages) {
//		
//		//部门列表
//		List<DictDepartment> deptList = userManagerService.selectDeptDict();
//		//职务列表
//		List<TDictTitle> postList = userManagerService.selectPostDict();
//		//在岗状态列表
//		List<CodeValue> inpostList = userManagerService.selectCodeDict(Constants.EMPLOYMENT_STATUS);
//		//人员类型列表
//		List<CodeValue> personTypeList = userManagerService.selectCodeDict(Constants.EMPLOYEE_TYPE);
//		//系统列表
//		List<IamSysInfo> sysInfoList = userManagerService.selectSysInfo();
//		
//		mav.addObject("sysInfoList", sysInfoList);
//		mav.addObject("deptList", deptList);
//		mav.addObject("postList", postList);
//		mav.addObject("inpostList", inpostList);
//		mav.addObject("personTypeList", personTypeList);
//		
//		if (userListDto != null) {
//			mav.addObject("userListDto",userListDto);
//		}
//	}
	
//	@RequestMapping("/iam/getDeptDict")
//    public ModelAndView getDeptDict(@RequestParam("name") String name)
//    {System.out.println("/iam/getDeptDict");
//    System.out.println(name);
//	    if(!StringUtils.isEmpty(name)){
//	        String deptName = null;
//	        try
//	        {
//	            deptName = java.net.URLDecoder.decode(name, "utf-8");
//	            deptName = "%"+deptName+"%";
//	        }
//	        catch (UnsupportedEncodingException e)
//	        {
//	            e.printStackTrace();
//	        }
//	        ModelAndView mav = new ModelAndView();
//	        if (deptName != null && !"".equals(deptName))
//	        {
//	            List<CodePerson> deptList = userManagerService.selectDeptDict(deptName);
//	            List<Object> list = new ArrayList<Object>();
//	            CodePerson dd = null;
//	            for(int i=0;i<deptList.size();i++){
//	                dd = (CodePerson)deptList.get(i);
//	                list.add(dd.getDepartName());
//	            }
//	            mav.addObject("results", list.toArray());
//	        }
//	        mav.setViewName("springJsonView");
//	        System.out.print(mav);
//	        return mav;
//	    }
//	    else{
//	        return null;
//	    }
//    }

	

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		// TODO Auto-generated method stub
		
	}

	
}
