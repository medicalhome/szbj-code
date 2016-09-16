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
   	 
		var orderSn = '${careOrder.orderSn}';
   	 
		closeTab("#moreTabs", orderSn);
   	 
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
	<div id="dialog">
		<div id="mainContent">
			<div id="_${careOrder.orderSn}" name="selectTabs">
				
					<ul>
						<c:if test="${!empty careOrder}">
							<li><div class="tabseperator">&nbsp;&nbsp;</div></li>
							<li class="headtitle"><a href="#tabs-1" class="lj">护理医嘱详细信息</a></li>
						</c:if>
						<c:if test="${showClosedCycle}">
							<li><div class="tabseperator">&nbsp;&nbsp;</div></li>
							<li class="headtitle"><a href="#tabs-3" class="lj">医嘱跟踪</a></li>
						</c:if>	
						 <c:if test="${fn:contains(lstDomainInPatient,careOrder.patientDomain)&&fn:contains(lstVisitTypeInPatient,visitTypeCode)}">
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
				
				<c:if test="${!empty careOrder}">
					<div id="tabs-1" class="tabcontainer">
						<table width="100%" cellpadding="2" cellspacing="1"
							style="border: solid 1px #c0ddea;border-collapse:collapse;border-bottom:0px;">
							<tr>
								<td class="label">医嘱编号:</td>
								<td class="dataitem">${careOrder.orderLid}</td>
								<td class="label">医嘱状态:</td>
								<td class="dataitem">${careOrder.orderStatusName}</td>								
							</tr>
							<tr>
								<td class="label">病区:</td>
								<td class="dataitem">${careOrder.wardsName}</td>
								<td class="label">床号:</td>
								<td class="dataitem">${careOrder.bedNo}</td>								
							</tr>
							<tr>
								<td class="label">医嘱序号:</td>
								<td class="dataitem">${careOrder.orderSeqnum}</td>
								<td class="label">医嘱类型:</td>
								<td class="dataitem">${careOrder.orderTypeName}</td>								
							</tr>
							<tr>
								<td class="label">医嘱名称:</td>
								<td class="dataitem">${careOrder.orderName}</td>
								<td class="label">长期或临时:</td>
								<td class="dataitem"><ref:translate
										domain="${Constants.DOMAIN_TEMPORARY_FLAG}"
										code="${careOrder.usage}" /></td>
							</tr>
						
							<tr>
								<td class="label">执行科室:</td>
								<td class="dataitem">${careOrder.execDeptName}</td>
								<!-- $Author :chang_xuewen
     								 $Date : 2013/10/31 11:00
     								 $[BUG]0038735 MODIFY BEGIN -->	
								<td class="label">医嘱预定开始时间:</td>
								<!-- $[BUG]0038735 MODIFY BEGIN -->	
								<td class="dataitem"><fmt:formatDate value="${careOrder.orderStartTime}" type="both" pattern="yyyy-MM-dd HH:mm" /></td>
							</tr>
							<tr>
								<td class="label">医嘱结束时间:</td>
								<td class="dataitem"><fmt:formatDate value="${careOrder.orderEndTime}" type="both" pattern="yyyy-MM-dd HH:mm" /></td>
								<td class="label">嘱托:</td>
								<td class="dataitem">${careOrder.comments}</td>
							</tr>
							<tr>
								<td class="label">描述:</td>
								<td class="dataitem">${careOrder.description}</td>
								<td class="label">开嘱人:</td>
								<td class="dataitem">${careOrder.orderPersonName}</td>
							</tr>
							<tr>
							<!-- $Author :chang_xuewen
     			 				 $Date : 2013/10/31 11:00
     			 				 $[BUG]0038735 MODIFY BEGIN -->
								<td class="label">医嘱录入时间:</td>
							<!-- $[BUG]0038735 MODIFY END -->
								<td class="dataitem"><fmt:formatDate
										value="${careOrder.inputTime}" type="both"
										pattern="yyyy-MM-dd HH:mm" /></td>
								<td class="label">医生确认人:</td>
								<td class="dataitem">${careOrder.doctorConfirmPersonName}</td>
							</tr>
							<tr>
								<td class="label">医生确认时间:</td>
								<td class="dataitem"><fmt:formatDate
										value="${careOrder.doctorConfirmTime}" type="both"
										pattern="yyyy-MM-dd HH:mm" /></td>
								<td class="label">护士确认人:</td>
								<td class="dataitem">${careOrder.nurseConfirmPersonName}</td>
							</tr>
							<tr>
								<td class="label">护士确认时间:</td>
								<td class="dataitem"><fmt:formatDate
										value="${careOrder.nurseConfirmTime}" type="both"
										pattern="yyyy-MM-dd HH:mm" /></td>
								<td class="label">停嘱人:</td>
								<td class="dataitem">${careOrder.stopPersonName}</td>
							</tr>
							<tr>
								<td class="label">停嘱时间:</td>
								<td class="dataitem"><fmt:formatDate
										value="${careOrder.stopTime}" type="both"
										pattern="yyyy-MM-dd HH:mm" /></td>
								<td class="label">取消人:</td>
								<td class="dataitem">${careOrder.cancelPersonName}</td>
							</tr>
							<tr>
								<td class="label">取消时间:</td>
								<td class="dataitem"><fmt:formatDate
										value="${careOrder.cancelTime}" type="both"
										pattern="yyyy-MM-dd HH:mm" /></td>
								<td class="label">数量:</td>
								<td class="dataitem">${careOrder.quantity}</td>
							</tr>
							<tr>
					
								<td class="label">执行频率:</td>
								<td class="dataitem">${careOrder.freqName}</td>
							<!-- 	<td class="label">是否加急:</td>
								<td class="dataitem"><ref:translate
											domain="${Constants.DOMAIN_BOOLEAN_UNCERTAIN}"
											code="${careOrder.urgentFlag}" /></td>
							</tr>
							<tr>
								<td class="label">是否药观:</td>
								<td class="dataitem"><ref:translate
											domain="${Constants.DOMAIN_BOOLEAN_UNCERTAIN}"
											code="${careOrder.medViewFlag}" /></td>
								<td class="label">是否皮试:</td>
								<td class="dataitem"><ref:translate
											domain="${Constants.DOMAIN_BOOLEAN_UNCERTAIN}"
											code="${careOrder.skinTestFlag}" /></td>	
							</tr>
							<tr>
								<td class="label">是否适应:</td>
								<td class="dataitem"><ref:translate
											domain="${Constants.DOMAIN_BOOLEAN_UNCERTAIN}"
											code="${careOrder.adaptiveFlag}" /></td> -->
								<td class="label">医嘱交费状态:</td>
								<td class="dataitem">${careOrder.chargeStatusName}</td>
