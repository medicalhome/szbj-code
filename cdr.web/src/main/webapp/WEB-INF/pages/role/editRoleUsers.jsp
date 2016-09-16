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
	<title>人员列表编辑</title>
    <link rel="Stylesheet" type="text/css" href="../styles/tablelist.css" />
    <script type="text/javascript" src="../scripts/role/roleAccess.js"></script>
	<script type="text/javascript">
		$(function(){
			//调用动态表格美化
			beautyTable();
		});
	</script>
	<script type="text/javascript">
		selectable("userTop", "userIds", true);
	</script>

</head>

<body style="margin: 0; padding: 0;">
	<form id="conditionForm" name="conditionForm" method="post" action="../role/editRoleUsers.html">
		<table class="blockTable" cellpadding="2" cellspacing="1" border="0">
			
			<tr class="blockHeader">
				<td width="100%" colspan="0" height="36" align="left" valign="middle">
					<div class="cell" style="width: 60px; text-align: right;">人员列表</div>
				</td>
			</tr>
			<tr>
				<td width="100%" colspan="11" height="36" align="left" valign="middle">
					<div class="cell" style="width: 0px; text-align: left;">
						<input type="hidden" name="searchRoleId" style="width: 98%;" onmouseover="this.style.background='#FDE8FE';"
	                        onmouseout="this.style.background='#FFFFFF'" value="${role.systemRoleId }"  />
	                </div>
					<div class="cell" style="width: 60px; text-align: right;">用户ID</div>
					<div class="cell" style="width: 80px;">							
							<input type="text" name="searchUserNo" style="width: 98%;" onmouseover="this.style.background='#FDE8FE';"
	                        onmouseout="this.style.background='#FFFFFF'" value="${systemRoleDto.searchUserNo}" />
					</div>
				
					<div class="cell" style="width: 60px; text-align: right;">用户名</div>
					<div class="cell" style="width: 80px;">							
							<input type="text" name="searchUserName" style="width: 98%;" onmouseover="this.style.background='#FDE8FE';"
	                        onmouseout="this.style.background='#FFFFFF'" value="${systemRoleDto.searchUserName}" />
					</div>
					<div style="width:180px;" class="cell">
						<div class="cell" style="width: 60px; text-align: right">科室部门</div>
						<div class="cell" style="width: 110px;">
							<select name="searchDeptCode" style="width: 100%;">
								<option value="${Constants.OPTION_SELECT}">请选择</option>
								<html:options domain="${Constants.DOMAIN_DEPARTMENT}"
								 value="${systemRoleDto.searchDeptCode}" />
							</select>
						</div>
					</div>
					<div style="width:100px; " class="cell" > 
						<input type="button"
							style="color: #464646; border: 0px; width: 100px; height: 25px; margin-top: 3px;cursor: pointer;"
							align="middle" value="搜索" onclick="search('../role/editRoleUsers.html', 'conditionForm');" />
					</div>

<%-- 					<div style="width:100px; " class="cell" > 
						<input type="button"
							style="color: #464646; border: 0px; width: 100px; height: 25px; margin-top: 3px;cursor: pointer;"
							align="middle" value="添加人员" onclick="loadPanel('../role/addUsersPage.html',{'searchRoleId' : ${role.systemRoleId}}, '#dynamicContent',{continueGoto:'continueGoto_documentation',isAddHistory:'1', isMainViewMenuPart:'1'});return false;" />
					</div> --%>

					<div style="width:100px; " class="cell" > 
						<input type="button"
							style="color: #464646; border: 0px; width: 100px; height: 25px; margin-top: 3px;cursor: pointer;"
							align="middle" value="添加人员" onclick="showDialog('../role/addUsersPage.html', '添加人员', {'addSearchRoleId' : ${role.systemRoleId}}, 900, 450)" />
					</div>
					
					<div style="width:100px; " class="cell" > 
						<input type="button"
							style="color: #464646; border: 0px; width: 100px; height: 25px; margin-top: 3px;cursor: pointer;"
							align="middle" value="删除人员" onclick="deleteUsers(${role.systemRoleId });" />
					</div>	
					<div style="width:100px; " class="cell" > 
						<input type="button"
							style="color: #464646; border: 0px; width: 100px; height: 25px; margin-top: 3px;cursor: pointer;"
							align="middle" value="返回" onclick="loadPanel('../role/loadRoleList.html',{}, '#dynamicContent',{continueGoto:'continueGoto_documentation',isAddHistory:'1', isMainViewMenuPart:'1'});return false;" />
					</div>	
				</td>
			</tr>
		</table>
		<table class="blockTable" cellpadding="2" cellspacing="1" border="0">
			<tr class="tabletitle">
				<td height="28" align="center" width="4%" >
					<input type="checkbox" id="userTop" name="userTop"  value="${role.systemRoleId}" onfocus="this.blur()"  />
				</td>
				<td height="28" align="center" width="10%" >列号</td>
				<td height="28" align="center" width="19%" >用户id</td>
				<td height="28" align="center" width="19%">用户名</td>
				<td height="28" align="center" width="10%">性别</td>
				<td height="28" align="center" width="19%">科室部门</td>
				<td height="28" align="center" width="19%">手机</td>
						
			</tr>
		</table>
		
		
		</form>
		<table id="tblid" style="width: 100%;" cellspacing="1" cellpadding="2" class="table">
			<c:if test="${fn:length(userList)==0}">
				<tr>
					<td colspan="11" align="center" class="odd" height="24">没有相关数据！</td>
				</tr>
			</c:if>
			<c:if test="${fn:length(userList)!=0}">
				<c:forEach items="${userList}" var="user" varStatus="status">
					<tr style="cursor:pointer" class="tabEnter selectable">
						<td height="24" align="center" width="4%" >
								<input type="checkbox" id="${user.userId}" name="userIds"  value="${user.userId}" onfocus="this.blur()" />
						</td>
						<td height="28" align="center" width="10%" >${status.index + 1}</td>
						<td height="28" align="left" width="19%" >${user.userId}</td>
						<td height="28" align="left" width="19%" >${user.userName }</td>
						<td height="28" align="left" width="10%" >${user.sexName }</td>
						<td height="28" align="center" width="19%">${user.deptName }</td>
						<td height="28" align="center" width="19%">${user.mobile }</td>
					</tr> 
				</c:forEach>
			</c:if>
			
			
			<tr class="page_line">
				<td colspan="11" style="height: 27px;">
					<form name="pagingform" method="get" action="../role/editRoleUsers.html">
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

</body>


</html>