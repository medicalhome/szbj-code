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
	<script type="text/javascript" src="../scripts/cpoe/raphael.js"></script>
	<script type="text/javascript" src="../scripts/cpoe/raphael_common.js"></script>
	<script type="text/javascript" src="../scripts/cpoe/jquery.cpoe.js"></script>
	<script type="text/javascript" src="../scripts/cpoe/popup.js"></script>
	<script type="text/javascript" src="../scripts/cpoe/cpoe.js"></script>
	<script>
		function returnParentName()
		{
			return '${parentDrugOrder.drugName}';
		}
		function returnMutexesName()
		{
			return '${mutexesOrderName}';
		}
		
		function getTabID(){
			if('${labOrder.orderSn}' == ''){
				return '${labReportParameters[0].labReportParam}';
			}else{
				return '${labOrder.orderSn}';
			}
		}
	
		 
        $(function() {
        	var labReportParams = ${labReportParams};
			for(var i = 0; i < labReportParams.length; i++){
				var report = $("#report_"+labReportParams[i].labReportSn+labReportParams[i].itemCode+labReportParams[i].compositeItemSn);
				if(report.length>0){
					report.load("../lab/reportDetail.html?rand=" + Math.random(),{"labReportSn":labReportParams[i].labReportSn,"itemCode":labReportParams[i].itemCode,"compositeItemSn":labReportParams[i].compositeItemSn,"patientSn":"${patientSn}","isOtherTabFlag":false},function(data){
						// 调整检验标题栏，设置滚动条
 			     	    var tabID = '#_'+getTabID();
			     	    var width=0;
			     	    $(tabID).find("div[name='labInnerTitle'] ul li").each(function(){
			 	    		width += $(this).width();
			 	    	});
			     	   	if(width>$(tabID).find("#labOuterTitle").width()){
			     	   		$(tabID).find("div[name='labInnerTitle']").width(width);
				    	}
			     	   	
						if(data=='reportFormatError'){
							$(this).empty().append("<div style='font-size:20px;font-weight:bold;text-align:center;'>该检验报告没有对应的显示形式。</div>");
						}
					});
				}
				if(labReportParams[i].LISLAB){
					var reportOtherTab = $("#report_"+labReportParams[i].labReportSn+labReportParams[i].itemCode+labReportParams[i].compositeItemSn+(i + 1));
					if(reportOtherTab.length>0){
						reportOtherTab.load("../lab/reportDetail.html?rand=" + Math.random(),{"labReportSn":labReportParams[i].labReportSn,"itemCode":labReportParams[i].itemCode,"compositeItemSn":labReportParams[i].compositeItemSn,"patientSn":"${patientSn}","isOtherTabFlag":true},function(data){
							// 调整检验标题栏，设置滚动条
	 			     	    var tabID = '#_'+getTabID();
				     	    var width=0;
				     	    $(tabID).find("div[name='labInnerTitle'] ul li").each(function(){
				 	    		width += $(this).width();
				 	    	});
				     	   	if(width>$(tabID).find("#labOuterTitle").width()){
				     	   		$(tabID).find("div[name='labInnerTitle']").width(width);
					    	}
				     	   	
							if(data=='reportFormatError'){
								$(this).empty().append("<div style='font-size:20px;font-weight:bold;text-align:center;'>该检验报告没有对应的显示形式。</div>");
							}
						});
					}
				}
			}

    		closeTab("#moreTabs", getTabID()); 

     	 	//$("#_${labOrder.orderSn}").tabs('select', 'tabs-2');
     	 	if(${!empty labOrder && Constants.TEMPORARY_FLAG==labOrder.temporaryFlag }){
     	 		/* var labCPOEPattern = getLabCPOEPattern(${orderStepDtos},'${patientDomain}','${labOrder.orderTypeMinCode}','${Constants.PATIENT_DOMAIN_OUTPATIENT}','${Constants.PATIENT_DOMAIN_INPATIENT}','${Constants.ORDER_TYPE_MIN_LABMICRO}');
 		    	cpoeTabs('#_${labOrder.orderSn}','#canvas_${labOrder.orderSn}','#animateButton_${labOrder.orderSn}',labCPOEPattern,'tabs-2',${withdrawFlag==Constants.REPORT_WITHDRAW_FLAG}); */
     	 		var labCPOEPattern = gd_getLabCPOEPattern(${orderStepDtos},'${visitType}','${Constants.VISIT_TYPE_CODE_OUTPATIENT}','${Constants.VISIT_TYPE_CODE_INPATIENT}',${cancelLab});
 		    	cpoeTabs('#_${labOrder.orderSn}','#canvas_${labOrder.orderSn}','#animateButton_${labOrder.orderSn}',labCPOEPattern,'tabs-2',${withdrawFlag==Constants.REPORT_WITHDRAW_FLAG});
 	     	}
     	 	
     		// $Author :yang_mingjie
	        // $Date : 2014/07/15 15:05$
	        // [BUG]0045623 MODIFY BEGIN 
	 	    if(${!empty labReportParameters}){
	 	    	//有医嘱信息和医嘱闭环时，显示第3项tab
		     	if(${!empty labOrder } && ${!empty labOrder && Constants.TEMPORARY_FLAG==labOrder.temporaryFlag }){
		     		$("#_"+${labOrder.orderSn}).tabs({selected:2});
		     	}else if(${!empty labOrder } || ${!empty labOrder && Constants.TEMPORARY_FLAG==labOrder.temporaryFlag }){
		     		$("#_"+${labOrder.orderSn}).tabs({selected:1});
		     	} 
	        }   
	     	//[BUG]0045623 MODIFY END 	
 		
        
    	}); 
    </script>

