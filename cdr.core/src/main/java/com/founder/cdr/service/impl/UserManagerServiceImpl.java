package com.founder.cdr.service.impl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.founder.cdr.core.Constants;
import com.founder.cdr.dao.UserManagerDao;
import com.founder.cdr.dto.iam.UserListDto;
import com.founder.cdr.entity.CodePerson;
import com.founder.cdr.entity.CodeValue;
import com.founder.cdr.entity.MedicalVisit;
import com.founder.cdr.entity.SystemAccount;
import com.founder.cdr.entity.SystemAccountAuth;
import com.founder.cdr.entity.SystemAuth;
import com.founder.cdr.entity.SystemAuthDto;

import com.founder.cdr.service.UserManagerService;
import com.founder.cdr.util.DateUtils;
import com.founder.cdr.util.ResetPwUtil;
import com.founder.cdr.util.StringUtils;

import com.founder.fasf.core.util.daohelper.entity.EntityDao;
import com.founder.fasf.core.util.message.LocalizableUtils;
import com.founder.fasf.web.paging.PagingContext;
import com.ibm.mq.MQException;

@Component
public class UserManagerServiceImpl implements UserManagerService {

	@Autowired
	UserManagerDao userManagerDao;

	@Autowired
	EntityDao entityDao;

	// @Autowired
	// UserManagerMQMsgSendService userManagerMQMsgSendService;
	//
	// @Autowired
	// SystemSettingsManagerService sysSetting;
	//
	// @Autowired
	// SendMsgByMobileNosWebservice sendMsgByMobileNosWebservice;
	//
	// @Autowired
	// SendEmailWebservice sendEmailWebservice;
	//
	// @Autowired
	// PwdManagerDao pwdManagerDao;
	//

	private static Logger logger = LoggerFactory
			.getLogger(UserManagerServiceImpl.class);

	/***
	 * 加载部门字典
	 * 
	 * @return
	 */
	public List<CodePerson> selectDeptDict(String name) {
		return (List<CodePerson>) userManagerDao.selectDeptDict(name);
	}

	//
	// /***
	// * 加载职务字典
	// *
	// * @return
	// */
	// public List<TDictTitle> selectPostDict()
	// {
	// return (List<TDictTitle>) userManagerDao.selectPostDict();
	// }
	//
	// /***
	// * 加载在岗状态字典
	// *
	// * @return
	// */
	// public List<DictEmploymentStatus> selectInPostDict()
	// {
	// return (List<DictEmploymentStatus>) userManagerDao.selectInPostDict();
	// }
	//
	// /***
	// * 加载人员类型字典
	// *
	// * @return
	// */
	// public List<DictEmployeeType> selectPersonTypeDict()
	// {
	// return (List<DictEmployeeType>) userManagerDao.selectPersonTypeDict();
	// }
	//
	// /***
	// * 加载系统列表
	// *
	// * @return
	// */
	// public List<IamSysInfo> selectSysInfo()
	// {
	// return (List<IamSysInfo>) userManagerDao.selectSysInfo();
	// }
	//
	/***
	 * 查询账户列表
	 */
	public List<UserListDto> userList(UserListDto userListDto,
			PagingContext pageContext) {
		String userid = userListDto.getSearchUserId() != null ? userListDto
				.getSearchUserId() : "";
		String userName = userListDto.getSearchUserName() != null ? userListDto
				.getSearchUserName() : "";
		String dept = userListDto.getSearchDept() != null ? userListDto
				.getSearchDept() : "";
		String state = userListDto.getSearchState() != null ? userListDto
				.getSearchState() : "";
		String authSys = userListDto.getSearchAuthSys() != null ? userListDto
				.getSearchAuthSys() : "";
		String postCode = userListDto.getSearchPost() != null ? userListDto
				.getSearchPost() : "";
		String InPost = userListDto.getSearchInPost() != null ? userListDto
				.getSearchInPost() : "";
		String personType = userListDto.getSearchPersonType() != null ? userListDto
				.getSearchPersonType() : "";
				
		String hidden = Constants.HIDDEN_ADMIN_ACCOUNT;
		if (hidden != null && !hidden.isEmpty()) {
			String[] hiddens = hidden.split("\\|");
			StringBuilder news = new StringBuilder();
			for (String s : hiddens) {
				news.append("'" + s + "',");
			}
			hidden = news.substring(1, news.length() - 2);
		}		
				
		return userManagerDao.selectUserList(userid, userName, dept, state,
				authSys, postCode, InPost, personType, hidden, pageContext);
	}

