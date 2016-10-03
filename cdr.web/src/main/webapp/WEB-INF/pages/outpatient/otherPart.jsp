<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="translation.tld" prefix="ref"%>
<%@ taglib uri="html.tld" prefix="html"%>
<jsp:useBean id="Constants" class="com.yly.cdr.core.Constants" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<title></title>
<link type="text/css" rel="Stylesheet" href="../styles/tablelist.css"
	charset="UTF8" />
	<script type="text/javascript"
	src="../scripts/order/nonDrugOrderList.js"></script>
</head>

<body style="margin: 0; padding: 0;">
	<c:if
			test="${fn:length(orderList)!=0 and viewSettings.showOther==true}">
			<table width="100%" class="ctab">
				<c:choose>
					<c:when
						test="${accessContorl == false|| (accessContorl && loginUserAclAuths.clinicalInfoAuth03)}">
						<tr>
							<td style="vertical-align: top;">
								<table width="100%" class="table">
									<tr style="height: 28px;">
										<td class="blockHeader" width="100px" colspan="10"
											style="border-right: 0px"><b> <img
												src="../images/pic_fywyz.png"
												style="padding-left: 3px; padding-right: 2px;" width="19"
												height="19" alt="" align="absmiddle" />其他医嘱
										</b></td>
										<td class="blockHeader" align="right"
											style="padding-right: 5px;"><input type="hidden"
											value="1" id="show_other" /> <a href="javascript:void(0);"
											id="show_other_text" onclick="showBlock('other');"
											onfocus="this.blur()" style="text-decoration: none">▼</a></td>
									</tr>
								</table>
								<table style="width: 100%" id="otherInfo" class="table">
									<tr>
										<td>
											<table style="width: 100%;" id="othert">
												<tr class="tabletitle">
													<td height="28" align="center" width="10%">医嘱类型</td>
													<td height="28" align="center" width="15%">项目名称</td>
													<td height="28" align="center" width="9%">长期或临时</td>
													<td height="28" align="center" width="17%">开嘱科室</td>
													<td height="28" align="center" width="17%">执行科室</td>
													<td height="28" align="center" width="8%">开嘱人</td>
												<!-- $Author :chang_xuewen
     			 			 						 $Date : 2013/10/31 11:00
     			 									 $[BUG]0038735 MODIFY BEGIN -->
													<td height="28" align="center" width="8%">医嘱录入时间</td>
												<!-- $[BUG]0038735 MODIFY END -->
													<!-- <td height="28" align="center" width="6%">确认人</td>
											<td height="28" align="center" width="8%">确认时间</td> -->
													<td height="28" align="center" width="8%">取消人</td>
													<td height="28" align="center" width="8%">取消时间</td>
												</tr>
												<c:forEach items="${orderList}" var="ord" varStatus="status">
													<tr
														<c:if test="${status.count%2==1}">class="odd tabEnter" onmouseout="this.className='odd'"</c:if>
														<c:if test="${status.count%2==0}">class="even tabEnter" onmouseout="this.className='even'"</c:if>
														onmouseover="this.className='mouseover'"
														style="cursor: pointer"
														onclick="justShow('${ord.orderPath}',$(this),
														{'name':'${fn:trim(fn:replace(ord.orderName,'\"','&quot;'))}','holdName':'${fn:trim(fn:replace(ord.orderName,'\"','&quot;'))}','otherName':'','holdOtherName':''});">
														<td height="24" align="left">${ord.orderTypeName}&nbsp;</td>
														<td height="24" align="left"
															<c:if test="${fn:length(ord.orderName)>10}"> title="${ord.orderName}" </c:if>>&nbsp;
															<c:if test="${fn:length(ord.orderName)>10}">${fn:substring(ord.orderName,0,10)}...</c:if>
															<c:if test="${fn:length(ord.orderName)<=10}">${ord.orderName}</c:if>
														</td>
														<td height="24" align="center"><ref:translate
																domain="${Constants.DOMAIN_TEMPORARY_FLAG}"
																code="${ord.temporaryFlag}" />&nbsp;</td>
														<td height="24" align="left">${ord.orderDeptName}&nbsp;</td>
														<td height="24" align="left">${ord.execDeptName}&nbsp;</td>
														<td height="24" align="left" style="padding-left: 3px;">${ord.orderPersonName}&nbsp;</td>
														<td height="24" align="center"><fmt:formatDate
																value="${ord.orderTime}" type="date"
																pattern="yyyy-MM-dd" />&nbsp;</td>
														<%-- <td height="24" align="left" style="padding-left: 6px;">${ord.confirmPersonName}&nbsp;</td>
												<td height="24" align="center"><fmt:formatDate
														value="${ord.confirmTime}" type="date" pattern="yyyy-MM-dd" />&nbsp;</td> --%>
														<td height="24" align="left" style="padding-left: 6px;">${ord.cancelPersonName}&nbsp;</td>
														<td height="24" align="center"><fmt:formatDate
																value="${ord.cancelTime}" type="date"
																pattern="yyyy-MM-dd" />&nbsp;</td>
													</tr>
												</c:forEach>
												<!-- $Author :chang_xuewen
													 $Date : 2013/07/01 16:40
													 $[BUG]0032417 MODIFY BEGIN  -->
												<c:if test="${pagingContext_other.totalRowCnt > Constants.ROWCNT_PER_PAGE_YW}">
												<tr>
													<td colspan="10" style="height: 27px;">
														<form name="pageform_other" method="get"
															action="../visit/otherPart.html">
															<div class="pagelinks">
																<div style="float: right; height: 100%;">
																	<div class="pagedesc">共${pagingContext_other.totalRowCnt}条记录！
																		第${pagingContext_other.pageNo}页/共${pagingContext_other.totalPageCnt}页</div>
																	<c:if test="${pagingContext_other.pageNo > 1}">
																		<div class="firstPage">
																			<a href="javascript:void(0);"																				
																				onclick="turnPage(1,'#otherdiv','other');return false;"><img
																				src="../images/1.gif"
																				style="border: 0px; width: 21px; height: 16px;" /></a>
																		</div>
																		<div class="prevPage">
																			<a href="javascript:void(0);"
																				onclick="turnPage(${pagingContext_other.pageNo-1},'#otherdiv','other');return false;"><img
																				src="../images/2.gif"
																				style="border: 0px; width: 41px; height: 16px;" /></a>
																		</div>
																	</c:if>
																	<c:forEach var="i" begin="${pagingContext_other.pageStartNo}"
																		end="${pagingContext_other.perPageCnt}" step="1">
																		<c:choose>
																			<c:when test="${i == pagingContext_other.pageNo}">
																				<div class="currentPage">
																					<font color="#2D56A5">${i}</font>
																				</div>
																			</c:when>
																			<c:otherwise>
																				<div class="pageno">
																					<a href="javascript:void(0);"
																						onclick="turnPage(${i},'#otherdiv','other'); return false;"><font
																						color="#2D56A5">${i}</font></a>
																				</div>
																			</c:otherwise>
																		</c:choose>
																	</c:forEach>
																	<c:if
																		test="${pagingContext_other.pageNo < pagingContext_other.totalPageCnt}">
																		<div class="nextPage">
																			<a href="javascript:void(0);"
																				onclick="turnPage(${pagingContext_other.pageNo + 1},'#otherdiv','other');return false;"><img
																				src="../images/4.gif"
																				style="border: 0px; width: 41px; height: 16px;" /></a>
																		</div>
																		<div class="lastPage">
																			<a href="javascript:void(0);"
																				onclick="turnPage(${pagingContext_other.totalPageCnt},'#otherdiv','other');return false;"><img
																				src="../images/3.gif"
																				style="border: 0px; width: 21px; height: 16px;" /></a>
																		</div>
																	</c:if>
																	<div class="pageNum">
																		<input type="text" name="screen"
																			style="display: none;" /> <input name="pageNum"
																			onkeyup="if(event.keyCode!=13){return false;} turnToPageNo($('#othert').find('[name=pageNum]').val(),${pagingContext_other.totalPageCnt},'#otherdiv','other');return false;"
																			style="width: 30px; float: left;" value="" />
																	</div>
																	<div class="goinput">
																		<a href="javascript:void(0);"
																			onclick="turnToPageNo($('#othert').find('[name=pageNum]').val(),${pagingContext_other.totalPageCnt},'#otherdiv','other');return false;"><img
																			src="../images/go.gif"
																			style="border: 0px; width: 21px; height: 15px;" /></a>
																	</div>
																</div>
															</div>															
														</form>
													</td>
												</tr>
											  </c:if>
											  <!-- $[BUG]0032417 MODIFY END  -->
											</table>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</c:when>
					<c:otherwise>
						<tr>
							<td class="noAccessMessage" align="center"></td>
						</tr>
					</c:otherwise>
				</c:choose>
			</table>
			<table class="ctab">
				<tr>
					<td height="10"></td>
				</tr>
			</table>
		</c:if>
</body>
</html>