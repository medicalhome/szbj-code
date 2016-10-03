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
	<style>
		.jbobLabel
		{
		   font-size: 14px;
		   text-align: right;
		   padding-right: 10px;
		   heignt:30px;
		   background-color: #D5EAFF;
		   border-bottom:solid 1px #c0ddea;
		   color: #043a8f;
		}
		
		.jbobLabelEx
		{
		   font-size: 14px;
		   text-align: center;
		   padding-right: 10px;
		   heignt:30px;
		   background-color: #D5EAFF;
		   border-bottom:solid 1px #c0ddea;
		   color: #043a8f;
		}
		
		
		.jbobDataitem
		{
		   font-size: 14px;
		   font-weight: bold;
		   color: #326bc5;
		   padding-left: 5px;
		   background-color: #FFFFFF;
		   border-bottom:solid 1px #c0ddea;
		   text-align: left;
		}
	</style>
	<script>
        $(function() {            
        	//Author:chang_xuewen
        	//Date : 2013/7/30 15:30
        	//[BUG]0035345 ADD BEGIN
            var orderSn = '${visitDetail.visitSn}';
           	closeTab("#moreTabs",orderSn);
          	//[BUG]0035345 ADD END
        });
        
        
    </script>
</head>
<body>
	<div id="dialog">
		<div id="mainContent">
			<div id="_${visitDetail.visitSn}" name="selectTabs">
				<ul>
					<c:if test="${!empty visitDetail}">
						<li><div class="tabseperator">&nbsp;&nbsp;</div></li>
						<li class="headtitle"><a href="#tabs-1" class="lj" hidefocus="true">就诊信息</a></li>
					</c:if>
					<c:if test="${fn:length(transferResult)!=0}">
						<li><div class="tabseperator"></div></li>
						<li class="headtitle"><a href="#tabs-2" class="lj">转科转区转床信息</a></li>
					</c:if>
					<c:if test="${fn:length(bloodReResult)!=0}">
						<li><div class="tabseperator"></div></li>
						<li class="headtitle"><a href="#tabs-3" class="lj">用血申请单</a></li>
					</c:if>
					<c:if test="${fn:length(bloodReOrResultList)!=0}">
						<li><div class="tabseperator"></div></li>
						<li class="headtitle"><a href="#tabs-4" class="lj">取血单</a></li>
					</c:if>
					<c:if test="${showClosedCycle}">
						<li><div class="tabseperator"></div></li>
						<li class="headtitle"><a href="#tabs-7" class="lj">用血跟踪</a></li>
					</c:if>
					<c:if test="${fn:length(requestAndRecordList)!=0}">
						<li><div class="tabseperator"></div></li>
						<li class="headtitle"><a href="#tabs-6" class="lj">输血记录单</a></li>
					</c:if>
					<c:if test="${fn:length(withdrawRecordDto)!=0}">
						<li><div class="tabseperator"></div></li>
						<li class="headtitle"><a href="#tabs-5" class="lj">召回记录</a></li>
					</c:if>
				</ul>
				<c:if test="${!empty visitDetail}">
					<div id="tabs-1" class="tabcontainer">
						<table width="100%" cellpadding="2" cellspacing="1"
							style="border: solid 1px #c0ddea;border-collapse:collapse;border-bottom:0px;">
							<tr>
								<td class="label">就诊类别:</td>
								<td class="dataitem">${visitDetail.visitTypeName}</td>
								<td class="label">就诊次数:</td>
								<td class="dataitem">${visitDetail.visitTimes}</td>
							</tr>
							<tr>
								<td class="label">主治医生:</td>
								<td class="dataitem">${visitDetail.attendingDoctorName}</td>
								<td class="label">科室主任:</td>
								<td class="dataitem">${visitDetail.deptDirectorName}</td>
							</tr>
							
							<c:if test="${Constants.VISIT_TYPE_CODE_OUTPATIENT==visitDetail.visitTypeCode||Constants.VISIT_TYPE_CODE_EMERGENCY==visitDetail.visitTypeCode}">
								<tr>						
								    <td class="label">就诊日期:</td>
								    <td class="dataitem"><fmt:formatDate
											value="${visitDetail.visitDate}" type="both"
											pattern="yyyy-MM-dd HH:mm" /></td>
									<td class="label">就诊科室:</td>
									<td class="dataitem">${visitDetail.visitDeptName}</td>
								</tr>
								<tr>
									<td class="label">就诊号别:</td>
									<td class="dataitem">${visitDetail.registrationClassName}</td>
									<td class="label">费用类别:</td>
								    <td class="dataitem">${visitDetail.chargeClassName}</td>
								</tr>
							</c:if>
							
							
							<!-- $Author :bin_zhang
	                             $Date : 2012/9/04 17:23$
	                             [BUG]0009334 ADD BEGIN  -->
							
							<c:if test="${Constants.VISIT_TYPE_CODE_INPATIENT==visitDetail.visitTypeCode}">
							
								<tr>
								    <td class="label">入院状态:</td>
									<td class="dataitem">${visitDetail.urgentLevelName}</td>
									<td class="label">入院病区:</td>
									<td class="dataitem">${visitDetail.admissionWardName}</td>
								</tr>
								<tr>						
								    <td class="label">入院日期:</td>
								    <td class="dataitem"><fmt:formatDate
											value="${visitDetail.visitDate}" type="both"
											pattern="yyyy-MM-dd HH:mm" /></td>
									<td class="label">入院科室:</td>
									<td class="dataitem">${visitDetail.visitDeptName}</td>
								</tr>
								<tr>
									<td class="label">住院医生姓名:</td>
									<td class="dataitem">${visitDetail.residentDoctorName}</td>
									<td class="label">入院医生姓名:</td>
									<td class="dataitem">${visitDetail.admissionDoctorName}</td>
								</tr>
								<tr>
									<td class="label">入院床号:</td>
									<td class="dataitem">${visitDetail.admissionBedNo}</td>
									<td class="label">费用类别:</td>
									<td class="dataitem">${visitDetail.chargeClassName}</td>
								</tr>
								<!--[BUG]0009334 ADD END  -->
								<c:if test="${Constants.VISIT_STATUS_OUT_HOSPITAL==visitDetail.visitStatusCode||Constants.VISIT_STATUS_DISCHARGE_HOSPITAL==visitDetail.visitStatusCode}">
									<tr>
										<td class="label">出院科室:</td>
										<td class="dataitem">${visitDetail.dischargeDeptName}</td>
										<td class="label">出院病区:</td>
										<td class="dataitem">${visitDetail.dischargeWardName}</td>
									</tr>
									<tr>
										<td class="label">出院床号:</td>
									    <td class="dataitem">${visitDetail.dischargeBedNo}</td>
									    <td class="label"></td>
								        <td class="dataitem"></td>
									</tr>
									<tr>
										<td class="label">出院状态:</td>
										<td class="dataitem">${visitDetail.dischargeStatusName}</td>
										<td class="label">出院日期:</td>
										<td class="dataitem"><fmt:formatDate
												value="${visitDetail.dischargeDate}" type="both"
												pattern="yyyy-MM-dd HH:mm" /></td>
									</tr>
								</c:if>
								<!-- 
								// Author:jia_yanqing
                                // Date:2013/2/26 16:21
                                // [BUG]0014113 ADD BEGIN
                                 -->
								 <c:if test="${Constants.VISIT_STATUS_IN_HOSPITAL==visitDetail.visitStatusCode&&(visitDetail.admissionWardName!=visitDetail.dischargeWardName||visitDetail.visitDeptName!=visitDetail.dischargeDeptName||visitDetail.admissionBedNo!=visitDetail.dischargeBedNo)}">
								     <tr>
										<td class="label">当前科室:</td>
										<td class="dataitem">${visitDetail.dischargeDeptName}</td>
										<td class="label">当前病区:</td>
										<td class="dataitem">${visitDetail.dischargeWardName}</td>
									 </tr>
									 <tr>
										<td class="label">当前床号:</td>
									    <td class="dataitem">${visitDetail.dischargeBedNo}</td>
									    <td class="label"></td>
								        <td class="dataitem"></td>
									</tr>
								</c:if>
							
								<!--[BUG]0014113 ADD END  -->
							</c:if>
							<tr>
								<td class="label">就诊状态:</td>
								<td class="dataitem">${visitDetail.visitStatusName}</td>
								<c:if test="${Constants.TRUE_ORG_CODE == Constants.DISABLE_ORG_CODE}">
									<td class="label">医疗机构名称：</td>
									<td class="dataitem">${visitDetail.orgName}</td>
								</c:if>
								<c:if test="${Constants.TRUE_ORG_CODE != Constants.DISABLE_ORG_CODE}">
									<td class="label"></td>
									<td class="dataitem"></td>
								</c:if>
							</tr>
						</table>
					</div>
				</c:if>
				<c:if test="${fn:length(transferResult)!=0}">
					<div id="tabs-2" class="tabcontainer">
						<table id="tblid" style="width: 100%;" cellspacing="1"
							cellpadding="2" class="table">
							<tr class="tabletitle">
								<td height="28" align="center">医嘱类型</td>
								<td height="28" align="center">下嘱科室</td>
								<td height="28" align="center">转入科室</td>
								<td height="28" align="center">转入病区</td>
								<td height="28" align="center">转入床号</td>
								<td height="28" align="center">转入时间</td>
								<td height="28" align="center">转出科室</td>
								<td height="28" align="center">转出病区</td>
								<td height="28" align="center">转出床号</td>
							</tr>
							<c:forEach items="${transferResult}" var="transferResult"
								varStatus="status">
								<tr <c:if test="${status.count%2==1}">class="odd"</c:if>
									<c:if test="${status.count%2==0}">class="even"</c:if>>
									<td height="24" align="center">${transferResult.orderTypeName}</td>
									<td height="24" align="center">${transferResult.orderDeptName}</td>
									<td height="24" align="center">${transferResult.toDeptName}</td>
									<td height="24" align="center">${transferResult.toWardName}</td>
									<td height="24" align="center">${transferResult.toBed}</td>
									<td height="24" align="center"><fmt:formatDate
											value="${transferResult.execTime}" type="date"
											pattern="yyyy-MM-dd HH:mm" /></td>
									<td height="24" align="center">${transferResult.fromDeptName}</td>
									<td height="24" align="center">${transferResult.fromWardName}</td>
									<td height="24" align="center">${transferResult.fromBed}</td>
								</tr>
							</c:forEach>
						</table>
					</div>
				</c:if>
				<c:if test="${fn:length(bloodReResult)!=0}">
					<div id="tabs-3"  class="tabcontainer">
					<div id="bloodRequest03" style="width:1000px;overflow-x:auto;overflow-y:hidden;">
						<table id="Table2" style="width: 1600px;" class="table"
							cellpadding="2" cellspacing="1" >
							<tr class="tabletitle">
								<td height="28" align="center" style="table-layout:fixed;width:40px">ABO血型</td>
								<td height="28" align="center" style="table-layout:fixed;width:40px">Rh（D）</td>
								<td height="28" align="center" style="table-layout:fixed;width:80px">血液种类</td>
								<td height="28" align="center" style="table-layout:fixed;width:40px">输血量</td>
								<td height="28" align="center" style="table-layout:fixed;width:20px">输血量单位</td>
								<td height="28" align="center" style="table-layout:fixed;width:120px">输血原因和目的</td>
								<td height="28" align="center" style="table-layout:fixed;width:80px">输血日期</td>
								<td height="28" align="center" style="table-layout:fixed;width:80px">申请科室</td>
								<td height="28" align="center" style="table-layout:fixed;width:50px">申请人</td>
								<td height="28" align="center" style="table-layout:fixed;width:80px">申请日期</td>
								<td height="28" align="center" style="table-layout:fixed;">输血特殊要求</td>
						        <td height="28" align="center" style="table-layout:fixed;width:20px">妊娠史</td>
                            <!--<td height="28" align="center" style="table-layout:fixed;width:20px">怀孕次数</td>
								<td height="28" align="center" style="table-layout:fixed;width:20px">是否生产</td>
								<td height="28" align="center" style="table-layout:fixed;width:20px">分娩次数</td> -->
								<td height="28" align="center" style="table-layout:fixed;width:20px">有无输血史</td>
								<td height="28" align="center" style="table-layout:fixed;width:30px">有无不良输血史</td>
						   <!-- <td height="28" align="center" style="table-layout:fixed;">描述</td>
								<td height="28" align="center" style="table-layout:fixed;width:20px">是否新生儿</td>
								<td height="28" align="center" style="table-layout:fixed;width:20px">是否自采血</td>
								<td height="28" align="center" style="table-layout:fixed;width:30px">是否其他疾病</td>-->
								<td height="28" align="center" style="table-layout:fixed;width:80px">疾病名称</td>
								
								<td height="28" align="center" style="table-layout:fixed;">是否交叉配血</td>
							<!--<td height="28" align="center" style="table-layout:fixed;width:80px">交叉配血名称</td> -->
								<td height="28" align="center" style="table-layout:fixed;width:20px">是否备血</td>
							<!--<td height="28" align="center" style="table-layout:fixed;width:120px">备用名称</td> -->
								<td height="28" align="center" style="table-layout:fixed;width:30px">是否干细胞移植后</td>
							<!--<td height="28" align="center" style="table-layout:fixed;width:100px">干细胞移植后循环名称</td> -->
																
								<td height="28" align="center" style="table-layout:fixed;width:60px">申请单编号</td>
								<td height="28" align="center" style="table-layout:fixed;width:60px">申请状态</td>
								<td height="28" align="center" style="table-layout:fixed;width:150px">临床诊断</td>
							</tr>
							<c:forEach items="${bloodReResult}" var="bloodReResult"
								varStatus="status">
								<tr <c:if test="${status.count%2==1}">class="odd"</c:if>
									<c:if test="${status.count%2==0}">class="even"</c:if>>
									<td height="24" align="center">${bloodReResult.bloodAboName}</td>
									<td height="24" align="center">${bloodReResult.bloodRhName}</td>
									<td height="24" align="center">${bloodReResult.bloodClassName}</td>
									<td height="24" align="center">${bloodReResult.quantity}</td>
									<td height="24" align="center">${bloodReResult.quantityUnit}</td>
									<td height="24" align="center">${bloodReResult.bloodReasonName}</td>
									<td height="24" align="center"><fmt:formatDate
											value="${bloodReResult.transfusionDate}" type="date"
											pattern="yyyy-MM-dd HH:mm" /></td>
										<td height="24" align="center">${bloodReResult.requestDeptName}</td>
									<td height="24" align="center">${bloodReResult.requestPersonName}</td>
									<td height="24" align="center"><fmt:formatDate
											value="${bloodReResult.requestDate}" type="date"
											pattern="yyyy-MM-dd HH:mm" /></td>
									<td height="24" align="center">${bloodReResult.specialRequest}</td>
							   	    <td height="24" align="center"><ref:translate
											domain="${Constants.DOMAIN_BOOLEAN_UNCERTAIN}"
											code="${bloodReResult.pregnancyFlag}" /></td>
								<!--<td height="24" align="center">${bloodReResult.pregnancyCount}</td>
									<td height="24" align="center"><ref:translate
											domain="${Constants.DOMAIN_BOOLEAN_UNCERTAIN}"
											code="${bloodReResult.childbirthFlag}" /></td>
									<td height="24" align="center">${bloodReResult.childbirthCount}</td> -->
									<td height="24" align="center"><ref:translate
											domain="${Constants.DOMAIN_BOOLEAN_UNCERTAIN}"
											code="${bloodReResult.historyFlag}" /></td>
									<td height="24" align="center"><ref:translate
											domain="${Constants.DOMAIN_BOOLEAN_UNCERTAIN}"
											code="${bloodReResult.adverseHistoryFlag}" /></td>
								<!--<td height="24" align="center">${bloodReResult.description}</td>
									<td height="24" align="center"><ref:translate
											domain="${Constants.DOMAIN_BOOLEAN_UNCERTAIN}"
											code="${bloodReResult.newbornFlag}" /></td>
									<td height="24" align="center"><ref:translate
											domain="${Constants.DOMAIN_BOOLEAN_UNCERTAIN}"
											code="${bloodReResult.selfBloodFlag}" /></td>
									<td height="24" align="center"><ref:translate
											domain="${Constants.DOMAIN_BOOLEAN_UNCERTAIN}"
											code="${bloodReResult.otherDiseaseFlag}" /></td>-->
									<td height="24" align="center">${bloodReResult.otherDiseaseName}</td>
									
									<td height="24" align="center"><ref:translate
											domain="${Constants.DOMAIN_BOOLEAN_UNCERTAIN}"
											code="${bloodReResult.crossMatchBloodFlag}" /></td>
								<!--<td height="24" align="center">${bloodReResult.crossMatchBloodName}</td> -->									
									<td height="24" align="center"><ref:translate
											domain="${Constants.DOMAIN_BOOLEAN_UNCERTAIN}"
											code="${bloodReResult.reserveFlag}" /></td>
								<!--<td height="24" align="center">${bloodReResult.reserveName}</td>-->
									<td height="24" align="center"><ref:translate
											domain="${Constants.DOMAIN_BOOLEAN_UNCERTAIN}"
											code="${bloodReResult.stemCellTransplantFlag}" /></td>
								<!-- <td height="24" align="center">${bloodReResult.stemCellTransplantName}</td> -->
									<td height="24" align="center">${bloodReResult.requestNo}</td>
									<td height="24" align="center">${bloodReResult.orderStatusName}</td>
									<td height="24" align="center">${bloodReResult.clinicalDiagnosis}</td>
								</tr>
							</c:forEach>
						</table>
					</div>
					</div>
				</c:if>
				<c:if test="${fn:length(bloodReOrResultList)!=0}">
					<div id="tabs-4" class="tabcontainer">
							<%-- <c:if test="${empty bloodReOrResultList}"> --%>
							<!-- <tr class="tabletitle">
								<td height="28" align="center">取血单号</td>
								<td height="28" align="center">科室</td>
								<td height="28" align="center">病区名称</td>
								<td height="28" align="center">床号</td>
								<td height="28" align="center">取血次数</td>
								<td height="28" align="center">取血量</td>
								<td height="28" align="center">取血单位</td>
								<td height="28" align="center">取血时间</td>
								<td height="28" align="center">临床诊断</td>
								<td height="28" align="center">输血目的</td>
								<td height="28" align="center">输血成分</td>
								<td height="28" align="center">送单人</td>
								<td height="28" align="center">接收人</td>
							</tr> -->
							<%-- 	<tr>
									<td colspan="13" align="center" class="odd" height="24">没有相关数据！</td>
								</tr>
							</c:if> --%>
						<c:forEach items="${bloodReOrResultList}"
								var="bloodReOrResultList">
								<table id="Table1" style="width: 100%;" border="0" cellpadding="2"
							cellspacing="1" class="table">
								<tr style="height: 28px; vertical-align: middle">
									<td class="blockHeader" align="left" height="27" colspan="13"
										style="border-top: solid 1px #B3C4D4; vertical-align: middle"><b><img
											src="../images/pic_jz_right.png"
											style="padding-left: 3px; padding-right: 2px;" width="19"
											height="19" alt="" align="absmiddle" />用血申请单</b></td>
								</tr>
								<tr class="tabletitle">
									<td height="28" align="center">申请单编号</td>
									<td height="28" align="center">血液种类名称</td>
									<td height="28" align="center">输血目的名称</td>
									<td height="28" align="center">输血量</td>
									<td height="28" align="center" colspan="7">临床诊断</td>
								</tr>
								<tr style="background-color: #F1D3F8;">
									<td height="24" align="center">${bloodReOrResultList[0].requestNo}</td>
									<td height="24" align="center">${bloodReOrResultList[0].bloodClassName}</td>
									<td height="24" align="center">${bloodReOrResultList[0].bloodReasonName}</td>
									<td height="24" align="center">${bloodReOrResultList[0].quantity}${bloodReOrResultList[0].quantityUnit}</td>
									<td height="24" align="center" colspan="7">${bloodReOrResultList[0].clinicalDiagnosis}</td>
								</tr>
								<tr style="height: 28px; vertical-align: middle">
									<td class="blockHeader" align="left" height="27"
										style="border-top: solid 1px #B3C4D4; vertical-align: middle"
										colspan="13"><b><img src="../images/pic_jz_right.png"
											style="padding-left: 3px; padding-right: 2px;" width="19"
											height="19" alt="" align="absmiddle" />取血单</b></td>
								</tr>
								<tr class="tabletitle">
									<td height="28" align="center">取血单号</td>
									<td height="28" align="center">科室</td>
									<td height="28" align="center">病区名称</td>
									<td height="28" align="center">床号</td>
									<td height="28" align="center">取血次数</td>
									<td height="28" align="center">取血量</td>
									<td height="28" align="center">取血单位</td>
									<td height="28" align="center">取血时间</td>
									<!-- <td height="28" align="center">临床诊断</td>
									<td height="28" align="center">输血目的</td> -->
									<td height="28" align="center">输血成分</td>
									<td height="28" align="center">送单人</td>
									<td height="28" align="center">接收人</td>
								</tr>
								<c:forEach items="${bloodReOrResultList}" var="bloodReOrResult"
									varStatus="status">
									<tr <c:if test="${status.count%2==1}">class="odd"</c:if>
										<c:if test="${status.count%2==0}">class="even"</c:if>>
										<td height="24" align="center">${bloodReOrResult.orderLid}</td>
										<td height="24" align="center">${bloodReOrResult.orderDeptName}</td>
										<td height="24" align="center">${bloodReOrResult.wardName}</td>
										<td height="24" align="center">${bloodReOrResult.bedNo}</td>
										<td height="24" align="center">${bloodReOrResult.bloodDeliveryCount}</td>
										<td height="24" align="center">${bloodReOrResult.deliveryQuality}</td>
										<td height="24" align="center">${bloodReOrResult.deliveryUnit}</td>
										<td height="24" align="center"><fmt:formatDate
												value="${bloodReOrResult.deliveryTime}" type="date"
												pattern="yyyy-MM-dd HH:mm" /></td>
										<%-- <td height="24" align="center">${bloodReOrResult.diagnoseName}</td>
										<td height="24" align="center">${bloodReOrResult.transfusionReasonName}</td> --%>
										<td height="24" align="center">${bloodReOrResult.transfusionComponents}</td>
										<td height="24" align="center">${bloodReOrResult.appSenderName}</td>
										<td height="24" align="center">${bloodReOrResult.appReceiverName}</td>
									</tr>
								</c:forEach>
								</table>
								<p style="height:10px;"></p>
								<br/>
							</c:forEach>
					</div>
				</c:if>
				
				<c:if test="${showClosedCycle}">
					<!-- 用血跟踪链接 -->
					<div id="tabs-7" class="tabcontainer">
						<c:forEach  var="data" items="${backMap}">
							 取血单号：${data.key}
							 <iframe id="dialogFrameDetail" style="width: 100%; height: 100%;"
										src="${data.value}" frameborder="1"> 
							 </iframe>
						</c:forEach>	 
					</div>
				</c:if>
				
			    <c:if test="${fn:length(requestAndRecordList)!=0}">
			       <div id="tabs-6" class="tabcontainer">
						<c:forEach items="${requestAndRecordList}" var="data">
							<table id="Table1" style="width: 100%;" border="0" cellpadding="2" cellspacing="1" class="table">
							    <tr style="height: 28px; vertical-align: middle">
									<td class="blockHeader" align="left" height="27" colspan="13"
										style="border-top: solid 1px #B3C4D4; vertical-align: middle"><b><img
											src="../images/pic_jz_right.png"
											style="padding-left: 3px; padding-right: 2px;" width="19"
											height="19" alt="" />用血申请单</b></td>
								</tr>
								<tr class="tabletitle">
									<td height="28" align="center" width="12%" class="jbobLabel">申请单编号</td>
									<td height="28" align="center" width="12%" class="jbobLabel">血液种类名称</td>
									<td height="28" align="center" width="12%" class="jbobLabel">输血目的名称</td>
									<td height="28" align="center" width="12%" class="jbobLabel">输血量</td>
									<td height="28" align="center" colspan="4" class="jbobLabel">临床诊断</td>
								</tr>
								<tr style="background-color: #F1D3F8;">
									<td height="24" align="center" >${data.requestNo}</td>
									<td height="24" align="center" >${data.bloodClassName}</td>
									<td height="24" align="center" >${data.bloodReasonName}</td>
									<td height="24" align="center" >${data.quantity}${Data.quantityUnit}</td>
									<td height="24" align="center" colspan="4">${data.clinicalDiagnosis}</td>
								</tr>
						
								<c:forEach items="${data.recordMenu}" var="menu">
									<tr style="height: 28px; vertical-align: middle">
										<td class="blockHeader" align="left" height="27"
											style="border-top: solid 1px #B3C4D4; vertical-align: middle"
											colspan="13"><b><img src="../images/pic_jz_right.png"
												style="padding-left: 3px; padding-right: 2px;" width="19"
												height="19" alt="" />输血记录单</b></td>
									</tr>
									<tr>
										<td height="28" align="right" width="12%" class="jbobLabel">复检ABO血型：</td>
										<td height="28" align="center" width="12%" class="jbobDataitem">${menu.recheckBloodAboName}</td>
										<td height="28" align="right" width="12%" class="jbobLabel">复检RH血型：</td>
										<td height="28" align="center" width="12%" class="jbobDataitem">${menu.recheckBloodRhName}</td>
										<td height="28" align="right" width="12%" class="jbobLabel">申请ABO血型：</td>
										<td height="28" align="center" width="12%" class="jbobDataitem">${menu.requestBloodAboName}</td>
										<td height="28" align="right" width="12%" class="jbobLabel">申请RH血型：</td>
										<td height="28" align="center" width="12%" class="jbobDataitem">${menu.requestBloodRhName}</td>
										
									</tr>
									<tr>
										<td height="28" align="right" class="jbobLabel">血液特殊要求：</td>
										<td height="28" class="jbobDataitem" align="left" colspan="7" >${menu.bloodSpecialRequestName}</td>
									</tr>
									<tr>
										<td height="28" align="right"  class="jbobLabel">输血记录：</td>
										<td height="28" align="center" class="jbobLabelEx">(血型)</td>
										<td height="28" align="center" class="jbobLabelEx"></td>
										<td height="28" align="center" class="jbobLabelEx">(血袋条号码)</td>
										<td height="28" align="center" class="jbobLabelEx"></td>
										<td height="28" align="center" class="jbobLabelEx">(规格)</td>
										<td height="28" align="center" class="jbobLabelEx"></td>
										<td height="28" align="center" class="jbobLabelEx">(供血种类)</td>
									</tr>
									<c:forEach items="${menu.bloodRecord}" var="record">
										<tr>
											<td height="28" align="center" ></td>
											<td height="28" align="center" class="jbobDataitem" width="10%">${record.bloodType}型</td>
											<td height="28" align="center" ></td>
											<td height="28" align="center" class="jbobDataitem" width="10%">${record.bloodBagBarCode}</td>
											<td height="28" align="center" ></td>
											<% 
												// $Author :yang_mingjie
 												// $Date : 2014/08/19 10:09$
 												// [BUG]0047587 MODIFY BEGIN 
 											%>
 											<!--
 											<td height="28" align="center" class="jbobDataitem" width="10%">${record.quality}${record.unit}</td>
 											 -->
											<td height="28" align="center" class="jbobDataitem" width="10%">${record.quality}</td>
											<td height="28" align="center" ></td>
											<td height="28" align="center" class="jbobDataitem" width="20%">${record.supplyBloodClassName}</td>
										</tr>
									</c:forEach>
									<tr>
										<td height="24" align="right"  class="jbobLabel">交叉配血试验结果：</td>
										<td height="24" align="center" class="jbobDataitem">${menu.crossMatchResult}</td>
										<td height="24" align="center" class="jbobDataitem"></td>
										<td height="24" align="center" class="jbobDataitem"></td>
										<td height="24" align="right"  class="jbobLabel">配血者：</td>
										<td height="24" align="center" class="jbobDataitem">${menu.matchBloodPersonName}</td>
										<td height="24" align="right"  class="jbobLabel">配血日期：</td>
										<% 
												// $Author :yang_mingjie
 												// $Date : 2014/08/19 10:09$
 												// [BUG]0047587 MODIFY BEGIN 
 											%>
										<td height="24" align="center" colspan="1" class="jbobDataitem"><fmt:formatDate
												value="${menu.matchBloodDate}" type="both" pattern="yyyy-MM-dd HH:mm:ss" />
										</td>
									</tr>
									<c:forEach items="${menu.examResult}" var="result">
										<tr>
											<td height="24" align="right" width="15%" class="jbobLabel">${result.itemName}：</td>
											<td height="24" align="center" colspan="7" class="jbobDataitem">${result.itemResult}</td>
										</tr>
									</c:forEach>
									<tr>
										<td height="24" align="right" class="jbobLabel">发血者：</td>
										<td height="24" align="center" class="jbobDataitem">${menu.assignedPersonName}</td>
										<td height="24" align="right" class="jbobLabel">发血日期：</td>
										<% 
												// $Author :yang_mingjie
 												// $Date : 2014/08/19 10:09$
 												// [BUG]0047587 MODIFY BEGIN 
 											%>
										<td height="24" align="center" class="jbobDataitem"><fmt:formatDate
												value="${menu.sendBloodDate}" type="both" pattern="yyyy-MM-dd HH:mm:ss" /></td>
										<td height="24" align="right" class="jbobLabel">取血者：</td>
										<td height="24" align="center" class="jbobDataitem">${menu.playingEntityName}</td>
										<td height="24" align="right" class="jbobLabel">取血日期：</td>
										<% 
												// $Author :yang_mingjie
 												// $Date : 2014/08/19 10:09$
 												// [BUG]0047587 MODIFY BEGIN 
 											%>
										<td height="24" align="center" class="jbobDataitem"><fmt:formatDate
												value="${menu.playingDate}" type="both" pattern="yyyy-MM-dd HH:mm:ss" />
										</td>
									</tr>
									<tr>
										<td height="24" align="right" class="jbobLabel">备注：</td>
										<td height="24" align="center" colspan="8" class="jbobDataitem">${menu.remark}</td>
									</tr>
								</c:forEach>
							</table>
							<p style="height:10px;"></p>
							<br/>
						</c:forEach> 
					</div> 
				</c:if>
				
				<c:if test="${fn:length(withdrawRecordDto)!=0}">
					<div id="tabs-5" class="tabcontainer">
						<table cellpadding="2" cellspacing="1" class="table" width="100%"
							align="center">
							<tr class="tabletitle">
								<td height="28" width="50%" align="center">召回人姓名</td>
								<td height="28" width="50%" align="center">召回时间</td>
								<!-- <td width="60%" align="center">召回原因</td> -->
							</tr>
							<c:forEach items="${withdrawRecordDto}" var="withdrawRecordDto"
								varStatus="status">
								<tr class=${status.count%2==0?'even':'odd'}>
									<td height="24" align="center">${withdrawRecordDto.withdrawPersonName}</td>
									<td height="24" align="center">${fn:substring(withdrawRecordDto.withdrawTime,0,16)}</td>
									<%-- <td>${withdrawRecordDto.withdrawReason}</td> --%>
								</tr>
							</c:forEach>
						</table>
					</div>
				</c:if>
			</div>
		</div>
	</div>
	<script>
        $(function() {            
         	//页面可见区域宽度
			var availWidth = document.body.offsetWidth;
          	var $bloodReq = $("#bloodRequest03");
          	$bloodReq.css("width",availWidth);
        });
        
        
    </script>
</body>
</html>
