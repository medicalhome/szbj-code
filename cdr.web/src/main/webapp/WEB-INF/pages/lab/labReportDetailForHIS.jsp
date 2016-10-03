<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="translation.tld" prefix="ref"%>
<jsp:useBean id="Constants" class="com.yly.cdr.core.Constants" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>检验详细</title>
	<meta http-equiv="Pragma" content="no-cache" />
	<meta http-equiv="Cache-Control" content="no-cache" />
	<meta http-equiv="Expires" content="0" />
	<link type="text/css" rel="Stylesheet" href="../styles/jquery-ui-1.8.18.custom.modify.css"
        charset="UTF8" />
    <link type="text/css" rel="Stylesheet" href="../styles/layout-default-1.3.0rc29.15.css"
        charset="UTF8" />
    <link type="text/css" rel="Stylesheet" href="../styles/layout-cdr.css" charset="UTF8" />
    <link type="text/css" rel="Stylesheet" href="../styles/tablelist.css" charset="UTF8" />
    <link type="text/css" rel="Stylesheet" href="../styles/loadingScreen.css" charset="UTF8" />
    <link type="text/css" rel="Stylesheet" href="../styles/tabs-menuPart.css" charset="UTF8" />
    <link type="text/css" rel="Stylesheet" href="../styles/Css.css" />
    <script type="text/javascript" src="../scripts/jquery-1.7.1.js"></script>
    <script type="text/javascript" src="../scripts/jquery-ui-1.8.18.custom.min.js"></script>
    <script type="text/javascript" src="../scripts/jquery.history.js"></script>
    <script type="text/javascript" src="../scripts/jquery.layout-1.3.0rc29.15.js"></script>
    <script type="text/javascript" src="../scripts/loadingScreen.js"></script>
    <script type="text/javascript" src="../scripts/common.js"></script>
    <script type="text/javascript" src="../scripts/layout.js"></script>
    <script type="text/javascript" src="../scripts/dialog-tabs.js"></script>
	<script type="text/javascript" src="../scripts/lab/labList.js"></script>
	<script type="text/javascript" src="../scripts/cpoe/raphael.js"></script>
	<script type="text/javascript" src="../scripts/cpoe/raphael_common.js"></script>
	<script type="text/javascript" src="../scripts/cpoe/jquery.cpoe.js"></script>
	<script type="text/javascript" src="../scripts/cpoe/popup.js"></script>
	<script type="text/javascript" src="../scripts/cpoe/cpoe.js"></script>
	<script>
        $(function() {
        	if(${noSuchReport}){
        		$("#labReportDetail").append("<div style='font-size:20px;font-weight:bold;text-align:center;'>系统中不存在对应的检验报告。</div>");
        		$("#labReportDetailTabs").tabs();
        	}else{
				$("#labReportDetail").load("../lab/reportDetail.html?rand=" + Math.random(),{"labReportSn":"${labResult.labReportSn}","itemCode":"${itemCode}","compositeItemSn":"","patientSn":"${patientSn}"},function(data){
					if(data=='reportFormatError'){
						$(this).empty().append("<div style='font-size:20px;font-weight:bold;text-align:center;'>该检验报告没有对应的显示形式。</div>");
					}
				});
	    		$("#labReportDetailTabs").tabs();
	     	 	if(${!empty labOrder && Constants.TEMPORARY_FLAG==labOrder.temporaryFlag }){
	 		    	var labCPOEPattern = getLabCPOEPattern(${orderStepDtos},'${labOrder.patientDomain}','${labOrder.orderTypeMinCode}','${Constants.PATIENT_DOMAIN_OUTPATIENT}','${Constants.PATIENT_DOMAIN_INPATIENT}','${Constants.ORDER_TYPE_MIN_LABMICRO}');
	 		    	cpoeTabs('#labReportDetailTabs','#canvas_${labOrder.orderSn}','#animateButton_${labOrder.orderSn}',labCPOEPattern,'tabs-3',false);
	 	     	}
        	}
    	}); 

    </script>
