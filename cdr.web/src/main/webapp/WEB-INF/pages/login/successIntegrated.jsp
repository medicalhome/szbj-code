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
<link rel="shortcut icon" type="image/x-icon" href="favicon.ico" />
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
		// $Author :jin_peng
   		// $Date : 2012/09/21 14:01$
    	// [BUG]0009561 ADD BEGIN
		$(function()
		{
			// 登陆集成环境登陆成功后验证跳转到普通患者页面
			successIntegrated('${patientSn}','${patientId}','${patientDomain}','${labReportLid}','${labItemCode}','${orgCode}',
					'${inpatientType}','${sceneType}','${reportTypeCode}','${businessSn}','${systemId}','${viewId}','${visitTimes}');
		});
		// [BUG]0009561 ADD END
		
		//$Author :jin_peng
		// $Date : 2012/10/31 17:38$
		// [BUG]0010836 ADD BEGIN
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
		// [BUG]0010836 ADD END
	</script>
</body>
</html>
