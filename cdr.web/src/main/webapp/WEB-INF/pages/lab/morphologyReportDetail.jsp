<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="translation.tld" prefix="ref"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:useBean id="Constants" class="com.founder.cdr.core.Constants"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>形态学检验报告单</title>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <meta http-equiv="Pragma" content="no-cache" />
	<meta http-equiv="Cache-Control" content="no-cache" />
	<meta http-equiv="Expires" content="0" />
</head>
<body>
	<%-- <c:choose>
		<c:when test="${reportImageFlag}">
			<li class="headtitle"><a href="#tabs-2" class="lj">形态学报告</a></li>
		</c:when>
		<c:when test="${morphology.reportTypeCode=='804'}">
			<li class="headtitle"><a href="#tabs-2" class="lj">基因分型检查报告</a></li>
		</c:when>
		<c:when test="${morphology.reportTypeCode=='805'}">
			<li class="headtitle"><a href="#tabs-2" class="lj">细胞植后植活检查</a></li>
		</c:when>
		<c:when test="${morphology.reportTypeCode=='806'}">
			<li class="headtitle"><a href="#tabs-2" class="lj">骨髓活检报告</a></li>
		</c:when>
		<c:when test="${morphology.reportTypeCode=='807'}">
			<li class="headtitle"><a href="#tabs-2" class="lj">细胞形态学检查</a></li>
		</c:when>
		<c:when test="${morphology.reportTypeCode=='809'}">
			<li class="headtitle"><a href="#tabs-2" class="lj">骨髓像检查报告</a></li>
		</c:when>
		<c:when test="${morphology.reportTypeCode=='810'}">
			<li class="headtitle"><a href="#tabs-2" class="lj">儿科骨髓像检查</a></li>
		</c:when>
		<c:when test="${morphology.reportTypeCode=='812'}">
			<li class="headtitle"><a href="#tabs-2" class="lj">液基细胞学检测</a></li>
		</c:when>
		<c:when test="${morphology.reportTypeCode=='813'}">
			<li class="headtitle"><a href="#tabs-2" class="lj">HPV报告</a></li>
		</c:when>
		<c:when test="${morphology.reportTypeCode=='814'}">
			<li class="headtitle"><a href="#tabs-2" class="lj">染色体检查报告</a></li>
		</c:when>
		<c:when test="${morphology.reportTypeCode=='815'}">
			<li class="headtitle"><a href="#tabs-2" class="lj">白血病基因分型</a></li>
		</c:when>
		<c:when test="${morphology.reportTypeCode=='816'}">
			<li class="headtitle"><a href="#tabs-2" class="lj">白血病免疫分型</a></li>
		</c:when>
		<c:when test="${morphology.reportTypeCode=='819'}">
			<li class="headtitle"><a href="#tabs-2" class="lj">核医学放免-产筛</a></li>
		</c:when>
		<c:when test="${morphology.reportTypeCode=='820'}">
			<li class="headtitle"><a href="#tabs-2" class="lj">染色体核型分析</a></li>
		</c:when>
		<c:otherwise>
			<li class="headtitle"><a href="#tabs-2" class="lj">形态学报告</a></li>
		</c:otherwise>
	</c:choose> --%>
	<c:if test="${Constants.REPORT_WITHDRAW_FLAG!=labResult.withdrawFlag}">
		<div class="responsability">${Constants.RESPONSABILITY_CLAIMING_CONTENT}</div>
		<c:choose>
			<c:when test="${reportImageFlag}">
				<img width="100%" src="${reportImagePath}"/>
			</c:when>
			<c:when test="${morphology.reportTypeCode=='804'}">
				<c:if test="${empty reportImagePath}">
				    <table width="100%" cellpadding="2" cellspacing="1" border="0" style="border-collapse:collapse;">
						<tr>
							<td align="center"><h2>HLA分型报告</h2></td>
						</tr>
					</table>
					<table width="100%" cellpadding="2" cellspacing="1" style="border: solid 1px #c0ddea;border-collapse:collapse;border-bottom:0px;">
						<tr class="odd" style="border-top: solid 1px #B3C4D4;" height="28">
							<td class="lablabel" width="10%">姓名：</td>
							<td class="labdataitem" width="15%">${patient.patientName}</td>
						
							<td class="lablabel" width="10%">性别：</td>
							<td class="labdataitem" width="15%">${patient.genderName}</td>
							
							<td class="lablabel" width="10%">年龄：</td>
							<td class="labdataitem" width="15%">${age}</td>
							
							
							<td class="lablabel" width="10%">日期：</td>
							<td class="labdataitem" width="15%"><fmt:formatDate value="${morphology.receiveTime}" type="date" pattern="yyyy-MM-dd" /></td>
						</tr>
						<tr>
							<td colspan="8" class="blockHeader" height="20" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">检验结果</td>
						</tr>
						<c:forEach items="${morphologyResults}" var="morphologyResult" varStatus="status">
							<c:if test="${(status.count-1)%4==0}"><tr class="${status.count%8==1?'odd':'even'}" height="28" ></c:if>
								<td class="lablabel">${morphologyResult.itemNameCn }：</td>
								<td class="labdataitem"<c:if test="${fn:length(morphologyResults)==status.count}">colspan="${9-(status.count%4)*2}"</c:if>>${morphologyResult.itemValue }</td>
							<c:if test="${status.count%4==0}"></tr></c:if>
						</c:forEach>
						<tr>
							<td colspan="8" class="blockHeader" height="20" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">审核意见</td>
						</tr>
						<tr class="odd" height="28">
							<td colspan="8">${morphology.testResults}</td>
						</tr> 
						
						<tr height="28" style="border-top: solid 1px #B3C4D4;">
							<td class="lablabel">实验者：</td>
							<td class="labdataitem">${morphology.testerName}</td>
							
							<td class="lablabel">审核医生：</td>
							<td class="labdataitem">${morphology.reviewerName}</td>
							
							<td class="lablabel">审核日期：</td>
							<td class="labdataitem"><fmt:formatDate value="${morphology.reviewDate}" type="date" pattern="yyyy-MM-dd" /></td>
						</tr>
					</table>
				</c:if>
				<c:if test="${!empty reportImagePath}">
					<table width="100%" cellpadding="2" cellspacing="1" style="border: solid 1px #c0ddea;border-collapse:collapse;border-bottom:0px;">
						<tr><td colspan="8"><img src="${reportImagePath}"/></td></tr>
						<tr height="28" style="border-top: solid 1px #B3C4D4;">
							<td class="lablabel">实验者：</td>
							<td class="labdataitem">${morphology.testerName}</td>
							
							<td class="lablabel">审核医生：</td>
							<td class="labdataitem">${morphology.reviewerName}</td>
							
							<td class="lablabel">送检日期：</td>
							<td class="labdataitem"><fmt:formatDate value="${morphology.receiveTime}" type="date" pattern="yyyy-MM-dd" /></td>
							
							<td class="lablabel">审核日期：</td>
							<td class="labdataitem"><fmt:formatDate value="${morphology.reviewDate}" type="date" pattern="yyyy-MM-dd" /></td>
						</tr>
					</table>
				</c:if>
			</c:when>
			<c:when test="${morphology.reportTypeCode=='805'}">
				<table width="100%" cellpadding="2" cellspacing="1" border="0" style="border-collapse:collapse;">
					<tr>
						<td colspan="6" align="center"><h2>移植后植活检测报告</h2></td>
					</tr>
				</table>
				<table width="100%" cellpadding="2" cellspacing="1" style="border: solid 1px #c0ddea;border-collapse:collapse;border-bottom:0px;">
					<tr class="odd" style="border-top: solid 1px #B3C4D4;" height="28">
						<td class="lablabel" width="10%">患者姓名：</td>
						<td class="labdataitem" width="15%">${patient.patientName}</td>
						
						<td class="lablabel" width="10%">性别：</td>
						<td class="labdataitem" width="15%">${patient.genderName}</td>
						
						<td class="lablabel" width="10%">年龄：</td>
						<td class="labdataitem" width="15%">${age}</td>
						
						<td class="lablabel" width="10%">临床诊断：</td>
						<td class="labdataitem" width="15%">${morphology.diagnosisText}</td>
					</tr>
					<tr height="28">
						<td class="lablabel">病房：</td>
						<td class="labdataitem">${morphology.wardName}</td>
						
						<td class="lablabel">床号：</td>
						<td class="labdataitem">${morphology.bedNo}</td>
						
						<td class="lablabel">取材：</td>
						<td class="labdataitem" colspan="3">${morphology.sampleTypeName}</td>
					</tr>
					<tr class="odd" height="28">
						<td class="lablabel">供者姓名：</td>
						<td class="labdataitem">${morphology.supplierName}</td>
						
						<td class="lablabel">性别：</td>
						<td class="labdataitem">${morphology.supplierGender}</td>
						
						<td class="lablabel">年龄：</td>
						<td class="labdataitem"  colspan="3">${morphology.supplierAge}</td>
					</tr>
					<tr height="28">
						<td class="lablabel">送检医师：</td>
						<td class="labdataitem" colspan="3">${morphology.submittingPersonName}</td>
						
						<td class="lablabel">送检日期：</td>
						<td class="labdataitem" colspan="3"><fmt:formatDate value="${morphology.receiveTime}" type="date" pattern="yyyy-MM-dd" /></td>
					</tr>
					<tr>
						<td colspan="8" class="blockHeader" height="20" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">检测方法</td>
					</tr>
					<tr><td colspan="8" class="odd" style="padding-left: 3px;">${morphology.testMethodDesc}</td></tr>
					<tr>
						<td colspan="8" class="blockHeader" height="20" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">检测结果</td>
					</tr>
					<tr><td colspan="8" class="odd" style="padding-left: 3px;">${morphology.testResults}</td></tr>
					<tr class="odd" height="28">
						<td class="lablabel">检测医生：</td>
						<td class="labdataitem" colspan="3">${morphology.testerName}</td>
						
						<td class="lablabel">检测日期：</td>
						<td class="labdataitem" colspan="3"><fmt:formatDate value="${morphology.testDate}" type="date" pattern="yyyy-MM-dd" /></td>
					</tr>
				</table>
			</c:when>
			<c:when test="${morphology.reportTypeCode=='806'}">
				<table width="100%" cellpadding="2" cellspacing="1" border="0" style="border-collapse:collapse;">
					<tr>
						<td colspan="6" align="center"><h2>骨髓活检报告</h2></td>
					</tr>
				</table>
				<table width="100%" cellpadding="2" cellspacing="1" style="border: solid 1px #c0ddea;border-collapse:collapse;border-bottom:0px;">
					<tr class="odd" style="border-top: solid 1px #B3C4D4;" height="28">
						<td class="lablabel" width="10%">姓名：</td>
						<td class="labdataitem" width="15%">${patient.patientName}</td>
						
						<td class="lablabel" width="10%">性别：</td>
						<td class="labdataitem" width="15%">${patient.genderName}</td>
						
						<td class="lablabel" width="10%">年龄：</td>
						<td class="labdataitem" width="15%">${age}</td>
						
						<td class="lablabel" width="10%">床位号：</td>
						<td class="labdataitem" width="15%">${morphology.bedNo}</td>
					</tr>
					<tr height="28">
						<td class="lablabel">医师：</td>
						<td class="labdataitem">${morphology.submittingPersonName}</td>
						
						<td class="lablabel">取材日期：</td>
						<td class="labdataitem"><fmt:formatDate value="${morphology.samplingTime}" type="date" pattern="yyyy-MM-dd" /></td>
						
						<td class="lablabel">染色：</td>
						<td class="labdataitem" colspan="3">${morphology.testMethod}</td>
					</tr>
					<tr height="28">
						<td class="lablabel">取材部位：</td>
						<td class="labdataitem" colspan="3">${morphology.samplingPart}</td>
						
						<td class="lablabel">临床诊断：</td>
						<td class="labdataitem" colspan="3">${morphology.diagnosisText}</td>
					</tr>
					<tr>
						<td colspan="8" class="blockHeader" height="20" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">镜下所见：</td>
					</tr>
					<tr><td colspan="8" class="odd" style="padding-left: 3px;">${morphology.phenomenonPerformance}</td></tr>
					<tr>
						<td colspan="8" class="blockHeader" height="20" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">病理诊断：</td>
					</tr>
					<tr><td colspan="8" class="odd" style="padding-left: 3px;">${morphology.testResults}</td></tr>
					<tr height="28" style="border-top: solid 1px #B3C4D4;">
						<td class="lablabel">初诊医师：</td>
						<td class="labdataitem">${morphology.testerName}</td>
						
						<td class="lablabel">复核医生：</td>
						<td class="labdataitem">${morphology.reviewerName}</td>
						
						<td class="lablabel">报告日期：</td>
						<td class="labdataitem" colspan="3"><fmt:formatDate value="${morphology.reportDate}" type="date" pattern="yyyy-MM-dd" /></td>
					</tr>
				</table>
			</c:when>
			<c:when test="${morphology.reportTypeCode=='807'}">
				<table width="100%" cellpadding="2" cellspacing="1" border="0" style="border-collapse:collapse;">
					<tr>
						<td colspan="6" align="center"><h2>血液检查报告</h2></td>
					</tr>
				</table>
				<table width="100%" cellpadding="2" cellspacing="1" style="border: solid 1px #c0ddea;border-collapse:collapse;border-bottom:0px;">
					<tr class="odd" style="border-top: solid 1px #B3C4D4;" height="28">
						<td class="lablabel" width="15%">姓名：</td>
						<td class="labdataitem" width="15%">${patient.patientName}</td>
						
						<td class="lablabel" width="15%">性别：</td>
						<td class="labdataitem" width="15%">${patient.genderName}</td>
						
						<td class="lablabel" width="15%">年龄：</td>
						<td class="labdataitem" width="25%">${age}</td>
					</tr>
					<tr height="28">
						<td class="lablabel">取材日期：</td>
						<td class="labdataitem"><fmt:formatDate value="${morphology.samplingTime}" type="date" pattern="yyyy-MM-dd" /></td>
						
						<td class="lablabel" >报告日期：</td>
						<td class="labdataitem" colspan="3"><fmt:formatDate value="${morphology.reportDate}" type="date" pattern="yyyy-MM-dd" /></td>
					</tr>
					<tr class="odd" height="28">
						<td class="lablabel" >病房：</td>
						<td class="labdataitem">${morphology.wardName}</td>
						
						<td class="lablabel" >床号：</td>
						<td class="labdataitem" >${morphology.bedNo}</td>
						
						<td class="lablabel" >临床诊断：</td>
						<td class="labdataitem">${morphology.diagnosisText}</td>
					</tr>
				</table>
				<table cellpadding="2" cellspacing="1" class="table" width="100%"
							align="center">
					<tr height="28" class="tabletitle">
						<td align="center" width="30%">细胞名称</td>
						<td align="center" width="25%">结果（%）</td>
						<td align="center" width="45%">正常值（%）</td>
					</tr>
					<c:set var="count" value="1"/>
					<c:forEach items="${morphologyResults}" var="morphologyResult"
						varStatus="status">
						<c:if test="${morphologyResult.itemCode>=1&&morphologyResult.itemCode<=10}">
						<tr height="24" class=${count%2==1?'odd':'even'}>
							<td align="center">${morphologyResult.itemNameCn}</td>
							<td align="center">${morphologyResult.itemValue}</td>
							<td align="center"><c:if test="${!empty morphologyResult.lowValue||!empty morphologyResult.highValue}">${morphologyResult.lowValue}-${morphologyResult.highValue}</c:if></td>
						</tr>
						<c:set var="count" value="${count+1}"/>
						</c:if>
					</c:forEach>
				</table>
				<table width="100%" cellpadding="2" cellspacing="1" style="border: solid 1px #c0ddea;border-collapse:collapse;border-bottom:0px;">
					<c:set var="count" value="1"/>
					<c:forEach items="${morphologyResults}" var="morphologyResult"
						varStatus="status">
						<c:if test="${morphologyResult.itemCode>=11&&morphologyResult.itemCode<=13}">
							<tr class=${count%2==1?'odd':'even'} height="28" >
								<td class="lablabel" width="25%">${morphologyResult.itemNameCn}：</td>
								<td class="labdataitem" width="75%">${morphologyResult.itemValue}</td>
							</tr>
							<c:set var="count" value="${count+1 }"/>
						</c:if>
					</c:forEach>
					<tr class="even" height="28" >
						<td class="labdataitem" colspan="2">
						<c:forEach items="${morphologyResults}" var="morphologyResult"
						varStatus="status">
						<c:if test="${morphologyResult.itemCode==14}">
							${morphologyResult.itemNameCn}${morphologyResult.itemValue}/
						</c:if>
						<c:if test="${morphologyResult.itemCode==15}">
							${morphologyResult.itemNameCn}${morphologyResult.itemValue}
						</c:if>
						</c:forEach>
						</td>
					</tr>
				</table>
				<table width="100%" cellpadding="2" cellspacing="1" style="border: solid 1px #c0ddea;border-collapse:collapse;border-bottom:0px;">	
					<tr height="18" >
						<td colspan="6" style="padding-left: 5px;">备注：</td>
					</tr>
					<tr height="18" >
						<td colspan="6" style="padding-left: 5px;">${morphology.reportMemo}</td>
					</tr>
					<tr height="28" >
						<td class="lablabel" width="15%">检验医生：</td>
						<td class="labdataitem" width="15%">${morphology.testerName}</td>
						
						<td class="lablabel" width="15%">审核医生：</td>
						<td class="labdataitem" width="15%">${morphology.reviewerName}</td>
						
						<td class="lablabel" width="15%">报告日期：</td>
						<td class="labdataitem" width="25%"><fmt:formatDate value="${morphology.reportDate}" type="date" pattern="yyyy-MM-dd" /></td>
					</tr>
				</table>
			</c:when>
			<c:when test="${morphology.reportTypeCode=='809'}">
				<table width="100%" cellpadding="2" cellspacing="1" style="border: solid 1px #c0ddea;border-collapse:collapse;border-bottom:0px;">
					<tr>
						<td colspan="6" align="center"><h2>骨髓像图文报告</h2></td>
					</tr>
				</table>
				<table width="100%" cellpadding="2" cellspacing="1" border="0" style="border-collapse:collapse;">
					<tr class="odd" style="border-top: solid 1px #B3C4D4;" height="28">
						<td class="lablabel" width="10%">姓名：</td>
						<td class="labdataitem" width="15%">${patient.patientName}</td>
						
						<td class="lablabel" width="10%">性别：</td>
						<td class="labdataitem" width="15%">${patient.genderName}</td>
						
						<td class="lablabel" width="10%">年龄：</td>
						<td class="labdataitem" width="15%">${age}</td>
						
						<td class="lablabel" width="10%">临床诊断：</td>
						<td class="labdataitem" width="15%">${morphology.diagnosisText}</td>
					</tr>
					<tr height="28">
						<td class="lablabel">病室：</td>
						<td class="labdataitem">${morphology.wardName}</td>
						
						<td class="lablabel">医师：</td>
						<td class="labdataitem">${morphology.submittingPersonName}</td>
						
						<td class="lablabel">化验号：</td>
						<td class="labdataitem" colspan="3">${morphology.reportNo}</td>
					</tr>
					<tr class="odd" height="28" style="border-bottom: solid 2px #B3C4D4;">
						<td class="lablabel">取材部位：</td>
						<td class="labdataitem">${morphology.samplingPart}</td>
						
						<td class="lablabel">取材日期：</td>
						<td class="labdataitem"><fmt:formatDate value="${morphology.samplingTime}" type="date" pattern="yyyy-MM-dd" /></td>
						
						<td class="lablabel">染色方法：</td>
						<td class="labdataitem" colspan="3">${morphology.testMethod}</td>
					</tr>
					<c:set var="count" value="1"/>
					<c:forEach items="${morphologyResults}" var="morphologyResult" varStatus="status">
						<c:if test="${morphologyResult.itemCode>=89}">
							<c:if test="${(count-1)%4==0}"><tr class="${count%8==1?'odd':''}" height="28" ></c:if>									
								<td class="lablabel">${morphologyResult.itemNameCn }：</td>
								<td class="labdataitem">${morphologyResult.itemValue }</td>
							<c:if test="${count%4==0}"></tr></c:if>
							<c:set var="count" value="${count+1}"/>									
						</c:if>
					</c:forEach>
					<c:forEach items="${morphologyResults}" var="morphologyResult" varStatus="status">
						<c:if test="${morphologyResult.itemCode<89}">
							<c:if test="${(count-1)%4==0}"><tr class="${count%8==1?'odd':''}" height="28" ></c:if>									
								<td class="lablabel">${morphologyResult.itemNameCn }：</td>
								<td class="labdataitem"<c:if test="${fn:length(morphologyResults)==count}">colspan="${9-(count%4)*2}"</c:if>>${morphologyResult.itemValue }</td>
							<c:if test="${count%4==0}"></tr></c:if>
							<c:set var="count" value="${count+1}"/>									
						</c:if>
					</c:forEach>
					<tr class="odd" height="28" style="border-top: solid 2px #B3C4D4;">
						<td class="lablabel">形态描述：</td>
						<td class="labdataitem" colspan="7">${morphology.phenomenonPerformance}</td>
					</tr>
					<tr class="odd" height="28" >
						<td class="lablabel">诊断：</td>
						<td class="labdataitem" colspan="7">${morphology.testResults}</td>
					</tr>
					<tr height="28" style="border-top: solid 2px #B3C4D4;">
						<td class="lablabel">检验医师：</td>
						<td class="labdataitem">${morphology.testerName}</td>
						
						<td class="lablabel">复核医师：</td>
						<td class="labdataitem" colspan="3">${morphology.reviewerName}</td>
						
						<td class="lablabel">报告日期：</td>
						<td class="labdataitem"><fmt:formatDate value="${morphology.reportDate}" type="date" pattern="yyyy-MM-dd" /></td>
					</tr>
				</table>
			</c:when>
			<c:when test="${morphology.reportTypeCode=='810'}">
				<table width="100%" cellpadding="2" cellspacing="1" style="border: solid 1px #c0ddea;border-collapse:collapse;border-bottom:0px;">
					<tr>
						<td colspan="6" align="center"><h2>儿科骨髓像图文报告</h2></td>
					</tr>
					<tr height="28">
						<td width="60%"></td>
						<td class="lablabel" width="15%">检验号：</td>
						<td class="labdataitem" width="25%">${morphology.reportNo}</td>
					</tr>
				</table>
				<table width="100%" cellpadding="2" cellspacing="1" border="0" style="border-collapse:collapse;">
					<tr class="odd" style="border-top: solid 1px #B3C4D4;" height="28">
						<td class="lablabel" width="15%">姓名：</td>
						<td class="labdataitem" width="15%">${patient.patientName}</td>
						
						<td class="lablabel" width="15%">性别：</td>
						<td class="labdataitem" width="15%">${patient.genderName}</td>
						
						<td class="lablabel" width="15%">年龄：</td>
						<td class="labdataitem" width="25%">${age}</td>
					</tr>
					<tr height="28">
						<td class="lablabel">医师：</td>
						<td class="labdataitem">${morphology.submittingPersonName}</td>
						
						<td class="lablabel">取材部位：</td>
						<td class="labdataitem">${morphology.samplingPart}</td>
						
						<td class="lablabel">采样日期：</td>
						<td class="labdataitem"><fmt:formatDate value="${morphology.samplingTime}" type="date" pattern="yyyy-MM-dd" /></td>
					</tr>
					<tr class="odd" height="28" style="border-bottom: solid 2px #B3C4D4;">
						<td class="lablabel">临床诊断：</td>
						<td class="labdataitem" colspan="5">${morphology.diagnosisText}</td>
					</tr>
					<c:set var="count" value="1"/>
					<c:forEach items="${morphologyResults}" var="morphologyResult" varStatus="status">
						<c:if test="${(count-1)%3==0}"><tr class="${count%6==1?'odd':''}" height="28" ></c:if>									
							<td class="lablabel">${morphologyResult.itemNameCn }：</td>
							<td class="labdataitem"<c:if test="${fn:length(morphologyResults)==count}">colspan="${7-(count%3)*2}"</c:if>>${morphologyResult.itemValue }</td>
						<c:if test="${count%3==0}"></tr></c:if>
						<c:set var="count" value="${count+1}"/>									
					</c:forEach>
					<tr>
						<td colspan="8" class="blockHeader" height="20" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">特征：</td>
					</tr>
					<tr><td colspan="8" class="odd" style="padding-left: 3px;">${morphology.phenomenonPerformance}</td></tr>
					<tr>
						<td colspan="8" class="blockHeader" height="20" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">检验诊断：</td>
					</tr>
					<tr><td colspan="8" class="odd" style="padding-left: 3px;">${morphology.testResults}</td></tr>
					<tr height="28" style="border-top: solid 1px #B3C4D4;">
						<td class="lablabel">检查医生：</td>
						<td class="labdataitem">${morphology.testerName}</td>
						
						<td class="lablabel">审核医生：</td>
						<td class="labdataitem">${morphology.reviewerName}</td>
						
						<td class="lablabel">报告日期：</td>
						<td class="labdataitem"><fmt:formatDate value="${morphology.reportDate}" type="date" pattern="yyyy-MM-dd" /></td>
					</tr>
				</table>
			</c:when>
			<c:when test="${morphology.reportTypeCode=='812'}">
				<table width="100%" cellpadding="2" cellspacing="1" style="border: solid 1px #c0ddea;border-collapse:collapse;border-bottom:0px;">
					<tr>
						<td colspan="6" align="center"><h2>液基细胞学检测报告</h2></td>
					</tr>
					<tr height="28">
						<td class="lablabel" width="15%">实验室编号：</td>
						<td class="labdataitem" width="15%">${morphology.laboratoryNo}</td>
						
						<td class="lablabel" width="45%">编号：</td>
						<td class="labdataitem" width="25%">${morphology.reportNo}</td>
					</tr>
				</table>
				<table width="100%" cellpadding="2" cellspacing="1" style="border: solid 1px #c0ddea;border-collapse:collapse;border-bottom:0px;">
					<tr class="odd" style="border-top: solid 1px #B3C4D4;" height="28">
						<td class="lablabel" width="15%">姓名：</td>
						<td class="labdataitem" width="15%">${patient.patientName}</td>
						
						<td class="lablabel" width="15%">年龄：</td>
						<td class="labdataitem" width="15%">${age}</td>
						
						<td class="lablabel" width="15%">电话：</td>
						<td class="labdataitem" width="25%">${patient.mobile}</td>
					</tr>
					<tr height="28">
						<td class="lablabel">本次月经时间：</td>
						<td class="labdataitem"><fmt:formatDate value="${morphology.lastMenstrualTime}" type="date" pattern="yyyy-MM-dd" /></td>
						
						<td class="lablabel">绝经：</td>
						<td class="labdataitem" colspan="3">${morphology.menopause}</td>
					</tr>
					<tr class="odd" height="28" style="border-bottom: solid 1px #B3C4D4;">
						<td class="lablabel">取材日期：</td>
						<td class="labdataitem"><fmt:formatDate value="${morphology.samplingTime}" type="date" pattern="yyyy-MM-dd" /></td>
						
						<td class="lablabel">送检医师：</td>
						<td class="labdataitem" colspan="3">${morphology.submittingPersonName}</td>
					</tr>
					<c:forEach items="${morphologyResults}" var="morphologyResult" varStatus="status">
						<tr class="${status.count%2==1?'odd':'even' }" height="28">
							<td class="lablabel">${morphologyResult.itemNameCn}：</td>
							<td class="labdataitem" colspan="5">${morphologyResult.itemValue}</td>
						</tr>
					</c:forEach>
					<tr>
						<td colspan="6" class="blockHeader" height="20" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">诊断</td>
					</tr>
					<tr><td colspan="6" class="odd" style="padding-left: 3px;">${morphology.testResults }</td></tr>
					<tr>
						<td colspan="6" class="blockHeader" height="20" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">诊断细分</td>
					</tr>
					<tr><td colspan="6" class="odd" style="padding-left: 3px;">${morphology.testResultsDetail }</td></tr>
					<tr>
						<td colspan="6" class="blockHeader" height="20" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">补充意见1</td>
					</tr>
					<tr><td colspan="6" class="odd" style="padding-left: 3px;">${morphology.memo}</td></tr>
					<tr>
						<td colspan="6" class="blockHeader" height="20" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">补充意见2</td>
					</tr>
					<tr><td colspan="6" class="odd" style="padding-left: 3px;">${morphology.reportMemo}</td></tr>
					<tr height="28" style="border-top: solid 1px #B3C4D4;">
						<td class="lablabel">诊断医生：</td>
						<td class="labdataitem">${morphology.testerName}</td>
						
						<td class="lablabel">审核医生：</td>
						<td class="labdataitem">${morphology.reviewerName}</td>
						
						<td class="lablabel">报告日期：</td>
						<td class="labdataitem"><fmt:formatDate value="${morphology.reportDate}" type="date" pattern="yyyy-MM-dd" /></td>
					</tr>
				</table>
			</c:when>
			<c:when test="${morphology.reportTypeCode=='813'}">
				<table width="100%" cellpadding="2" cellspacing="1" style="border: solid 1px #c0ddea;border-collapse:collapse;border-bottom:0px;">
					<tr>
						<td colspan="6" align="center"><h2>HPV DNA检测报告</h2></td>
					</tr>
					<tr height="28">
						<td class="lablabel" width="45%">实验室编号：</td>
						<td class="labdataitem" width="15%">${morphology.laboratoryNo}</td>
						
						<td class="lablabel" width="15%">编号：</td>
						<td class="labdataitem" width="25%">${morphology.reportNo}</td>
					</tr>
				</table>
				<table width="100%" cellpadding="2" cellspacing="1" style="border: solid 1px #c0ddea;border-collapse:collapse;border-bottom:0px;">
					<tr class="odd" style="border-top: solid 1px #B3C4D4;" height="28">
						<td class="lablabel" width="15%">姓名：</td>
						<td class="labdataitem" width="15%">${patient.patientName}</td>
						
						<td class="lablabel" width="15%">年龄：</td>
						<td class="labdataitem" width="15%">${age}</td>
						
						<td class="lablabel" width="15%">电话：</td>
						<td class="labdataitem" width="25%">${patient.mobile}</td>
					</tr>
					<tr class="odd" height="28">
						<td class="lablabel">送检标本：</td>
						<td class="labdataitem">${morphology.samplingPart}</td>
						
						<td class="lablabel">送检医师：</td>
						<td class="labdataitem">${morphology.submittingPersonName}</td>
						
						<td class="lablabel">送检日期：</td>
						<td class="labdataitem"><fmt:formatDate value="${morphology.receiveTime}" type="date" pattern="yyyy-MM-dd" /></td>
					</tr>
					<c:forEach items="${morphologyResults}" var="morphologyResult" varStatus="status">
						<tr class="odd" height="28" style="border-top: solid 1px #B3C4D4;">
							<td class="lablabel">检测值：</td>
							<td class="labdataitem" colspan="5">${morphologyResult.itemValue}</td>
						</tr>
						<tr height="28">
							<td class="lablabel">正常参考值：</td>
							<td class="labdataitem" colspan="5">${morphologyResult.normalRefValueText}</td>
						</tr>
						<tr class="odd" height="28">
							<td class="lablabel">定性结果：</td>
							<td class="labdataitem" colspan="5">${compositeItem.warnFlagName}</td>
						</tr>  
					</c:forEach>
					<tr height="28" style="border-top: solid 1px #B3C4D4;">
						<td class="lablabel">诊断医生：</td>
						<td class="labdataitem">${morphology.reporterName}</td>
						
						<td class="lablabel">审核人：</td>
						<td class="labdataitem">${morphology.reviewerName}</td>
						
						<td class="lablabel">报告日期：</td>
						<td class="labdataitem"><fmt:formatDate value="${morphology.reportDate}" type="date" pattern="yyyy-MM-dd" /></td>
					</tr>
					<tr>
						<td colspan="6" style="padding-left: 3px;">注：</td>
					</tr>
					<tr>
						<td colspan="6" style="padding-left: 3px;">${morphology.reportMemo}</td>
					</tr>
						<!-- <td>1.高危型包含16、18、31、33、35、39、45、51、52、56、58、59、68共13种高危亚型。</td>
						<td>本结果仅对本次标本负责。请妥善保管此结果单，下次复诊时随病历本一起携带随诊。</td>
						<td>2.高危型乳头瘤病毒（HPV）持续感染是引起子宫癌的必要条件。</td>
						<td>3.HPV的人群感染率大20-30%，预防及早期发现HPV感染就是预防宫颈癌。</td>
						<td>4.感染了HPV病毒并不代表得了癌症，应及时联系医生进行进一步的检查。</td> -->
				</table>
			</c:when>
			<c:when test="${morphology.reportTypeCode=='814'}">
				<table width="100%" cellpadding="2" cellspacing="1" style="border: solid 1px #c0ddea;border-collapse:collapse;border-bottom:0px;">
					<tr>
						<td colspan="6" align="center"><h2>染色体检查报告</h2></td>
					</tr>
					<tr height="28">
						<td class="lablabel" width="75%">标本号：</td>
						<td class="labdataitem" width="25%">${morphology.sampleNo}</td>
					</tr>
				</table>
				<table width="100%" cellpadding="2" cellspacing="1" border="0" style="border-collapse:collapse;">
					<tr class="odd" style="border-top: solid 1px #B3C4D4;" height="28">
						<td class="lablabel" width="15%">姓名：</td>
						<td class="labdataitem" width="15%">${patient.patientName}</td>
						
						<td class="lablabel" width="15%">性别：</td>
						<td class="labdataitem" width="15%">${patient.genderName}</td>
						
						<td class="lablabel" width="15%">年龄：</td>
						<td class="labdataitem" width="25%">${age}</td>
					</tr>
					<tr height="28">
						<td class="lablabel">送检日期：</td>
						<td class="labdataitem"><fmt:formatDate value="${morphology.receiveTime}" type="date" pattern="yyyy-MM-dd" /></td>
						
						<td class="lablabel">初步诊断：</td>
						<td class="labdataitem" colspan="3">${morphology.diagnosisText}</td>
					</tr>
					<tr class="odd" height="28">
						<td class="lablabel">送检标本：</td>
						<td class="labdataitem">${morphology.sampleTypeName}</td>
						
						<td class="lablabel">制备方法：</td>
						<td class="labdataitem">${morphology.preparationMethod}</td>
						
						<td class="lablabel">检测技术：</td>
						<td class="labdataitem">${morphology.testMethod}</td>
					</tr>
					<tr>
						<td colspan="6" class="blockHeader" height="20" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">分析结果</td>
					</tr>
					<tr><td colspan="6" class="odd" style="padding-left: 3px;">${morphology.testResults }</td></tr>
					<tr height="28" style="border-top: solid 1px #B3C4D4;">
						<td class="lablabel">审核医生：</td>
						<td class="labdataitem">${morphology.reviewerName }</td>
						
						<td class="lablabel">报告人：</td>
						<td class="labdataitem">${morphology.reporterName }</td>
						
						<td class="lablabel">报告日期：</td>
						<td class="labdataitem"><fmt:formatDate value="${morphology.reportDate}" type="date" pattern="yyyy-MM-dd" /></td>
					</tr>
				</table>
			</c:when>
			<c:when test="${morphology.reportTypeCode=='815'}">
				<table width="100%" cellpadding="2" cellspacing="1" style="border: solid 1px #c0ddea;border-collapse:collapse;border-bottom:0px;"">
					<tr>
						<td colspan="6" align="center"><h2>融合基因RT-PCR检查报告</h2></td>
					</tr>
				</table>
				<table width="100%" cellpadding="2" cellspacing="1" border="0" style="border-collapse:collapse;">
					<tr class="odd" style="border-top: solid 1px #B3C4D4;" height="28">
						<td class="lablabel" width="10%">姓名：</td>
						<td class="labdataitem" width="15%">${patient.patientName}</td>
						
						<td class="lablabel" width="10%">性别：</td>
						<td class="labdataitem" width="15%">${patient.genderName}</td>
						
						<td class="lablabel" width="10%">年龄：</td>
						<td class="labdataitem" width="15%">${age}</td>
						
						<td class="lablabel" width="10%">取材：</td>
						<td class="labdataitem" width="15%">${morphology.samplingPart}</td>
					</tr>
					<tr height="28">
						<td class="lablabel">房号：</td>
						<td class="labdataitem" colspan="3">${morphology.wardName}</td>
						
						<td class="lablabel">床号：</td>
						<td class="labdataitem" colspan="3">${morphology.bedNo}</td>
					</tr>
					<tr class="odd" height="28">
						<td class="lablabel">接收日期：</td>
						<td class="labdataitem" colspan="3"><fmt:formatDate value="${morphology.receiveTime}" type="date" pattern="yyyy-MM-dd" /></td>
						
						<td class="lablabel">编号：</td>
						<td class="labdataitem" colspan="3">${morphology.reportNo}</td>
					</tr>
					<tr height="28">
						<td class="lablabel">临床诊断：</td>
						<td class="labdataitem" colspan="3">${morphology.diagnosisText}</td>
						
						<td class="lablabel">检查项目：</td>
						<td class="labdataitem" colspan="3">${morphology.itemName}</td>
					</tr>
					<tr>
						<td colspan="8" class="blockHeader" height="20" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">结果</td>
					</tr>
					<tr><td colspan="8" class="odd" style="padding-left: 3px;">${morphology.testResults}</td></tr>
				</table>
				<table width="100%" cellpadding="2" cellspacing="1" style="border: solid 1px #c0ddea;border-collapse:collapse;border-bottom:0px;">
					<tr height="28">
						<td class="lablabel" width="10%">报告时间：</td>
						<td class="labdataitem" width="15%" ><fmt:formatDate value="${morphology.reportDate}" type="date" pattern="yyyy-MM-dd" /></td>
						
						<td class="lablabel" width="10%">报告者：</td>
						<td class="labdataitem" width="15%">${morphology.reporterName}</td>
						
						<td class="lablabel" width="10%">审核者：</td>
						<td class="labdataitem" width="40%">${morphology.reviewerName}</td>
					</tr>
				</table>
			</c:when>
			<c:when test="${morphology.reportTypeCode=='816'}">
				<table width="100%" cellpadding="2" cellspacing="1" style="border: solid 1px #c0ddea;border-collapse:collapse;border-bottom:0px;">
					<tr>
						<td colspan="6" align="center"><h2>免疫分型报告</h2></td>
					</tr>
				</table>
				<table width="100%" cellpadding="2" cellspacing="1" border="0" style="border-collapse:collapse;">
					<tr class="odd" style="border-top: solid 1px #B3C4D4;" height="28">
						<td class="lablabel" width="10%">姓名：</td>
						<td class="labdataitem" width="15%">${patient.patientName}</td>
						
						<td class="lablabel" width="10%">性别：</td>
						<td class="labdataitem" width="15%">${patient.genderName}</td>
						
						<td class="lablabel" width="10%">年龄：</td>
						<td class="labdataitem" width="15%">${age}</td>
						
						<td class="lablabel" width="10%">编号：</td>
						<td class="labdataitem" width="15%">${morphology.reportNo}</td>
					</tr>
					<tr height="28">
						<td class="lablabel">标本：</td>
						<td class="labdataitem" colspan="3">${morphology.sampleTypeName}</td>
						
						<td class="lablabel">诊断：</td>
						<td class="labdataitem" colspan="3">${morphology.diagnosisText}</td>
						
					</tr>
					<tr>
						<td colspan="8" class="blockHeader" height="20" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">结论</td>
					</tr>
					<tr><td colspan="8" class="odd" style="padding-left: 3px;">${morphology.testResults }</td></tr>
					<tr height="28" style="border-top: solid 1px #B3C4D4;">
						<td class="lablabel">报告者：</td>
						<td class="labdataitem">${morphology.reporterName}</td>
						
						<td class="lablabel">审核者：</td>
						<td class="labdataitem">${morphology.reviewerName}</td>
						
						<td class="lablabel">送检日期：</td>
						<td class="labdataitem"><fmt:formatDate value="${morphology.receiveTime}" type="date" pattern="yyyy-MM-dd" /></td>
						
						<td class="lablabel">报告日期：</td>
						<td class="labdataitem"><fmt:formatDate value="${morphology.reportDate}" type="date" pattern="yyyy-MM-dd" /></td>
					</tr>
				</table>
			</c:when>
			<c:when test="${morphology.reportTypeCode=='819'}">
				<table width="100%" cellpadding="2" cellspacing="1" border="0" style="border-collapse:collapse;">
					<tr>
						<td colspan="6" align="center"><h2>血清学产前筛查</h2></td>
					</tr>
				</table>
				<table width="100%" cellpadding="2" cellspacing="1" style="border: solid 1px #c0ddea;border-collapse:collapse;border-bottom:0px;">
					<tr class="odd" style="border-top: solid 1px #B3C4D4;" height="28">
						<td class="lablabel" width="15%">姓名：</td>
						<td class="labdataitem" width="15%">${patient.patientName}</td>
						
						<td class="lablabel" width="15%">出生日期：</td>
						<td class="labdataitem" width="15%"><fmt:formatDate value="${patient.birthDate}" type="date" pattern="yyyy-MM-dd" /></td>
						
						<td class="lablabel" width="15%">预产年龄：</td>
						<td class="labdataitem" width="25%">${morphology.expectedAge}</td>
					</tr>
					<tr height="28">
						<td class="lablabel" width="15%">胎儿数：</td>
						<td class="labdataitem" width="15%">${morphology.fetalNumber}</td>
						
						<td class="lablabel" width="15%">末次月经：</td>
						<td class="labdataitem" width="15%"><fmt:formatDate value="${morphology.lastMenstrualTime}" type="date" pattern="yyyy-MM-dd" /></td>
						
						<td class="lablabel" width="15%">孕周计算基于：</td>
						<td class="labdataitem" width="25%">${morphology.gestationalWeekCalcMethod}</td>
					</tr>
					<tr>
						<td colspan="8" class="blockHeader" height="20" align="left" style="padding-left: 8px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">样本信息</td>
					</tr>
					<tr class="odd" height="28" >
						<td class="lablabel">样本编号：</td>
						<td class="labdataitem">${morphology.sampleNo}</td>
						
						<td class="lablabel">采样日期：</td>
						<td class="labdataitem" colspan="3"><fmt:formatDate value="${morphology.samplingTime}" type="date" pattern="yyyy-MM-dd" /></td>
					</tr>
					<tr height="28" >
						<c:forEach items="${morphologyResults}" var="morphologyResult" varStatus="status">
							<c:if test="${morphologyResult.itemCode>=1&&morphologyResult.itemCode<=3}">
								<td class="lablabel">${morphologyResult.itemNameCn}：</td>
								<c:choose>
									<c:when test="${morphologyResult.itemCode==3}">
										<td class="labdataitem">${fn:substring(morphologyResult.itemValue,0,4)}-${fn:substring(morphologyResult.itemValue,4,6)}-${fn:substring(morphologyResult.itemValue,6,8)}</td>
									</c:when>
									<c:otherwise>
										<td class="labdataitem">${morphologyResult.itemValue}</td>
									</c:otherwise>
								</c:choose>
							</c:if>
						</c:forEach>
					</tr>
					<tr class="odd" height="28" >
						<c:forEach items="${morphologyResults}" var="morphologyResult" varStatus="status">
							<c:if test="${morphologyResult.itemCode>=4&&morphologyResult.itemCode<=6}">
								<td class="lablabel">${morphologyResult.itemNameCn}：</td>
								<td class="labdataitem">${morphologyResult.itemValue}</td>
							</c:if>
						</c:forEach>
					</tr>
				</table>
				<table cellpadding="2" cellspacing="1" class="table" width="100%"
							align="center">
					<tr>
						<td class="blockHeader" colspan="11" height="27" align="left"
							style="padding-left: 3px; border-top: solid 1px #B3C4D4; font-weight: bold;">
							样本测试项目
						</td>
					</tr>
					<tr height="28" class="tabletitle">
						<td align="center">标记物</td>
						<td align="center">结果</td>
						<td align="center">单位</td>
						<td align="center">校正MoM</td>
					</tr>
					<c:set var="count" value="1"/>
					<c:forEach items="${morphologyResults}" var="morphologyResult"
						varStatus="status">
						<c:if test="${morphologyResult.itemCode>=7&&morphologyResult.itemCode<=9}">
						<tr height="24" class=${count%2==1?'odd':'even'}>
							<td align="center">${morphologyResult.itemNameCn}</td>
							<td align="center">${morphologyResult.itemValue}</td>
							<td align="center">${morphologyResult.itemUnit}</td>
							<td align="center">${morphologyResult.correctMom}</td>
						</tr>
						<c:set var="count" value="${count+1}"/>
						</c:if>
					</c:forEach>
				</table>
				<table width="100%" cellpadding="2" cellspacing="1" style="border: solid 1px #c0ddea;border-collapse:collapse;border-bottom:0px;">	
					<tr>
						<td colspan="4" class="blockHeader" height="20" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">风险计算项目</td>
					</tr>
					<c:forEach items="${morphologyResults}" var="morphologyResult"
						varStatus="status">
						<c:if test="${morphologyResult.itemCode>=10&&morphologyResult.itemCode<=12}">
							<tr class="odd" height="28" >
								<td class="lablabel">筛查项目：</td>
								<td class="labdataitem" colspan="3">${morphologyResult.itemNameCn}</td>
							</tr>
							<tr height="28" >
								<td class="lablabel">筛查结果：</td>
								<td class="labdataitem" colspan="3">${morphologyResult.itemValue}</td>
							</tr>
							<tr class="odd" height="28" >
								<td class="lablabel" width="15%">风险值：</td>
								<td class="labdataitem" width="25%">${morphologyResult.riskValue}</td>
								
								<td class="lablabel" width="10%">年龄风险：</td>
								<td class="labdataitem" width="55%">${morphologyResult.ageRiskValue}</td>
							</tr>
							<tr height="28" style="border-bottom: solid 1px #B3C4D4;">
								<td class="lablabel">风险截断值：</td>
								<td class="labdataitem" colspan="3">${morphologyResult.normalRefValueText}</td>
							</tr>
						</c:if>
					</c:forEach>
				</table>
				<table width="100%" cellpadding="2" cellspacing="1" style="border: solid 1px #c0ddea;border-collapse:collapse;border-bottom:0px;">
					<tr height="18" >
						<td colspan="6" style="padding-left: 5px;">注意：</td>
					</tr>
					<tr height="18" >
						<td colspan="6" style="padding-left: 5px;">${morphology.reportMemo}</td>
					</tr>
					<tr height="28" >
						<td class="lablabel" width="15%">检验者：</td>
						<td class="labdataitem" width="15%">${morphology.testerName}</td>
						
						<td class="lablabel" width="15%">审核者：</td>
						<td class="labdataitem" width="15%">${morphology.reviewerName}</td>
						
						<td class="lablabel" width="15%">报告日期：</td>
						<td class="labdataitem" width="25%"><fmt:formatDate value="${morphology.reportDate}" type="date" pattern="yyyy-MM-dd" /></td>
					</tr>
				</table>
			</c:when>
			<c:when test="${morphology.reportTypeCode=='820'}">
				<table width="100%" cellpadding="2" cellspacing="1" border="0" style="border-collapse:collapse;">
					<tr>
						<td colspan="6" align="center"><h2>染色体核型分析报告单</h2></td>
					</tr>
				</table>
				<table width="100%" cellpadding="2" cellspacing="1" style="border: solid 1px #c0ddea;border-collapse:collapse;border-bottom:0px;">
					<tr class="odd" style="border-top: solid 1px #B3C4D4;" height="28">
						<td class="lablabel" width="15%">姓名：</td>
						<td class="labdataitem" width="15%">${patient.patientName}</td>
						
						<td class="lablabel" width="15%">性别：</td>
						<td class="labdataitem" width="15%">${patient.genderName}</td>
						
						<td class="lablabel" width="15%">年龄：</td>
						<td class="labdataitem" width="25%">${age}</td>
					</tr>
					<tr height="28">
						<td class="lablabel">样本来源：</td>
						<td class="labdataitem">${morphology.sampleTypeName}</td>
						
						<td class="lablabel">接收日期：</td>
						<td class="labdataitem"><fmt:formatDate value="${morphology.receiveTime}" type="date" pattern="yyyy-MM-dd" /></td>
						
						<td class="lablabel">编号：</td>
						<td class="labdataitem">${morphology.reportNo}</td>
					</tr>
					<tr class="odd" height="28">
						<td class="lablabel">穿刺特征：</td>
						<td class="labdataitem" colspan="3">${morphology.phenomenonPerformance}</td>
						
						<td class="lablabel">联系电话：</td>
						<td class="labdataitem">${patient.mobile}</td>
					</tr>
					<tr>
						<td colspan="6" class="blockHeader" height="20" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">结果报告：</td>
					</tr>
					<tr><td colspan="6" class="odd" style="padding-left: 3px;">${morphology.testResults}</td></tr>
					<tr><td colspan="6" class="odd" style="padding-left: 3px;">备注：</td></tr>
					<tr><td colspan="6" class="odd" style="padding-left: 3px;">${morphology.reportMemo}</td></tr>
					<!-- <tr><td>1.常规G显带法不能诊断染色体微小缺失、重复等结构改变，需结合临床。</td></tr>
					<tr><td>2.核型分析结果不能替代其他产前检查项目，孕妇需遵医嘱定期进行产检。</td></tr> -->
					<tr height="28" style="border-top: solid 1px #B3C4D4;">
						<td class="lablabel">报告人：</td>
						<td class="labdataitem">${morphology.testerName}</td>
						
						<td class="lablabel">报告审核人：</td>
						<td class="labdataitem">${morphology.reviewerName}</td>
						
						<td class="lablabel">报告日期：</td>
						<td class="labdataitem"><fmt:formatDate value="${morphology.reviewDate}" type="date" pattern="yyyy-MM-dd" /></td>
					</tr>
				</table>
			</c:when>
			<c:otherwise>
				<table cellpadding="2" cellspacing="1" class="table" width="100%" align="center">
					<tr>
						<td colspan="11" align="center" class="odd" height="24" style="font-size:20px;font-weight:bold;">没有相关形态学报告信息！</td>
					</tr>
				</table>
			</c:otherwise>
		</c:choose>
	</c:if>
</body>
</html>