<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="translation.tld" prefix="ref"%>
<%@ taglib uri="html.tld" prefix="html"%>
<jsp:useBean id="Constants" class="com.yly.cdr.core.Constants" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Pragma" content="no-cache" />
<!-- Prevents caching at the Proxy Server -->
<meta http-equiv="Expires" content="0" />
<title>新建角色</title>
<link rel="Stylesheet" type="text/css" href="../styles/tablelist.css" />
    <script type="text/javascript" src="../scripts/role/roleAccess.js"></script>

<script type="text/javascript">
	$(function() {
		//调用动态表格美化
		beautyTable();
	});
</script>
</head>

<body style="margin: 0; padding: 0;">
	<form id="conditionForm" name="conditionForm" method="post"
		action="../role/editRoleInfoAndUsers.html">
		<table class="blockTable" cellpadding="2" cellspacing="1" border="0">
<!-- 			<tr class="blockHeader">
				<td width="100%" colspan="11" height="36" align="left"
					valign="middle">
					<div class="cell" style="width: 550px; text-align: right;">新建角色</div>
				</td>
			</tr> -->

			<tr class="conditionRow">
				<td width="100%" colspan="11" height="30" align="left"
					valign="middle">
					<div class="cell" style="width: 60px; text-align: right;">角色名称：</div>
					<div class="cell" style="width: 98%; text-align: right;">
						<input type="text" name="roleName" style="width: 98%;"
							onmouseover="this.style.background='#FDE8FE';"
							onmouseout="this.style.background='#FFFFFF'" />
					</div></td>
			</tr>
			<tr class="conditionRow">
				<td width="100%" colspan="11" height="30" align="left"
					valign="middle">
					<div class="cell" style="width: 60px; text-align: right;">职业类别：</div>
					<div class="cell" style="width: 98%; text-align: right;">
						<div class="cell" style="width: 100%;">
							<select id="occupationType" name="occupationType" style="width: 100%;">
								<option value="${Constants.OPTION_SELECT}">请选择</option>
								<option value = "0">医生</option>
								<option value = "1">护士</option>
								<!-- <option value = "2">其他</option> -->
							</select>
						</div>
					</div></td>
			</tr>
			<tr class="conditionRow">
				<td width="100%" colspan="11" align="left" valign="middle">
					<div class="cell" style="width: 60px; text-align: right;">角色描述：</div>
					<div class="cell"
						style="width: 98%; text-align: right; height: 80px;">
						<textarea name="memo" id="memo" cols="45"
							style="width: 98%; overflow: scroll; height: 80px;" rows="5"></textarea>
					</div></td>
			</tr>
			<tr class="conditionRow">
				<td width="100%" colspan="11" height="36" align="left" valign="middle">
					<div style="width:98%; " class="cell" align="center"> 
						<input type="button"
							style="border:0px;BACKGROUND-IMAGE:url(../images/button_save.png);width:50px;height:22px;cursor: pointer;"
							align="middle" 
							onclick="saveRole();" />
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