</head>
<body>
<div id="dialog">
	<div id="mainContent">
		<div id="labReportDetailTabs">	
			<ul>
				<li><div class="tabseperator">&nbsp;&nbsp;</div></li>
				<li class="headtitle"><a href="#tabs-1" class="lj" hidefocus="true">检验报告</a></li>
				<c:if test="${!empty labOrder }">
				    <li><div class="tabseperator"></div></li>
					<li class="headtitle"><a href="#tabs-2" class="lj">检验医嘱</a></li>
				</c:if>
				<c:if test="${!empty labOrder && Constants.TEMPORARY_FLAG==labOrder.temporaryFlag }">
					<li><div class="tabseperator"></div></li>
					<li class="headtitle"><a href="#tabs-3" class="lj">医嘱跟踪</a></li>				
				</c:if>
			</ul>
			<div id="tabs-1" class="tabcontainer">
				<div id="labReportDetail"></div>
			</div>
			<c:if test="${!empty labOrder}">
				<div id="tabs-2" class="tabcontainer">
					<table width="100%" cellpadding="2" cellspacing="1" style="border: solid 1px #c0ddea;border-collapse:collapse;border-bottom:0px;">
					    <tr  class="odd">
							<td class="label">医嘱号:</td>
							<td class="dataitem">${labOrder.orderLid}</td>
							<td class="label">医嘱类型:</td>
							<td class="dataitem">${labOrder.orderTypeName}</td>
						</tr>
						<tr>
							<td class="label">检验项目:</td>
							<td class="dataitem">${labOrder.itemName}</td>
							<td class="label">检验方法:</td>
							<td class="dataitem">${labOrder.testMethod}</td>
						</tr>
						<tr>
						    <td class="label">执行频率:</td>
						    <td class="dataitem">${labOrder.execFreqName}</td>
   						    <td class="label">长期或临时:</td>
						    <td class="dataitem"><ref:translate
							domain="${Constants.DOMAIN_TEMPORARY_FLAG}"
							code="${labOrder.temporaryFlag}" /></td>
					    </tr>
						<tr>
						    <td class="label">医嘱预定开始时间:</td>
						    <td class="dataitem"><fmt:formatDate value="${labOrder.orderStartTime}" type="both" pattern="yyyy-MM-dd HH:mm" /></td>
						    <td class="label">医嘱停止时间:</td>
						    <td class="dataitem"><fmt:formatDate value="${labOrder.stopTime}" type="both" pattern="yyyy-MM-dd HH:mm" /></td>
					    </tr>
						<tr>
							<td class="label">下嘱科室:</td>
							<td class="dataitem">${labOrder.orderDeptName}</td>
							<td class="label">执行科室:</td>
							<td class="dataitem">${labOrder.executiveDeptName}</td>
						</tr>
						<tr class="odd">
							<td class="label">下嘱人:</td>
							<td class="dataitem">${labOrder.orderPersonName}</td>
							<td class="label">医嘱录入时间:</td>
							<td class="dataitem"><fmt:formatDate value="${labOrder.orderTime}" type="both" pattern="yyyy-MM-dd HH:mm" /></td>
						</tr>
						<tr>
							<td class="label">确认人:</td>
							<td class="dataitem">${labOrder.confirmPersonName}</td>
							<td class="label">确认时间:</td>
							<td class="dataitem"><fmt:formatDate value="${labOrder.confirmTime}" type="both" pattern="yyyy-MM-dd HH:mm" /></td>
						</tr>
						<tr class="odd">
							<td class="label">取消人:</td>
							<td class="dataitem">${labOrder.cancelPersonName}</td>
							<td class="label">取消时间:</td>
							<td class="dataitem"><fmt:formatDate value="${labOrder.cancelTime}" type="both" pattern="yyyy-MM-dd HH:mm" /></td>
						</tr>
						<tr>
							<td class="label">医嘱交费状态:</td>
							<td class="dataitem">${labOrder.chargeStatusName}</td>
