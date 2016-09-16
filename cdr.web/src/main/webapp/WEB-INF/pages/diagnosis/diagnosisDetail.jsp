<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="translation.tld" prefix="ref"%>
<jsp:useBean id="Constants" class="com.founder.cdr.core.Constants" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<script>
    $(document).ready(function(){

    	var orderSn = '${diagnosisDetail.diagnosisSn}';
    	
   	 	closeTab("#moreTabs",orderSn);
   	 
   	 /* $("#moreTabs").tabs();
   	 tabsBind("#moreTabs",orderSn);
   	 $('#moreTabs').tabs({add:addEventHandler}); 
   	 
   	 var firstLi = $("#select_moreTabs1");
   	 
  	 $("#moreTabs").children("ul").children("li").children("a").css(
		"background-image", "url(../images/title2.png)");
  	 
  	 firstLi.children("a").css("background-image","url(../images/title2.png)").addClass("parentli");
  	 
  	 $('<img src="../images/close.png"/ style="position:absolute;right:3px;top:10px;cursor:pointer;">').appendTo(firstLi).click(
	 function(){ //关闭按钮,关闭事件绑定 
		 var index = $('#moreTabs ul:first>li').index(firstLi.get(0));
		 
		 var orderSn = '${diagnosisDetail.diagnosisSn}';
		 
		 initPanel('_'+orderSn, index, $("#moreTabs"));
	 }); */
   });
   </script>