<%-- 								<c:choose>
									<c:when test="${careOrder.chargeStatusCode == Constants.CHARGE_STATUS_NOTCHARGE}">
										<td class="dataitem"></td>
									</c:when>
									<c:otherwise>
										<td class="dataitem">${careOrder.chargeStatusName}</td>
									</c:otherwise>
								</c:choose> --%>
							</tr>
							
							<!--$Author : wu_jianfeng
								$Date : 2013/3/13 18:06$
		                           [BUG]0014531 MODIFY BEGIN 
		                       -->
						<!--  	<tr>
								<td class="label">互斥医嘱类型:</td>
								<td class="dataitem">${careOrder.mutexesOrderTypeName}</td>
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
							<c:if test="${Constants.TRUE_ORG_CODE == Constants.DISABLE_ORG_CODE}">
						   	<tr>
								
							<!-- 	<td class="label">开嘱科室:</td>
								<td class="dataitem">${careOrder.orderDeptName}</td>
							</tr>
						    <tr> -->
							    <td class="label">医疗机构:</td>
							    <td class="dataitem"><ref:translate domain="${Constants.DOMAIN_ORG_CODE}" code="${careOrder.orgCode}"/></td>
							    <td class="label"></td>
							    <td class="dataitem"></td>
						    </tr>
						    </c:if>
							<!-- [BUG]0014531 MODIFY END -->
						</table>
					</div>
				</c:if>
				<c:if test="${fn:contains(lstDomainInPatient,careOrder.patientDomain)&&fn:contains(lstVisitTypeInPatient,visitTypeCode)}">
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
	</div>
</body>
</html>
