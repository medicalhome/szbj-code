<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="translation.tld" prefix="ref"%>
<%@ taglib uri="html.tld" prefix="html"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="Constants" class="com.founder.cdr.core.Constants" />
<jsp:useBean id="TimerAndInpatientDto"
	class="com.founder.cdr.dto.TimerAndInpatientDto" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Pragma" content="no-cache" />
<!-- Prevents caching at the Proxy Server -->
<meta http-equiv="Expires" content="0" />
<title>具体检查</title>
<link rel="Stylesheet" type="text/css" href="../styles/tablelist.css" />
<script type="text/javascript" src="../scripts/exam/examList.js"></script>
<style>
.examDropDownDivCSS{float: left; margin-left: 2px; margin-top: 2px; margin-right: 2px; width: 99%;}
</style>
<script type="text/javascript">
	/* var b_version=navigator.appVersion;
	var version=b_version.split(";");
	var trim_Version=version[1].replace(/[ ]/g,"");
	if(trim_Version=="MSIE6.0")
	{
		$("div[name='examDivWithControl']").css("width","63px");
		$("div[name='examdivropdown']").css("width","333px");
	}
	else
	{
		$("div[name='examDivWithControl']").css("width","71px");
		$("div[name='examdivropdown']").css("width","371px");
	} */
