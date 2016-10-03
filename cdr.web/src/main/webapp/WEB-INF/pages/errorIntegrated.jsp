<%@ page language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="translation.tld" prefix="ref"%>
<%@ taglib uri="html.tld" prefix="html"%>
<jsp:useBean id="Constants" class="com.yly.cdr.core.Constants"/>
﻿<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Pragma" content="no-cache" />
	<meta http-equiv="Expires" content="0" />
    <title></title>
	<link rel="Stylesheet" type="text/css" href="../styles/tablelist.css" />
</head>
<body style="margin: 0; padding: 0;">
	<script type="text/javascript">
		// $Author :jin_peng
	    // $Date : 2012/09/21 14:01$
	    // [BUG]0009561 ADD BEGIN
	    var userName = '${userName}';
	    var userNameReal = userName;
	    
	    if(userName != null)
    	{
	    	userNameReal = userName.split("&")[0];
    	}
	    
	    // 登陆集成页面错误提示信息
		alert("用户：" + userNameReal + " 没有访问临床数据中心的权限，请与管理员联系！");
	    // [BUG]0009561 ADD END
	</script>
</body>
</html>
