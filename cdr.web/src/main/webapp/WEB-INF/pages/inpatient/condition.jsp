<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="translation.tld" prefix="ref"%>
<%@ taglib uri="html.tld" prefix="html"%>
<jsp:useBean id="Constants" class="com.yly.cdr.core.Constants" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<title> 住院视图</title>
</head>
<body style="margin: 0; padding: 0;">

	<select id="dischargeDateAndVisitSn" name="dischargeDateAndVisitSn" class="zytab_1" style="width: 100%;"
		<c:choose>
			<c:when test="${orgCode == null || fn:length(orgCode) == 0}">
				style="width: 250px;"
			</c:when>
			<c:otherwise>
				style="width: 200px;"
			</c:otherwise>
		</c:choose>>
		<c:forEach items="${visitTimesEtcList}" var="vts">
			<option value="<fmt:formatDate value='${vts.dischargeDate}' type='date' pattern='yyyy-MM-dd' />;${vts.visitSn};<fmt:formatDate value='${vts.visitDate}' type='date' pattern='yyyy-MM-dd' />" 
			title="<ref:translate domain='${Constants.DOMAIN_ORG_CODE}' code='${vts.orgCode}'/>"
			<c:if test="${vts.visitSn == vstSn}">selected</c:if>>
			<fmt:formatDate value="${vts.visitDate}" type="date" pattern="yyyy-MM-dd" /> - <c:if test="${vts.dischargeDate == null}">至今</c:if><fmt:formatDate value="${vts.dischargeDate}" type="date" pattern="yyyy-MM-dd" />
			&nbsp;<c:if test="${orgCode == null || fn:length(orgCode) == 0}"><ref:translate domain="${Constants.DOMAIN_ORG_CODE}" code="${vts.orgCode}"/></c:if>
			</option>
		</c:forEach>
	</select> 

</body>
</html>
