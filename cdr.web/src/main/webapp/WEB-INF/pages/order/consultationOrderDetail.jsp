<%@ page language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="translation.tld" prefix="ref"%>
<%@ taglib uri="html.tld" prefix="html"%>
<jsp:useBean id="Constants" class="com.founder.cdr.core.Constants"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<meta http-equiv="Pragma" content="no-cache"/>
	<meta http-equiv="Cache-Control" content="no-cache"/>
	<meta http-equiv="Expires" content="0"/>
	<script>
    $(document).ready(function(){
      	 
		var orderSn = '${consultationOrder.orderSn}';
   	 
		closeTab("#moreTabs", orderSn);
		/* tabsBind("#moreTabs", orderSn); */
   	 
    });
    
    function returnParentName()
	{
		return '';
	}
	function returnMutexesName()
	{
		return '${mutexesOrderName}';
	}
	</script>
</head>
<body>
<div id="mainContent">
	<div id="_${consultationOrder.orderSn}" name="selectTabs">
		
			<ul>
				<c:if test="${!empty consultationOrder}">
				    <li><div class="tabseperator">&nbsp;&nbsp;</div></li>
					<li class="headtitle"><a href="#tabs-1" class="lj">会诊医嘱详细信息</a></li>
				</c:if>
				<c:if test="${consultationOrder.patientDomain==Constants.PATIENT_DOMAIN_INPATIENT }">
					<c:if test="${fn:length(nursingOperation)!=0}">
						<li><div class="tabseperator"></div></li>
						<li class="headtitle"><a href="#tabs-2" class="lj">操作记录</a></li>
					</c:if>
				</c:if>
			</ul>
		
		<c:if test="${!empty consultationOrder}">
			<div id="tabs-1" class="tabcontainer">
				<table width="100%" cellpadding="2" cellspacing="1"
					style="border: solid 1px #c0ddea;border-collapse:collapse;border-bottom:0px;">
					<tr>
						<td class="label">医嘱编号:</td>
						<td class="dataitem">${consultationOrder.orderLid}</td>
						<td class="label">医嘱状态:</td>
						<td class="dataitem">${consultationOrder.orderStatusName}</td>
					</tr>
					<tr>
						<td class="label">医嘱类型:</td>
						<td class="dataitem">${consultationOrder.orderTypeName}</td>
						<td class="label">医嘱名称:</td>
						<td class="dataitem">${consultationOrder.orderName}</td>
					</tr>
					<tr>
						<td class="label">医嘱交费状态:</td>
						<td class="dataitem">${consultationOrder.chargeStatusName}</td>
<%-- 						<c:choose>
							<c:when test="${consultationOrder.chargeStatusCode == Constants.CHARGE_STATUS_NOTCHARGE}">
								<td class="dataitem"></td>
							</c:when>
							<c:otherwise>
								<td class="dataitem">${consultationOrder.chargeStatusName}</td>
							</c:otherwise>
						</c:choose> --%>
						<!--<td class="label">是否加急:</td>
						<td class="dataitem"><ref:translate
										domain="${Constants.DOMAIN_BOOLEAN_UNCERTAIN}"
										code="${consultationOrder.urgentFlag}" /></td>-->
						<td class="label">开嘱科室:</td>
						<td class="dataitem">${consultationOrder.orderDeptName}</td>
					</tr>
					<tr>
						<td class="label">开嘱人:</td>
						<td class="dataitem">${consultationOrder.orderPersonName}</td>
						<!-- $Author :chang_xuewen
     			 			 $Date : 2013/10/31 11:00
     			 		     $[BUG]0038735 MODIFY BEGIN -->
						<td class="label">医嘱录入时间:</td>
						<!-- $[BUG]0038735 MODIFY END -->
						<td class="dataitem"><fmt:formatDate value="${consultationOrder.orderTime}" type="both" pattern="yyyy-MM-dd HH:mm" /></td>
					</tr>
					<tr>
						<td class="label">确认人:</td>
						<td class="dataitem">${consultationOrder.confirmPersonName}</td>
						<td class="label">确认时间:</td>
						<td class="dataitem"><fmt:formatDate value="${consultationOrder.confirmTime}" type="both" pattern="yyyy-MM-dd HH:mm" /></td>
					</tr>
					<tr>
						<td class="label">取消人:</td>
						<td class="dataitem">${consultationOrder.cancelPersonName}</td>
						<td class="label">取消时间:</td>
						<td class="dataitem"><fmt:formatDate value="${consultationOrder.cancelTime}" type="both" pattern="yyyy-MM-dd HH:mm" /></td>
					</tr>
					<!--$Author : wu_jianfeng
						$Date : 2013/3/13 18:06$
                        [BUG]0014531 MODIFY BEGIN 
                    -->
					<!--<tr>
						<td class="label">互斥医嘱:</td>
						<td class="dataitem">
							<c:choose>
							<c:when test="${mutexesOrderType != ''}">
							<a href="javascript:void(0);" onclick="showMutexesOrder(this,'${mutexesOrderType}','${mutexesOrderSn}','${mutexesOrderName}','${patientSn}','#moreTabs',event)"> 
								${mutexesOrderName}
							</a>
							</c:when>
							</c:choose>
						</td>
						
					</tr>-->
					<!-- [BUG]0014531 MODIFY END -->
				</table>
			</div>
		</c:if>
		<c:if test="${consultationOrder.patientDomain==Constants.PATIENT_DOMAIN_INPATIENT }">
			<c:if test="${fn:length(nursingOperation)!=0}">
				<div id="tabs-2" class="tabcontainer">
					<table id="Table1" style="width: 100%;" class="table" cellpadding="2"
						cellspacing="1">
						<tr class="tabletitle">
							<td height="28" align="center" width="15%">操作时间</td>
							<td height="28" align="center" width="10%">医嘱执行状态</td>
							<td height="28" align="center" width="15%">操作项目类目</td>
							<td height="28" align="center" width="15%">操作名称</td>
							<td height="28" align="center" width="20%">操作结果</td>
							<td height="28" align="center" width="10%">操作人</td>
							<td height="28" align="center" width="15%">效果评价</td>
						</tr>
						<c:forEach items="${nursingOperation}" var="non" varStatus="status">
							<tr
								<c:if test="${status.count%2==1}">class="odd"</c:if>
								<c:if test="${status.count%2==0}">class="even"</c:if>>
								<td height="24" align="center"><fmt:formatDate
										value="${non.operationTime}" type="date" pattern="yyyy-MM-dd HH:mm" /></td>
								<td height="24" align="left">${non.orderStatusName}</td>
								<td height="24" align="left">${non.operationTypeName}</td>
								<td height="24" align="left">${non.operationItemName}</td>
								<td height="24" align="left">${non.operationResult}</td>
								<td height="24" align="left">${non.operatorName}</td>
								<td height="24" align="left">${non.operationEvaluation}</td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</c:if>
		</c:if>
	</div>
</div>
</body>
</html>
