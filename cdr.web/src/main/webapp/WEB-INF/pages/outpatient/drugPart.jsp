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
</head>

<body style="margin: 0; padding: 0;">
	<c:if test="${fn:length(drugList)!=0 and viewSettings.showDrug==true}">
			<table width="100%" class="ctab">
				<c:choose>
					<c:when
						test="${accessContorl == false|| (accessContorl && loginUserAclAuths.clinicalInfoAuth02)}">
						<tr>
							<td style="vertical-align: top;">
								<table class="table" width="100%" border="0">
									<tr style="height: 28px; border-bottom: solid 1px #C5D6E0;">
										<td class="blockHeader" colspan="10" style="border-right: 0px"><b>
												<img src="../images/pic_ywyz_right.png" align="absmiddle"
												style="padding-left: 3px; padding-right: 2px;" width="19"
												height="19" alt="" />药物医嘱
										</b></td>
										<td class="blockHeader" align="right"
											style="padding-right: 5px;"><input type="hidden"
											value="1" id="show_drug" /> <a href="javascript:void(0);"
											id="show_drug_text" onclick="showBlock('drug');"
											onfocus="this.blur()" style="text-decoration: none">▼</a></td>
									</tr>
								</table>
								<table style="width: 100%" id="drugInfo" class="table">
									<tr>
										<td>
											<table id="drugt" style="width: 100%;">
												<tr class="tabletitle">
													<td height="28" align="center" width="12%">药物化学名</td>
													<td height="28" align="center" width="12%">药物英文名</td>
													<td height="28" align="center" width="6%">用药天数</td>
													<td height="28" align="center" width="8%">药物类型</td>
													<td height="28" align="center" width="8%">药物剂型</td>
													<td height="28" align="center" width="8%">用药途径</td>
													<td height="28" align="center" width="8%">次剂量</td>
													<td height="28" align="center" width="8%">总剂量</td>
													<td height="28" align="center" width="7%">使用频率</td>
													<!-- <td height="28" align="center" width="8%">长期或临时</td> -->
													<td height="28" align="center" width="7%">开嘱人</td>
												<!-- $Author :chang_xuewen
     			 			 						 $Date : 2013/10/31 11:00
     			 									 $[BUG]0038735 MODIFY BEGIN -->
													<td height="28" align="center">医嘱录入时间</td>
												<!-- $[BUG]0038735 MODIFY END -->
													<td height="28" align="center" width="10%">开医嘱科室</td>
												</tr>
												<c:forEach items="${drugList}" var="drug" varStatus="status">
												<!-- $Author :chang_xuewen
													 $Date : 2013/06/28 16:20
													 $[BUG]0033461 MODIFY BEGIN  -->
													<tr
														<c:if test="${status.count%2==1}">class="odd tabEnter" onmouseout="this.className='odd'"</c:if>
														<c:if test="${status.count%2==0}">class="even tabEnter" onmouseout="this.className='even'"</c:if>
														style="cursor: pointer"
														onmouseover="this.className='mouseover'"														
														onclick="justShow('../drug/detail_${drug.orderSn}.html?flag=tabs',$(this),
														{'otherName':'<c:choose>
										<c:when test="${drug.orderType==Constants.HERBAL_MEDICINE_CLASS}">草药&nbsp;</c:when>										
										<c:otherwise>${drug.approvedDrugName}&nbsp;</c:otherwise>
									</c:choose>','holdOtherName':'${drug.approvedDrugName}',
						'name':'<c:choose>
									<c:when test="${drug.orderType==Constants.HERBAL_MEDICINE_CLASS}">草药&nbsp;</c:when>
									<c:otherwise>${drug.drugName}&nbsp;</c:otherwise>
								</c:choose>','holdName':'${drug.drugName}'});">
													<!-- [BUG]0033461 MODIFY END -->
														<td height="24" align="left"
															<c:if test="${fn:length(drug.drugName)>10}"> title="${drug.drugName}" </c:if>>
															<!-- $Author :jin_peng
																 $Date : 2013/02/19 11:20$
																 [BUG]0013981 MODIFY BEGIN --> <c:choose>
																<c:when
																	test="${drug.orderType==Constants.HERBAL_MEDICINE_CLASS}">
																	<font color="blue">草药</font>
																</c:when>
																<c:otherwise>${drug.drugName}</c:otherwise>
															</c:choose> <!-- [BUG]0013981 MODIFY END -->
														</td>
														<td height="24" align="left"
															<c:if test="${fn:length(drug.englishName)>25}"> title="${drug.englishName}" </c:if>>
															<c:if test="${fn:length(drug.englishName)>25}">${fn:substring(drug.englishName,0,25)}...</c:if>
															<c:if test="${fn:length(drug.englishName)<=25}">${drug.englishName}</c:if>
														</td>
														<td height="24" align="left" >
															${drug.days}
														</td>
														<td height="24" align="left"
															<c:if test="${fn:length(drug.medicineTypeName)>10}"> title="${drug.medicineTypeName}" </c:if>>
															<c:if test="${fn:length(drug.medicineTypeName)>10}">${fn:substring(drug.medicineTypeName,0,10)}...</c:if>
															<c:if test="${fn:length(drug.medicineTypeName)<=10}">${drug.medicineTypeName}</c:if>
														</td>
														<td height="24" align="left"
															<c:if test="${fn:length(drug.medicineForm)>10}"> title="${drug.medicineForm}" </c:if>>
															<c:if test="${fn:length(drug.medicineForm)>10}">${fn:substring(drug.medicineForm,0,10)}...</c:if>
															<c:if test="${fn:length(drug.medicineForm)<=10}">${drug.medicineForm}</c:if>
														</td>
														<td height="24" align="left"
															<c:if test="${fn:length(drug.routeName)>10}"> title="${drug.routeName}" </c:if>>
															<c:if test="${fn:length(drug.routeName)>10}">${fn:substring(drug.routeName,0,10)}...</c:if>
															<c:if test="${fn:length(drug.routeName)<=10}">${drug.routeName}</c:if>
														</td>
														<td height="24" align="right">${drug.dosage}
															${drug.dosageUnit}</td>
														<td height="24" align="right">${drug.totalDosage}
															${drug.totalDosageUnit}</td>
														<td height="24" align="right"
															title="${drug.medicineFreqName }">${drug.medicineFreqName}</td>
														<%-- <td height="24" align="left"><ref:translate
																domain="${Constants.DOMAIN_TEMPORARY_FLAG}"
																code="${drug.temporaryFlag}" /></td> --%>
														<td height="24" align="left">${drug.orderPersonName}</td>
														<td height="24" align="center"><fmt:formatDate
																value="${drug.orderTime}" type="date"
																pattern="yyyy-MM-dd" /></td>
														<td height="24" align="left">${drug.orderDeptName}</td>
													</tr>
												</c:forEach>
												<!-- $Author :chang_xuewen
													 $Date : 2013/05/20 11:20
													 $[BUG]0032417 MODIFY BEGIN  -->
												<c:if test="${pagingContext_drug.totalRowCnt > Constants.ROWCNT_PER_PAGE_YW}">
												<tr>
													<td colspan="10" style="height: 27px;">
														<form name="pageform_drug" method="get"
															action="../visit/drugPart.html">
															<div class="pagelinks">
																<div style="float: right; height: 100%;">
																	<div class="pagedesc">共${pagingContext_drug.totalRowCnt}条记录！
																		第${pagingContext_drug.pageNo}页/共${pagingContext_drug.totalPageCnt}页</div>
																	<c:if test="${pagingContext_drug.pageNo > 1}">
																		<div class="firstPage">
																			<a href="javascript:void(0);"																				
																				onclick="turnPage(1,'#drugdiv','drug');return false;"><img
																				src="../images/1.gif"
																				style="border: 0px; width: 21px; height: 16px;" /></a>
																		</div>
																		<div class="prevPage">
																			<a href="javascript:void(0);"
																				onclick="turnPage(${pagingContext_drug.pageNo-1},'#drugdiv','drug');return false;"><img
																				src="../images/2.gif"
																				style="border: 0px; width: 41px; height: 16px;" /></a>
																		</div>
																	</c:if>
																	<c:forEach var="i" begin="${pagingContext_drug.pageStartNo}"
																		end="${pagingContext_drug.perPageCnt}" step="1">
																		<c:choose>
																			<c:when test="${i == pagingContext_drug.pageNo}">
																				<div class="currentPage">
																					<font color="#2D56A5">${i}</font>
																				</div>
																			</c:when>
																			<c:otherwise>
																				<div class="pageno">
																					<a href="javascript:void(0);"
																						onclick="turnPage(${i},'#drugdiv','drug'); return false;"><font
																						color="#2D56A5">${i}</font></a>
																				</div>
																			</c:otherwise>
																		</c:choose>
																	</c:forEach>
																	<c:if
																		test="${pagingContext_drug.pageNo < pagingContext_drug.totalPageCnt}">
																		<div class="nextPage">
																			<a href="javascript:void(0);"
																				onclick="turnPage(${pagingContext_drug.pageNo + 1},'#drugdiv','drug');return false;"><img
																				src="../images/4.gif"
																				style="border: 0px; width: 41px; height: 16px;" /></a>
																		</div>
																		<div class="lastPage">
																			<a href="javascript:void(0);"
																				onclick="turnPage(${pagingContext_drug.totalPageCnt},'#drugdiv','drug');return false;"><img
																				src="../images/3.gif"
																				style="border: 0px; width: 21px; height: 16px;" /></a>
																		</div>
																	</c:if>
																	<div class="pageNum">
																		<input type="text" name="screen"
																			style="display: none;" /> <input name="pageNum"
																			onkeyup="if(event.keyCode!=13){return false;} turnToPageNo($('#drugt').find('[name=pageNum]').val(),${pagingContext_drug.totalPageCnt},'#drugdiv','drug');return false;"
																			style="width: 30px; float: left;" value="" />
																	</div>
																	<div class="goinput">
																		<a href="javascript:void(0);"
																			onclick="turnToPageNo($('#drugt').find('[name=pageNum]').val(),${pagingContext_drug.totalPageCnt},'#drugdiv','drug');return false;"><img
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