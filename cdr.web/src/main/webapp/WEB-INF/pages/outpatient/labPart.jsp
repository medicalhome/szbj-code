<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="translation.tld" prefix="ref"%>
<%@ taglib uri="html.tld" prefix="html"%>
<jsp:useBean id="Constants" class="com.founder.cdr.core.Constants" />
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
	<script type="text/javascript" src="../scripts/lab/labList.js"></script>
</head>

<body style="margin: 0; padding: 0;">
	<c:if test="${fn:length(labList)!=0 and viewSettings.showLab==true}">
			<table width="100%" class="ctab">
				<c:choose>
					<c:when
						test="${accessContorl == false|| (accessContorl && loginUserAclAuths.clinicalInfoAuth05)}">
						<tr>
							<td style="vertical-align: top;">
								<table width="100%" class="table">
									<!-- $Author :bin_zhang
							     	  $Date : 2012/10/24 17:08$
							    	  [BUG]0010731 MODIFY BEGIN -->
									<tr style="height: 28px;">
										<td class="blockHeader" width="100px" colspan="8"
											style="border-right: 0px"><b> <img
												src="../images/pic_jiany.png"
												style="padding-left: 3px; padding-right: 1px;" width="19"
												height="20" alt="" align="absmiddle" />检验
										</b></td>
										<td class="blockHeader" align="right"
											style="padding-right: 5px;"><input type="hidden"
											align="right" value="1" id="show_lab" /> <a
											href="javascript:void(0);" id="show_lab_text"
											onclick="showBlock('lab');" onfocus="this.blur()"
											style="text-decoration: none">▼</a></td>
									</tr>
									<!--[BUG]0010731 MODIFY END -->
								</table>
								<table style="width: 100%" id="labInfo" class="table">
									<tr>
										<td>
											<table id="labt" style="width: 100%;">
												<tr class="tabletitle">
													<td height="28" align="center" width="13%">检验名称</td>
													<td height="28" align="center" width="10%">医嘱状态</td>
													<td height="28" align="center" width="11%">申请日期</td>
													<td height="28" align="center" width="10%">执行科室</td>
													<td height="28" align="center" width="10%">检验医生</td>
													<td height="28" align="center" width="11%">检验日期</td>
													<td height="28" align="center" width="10%">报告医生</td>
													<td height="28" align="center" width="11%">报告日期</td>
													<td height="28" align="center" width="6%">召回状态</td>
													<td height="28" align="center" width="8%">签章报告</td>
												</tr>
												<c:forEach items="${labList}" var="lab" varStatus="status">
													<!-- $Author :bin_zhang
							                                $Date : 2012/10/30 14:16$
							                                [BUG]0010922 MODIFY BEGIN -->
													<tr
														<c:choose>
													    	<c:when test="${lab.withdrawFlag==Constants.REPORT_WITHDRAW_FLAG}">
													    		class="forbidden" onmouseout="this.className='forbidden tabEnter'" onmouseover="this.className='forbidden'"
													    	</c:when>
													    	<c:when test="${lab.reviewerId=='000'}">
													    		class="tempReport tabEnter" onmouseout="this.className='tempReport'"
													    	</c:when>
													    	<c:when test="${empty lab.labReportSn}">
																class="orderOnly tabEnter" onmouseout="this.className='orderOnly'" 
															</c:when>
															<c:when test="${empty lab.orderSn}">
																class="reportOnly tabEnter" onmouseout="this.className='reportOnly'" 
															</c:when>
													    	<c:otherwise>
													    		 <c:if test="${status.count%2==1 }">class="odd tabEnter" onmouseout="this.className='odd'"</c:if>
													    		 <c:if test="${status.count%2==0 }">class="even tabEnter" onmouseout="this.className='even'"</c:if>
													    	</c:otherwise>
												        </c:choose>
														style="cursor: pointer"
														onmouseover="this.className='mouseover'"
														onclick="showLabDetailDialog('${lab.withdrawFlag}','${lab.labReportSn}','${lab.compositeItemSn}','${patientSn}','${lab.itemCode}','${lab.sourceSystemId}','${lab.orderSn}',$(this),1,'${lab.itemName}');">
														<!-- [BUG]0010922 MODIFY END -->
														<td height="24" align="left"
															<c:if test="${fn:length(lab.itemName)>10}"> title="${lab.itemName}" </c:if>>
															<c:if test="${fn:length(lab.itemName)>10}">${fn:substring(lab.itemName,0,10)}...</c:if>
															<c:if test="${fn:length(lab.itemName)<=10}">${lab.itemName}</c:if>
														</td>
														<td height="24" align="left">${lab.orderStatusName}</td>
														<td height="24" align="left"><fmt:formatDate
																value="${lab.requestDate}" type="date"
																pattern="yyyy-MM-dd" /></td>
														<td height="24" align="left">${lab.labDeptName}</td>
														<td height="24" align="left">${lab.testerName}</td>
														<td height="24" align="center"><fmt:formatDate
																value="${lab.testDate}" type="date" pattern="yyyy-MM-dd" /></td>
														<td height="24" align="left">${lab.reporterName}</td>
														<td height="24" align="left"><fmt:formatDate
																value="${lab.reportDate}" type="date"
																pattern="yyyy-MM-dd" /></td>
														<td height="24" align="center"><c:if
																test="${lab.withdrawFlag==Constants.REPORT_WITHDRAW_FLAG}">
																<a href="javascript:void(0);"
																	<c:if test="${labListSearchParameters.visitSn != null}">onclick="showDialog('../exam/withdraw_${lab.labReportSn}.html?flag=tabs','召回信息', {}, 700, 500, '#ajaxDialogDetail');stopBubble(event);return false;"</c:if>
																	onclick="showDialog('../exam/withdraw_${lab.labReportSn}.html?flag=tabs','召回信息', {});stopBubble(event);return false;"><img
																	src="../images/jczh.png" align="middle"
																	style="padding-right: 1px; border: 0px" /></a>
															</c:if> <c:if
																test="${lab.withdrawFlag==Constants.REPORT_MODIFY_FLAG}">
																<a href="javascript:void(0);"
																	<c:if test="${labListSearchParameters.visitSn != null}">onclick="showDialog('../exam/withdraw_${lab.labReportSn}.html?flag=tabs','召回信息', {}, 700, 500, '#ajaxDialogDetail');stopBubble(event);return false;"</c:if>
																	onclick="showDialog('../exam/withdraw_${lab.labReportSn}.html?flag=tabs','召回信息', {});stopBubble(event);return false;"><img
																	src="../images/yxg.png" align="middle"
																	style="padding-right: 1px; border: 0px" /></a>
															</c:if></td>
														<td height="24" align="center"
															<c:if test="${empty lab.signatureId}"> onclick="stopBubble(event);return false;"</c:if>>
															<c:if test="${!empty lab.signatureId}">
																<a href="javascript:void(0);"
																	onclick="previewDoc('${signatureUrl}','${lab.signatureId}');return false;"><img
																	src="../images/qzbg.png" align="middle"
																	style="padding-right: 1px; border: 0px" /></a>
															</c:if>
														</td>
													</tr>
												</c:forEach>
												<!-- $Author :chang_xuewen
													 $Date : 2013/07/01 16:40
													 $[BUG]0032417 MODIFY BEGIN  -->
												<c:if test="${pagingContext_lab.totalRowCnt > Constants.ROWCNT_PER_PAGE_YW}">
												<tr>
													<td colspan="10" style="height: 27px;">
														<form name="pageform_lab" method="get"
															action="../visit/labPart.html">
															<div class="pagelinks">
																<div style="float: right; height: 100%;">
																	<div class="pagedesc">共${pagingContext_lab.totalRowCnt}条记录！
																		第${pagingContext_lab.pageNo}页/共${pagingContext_lab.totalPageCnt}页</div>
																	<c:if test="${pagingContext_lab.pageNo > 1}">
																		<div class="firstPage">
																			<a href="javascript:void(0);"																				
																				onclick="turnPage(1,'#labdiv','lab');return false;"><img
																				src="../images/1.gif"
																				style="border: 0px; width: 21px; height: 16px;" /></a>
																		</div>
																		<div class="prevPage">
																			<a href="javascript:void(0);"
																				onclick="turnPage(${pagingContext_lab.pageNo-1},'#labdiv','lab');return false;"><img
																				src="../images/2.gif"
																				style="border: 0px; width: 41px; height: 16px;" /></a>
																		</div>
																	</c:if>
																	<c:forEach var="i" begin="${pagingContext_lab.pageStartNo}"
																		end="${pagingContext_lab.perPageCnt}" step="1">
																		<c:choose>
																			<c:when test="${i == pagingContext_lab.pageNo}">
																				<div class="currentPage">
																					<font color="#2D56A5">${i}</font>
																				</div>
																			</c:when>
																			<c:otherwise>
																				<div class="pageno">
																					<a href="javascript:void(0);"
																						onclick="turnPage(${i},'#labdiv','lab'); return false;"><font
																						color="#2D56A5">${i}</font></a>
																				</div>
																			</c:otherwise>
																		</c:choose>
																	</c:forEach>
																	<c:if
																		test="${pagingContext_lab.pageNo < pagingContext_lab.totalPageCnt}">
																		<div class="nextPage">
																			<a href="javascript:void(0);"
																				onclick="turnPage(${pagingContext_lab.pageNo + 1},'#labdiv','lab');return false;"><img
																				src="../images/4.gif"
																				style="border: 0px; width: 41px; height: 16px;" /></a>
																		</div>
																		<div class="lastPage">
																			<a href="javascript:void(0);"
																				onclick="turnPage(${pagingContext_lab.totalPageCnt},'#labdiv','lab');return false;"><img
																				src="../images/3.gif"
																				style="border: 0px; width: 21px; height: 16px;" /></a>
																		</div>
																	</c:if>
																	<div class="pageNum">
																		<input type="text" name="screen"
																			style="display: none;" /> <input name="pageNum"
																			onkeyup="if(event.keyCode!=13){return false;} turnToPageNo($('#labt').find('[name=pageNum]').val(),${pagingContext_lab.totalPageCnt},'#labdiv','lab');return false;"
																			style="width: 30px; float: left;" value="" />
																	</div>
																	<div class="goinput">
																		<a href="javascript:void(0);"
																			onclick="turnToPageNo($('#labt').find('[name=pageNum]').val(),${pagingContext_lab.totalPageCnt},'#labdiv','lab');return false;"><img
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