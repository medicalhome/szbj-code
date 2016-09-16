<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="translation.tld" prefix="ref"%>
<%@ taglib uri="html.tld" prefix="html"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="Constants" class="com.founder.cdr.core.Constants"/>
<jsp:useBean id="TimerAndInpatientDto" class="com.founder.cdr.dto.TimerAndInpatientDto"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Pragma" content="no-cache" />
	<!-- Prevents caching at the Proxy Server -->
	<meta http-equiv="Expires" content="0" />
	<title>住院视图列表</title>
	<link rel="Stylesheet" type="text/css" href="../styles/jquery-tipTip.css" />
	<link rel="Stylesheet" type="text/css" href="../styles/inpatient-view.css" />
	<script language="javascript" type="text/javascript" src="../scripts/timerInpatient/excanvas.min.js"></script>
	<script language="javascript" type="text/javascript" src="../scripts/timerInpatient/jquery.flot.modify.js"></script>
	<script language="javascript" type="text/javascript" src="../scripts/timerInpatient/jquery.flot.symbol.modify.js"></script>
	<script language="javascript" type="text/javascript" src="../scripts/jquery.tipTip.js"></script>
	<script language="javascript" type="text/javascript" src="../scripts/timerInpatient/timerInpatient.js"></script>
	<script type="text/javascript" src="../scripts/exam/examList.js"></script>
	<script type="text/javascript" src="../scripts/lab/labList.js"></script>
	<script type="text/javascript" src="../scripts/doc/docList.js"></script>
