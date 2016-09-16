<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="html2.tld" prefix="html2"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="translation.tld" prefix="ref"%>
<%@ taglib uri="html.tld" prefix="html"%>
<jsp:useBean id="Constants" class="com.founder.cdr.core.Constants" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Pragma" content="no-cache" />
<!-- Prevents caching at the Proxy Server -->
<meta http-equiv="Expires" content="0" />
<title>打印</title>
<link type="text/css" rel="Stylesheet"
	href="../styles/jquery-ui-1.8.18.custom.modify.css" charset="UTF8" />
<link type="text/css" rel="Stylesheet"
	href="../styles/jquery-ui-autocomplete.custom.css" charset="UTF8" />
<link type="text/css" rel="Stylesheet"
	href="../styles/jquery-ui-combobox.custom.modify.css" charset="UTF8" />
<link type="text/css" rel="Stylesheet"
	href="../styles/layout-default-1.3.0rc29.15.css" charset="UTF8" />
<link type="text/css" rel="Stylesheet" href="../styles/layout-cdr.css"
	charset="UTF8" />
<link type="text/css" rel="Stylesheet" href="../styles/tablelist.css"
	charset="UTF8" />
<link type="text/css" rel="Stylesheet" href="../styles/timer-shaft.css"
	charset="utf-8" />
<link type="text/css" rel="Stylesheet"
	href="../styles/loadingScreen.css" charset="UTF8" />
<link type="text/css" rel="Stylesheet"
	href="../styles/jquery-suggest.css" charset="UTF8" />
<link type="text/css" rel="Stylesheet"
	href="../styles/tabs-menuPart.css" charset="UTF8" />
<link type="text/css" rel="stylesheet" href="../styles/header.css"
	charset="UTF8" />
<link rel="icon" href="../favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" type="image/x-icon" href="../favicon.ico" />
<script type="text/javascript" src="../scripts/common.js"></script>
<script type="text/javascript" src="../scripts/layout.js"></script>
<script type="text/javascript"
	src="../scripts/system/systemMenuSetting.js"></script>
<script type="text/javascript" src="../scripts/system/systemSetting.js"></script>
<script type="text/javascript">
	$(function() {
		setupDatePickerSetting();
		// 初始化带有datepicker样式的日期输入框
		setupDatePicker();
		//打印范围-患者信息默认选中
		$("#visitType_01").attr("checked", true);
		var visitType = $("#_visitType").val();
		$("input[name='rowsPrintVisitType']").each(function() {
			if ($(this).val() == visitType) {
				$(this).attr("checked", true);
			}
			/* 			if ($(this).val() == "01") {
			 $("#inpatientDischargeDateAndVisitSn").attr("disabled",true);
			 $("#inpatientStartDate").attr("disabled",true);
			 $("#inpatientEndDate").attr("disabled",true);
			 } */

		});
		showDateCondition();
		$("input[name=rowsPrintVisitType]").click(function() {
			showDateCondition();
		});
	});
	
		<%--
		$Author :yang_mingjie
	       $Date : 2014/05/30 14:23$
	       [BUG]0044724 ADD BEGIN 
		--%>
		function showDateCondition() {
			switch ($("input[name=rowsPrintVisitType]:checked").attr("id")) {
			case "rowsVisitType01":
				$("#inpatientDischargeDateAndVisitSn").attr("disabled", true);
				$("#startDate").attr("disabled", true);
				$("#endDate").attr("disabled", true);
				$("#startDate").val("");
				$("#endDate").val("");
				//$("#inpatientDischargeDateAndVisitSn").val("");
				$("#visitDate").attr("disabled", false);
				$("#visitType_09").attr("checked", false);
				$("#visitType_09").attr("disabled", true);
				$("#visitType_10").attr("checked", false);
				$("#visitType_10").attr("disabled", true);
				break;
			case "rowsVisitType02":
				$("#inpatientDischargeDateAndVisitSn").attr("disabled", false);
				$("#startDate").attr("disabled", false);
				$("#endDate").attr("disabled", false);
				$("#visitDate").attr("disabled", true);
				$("#visitDate").val("");
				$("#startDate").val("");
				$("#endDate").val("");
				$("#visitType_09").attr("disabled", false);
				$("#visitType_10").attr("disabled", false);
				break;
			default:
				break;
			}
	}
