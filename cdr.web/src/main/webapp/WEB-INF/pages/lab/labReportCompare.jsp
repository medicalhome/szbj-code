<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:useBean id="Constants" class="com.founder.cdr.core.Constants" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>历次对比结果及图形</title>
	<meta http-equiv="Pragma" content="no-cache" />
	<meta http-equiv="Cache-Control" content="no-cache" />
	<meta http-equiv="Expires" content="0" />
	<link type="text/css" rel="Stylesheet"
		href="../styles/jquery-ui-1.8.18.custom.modify.css" charset="UTF8" />
</head>
<body style="margin: 0; padding: 0;">
	<table cellspacing="0" cellpadding="1" border="0" width="100%">
         <tr>
             <td class="blockHeader" colspan="7" height="27" align="left" style="font-weight: bold;border-top: solid 1px #B3C4D4;">
                 <img src="../images/pic_jiany.png" style="padding-left: 3px; padding-right: 1px;"
                     width="19" height="20" alt="" align="absmiddle" />历次对比结果及图形
             </td>
         </tr>
     </table>
     <table id="cptblid" cellspacing="0" cellpadding="1" border="0" width="100%" height="100%" class="table" style="table-layout:fixed;word-break:break-all;">
        <tr height="28" class="tabletitle">
        	<c:forEach items="${relatedResultItemList}" var="resultItem" varStatus="status">
	            <td align="center" style="font-size:9pt;"><fmt:formatDate value="${resultItem.testDate}" type="date" pattern="yyyy-MM-dd" /></td>
	        </c:forEach>
	        <c:forEach var="item" varStatus="status" begin="1" end="${rowsPerPageLabCount - fn:length(relatedResultItemList)}">
	        	<td>&nbsp;</td>
	        </c:forEach>
        </tr>
        <tr height="24" class="even" >
            <c:forEach items="${relatedResultItemList}" var="resultItem" varStatus="status">
            	<c:choose>
            		<c:when test="${resultItem.qualitativeResults!=null}">
            			<td align="center">${resultItem.qualitativeResults}</td>
            		</c:when>
            		<c:otherwise>
            			<td align="center" style="font-size:9pt;">
            				<c:choose>
            					<c:when test="${resultItem.resultHighLowFlagStr == 'high'}">
            						<font color="red">${resultItem.itemValue}↑</font>
            					</c:when>
            					<c:when test="${resultItem.resultHighLowFlagStr == 'low'}">
            						<font color="red">${resultItem.itemValue}↓</font>
            					</c:when>
            					<c:when test="${resultItem.resultHighLowFlagStr == 'normal'}">
            						${resultItem.itemValue}
            					</c:when>
            					<c:otherwise>
            						<font color="red">${resultItem.itemValue}(error)</font>
            					</c:otherwise>
            				</c:choose>
            			</td>
            		</c:otherwise>
            	</c:choose>
            </c:forEach>
            <c:forEach var="item" varStatus="status" begin="1" end="${rowsPerPageLabCount - fn:length(relatedResultItemList)}">
	        	<td>&nbsp;</td>
	        </c:forEach>
        </tr>
        <tr>
            <td colspan="${rowsPerPageLabCount}" style="height: 27px;">
                <form name="comparepagingform" method="get" action="../lab/compare.html">
                <div style="display:none">
                	<input type="text" name="patientSn" value="${patientSn}" />
					<input type="text" name="itemCode" value="${itemCode}" />
					<input type="text" name="itemName" value="${itemName}" />
                </div>
                <div class="pagelinks">
                    <div style="float: right; height: 100%;">
                        <div class="pagedesc">共${labResultPagingContext.totalRowCnt}条记录！      第${labResultPagingContext.totalPageCnt - labResultPagingContext.pageNo + 1}页/共${labResultPagingContext.totalPageCnt}页</div>
                        <c:if test="${labResultPagingContext.pageNo < labResultPagingContext.totalPageCnt}">
						<div class="firstPage">
							<a href="javascript:void(0);" onclick="compareJumpToPage(${labResultPagingContext.totalPageCnt});return false;"><img
								src="../images/1.gif"
								style="border: 0px; width: 21px; height: 16px;" />
							</a>
						</div>
						<div class="prevPage">
							<a href="javascript:void(0);" onclick="compareJumpToPage(${labResultPagingContext.pageNo+1});return false;"><img
								src="../images/2.gif"
								style="border: 0px; width: 41px; height: 16px;" />
							</a>
						</div>
						</c:if>
						<c:forEach var="i" begin="${labResultPagingContext.pageStartNo}" end="${labResultPagingContext.perPageCnt}" step="1">
							<c:choose>
								<c:when test="${i == (labResultPagingContext.totalPageCnt - labResultPagingContext.pageNo + 1)}">
									<div id="currentPage" class="currentPage">
					                	<font color="#2D56A5">${i}</font>
									</div>
								</c:when>
								<c:otherwise>
									<div id="currentPage" class="pageno">
										<a href="javascript:void(0);" onclick="compareJumpToPage(${labResultPagingContext.totalPageCnt - i + 1});return false;"><font color="#2D56A5">${i}</font></a>
										</div>
								</c:otherwise>
							</c:choose>
						</c:forEach>
							
						<c:if test="${labResultPagingContext.pageNo>1}">
						<div class="nextPage">
							<a href="javascript:void(0);" onclick="compareJumpToPage(${labResultPagingContext.pageNo-1});return false;"><img
								src="../images/4.gif"
								style="border: 0px; width: 41px; height: 16px;" />
							</a>
						</div>
						<div class="lastPage">
							<a href="javascript:void(0);" onclick="compareJumpToPage(1);return false;"><img
								src="../images/3.gif"
								style="border: 0px; width: 21px; height: 16px;" />
							</a>
						</div>
						</c:if>
						<div class="pageNum" onclick="stopBubble(event);">
							<input type="text" name="screen" style="display:none;"/>
								<input name="pageNum"
								    onkeyup="if(event.keyCode!=13){return false;} compareJumpToPageNo($('#cptblid').find('[name=pageNum]').val(),${labResultPagingContext.totalPageCnt},'1');return false;"
								    style="width: 30px; float: left;" value="" />
						</div>
						<div class="goinput">
							<a href="javascript:void(0);"
								onclick="compareJumpToPageNo($('#cptblid').find('[name=pageNum]').val(),${labResultPagingContext.totalPageCnt},'1');return false;"><img
								src="../images/go.gif"
								style="border: 0px; width: 21px; height: 15px;" /> </a>
						</div>
                    </div>
                </div>
                </form>
            </td>
        </tr>
        <tr height="1"></tr>
        <c:if test="${showLegendFlag == true }">
	        <tr>
	            <td colspan="${rowsPerPageLabCount}">
	                <img name='big' width="100%" height="280" src='${legendUrl}' />
	            </td>
	        </tr>
        </c:if>
     </table>
</body>
</html>