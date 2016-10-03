<%@ page language="java" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="translation.tld" prefix="ref"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="html2.tld" prefix="html2"%>
<%@ taglib uri="html.tld" prefix="html"%>
<jsp:useBean id="Constants" class="com.yly.cdr.core.Constants" />
<jsp:useBean id="TimerAndInpatientDto" class="com.yly.cdr.dto.TimerAndInpatientDto"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<script type="text/javascript" src="../scripts/exam/examMenuPart.js"></script>
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
			<div id="img_prev" style="width:100%;height:10px;float: left;background-color: #BDF0FD;text-align:center;cursor: pointer;" 
				onclick="jumpToNext('../drug/detailDrugToNext_'+'${patientSn}'+'.html?flag=tabs',{'gotoType':'part',
				'pageNo':'${pagingContext.pageNo}','totalPageCnt':'${pagingContext.totalPageCnt}','patientSn':'${patientSn}',
				'prev':'prev'},'#pre_${pagingContext.pageNo}');">
				<img style="height:10px;" src="../images/upPage.png"/>
			</div>
		</c:if>
		<table style="width: 100%;" cellspacing="1" cellpadding="2" border="0" class="table">
			<tr class="tabletitle">
				<td id="tdTitle" height="24" align="center" width="50%">检查项目</td>
				<td height="24" align="center">报告日期</td>
			</tr>
		</table>
		<div id="prev_next" style="overflow-y: ${overflow};">
			<!--[if lt ie 8]><div style='+zoom:1;'><![endif]-->
			<div id="pre_${pagingContext.pageNo}"></div>
			<div>
				<%-- <table id="${pagingContext.pageNo}" style="width: 100%;" cellspacing="1" cellpadding="2" border="0" class="table">
					<c:forEach items="${examList}" var="exam" varStatus="status">
						<tr id="${exam.orderSn}"
							<c:if test="${status.count%2==1}">class="odd tabEnter" onmouseout="this.className='odd';removeTabsTip('#tabstip-exam')"</c:if>
							<c:if test="${status.count%2==0}">class="even tabEnter" onmouseout="this.className='even';removeTabsTip('#tabstip-exam')"</c:if> 
							<c:if test="${!empty exam.orderSn}">
							style="cursor:pointer;" 
							onmouseover="this.className='mouseover';
							showExamOrder(event,'#tabstip-exam',
								{'检查部位':'${exam.examinationRegionName}',
								 '医嘱状态':'${exam.orderStatusName}',
								 '申请日期':'<fmt:formatDate value="${exam.requestDate}" type="date" pattern="yyyy-MM-dd"/>',
								 '检查方法':'${exam.examinationMethodName}',
								 '检查医生':'${exam.examiningDoctorName}',
								 '检查日期':'<fmt:formatDate value="${exam.examinationDate}" type="date" pattern="yyyy-MM-dd" />',
								 '检查结果':'${exam.withdrawFlag==Constants.REPORT_WITHDRAW_FLAG?Constants.REPORT_WITHDRAW_CONTENT:exam.eiImagingConclusion==null?exam.erImagingConclusion:exam.eiImagingConclusion}',
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
				</table> --%>
				<jsp:include page="examDetailViewMenuPartChart.jsp" />
			</div>
			<div id="next_${pagingContext.pageNo}"></div>
			<!--[if lt ie 8]></div><![endif]-->
		</div>
		<c:if test="${pagingContext.pageNo < pagingContext.totalPageCnt}">
			<!-- 住院视图用于此连接 -->
			<c:if test="${gotoType == 'exam'}">
				<div id="img_next"
					onclick="jumpToNext('../exam/list_'+'${patientSn}'+'_.html?flag=tabs',
					{'gotoType':'part',
					 'pageNo':'${pagingContext.pageNo}',
					 'totalPageCnt':'${pagingContext.totalPageCnt}',
					 'patientSn':'${patientSn}',
					 'next':'next',
					 'type':'${TimerAndInpatientDto.EXAMINATION}',
					 'visitSn':'${examListSearchParameters.visitSn}',
					 'inpatientDate':'${examListSearchParameters.inpatientDate}'},
					 '#next_${pagingContext.pageNo}');">
					<img style="height:10px;" src="../images/downPage.png"/>
				</div>
			</c:if>
			<!-- 时间轴视图用于此连接 -->
			<c:if test="${gotoType == 'exam_timer'}">
				<div id="img_next"
					onclick="jumpToNext('../exam/list_'+'${patientSn}'+'_.html?flag=tabs',
					{'gotoType':'part',
					 'pageNo':'${pagingContext.pageNo}',
					 'totalPageCnt':'${pagingContext.totalPageCnt}',
					 'patientSn':'${patientSn}',
					 'next':'next',
					 'type':'${TimerAndInpatientDto.EXAMINATION}',
					 'visitSn':'${examListSearchParameters.visitSn}'},
					 '#next_${pagingContext.pageNo}');">
					<img style="height:10px;" src="../images/downPage.png"/>
				</div>
			</c:if>
		</c:if>
	</div>
	<div id="tabs-animate" class="paneSeperator_open">&nbsp;&nbsp;</div>
</body>
</html>