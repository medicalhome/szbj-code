<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="translation.tld" prefix="ref"%>
<%@ taglib uri="html.tld" prefix="html"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="Constants" class="com.yly.cdr.core.Constants" />
<jsp:useBean id="TimerAndInpatientDto"
	class="com.yly.cdr.dto.TimerAndInpatientDto" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Pragma" content="no-cache" />
<!-- Prevents caching at the Proxy Server -->
<meta http-equiv="Expires" content="0" />
<title>具体临时药物医嘱</title>
<link rel="Stylesheet" type="text/css" href="../styles/tablelist.css" />
</head>
<body style="margin: 0; padding: 0;">
	<c:forEach items="${specificallyTempDrugist}"
		var="specificallyTempDrugist" varStatus="status">
		<div
			<c:choose>
				<c:when test="${status.count > 2}">
		 			style="width:${firstDivWidth};margin-top:3px;"
		 		</c:when>
		 		<c:otherwise>
		 			 style="width:${firstDivWidth};"
		 		</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${status.count % 2 != 0}">
		 			class="leftClass"
		 		</c:when>
		 		<c:otherwise>
		 			 class="rightClass"
		 		</c:otherwise>
			</c:choose>>
			<table style="width: 100%; color: blue; cursor: pointer;"
				cellpadding="0" cellspacing="0"
				onmouseover="this.style.color='red';"
				onmouseout="this.style.color='blue';"
				onclick="loadCommonPanel('临时药物医嘱列表',{'url':'../drug/detail_${specificallyTempDrugist.orderSn}.html?flag=tabs',
						'tabsFlag':'true','gotoType':'drug','special':'special','width':'70%','patientSn':'${patientSn}','orderSn':'${specificallyTempDrugist.orderSn}','trID':'${specificallyTempDrugist.orderSn}',
						'type':'${TimerAndInpatientDto.DRUG_ORDER}','visitSn':'${specificallyTempDrugist.visitSn}',
						'name':'${specificallyTempDrugist.drugName == null?specificallyTempDrugist.approvedDrugName:specificallyTempDrugist.drugName}',
						'inpatientDate':'${inpatientDate}','temporaryFlag':'${Constants.TEMPORARY_FLAG}','longTempFlag':'${Constants.TEMPORARY_FLAG}',
						'cancelOrderStatus':'${Constants.ORDER_STATUS_CANCEL}'});return false;">
				<tr>
					<td style="width: 50%; border-top: 0; border-bottom: 0; border-left: 0; border-right: 0;">
						<!-- $Author :jin_peng
							 $Date : 2013/02/19 11:20$
							 [BUG]0013981 MODIFY BEGIN -->
						<c:choose>
							<c:when test="${status.count > 2}">
								<c:if test="${specificallyTempDrugist.orderType==Constants.HERBAL_MEDICINE_CLASS}">草药</c:if>
								${specificallyTempDrugist.drugName}&nbsp;
							</c:when>
							<c:otherwise>
			 					${specificallyTempDrugist.drugName}&nbsp;
			 				</c:otherwise>
						</c:choose>
						<!-- [BUG]0013981 MODIFY END -->
					</td>
					<td style="width: 20%; border-top: 0; border-bottom: 0; border-left: 0; border-right: 0;" 
						<c:if test="${fn:length(specificallyLongDrug.medicineForm)>3}">title="${specificallyLongDrug.medicineForm}"</c:if>>
						<c:choose>
							<c:when test="${fn:length(specificallyLongDrug.medicineForm)>3}">${fn:substring(specificallyLongDrug.medicineForm,0,3)}...</c:when>
							<c:otherwise>${specificallyLongDrug.medicineForm}&nbsp;</c:otherwise>
						</c:choose>
					</td>
					<td style="width: 15%; border-top: 0; border-bottom: 0; border-left: 0; border-right: 0;">
						${specificallyTempDrugist.dosage}${specificallyTempDrugist.dosageUnit}&nbsp;
					</td>
					<td style="width: 15%; border-top: 0; border-bottom: 0; border-left: 0; border-right: 0;">
						${specificallyTempDrugist.totalDosage}${specificallyTempDrugist.totalDosageUnit}&nbsp;
					</td>
				</tr>
			</table>
		</div>
	</c:forEach>
	<div class="gapBottom">&nbsp;</div>
</body>
</html>