</script>
</head>
<body style="margin: 0; padding: 0;">
 	<div class="leftClass" 
		<c:choose>
			<c:when test="${status.count > 2}">
				style="width:${firstDivWidth};margin-top:3px; border-bottom:0;" 
			</c:when>
			<c:otherwise>
				 style="width:${firstDivWidth};border-bottom:0;"
			</c:otherwise>
		</c:choose>>
		<c:forEach items="${specificallyExamList}" var="specificallyExamList" begin="0" step="2" 
			varStatus="status">
			<c:set var="count" value="${count+1}"/>
			<div class="leftClass" style="color: blue; height:20px; margin-left: 0px; width:100%; float: none;">
				<div 
					<c:if test="${specificallyExamList.examReportSn == null}">
						style="margin-top:3px;text-decoration:none;color:black;cursor:pointer;cwidth:100%;" 
						onmouseover="this.style.color='red';"  onmouseout="this.style.color='black';"
					</c:if>
					<c:if test="${specificallyExamList.examReportSn != null}">
						style="color:blue;cursor:pointer;width:100%;" 
						onmouseover="this.style.color='red';"  onmouseout="this.style.color='blue';"
					</c:if>
						onclick="showExamDetailDialogTabs('${specificallyExamList.withdrawFlag}','${specificallyExamList.examReportSn}','${patientSn}','${specificallyExamList.itemSn}',
								'${specificallyExamList.examOrderSn}','${TimerAndInpatientDto.EXAMINATION}','${visitSn}','true','exam','70%',
								'${specificallyExamList.examinationItemName}','${inpatientDate}','special');" onmouseover="this.style.color='red';"
					>
					<div name="examDivWithControl" style="float: left;width:45%;" 
						<c:if test="${fn:length(specificallyExamList.examinationItemName)>13}">title="${specificallyExamList.examinationItemName}"</c:if>>
						<c:choose> 
							<c:when test="${status.count > 2}">
								<c:if test="${fn:length(specificallyExamList.examinationItemName)>13}">${fn:substring(specificallyExamList.examinationItemName,0,13)}...</c:if>
								<c:if test="${fn:length(specificallyExamList.examinationItemName)<=13}">${specificallyExamList.examinationItemName}&nbsp;</c:if>
							</c:when>
							<c:otherwise>
								${specificallyExamList.examinationItemName}&nbsp;
							</c:otherwise>
						</c:choose>
					</div>
					<div name="examDivWithControl" style="float: left;width:30%;">
						<c:choose> 
							<c:when test="${fn:length(specificallyExamList.execDeptName)>8}">${fn:substring(specificallyExamList.execDeptName,0,8)}...</c:when>
							<c:otherwise>${specificallyExamList.execDeptName}&nbsp;</c:otherwise>
						</c:choose>
					</div>
					<%-- <div name="examDivWithControl" style="float: left;">
						<c:choose> 
							<c:when test="${fn:length(specificallyExamList.examinationRegionName)>4}">${fn:substring(specificallyExamList.examinationItemName,0,4)}...</c:when>
							<c:otherwise>${specificallyExamList.examinationRegionName} &nbsp;</c:otherwise>
						</c:choose>
					</div>
					<div name="examDivWithControl" style="float: left;">
						<c:choose> 
							<c:when test="${fn:length(specificallyExamList.examinationMethodName)>4}">${fn:substring(specificallyExamList.examinationMethodName,0,4)}...</c:when>
							<c:otherwise>${specificallyExamList.examinationMethodName} &nbsp;</c:otherwise>
						</c:choose>
	                </div> --%>
					<div name="examDivWithControl" style="float: left; width:20%;">
						<c:choose> 
							<c:when test="${fn:length(specificallyExamList.orderStatusName)>5}">${fn:substring(specificallyExamList.orderStatusName,0,5)}...</c:when>
							<c:otherwise>${specificallyExamList.orderStatusName} &nbsp;</c:otherwise>
						</c:choose>
					</div>
					<%-- <div name="examDivWithControl" style="float: left;">
						<fmt:formatDate
							value="${specificallyExamList.examinationDate}" type="date"
							pattern="yyyy-MM-dd" />&nbsp;
					</div> --%>
					<div style="float:left;">
						<c:if test="${specificallyExamList.examReportSn != null}">
							<a href="javascript:void(0);" onclick="expandedDetail('examdivropdown_${count}');" onfocus="this.blur()"
								style="text-decoration: none">▼</a>
						</c:if>
					</div>
				</div>
			</div>
			<div name="examdivropdown" id="examdivropdown_${count}" style="float: left;display:none;border: 1px solid #0099ff;border-top: 0px;">
				<div class="examDropDownDivCSS">
					${specificallyExamList.eiImagingFinding==null?specificallyExamList.erImagingFinding:specificallyExamList.eiImagingFinding}
				</div>
				<div class="examDropDownDivCSS">
					${specificallyExamList.eiImagingConclusion==null?specificallyExamList.erImagingConclusion:specificallyExamList.eiImagingConclusion}
				</div>
				<div class="examDropDownDivCSS">
					<div style="float: left; width: 40%;">${specificallyExamList.examDeptName}&nbsp;</div>
					<div style="float: left; width: 20%;" align="left">${specificallyExamList.examinationDoctorName}&nbsp;</div>
					<div style="float: right; width: 30%;" align="left">
						<fmt:formatDate value="${specificallyExamList.examinationDate}"
							type="date" pattern="yyyy-MM-dd" />
						&nbsp;
					</div>
				</div>
			</div>
		</c:forEach>
	</div>

 	<div class="rightClass" style="width: 49%;border-bottom: 0;">
		<c:forEach items="${specificallyExamList}" var="specificallyExamList" begin="1" step="2" 
			varStatus="status">
			<c:set var="count" value="${count+1}"/>
			<div class="leftClass" style="color: blue;height:20px;margin-left: 0px;width:100%; float: none;">
				<div 
					<c:if test="${specificallyExamList.examReportSn == null}">
						style="margin-top:3px;text-decoration:none;color:black;cursor:pointer;cwidth:100%;" 
						onmouseover="this.style.color='red';"  onmouseout="this.style.color='black';"
					</c:if>
					<c:if test="${specificallyExamList.examReportSn != null}">
						style="color:blue;cursor:pointer;width:100%;" 
						onmouseover="this.style.color='red';"  onmouseout="this.style.color='blue';"
					</c:if>
						onclick="showExamDetailDialogTabs('${specificallyExamList.withdrawFlag}','${specificallyExamList.examReportSn}','${patientSn}','${specificallyExamList.itemSn}',
								'${specificallyExamList.examOrderSn}','${TimerAndInpatientDto.EXAMINATION}','${visitSn}','true','exam','70%',
								'${specificallyExamList.examinationItemName}','${inpatientDate}','special');"
					>
					<div name="examDivWithControl" style="float: left;width:45%;"
						<c:if test="${fn:length(specificallyExamList.examinationItemName)>13}">title="${specificallyExamList.examinationItemName}"</c:if>>
						<c:choose> 
							<c:when test="${fn:length(specificallyExamList.examinationItemName)>13}">${fn:substring(specificallyExamList.examinationItemName,0,13)}...</c:when>
							<c:otherwise>${specificallyExamList.examinationItemName}&nbsp;</c:otherwise>
						</c:choose>
					</div>
					<div name="examDivWithControl" style="float: left;width:30%;"
						<c:if test="${fn:length(specificallyExamList.execDeptName)>8}">title="${specificallyExamList.execDeptName}"</c:if>>
						<c:choose> 
							<c:when test="${fn:length(specificallyExamList.execDeptName)>8}">${fn:substring(specificallyExamList.execDeptName,0,8)}...</c:when>
							<c:otherwise>${specificallyExamList.execDeptName}&nbsp;</c:otherwise>
						</c:choose>
					</div>
					<%-- <div name="examDivWithControl" style="float: left;">
						<c:choose> 
							<c:when test="${fn:length(specificallyExamList.examinationRegionName)>4}">${fn:substring(specificallyExamList.examinationItemName,0,4)}...</c:when>
							<c:otherwise>${specificallyExamList.examinationRegionName} &nbsp;</c:otherwise>
						</c:choose>
					</div>
					<div name="examDivWithControl" style="float: left;">
						<c:choose> 
							<c:when test="${fn:length(specificallyExamList.examinationMethodName)>4}">${fn:substring(specificallyExamList.examinationMethodName,0,4)}...</c:when>
							<c:otherwise>${specificallyExamList.examinationMethodName} &nbsp;</c:otherwise>
						</c:choose>
	                </div> --%>
					<div name="examDivWithControl" style="float: left; width:20%;"
					 	<c:if test="${fn:length(specificallyExamList.orderStatusName)>5}">title="${specificallyExamList.orderStatusName}"</c:if>>
						<c:choose> 
							<c:when test="${fn:length(specificallyExamList.orderStatusName)>5}">${fn:substring(specificallyExamList.orderStatusName,0,5)}...</c:when>
							<c:otherwise>${specificallyExamList.orderStatusName} &nbsp;</c:otherwise>
						</c:choose>
					</div>
					<%-- <div name="examDivWithControl" style="float: left;">
						<fmt:formatDate
							value="${specificallyExamList.examinationDate}" type="date"
							pattern="yyyy-MM-dd" />&nbsp;
					</div> --%>
					<div style="float:left;">
						<c:if test="${specificallyExamList.examReportSn != null}">
							<a href="javascript:void(0);" onclick="expandedDetail('examdivropdown_${count}');" onfocus="this.blur()"
								style="text-decoration: none">▼</a>
						</c:if>
					</div>
				</div>
			</div>
			<div name="examdivropdown" id="examdivropdown_${count}"
				style="float: left; display: none; border: 1px solid #0099ff; border-top: 0px;">
				<div class="examDropDownDivCSS">
					${specificallyExamList.eiImagingFinding==null?specificallyExamList.erImagingFinding:specificallyExamList.eiImagingFinding}
				</div>
				<div class="examDropDownDivCSS">
					${specificallyExamList.eiImagingConclusion==null?specificallyExamList.erImagingConclusion:specificallyExamList.eiImagingConclusion}
				</div>
				<div class="examDropDownDivCSS">
					<div style="float: left; width: 40%;">${specificallyExamList.examDeptName}&nbsp;</div>
					<div style="float: left; width: 20%;" align="left">${specificallyExamList.examinationDoctorName}&nbsp;</div>
					<div style="float: right; width: 30%;" align="left">
						<fmt:formatDate value="${specificallyExamList.examinationDate}"
							type="date" pattern="yyyy-MM-dd" />
						&nbsp;
					</div>
				</div>
			</div>
		</c:forEach>
	</div>
		
 	<div class="gapBottom">&nbsp;</div>
 	
</body>
</html>
