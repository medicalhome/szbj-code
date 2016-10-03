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
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<title>手术申请单-详细</title>
<link type="text/css" rel="Stylesheet"
	href="../styles/jquery-ui-1.8.18.custom.modify.css" charset="UTF8" />

</head>
<body>
	<table width="100%" cellpadding="2" cellspacing="1"
		style="border: solid 1px #c0ddea; border-collapse: collapse; border-bottom: 0px;">

		<tr class="odd">
			<td class="label">申请单编号:</td>
			<td class="dataitem">${pp.requestNo}</td>
			<td class="label">手术名称:</td>
			<td class="dataitem">${pp.operationName}</td>
		</tr>

		<tr>
			<td class="label">手术等级:</td>
			<td class="dataitem">${pp.procedureLevelName}</td>
			<td class="label">建议执行时间:</td>
			<td class="dataitem"><fmt:formatDate
					value="${pp.proposalExecTime}" type="both"
					pattern="yyyy-MM-dd HH:mm" /></td>
		</tr>

		<tr class="odd">
			<td class="label">注意事项:</td>
			<td class="dataitem">${pp.precautions}</td>
			<td class="label">描述:</td>
			<td class="dataitem">${pp.description}</td>
		</tr>

		<tr>
			<td class="label">执行科室:</td>
			<td class="dataitem">${pp.execDeptName}</td>
			<td class="label">申请机构(科室):</td>
			<td class="dataitem">${pp.orderDeptName}</td>
		</tr>

		<tr class="odd">
			<td class="label">申请医生:</td>
			<td class="dataitem">${pp.orderPersonName}</td>
			<td class="label">申请日期:</td>
			<td class="dataitem"><fmt:formatDate value="${pp.requestDate}"
					type="date" pattern="yyyy-MM-dd HH:mm" /></td>
		</tr>

		<tr>
			<td class="label">申请原因:</td>
			<td class="dataitem">${pp.requestReason}</td>
			<td class="label"></td>
			<td class="dataitem"></td>
		</tr>
	</table>
</body>
</html>


































