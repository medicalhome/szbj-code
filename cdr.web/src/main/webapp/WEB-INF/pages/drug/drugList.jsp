<%@ page language="java" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="translation.tld" prefix="ref"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="html2.tld" prefix="html2"%>
<%@ taglib uri="html.tld" prefix="html"%>
<jsp:useBean id="Constants" class="com.founder.cdr.core.Constants" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<link rel="Stylesheet" type="text/css" href="../styles/tablelist.css" />
<link rel="Stylesheet" type="text/css" href="../styles/chart.css" />
<script language="javascript" type="text/javascript" src="../scripts/jquery.flip.js"></script>
<script language="javascript" type="text/javascript" src="../scripts/timerInpatient/excanvas.min.js"></script>
<script language="javascript" type="text/javascript" src="../scripts/timerInpatient/jquery.flot.others.js"></script>
<script language="javascript" type="text/javascript" src="../scripts/timerInpatient/jquery.flot.pie.js"></script>
<script language="javascript" type="text/javascript" src="../scripts/timerInpatient/jquery.flot.stack.js"></script>
<script type="text/javascript" src="../scripts/drug/drugChart.js"></script>
<script type="text/javascript" src="../scripts/drug/drugMenuPart.js"></script>
<script type="text/javascript" src="../scripts/drug/drugList.js"></script>
<script>
	$(function() {
		// $Author:wu_jianfeng
		// $Date : 2012/10/24 14:10
		// $[BUG]0010542 MODIFY BEGIN
		$( "#orderDept").htmlSelectSuggest({width:140, 
			onKeyUp: function(event){
				if (event.keyCode==13) 
		    	{ 
					search('', 'conditionForm');    									
				} 
			}
		});

		$( "#medicineType").htmlSelectSuggest({width:120, 
			onKeyUp: function(event){
				if (event.keyCode==13) 
		    	{ 
					search('', 'conditionForm');    									
				} 
			}
		});
		// $[BUG]0010542 MODIFY END
		
		setChartPatientSn('${patientSn}');
		/* $Author :chang_xuewen
		 $Date : 2013/07/04 11:00
		 $[BUG]0033461 ADD BEGIN */
		//调用动态表格美化
		beautyTable();
		/* $[BUG]0033461 ADD BEGIN */
		logical();
	});
	
	/* $(function() {
		$( "#tblid tr" ).draggable({
			appendTo: "body",
			helper: "clone"
		});
	
	}); */
	</script>
