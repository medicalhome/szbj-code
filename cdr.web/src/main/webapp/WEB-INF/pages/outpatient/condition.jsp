<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="translation.tld" prefix="ref"%>
<%@ taglib uri="html.tld" prefix="html"%>
<jsp:useBean id="Constants" class="com.founder.cdr.core.Constants" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<title>门急诊视图</title>
</head>
<body style="margin: 0; padding: 0;">

	<select name="date" id="date" 
		<c:choose>
			<c:when test="${orgCode == null || fn:length(orgCode) == 0}">
				style="width: 200px;"
			</c:when>
			<c:otherwise>
				style="width: 120px;"
			</c:otherwise>
		</c:choose>>
		<c:forEach items="${visitDates}" var="visitDate" varStatus="status">
			<option value="${visitDate.visitSn}" title="<ref:translate domain='${Constants.DOMAIN_ORG_CODE}' code='${visitDate.orgCode}'/>" 
				<c:if test="${visitDate.visitSn == visitDetail.visitSn}">selected</c:if>>
				${visitDate.visitDate}<c:if test="${orgCode == null || fn:length(orgCode) == 0}"><ref:translate domain="${Constants.DOMAIN_ORG_CODE}" code="${visitDate.orgCode}"/></c:if>
			</option>
		</c:forEach>
	</select>

</body>
</html>
