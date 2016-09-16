<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="translation.tld" prefix="ref"%>
<jsp:useBean id="Constants" class="com.founder.cdr.core.Constants" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>会诊详细信息</title>
	<meta http-equiv="Pragma" content="no-cache" />
	<meta http-equiv="Cache-Control" content="no-cache" />
	<meta http-equiv="Expires" content="0" />
	<style type="text/css">
		.doclabel1
		{
		   font-size: 12px;
		   text-align: center;
		   padding-right: 3px;
		   heignt:30px;
		   width:13%;
		   background-color: #D5EAFF;
		   border-bottom:solid 1px #c0ddea;
		   color: #043a8f;
		}
		.docdataitem1
		{
		   font-size: 12px;
		   font-weight: bold;
		   color: #0000FF;
		   padding-left: 5px;
		   text-align: center;
		   heignt:30px;
		   width:20%;
		   background-color: #FFFFFF;
		   border-bottom:solid 1px #c0ddea;
		   text-align: left;
		}
	</style>
	<script type="text/javascript" src="../scripts/cpoe/raphael.js"></script>
	<script type="text/javascript" src="../scripts/cpoe/raphael_common.js"></script>
	<script type="text/javascript" src="../scripts/cpoe/jquery.cpoe.js"></script>
	<script type="text/javascript" src="../scripts/cpoe/popup.js"></script>
	<script type="text/javascript" src="../scripts/cpoe/cpoe.js"></script>
	<script>
	
		function returnsn(){
			return '${requestNo}';
		}
		function documentSnJson(){
			return '${documentSnJson}';
		}
 		$(function(){
			$("#tabs").tabs();
			var reportSn = returnsn();
			//alert("requestNo:"+reportSn);
			var commonSn ;
			commonSn = reportSn;
			closeTab("#moreTabs", reportSn);
			$("#_"+commonSn).tabs('select', 'tabs-1');
			var documentSnList = documentSnJson();
			documentSnList = unescape(documentSnList);//解析双引号、单引号  
			//alert("documentSnList:"+documentSnList);
			var consultationRecordId = $("#_"+commonSn).siblings()[0];
		    if(documentSnList == "" || documentSnList == null){
		    	//alert("23");
		    	$(consultationRecordId).children().children().children()[2].style.display="";
		    	return ;
		    }
		    var json = eval('('+documentSnList+')');//json格式字符串转为json数据类型  
		    for(var i=0;i<json.length;i++){  
		        var documentSn = json[i].documentSn;
		        //alert("documentSn::"+documentSn);
				var id="consultationRecord_"+commonSn+i;
				//alert("id:"+id);
				var viewContainer = "<div id="+id+" style='word-break:break-all;word-wrap:break-word;border:0px;'> </div>";
				$(consultationRecordId).append(viewContainer);
				jQuery("#"+id).load("../consultation/recordDetail.html?documentSn="+documentSn, function(response, status, xhr){
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
			
		});  
		
	</script>
</head>
<body>
	<div id="dialog">
		<div id="mainContent">
			<div style="word-break:break-all;word-wrap:break-word;border:0px;" 
				id="_${requestNo}" 
				name="selectTabs">
				<ul>
				    <li><div class="tabseperator">&nbsp;&nbsp;</div></li>
						<li  class="headtitle"><a href="#tabs-1" class="lj" hidefocus="true">会诊记录</a></li>
				</ul>
				<c:if test="${!empty consultationRequest}">
					<div id="tabs-1" class="tabcontainer">
						<table width="100%" cellpadding="2" cellspacing="1" style="border: solid 1px #c0ddea;border-collapse:collapse;border-bottom:0px;">
						    <tr>
						    	<td class="doclabel">姓名:</td>
								<td class="docdataitem">${patient.patientName}</td>
								<td class="doclabel">性别:</td>
								<td class="docdataitem">${patient.genderName}</td>
							</tr>
							<tr>
								<td class="doclabel">出生日期:</td>
								<td class="docdataitem"><fmt:formatDate type="date"
												value="${patient.birthDate}" pattern="yyyy-MM-dd" /></td>
								<td class="doclabel">年龄:</td>
								<td class="docdataitem">${age}</td>
							</tr>
							<c:if test="${Constants.TRUE_ORG_CODE == Constants.DISABLE_ORG_CODE}">
								<tr>
									<td class="doclabel">医疗机构名称:</td>
									<td class="docdataitem">${orgName}</td>
									<td class="label"></td>
									<td class="docdataitem"></td>
								</tr>
							</c:if>
						</table>
						<table width="100%" cellpadding="2" cellspacing="1" style="border: solid 1px #c0ddea;border-collapse:collapse;border-bottom:0px;">
							<tr>
								<td class="blockHeader" colspan="4" height="27" align="left"
									style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">
									会诊申请单信息</td>
							</tr>
							<tr class="odd">
								<td class="doclabel">申请单编号:</td>
								<td class="docdataitem">${consultationRequest.requestNo}</td>
								<td class="doclabel">申请类型:</td>
								<td class="docdataitem">${consultationRequest.consultationTypeName}</td>
							</tr>
							<tr>
								<td class="doclabel">医嘱类别名称:</td>
								<td class="docdataitem">${consultationRequest.orderName}</td>
								<td class="doclabel">申请日期:</td>
								<td class="docdataitem"><fmt:formatDate value="${consultationRequest.requestTime}" type="date" pattern="yyyy-MM-dd" /></td>
							</tr>
							<tr>
								<td class="doclabel">申请人名称:</td>
								<td class="docdataitem">${consultationRequest.requestDoctorName}</td>
								<td class="doclabel">申请科室名称:</td>
								<td class="docdataitem">${consultationRequest.orderDeptName}</td>
							</tr>
							<tr>
								<td class="doclabel">会诊地点:</td>
								<td class="docdataitem">${consultationRequest.consultationPlace}</td>
								<td class="doclabel">会诊类型:</td>
								<td class="docdataitem">${consultationRequest.urgencyName}</td>
							</tr>
							<tr>
								<td class="doclabel">会诊状态:</td>
								<td class="docdataitem">${consultationRequest.requestStatusName}</td>
								<td class="doclabel"></td>
								<td class="docdataitem"></td>
							</tr>							
							<tr>
								<td class="doclabel">申请理由及目的:</td>
								<td class="docdataitem" colspan="3">${consultationRequest.reason}</td>
							</tr>
						</table>						
					</div>
				</c:if>
				<c:if test="${fn:length(consultationDeptList)!=0}">
					<div id="tabs-3" class="tabcontainer">
						<table cellpadding="2" cellspacing="1" class="table" width="100%"
							align="center">
							<tr>
								<td class="blockHeader" colspan="3" height="27" align="left"
									style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">
									应邀科室信息</td>
							</tr>
							<tr class="doclabel1">
								<td height="28" align="center">所属医院</td>
								<td height="28" align="center">被邀科室</td>
								<td height="28" align="center">被邀医生</td>
							</tr>
							<c:if test="${fn:length(consultationDeptList)==0}">
								<tr>
									<td colspan="3" align="center" class="odd" height="24">没有相关数据！</td>
								</tr>
							</c:if>
							<c:forEach items="${consultationDeptList}" var="consultationDeptList"
								varStatus="status">
								<tr height="24"
								    		 <c:if test="${status.count%2==1 }">class="odd"</c:if>
								    		 <c:if test="${status.count%2==0 }">class="even"</c:if>
								    >
									<td height="24" align="center">${consultationDeptList.consultationOrgName}</td>
									<td height="24" align="center">${consultationDeptList.consultationDeptName}</td>
									<td height="24" align="center">${consultationDeptList.consultationPersonName}</td>
								</tr>
							</c:forEach>
						</table>
					</div>
				</c:if>
			</div>
 			<div name="consultationRecord" style="word-break:break-all;word-wrap:break-word;border:0px;">
					<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;border-bottom: solid 0px #B3C4D4;bgcolor:white;">
							<tr>
								<td class="blockHeader" colspan="9" height="27" align="left"
									style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 0px #B3C4D4; font-weight: bold;">会诊记录信息</td>
							</tr>
							<tr>
								<td colspan="9" height="30" align="center" style="background-color:#fcffe2;border-bottom: solid 1px #B3C4D4;"><div class="responsability">${Constants.RESPONSABILITY_CLAIMING_CONTENT}</div></td>
							</tr>
							<tr style="display:none">
								<td colspan="9" style="text-align:center;height:30px;" class="blws">没有相关内容！</td>
							</tr>
					</table>
			</div>
		</div>
	</div>
</body>
</html>
									