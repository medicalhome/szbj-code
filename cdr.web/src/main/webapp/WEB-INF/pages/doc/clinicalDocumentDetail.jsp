<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="translation.tld" prefix="ref"%>
<jsp:useBean id="Constants" class="com.yly.cdr.core.Constants"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <meta http-equiv="Pragma" content="no-cache" />
	<meta http-equiv="Cache-Control" content="no-cache" />
	<meta http-equiv="Expires" content="0" />
    <title>病历文书CDA</title>
    <link type="text/css" rel="Stylesheet" href="../styles/jquery-ui-1.8.18.custom.modify.css"
        charset="UTF8" />
    <link type="text/css" rel="Stylesheet" href="../styles/layout-default-1.3.0rc29.15.css"
        charset="UTF8" />
    <link type="text/css" rel="Stylesheet" href="../styles/layout-cdr-dialog.css" charset="UTF8" />
    <link type="text/css" rel="Stylesheet" href="../styles/tablelist.css" charset="UTF8" />
    <script>
    $(document).ready(function(){

    	var orderSn = '${doc.serviceId}${doc.documentLid}';
    	
   	 	closeTab("#moreTabs",orderSn);
   });
    /*$Author :chang_xuewen
     * $Date : 2013/08/07 16:20
     * $[BUG]0035740 ADD BEGIN*/
    function returnParentName()
	{
		return '${parentDrugOrder.drugName}';
	}
	function returnMutexesName()
	{
		return '${mutexesOrderName}';
	}
	/* $[BUG]0035740 ADD END*/
   </script>
</head>

<body>
	<div id="dialog">
		<div id="mainContent">
			<div id="_${doc.serviceId}${doc.documentLid}" name="selectTabs">
				<div class="responsability">${Constants.RESPONSABILITY_CLAIMING_CONTENT}</div>
				<c:if test="${empty docImagePath}">
					<h4 align="center">没有相关的CDA报告信息！</h4>
				</c:if>
				<c:if test="${!empty docImagePath}">
					<div align="center">
						<c:if test="${docType != 'pdf' && docType != 'xps'}">
							<img width="100%" src="${docImagePath}"/>
						</c:if>
						<c:if test="${portalFlag == 'true' && (docType == 'pdf' || docType == 'xps')}">
							<iframe id="mainframe" name="mainframe" marginwidth="0" marginheight="0" src="${docImagePath}"
	                    		frameborder="0" width="98%" scrolling="no" height="1024"></iframe> 
						</c:if>
						<c:if test="${portalFlag != 'true' && (docType == 'pdf' || docType == 'xps')}">
							<iframe id="mainframe" name="mainframe" marginwidth="0" marginheight="0" src="${docImagePath}"
	                    		frameborder="0" width="98%" scrolling="no" height="1980"></iframe> 
						</c:if>
					</div>
				</c:if>
			</div>
		</div>
	</div>
</body>
</html>