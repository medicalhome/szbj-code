<%@ page language="java" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="translation.tld" prefix="ref"%>
<%@ taglib uri="html.tld" prefix="html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>用户登录</title>
<!-- <link href="./styles/login.css" rel="stylesheet" type="text/css" /> -->
<script type="text/javascript" src="../scripts/jquery-1.7.1.min.js"></script>
</head>

<body>
	<script type="text/javascript">
		// $Author :jin_peng
   		// $Date : 2012/10/30 17:12$
    	// [BUG]0010836 ADD BEGIN
    	// 系统退出
		$(function()
		{
			window.location.href = "../cdr/j_spring_security_logout";
		});
		// [BUG]0010836 ADD END
	</script>
</body>
</html>
