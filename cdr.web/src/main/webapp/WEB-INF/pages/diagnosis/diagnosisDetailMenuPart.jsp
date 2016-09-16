<%@ page language="java" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="translation.tld" prefix="ref"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="html2.tld" prefix="html2"%>
<%@ taglib uri="html.tld" prefix="html"%>
<jsp:useBean id="Constants" class="com.founder.cdr.core.Constants" />
<jsp:useBean id="TimerAndInpatientDto" class="com.founder.cdr.dto.TimerAndInpatientDto"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<script type="text/javascript" src="../scripts/diagnosis/diagnosisMenuPart.js"></script>
<script type="text/javascript" src="../scripts/commonTabs.js"></script>
<script type="text/javascript">
function getPageNo()
{
	return '${pagingContext.pageNo}';
}

function getTotalPageCnt()
{
	return '${pagingContext.totalPageCnt}';
}

$(function(){
	setLeftHeight(getPageNo(),getTotalPageCnt());
});

</script>
</head>
<body style="margin: 0; padding: 0;">
	<div id="dd_detailContent">
		<c:if test="${pagingContext.pageNo>1}">
			<div id="img_prev"
				onclick="jumpToNext('../diagnosis/list_'+'${patientSn}'+'.html?flag=tabs',{'gotoType':'part','pageNo':'${pagingContext.pageNo}','totalPageCnt':'${pagingContext.totalPageCnt}','patientSn':'${patientSn}','prev':'prev'},'#pre_${pagingContext.pageNo}');">
				<img style="height:10px;" src="../images/upPage.png"/>
			</div>
		</c:if>
		<table style="width: 100%;" cellspacing="1" cellpadding="2" border="0" class="table">
			<tr class="tabletitle">
				<td id="tdTitle" height="24" align="center" width="50%">诊断类型</td>
				<td height="24" align="center">诊断名称</td>
			</tr>
		</table>
		<div id="prev_next" style="overflow-y: ${overflow};">
			<!--[if lt ie 8]><div style='+zoom:1;'><![endif]-->
			<div id="pre_${pagingContext.pageNo}"></div>
			<div>
				<%-- <table id="${pagingContext.pageNo}" style="width: 100%;" cellspacing="1" cellpadding="2" border="0" class="table">
					<c:forEach items="${diagnosisList}" var="dia" varStatus="status">
						<tr id="${dia.diagnosisSn}"
							<c:if test="${status.count%2==1}">class="odd tabEnter" onmouseout="this.className='odd';removeTabsTip('#tabstip-diagnosis')"</c:if>
							<c:if test="${status.count%2==0}">class="even tabEnter" onmouseout="this.className='even';removeTabsTip('#tabstip-diagnosis')"</c:if> 
							<c:if test="${!empty dia.diagnosisSn}">
							style="cursor:pointer;" 
							onmouseover="this.className='mouseover';showDiagnosis(event,
							{'diagnosticDeptName':'${dia.diagnosticDeptName}','diagnosisDoctorName':'${dia.diagnosisDoctorName}','visitTypeName':'${dia.visitTypeName}',
							'diagnosisDate':'<fmt:formatDate value="${dia.diagnosisDate}" type="date" pattern="yyyy-MM-dd HH:mm" />',
							'mainDiagnosisFlag':'<ref:translate domain="${Constants.DOMAIN_BOOLEAN_UNCERTAIN}" code="${dia.mainDiagnosisFlag}" />',
							'uncertainFlag':'<ref:translate domain="${Constants.DOMAIN_BOOLEAN_UNCERTAIN}" code="${dia.uncertainFlag}" />',
							'contagiousFlag':'<ref:translate domain="${Constants.DOMAIN_BOOLEAN_UNCERTAIN}" code="${dia.contagiousFlag}" />'});"
							onclick="addMoreTabs(this,'../diagnosis/detail_${dia.diagnosisSn}.html?flag=tabs',{'menuPartFlag':'true','width':'70%','orderSn':'${dia.diagnosisSn}',
							'name':'<c:choose>
											<c:when test="${fn:length(dia.diseaseName)>4}">${fn:substring(dia.diseaseName,0,4)}...</c:when>
											<c:otherwise>${dia.diseaseName}&nbsp;</c:otherwise>
										</c:choose>','holdName':'${dia.diseaseName}',
							'otherName':'','holdOtherName':'','patientSn':'${patientSn}'},'#moreTabs');"</c:if>>
							<td align="left" <c:if test="${dia.diagnosisSn==diagnosisSn && !empty dia.diagnosisSn}">style="background-color: #87CEFF;font-weight: bolder;"</c:if>>
							${dia.diagnosisTypeName}</td>
							<td align="left" <c:if test="${dia.diagnosisSn==diagnosisSn && !empty dia.diagnosisSn}">style="background-color: #87CEFF;font-weight: bolder;"</c:if>>
							${dia.diseaseName}</td>
						</tr>
					</c:forEach>
				</table> --%>
				<jsp:include page="diagnosisDetailMenuPartChart.jsp"/>
			</div>
			<div id="next_${pagingContext.pageNo}"></div>
			<!--[if lt ie 8]></div><![endif]-->
		</div>
		<c:if test="${pagingContext.pageNo < pagingContext.totalPageCnt}">
			<!-- 住院视图用于此连接 -->
			<c:if test="${gotoType == 'diagnosis'}">
				<div id="img_next" 
					onclick="jumpToNext('../diagnosis/list_'+'${patientSn}'+'.html?flag=tabs',
					{'gotoType':'part',
					 'pageNo':'${pagingContext.pageNo}',
					 'totalPageCnt':'${pagingContext.totalPageCnt}',
					 'patientSn':'${patientSn}',
					 'next':'next',
					 'type':'${TimerAndInpatientDto.DIAGNOSIS}',
					 'visitSn':'${diagnosisListSearchPra.visitSn}',
					 'inpatientDate':'${diagnosisListSearchPra.inpatientDate}'},
					 '#next_${pagingContext.pageNo}');">
					<img style="height:10px;" src="../images/downPage.png"/>
				</div>
			</c:if>
			<!-- 时间轴视图用于此连接 -->
			<c:if test="${gotoType == 'diagnosis_timer'}">
				<div id="img_next" 
					onclick="jumpToNext('../diagnosis/list_'+'${patientSn}'+'.html?flag=tabs',
					{'gotoType':'part',
					 'pageNo':'${pagingContext.pageNo}',
					 'totalPageCnt':'${pagingContext.totalPageCnt}',
					 'patientSn':'${patientSn}',
					 'next':'next',
					 'type':'${TimerAndInpatientDto.DIAGNOSIS}',
					 'visitSn':'${diagnosisListSearchPra.visitSn}'},
					 '#next_${pagingContext.pageNo}');">
					<img style="height:10px;" src="../images/downPage.png"/>
				</div>
			</c:if>
		</c:if>
	</div>
	<div id="tabs-animate" class="paneSeperator_open">&nbsp;&nbsp;</div>
</body>
</html>