	/***
	 * 查询账户列表
	 */
	public UserListDto selectUserByNo(String userNo) {

		return userManagerDao.selectUserByNo(userNo);
	}

	/**
	 * 初始化个人权限
	 */

	public List<SystemAuthDto> selectPersonAuthList(String userNo) {
		List list = new ArrayList<Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", userNo);
		paramMap.put("deleteFlag", "0");
		// 初始化个人权限设定
		List pAuthList = entityDao.selectByCondition(SystemAccountAuth.class,
				paramMap);
		paramMap = null;
		SystemAuthDto actlAuthDto = null;

		// 角色和缺省的并集
		List oAuthList = userManagerDao.selectRoleAuthByUserNo(userNo);

		for (int i = 0; i < pAuthList.size(); i++) {
			String pAuthId = ((SystemAccountAuth) pAuthList.get(i))
					.getSystemAuthId();
			for (int j = 0; j < oAuthList.size(); j++) {
				String oAuthId = ((SystemAuth) oAuthList.get(j))
						.getSystemAuthId();
				if (pAuthId.equals(oAuthId)) {
					oAuthList.remove(j);
					j--;
					break;
				}
			}
		}

		for (int i = 0; i < pAuthList.size(); i++) {
			actlAuthDto = new SystemAuthDto();
			actlAuthDto.setAuthId(((SystemAccountAuth) pAuthList.get(i))
					.getSystemAuthId());
			list.add(actlAuthDto);
		}

		for (int j = 0; j < oAuthList.size(); j++) {
			actlAuthDto = new SystemAuthDto();
			actlAuthDto.setAuthId("o_"
					+ ((SystemAuth) oAuthList.get(j)).getSystemAuthId());
			list.add(actlAuthDto);
		}
		return list;

	}

	public List<SystemAuth> getDefaultAccess(int type, int area) {
		List<SystemAuth> authList = null;

		// 缺省权限列表
		authList = userManagerDao.selectDefaultAccess(type, area);

		return authList;

	}

	//
	// /***
	// * 修改账户状态 启用 或 停用
	// */
	// @Transactional
	// public void updateStates(UserListDto userListDto, String state)
	// {
	// List<IamAccountInfo> accountLst = new ArrayList<IamAccountInfo>();
	// String ids = userListDto.getUserIds();
	// if (ids != null && !"".equals(ids))
	// {
	// String[] arrayId = ids.split(",");
	// for (int i = 0; i < arrayId.length; i++)
	// {
	// IamAccountInfo entity = (IamAccountInfo) entityDao.selectById(
	// IamAccountInfo.class, arrayId[i]);
	// entity.setStatus(Integer.valueOf(state));
	// entityDao.update(entity);
	// accountLst.add(entity);
	// }
	// // 发送更新消息
	// this.sendMQMsgForAccount(accountLst, "update", null);
	// }
	// }

	/***
	 * 重置密码
	 */
	@Transactional
	public void updatePasswd(UserListDto userListDto, int resetType, int riType) {

		// 重置密码分两种，0：默认生成和用户Id相同；1：生成随机密码；
		// 邮件提醒方式有四种，0：不提醒；1：短信提醒；2：邮件提醒；3：短信 + 邮件提醒；
		List<SystemAccount> accountLst = new ArrayList<SystemAccount>();
		String ids = userListDto.getUserIds();
		if (ids != null && !"".equals(ids)) {
			String[] arrayId = ids.split(",");
			for (int i = 0; i < arrayId.length; i++) {
				Map<String, Object> condition = new HashMap<String, Object>();
				condition.put("userId", arrayId[i]);
				List entity = new ArrayList<SystemAccount>();
				entity = entityDao.selectByCondition(SystemAccount.class,
						condition);
				SystemAccount entityAccout = (SystemAccount) entity.get(0);
				this.resetPasswd(entityAccout, resetType);
				// accountLst.add(entity);
			}

			// this.sendMQMsgForAccount(accountLst, "update", null);
		}
	}

