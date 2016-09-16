﻿<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="translation.tld" prefix="ref"%>
<%@ taglib uri="html2.tld" prefix="html2"%>
<jsp:useBean id="Constants" class="com.founder.cdr.core.Constants" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Pragma" content="no-cache" />
	<!-- Prevents caching at the Proxy Server -->
	<meta http-equiv="Expires" content="0" />
	<title></title>
	<link rel="Stylesheet" type="text/css" href="../styles/tablelist.css" />
	<script type="text/javascript" src="../scripts/exam/examList.js"></script>
	<script>
		$(function() {
			parent.condition();
			$(".datepicker").bind("blur",parent.isDateString);
			//添加页面文本框的回车检索
			$("#conditionForm input").keyup(function(event){
				if (event.keyCode==13) 
			    { 
					search('','conditionForm');
			    } 
			});

			// $Author:wu_jianfeng
			// $Date : 2012/10/24 14:10
			// $[BUG]0010542 MODIFY BEGIN
			$( "#examinationDept").htmlSelectSuggest({width:120, 
				onKeyUp: function(event){
					if (event.keyCode==13) 
			    	{ 
						search('', 'conditionForm');	    									
					} 
				}
			});
			// $[BUG]0010542 MODIFY END
			
			// Author:jin_peng
		    // Date : 2014/1/2 15:58
		    // [BUG]0041392 ADD BEGIN
			$( "#orderDept").htmlSelectSuggest({width:140, 
				onKeyUp: function(event)
				{
					if (event.keyCode==13) 
			    	{ 
						search('', 'conditionForm');    									
					} 
				}
			});
			
			// [BUG]0041392 ADD END
			
			logical();
		});
