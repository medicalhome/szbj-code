<!-- $Author : wu_jianfeng
     $Date : 2012/09/28 17:49$
     [BUG]0010107 ADD BEGIN
                就诊索引视图修改 -->
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="translation.tld" prefix="ref"%>
<%@ taglib uri="translation.tld" prefix="ref"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="html2.tld" prefix="html2"%>
<%@ taglib uri="optionList.tld" prefix="optionList"%>
<jsp:useBean id="Constants" class="com.founder.cdr.core.Constants" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<meta http-equiv="Pragma" content="no-cache">
	<meta http-equiv="Cache-Control" content="no-cache">
	<meta http-equiv="Expires" content="0">
	<link rel="Stylesheet" type="text/css" href="../styles/tablelist.css" />
	<link rel="Stylesheet" type="text/css" href="../styles/visit-index-view.css" />
	<script type="text/javascript" src="../scripts/visit/visitIndexViewPart.js"></script>
	<script type="text/javascript" src="../scripts/timerInpatient/timerInpatient.js"></script>
	<style></style>
	<script >
		$(function() {
			//调用父js的时间格式校验
			$(".datepicker").bind("blur",parent.isDateString);
		});
		
	    $(document).ready(function() {
			//显示页脚版本信息
			$("#normalTab").show();
	
			// $Author:wu_jianfeng
			// $Date : 2012/11/26 15:10
			// $[BUG]0011511 ADD BEGIN
	
			$( "#visitDept").htmlMultiSelectSuggest({});
	
			// $[BUG]0011511 ADD END
	
			var pageNo = getPageNo();
			var allSize = getAllSize();
			var pageSize = getPageSize();
			
			$("#prev").attr("class",　"but butFront");
			$("#next").attr("class",　"but butBehind");
			
			// $Author:jin_peng
			// $Date : 2012/11/1 10:30
			// $[BUG]0010908 ADD BEGIN
			$("#prev").css("cursor","pointer");
			$("#next").css("cursor","pointer");
			
			// $[BUG]0010908 ADD END
			
			if(pageNo == "1")
			{
				$("#prev").attr("disabled",　true);
				$("#prev").attr("class",　"but butDisabledFront");
				if(allSize <= pageSize)
				{
					$("#next").attr("disabled",　true);
					$("#next").attr("class",　"but butDisabledBehind");
				}	
			}
			else
			{
				var allPageSize = parseInt(pageNo) * pageSize;
				if(allPageSize >= allSize)
				{	
					$("#next").attr("disabled",　true);
					$("#next").attr("class",　"but butDisabledBehind");
				}
			}
			
			var patientAlert = getPatientAlerts();
			
			if(patientAlert == null || patientAlert == "")
			{
	        	$("#patientAlert").hide();        		
	    	}
			else
			{
	        	$("#alertText").html(patientAlert);
	        	$("#patientAlert").show();
	    	}
	
			adjustWidth(null, "#visitDeptContainer");	
			
			logical();
			banTab($('.xtab'));
		});
		
		function getAllSize()
		{
			return ${allSize};
		}
		
		function getPageSize()
		{
			return ${rows} * ${cols};
		}
	
		function getPageNo()
		{
			return ${pageNo};
		}
		
		function redirectToInpatientView(){
			changeView('#inpatientView','${patientSn}',
				{
					inpatientStartDate: "<fmt:formatDate value='${visitIndex.visitDate}' type='date' pattern='yyyy-MM-dd' />",
				 	inpatientEndDate:   "<fmt:formatDate value='${visitIndex.dischargeDate}' type='date' pattern='yyyy-MM-dd' />",
				 	visitTimes: "${visitIndex.visitTimes}"
				}
			);
		}
		
		function adjustWidth(obj, td)
		{
			var $td;
			var width = 0;
			if(obj != null)
			{
				$td = $(obj).parent().parent();
			}
			else
				$td = $(td).parent();
			var $holder = $td.find(".holder");
			$holder.find("li").each(function(){
				width += $(this).width() + 25;
			});
			$td.width(width);
		}
	</script>
