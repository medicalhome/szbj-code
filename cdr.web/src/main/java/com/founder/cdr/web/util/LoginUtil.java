package com.founder.cdr.web.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.founder.cdr.core.Constants;
import com.founder.cdr.entity.SystemAccountLog;
import com.founder.cdr.util.DateUtils;
import com.founder.cdr.util.StringUtils;
import com.founder.cdr.web.loginValidation.LoginUserDetails;

public class LoginUtil {
	
	 private static Logger logger = LoggerFactory.getLogger(LoginUtil.class);

	// $Author :jin_peng
    // $Date : 2012/10/29 17:38$
    // [BUG]0010836 ADD BEGIN
    /**
     * 获取客户端ip地址
     * @param request 上下文对象
     * @return 客户端ip地址
     */
    private static  String getClientIpAddr(HttpServletRequest request)
    {
        // 分不同的特殊情况获取用户客户端ip地址
        String ip = request.getHeader("X-Forwarded-For");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getRemoteAddr();
        }

        return ip;
    }

    // $Author:jin_peng
    // $Date:2013/09/23 15:50
    // [BUG]0037540 ADD BEGIN
    /**
     * 获取客户端机器名地址
     * @param request 上下文对象
     * @return 客户端ip地址
     */
    private static String getClientComputerName(HttpServletRequest request, String ip)
    {
        String clientHostName = null;

        try
        {
        	//登录时，为避免长时间获取机器名，赋IP值
			if(request.getRequestURI().contains("login")){
				clientHostName = request.getRemoteHost();
			}else{
				clientHostName = InetAddress.getByName(ip).getHostName();
			}
        }
        catch (UnknownHostException e)
        {
            logger.error("获取客户端机器名失败，客户端ip地址为：" + ip);

            e.printStackTrace();
        }

        return clientHostName;
    }

    // [BUG]0037540 ADD END

    /**
     * 获取用户日志对象
     * @param request 上下文对象
     * @param userDetails 用户信息对象
     * @param patientDomain 域ID
     * @return 已设置完成的用户日志对象
     */
    public static SystemAccountLog getSystemAccountLog(HttpServletRequest request,
            LoginUserDetails userDetails, String patientDomain)
    {
        // 链接到cdr系统的方式编码
        String linkKindsCode = null;

        // 链接到cdr系统的方式名称
        String linkKindsName = null;

        // 获取系统当前时间
        Date nowDate = DateUtils.getSystemTime();

        // 创建用户日志对象
        SystemAccountLog systemAccountLog = new SystemAccountLog();
        
        // 设置用户主键内部序列号
        systemAccountLog.setAccountLogSn(userDetails.getSequenceNo());
        
        //针对药房链接记录，把链接中userid作为日志的用户名
		if(null != userDetails.getInpatientType() && Constants.obtainViewTypesMap().containsKey(userDetails.getInpatientType())){
			systemAccountLog.setUserId(userDetails.getDisplayUsername());
		}else{
	        // 设置用户ID
	        systemAccountLog.setUserId(userDetails.getUsername());
		}
		//针对输液统计，emr3.0，把链接中userid作为日志的用户名 wang.meng
		if(null != userDetails.getViewId() && Constants.obtainViewIdsMap().containsKey(userDetails.getViewId())){
			systemAccountLog.setUserId(userDetails.getDisplayUsername());
		}else{
	        // 设置用户ID
	        systemAccountLog.setUserId(userDetails.getUsername());
		}
        // 设置用户ID
 //       systemAccountLog.setUserId(userDetails.getUsername());

        // 设置用户名称
        systemAccountLog.setUserName(userDetails.getDisplayUsername());

        // 设置用户所在部门
        systemAccountLog.setDeptCode(userDetails.getDeptCode());

        // 设置登陆使用cdr系统开始时间
        systemAccountLog.setUseBeginDate(nowDate);

        // $Author: jin_peng
        // $Date : 2013/11/25 13:41
        // $[BUG]0039829 MODIFY BEGIN
        if (userDetails.isEMRFlag())
        {
            linkKindsCode = Constants.LINK_KINDS_CODE_EMR;

            linkKindsName = Constants.LINK_KINDS_NAME_EMR;
        }
        else
        {
            
        	 // emr3.0及输液统计 wang.meng
        	if (null != userDetails.getViewId() && !userDetails.getViewId().equals("")){
            	linkKindsCode = userDetails.getViewId();
            	linkKindsName = Constants.obtainSystemIdsMap().get(userDetails.getSystemId())+Constants.obtainViewIdsMap().get(userDetails.getViewId());
            }
        	// 分别判断用户链接到cdr系统的方式（登陆页面登陆/集成登陆）
            // 集成门诊
        	else if (Constants.PATIENT_DOMAIN_OUTPATIENT.equals(patientDomain))
            {
                linkKindsCode = Constants.LINK_KINDS_CODE_OUT;

                linkKindsName = Constants.LINK_KINDS_NAME_OUT;
            }
            // 集成住院
            else if (Constants.PATIENT_DOMAIN_INPATIENT.equals(patientDomain))
            {
            	if(null != userDetails.getInpatientType() && Constants.obtainViewTypesMap().containsKey(userDetails.getInpatientType())){//判断是否药房链接
                    linkKindsCode = Constants.LINK_KINDS_CODE_INPATIENT;

                    linkKindsName = Constants.LINK_KINDS_NAME_INPATIENT; 
                    if(null != userDetails.getSceneType() && !userDetails.getSceneType().equals("")){
                    	linkKindsCode = userDetails.getSceneType();
                    	linkKindsName = Constants.obtainSceneTypesMap().get(userDetails.getSceneType());
                    }
            	}
            	else{
                    linkKindsCode = Constants.LINK_KINDS_CODE_IN;

                    linkKindsName = Constants.LINK_KINDS_NAME_IN;            		
            	}
            }
            // cdr登陆页面
            else if (StringUtils.isEmpty(patientDomain))
            {
                linkKindsCode = Constants.LINK_KINDS_CODE_CDR;

                linkKindsName = Constants.LINK_KINDS_NAME_CDR;
            }
        }

        // $[BUG]0039829 MODIFY END

        // 设置链接cdr系统方式编码
        systemAccountLog.setLinkKindsCode(linkKindsCode);

        // 设置链接cdr系统方式名称
        systemAccountLog.setLinkKindsName(linkKindsName);

        // 设置用户客户端ip地址
        String ipAddress = getClientIpAddr(request);

        systemAccountLog.setClientIp(ipAddress);

        // $Author:jin_peng
        // $Date:2013/09/23 15:50
        // [BUG]0037540 ADD BEGIN
        // 设置用户客户端机器名
        String computerName = getClientComputerName(request, ipAddress);

        systemAccountLog.setClientComputerName(computerName);

        // [BUG]0037540 ADD END

        // 设置记录创建时间
        systemAccountLog.setCreateTime(nowDate);

        // 设置记录更新时间
        systemAccountLog.setUpdateTime(nowDate);

        // 设置记录新建时默认更新回数
        systemAccountLog.setUpdateCount(Constants.INSERT_UPDATE_COUNT);

        // 设置记录新建时默认删除标识
        systemAccountLog.setDeleteFlag(Constants.NO_DELETE_FLAG);

        return systemAccountLog;
    }
}
