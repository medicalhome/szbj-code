<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="translation.tld" prefix="ref"%>
<%@ taglib uri="html.tld" prefix="html"%>
<jsp:useBean id="Constants" class="com.yly.cdr.core.Constants"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Pragma" content="no-cache" />
    <!-- Prevents caching at the Proxy Server -->
    <meta http-equiv="Expires" content="0" />
    <!-- $Author :liu_hongjie
 	$Date : 2014/8/21 14:57$
	[BUG]0047820 MODIFY BEGIN -->
    <title>${pageTitle}</title>
    <!--   $[BUG]0047820 ADD BEGIN -->
    <link rel="Stylesheet" type="text/css" href="../styles/tablelist.css" />
    <link type="text/css" rel="Stylesheet" href="../styles/jquery-ui-1.8.18.custom.modify.css"
        charset="UTF8" />
    <link type="text/css" rel="Stylesheet" href="../styles/jquery-ui-autocomplete.custom.css"
        charset="UTF8" />
    <link type="text/css" rel="Stylesheet" href="../styles/jquery-ui-combobox.custom.modify.css"
        charset="UTF8" />
    <link type="text/css" rel="Stylesheet" href="../styles/layout-default-1.3.0rc29.15.css"
        charset="UTF8" />
    <link type="text/css" rel="Stylesheet" href="../styles/layout-cdr.css" charset="UTF8" />
    <link type="text/css" rel="Stylesheet" href="../styles/tablelist.css" charset="UTF8" />
    <link type="text/css" rel="Stylesheet" href="../styles/timer-shaft.css" charset="utf-8" />
    <link type="text/css" rel="Stylesheet" href="../styles/loadingScreen.css" charset="UTF8" />
    <link type="text/css" rel="Stylesheet" href="../styles/jquery-suggest.css" charset="UTF8" />
    <link type="text/css" rel="Stylesheet" href="../styles/tabs-menuPart.css" charset="UTF8" />
    <link type="text/css" rel="stylesheet" href="../styles/header.css" charset="UTF8"  />
    <link rel="icon" href="../favicon.ico" type="image/x-icon" />
    <link rel="shortcut icon" type="image/x-icon" href="../favicon.ico" />
    <script type="text/javascript" src="../scripts/jquery-1.7.1.js"></script>
    <script type="text/javascript" src="../scripts/jquery-ui-1.8.18.custom.min.js"></script>
    <script type="text/javascript" src="../scripts/jquery.history.js"></script>
    <script type="text/javascript" src="../scripts/forwardOrBack/forwardOrBack.js"></script>
    <script type="text/javascript" src="../scripts/tabEnter/tabEnter.js"></script>
    <script type="text/javascript" src="../scripts/jquery.ui.autocomplete.js"></script>
    <script type="text/javascript" src="../scripts/jquery.ui.combobox.js"></script>
    
    <script type="text/javascript" src="../scripts/jquery.layout-1.3.0rc29.15.js"></script>
    <script type="text/javascript" src="../scripts/jquery.bgiframe.js"></script>
    <script type="text/javascript" src="../scripts/jquery.suggest.js"></script>
    <script type="text/javascript" src="../scripts/htmlSelectSuggest.js"></script>
    <script type="text/javascript" src="../scripts/htmlMultiSelectSuggest.js"></script>
    <script type="text/javascript" src="../scripts/loadingScreen.js"></script>
    <script type="text/javascript" src="../scripts/common.js"></script>
    <script type="text/javascript" src="../scripts/layout.js"></script>
    <script type="text/javascript" src="../scripts/visit/mainView.js"></script>
    <script type="text/javascript" src="../scripts/dialog-tabs.js"></script>
    <script type="text/javascript" src="../scripts/visit/normalViewPart.js"></script>
    <script type="text/javascript" src="../scripts/help/help.js"></script>

    <link rel="Stylesheet" type="text/css" href="../styles/Css.css" />
    <script language="javascript" src="../scripts/ProSlide.modify.js"></script>
    <script type="text/javascript" src="../scripts/timerInpatient/timerInpatient.js"></script>
	<script type="text/javascript" src="../scripts/doc/docList.js"></script>
	<!-- $Author :chang_xuewen
			 $Date : 2013/07/04 11:00
			 $[BUG]0033461 ADD BEGIN -->
	<script type="text/javascript">
		$(function(){
			// 初始化带有datepicker样式的日期输入框
			setupDatePicker();
			setupDatePickerSetting();
			//调用动态表格美化
			beautyTable();
			
			parent.condition();
			$(".datepicker").bind("blur",parent.isDateString);
			
			//添加页面文本框的回车检索
			$("#conditionForm input").keyup(function(event){
				if (event.keyCode==13) 
			    { 
					search('','conditionForm');
			    } 
			});
			logical();
		});
	</script>
	<!--   $[BUG]0033461 ADD BEGIN -->
