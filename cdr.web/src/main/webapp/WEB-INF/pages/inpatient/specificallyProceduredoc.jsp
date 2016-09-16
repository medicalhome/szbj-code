<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="translation.tld" prefix="ref"%>
<%@ taglib uri="html.tld" prefix="html"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="Constants" class="com.founder.cdr.core.Constants"/>
<jsp:useBean id="TimerAndInpatientDto" class="com.founder.cdr.dto.TimerAndInpatientDto"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Pragma" content="no-cache" />
	<!-- Prevents caching at the Proxy Server -->
	<meta http-equiv="Expires" content="0" />
	<title>具体手术-病例文书</title>
	<link rel="Stylesheet" type="text/css" href="../styles/tablelist.css" />
</head>
<body style="margin: 0; padding: 0;">
	<c:forEach items="${specificallyProceduredocList}" var="specificallyProceduredocList"
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
			<table style="width: 100%; color: blue; cursor: pointer;"
				cellpadding="0" cellspacing="0"
				onmouseover="this.style.color='red';"
				onmouseout="this.style.color='blue';"
				onclick="loadCommonPanel('手术详细与手术病历文书列表',{'url':'../doc/detail_${specificallyProceduredocList.documentSn}.html',
						'tabsFlag':'true','gotoType':'prodoc','special':'special','width':'70%','patientSn':'${patientSn}','documentLid':'${specificallyProceduredocList.documentLid}','trID':'${specificallyProceduredocList.documentLid}',
						'type':'${TimerAndInpatientDto.DOCUMENTATION}','visitSn':'${visitSn}','name':'${specificallyProceduredocList.documentName}',
						'inpatientDate':'${inpatientDate}'});">
				<tr>
					<td style="float: left; width: 30%; border-top: 0; border-bottom: 0; border-left: 0; border-right: 0;">
					 	${specificallyProceduredocList.documentTypeName}&nbsp;
					</td>
					<td style="float: left; width: 60%; border-top: 0; border-bottom: 0; border-left: 0; border-right: 0;">
						${specificallyProceduredocList.documentName}&nbsp;
					</td>
					<td style="float: left; width: 9%; border-top: 0; border-bottom: 0; border-left: 0; border-right: 0;">
						${specificallyProceduredocList.documentAuthorName}&nbsp;
					</td>
				</tr>
			</table>
		</div>
	</c:forEach>
	<div class="gapBottom">&nbsp;</div>
</body>
</html>
