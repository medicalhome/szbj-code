<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="translation.tld" prefix="ref"%>
<%@ taglib uri="html.tld" prefix="html"%>
<jsp:useBean id="Constants" class="com.founder.cdr.core.Constants" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Pragma" content="no-cache" />
<!-- Prevents caching at the Proxy Server -->
<meta http-equiv="Expires" content="0" />
<title>重置密码</title>
<link rel="Stylesheet" type="text/css" href="../styles/tablelist.css" />
    <script type="text/javascript" src="../scripts/visit/changePsw.js"></script>

<script type="text/javascript">
	$(function() {
		//调用动态表格美化
		beautyTable();
	});
</script>
</head>
<body style="margin: 0; padding: 0;">
	<form id="conditionForm" name="conditionForm" method="post"
			action="../visit/changePsw.html">
		<table class="blockTable" cellpadding="3" cellspacing="2" border="0">
			<tr class="conditionRow">
				<td align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;为了您的账号安全，请不要设置过于简单的密码，新密码的长度必须大于等于6位</td>
			</tr>
			<tr class="conditionRow"><td>&nbsp;</td></tr>
			<tr class="conditionRow">
				<td width="100%" colspan="11" height="30" align="left"
					valign="middle">
					<div class="cell" style="width: 100px; text-align: right;">原始密码：</div>
					<div class="cell" style="width: 160px; text-align: right;">
						<input type="password" id="oldPassword" style="width: 99%;"
							onmouseover="this.style.background='#FDE8FE';"
							onmouseout="this.style.background='#FFFFFF'" />
					</div>
					<div id="wrongOldPassword" class="cell" style="width: 80px; text-align: center; color : #F00; display : none">原密码不正确</div>
				</td>
			</tr>
			<tr class="conditionRow">
				<td width="100%" colspan="11" height="30" align="left"
					valign="middle">
					<div class="cell" style="width: 100px; text-align: right;">新密码：</div>
					<div class="cell" style="width: 160px; text-align: right;">
						<input type="password" id="newPassword" style="width: 99%;"
							onmouseover="this.style.background='#FDE8FE';"
							onmouseout="this.style.background='#FFFFFF'" />
					</div>
					<div id="wrongNewPassword" class="cell" style="width: 80px; text-align: center; color : #F00; display : none">请输入密码</div>
				</td>
			</tr>
			<tr class="conditionRow">
				<td width="100%" colspan="11" height="30" align="left"
					valign="middle">
					<div class="cell" style="width: 100px; text-align: right;">确认密码：</div>
					<div class="cell" style="width: 160px; text-align: right;">
						<input type="password" id="newPasswordRepeat" style="width: 99%;"
							onmouseover="this.style.background='#FDE8FE';"
							onmouseout="this.style.background='#FFFFFF'" />
					</div>
					<div id="differentNewPsw" class="cell" style="width: 80px; text-align: center; color : #F00; display : none;">密码不一致</div>
				</td>
			</tr>
			<tr class="conditionRow">
				<td width="100%" colspan="11" height="36" align="left" valign="middle">
					<div class="cell" style="width: 100px; text-align: right;"></div>
					<div style="width:80px; " class="cell" align="center"> 
						<input type="button"
							style="border:0px;BACKGROUND-IMAGE:url(../images/button_save.png);width:50px;height:22px;cursor: pointer;"
							align="middle" 
							onclick="changePsw();" />
					</div>
					<div style="width:80px; " class="cell" align="center"> 		
						<input type="button"
							style="border:0px;BACKGROUND-IMAGE:url(../images/button_close.png);width:50px;height:22px;cursor: pointer;"
							align="middle" onclick="closeDialog();" />		
					</div>
				</td>
			</tr>
		</table>	
			
	</form>		
</body>
</html>