</script>
</head>
<body style="margin: 0; padding: 0;">
	<form id="conditionForm" name="conditionForm" method="post"
		action="../exam/list_${patientSn}_${fn:replace(fn:replace(fn:replace(ExamListSearchParameters.examinationTypes,'[',''),']',''),' ','')}.html" <c:if test="${ExamListSearchParameters.visitSn != null}">style="display:none;"</c:if>>
		<table class="blockTable" cellpadding="2" cellspacing="1" border="1px"
			bordercolor="#BFD9F0">
			<tr style="height: 28px;" id="tableheader">
				<td class="blockHeader" colspan="13" height="27" align="left"
					style="border-top: solid 1px #B3C4D4;"><b><img
						src="../images/pic_jc_right.gif"
						style="padding-left: 3px; padding-right: 2px;" width="20"
						height="16" alt="" align="absmiddle" />
					    <%-- <c:choose>
							<c:when test="${isDeptNevFlag==0}">检查</c:when>
							<c:when test="${isDeptNevFlag==1&&ExamListSearchParameters.examinationDept!=null}"><ref:translate domain="${Constants.DOMAIN_DEPARTMENT}" code="${ExamListSearchParameters.examinationDept}"/></c:when>
							<c:otherwise>其他</c:otherwise>
						</c:choose> --%>${leftTopTitle}</b></td>
			</tr>
			<tr class="conditionRow">
				<td width="100%" colspan="13" height="36" align="left"
					valign="middle">
					<div class="cell ra" style="width: 55px; text-align: right">检查科室</div>
		                <!-- $Author :wu_jianfeng
	 					 $Date : 2012/10/24 17:08$
						 [BUG]0010542 MODIFY BEGIN -->
					<div class="cell" style="width: 130px;">
						<select id="examinationDept" name="examinationDept">
							<option value="${Constants.OPTION_SELECT}" selected="selected">请选择类型</option>
							<html2:pycodeoptions domain="${Constants.DOMAIN_DEPARTMENT}"
								value="${ExamListSearchParameters.examinationDept}" />
						</select>
					</div>
	                <!-- [BUG]0010542 MODIFY END -->
					<div class="cell ra" style="width: 55px; text-align: right;">检查日期</div>
					<div class="cell">
						<input name="examinationDateFrom" style="width: 90px;"
							id="examinationDateFrom" class="datepicker"
							value="${ExamListSearchParameters.examinationDateFrom}" /> <span
							style="margin: 0 4px 0 4px;">--</span> <input
							name="examinationDateTo" style="width: 90px;"
							id="examinationDateTo" class="datepicker"
							value="${ExamListSearchParameters.examinationDateTo}" />
					</div>
					<div class="cell" style="width: 70px; text-align: center;">
						<input type="button"
							style="color: #464646; border: 0px; BACKGROUND-IMAGE: url(../images/5.jpg); width: 57px; height: 25px; margin-top: 3px; cursor: pointer;"
							align="absmiddle" onclick="search('', 'conditionForm');" />
					</div>
					<div id="toggleBlock" class="container-on cell">
						<input type="button" id="buttonBlock" style="color:#464646;border:0px;BACKGROUND-IMAGE:url(../images/6.jpg);width:77px;height:25px;margin-top:3px;cursor: pointer;" onclick="showTr('tr1',this)"   align="absmiddle" />  
						<input type="hidden" id="conditionValue" name="conditionValue" value="${conditionValue}" /> 
						<input type="hidden" id="senSave" name="senSave" value="${senSave}" /> 
						<input type="hidden" name="type" value="${ExamListSearchParameters.type}" /> 
						<input type="hidden" name="visitSn" value="${ExamListSearchParameters.visitSn}" /> 
						<input type="hidden" name="inpatientDate" value="${ExamListSearchParameters.inpatientDate}" />
					</div>
				</td>
			</tr>
			<tr class="conditionRow">
				<td width="100%" colspan="13" height="36" align="left"
					valign="middle">
					<div class="cell" style="width: 65px; text-align: right;">检查项目名</div>
					<div class="cell" style="width: 115px;">
						<input type="text" name="examinationItem" style="width: 115px;"
							onmouseover="this.style.background='#FDE8FE';"
							onmouseout="this.style.background='#FFFFFF'"
							value="${ExamListSearchParameters.examinationItem}" />
					</div>
					
					<c:if test="${Constants.TRUE_ORG_CODE == Constants.DISABLE_ORG_CODE}">
						<div class="cell" style="width: 65px; text-align: right;">医疗机构</div>
						<div style="float:left;margin-top:4px;width: 140px;">
							<select id="orgCode" name="orgCode" style="width: 140px;">
								<option value="${Constants.OPTION_SELECT}">请选择</option>
								<html2:pycodeoptions domain="${Constants.DOMAIN_ORG_CODE}"
									value="${ExamListSearchParameters.orgCode}" />
							</select>
						</div>
					</c:if>
				</td>
			</tr>
			<tr id="tr1" class="conditionRow moreCondition off">
				<td width="100%" colspan="13" height="36" align="left" valign="middle">
					<div class="cell" style="width:200px;">	
						<div class="cell ra" style="width: 55px; text-align: right;">检查部位</div>
						<div class="cell" style="width: 130px;">
							<input type="text" name="examinationRegion" style="width: 120px;"
								onmouseover="this.style.background='#FDE8FE';"
								onmouseout="this.style.background='#FFFFFF'"
								value="${ExamListSearchParameters.examinationRegion}" />
						</div>
					</div>
					<div class="cell" style="width:160px;">
						<div class="cell" style="width: 55px; text-align: right;">检查医生</div>
						<div class="cell" style="width: 90px;">
							<input type="text" name="examiningDoctor" style="width: 80px;"
								onmouseover="this.style.background='#FDE8FE';"
								onmouseout="this.style.background='#FFFFFF'"
								value="${ExamListSearchParameters.examiningDoctor}" />
						</div>
					</div>
					<div class="cell" style="width:270px;">
						<div class="cell ra" style="width: 55px; text-align: right;">报告日期</div>
						<div class="cell">
							<input name="reportDateFrom" style="width: 80px;"
								id="reportDateFrom" class="datepicker"
								value="${ExamListSearchParameters.reportDateFrom }" /> <span
								style="margin: 0 4px 0 4px;">--</span> <input name="reportDateTo"
								style="width: 80px;" id="reportDateTo" class="datepicker"
								value="${ExamListSearchParameters.reportDateTo}" />
						</div>
					</div>
					<div class="cell" style="width: 180px;">
						<div class="cell" style="width: 55px; text-align: right">开医嘱人</div>
						<div class="cell" style="width: 110px;">
							<input type="text" id="orderPersonName" name="orderPersonName"
								style="width:100px;"
								onmouseover="this.style.background='#FDE8FE';"
								onmouseout="this.style.background='#FFFFFF'"
								value="${ExamListSearchParameters.orderPersonName}" />
						</div>
					</div>
					<div class="cell" style="width: 230px;">
						<div class="cell" style="width: 65px; text-align: right">医嘱开立科室</div>
						<div class="cell" style="width: 150px;">
							<select id="orderDept" name="orderDept" style="width: 140px;">
								<option value="${Constants.OPTION_SELECT}">请选择</option>
								<html2:pycodeoptions domain="${Constants.DOMAIN_DEPARTMENT}"
									value="${ExamListSearchParameters.orderDept}" />
							</select>
						</div>
					</div>
				</td>
			</tr>
			<!-- $Author :chang_xuewen
			$Date : 2013/07/04 11:00
			$[BUG]0033461 MODIFY BEGIN -->
			<tr class="tabletitle"  style="width: 100%;">
			<td height="28" width="13%" align="center">检查项目名</td>
			<td height="28" width="9%" align="center">检查部位</td>
			<td height="28" width="9%" align="center">医嘱状态</td>
			<td height="28" width="9%" align="center">申请日期</td>
			<td height="28" width="9%" align="center">检查方法</td>
			<td height="28" width="8%" align="center">检查医生</td>
			<td height="28" width="9%" align="center">检查日期</td>
			<!-- <td height="28" align="center">检查类型名称</td> -->
			<!-- <td height="28" align="center">报告医生</td>
			<td height="28" align="center">报告日期</td> -->
			<!-- <td height="28" align="center">审核医生</td>
			<td height="28" align="center">审核日期</td> -->
			<td height="28" width="9%" align="center">检查科室</td>
			<td height="28" width="17%" align="center">检查结果</td>
			<c:if test="${Constants.TRUE_ORG_CODE == Constants.DISABLE_ORG_CODE}">
				<td height="28" width="8%" align="center">医疗机构</td>
			</c:if>
			<td height="28" width="8%" align="center">召回状态</td>
			<c:if test="${Constants.COMM_INTERFACE == Constants.IMAGE_CENTER}">
				<td height="28" width="3%" align="center">影像</td>
			</c:if>
			<c:if test="${Constants.COMM_INTERFACE == Constants.ELECTRONIC_SIGNATURE}">
				<td height="28" width="3%" align="center">签章报告</td>
			</c:if>	
		</tr>
		<!-- $[BUG]0033461 MODIFY END -->
		</table>
	</form>
	<div id='listcon'>
	<!--[if lt ie 8]><div style='+zoom:1;'><![endif]-->
	<table id="tblid" style="width: 100%;" cellspacing="1" cellpadding="2"
		border="0" class="table">
		<c:if test="${fn:length(examList)==0}">
			<tr>
				<td colspan="12" align="center" class="odd" height="24">没有相关数据！</td>
			</tr>
		</c:if>
		<c:forEach items="${examList}" var="examList" varStatus="status">
		<!-- $Author :chang_xuewen
			$Date : 2013/07/04 11:00
			$[BUG]0033461 MODIFY BEGIN -->
			<tr
				<c:choose>
			    	<c:when test="${examList.withdrawFlag==Constants.REPORT_WITHDRAW_FLAG}">
			    		class="forbidden tabEnter" onmouseout="this.className='forbidden'" onmouseover="this.className='forbidden'"
			    	</c:when>
					<c:when test="${empty examList.examReportSn}">
						class="orderOnly tabEnter" onmouseout="this.className='orderOnly'" 	
					</c:when>
					<c:when test="${empty examList.orderSn}">
						class="reportOnly tabEnter" onmouseout="this.className='reportOnly'" 
					</c:when>
			    	<c:otherwise>
			    		 <c:if test="${status.count%2==1 }">class="odd tabEnter" onmouseout="this.className='odd'"</c:if>
			    		 <c:if test="${status.count%2==0 }">class="even tabEnter" onmouseout="this.className='even'"</c:if>
			    	</c:otherwise>
		        </c:choose>
				id="${examList.orderSn}_${examList.examReportSn}_${examList.itemSn}" style="cursor: pointer" onmouseover="this.className='mouseover'"
				<c:if test="${ExamListSearchParameters.visitSn != null}">onclick="showExamDetailDialog2('${examList.withdrawFlag}','${examList.examReportSn}','${examList.patientSn}','${examList.itemClass}','${examList.examinationRegion}','${examList.examinationItem}','${examList.itemSn}','${examList.orderSn}');"</c:if>
				onclick="showExamDetailDialog('${examList.withdrawFlag}','${examList.examReportSn}','${examList.patientSn}','${examList.itemClass}','${examList.examinationRegion}','${examList.examinationItem}','${examList.itemSn}','${examList.orderSn}',$(this),2,'${examList.examinationItemName}','${examList.examinationMethodName}');">
				<!-- $[BUG]0033461 MODIFY END -->
				<td height="24" width="13%" align="center" <c:if test="${fn:length(examList.examinationItemName)>10}"> title="${examList.examinationItemName}" </c:if>>
				<c:if test="${fn:length(examList.examinationItemName)>10}">${fn:substring(examList.examinationItemName,0,10)}...</c:if>
                <c:if test="${fn:length(examList.examinationItemName)<=10}">${examList.examinationItemName}</c:if></td>
				<td height="24" width="9%" align="center">${examList.examinationRegionName}</td>
				<td height="24" width="9%" align="center">${examList.orderStatusName}</td>
				<td height="24" width="9%" align="center"><fmt:formatDate value="${examList.requestDate}" type="date" pattern="yyyy-MM-dd"/></td>
				<td height="24" width="9%" align="center" <c:if test="${fn:length(examList.examinationMethodName)>10}"> title="${examList.examinationMethodName}" </c:if>>
				<c:if test="${fn:length(examList.examinationMethodName)>10}">${fn:substring(examList.examinationMethodName,0,10)}...</c:if>
                <c:if test="${fn:length(examList.examinationMethodName)<=10}">${examList.examinationMethodName}</c:if></td>
				<td height="24" width="8%" align="center">${examList.examiningDoctorName}</td>
				<td height="24" width="9%" align="center"><fmt:formatDate
											value="${examList.examinationDate}" type="date"
											pattern="yyyy-MM-dd" /></td>
				<%-- <td height="24" align="center">${examList.itemClassName}</td> --%>							
				<%-- <td height="24" align="center">${examList.reportDoctorName}</td>
				<td height="24" align="center"><fmt:formatDate
											value="${examList.reportDate}" type="date"
											pattern="yyyy-MM-dd" /></td> --%>
				<%-- <td height="24" align="center">${examList.reviewDoctorName}</td>
				<td height="24" align="center">${fn:substring(examList.reviewDate,0,10)}</td> --%>
				<td height="24" width="9%" align="center">${examList.examDeptName}</td>
				<td height="24" width="17%" align="left">
					<c:choose>
				    	<c:when test="${examList.withdrawFlag==Constants.REPORT_WITHDRAW_FLAG}">
				    		${Constants.REPORT_WITHDRAW_CONTENT}
				    	</c:when>
				    	<c:otherwise>
				    		 ${examList.eiImagingConclusion==null?examList.erImagingConclusion:examList.eiImagingConclusion}
				    	</c:otherwise>
		        	</c:choose>
				</td>
				<c:if test="${Constants.TRUE_ORG_CODE == Constants.DISABLE_ORG_CODE}">
					<td height="24" width="8%" align="center"><ref:translate
	                        domain="${Constants.DOMAIN_ORG_CODE}"
	                        code="${examList.orgCode}" /></td>
	            </c:if>
				<td height="24" width="8%" align="center"><c:if
						test="${examList.withdrawFlag==Constants.REPORT_WITHDRAW_FLAG}">
						<a href="javascript:void(0);"
							onclick="showDialog('../exam/withdraw_${examList.examReportSn}.html','召回信息', {});stopBubble(event);return false;"><img src="../images/jczh.png"  align="middle" style="padding-right: 1px;border:0px" /></a>
					</c:if> <c:if test="${examList.withdrawFlag==Constants.REPORT_MODIFY_FLAG}">
						<a href="javascript:void(0);"
							onclick="showDialog('../exam/withdraw_${examList.examReportSn}.html','召回信息', {});stopBubble(event);return false;"><img src="../images/yxg.png"  align="middle" style="padding-right: 1px;border:0px" /></a>
					</c:if></td>
				<c:if test="${Constants.COMM_INTERFACE == Constants.IMAGE_CENTER}">	
					<td  height="24" width="3%" align="center" <c:if test="${empty examList.imageIndexNo}"> onclick="stopBubble(event);return false;"</c:if>>
			        	<c:if test="${!empty examList.imageIndexNo}">
			        		<a href="javascript:void(0);" onclick="showImageCenter('${imageCenterUrl}','${examList.imageIndexNo}');return false;"  onfocus="this.blur()"><img src="../images/pic_jc.gif" align="middle" style="padding-right: 1px;border:0px"/></a>
			        	</c:if>
			        </td>	
			    </c:if>
			    <c:if test="${Constants.COMM_INTERFACE == Constants.ELECTRONIC_SIGNATURE}">    
					<td  height="24" width="3%" align="center" <c:if test="${empty examList.signatureId}"> onclick="stopBubble(event);return false;"</c:if>>
			        	<c:if test="${!empty examList.signatureId}">
			        		<a href="javascript:void(0);"onclick="previewDoc('${signatureUrl}','${examList.signatureId}');return false;" onfocus="this.blur()"><img src="../images/qzbg.png" align="middle" style="padding-right: 1px;border:0px"/></a>
			        	</c:if>
			        </td>
			    </c:if>    
			</tr>
		</c:forEach>
		<tr>
			<td colspan="13" style="height: 27px;">
				<form name="pagingform" method="get"
					action="../exam/list_${patientSn}_.html">
					<div class="pagelinks">
						<div style="float:left;margin-top:8px;">
							<div style="width:10px;height:10px;background-color:#D3F8F8;float:left;margin-right:3px;"/><div style="float:left;">仅有医嘱信息</div>
							<div style="width:10px;height:10px;background-color:#E9D3F8;float:left;margin-right:3px;"/><div style="float:left;">仅有报告信息</div>
							<div style="width:10px;height:10px;background-color:#FCE6C9;float:left;margin-right:3px;"/><div style="float:left;">已召回报告</div>
						</div>
					<!--[if lt ie 8]></div><div class="pagelinks"><![endif]-->
						<div style="float: right; height: 100%;">
							<div class="pagedesc">共${pagingContext.totalRowCnt}条记录！
								第${pagingContext.pageNo}页/共${pagingContext.totalPageCnt}页</div>
							<c:if test="${pagingContext.pageNo > 1}">
								<div class="firstPage">
									<a href="javascript:void(0);"
										<c:if test="${ExamListSearchParameters.visitSn != null}">onclick="jumpToPage(1,'#ajaxDialog');return false;"</c:if>
										onclick="jumpToPage(1);return false;"><img
										src="../images/1.gif"
										style="border: 0px; width: 21px; height: 16px;" /></a>
								</div>
								<div class="prevPage">
									<a href="javascript:void(0);"
										<c:if test="${ExamListSearchParameters.visitSn != null}">onclick="jumpToPage(${pagingContext.pageNo-1},'#ajaxDialog');return false;"</c:if>
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
												<c:if test="${ExamListSearchParameters.visitSn != null}">onclick="jumpToPage(${i},'#ajaxDialog');return false;"</c:if>
												onclick="jumpToPage(${i}); return false;"><font
												color="#2D56A5">${i}</font></a>
										</div>
									</c:otherwise>
								</c:choose>
							</c:forEach>
							<c:if test="${pagingContext.pageNo < pagingContext.totalPageCnt}">
								<div class="nextPage">
									<a href="javascript:void(0);"
										<c:if test="${ExamListSearchParameters.visitSn != null}">onclick="jumpToPage(${pagingContext.pageNo + 1},'#ajaxDialog');return false;"</c:if>
										onclick="jumpToPage(${pagingContext.pageNo + 1});return false;"><img
										src="../images/4.gif"
										style="border: 0px; width: 41px; height: 16px;" /></a>
								</div>
								<div class="lastPage">
									<a href="javascript:void(0);"
										<c:if test="${ExamListSearchParameters.visitSn != null}">onclick="jumpToPage(${pagingContext.totalPageCnt},'#ajaxDialog');return false;"</c:if>
										onclick="jumpToPage(${pagingContext.totalPageCnt});return false;"><img
										src="../images/3.gif"
										style="border: 0px; width: 21px; height: 16px;" /></a>
								</div>
							</c:if>
							<div class="pageNum">
								<input type="text" name="screen" style="display:none;"/>
								<input name="pageNum"
									<c:if test="${ExamListSearchParameters.visitSn != null}">onkeyup="if(event.keyCode!=13){return false;} jumpToPageNo($('#tblid').find('[name=pageNum]').val(),${pagingContext.totalPageCnt},'#ajaxDialog');return false;"</c:if>
								    onkeyup="if(event.keyCode!=13){return false;} jumpToPageNo($('#tblid').find('[name=pageNum]').val(),${pagingContext.totalPageCnt});return false;"
								    style="width: 30px; float: left;" value="" />
							</div>
							<div class="goinput">
								<a href="javascript:void(0);"
									<c:if test="${ExamListSearchParameters.visitSn != null}">onclick="jumpToPageNo($('#tblid').find('[name=pageNum]').val(),${pagingContext.totalPageCnt},'#ajaxDialog');return false;"</c:if>
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
