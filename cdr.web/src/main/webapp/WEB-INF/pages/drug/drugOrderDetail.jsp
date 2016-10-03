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
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Cache-Control" content="no-cache"/>
    <meta http-equiv="Expires" content="0"/>
<title>用药医嘱详细信息</title>
<script type="text/javascript" src="../scripts/drug/drugMenuPart.js"></script>
<script type="text/javascript">
	/*$Author :chang_xuewen
	 * $Date : 2013/08/07 16:20
	 * $[BUG]0035740 ADD BEGIN*/
	function returnParentName()
	{
		return '${parentDrugOrder.drugName}';
	}
	function returnMutexesName()
	{
		return '${mutexesOrderName}';
	}
	/*$[BUG]0035740 ADD END*/
    function getFlag()
	{
		return '${flag}';
	}

	function getOrderSn()
	{
		return '${orderSn}';
	}

     $(document).ready(function(){
    	 
    	 var orderSn = '${drugOrder.orderSn}';
    	 
    	 closeTab("#moreTabs",orderSn);
     });
</script>
</head>
<body>
	<div id="dialog">
		<div id="mainContent">
			<div id="_${drugOrder.orderSn}" name="selectTabs">
				<ul>
					<li><div class="tabseperator">&nbsp;</div></li>
					<li class="headtitle"><a href="#tabs-1" class="lj">医嘱信息</a></li>
					
				<c:if test="${showClosedCycle}">
					<li><div class="tabseperator">&nbsp;</div></li>
					<li class="headtitle"><a href="#tabs-5" class="lj">医嘱跟踪</a></li>
				</c:if>
					
					<c:choose>
						<c:when
							test="${drugOrder.visitTypeCode==Constants.VISIT_TYPE_CODE_OUTPATIENT}">
							<c:if test="${!empty Prescription}">
								<li><div class="tabseperator"></div></li>
								<li class="headtitle" ><a href="#tabs-2" class="lj">处方信息</a></li>
							</c:if>
							<c:if test="${fn:length(Dispensing)!=0}">
								<li><div class="tabseperator"></div></li>
								<li class="headtitle"><a href="#tabs-3" class="lj">发药记录</a></li>
							</c:if>
						</c:when>
						<c:when
							test="${drugOrder.visitTypeCode==Constants.VISIT_TYPE_CODE_EMERGENCY}">
							<c:if test="${!empty Prescription}">
								<li><div class="tabseperator"></div></li>
								<li class="headtitle"><a href="#tabs-2" class="lj">处方信息</a></li>
							</c:if>
							<c:if test="${fn:length(Dispensing)!=0}">
								<li><div class="tabseperator"></div></li>
								<li class="headtitle"><a href="#tabs-3" class="lj">发药记录</a></li>
							</c:if>
						</c:when>
						<c:when
							test="${drugOrder.visitTypeCode==Constants.VISIT_TYPE_CODE_INPATIENT}">
							<c:if test="${fn:length(Dispensing)!=0}">
								<li><div class="tabseperator"></div></li>
								<li class="headtitle"><a href="#tabs-2" class="lj">发药记录</a></li>
							</c:if>
							<c:if test="${fn:length(list_ad)!=0}">
								<li><div class="tabseperator"></div></li>
								<li class="headtitle"><a href="#tabs-3" class="lj">执行记录</a></li>
							</c:if>
						</c:when>
					</c:choose>
					<c:if test="${drugOrder.orderType==Constants.HERBAL_MEDICINE_CLASS}">
						<c:if test="${fn:length(hf)!=0}">
							<li><div class="tabseperator"></div></li>
							<li class="headtitle"><a href="#tabs-4" class="lj">草药配方 </a></li>
						</c:if>
					</c:if>
				</ul>
				
				<c:if test="${showClosedCycle}">
					<!-- 所有的药物医嘱都需要显示"医嘱跟踪" -->
					<div id="tabs-5" class="tabcontainer" style="width: 100%; height:400px;">
					     <iframe id="dialogFrameDetail" style="width: 100%; height: 100%;"
									src="${linkUrl}" frameborder="1"> 
						 </iframe>
					</div>
				</c:if>
				
				<c:if test="${!empty drugOrder}">
					<div id="tabs-1" class="tabcontainer">
						<table width="100%" cellpadding="2" cellspacing="1"
							style="border: solid 1px #c0ddea; border-collapse: collapse; border-bottom: 0px;">
							<tr>
								<td class="label">药物编码:</td>
								<td class="dataitem">${drugOrder.drugCode}</td>
								<td class="label">药物商品名:</td>
								<td class="dataitem">
									<!-- $Author :jin_peng
										 $Date : 2013/02/19 11:20$
										 [BUG]0013981 MODIFY BEGIN --> <c:choose>
										<c:when
											test="${drugOrder.orderType==Constants.HERBAL_MEDICINE_CLASS}"></c:when>
										<c:otherwise>${drugOrder.drugName}</c:otherwise>
									</c:choose>
								</td>
								<!-- [BUG]0013981 MODIFY END -->
							</tr>
							<tr>
								<td class="label">药物通用名:</td>
								<td class="dataitem">
									<!-- $Author :jin_peng
											 $Date : 2013/02/19 11:20$
											 [BUG]0013981 MODIFY BEGIN --> <c:choose>
										<c:when
											test="${drugOrder.orderType==Constants.HERBAL_MEDICINE_CLASS}">草药</c:when>
										<c:otherwise>${drugOrder.approvedDrugName}</c:otherwise>
									</c:choose> <!-- [BUG]0013981 MODIFY END -->
								</td>
								<td class="label">药物类别:</td>
								<td class="dataitem">${drugOrder.medicineClassName}</td>
							</tr>
							<tr>
								<td class="label">药物类型:</td>
								<td class="dataitem">${drugOrder.medicineTypeName}</td>
								<td class="label">用药途径:</td>
								<td class="dataitem">${drugOrder.routeName}</td>
							</tr>
							<tr>
								<td class="label">次剂量:</td>
								<td class="dataitem">${drugOrder.dosage}${drugOrder.dosageUnit}</td>
								<td class="label">总用量:</td>
								<td class="dataitem">${drugOrder.totalDosage}${drugOrder.totalDosageUnit}</td>
							</tr>
							<tr>
								<td class="label">使用频率:</td>
								<td class="dataitem">${drugOrder.medicineFreqName}</td>
								<td class="label">天数:</td>
								<td class="dataitem">${drugOrder.days}</td>
							</tr>
							<tr>
								<td class="label">领药量:</td>
								<td class="dataitem">${drugOrder.dispensingQuantity}${drugOrder.dispensingUnit}</td>
								<td class="label">是否适应:</td>
								<td class="dataitem"><ref:translate
										domain="${Constants.DOMAIN_BOOLEAN_UNCERTAIN}"
										code="${drugOrder.adaptiveFlag}" /></td>								
							</tr>
					<!-- 		<tr>
								<td class="label">是否皮试:</td>
								<td class="dataitem"><ref:translate
										domain="${Constants.DOMAIN_BOOLEAN_UNCERTAIN}"
										code="${drugOrder.skinTestFlag}" /></td>
								<td class="label">皮试结果:</td>
								<td class="dataitem">${drugOrder.skinTestResult}</td>
							</tr>
							<tr>
								<td class="label">是否加急:</td>
								<td class="dataitem"><ref:translate
										domain="${Constants.DOMAIN_BOOLEAN_UNCERTAIN}"
										code="${drugOrder.urgentFlag}" /></td>
								<td class="label">是否药观:</td>
								<td class="dataitem"><ref:translate
										domain="${Constants.DOMAIN_BOOLEAN_UNCERTAIN}"
										code="${drugOrder.medViewFlag}" /></td>
							</tr> -->
							<tr>
								<td class="label">药物剂型:</td>
								<td class="dataitem">${drugOrder.medicineForm}</td>
								<td class="label">长期或临时:</td>
								<td class="dataitem"><ref:translate
										domain="${Constants.DOMAIN_TEMPORARY_FLAG}"
										code="${drugOrder.temporaryFlag}" /></td>
							</tr>
							<c:if test="${drugOrder.temporaryFlag == Constants.LONG_TERM_FLAG}">
								<tr>
								<!-- $Author :chang_xuewen
     								 $Date : 2013/10/31 11:00
     								 $[BUG]0038735 MODIFY BEGIN -->								
									<td class="label">医嘱预定开始时间:</td>
								<!-- $[BUG]0038735 MODIFY END -->	
									<td class="dataitem"><fmt:formatDate
											value="${drugOrder.orderStartTime}" type="date"
											pattern="yyyy-MM-dd HH:mm" /></td>
									<td class="label">医嘱结束时间:</td>
									<td class="dataitem"><fmt:formatDate
											value="${drugOrder.orderEndTime}" type="date"
											pattern="yyyy-MM-dd HH:mm" /></td>
								</tr>
							</c:if>
							<tr>
								<td class="label">开医嘱科室:</td>
								<td class="dataitem">${drugOrder.orderDeptName}</td>
								<td class="label">执行科室:</td>
								<td class="dataitem">${drugOrder.execDeptName}</td>
							</tr>
							<tr>
								<td class="label">医嘱编号:</td>
								<td class="dataitem">${drugOrder.orderLid}</td>
								<td class="label">医嘱序号:</td>
								<td class="dataitem">${drugOrder.orderSeqnum}</td>
							</tr>
							<tr>
								<td class="label">开医嘱人:</td>
								<td class="dataitem">${drugOrder.orderPersonName}</td>
							<!-- $Author :chang_xuewen
     			 				 $Date : 2013/10/31 11:00
     			 				 $[BUG]0038735 MODIFY BEGIN -->	
								<td class="label">医嘱录入时间:</td>
							<!-- $[BUG]0038735 MODIFY END -->	
								<td class="dataitem"><fmt:formatDate
										value="${drugOrder.orderTime}" type="date"
										pattern="yyyy-MM-dd HH:mm" /></td>
							</tr>
							<tr>
								<td class="label">医嘱医生确认人:</td>
								<td class="dataitem">${drugOrder.doctorConfirmPersonName}</td>
								<td class="label">医嘱医生确认时间:</td>
								<td class="dataitem"><fmt:formatDate
										value="${drugOrder.doctorConfirmTime}" type="date"
										pattern="yyyy-MM-dd HH:mm" /></td>
							</tr>
						<tr>
								<td class="label">医嘱护士确认人:</td>
								<td class="dataitem">${drugOrder.nurseConfirmPersonName}</td>
								<td class="label">医嘱护士确认时间:</td>
								<td class="dataitem"><fmt:formatDate
										value="${drugOrder.nurseConfirmTime}" type="date"
										pattern="yyyy-MM-dd HH:mm" /></td>
							</tr>
							<tr>
								<td class="label">停医嘱人:</td>
								<td class="dataitem">${drugOrder.stopPersonName}</td>
								<td class="label">停嘱时间:</td>
								<td class="dataitem"><fmt:formatDate
										value="${drugOrder.stopTime}" type="date"
										pattern="yyyy-MM-dd HH:mm" /></td>
							</tr>
							<tr>
								<td class="label">医嘱取消人:</td>
								<td class="dataitem">${drugOrder.cancelPersonName}</td>
								<td class="label">取消时间:</td>
								<td class="dataitem"><fmt:formatDate
										value="${drugOrder.cancelTime}" type="date"
										pattern="yyyy-MM-dd HH:mm" /></td>
							</tr>
							<tr>
								<td class="label">病区:</td>
								<td class="dataitem">${drugOrder.wardName}</td>
								<td class="label">嘱托:</td>
								<td class="dataitem">${drugOrder.comments}</td>
							<!-- <td class="label">互斥医嘱类型:</td>
								<td class="dataitem">${drugOrder.mutexesOrderTypeName}</td>		 -->						
							</tr>
							<tr>
								<td class="label">医嘱状态:</td>
								<td class="dataitem">${drugOrder.orderStatusName }</td>
								<td class="label">医嘱交费状态:</td>
								<td class="dataitem">${drugOrder.chargeStatusName}</td>