</head>
<body>
<div id="dialog">
	<div id="mainContent">
		<div <c:choose>
			    	<c:when test="${empty labOrder}">
			    		id="_${labReportParameters[0].labReportParam}"
			    	</c:when>
			    	<c:otherwise>
			    		 id="_${labOrder.orderSn}"
			    	</c:otherwise>
		    	</c:choose>				
		    	 name="selectTabs">	
		    <div id="labOuterTitle" style="overflow-x:auto;overflow-y:hidden;width:100%;position:relative;">	 
		    <div name="labInnerTitle" >
				<ul >
					<c:if test="${!empty labOrder }">
					    <li><div class="tabseperator"  >&nbsp;&nbsp;</div></li>
					    <li class="headtitle"><a href="#tabs-1" class="lj" hidefocus="true" >检验医嘱信息</a></li>
					</c:if>
					<c:if test="${!empty labOrder && Constants.TEMPORARY_FLAG==labOrder.temporaryFlag }">
						<li><div class="tabseperator" ></div></li>
						<li class="headtitle"><a href="#tabs-2" class="lj" >医嘱跟踪</a></li>				
					</c:if>
					<c:forEach items="${labReportParameters}" var="labReportParameter" varStatus="status">
						<c:if test="${labReportParameter.withdrawFlag!=Constants.REPORT_WITHDRAW_FLAG }">
							<li><div class="tabseperator" ></div></li>
							<li class="headtitle"><a href="#tabs-${status.count * 2 + 1 }" class="lj" >报告${labReportParameter.reportDate}</a></li>
							<c:if test="${labReportParameter.LISLAB }">
								<li><div class="tabseperator" ></div></li>
								<li class="headtitle"><a href="#tabs-${status.count * 2 + 2 }" class="lj" >检验项目</a></li>
							</c:if>
						</c:if>
					</c:forEach>	
				</ul>
			</div>
			</div>
			<c:if test="${!empty labOrder}">
				<div id="tabs-1" class="tabcontainer">
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
							<td class="label">医嘱状态:</td>
							<td class="dataitem">${labOrder.orderStatusName}</td>
							<%-- <td class="label">检验方法:</td>
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
						<!-- $Author :chang_xuewen
     						 $Date : 2013/10/31 11:00
     						 $[BUG]0038735 MODIFY BEGIN -->	
						    <td class="label">医嘱预定开始时间:</td>
						<!-- $[BUG]0038735 MODIFY END -->	
						    <td class="dataitem"><fmt:formatDate value="${labOrder.orderStartTime}" type="both" pattern="yyyy-MM-dd HH:mm" /></td>
						    <td class="label">医嘱停止时间:</td>
						    <td class="dataitem"><fmt:formatDate value="${labOrder.stopTime}" type="both" pattern="yyyy-MM-dd HH:mm" /></td> --%>
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
							<%-- <td class="label">医嘱状态:</td>
							<td class="dataitem">${labOrder.orderStatusName}</td> --%>
							<td class="label"></td>
							<td class="dataitem"></td>
						</tr>
						<%-- <tr>
							<td class="label">标本号:</td>
							<td class="dataitem">${labOrder.sampleNo}</td>
						<!-- 	
							<td class="label">互斥医嘱:</td>
							<td class="dataitem">
								<c:if test="${mutexesOrderType != ''}">
								<a href="javascript:void(0);" onclick="showMutexesOrder(this,'${mutexesOrderType}','${mutexesOrderSn}','${mutexesOrderName}','${patientSn}','#moreTabs',event)"> 
									${mutexesOrderName}
								</a>
								</c:if>
							</td> -->
							<td class="label">婴儿编号:</td>
							<td class="dataitem">${labOrder.childNo}</td>
						</tr>
						<tr>
							
							<td class="label">是否加急:</td>
							<td class="dataitem"><ref:translate
									domain="${Constants.DOMAIN_BOOLEAN_UNCERTAIN}"
									code="${labOrder.urgentFlag}" /></td>
							<td class="label">是否药观:</td>
							<td class="dataitem"><ref:translate
									domain="${Constants.DOMAIN_BOOLEAN_UNCERTAIN}"
									code="${labOrder.medViewFlag}" /></td>
					    </tr> --%>
						<tr>	
							<%-- <td class="label">是否皮试:</td>
							<td class="dataitem"><ref:translate
									domain="${Constants.DOMAIN_BOOLEAN_UNCERTAIN}"
									code="${labOrder.skinTestFlag}" /></td> --%>
							<c:choose>
								<c:when test="${Constants.TRUE_ORG_CODE == Constants.DISABLE_ORG_CODE}">
									<td class="label">医疗机构:</td>
									<td class="dataitem" colspan="3"><ref:translate domain="${Constants.DOMAIN_ORG_CODE}" code="${labOrder.orgCode}"/></td>
					    
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
						<%-- <tr class="odd">
							<td class="label">标本要求:</td>
							<td class="dataitem" colspan="3">${labOrder.sampleRequirement}</td>
						</tr> --%>
						
					</table>
				</div>
			</c:if>
			<c:if test="${!empty labOrder && Constants.TEMPORARY_FLAG==labOrder.temporaryFlag}">
				<div id="tabs-2" class="tabcontainer">
					<div><input type="button" value="观看路径动画" id="animateButton_${labOrder.orderSn}" /></div>
					<div id="canvas_${labOrder.orderSn}"></div>
				</div>
			</c:if>
			<c:forEach items="${labReportParameters}" var="labReportParameter" varStatus="status">
				<c:if test="${labReportParameter.withdrawFlag!=Constants.REPORT_WITHDRAW_FLAG}">
					<div id="tabs-${status.count * 2 + 1 }" class="tabcontainer">
						<div id="report_${labReportParameter.labReportParam}"></div>
					</div>
					<c:if test="${labReportParameter.LISLAB}">
						<div id="tabs-${status.count * 2 + 2 }" class="tabcontainer">
							<div id="report_${labReportParameter.labReportParam}${status.count}"></div>
						</div>
					</c:if>
				</c:if>
			</c:forEach>
		</div>
	</div>
</div>
</body>
</html>