	// /***
	// * 删除用户
	// */
	// public void deleteUser(UserListDto userListDto)
	// {
	// String userNo = userListDto.getUserNo();
	// if (userNo != null && !"".equals(userNo))
	// {
	// IamAccountInfo entity = (IamAccountInfo) entityDao.selectById(
	// IamAccountInfo.class, userNo);
	// entityDao.delete(entity);
	// }
	// }
	//
	// /***
	// * 已启用账户列表
	// *
	// * @param userListDto
	// * @return
	// */
	// public List<UserListDto> startedUserList(UserListDto userListDto,
	// PagingContext pageContext)
	// {
	// String userid = userListDto.getSearchUserId() != null ?
	// userListDto.getSearchUserId()
	// : "";
	// String userName = userListDto.getSearchUserName() != null ?
	// userListDto.getSearchUserName()
	// : "";
	// String dept = userListDto.getSearchDept() != null ?
	// userListDto.getSearchDept()
	// : "";
	// String postCode = userListDto.getSearchPost() != null ?
	// userListDto.getSearchPost()
	// : "";
	// String InPost = userListDto.getSearchInPost() != null ?
	// userListDto.getSearchInPost()
	// : "";
	// String personType = userListDto.getSearchPersonType() != null ?
	// userListDto.getSearchPersonType()
	// : "";
	// return userManagerDao.selectStartedUserList(userid, userName, dept,
	// postCode, InPost, personType, pageContext);
	// }
	//
	// /***
	// * 已启用的并选择了公共服务系统的账户列表
	// *
	// * @param userListDto
	// * @return
	// */
	// public List<UserListDto> startedAndCommonUserList(UserListDto
	// userListDto,
	// PagingContext pageContext)
	// {
	// String userid = userListDto.getSearchUserId() != null ?
	// userListDto.getSearchUserId()
	// : "";
	// String userName = userListDto.getSearchUserName() != null ?
	// userListDto.getSearchUserName()
	// : "";
	// String dept = userListDto.getSearchDept() != null ?
	// userListDto.getSearchDept()
	// : "";
	// String postCode = userListDto.getSearchPost() != null ?
	// userListDto.getSearchPost()
	// : "";
	// String InPost = userListDto.getSearchInPost() != null ?
	// userListDto.getSearchInPost()
	// : "";
	// String personType = userListDto.getSearchPersonType() != null ?
	// userListDto.getSearchPersonType()
	// : "";
	// return userManagerDao.selectStartedCommonUserList(userid, userName, dept,
	// postCode, InPost, personType, pageContext);
	// }
	//
	// /***
	// *
	// * 查询因到期应该停用的账户列表
	// *
	// * @return
	// */
	// public List<IamAccountInfo> selectEndUsers()
	// {
	//
	// return userManagerDao.selectEndUsers();
	// }
	//
	// /***
	// *
	// * 到期账户停用
	// *
	// */
	// public void stopUsersIfEnd()
	// {
	//
	// List<IamAccountInfo> list = userManagerDao.selectEndUsers();
	//
	// System.out.println(list.size());
	//
	// int result = userManagerDao.updateStatusIfEnd();
	//
	// System.out.println(result);
	//
	// /***
	// *
	// * 此处有短信、邮件通知接口调用
	// *
	// */
	//
	// System.out.println("---------------停用账户封装成xml发送到各个系统-----------------------");
	// System.out.println("---------------短信或邮件通知用户账号已停用----------------");
	//
	// }
	//
	// /***
	// * 查询用户列表(权限设置)
	// */
	// public List<UserListDto> searchSelectedUsers(String[] userIds)
	// {
	// List<UserListDto> lst = new ArrayList<UserListDto>();
	// if (userIds != null && userIds.length > 0)
	// {
	// for (int i = 0; i < userIds.length; i++)
	// {
	// UserListDto dto = userManagerDao.selectSetAuthUsers(userIds[i]);
	// lst.add(dto);
	// }
	// }
	// return lst;
	// }
	//
	// /***
	// * 查询账户权限
	// */
	// public List<String> selectUserAuthorities(String[] userIds)
	// {
	// List<String> lst = new ArrayList<String>();
	// if (userIds != null && !"".equals(userIds))
	// {
	// for (int i = 0; i < userIds.length; i++)
	// {
	// Map<String, Object> condition = new HashMap<String, Object>();
	// condition.put("userNo", userIds[i]);
	// List<Object> resrs = entityDao.selectByCondition(
	// IamAccountSys.class, condition);
	// for (Object resr : resrs)
	// {
	// lst.add(((IamAccountSys) resr).getSysId());
	// }
	// }
	// }
	// return lst;
	// }
	//
	// /***
	// * 保存账户权限
	// */
	// @Transactional
	// public int saveAccountAuthorities(TreeDto treeDto)
	// {
	// try
	// {
	// if (treeDto.getIds() != null && !"".equals(treeDto.getIds()))
	// {
	// String[] users = treeDto.getIds();
	// Map[] datas = new HashMap[users.length];
	// for (int i = 0; i < users.length; i++)
	// {
	// List<IamAccountSys> delLst = new ArrayList<IamAccountSys>();
	// List<IamAccountSys> addLst = new ArrayList<IamAccountSys>();
	//
	// Map<String, Object> condition = new HashMap<String, Object>();
	// condition.put("userNo", users[i]);
	// List<Object> oldResrs = entityDao.selectByCondition(
	// IamAccountSys.class, condition);
	//
	// String[] resrs = null;
	// if (treeDto.getSelectIds() != null
	// && !"".equals(treeDto.getSelectIds()))
	// {
	// resrs = treeDto.getSelectIds().split(",");
	// }
	// else
	// {
	// IamAccountInfo userDto = (IamAccountInfo) entityDao.selectById(
	// IamAccountInfo.class, users[i]);
	// if (userDto.getStatus() == 0)
	// return ConstantsDef.OPERATION_INVALID;
	// }
	//
	// delLst = this.returnDelAuth(oldResrs, resrs);
	// addLst = this.returnAddAuth(oldResrs, resrs, users[i]);
	// if (delLst != null && delLst.size() > 0)
	// {
	// for (IamAccountSys entity : delLst)
	// {
	// entityDao.delete(entity);
	// if("S028".equals(entity.getSysId())){
	// IamUserInfo sysUser =
	// (IamUserInfo)entityDao.selectById(IamUserInfo.class, entity.getUserNo());
	// entityDao.delete(sysUser);
	// }
	// }
	// }
	// if (addLst != null && addLst.size() > 0)
	// {
	// for (IamAccountSys entity : addLst)
	// {
	// entityDao.insert(entity);
	// }
	// }
	//
	// IamAccountInfo entity = (IamAccountInfo) entityDao.selectById(
	// IamAccountInfo.class, users[i]);
	// if (treeDto.getSelectIds() != null
	// && !"".equals(treeDto.getSelectIds()))
	// {
	// // 账户启用
	// if (entity.getStatus() != ConstantsDef.ACCOUNT_STATUS_1)
	// {
	// entity.setStatus(ConstantsDef.ACCOUNT_STATUS_1);
	// this.resetPasswdForStartedUser(entity);
	// }
	// }
	// Map data = new HashMap();
	// data.put("userInfo", entity);
	// data.put("delLst", delLst);
	// data.put("addLst", addLst);
	// datas[i] = data;
	// }
	// // 发送更新消息
	// if (datas != null && datas.length > 0)
	// {
	// for (int i = 0; i < datas.length; i++)
	// {
	// List<IamAccountInfo> accountLst = new ArrayList<IamAccountInfo>();
	// accountLst.add((IamAccountInfo) datas[i].get("userInfo"));
	// List<IamAccountSys> delLst = (List<IamAccountSys>)
	// datas[i].get("delLst");
	// if (delLst != null && delLst.size() > 0)
	// {
	// for (IamAccountSys resr : delLst)
	// {
	// if (resr.getSysId().startsWith("S"))
	// {
	// this.sendMQMsgForAccount(accountLst,
	// "delete", resr.getSysId());
	// }
	// }
	// }
	// List<IamAccountSys> addLst = (List<IamAccountSys>)
	// datas[i].get("addLst");
	// if (addLst != null && addLst.size() > 0)
	// {
	// for (IamAccountSys resr : addLst)
	// {
	// if (resr.getSysId().startsWith("S"))
	// {
	// this.sendMQMsgForAccount(accountLst,
	// "insert", resr.getSysId());
	// }
	// }
	// }
	// }
	// }
	// }
	// }
	// catch (Exception e)
	// {
	// return ConstantsDef.OPERATION_FAILURE;
	// }
	// return ConstantsDef.OPERATION_SUCCESS;
	// }
	//
	// /***
	// * 返回需要删除的权限
	// */
	// private List<IamAccountSys> returnDelAuth(List<Object> oldResrs,
	// String[] newResrs)
	// {
	// List<IamAccountSys> lst = new ArrayList<IamAccountSys>();
	// if (oldResrs != null && oldResrs.size() > 0)
	// {
	// for (Object oldResr : oldResrs)
	// {
	// IamAccountSys entity = (IamAccountSys) oldResr;
	// boolean isExists = false;
	// if (newResrs != null && newResrs.length > 0)
	// {
	// for (int i = 0; i < newResrs.length; i++)
	// {
	// if (newResrs[i].equals(entity.getSysId()))
	// {
	// isExists = true;
	// break;
	// }
	// }
	// }
	// if (!isExists)
	// lst.add(entity);
	// }
	// }
	// return lst;
	// }
	//
	// /***
	// * 返回需要添加的权限
	// */
	// private List<IamAccountSys> returnAddAuth(List<Object> oldResrs,
	// String[] newResrs, String userNo)
	// {
	// List<IamAccountSys> lst = new ArrayList<IamAccountSys>();
	// if (newResrs != null && newResrs.length > 0)
	// {
	// for (int i = 0; i < newResrs.length; i++)
	// {
	// boolean isExists = false;
	// if (oldResrs != null && oldResrs.size() > 0)
	// {
	// for (Object oldResr : oldResrs)
	// {
	// IamAccountSys entity = (IamAccountSys) oldResr;
	// if (newResrs[i].equals(entity.getSysId()))
	// {
	// isExists = true;
	// break;
	// }
	// }
	// }
	// if (!isExists)
	// {
	// IamAccountSys newResr = new IamAccountSys();
	// newResr.setSysId(newResrs[i]);
	// newResr.setUserNo(userNo);
	// lst.add(newResr);
	// }
	// }
	// }
	// return lst;
	// }

