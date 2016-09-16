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
</head>
<body style="margin: 0; padding: 0;">
	<table id="${pagingContext.pageNo}" style="width: 100%;" cellspacing="1" cellpadding="2" border="0" class="table">
		<c:forEach items="${drugList}" var="drugList" varStatus="status">
			<tr id="${drugList.orderSn}"
				<c:if test="${status.count%2==1}">class="odd tabEnter" onmouseout="this.className='odd';removeTabsTip('#tabstip-drug')"</c:if>
				<c:if test="${status.count%2==0}">class="even tabEnter" onmouseout="this.className='even';removeTabsTip('#tabstip-drug')"</c:if> 
				<c:if test="${!empty drugList.orderSn}">
				style="cursor:pointer;" 
				onmouseover="this.className='mouseover';
				showDetailTip(event,'#tabstip-drug',
				   {'药物类型':'${drugList.medicineTypeName}',
					'药物剂型':'${drugList.medicineForm}',
					'用药途径':'${drugList.routeName}',
					'次剂量':'${drugList.dosage}${drugList.dosageUnit}',
					'总剂量':'${drugList.totalDosage}${drugList.totalDosageUnit}',
					'使用频率':'${drugList.medicineFreqName}',
					'长期或临时':'<ref:translate domain="${Constants.DOMAIN_TEMPORARY_FLAG}" code="${drugList.temporaryFlag}" />',
					'开嘱人':'${drugList.orderPersonName}',
					'开嘱科室':'${drugList.orderDeptName}'});"
				onclick="addMoreTabs(this,'../drug/detail_'+'${drugList.orderSn}'+'.html?flag=tabs',
						{'menuPartFlag':'true','temporaryFlag':'${temporaryFlag}','width':'70%','orderSn':'${drugList.orderSn}',
						'otherName':'<c:choose>
										<c:when test="${drugList.orderType==Constants.HERBAL_MEDICINE_CLASS}">草药&nbsp;</c:when>
										<c:when test="${fn:length(drugList.approvedDrugName)>4}">${fn:substring(drugList.approvedDrugName,0,4)}...</c:when>
										<c:otherwise>${drugList.approvedDrugName}&nbsp;</c:otherwise>
									</c:choose>','holdOtherName':'${drugList.approvedDrugName}',
						'name':'<c:choose>
									<c:when test="${drugList.orderType==Constants.HERBAL_MEDICINE_CLASS}">草药&nbsp;</c:when>
									<c:when test="${fn:length(drugList.drugName)>4}">${fn:substring(drugList.drugName,0,4)}...</c:when>
									<c:otherwise>${drugList.drugName}&nbsp;</c:otherwise>
								</c:choose>','holdName':'${drugList.drugName}',
						'patientSn':'${drugList.patientSn}','visitDateSn':'${vdl.visitDate}'},'#moreTabs');"</c:if>>
				<td align="center" <c:if test="${drugList.orderSn==orderSn && !empty drugList.orderSn}">style="background-color: #87CEFF;font-weight: bolder;"</c:if>>
				<fmt:formatDate value="${drugList.orderTime}" type="date" pattern="yyyy-MM-dd HH:mm" /></td>
				<td align="left" <c:if test="${drugList.orderSn==orderSn && !empty drugList.orderSn}">style="background-color: #87CEFF;font-weight: bolder;"</c:if>>
					<c:choose>
						<c:when test="${drugList.orderType==Constants.HERBAL_MEDICINE_CLASS}">草药&nbsp;</c:when>
						<%-- <c:when test="${fn:length(drugList.approvedDrugName)>4}">${fn:substring(drugList.approvedDrugName,0,4)}...</c:when> --%>
						<c:otherwise>${drugList.drugName}&nbsp;</c:otherwise>
					</c:choose>
				</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>