</head>
<body style="margin: 0; padding: 0;">
	<!-- $Author :jin_peng
		 $Date : 2013/01/04 15:58$
		 [BUG]0012888 MODIFY BEGIN -->
	<!--<c:if test="${pagingContext.totalRowCnt > 0 && drugListSearchPra.visitSn == null}">
		<div style="width:99%;height:26px;margin-left:10px;display:none">
			<div style="float:right;width:66px;height:24px;background-color:#66ccff;text-align:center;line-height:2;cursor:pointer" 
				onmouseover="this.style.color='red'" onmouseout="this.style.color='black'" onclick="chartFlip('chartId')">图表</div>
			<div style="float:right;width:5px;height:24px;text-align:center">&nbsp;</div>
			<div style="float:right;width:68px;height:24px;background-color:#66ccff;text-align:center;line-height:2;cursor:pointer" 
				onmouseover="this.style.color='red'" onmouseout="this.style.color='black'" onclick="listFlip('listId')">列表</div>
		</div>
	</c:if>-->
	<div id="listId" style="display:block;">
	<form id="conditionForm" name="conditionForm" method="post"
		action="../drug/list_${patientSn}.html"
		<c:if test="${drugListSearchPra.visitSn != null}">style="display:none;"</c:if>>
		<table class="blockTable" cellpadding="2" cellspacing="1" border="1px" style="width: 100%;"
			bordercolor="#BFD9F0">
			<tr style="height: 28px;" id="tableheader">
				<td class="blockHeader" colspan="13" height="27" align="left"
					style="border-top: solid 1px #B3C4D4;"><div style="float:left;"><b><img
						src="../images/pic_ywyz_right.png" align="absmiddle"
						style="padding-left: 3px; padding-right: 2px;" width="19"
						height="19" alt="" />药物医嘱</div><c:if test="${pagingContext.totalRowCnt > 0 && drugListSearchPra.visitSn == null}"><div style="float:right;line-height:19px"><span style="margin-right:7px;text-align:center;margin-top:2px;cursor:pointer" 
							onmouseover="this.style.color='#FF8C10'" onmouseout="this.style.color='black'" class="tabEnter" onclick="chartFlip('chartId')">图 表</span></div></c:if></b></td>
			</tr>
			<tr class="conditionRow" style="width: 100%;">
				<td colspan="13" height="36" align="left" valign="middle">
					<div class="cell" style="width: 200px;">
						<div class="cell" style="width: 65px; text-align: right;">化学名</div>
						<div class="cell" style="width: 120px;">
							<input type="text" id="approvedDrugName" style="width: 110px;" name="approvedDrugName"
								onmouseover="this.style.background='#FDE8FE';"
								onmouseout="this.style.background='#FFFFFF'"
								value="${drugListSearchPra.approvedDrugName}" />
						</div>
					</div>
					<div class="cell" style="width: 270px;">
						<div class="cell" style="width: 55px; text-align: right;">开嘱日期</div>
						<div class="cell">
							<input id="orderTimeFrom" name="orderTimeFrom"
								style="width: 80px;" class="datepicker"
								value="${drugListSearchPra.orderTimeFrom}" /> <span
								style="margin: 0 4px 0 4px;">--</span> <input id="orderTimeTo"
								name="orderTimeTo" style="width: 80px;" class="datepicker"
								value="${drugListSearchPra.orderTimeTo}" />
						</div>
					</div>
					<!-- $Author :jin_peng
						 $Date : 2012/11/22 14:00$
						 [BUG]0011026 MODIFY BEGIN -->
					<!-- [BUG]0011026 MODIFY END -->
					<div class="cell" style="width: 160px;">
						<div class="cell" style="width: 70px; text-align: center;">
							<input type="button" onclick="search('', 'conditionForm');"
								style="color: #464646; border: 0px; BACKGROUND-IMAGE: url(../images/5.jpg); width: 57px; height: 25px; margin-top: 3px; cursor: pointer;"
								class="button" align="absmiddle" />
						</div>
						<div id="toggleBlock" class="container-on cell">
							<input type="button" id="buttonBlock"
								style="color: #464646; border: 0px; BACKGROUND-IMAGE: url(../images/6.jpg); width: 77px; height: 25px; margin-top: 3px; cursor: pointer;"
								onclick="showTr('tr1',this)" align="absmiddle" />
							<input type="hidden" id="conditionValue" name="conditionValue"
								value="${conditionValue}" /> <input type="hidden" id="senSave"
								name="senSave" value="${senSave}" /> <input type="hidden"
								name="type" value="${drugListSearchPra.type}" /> <input
								type="hidden" name="visitSn" value="${drugListSearchPra.visitSn}" />
							<input type="hidden" name="inpatientDate"
								value="${drugListSearchPra.inpatientDate}" />
						    <!-- $Author :jin_peng
	        				 $Date : 2012/10/15 11:21$
	        				 [BUG]0010239 ADD BEGIN -->
	        				 <input type="hidden" name="inpatientLongTermDate"
								value="${drugListSearchPra.inpatientLongTermDate}" />
							
							<input type="hidden" name="longTempFlag"
								value="${drugListSearchPra.longTempFlag}" />
								
							<input type="hidden" name="cancelOrderStatus"
								value="${drugListSearchPra.cancelOrderStatus}" />
	        				 
	        				<!-- [BUG]0010239 ADD END -->
						</div>
					</div>
				</td>
			</tr>
			<tr class="conditionRow">
				<td colspan="13" height="36" align="left" valign="middle">
					<div class="cell" style="width: 190px;">
						<div class="cell" style="width: 65px; text-align: right;">就诊日期</div>
						<div class="cell">
							<select id="visitDateSn" name="visitDateSn" style="width: 110px;">
								<option value="${Constants.OPTION_SELECT}">请选择</option>
								<c:forEach items="${drugListSearchPra.visitDateList}" var="vdl">
									<option value="${vdl.visitSn}" <c:if test="${vdl.visitSn == drugListSearchPra.visitDateSn}">selected</c:if>>${vdl.visitDate}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					
					<c:if test="${Constants.TRUE_ORG_CODE == Constants.DISABLE_ORG_CODE}">
					<div class="cell" style="width: 65px; text-align: right;">医疗机构</div>
					<div style="float:left;margin-top:4px;width: 140px;">
						<select id="orgCode" name="orgCode" style="width: 140px;">
							<option value="${Constants.OPTION_SELECT}">请选择</option>
							<html2:pycodeoptions domain="${Constants.DOMAIN_ORG_CODE}"
								value="${drugListSearchPra.orgCode}" />
						</select>
					</div>
					</c:if>
				</td>
			</tr>
			<tr id='tr1' class="conditionRow moreCondition off">
				<td colspan="13" height="36" align="left" valign="middle">
					<div class="cell" style="width: 210px;">
						<div class="cell ra" style="width: 65px; text-align: right;">药物类型</div>
		                <!-- $Author :wu_jianfeng
		 					 $Date : 2012/10/24 17:08$
							 [BUG]0010542 MODIFY BEGIN -->
						<div class="cell" style="width: 130px;">
							<select id="medicineType" name="medicineType" style="width: 100px;">
								<option value="${Constants.OPTION_SELECT}">请选择</option>
								<html2:pycodeoptions domain="${Constants.DOMAIN_MEDICINE_TYPE}"
									value="${drugListSearchPra.medicineType}" />
							</select>
						</div>
					</div>
	                <!-- [BUG]0010542 MODIFY END -->
	                <div class="cell" style="width: 250px;">
						<div class="cell ra" style="width: 55px; text-align: right;">停嘱时间</div>
						<div class="cell">
							<input id="stopTimeFrom" name="stopTimeFrom" style="width: 70px;"
								class="datepicker" value="${drugListSearchPra.stopTimeFrom}" />
							<span style="margin: 0 4px 0 4px;">--</span> <input
								id="stopTimeTo" name="stopTimeTo" style="width: 70px;"
								class="datepicker" value="${drugListSearchPra.stopTimeTo}" />
						</div>
					</div>
					<div class="cell" style="width: 180px;">
						<div class="cell" style="width: 55px; text-align: right">开医嘱人</div>
						<div class="cell" style="width: 110px;">
							<input type="text" id="orderPersonName" name="orderPersonName"
								style="width:100px;"
								onmouseover="this.style.background='#FDE8FE';"
								onmouseout="this.style.background='#FFFFFF'"
								value="${drugListSearchPra.orderPersonName}" />
						</div>
					</div>
	                <!-- [BUG]0010542 MODIFY END -->
				<!-- </td>
			</tr>

			<tr id='tr2' class="conditionRow moreCondition off">
				<td width="100%" height="36" align="left" valign="middle"> -->
				
					
					<%-- <div class="cell ra" style="width: 60px; text-align: right;">诊断类型</div>
					<div class="cell" style="width: 120px;">
						<select id="diagnoseType" name="diagnoseType" style="width: 98%;">
							<option value="${Constants.OPTION_SELECT}">请选择</option>
							<html:options domain="${Constants.DOMAIN_DIAGNOSIS_TYPE}"
								value='${drugListSearchPra.diagnoseType}' />
						</select>
					</div> --%>
					<%-- <div class="cell" style="width: 80px; text-align: right;">疾病诊断名称</div>
					<div class="cell" style="width: 140px;">
						<select id="diseaseName" name="diseaseName" style="width: 98%;">
							<option value="${Constants.OPTION_SELECT}">请选择</option>
							<html2:pycodeoptions domain="${Constants.DOMAIN_ICD_OUTPATIENT}"
								value="${drugListSearchPra.diseaseName}" />
						</select>
					</div> --%>
					<div class="cell" style="width: 210px;">
						<div class="cell" style="width: 73px;">诊断疾病名称</div>
						<div class="cell" style="width: 120px;">
							 <input type="text" id="diseaseName" name="diseaseName" style="width: 110px;" onmouseover="this.style.background='#FDE8FE';"
								onmouseout="this.style.background='#FFFFFF'" value="${drugListSearchPra.diseaseName}" />
						</div>
					</div>
					<div class="cell" style="width: 160px;">
	                    <div class="cell ra" style="width: 65px; text-align: right;">就诊类型</div>
						<div class="cell" style="width: 80px;">
							<select id="visitType" name="visitType" style="width: 98%;">
								<option value="${Constants.OPTION_SELECT}">请选择</option>
								<html:options domain="${Constants.DOMAIN_VISIT_TYPE}"
									value='${drugListSearchPra.visitType}' />
							</select>
						</div>
					</div>
					<div class="cell" style="width: 205px;">
						<div class="cell ra" style="width: 65px; text-align: right;">长期或临时</div>
						<div class="cell" style="width: 120px;">
							<select id="temporaryFlag" name="temporaryFlag" style="width: 110px;">
								<option value="${Constants.OPTION_SELECT}">请选择</option>
								<html:options domain="${Constants.DOMAIN_TEMPORARY_FLAG}"
									value='${drugListSearchPra.temporaryFlag}' />
							</select>
						</div>
					</div>
					<div class="cell" style="width: 240px;">
						<div class="cell" style="width: 80px; text-align: right">医嘱开立科室</div>
		                <!-- $Author :wu_jianfeng
		 					 $Date : 2012/10/24 17:08$
							 [BUG]0010542 MODIFY BEGIN -->
						<div class="cell" style="width: 150px;">
							<select id="orderDept" name="orderDept" style="width: 140px;">
								<option value="${Constants.OPTION_SELECT}">请选择</option>
								<html2:pycodeoptions domain="${Constants.DOMAIN_DEPARTMENT}"
									value="${drugListSearchPra.orderDept}" />
							</select>
						</div>
					</div>
				</td>
			</tr>
			<!-- $Author :chang_xuewen
			$Date : 2013/07/04 11:00
			$[BUG]0033461 MODIFY BEGIN -->
			<tr class="tabletitle">
			<td height="28" align="center" width="8%">药品化学名</td>
			<td height="28" align="center" width="8%">药品英文名</td>
			<td height="28" align="center" width="5%">用药天数</td>
			<td height="28" align="center" width="6%">药物类型</td>
			<td height="28" align="center" width="6%">药物剂型</td>
			<td height="28" align="center" width="6%">用药途径</td>
			<td height="28" align="center" width="6%">次剂量</td>
			<td height="28" align="center" width="6%">总剂量</td>
			<td height="28" align="center" width="7%">使用频率</td>
			<td height="28" align="center" width="6%">长期或临时</td>
			<td height="28" align="center" width="6%">开嘱人</td>
			<!-- $Author :chang_xuewen
     			 $Date : 2013/10/31 11:00
     			 $[BUG]0038735 MODIFY BEGIN -->	
			<td height="28" align="center" width="11%">医嘱录入时间</td>
			<!-- $[BUG]0038735 MODIFY END -->	
			<td height="28" align="center" width="10%">开医嘱科室</td>
			<c:if test="${Constants.TRUE_ORG_CODE == Constants.DISABLE_ORG_CODE}">
				<td height="28" align="center" width="9%">医疗机构</td>
			</c:if>
		</tr>
		<!-- $[BUG]0033461 MODIFY END -->
		</table>
	</form>
	<div id='listcon'>
	<!--[if lt ie 8]><div style='+zoom:1;'><![endif]-->
	<table id="tblid" style="width: 100%;" cellspacing="1" cellpadding="2"
		border="0" class="table">
		<c:if test="${fn:length(drugList)==0}">
			<tr>
				<td colspan="13" align="center" class="odd" height="24">没有相关数据！</td>
			</tr>
		</c:if>

		<!-- $Author :jin_peng
			 $Date : 2013/02/19 11:20$
			 [BUG]0013981 MODIFY BEGIN -->
		<c:forEach items="${drugList}" var="drugList" varStatus="status">
			<!-- $Author :chang_xuewen
			$Date : 2013/07/04 11:00
			$[BUG]0033461 MODIFY BEGIN 
			onclick="bigShow($(this),'../drug/detail_${drugList.orderSn}.html?flag=tabs');-->
			<tr id='${drugList.orderSn}'
				style="cursor: pointer"  class="tabEnter"
				<c:if test="${drugListSearchPra.visitSn != null}">onclick="showDialog('../drug/detail_${drugList.orderSn}.html?flag=tabs','药物医嘱',{},700,500,'#ajaxDialogDetail');"</c:if>
				onclick="bigShow($(this),'../drug/detail_${drugList.orderSn}.html?flag=tabs',
						{'otherName':'<c:choose>
										<c:when test="${drugList.orderType==Constants.HERBAL_MEDICINE_CLASS}">草药&nbsp;</c:when>										
										<c:otherwise>${drugList.approvedDrugName}&nbsp;</c:otherwise>
									</c:choose>','holdOtherName':'${drugList.approvedDrugName}',
						'name':'<c:choose>
									<c:when test="${drugList.orderType==Constants.HERBAL_MEDICINE_CLASS}">草药&nbsp;</c:when>
									<c:otherwise>${drugList.drugName}&nbsp;</c:otherwise>
								</c:choose>','holdName':'${drugList.drugName}','orderSn':'${drugList.orderSn}'});">
					<td height="24" width="12% align="center" style="display:none">
					药物医嘱|../drug/detail_${drugList.orderSn}.html|  <c:choose>
						<c:when test="${drugList.orderType==Constants.HERBAL_MEDICINE_CLASS}"><font color="blue">草药</font></c:when>
						<c:otherwise>${drugList.drugName}</c:otherwise>
					</c:choose>|	<c:if test="${fn:length(drugList.orderDeptName)>6}">${fn:substring(drugList.orderDeptName,0,6)}</c:if>
												<c:if test="${fn:length(drugList.orderDeptName)<=6}">${drugList.orderDeptName}</c:if>|
			<!-- $[BUG]0033461 MODIFY END -->
				</td>
				<td height="24" width="8% align="center" 
					<c:if test="${fn:length(drugList.drugName)>6}"> title="${drugList.drugName}" </c:if>>
					<c:choose>
						<c:when test="${drugList.orderType==Constants.HERBAL_MEDICINE_CLASS}"><font color="blue">草药</font></c:when>
						<c:otherwise>${drugList.drugName}</c:otherwise>
					</c:choose>
				</td>
				<td height="24" width="8% align="center" 
					<c:if test="${fn:length(drugList.englishName)>16}"> title="${drugList.englishName}" </c:if>>
					<c:if test="${fn:length(drugList.englishName)>16}">${fn:substring(drugList.englishName,0,16)}...</c:if>
					<c:if test="${fn:length(drugList.englishName)<=16}">${drugList.englishName}</c:if>
				</td>	
				<td height="24"  width="5%" align="center">${drugList.days}</td>
				<td height="24" width="6%" align="center"
					<c:if test="${fn:length(drugList.medicineTypeName)>6}"> title="${drugList.medicineTypeName}" </c:if>>
					<c:if test="${fn:length(drugList.medicineTypeName)>6}">${fn:substring(drugList.medicineTypeName,0,6)}...</c:if>
					<c:if test="${fn:length(drugList.medicineTypeName)<=6}">${drugList.medicineTypeName}</c:if>
				</td>
				<td height="24" width="6%" align="center"
					<c:if test="${fn:length(drugList.medicineForm)>10}"> title="${drugList.medicineForm}" </c:if>>
					<c:if test="${fn:length(drugList.medicineForm)>10}">${fn:substring(drugList.medicineForm,0,10)}...</c:if>
					<c:if test="${fn:length(drugList.medicineForm)<=10}">${drugList.medicineForm}</c:if>
				</td>
				<td height="24" width="6%" align="center"
					<c:if test="${fn:length(drugList.routeName)>10}"> title="${drugList.routeName}" </c:if>>
					<c:if test="${fn:length(drugList.routeName)>10}">${fn:substring(drugList.routeName,0,10)}...</c:if>
					<c:if test="${fn:length(drugList.routeName)<=10}">${drugList.routeName}</c:if>
				</td>
				<td height="24" width="6%" align="center">${drugList.dosage}
					${drugList.dosageUnit}</td>
				<td height="24" width="6%" align="center">${drugList.totalDosage}
					${drugList.totalDosageUnit}</td>
				<td height="24" width="7%" align="center" title="${drugList.medicineFreqName }">${drugList.medicineFreqName}</td>
				<td height="24" width="6%" align="center"><ref:translate
						domain="${Constants.DOMAIN_TEMPORARY_FLAG}"
						code="${drugList.temporaryFlag}" /></td>
				<td height="24"  width="6%" align="center">${drugList.orderPersonName}</td>
				<td height="24" width="11%" align="center"><fmt:formatDate
						value="${drugList.orderTime}" type="date" pattern="yyyy-MM-dd HH:mm" /></td>
				<td height="24" width="10%" align="center">${drugList.orderDeptName}</td>
				<c:if test="${Constants.TRUE_ORG_CODE == Constants.DISABLE_ORG_CODE}">
					<td height="24" width="9%" align="center"><ref:translate
	                        domain="${Constants.DOMAIN_ORG_CODE}"
	                        code="${drugList.orgCode}" /></td>
	            </c:if>
				<%-- <td height="24" align="left">${drugList.diseaseName}</td>
				<td height="24" align="center"><fmt:formatDate
						value="${drugList.visitDate}" type="date" pattern="yyyy-MM-dd" /></td> --%>
			</tr>
		</c:forEach>
		<!-- [BUG]0013981 MODIFY END -->

		<!-- $Author :chang_xuewen
			$Date : 2013/07/04 11:00
			$[BUG]0033461 MODIFY BEGIN -->
		<tr class="page_line" >
		<!-- $[BUG]0033461 MODIFY END -->
			<td colspan="13" style="height: 27px;">
				<form name="pagingform" method="get"
					action="../drug/list_${patientSn}.html">
					<div class="pagelinks">
						<div style="float: right; height: 100%;">
							<div class="pagedesc">共${pagingContext.totalRowCnt}条记录！
								第${pagingContext.pageNo}页/共${pagingContext.totalPageCnt}页</div>
							<c:if test="${pagingContext.pageNo > 1}">
								<div class="firstPage">
									<a href="javascript:void(0);"
										<c:if test="${drugListSearchPra.visitSn != null}">onclick="jumpToPage(1,'#ajaxDialog');return false;"</c:if>
										onclick="jumpToPage(1);return false;"><img
										src="../images/1.gif"
										style="border: 0px; width: 21px; height: 16px;" /></a>
								</div>
								<div class="prevPage">
									<a href="javascript:void(0);"
										<c:if test="${drugListSearchPra.visitSn != null}">onclick="jumpToPage(${pagingContext.pageNo-1},'#ajaxDialog');return false;"</c:if>
										onclick="jumpToPage(${pagingContext.pageNo-1});return false;"><img
										src="../images/2.gif"
										style="border: 0px; width: 41px; height: 16px;" /></a>
								</div>
							</c:if>
							<c:forEach var="i" begin="${pagingContext.pageStartNo}"
								end="${pagingContext.perPageCnt}" step="1">
								<c:choose>
									<c:when test="${i == pagingContext.pageNo}">
										<div class="currentPage">
											<font color="#2D56A5">${i}</font>
										</div>
									</c:when>
									<c:otherwise>
										<div class="pageno">
											<a href="javascript:void(0);"
												<c:if test="${drugListSearchPra.visitSn != null}">onclick="jumpToPage(${i},'#ajaxDialog');return false;"</c:if>
												onclick="jumpToPage(${i}); return false;"><font
												color="#2D56A5">${i}</font></a>
										</div>
									</c:otherwise>
								</c:choose>
							</c:forEach>
							<c:if test="${pagingContext.pageNo < pagingContext.totalPageCnt}">
								<div class="nextPage">
									<a href="javascript:void(0);"
										<c:if test="${drugListSearchPra.visitSn != null}">onclick="jumpToPage(${pagingContext.pageNo + 1},'#ajaxDialog');return false;"</c:if>
										onclick="jumpToPage(${pagingContext.pageNo + 1});return false;"><img
										src="../images/4.gif"
										style="border: 0px; width: 41px; height: 16px;" /></a>
								</div>
								<div class="lastPage">
									<a href="javascript:void(0);"
										<c:if test="${drugListSearchPra.visitSn != null}">onclick="jumpToPage(${pagingContext.totalPageCnt},'#ajaxDialog');return false;"</c:if>
										onclick="jumpToPage(${pagingContext.totalPageCnt});return false;"><img
										src="../images/3.gif"
										style="border: 0px; width: 21px; height: 16px;" /></a>
								</div>
							</c:if>
							<div class="pageNum">
								<input type="text" name="screen" style="display: none;" /> <input
									name="pageNum"
									<c:if test="${drugListSearchPra.visitSn != null}">onkeyup="if(event.keyCode!=13){return false;} jumpToPageNo($('#tblid').find('[name=pageNum]').val(),${pagingContext.totalPageCnt},'#ajaxDialog');return false;"</c:if>
									onkeyup="if(event.keyCode!=13){return false;} jumpToPageNo($('#tblid').find('[name=pageNum]').val(),${pagingContext.totalPageCnt});return false;"
									style="width: 30px; float: left;" value="" />
							</div>
							<div class="goinput">
								<a href="javascript:void(0);"
									<c:if test="${drugListSearchPra.visitSn != null}">onclick="jumpToPageNo($('#tblid').find('[name=pageNum]').val(),${pagingContext.totalPageCnt},'#ajaxDialog');return false;"</c:if>
									onclick="jumpToPageNo($('#tblid').find('[name=pageNum]').val(),${pagingContext.totalPageCnt});return false;"><img
									src="../images/go.gif"
									style="border: 0px; width: 21px; height: 15px;" /></a>
							</div>
							<div id="ajaxDialogDetail" style="display: none;">
								<iframe id="dialogFrameDetail"
									style="width: 100%; height: 100%;" src="" frameborder="0">
								</iframe>
							</div>
						</div>
					</div>
				</form>
			</td>
		</tr>
	</table>
	 <!--[if lt ie 8]></div><![endif]-->
	</div>
	</div>
	<div id="chartId" style="display:none;">
		<table cellspacing="1" cellpadding="2" id="chartTab" border="0" width="100%">
			<tr style="height: 28px;">
				<td class="blockHeader" colspan="13" height="27" align="left"
					style="border-top: solid 1px #B3C4D4;"><div style="float:left;font-family:'微软雅黑';font-size: 12px;color:#153460;font-weight:bold"><img
						src="../images/pic_ywyz_right.png" align="absmiddle"
						style="padding-left: 3px; padding-right: 2px;" width="19"
						height="19" alt="" />药物医嘱</div><div style="float:right;line-height:19px;"><span style="margin-right:7px;text-align:center;margin-top:2px;cursor:pointer;font-family:'微软雅黑';font-weight:bold;font-size: 12px;" 
							onmouseover="this.style.color='#FF8C10'" onmouseout="this.style.color='black'" class="tabEnter" onclick="listFlip('listId')">列 表</span></div></td>
			</tr>
			<tr>
				<td style="width:40%;overflow:hidden"> <!-- width:540px; -->
					<div id="pieId" style="height:395px;border:1px solid #E4F1FA;overflow:hidden;">
						<div style="height:395px;text-align:center;font-size:170%;color:#FF8C10;font-weight:bold;line-height:394px">正在加载，请稍后...</div>
					</div>
				</td>
				<td style="width:5px;">&nbsp;<div id="hoverPie"></div></td>
				<td>
					<div style="height:395px;width:101% !important;width:100%">
						<div id="chartLoading">
							<div id="loadingScreenChart" style="display: none;background-color:#ffffff"><div id='loadingMessageChart' style="font-size:110%;color:#FF8C10;font-weight:bold;padding-left:20px;line-height:36px">数据加载中，请稍候...</div></div>
						</div>
						
						<div id="trenderId" style="height:395px;border:1px solid #E4F1FA">
							<div id="fFont" style="height:395px;text-align:center;font-size:170%;color:#FF8C10;font-weight:bold;line-height:394px">数据加载中，请稍后...</div>
						</div>
					</div>
				</td>
			</tr>
		</table>
	</div>
	<!-- [BUG]0012888 MODIFY END -->
</body>
</html>