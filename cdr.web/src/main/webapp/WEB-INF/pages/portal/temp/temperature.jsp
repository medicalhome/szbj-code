<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="translation.tld" prefix="ref"%>
<%@ taglib uri="html.tld" prefix="html"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="Constants" class="com.yly.cdr.core.Constants"/>
<jsp:useBean id="TimerAndInpatientDto" class="com.yly.cdr.dto.TimerAndInpatientDto"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Pragma" content="no-cache" />
	<!-- Prevents caching at the Proxy Server -->
	<meta http-equiv="Expires" content="0" />
	<title>体温单</title>
	<link type="text/css" rel="Stylesheet" href="../styles/jquery-ui-1.8.18.custom.modify.css"
        charset="UTF8" />
    <link type="text/css" rel="Stylesheet" href="../styles/jquery-ui-autocomplete.custom.css"
        charset="UTF8" />
    <link type="text/css" rel="Stylesheet" href="../styles/jquery-ui-combobox.custom.modify.css"
        charset="UTF8" />
    <link type="text/css" rel="Stylesheet" href="../styles/layout-default-1.3.0rc29.15.css"
        charset="UTF8" />
    <link type="text/css" rel="Stylesheet" href="../styles/layout-cdr.css" charset="UTF8" />
    <link type="text/css" rel="Stylesheet" href="../styles/tablelist.css" charset="UTF8" />
    <link type="text/css" rel="Stylesheet" href="../styles/timer-shaft.css" charset="utf-8" />
    <link type="text/css" rel="Stylesheet" href="../styles/loadingScreen.css" charset="UTF8" />
    <link type="text/css" rel="Stylesheet" href="../styles/jquery-suggest.css" charset="UTF8" />
    <link type="text/css" rel="Stylesheet" href="../styles/tabs-menuPart.css" charset="UTF8" />
    <link type="text/css" rel="stylesheet" href="../styles/header.css" charset="UTF8"  />
    <link rel="Stylesheet" type="text/css" href="../styles/Css.css" />
	<link rel="Stylesheet" type="text/css" href="../styles/jquery-tipTip.css" />
	<link rel="Stylesheet" type="text/css" href="../styles/inpatient-view.css" />
    <link rel="icon" href="../favicon.ico" type="image/x-icon" />
    <link rel="shortcut icon" type="image/x-icon" href="../favicon.ico" />
    <script type="text/javascript" src="../scripts/jquery-1.7.1.js"></script>
    <script type="text/javascript" src="../scripts/jquery-ui-1.8.18.custom.min.js"></script>
    <script type="text/javascript" src="../scripts/jquery.history.js"></script>
    <script type="text/javascript" src="../scripts/forwardOrBack/forwardOrBack.js"></script>
    <script type="text/javascript" src="../scripts/tabEnter/tabEnter.js"></script>
    <script type="text/javascript" src="../scripts/jquery.ui.autocomplete.js"></script>
    <script type="text/javascript" src="../scripts/jquery.ui.combobox.js"></script>
    <script type="text/javascript" src="../scripts/jquery.layout-1.3.0rc29.15.js"></script>
    <script type="text/javascript" src="../scripts/jquery.bgiframe.js"></script>
    <script type="text/javascript" src="../scripts/jquery.suggest.js"></script>
    <script type="text/javascript" src="../scripts/htmlSelectSuggest.js"></script>
    <script type="text/javascript" src="../scripts/htmlMultiSelectSuggest.js"></script>
    <script type="text/javascript" src="../scripts/loadingScreen.js"></script>
    <script type="text/javascript" src="../scripts/common.js"></script>
    <script type="text/javascript" src="../scripts/layout.js"></script>
    <script type="text/javascript" src="../scripts/visit/mainView.js"></script>
    <script type="text/javascript" src="../scripts/dialog-tabs.js"></script>
    <script type="text/javascript" src="../scripts/visit/normalViewPart.js"></script>
    <script type="text/javascript" src="../scripts/help/help.js"></script>

	<script language="javascript" type="text/javascript" src="../scripts/timerInpatient/excanvas.min.js"></script>
	<script language="javascript" type="text/javascript" src="../scripts/timerInpatient/jquery.flot.modify.js"></script>
	<script language="javascript" type="text/javascript" src="../scripts/timerInpatient/jquery.flot.symbol.modify.js"></script>
	<script language="javascript" type="text/javascript" src="../scripts/jquery.tipTip.js"></script>
	<script language="javascript" type="text/javascript" src="../scripts/timerInpatient/timerInpatient.js"></script>
	<script type="text/javascript" src="../scripts/exam/examList.js"></script>
	<script type="text/javascript" src="../scripts/lab/labList.js"></script>
	<script type="text/javascript" src="../scripts/doc/docList.js"></script>
	<script type="text/javascript">
	function inpatient(url, formName, id, isAddHistory)
	{
		var inpatientStartDate = $("#inpatientStartDate").val();
	    var inpatientEndDate = $("#inpatientEndDate").val();
	    var attrDisabled = $("#dischargeDateAndVisitSn").attr("disabled");
		if(attrDisabled == true)
		{
			showMsg("提示","正在加载就诊日期选择列表，请稍后。。。");
			return false;
		}
		
		var visitSn = $("#dischargeDateAndVisitSn").val();
		if($.trim(visitSn) == "")
		{
			showMsg("提示","请选择住院（日期）记录");
			return false;
		}
	    
	    if(inpatientStartDate != "" && inpatientEndDate != "" && inpatientStartDate > inpatientEndDate)
	    {
	    	showMsg("提示","住院开始时间不能大于住院结束时间！");
	    	return;
	    }
	    search(url, formName, id, 'undefined');
	}
	</script>
