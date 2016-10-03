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
<script>
	var pageNo = '${pageNo}';
</script>
</head>
<body style="margin: 0; padding: 0;">
	<table id="${pagingContext.pageNo}" style="width: 100%;" cellspacing="1" cellpadding="2" border="0" class="table">
		<c:forEach items="${examList}" var="exam" varStatus="status">
			<tr id="${exam.orderSn}_${exam.examReportSn}_${exam.itemSn}"
				<c:if test="${status.count%2==1}">class="odd tabEnter" onmouseout="this.className='odd';removeTabsTip('#tabstip-exam')"</c:if>
				<c:if test="${status.count%2==0}">class="even tabEnter" onmouseout="this.className='even';removeTabsTip('#tabstip-exam')"</c:if> 
				<c:if test="${!empty exam.orderSn}">
				style="cursor:pointer;" 
				onmouseover="this.className='mouseover';
				showDetailTip(event,'#tabstip-exam',
					{'检查部位':'${exam.examinationRegionName}',
					 '医嘱状态':'${exam.orderStatusName}',
					 '申请日期':'<fmt:formatDate value="${exam.requestDate}" type="date" pattern="yyyy-MM-dd"/>',
					 '检查方法':'${exam.examinationMethodName}',
					 '检查医生':'${exam.examiningDoctorName}',
					 '检查日期':'<fmt:formatDate value="${exam.examinationDate}" type="date" pattern="yyyy-MM-dd" />',					 
					 '检查科室':'${exam.orderDeptName}'});"
				onclick="addMoreTabs(this,'../exam/detail.html?flag=tabs',
						{'menuPartFlag':'true','width':'70%','orderSn':'${exam.orderSn}',
						'otherName':'','holdOtherName':'',
						'name':'<c:choose>
									<c:when test="${fn:length(exam.examinationItemName)>4}">${fn:substring(exam.examinationItemName,0,4)}...</c:when>
									<c:otherwise>${exam.examinationItemName}&nbsp;</c:otherwise>
								</c:choose>','holdName':'${exam.examinationItemName}',
						'patientSn':'${patientSn}','examReportSn':'${exam.examReportSn}','examinationRegion':'${exam.examinationRegion}',
						'examinationItem':'${exam.examinationItem}','itemSn':'${exam.itemSn}','withdrawFlag':'${exam.withdrawFlag}','itemClass':'${exam.itemClass}'},'#moreTabs');"</c:if>>
				<td align="left" <c:if test="${exam.orderSn==orderSn && !empty exam.orderSn}">style="background-color: #87CEFF;font-weight: bolder;"</c:if>>
					${exam.examinationItemName}</td>
				<td align="center" <c:if test="${exam.orderSn==orderSn && !empty exam.orderSn}">style="background-color: #87CEFF;font-weight: bolder;"</c:if>>
					<fmt:formatDate value="${exam.reportDate}" type="date" pattern="yyyy-MM-dd" />
				</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>