</script>

</head>
<body style="margin: 0; padding: 0;" bgcolor="#FFFFFF">
	<div id="dialogg" class="">
		<div id="main_Content">
			<form id="printForm" name="printForm" method="post"
				action="">
				<div style="width: 100%;">
					<table  style="width: 100%;" class="blockTable"
						cellspacing="0" cellpadding="0" border="0">
						<tr class="">
							<td width="50%" align="left" valign="middle"
								style="border: solid 1px #D8D9DB;">
								<fieldset style="height: 140px; width:98%;border: 2px #fff groove">
									<legend>打印条件</legend>
									<table style="width: 100%;" cellspacing="0" cellpadding="0"
										border="0">
										<c:if test="${Constants.TRUE_ORG_CODE == Constants.DISABLE_ORG_CODE}">
											<tr>
												<td align="right"
													style="padding-top: 2px; padding-right: 2px;">医疗机构：</td>
												<td align="left" style="padding-top: 2px; padding-left: 2px;"><select
													id="org_Code" name="org_Code" style="width: 205px;"
													>
														<option value="${Constants.OPTION_SELECT}">请选择</option>
														<html:options domain="${Constants.DOMAIN_ORG_CODE}"
															value='${orgCode}' />
												</select></td>
											</tr>
										</c:if>
										<tr>
											<td align="right"
												style="padding-top: 2px; padding-right: 2px;">就诊类型：</td>
											<td align="left" style="padding-top: 2px; padding-left: 2px;">
											<input type="hidden" id="_visitType" name="_visitType" value="${visitType}"></input> 
											<input type="radio" id="rowsVisitType01" name="rowsPrintVisitType" value="01" />
											门诊&nbsp;&nbsp;
											<input type="radio" id="rowsVisitType02" name="rowsPrintVisitType" value="03" />
											住院&nbsp;&nbsp;</td>
										</tr>
										<tr>
											<td align="right"
												style="padding-top: 2px; padding-right: 2px;">门诊日期：</td>
											<td align="left" style="padding-top: 2px; padding-left: 2px;">
												<select id="visitDate" name="visitDate"
												style="width: 205px;">
													<option value="${Constants.OPTION_SELECT}">请选择</option>
													<c:forEach items="${visitDateList}" var="vdl">
														<option value="${vdl.visitSn};${vdl.visitDate}"
															<c:if test="${vdl.visitSn == visitDateSn}">selected</c:if>>${vdl.visitDateDept}</option>
													</c:forEach>
											</select>
											</td>
										</tr>
										<tr>
											<td align="right"
												style="padding-top: 2px; padding-right: 2px;">住院日期：</td>
											<td align="left" style="padding-top: 2px; padding-left: 2px;">
												<select id="inpatientDischargeDateAndVisitSn"
												name="inpatientDischargeDateAndVisitSn" class="zytab_1"
												style="width: 205px;"
												<c:choose>
															<c:when test="${(orgCode == null || fn:length(orgCode) == 0) && (Constants.TRUE_ORG_CODE == Constants.DISABLE_ORG_CODE)}">
																style="width: 160px;"
															</c:when>
															<c:otherwise>
																style="width: 160px;"
															</c:otherwise>
														</c:choose>>
													<option value="${Constants.OPTION_SELECT}">请选择</option>
													<c:forEach items="${visitTimesEtcList}" var="vts">
														<option
															value="<fmt:formatDate value='${vts.dischargeDate}' type='date' pattern='yyyy-MM-dd' />;${vts.visitSn};<fmt:formatDate value='${vts.visitDate}' type='date' pattern='yyyy-MM-dd' />"
															<c:if test="${Constants.TRUE_ORG_CODE == Constants.DISABLE_ORG_CODE}">title="<ref:translate domain='${Constants.DOMAIN_ORG_CODE}' code='${vts.orgCode}'/>"</c:if>
															<c:if test="${vts.visitSn == vstSn}">selected</c:if>>
															<fmt:formatDate value="${vts.visitDate}" type="date"
																pattern="yyyy-MM-dd" />
															-
															<c:if test="${vts.dischargeDate == null}">至今</c:if>
															<fmt:formatDate value="${vts.dischargeDate}" type="date"
																pattern="yyyy-MM-dd" />
														</option>
													</c:forEach>
											</select>
											</td>
										</tr>
										<tr>
											<td align="right"
												style="padding-top: 2px; padding-right: 2px;">选择日期：</td>
											<td align="left" style="padding-top: 2px; padding-left: 2px;">
												<input type="text" id="startDate"
												name="startDate" style="width: 80px;"
												onmouseover="this.style.background='#FDE8FE';"
												onmouseout="this.style.background='#FFFFFF'"
												class="datepicker"
												value="${fn:replace(inpatientStartDate, '/', '-')}" />&nbsp;至&nbsp;
												<input type="text" id="endDate"
												name="endDate" style="width: 80px;"
												onmouseover="this.style.background='#FDE8FE';"
												onmouseout="this.style.background='#FFFFFF'"
												class="datepicker"
												value="${fn:replace(inpatientEndDate, '/', '-')}" />
											</td>
										</tr>
									</table>
								</fieldset>
							</td>
							<td width="50%" align="left" valign="middle"
								style="border: solid 1px #D8D9DB;">
								<fieldset style="height: 140px;width:98%; border: 2px #fff groove">
									<legend>打印范围</legend>
									<table style="width: 100%;" cellspacing="0" cellpadding="0"
										border="0">
										<tr>
											<td align="left" width='35%'>
												<div style="float: left; width: 100%">
													&nbsp;&nbsp;
													<input type="checkbox" id="visitType_01"
														name="rowVisitType" value="2_01" onfocus="this.blur()" />患者基本信息
												</div>
											</td>
											<td align="left">
												<div style="float: left; width: 100%">
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													<input type="checkbox" id="visitType_02"
														name="rowVisitType" value="2_02" onfocus="this.blur()" />就诊
												</div>
											</td>
										</tr>
										<tr>
											<td>
												<div style="float: left; width: 100%">
												&nbsp;&nbsp;
													<input type="checkbox" id="visitType_03"
														name="rowVisitType" value="2_03" onfocus="this.blur()" />过敏/不良反应
												</div>
											</td>
											<td>
												<div style="float: left; width: 100%">
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													<input type="checkbox" id="visitType_04"
														name="rowVisitType" value="2_04" onfocus="this.blur()" />诊断
												</div>
											</td>
										</tr>
										<tr>
											<td>
												<div style="float: left; width: 100%">
												&nbsp;&nbsp;
													<input type="checkbox" id="visitType_05"
														name="rowVisitType" value="2_05" onfocus="this.blur()" />医嘱
												</div>
											</td>
											<td>
												<div style="float: left; width: 100%">
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													<input type="checkbox" id="visitType_06"
														name="rowVisitType" value="2_06" onfocus="this.blur()" />检查
												</div>
											</td>
										</tr>
										<tr>
											<td>
												<div style="float: left; width: 100%">
												&nbsp;&nbsp;
													<input type="checkbox" id="visitType_07"
														name="rowVisitType" value="2_07" onfocus="this.blur()" />检验
												</div>
											</td>
											<td>
												<div style="float: left; width: 100%">
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													<input type="checkbox" id="visitType_08"
														name="rowVisitType" value="2_08" onfocus="this.blur()" />手术
												</div>
											</td>
										</tr>
										<tr>
											<td>
												<div style="float: left; width: 100%">
												&nbsp;&nbsp;
													<input type="checkbox" id="visitType_09"
														name="rowVisitType" value="2_09" onfocus="this.blur()" />病案首页
												</div>
											</td>
											<td>
												<div style="float: left; width: 100%">
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													<input type="checkbox" id="visitType_10"
														name="rowVisitType" value="2_10" onfocus="this.blur()" />入院记录
												</div>
											</td>
										</tr>
									</table>
								</fieldset>
							</td>
						</tr>
					</table>
				</div>
				<br />
				<div style="width: 100%;">
					<table style="width: 100%;" cellspacing="0"
						cellpadding="0" border="0">
						<tr class="">
							<td align="center" valign="middle" style="border: 0px;"
								height="10"><input type="button" value=""
								style="border: 0px; BACKGROUND-IMAGE: url(../images/button_print.png); width: 51px; height: 23px; cursor: pointer;"
								onfocus="this.blur()" onclick="print()" />&nbsp;&nbsp; <input
								type="button" onfocus="this.blur()" onclick="closeDialog()"
								style="border: 0px; BACKGROUND-IMAGE: url(../images/qx.jpg); width: 51px; height: 23px; cursor: pointer;" />
							</td>
						</tr>
					</table>
				</div>
			</form>
		</div>
	</div>
	<script type="text/javascript">
		$("#visitDate").change(function(){
		    //获取门诊日期select 选中的 value :
		 	var visitDateSn = $("#visitDate").val();
			var array = visitDateSn.split(";");
			visit_sn = array[0];
			visit_date = array[1];
			start_date=visit_date;
			end_date=visit_date;
			$("#startDate").val(start_date);
			$("#endDate").val(end_date);		
		});
		function print() {
			//alert("#orgCode:"+$("#orgCode").val());
			if($("#org_Code").val() == "" || $("#org_Code").val() == -1){
				showMsg("提示", "请选择医疗机构！");
				return false;
			}
			
			var inpatientStartDate = $("#startDate").val();
			var inpatientEndDate = $("#endDate").val();
			var visitTypeChecked = $("input[name=rowsPrintVisitType]:checked").val();
			if (visitTypeChecked == "01" ){
				if($("#visitDate").val()==-1){
					showMsg("提示", "请选择门诊日期！");
					return false;					
				}
			}
			
			if (visitTypeChecked == "03" ){
				if($("#inpatientDischargeDateAndVisitSn").val()==-1){
					showMsg("提示", "请选择住院日期！");
					return false;					
				}
				if(inpatientEndDate != ""
					&& inpatientStartDate > inpatientEndDate) {
				showMsg("提示", "选择日期  开始时间不能>结束时间！");
				return false;
				}
			}
			var visitTypeChecked = $("input[name=rowVisitType]:checked").val();
			if (visitTypeChecked == undefined || visitTypeChecked == "") {
				showMsg("提示", "打印范围至少选中一项！");
				return false;
			}
			var visitType = $("input[name=rowsPrintVisitType]:checked").val();
			var visit_sn;//就诊序号
			var visit_date;//就诊日期
			var start_date;//住院开始日期
			var end_date;//住院结束日期
			var org_code = $("#org_Code").val();//机构代码
			//alert("org_code:::::"+orgCode);
			var patientInfo = $("#visitType_01").is(":checked")?1:0;//患者信息
			var visit = $("#visitType_02").is(":checked")?1:0;//就诊
			var allergic = $("#visitType_03").is(":checked")?1:0;//过敏/不良反应
			var diagnosis = $("#visitType_04").is(":checked")?1:0;//诊断
			var order = $("#visitType_05").is(":checked")?1:0;//医嘱
			var examine = $("#visitType_06").is(":checked")?1:0;//检查
			var check = $("#visitType_07").is(":checked")?1:0;//检验
			var surgical = $("#visitType_08").is(":checked")?1:0;//手术
			var medicalindex = $("#visitType_09").is(":checked")?1:0;//病历首页
			var inpatient = $("#visitType_10").is(":checked")?1:0;//入院记录
			if (visitType == "01") {
				var visitDateSn = $("#visitDate").val();
 				var array = visitDateSn.split(";");
				visit_sn = array[0];
				visit_date = array[1]; 
				//alert(visit_date.substring(0,10));
				if(visit_date != "" && visit_date.length >= 10){
					start_date = end_date = visit_date.substring(0,10);
				}
				//alert("start_date:"+start_date);
				//alert("end_date:"+end_date);
			}
			if (visitType == "03") {
				var inpatientStartDate = $("#startDate").val();
				var inpatientEndDate = $("#endDate").val();
				var visitDateSn = $("#inpatientDischargeDateAndVisitSn").val();
				var array = visitDateSn.split(";");
				visit_sn = array[1];
				end_date = array[0];
				start_date = array[2];
				if(end_date == "" && start_date != ""){
					end_date = getFormatDate();
				}
				if(inpatientStartDate != "" || inpatientEndDate != ""){
					start_date = inpatientStartDate;
					end_date = inpatientEndDate;
				}
			}

			var user_name = '${userSn}';
			var biosUrl = "../ReportEmitter?rpt=cdr-report.brt";
			biosUrl += '&emitter=topdf&fitwidth=true&params=patient_sn='+${patientSn}+';user_name='+user_name+';visit_sn='+visit_sn+';org_code='+org_code
			+';start_date='+start_date+';end_date='+end_date+';isPatientInfo='+patientInfo+';isVisit='+visit+';isAllergic='+allergic
			+';isDiagnosis='+diagnosis+';isOrder='+order+';isExamine='+examine+';isCheck='+check+';isSurgical='+surgical+';isMedicalindex='+medicalindex
			+';isInpatient='+inpatient+';vars=null;brf=false';
		//	window.location.href = biosUrl;
		//	alert(biosUrl);
			$("#printForm").attr("action",biosUrl);
			$("#printForm").submit();
			
			//获得窗口的垂直位置
/*   			var iTop = (window.screen.availHeight - 30 - 600) / 2;
			//获得窗口的水平位置
			var iLeft = (window.screen.availWidth - 10 - 800) / 2;
			window.open(
					biosUrl,
					'',
					'height=600,width=700,top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no, status=no');
	  */	}
		
		function getFormatDate()
		{
		   var day=new Date();
		   var Year=0;
		   var Month=0;
		   var Day=0;
		   var Hour = 0;
		   var Minute = 0;
		   var Second = 0;
		   var CurrentDate="";
		   //初始化时间
		   Year       = day.getFullYear();
		   Month      = day.getMonth()+1;
		   Day        = day.getDate();
		  // Hour       = day.getHours();
		 //  Minute     = day.getMinutes();
		  // Second     = day.getSeconds();
		  
		   CurrentDate = Year + "-";
		   if (Month >= 10 )
		   {
		    CurrentDate = CurrentDate + Month + "-";
		   }
		   else
		   {
		    CurrentDate = CurrentDate + "0" + Month + "-";
		   }
		   if (Day >= 10 )
		   {
		    CurrentDate = CurrentDate + Day ;
		   }
		   else
		   {
		    CurrentDate = CurrentDate + "0" + Day ;
		   }
		  
/* 		   if(Hour >=10)
		   {
		    CurrentDate = CurrentDate +" "+ Hour ;
		   }
		   else
		   {
		    CurrentDate = "0" + Hour ;
		   }
		   if(Minute >=10)
		   {
		    CurrentDate = CurrentDate + ":" + Minute ;
		   }
		   else
		   {
		    CurrentDate = CurrentDate + ":0" + Minute ;
		   }     
		   if(Second>=10)
		   {
		    CurrentDate = CurrentDate + ":" + Second;
		   }
		   else
		   {
		    CurrentDate = CurrentDate + ":0" + Second;
		   } */
		   return CurrentDate;
		}
	</script>
</body>

</html>