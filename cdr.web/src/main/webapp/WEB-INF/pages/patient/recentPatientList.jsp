<%@ page language="java" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="translation.tld" prefix="ref"%>
<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="html2.tld" prefix="html2"%>
<jsp:useBean id="Constants" class="com.founder.cdr.core.Constants" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Pragma" content="no-cache" />
	<!-- Prevents caching at the Proxy Server -->
	<meta http-equiv="Expires" content="0" />
	<title>患者列表</title>
	<link rel="Stylesheet" type="text/css" href="../styles/tablelist.css" />
	<script type="text/javascript" src="../scripts/htmlSelectSuggest.js"></script>
<script>
	$(function()
	{
		logicalPatient();
		
		$(".deptSelect_rec").htmlSelectSuggest({width:150,
			onKeyUp: function(event)
			{
				if (event.keyCode==13) 
		    	{ 
					recentpatientSearch();
				} 
			}
		});
		$(".wardSelect_rec").htmlSelectSuggest({width:80,
			onKeyUp: function(event)
			{
				if (event.keyCode==13) 
		    	{ 
					recentpatientSearch();
				} 
			}
		});
		
		enterRecentpatientCondition();
		
		onchangeHeight("pl_tabs3");
	});	
	
	function enterRecentpatientCondition()
	{
		//添加页面文本框的回车检索
	    $("#conditionFormRecentpatient input").keyup(function(event) 
	    {
	        if (event.keyCode == 13) 
	        {
	        	recentpatientSearch();
	        }
	    });
	}
</script>
</head>
<body style="margin: 0; padding: 0;">
	<div id="recentpatient_condition"  style="margin-left:1px;margin-right:1px;">
	 	<form id="conditionFormRecentpatient" name="conditionFormRecentpatient" method="post" action="../patient/list_recent.html">
	 		<table class="blockTable" style="width:100%" cellpadding="2" cellspacing="0" border="0" id="tb_outpatientList">
	 			<tr class="conditionRow">
	 				<%-- <td style="width:55px;padding-left:5px">就诊号：</td>
	 				<td style="width:175px;text-align:left;padding-left:2px">
	 					<select id="recent_patient_domain" name="recent_patient_domain" style="width:68px;">
							 <option value="${Constants.OPTION_SELECT}">请选择</option>
							 <option value="${Constants.PATIENT_DOMAIN_OUTPATIENT}">就诊卡号</option>
							 <option value="${Constants.PATIENT_DOMAIN_INPATIENT}">住院号</option>
						</select>
		        		<input type="text" id="recent_visit_no" name="recent_visit_no" style="width: 90px;" 
		        			onmouseover="this.style.background='#FDE8FE';" onmouseout="this.style.background='#FFFFFF'" />
	 				</td> --%>
	 				<td><div align="right" style="padding-right: 2px;">病人号：</div></td>
								<td align="left" style="padding-left: 2px;">
									<input type="text" id="patientEId" name="patientEId" style="width: 96px;" onmouseover="this.style.background='#FDE8FE';"
										onmouseout="this.style.background='#FFFFFF'" value="${patientListSearchPra.patientEId}"/>
								</td>
	 				<td style="width:40x;text-align:left">姓名：</td>
	 				<td style="width:90px;text-align:left"><input type="text" id="recentpatient_name" name="recentpatient_name" style="width: 80px;" 
 	  											onmouseover="this.style.background='#FDE8FE';" onmouseout="this.style.background='#FFFFFF'"/></td>
	 				<td style="width:40px;padding-left:4px;">科室：</td>
 	  				<td style="width:90px">
 	  					<div class="selectdiv">								
							<select id="deptName_rec" class="deptSelect_rec" name="deptName"
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
					</td>
					<td align="right">病区： </td>
					<td align="left">
						<div class="selectdiv2">
							<select id="wardName_rec" class="wardSelect_rec" name="wardName"
								style="width: 80px;">
								<option value="${Constants.OPTION_SELECT}">请选择</option>
								<html2:pycodeoptions
									domain="${Constants.DOMAIN_WARD}"/>
							</select>
						</div>
					</td>
	 				<td style="text-align:left;padding-left:20px"><input type="button" name="recentpatient_search" onclick="recentpatientSearch()" style="color:#464646;border:0px;BACKGROUND-IMAGE:url(../images/5.jpg);width:57px;height:25px;cursor: pointer;" /></td>
	 			</tr>
	 			
	 				<!-- $Author :chang_xuewen
 					 $Date : 2013/12/16 10:00$
					 [BUG]0040770 MODIFY BEGIN -->
					<c:if test="${Constants.TRUE_ORG_CODE == Constants.DISABLE_ORG_CODE}">
					<tr class="conditionRow">
					<td>
					<div class="cell"  style="width: 60px; padding-left: 2px;">医疗机构：</div>
					</td>
					<td>
					<div style="float:left;margin-top:4px;width: 170px;">
						<select id="orgCodeFlag" name="orgCodeFlag" style="width:170px;">
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
 	  				<td>&nbsp;</td>
 	  				<td>&nbsp;</td>
 	  				<td>&nbsp;</td>
	 			</tr>
					</c:if> 
					
                	<!-- [BUG]0040770 MODIFY END -->
                						
	 				
					
 	  				
	 		</table>
	 	</form>
	</div>
	<div id="recentpatient_content">
		<jsp:include page="recentPatientDetail.jsp" />
	</div>
</body>
</html>