</head>
<body style="margin: 0; padding: 0;">
<div id="dynamicContent">
<div id="alertMessage" style="display:none;"></div>
			<table class="blockTable" cellpadding="2" cellspacing="1" >
				<tr style="height: 28px;" id="tableheader">
					<td  class="blockHeader" style="width: 60px; text-align: center;">姓名:
					</td>
					<td style="width: 60px; text-align: left;">${patient.patientName}</td>
					<td  class="blockHeader" style="width: 70px; text-align: center;">性别:
					</td>
					<td style="width: 60px; text-align: left;">${patient.genderName}</td>
					<td class="blockHeader" style="width: 70px; text-align: center;">年龄:</td>
					<td style="width: 60px; text-align: left;">${age}</td>
					<td class="blockHeader" style="width: 70px; text-align: center;">病历号:
					</td>
					<td style="width: 60px; text-align: left;">${patientCrossIndex.inpatientNo}</td>
					<td width="55%" ></td>
				</tr>
			</table>
	<table cellpadding="0" cellspacing="0" class="tlin" align="left">
		<tr>
			<td>
				<form name="conditionInPatientForm" method="post" action="../temp/list_${patientSn}.html">
				<input type="hidden" name="patientId" value="${patientId}" />
				<input type="hidden" name="patientDomain" value="${patientDomain}" />
					<table class="blockTable" cellpadding="0" cellspacing="0" border="0">
						<tr class="condition">
							<td width="100%" align="left" valign="middle" style="border:solid 1px #D8D9DB;">
								
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
										onclick="jumpTo('', 4, ${inpatientStartDate != null && fn:length(inpatientStartDate) != 0 ? rowNumEnd - bCount + 1 : rowNumStart + bCount - visitRowsCnt}, 'conditionInPatientForm', '#dynamicContent',{'inpatientStartDate':'${inpatientStartDate}','inpatientEndDate':'${inpatientEndDate}','dischargeDateAndVisitSn':'${dischargeDateAndVisitSn}','visitSnChange':'${visitSnChange}','pagingKindFlag':'${Constants.PAGING_BACKWARD}','actuallyNumPaging':'${actuallyNum}','orgCodeFlag':'${orgCode}','patientDomain':'${patientDomain}','patientId':'${patientId}'},'undefined');return false;" style="margin-top:2px;cursor:pointer" />
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
										onclick="jumpTo('', 2, ${inpatientStartDate != null && fn:length(inpatientStartDate) != 0 ? rowNumStart - visitRowsCnt + fCount : rowNumEnd - fCount + 1}, 'conditionInPatientForm', '#dynamicContent',{'inpatientStartDate':'${inpatientStartDate}','inpatientEndDate':'${inpatientEndDate}','dischargeDateAndVisitSn':'${dischargeDateAndVisitSn}','visitSnChange':'${visitSnChange}','pagingKindFlag':'${Constants.PAGING_FORWARD}','actuallyNumPaging':'${actuallyNum}','orgCodeFlag':'${orgCode}','patientDomain':'${patientDomain}','patientId':'${patientId}'},'undefined');return false;" style="margin-top:2px;cursor:pointer" />
								</div>
								<div class="cell" style="width: 60px; text-align: right; float:right;">
									<input type="button" id="zyTabGo" class="but butSearch zytab_5" onclick="inpatient('../temp/list_${patientSn}.html', 'conditionInPatientForm', '#dynamicContent', '1');" style="margin-top:2px;cursor:pointer" />
								</div>
								<div class="cell" style="width: 90px; text-align: right; float:right;">
									<input type="text" id="inpatientEndDate" name="inpatientEndDate" style="width: 100%; margin-top:4px;" 
											onmouseover="this.style.background='#FDE8FE' " onmouseout="this.style.background='#FFFFFF'"
											class="datepicker zytab_4" value="${fn:replace(inpatientEndDate, '/', '-')}" />
								</div>
								<div class="cell zytab_3 tabEnter" id="crossDate" style="text-align: right; float:right; margin: 0 4px 0 4px;">--</div>
								<div class="cell" style="width: 90px; text-align: right; float:right;">
									<input type="text" id="inpatientStartDate" name="inpatientStartDate" style="width: 100%; margin-top:4px;" 
											onmouseover="this.style.background='#FDE8FE' " onmouseout="this.style.background='#FFFFFF'"
											class="datepicker zytab_2" value="${fn:replace(inpatientStartDate, '/', '-')}" />
								</div>
								<div class="cell tiTab" id="zyfirst" style="width: 67px; text-align: right; float:right; ">选择日期：</div>
								<div id="relatedItem" class="cell" style="width: 250px; text-align: right; float:right;">
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
					
				</table>
			</td>
		</tr>
	</table>
	
	<script type="text/javascript">
		var temp = null;
		var bloodLow = null;
		var bloodHigh = null;
		var pulse = null;
	
		$(function () 	
		{
			// 初始化带有datepicker样式的日期输入框
			setupDatePicker();
			
			setupDatePickerSetting();
			
			//初始设置页面布局及尺寸
			setDocumentSize();
			
			temp = generateData(${temperatureList});
			
			bloodLow = generateData(${bloodPressureLowList});
			
			bloodHigh = generateData(${bloodPressureHighList});
			
			pulse = generateData(${pulseList});
			
	
      
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
  </div>
</body>
</html>