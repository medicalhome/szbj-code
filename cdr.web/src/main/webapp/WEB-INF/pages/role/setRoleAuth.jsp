<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="translation.tld" prefix="ref"%>
<%@ taglib uri="html.tld" prefix="html"%>
<jsp:useBean id="Constants" class="com.founder.cdr.core.Constants"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Pragma" content="no-cache" />
    <!-- Prevents caching at the Proxy Server -->
    <meta http-equiv="Expires" content="0" />
    <title>角色权限</title>
	<link rel="Stylesheet" type="text/css" href="../styles/tablelist.css" />
  	<script type="text/javascript" src="../scripts/role/roleAccess.js"></script>
  	<script type="text/javascript" src="../scripts/tableEnter/tableEnter.js"></script>
	<script type="text/javascript">
		$(function() {
			$("#ss_tabs").tabs();
			
		});
	</script>
</head>
<body  style="margin: 0; padding: 0;" bgcolor="#FFFFFF">
<div id="dialog">
	<div id="mainContent">
		<form id="setPersonAuthForm" name="setPersonAuthForm" method="post" action="" >
			<div id="ss_tabs">
				<table class="blockTable" cellpadding="2" cellspacing="1" border="0">
					<tr class="tabletitle">			
						<td height="28" align="center" width="30%">角色名</td>
						<td height="28" align="center" width="30%">职业类别</td>
						<td height="28" align="center" width="40%">角色描述</td>
							
					</tr>
				
				</table>
				<table id="tblid" style="width: 100%;" cellspacing="1" cellpadding="2" class="table">
					<tr id="${role.systemRoleId}" style="cursor:pointer" class="tabEnter">
						<td height="24" align="center" width="30%">${role.roleName}</td>
						<td height="24" align="center" width="30%">
							<c:if test="${role.occupationType == 0}">
								医生
							</c:if>
							<c:if test="${role.occupationType == 1}">
								护士
							</c:if>
							<c:if test="${role.occupationType == 2}">
								系统管理员
							</c:if>
						</td>
						<td height="24" align="center" width="40%">${role.memo}</td>
					</tr> 
				</table>
			
			<!--患者范围 -->
			<table id="tb_patientRange" style="width: 100%;" cellspacing="0" cellpadding="0" border="0">
							<tr>
								<td class="blockHeader" height="27" align="left" style="border-top: solid 1px #B3C4D4; font-weight: bold;" colspan="4">
								<img src="../images/pic_gm_left.png" style="padding-left: 3px; padding-right: 4px;" width="16"
											height="16" alt="" align="absmiddle" />患者范围</td>
							</tr>
							 <c:forEach items="${patientRange}" var="pr" varStatus="status">
							
							    <tr>
									<td width="100%" height="28" style="border-top:1px solid;border-color:#B3C4D4;line-height:28px;" colspan="4">
									  
									
										    <c:choose>
										       <c:when test="${fn:substring(pr.systemAuthId,0,2)=='y_'}">
										          	<input type="checkbox" id="${pr.systemAuthId}" name="patientRangeViews" checked="true" value="${pr.systemAuthId}" onfocus="this.blur()" />
										       </c:when>
										       <c:when test="${fn:substring(pr.systemAuthId,0,2)=='o_'}">
										          	<input type="checkbox" id="${pr.systemAuthId}" name="patientRangeViews" checked="true" disabled="disabled" value="${pr.systemAuthId}" onfocus="this.blur()"/>
										       	</c:when>
										       <c:otherwise>
										          <input type="checkbox" id="${pr.systemAuthId}" name="patientRangeViews" value="${pr.systemAuthId}" onfocus="this.blur()"/>
										       </c:otherwise>
										    </c:choose>${pr.memo}
								   </td>
								</tr>
							</c:forEach>
			</table>
			<!-- 患者基本信息 -->
			<table id="tb_patientInfo" style="width: 100%;" cellspacing="0" cellpadding="0" border="0">
							<tr>
								<td class="blockHeader" height="27" align="left" style="border-top: solid 1px #B3C4D4; font-weight: bold;" colspan="10">
								<img src="../images/pic_gm_left.png" style="padding-left: 3px; padding-right: 4px;" width="16"
											height="16" alt="" align="absmiddle" />患者基本信息</td>
							</tr>
							<tr>
								<c:forEach items="${patientInfo}" var="pi" varStatus="status">
								
								   
										<td  height="28" style="border-top:1px solid;border-color:#B3C4D4;line-height:28px;">
										  
										
											    <c:choose>
											       <c:when test="${fn:substring(pi.systemAuthId,0,2)=='y_'}">
											          	<input type="checkbox" id="${pi.systemAuthId}" name="patientInfoViews" checked="true" value="${pr.systemAuthId}" onfocus="this.blur()" />
											       </c:when>
											       <c:when test="${fn:substring(pi.systemAuthId,0,2)=='o_'}">
										          		<input type="checkbox" id="${pi.systemAuthId}" name="patientInfoViews" checked="true" disabled="disabled" value="${pi.systemAuthId}" onfocus="this.blur()"/>
										       		</c:when>
											       <c:otherwise>
											          	<input type="checkbox" id="${pi.systemAuthId}" name="patientInfoViews" value="${pi.systemAuthId}" onfocus="this.blur()"/>						       		
											       </c:otherwise>
											    </c:choose>${pi.memo}
									   </td>
									
								</c:forEach>
							</tr>
			</table>
			<!-- 临床信息 -->
			<table id="tb_clinicalInfo" style="width: 100%;" cellspacing="0" cellpadding="0" border="0">
							<tr>
								<td class="blockHeader" height="27" align="left" style="border-top: solid 1px #B3C4D4; font-weight: bold;" colspan="8">
								<img src="../images/pic_gm_left.png" style="padding-left: 3px; padding-right: 4px;" width="16"
											height="16" alt="" align="absmiddle" />临床信息</td>
							</tr>
							<tr>
								<c:forEach items="${clinicalInfo}" var="ci" varStatus="status">
								
								   
										<td  height="28" style="border-top:1px solid;border-color:#B3C4D4;line-height:28px;">
										  
										
											    <c:choose>
											       <c:when test="${fn:substring(ci.systemAuthId,0,2)=='y_'}">
											          	<input type="checkbox" id="${ci.systemAuthId}" name="clinicalInfoViews" checked="true" value="${ci.systemAuthId}" onfocus="this.blur()" />
											       </c:when>
											        <c:when test="${fn:substring(ci.systemAuthId,0,2)=='o_'}">
										          		<input type="checkbox" id="${ci.systemAuthId}" name="clinicalInfoViews" checked="true" disabled="disabled" value="${ci.systemAuthId}" onfocus="this.blur()"/>
										       		</c:when>
											       <c:otherwise>
											          	<input type="checkbox" id="${ci.systemAuthId}" name="clinicalInfoViews" value="${ci.systemAuthId}" onfocus="this.blur()"/>						       		
											       </c:otherwise>
											    </c:choose>${ci.memo}
									   </td>
									
								</c:forEach>
							</tr>
				</table>
			</div>
			<div style="width:100%;">
				<table id="tb_button" style="width: 100%;" cellspacing="0" cellpadding="0" border="0">
					<tr><td style="height:15px;" /></tr>
					<tr>
					    <td align="center" valign="middle" style="border:0px;" height="30">
					        <input type="button"
							style="color: #464646; border: 0px; BACKGROUND-IMAGE: url(../images/button_save.png); width: 50px; height: 22px; margin-top: 3px;cursor: pointer;"
							align="absmiddle"
							onclick="saveAuth('${role.roleName }', '${role.systemRoleId}');" />
			        
							<input type="button"  onfocus="this.blur()" onclick ="closeDialog()" style="border:0px;BACKGROUND-IMAGE:url(../images/button_close.png);width:50px;height:22px;cursor: pointer;"/>
							
				        </td>
				    </tr>
					<tr><td style="height:5px;"/></tr>
				</table>
			</div>				
			</form>
	  </div>
	</div>
</body>
</html>