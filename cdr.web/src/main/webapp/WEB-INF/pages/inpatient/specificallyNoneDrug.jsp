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
	<title>具体非药物医嘱</title>
	<link rel="Stylesheet" type="text/css" href="../styles/tablelist.css" />
</head>
<body style="margin: 0; padding: 0;">
	<c:forEach items="${specificallyNoneDrugList}" var="specificallyNoneDrugList" varStatus="status">
		<div <c:choose>
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
				<table style="width:100%;color: blue;cursor: pointer;" cellpadding="0" cellspacing="0" onmouseover="this.style.color='red';" onmouseout="this.style.color='blue';" 
					onclick="loadCommonPanel('${specificallyNoneDrugList.orderTitle}',{'url':'${specificallyNoneDrugList.orderPath}','gotoType':'nondrug','special':'special',
							'tabsFlag':'true','width':'70%','name':'${specificallyNoneDrugList.orderName}','patientSn':'${patientSn}','orderSn':'${specificallyNoneDrugList.orderSn}','trID':'${specificallyNoneDrugList.orderSn}',
							'type':'${TimerAndInpatientDto.NO_DRUG_ORDER}','visitSn':'${visitSn}','inpatientDate':'${inpatientDate}'});">
					<tr>
						<%-- <td style="width: 15%;border-top:0;border-bottom:0;border-left:0;border-right:0;">
							<c:choose> 
								<c:when test="${fn:length(specificallyNoneDrugList.wardName)>6}">${fn:substring(specificallyNoneDrugList.wardName,0,6)}...</c:when>
								<c:otherwise>${specificallyNoneDrugList.wardName}&nbsp;</c:otherwise>
							</c:choose>
						</td> --%>
						<td style="width: 25%;border-top:0;border-bottom:0;border-left:0;border-right:0;">
				 			${specificallyNoneDrugList.orderTypeName}&nbsp;
						</td>
						<td style="width: 55%;border-top:0;border-bottom:0;border-left:0;border-right:0;"
							<c:if test="${fn:length(specificallyNoneDrugList.orderName)>17}">title="${specificallyNoneDrugList.orderName}"</c:if>>
							<c:choose>
								<c:when test="${fn:length(specificallyNoneDrugList.orderName)>17}">
									${fn:substring(specificallyNoneDrugList.orderName,0,17)}...
								</c:when>
								<c:otherwise>
									${specificallyNoneDrugList.orderName}&nbsp;
								</c:otherwise>
							</c:choose>
						</td>
						<td style="width: 20%;border-top:0;border-bottom:0;border-left:0;border-right:0;">
							${specificallyNoneDrugList.orderStatusName}&nbsp;
						</td>
					</tr>
				</table>
			</div>
	</c:forEach>
	
	<div class="gapBottom">&nbsp;</div>
	
</body>
</html>
