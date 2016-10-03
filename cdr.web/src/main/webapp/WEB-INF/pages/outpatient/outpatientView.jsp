<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="translation.tld" prefix="ref"%>
<%@ taglib uri="html.tld" prefix="html"%>
<jsp:useBean id="Constants" class="com.yly.cdr.core.Constants" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<meta http-equiv="Pragma" content="no-cache" />
	<meta http-equiv="Cache-Control" content="no-cache" />
	<meta http-equiv="Expires" content="0" />
	<title>门急诊视图</title>
	<link type="text/css" rel="Stylesheet" href="../styles/tablelist.css"
		charset="UTF8" />
	<link type="text/css" rel="Stylesheet" href="../styles/tabs-menuPart.css"
		charset="UTF8" />		
	<script type="text/javascript" src="../scripts/exam/examList.js"></script>
	<script type="text/javascript" src="../scripts/lab/labList.js"></script>
	<script type="text/javascript"
		src="../scripts/order/nonDrugOrderList.js"></script>
	<script type="text/javascript" src="../scripts/doc/docList.js"></script>
	<script type="text/javascript"
		src="../scripts/visit/outpatientViewPart.js"></script>
	<script type="text/javascript"
		src="../scripts/timerInpatient/timerInpatient.js"></script>
	<c:if test="${visitDetail.visitSn==null}">
		<script type="text/javascript">
				$(function(){alert("该患者不存在门诊就诊记录！");});	
		</script>
	</c:if>
