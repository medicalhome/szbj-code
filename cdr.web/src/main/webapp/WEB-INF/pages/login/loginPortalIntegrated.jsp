<%@ page language="java" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="translation.tld" prefix="ref"%>
<%@ taglib uri="html.tld" prefix="html"%>
<jsp:useBean id="Constants" class="com.founder.cdr.core.Constants"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>用户登录</title>
<!-- <link href="./styles/login.css" rel="stylesheet" type="text/css" /> -->
<link type="text/css" rel="Stylesheet" href="../styles/jquery-ui-1.8.18.custom.modify.css"
        charset="UTF8" />
<script type="text/javascript" src="../scripts/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../scripts/jquery-ui-1.8.18.custom.min.js"></script>
<script type="text/javascript" src="../scripts/login/loginIntegrated.js"></script>
<script type="text/javascript" src="../scripts/visit/mainView.js"></script>
</head>

<body>
	<div id="alertMessage" style="display:none;"></div>

	<script type="text/javascript">
		$(function()
		{
			// 登陆集成环境验证提交
			//$Author :chang_xuewen
			// $Date : 2014/01/27 10:00$
			// [BUG]0041864 MODIFY BEGIN
			PortalIntegratedSubmit('${userId}','${pword}','${patientId}','${domainId}','${systemId}','${viewId}','${visitTimes}');
			// [BUG]0041864 MODIFY END
		});
		
		document.onkeydown = function()
		{
			// 键盘关闭浏览器记录用户退出系统日志
			keyClosed('${Constants.EXIT_KINDS_FLAG_CLOSED}');
		}
	    
		window.onbeforeunload = function()
		{
			// 关闭按钮关闭浏览器记录用户退出系统日志
			buttonClosed('${Constants.EXIT_KINDS_FLAG_CLOSED}');
		}
	</script>
</body>
</html>
