﻿<%@ page language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
    <title>患者列表</title>
	<script type="text/javascript" src="../scripts/searchTable/searchTableCommon.js"></script>
	<script type="text/javascript" src="../scripts/searchTable/searchTableCheck.js"></script>
	<script type="text/javascript" src="../scripts/searchTable/customBusinessFields.js"></script>
	<script type="text/javascript" src="../scripts/searchTable/searchTableRow.js"></script>
	<script type="text/javascript" src="../scripts/searchTable/searchTablePairRow.js"></script>
	<script type="text/javascript" src="../scripts/searchTable/groupSearchTableRow.js"></script>
	<script type="text/javascript" src="../scripts/searchTable/searchTable.js"></script>
	<script type="text/javascript" src="../scripts/patient/patientList.js"></script>	
    <script type="text/javascript" src="../scripts/patient/patientListDetail.js"></script>	
    <script>
		$(function()
		{
			$("#patientDomainStr").val('${patientListSearchPra.patientDomainStr}');
			//searchPatients('advance', true);
			searchPatients('normal', true);
			$( ".deptSelect_ser").htmlSelectSuggest({width:177,
				onKeyUp: function(event)
				{
					if (event.keyCode==13) 
			    	{ 
						searchPatients('normal', false);
					} 
				}
			});
			$( ".wardSelect_ser").htmlSelectSuggest({width:96,
				onKeyUp: function(event)
				{
					if (event.keyCode==13) 
			    	{ 
						searchPatients('normal', false);
					} 
				}
			});
			// $Author:chang_xuewen
            // $Date : 2013/12/31 16:10
            // $[BUG]0040993 ADD BEGIN
     		$( ".staffSelect_ser").htmlSelectSuggest({width:177,
				onKeyUp: function(event)
				{
					if (event.keyCode==13) 
			    	{ 
						searchPatients('normal', false);
					} 
				}
			});     
			// $[BUG]0040993 ADD END
			//添加页面文本框的回车检索
			$("#conditionFormPatient input").keyup(function(event){
				if (event.keyCode==13) 
			    { 
					searchPatients('normal', false);
			    } 
			});
			
			logicalPatient();

			onchangeHeight("pl_tabs4");
		// $Author:chang_xuewen
        // $Date : 2014/01/02 16:10
        // $[BUG]0040993 ADD BEGIN
			if("true" === reacl()){
				$(".selectdiv3").find("input").attr("disabled",true);
			}
		});	
		
		
		function reacl(){
			return "${(useACL && aclAuths.patientScopeAuth01) && !(useACL && aclAuths.patientScopeAuth05)}";
			//return "${useACL && aclAuths.patientScopeAuth05}";
		}
		
		
		function display(){
			//alert("dis");
  			$("#staff").attr("disabled",true);
			jQuery(".selectdiv3").load("../patient/displayStaff.html", function(response, status, xhr){
				if(status == "success")
				{
					//alert("success");
					//
					
				}
		        //如果状态是error或者timeout, 显示错误对话框
				else if(status == "error" || status == "timeout")
		        {
					alert("error");
		        }
			});  
   			$("#deptName").attr("disabled",true);
			jQuery("#selectdivdept").load("../patient/displayDept.html?tab=pl", function(response, status, xhr){
				if(status == "success")
				{
					//alert("success");
				}
		        //如果状态是error或者timeout, 显示错误对话框
				else if(status == "error" || status == "timeout")
		        {
					alert("error");
		        }
			});
   			$("#wardName").attr("disabled",true);
			jQuery("#selectdivward").load("../patient/displayWard.html?tab=pl", function(response, status, xhr){
				if(status == "success")
				{
					//alert("success");
				}
		        //如果状态是error或者timeout, 显示错误对话框
				else if(status == "error" || status == "timeout")
		        {
					alert("error");
		        }
			}); 
		}
	window.setTimeout(function(){display()},300);
	</script>
