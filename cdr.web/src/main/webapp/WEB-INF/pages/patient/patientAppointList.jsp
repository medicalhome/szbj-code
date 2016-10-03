<%@ page language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="translation.tld" prefix="ref"%>
<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="html2.tld" prefix="html2"%>
<jsp:useBean id="Constants" class="com.yly.cdr.core.Constants"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Pragma" content="no-cache" />
    <!-- Prevents caching at the Proxy Server -->
    <meta http-equiv="Expires" content="0" />
    <title>预约患者列表</title>
	<link rel="Stylesheet" type="text/css" href="../styles/tablelist.css" />
	<script type="text/javascript" src="../scripts/patient/patientList.js"></script>
    <script type="text/javascript" src="../scripts/htmlSelectSuggest.js"></script>	
	<script type="text/javascript">
		$(function()
		{
			 logicalPatient();
			
			$(".outpatient_name").htmlSelectSuggest({width:120,
				onKeyUp: function(event)
				{
					if (event.keyCode==13) 
			    	{ 
						patientAppointSearch();
					} 
				}
			});
			
			enterOutpatientCondition();
			
			onchangeHeight("pl_tabs6",".selectdiv"); 
		});
		
		function enterOutpatientCondition()
		{
			//添加页面文本框的回车检索
		    $("#conditionFormPatientAppoint input").keyup(function(event) 
		    {
		        if (event.keyCode == 13) 
		        {
		        	patientAppointSearch();
		        }
		    }); 
		}
	</script>
</head>
<body style="margin: 0; padding: 0;">
 	  <div id="outpatient_condition"  style="margin-left:1px;margin-right:1px;">
 	  	<form id="conditionFormPatientAppoint" name="conditionFormPatientAppoint" method="post" action="../patient/list_patientappoint.html">
 	  		<table class="blockTable" style="width:100%" cellpadding="2" cellspacing="0" border="0" id="tb_outpatientList">
 	  			<tr class="conditionRow">
 	  				<td style="width:65px;padding-left:5px">姓名：</td>
 	  				<td style="width:65px;">
 	  					<input type="text" id="outpatient_name" name="outpatient_name" style="width: 110px;" value="${patientListSearchPra.patientName}"
 	  						onmouseover="this.style.background='#FDE8FE';" onmouseout="this.style.background='#FFFFFF'" /></td>
 	  				<td style="width:63px;padding-left:7px;">预约日期：</td>
 	  				<td align="left" style="padding-left: 2px;">
						 <input type="text" id="requestStartDate" name="requestStartDate" style="width: 74px;" onmouseover="this.style.background='#FDE8FE';"
							onmouseout="this.style.background='#FFFFFF'"  class="datepicker" value="${patientListSearchPra.requestStartDate}" />&nbsp;至&nbsp;
						 <input type="text" id="requestEndDate" name="visitEndDate" style="width: 74px;" onmouseover="this.style.background='#FDE8FE';"
							onmouseout="this.style.background='#FFFFFF'" class="datepicker" value="${patientListSearchPra.requestEndDate}" />
					</td>
					<td align="left" style="padding-left: 2px;">
						 <input type="checkbox" id="normalPatient" name="normalPatient">本科室预约患者</input>
					</td>
 	  				<td style="text-align:left;padding-left:20px">
 	  				<input type="button" name="patientAppoint_search" onclick="patientAppointSearch()" style="color:#464646;border:0px;BACKGROUND-IMAGE:url(../images/5.jpg);width:57px;height:25px;cursor: pointer;" /></td>
 	  			</tr>
 	  			
 	  		</table>
 	  	</form>
 	  </div>
 	  <div id="patientAppoint_content">
 	  	<jsp:include page="patientAppointDetail.jsp" />
 	  </div>
</body>
</html>