</head>
<body>
	<div id="dialog">
		<div id="mainContent">
			<div id="_${diagnosisDetail.diagnosisSn}" name="selectTabs">
				<div id="tabs-1" class="tabcontainer">
					<table width="100%" cellpadding="2" cellspacing="1"
						style="border: solid 1px #c0ddea; border-collapse: collapse; border-bottom: 0px;">
						<tr>
							<td class="label">诊断类型:</td>
							<td class="dataitem">${diagnosisDetail.diagnosisTypeName}</td>
							<td class="label">诊断名称:</td>
							<td class="dataitem">${diagnosisDetail.diseaseName}</td>
						</tr>
						<tr>
							<td class="label">科室:</td>
							<td class="dataitem">${diagnosisDetail.diagnosticDeptName}</td>
							<td class="label">医生:</td>
							<td class="dataitem">${diagnosisDetail.diagnosisDoctorName}</td>
						</tr>
						<tr>
							<td class="label">诊断日期:</td>
							<td class="dataitem"><fmt:formatDate
									value="${diagnosisDetail.diagnosisDate}" type="date"
									pattern="yyyy-MM-dd HH:mm" /></td>
							<td class="label">就诊类别:</td>
							<td class="dataitem">${diagnosisDetail.visitTypeName}</td>
						</tr>
						<tr>
							<td class="label">是否主要诊断:</td>
							<td class="dataitem"><%-- <ref:translate
									domain="${Constants.DOMAIN_BOOLEAN_UNCERTAIN}"
									code="${diagnosisDetail.mainDiagnosisFlag}" /> --%>
								<c:if test="${diagnosisDetail.mainDiagnosisFlag == Constants.MAIN_DIAGNOSIS}">
									是
								</c:if>
								<c:if test="${diagnosisDetail.mainDiagnosisFlag != Constants.MAIN_DIAGNOSIS}">
									否
								</c:if>
							</td>
							<td class="label">待查:</td>
							<td class="dataitem"><%-- <ref:translate
									domain="${Constants.DOMAIN_BOOLEAN_UNCERTAIN}"
									code="${diagnosisDetail.uncertainFlag}" /> --%>
								<c:if test="${diagnosisDetail.uncertainFlag == Constants.CERTAIN_DB}">
									是
								</c:if>
								<c:if test="${diagnosisDetail.uncertainFlag == Constants.UN_CERTAIN_DB}">
									否
								</c:if>
							</td>
						</tr>
						<tr style="display:none">
							<td class="label">传染病:</td>
							<td class="dataitem"><ref:translate
									domain="${Constants.DOMAIN_BOOLEAN_UNCERTAIN}"
									code="${diagnosisDetail.contagiousFlag}" /></td>
									
							<c:choose>
								<c:when test="${Constants.TRUE_ORG_CODE == Constants.DISABLE_ORG_CODE}">
									<td class="label">医疗机构</td>
									<td class="dataitem">${diagnosisDetail.orgName}</td>
								</c:when>
								<c:otherwise>
									<td class="label"></td>
									<td class="dataitem"></td>
								</c:otherwise>
							</c:choose>
						
						</tr>
					</table>
					<%--
						$Author :bin_zhang
			               $Date : 2012/09/03 14:28$
			               [BUG]0007972 ADD BEGIN 
					--%>
					<table width="100%" cellpadding="2" cellspacing="1" border="0px;">
						<c:if test="${diagnosisDetail.mainDiagnosisFlag==mainDiagnosis}">
							<tr class="even">
								<td colspan="4" align="right"
									style="border: solid 0px #c0ddea; padding-right: 3px; height: 10px">
									<c:choose>
										<c:when test="${fn:length(diagnosisCiDetail)==0}">
											<b>无次要诊断！</b>
										</c:when>
										<c:otherwise>
											<div style="display: block;" width="100%" id="panel">
												<table width="100%" cellpadding="2" cellspacing="1"
													class="table" align="center">
													<tr style="height: 28px;">
														<td class="blockHeader" colspan="5" height="27" align="left"
															style="border-top: solid 1px #B3C4D4;"><b><img
																src="../images/pic_zd_right.gif"
																style="padding-left: 3px; padding-right: 2px;" width="16"
																height="16" alt="" align="absmiddle" />次要诊断信息</b></td>
													</tr>
													<tr class="tabletitle">
														<td height="28" align="center" width="20%">诊断类型</td>
														<td height="28" align="center" width="25%">疾病名称</td>
														<td height="28" align="center" width="20%">科室</td>
														<td height="28" align="center" width="15%">医生</td>
														<td height="28" align="center" width="20%">诊断日期</td>
													</tr>
													<c:forEach items="${diagnosisCiDetail}"
														var="diagnosisCiDetail" varStatus="loopDiagnosis">
														<tr
															<c:if test="${loopDiagnosis.count%2==1}">class="odd" onmouseout="this.className='odd'"</c:if>
															<c:if test="${loopDiagnosis.count%2==0}">class="even" onmouseout="this.className='even'"</c:if>>
															<td height="24" align="left" width="19%">${diagnosisCiDetail.diagnosisTypeName}</td>
															<td height="24" align="left" width="24%"
																<c:if test="${fn:length(diagnosisCiDetail.diseaseName)>10}"> title="${diagnosisCiDetail.diseaseName}" </c:if>>
																<c:if
																	test="${fn:length(diagnosisCiDetail.diseaseName)>10}">${fn:substring(diagnosisCiDetail.diseaseName,0,10)}...</c:if>
																<c:if
																	test="${fn:length(diagnosisCiDetail.diseaseName)<=10}">${diagnosisCiDetail.diseaseName}</c:if>
															</td>
															<td height="24" align="left" width="20%"
																<c:if test="${fn:length(diagnosisCiDetail.diagnosticDeptName)>10}"> title="${diagnosisCiDetail.diagnosticDeptName}" </c:if>>
																<c:if
																	test="${fn:length(diagnosisCiDetail.diagnosticDeptName)>10}">${fn:substring(diagnosisCiDetail.diagnosticDeptName,0,10)}...</c:if>
																<c:if
																	test="${fn:length(diagnosisCiDetail.diagnosticDeptName)<=10}">${diagnosisCiDetail.diagnosticDeptName}</c:if>
															</td>
															<td height="24" align="left" width="15%"
																<c:if test="${fn:length(diagnosisCiDetail.diagnosisDoctorName)>10}"> title="${diagnosisCiDetail.diagnosisDoctorName}" </c:if>>
																<c:if
																	test="${fn:length(diagnosis.diagnosisDoctorName)>10}">${fn:substring(diagnosisCiDetail.diagnosisDoctorName,0,10)}...</c:if>
																<c:if
																	test="${fn:length(diagnosis.diagnosisDoctorName)<=10}">${diagnosisCiDetail.diagnosisDoctorName}</c:if>
															</td>
															<td height="24" align="center" width="22%"><fmt:formatDate
																	value="${diagnosisCiDetail.diagnosisDate}" type="date"
																	pattern="yyyy-MM-dd HH:mm" /></td>
														</tr>
													</c:forEach>
												</table>
											</div>
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
						</c:if>
						<%-- [BUG]0007972 ADD END --%>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