</head>
<body style="margin: 0; padding: 0;">
<%-- <c:choose>
	<c:when test="${loginFlag != null && fn:length(loginFlag) != 0}"> --%>
		<div id="pl_tabs4" style="margin-left:1px;margin-right:1px;">
		  <form id="conditionFormPatient" name="conditionFormPatient" method="post" action="../patient/list_patient_detail.html">
			<table cellpadding="2" cellspacing="0" border="0" class="blockTable" >
				<tr class="conditionRow">
					<td style="vertical-align: top;">
						<table class="blockTable" style="width:99%" cellpadding="2" cellspacing="0" border="0" id="tb_patientList">
							<tr class="conditionRow" height="29">
								<%-- <td width="60" align="right" style="padding-right: 2px;">就诊号</td>
								<td width="200" align="left" style="padding-left: 2px;">
									<select id="patientDomainStr" name="patientDomainStr" style="width:68px;">
										 <option value="${Constants.OPTION_SELECT}">请选择</option>
							                <!--
							                $Author:wu_jianfeng
							                $Date:2012/9/13 10:42
							                $[BUG]BUG0009696 MODIFY BEGIN
											-->
										 <option value="${Constants.PATIENT_DOMAIN_OUTPATIENT}">就诊卡号</option>
										 <option value="${Constants.PATIENT_DOMAIN_INPATIENT}">住院号</option>
							                <!-- $[BUG]BUG0009696 MODIFY BEGIN -->
									</select>
							         <input type="text" id="visitNo" name="visitNo" style="width: 105px;" 
										onmouseover="this.style.background='#FDE8FE';" onmouseout="this.style.background='#FFFFFF'" value="${patientListSearchPra.visitNo}"/>
								</td> --%>
								<td  align="right" style="padding-right: 2px;" >病人号</td>
								<td  align="left" style="padding-left: 2px;">
									<input type="text" id="patientEId" name="patientEId" style="width: 96px;" onmouseover="this.style.background='#FDE8FE';"
										onmouseout="this.style.background='#FFFFFF'" value="${patientListSearchPra.patientEId}"/>
								</td>
								<td width="60" align="right" style="padding-right: 2px;">姓名</td>						
								<td width="110" align="left" style="padding-left: 2px;">
									<input type="text" id="patientName" name="patientName" style="width: 96px;" onmouseover="this.style.background='#FDE8FE';"
											onmouseout="this.style.background='#FFFFFF'" value="${patientListSearchPra.patientName}"/>
								</td>
								<td width="50" align="right" style="padding-right: 2px;">性别</td>
								<td width="90" align="left" style="padding-left: 2px;">
									<select id="gender" name="gender" style="width:90px;">
										 <option value="${Constants.OPTION_SELECT}">请选择</option>
									   <html:options domain="${Constants.DOMAIN_GENDER}" value='${patientListSearchPra.genderCode}' />
									</select>
								</td>
							</tr>
							<tr class="conditionRow" height="29">
								<td align="right" style="padding-right: 2px;">就诊日期</td>
								<td align="left" style="padding-left: 2px;">
									 <input type="text" id="visitStartDate" name="visitStartDate" style="width: 74px;" onmouseover="this.style.background='#FDE8FE';"
										onmouseout="this.style.background='#FFFFFF'"  class="datepicker" value="${patientListSearchPra.visitStartDate}" />&nbsp;至&nbsp;
									 <input type="text" id="visitEndDate" name="visitEndDate" style="width: 74px;" onmouseover="this.style.background='#FDE8FE';"
										onmouseout="this.style.background='#FFFFFF'" class="datepicker" value="${patientListSearchPra.visitEndDate}" />
								</td>
								<td align="right" style="padding-right: 2px;">就诊类型</td>
								<td align="left" style="padding-left: 2px;">
									<select id="visitTypeStr" name="visitTypeStr" style="width:100px;">
										 <option value="${Constants.OPTION_SELECT}">请选择</option>
									     <html:options domain="${Constants.DOMAIN_VISIT_TYPE}" value='${patientListSearchPra.visitTypeStr}' />
									</select>
								</td>
								<td align="right" style="padding-right: 2px;">年龄段</td>
								<td align="left" style="padding-left: 2px;">
									<select id="baseAge" name="baseAge" style="width:90px;">
										<option value="${Constants.OPTION_SELECT}">请选择</option>
									   	<html:options domain="${Constants.AGE_GROUP}" value='${patientListSearchPra.baseAge}' />
									</select>
								</td>
							</tr>
							<tr class="conditionRow" height="29">
								<td align="right" style="padding-right: 2px;">科室</td>
								<td align="left" style="padding-left: 2px;">
								<div class="selectdiv" id="selectdivdept">								
									<select id="deptName" class="deptSelect_ser" name="deptName"
										style="width: 250px;">
										<option value="${Constants.OPTION_SELECT}">请选择</option>

									</select>
								</div>
									 <%-- <input type="text" id="deptName" name="deptName" style="width: 177px;" onmouseover="this.style.background='#FDE8FE';"
										onmouseout="this.style.background='#FFFFFF'" value="${patientListSearchPra.deptName}"/> --%>
								</td>
								<td align="right" style="padding-right: 2px;">病区</td>
								<td align="left" style="padding-left: 2px;">
								<div class="selectdiv" id="selectdivward">
									<select id="wardName" class="wardSelect_ser" name="wardName"
										style="width: 250px;">
										<option value="${Constants.OPTION_SELECT}">请选择</option>
