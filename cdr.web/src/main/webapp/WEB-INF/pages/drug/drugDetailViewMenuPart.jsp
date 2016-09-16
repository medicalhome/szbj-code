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
<script type="text/javascript" src="../scripts/drug/drugMenuPart.js"></script>
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
				onclick="jumpToNext('../drug/list_'+'${patientSn}'+'.html?flag=tabs',
				{'gotoType':'part','pageNo':'${pagingContext.pageNo}','totalPageCnt':'${pagingContext.totalPageCnt}','patientSn':'${patientSn}',
				'prev':'prev'},'#next_${pagingContext.pageNo}');">
				<img style="height:10px;" src="../images/upPage.png"/>
			</div>
		</c:if>
		<table style="width: 100%;" cellspacing="1" cellpadding="2" border="0" class="table">
			<tr class="tabletitle">
			<!-- $Author :chang_xuewen
     			 $Date : 2013/10/31 11:00
     			 $[BUG]0038735 MODIFY BEGIN -->			
				<td id="tdTitle" height="24" align="center" width="50%">医嘱录入时间</td>
			<!-- $[BUG]0038735 MODIFY END -->
				<td height="24" align="center">药物商品名</td>
			</tr>
		</table>
		<div id="prev_next" style="overflow-y: ${overflow};">
		<!--[if lt ie 8]><div style='+zoom:1;'><![endif]-->
			<div id="pre_${pagingContext.pageNo}"></div>
			<div>
				<%-- <table id="${pagingContext.pageNo}" style="width: 100%;" cellspacing="1" cellpadding="2" border="0" class="table">
					<c:forEach items="${drugList}" var="drugList" varStatus="status">
						<tr id="${drugList.orderSn}"
							<c:if test="${status.count%2==1}">class="odd tabEnter" onmouseout="this.className='odd';removeTabsTip('#tabstip-drug')"</c:if>
							<c:if test="${status.count%2==0}">class="even tabEnter" onmouseout="this.className='even';removeTabsTip('#tabstip-drug')"</c:if> 
							<c:if test="${!empty drugList.orderSn}">
							style="cursor:pointer;" 
							onmouseover="this.className='mouseover';showDrugOrder(event,{'medicineClassName':'${drugList.medicineClassName}','medicineTypeName':'${drugList.medicineTypeName}',
								'medicineForm':'${drugList.medicineForm}','routeName':'${drugList.routeName}','dosage':'${drugList.dosage}','dosageUnit':'${drugList.dosageUnit}',
								'totalDosage':'${drugList.totalDosage}','totalDosageUnit':'${drugList.totalDosageUnit}','medicineFreqName':'${drugList.medicineFreqName}',
								'temporaryFlag':'<ref:translate domain="${Constants.DOMAIN_TEMPORARY_FLAG}" code="${drugList.temporaryFlag}" />',
								'orderPersonName':'${drugList.orderPersonName}','orderDeptName':'${drugList.orderDeptName}'});"
							onclick="addMoreTabs(this,'../drug/detail_'+'${drugList.orderSn}'+'.html?flag=tabs',
									{'menuPartFlag':'true','temporaryFlag':'${temporaryFlag}','width':'70%','orderSn':'${drugList.orderSn}',
									'name':'<c:choose>
													<c:when test="${drugList.orderType==Constants.HERBAL_MEDICINE_CLASS}">草药&nbsp;</c:when>
													<c:when test="${fn:length(drugList.drugName)>4}">${fn:substring(drugList.drugName,0,4)}...</c:when>
													<c:otherwise>${drugList.drugName}&nbsp;</c:otherwise>
												</c:choose>','holdName':'${drugList.drugName}',
									'otherName':'<c:choose>
												<c:when test="${drugList.orderType==Constants.HERBAL_MEDICINE_CLASS}">草药&nbsp;</c:when>
												<c:when test="${fn:length(drugList.approvedDrugName)>4}">${fn:substring(drugList.approvedDrugName,0,4)}...</c:when>
												<c:otherwise>${drugList.approvedDrugName}&nbsp;</c:otherwise>
											</c:choose>','holdOtherName':'${drugList.approvedDrugName}',
										'patientSn':'${drugList.patientSn}','visitDateSn':'${vdl.visitDate}'},'#moreTabs');"</c:if>>
							<td align="center" <c:if test="${drugList.orderSn==orderSn && !empty drugList.orderSn}">style="background-color: #87CEFF;font-weight: bolder;"</c:if>>
							<fmt:formatDate value="${drugList.orderTime}" type="date" pattern="yyyy-MM-dd HH:mm" /></td>
							<td align="left" <c:if test="${drugList.orderSn==orderSn && !empty drugList.orderSn}">style="background-color: #87CEFF;font-weight: bolder;"</c:if>>
								<c:choose>
									<c:when test="${drugList.orderType==Constants.HERBAL_MEDICINE_CLASS}">草药&nbsp;</c:when>
									<c:when test="${fn:length(drugList.approvedDrugName)>4}">${fn:substring(drugList.approvedDrugName,0,4)}...</c:when>
									<c:otherwise>${drugList.drugName}&nbsp;</c:otherwise>
								</c:choose>
							</td>
						</tr>
					</c:forEach>
				</table> --%>
				<jsp:include page="drugDetailViewMenuPartChart.jsp" />
			</div>
			<div id="next_${pagingContext.pageNo}"></div>
			<!--[if lt ie 8]></div><![endif]-->
		</div>
		<c:if test="${pagingContext.pageNo < pagingContext.totalPageCnt}">
			<!-- 住院视图用于此连接 -->
			<c:if test="${gotoType == 'drug'}">
				<div id="img_next" 
					onclick="jumpToNext('../drug/list_'+'${patientSn}'+'.html?flag=tabs',
					{'gotoType':'part',
					 'pageNo':'${pagingContext.pageNo}',
					 'totalPageCnt':'${pagingContext.totalPageCnt}',
					 'patientSn':'${patientSn}',
					 'next':'next',
					 'type':'${TimerAndInpatientDto.DRUG_ORDER}',
					 'visitSn':'${drugListSearchPra.visitSn}',
					 'inpatientDate':'${drugListSearchPra.inpatientDate}',
					 'temporaryFlag':'${Constants.TEMPORARY_FLAG}',
					 'longTempFlag':'${Constants.TEMPORARY_FLAG}',
					 'cancelOrderStatus':'${Constants.ORDER_STATUS_CANCEL}'},
					'#next_${pagingContext.pageNo}');">
					<img style="height:10px;" src="../images/downPage.png"/>
				</div>
			</c:if>
			<!-- 时间轴视图用于此连接 -->
			<c:if test="${gotoType == 'drug_timer'}">
				<div id="img_next" 
					onclick="jumpToNext('../drug/list_'+'${patientSn}'+'.html?flag=tabs',
					{'gotoType':'part',
					 'pageNo':'${pagingContext.pageNo}',
					 'totalPageCnt':'${pagingContext.totalPageCnt}',
					 'patientSn':'${patientSn}',
					 'next':'next',
					 'type':'${TimerAndInpatientDto.DRUG_ORDER}',
					 'visitSn':'${drugListSearchPra.visitSn}'},
					'#next_${pagingContext.pageNo}');">
					<img style="height:10px;" src="../images/downPage.png"/>
				</div>
			</c:if>
		</c:if>
	</div>
	<div id=tabs-animate class="paneSeperator_open">&nbsp;&nbsp;</div>
</body>
</html>