</head>
<body style="margin: 0; padding: 0;">
	<div id="outpatientView" style="padding: 0px;">
		<table class="ctab" cellpadding="2" cellspacing="1" width="100%" id="mzfirst"
			style="border: solid 1px #D8D9DB; background-color: #F0F8FE;">
			<tr style="background-color: #F0F8FE;">
				<td class="allerRightout" style="text-align: right; border: 0px;">&nbsp;</td>
				<!-- 
					$Author:jin_peng
					$Date : 2012/11/27 14:04
					$[BUG]0011883 MODIFY BEGIN
				 -->
				<!-- 
					$Author:jin_peng
					$Date : 2012/11/1 10:30
					$[BUG]0010908 ADD BEGIN
				 -->
				<!-- $[BUG]0010908 ADD END -->
				<td>
					<div id="patientAlerts" class="cell reAller cellEllipsis"
						style="text-align: center; float: left;">
						<img src="../images/pic_gm.gif"
							style="padding-right: 6px; vertical-align: middle;" /><font
							color="#DB2C33"><b id="aller"
							onmouseover="createTip($('#aller').html())"
							onmouseout="removeTip()"></b></font>
					</div>

					<div id="patientAlertsOut" style="text-align: center; float: left;">
						&nbsp;</div>
						
					<c:if test="${Constants.TRUE_ORG_CODE == Constants.DISABLE_ORG_CODE}">
						<div class="allerRightout cell" align="right"
						style="text-align: right; border: 0px; width: 70px; float: left; margin-left: 24px">医疗机构：</div>

						<div class="allerRightout cell" align="right"
							style="text-align: right; width: 128px; float: left">
							<select id="orgCode" name="orgCode" onchange="obtainVisitDate(this.value,'${patientSn}')" style="width:128px;">
								<option value="${Constants.OPTION_SELECT}">请选择</option>
								<html:options domain="${Constants.DOMAIN_ORG_CODE}"
								value='${orgCode}' title="true" />						
							</select>
						</div>	
					</c:if>

					<div class="allerRightout cell" align="right"
						<c:choose>
							<c:when test="${Constants.TRUE_ORG_CODE == Constants.DISABLE_ORG_CODE}">
								style="text-align: right; border: 0px; width: 70px; float: left; margin-left: -5px"
							</c:when>
							<c:otherwise>
								style="text-align: right; border: 0px; width: 70px; float: left; margin-left: 24px"
							</c:otherwise>
						</c:choose>>就诊日期：</div>

					<div id="relatedItem" class="allerRightout cell" align="right"
						<c:choose>
							<c:when test="${(orgCode == null || fn:length(orgCode) == 0) && (Constants.TRUE_ORG_CODE == Constants.DISABLE_ORG_CODE)}">
								style="text-align: right; width: 220px; float: left"
							</c:when>
							<c:otherwise>
								style="text-align: right; width: 120px; float: left"
							</c:otherwise>
						</c:choose>>
						<select name="date" id="date" 
							<c:choose>
								<c:when test="${(orgCode == null || fn:length(orgCode) == 0) && (Constants.TRUE_ORG_CODE == Constants.DISABLE_ORG_CODE)}">
									style="width: 220px;"
								</c:when>
								<c:otherwise>
									style="width: 120px;"
								</c:otherwise>
							</c:choose>>
							<c:forEach items="${visitDates}" var="visitDate"
								varStatus="status">
								<option value="${visitDate.visitSn}" 
									<c:if test="${Constants.TRUE_ORG_CODE == Constants.DISABLE_ORG_CODE}">title="<ref:translate domain='${Constants.DOMAIN_ORG_CODE}' code='${visitDate.orgCode}'/>"</c:if>
									<c:if test="${visitDate.visitSn == visitDetail.visitSn}">selected</c:if>>
									${visitDate.visitDate}<c:if test="${Constants.TRUE_ORG_CODE == Constants.DISABLE_ORG_CODE}"><c:if test="${orgCode == null || fn:length(orgCode) == 0}"><ref:translate domain="${Constants.DOMAIN_ORG_CODE}" code="${visitDate.orgCode}"/></c:if></c:if>
								</option>
							</c:forEach>
						</select>
					</div>

					<div class="allerRightout" style="width: 5px; float: left">&nbsp;</div>

					<div class="allerRightout cell"
						style="width: 61px; float: left; padding-bottom: 4px;">
						<input type="button" style="cursor: pointer" id="sr" class="but butSearch"
							onclick="go('1')" />
					</div>

					<div class="allerRightout cell" style="width: 60px; float: left">
						<input id="prev" type="button" id="pr" onclick="prev('1','${currentVisitSn}','${orgCode}')"/>
					</div>

					<div class="allerRightout cell" style="width: 60px; float: left">
						<input id="next" type="button" id="nr" onclick="next('1','${currentVisitSn}','${orgCode}')"/>
					</div>
				</td>
				<!-- $[BUG]0011883 MODIFY END -->
			</tr>
		</table>

		<table class="ctab" style="width: 100%;" border="0">
			<tr>
				<td
					style="vertical-align: top; background: url('../images/mzsttop.png'); border: 1px solid #C5D6E0; border-collapse: collapse; font-family:"微软雅黑";">
					<table cellspacing="2" cellpadding="1" height="90" border="0"
						id="Table1" style="margin-left: 110px;">
						<tr style="height: 28px;">
							<c:choose>
								<c:when test="${visitDetail.visitSn==null}">
									<td height="27" colspan="5" style="border-right: 0px;"><b>该患者没有任何门诊就诊记录!</b></td>
								</c:when>
								<c:otherwise>
									<td style="border-right: 0px;" align="left"><b> <fmt:formatDate
												value="${visitDetail.visitDate}" type="both"
												pattern="yyyy-MM-dd" /></b></td>
									<td width="10px"></td>
									<td style="border-right: 0px;" align="left" nowrap><b>${visitDetail.visitTypeName}</b>
									</td>
									<td width="10px"></td>
									<td style="border-right: 0px;" align="left" nowrap><b>${visitDetail.visitDeptName}</b>
									</td>
									<td width="10px"></td>
									<td style="border-right: 0px;" align="left" nowrap><b>${visitDetail.visitDoctorName}</b>
									</td>
									<td>&nbsp;</td>
								</c:otherwise>
							</c:choose>
						</tr>
						<c:choose>
							<c:when test="${viewSettings.showDiagnosis==true}">
								<tr>
									<td style="border-top: 0px;" align="left" colspan="5"><b>主要诊断：</b>
										<c:choose>
											<c:when
												test="${accessContorl == false|| (accessContorl && loginUserAclAuths.clinicalInfoAuth01)}">
												<font color="#0099CC">${diagnosisMain}</font>
											</c:when>
											<c:otherwise>
												<span class="dataitem" style="color: red">${Constants.ACL_NOACCESS_MESSAGE}</span>
											</c:otherwise>
										</c:choose></td>
								</tr>
								<tr>
									<td align="left" colspan="5"><b>次要诊断：</b> <c:choose>
											<c:when
												test="${accessContorl == false|| (accessContorl && loginUserAclAuths.clinicalInfoAuth01)}">
												<font color="#0099CC">${diagnosisCi}</font>
											</c:when>
											<c:otherwise>
												<span class="dataitem" style="color: red">${Constants.ACL_NOACCESS_MESSAGE}</span>
											</c:otherwise>
										</c:choose></td>
								</tr>
							</c:when>
							<c:otherwise>
								<tr>
									<td colspan="5">&nbsp;</td>
								</tr>
								<tr>
									<td colspan="5">&nbsp;</td>
								</tr>
							</c:otherwise>
						</c:choose>
					</table>
				</td>
			</tr>
		</table>
		<!-- $Author :bin_zhang
			  $Date : 2012/12/25 10:08$
			  [BUG]0012694 ADD BEGIN -->
		<table width="100%" id="ov_show_all">
			<tr>
				<td height="10" align="right"><input type="hidden" value="1"
					id="show_all" /> <a href="javascript:void(0);" id="show_all_text"
					onclick="showBlockAll();" style="text-decoration: none; text-align: right; padding-right: 5px;">▼</a>
				</td>
			</tr>
		</table>
		<!--[BUG]0012694 ADD END -->
		<!-- $Author :chang_xuewen
			 $Date : 2013/07/04 11:00
			 $[BUG]0033461 MODIFY BEGIN -->
		<div id="drugdiv"></div>
		
		<div id="examdiv"></div>
		
		<div id="labdiv"></div>
		
		<div id="docdiv"></div>
		
		<div id="otherdiv"></div>
		<!--[BUG]0033461 MODIFY END -->
	</div>
</body>
</html>
