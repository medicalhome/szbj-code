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
    <title>角色权限</title>
    <link rel="Stylesheet" type="text/css" href="../styles/tablelist.css" />
    <script type="text/javascript" src="../scripts/role/roleAccess.js"></script>
	<script type="text/javascript">
		$(function(){
			//调用动态表格美化
			beautyTable();
		});
	</script>
	<script type="text/javascript">
		var type = "${systemRoleDto.searchOccuType}";
		if(type!="" && type != "undefined"){
			type = Number(type)+1;
			document.getElementById("searchOccuType").options[type].selected = true;				
		}
	</script>
</head>
<body style="margin: 0; padding: 0;">
	<form id="conditionForm" name="conditionForm" method="post" action="../role/loadRoleList.html" onkeydown="if(event.keyCode==13){return false;}">
		<table class="blockTable" cellpadding="2" cellspacing="1" border="0">
		
			<tr class="conditionRow">
				<td width="100%" colspan="11" height="36" align="left" valign="middle">
					<div class="cell" style="width: 60px; text-align: right;">角色名</div>
					<div class="cell" style="width: 80px;">
							
							<input type="text" name="searchRoleName" style="width: 98%;" onmouseover="this.style.background='#FDE8FE';"
	                        onmouseout="this.style.background='#FFFFFF'" value="${systemRoleDto.searchRoleName}" />
   							
					</div>
					
					<div style="width:180px;" class="cell">
						<div class="cell" style="width: 60px; text-align: right">职业类别</div>
						<div class="cell" style="width: 110px;">
							<select id="searchOccuType" name="searchOccuType" style="width: 100%;" >
								<option value="${Constants.OPTION_SELECT}">请选择</option>
								<%-- <html:options domain=""
								 value="${systemRoleDto.occupationType}" /> --%>
								 <option value = "0">医生</option>
								 <option value = "1">护士</option>
								 <option value = "2">系统管理员</option>
								 <!-- <option value = "2">其他</option> -->
							</select>
						</div>
					</div>
					
					<div style="width:160px;" class="cell">
						<div class="cell" style="width: 70px; text-align: center;">
							<input type="button"
								style="color: #464646; border: 0px; BACKGROUND-IMAGE: url(../images/5.jpg); width: 57px; height: 25px; margin-top: 3px;cursor: pointer;"
								align="absmiddle"
								onclick="search('../role/loadRoleList.html', 'conditionForm');" />
						</div>
					</div>
					<div style="width:100px; " class="cell" > 
						<input type="button"
							style="color: #464646; border: 0px; width: 57px; height: 25px; margin-top: 3px;cursor: pointer;"
							align="middle" value="添加" onclick="showDialog('../role/addNewRole.html', '新建角色', {}, 300, 294);" />
					</div>	
				</td>
			</tr>
			
			<tr class="tabletitle">
				<td height="28" align="center" width="10%" >列号</td>
				<td height="28" align="center" width="25%" >角色名</td>
				<td height="28" align="center" width="25%">职业类别</td>
				<td height="28" align="center" width="15%">角色描述</td>
				<td height="28" align="center" width="25%">操作</td>			
			</tr>
	
		</table>
	</form>
	
	<div id='listcon'>
	<!--[if lt ie 8]><div style='+zoom:1;'><![endif]-->
	<table id="tblid" style="width: 100%;" cellspacing="1" cellpadding="2">
		
		<c:if test="${fn:length(roleList)==0}">
			<tr>
				<td colspan="11" align="center" class="odd" height="24">没有相关数据！</td>
			</tr>
		</c:if>
		<c:forEach items="${roleList}" var="role" varStatus="status">
			<tr id="${role.roleId}" style="cursor:pointer" class="tabEnter" >
			
				<td height="28" align="center" width="10%" >${status.index + 1}</td>
				<td height="28" align="left" width="25%" >${role.roleName }</td>
				<td height="28" align="center" width="25%">
					<c:if test="${role.occupationType == 0}">
						医生
					</c:if>
					<c:if test="${role.occupationType == 1}">
						护士
					</c:if>
					<c:if test="${role.occupationType == 2}">
						系统管理员
					</c:if>
					<%-- <c:if test="${role.occupationType != 1 && role.occupationType != 0}">
						其他
					</c:if> --%>
				</td>
				<td height="28" align="center" width="15%">${role.memo }</td>
				<td height="28" align="center" width="25%">
					<c:choose>
						<c:when test="${role.occupationType != 2}">
							<a href="javascript:void(0);" onclick="showDialog('../role/editRoleInfo.html', '角色编辑', {searchRoleId :${role.roleId}}, 300, 294);">角色编辑</a>
						</c:when>
						<c:otherwise><s>角色编辑</s></c:otherwise>
					</c:choose>
						
					<a href="javascript:void(0);" onclick="loadPanel('../role/editRoleUsers.html',{searchRoleId: ${role.roleId}}, '#dynamicContent',{continueGoto:'continueGoto_documentation',isAddHistory:'1', isMainViewMenuPart:'1'});return false;">
					人员设定</a>	
					
					<c:choose>
						<c:when test="${role.occupationType != 2}">
							<a href="javascript:void(0);" onclick="showDialog('../role/initRoleAuth.html', '角色权限设定', {searchRoleId :${role.roleId}}, 700, 500);">权限设定</a>
						</c:when>
						<c:otherwise><s>权限设定</s></c:otherwise>
					</c:choose>
					
					<%-- <a href="javascript:void(0);" onclick="showDialog('../role/initRoleAuth.html', '角色权限设定', {searchRoleId :${role.roleId}}, 700, 500);">权限设定</a> --%>
					
					<c:choose>
						<c:when test="${role.occupationType != 2}">
							<a href="javascript:void(0);" onclick="deleteRole('${role.roleId}');return false;">删除</a>	
						</c:when>
						<c:otherwise><s>删除</s></c:otherwise>
					</c:choose>					
				</td>
			</tr> 
		</c:forEach>
		
		<tr class="page_line">
			<td colspan="11" style="height: 27px;">
				<form name="pagingform" method="get" action="../role/loadRoleList.html">
					<div class="pagelinks">
						<div style="float: right; height: 100%;">
							<div class="pagedesc">共${pagingContext.totalRowCnt}条记录！ 第${pagingContext.pageNo}页/共${pagingContext.totalPageCnt}页</div>
							<c:if test="${pagingContext.pageNo > 1}">
								<div class="firstPage">
									<a href="javascript:void(0);"
										<c:if test="${systemRoleDto.roleId != null}">onclick="jumpToPage(1,'#ajaxDialog');return false;"</c:if>
										onclick="jumpToPage(1);return false;"><img
										src="../images/1.gif"
										style="border: 0px; width: 21px; height: 16px;" /></a>
								</div>
								<div class="prevPage">
									<a href="javascript:void(0);"
										<c:if test="${systemRoleDto.roleId != null}">onclick="jumpToPage(${pagingContext.pageNo-1},'#ajaxDialog');return false;"</c:if>
										onclick="jumpToPage(${pagingContext.pageNo-1});return false;"><img
										src="../images/2.gif"
										style="border: 0px; width: 41px; height: 16px;" /></a>
								</div>
							</c:if>
							<c:forEach var="i" begin="${pagingContext.pageStartNo}" end="${pagingContext.perPageCnt}"
								step="1">
								<c:choose>
									<c:when test="${i == pagingContext.pageNo}">
										<div class="currentPage">
											<font color="#2D56A5">${i}</font>
										</div>
									</c:when>
									<c:otherwise>
										<div class="pageno">
											<a href="javascript:void(0);"
												<c:if test="${systemRoleDto.roleId != null}">onclick="jumpToPage(${i},'#ajaxDialog');return false;"</c:if>
												onclick="jumpToPage(${i}); return false;"><font
												color="#2D56A5">${i}</font></a>
										</div>
									</c:otherwise>
								</c:choose>
							</c:forEach>
							<c:if test="${pagingContext.pageNo < pagingContext.totalPageCnt}">
								<div class="nextPage">
									<a href="javascript:void(0);"
										<c:if test="${systemRoleDto.roleId != null}">onclick="jumpToPage(${pagingContext.pageNo + 1},'#ajaxDialog');return false;"</c:if>
										onclick="jumpToPage(${pagingContext.pageNo + 1});return false;"><img
										src="../images/4.gif"
										style="border: 0px; width: 41px; height: 16px;" /></a>
								</div>
								<div class="lastPage">
									<a href="javascript:void(0);"
										<c:if test="${systemRoleDto.roleId != null}">onclick="jumpToPage(${pagingContext.totalPageCnt},'#ajaxDialog');return false;"</c:if>
										onclick="jumpToPage(${pagingContext.totalPageCnt});return false;"><img
										src="../images/3.gif"
										style="border: 0px; width: 21px; height: 16px;" /></a>
								</div>
							</c:if>
							<div class="pageNum">
								<input type="text" name="screen" style="display:none;"/>
								<input name="pageNum"
									<c:if test="${systemRoleDto.roleId != null}">onkeyup="if(event.keyCode!=13){return false;} jumpToPageNo($('#tblid').find('[name=pageNum]').val(),${pagingContext.totalPageCnt},'#ajaxDialog');return false;"</c:if>
								    onkeyup="if(event.keyCode!=13){return false;} jumpToPageNo($('#tblid').find('[name=pageNum]').val(),${pagingContext.totalPageCnt});return false;"
								    style="width: 30px; float: left;" value="" />
							</div>
							<div class="goinput">
								<a href="javascript:void(0);"
									<c:if test="${systemRoleDto.roleId != null}">onclick="jumpToPageNo($('#tblid').find('[name=pageNum]').val(),${pagingContext.totalPageCnt},'#ajaxDialog');return false;"</c:if>
									onclick="jumpToPageNo($('#tblid').find('[name=pageNum]').val(),${pagingContext.totalPageCnt});return false;"><img
									src="../images/go.gif"
									style="border: 0px; width: 21px; height: 15px;" /></a>
							</div>
							<div id="ajaxDialogDetail" style="display: none;">
								<iframe id="dialogFrameDetail" style="width: 100%; height: 100%;" src=""
									frameborder="0"> </iframe>
							</div>
						</div>
					</div>
				</form>
			</td>
		</tr>
	</table>
	 <!--[if lt ie 8]></div><![endif]-->
	</div>
	
</body>
</html>