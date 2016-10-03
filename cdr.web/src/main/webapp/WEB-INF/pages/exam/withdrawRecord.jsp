<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="translation.tld" prefix="ref"%>
<jsp:useBean id="Constants" class="com.yly.cdr.core.Constants" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>召回信息</title>
	<meta http-equiv="Pragma" content="no-cache" />
	<meta http-equiv="Cache-Control" content="no-cache" />
	<meta http-equiv="Expires" content="0" />
	<link type="text/css" rel="Stylesheet"
		href="../styles/jquery-ui-1.8.18.custom.modify.css" charset="UTF8" />
	<link type="text/css" rel="Stylesheet"
		href="../styles/layout-default-1.3.0rc29.15.css" charset="UTF8" />
	<link type="text/css" rel="Stylesheet"
		href="../styles/layout-cdr-dialog.css" charset="UTF8" />
	<link type="text/css" rel="Stylesheet" href="../styles/tablelist.css"
		charset="UTF8" />
	<script>
		$(function()
		{
			$( "#tabs" ).tabs();
		});
	</script>
</head>
<body>
	<div id="dialog">
		<div id="tabs-1" class="tabcontainer">
			<table cellpadding="2" cellspacing="1" class="table" width="100%"
				align="center">
				<tr height="28" class="tabletitle">
					<td width="20%" align="center">召回人姓名</td>
					<td width="20%" align="center">召回时间</td>
					<td width="60%" align="center">召回原因</td>
				</tr>
				<c:if test="${fn:length(withdrawRecordDto)==0}">
				<tr>
					<td colspan="3" align="center" class="odd" height="24">没有相关数据！</td>
				</tr>
				</c:if>
				<c:forEach items="${withdrawRecordDto}" var="withdrawRecordDto"
					varStatus="status">
					<tr class=${status.count%2==0?'even':'odd'} height="24">
						<td>${withdrawRecordDto.withdrawPersonName}</td>
						<td>${fn:substring(withdrawRecordDto.withdrawTime,0,16)}</td>
						<td>${withdrawRecordDto.withdrawReason}</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
</body>
</html>