	// /***
	// * 发送账户更新通知
	// */
	// public void sendMQMsgForAccount(List<IamAccountInfo> accountLst,
	// String action, String targetSysCode)
	// {
	// DateFormat DATE_FORMAT_MSG = new SimpleDateFormat("yyyyMMddHHmmss");
	// if (accountLst != null && accountLst.size() > 0)
	// {
	// for (IamAccountInfo entity : accountLst)
	// {
	// MessageHead msgHead = new MessageHead();
	// msgHead.setMsgId("BS901");
	// msgHead.setMsgName("账户信息服务结果数据");
	// msgHead.setSourceSysCode("S028");
	// msgHead.setVersion(Long.parseLong(DATE_FORMAT_MSG.format(new Date())));
	// Map[] datas = this.entityToMap(entity);
	// String result = null;
	// if (datas != null)
	// {
	// if (targetSysCode == null || targetSysCode == "")
	// {
	// List<IamAccountSys> listSys =
	// pwdManagerDao.selectVisitSys(entity.getUserNo());
	// // 人员在存在可访问的系统时才发送账户信息
	// if (listSys.size() > 0)
	// {
	// for (IamAccountSys sys : listSys)
	// {
	// msgHead.setTargetSysCode(sys.getSysId());
	// Message msg = MessageUtils.buildMessage(
	// msgHead, datas);
	// System.currentTimeMillis();
	// // 设置action的值,同时将mobile设值为空。
	// for (int i = 0; i < msg.getBody().getRows().size(); i++)
	// {
	// msg.getBody().getRows().get(i).setAction(
	// action);
	// msg.getBody().getRows().get(i).put(
	// "mobile", null);
	// }
	// result = MessageUtils.marshal(msg);
	// try
	// {
	// userManagerMQMsgSendService.sendMQMessage(result);
	// }
	// catch (MQException e)
	// {
	// e.printStackTrace();
	// }
	//
	// }
	// }else{
	// logger.info("该用户没有可访问的系统，不发送MQ消息！");
	// }
	// }
	// else
	// {
	// msgHead.setTargetSysCode(targetSysCode);
	// Message msg = MessageUtils.buildMessage(msgHead, datas);
	// System.currentTimeMillis();
	// // 设置action的值,同时将mobile设值为空。
	// for (int i = 0; i < msg.getBody().getRows().size(); i++)
	// {
	// msg.getBody().getRows().get(i).setAction(action);
	// msg.getBody().getRows().get(i).put("mobile", null);
	// }
	// result = MessageUtils.marshal(msg);
	// try
	// {
	// userManagerMQMsgSendService.sendMQMessage(result);
	// }
	// catch (MQException e)
	// {
	// e.printStackTrace();
	// }
	// }
	// }
	// }
	// }
	// }
	//
	// private Map[] entityToMap(IamAccountInfo entity)
	// {
	// Map<String, Object>[] datas = new HashMap[1];
	// Map<String, Object> data = new HashMap<String, Object>();
	// data.put("userID", entity.getUserNo());
	// data.put("userName", entity.getUserName());
	// data.put("passwd", entity.getPasswd());
	// data.put("sex", entity.getSex());
	// data.put("email", entity.getEmail());
	// data.put("mobile", "");
	// data.put("deptCode", entity.getDeptCode());
	// data.put("groupCd", entity.getGroupCd());
	// data.put("employmentStatusCd", entity.getEmploymentStatusCd());
	// data.put("employeeTypeCd", entity.getEmployeeTypeCd());
	// data.put("jobCategory", entity.getJobCategory());
	// Date date = new Date();
	// if (entity.getServiceStartDate() != null)
	// date = new Date(entity.getServiceStartDate().getTime());
	// data.put("serviceStartDate", date);
	// data.put("status", entity.getStatus());
	// data.put("memo", entity.getMemo());
	// data.put("resetPasswdFlag", entity.getResetPasswdFlag());
	// datas[0] = data;
	// return datas;
	// }
	//
	// private void resetPasswdForStartedUser(IamAccountInfo entity)
	// {
	// String passwd = "";
	// String initPasswd = "";
	// int reType = sysSetting.selectPwdCreateRule();
	// if (reType == 1)
	// {
	// initPasswd = ResetPwUtil.genRandomNum();
	// }
	// else
	// {
	// initPasswd = entity.getUserNo();
	// }
	// passwd = ResetPwUtil.md5(initPasswd);
	// entity.setPasswd(passwd);
	// entityDao.update(entity);
	// StringBuilder content = new StringBuilder();
	// content.append(LocalizableUtils.getLocalizedString("userStart.message",
	// LocaleContextHolder.getLocale()));
	// content.append(entity.getUserNo());
	// content.append(LocalizableUtils.getLocalizedString(
	// "userStart.password.message", LocaleContextHolder.getLocale()));
	// content.append(initPasswd);
	// String title = LocalizableUtils.getLocalizedString(
	// "userStart.email.title", LocaleContextHolder.getLocale());
	// SendMsgThread sendMsg = new SendMsgThread(entity, content.toString(),
	// title, sysSetting, sendMsgByMobileNosWebservice, sendEmailWebservice);
	// Thread thread = new Thread(sendMsg);
	// thread.start();
	// }
	//
	private void resetPasswd(SystemAccount entity, int resetType) {
		String passwd = "";
		String initPasswd = "";
		int reType = resetType;
		if (reType == 1) {
			initPasswd = ResetPwUtil.genRandomNum();
		} else {
			initPasswd = entity.getUserId();
		}
		passwd = ResetPwUtil.md5(initPasswd);
		entity.setPasswd(passwd);
		entityDao.update(entity);
		// StringBuilder content = new StringBuilder();
		// content.append(LocalizableUtils.getLocalizedString(
		// "resetPwd.system.message", LocaleContextHolder.getLocale()));
		// content.append(initPasswd);
		// String title = LocalizableUtils.getLocalizedString(
		// "resetPwd.email.title", LocaleContextHolder.getLocale());
		// SendMsgThread sendMsg = new SendMsgThread(entity, content.toString(),
		// title, sysSetting, sendMsgByMobileNosWebservice,
		// sendEmailWebservice);
		// Thread thread = new Thread(sendMsg);
		// thread.start();
	}

