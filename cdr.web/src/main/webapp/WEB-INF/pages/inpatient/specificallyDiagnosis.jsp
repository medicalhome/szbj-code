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
	<title>具体诊断</title>
	<link rel="Stylesheet" type="text/css" href="../styles/tablelist.css" />
</head>
<body style="margin: 0; padding: 0;">
	<c:forEach items="${specificallyDiagnosisList}" var="specificallyDiagnosis" varStatus="status">
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
					onclick="loadCommonPanel('诊断详细',{'url':'../diagnosis/detail_${specificallyDiagnosis.diagnosisSn}.html','tabsFlag':'true','gotoType':'diagnosis','special':'special','width':'70%',
							'patientSn':'${patientSn}','diagnosisSn':'${specificallyDiagnosis.diagnosisSn}','type':'${TimerAndInpatientDto.DIAGNOSIS}',
							'name':'${specificallyDiagnosis.diseaseName}','trID':'${specificallyDiagnosis.diagnosisSn}','visitSn':'${visitSn}','inpatientDate':'${inpatientDate}'});">
				<tr style="width:100%;">
					<td style="float:left;width:40%;border-top:0;border-bottom:0;border-left:0;border-right:0;" <c:if test="${fn:length(specificallyDiagnosis.diseaseName)>10}">title="${specificallyDiagnosis.diseaseName}"</c:if>>
						<c:choose>
							<c:when test="${status.count > 2}">
					 			<c:if test="${fn:length(specificallyDiagnosis.diseaseName)>10}">${fn:substring(specificallyDiagnosis.diseaseName,0,10)}</c:if>
					 			<c:if test="${fn:length(specificallyDiagnosis.diseaseName)<=10}">${specificallyDiagnosis.diseaseName}&nbsp;</c:if>
					 		</c:when>
					 		<c:otherwise>
					 			 ${specificallyDiagnosis.diseaseName}&nbsp;
					 		</c:otherwise>
						</c:choose>
					</td>
					<td style="float:left;width:5%;border-top:0;border-bottom:0;border-left:0;border-right:0;">
						<c:choose>
							<c:when test="${specificallyDiagnosis.uncertainFlag == Constants.CERTAIN_DB}">?</c:when>
							<c:otherwise>&nbsp;</c:otherwise>
						</c:choose>
					</td>
					<td style="float:left;width:40%;border-top:0;border-bottom:0;border-left:0;border-right:0;">${specificallyDiagnosis.diagnosisTypeName}&nbsp;</td>
					<td style="float:left;width:15%;border-top:0;border-bottom:0;border-left:0;border-right:0;">${specificallyDiagnosis.diagnosisDoctorName}&nbsp;</td>
				</tr>
			</table>
		</div>
	</c:forEach>
	
	<div class="gapBottom">&nbsp;</div>
</body>
</html>
