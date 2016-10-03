<%@ page language="java" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="translation.tld" prefix="ref"%>
<%@ taglib uri="html.tld" prefix="html"%>
<jsp:useBean id="Constants" class="com.yly.cdr.core.Constants" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Pragma" content="no-cache" />
	<!-- Prevents caching at the Proxy Server -->
	<meta http-equiv="Expires" content="0" />
	<title>患者列表</title>
	<link rel="Stylesheet" type="text/css" href="../styles/tablelist.css" />
	<script type="text/javascript" src="../scripts/patient/patientList.js"></script>
	<script type="text/javascript" src="../scripts/dialog-tabs.js"></script>
	<script>
		$("#conditionFormPatientList #patientName").keyup(function(event){
			if (event.keyCode==13) 
		    { 
		    	searchFavPatient('', 'conditionFormPatientList','#pfcontent');
		    } 
		});
		
		$(function()
		{
			$("input[type='button']").removeAttr("onfocus");
			$("input[type='checkbox']").removeAttr("onfocus");
			
			logicalPatient();
		});
	   
	</script>
</head>
<body style="margin: 0; padding: 0;">
	<form id="conditionFormPatientList" name="conditionFormPatientList" method="post"
		action="../patient/list_pfpatientlist_${folderId}.html?flag=${loginFlag}">
			<input type="hidden" id="pl_PostbackEvent" name="postbackEvent" value="" /> 
			<input type="hidden" id="pl_PatientFavIds" name="patientFavIds" value="" />
		<table class="blockTable" style="width: 99%;" cellpadding="2" align="center"
			cellspacing="0" border="0">
			<tr class="conditionRow">
				<td width="100%" height="26" align="right" valign="middle">
					<div class="cell" style="width: 70px;">姓名</div>
					<div class="cell" style="width: 93px; text-align: right;">
						<input type="text" id="patientName" name="patientName"
							style="width: 98%;"
							onmouseover="this.style.background='#FDE8FE';"
							onmouseout="this.style.background='#FFFFFF'"
							value="${patientFavListSearchPra.patientName}" />
					</div>
					<div class="cell" style="width: 50px; padding-left: 5px">性别</div>
					<div class="cell" style="width: 100px; padding-right: 4px;">
						<select id="gender" name="gender" style="width: 98%;">
							<option value="${Constants.OPTION_SELECT}">请选择</option>
							<html:options domain="${Constants.DOMAIN_GENDER}"
								value='${patientFavListSearchPra.genderCode}' />
						</select>
					</div>
					<div class="cell" style="text-align: right; padding-left: 20px">
						<input type="button" onclick="searchFavPatient('', 'conditionFormPatientList','#pfcontent');"							
							onfocus="this.blur()"
							style="border: 0px; BACKGROUND-IMAGE: url(../images/5.jpg); width: 57px; height: 25px; margin-top: 3px; cursor: pointer;"
							align="absmiddle" /> 
						<input type="button" onclick="deleteFavPatient('', 'conditionFormPatientList','#pfcontent');"							
							onfocus="this.blur()"
							style="border: 0px; BACKGROUND-IMAGE: url(../images/schz.jpg); width: 74px; height: 25px; margin-top: 3px; cursor: pointer;"
							align="absmiddle" value="" />
					</div>
				</td>
			</tr>
		</table>
		<table id="tb_patient" style="width: 99%;" align="center" class="table"
			cellspacing="1" cellpadding="2" border="0" valign="top">
			<tr class="tabletitle">
				<td height="28" align="center" width="5%"><input
					type="checkbox" class="tabEnterPatient"
					onclick="selectAllOrNo('selectOrNot','patientFavId')"
					id="selectOrNot" title="全选或全不选"></td>
				<!-- <td height="28" align="center" width="28%">就诊卡号/住院号</td> -->
				<td height="28" align="center" width="28%">病人号</td>
				<td height="28" align="center" width="12%">姓名</td>
				<td height="28" align="center" width="5%">性别</td>
				<td height="28" align="center" width="16%">就诊日期</td>
				<td height="28" align="center" width="8%">年龄</td>
				<td height="28" align="center" width="12%">病区</td>
				<td height="28" align="center" width="10%">床号</td>
			</tr>
			<!--  $Author: wu_jianfeng
             $Date : 2013/02/25 15:10
             $[BUG]0011609 MODIFY BEGIN -->
			<c:choose>
				<c:when test="${queryFlag==0}">
					<tr>
						<td colspan="8" align="center" class="odd" height="24">请先选择收藏夹！</td>
					</tr>
				</c:when>
				<c:otherwise>
					<c:if test="${fn:length(patientList)==0}">
						<tr>
							<td colspan="8" align="center" class="odd" height="24">当前选中的文件夹中没有相关患者！</td>
						</tr>
					</c:if>
				</c:otherwise>			
			</c:choose>
			<!-- $[BUG]0011609 MODIFY END -->

			<c:forEach items="${patientList}" var="patient" varStatus="status">
			<!--  $Author:bin_zhang
             $Date : 2012/12/25 15:10
             $[BUG]0012700 ADD BEGIN -->
				<tr
					<c:if test="${status.count%2==1}">class="odd tabEnterPatient" onmouseout="bzHide();this.className='odd'"</c:if>
					<c:if test="${status.count%2==0}">class="even tabEnterPatient" onmouseout="bzHide();this.className='even'"</c:if>
					style="cursor: pointer" onmouseover="bzShow(${patient.patientSn});this.className='mouseover'"
					onclick="javascript:location.href='../visit/${patient.patientSn}.html';"> 
			<!-- $[BUG]0012700 ADD END -->
					<td height="24" align="center"><a href="javascript:void(0)" name="controlCheckbox" class="tabEnterPatient"><input id="patientFavId"
						name="patientFavId" type="checkbox" class="tabEnterPatient"
						value="${patient.patientFavSn}" tabindex="-1"
						onclick="window.event.cancelBubble=true;" onfocus="this.blur()" /></a></td>
					<%-- <td height="24" align="left" onmouseout="removeTabsTip('#tabstip')"
					<c:if test="${patient.cardFlag=='1'}">				
						onmouseover="showPatientDetailTip(event,'#tabstip',${status.count},${patient.outpatientNoJson},${patient.inpatientNoJson});"
					</c:if>>${patient.outpatientNo}/${patient.inpatientNo}
						<c:if test="${patient.cardFlag=='1'}">		
							<img src="../images/cardsflag.gif" align="middle" style="width:20px;height:20px;padding-right: 1px;border:0px"/>
						</c:if>
					</td> --%>
					<td height="24" align="left">${patient.patientEid}</td>
					<td height="24" align="left">${patient.patientName}</td>
					<td height="24" align="center">${patient.genderName}</td>
					<td height="24" align="center"><fmt:formatDate
							value="${patient.visitDate}" type="both" pattern="yyyy-MM-dd" />
					</td>
					<td height="24" align="left">${patient.age}<span id="pf${patient.patientSn}" style="display:none">${patient.memo}</span></td>
					<td height="24" align="left">${patient.dischargeWardName}</td>
					<td height="24" align="left">${patient.dischargeBedNo}</td>
				</tr>
				<div id="ajaxDialog${status.count}" class="ajaxDialog"
					style="display: block; z-index: 10000;">
				</div>
			</c:forEach>
			<tr>
				<td colspan="8" style="height: 27px;">
					<div class="pagelinks">
						<div style="float: center; height: 100%;">
							<div class="pagedesc">共${pagingContext.totalRowCnt}条记录！
								第${pagingContext.pageNo}页/共${pagingContext.totalPageCnt}页</div>
							<c:if test="${pagingContext.pageNo > 1}">
								<div class="firstPage">
									<a href="javascript:void(0);"
										onclick="jumpToPatientFavListPage(1);return false;"><img
										src="../images/1.gif"
										style="border: 0px; width: 12px; height: 16px;" /></a>
								</div>
								<div class="prevPage">
									<a href="javascript:void(0);"
										onclick="jumpToPatientFavListPage(${pagingContext.pageNo-1});return false;"><img
										src="../images/2.gif"
										style="border: 0px; width: 18px; height: 16px;" /></a>
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
												onclick="jumpToPatientFavListPage(${i}); return false;"><font
												color="#2D56A5">${i}</font></a>
										</div>
									</c:otherwise>
								</c:choose>
							</c:forEach>
							<c:if test="${pagingContext.pageNo < pagingContext.totalPageCnt}">
								<div class="nextPage">
									<a href="javascript:void(0);"
										onclick="jumpToPatientFavListPage(${pagingContext.pageNo + 1});return false;"><img
										src="../images/4.gif"
										style="border: 0px; width: 18px; height: 16px;" /></a>
								</div>
								<div class="lastPage">
									<a href="javascript:void(0);"
										onclick="jumpToPatientFavListPage(${pagingContext.totalPageCnt});return false;"><img
										src="../images/3.gif"
										style="border: 0px; width: 12px; height: 16px;" /></a>
								</div>
							</c:if>
							<div class="pageNum">
								<input type="text" name="screen" style="display: none;" /> <input
									name="pageNum"
									onkeyup="if(event.keyCode!=13){return false;} jumpToPatientFavListPageNo($('#tb_patient').find('[name=pageNum]').val(),${pagingContext.totalPageCnt});return false;"
									style="width: 20px; float: left;" value="" />
							</div>
							<div class="goinput">
								<a href="javascript:void(0);"
									onclick="jumpToPatientFavListPageNo($('#tb_patient').find('[name=pageNum]').val(),${pagingContext.totalPageCnt});return false;"><img
									src="../images/go.gif"
									style="border: 0px; width: 18px; height: 15px;" /></a>
							</div>
						</div>
					</div>
				</td>
			</tr>
		</table>
	</form>

	<!--  $Author: wu_jianfeng
     $Date : 2013/02/25 15:10
     $[BUG]0011609 MODIFY BEGIN -->
	<c:if test="${fn:length(patientList)!=0}">
	<table cellspacing="0" cellpadding="0" border="0" width="100%">
		<tr>
			<td colspan="2" height="10"></td>
		</tr>
		<tr>
			<td width="70px" valign="top">&nbsp;备注信息：</td>
			<td valign="top" style="height:100px;">
				<span id='bz'></span>
			</td>
		</tr>
	</table>
	</c:if>
	<!-- $[BUG]0011609 MODIFY END -->
</body>
</html>