</head>
<body style="margin: 0; padding: 0;">
<div id="dynamicContent">
<div id="alertMessage" style="display:none;"></div>
	<form id="conditionForm" name="conditionForm" method="post" 
	action="../portalDoc/list_${patientSn}.html" <c:if test="${DocListSearchParameters.visitSn != null}">style="display:none;"</c:if>>
		<table class="blockTable" cellpadding="2" cellspacing="1" >
			<tr style="height: 28px;" id="tableheader">
				<td  class="blockHeader"style="width: 60px; text-align: center;">姓名: </td>
				<td style="width: 60px; text-align: left;">${patient.patientName}</td>
				<td  class="blockHeader" style="width: 60px; text-align: center;">性别:</td>
				<td style="width: 60px; text-align: left;">${patient.genderName}</td>
				<td class="blockHeader" style="width: 60px; text-align: center;">年龄:</td>
				<td style="width: 60px; text-align: left;">${age}</td>
				
				<%-- <td class="blockHeader" style="width: 70px; text-align: center;">病历号: </td>
				<td style="width: 60px; text-align: left;">${patientCrossIndex.inpatientNo}</td> --%>
				<td width="55%" ></td>
			</tr>
			<tr class="conditionRow">
				<td width="100%" colspan="13" height="36" align="left" valign="middle">
				<input type="hidden" id="visitTimes" name ="visitTimes" value ="${DocListSearchParameters.visitTimes }"/>
				<input type="hidden" id="patientId" name ="patientId" value ="${patientCrossIndex.patientLid }"/>
				<input type="hidden" id="patientDomain" name ="patientDomain" value ="${patientCrossIndex.patientDomain }"/>
				<input type="hidden" id="vi" name ="vi" value ="${vi}"/>
				<%-- <input type="hidden"  name="documentType" value="${DocListSearchParameters.documentType}"/>  --%>
				
				<div class="cell" style="width: 65px; text-align: right;">文档类型</div>
				<div class="cell" style="width: 125px;">
						<select id="documentType" name="documentType" style="width: 120px;">
							<c:if test="${vi=='V004' }">
							<option value="18" <c:if test="${documentType=='18'}">selected</c:if>>麻醉信息</option>
							</c:if>
							
							<c:if test="${vi=='V005' }">
							<option value="${Constants.OPTION_SELECT}" <c:if test="${documentType==Constants.OPTION_SELECT}">selected</c:if>>所有 </option>
							<option value="02,16" <c:if test="${documentType=='02,16'}">selected</c:if>>门急诊病历 </option>
							<option value="05" <c:if test="${documentType=='05'}">selected</c:if>>治疗处置记录 </option>
							<option value="18" <c:if test="${documentType=='18'}">selected</c:if>>手术操作记录 </option>
							<option value="06" <c:if test="${documentType=='06'}">selected</c:if>>护理记录 </option>
							<option value="08" <c:if test="${documentType=='08'}">selected</c:if>>病案首页 </option>
							<option value="09" <c:if test="${documentType=='09'}">selected</c:if>>入院记录 </option>
							<option value="10" <c:if test="${documentType=='10'}">selected</c:if>>病程记录 </option>
							<option value="12" <c:if test="${documentType=='12'}">selected</c:if>>出院记录 </option>
							<option value="01,17,99" <c:if test="${documentType=='01,17,99'}">selected</c:if>>其他 </option>
							</c:if>
						</select>
				</div>
				
					<%-- <c:if test="${DocListSearchParameters.documentType=='06' }"> --%>
						<div class="cell" style="width: 65px; text-align: right;">文档名称</div>
						<div class="cell" style="width: 120px;">
							<input type="text" name="documentName" id="documentName" style="width: 98%;" onmouseover="this.style.background='#FDE8FE';"
		                        onmouseout="this.style.background='#FFFFFF'" value="${DocListSearchParameters.documentName}" />
						</div>
					<%-- </c:if> --%>
					<div class="cell" style="width: 55px; text-align: right;">提交时间</div>
					<div class="cell"> 
						<input id="submitTimeFrom" name="submitTimeFrom" style="width: 90px;" onmouseover="this.style.background='#FDE8FE';"
		                        onmouseout="this.style.background='#FFFFFF'" class="datepicker"
		                        value="${DocListSearchParameters.submitTimeFrom}" /> 
		                <span style="margin: 0 4px 0 4px;">--</span> 
		                <input id="submitTimeTo" name="submitTimeTo" style="width: 90px;" onmouseover="this.style.background='#FDE8FE';"
		                        onmouseout="this.style.background='#FFFFFF'" class="datepicker"
		                        value="${DocListSearchParameters.submitTimeTo}" />
	                </div>
						<div class="cell" style="width: 70px; text-align: center;">
						<input type="button"
							style="color: #464646; border: 0px; width: 57px; height: 25px; margin-top: 3px; cursor: pointer; BACKGROUND-IMAGE: url(../images/5.jpg);"
							onclick="search('','conditionForm');"/>						
					   </div>
					   <!-- <div id="toggleBlock" class="container-on cell">
						<input type="button"
							style="color: #464646; border: 0px; width: 57px; height: 24px; margin-top: 3px; cursor: pointer;"
							onclick="emrClear();" value="清除" />
					   </div> -->
	                <!--
	                <div class="cell" style="width: 70px; text-align: center;">
							<input type="button" style="color:#464646;border:0px;BACKGROUND-IMAGE:url(../images/5.jpg);width:57px;height:25px; margin-top:3px; cursor:pointer;"  align="absmiddle" onclick="search('','conditionForm');"/>
							<input type="hidden" name="type" value="${DocListSearchParameters.type}" />
							<input type="hidden" name="visitSn"
							value="${DocListSearchParameters.visitSn}" /> <input type="hidden"
							name="inpatientDate" value="${DocListSearchParameters.inpatientDate}" />
					</div>
	                  -->					
				</td>
			</tr>
		</table>
	</form>
	<div style="width:45%;float:left;">
		<!-- $Author :wang_yanbo
			 $Date : 2014/08/28 16:00
			 $[BUG]0048052 MODIFY BEGIN -->
		<table  style="border: solid 1px #dbe7f1;" id="tblid" style="width: 100%;" cellspacing="1" cellpadding="2" class="blockTable">
			<!-- $[BUG]0048052 MODIFY END -->
			<tr class="blockHeader">
				<td height="28" align="center" width="50%" >文档名称</td>
				<td height="28" align="center" width="50%">提交时间</td>					
				<!-- <td height="28" align="center" width="14%">审核人</td> -->
			</tr>
			<c:if test="${fn:length(docList)==0}">
			<tr>
				<td colspan="3" 
					align="center" class="odd" height="24">没有相关数据！</td>
			</tr>
		</c:if>
		<c:forEach items="${docList}" var="doc" varStatus="status">
			<tr id="${doc.documentLid}" style="cursor:pointer" class="tabEnter"
				<c:if test="${DocListSearchParameters.visitSn != null}">onclick="showDialog('../portalDoc/detail_${doc.documentSn}.html','病历文书CDA',{'orgCode':'${doc.orgCode}'},850,500,'#ajaxDialogDetail');"</c:if> 
				onclick="portalBigShow($(this),'../portalDoc/detail_${doc.documentSn}.html',
{'orderSn':'${doc.documentLid}','otherName':'','holdOtherName':'','name':'${doc.documentName}','holdName':'${doc.documentName}'});"> 
				<td  height="24" align="center"  width="50%" <c:if test="${fn:length(doc.documentName)>10}"> title="${doc.documentName}" </c:if>>
				<c:if test="${fn:length(doc.documentName)>10}">${fn:substring(doc.documentName,0,10)}...</c:if>
                <c:if test="${fn:length(doc.documentName)<=10}">${doc.documentName}</c:if></td>
				<td  height="24" align="center" width="50%"><fmt:formatDate value="${doc.submitTime}" type="date" pattern="yyyy-MM-dd"/></td>
				<%-- <td  height="24" align="left" >${doc.reviewPersonName}</td> --%>
		        <!-- <td  height="24" align="center" ><a href="javascript:void(0);" onclick="stopBubble(event);return false;">链接</a></td> -->
			</tr> 
		</c:forEach>
		<!-- $Author :chang_xuewen
			$Date : 2013/07/04 11:00
			$[BUG]0033461 MODIFY BEGIN -->
		<tr class="page_line">
		<!-- $[BUG]0033461 MODIFY END -->
			<td colspan="2"style="height: 27px;">
				<form name="pagingform" method="get" action="../portalDoc/list_${patientSn}_.html">
					<div class="pagelinks">
						<div style="float: left; height: 100%;">
							<div class="pagedesc">共${pagingContext.totalRowCnt}条记录！ 第${pagingContext.pageNo}页/共${pagingContext.totalPageCnt}页</div>
							<c:if test="${pagingContext.pageNo > 1}">
								<div class="firstPage">
									<a href="javascript:void(0);"
										<c:if test="${DocListSearchParameters.visitSn != null}">onclick="jumpToPage(1,'#ajaxDialog');return false;"</c:if>
										onclick="jumpToPage(1);return false;"><img
										src="../images/1.gif"
										style="border: 0px; width: 21px; height: 16px;" /></a>
								</div>
								<div class="prevPage">
									<a href="javascript:void(0);"
										<c:if test="${DocListSearchParameters.visitSn != null}">onclick="jumpToPage(${pagingContext.pageNo-1},'#ajaxDialog');return false;"</c:if>
										onclick="jumpToPage(${pagingContext.pageNo-1});return false;"><img
										src="../images/2.gif"
										style="border: 0px; width: 21px; height: 16px;" /></a>
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
												<c:if test="${DocListSearchParameters.visitSn != null}">onclick="jumpToPage(${i},'#ajaxDialog');return false;"</c:if>
												onclick="jumpToPage(${i}); return false;"><font
												color="#2D56A5">${i}</font></a>
										</div>
									</c:otherwise>
								</c:choose>
							</c:forEach>
							<c:if test="${pagingContext.pageNo < pagingContext.totalPageCnt}">
								<div class="nextPage">
									<a href="javascript:void(0);"
										<c:if test="${DocListSearchParameters.visitSn != null}">onclick="jumpToPage(${pagingContext.pageNo + 1},'#ajaxDialog');return false;"</c:if>
										onclick="jumpToPage(${pagingContext.pageNo + 1});return false;"><img
										src="../images/4.gif"
										style="border: 0px; width: 21px; height: 16px;" /></a>
								</div>
								<div class="lastPage">
									<a href="javascript:void(0);"
										<c:if test="${DocListSearchParameters.visitSn != null}">onclick="jumpToPage(${pagingContext.totalPageCnt},'#ajaxDialog');return false;"</c:if>
										onclick="jumpToPage(${pagingContext.totalPageCnt});return false;"><img
										src="../images/3.gif"
										style="border: 0px; width: 21px; height: 16px;" /></a>
								</div>
							</c:if>
							<div class="pageNum">
								<input type="text" name="screen" style="display:none;"/>
								<input name="pageNum"
									<c:if test="${DocListSearchParameters.visitSn != null}">onkeyup="if(event.keyCode!=13){return false;} jumpToPageNo($('#tblid').find('[name=pageNum]').val(),${pagingContext.totalPageCnt},'#ajaxDialog');return false;"</c:if>
								    onkeyup="if(event.keyCode!=13){return false;} jumpToPageNo($('#tblid').find('[name=pageNum]').val(),${pagingContext.totalPageCnt});return false;"
								    style="width: 30px; float: left;" value="" />
							</div>
							<div class="goinput">
								<a href="javascript:void(0);"
									<c:if test="${DocListSearchParameters.visitSn != null}">onclick="jumpToPageNo($('#tblid').find('[name=pageNum]').val(),${pagingContext.totalPageCnt},'#ajaxDialog');return false;"</c:if>
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
	</div>
	<div style="width:55%;height:100%;float:right;background-color:#fff;" id="portalDetail">
		<table style="border:1px solid #dbe7f1;width:100%">
			<tr class="" style="height:24px;">
				<td>&nbsp;</td>
			</tr>
		</table>
	</div> 
</div>
</body>
</html>