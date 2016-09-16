<%@ page language="java" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="translation.tld" prefix="ref"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="html2.tld" prefix="html2"%>
<%@ taglib uri="html.tld" prefix="html"%>
<jsp:useBean id="Constants" class="com.founder.cdr.core.Constants" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script>
	var pageNo = '${pageNo}';
</script>
</head>
<body style="margin: 0; padding: 0;">
	<table id="${pagingContext.pageNo}" style="width: 100%;" cellspacing="1" cellpadding="2" border="0" class="table">
		<c:forEach items="${orderList}" var="ord" varStatus="status">
			<tr id="${ord.orderSn}"
				<c:if test="${status.count%2==1}">class="odd tabEnter" onmouseout="this.className='odd';removeTabsTip('#tabstip-non')"</c:if>
				<c:if test="${status.count%2==0}">class="even tabEnter" onmouseout="this.className='even';removeTabsTip('#tabstip-non')"</c:if> 
				<c:if test="${!empty ord.orderSn}">
				style="cursor:pointer;" 
				onmouseover="this.className='mouseover';
				showDetailTip(event,'#tabstip-non',
					{'病区':'${ord.wardName}',
					 '长期或临时':'<ref:translate domain="${Constants.DOMAIN_TEMPORARY_FLAG}" code="${ord.temporaryFlag}"/>',
					 '开嘱科室':'${ord.orderDeptName}',
					 '执行科室':'${ord.execDeptName}',
					 '开嘱人':'${ord.orderPersonName}',
					 '医嘱录入时间':'<fmt:formatDate value="${ord.orderTime}" type="date" pattern="yyyy-MM-dd HH:mm" />',
					 '取消人':'${ord.cancelPersonName}',
					 '取消时间':'<fmt:formatDate value="${ord.cancelTime}" type="date" pattern="yyyy-MM-dd HH:mm" />'});"
				onclick="addMoreTabs(this,'${ord.orderPath}',{'menuPartFlag':'true','width':'70%','orderSn':'${ord.orderSn}','otherName':'','holdOtherName':'',
						'name':'<c:choose>
									<c:when test="${fn:length(ord.orderName)>4}">${fn:replace(fn:substring(ord.orderName,0,4),'\"','&quot;')}...</c:when>
									<c:otherwise>${fn:trim(fn:replace(ord.orderName,'\"','&quot;'))}</c:otherwise>
								</c:choose>','holdName':'${fn:trim(fn:replace(ord.orderName,'\"','&quot;'))}',
						'patientSn':'${patientSn}'},'#moreTabs');"</c:if>>
				<td align="left" <c:if test="${ord.orderSn==orderSn && !empty ord.orderSn}">style="background-color: #87CEFF;font-weight: bolder;"</c:if>>
					${ord.orderTypeName}
				</td>
				<td align="left" <c:if test="${ord.orderSn==orderSn && !empty ord.orderSn}">style="background-color: #87CEFF;font-weight: bolder;"</c:if>>
					${ord.orderName}
				</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>