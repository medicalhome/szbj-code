<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="translation.tld" prefix="ref"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="html2.tld" prefix="html2"%>
<%@ taglib uri="html.tld" prefix="html"%>
<jsp:useBean id="Constants" class="com.yly.cdr.core.Constants" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <title></title>
    <link rel="Stylesheet" type="text/css" href="../styles/tablelist.css" />
    <script type="text/javascript" src="../scripts/diagnosis/diagnosisList.js"></script>
    <script>
        $(function() {
            // $Author:wu_jianfeng
            // $Date : 2012/10/24 14:10
            // $[BUG]0010542 MODIFY BEGIN
            $("#diagnosticDept").htmlSelectSuggest({
                onKeyUp: function(event) {
                    if (event.keyCode == 13) {
                        search('../diagnosis/list_${patientSn}.html', 'conditionForm');
                    }
                }
            });
            // $[BUG]0010542 MODIFY END
            /* $Author :chang_xuewen
	   		 $Date : 2013/07/04 11:00
	   		 $[BUG]0033461 ADD BEGIN */
	   		//调用动态表格美化
	   		beautyTable();
	   		/* $[BUG]0033461 ADD BEGIN */
	   		logical();
        });
    </script>
</head>
<body style="margin: 0; padding: 0;">
	<form id="conditionForm" name="conditionForm" method="post"
		action="../diagnosis/list_${patientSn}.html"  <c:if test="${diagnosisListSearchPra.visitSn != null}">style="display:none;"</c:if>>
		<table class="blockTable" cellpadding="2" cellspacing="1" border="1px"
			bordercolor="#BFD9F0">
			<tr style="height: 28px;" id="tableheader">
				<td class="blockHeader" colspan="10" height="27" align="left"
					style="border-top: solid 1px #B3C4D4;"><b><img
						src="../images/pic_zd_right.gif"
						style="padding-left: 3px; padding-right: 2px;" width="16"
						height="16" alt="" align="absmiddle" />诊断</b></td>
			</tr>
			<tr class="conditionRow">
				<td colspan="10" height="36" align="left" valign="middle">
					<div class="cell" style="width: 185px;">
						<div class="cell" style="width: 55px;text-align: right;">诊断类型</div>
						<div class="cell" style="width: 120px;">
							<select name="diagnosisType" style="width: 120px;">
								<option value="${Constants.OPTION_SELECT}">请选择</option>
								<html:options domain="${Constants.DOMAIN_DIAGNOSIS_TYPE}"
									value='${diagnosisListSearchPra.diagnosisTypeCode}' />
							</select>
						</div>
					</div>
					<div class="cell" style="width: 185px;">
						<div class="cell" style="text-align: right; width:55px;">诊断名称</div>
						<div class="cell" style="width:120px;">
							<input name='diseaseName' type='text'  style="width:110px;" onmouseover="this.style.background='#FDE8FE';"
								onmouseout="this.style.background='#FFFFFF'" value='${diagnosisListSearchPra.diseaseName}' />
						</div>
					</div>
					<div class="cell" style="width: 195px;">
						<div class="cell" style="text-align: right; width:55px;">诊断科室</div>
		                <!-- $Author :wu_jianfeng
		 					 $Date : 2012/10/24 17:08$
							 [BUG]0010542 MODIFY BEGIN -->
						<div class="cell" style="width:130px;">
							<select  id="diagnosticDept" name="diagnosticDept">
								<option value="${Constants.OPTION_SELECT}">请选择</option>
								<html2:pycodeoptions domain="${Constants.DOMAIN_DEPARTMENT}"
									value="${diagnosisListSearchPra.diagnosticDeptId}" />
							</select>
						</div>
					</div>
	                <!-- [BUG]0010542 MODIFY END -->
	                <div class="cell" style="width: 160px;">
						<div class="cell" style="width: 70px;">
							<input type="button"
								style="color: #464646; border: 0px; BACKGROUND-IMAGE: url(../images/5.jpg); width:57px; height: 25px; margin-top: 3px;cursor: pointer;"
								align="absmiddle"
								onclick="search('../exam/list_${patientSn}.html', 'conditionForm');" />
						</div>
						<div id="toggleBlock" class="container-on cell">
							  <input type="button" id="buttonBlock" style="color:#464646;border:0px;BACKGROUND-IMAGE:url(../images/6.jpg);width:77px;height:25px;margin-top:3px;cursor: pointer;" onclick="showTr('tr1',this)"   align="absmiddle" />  <input type="hidden" id="conditionValue"
								name="conditionValue" value="${conditionValue}" /> <input
								type="hidden" id="senSave" name="senSave" value="${senSave}" />
								<input type="hidden" name="type" value="${diagnosisListSearchPra.type}" />
								<input type="hidden" name="visitSn" value="${diagnosisListSearchPra.visitSn}" />
								<input type="hidden" name="inpatientDate" value="${diagnosisListSearchPra.inpatientDate}" />
						</div>
					</div>
				</td>
			</tr>
			<c:if test="${Constants.TRUE_ORG_CODE == Constants.DISABLE_ORG_CODE}">
				<tr class="conditionRow">
					<td colspan="10" height="36" align="left" valign="middle">
						<div class="cell" style="width: 180px;">
							<div class="cell" style="text-align: right; width:55px;">医疗机构</div>
							<div class="cell" style="width: 115px;">
								<select name="orgCode" style="width: 110px;">
									<option value="${Constants.OPTION_SELECT}">请选择</option>
									<html:options domain="${Constants.DOMAIN_ORG_CODE}"
										value='${diagnosisListSearchPra.orgCode}' />
								</select>
							</div>
						</div>
					</td>
				</tr>
			</c:if>
			<tr id='tr1' class="conditionRow moreCondition off">
				<td colspan="10" height="36" align="left" valign="middle">
					<div class="cell" style="width: 190px;">
						<div class="cell" style="text-align: right; width:55px;">诊断医生</div>
						<div class="cell" style="width: 120px;">
								<input name='diagnosisDoctor' style="width: 110px;" type='text' onmouseover="this.style.background='#FDE8FE';"
								onmouseout="this.style.background='#FFFFFF'"
								value='${diagnosisListSearchPra.diagnosisDoctorName}' />
						</div>
					</div>
					<div class="cell" style="width: 260px;">
						<div class="cell" style="text-align: right; width:55px;">诊断日期</div>
						<div class="cell">
							<input type="text" id="diagnosisDateFromStr" name="diagnosisDateFromStr" style="width: 65px;"
								value='${diagnosisListSearchPra.diagnosisDateFromStr}'
								onmouseover="this.style.background='#FDE8FE';"
								onmouseout="this.style.background='#FFFFFF'" 
								class="datepicker" /> <span
								style="margin: 0 4px 0 4px;">--</span> <input type="text" id="diagnosisDateToStr" name="diagnosisDateToStr" style="width: 80px;"
								value='${diagnosisListSearchPra.diagnosisDateToStr}'
								onmouseover="this.style.background='#FDE8FE';"
								onmouseout="this.style.background='#FFFFFF'" 
								class="datepicker" />
						</div>
					</div>
					<div class="cell" style="width: 165px;">
						<div class="cell" style="text-align: right; width:73px;">是否主要诊断</div>
						<div class="cell" style="width:80px;">
							<select name="mainDiagnosisFlag" style="width:80px;">
								<option value="${Constants.OPTION_SELECT}">请选择</option>
								<html:options domain="${Constants.DOMAIN_BOOLEAN_UNCERTAIN}"
									value='${diagnosisListSearchPra.mainDiagnosisFlag}' />
							</select>
						</div>
					</div>
					<div class="cell" style="width: 145px;">
						<div class="cell" style="text-align: right; width: 55px;">是否待查</div>
						<div class="cell" style="width: 80px;">
							<select name="uncertainFlag">
								<option value="${Constants.OPTION_SELECT}">请选择</option>
								<html:options domain="${Constants.DOMAIN_BOOLEAN_UNCERTAIN}"
									value='${diagnosisListSearchPra.uncertainFlag}' />
							</select>
						</div>
					</div>
				<!-- </td>
			</tr>
			<tr id='tr2' class="conditionRow moreCondition off">
				<td width="100%" height="36" align="left" valign="middle"> -->	
					<div class="cell" style="width: 180px;">
						<div class="cell" style="text-align: right; width:60px;">是否传染病</div>
						<div class="cell" style="width: 110px;">
							<select name="contagiousFlag" style="width: 110px;">
								<option value="${Constants.OPTION_SELECT}">请选择</option>
								<html:options domain="${Constants.DOMAIN_BOOLEAN_UNCERTAIN}"
									value='${diagnosisListSearchPra.contagiousFlag}' />
							</select>
						</div>
					</div>
				</td>
			</tr>
			<!-- $Author :chang_xuewen
			$Date : 2013/07/04 11:00
			$[BUG]0033461 MODIFY BEGIN -->
			<tr class="tabletitle">
				<td height="28" align="center" width="10%">诊断类型</td>
				<td height="28" align="center" width="18%">诊断名称</td>
				<td height="28" align="center" width="18%">科室</td>
				<td height="28" align="center" width="15%">医生</td>
				<td height="28" align="center" width="14%">诊断日期</td>
				<td height="28" align="center" width="10%">就诊类别</td>
				<td height="28" align="center" width="10%">是否主要诊断</td>
				<td height="28" align="center" width="5%">待查</td>
				<td height="28" align="center" width="5%" style="display:none">传染病</td>
				<c:if test="${Constants.TRUE_ORG_CODE == Constants.DISABLE_ORG_CODE}">
					<td height="28" align="center" width="11%">医疗机构</td>
				</c:if>
				
			</tr>
			<!-- [BUG]0033461 MODIFY END -->
		</table>
	</form>
	<div id='listcon'>
	<!--[if lt ie 8]><div style='+zoom:1;'><![endif]-->
	 <table id="tblid" style="width: 100%;" cellspacing="1" cellpadding="2" class="table">		
		<c:if test="${fn:length(diagnosisList)==0}">
							<tr>
								<td colspan="9" align="center" class="odd" height="24">没有相关数据！</td>
							</tr>
						</c:if>
		<c:forEach items="${diagnosisList}" var="diagnosisList"
			varStatus="status">
			<!-- $Author :chang_xuewen
			$Date : 2013/07/04 11:00
			$[BUG]0033461 MODIFY BEGIN -->
			<tr id='${diagnosisList.diagnosisSn}'  style="cursor: pointer" class="tabEnter"
				<c:if test="${diagnosisListSearchPra.visitSn != null}">onclick="showDialog('../diagnosis/detail_${diagnosisList.diagnosisSn}.html', '诊断详细',{},700,500,'#ajaxDialogDetail');"</c:if>  
				onclick="bigShow($(this),'../diagnosis/detail_${diagnosisList.diagnosisSn}.html?flag=tabs',
{'name':'${diagnosisList.diseaseName}','holdName':'${diagnosisList.diseaseName}','otherName':'','holdOtherName':'','orderSn':'${diagnosisList.diagnosisSn}'});">
			<!-- $[BUG]0033461 MODIFY END -->
				<td height="24" align="left" width="10%">${diagnosisList.diagnosisTypeName}</td>
				<td height="24" align="left" width="18%" <c:if test="${fn:length(diagnosisList.diseaseName)>10}"> title="${diagnosisList.diseaseName}" </c:if>>
				<c:if test="${fn:length(diagnosisList.diseaseName)>10}">${fn:substring(diagnosisList.diseaseName,0,10)}...</c:if>
                <c:if test="${fn:length(diagnosisList.diseaseName)<=10}">${diagnosisList.diseaseName}</c:if></td>
				<td height="24" width="18%" align="left" <c:if test="${fn:length(diagnosisList.diagnosticDeptName)>10}"> title="${diagnosisList.diagnosticDeptName}" </c:if>>
				<c:if test="${fn:length(diagnosisList.diagnosticDeptName)>10}">${fn:substring(diagnosisList.diagnosticDeptName,0,10)}...</c:if>
                <c:if test="${fn:length(diagnosisList.diagnosticDeptName)<=10}">${diagnosisList.diagnosticDeptName}</c:if></td>
				<td height="24" width="15%" align="left" <c:if test="${fn:length(diagnosisList.diagnosisDoctorName)>10}"> title="${diagnosisList.diagnosisDoctorName}" </c:if>>
				<c:if test="${fn:length(diagnosisList.diagnosisDoctorName)>10}">${fn:substring(diagnosisList.diagnosisDoctorName,0,10)}...</c:if>
                <c:if test="${fn:length(diagnosisList.diagnosisDoctorName)<=10}">${diagnosisList.diagnosisDoctorName}</c:if></td>
				<td height="24" width="14%" align="center"><fmt:formatDate
											value="${diagnosisList.diagnosisDate}" type="date"
											pattern="yyyy-MM-dd" /></td>
				<td height="24" width="10%" align="center">${diagnosisList.visitTypeName}</td>
				<td height="24" width="10%" align="center"><c:if test= "${diagnosisList.mainDiagnosisFlag==Constants.MAIN_DIAGNOSIS}"><img src="../images/yes.png" align="middle" style="padding-right: 1px;border:0px" /></c:if>
			
				<%-- <ref:translate
						domain="${Constants.DOMAIN_BOOLEAN_UNCERTAIN}"
						code="${diagnosisList.mainDiagnosisFlag}" /> --%></td>
				<td height="24" width="5%" align="center"><%-- <ref:translate
						domain="${Constants.DOMAIN_BOOLEAN_UNCERTAIN}"
						code="${diagnosisList.uncertainFlag}" /> --%><c:if test= "${diagnosisList.uncertainFlag==Constants.CERTAIN_DB}"><img src="../images/yes.png" align="middle" style="padding-right: 1px;border:0px" /></c:if></td>
				<td height="24" width="5%" align="center" style="display:none">
				<c:if test= "${diagnosisList.contagiousFlag==1}"><img src="../images/yes.png" align="middle" style="padding-right: 1px;border:0px" /></c:if>
				<%-- <ref:translate
						domain="${Constants.DOMAIN_BOOLEAN_UNCERTAIN}"
						code="${diagnosisList.contagiousFlag}" /> --%></td>
				<c:if test="${Constants.TRUE_ORG_CODE == Constants.DISABLE_ORG_CODE}">
					<td height="24" width="11%" align="center">${diagnosisList.orgName}</td>
				</c:if>
			</tr>
		</c:forEach>
		<!-- $Author :chang_xuewen
			$Date : 2013/07/04 11:00
			$[BUG]0033461 MODIFY BEGIN -->
		<tr class="page_line">
		<!-- $[BUG]0033461 MODIFY END -->
			<td colspan="10" style="height: 27px;">
				<form name="pagingform" method="get"
					action="../diagnosis/list_${patientSn}.html">
					<div class="pagelinks">
					<div style="float: right; height: 100%;">
						<div class="pagedesc">共${pagingContext.totalRowCnt}条记录！      第${pagingContext.pageNo}页/共${pagingContext.totalPageCnt}页</div>
							<c:if test="${pagingContext.pageNo > 1}">
								<div class="firstPage">
									<a href="javascript:void(0);"
										<c:if test="${diagnosisListSearchPra.visitSn != null}">onclick="jumpToPage(1,'#ajaxDialog');return false;"</c:if>
										onclick="jumpToPage(1);return false;"><img
										src="../images/1.gif"
										style="border: 0px; width: 21px; height: 16px;" /></a>
								</div>
								<div class="prevPage">
									<a href="javascript:void(0);"
										<c:if test="${diagnosisListSearchPra.visitSn != null}">onclick="jumpToPage(${pagingContext.pageNo-1},'#ajaxDialog');return false;"</c:if>
										onclick="jumpToPage(${pagingContext.pageNo-1});return false;"><img
										src="../images/2.gif"
										style="border: 0px; width: 41px; height: 16px;" /></a>
								</div>
							</c:if>
							<c:forEach var="i" begin="${pagingContext.pageStartNo}" end="${pagingContext.perPageCnt}"
								step="1">
								<c:choose>
									<c:when test="${i == pagingContext.pageNo}">
										<div class="currentPage">
											<font color="#2D56A5">${i}</font>
										</div>
									</c:when>
									<c:otherwise>
										<div class="pageno">
											<a href="javascript:void(0);"
												<c:if test="${diagnosisListSearchPra.visitSn != null}">onclick="jumpToPage(${i},'#ajaxDialog');return false;"</c:if>
												onclick="jumpToPage(${i}); return false;"><font
												color="#2D56A5">${i}</font></a>
										</div>
									</c:otherwise>
								</c:choose>
							</c:forEach>
							<c:if test="${pagingContext.pageNo < pagingContext.totalPageCnt}">
								<div class="nextPage">
									<a href="javascript:void(0);"
										<c:if test="${diagnosisListSearchPra.visitSn != null}">onclick="jumpToPage(${pagingContext.pageNo + 1},'#ajaxDialog');return false;"</c:if>
										onclick="jumpToPage(${pagingContext.pageNo + 1});return false;"><img
										src="../images/4.gif"
										style="border: 0px; width: 41px; height: 16px;" /></a>
								</div>
								<div class="lastPage">
									<a href="javascript:void(0);"
										<c:if test="${diagnosisListSearchPra.visitSn != null}">onclick="jumpToPage(${pagingContext.totalPageCnt},'#ajaxDialog');return false;"</c:if>
										onclick="jumpToPage(${pagingContext.totalPageCnt});return false;"><img
										src="../images/3.gif"
										style="border: 0px; width: 21px; height: 16px;" /></a>
								</div>
							</c:if>
							<div class="pageNum">
								<input type="text" name="screen" style="display:none;"/>
								<input name="pageNum"
									<c:if test="${diagnosisListSearchPra.visitSn != null}">onkeyup="if(event.keyCode!=13){return false;} jumpToPageNo($('#tblid').find('[name=pageNum]').val(),${pagingContext.totalPageCnt},'#ajaxDialog');return false;"</c:if>
								    onkeyup="if(event.keyCode!=13){return false;} jumpToPageNo($('#tblid').find('[name=pageNum]').val(),${pagingContext.totalPageCnt});return false;"
								    style="width: 30px; float: left;" value="" />
							</div>
							<div class="goinput">
								<a href="javascript:void(0);"
									<c:if test="${diagnosisListSearchPra.visitSn != null}">onclick="jumpToPageNo($('#tblid').find('[name=pageNum]').val(),${pagingContext.totalPageCnt},'#ajaxDialog');return false;"</c:if>
									onclick="jumpToPageNo($('#tblid').find('[name=pageNum]').val(),${pagingContext.totalPageCnt});return false;"><img
									src="../images/go.gif"
									style="border: 0px; width: 21px; height: 15px;" /></a>
							</div>
							<div id="ajaxDialogDetail" style="display: none;">
								<iframe id="dialogFrameDetail" style="width: 100%; height: 100%;" src=""
									frameborder="0"> </iframe>
							</div>
						</div>
				</div>
				</form>
			</td>
		</tr>
	</table>
	 <!--[if lt ie 8]></div><![endif]-->
	</div>
</body>
</html>
