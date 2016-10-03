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
    <title>门诊病历</title>
	<link rel="Stylesheet" type="text/css" href="../styles/tablelist.css" />
	<script type="text/javascript" src="../scripts/doc/docList.js"></script>
	<!-- $Author :chang_xuewen
			 $Date : 2013/07/04 11:00
			 $[BUG]0033461 ADD BEGIN -->
	<script type="text/javascript">
		$(function(){
			//调用动态表格美化
			beautyTable();
		});
	</script>
	<!--   $[BUG]0033461 ADD BEGIN -->
</head>
<body style="margin: 0; padding: 0;">
	<form id="conditionForm" name="conditionForm" method="post" action="../doc/list_${patientSn}.html" <c:if test="${DocListSearchParameters.visitSn != null}">style="display:none;"</c:if>>
		<table class="blockTable" cellpadding="2" cellspacing="1" border="0">
			<tr style="height: 28px;">
				<td class="blockHeader"
					<c:choose>
						<c:when test="${Constants.TRUE_ORG_CODE == Constants.DISABLE_ORG_CODE}">
							colspan="7"	
						</c:when>
						<c:otherwise>
							colspan="6" 
						</c:otherwise>
					</c:choose> 
					height="27" align="left" style="border-top:solid 1px #B3C4D4;">
					<b><img src="../images/pic_mzbl.png" style="padding-left:3px; padding-right: 2px;" width="19" height="19" alt="" align="absmiddle" />
					${leftTopTitle}
					</b>
				</td>
			</tr>
			<tr class="conditionRow">
				<td width="100%" 
					<c:choose>
						<c:when test="${Constants.TRUE_ORG_CODE == Constants.DISABLE_ORG_CODE}">
							colspan="7" 
						</c:when>
						<c:otherwise>
							colspan="6" 
						</c:otherwise>
					</c:choose>
					height="36" align="left" valign="middle">
					
					<div class="cell" style="width: 60px; text-align: right;">文档类别</div>
					<div class="cell" style="width: 100px;">
						<select name="documentType" style="width:100%;">
					       <option value="${Constants.OPTION_SELECT}">请选择</option>
				           <html:options domain="${Constants.DOMAIN_CLINICAL_DOCUMENT_TYPE}" value="${DocListSearchParameters.documentType}"/>
		              </select>
					</div>
					<div class="cell" style="width: 60px; text-align: right;">文档名称</div>
					<div class="cell" style="width: 80px;">
						<input type="text" name="documentName" style="width: 98%;" onmouseover="this.style.background='#FDE8FE';"
	                        onmouseout="this.style.background='#FFFFFF'" value="${DocListSearchParameters.documentName}" />
					</div>
					<div class="cell" style="width: 60px; text-align: right;">文档作者</div>
					<div class="cell" style="width: 80px;">
			         	<input type="text" name="documentAuthor" style="width: 98%;" onmouseover="this.style.background='#FDE8FE';"
	                        onmouseout="this.style.background='#FFFFFF'" value="${DocListSearchParameters.documentAuthor}" />
					</div>
					<div class="cell" style="width: 60px; text-align: right;">提交时间</div>
					<div class="cell"> 
						<input id="submitTimeFrom" name="submitTimeFrom" style="width: 70px;" onmouseover="this.style.background='#FDE8FE';"
		                        onmouseout="this.style.background='#FFFFFF'" class="datepicker"
		                        value="${DocListSearchParameters.submitTimeFrom}" /> 
		                <span style="margin: 0 4px 0 4px;">--</span> 
		                <input id="submitTimeTo" name="submitTimeTo" style="width: 70px;" onmouseover="this.style.background='#FDE8FE';"
		                        onmouseout="this.style.background='#FFFFFF'" class="datepicker"
		                        value="${DocListSearchParameters.submitTimeTo}" />
	                </div>
	                <c:if test="${Constants.TRUE_ORG_CODE == Constants.DISABLE_ORG_CODE}">
		                <div class="cell" style="width: 60px; text-align: right;">医疗机构</div>
						<div class="cell" style="width: 130px;"> 
							<select id="orgCode" name="orgCode" style="width:100%;">
								<option value="${Constants.OPTION_SELECT}">请选择</option>
								<html:options domain="${Constants.DOMAIN_ORG_CODE}"
								value='${DocListSearchParameters.orgCode}' title="true" />						
							</select>
		                </div>
	                </c:if>
					<div class="cell" style="width: 70px; text-align: center;">
							<input type="button" style="color:#464646;border:0px;BACKGROUND-IMAGE:url(../images/5.jpg);width:57px;height:25px; margin-top:3px; cursor:pointer;"  align="absmiddle" onclick="search('','conditionForm');"/>
							<input type="hidden" name="type" value="${DocListSearchParameters.type}" />
							<input type="hidden" name="visitSn"
							value="${DocListSearchParameters.visitSn}" /> <input type="hidden"
							name="inpatientDate" value="${DocListSearchParameters.inpatientDate}" />
							<input type="hidden" name="documentTypes" value="${fn:replace(fn:replace(fn:replace(DocListSearchParameters.inDocumentTypes,'[',''),']',''),' ','')}" />
					</div>
				</td>
			</tr>
			<!-- $Author :chang_xuewen
			$Date : 2013/07/04 11:00
			$[BUG]0033461 MODIFY BEGIN -->
			<tr class="tabletitle">
				<td height="28" align="center" width="19%" >文档类别</td>
				<td height="28" align="center" width="25%" >文档名称</td>
				<td height="28" align="center" width="12%">文档作者</td>
				<td height="28" align="center" width="12%">文档修改者</td>
				<td height="28" align="center" width="12%">提交时间</td>	
				<!-- <td height="28" align="center" width="14%">审核人</td> -->
				<c:if test="${Constants.TRUE_ORG_CODE == Constants.DISABLE_ORG_CODE}">
					<td height="28" align="center">医疗机构</td>
				</c:if>
				<c:if test="${Constants.COMM_INTERFACE == Constants.ELECTRONIC_SIGNATURE}">
					<td height="28" align="center" 
						<c:choose>
							<c:when test="${Constants.TRUE_ORG_CODE == Constants.DISABLE_ORG_CODE}">
								width="8%"  
							</c:when>
							<c:otherwise>
								width="15%" 
							</c:otherwise>
						</c:choose>>签章报告</td>
				</c:if>	
			</tr>
			<!-- $[BUG]0033461 MODIFY END -->
		</table>
	</form>
	<div id='listcon'>
	<!--[if lt ie 8]><div style='+zoom:1;'><![endif]-->
	<table id="tblid" style="width: 100%;" cellspacing="1" cellpadding="2" class="table">
		
			<c:if test="${fn:length(docList)==0}">
			<tr>
				<td 
					<c:choose>
						<c:when test="${Constants.TRUE_ORG_CODE == Constants.DISABLE_ORG_CODE}">
							colspan="7" 
						</c:when>
						<c:otherwise>
							colspan="6" 
						</c:otherwise>
					</c:choose>
				align="center" class="odd" height="24">没有相关数据！</td>
			</tr>
		</c:if>
		<c:forEach items="${docList}" var="doc" varStatus="status">
			<tr id="${doc.serviceId}${doc.documentLid}" style="cursor:pointer" class="tabEnter"
				<c:if test="${DocListSearchParameters.visitSn != null}">onclick="showDialog('../doc/detail_${doc.documentSn}.html','病历文书CDA',{'orgCode':'${doc.orgCode}'},850,500,'#ajaxDialogDetail');"</c:if> 
				onclick="bigShow($(this),'../doc/detail_${doc.documentSn}.html',
{'orderSn':'${doc.serviceId}${doc.documentLid}','otherName':'','holdOtherName':'','name':'${doc.documentName}','holdName':'${doc.documentName}'});"> 
				<td  height="24" align="left"  width="19%">${doc.documentTypeName}</td>
				<td  height="24" align="left"  width="25%" <c:if test="${fn:length(doc.documentName)>25}"> title="${doc.documentName}" </c:if>>
				<c:if test="${fn:length(doc.documentName)>25}">${fn:substring(doc.documentName,0,25)}...</c:if>
                <c:if test="${fn:length(doc.documentName)<=25}">${doc.documentName}</c:if></td>
				<td  height="24" align="left" width="12%" >${doc.documentAuthorName}</td>
				<td  height="24" align="left" width="12%">${doc.documentModifierName}</td>
				<td  height="24" align="center" width="12%"><fmt:formatDate value="${doc.submitTime}" type="date" pattern="yyyy-MM-dd hh:mm"/></td>
				<c:if test="${Constants.TRUE_ORG_CODE == Constants.DISABLE_ORG_CODE}">
					<td height="24" align="left"><ref:translate domain="${Constants.DOMAIN_ORG_CODE}" code="${doc.orgCode}"/></td>
				</c:if>
				<%-- <td  height="24" align="left" >${doc.reviewPersonName}</td> --%>
		        <!-- <td  height="24" align="center" ><a href="javascript:void(0);" onclick="stopBubble(event);return false;">链接</a></td> -->
		       <c:if test="${Constants.COMM_INTERFACE == Constants.ELECTRONIC_SIGNATURE}">
			        <td  height="24" align="center" 
			        	<c:choose>
							<c:when test="${Constants.TRUE_ORG_CODE == Constants.DISABLE_ORG_CODE}">
								width="8%" 
							</c:when>
							<c:otherwise>
								width="15%" 
							</c:otherwise>
						</c:choose>
			        <c:if test="${empty doc.signatureId}"> onclick="stopBubble(event);return false;"</c:if>>
			        	<c:if test="${!empty doc.signatureId}">
			        		<a href="javascript:void(0);"onclick="previewDoc('${signatureUrl}','${doc.signatureId}');return false;"><img src="../images/qzbg.png" align="middle" style="padding-right: 1px;border:0px"/></a>
			        	</c:if>
			        </td>
			    </c:if>    
			</tr> 
		</c:forEach>
		<!-- $Author :chang_xuewen
			$Date : 2013/07/04 11:00
			$[BUG]0033461 MODIFY BEGIN -->
		<tr class="page_line">
		<!-- $[BUG]0033461 MODIFY END -->
			<td 
				<c:choose>
					<c:when test="${Constants.TRUE_ORG_CODE == Constants.DISABLE_ORG_CODE}">
						colspan="7" 
					</c:when>
					<c:otherwise>
						colspan="6" 
					</c:otherwise>
				</c:choose>
			style="height: 27px;">
				<form name="pagingform" method="get" action="../doc/list_${patientSn}_.html">
					<div class="pagelinks">
						<div style="float: right; height: 100%;">
							<div class="pagedesc">${pagingContext.perPageCnt}共${pagingContext.totalRowCnt}条记录！ 第${pagingContext.pageNo}页/共${pagingContext.totalPageCnt}页</div>
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
										style="border: 0px; width: 41px; height: 16px;" /></a>
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
	 <!--[if lt ie 8]></div><![endif]-->
	</div>
</body>
</html>