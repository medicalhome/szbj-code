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
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<title>手术-详细</title>

<script>
$(document).ready(function(){
	 var orderSn = '${sp.procedureSn}';

	 closeTab("#moreTabs",orderSn);	 
	 
	 $(".ui-tabs-panel").css("padding","2px");
	 
	 /* if($("#BIGviewFrame").length == 0){
		 $("#procedureRequest").hide();
		 $("#procedureOperation").hide();
	 } */
});
</script>

</head>
<body>
	<div id="dialog">
		<div id="mainContent">
			<div id="_${sp.procedureSn}" class="procedureTabs" name="selectTabs">
				<ul>
					<c:if test="${!empty sp}">
					    <li><div class="tabseperator">&nbsp;&nbsp;</div></li>
						<li  class="headtitle"><a href="#tabs-1" class="lj">手术详细信息</a></li>
					</c:if>
					<c:if test="${fn:length(ae)!=0}">
						<li><div class="tabseperator"></div></li>
						<li  class="headtitle"><a href="#tabs-2" class="lj">麻醉操作记录</a></li>
					</c:if>
					<c:if test="${fn:length(pe)!=0}">
						<li><div class="tabseperator"></div></li>
						<li  class="headtitle"><a href="#tabs-3" class="lj">麻醉体格检查</a></li>
					</c:if>
					
						<li><div class="tabseperator"></div></li>
						<li id="procedureRequest" class="headtitle"><a href="../procedure/request_${sp.orderSn}.html" class="lj">手术申请单</a></li>
						
						<li><div class="tabseperator"></div></li>
						<li id="procedureOperation" class="headtitle"><a href="../procedure/operation_${sp.procedureSn}.html" class="lj">手术操作记录</a></li>
				</ul>
				<c:if test="${!empty sp}">
					<div id="tabs-1" class="tabcontainer">
						<table width="100%" cellpadding="2" cellspacing="1"
							style="border: solid 1px #c0ddea;border-collapse:collapse;border-bottom:0px;">
							<tr class="odd">
								<td class="label">手术名称:</td>
								<td class="dataitem">${sp.operationName}</td>
								<td class="label">手术间:</td>
								<td class="dataitem">${sp.operatingRoom}</td>
							</tr>
							<tr>
								<td class="label">手术日期:</td>
								<td class="dataitem"><fmt:formatDate
												value="${sp.operationDate}" type="date"
												pattern="yyyy-MM-dd HH:mm" /></td>
								<td class="label">手术部位名称:</td>
								<td class="dataitem">${sp.surgerySite}</td>
							</tr>
							<tr class="odd">
								<td class="label">手术执行科室:</td>
								<td class="dataitem">${sp.surgicalDeptName}</td>
								<td class="label">床号:</td>
								<td class="dataitem">${po.bedNo}</td>
							</tr>
							<tr>
								<td class="label">手术开始时间:</td>
								<td class="dataitem"><fmt:formatDate
												value="${sp.startTime}" type="date"
												pattern="yyyy-MM-dd HH:mm" /></td>
								<td class="label">手术结束时间:</td>
								<td class="dataitem"><fmt:formatDate
												value="${sp.endTime}" type="date"
												pattern="yyyy-MM-dd HH:mm" /></td>
							</tr>
							<tr class="odd">
								<td class="label">系数:</td>
								<td class="dataitem">${sp.difficulty}</td>
								<td class="label">手术工作量:</td>
								<td class="dataitem">${sp.workload}</td>
							</tr>
							<tr>
								<td class="label">手术切口愈合等级:</td>
								<td class="dataitem">${sp.healingGradeName}</td>
								<td class="label">操作方法:</td>
								<td class="dataitem">${sp.operationMethodName}</td>
							</tr>
							<tr class="odd">
								<td class="label">操作次数:</td>
								<td class="dataitem">${sp.operationTimes}</td>
								<td class="label">介入物名称:</td>
								<td class="dataitem">${sp.intervenor}</td>
							</tr>
							<c:if test="${Constants.TRUE_ORG_CODE == Constants.DISABLE_ORG_CODE}">
								<tr>
									<td class="label">医疗机构:</td>
									<td class="dataitem">${sp.orgName}</td>
									<td class="label"></td>
									<td class="dataitem"></td>
								</tr>
							</c:if>
							<tr>
								<td colspan="4" height="10" align="center" style="border: 0px;"></td>
							</tr>
						</table>
						<table width="100%" cellpadding="2" cellspacing="1"
							style="border: solid 1px #c0ddea;border-collapse:collapse;border-bottom:0px;">
							<tr>
								<td class="blockHeader" colspan="4" height="27" align="left"
									style="border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; padding-left: 4px; font-weight: bold;">
									手术参与者</td>
							</tr>
							<tr class="odd">
								<td class="label">主刀医生:</td>
								<td class="dataitem">${p11}</td>
								<td class="label">助1:</td>
								<td class="dataitem">${p22}</td>
							</tr>
							<tr>
								<td class="label">助2:</td>
								<td class="dataitem">${p33}</td>
								<td class="label">麻醉医生:</td>
								<td class="dataitem">${p77}</td>
							</tr>
							<tr>
								<td colspan="4" height="10"></td>
							</tr>
						</table>
						<table class="blockTable" cellpadding="2" cellspacing="1"
							border="0">
							<tr style="height: 28px;">
								<td class="blockHeader" colspan="15" height="27" align="left"
									style="border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; padding-left: 4px; font-weight: bold;">
									麻醉</td>
							</tr>
						</table>
						<table id="Table1" style="width: 100%;" class="table"
							cellpadding="2" cellspacing="1">
							<tr class="tabletitle">
								<td height="28" align="center">科室</td>
								<td height="28" align="center">麻醉方式</td>
								<td height="28" align="center">工作量</td>
								<td height="28" align="center">准备时间</td>
								<td height="28" align="center">开始时间</td>
								<td height="28" align="center">结束时间</td>
							</tr>
							<tr class="odd">
								<td height="24" align="left">${ane.anesthesiaDeptName}</td>
								<td height="24" align="left">${ane.anesthesiaMethodName}</td>
								<td height="24" align="right">${ane.workload}</td>
								<td height="24" align="center">
									<fmt:formatDate
												value="${ane.prepareTime}" type="date"
												pattern="yyyy-MM-dd HH:mm" /></td>
								<td height="24" align="center">
									<fmt:formatDate
												value="${ane.startTime}" type="date"
												pattern="yyyy-MM-dd HH:mm" /></td>
								<td height="24" align="center">
									<fmt:formatDate
												value="${ane.endTime}" type="date"
												pattern="yyyy-MM-dd HH:mm" /></td>
							</tr>
						</table>
					</div>
				</c:if>
				<c:if test="${fn:length(ae)!=0}">
					<div id="tabs-2" class="tabcontainer">
						<table id="tblid" style="width: 100%;" class="table"
							cellpadding="2" cellspacing="1">
							<tr class="tabletitle">
								<td height="28" align="center">操作开始时间</td>
								<td height="28" align="center">操作结束时间</td>
								<td height="28" align="center">操作类型</td>
								<td height="28" align="center">操作人</td>
								<td height="28" align="center">操作内容</td>
							</tr>
							<c:forEach items="${ae}" var="ae" varStatus="status">
								<tr class="${status.index % 2 == 0 ? 'odd' : 'even'}">
									<td height="24" align="center">
										<fmt:formatDate
												value="${ae.startTime}" type="date"
												pattern="yyyy-MM-dd HH:mm" /></td>
									<td height="24" align="center">
										<fmt:formatDate
												value="${ae.finishedTime}" type="date"
												pattern="yyyy-MM-dd HH:mm" /></td>
									<td height="24" align="left">${ae.operationType}</td>
									<td height="24" align="left">${ae.operatorName}</td>
									<td height="24" align="left">${ae.operationContent}</td>
								</tr>
							</c:forEach>
						</table>
					</div>
				</c:if>
				<c:if test="${fn:length(pe)!=0}">
					<div id="tabs-3" class="tabcontainer">
						<table id="Table2" style="width: 100%;" class="table"
							cellpadding="2" cellspacing="1">
							<tr class="tabletitle">
								<td height="28" align="center" width="15%">检查项目</td>
								<td height="28" align="center" width="15%">检查结果</td>
								<td height="28" align="center" width="15%">检查时间</td>
								<td height="28" align="center" width="15%">执行科室</td>
								<td height="28" align="center" width="10%">执行人</td>
								<td height="28" align="center" colspan="1">检查描述</td>
							</tr>
							<c:forEach items="${pe}" var="pe" varStatus="status">
								<tr class="${status.index % 2 == 0 ? 'odd' : 'even'}">
									<td height="24" align="left">${pe.itemName}</td>
									<td height="24" align="left">
										${pe.itemResult}${pe.itemResultUnit}</td>
									<td height="24" align="center"><fmt:formatDate
												value="${pe.examTime}" type="date"
												pattern="yyyy-MM-dd HH:mm" /></td>
									<td height="24" align="left">${pe.anesthesiaDeptName}</td>
									<td height="24" align="left">${pe.operatorName}</td>
									<td height="24" align="left">${pe.examDescription}</td>
								</tr>
							</c:forEach>
						</table>
					</div>
				</c:if>				
			</div>
		</div>
	</div>
</body>
</html>
