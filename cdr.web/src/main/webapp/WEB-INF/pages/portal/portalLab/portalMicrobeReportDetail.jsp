<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="translation.tld" prefix="ref"%>
<jsp:useBean id="Constants" class="com.founder.cdr.core.Constants" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>微生物检验报告单</title>
	<meta http-equiv="Pragma" content="no-cache" />
	<meta http-equiv="Cache-Control" content="no-cache" />
	<meta http-equiv="Expires" content="0" />
</head>
<body>
	<c:if test="${!empty labResult && labResult.withdrawFlag!=Constants.REPORT_WITHDRAW_FLAG}">
		<div class="responsability">${Constants.RESPONSABILITY_CLAIMING_CONTENT}</div>
		<table width="100%" cellpadding="2" cellspacing="1" border="0" style="border-collapse:collapse;">
			<tr height="18">
				<td class="doclabel2">标本号：</td>
				<td class="docdataitem2">${labResult.sampleNo}</td>
				<td class="doclabel2">采样时间：</td>
				<td class="labdataitem" style="font-weight:normal;"><fmt:formatDate value="${labResult.samplingTime}" type="date" pattern="yyyy-MM-dd HH:mm" /></td>
				<td class="lablabel" width="10%">样本类型：</td>
				<td class="labdataitem" width="22%">${labResult.sampleTypeName}</td>
			</tr>
		</table>
		<table width="100%" cellpadding="2" cellspacing="1"
		 style="border: solid 1px #c0ddea;border-collapse:collapse;border-bottom:0px;">
		</table>
		<table width="100%" cellpadding="2" cellspacing="1" class="table">
			<tr height="18" class="odd"><td colspan="5">&nbsp;&nbsp;&nbsp;结果：</td></tr>
			<c:if test="${labResult.reportTypeCode==Constants.LAB_REPORT_TYPE_CODE_IMMUNITY}">
				<tr height="18" class="odd"><td colspan="5">&nbsp;&nbsp;&nbsp;检测项目&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;结果&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;参考信息</td></tr>
			</c:if>
			<c:forEach items="${labItems}" var="entry" varStatus="status">
				<tr height="18" class=${status.count%2==1?'odd':'even'}>
					<c:choose>
						<c:when test="${labResult.reportTypeCode==Constants.LAB_REPORT_TYPE_CODE_IMMUNITY}">
							<td colspan="5">&nbsp;&nbsp;&nbsp;${entry.key.itemNameCn}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${entry.key.itemValue}${entry.key.itemUnit}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${entry.key.normalRefValueText}</td>
						</c:when>
						<c:when test="${labResult.reportTypeCode==Constants.LAB_REPORT_TYPE_CODE_DRUGALLERGY}">
							<td colspan="5">&nbsp;&nbsp;&nbsp;${entry.key.itemNameCn}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${entry.key.itemValue}</td>
						</c:when>
						<c:otherwise>
							<td colspan="5">&nbsp;&nbsp;&nbsp;<c:if test="${!empty entry.key.itemNameCn}">${entry.key.itemNameCn}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</c:if>${entry.key.qualitativeResults}</td>						
						</c:otherwise>
					</c:choose>
				</tr>
			</c:forEach>
			<c:forEach items="${labItems}" var="entry" varStatus="status">
				<c:if test="${fn:length(entry.value)!=0}">
					<tr height="28" style="border-top: solid 1px #B3C4D4;">
						<td colspan="5">&nbsp;&nbsp;&nbsp;${entry.key.itemNameCn}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${entry.key.qualitativeResults}</td>
					</tr>
					<tr height="28" class="tabletitle">
						<td align="center">抗菌药物</td>
						<td align="center">折点</td>
						<td align="center">KB</td>
						<td align="center">MIC</td>
						<td align="center">敏感度</td>
					</tr>
					<c:forEach items="${entry.value}" var="drug" varStatus="status">
						<tr class=${status.count%2==1?'odd':'even'} height="24">
							<td align="center">${drug.drugNameCn}</td>
							<td align="center">${drug.breakpoint}</td>
							<td align="center">${drug.kb}</td>
							<td align="center">${drug.mic}</td>
							<td align="center">${drug.sensitivity}</td> 
						</tr>
					</c:forEach>
				</c:if>
			</c:forEach>
			<tr height="18" style="border-top: solid 1px #B3C4D4;" class="odd"><td colspan="5">&nbsp;&nbsp;&nbsp;评语：<font style="color: #0000FF;font-weight: bold;font-size: 12px;heignt:20px;">${labResult.reportMemo}</font></td></tr>
			<tr><td colspan="5">&nbsp;&nbsp;&nbsp;注：${labResult.technicalNote}</td></tr>
		</table>
		<table width="100%" cellpadding="2" cellspacing="1" style="border: solid 1px #c0ddea;border-collapse:collapse;border-bottom:0px;">
			<tr>
				<td width="70%" ></td>
				<td width="10%" >报告时间：</td>
				<td width="20%" style="font-weight:normal;"><fmt:formatDate value="${labResult.reportDate}" type="date" pattern="yyyy-MM-dd HH:mm" /></td>
			</tr>
		</table>
		<div align="right">此报告仅对送检标本负责，结果供医生参考。</div>
		<c:if test="${labResult.reviewerId=='000' }"><div align="right">此报告为临时报告，仅供参考。</div></c:if>
	</c:if>
</body>
</html>