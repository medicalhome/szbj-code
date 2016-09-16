<%@ page language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="translation.tld" prefix="ref" %>
<%@ taglib uri="html.tld" prefix="html" %>
<jsp:useBean id="Constants" class="com.founder.cdr.core.Constants"/>
<jsp:useBean id="LoginDto" class="com.founder.cdr.dto.LoginDto"/>
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
<link type="text/css" rel="Stylesheet" href="styles/tablelist.css" charset="UTF8" charset="UTF8" />
<link type="text/css" rel="stylesheet" href="styles/validationEngine.jquery.css" charset="UTF8"  />
<link rel="icon" href="favicon.ico" type="image/x-icon" /> 
<link rel="shortcut icon" type="image/x-icon" href="favicon.ico" /> 
<script type="text/javascript" src="scripts/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="scripts/jquery-ui-1.8.18.custom.min.js"></script>
<script type="text/javascript" src="scripts/jquery.validationEngine-zh_CN.js"></script>
<script type="text/javascript" src="scripts/jquery.validationEngine.js"></script>
<script type="text/javascript" src="scripts/login/login.js"></script>
<script type="text/javascript" src="scripts/checkInput.js"></script>
</head>

<body>
	<div id="confirmMessage" style="display:none;"></div>
	<div id="loading">
		<div id="loadingScreen" style="display: none;">
			<div id="loadingMessage">数据加载中，请稍候...</div>
		</div>
	</div>
	<!-- 
      		  //   $Author :yang_mingjie
      		 //   $Date : 2014/08/11 8:49$
      		 //   [BUG]0046046 ADD BEGIN
      		 -->
	<div class="loginbj"  style="background:url(${loginBgPic});">
        <div class="login-img">
            <div class="login-logo"></div>
             <div class="SysteName" style="width:630px; height:40px; position: absolute; top:60px; font-family:'方正舒体'; font-size:37px; text-align:center;font-weight:bold;">临床数据中心</div>
      		<div class="SysteEngName" style="width:630px; height:40px; position: absolute; top:110px; font-family:'Arial'; font-size:20px; text-align:center;">Clinical Data Repository </div>
      		<!-- [BUG]0046046 ADD END -->
        </div>
        <!-- 
			 * $Author :yang_mingjie
			 * $Date : 2014/07/04 10:09$ 
			 * [BUG]0046008 MODIFY BEGIN 
			 * 版权和版本号
		-->
        <!-- <div style="position: absolute; top: 293px; left: 11px; text-align: right;">
               <span style="color: #3a719f; font-size: 12px; font-family: 宋体;font-weight:bold; ">CDR_T49_20121123</span>
        </div> -->
        <div style="margin-top:-20px;position:absolute;margin-left:130px"><span style="padding-left:0px;font-size:13px;font-weight:normal;color:#ff9900;font-weight:bold;">${errorMessage}</span></div>
        <!-- [BUG]0046008 MODIFY End -->
        <div class="login-form">
            <form name="loginForm" style="height:50px; line-height:50px; margin-left:10px;" id="loginForm" action="../cdr/j_spring_security_check" method="post">
                <span class="font12">用户名：</span><input type="text" name="j_username" id="j_username" value="${fn:indexOf(userName, '&#64;') == -1 ? userName : fn:substring(userName, 0, fn:indexOf(userName, '&#64;'))}" 
                										onkeyup="cancelPrompt('#j_username','.j_usernameformError')" onblur="obtainCookieInfoByName(this.value);" class="validate[required] input-text"/>
                <span class="font12 input-Spacing-text">密 码：</span><input type="password" name="j_password" id="j_password" 
                														onkeyup="cancelPrompt('#j_password','.j_passwordformError')" class="validate[required] input-text"/>
                <input type="button" value="登录" onclick="loginSubmit();" class="input-button input-Spacing font-white" style="margin-left:8px" />
                <input type="reset" value="重置" class="input-button font-white" />
                <input type="checkbox" id="saveLoginInfoFlag" value="" style="margin-left:4px"/>记住密码
                <img style="width:28px;height:28x;margin-left:4px;cursor:pointer;display: none;" align="middle" title="忘记密码" onclick="obtainPassword('${Constants.OBTAIN_PASSWORD_URL}')" src="images/login/login_obtain_pw.gif" />
            </form>
        </div>
    </div>
    
	<script type="text/javascript">
		// $Author: jin_peng
	    // $Date : 2013/09/12 15:37
	    // $[BUG]0036478 ADD BEGIN
	    $(function()
	    {
	    	obtainInitInfo('${LoginDto.FIRST_LOGIN_COOKIE_NAME}','${errorMessage}');
	    	
	    	jQuery("#loginForm").validationEngine();
	    	
	    	var isPasswordReset = '${isPasswordReset}';
	    	
	    	var passwordReset = '${LoginDto.PASSWORD_RESET}';
	    	
	    	var userName = '${userName}';
	    	
	    	var errorMessageTips = '${errorMessageTips}';
	    	
	    	var status = '${status}';
		    
		    if(userName.indexOf('&#64;') >= 0)
	    	{
	    		userName = userName.substring(0,userName.indexOf('&#64;'));
	    	}
		    
		    if(status != null && status != "" && status == "error")
	    	{
		    	if(userName != null && userName != "")
	    		{
		    		errorUrlTips({"userName":userName,"errorMessage":errorMessageTips});	    		
	    		}
	    	}
	    	
	    	if(isPasswordReset == passwordReset)
    		{
	    		var wid = 330;
	    		
	    		if(isIE6())
    			{
    				wid = 350;
    			}
	    		
	    		confirmMsg("密码修改", "为保证安全首次登陆用户请您修改密码。", function() 
	    		{
	    		    var h = (window.screen.availHeight - 55 - 670) / 2;

	    		    var w = (window.screen.availWidth - 5 - 1024) / 2;
	    		    
	    		    var test = window.open("${Constants.MODIFY_PASSWORD_LINK}?userNo="+userName, null, "height=670,width=1024,status=yes,toolbar=no,menubar=no,location=no,resizable=yes,left=" + w + ",top=" + h);
	    		},wid);
    		}
	    });
	    
	    // $[BUG]0036478 ADD END
	    
	    function errorUrlTips(data)
	    {
	    	if(data == null || data == undefined)
    		{
    			return false;
    		}
	    	
	    	$.ajax(
       		{
      			   type: "GET",
      			   cache: false,
      			   url: "login.html",
      			   data: "status=errorLog" + "&userName=" + data.userName + "&errorMessage=" + data.errorMessage
       		});
	    }
	
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
