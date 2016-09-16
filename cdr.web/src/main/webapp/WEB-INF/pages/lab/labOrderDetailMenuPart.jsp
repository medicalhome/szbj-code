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
<script type="text/javascript" src="../scripts/lab/labMenuPart.js"></script>
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
				onclick="jumpToNext('../drug/detailDrugToNext_'+'${patientSn}'+'.html?flag=tabs',{'gotoType':'part',
				'pageNo':'${pagingContext.pageNo}','totalPageCnt':'${pagingContext.totalPageCnt}','patientSn':'${patientSn}',
				'prev':'prev'},'#pre_${pagingContext.pageNo}');">
				<img style="height:10px;" src="../images/upPage.png"/>
			</div>
		</c:if>
		<table style="width: 100%;" cellspacing="1" cellpadding="2" border="0" class="table">
			<tr class="tabletitle">
				<td id="tdTitle" height="24" align="center" width="50%">检验名称</td>
				<td height="24" align="center">报告日期</td>
			</tr>
		</table>
		<div id="prev_next" style="overflow-y: ${overflow};">
			<!--[if lt ie 8]><div style='+zoom:1;'><![endif]-->
			<div id="pre_${pagingContext.pageNo}"></div>
			<div>
				<%-- <table id="${pagingContext.pageNo}" style="width: 100%;" cellspacing="1" cellpadding="2" border="0" class="table">
					<c:forEach items="${labList}" var="lab" varStatus="status">
						<tr id="${lab.orderSn}"
							<c:if test="${status.count%2==1}">class="odd tabEnter" onmouseout="this.className='odd';removeTabsTip('#tabstip-lab')"</c:if>
							<c:if test="${status.count%2==0}">class="even tabEnter" onmouseout="this.className='even';removeTabsTip('#tabstip-lab')"</c:if> 
							<c:if test="${!empty lab.orderSn}">
							style="cursor:pointer;" 
							onmouseover="this.className='mouseover';showDrugOrder(event,
							{'orderStatusName':'${lab.orderStatusName}','requestDate':'<fmt:formatDate value="${lab.requestDate}" type="date" pattern="yyyy-MM-dd" />',
							'labDeptName':'${lab.labDeptName}','testerName':'${lab.testerName}','reporterName':'${lab.reporterName}','testDate':'<fmt:formatDate value="${lab.testDate}" type="date" pattern="yyyy-MM-dd" />'});"
							onclick="addMoreTabs(this,'../lab/detail.html?flag=tabs',{'menuPartFlag':'true','width':'70%','orderSn':'${lab.orderSn}',
							'name':'<c:choose>
											<c:when test="${fn:length(lab.itemName)>4}">${fn:substring(lab.itemName,0,4)}...</c:when>
											<c:otherwise>${lab.itemName}&nbsp;</c:otherwise>
										</c:choose>','holdOtherName':'${drugList.drugName}',
							'otherName':'','holdName':'${lab.itemName}','patientSn':'${patientSn}','labReportSn':'${lab.labReportSn}',
							'compositeItemSn':'${lab.compositeItemSn}','itemCode':'${lab.itemCode}','sourceSystemId':'${lab.sourceSystemId}',
							'type':'${TimerAndInpatientDto.LAB}'},'#moreTabs');"</c:if>>
							<td align="left" <c:if test="${lab.orderSn==orderSn && !empty lab.orderSn}">style="background-color: #87CEFF;font-weight: bolder;"</c:if>>
							${lab.itemName}</td>
							<td align="center" <c:if test="${lab.orderSn==orderSn && !empty lab.orderSn}">style="background-color: #87CEFF;font-weight: bolder;"</c:if>>
								<fmt:formatDate value="${lab.reportDate}" type="date" pattern="yyyy-MM-dd" />
							</td>
						</tr>
					</c:forEach>
				</table> --%>
				<jsp:include page="labOrderDetailMenuPartChart.jsp" />
			</div>
			<div id="next_${pagingContext.pageNo}"></div>
			<!--[if lt ie 8]></div><![endif]-->
		</div>
		<c:if test="${pagingContext.pageNo < pagingContext.totalPageCnt}">
			<!-- 住院视图用于此连接 -->
			<c:if test="${gotoType == 'lab'}">
				<div id="img_next"
					onclick="jumpToNext('../lab/list_${patientSn}_.html?flag=tabs',
					{'gotoType':'part',
					 'pageNo':'${pagingContext.pageNo}',
					 'totalPageCnt':'${pagingContext.totalPageCnt}',
					 'patientSn':'${patientSn}',
					 'next':'next',
					 'type':'${TimerAndInpatientDto.LAB}',
					 'visitSn':'${labListSearchParameters.visitSn}',
					 'inpatientDate':'${labListSearchParameters.inpatientDate}'},
					 '#next_${pagingContext.pageNo}');">
					<img style="height:10px;" src="../images/downPage.png"/>
				</div>
			</c:if>
			<!-- 时间轴视图用于此连接 -->
			<c:if test="${gotoType == 'lab_timer'}">
				<div id="img_next"
					onclick="jumpToNext('../lab/list_${patientSn}_.html?flag=tabs',
					{'gotoType':'part',
					 'pageNo':'${pagingContext.pageNo}',
					 'totalPageCnt':'${pagingContext.totalPageCnt}',
					 'patientSn':'${patientSn}',
					 'next':'next',
					 'type':'${TimerAndInpatientDto.LAB}',
					 'visitSn':'${labListSearchParameters.visitSn}'},
					 '#next_${pagingContext.pageNo}');">
					<img style="height:10px;" src="../images/downPage.png"/>
				</div>
			</c:if>
		</c:if>
	</div>
	<div id="tabs-animate" class="paneSeperator_open">&nbsp;&nbsp;</div>
</body>
</html>