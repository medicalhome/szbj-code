<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="translation.tld" prefix="ref"%>
<%@ taglib uri="html.tld" prefix="html"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="Constants" class="com.yly.cdr.core.Constants"/>
<jsp:useBean id="TimerAndInpatientDto" class="com.yly.cdr.dto.TimerAndInpatientDto"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Pragma" content="no-cache" />
	<!-- Prevents caching at the Proxy Server -->
	<meta http-equiv="Expires" content="0" />
	<title>具体手术</title>
	<link rel="Stylesheet" type="text/css" href="../styles/tablelist.css" />
</head>
<body style="margin: 0; padding: 0;">
		<c:forEach items="${specificallyProcedureList}" var="specificallyProcedureList"
		varStatus="status">
		<div
			<c:choose>
				<c:when test="${status.count > 2}">
		 			style="width:${firstDivWidth};margin-top:3px;"
		 		</c:when>
		 		<c:otherwise>
		 			 style="width:${firstDivWidth};"
		 		</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${status.count % 2 != 0}">
		 			class="leftClass"
		 		</c:when>
		 		<c:otherwise>
		 			 class="rightClass"
		 		</c:otherwise>
			</c:choose>>
			<table style="width:100%;color: blue; cursor: pointer;" cellpadding="0"
				cellspacing="0" onmouseover="this.style.color='red';"
				onmouseout="this.style.color='blue';"
				onclick="loadCommonPanel('手术详细与手术病历文书列表', {'url':'../order/procedure_${specificallyProcedureList.orderSn}.html',
						'tabsFlag':'true','gotoType':'procedure_doc','special':'special','width':'70%','patientSn':'${patientSn}','procedureSn':'${specificallyProcedureList.orderSn}','trID':'${specificallyProcedureList.orderSn}',
						'type':'${TimerAndInpatientDto.PROCEDURE}','visitSn':'${visitSn}','name':'${specificallyProcedureList.orderName}','inpatientDate':'${inpatientDate}'});">
				<tr>
					<td style="width: 60%;border-top:0;border-bottom:0;border-left:0;border-right:0;">
			 			 ${specificallyProcedureList.orderName}&nbsp;
					</td>
					<td style="width: 40%;border-top:0;border-bottom:0;border-left:0;border-right:0;">${specificallyProcedureList.inpatientDate}</td>
					<%-- <td style="float: left; width: 70px; border-top: 0; border-bottom: 0; border-left: 0; border-right: 0;"
						class="gapContent"><fmt:formatDate
											value="${specificallyProcedureList.operationDate}" type="date"
											pattern="yyyy-MM-dd" /></td> --%>
				</tr>
			</table>
		</div>
	</c:forEach>

	<div class="gapBottom">&nbsp;</div>

	
</body>
</html>
