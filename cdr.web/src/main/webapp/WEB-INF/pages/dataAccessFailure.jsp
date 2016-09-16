<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="app-ctx" value="${ctx}/app" />
<c:set var="datePattern">
	<fmt:message key="date.format" />
</c:set>
﻿<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Cache-Control" content="no-store" />
	<!-- HTTP 1.0 -->
	<meta http-equiv="Pragma" content="no-cache" />
	<!-- Prevents caching at the Proxy Server -->
	<meta http-equiv="Expires" content="0" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<!-- leave this for stats please -->
	<link rel="icon" href="<c:url value="/images/favicon.ico"/>" />
	<title><fmt:message key="404.title"/></title>
<head>
    <meta name="heading" content="<fmt:message key="404.title"/>"/>
    <meta name="menu" content="AdminMenu"/>
</head>

<p>
    <c:out value="${requestScope.exception.message}"/>
</p>

<%--
<% 
((Exception) request.getAttribute("exception")).printStackTrace(new java.io.PrintWriter(out));  
%>
--%>

<a href="mainMenu" onclick="history.back();return false">&#171; Back</a>