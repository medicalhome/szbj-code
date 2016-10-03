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
	<script type="text/javascript" src="../scripts/jquery-1.7.1.js"></script>
    <script type="text/javascript" src="../scripts/jquery-ui-1.8.18.custom.min.js"></script>
    <script type="text/javascript" src="../scripts/jquery.history.js"></script>
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
			for(var i=0; i<labReportParams.length; i++){
				var report = $("#report_"+labReportParams[i].labReportSn+labReportParams[i].itemCode+labReportParams[i].compositeItemSn);
				if(report.length>0){
					alert(123);
					report.load("../portalLab/reportDetail.html?rand=" + Math.random(),{"labReportSn":labReportParams[i].labReportSn,"itemCode":labReportParams[i].itemCode,"compositeItemSn":labReportParams[i].compositeItemSn,"patientSn":"${patientSn}"},function(data){
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
    		closeTab("#moreTabs", getTabID()); 
     	 	//$("#_${labOrder.orderSn}").tabs('select', 'tabs-2');
     	 	if(${!empty labOrder && Constants.TEMPORARY_FLAG==labOrder.temporaryFlag }){
 		    	var labCPOEPattern = getLabCPOEPattern(${orderStepDtos},'${labOrder.patientDomain}','${labOrder.orderTypeMinCode}','${Constants.PATIENT_DOMAIN_OUTPATIENT}','${Constants.PATIENT_DOMAIN_INPATIENT}','${Constants.ORDER_TYPE_MIN_LABMICRO}');
 		    	cpoeTabs('#_${labOrder.orderSn}','#canvas_${labOrder.orderSn}','#animateButton_${labOrder.orderSn}',labCPOEPattern,'tabs-2',${withdrawFlag==Constants.REPORT_WITHDRAW_FLAG});
 	     	}
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
		    <div name="labInnerTitle">
			<ul>
				<c:forEach items="${labReportParameters}" var="labReportParameter" varStatus="status">
					<c:if test="${labReportParameter.withdrawFlag!=Constants.REPORT_WITHDRAW_FLAG }">
						<li><div class="tabseperator"></div></li>
						<li class="headtitle"><a href="#tabs-${status.count+2 }" class="lj">报告${labReportParameter.reportDate}</a></li>
					</c:if>
				</c:forEach>
			</ul>
			</div>
			</div>
			<c:forEach items="${labReportParameters}" var="labReportParameter" varStatus="status">
				<c:if test="${labReportParameter.withdrawFlag!=Constants.REPORT_WITHDRAW_FLAG}">
					<div id="tabs-${status.count+2 }" class="tabcontainer">
						<div id="report_${labReportParameter.labReportParam}"></div>
					</div>
				</c:if>
			</c:forEach>
		</div>
	</div>
</div>
</body>
</html>