<%-- 								<c:choose>
									<c:when
										test="${drugOrder.chargeStatusCode == Constants.CHARGE_STATUS_NOTCHARGE}">
										<td class="dataitem"></td>
									</c:when>
									<c:otherwise>
										<td class="dataitem">${drugOrder.chargeStatusName}</td>
									</c:otherwise>
								</c:choose> --%>
							</tr>
							<!--$Author : wu_jianfeng
									$Date : 2013/3/13 18:06$
	                                [BUG]0014531 ADD BEGIN 
	                            -->
						<!--	<tr>
								<td class="label">父医嘱:</td>
								<td class="dataitem"><a href="javascript:void(0);"
									onclick="showParentOrder(this,'${patientSn}','${parentDrugOrder.orderSn}','${parentDrugOrder.drugName}','${parentDrugOrder.temporaryFlag}','#moreTabs',event);">${parentDrugOrder.drugName}</a></td>
								<td class="label">互斥医嘱:</td>
								<td class="dataitem"><c:choose>
										<c:when test="${mutexesOrderType != ''}">
											<a href="javascript:void(0);"
												onclick="showMutexesOrder(this,'${mutexesOrderType}','${mutexesOrderSn}','${mutexesOrderName}','${patientSn}','#moreTabs',event)">
												${mutexesOrderName} </a>
										</c:when>
									</c:choose></td>
							</tr>  -->
							<!-- [BUG]0014531 ADD END -->
							<c:if test="${Constants.TRUE_ORG_CODE == Constants.DISABLE_ORG_CODE}">
								<tr>								
									<td class="label">医疗机构:</td>
									<td class="dataitem">${drugOrder.orgName}</td>
									<td class="label"></td>
									<td class="dataitem"></td>
								</tr>
							</c:if>
						</table>
					</div>
				</c:if>
				<c:choose>
					<c:when
						test="${drugOrder.visitTypeCode==Constants.VISIT_TYPE_CODE_OUTPATIENT}">
						<c:if test="${!empty Prescription}">
							<div id="tabs-2" class="tabcontainer">
								<table width="100%" cellpadding="2" cellspacing="1"
									style="border: solid 1px #c0ddea; border-collapse: collapse; border-bottom: 0px;">
	
									<tr>
										<td class="label">处方号:</td>
										<td class="dataitem">${Prescription.prescriptionNo}</td>
										<td class="label">年龄:</td>
										<td class="dataitem">${Prescription.patientAge}</td>
									</tr>
									<tr>
										<td class="label">处方类别:</td>
										<td class="dataitem">${Prescription.prescriptionClassName}</td>
										<td class="label">处方类型:</td>
										<td class="dataitem">${Prescription.prescriptionTypeName}</td>
									</tr>
									<tr>
										<td class="label">诊断信息:</td>
										<td class="dataitem">${diagnosis}</td>
										<td class="label">费用:</td>
										<td class="dataitem">${Prescription.price}</td>
									</tr>
									<tr>
										<td class="label">开方日期:</td>
										<td class="dataitem"><fmt:formatDate
												value="${Prescription.orderTime}" type="date"
												pattern="yyyy-MM-dd HH:mm" /></td>
										<td class="label"></td>
										<td class="dataitem"></td>
									</tr>
									<tr>
										<td class="label">审核人:</td>
										<td class="dataitem">${Prescription.reviewPersonName}</td>
										<td class="label">审核时间:</td>
										<td class="dataitem"><fmt:formatDate
												value="${Prescription.reviewTime}" type="date"
												pattern="yyyy-MM-dd HH:mm" /></td>
									</tr>
	
								</table>
	
							</div>
						</c:if>
						<c:if test="${fn:length(Dispensing)!=0}">
							<div id="tabs-3" class="tabcontainer">
								<table id="tblid" cellpadding="2" cellspacing="1" class="table"
									width="100%" align="center">
									<tr class="tabletitle">
										<td height="24" align="center">配药人</td>
										<td height="24" align="center">发药人</td>
										<td height="24" align="center">发药时间</td>
										<td height="24" align="center">执行科室</td>
										<td height="24" align="center">批次号</td>
										<td height="24" align="center">发药数量</td>
										<td height="24" align="center">领量单位</td>
									</tr>
									<c:forEach items="${Dispensing}" var="Dispensing"
										varStatus="status">
										<tr <c:if test="${status.count%2==1}">class="odd"</c:if>
											<c:if test="${status.count%2==0}">class="even"</c:if>>
											<td height="24" align="center">${Dispensing.dispenserName}</td>
											<td height="24" align="center">${Dispensing.supplyPersonName}</td>
											<td height="24" align="center"><fmt:formatDate
													value="${Dispensing.supplyTime}" type="date"
													pattern="yyyy-MM-dd HH:mm" /></td>
											<td height="24" align="center">${Dispensing.execDeptName}</td>
											<td height="24" align="center">${Dispensing.batchNo}</td>
											<td height="24" align="center">${Dispensing.dispensingQuantity}</td>
											<td height="24" align="center">${Dispensing.dispensingUnit}</td>
										</tr>
									</c:forEach>
								</table>
	
							</div>
						</c:if>
					</c:when>
	
					<c:when
						test="${drugOrder.visitTypeCode==Constants.VISIT_TYPE_CODE_EMERGENCY}">
						<c:if test="${!empty Prescription}">
							<div id="tabs-2" class="tabcontainer">
								<table width="100%" cellpadding="2" cellspacing="1"
									style="border: solid 1px #c0ddea; border-collapse: collapse; border-bottom: 0px;">
									<tr>
										<td class="label">处方号:</td>
										<td class="dataitem">${Prescription.prescriptionNo}</td>
										<td class="label">年龄:</td>
										<td class="dataitem">${Prescription.patientAge}</td>
									</tr>
									<tr>
										<td class="label">处方类别:</td>
										<td class="dataitem">${Prescription.prescriptionClassName}</td>
										<td class="label">处方类型:</td>
										<td class="dataitem">${Prescription.prescriptionTypeName}</td>
									</tr>
									<tr>
										<td class="label">诊断信息:</td>
										<td class="dataitem">${diagnosis}</td>
										<td class="label">费用:</td>
										<td class="dataitem">${Prescription.price}</td>
									</tr>
									<tr>
										<td class="label">开方日期:</td>
										<td class="dataitem"><fmt:formatDate
												value="${Prescription.orderTime}" type="date"
												pattern="yyyy-MM-dd HH:mm" /></td>
										<td class="label"></td>
										<td class="dataitem"></td>
									</tr>
									<tr>
										<td class="label">审核人:</td>
										<td class="dataitem">${Prescription.reviewPersonName}</td>
										<td class="label">审核时间:</td>
										<td class="dataitem"><fmt:formatDate
												value="${Prescription.reviewTime}" type="date"
												pattern="yyyy-MM-dd HH:mm" /></td>
									</tr>
								</table>
							</div>
						</c:if>
						<c:if test="${fn:length(Dispensing)!=0}">
							<div id="tabs-3" class="tabcontainer">
								<table id="tblid" cellpadding="2" cellspacing="1" class="table"
									width="100%" align="center">
	
									<tr class="tabletitle">
										<td height="24" align="center">配药人</td>
										<td height="24" align="center">发药人</td>
										<td height="24" align="center">发药时间</td>
										<td height="24" align="center">执行科室</td>
										<td height="24" align="center">批次号</td>
										<td height="24" align="center">发药数量</td>
										<td height="24" align="center">领量单位</td>
									</tr>
									<c:if test="${fn:length(Dispensing)==0}">
										<tr>
											<td colspan="6" align="center" class="odd" height="24">没有相关数据！</td>
										</tr>
									</c:if>
									<c:forEach items="${Dispensing}" var="Dispensing"
										varStatus="status">
										<tr <c:if test="${status.count%2==1}">class="odd"</c:if>
											<c:if test="${status.count%2==0}">class="even"</c:if>>
											<td>${Dispensing.dispenserName}</td>
											<td>${Dispensing.supplyPersonName}</td>
											<td><fmt:formatDate value="${Dispensing.supplyTime}"
													type="date" pattern="yyyy-MM-dd HH:mm" /></td>
											<td>${Dispensing.execDeptName}</td>
											<td height="24" align="center">${Dispensing.batchNo}</td>
											<td height="24" align="center">${Dispensing.dispensingQuantity}</td>
											<td height="24" align="center">${Dispensing.dispensingUnit}</td>
										</tr>
									</c:forEach>
								</table>
							</div>
						</c:if>
					</c:when>
	
					<c:when
						test="${drugOrder.visitTypeCode==Constants.VISIT_TYPE_CODE_INPATIENT}">
						<c:if test="${fn:length(Dispensing)!=0}">
							<div id="tabs-2" class="tabcontainer">
								<table id="tblid" cellpadding="2" cellspacing="1" class="table"
									width="100%" align="center">
									<tr class="tabletitle">
										<td height="24" align="center">配药人</td>
										<td height="24" align="center">发药人</td>
										<td height="24" align="center">发药时间</td>
										<td height="24" align="center">执行科室</td>
										<td height="28" align="center">批次号</td>
										<td height="28" align="center">发药数量</td>
										<td height="24" align="center">领量单位</td>
									</tr>
									<c:forEach items="${Dispensing}" var="Dispensing"
										varStatus="status">
										<tr <c:if test="${status.count%2==1}">class="odd"</c:if>
											<c:if test="${status.count%2==0}">class="even"</c:if>>
											<td height="24" align="center">${Dispensing.dispenserName}</td>
											<td height="24" align="center">${Dispensing.supplyPersonName}</td>
											<td height="24" align="center"><fmt:formatDate
													value="${Dispensing.supplyTime}" type="date"
													pattern="yyyy-MM-dd HH:mm" /></td>
											<td height="24" align="center">${Dispensing.execDeptName}</td>
											<td height="24" align="center">${Dispensing.batchNo}</td>
											<td height="24" align="center">${Dispensing.dispensingQuantity}</td>
											<td height="24" align="center">${Dispensing.dispensingUnit}</td>
										</tr>
									</c:forEach>
								</table>
							</div>
						</c:if>
						<c:if test="${fn:length(list_ad)!=0}">
							<div id="tabs-3" class="tabcontainer">
								<table id="tblid" cellpadding="2" cellspacing="1" class="table"
									width="100%" align="center">
									<tr class="tabletitle">
										<td height="24" align="center">药物名称</td>
										<td height="24" align="center">用药途径</td>
										<td height="24" align="center">次剂量</td>
										<td height="24" align="center">执行人</td>
										<td height="24" align="center">用药时间</td>
										<td height="24" align="center">床号</td>
										<td height="24" align="center">科室</td>
									</tr>
									<c:forEach items="${list_ad}" var="list_ad" varStatus="status">
										<c:if test="${not empty list_ad}">
											<tr <c:if test="${status.count%2==1}">class="odd"</c:if>
												<c:if test="${status.count%2==0}">class="even"</c:if>>
												<td height="24" align="center">${list_ad.medicineName}</td>
												<td height="24" align="center">${list_ad.medicineForm}</td>
												<td height="24" align="center">${list_ad.dosage}${list_ad.dosageUnit}</td>
												<td height="24" align="center">${list_ad.operatorName}</td>
												<td height="24" align="center"><fmt:formatDate
														value="${list_ad.medicineTime}" type="date"
														pattern="yyyy-MM-dd HH:mm" /></td>
												<td height="24" align="center">${list_ad.bedNo}</td>
												<td height="24" align="center">${list_ad.deptName}</td>
											</tr>
										</c:if>
									</c:forEach>
								</table>
							</div>
						</c:if>
					</c:when>
				</c:choose>
				<c:if test="${drugOrder.orderType==Constants.HERBAL_MEDICINE_CLASS}">
					<c:if test="${fn:length(hf)!=0}">
						<div id="tabs-4" class="tabcontainer">
							<table id="tblid" cellpadding="2" cellspacing="1" class="table"
								width="100%" align="center">
								<tr class="tabletitle">
									<td height="24" align="center">药物名称</td>
									<td height="24" align="center">药物数量</td>
									<td height="24" align="center">煎法</td>
									<!--  Author :jia_yanqing
	                                          Date : 2012/12/26 16:00
	                                          [BUG]0012797 MODIFY BEGIN -->
									<td height="24" align="center">与付数无关</td>
								</tr>
								<c:forEach items="${hf}" var="hf" varStatus="status">
									<tr <c:if test="${status.count%2==1}">class="odd"</c:if>
										<c:if test="${status.count%2==0}">class="even"</c:if>>
										<td height="24" align="center">${hf.herbName}<c:if
												test="${hf.approvedHerbName != null}">(${hf.approvedHerbName})</c:if></td>
										<td height="24" align="center">${hf.quantity}${hf.unit}</td>
										<td height="24" align="center">${hf.decoctionMethodName}</td>
										<td height="24" align="center"><c:if
												test="${hf.numUnconcernedFlag==Constants.MEDICAL_CLASS_WESTERN}">
											    ★
											</c:if></td>
										<!-- [BUG]0012797 MODIFY END -->
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
