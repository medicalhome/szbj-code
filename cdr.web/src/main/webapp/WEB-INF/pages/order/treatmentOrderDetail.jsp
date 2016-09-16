<%@ page language="java" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="translation.tld" prefix="ref"%>
<%@ taglib uri="html.tld" prefix="html"%>
<jsp:useBean id="Constants" class="com.founder.cdr.core.Constants" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<meta http-equiv="Pragma" content="no-cache"/>
	<meta http-equiv="Cache-Control" content="no-cache"/>
	<meta http-equiv="Expires" content="0"/>
	<script>
    $(document).ready(function(){
   	 
		var orderSn = '${treatmentOrder.orderSn}';
   	 
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
		<div id="_${treatmentOrder.orderSn}" name="selectTabs">
			
				<ul>
					<c:if test="${!empty treatmentOrder}">
						<li><div class="tabseperator">&nbsp;&nbsp;</div></li>
						<li class="headtitle"><a href="#tabs-1" class="lj">诊疗医嘱详细信息</a></li>
					</c:if>
					
					<c:if test="${showClosedCycle}">
						<li><div class="tabseperator">&nbsp;&nbsp;</div></li>
						<li class="headtitle"><a href="#tabs-3" class="lj">医嘱跟踪</a></li>
					</c:if>
					
		<!-- Author:duxiaolan Date:2014/11/21 jiangsu-->			
	
			 <c:if test="${fn:contains(lstDomainInPatient,treatmentOrder.patientDomain)&&fn:contains(lstVisitTypeInPatient,visitTypeCode)}">
						<c:if test="${fn:length(nursingOperation)!=0}">
							<li><div class="tabseperator"></div></li>
							<li class="headtitle"><a href="#tabs-2" class="lj">操作记录</a></li>
						</c:if>
				
				</c:if>
				</ul>
				
				<c:if test="${showClosedCycle}">
					<!-- 医嘱跟踪链接 -->
					<div id="tabs-3" class="tabcontainer">
						 <iframe id="dialogFrameDetail" style="width: 100%; height: 100%;"
									src="${linkUrl}" frameborder="1"> 
						 </iframe>
					</div>
				</c:if>
			
			<c:if test="${!empty treatmentOrder}">
				<div id="tabs-1" class="tabcontainer">
					<table width="100%" cellpadding="2" cellspacing="1"
						style="border: solid 1px #c0ddea;border-collapse:collapse;border-bottom:0px;">
	
						<tr class="odd">
							<td class="label">医嘱编号:</td>
							<td class="dataitem">${treatmentOrder.orderLid}</td>
							<td class="label">医嘱状态:</td>
							<td class="dataitem">${treatmentOrder.orderStatusName}</td>
						</tr>
						<tr>
							<td class="label">病区:</td>
							<td class="dataitem">${treatmentOrder.wardName}</td>
							<td class="label">床号:</td>
							<td class="dataitem">${treatmentOrder.bedNo}</td>
						</tr>
						<tr class="odd">
							<td class="label">医嘱类型:</td>
							<td class="dataitem">${treatmentOrder.orderTypeName}</td>
							<td class="label">医嘱名称:</td>
							<td class="dataitem">${treatmentOrder.itemName}</td>
						</tr>
						<tr>
							<td class="label">医嘱序号:</td>
							<td class="dataitem">${treatmentOrder.orderSeqnum}</td>
							<td class="label">医嘱交费状态:</td>
							<td class="dataitem" colspan="3">${treatmentOrder.chargeStatusName}</td>
<%-- 							<c:choose>
								<c:when test="${treatmentOrder.chargeStatusCode == Constants.CHARGE_STATUS_NOTCHARGE}">
									<td class="dataitem" colspan="3"></td>
								</c:when>
								<c:otherwise>
									<td class="dataitem" colspan="3">${treatmentOrder.chargeStatusName}</td>
								</c:otherwise>
							</c:choose> --%>
						</tr>
						<tr >
							<td class="label">开嘱科室:</td>
							<td class="dataitem">${treatmentOrder.orderDeptName}</td>
							<td class="label">长期或临时:</td>
							<td class="dataitem"><ref:translate
									domain="${Constants.DOMAIN_TEMPORARY_FLAG}"
									code="${treatmentOrder.temporaryFlag}" /></td>
						</tr>
						<tr class="odd">
							<td class="label">执行科室:</td>
							<td class="dataitem">${treatmentOrder.execDeptName}</td>
							<td class="label">执行频率:</td>
							<td class="dataitem">${treatmentOrder.execFreqName}</td>
						</tr>
						<tr class="odd">
							<td class="label">开嘱人:</td>
							<td class="dataitem">${treatmentOrder.orderPersonName}</td>
						<!-- $Author :chang_xuewen
     			 			 $Date : 2013/10/31 11:00
     			 			 $[BUG]0038735 MODIFY BEGIN -->
							<td class="label">医嘱录入时间:</td>
						<!-- $[BUG]0038735 MODIFY END -->
							<td class="dataitem"><fmt:formatDate
									value="${treatmentOrder.orderTime}" type="both"
									pattern="yyyy-MM-dd HH:mm" /></td>
						</tr>
						<tr >
							<td class="label">医生确认人:</td>
							<td class="dataitem">${treatmentOrder.doctorConfirmPersonName}</td>
							<td class="label">医生确认时间:</td>
							<td class="dataitem"><fmt:formatDate
									value="${treatmentOrder.doctorConfirmTime}" type="both"
									pattern="yyyy-MM-dd HH:mm" /></td>
						</tr>
						<tr>
							<td class="label">护士确认人:</td>
							<td class="dataitem">${treatmentOrder.nurseConfirmPersonName}</td>
							<td class="label">护士确认时间:</td>
							<td class="dataitem"><fmt:formatDate
									value="${treatmentOrder.nurseConfirmTime}" type="both"
									pattern="yyyy-MM-dd HH:mm" /></td>
						</tr>
						<tr>
							<td class="label">停嘱人:</td>
							<td class="dataitem">${treatmentOrder.stopPersonName}</td>
							<td class="label">停嘱时间:</td>
							<td class="dataitem"><fmt:formatDate
									value="${treatmentOrder.stopTime}" type="both"
									pattern="yyyy-MM-dd HH:mm" /></td>
						</tr>
						<tr>
							<td class="label">取消人:</td>
							<td class="dataitem">${treatmentOrder.cancelPersonName}</td>
							<td class="label">取消时间:</td>
							<td class="dataitem"><fmt:formatDate
									value="${treatmentOrder.cancelTime}" type="both"
									pattern="yyyy-MM-dd HH:mm" /></td>
						</tr>
						<tr class="odd">
							<td class="label">数量:</td>
							<td class="dataitem">${treatmentOrder.quantity}</td>
							<td class="label">单位:</td>
							<td class="dataitem">${treatmentOrder.unit}</td>
						</tr>
						<tr>
							<td class="label">规格:</td>
							<td class="dataitem">${treatmentOrder.specification}</td>
					<!-- 		<td class="label">是否药观:</td>
							<td class="dataitem"><ref:translate
							domain="${Constants.DOMAIN_BOOLEAN_UNCERTAIN}"
							code="${treatmentOrder.medViewFlag}" /></td>
						</tr>
						<tr>
							<td class="label">是否适应:</td>
							<td class="dataitem"><ref:translate
							domain="${Constants.DOMAIN_BOOLEAN_UNCERTAIN}"
							code="${treatmentOrder.adaptiveFlag}"/></td>
							<td class="label">是否皮试:</td>
							<td class="dataitem"><ref:translate
							domain="${Constants.DOMAIN_BOOLEAN_UNCERTAIN}"
							code="${treatmentOrder.skinTestFlag}" /></td>
						</tr>
						<tr class="odd">
							<td class="label">是否加急:</td>
							<td class="dataitem"><ref:translate
							domain="${Constants.DOMAIN_BOOLEAN_UNCERTAIN}"
							code="${treatmentOrder.urgentFlag}"/></td>
							 -->
							<td class="label">嘱托信息:</td>
							<td class="dataitem">${treatmentOrder.comments}</td>
						</tr>
						<!--$Author : wu_jianfeng
							$Date : 2013/3/13 18:06$
                            [BUG]0014531 MODIFY BEGIN 
                        -->
				<!--   <tr>
							<td class="label">互斥医嘱类型名称:</td>
							<td class="dataitem">${treatmentOrder.mutexesOrderTypeName}</td>
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
						<tr>
							<td class="label">医嘱备注:</td>
							<td class="dataitem">${treatmentOrder.memo}</td>
						<c:if test="${Constants.TRUE_ORG_CODE == Constants.DISABLE_ORG_CODE}">						
							<td class="label">医疗机构:</td>
							<td class="dataitem"><ref:translate domain="${Constants.DOMAIN_ORG_CODE}" code="${treatmentOrder.orgCode}"/></td>					
						</c:if>
						<c:if test="${Constants.TRUE_ORG_CODE != Constants.DISABLE_ORG_CODE}">
							<td class="label"></td>
							<td class="dataitem"></td>
						</c:if>
						</tr>
						<!-- [BUG]0014531 MODIFY END -->
	
					</table>
				</div>
			</c:if>
		<!-- Author:duxiaolan Date:2014/11/21 jiangsu-->
			 <c:if test="${fn:contains(lstDomainInPatient,treatmentOrder.patientDomain)&&fn:contains(lstVisitTypeInPatient,visitTypeCode)}">
				<c:if test="${fn:length(nursingOperation)!=0}">
					<div id="tabs-2" class="tabcontainer">
						<table id="Table1" style="width: 100%;" class="table"
							cellpadding="2" cellspacing="1">
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
									<c:if test="${status.count%2==0}">class="even"</c:if>
									>
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