<%-- 										<html2:pycodeoptions
											domain="${Constants.DOMAIN_WARD}"/> --%>
									</select>
								</div>
									<%-- <input type="text" id="wardName" name="wardName" style="width: 90px;" onmouseover="this.style.background='#FDE8FE';"
										onmouseout="this.style.background='#FFFFFF'" value="${patientListSearchPra.wardName}"/> --%>
								</td>
								<td align="right" style="padding-right: 2px;">床号</td>
								<td align="left" style="padding-left: 2px;">
									<input type="text" id="bedNo" name="bedNo" style="width: 86px;" onmouseover="this.style.background='#FDE8FE';"
										onmouseout="this.style.background='#FFFFFF'" value="${patientListSearchPra.bedNo}"/>
								</td>
							</tr>
							<tr class="conditionRow" height="29">
								<td>
								<div align="right" style="padding-right: 2px;">门诊/住院医生</div>
								</td>
								<td align="left" style="padding-left: 2px;">
								<div class="selectdiv3" >									
									<select id="staff" name="staff" class="staffSelect_ser" style="width:181px;">
										<option value="${Constants.OPTION_SELECT}">请选择</option>
<%-- 										<html2:pycodeoptions
											domain="${Constants.DOMAIN_STAFF}"/> --%>
									</select>
								</div>
								</td>			
								
								<td>
									<div align="right" style="padding-right: 2px;">身份证号</div>
								</td>
								<td width="110" align="left" style="padding-left: 2px;">
									<input type="text" id="idNumber" name="idNumber" style="width: 96px;" onmouseover="this.style.background='#FDE8FE';"
										onmouseout="this.style.background='#FFFFFF'" value="${patientListSearchPra.idNumber}"/>
								</td>
								<td></td>
								<td></td>
							</tr>
							<%-- <tr class="conditionRow" height="29">
								<td align="right" style="padding-right: 2px;">疾病名称</td>
								<td align="left" style="padding-left: 2px;"  colspan="5">
									 <input type="text" id="diseaseName" name="diseaseName" style="width: 177px;" onmouseover="this.style.background='#FDE8FE';"
										onmouseout="this.style.background='#FFFFFF'" value="${patientListSearchPra.diseaseName}"/>
								</td>
							</tr> --%>
							<!-- $Author :chang_xuewen
			 					 $Date : 2013/12/16 10:00$
								 [BUG]0040770 MODIFY BEGIN -->
							<c:if test="${Constants.TRUE_ORG_CODE == Constants.DISABLE_ORG_CODE}">
							<tr  class="conditionRow" height="29">
								<td>
								<div align="right" style="padding-right: 2px;">医疗机构</div>
								</td>
								<td>
								<div align="left" style="float:left;margin-top:4px;width: 200px;padding-left: 2px;">
									<select id="orgCodeFlag" name="orgCodeFlag" style="width:181px;">
										<option value="${Constants.OPTION_SELECT}">请选择</option>
										<html:options domain="${Constants.DOMAIN_ORG_CODE}"
										value='${orgCode}' />						
									</select>
								</div>
								</td>								
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
							</tr>
							</c:if> 
			                <!-- [BUG]0040770 MODIFY END -->
						</table>
						<table class="blockTable" id="tbPatientListAS" style="width: 100%;display:none;" cellspacing="0" cellpadding="2"
							border="0">
							<tr class="tabletitle" id="title">
								<td height="28" align="center"></td>
								<td height="28" align="center">且&nbsp;/&nbsp;或</td>
								<td height="28" align="center">字段名称</td>
								<td height="28" align="center">条件</td>
								<td height="28" align="center">字段值</td>
								<td height="28" align="center"></td>
							</tr>
							<tr class="odd" id="new">
								<td height="24" align="left" width="60px" style="padding-left:2px;"nowrap>选择字段</td>
								<td height="24" align="center" width="75px">
									<select id="t_control" style="width:70px;display:none;" class="control">
										<option value="-1" selected>请选择</option>
										<option value="and">and</option>
										<option value="or">or</option>
									</select>
								</td>
								<td height="24" width="110px" style="text-align:left;">&nbsp;
									<select id="t_field" style="width:100px" class="field">
										<option value="-1" selected>请选择</option>
										<option value="patientName">患者姓名</option>
										<option value="patientGender">患者性别</option>
										<option value="visitNo">就诊号</option>
										<option value="visitType">就诊类型</option>
										<option value="visitDate">就诊日期</option>
										<option value="baseAge">年龄段</option>
										<option value="diseaseName">疾病名称</option>
										<option value="labItemResult">检验项目结果</option>
										<option value="visitDept">就诊科室</option>
									</select>
								</td>
								<td height="24" align="center" width="80px" >
									<select id="t_opt" style="width:80px;display:none;" class="opt">
										<option value="-1" selected>请选择</option>
										<option value="equal">等于</option>
										<option value="notEqual">不等于</option>
										<option value="like">类似于</option>
										<option value="more">大于</option>
										<option value="less">小于</option>
										<option value="moreEq">大于等于</option>
										<option value="LessEq">小于等于</option>
									</select>
								</td>
								<td height="24" align="center" width="150px">
									<input id="t_inputValue" value="" type="textbox" class="inputValue" style="width:120px;display:none;" />
									<select id="t_selectValue" class="selectValue" style="width:125px;display:none;"></select>
									<input type="text" id="t_inputDate" onmouseover="this.style.background='#FDE8FE';"
										onmouseout="this.style.background='#FFFFFF'"  class="datepickerOther" value="" style="width:120px;display:none;" />						
								</td>
								<td height="24" align="center" width="30px">
									<a id="t_addbtn" class="addbtn" href="javascript:void(0)" onclick="addSearchTableRow();"><img src="../images/button_addsearch.png" alt="增加条件" title="增加条件" style="border:0;"/></a> 
									<a id="t_deletebtn" class="deletebtn"  style="display:none;" href="javascript:void(0)" onclick="deleteSearchTableRow(this);"><img src="../images/button_deletesearch.png" alt="删除条件" title="删除条件" style="border:0;"/></a> 
								</td>
							</tr>
						</table>
					</td>
					<td style="vertical-align: top;" width="95px">
					     <table cellpadding="5" cellspacing="0"  width="100%">
					        <!-- $Author :chang_xuewen
						      $Date : 2013/11/07 11:00
						      $[BUG]0039036 DELETE BEGIN -->
					     	<!-- <tr>
					     		<td style="text-align:left; padding-left:5px">
								     <input type="button" id="btnSearchType"
										style="color: #464646; border: 0px; BACKGROUND-IMAGE: url(../images/6.jpg); width: 77px; height: 25px; margin-top: 3px;cursor: pointer;"
										onclick="btnSearchType_onclick()" align="absmiddle" />
					     		</td>
					     	</tr> -->
					     	<!-- $[BUG]0039036 DELETE END -->
					     	<tr>
					     		<td style="text-align:left; padding-left:5px">
									 <input type="button" onclick="btnSearch_onclick()" style="color:#464646;border:0px;BACKGROUND-IMAGE:url(../images/5.jpg);width:57px;height:25px;margin-top:3px;cursor: pointer;" align="absmiddle" />
				   					 <input type="hidden" id="postbackEvent_search" name="postbackEvent" value="search"/>	   					 
				   					 <input type="hidden" id="searchType" name="searchType" value="normal"/> 
					     		</td>
					     	</tr>
					     </table>
					</td>
				</tr>
			    <tr class="conditionRow" height="5px"><td colspan="3"></td></tr>
			</table>
		  </form>
		  <div>	    	
	  		<div id="patientNormalList" name="patientList">
	  			<div id='LS_patientNormalList' class='loadingScreen'><div class='loadingMessage'>数据加载中，请稍候...</div></div>
	  			<div id="patientNormalListContent" name="patientListContent" />
	  		</div>
	  		<div id="patientAdvanceList" name="patientList" style="display:none;">
	  			<div id='LS_patientAdvanceList' class='loadingScreen'><div class='loadingMessage'>数据加载中，请稍候...</div></div>
	  			<div id="patientAdvanceListContent" name="patientListContent" />
	  		</div>
		  </div>
      </div>