</head>
<body style="margin: 0; padding: 0;">
	<table cellpadding="0" cellspacing="0" class="tlin" align="left">
		<tr>
			<td>
				<!-- $Author :jin_peng
					 $Date : 2012/11/06 15:40$
					 [BUG]0011026 MODIFY BEGIN -->
			    <!-- $Author :jin_peng
					 $Date : 2012/10/08 14:16$
					 [BUG]0010132 MODIFY BEGIN -->
				<form name="conditionInPatientForm" method="post" action="../inpatient/list_${patientSn}.html">
					<table class="blockTable" cellpadding="0" cellspacing="0" border="0">
						<tr class="condition">
							<td width="100%" align="left" valign="middle" style="border:solid 1px #D8D9DB;">
								<div  id="patientAlerts" class="cell reAllerin cellEllipsis" style="width:800px;text-align:center;" >	
									<img src="../images/pic_gm.gif" style="padding-right: 6px; vertical-align:middle"  /><font
										color="#DB2C33"><b id="aller" onmouseover="createTip($('#aller').html())" onmouseout="removeTip()"></b></font>
								</div>
								<div class="cell" style="width: 58px; text-align: right; float:right; padding-right:1.5%;">
									<input type="button" id="zyTabPrev"
										<c:choose>
											<c:when test="${existsFlag == TimerAndInpatientDto.NO_EXISTS_FLAG ? true : (inpatientStartDate != null && fn:length(inpatientStartDate) != 0 ? rowNumEnd - bCount >= totalCount : rowNumStart + bCount <= 1)}">
									 			disabled="true" class="but butDisabledBehind zytab_7"
									 		</c:when>
									 		<c:otherwise>
									 			 class="but butBehind zytab_7"
									 		</c:otherwise>
										</c:choose> 
										onclick="jumpTo('', 4, ${inpatientStartDate != null && fn:length(inpatientStartDate) != 0 ? rowNumEnd - bCount + 1 : rowNumStart + bCount - visitRowsCnt}, 'conditionInPatientForm', '#dyContent',{'inpatientStartDate':'${inpatientStartDate}','inpatientEndDate':'${inpatientEndDate}','dischargeDateAndVisitSn':'${dischargeDateAndVisitSn}','visitSnChange':'${visitSnChange}','pagingKindFlag':'${Constants.PAGING_BACKWARD}','actuallyNumPaging':'${actuallyNum}','orgCodeFlag':'${orgCode}'},'1');return false;" style="margin-top:2px;cursor:pointer" />
								</div>
								<div class="cell" style="width: 56px; text-align: right; float:right;">
									<input type="button" id="zyTabNext"
										<c:choose>
											<c:when test="${existsFlag == TimerAndInpatientDto.NO_EXISTS_FLAG ? true : (inpatientStartDate != null && fn:length(inpatientStartDate) != 0 ? (inpatientEndDate != null && fn:length(inpatientEndDate) != 0 ? rowNumStart + fCount <= actuallyNum : rowNumStart + fCount <= 1) : rowNumEnd - fCount >= totalCount)}">
									 			disabled="true" class="but butDisabledFront zytab_6"
									 		</c:when>
									 		<c:otherwise>
									 			class="but butFront zytab_6"
									 		</c:otherwise>
										</c:choose> 
										onclick="jumpTo('', 2, ${inpatientStartDate != null && fn:length(inpatientStartDate) != 0 ? rowNumStart - visitRowsCnt + fCount : rowNumEnd - fCount + 1}, 'conditionInPatientForm', '#dyContent',{'inpatientStartDate':'${inpatientStartDate}','inpatientEndDate':'${inpatientEndDate}','dischargeDateAndVisitSn':'${dischargeDateAndVisitSn}','visitSnChange':'${visitSnChange}','pagingKindFlag':'${Constants.PAGING_FORWARD}','actuallyNumPaging':'${actuallyNum}','orgCodeFlag':'${orgCode}'},'1');return false;" style="margin-top:2px;cursor:pointer" />
								</div>
								<div class="cell" style="width: 60px; text-align: right; float:right;">
									<input type="button" id="zyTabGo" class="but butSearch zytab_5" onclick="searchInpatient('../inpatient/list_${patientSn}.html', 'conditionInPatientForm', '#dyContent', '1');" style="margin-top:2px;cursor:pointer" />
								</div>
								<div class="cell" style="width: 100px; text-align: right; float:right;">
									<input type="text" id="inpatientEndDate" name="inpatientEndDate" style="width: 98%; margin-top:4px;" 
											onmouseover="this.style.background='#FDE8FE' " onmouseout="this.style.background='#FFFFFF'"
											class="datepicker zytab_4" value="${fn:replace(inpatientEndDate, '/', '-')}" />
								</div>
								<div class="cell zytab_3 tabEnter" id="crossDate" style="text-align: right; float:right; margin: 0 4px 0 4px;">--</div>
								<div class="cell" style="width: 100px; text-align: right; float:right;">
									<input type="text" id="inpatientStartDate" name="inpatientStartDate" style="width: 98%; margin-top:4px;" 
											onmouseover="this.style.background='#FDE8FE' " onmouseout="this.style.background='#FFFFFF'"
											class="datepicker zytab_2" value="${fn:replace(inpatientStartDate, '/', '-')}" />
								</div>
								<div class="cell tiTab" id="zyfirst" style="width: 67px; text-align: right; float:right; ">选择日期：</div>
								<div id="relatedItem" class="cell" style="width: 200px; text-align: right; float:right;">
									<select id="dischargeDateAndVisitSn" name="dischargeDateAndVisitSn" class="zytab_1" style="width: 100%;"
										<c:choose>
											<c:when test="${(orgCode == null || fn:length(orgCode) == 0) && (Constants.TRUE_ORG_CODE == Constants.DISABLE_ORG_CODE)}">
												style="width: 200px;"
											</c:when>
											<c:otherwise>
												style="width: 120px;"
											</c:otherwise>
										</c:choose>>
										<option value="${Constants.OPTION_SELECT}">请选择</option>
										<c:forEach items="${visitTimesEtcList}" var="vts">
											<option value="<fmt:formatDate value='${vts.dischargeDate}' type='date' pattern='yyyy-MM-dd' />;${vts.visitSn};<fmt:formatDate value='${vts.visitDate}' type='date' pattern='yyyy-MM-dd' />" 
											<c:if test="${Constants.TRUE_ORG_CODE == Constants.DISABLE_ORG_CODE}">title="<ref:translate domain='${Constants.DOMAIN_ORG_CODE}' code='${vts.orgCode}'/>"</c:if>
											<c:if test="${vts.visitSn == vstSn}">selected</c:if>>
											<fmt:formatDate value="${vts.visitDate}" type="date" pattern="yyyy-MM-dd" /> - <c:if test="${vts.dischargeDate == null}">至今</c:if><fmt:formatDate value="${vts.dischargeDate}" type="date" pattern="yyyy-MM-dd" />
											&nbsp;<c:if test="${Constants.TRUE_ORG_CODE == Constants.DISABLE_ORG_CODE}"><c:if test="${orgCode == null || fn:length(orgCode) == 0}"><ref:translate domain="${Constants.DOMAIN_ORG_CODE}" code="${vts.orgCode}"/></c:if></c:if>
											</option>
										</c:forEach>
									</select>
								</div>
								<div class="cell" style="width: 84px; text-align: right; float:right; ">住院日期：</div>
								<c:if test="${existsFlag == TimerAndInpatientDto.NO_EXISTS_FLAG && (inpatientStartDate == null || fn:length(inpatientStartDate) == 0) && (inpatientEndDate == null || fn:length(inpatientEndDate) == 0)}">
									<div class="cell" style="width: 200px; text-align: left; float:left; padding-left:6px;"><b>该患者没有任何住院记录!</b></div>
								</c:if>
								<c:if test="${Constants.TRUE_ORG_CODE == Constants.DISABLE_ORG_CODE}">
									<div style=" text-align: right; float:right;margin-top:4px;width: 140px;">
										<select id="orgCodeFlag" name="orgCodeFlag" style="width:130px;" onchange="obtainVisitDate_inp(this.value,'${patientSn}')">
											<option value="${Constants.OPTION_SELECT}">请选择</option>
											<html:options domain="${Constants.DOMAIN_ORG_CODE}"
											value='${orgCode}' />						
										</select>
									</div>
									<div class="cell" style="width: 84px; text-align: right; float:right; ">医疗机构：</div>
								</c:if>
							</td>
						</tr>
						<tr style="height:3px"></tr>
                    </table>
				</form>
				<!-- [BUG]0010132 MODIFY END -->
				<!-- [BUG]0011026 MODIFY END -->
			</td>
		</tr>
		<tr>
			<td>
				<!-- $Author :jin_peng
					 $Date : 2012/12/24 09:45$
					 [BUG]0012698 MODIFY BEGIN -->
				<div id="seleSlide" style="width:1020px;height:20px;position:absolute;border:1px solid #ff6600;margin-top:2px;display: none;">&nbsp;</div>
				<table cellpadding="0" cellspacing="0" class="tlin tab tabLayout" align="left" width="100%;">
					<tr>
						<td id="tabLeft" height="24" width="100%;" align="center" colspan="6" class="tabBigLeft wordSpace fontBold tabEnter" style="cursor:pointer" onclick="backShow();" 
							onmouseover="this.style.color='#ff6600'" onmouseout="this.style.color='black'" title="原始">
								<a id="lllllll" href="javascript:void(0)" onclick="hideOrShowAllContent('all');" style="border:0" ><img id="im" style="width:15px;height:15px;border:0" src="../images/tree_folder2.gif" align="middle" onmouseover="$('#tabLeft').css('color','black');stopBubble(window.event);" /></a><input type="hidden" value="1" id="show_all"/>日 期</td>
						<c:forEach items="${inpatientList}" var="inpatient" varStatus="status">
							<td id="inpDt${status.count}" height="24" width="100%;" align="center" style="color:${inpatient.inpatientDateDisplayColor}" colspan="6"
								<c:choose>
									<c:when test="${inpatient.inpatientDate != null}">
										onclick="executeSpecificity('inpDt${status.count}','${status.count}','<fmt:formatDate value="${inpatient.inpatientDate}" type="date" pattern="yyyy/MM/dd" />','${inpatient.visitSn}')" onmouseover="this.style.color='#ff6600';this.style.cursor='pointer';" onmouseout="this.style.color='${inpatient.inpatientDateDisplayColor}'" class="fontBold tabRightin tabEnter"
									</c:when>
									<c:otherwise>
										class="fontBold tabRightin"
									</c:otherwise>
								</c:choose>>
									<c:if test="${inpatient.inpatientDate == null}">&nbsp;</c:if>
									<fmt:formatDate value="${inpatient.inpatientDate}" type="date" pattern="yyyy/MM/dd" />
							</td>
						</c:forEach>
					</tr>
					<tr>
						<td id="inpDs" height="24" align="center" colspan="6"
							class="tabBigLeft wordSpace fontBold" style="padding-left: 10px">
							住院天数<c:if test="${!empty inpatientList[0]}">
								<c:choose>
									<c:when
										test="${empty inpatientList[0].dischargeWardName || empty inpatientList[0].dischargeBedNo}">(<c:if
											test="${fn:length(inpatientList[0].dischargeWardName)>4}">${inpatient.dischargeWardName}...</c:if>
										<c:if
											test="${fn:length(inpatientList[0].dischargeWardName)<=4}">${inpatient.dischargeWardName}</c:if>${inpatient.dischargeBedNo})</c:when>
									<c:otherwise>(${inpatientList[0].dischargeWardName}/${inpatientList[0].dischargeBedNo})</c:otherwise>
								</c:choose>
							</c:if>
						</td>
						<c:forEach items="${inpatientList}" var="inpatient" varStatus="status">
							<td height="24" align="center" class="fontBold tabRightin" colspan="6">
								<div>
									<c:if test="${inpatient.inpatientDate == null}">&nbsp;</c:if>
									<c:if test="${inpatient.inpatientDate != null}">
										<div id="inpatientDay_${inpatient.inpatientDays}">
											${inpatient.inpatientDays}
											<div id="inpatientDay_${inpatient.inpatientDays}_time" style="display:none">
												<fmt:formatDate value="${inpatient.inpatientDate}" type="date" pattern="yyyy-MM-dd HH:mm" />
											</div>
											<c:if test="${fn:substring(inpatient.inpatientDate,0,10) == fn:substring(inpatient.entranceTime,0,10)}">
												<div id="inpatientDay_entrance_time" style="display:none">
													<input name="inpatientDay_entrance_time" value="${inpatient.inpatientDays}"/>
													<input name="inpatientDay_status_count" value="${status.count}"/>
													<div><fmt:formatDate value="${inpatient.entranceTime}" type="date" pattern="yyyy-MM-dd HH:mm" /></div>
												</div>
											</c:if>
										</div>									
									</c:if>
								</div>
							</td>
						</c:forEach>
					</tr>
					<c:if test="${viewSettings.showTC == true}">
					<tr id="threeItemTitle" class="threeItemTitle">
						<td height="25" align="center" class="tabBigLeft" colspan="6"><input type="hidden" value="1" id="show_threeItem"/><div style="float:left;padding-left:40%;font-weight:bold;"><img src="../images/tree_folder1.gif" class="tabEnter" align="middle" onclick="hideOrShowContent('threeItem');"/>三 测 单</div></td>
						<td height="25" colspan="${visitRowsCnt*6}">&nbsp;</td>
					</tr>
					
					<!-- $Author :jin_peng
						 $Date : 2012/11/13 14:25$
						 [BUG]0011406 MODIFY BEGIN -->
					<tbody id="threeItemContentt" class="threeItemContent">
					<tr>
						<td height="24" align="center" colspan="6" class="tabBigLeft wordSpace fontBold">
							<div><img src="../images/tree_folder2.gif" class="tabEnter" align="middle" onclick="hideOrShowContent('threeItem');"/>时 间</div>
						</td>
						<td height="24" align="center" colspan="1" class="perTdin fontBold">4</td>
						<td height="24" align="center" colspan="1" class="perTdin fontBold">8</td>
						<td height="24" align="center" colspan="1" class="perTdin fontBold">12</td>
						<td height="24" align="center" colspan="1" class="perTdin fontBold">16</td>
						<td height="24" align="center" colspan="1" class="perTdin fontBold">20</td>
						<td height="24" align="center" colspan="1" class="perTdin fontBold">24</td>
						
						<td height="24" align="center" colspan="1" class="perTdin fontBold">4</td>
						<td height="24" align="center" colspan="1" class="perTdin fontBold">8</td>
						<td height="24" align="center" colspan="1" class="perTdin fontBold">12</td>
						<td height="24" align="center" colspan="1" class="perTdin fontBold">16</td>
						<td height="24" align="center" colspan="1" class="perTdin fontBold">20</td>
						<td height="24" align="center" colspan="1" class="perTdin fontBold">24</td>
						
						<td height="24" align="center" colspan="1" class="perTdin fontBold">4</td>
						<td height="24" align="center" colspan="1" class="perTdin fontBold">8</td>
						<td height="24" align="center" colspan="1" class="perTdin fontBold">12</td>
						<td height="24" align="center" colspan="1" class="perTdin fontBold">16</td>
						<td height="24" align="center" colspan="1" class="perTdin fontBold">20</td>
						<td height="24" align="center" colspan="1" class="perTdin fontBold">24</td>
						
						<td height="24" align="center" colspan="1" class="perTdin fontBold">4</td>
						<td height="24" align="center" colspan="1" class="perTdin fontBold">8</td>
						<td height="24" align="center" colspan="1" class="perTdin fontBold">12</td>
						<td height="24" align="center" colspan="1" class="perTdin fontBold">16</td>
						<td height="24" align="center" colspan="1" class="perTdin fontBold">20</td>
						<td height="24" align="center" colspan="1" class="perTdin fontBold">24</td>
						
						<td height="24" align="center" colspan="1" class="perTdin fontBold">4</td>
						<td height="24" align="center" colspan="1" class="perTdin fontBold">8</td>
						<td height="24" align="center" colspan="1" class="perTdin fontBold">12</td>
						<td height="24" align="center" colspan="1" class="perTdin fontBold">16</td>
						<td height="24" align="center" colspan="1" class="perTdin fontBold">20</td>
						<td height="24" align="center" colspan="1" class="perTdin fontBold">24</td>
						
						<td height="24" align="center" colspan="1" class="perTdin fontBold">4</td>
						<td height="24" align="center" colspan="1" class="perTdin fontBold">8</td>
						<td height="24" align="center" colspan="1" class="perTdin fontBold">12</td>
						<td height="24" align="center" colspan="1" class="perTdin fontBold">16</td>
						<td height="24" align="center" colspan="1" class="perTdin fontBold">20</td>
						<td height="24" align="center" colspan="1" class="perTdin fontBold">24</td>
						
						<td height="24" align="center" colspan="1" class="perTdin fontBold">4</td>
						<td height="24" align="center" colspan="1" class="perTdin fontBold">8</td>
						<td height="24" align="center" colspan="1" class="perTdin fontBold">12</td>
						<td height="24" align="center" colspan="1" class="perTdin fontBold">16</td>
						<td height="24" align="center" colspan="1" class="perTdin fontBold">20</td>
						<td height="24" align="center" colspan="1" class="perTdin fontBold">24</td>
					</tr>
					
					<tr>
						<td height="24" align="center" colspan="2" class="tabSmallLeft fontBold trBackGround">
							体温
						</td>
						<td height="24" align="center" colspan="2" class="tabSmallLeft fontBold trBackGround">
							血压
						</td>
						<td height="24" align="center" colspan="2" class="tabSmallLeftChange fontBold trBackGround">
							脉搏
						</td>
						<td id="led" align="left" colspan="42" rowspan="8">
							<div id="pl" style="overflow:hidden;">
								<div id="placeholder" style="display:block;" onclick="$('#inpatientStartDate').blur();$('#inpatientEndDate').blur();"></div>
							</div>
						</td>
					</tr>
					<tr class="trBackGround">
						<!-- $Author :jin_peng
							 $Date : 2012/12/20 13:45$
							 [BUG]0012702 MODIFY BEGIN -->
						<td height="24" align="center" colspan="2" class="tabSmallLeft temColor tabEnter addBorder" style="cursor:${tempcursor}" onclick="if(temp.length != 0)showOrHideChart('square');">
							<div id="square" class="s">■</div>
						</td>
						<td height="24" align="center" colspan="2" class="tabSmallLeft bpColor tabEnter addBorder" style="cursor:${bloodcursor}" onclick="if(bloodLow.length != 0 || bloodHigh.length != 0)showOrHideChart('triangle')">
							<div id="triangle" class="s">▲▼</div>
						</td>
						<td height="24" align="center" colspan="2" class="tabSmallLeftChange pulseColor tabEnter addBorder" style="cursor:${pulsecursor}" onclick="if(pulse.length != 0)showOrHideChart('circle')">
							<div id="circle" class="s">●</div>
						</td>
						<!-- [BUG]0012702 MODIFY END -->
					</tr>
					<tr class="trBackGround">
						<td height="24" align="center" colspan="2" class="tabSmallLeft temColor">40</td>
						<td height="24" align="center" colspan="2" class="tabSmallLeft bpColor">160</td>
						<td height="24" align="center" colspan="2" class="tabSmallLeftChange pulseColor">140</td>
					</tr>
					<tr class="trBackGround">
						<td height="24" align="center" colspan="2" class="tabSmallLeft temColor">39</td>
						<td height="24" align="center" colspan="2" class="tabSmallLeft bpColor">140</td>
						<td height="24" align="center" colspan="2" class="tabSmallLeftChange pulseColor">120</td>
					</tr>
					<tr class="trBackGround">
						<td height="24" align="center" colspan="2" class="tabSmallLeft temColor">38</td>
						<td height="24" align="center" colspan="2" class="tabSmallLeft bpColor">120</td>
						<td height="24" align="center" colspan="2" class="tabSmallLeftChange pulseColor">100</td>
					</tr>
					<tr class="trBackGround">
						<td height="24" align="center" colspan="2" class="tabSmallLeft temColor">37</td>
						<td height="24" align="center" colspan="2" class="tabSmallLeft bpColor">100</td>
						<td height="24" align="center" colspan="2" class="tabSmallLeftChange pulseColor">80</td>
					</tr>
					<tr class="trBackGround">
						<td height="24" align="center" colspan="2" class="tabSmallLeft temColor">36</td>
						<td height="24" align="center" colspan="2" class="tabSmallLeft bpColor">80</td>
						<td height="24" align="center" colspan="2" class="tabSmallLeftChange pulseColor">60</td>
					</tr>
					<tr class="trBackGround">
						<td height="24" align="center" colspan="2" class="tabSmallLeft temColor">35</td>
						<td height="24" align="center" colspan="2" class="tabSmallLeft bpColor">60</td>
						<td height="24" align="center" colspan="2" class="tabSmallLeftChange pulseColor">40</td>
					</tr>
					
					<tr>
						<td height="24" align="center" colspan="6" style="padding-right:9px" class="tabBigLeft trBackGround wordSpace fontBold">呼 吸(次/分)▼</td>
						<c:forEach items="${breatheList}" var="breathe" varStatus="status">
							<td class="perTdin" height="24" align="center" colspan="1" <c:if test="${fn:length(breathe.itemResult)>2}"> title="${breathe.itemResult}" </c:if>>
								<c:choose>
									<c:when test="${breathe != null}">
										${fn:substring(breathe.itemResult,0,2)}
									</c:when>
									<c:otherwise>
										&nbsp;
									</c:otherwise>
								</c:choose>
							</td>
						</c:forEach>
					</tr>
					
					<tr>
						<td height="24" align="center" colspan="6" style="padding-right:10px" class="tabBigLeft trBackGround fontBold">大便次数(次/天)▼</td>
						<c:forEach items="${defacateList}" var="defacate" varStatus="status">
							<td height="24" class="tabRightin" align="center" colspan="6">
								<c:choose>
									<c:when test="${defacate != null}">
										<c:forEach items="${defacate}" var="defItem" varStatus="statusItem">
											<c:if test="${defItem.itemResult == null}">&nbsp;</c:if>
											${defItem.itemResult}
										</c:forEach>
									</c:when>
									<c:otherwise>
										&nbsp;
									</c:otherwise>
								</c:choose>
							</td>
						</c:forEach>
					</tr>
					
					<tr>
						<td height="24" align="center" colspan="6" class="tabBigLeft trBackGround fontBold">总入量(毫升/天)▼</td>
						<c:forEach items="${inputList}" var="input" varStatus="status">
							<td height="24" class="tabRightin" align="center" colspan="6">
								<c:choose>
									<c:when test="${input != null}">
										<c:forEach items="${input}" var="inpItem" varStatus="statusItem">
											<c:if test="${inpItem.itemResult == null}">&nbsp;</c:if>
											${inpItem.itemResult}
										</c:forEach>
									</c:when>
									<c:otherwise>
										&nbsp;
									</c:otherwise>
								</c:choose>
							</td>
						</c:forEach>
					</tr>
					
					<tr>
						<td height="24" align="center" colspan="6" class="tabBigLeft trBackGround wordSpace fontBold">总出量(毫升/天)▼</td>
						<c:forEach items="${peeList}" var="pee" varStatus="status">
							<td height="24" class="tabRightin" align="center" colspan="6">
								<c:choose>
									<c:when test="${pee != null}">
										<c:forEach items="${pee}" var="peeItem" varStatus="statusItem">
											<c:if test="${peeItem.itemResult == null}">&nbsp;</c:if>
											${peeItem.itemResult}
										</c:forEach>
									</c:when>
									<c:otherwise>
										&nbsp;
									</c:otherwise>
								</c:choose>
							</td>
						</c:forEach>
					</tr>
					
					<tr>
						<td height="24" align="center" colspan="6" style="padding-right:9px" class="tabBigLeft trBackGround wordSpace fontBold">体 重(KG)▼</td>
						<c:forEach items="${weightList}" var="weight" varStatus="status">
							<td height="24" class="tabRightin" align="center" colspan="6">
								<c:choose>
									<c:when test="${weight != null}">
										<c:forEach items="${weight}" var="weiItem" varStatus="statusItem">
											<c:if test="${weiItem.itemResult == null}">&nbsp;</c:if>
											${weiItem.itemResult}
										</c:forEach>
									</c:when>
									<c:otherwise>
										&nbsp;
									</c:otherwise>
								</c:choose>
							</td>
						</c:forEach>
					</tr>
					
					<tr>
						<td height="24" align="center" colspan="6" style="padding-right:9px" class="tabBigLeft trBackGround wordSpace fontBold">疼痛评估指标(静息/活动)</td>
						<c:forEach items="${painAssessmentMergedList}" var="painAssessmentMerged" varStatus="status">
							<td height="24" class="tabRightin" align="center" colspan="6">
								<c:choose>
									<c:when test="${painAssessmentMerged != null}">
										<c:forEach items="${painAssessmentMerged}" var="painAssessmentMergedItem" varStatus="statusItem">
											<c:if test="${painAssessmentMergedItem.itemResult == null}">&nbsp;</c:if>
											${painAssessmentMergedItem.itemResult}
										</c:forEach>
									</c:when>
									<c:otherwise>
										&nbsp;
									</c:otherwise>
								</c:choose>
							</td>
						</c:forEach>
					</tr>
					</tbody>
					</c:if>
					<!-- [BUG]0011406 MODIFY END -->
					
					<c:set var="count" value="1"/>
					<c:if test="${(useACLFlag && accessControl.clinicalInfoAuth01)||!useACLFlag}"> 
					<c:if test="${viewSettings.showDiagnosis == true}">
					<tr id="diagnosisTitle" class="${count%2==1?'evenRow':'oddRow'}">
						<td height="25" align="center" class="tabBigLeft" colspan="6">
							<div class="cellBodyHeadTitleImagein" style="padding-left:12%;"> <!-- style="padding-left:19px;" -->
								<img src="../images/pic_zd_left.png" width="50%" height="50%"/>
							</div>
							<div class="cellBodyHeadTitleText">
									<img src="../images/tree_folder1.gif" align="middle"
										onclick="hideOrShowContent('diagnosis');" class="tabEnter" /><a
										href="javascript:void(0)"
										onclick="changeView('#normal', ${patientSn},'','#continueGoto_diagnosis','1');">诊 断</a>
								</div>
						</td>
						<td height="25" colspan="${visitRowsCnt*6}"><input type="hidden" value="1" id="show_diagnosis"/></td>
					</tr>
					<tr id="diagnosisContentt" class="${count%2==1?'evenRow':'oddRow' }">
						<td height="24" class="tabBigLeft" colspan="6">
							<div id="Dignosis" class="cellBodyHead cellBodyHeadGroundDignosis">
									<img src="../images/tree_folder2.gif" align="middle"
										onclick="hideOrShowContent('diagnosis');" class="tabEnter" /><a
										href="javascript:void(0)"
										onclick="changeView('#normal', ${patientSn},'','#continueGoto_diagnosis','1');">诊 断</a>
								</div>	
						</td>
						<c:forEach items="${diagnosisList}" var="diagnosis" varStatus="status">
							<td height="24" align="center" class="cellTd tabRightin inpDt${status.count}" colspan="6">
								<div class="inpDtItem${status.count}">
									<c:choose>
										<c:when test="${diagnosis != null}">
											<c:forEach items="${diagnosis}" var="diaItem" varStatus="statusItem">
												<c:choose>
													<c:when test="${statusItem.count <= Constants.TIMER_INPATIENT_DISPLAY_TD_COUNT}">
														<div class="cellBody">
															<a href="javascript:void(0)"
																<c:if test="${diaItem.mainDiagnosisFlag == TimerAndInpatientDto.MAIN_DIAGNOSIS_FLAG}">style="color:blue;" onmouseover="this.style.color='red';" 
																onmouseout="this.style.color='blue'"</c:if>  
																onclick="loadCommonPanel('诊断详细',{'url':'../diagnosis/detail_${diaItem.diagnosisSn}.html','tabsFlag':'true','gotoType':'diagnosis','width':'70%',
																		'patientSn':'${patientSn}','diagnosisSn':'${diaItem.diagnosisSn}','type':'${TimerAndInpatientDto.DIAGNOSIS}','name':'${diaItem.diseaseName}','trID':'${diaItem.diagnosisSn}',
																		'visitSn':'${diaItem.visitSn}','inpatientDate':'<fmt:formatDate value="${diaItem.inpatientDate}" type="date" pattern="yyyy-MM-dd" />'});">
																<c:if test="${diaItem.diseaseName == null}">&nbsp;</c:if>${diaItem.diagnosisTypeName}-${diaItem.diseaseName}
															</a>
														</div>
													</c:when>
													<c:otherwise>
														<div class="cellBodyRight">
															<a href="javascript:void(0)" 
																onclick="loadCommonPanel('诊断详细',{'url':'../diagnosis/detail_${diagnosis[0].diagnosisSn}.html','tabsFlag':'true','gotoType':'diagnosis','width':'70%',
																		'patientSn':'${patientSn}','diagnosisSn':'${diagnosis[0].diagnosisSn}','type':'${TimerAndInpatientDto.DIAGNOSIS}','name':'${diagnosis[0].diseaseName}','trID':'${diagnosis[0].diagnosisSn}',
																		'visitSn':'${diagnosis[0].visitSn}','inpatientDate':'<fmt:formatDate value="${diagnosis[0].inpatientDate}" type="date" pattern="yyyy-MM-dd" />'});">
																		更多>>
															</a>
														</div>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</c:when>
										<c:otherwise>
											&nbsp;
										</c:otherwise>
									</c:choose>
								</div>
								<div class="inpDtItemShow${status.count} sHead">
									<c:if test="${diagnosis != null && fn:length(diagnosis) > 0}">
										<div class='loading${status.count} loadingBar'>数据加载中，请稍候...</div>
										<div id="specificallyDiagnosis${status.count}" class="cont${status.count}">
											<div class="loadingBar">数据加载中，请稍候...</div>
										</div>
									</c:if>
								</div >
							</td>
						</c:forEach>
					</tr>
					<c:set var="count" value="${count+1}"/>
					</c:if>
					</c:if>
					<c:if test="${(useACLFlag && accessControl.clinicalInfoAuth02)||!useACLFlag}">
					<c:if test="${viewSettings.showLongDrug == true}">					
					<tr id="longTermDrugTitle" class="${count%2==1?'evenRow':'oddRow'}">
						<td height="25" align="center" class="tabBigLeft" colspan="6">
							<div class="cellBodyHeadTitleImagein" style="padding-left:12%;">
								<img src="../images/pic_ywyz_left.png" width="30%" height="30%" align="middle"/>
							</div>
							<div class="cellBodyHeadTitleText">
								<img src="../images/tree_folder1.gif" align="middle" class="tabEnter" onclick="hideOrShowContent('longTermDrug');"/><a
										href="javascript:void(0)"
										onclick="changeView('#normal', ${patientSn},'','#continueGoto_standingOrder','1');">长期药物医嘱</a>
							</div>
						</td>
						<td height="25" colspan="${visitRowsCnt*6}"><input type="hidden" value="1" id="show_longTermDrug"/></td>
					</tr> 
        		
        				 <!-- $Author :jin_peng
        				 $Date : 2012/10/10 18:28$
        				 [BUG]0010239 MODIFY BEGIN -->
        			<tr id="longTermDrugContentt" class="${count%2==1?'evenRow':'oddRow'}">
						<td height="24" class="tabBigLeft" colspan="6">
							<div id="longTermDrug" class="cellBodyHead cellBodyHeadGroundDrug">
								<img src="../images/tree_folder2.gif" align="middle"  class="tabEnter"
									onclick="hideOrShowContent('longTermDrug');" /><a
									href="javascript:void(0)"
									onclick="changeView('#normal', ${patientSn},'','#continueGoto_standingOrder','1');">长期药物医嘱</a>
							</div>	
						</td>
						<c:forEach items="${longTermDrugOrderList}" var="longTermMedical" varStatus="status">
							<c:set var="longTermCount" value="0" />
							<td height="24" align="" class="cellTd tabRightin inpDt${status.count}" colspan="6">
								<div class="inpDtItem${status.count}">
									<c:choose>
										<c:when test="${longTermMedical != null}">
											<c:set var="longTermDisplayCount" value="0"/>
											<c:set var="extensionCount" value="0" />
											<c:set var="longTermIndex" value="-1"/>
											<c:forEach items="${longTermMedical}" var="longTermMedItem" varStatus="statusItem">
												<c:choose>
													<c:when test="${statusItem.count <= Constants.TIMER_INPATIENT_DISPLAY_TD_COUNT}">
														<c:set var="extensionCount" value="${extensionCount + 1}" />
														<c:choose>
															<c:when test="${longTermMedItem.orderSn != null}">
																<c:set var="longTermDisplayCount" value="${longTermDisplayCount + 1}"/>
																<div>
																	<c:choose>
																		<c:when test="${longTermMedItem.isFirst == TimerAndInpatientDto.IS_FIRST}">
																			<!-- $Author :yang_mingjie
        																				 $Date : 2014/06/09 14:28$
        				 																[BUG]0044564 MODIFY BEGIN -->
																			<a href="javascript:void(0)" style="color:black;" 
																				onmouseover="this.style.color='red';showLongTermOrder({'drugName':'${longTermMedItem.drugName}','name':'${longTermMedItem.name}','specification':'${longTermMedItem.specification}',
																					'dosage':'${longTermMedItem.dosage}','dosageUnit':'${longTermMedItem.dosageUnit}','medicineFreqName':'${longTermMedItem.medicineFreqName}','routeName':'${longTermMedItem.routeName}'});" 
																					 onmouseout="this.style.color='black';removeTip();" 
																					 onclick="loadCommonPanel('长期药物医嘱列表',{'url':'../drug/detail_${longTermMedItem.orderSn}.html?flag=tabs','tabsFlag':'true','gotoType':'drug','width':'70%',
																					 		'patientSn':'${patientSn}','orderSn':'${longTermMedItem.orderSn}','type':'${TimerAndInpatientDto.DRUG_ORDER}','visitSn':'${longTermMedItem.visitSn}',
																					 		'name':'${longTermMedItem.drugName}','trID':'${longTermMedItem.orderSn}','inpatientLongTermDate':'<fmt:formatDate value="${longTermMedItem.inpatientDate}" type="date" pattern="yyyy-MM-dd" />',
																							'longTempFlag':'${Constants.LONG_TERM_FLAG}','cancelOrderStatus':'${Constants.ORDER_STATUS_CANCEL}'});return false;">
																				<span id="fon${statusItem.count}"><c:if test="${longTermMedItem.drugName == null && longTermMedItem.medicineFreqName == null}">&nbsp;</c:if>
																				 <!-- [BUG]0044564 MODIFY END -->
																				<!-- $Author :jin_peng
																					 $Date : 2013/02/19 11:20$
																					 [BUG]0013981 MODIFY BEGIN -->
																				<c:choose>
																					<c:when test="${longTermMedItem.orderType==Constants.HERBAL_MEDICINE_CLASS}">草药&nbsp;</c:when>
																					<c:otherwise>${longTermMedItem.drugName}(${longTermMedItem.name})&nbsp;</c:otherwise>
																				</c:choose> 
																				
																					<!-- $Author :yang_mingjie
        																				 $Date : 2014/06/09 14:28$
        				 																[BUG]0044564 MODIFY BEGIN -->
        				 																 ${longTermMedItem.specification}&nbsp;
																				         ${longTermMedItem.dosage}${longTermMedItem.dosageUnit}&nbsp;
																				         ${longTermMedItem.medicineFreqName}</span>
																				 <!-- [BUG]0044564 MODIFY END -->
																				<!-- [BUG]0013981 MODIFY END -->
																			</a>
																			<div style="width:100%;height:2px;">&nbsp;</div>
																		</c:when>
																		<c:otherwise>
																			<div style="width:100%;" class="tol${statusItem.count}">&nbsp;</div>
																			<div style="width:100%;height:2px;">&nbsp;</div>
																		</c:otherwise>
																	</c:choose>
																	<!-- $Author :yang_mingjie
        																				 $Date : 2014/06/06 14:28$
        				 																[BUG]0044564 MODIFY BEGIN -->
																	<a href="javascript:void(0)" style="color:black;cursor:pointer;" onmouseover="showLongTermOrder({'drugName':'${longTermMedItem.drugName}','name':'${longTermMedItem.name}','specification':'${longTermMedItem.specification}','medicineFreqName':'${longTermMedItem.medicineFreqName}',
																		'dosage':'${longTermMedItem.dosage}','dosageUnit':'${longTermMedItem.dosageUnit}','routeName':'${longTermMedItem.routeName}'});" 
																		 onmouseout="this.style.color='black';removeTip();" onmouseout="removeTip();" 
																		 onclick="loadCommonPanel('长期药物医嘱列表',{'url':'../drug/detail_${longTermMedItem.orderSn}.html?flag=tabs','tabsFlag':'true','gotoType':'drug','width':'70%',
																		 		'patientSn':'${patientSn}','orderSn':'${longTermMedItem.orderSn}','type':'${TimerAndInpatientDto.DRUG_ORDER}','visitSn':'${longTermMedItem.visitSn}',
																		 		'name':'${longTermMedItem.drugName}','trID':'${longTermMedItem.orderSn}','inpatientLongTermDate':'<fmt:formatDate value="${longTermMedItem.inpatientDate}" type="date" pattern="yyyy-MM-dd" />',
																				'longTempFlag':'${Constants.LONG_TERM_FLAG}','cancelOrderStatus':'${Constants.ORDER_STATUS_CANCEL}'});return false;">
																		<div style="width:100%;height:9px;background-color:${longTermMedItem.lineColor}"></div>
																	</a> 
																	<!-- [BUG]0044564 MODIFY END -->
																	<div style="width:100%;height:3px;">&nbsp;</div>
															    </div>
															    <c:if test="${longTermIndex == -1}">
																	<c:set var="longTermIndex" value="${statusItem.count - 1}"/>
																</c:if>
															</c:when>
															<c:otherwise>
																<!--   $Author :jin_peng
																       $Date : 2012/10/25 14:25$
																       [BUG]0010731 MODIFY BEGIN -->
																<div style="width:100%;" class="tol${statusItem.count}">&nbsp;</div>
																<div style="width:100%;height:2px;">&nbsp;</div>
																<div style="width:100%;height:9px;">&nbsp;</div>
																<div style="width:100%;height:3px;">&nbsp;</div>
																<!-- [BUG]0010731 MODIFY END -->
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<c:if test="${longTermMedItem.longTermEachCount - longTermDisplayCount > 0}">
															<c:set var="longTermCount" value="${longTermCount + 1}" />
															<div class="cellBodyRight">
																<c:if test="${longTermIndex == -1}">
																	<c:set var="longTermIndex" value="${Constants.TIMER_INPATIENT_DISPLAY_TD_COUNT}"/>
																</c:if>
																<a href="javascript:void(0)" 
																onclick="ajaxOrderSn('${patientSn}','${TimerAndInpatientDto.DRUG_ORDER}','${longTermMedical[longTermIndex].visitSn}',
																	'${longTermMedical[longTermIndex].drugName}','${Constants.LONG_TERM_FLAG}','${Constants.ORDER_STATUS_CANCEL}',
																	'<fmt:formatDate value="${longTermMedical[longTermIndex].inpatientDate}" type="date" pattern="yyyy-MM-dd" />');return false;">更多>></a></div>
														</c:if>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</c:when>
										<c:otherwise>
											&nbsp;
										</c:otherwise>
									</c:choose>
								</div>
								<div class="inpDtItemShow${status.count} sHead">
									<c:if test="${(longTermMedical != null && fn:length(longTermMedical) > 0 && longTermCount > 0 )||longTermDisplayCount > 0}">
										<div class="loading${status.count} loadingBar">数据加载中，请稍候...</div>
										<div id="specificallyLongDrug${status.count}" class="cont${status.count}">
											<div class="loadingBar">数据加载中，请稍候...</div>
										</div>
									</c:if>
								</div >
							</td>
						</c:forEach>
					</tr>
					<c:set var="count" value="${count+1}"/>
					</c:if>
					<c:if test="${viewSettings.showShortDrug == true}">					
					<tr id="tempTermDrugTitle" class="${count%2==1?'evenRow':'oddRow'}">
						<td height="25" align="center" class="tabBigLeft" colspan="6">
							<div class="cellBodyHeadTitleImagein" style="padding-left:12%;">
								<img src="../images/pic_ywyz_left.png" width="30%" height="30%" align="middle"/>
							</div>
							<div class="cellBodyHeadTitleText">
								<img src="../images/tree_folder1.gif" align="middle" class="tabEnter" onclick="hideOrShowContent('tempTermDrug');"/><a
									href="javascript:void(0)"
									onclick="changeView('#normal', ${patientSn},'','#continueGoto_stardOrder','1');">临时药物医嘱</a>
							</div>
						</td>
						<td height="25" colspan="${visitRowsCnt*6}"><input type="hidden" value="1" id="show_tempTermDrug"/></td>
					</tr>
					<tr id="tempTermDrugContentt" class="${count%2==1?'evenRow':'oddRow'}">
						<td height="24" class="tabBigLeft" colspan="6">
							<div id="tempDrug" class="cellBodyHead cellBodyHeadGroundDrug">
								<img src="../images/tree_folder2.gif" align="middle" class="tabEnter"
									onclick="hideOrShowContent('tempTermDrug');" /><a
									href="javascript:void(0)"
									onclick="changeView('#normal', ${patientSn},'','#continueGoto_stardOrder','1');">临时药物医嘱</a>
							</div>	
						</td>
						<c:forEach items="${drugOrderList}" var="medical" varStatus="status">
							<td height="24" align="center"  class="cellTd tabRightin inpDt${status.count}" colspan="6">
								<div class="inpDtItem${status.count}">
									<c:choose>
										<c:when test="${medical != null}">
											<c:forEach items="${medical}" var="medItem" varStatus="statusItem">
												<c:choose>
													<c:when test="${statusItem.count <= Constants.TIMER_INPATIENT_DISPLAY_TD_COUNT}">
														<div class="cellBody"><a href="javascript:void(0)" 
														onclick="loadCommonPanel('临时药物医嘱列表',{'url':'../drug/detail_${medItem.orderSn}.html?flag=tabs',
																'tabsFlag':'true','gotoType':'drug','width':'70%','patientSn':'${patientSn}','orderSn':'${medItem.orderSn}','trID':'${medItem.orderSn}',
																'type':'${TimerAndInpatientDto.DRUG_ORDER}','visitSn':'${medItem.visitSn}','name':'${medItem.drugName}',
																'inpatientDate':'<fmt:formatDate value="${medItem.inpatientDate}" type="date" pattern="yyyy-MM-dd" />',
																'temporaryFlag':'${Constants.TEMPORARY_FLAG}','longTempFlag':'${Constants.TEMPORARY_FLAG}',
																'cancelOrderStatus':'${Constants.ORDER_STATUS_CANCEL}'});return false;">
															<c:if test="${medItem.drugName == null && medItem.dosage == null && medItem.dosageUnit == null && medItem.medicineFrenquency == null}">&nbsp;</c:if>
															<!-- $Author :jin_peng
																 $Date : 2013/02/19 11:20$
																 [BUG]0013981 MODIFY BEGIN -->
															<c:choose>
																<c:when test="${medItem.orderType==Constants.HERBAL_MEDICINE_CLASS}">草药&nbsp;</c:when>
																<c:otherwise>${medItem.drugName}&nbsp;</c:otherwise>
															</c:choose>
															${medItem.dosage}${medItem.dosageUnit}</a></div>
															<!-- [BUG]0013981 MODIFY END -->
													</c:when>
													<c:otherwise>
														<c:set var="approvedDrugName" value="${fn:trim(medical[0].approvedDrugName)}" />
														<div class="cellBodyRight"><a href="javascript:void(0)" 
															onclick="loadCommonPanel('临时药物医嘱列表',{'url':'../drug/detail_${medical[0].orderSn}.html?flag=tabs',
																	'tabsFlag':'true','gotoType':'drug','width':'70%','patientSn':'${patientSn}','orderSn':'${medical[0].orderSn}','trID':'${medical[0].orderSn}',
																	'type':'${TimerAndInpatientDto.DRUG_ORDER}','visitSn':'${medical[0].visitSn}','name':'${medical[0].drugName}',
																	'inpatientDate':'<fmt:formatDate value="${medical[0].inpatientDate}" type="date" pattern="yyyy-MM-dd" />',
																	'temporaryFlag':'${Constants.TEMPORARY_FLAG}','longTempFlag':'${Constants.TEMPORARY_FLAG}',
																	'cancelOrderStatus':'${Constants.ORDER_STATUS_CANCEL}'});return false;">更多>></a></div>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</c:when>
										<c:otherwise>
											&nbsp;
										</c:otherwise>
									</c:choose>
								</div>
								<div class="inpDtItemShow${status.count} sHead">
									<c:if test="${medical != null && fn:length(medical) > 0}">
										<div class="loading${status.count} loadingBar">数据加载中，请稍候...</div>
										<div id="specificallyTempDrug${status.count}" class="cont${status.count}">
											<div class="loadingBar">数据加载中，请稍候...</div>
										</div>
									</c:if>
								</div >
							</td>
						</c:forEach>
					</tr>
					<c:set var="count" value="${count+1}"/>
					<!-- [BUG]0010239 MODIFY END -->
					</c:if>	
					</c:if>	
					<c:if test="${(useACLFlag && accessControl.clinicalInfoAuth06)||!useACLFlag}">	
					<c:if test="${viewSettings.showExam == true}">
					<tr id="examinationTitle" class="${count%2==1?'evenRow':'oddRow'}">
						<td height="25" align="center" class="tabBigLeft" colspan="6">
							<div class="cellBodyHeadTitleImagein" style="padding-left:12%;">
								<img src="../images/pic_jc_right.png" width="48%" height="48%" align="middle"/>
							</div>
							<div class="cellBodyHeadTitleText">
								<img src="../images/tree_folder1.gif" align="middle"
									onclick="hideOrShowContent('examination');" class="tabEnter" /><a
									href="javascript:void(0)"
									onclick="changeView('#normal', ${patientSn},'','#continueGoto_examination','1');">检 查</a>
							</div>
						</td>
						<td height="25" colspan="${visitRowsCnt*6}"><input type="hidden" value="1" id="show_examination"/></td>
					</tr>
					<tr id="examinationContentt" class=${count%2==1?'evenRow':'oddRow'}>
						<td height="24" class="tabBigLeft" colspan="6">
							<div id="Examination"
								class="cellBodyHead cellBodyHeadGroundExamination">
								<img src="../images/tree_folder2.gif" align="middle"
									onclick="hideOrShowContent('examination');" class="tabEnter" /><a
									href="javascript:void(0)"
									onclick="changeView('#normal', ${patientSn},'','#continueGoto_examination','1');">检 查</a>
							</div>
						</td>
						<c:forEach items="${examinationList}" var="examination" varStatus="status">
							<td height="24" align="center" class="cellTd tabRightin inpDt${status.count}" colspan="6">
								<div class="inpDtItem${status.count}">
									<c:choose>
										<c:when test="${examination != null}">
											<c:forEach items="${examination}" var="examItem" varStatus="statusItem">
											<!-- $Author :chang_xuewen
											     $Date : 2013/11/05 11:00
											     $[BUG]0040313 MODIFY BEGIN -->
												<!-- <input id="content_${statusItem.count}" value="${examItem.examOrLabTipContent}" style="display: none; "/> -->
												<div style="display:none;" id="content_${statusItem.count}">${examItem.examOrLabTipContent}</div>
												<c:choose>
													<c:when test="${statusItem.count <= Constants.TIMER_INPATIENT_DISPLAY_TD_COUNT}">
														<div class="cellBody">
															<a href="javascript:void(0)" 
																<c:if test="${examItem.examReportSn == null}">
																	style="text-decoration:underline;color:black;cursor:pointer;" 
																	onmouseover="this.style.color='red';" onmouseout="this.style.color='black';"
																</c:if>
																<c:if test="${examItem.examReportSn != null}">
																	style="text-decoration:underline;color:blue;cursor:pointer;" 
																	onmouseover="this.style.color='red';removeTip();createExamTip('#content_${statusItem.count}');" onmouseout="this.style.color='blue';removeExamTip();"
																</c:if>
																onclick="showExamDetailDialogTabs('${examItem.withdrawFlag}','${examItem.examReportSn}','${patientSn}','${examItem.itemSn}',
																	'${examItem.examOrderSn}','${TimerAndInpatientDto.EXAMINATION}','${examItem.visitSn}','true','exam','70%',
																	'${examItem.examinationItemName}','<fmt:formatDate value="${examItem.inpatientDate}" type="date" pattern="yyyy-MM-dd" />');"
																>
																<c:if test="${examItem.examinationMethodName == null && examItem.examinationItemName == null}">&nbsp;</c:if>
																${examItem.examinationMethodName} ${examItem.examinationItemName}</a>
														</div>
													</c:when>
													<c:otherwise>
														<div class="cellBodyRight"><a href="javascript:void(0)" 
														onclick="showExamDetailDialogTabs('${examination[0].withdrawFlag}','${examination[0].examReportSn}','${patientSn}',
																'${examination[0].itemSn}','${examination[0].examOrderSn}','${TimerAndInpatientDto.EXAMINATION}','${examination[0].visitSn}',
																'true','exam','70%','${examination[0].examinationItemName}','<fmt:formatDate
																	value="${examItem.inpatientDate}" type="date" pattern="yyyy-MM-dd" />')">更多>></a></div>
													</c:otherwise>
												</c:choose>
												<!-- $[BUG]0040313 MODIFY END -->
											</c:forEach>
										</c:when>
										<c:otherwise>
											&nbsp;
										</c:otherwise>
									</c:choose>
								</div>
								<div class="inpDtItemShow${status.count} sHead">
									<c:if test="${fn:length(examination) > 0}">
										<div class="loading${status.count} loadingBar">数据加载中，请稍候...</div>
										<div id="specificallyExam${status.count}" class="cont${status.count}">
											<div class="loadingBar">数据加载中，请稍候...</div>
										</div>
									</c:if>
								</div >
							</td>
						</c:forEach>
					</tr>
					<c:set var="count" value="${count+1}"/>
					</c:if>	
					</c:if>	
					<c:if test="${(useACLFlag && accessControl.clinicalInfoAuth05)||!useACLFlag}">
					<c:if test="${viewSettings.showLab == true}">
					<tr id="labTitle" class="${count%2==1?'evenRow':'oddRow'}">
						<td height="25" align="center" class="tabBigLeft" colspan="6">
							<div class="cellBodyHeadTitleImagein" style="padding-left:12%;">
								<img src="../images/pic_jy_right.png" width="32%" height="32%" align="middle"/>
							</div>
							<div class="cellBodyHeadTitleText">
								<img src="../images/tree_folder1.gif" align="middle" class="tabEnter"
									onclick="hideOrShowContent('lab');" /><a
									href="javascript:void(0)"
									onclick="changeView('#normal', ${patientSn},'','#continueGoto_lab','1');">检 验</a>
							</div>
						</td>
						<td height="25" colspan="${visitRowsCnt*6}"><input type="hidden" value="1" id="show_lab"/></td>
					</tr>
					<tr id="labContentt" class="${count%2==1?'evenRow':'oddRow'}">
						<td height="24" class="tabBigLeft" colspan="6">
							<div id="Lab" class="cellBodyHead cellBodyHeadGroundLab">
								<img src="../images/tree_folder2.gif" align="middle" class="tabEnter" onclick="hideOrShowContent('lab');"/><a
									href="javascript:void(0)"
									onclick="changeView('#normal', ${patientSn},'','#continueGoto_lab','1');">检 验</a>
							</div>	
						</td>
						<c:forEach items="${labList}" var="lab" varStatus="status">
							<td height="24" align="center"  class="cellTd tabRightin inpDt${status.count}" colspan="6">
								<div class="inpDtItem${status.count}">
									<c:choose>
										<c:when test="${lab != null}">
											<c:forEach items="${lab}" var="labItem" varStatus="statusItem">
												<c:choose>
													<c:when test="${statusItem.count <= Constants.TIMER_INPATIENT_DISPLAY_TD_COUNT}">
														<div class="cellBody"><a href="javascript:void(0)"
															<c:choose>
																<c:when test="${labItem.labReportSn == null}">
																	style="text-decoration:underline;color:black;cursor:pointer;"
																	onmouseover="this.style.color='red';" onmouseout="this.style.color='black';"
																</c:when>
																<c:when test="${labItem.exceptionFlag != null && labItem.exceptionFlag == TimerAndInpatientDto.EXCEPTION_EXISTS_FLAG}">
																	style="text-decoration:underline;color:#fa7e8b;" onmouseover="this.style.color='red';removeTip();createLabTip('${labItem.exceptionContent}')" onmouseout="this.style.color='#fa7e8b';removeLabTip()"
																</c:when>
																<c:otherwise>
																	style="text-decoration:underline; color:blue;" onmouseover="this.style.color='red';" onmouseout="this.style.color='blue';"
																</c:otherwise>
															</c:choose>
															onclick="removeTip();showLabDetailDialogTabs('${labItem.withdrawFlag}','${labItem.labReportSn}',
																	'${labItem.compositeItemSn}','${patientSn}','${labItem.itemCode}','${labItem.sourceSystemId}','${labItem.labOrderSn}','${labItem.visitSn}',
																	'<fmt:formatDate value="${labItem.inpatientDate}" type="date" pattern="yyyy-MM-dd" />','${TimerAndInpatientDto.LAB}','lab','true','70%','${labItem.itemName}');return false;">
															<c:if test="${labItem.itemName == null}">&nbsp;</c:if>${labItem.itemName}</a></div>
													</c:when>
													<c:otherwise>
														<div class="cellBodyRight"><a href="javascript:void(0)" onclick="showLabDetailDialogTabs('${lab[0].withdrawFlag}','${lab[0].labReportSn}',
																	'${lab[0].compositeItemSn}','${patientSn}','${lab[0].itemCode}','${lab[0].sourceSystemId}','${lab[0].labOrderSn}','${lab[0].visitSn}',
																	'<fmt:formatDate value="${labItem.inpatientDate}" type="date" pattern="yyyy-MM-dd" />','${TimerAndInpatientDto.LAB}','lab','true','70%','${lab[0].itemName}');return false;">更多>></a></div>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</c:when>
										<c:otherwise>
											&nbsp;
										</c:otherwise>
									</c:choose>
								</div>
								<div class="inpDtItemShow${status.count} sHead">
									<!-- <div style="float:left;width:500px">
										<div class="leftClass" style="width:500px"><div style="float:left;cursor:pointer" onmouseover="this.style.color='red';" onmouseout="this.style.color='black';" onclick="expandedDetail()">▶</div> &nbsp;&nbsp;测试</div>
										<div id="testDropdown" class="leftClass" style="width:498px;display:none;height:100px;border:1px solid #0099ff;float:left;border-top:0">
											测试<br></br>测试
										</div>
									</div>  -->
									<c:if test="${fn:length(lab) > 0}">
										<div class="loading${status.count} loadingBar">数据加载中，请稍候...</div>
										<div id="specificallyLab${status.count}" class="cont${status.count}">
											<div class="loadingBar">数据加载中，请稍候...</div>
										</div>
									</c:if>
								</div >
							</td>
						</c:forEach>
					</tr>	
					<c:set var="count" value="${count+1}"/>
					</c:if>	
					</c:if>	
					<c:if test="${(useACLFlag && accessControl.clinicalInfoAuth04)||!useACLFlag}">
					<c:if test="${viewSettings.showProcedure == true}">
					<tr id="procedureTitle" class="${count%2==1?'evenRow':'oddRow'}">
						<td height="25" align="center" class="tabBigLeft" colspan="6">
							<div class="cellBodyHeadTitleImagein" style="padding-left:12%;">
								<img src="../images/pic_ss_left.png" width="52%" height="52%" align="middle"/>
							</div>
							<div class="cellBodyHeadTitleText">
								<img src="../images/tree_folder1.gif" align="middle" class="tabEnter"
									onclick="hideOrShowContent('procedure');" /><a
									href="javascript:void(0)"
									onclick="changeView('#normal', ${patientSn},'','#continueGoto_procedure','1');">手 术</a>
							</div>
						</td>
						<td height="25" colspan="${visitRowsCnt*6}"><input type="hidden" value="1" id="show_procedure"/></td>
					</tr>	
					<tr id="procedureContentt" class="${count%2==1?'evenRow':'oddRow'}">
						<td height="24" class="tabBigLeft" colspan="6">
							<div id="Procedure" class="cellBodyHead cellBodyHeadGroundProcedure">
								<img src="../images/tree_folder2.gif" align="middle" class="tabEnter"
									onclick="hideOrShowContent('procedure');" /><a
									href="javascript:void(0)"
									onclick="changeView('#normal', ${patientSn},'','#continueGoto_procedure','1');">手 术</a>
							</div>	
						</td>
						<c:forEach items="${procedureList}" var="procedure" varStatus="status1">
							<c:forEach items="${proceduredocList}" var="proceduredoc" varStatus="status2">
							<c:choose>
								<c:when test="${status1.count == status2.count}">
								<td height="24" align="center"  class="cellTd tabRightin inpDt${status1.count}" colspan="6">
									<div class="inpDtItem${status1.count}">
									<c:set var="cont" value="0"></c:set>
										<c:choose>
											<c:when test="${procedure != null}">
												<c:forEach items="${procedure}" var="proItem" varStatus="statusItem1">
													<c:set var="cont" value="${statusItem1.count}"></c:set>
													<c:choose>
														<c:when test="${statusItem1.count <= Constants.TIMER_INPATIENT_DISPLAY_TD_COUNT}">
															<div class="cellBody"><a href="javascript:void(0)" onclick="loadCommonPanel('手术详细与手术病历文书列表', {'url':'../order/procedure_${proItem.orderSn}.html',
																'tabsFlag':'true','gotoType':'procedure_doc','width':'70%','patientSn':'${patientSn}','procedureSn':'${proItem.orderSn}','trID':'${proItem.orderSn}',
																'type':'${TimerAndInpatientDto.PROCEDURE}','visitSn':'${proItem.visitSn}','name':'${proItem.orderName}',
																'inpatientDate':'<fmt:formatDate value="${proItem.inpatientDate}" type="date" pattern="yyyy-MM-dd" />'});">
																<c:if test="${proItem.orderName == null}">&nbsp;</c:if>${proItem.orderName}</a></div>
														</c:when>
														<c:otherwise>
															<div class="cellBodyRight"><a href="javascript:void(0)" onclick="loadCommonPanel('手术详细与手术病历文书列表',{'url':'../order/procedure_${procedure[0].orderSn}.html',
															'tabsFlag':'true','gotoType':'procedure_doc','width':'70%','patientSn':'${patientSn}','procedureSn':'${procedure[0].orderSn}','trID':'${procedure[0].orderSn}',
															'type':'${TimerAndInpatientDto.PROCEDURE}','visitSn':'${procedure[0].visitSn}','name':'${procedure[0].orderName}',
																'inpatientDate':'<fmt:formatDate value="${procedure[0].inpatientDate}" type="date" pattern="yyyy-MM-dd" />'});">更多>></a></div>
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</c:when>
										</c:choose>										
										<c:if test="${cont < Constants.TIMER_INPATIENT_DISPLAY_TD_COUNT}">
										<c:choose>
										<c:when test="${proceduredoc != null}">	
										<c:set var="num" value="0"></c:set>											
											<c:forEach items="${proceduredoc}" var="pdocItem" varStatus="statusItem2">
												<c:set var="num" value="${statusItem2.count}"></c:set>
												<c:if test="${(cont + num) <= (Constants.TIMER_INPATIENT_DISPLAY_TD_COUNT + 1)}">
												<c:choose>
													<c:when test="${(cont + num) <= Constants.TIMER_INPATIENT_DISPLAY_TD_COUNT}">
														<div class="cellBody doc_loop"><a href="javascript:void(0)" onclick="loadCommonPanel('手术详细与手术病历文书列表',{'url':'../doc/detail_${pdocItem.documentSn}.html',
														'tabsFlag':'true','gotoType':'prodoc','width':'70%','patientSn':'${patientSn}','documentLid':'${pdocItem.documentLid}','trID':'${pdocItem.documentLid}',
														'type':'${TimerAndInpatientDto.DOCUMENTATION}','visitSn':'${pdocItem.visitSn}','name':'${pdocItem.documentName}',
														'inpatientDate':'<fmt:formatDate value="${pdocItem.inpatientDate}" type="date" pattern="yyyy-MM-dd" />'});">
															<c:if test="${pdocItem.documentName == null}">&nbsp;</c:if>${pdocItem.documentName}</a></div>
													</c:when>
													<c:otherwise>
														<div class="cellBodyRight"><c:if test="${pdocItem.documentAuthorName == null && pdocItem.writeTime == null}">&nbsp;</c:if>${pdocItem.documentAuthorName}(<fmt:formatDate value="${pdocItem.writeTime}" type="time" pattern="MM/dd HH:mm" />)</div>
														<div class="cellBodyRight doc_more"><a href="javascript:void(0)" onclick="loadCommonPanel('手术详细与手术病历文书列表',{'url':'../doc/detail_${proceduredoc[0].documentSn}.html',
														'tabsFlag':'true','gotoType':'prodoc','width':'70%','patientSn':'${patientSn}','documentLid':'${proceduredoc[0].documentLid}','trID':'${proceduredoc[0].documentLid}',
														'type':'${TimerAndInpatientDto.DOCUMENTATION}','visitSn':'${proceduredoc[0].visitSn}','name':'${proceduredoc[0].documentName}',
															'inpatientDate':'<fmt:formatDate value="${proceduredoc[0].inpatientDate}" type="date" pattern="yyyy-MM-dd" />'});">更多>></a></div>
													</c:otherwise>
												</c:choose>
												</c:if>
											</c:forEach>
										</c:when>
										<c:otherwise>
											&nbsp;
										</c:otherwise>
									</c:choose>
									</c:if>
									</div>		
									<div class="inpDtItemShow${status1.count} sHead">
										<c:if test="${procedure != null && fn:length(procedure) > 0}">
											<div class="loading${status1.count} loadingBar">数据加载中，请稍候...</div>
											<div id="specificallyProcedure${status1.count}" class="cont${status1.count}">
												<div class="loadingBar">数据加载中，请稍候...</div>
											</div>									
										</c:if>
									</div >		
									<div class="inpDtItemShow${status2.count} sHead">
										<c:if test="${proceduredoc != null && fn:length(proceduredoc) > 0}">
											<div class="loading${status2.count} loadingBar">数据加载中，请稍候...</div>
											<div id="specificallyProceduredoc${status2.count}" class="cont${status2.count}">
												<div class="loadingBar">数据加载中，请稍候...</div>
											</div>									
										</c:if>
									</div >		
									</td>
									</c:when>
								</c:choose>	
							</c:forEach>			
						</c:forEach>
						
					</tr>
					<c:set var="count" value="${count+1}"/>
					</c:if>	
					</c:if>	
					<c:if test="${(useACLFlag && accessControl.clinicalInfoAuth07)||!useACLFlag}">
					<c:if test="${viewSettings.showDoc == true}">
					<tr id="documentationTitle" class="${count%2==1?'evenRow':'oddRow'}">
						<td height="25" align="center" class="tabBigLeft" colspan="6">
							<div class="cellBodyHeadTitleImagein" style="padding-left:12%;">
								<img src="../images/pic_blws_left.png" width="30%" height="30%" align="middle"/>
							</div>
							<div class="cellBodyHeadTitleText">
								<img src="../images/tree_folder1.gif" align="middle" class="tabEnter"
									onclick="hideOrShowContent('documentation');" /><a
									href="javascript:void(0)"
									onclick="changeView('#normal', ${patientSn},'','#continueGoto_documentation','1');">病历文书</a>
							</div>
						</td>
						<td height="25" colspan="${visitRowsCnt*6}"><input type="hidden" value="1" id="show_documentation"/></td>
					</tr>
					<tr id="documentationContentt" class="${count%2==1?'evenRow':'oddRow'}">
						<td height="24" class="tabBigLeft" colspan="6">
							<div id="Documentation"
								class="cellBodyHead cellBodyHeadGroundDocumentation">
								<img src="../images/tree_folder2.gif" align="middle" class="tabEnter"
									onclick="hideOrShowContent('documentation');" /><a
									href="javascript:void(0)"
									onclick="changeView('#normal', ${patientSn},'','#continueGoto_documentation','1');">病历文书</a>
							</div>
						</td>
						<c:forEach items="${documenationList}" var="documentation" varStatus="status">
							<td height="24" align="center"  class="cellTd tabRightin inpDt${status.count}" colspan="6">
								<div class="inpDtItem${status.count}">
									<c:choose>
										<c:when test="${documentation != null}">
											<c:forEach items="${documentation}" var="docItem" varStatus="statusItem">
												<c:choose>
													<c:when test="${statusItem.count <= Constants.TIMER_INPATIENT_DISPLAY_TD_COUNT}">
														<div class="cellBody"><a href="javascript:void(0)" onclick="loadCommonPanel('病历文书列表',{'url':'../doc/detail_${docItem.documentSn}.html',
														'tabsFlag':'true','gotoType':'doc','width':'70%','patientSn':'${patientSn}','documentLid':'${docItem.documentLid}','trID':'${docItem.documentLid}',
														'type':'${TimerAndInpatientDto.DOCUMENTATION}','visitSn':'${docItem.visitSn}','name':'${docItem.documentName}',
														'inpatientDate':'<fmt:formatDate value="${docItem.inpatientDate}" type="date" pattern="yyyy-MM-dd" />'});">
															<c:if test="${docItem.documentName == null}">&nbsp;</c:if>${docItem.documentName}</a></div>
														<div class="cellBodyRight"><c:if test="${docItem.documentAuthorName == null && docItem.writeTime == null}">&nbsp;</c:if>${docItem.documentAuthorName}(<fmt:formatDate value="${docItem.writeTime}" type="time" pattern="MM/dd HH:mm" />)</div>
													</c:when>
													<c:otherwise>
														<div class="cellBodyRight"><a href="javascript:void(0)" onclick="loadCommonPanel('病历文书列表',{'url':'../doc/detail_${documentation[0].documentSn}.html',
														'tabsFlag':'true','gotoType':'doc','width':'70%','patientSn':'${patientSn}','documentLid':'${documentation[0].documentLid}','trID':'${documentation[0].documentLid}',
														'type':'${TimerAndInpatientDto.DOCUMENTATION}','visitSn':'${documentation[0].visitSn}','name':'${documentation[0].documentName}',
															'inpatientDate':'<fmt:formatDate value="${documentation[0].inpatientDate}" type="date" pattern="yyyy-MM-dd" />'});">更多>></a></div>
													</c:otherwise>
												</c:choose> 
											</c:forEach>
										</c:when>
										<c:otherwise>
											&nbsp;
										</c:otherwise>
									</c:choose>
								</div>
								<div class="inpDtItemShow${status.count} sHead">
									<c:if test="${documentation != null && fn:length(documentation) > 0}">
										<div class="loading${status.count} loadingBar">数据加载中，请稍候...</div>
										<div id="specificallyDocument${status.count}" class="cont${status.count}">
											<div class="loadingBar">数据加载中，请稍候...</div>
										</div>									
									</c:if>
								</div >
							</td>
						</c:forEach>
					</tr>
					<c:set var="count" value="${count+1}"/>
					</c:if>	
					</c:if>	
					<c:if test="${(useACLFlag && accessControl.clinicalInfoAuth03)||!useACLFlag}">
					<c:if test="${viewSettings.showOther == true}">
					<tr id="noDrugTitle" class="${count%2==1?'evenRow':'oddRow'}">
						<td height="25" align="center" class="tabBigLeft" colspan="6">
							<div class="cellBodyHeadTitleImagein" style="padding-left:12%;">
								<img src="../images/pic_footer_bj.png" width="30%" height="30%" align="middle"/>
							</div>
							<div class="cellBodyHeadTitleText">
								<img src="../images/tree_folder1.gif" align="middle" class="tabEnter"
									onclick="hideOrShowContent('noDrug');" /><a
									href="javascript:void(0)"
									onclick="changeView('#normal', ${patientSn},'','#continueGoto_noDrug','1');">非药物医嘱</a>
							</div>
						</td>
						<td height="25" colspan="${visitRowsCnt*6}"><input type="hidden" value="1" id="show_noDrug"/></td>
					</tr>
					<tr id="noDrugContentt" class="${count%2==1?'evenRow':'oddRow'}">
						<td height="24" class="tabBigLeft" colspan="6">
							<div id="NoDrug" class="cellBodyHead cellBodyHeadGroundNoDrug">
								<img src="../images/tree_folder2.gif" align="middle" class="tabEnter"
									onclick="hideOrShowContent('noDrug');" /><a
									href="javascript:void(0)"
									onclick="changeView('#normal', ${patientSn},'','#continueGoto_noDrug','1');">非药物医嘱</a>
							</div>	
						</td>
						<c:forEach items="${noDrugOrderList}" var="noDrugOrder" varStatus="status">
							<td height="24" align="center"  class="cellTd tabRightin inpDt${status.count}" colspan="6">
								<div class="inpDtItem${status.count}">
									<c:choose>
										<c:when test="${noDrugOrder != null}">
											<c:forEach items="${noDrugOrder}" var="ndoItem" varStatus="statusItem">
												<c:choose>
													<c:when test="${statusItem.count <= Constants.TIMER_INPATIENT_DISPLAY_TD_COUNT}">
														<%-- <div class="cellBody"><a href="javascript:void(0)" onclick="showDialog('${ndoItem.orderPath}','${ndoItem.orderTitle}');"> --%>
														<div class="cellBody"><a href="javascript:void(0)" onclick="loadCommonPanel('${ndoItem.orderTitle}',{'url':'${ndoItem.orderPath}','gotoType':'nondrug',
															'tabsFlag':'true','width':'70%','name':'${fn:trim(fn:replace(ndoItem.orderName,'\"','&quot;'))}','patientSn':'${patientSn}','orderSn':'${ndoItem.orderSn}','trID':'${ndoItem.orderSn}',
															'type':'${TimerAndInpatientDto.NO_DRUG_ORDER}','visitSn':'${ndoItem.visitSn}',
															'inpatientDate':'<fmt:formatDate value="${ndoItem.inpatientDate}" type="date" pattern="yyyy-MM-dd" />'});">
															<c:if test="${ndoItem.orderName == null}">&nbsp;</c:if>${ndoItem.orderName}</a></div>
													</c:when>
													<c:otherwise>
														<div class="cellBodyRight"><a href="javascript:void(0)" onclick="loadCommonPanel('${noDrugOrder[0].orderTitle}',{'url':'${noDrugOrder[0].orderPath}',
															'gotoType':'nondrug','tabsFlag':'true','width':'70%','patientSn':'${patientSn}','orderSn':'${noDrugOrder[0].orderSn}','trID':'${noDrugOrder[0].orderSn}',
															'type':'${TimerAndInpatientDto.NO_DRUG_ORDER}','name':'${fn:trim(fn:replace(noDrugOrder[0].orderName,'\"','&quot;'))}','visitSn':'${noDrugOrder[0].visitSn}',
															'inpatientDate':'<fmt:formatDate value="${noDrugOrder[0].inpatientDate}" type="date" pattern="yyyy-MM-dd" />'});return false;">更多>></a></div>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</c:when>
										<c:otherwise>
											&nbsp;
										</c:otherwise>
									</c:choose>
								</div>
								<div class="inpDtItemShow${status.count} sHead">
									<c:if test="${noDrugOrder != null && fn:length(noDrugOrder) > 0}">
										<div class="loading${status.count} loadingBar">数据加载中，请稍候...</div>
										<div id="specificallyNoneDrug${status.count}" class="cont${status.count}">
											<div class="loadingBar">数据加载中，请稍候...</div>
										</div>
									</c:if>
								</div >
							</td>
						</c:forEach>
					</tr>
					<c:set var="count" value="${count+1}"/>
					</c:if>	
					</c:if>	
				</table>
			</td>
		</tr>
		<!-- [BUG]0012698 MODIFY END -->
		
		<!-- <tr>
			<td>
				<table class="blockTable" cellpadding="0" cellspacing="0" class="tl" border="0">
					<tr style="height:2px"></tr>
					<tr class="conditionCopyRight">
						<td align="left" valign="middle">
							<div class="cellFoot" style="width: 100%; text-align: center; color:white; height:29px; ">Copyright &copy; 2012 方正国际软件有限公司 版权所有</div>
						</td>
					</tr>
				</table>
			</td>
		</tr> -->
	</table>
	
	<script type="text/javascript">
		var temp = null;
		var bloodLow = null;
		var bloodHigh = null;
		var pulse = null;
	
		$(function () 	
		{
			//初始设置页面布局及尺寸
			setDocumentSize();
			
			temp = generateData(${temperatureList});
			
			bloodLow = generateData(${bloodPressureLowList});
			
			bloodHigh = generateData(${bloodPressureHighList});
			
			pulse = generateData(${pulseList});
			
			//初始设置过敏及危险不良反应值
			var patientAlerts = getPatientAlerts();
			
			if(patientAlerts == null || patientAlerts == "")
			{
	        	$("#patientAlerts").hide();        		
        	}
			else
			{
	        	$("#aller").html(getPatientAlerts());    		
        	}
			
			//调用父js的检索框动态加载刷新页面
			condition();
			
		    //调用父js的时间格式校验
			$(".datepicker").bind("blur",parent.isDateString);
		    
			//$Author :jin_peng
			//$Date : 2012/12/24 13:36$
			//[BUG]0012698 ADD BEGIN
		    // 设置选中框透明度
		    $("#seleSlide").animate({opacity:0},0); 
		    
		  	//[BUG]0012698 ADD END
		  	
		  	if(isIE6())
		  	{
		  		$("#seleSlide").css("left","0px").css("top","127px");	
		  	}
		    
		    //初始化页面时隐藏所有栏目
		    $("#diagnosisTitle").hide();
		    $("#longTermDrugTitle").hide();
		    $("#tempTermDrugTitle").hide();
		    $("#labTitle").hide();
		    $("#examinationTitle").hide();
		    $("#noDrugTitle").hide();
		    $("#procedureTitle").hide();
		    $("#documentationTitle").hide();
		    $("#threeItemTitle").hide();
		    
		    if($("#threeItemContentt").size() > 0)
	    	{
		    	$("#im").css("marginLeft","0px");
				$("#inpDs").css("paddingLeft","10px");
	    	}
		    else
		    {
		    	$("#im").css("marginLeft","22px");
				$("#inpDs").css("paddingLeft","30px");
		    }
		 	
		 	logical();
		 	
		 	if($("#threeItemContentt").size() > 0)
			{
				//初始化三测单曲线图数据值
				setData(temp,bloodLow,bloodHigh,pulse);
				
				//初始化三测单整体曲线图显示参数
				initLegendViewParams();
				
				//初始化三测单曲线图拐点显示内容
				initHoverContent();
				
				//隐藏横纵坐标div部分
				dropXYLabel();
			}
		 	
			// 调整长期药物医嘱曲线定位
		    longTermResize();
		});
		
		// resize调用控制变量
		var  resizeTimer = null;
		
		$(window).resize(function() 
		{
			if($("#seleSlide").size() > 0)
			{
				// 减少页面调用次数
				if(resizeTimer)
				{
					clearTimeout(resizeTimer);
				}
				
				resizeTimer = setTimeout("completeResize()",150); 
			}
		});
		
		function completeResize()
		{
			//当浏览器可见区域改变时重设页面布局及尺寸
			setDocumentSize("resize");
			
			// 调整长期药物医嘱曲线定位
			longTermResize();
			
			//当浏览器可见区域改变时重设三测单曲线图显示参数
			if(ledHeight != undefined)
			{
				try
				{
					if($("#threeItemContentt:visible").size() > 0)
					{
						initLegendViewParams();
					}
				}
				catch(e)
				{
					return false;
				}
			}
			
		}
		
		// $Author :jin_peng
	    // $Date : 2012/10/25 14:25$
	    // [BUG]0010731 ADD BEGIN
		function longTermResize()
		{
			var extensionString = '${extensionCount}';
			
			if(extensionString != null && extensionString != "" && extensionString != undefined)
			{
				var extensionCount = parseInt('${extensionCount}');
				
				for(var i = 1 ; i <= extensionCount ; i++)
				{
					var tol = $(".tol" + i);
					var fon = $("#fon" + i);
					
					$(".tol" + i).css("height",fon.outerHeight());
				}
			}
		}
		// [BUG]0010731 ADD END
	</script>

	<!-- $Author :jin_peng
		 $Date : 2012/11/06 15:40$
		 [BUG]0011026 MODIFY BEGIN -->
	<!-- $Author :jin_peng
		 $Date : 2012/10/08 14:16$
		 [BUG]0010132 MODIFY BEGIN -->
	<c:if test="${existsFlag == TimerAndInpatientDto.NO_EXISTS_FLAG && ((inpatientStartDate != null && fn:length(inpatientStartDate) != 0) || (inpatientEndDate != null && fn:length(inpatientEndDate) != 0))}">
		<script type="text/javascript">
			var promptMessage = "";
			
			var dischargeDateAndVisitSn = '${dischargeDateAndVisitSn}';
			
			var inpatientStartDate = '${inpatientStartDate}';
			
			var inpatientEndDate = '${inpatientEndDate}';
			
			var optionSelect = '${Constants.OPTION_SELECT}';
			
			if(dischargeDateAndVisitSn != null && dischargeDateAndVisitSn != "" && dischargeDateAndVisitSn != optionSelect)
			{
				promptMessage = "住院日期：" + '${visitDate}' + "到" + ('${dischargeDate}' == null || '${dischargeDate}' == 'null' || '${dischargeDate}' == '' ? "目前" : "出院日期：" + '${dischargeDate}') + "的住院中，";
			}
			
			if(inpatientStartDate != null && inpatientStartDate != "" && (inpatientEndDate == null || inpatientEndDate == ""))
			{
				promptMessage = promptMessage + "住院开始日期：" + inpatientStartDate + "后不存在该患者的住院记录";
			}
			
			if(inpatientEndDate != null && inpatientEndDate != "" && (inpatientStartDate == null || inpatientStartDate == ""))
			{
				promptMessage = promptMessage + "住院结束日期：" + inpatientEndDate + "前不存在该患者的住院记录";
			}
			
			if(inpatientStartDate != null && inpatientStartDate != "" && inpatientEndDate != null && inpatientEndDate != "")
			{
				promptMessage = promptMessage + "住院开始日期：" + inpatientStartDate + "到住院结束日期：" + inpatientEndDate + "之间内不存在该患者的住院记录";
			}
		
			alert(promptMessage);
		</script>
	</c:if>
	
	<c:if test="${existsFlag == TimerAndInpatientDto.NO_EXISTS_FLAG && (inpatientStartDate == null || fn:length(inpatientStartDate) == 0) && (inpatientEndDate == null || fn:length(inpatientEndDate) == 0)}">
		<script type="text/javascript">
			alert("该患者不存在住院记录！");
		</script>
	</c:if>
	<!-- [BUG]0010132 MODIFY END -->
	<!-- [BUG]0011026 MODIFY END -->
</body>
</html>