<%-- 							<c:choose>
								<c:when test="${labOrder.chargeStatusCode == Constants.CHARGE_STATUS_NOTCHARGE}">
									<td class="dataitem"></td>
								</c:when>
								<c:otherwise>
									<td class="dataitem">${labOrder.chargeStatusName}</td>
								</c:otherwise>
							</c:choose> --%>
							<td class="label">医嘱状态:</td>
							<td class="dataitem">${labOrder.orderStatusName}</td>
						</tr>
						<tr>
							<td class="label">标本号:</td>
							<td class="dataitem">${labOrder.sampleNo}</td>
							<td class="label">互斥医嘱:</td>
							<td class="dataitem">
								<c:if test="${mutexesOrderType != ''}">
								<a href="javascript:void(0);" onclick="showMutexesOrder(this,'${mutexesOrderType}','${mutexesOrderSn}','${mutexesOrderName}','${patientSn}','#moreTabs',event)"> 
									${mutexesOrderName}
								</a>
								</c:if>
							</td>
						</tr>
						<tr>
							<td class="label">是否皮试:</td>
							<td class="dataitem"><ref:translate
									domain="${Constants.DOMAIN_BOOLEAN_UNCERTAIN}"
									code="${labOrder.skinTestFlag}" /></td>
							<td class="label">是否加急:</td>
							<td class="dataitem"><ref:translate
									domain="${Constants.DOMAIN_BOOLEAN_UNCERTAIN}"
									code="${labOrder.urgentFlag}" /></td>
					    </tr>
						<tr>
							<td class="label">是否药观:</td>
							<td class="dataitem"><ref:translate
									domain="${Constants.DOMAIN_BOOLEAN_UNCERTAIN}"
									code="${labOrder.medViewFlag}" /></td>
							<c:choose>
								<c:when test="${Constants.TRUE_ORG_CODE == Constants.DISABLE_ORG_CODE}">
									<td class="label">医疗机构:</td>
									<td class="dataitem"><ref:translate domain="${Constants.DOMAIN_ORG_CODE}" code="${labOrder.orgCode}"/></td>
					  
								</c:when>
								<c:otherwise>
									<td class="label"></td>
									<td class="dataitem"></td>
								</c:otherwise>
							</c:choose>
					    </tr>
						<tr>
							<td colspan="4" height="10"></td>
						</tr>
					</table>
					<table width="100%" cellpadding="2" cellspacing="1" style="border: solid 1px #c0ddea;border-collapse:collapse;border-bottom:0px;">
						<tr>
							<td class="blockHeader" colspan="4" height="27" align="left"
								style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">
								申请单信息</td>
						</tr>
						<tr class="odd">
							<td class="label">申请单编号:</td>
							<td class="dataitem">${labOrder.requestNo}</td>
							<td class="label">申请日期:</td>
							<td class="dataitem"><fmt:formatDate value="${labOrder.requestDate}" type="both" pattern="yyyy-MM-dd HH:mm" /></td>
						</tr>
						<tr>
							<td class="label">诊断:</td>
							<td class="dataitem">${labOrder.diagnosis}</td>
							<td class="label">申请原因:</td>
							<td class="dataitem">${labOrder.requestReason}</td>
						</tr>
						<tr class="odd">
							<td class="label">标本要求:</td>
							<td class="dataitem" colspan="3">${labOrder.sampleRequirement}</td>
						</tr>
						
					</table>
				</div>
			</c:if>
			<c:if test="${!empty labOrder && Constants.TEMPORARY_FLAG==labOrder.temporaryFlag}">
				<div id="tabs-3" class="tabcontainer">
					<div><input type="button" value="观看路径动画" id="animateButton_${labOrder.orderSn}" /></div>
					<div id="canvas_${labOrder.orderSn}"></div>
				</div>
			</c:if>
		</div>
	</div>
</div>
</body>
</html>
