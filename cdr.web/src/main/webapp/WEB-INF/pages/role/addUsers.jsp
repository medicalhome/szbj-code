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
	<title>添加人员</title>
    <link rel="Stylesheet" type="text/css" href="../styles/tablelist.css" />
    <script type="text/javascript" src="../scripts/role/roleAccess.js"></script>
	<script type="text/javascript">
		$(function(){
			//调用动态表格美化
			beautyTable("tblidNew");
		});
	</script>
	<script type="text/javascript">
		selectable("addUserTop", "addUserIds", true);
	</script>
</head>
<body style="margin: 0; padding: 0;">
	<form id="conditionFormNew" name="conditionFormNew" method="post" action="../role/addUsersPage.html">
		<table class="blockTable" cellpadding="2" cellspacing="1" border="0">
			<tr class="blockHeader">
				<td width="100%" colspan="0" height="36" align="left" valign="middle">
					<div class="cell" style="width: 60px; text-align: right;">添加人员</div>
				</td>
			</tr>
		
			<tr>
				<td width="100%" colspan="11" height="36" align="left" valign="middle">
					<div class="cell" style="width: 0px; text-align: left;">
						<input type="hidden" name="addSearchRoleId" style="width: 98%;" onmouseover="this.style.background='#FDE8FE';"
	                        onmouseout="this.style.background='#FFFFFF'" value="${role.systemRoleId }"  />
	                </div>
				
					<div class="cell" style="width: 60px; text-align: right;">用户ID</div>
					<div class="cell" style="width: 80px;">							
							<input type="text" name="addSearchUserNo" style="width: 98%;" onmouseover="this.style.background='#FDE8FE';"
	                        onmouseout="this.style.background='#FFFFFF'" value="${addUsersDto.addSearchUserNo}" />
					</div>
				
					<div class="cell" style="width: 60px; text-align: right;">用户名</div>
					<div class="cell" style="width: 80px;">							
							<input type="text" name="addSearchUserName" style="width: 98%;" onmouseover="this.style.background='#FDE8FE';"
	                        onmouseout="this.style.background='#FFFFFF'" value="${addUsersDto.addSearchUserName}" />
					</div>
					<div style="width:180px;" class="cell">
						<div class="cell" style="width: 60px; text-align: right">科室部门</div>
						<div class="cell" style="width: 110px;">
							<select name="addSearchDeptCode" style="width: 100%;">
								<option value="${Constants.OPTION_SELECT}">请选择</option>
								<html:options domain="${Constants.DOMAIN_DEPARTMENT}"
								 value="${addUsersDto.addSearchDeptCode}" />
							</select>
						</div>
					</div>
					<div style="width:100px; " class="cell" > 
						<input type="button"
							style="color: #464646; border: 0px; width: 100px; height: 25px; margin-top: 3px;cursor: pointer;"
							align="middle" value="搜索" onclick="search('../role/addUsersPage.html', 'conditionFormNew', '#ajaxDialog');" />
					</div>
					<div style="width:100px; " class="cell" > 
						<input type="button"
							style="color: #464646; border: 0px; width: 100px; height: 25px; margin-top: 3px;cursor: pointer;"
							align="middle" value="添加" onclick="addUsers(${role.systemRoleId });" />
					</div>
					<div style="width:100px; " class="cell" > 
						<input type="button"
							style="color: #464646; border: 0px; width: 100px; height: 25px; margin-top: 3px;cursor: pointer;"
							align="middle" value="关闭" onClick="closeDialog();"/>
					</div>
				</td>
			</tr>
		</table>
		<table class="blockTable" cellpadding="2" cellspacing="1" border="0">
			<tr class="tabletitle">
				<td height="28" align="center" width="4%" >
					<input type="checkbox" id="addUserTop" name="addUserTop"  value="${role.systemRoleId}" onfocus="this.blur()" />
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
		<table id="tblidNew" style="width: 100%;" cellspacing="1" cellpadding="2" class="table">
			<c:if test="${fn:length(userList)==0}">
				<tr>
					<td colspan="11" align="center" class="odd" height="24">没有相关数据！</td>
				</tr>
			</c:if>
			<c:if test="${fn:length(userList)!=0}">
				<c:forEach items="${userList}" var="user" varStatus="status">
					<tr style="cursor:pointer" class="tabEnter selectable">
						<td height="24" align="center" width="4%" >
								<input type="checkbox" id="${user.userId}" name="addUserIds"  value="${user.userId}" onfocus="this.blur()" />
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
					<form name="pagingformNew" method="get" action="../role/addUsersPage.html">
						<div class="pagelinks">
							<div style="float: right; height: 100%;">
								<div class="pagedesc">共${pagingContext.totalRowCnt}条记录！ 第${pagingContext.pageNo}页/共${pagingContext.totalPageCnt}页</div>
								<c:if test="${pagingContext.pageNo > 1}">
									<div class="firstPage">
										<a href="javascript:void(0);"
											<c:if test="${role.systemRoleId != null}">onclick="jumpToPageNew(1,'#ajaxDialog');return false;"</c:if>
											onclick="jumpToPageNew(1);return false;"><img
											src="../images/1.gif"
											style= "border: 0px; width: 21px; height: 16px;" /></a>
									</div>
									<div class="prevPage">
										<a href="javascript:void(0);"
											<c:if test="${role.systemRoleId != null}">onclick="jumpToPageNew(${pagingContext.pageNo-1},'#ajaxDialog');return false;"</c:if>
											onclick="jumpToPageNew(${pagingContext.pageNo-1});return false;"><img
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
													<c:if test="${role.systemRoleId != null}">onclick="jumpToPageNew(${i},'#ajaxDialog');return false;"</c:if>
													onclick="jumpToPageNew(${i}); return false;"><font
													color="#2D56A5">${i}</font></a>
											</div>
										</c:otherwise>
									</c:choose>
								</c:forEach>
								<c:if test="${pagingContext.pageNo < pagingContext.totalPageCnt}">
									<div class="nextPage">
										<a href="javascript:void(0);"
											<c:if test="${role.systemRoleId != null}">onclick="jumpToPageNew(${pagingContext.pageNo + 1},'#ajaxDialog');return false;"</c:if>
											onclick="jumpToPageNew(${pagingContext.pageNo + 1});return false;"><img
											src="../images/4.gif"
											style="border: 0px; width: 41px; height: 16px;" /></a>
									</div>
									<div class="lastPage">
										<a href="javascript:void(0);"
											<c:if test="${role.systemRoleId != null}">onclick="jumpToPageNew(${pagingContext.totalPageCnt},'#ajaxDialog');return false;"</c:if>
											onclick="jumpToPageNew(${pagingContext.totalPageCnt});return false;"><img
											src="../images/3.gif"
											style="border: 0px; width: 21px; height: 16px;" /></a>
									</div>
								</c:if>
								<div class="pageNum">
									<input type="text" name="screen" style="display:none;"/>
									<input name="pageNum"
										<c:if test="${role.systemRoleId != null}">onkeyup="if(event.keyCode!=13){return false;} jumpToPageNoNew($('#tblidNew').find('[name=pageNum]').val(),${pagingContext.totalPageCnt},'#ajaxDialog');return false;"</c:if>
									    onkeyup="if(event.keyCode!=13){return false;} jumpToPageNoNew($('#tblidNew').find('[name=pageNum]').val(),${pagingContext.totalPageCnt});return false;"
									    style="width: 30px; float: left;" value="" />
								</div>
								<div class="goinput">
									<a href="javascript:void(0);"
										<c:if test="${role.systemRoleId != null}">onclick="jumpToPageNoNew($('#tblidNew').find('[name=pageNum]').val(),${pagingContext.totalPageCnt},'#ajaxDialog');return false;"</c:if>
										onclick="jumpToPageNoNew($('#tblidNew').find('[name=pageNum]').val(),${pagingContext.totalPageCnt});return false;"><img
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