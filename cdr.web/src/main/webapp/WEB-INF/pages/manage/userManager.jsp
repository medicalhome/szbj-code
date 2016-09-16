<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="translation.tld" prefix="ref"%>
<%@ taglib uri="html.tld" prefix="html"%>
<jsp:useBean id="Constants" class="com.founder.cdr.core.Constants"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Pragma" content="no-cache" />
    <!-- Prevents caching at the Proxy Server -->
    <meta http-equiv="Expires" content="0" />
    <title>账户管理</title>
	<link rel="Stylesheet" type="text/css" href="../styles/tablelist.css" />	
  	<script type="text/javascript" src="../scripts/manage/userManager.js"></script>
	<script type="text/javascript">
		$(function(){
			//调用动态表格美化
			beautyTable();
		});
	</script>
	<script type="text/javascript">
		
		selectable("userViews_ctl", "userViews", false);

	</script>
</head>
<body style="margin: 0; padding: 0;">
	<form id="conditionForm" name="conditionForm" method="post" action="../iam/searchAccountTable.html" >
		<table class="blockTable" cellpadding="2" cellspacing="1" border="0">
			
			<tr class="conditionRow">
				<td width="100%" colspan="12" height="36" align="left" valign="middle">
					
					<div class="cell" style="width: 60px; text-align: right;">账户ID</div>
					<div class="cell" style="width: 80px;">
						<input type="text" name="searchUserId" style="width: 98%;" onmouseover="this.style.background='#FDE8FE';"
	                        onmouseout="this.style.background='#FFFFFF'" value="${userListDto.searchUserId}" />
					</div>
					
					
					<div class="cell" style="width: 60px; text-align: right;">账户名</div>
					<div class="cell" style="width: 80px;">
						<input type="text" name="searchUserName" style="width: 98%;" onmouseover="this.style.background='#FDE8FE';"
	                        onmouseout="this.style.background='#FFFFFF'" value="${userListDto.searchUserName}" />
					</div>
		
	                <div style="width:180px; display:none " class="cell" >
						<div class="cell" style="width: 60px; text-align: right">在岗状态</div>
						<div class="cell" style="width: 110px;">
							<select name="searchInPost" style="width: 100%;">
								<option value="${Constants.OPTION_SELECT}">请选择</option>
								<html:options domain="${Constants.EMPLOYMENT_STATUS}"
								 value="${userListDto.searchInPost}" />
							</select>
						</div>
					</div>
	       			<div style="width:180px; display:none " class="cell">
						<div class="cell" style="width: 60px; text-align: right">人员类型</div>
						<div class="cell" style="width: 110px;">
							<select name="searchPersonType" style="width: 100%;">
								<option value="${Constants.OPTION_SELECT}">请选择</option>
								<html:options domain="${Constants.EMPLOYEE_TYPE}"
								 value="${userListDto.searchPersonType}" />
							</select>
						</div>
					</div>
	                
	                <div style="width:160px;" class="cell">
						<div class="cell" style="width: 70px; text-align: center;">
							<input type="button"
								style="color: #464646; border: 0px; BACKGROUND-IMAGE: url(../images/5.jpg); width: 57px; height: 25px; margin-top: 3px;cursor: pointer;"
								align="absmiddle"
								onclick="search('../iam/searchAccountTable.html', 'conditionForm');" />
							
						</div>
					</div>
					 <div style="width:160px;" class="cell">
						<div class="cell" style="width: 70px; text-align: center;">
							<input type="button"
								style="color: #464646; border: 0px; BACKGROUND-IMAGE: url(../images/button_resetPW.jpg); width: 65px; height: 25px; margin-top: 3px;cursor: pointer;"
								align="absmiddle"
								onclick="multiResetPw();" />
						</div>
					</div>
					</td>
	               
				
			
			
			</tr>
			<!-- $Author :chang_xuewen
			$Date : 2013/07/04 11:00
			$[BUG]0033461 MODIFY BEGIN -->
			<tr class="tabletitle">
				<td height="28" align="center" width="4%" ><input type="checkbox" id="userViews_ctl" name="userViews_ctl"  value="${user.userId}" onfocus="this.blur()" /></td>
				<td height="28" align="center" width="8%" >列号</td>
				<td height="28" align="center" width="12%" >账户ID</td>
				<td height="28" align="center" width="12%">账户名</td>
				<td height="28" align="center" width="10%">性别</td>
				<td height="28" align="center" width="11%">科室部门</td>	
				<td height="28" align="center" width="11%">手机</td>	
				<!-- <td height="28" align="center" width="8%">人员类型</td>	
				<td height="28" align="center" width="8%">在岗状态</td>	 -->
				<td height="28" align="center" width="10%">入职日期</td>	
				<td height="28" align="center" width="8%">岗位类别</td>	
				<td height="28" align="center" width="14%">操作</td>					
			</tr>
			<!-- $[BUG]0033461 MODIFY END -->
		</table>
	</form>
	
	<div id='listcon'>
	<!--[if lt ie 8]><div style='+zoom:1;'><![endif]-->
	<table id="tblid" style="width: 100%;" cellspacing="1" cellpadding="2" class="table">
		
			<c:if test="${fn:length(userList)==0}">
			<tr>
				<td colspan="12" align="center" class="odd" height="24">没有相关数据！</td>
			</tr>
		</c:if>
		<c:forEach items="${userList}" var="user" varStatus="status">
			<tr id="${user.userNo}" style="cursor:pointer"  class="tabEnter selectable">
				<td height="24" align="center" width="4%" >
						<input type="checkbox" id="${user.userNo}" name="userViews"  value="${user.userNo}" onfocus="this.blur()" />
				</td>
			    <td height="24" align="center" width="8%">${status.index+1}</td>
				<td height="24" align="left" width="12%">${user.userNo}</td>
				<td height="24" align="left" width="12%">${user.userName}</td>
				<td height="24" align="center" width="10%">${user.sexName}</td>
				<td height="24" align="left" width="11%">${user.deptName}</td>	
				<td height="24" align="left" width="11%">${user.mobile}</td>	
				<%-- <td height="24" align="left" width="8%">${user.personType}</td>	
				<td height="24" align="left" width="8%">${user.inPostStatus}</td> --%>	
				<td height="24" align="left" width="10%">${user.entryDate}</td>	
				<td height="24" align="center" width="8%">${user.posiClass}</td>	
				<td height="24" width="14%" align="center">
					<a href="javascript:void(0);" onclick="singleResetPW('${user.userNo}');return false;">重置密码</a>	
					<a href="javascript:void(0);" onclick="showDialog('../iam/setPersonAuth.html', '个人权限设定', {'userNo':'${user.userNo}'}, 700, 600);">权限设定</a>		
				</td>	
			   
			</tr> 
		</c:forEach>
		
		<tr class="page_line">
			<td colspan="12" style="height: 27px;">
				<form name="pagingform" method="get" action="../iam/searchAccountTable.html">
					<div class="pagelinks">
						<div style="float: right; height: 100%;">
							<div class="pagedesc">共${pagingContext.totalRowCnt}条记录！ 第${pagingContext.pageNo}页/共${pagingContext.totalPageCnt}页</div>
							<c:if test="${pagingContext.pageNo > 1}">
								<div class="firstPage">
									<a href="javascript:void(0);"
										<c:if test="${userListDto.userNo != null}">onclick="jumpToPage(1,'#ajaxDialog');return false;"</c:if>
										onclick="jumpToPage(1);return false;"><img
										src="../images/1.gif"
										style="border: 0px; width: 21px; height: 16px;" /></a>
								</div>
								<div class="prevPage">
									<a href="javascript:void(0);"
										<c:if test="${userListDto.userNo != null}">onclick="jumpToPage(${pagingContext.pageNo-1},'#ajaxDialog');return false;"</c:if>
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
												<c:if test="${userListDto.userNo != null}">onclick="jumpToPage(${i},'#ajaxDialog');return false;"</c:if>
												onclick="jumpToPage(${i}); return false;"><font
												color="#2D56A5">${i}</font></a>
										</div>
									</c:otherwise>
								</c:choose>
							</c:forEach>
							<c:if test="${pagingContext.pageNo < pagingContext.totalPageCnt}">
								<div class="nextPage">
									<a href="javascript:void(0);"
										<c:if test="${userListDto.userNo != null}">onclick="jumpToPage(${pagingContext.pageNo + 1},'#ajaxDialog');return false;"</c:if>
										onclick="jumpToPage(${pagingContext.pageNo + 1});return false;"><img
										src="../images/4.gif"
										style="border: 0px; width: 41px; height: 16px;" /></a>
								</div>
								<div class="lastPage">
									<a href="javascript:void(0);"
										<c:if test="${userListDto.userNo != null}">onclick="jumpToPage(${pagingContext.totalPageCnt},'#ajaxDialog');return false;"</c:if>
										onclick="jumpToPage(${pagingContext.totalPageCnt});return false;"><img
										src="../images/3.gif"
										style="border: 0px; width: 21px; height: 16px;" /></a>
								</div>
							</c:if>
							<div class="pageNum">
								<input type="text" name="screen" style="display:none;"/>
								<input name="pageNum"
									<c:if test="${userListDto.userNo != null}">onkeyup="if(event.keyCode!=13){return false;} jumpToPageNo($('#tblid').find('[name=pageNum]').val(),${pagingContext.totalPageCnt},'#ajaxDialog');return false;"</c:if>
								    onkeyup="if(event.keyCode!=13){return false;} jumpToPageNo($('#tblid').find('[name=pageNum]').val(),${pagingContext.totalPageCnt});return false;"
								    style="width: 30px; float: left;" value="" />
							</div>
							<div class="goinput">
								<a href="javascript:void(0);"
									<c:if test="${userListDto.userNo != null}">onclick="jumpToPageNo($('#tblid').find('[name=pageNum]').val(),${pagingContext.totalPageCnt},'#ajaxDialog');return false;"</c:if>
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