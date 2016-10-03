<%@ page language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="translation.tld" prefix="ref" %>
<%@ taglib uri="html.tld" prefix="html" %>
<jsp:useBean id="Constants" class="com.yly.cdr.core.Constants"/>
<jsp:useBean id="LoginDto" class="com.yly.cdr.dto.LoginDto"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>CDR临床数据中心</title>
<link href="./styles/login.css" rel="stylesheet" type="text/css" />
<link type="text/css" rel="Stylesheet" href="styles/jquery-ui-1.8.18.custom.modify.css"
        charset="UTF8" />
<link type="text/css" rel="Stylesheet" href="styles/layout-default-1.3.0rc29.15.css"
        charset="UTF8" />
<link type="text/css" rel="Stylesheet" href="styles/tablelist.css" charset="UTF8" />
<link rel="icon" href="favicon.ico" type="image/x-icon" /> 
<link rel="shortcut icon" type="image/x-icon" href="favicon.ico" /> 
<script type="text/javascript" src="scripts/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="scripts/jquery-ui-1.8.18.custom.min.js"></script>
<script type="text/javascript" src="scripts/login/login.js"></script>
</head>

<body>
	<div id="confirmMessage" style="display:none;"></div>
	
	<div id="loading">
		<div id="loadingScreen" style="display: none;">
			<div id="loadingMessage" style="">数据加载中，请稍候...</div>
		</div>
	</div>

	<form name="loginForm" id="loginForm" action="../cdr/j_spring_security_check" method="post">
		<div class="container">
			<div class="header"><div class="logo"></div></div>
			<div class="main">
				<div class="mainleft"></div>
				<div class="mainright">
					<div class="xtdl">
						<span style="padding-left:21px;">系 统 登 录</span>&nbsp;&nbsp;
					</div>
					
					<ul class="name">
						<li>用户名：<input name="j_username" id="j_username" type="text" value="${userName}" style="width:148px;"
							onblur="promptMessage('#j_username','#p_username')" onkeyup="cancelPrompt('#j_username','#p_username')" /> &nbsp;
							<span id="p_username" class="prompt">请输入用户名</span>
							<span style="padding-left:0px;font-size:12px;font-weight:normal;color:red;">${errorMessage}</span>					
						</li>					
						<li>密&nbsp;&nbsp;&nbsp;&nbsp;码：<input name="j_password" id="j_password" type="password" style="width:148px;"
							onblur="promptMessage('#j_password','#p_password')" onkeyup="cancelPrompt('#j_password','#p_password')" /> &nbsp;
							<span id="p_password" class="prompt">请输入密码</span></li>
					</ul>
					<div class="btn">
						<div class="btn_1"><a name="validate" href="javascript:void(0);" onclick="loginSubmit()" 
							style="text-decoration: none;color:#464D55">确 认</a></div>
						<div class="btn_2"><a name="clear" href="javascript:void(0);" onclick="loginClear('#j_username','#j_password')" 
							style="text-decoration: none;color:#464D55">清 除</a></div>
						<div class="clear"></div>
					</div>
				</div>
				<div class="footer" style="width:auto">Copyright ${Constants.VERSION_NO_COPYRIGHT} &copy; 2013 方正国际软件有限公司 版权所有</div>
			</div>
		</div>
	</form>
	
	<script type="text/javascript">
		// $Author: jin_peng
	    // $Date : 2013/09/12 15:37
	    // $[BUG]0036478 ADD BEGIN
	    $(function()
	    {
	    	var isPasswordReset = '${isPasswordReset}';
	    	
	    	var passwordReset = '${LoginDto.PASSWORD_RESET}';
	    	
	    	if(isPasswordReset == passwordReset)
    		{
	    		confirmMsg("密码修改", "为保证安全密码不能与用户名相同，请您修改密码。", function() 
	    		{
	    		    var h = (window.screen.availHeight - 55 - 670) / 2;

	    		    var w = (window.screen.availWidth - 5 - 1024) / 2;

	    		    var test = window.open("${Constants.MODIFY_PASSWORD_LINK}?userNo=${userName}", null, "height=670,width=1024,status=yes,toolbar=no,menubar=no,location=no,resizable=yes,left=" + w + ",top=" + h);
	    		});
    		}
	    });
	    
	    // $[BUG]0036478 ADD END
	
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
