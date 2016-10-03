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
		<c:forEach items="${diagnosisList}" var="dia" varStatus="status">
			<tr id="${dia.diagnosisSn}"
				<c:if test="${status.count%2==1}">class="odd tabEnter" onmouseout="this.className='odd';removeTabsTip('#tabstip-diagnosis')"</c:if>
				<c:if test="${status.count%2==0}">class="even tabEnter" onmouseout="this.className='even';removeTabsTip('#tabstip-diagnosis')"</c:if> 
				
				<c:if test="${!empty dia.diagnosisSn}">
				style="cursor:pointer;" 
				onmouseover="this.className='mouseover';
				showDetailTip(event,'#tabstip-diagnosis',
					{'科室':'${dia.diagnosticDeptName}',
					 '医生':'${dia.diagnosisDoctorName}',					 
					 '诊断日期':'<fmt:formatDate value="${dia.diagnosisDate}" type="date" pattern="yyyy-MM-dd HH:mm" />',
					 '就诊类别':'${dia.visitTypeName}',
					 '是否主要诊断': '${dia.mainDiagnosisFlag == Constants.MAIN_DIAGNOSIS ? "是" : "否" }',<%-- '<ref:translate domain="${Constants.DOMAIN_BOOLEAN_UNCERTAIN}" code="${dia.mainDiagnosisFlag}" />', --%>
					 '待查':'<ref:translate domain="${Constants.DOMAIN_BOOLEAN_UNCERTAIN}" code="${dia.uncertainFlag}" />',
					 <%-- '传染病':'<ref:translate domain="${Constants.DOMAIN_BOOLEAN_UNCERTAIN}" code="${dia.contagiousFlag}" />' --%>});"
				onclick="addMoreTabs(this,'../diagnosis/detail_${dia.diagnosisSn}.html?flag=tabs',{'menuPartFlag':'true','width':'70%','orderSn':'${dia.diagnosisSn}',
				'name':'<c:choose>
								<c:when test="${fn:length(dia.diseaseName)>4}">${fn:substring(dia.diseaseName,0,4)}...</c:when>
								<c:otherwise>${dia.diseaseName}&nbsp;</c:otherwise>
							</c:choose>','holdName':'${dia.diseaseName}',
				'otherName':'','holdOtherName':'','patientSn':'${patientSn}'},'#moreTabs');"</c:if>>
				<td align="center" <c:if test="${dia.diagnosisSn==diagnosisSn && !empty dia.diagnosisSn}">style="background-color: #87CEFF;font-weight: bolder;"</c:if>>
				${dia.diagnosisTypeName}</td>
				<td align="center" <c:if test="${dia.diagnosisSn==diagnosisSn && !empty dia.diagnosisSn}">style="background-color: #87CEFF;font-weight: bolder;"</c:if>>
				${dia.diseaseName}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>