	/***
	 * 根据部门name获取部门code
	 * 
	 * @return
	 */
	public List<CodePerson> selectDeptCodeByDeptName(String name) {
		return (List<CodePerson>) userManagerDao.selectDeptCodeByDeptName(name);
	}

	// @Override
	// public Map[] selectSystemReg(SystemRegisterDto systemRegisterDto,
	// PagingContext pagingContext)
	// {
	// String searchSysID = systemRegisterDto.getSearchSysID() == null ? ""
	// : systemRegisterDto.getSearchSysID();
	// String searchSysNameValue = systemRegisterDto.getSearchSysName() == null
	// ? ""
	// : systemRegisterDto.getSearchSysName();
	// String searchSysName = searchSysNameValue.trim();
	// String searchCateCode = systemRegisterDto.getSearchCateCode() == null ?
	// ""
	// : systemRegisterDto.getSearchCateCode();
	// String searchSupId = systemRegisterDto.getSearchSupId() == null ? ""
	// : systemRegisterDto.getSearchSupId();
	// return userManagerDao.selectSystemReg(searchSysID,searchSysName,
	// searchCateCode, searchSupId, pagingContext);
	// }

	/***
	 * 加载字典
	 * 
	 * @return
	 */

	public List<CodeValue> selectCodeDict(String dictCode) {
		return (List<CodeValue>) userManagerDao.selectCodeDict(dictCode);
	}

