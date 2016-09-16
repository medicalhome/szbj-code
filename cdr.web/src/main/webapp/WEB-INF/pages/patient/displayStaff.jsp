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
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="Expires" content="0" />

   <script>
		$(function()
		{//alert("1");

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

			
/* 			logicalPatient();

			onchangeHeight("pl_tabs4"); */
		// $Author:chang_xuewen
        // $Date : 2014/01/02 16:10
        // $[BUG]0040993 ADD BEGIN
			if("true" == reacl()){
				$(".selectdiv3").find("input").attr("disabled",true);
			}
		});	
		function reacl(){
			return "${(useACL && aclAuths.patientScopeAuth01) && !(useACL && aclAuths.patientScopeAuth05)}";
			//return "${useACL && aclAuths.patientScopeAuth05}";
		}
		//var hgt = $(input).attr("autocomplete", "off").height();
		// $[BUG]0040993 ADD END
	</script>
</head>
<body style="margin: 0; padding: 0;">
	<select id="staff" name="staff" class="staffSelect_ser" style="width:181px;">
			<option value="${Constants.OPTION_SELECT}">请选择</option>
			<html2:pycodeoptions
				domain="${Constants.DOMAIN_STAFF}"/>
	</select>
</body>

</html>