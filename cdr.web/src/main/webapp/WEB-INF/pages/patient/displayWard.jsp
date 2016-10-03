﻿<%@ page language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
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
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="Expires" content="0" />

   <script>
		$(function()
		{
			$( ".wardSelect_ser").htmlSelectSuggest({width:96,
				onKeyUp: function(event)
				{
					if (event.keyCode==13) 
			    	{ 
						searchPatients('normal', false);
					} 
				}
			});
		});
	</script>
</head>
<body style="margin: 0; padding: 0;">
									<select id="wardName" class="wardSelect_ser" name="wardName"
										style="width: 250px;">
										<option value="${Constants.OPTION_SELECT}">请选择</option>
										<html2:pycodeoptions
											domain="${Constants.DOMAIN_WARD}"/>
									</select>
</body>

</html>