</head>
<body style="margin: 0; padding: 0;">
	<div id="visitIndexView">
		<div id="visitIndexSearch" style="border:solid 1px #D8D9DB;background-color:#F0F8FE;"> 
			<form id="conditionForm" name="conditionForm" method="post" action="../visit/visitIndexViewPart_${patientSn}.html">
				<table cellpadding="0" cellspacing="0" border="0" width="100%" style="table-layout: fixed;" id="jzfirst">						
					<tr>
						<td style="text-align:center;">
							<div id="patientAlert" style="text-overflow:ellipsis; white-space:nowrap; overflow:hidden;">
								<img src="../images/pic_gm.gif" style="padding-right: 6px; vertical-align:middle; "/>
								<font color="#DB2C33">
									<b id="alertText" onmouseover="createTip($('#alertText').html())" onmouseout="removeTip()">
											&nbsp;</b>
								</font>
							</div>
						</td>
						<td width="5px">&nbsp;</td>
						<td width="65px">
							主诊断
						</td>
						<td width="110px">
							<select id="diagnosisName" name="diagnosisName" style="width: 100px;">						
								<option value="${Constants.OPTION_SELECT}">请选择</option>
								<%-- <optionList:optionList values="${diagnosisNameList}" selected="${diagnosisName}" />	 --%>
								<c:if test="${diagnosisNameList != null}">
									<c:forEach items="${diagnosisNameList}" var="dn" varStatus="status">
									<option value="${dn}"  <c:if test="${dn == diagnosisName}">selected="selected" </c:if> >
									${dn}
									</option>
									</c:forEach>
								</c:if>
							</select>
						</td>
						
						<td width="55px">
							就诊类型
						</td>
						<td width="110px">
							<select id="visitType" name="visitType" style="width: 100px;">						
								<option value="${Constants.OPTION_SELECT}">请选择</option>
								<html:options domain="${Constants.DOMAIN_VISIT_TYPE}" 
									value="${visitListSearchPra.visitTypeCode}" />
							</select>
						</td>
						
						<c:if test="${Constants.TRUE_ORG_CODE == Constants.DISABLE_ORG_CODE}">
							<td width="55px">
								医疗机构
							</td>
							<td width="140px">
								<select id="orgCode" name="orgCode" style="width: 130px;">
									<option value="${Constants.OPTION_SELECT}">请选择</option>
									<html2:pycodeoptions domain="${Constants.DOMAIN_ORG_CODE}"
										value="${visitListSearchPra.orgCode}" />
								</select>
							</td>
						</c:if>
						
						<td width="55">就诊科室</td>
						<td width="90px">						
							 <div id="visitDeptContainer" >
								 <select id="visitDept" name="visitDept" onchange="adjustWidth(this)" style="width: 88px; display:none;" multiple>
									<option value="${Constants.OPTION_SELECT}">请选择科室</option>
									<html2:pycodeoptions source="${visitDepts}" domain="${Constants.DOMAIN_DEPARTMENT}" more="${visitListSearchPra.visitDepts}"/>
								 </select>
								 <input type="input" id="visitDept1" style="display:none;"/>
							 </div>	
						</td>
						<td width="62px">
							<input type="button" style="cursor:pointer" id="jzTabGo" class="but butSearch" onclick="searchVisitIndex('../visit/visitIndexViewPart_${patientSn}.html', 'conditionForm', '#visitIndexView', '1');" />
						</td>
						<td width="60px">
							<input id="prev" type="button" onclick="jumpToPage(${pageNo-1}, '#dyContent', '1', '#visitIndexView');return false;"  />
						</td>
						<td width="60px">
							<input id="next" type="button" onclick="jumpToPage(${pageNo+1}, '#dyContent', '1', '#visitIndexView');return false;" />
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div id="viewContent" style="border:1px #C7CACF">
			<table id="visitIndexInfo" style="width:100%; background-color:#EFFFFF; border:1px #C7CACF" >
			<tr><td align="center"><table  style="width:100%;">
				<c:forEach var="rowIndex" begin="0" end="${rows - 1}" step="1">
					<tr>
						<c:forEach var="colIndex" begin="0" end="${cols - 1}" step="1">
							<td>
								<c:forEach items="${visitList}" var="visitIndex" begin="${colIndex + rowIndex * cols}" end="${colIndex + rowIndex * cols}" varStatus="status">
									<c:choose>
										<c:when test="${visitIndex.visitTypeCode==VISIT_TYPE_CODE_OUTPATIENT}">
											<td class="mzInfo" width="${100 / cols}%">
										</c:when>
										<c:when test="${visitIndex.visitTypeCode==VISIT_TYPE_CODE_PHYSICALEXAM}">
											<td class="mzInfo" width="${100 / cols}%">
										</c:when>
										<c:when test="${visitIndex.visitTypeCode==VISIT_TYPE_CODE_PHYSICALEXAM_BOSS}">
											<td class="mzInfo" width="${100 / cols}%">
										</c:when>	
										<c:when test="${visitIndex.visitTypeCode==VISIT_TYPE_CODE_EMERGENCY}">
											<td class="jzInfo" width="${100 / cols}%">
										</c:when>
										<c:when test="${visitIndex.visitTypeCode==VISIT_TYPE_CODE_EMERGENCY_ST}">
											<td class="jzInfo" width="${100 / cols}%">
										</c:when>
										<c:when test="${visitIndex.visitTypeCode==VISIT_TYPE_CODE_INPATIENT}">
											<td class="zyInfo" width="${100 / cols}%">
										</c:when>	
										<c:when test="${visitIndex.visitTypeCode==VISIT_TYPE_CODE_INPATIENT_TR}">
											<td class="zyInfo" width="${100 / cols}%">
										</c:when>			
										<c:otherwise>
											<td class="noInfo" width="${100 / cols}%">
										</c:otherwise>
									</c:choose>
									
									
									<c:choose>
										<c:when test="${visitIndex==null}">
											<div class="emptyVisitinfo">&nbsp;</div>
										</c:when>				
										<c:otherwise>
											<table id="mouseSelectDiv" tabindex=0
													<c:choose>
												  		<c:when test="${(visitIndex.visitTypeCode==VISIT_TYPE_CODE_INPATIENT||visitIndex.visitTypeCode==VISIT_TYPE_CODE_INPATIENT_TR)&&visitIndex.visitStatusCode!=VISIT_STATUS_OUT_HOSPITAL}">
													  		style="width:100%; height: 100% ;cursor: pointer"
													  		onclick="changeView('#inpatientView','${patientSn}',{dischargeDateAndVisitSn:'<fmt:formatDate value="${visitIndex.dischargeDate}" type="date" pattern="yyyy-MM-dd" />;${visitIndex.visitSn};<fmt:formatDate value="${visitIndex.visitDate}" type="date" pattern="yyyy-MM-dd" />', inpatientStartDate:'<fmt:formatDate value="${visitIndex.visitDate}" type="date" pattern="yyyy-MM-dd" />'},'','1');"
												  		</c:when>
												  		<c:when test = "${(visitIndex.visitTypeCode == VISIT_TYPE_CODE_INPATIENT||visitIndex.visitTypeCode==VISIT_TYPE_CODE_INPATIENT_TR)&& visitIndex.visitStatusCode == VISIT_STATUS_OUT_HOSPITAL}">
												  			style="width:100%; height: 100% "
												  		</c:when>
												  		<c:otherwise>
												  			style="width:100%; height: 100% ;cursor: pointer"
												  			onclick="changeOutpatientView('../visit/outpatientViewPart.html', '${visitIndex.visitSn}');"
												  		</c:otherwise>
												    </c:choose>
													<c:if test="${visitIndex.visitTypeCode==VISIT_TYPE_CODE_OUTPATIENT||visitIndex.visitTypeCode==VISIT_TYPE_CODE_PHYSICALEXAM||visitIndex.visitTypeCode==VISIT_TYPE_CODE_PHYSICALEXAM_BOSS}">															 
													onmouseover="this.className='mzInfoMouseOver'" onmouseout="this.className = 'mzInfo'" class="tabEnter"
													</c:if>
													<c:if test="${visitIndex.visitTypeCode==VISIT_TYPE_CODE_EMERGENCY||visitIndex.visitTypeCode==VISIT_TYPE_CODE_EMERGENCY_ST}">
													onmouseover="this.className='jzInfoMouseOver'" onmouseout="this.className='jzInfo'" class="tabEnter"
													</c:if>
													<c:if test="${visitIndex.visitTypeCode==VISIT_TYPE_CODE_INPATIENT||visitIndex.visitTypeCode==VISIT_TYPE_CODE_INPATIENT_TR}">
													onmouseover="this.className='zyInfoMouseOver'" onmouseout="this.className='zyInfo'" class="tabEnter"
													</c:if>
													>
												<tr>
													<td width="35%">
														<!--$Author:tong_meng 
													      	$Date : 2013/11/07 10:10
													      	$[BUG]0039034 ADD BEGIN-->
														<c:if test="${visitIndex.visitTypeCode==VISIT_TYPE_CODE_INPATIENT||visitIndex.visitTypeCode==VISIT_TYPE_CODE_INPATIENT_TR}">
															<div style="margin-left:23px;;margin-bottom:43px;text-align:left;">
																<div>
																	<c:if test="${visitIndex.dischargeWardName != null}">
																		<c:if test="${fn:length(visitIndex.dischargeWardName)>10}">
																			${fn:substring(visitIndex.dischargeWardName,0,10)}...&nbsp;
																		</c:if>
																		<c:if test="${fn:length(visitIndex.dischargeWardName)<=10}">
																			${visitIndex.dischargeWardName}&nbsp;
																		</c:if>
																	</c:if>
																</div>
																<div><c:if test="${!empty visitIndex.dischargeBedNo}">${visitIndex.dischargeBedNo}床</c:if></div>
															</div>
														</c:if>
													     <!--$[BUG]0039034 ADD BEGIN-->
														<div style="width:80px">&nbsp;</div>
													</td>
													<td>
														<table style="width:100%;">
															<tr height="17px">
																<td align="left">
													  			<!-- $Author :jin_peng
																	 $Date : 2012/11/06 15:40$
																	 [BUG]0011026 MODIFY BEGIN -->
													  		 		<fmt:formatDate value="${visitIndex.visitDate}" type="date" pattern="yyyy-MM-dd HH:mm" />
		  													  		<c:if test="${visitIndex.visitStatusCode==VISIT_STATUS_DISCHARGE_HOSPITAL}">
		  													  		- <fmt:formatDate value="${visitIndex.dischargeDate}" type="date" pattern="yyyy-MM-dd HH:mm" />
		  													  		</c:if>
													  			<!-- [BUG]0011026 MODIFY END -->
																</td>
															</tr>
															<tr height="17px">
																<td align="left">
																<c:choose>
																<c:when test="${visitIndex.visitTypeCode==VISIT_TYPE_CODE_INPATIENT||visitIndex.visitTypeCode==VISIT_TYPE_CODE_INPATIENT_TR}">
																<b>${visitIndex.visitStatusName}</b>
																</c:when>
																<c:otherwise>		
																	<b>${visitIndex.visitTypeName}</b>
																</c:otherwise>
																</c:choose>
																</td>
															</tr>
															<tr height="17px">
																<c:choose>
																	<c:when test="${visitIndex.visitTypeCode==VISIT_TYPE_CODE_INPATIENT||visitIndex.visitTypeCode==VISIT_TYPE_CODE_INPATIENT_TR}">
																		<td align="left" <c:if test="${fn:length(visitIndex.dischargeDeptName)>10}"> title="${visitIndex.dischargeDeptName}"</c:if>>
																			<c:if test="${fn:length(visitIndex.visitDeptName)>10}">
																				${fn:substring(visitIndex.dischargeDeptName,0,10)}...
																			</c:if>
																			<c:if test="${fn:length(visitIndex.dischargeDeptName)<=10}">
																				${visitIndex.dischargeDeptName}
																			</c:if>
																		</td>
																	</c:when>
																	<c:otherwise>
																		<td align="left" <c:if test="${fn:length(visitIndex.visitDeptName)>10}"> title="${visitIndex.visitDeptName}"</c:if>>
																			<c:if test="${fn:length(visitIndex.visitDeptName)>10}">
																				${fn:substring(visitIndex.visitDeptName,0,10)}...
																			</c:if>
																			<c:if test="${fn:length(visitIndex.visitDeptName)<=10}">
																				${visitIndex.visitDeptName}
																			</c:if>
																		</td>
																	</c:otherwise>
																</c:choose>
															</tr>
															<tr height="17px">
																<td align="left" <c:if test="${fn:length(visitIndex.visitDoctorName)>10}"> title="${visitIndex.visitDoctorName}"</c:if> >
																	<c:if test="${fn:length(visitIndex.visitDoctorName)>10}">
																		${fn:substring(visitIndex.visitDoctorName,0,10)}...
																	</c:if>
																	<c:if test="${fn:length(visitIndex.visitDoctorName)<=10}">
																		${visitIndex.visitDoctorName}
																	</c:if>
																
															</tr>
															<tr height="17px">
																<td align="left" <c:if test="${fn:length(visitIndex.mainDiagnosis)>10}"> title="${visitIndex.mainDiagnosis}"</c:if>>
																	<c:choose>
																		<c:when test="${accessContorl == false || (accessContorl && aclAuths.clinicalInfoAuth01)}">												
																			<c:if test="${fn:length(visitIndex.mainDiagnosis)>10}">
																				${fn:substring(visitIndex.mainDiagnosis,0,10)}...
																			</c:if>
																			<c:if test="${fn:length(visitIndex.mainDiagnosis)<=10}">
																				${visitIndex.mainDiagnosis}
																			</c:if>
																		</c:when>
																		<c:otherwise>
																			<font style="color: red">
																				${Constants.ACL_NOACCESS_MESSAGE}诊断信息</font>
																		</c:otherwise>
																	</c:choose>
																		
		
																</td>
															</tr>
															<c:if test="${Constants.TRUE_ORG_CODE == Constants.DISABLE_ORG_CODE}">
																<tr height="17px">
																	<td align="left"><ref:translate domain="${Constants.DOMAIN_ORG_CODE}" code="${visitIndex.orgCode}" /></td>
																</tr>
															</c:if>
															<!-- 
															<tr height="17px">
																<td align="left">
																	  <c:if test="${visitIndex.visitTypeCode==VISIT_TYPE_CODE_OUTPATIENT||visitIndex.visitTypeCode==VISIT_TYPE_CODE_EMERGENCY}">
																		<a href="javascript:void(0)" onclick="changeOutpatientView('../visit/outpatientViewPart.html', '${visitIndex.visitSn}');">查看详细</a>
																		</c:if>
																		 <c:if test="${visitIndex.visitTypeCode==VISIT_TYPE_CODE_INPATIENT}">
																		<a href="javascript:void(0)" onclick="changeView('#inpatientView','${patientSn}',{inpatientStartDate:'<fmt:formatDate value="${visitIndex.visitDate}" type="date" pattern="yyyy/MM/dd" />',inpatientEndDate:'<fmt:formatDate value="${visitIndex.dischargeDate}" type="date" pattern="yyyy/MM/dd" />','visitTimes':'${visitIndex.visitTimes}'})">查看详细</a>
																		</c:if>
																</td>
															</tr> -->
														</table>
													</td>
												</tr>
											</table>
										</c:otherwise>
									</c:choose>
								</c:forEach>	
							</td>				
						</c:forEach>
					</tr>
				</c:forEach>
			</table></td></tr></table>
		</div>
	</div>
</body>
</html>
<!-- [BUG]0010107 ADD END -->
