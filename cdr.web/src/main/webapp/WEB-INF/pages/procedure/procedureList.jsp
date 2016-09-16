<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="translation.tld" prefix="ref"%>
<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="html2.tld" prefix="html2"%>
<jsp:useBean id="Constants" class="com.founder.cdr.core.Constants"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Pragma" content="no-cache" />
<!-- Prevents caching at the Proxy Server -->
<meta http-equiv="Expires" content="0" />
<title></title>
<link rel="Stylesheet" type="text/css" href="../styles/tablelist.css" />
<script type="text/javascript"
	src="../scripts/procedure/procedureList.js"></script>
<script>
	$(function() {
		// $Author:wu_jianfeng
		// $Date : 2012/10/24 14:10
		// $[BUG]0010542 MODIFY BEGIN

		$( "#surgicalDept").htmlSelectSuggest({width:140, 
			onKeyUp: function(event){
				if (event.keyCode==13) 
		    	{ 
					search('../procedure/list_${patientSn}.html', 'conditionForm');									
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
		action="../procedure/list_${patientSn}.html" <c:if test="${procedureListSearchPra.visitSn != null}">style="display:none;"</c:if>>
		<table class="blockTable" cellpadding="0" cellspacing="0" border="1px"
			bordercolor="#BFD9F0">
			<tr style="height: 28px;">
				<td class="blockHeader" colspan="13" height="27" align="left"
					style="border-top: solid 1px #B3C4D4;"><b><img
						src="../images/pic_sh.png"
						style="padding-left: 3px; padding-right: 2px;" width="19"
						height="19" alt="" align="absmiddle" />手术</b></td>
			</tr>
			<tr class="conditionRow">
				<td width="100%" colspan="13" height="36" valign="middle">
					<div class="cell" style="width: 60px; text-align: right;">手术名称</div>
					<div class="cell" style="width: 100px;">
						<input type="text" name="operationName" style="width: 100%"
							onMouseOver="this.style.background='#FDE8FE';"
							onMouseOut="this.style.background='#FFFFFF'"
							value="${procedureListSearchPra.operationName}">
					</div>
					<div class="cell" style="width: 60px; text-align: right;">手术日期</div>
					<div class="cell">
						<input type="text" id="operationDateFromStr" name="operationDateFromStr"
							style="width: 70px;"
							onmouseover="this.style.background='#FDE8FE';"
							onmouseout="this.style.background='#FFFFFF'" 
							class="datepicker"
							value="${procedureListSearchPra.operationDateFromStr}" /> <span
							style="margin: 0 4px 0 4px;">--</span> <input id="operationDateToStr" type="text"
							name="operationDateToStr" style="width: 70px;"
							onmouseover="this.style.background='#FDE8FE';"
							onmouseout="this.style.background='#FFFFFF'" 
							class="datepicker"
							value="${procedureListSearchPra.operationDateToStr}" />
					</div>
					<div class="cell" style="width: 60px; text-align: right;">执行科室</div>
                    <!-- $Author :wu_jianfeng
     					 $Date : 2012/10/24 17:08$
    					 [BUG]0010542 MODIFY BEGIN -->
					<div style="float:left;margin-top:4px;width: 140px;">
						<select id="surgicalDept" name="surgicalDept" style="width: 140px;">
							<option value="${Constants.OPTION_SELECT}">请选择</option>
							<html2:pycodeoptions domain="${Constants.DOMAIN_DEPARTMENT}"
								value="${procedureListSearchPra.surgicalDept}" />
						</select>
					</div>
					<c:if test="${Constants.TRUE_ORG_CODE == Constants.DISABLE_ORG_CODE}">
						<div class="cell" style="width: 60px; text-align: right;">医疗机构</div>
						<div style="float:left;margin-top:4px;width: 140px;">
							<select id="orgCode" name="orgCode" style="width: 140px;">
								<option value="${Constants.OPTION_SELECT}">请选择</option>
								<html2:pycodeoptions domain="${Constants.DOMAIN_ORG_CODE}"
									value="${procedureListSearchPra.orgCode}" />
							</select>
						</div>
					</c:if>
                    <!-- [BUG]0010542 MODIFY END -->
					<div class="cell" style="width: 70px; text-align: center;">
						<input type="button"
							style="color: #464646; border: 0px; BACKGROUND-IMAGE: url(../images/5.jpg); width: 57px; height: 25px; margin-top: 3px;cursor: pointer;"
							align="absmiddle"
							onclick="search('../exam/list_${patientSn}.html', 'conditionForm');" />
					</div>
					<div id="toggleBlock" class="container-on cell"
						style="width: 77px;">
						  <input type="button" id="buttonBlock" style="color:#464646;border:0px;BACKGROUND-IMAGE:url(../images/6.jpg);width:77px;height:25px;margin-top:3px;" onclick="showTr('tr1',this)"   align="absmiddle" /> <input type="hidden" id="conditionValue"
							name="conditionValue" value="${conditionValue}" /> <input
							type="hidden" id="senSave" name="senSave" value="${senSave}" />
							<input type="hidden" name="type" value="${procedureListSearchPra.type}" />
							<input type="hidden" name="visitSn"
							value="${procedureListSearchPra.visitSn}" /> <input type="hidden"
							name="inpatientDate" value="${procedureListSearchPra.inpatientDate}" />
					</div>
				</td>
			</tr>
			<tr id='tr1' class="conditionRow moreCondition off">
				<td width="100%" colspan="13" height="36" valign="middle">
					<div class="cell ra" style="width: 60px; text-align: right;">手术医生</div>
					<div class="cell" style="width: 100px;">
						<input type="text" name="zhudao" style="width: 98%;"
							onmouseover="this.style.background='#FDE8FE';"
							onmouseout="this.style.background='#FFFFFF'"
							value="${procedureListSearchPra.zhudao}" />
					</div>

					<div class="cell ra" style="width: 60px; text-align: right">手术台</div>
					<div class="cell" style="width: 100px;">
						<input type="text" name="workload" style="width: 98%;"
							onmouseover="this.style.background='#FDE8FE';"
							onmouseout="this.style.background='#FFFFFF'"
							value="${procedureListSearchPra.workload}" />
					</div>
				<!--  	<div class="cell" style="width: 60px; text-align: right">手术等级</div>
					<div class="cell" style="width: 140px;">
							<select name="procedureLevel" style="width: 140px;">
							<option value="${Constants.OPTION_SELECT}">请选择</option>
							<html:options domain="${Constants.DOMAIN_PROCEDURE_LEVEL}"
								value="${procedureListSearchPra.procedureLevel}" />
						</select>
					</div>
					<div class="cell ra" style="width: 60px; text-align: right">愈合等级</div>
					<div class="cell" style="width: 140px;">
						<select name="healingGrade" style="width: 140px;">
							<option value="${Constants.OPTION_SELECT}">请选择</option>
							<html:options domain="${Constants.DOMAIN_HEALING_GRADE}"
								value="${procedureListSearchPra.healingGrade}" />
						</select>
					</div>-->
				</td>
			</tr>
			<!-- $Author :chang_xuewen
			$Date : 2013/07/04 11:00
			$[BUG]0033461 MODIFY BEGIN -->
			<tr class="tabletitle">
			<td height="28" align="center" width="12%">手术名称</td>
			<td height="28" align="center" width="7%">手术日期</td>
			<td height="28" align="center" width="7%">手术性质</td>
			<td height="28" align="center" width="12%">麻醉方式</td>
			<td height="28" align="center" width="7%">手术台</td>
			<td height="28" align="center" width="7%">手术医生</td>
			<td height="28" align="center" width="7%">手术科室</td>
			<td height="28" align="center" width="7%">申请科室</td>
			<c:if test="${Constants.TRUE_ORG_CODE == Constants.DISABLE_ORG_CODE}">
				<td height="28" align="center" width="7%">医疗机构</td>
			</c:if>
		</tr>
		<!-- $[BUG]0033461 MODIFY END -->
		</table>
	</form>
	<div id='listcon'>
	<!--[if lt ie 8]><div style='+zoom:1;'><![endif]-->
	<table id="tblid" style="width: 100%;" cellspacing="0" cellpadding="0"
		class="table">
		<c:if test="${fn:length(Procedure)==0}">
						<tr>
							<td colspan="13" align="center" class="odd" height="24">没有相关数据！</td>
						</tr>
					</c:if>
			<c:forEach items="${Procedure}" var="Procedure" varStatus="status">
			<!-- $Author :chang_xuewen
			$Date : 2013/07/04 11:00
			$[BUG]0033461 MODIFY BEGIN -->
			<tr id="${Procedure.orderSn}" style="cursor: pointer" class="tabEnter"
			onclick="bigShow($(this),'../procedure/procedure_${Procedure.orderSn}.html?flag=tabs',
{'orderSn':'${Procedure.orderSn}','otherName':'','holdOtherName':'','name':'${Procedure.operationName}','holdName':'${Procedure.operationName}'});">
			<td height="24" width="12%" style="padding-left: 2px;">
				${Procedure.operationName}
			</td>
			<td height="24"  width="7%" align="center">
				${fn:substring(Procedure.planExecTime,0,10)}
			</td>
			<td height="24"  width="7%" align="left">
				${Procedure.operationPropertyName}
			</td>
			<td height="24"  width="12%" align="left">
				${Procedure.anesthesiaName}
			</td>
			<td height="24"  width="7%" align="left">
				${Procedure.operationTable}
			</td>
			<td height="24"  width="7%" align="left">
				${Procedure.operatorName}
			</td>
			<td height="24"  width="7%" align="left">
				${Procedure.execDept}
			</td>
			<td height="24"  width="7%" align="left">
				${Procedure.orderDept}
			</td>
			<c:if test="${Constants.TRUE_ORG_CODE == Constants.DISABLE_ORG_CODE}">
				<td height="24" width="7%" align="center"><ref:translate
	                        domain="${Constants.DOMAIN_ORG_CODE}"
	                        code="${Procedure.orgCode}" /></td>
	        </c:if>
		</tr>
		<!-- $[BUG]0033461 MODIFY END -->
		</c:forEach>
		
		<!-- $Author :chang_xuewen
			$Date : 2013/07/04 11:00
			$[BUG]0033461 MODIFY BEGIN -->
		<tr class="page_line">
		<!-- $[BUG]0033461 MODIFY END -->
			<td colspan="13" style="height: 27px;">
				<form name="pagingform" method="get"
					action="../procedure/list_${patientSn}.html">
					<div class="pagelinks">
						<div style="float: right; height: 100%;">
							<div class="pagedesc">共${pagingContext.totalRowCnt}条记录！
								第${pagingContext.pageNo}页/共${pagingContext.totalPageCnt}页</div>
							<c:if test="${pagingContext.pageNo > 1}">
								<div class="firstPage">
									<a href="javascript:void(0);"
										<c:if test="${procedureListSearchPra.visitSn != null}">onclick="jumpToPage(1,'#ajaxDialog');return false;"</c:if>
										onclick="jumpToPage(1);return false;"><img
										src="../images/1.gif"
										style="border: 0px; width: 21px; height: 16px;" /></a>
								</div>
								<div class="prevPage">
									<a href="javascript:void(0);"
										<c:if test="${procedureListSearchPra.visitSn != null}">onclick="jumpToPage(${pagingContext.pageNo-1},'#ajaxDialog');return false;"</c:if>
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
												<c:if test="${procedureListSearchPra.visitSn != null}">onclick="jumpToPage(${i},'#ajaxDialog');return false;"</c:if>
												onclick="jumpToPage(${i}); return false;"><font
												color="#2D56A5">${i}</font></a>
										</div>
									</c:otherwise>
								</c:choose>
							</c:forEach>
							<c:if test="${pagingContext.pageNo < pagingContext.totalPageCnt}">
								<div class="nextPage">
									<a href="javascript:void(0);"
										<c:if test="${procedureListSearchPra.visitSn != null}">onclick="jumpToPage(${pagingContext.pageNo + 1},'#ajaxDialog');return false;"</c:if>
										onclick="jumpToPage(${pagingContext.pageNo + 1});return false;"><img
										src="../images/4.gif"
										style="border: 0px; width: 41px; height: 16px;" /></a>
								</div>
								<div class="lastPage">
									<a href="javascript:void(0);"
										<c:if test="${procedureListSearchPra.visitSn != null}">onclick="jumpToPage(${pagingContext.totalPageCnt},'#ajaxDialog');return false;"</c:if>
										onclick="jumpToPage(${pagingContext.totalPageCnt});return false;"><img
										src="../images/3.gif"
										style="border: 0px; width: 21px; height: 16px;" /></a>
								</div>
							</c:if>
							<div class="pageNum">
								<input type="text" name="screen" style="display:none;"/>
								<input name="pageNum"
									<c:if test="${procedureListSearchPra.visitSn != null}">onkeyup="if(event.keyCode!=13){return false;} jumpToPageNo($('#tblid').find('[name=pageNum]').val(),${pagingContext.totalPageCnt},'#ajaxDialog');return false;"</c:if>
								    onkeyup="if(event.keyCode!=13){return false;} jumpToPageNo($('#tblid').find('[name=pageNum]').val(),${pagingContext.totalPageCnt});return false;"
								    style="width: 30px; float: left;" value="" />
							</div>
							<div class="goinput">
								<a href="javascript:void(0);"
									<c:if test="${procedureListSearchPra.visitSn != null}">onclick="jumpToPageNo($('#tblid').find('[name=pageNum]').val(),${pagingContext.totalPageCnt},'#ajaxDialog');return false;"</c:if>
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