<%-- 	</c:when>
	<c:otherwise>
		<div id="pl_tabs4" style="margin-left:1px;margin-right:1px;">
		  <form id="conditionFormPatient" name="conditionFormPatient" method="post" action="../patient/list_patient.html?flag=${loginFlag}">
			<table cellpadding="2" cellspacing="0" border="0" class="blockTable" >
				<tr class="conditionRow">
					<td style="vertical-align: top;">
						<table class="blockTable" style="width:100%" cellpadding="2" cellspacing="0" border="0" id="tb_patientList">
							<tr class="conditionRow" height="29">
								<td width="60" align="right" style="padding-right: 2px;">就诊号</td>
								<td width="200" align="left" style="padding-left: 2px;">
									<select id="patientDomainStr" name="patientDomainStr" style="width:68px;">
										 <option value="${Constants.OPTION_SELECT}">请选择</option>
							                <!--
							                $Author:wu_jianfeng
							                $Date:2012/9/13 10:42
							                $[BUG]BUG0009696 MODIFY BEGIN
											-->
										 <option value="${Constants.PATIENT_DOMAIN_OUTPATIENT},${Constants.PATIENT_DOMAIN_PHYSICAL_EXAM}">门诊号</option>
										 <option value="${Constants.PATIENT_DOMAIN_INPATIENT}">住院号</option>
										 <option value="${Constants.PATIENT_DOMAIN_IMAGE}">影像号</option>
							                <!-- $[BUG]BUG0009696 MODIFY BEGIN -->
									</select>
							         <input type="text" id="visitNo" name="visitNo" style="width: 105px;" 
										onmouseover="this.style.background='#FDE8FE';" onmouseout="this.style.background='#FFFFFF'" value="${patientListSearchPra.visitNo}"/>
								</td>
								<td width="60" align="right" style="padding-right: 2px;">姓名</td>						
								<td width="110" align="left" style="padding-left: 2px;">
									<input type="text" id="patientName" name="patientName" style="width: 96px;" onmouseover="this.style.background='#FDE8FE';"
											onmouseout="this.style.background='#FFFFFF'" value="${patientListSearchPra.patientName}"/>
								</td>
								<td width="50" align="right" style="padding-right: 2px;">性别</td>
								<td width="90" align="left" style="padding-left: 2px;">
									<select id="gender" name="gender" style="width:90px;">
										 <option value="${Constants.OPTION_SELECT}">请选择</option>
									   <html:options domain="${Constants.DOMAIN_GENDER}" value='${patientListSearchPra.genderCode}' />
									</select>
								</td>
							</tr>
							<tr class="conditionRow" height="29">
								<td align="right" style="padding-right: 2px;">就诊日期</td>
								<td align="left" style="padding-left: 2px;">
									 <input type="text" id="visitStartDate" name="visitStartDate" style="width: 74px;" onmouseover="this.style.background='#FDE8FE';"
										onmouseout="this.style.background='#FFFFFF'"  class="datepicker" value="${patientListSearchPra.visitStartDate}" />&nbsp;至&nbsp;
									 <input type="text" id="visitEndDate" name="visitEndDate" style="width: 74px;" onmouseover="this.style.background='#FDE8FE';"
										onmouseout="this.style.background='#FFFFFF'" class="datepicker" value="${patientListSearchPra.visitEndDate}" />
								</td>
								<td align="right" style="padding-right: 2px;">就诊类型</td>
								<td align="left" style="padding-left: 2px;">
									<select id="visitTypeStr" name="visitTypeStr" style="width:100px;">
										 <option value="${Constants.OPTION_SELECT}">请选择</option>
									     <html:options domain="${Constants.DOMAIN_VISIT_TYPE}" value='${patientListSearchPra.visitTypeStr}' />
									</select>
								</td>
								<td align="right" style="padding-right: 2px; width: 50px;">年龄段</td>
								<td align="left" style="padding-left: 2px;">
									<select id="baseAge" name="baseAge" style="width:90px;">
										<option value="${Constants.OPTION_SELECT}">请选择</option>
									   	<html:options domain="${Constants.AGE_GROUP}" value='${patientListSearchPra.baseAge}' />
									</select>
								</td>
							</tr>
							<tr class="conditionRow" height="29">
								<td align="right" style="padding-right: 2px;">科室</td>
								<td align="left" style="padding-left: 2px;">
								<div class="selectdiv">
									<select id="deptName" class="deptSelect_ser" name="deptName"
										style="width: 250px;">
										<option value="${Constants.OPTION_SELECT}">请选择</option>
										<c:choose>
											<c:when test="${useACL && aclAuths.patientScopeAuth05}">
												<html2:pycodeoptions
												domain="${Constants.DOMAIN_DEPARTMENT}"/>
											</c:when>
											<c:when test="${(useACL && aclAuths.patientScopeAuth01) || (useACL && aclAuths.patientScopeAuth02) || (useACL && aclAuths.patientScopeAuth03) || (useACL && aclAuths.patientScopeAuth06)}">
												<html2:pycodeoptions
												deptIds="${deptIds}"
												domain="MS025_LIMIT"/>
											</c:when>
											<c:otherwise>
												<c:if test="${!useACL}">
													<html2:pycodeoptions
													domain="${Constants.DOMAIN_DEPARTMENT}"/>
												</c:if>
											</c:otherwise>
										</c:choose>
									</select>
								</div>
									 <input type="text" id="deptName" name="deptName" style="width: 177px;" onmouseover="this.style.background='#FDE8FE';"
										onmouseout="this.style.background='#FFFFFF'" value="${patientListSearchPra.deptName}"/>
								</td>
								<td align="right" style="padding-right: 2px;">病区</td>
								<td align="left" style="padding-left: 2px;">
								<div class="selectdiv2">
									<select id="wardName" class="wardSelect_ser" name="wardName"
										style="width: 250px;">
										<option value="${Constants.OPTION_SELECT}">请选择</option>
										<html2:pycodeoptions
											domain="${Constants.DOMAIN_WARD}"/>
									</select>
								</div>
									<input type="text" id="wardName" name="wardName" style="width: 90px;" onmouseover="this.style.background='#FDE8FE';"
										onmouseout="this.style.background='#FFFFFF'" value="${patientListSearchPra.wardName}"/>
								</td>
								<td align="right" style="padding-right: 2px; width: 50px;">床号</td>
								<td align="left" style="padding-left: 2px;">
									<input type="text" id="bedNo" name="bedNo" style="width: 86px;" onmouseover="this.style.background='#FDE8FE';"
										onmouseout="this.style.background='#FFFFFF'" value="${patientListSearchPra.bedNo}"/>
								</td>
							</tr>
							<tr class="conditionRow" height="29">
								<td>
								<div align="right" style="padding-right: 2px; width: 60px;">门诊/住院医生</div>
								</td>
								<td align="left" style="padding-left: 2px;">
								<div class="selectdiv3">
									<select id="staff" name="staff" class="staffSelect_ser" style="width:181px;">
										<option value="${Constants.OPTION_SELECT}">请选择</option>
										<html2:pycodeoptions
											domain="${Constants.DOMAIN_STAFF}"/>
									</select>
								</div>
								</td>	
								<!-- $Author :chang_xuewen
			 					 $Date : 2013/12/16 10:00$
								 [BUG]0040770 MODIFY BEGIN -->
							<c:if test="${Constants.TRUE_ORG_CODE == Constants.DISABLE_ORG_CODE}">
								<td>
								<div align="right" style="padding-right: 2px; width: 60px;">医疗机构</div>
								</td>
								<td colspan="2">
								<div align="left" style="float:left;margin-top:4px;width: 171px;padding-left: 2px;">
									<select id="orgCodeFlag" name="orgCodeFlag" style="width:171px;">
										<option value="${Constants.OPTION_SELECT}">请选择</option>
										<html:options domain="${Constants.DOMAIN_ORG_CODE}"
										value='${orgCode}' />						
									</select>
								</div>
								</td>								
							</c:if>
			                <!-- [BUG]0040770 MODIFY END -->		
								<td>&nbsp;</td>
							</tr>
							<tr class="conditionRow" height="29">
								<td align="right" style="padding-right: 2px;">疾病名称</td>
								<td align="left" style="padding-left: 2px;"  colspan="5">
									 <input type="text" id="diseaseName" name="diseaseName" style="width: 177px;" onmouseover="this.style.background='#FDE8FE';"
										onmouseout="this.style.background='#FFFFFF'" value="${patientListSearchPra.diseaseName}"/>
								</td>
							</tr>
						</table>
						<table class="blockTable" id="tbPatientListAS" style="width: 100%;display:none;" cellspacing="0" cellpadding="2"
							border="0">
							<tr class="tabletitle" id="title">
								<td height="28" align="center"></td>
								<td height="28" align="center">且&nbsp;/&nbsp;或</td>
								<td height="28" align="center">字段名称</td>
								<td height="28" align="center">条件</td>
								<td height="28" align="center">字段值</td>
								<td height="28" align="center"></td>
							</tr>
							<tr class="odd" id="new">
								<td height="24" align="left" width="60px" style="padding-left:2px;"nowrap>选择字段</td>
								<td height="24" align="center" width="75px">
									<select id="t_control" style="width:70px;display:none;" class="control">
										<option value="-1" selected>请选择</option>
										<option value="and">and</option>
										<option value="or">or</option>
									</select>
								</td>
								<td height="24" width="110px" style="text-align:left;">
									<select id="t_field" style="width:100px" class="field">
										<option value="-1" selected>请选择</option>
										<option value="patientName">患者姓名</option>
										<option value="patientGender">患者性别</option>
										<option value="visitNo">就诊号</option>
										<option value="visitType">就诊类型</option>
										<option value="visitDate">就诊日期</option>
										<option value="baseAge">年龄段</option>
										<option value="diseaseName">疾病名称</option>
										<option value="labItemResult">检验项目结果</option>
										<!--<option value="visitDept">就诊科室</option>-->
									</select>
								</td>
								<td height="24" align="center" width="80px" >
									<select id="t_opt" style="width:80px;display:none;" class="opt">
										<option value="-1" selected>请选择</option>
										<option value="equal">等于</option>
										<option value="notEqual">不等于</option>
										<option value="like">类似于</option>
										<option value="more">大于</option>
										<option value="less">小于</option>
										<option value="moreEq">大于等于</option>
										<option value="LessEq">小于等于</option>
									</select>
								</td>
								<td height="24" align="center" width="150px">
									<input id="t_inputValue" value="" type="textbox" class="inputValue" style="width:120px;display:none;" />
									<select id="t_selectValue" class="selectValue" style="width:125px;display:none;"></select>
									<input type="text" id="t_inputDate" onmouseover="this.style.background='#FDE8FE';"
										onmouseout="this.style.background='#FFFFFF'"  class="datepickerOther" value="" style="width:120px;display:none;" />						
								</td>
								<td height="24" align="center" width="30px">
									<a id="t_addbtn" class="addbtn" href="javascript:void(0)" onclick="addSearchTableRow();"><img src="../images/button_addsearch.png" alt="增加条件" title="增加条件" style="border:0;"/></a> 
									<a id="t_deletebtn" class="deletebtn"  style="display:none;" href="javascript:void(0)" onclick="deleteSearchTableRow(this);"><img src="../images/button_deletesearch.png" alt="删除条件" title="删除条件" style="border:0;"/></a> 
								</td>
							</tr>
						</table>
					</td>
					<td style="vertical-align: top;" width="95px">
					     <table cellpadding="5" cellspacing="0"  width="100%">
					     <!-- $Author :chang_xuewen
						      $Date : 2013/11/07 11:00
						      $[BUG]0039036 DELETE BEGIN -->					     
					     	<!-- <tr>
					     		<td style="text-align:left; padding-left:5px">
								     <input type="button" id="btnSearchType"
										style="color: #464646; border: 0px; BACKGROUND-IMAGE: url(../images/6.jpg); width: 77px; height: 25px; margin-top: 3px;cursor: pointer;"
										onclick="btnSearchType_onclick()" align="absmiddle" />
					     		</td>
					     	</tr> -->
					     	<!-- $[BUG]0039036 DELETE END -->	
					     	<tr>
					     		<td style="text-align:left; padding-left:5px">
									 <input type="button" onclick="btnSearch_onclick()" style="color:#464646;border:0px;BACKGROUND-IMAGE:url(../images/5.jpg);width:57px;height:25px;margin-top:3px;cursor: pointer;" align="absmiddle" />
				   					 <input type="hidden" id="postbackEvent_search" name="postbackEvent" value="search"/>	   					 
				   					 <input type="hidden" id="searchType" name="searchType" value="normal"/> 
					     		</td>
					     	</tr>
					     </table>
					</td>
				</tr>
			    <tr class="conditionRow" height="5px"><td colspan="3"></td></tr>
			</table>
		  </form>
		  <div>	    	
	  		<div id="patientNormalList" name="patientList">
	  			<div id='LS_patientNormalList' class='loadingScreen'><div class='loadingMessage'>数据加载中，请稍候...</div></div>
	  			<div id="patientNormalListContent" name="patientListContent" />
	  		</div>
	  		<div id="patientAdvanceList" name="patientList" style="display:none;">
	  			<div id='LS_patientAdvanceList' class='loadingScreen'><div class='loadingMessage'>数据加载中，请稍候...</div></div>
	  			<div id="patientAdvanceListContent" name="patientListContent" />
	  		</div>
		  </div>
      </div>
	</c:otherwise>