	/***
	 * 保存个人设定权限
	 * 
	 * @param userNo
	 * @param authList
	 */
	public void saveAuthList(String userNo, List<String> authList) {
		// TODO Auto-generated method stub
		
		
		List<Map> oList = userManagerDao.selectPersonActlAuth(userNo);
		//将缺省和角色设定的权限删除，只保存个人自己设定的
		for(int i=0;i<authList.size();i++){
			if(authList.get(i).contains("o_")){
				authList.remove(i);
				i--;
			}
		}
		for(int i=0;i<oList.size();i++){
			String oAuthId = oList.get(i).get("systemAuthId").toString();
			for(int j=0;j<authList.size();j++){
				String nAuthId = authList.get(j);
				if(oAuthId.equals(nAuthId)){
					oList.remove(i);
					authList.remove(j);
					i--;
					j--;
					break;
				}
			}
		}
		// 清除个人权限
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("userId", userNo);
		for (Map map : oList) {
			String authId = map.get("systemAuthId").toString();
			condition.put("systemAuthId", authId);
			condition.put("deleteFlag", "0");
			SystemAccountAuth ActlUserAuth = (SystemAccountAuth) entityDao
					.selectByCondition(SystemAccountAuth.class, condition).get(0);
			entityDao.delete(ActlUserAuth);
		}

		List<Map> nList = new ArrayList<Map>();
		Map map = null;
		SystemAccountAuth userAuth = null;
		for (String authId : authList) {
			map = new HashMap();
			map.put("systemAuthId", authId);
			SystemAuth auth = (SystemAuth) entityDao.selectById(SystemAuth.class,
					authId);
			map.put("authDesc", auth.getMemo());
			nList.add(map);
			userAuth = new SystemAccountAuth();
			userAuth.setUserId(userNo);
			userAuth.setSystemAuthId(authId);
			Date sysdate = DateUtils.getSystemTime();
			userAuth.setCreateTime(sysdate);
			userAuth.setUpdateTime(sysdate);
			userAuth.setDeleteFlag("0");
			userAuth.setUpdateCount(BigDecimal.ZERO);
			entityDao.insert(userAuth);
			userAuth = null;
		}
		nList = null;
		map = null;
		authList = null;
	}

	@Override
	public boolean changeUserPassword(String userId, String userName,
			String newPasswordMD5) {
		Map<String, Object> condition = new HashMap<String, Object>();
    	condition.put("userId", userId);
    	condition.put("userName", userName);
    	List<Object> result = entityDao.selectByCondition(SystemAccount.class, condition);
    	if(result == null || result.isEmpty()){
    		return false;
    	} else {
    		SystemAccount sa = (SystemAccount) result.get(0);
    		sa.setPasswd(newPasswordMD5);
    		entityDao.updatePartially(sa, "passwd");
    		return true;
    	}
		
	}

}
