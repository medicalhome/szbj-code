<%@ page language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="translation.tld" prefix="ref" %>
<%@ taglib uri="html.tld" prefix="html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>用户登录</title>
	<link href="./styles/login.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<div class="container">
		<div class="header">
			<div class="logo"></div>
		</div>
		<div class="main">
			<div class="mainleft"></div>
			<div class="mainright">
				<div class="xtdl">系 统 登 录</div>
				<ul class="name">
					<li>用户名：<input name="UserName"  type="text" /></li>
				
					<li>密&nbsp;&nbsp;&nbsp;&nbsp;码：<input name="Password" type="text" /></li>
				</ul>
				<div class="btn">
					<div class="btn_1"><a name="a" href="../cdr/visit/13.html" style="text-decoration: none;color:#464D55">确 认</a></div>
					<div class="btn_2" style="text-decoration: none;color:#464D55">清 除</div>
					<div class="clear"></div>
				</div>
			</div>
			<div class="footer">技术支持：方正国际</div>
		</div>
	</div>
</body>
</html>
