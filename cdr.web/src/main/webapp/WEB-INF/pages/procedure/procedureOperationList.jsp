<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="translation.tld" prefix="ref"%>
<jsp:useBean id="Constants" class="com.founder.cdr.core.Constants" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<title>手术操作记录</title>
<link type="text/css" rel="Stylesheet"
	href="../styles/jquery-ui-1.8.18.custom.modify.css" charset="UTF8" />
</head>

<body>
	<table id="tblid" style="width: 100%;" cellspacing="1" cellspacing="2"
		class="table">
		<tr class="tabletitle">
			<td height="28" align="center">操作类型</td>
			<td height="28" align="center">操作人员</td>
			<td height="28" align="center">操作时间</td>
			<td height="28" align="center">操作内容</td>
		</tr>
		<c:if test="${fn:length(pr)==0}">
			<tr>
				<td colspan="4" align="center" class="odd" height="24">没有相关数据！</td>
			</tr>
		</c:if>
		<c:forEach items="${pr}" var="pr" varStatus="status">
			<tr <c:if test="${status.count%2==1}">class="odd"</c:if>
				<c:if test="${status.count%2==0}">class="even"</c:if>>
				<td height="24" align="center">${pr.operationTypeName}</td>
				<td height="24" align="center">${pr.operatorName}</td>
				<td height="24" align="center"><fmt:formatDate
						value="${pr.operationTime}" type="date" pattern="yyyy-MM-dd" /></td>
				<td height="24" align="center">${pr.operationDescription}</td>
			</tr>
		</c:forEach>

	</table>
</body>
</html>




