</c:choose>
			<div style="display:none">
				<select id="plas_patientDomain" name="plas_patientDomain" style="width:98%;">
					 <option value="${Constants.OPTION_SELECT}">请选择</option>
					 <option value="${Constants.PATIENT_DOMAIN_OUTPATIENT},${Constants.PATIENT_DOMAIN_PHYSICAL_EXAM}">门诊号</option>
					 <option value="${Constants.PATIENT_DOMAIN_INPATIENT}">住院号</option>
					 <option value="${Constants.PATIENT_DOMAIN_IMAGE}">影像号</option>
				</select>
				<select id="plas_visitType" name="plas_visitType" style="width:98%;">
					 <option value="${Constants.OPTION_SELECT}">请选择</option>
				     <html:options domain="${Constants.DOMAIN_VISIT_TYPE}" value='' />
				</select>
				<select id="plas_gender" name="plas_gender" style="width:98%;">
					 <option value="${Constants.OPTION_SELECT}">请选择</option>
				   <html:options domain="${Constants.DOMAIN_GENDER}" value='' />
				</select>
				<select id="plas_baseAge" name="plas_baseAge" style="width:98%;">
					<option value="${Constants.OPTION_SELECT}">请选择</option>
				   	<html:options domain="${Constants.AGE_GROUP}" value='' />
				</select>
				<select id="plas_labSubItem" name="plas_labSubItem" style="width:98%;">
					<option value="${Constants.OPTION_SELECT}">请选择</option>
					<html2:pycodeoptions domain="${Constants.DOMAIN_LAB_SUBITEM}" value="" />
				</select>
				<select id="plas_visitDept" name="plas_visitDept" style="width: 140px;">
					<option value="${Constants.OPTION_SELECT}">请选择科室</option>
					<html2:pycodeoptions domain="${Constants.DOMAIN_DEPARTMENT}" value="" />
				</select>
			</div> --%>
	  
</body>

</html>