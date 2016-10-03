<%@ page language="java" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="translation.tld" prefix="ref"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="html2.tld" prefix="html2"%>
<%@ taglib uri="html.tld" prefix="html"%>
<jsp:useBean id="Constants" class="com.yly.cdr.core.Constants" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
</head>
<body style="margin: 0; padding: 0;">
	<table id="${pagingContext.pageNo}" style="width: 100%;" cellspacing="1" cellpadding="2" border="0" class="table">
		<c:forEach items="${labList}" var="lab" varStatus="status">
			<tr id="${lab.orderSn}"
				<c:if test="${status.count%2==1}">class="odd tabEnter" onmouseout="this.className='odd';removeTabsTip('#tabstip-lab')"</c:if>
				<c:if test="${status.count%2==0}">class="even tabEnter" onmouseout="this.className='even';removeTabsTip('#tabstip-lab')"</c:if> 
				<c:if test="${!empty lab.orderSn}">
				style="cursor:pointer;" 
				onmouseover="this.className='mouseover';
				showDetailTip(event,'#tabstip-lab',
					{'医嘱状态':'${lab.orderStatusName}',
					 '申请日期':'<fmt:formatDate value="${lab.requestDate}" type="date" pattern="yyyy-MM-dd HH:mm" />',
					 '执行科室':'${lab.labDeptName}',
					 '检验医生':'${lab.testerName}',
					 '报告医生':'${lab.reporterName}',
					 '检验日期':'<fmt:formatDate value="${lab.testDate}" type="date" pattern="yyyy-MM-dd HH:mm" />'});"
				onclick="addMoreTabs(this,'../lab/detail.html?flag=tabs',{'menuPartFlag':'true','width':'70%','orderSn':'${lab.orderSn}',
				'name':'<c:choose>
								<c:when test="${fn:length(lab.itemName)>4}">${fn:substring(lab.itemName,0,4)}...</c:when>
								<c:otherwise>${lab.itemName}&nbsp;</c:otherwise>
							</c:choose>','holdOtherName':'${drugList.drugName}',
				'otherName':'','holdName':'${lab.itemName}','patientSn':'${patientSn}','labReportSn':'${lab.labReportSn}',
				'compositeItemSn':'${lab.compositeItemSn}','itemCode':'${lab.itemCode}','sourceSystemId':'${lab.sourceSystemId}',
				'type':'${TimerAndInpatientDto.LAB}'},'#moreTabs');"</c:if>>
				<td height="24" align="center" <c:if test="${lab.orderSn==orderSn && !empty lab.orderSn}">style="background-color: #87CEFF;font-weight: bolder;"</c:if>>
				${lab.itemName}</td>
				<td height="24" align="left" <c:if test="${lab.orderSn==orderSn && !empty lab.orderSn}">style="background-color: #87CEFF;font-weight: bolder;"</c:if>>
					<fmt:formatDate value="${lab.reportDate}" type="date" pattern="yyyy-MM-dd" />
				</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>