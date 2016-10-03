<%@ page language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="translation.tld" prefix="ref"%>
<%@ taglib uri="html.tld" prefix="html"%>
<jsp:useBean id="Constants" class="com.yly.cdr.core.Constants"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <title>患者列表</title>
	<link type="text/css" rel="Stylesheet" href="../styles/jquery-ui-1.8.18.custom.modify.css"
        charset="UTF8" />
    <link type="text/css" rel="Stylesheet" href="../styles/layout-default-1.3.0rc29.15.css"
        charset="UTF8" />
    <link type="text/css" rel="Stylesheet" href="../styles/layout-cdr.css" charset="UTF8" />
    <link type="text/css" rel="Stylesheet" href="../styles/tablelist.css" charset="UTF8" />
    <link type="text/css" rel="Stylesheet" href="../styles/timer-shaft.css" charset="utf-8" />
	<link type="text/css" rel="Stylesheet" href="../styles/loadingScreen.css" charset="UTF8" />
	<link type="text/css" rel="Stylesheet" href="../styles/jquery-suggest.css" charset="UTF8" />	
	<link rel="icon" href="../favicon.ico" type="image/x-icon" /> 
    <link rel="shortcut icon" type="image/x-icon" href="../favicon.ico" />
    <script type="text/javascript" src="../scripts/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="../scripts/jquery-ui-1.8.18.custom.min.js"></script>
    <script type="text/javascript" src="../scripts/jquery.layout-1.3.0rc29.15.js"></script>
    <script type="text/javascript" src="../scripts/jquery.bgiframe.js"></script>
	<script type="text/javascript" src="../scripts/loadingScreen.js"></script>
	<script type="text/javascript" src="../scripts/jquery.suggest.js"></script>
	<script type="text/javascript" src="../scripts/htmlSelectSuggest.js"></script>
	<script type="text/javascript" src="../scripts/htmlMultiSelectSuggest.js"></script>
    <script type="text/javascript" src="../scripts/common.js"></script>
    <script type="text/javascript" src="../scripts/layout.js"></script>
    <script type="text/javascript" src="../scripts/visit/mainView.js"></script>
    <script>
 		window.onresize  =  resetDIV;  
        function  resetDIV()  
        {  
        	setDialogInCenter();
        }  

		function setDialogInCenter() {
	        if (typeof ($("#patientSelectDialog").parent("div.ui-dialog")[0]) == "undefined") {
	            setTimeout(setDialogInCenter, 1);
	        }
	        else {
	        	$("#patientSelectDialog").parent("div.ui-dialog").css("position", "absolute");
	            var dlgHeight = $("#patientSelectDialog").parent("div.ui-dialog").outerHeight();
	            var windowHeight = $(window).height();
	            var windowWidth = $(window).width();
	            var dlgWidth = $("#patientSelectDialog").parent("div.ui-dialog").outerWidth();
	            if(windowHeight > dlgHeight)
	            	$("#patientSelectDialog").parent("div.ui-dialog").css("top", ((windowHeight - dlgHeight) / 2) + "px");
	            else
	            	$("#patientSelectDialog").parent("div.ui-dialog").css("top", 0 + "px");
	            if(windowWidth > dlgWidth)
	            	$("#patientSelectDialog").parent("div.ui-dialog").css("left", ((windowWidth - dlgWidth) / 2)  + "px");
	            else
	            	$("#patientSelectDialog").parent("div.ui-dialog").css("left", 0  + "px");	            	
	        }
	    }
    </script>
</head>
<body style="background:url('../images/dt.jpg');">
    <div id="patientSelectDialog">	    
    </div>
    <div id="loading">
		<div id="loadingScreen" style="display:none"><div id="loadingMessage">数据加载中，请稍候...</div></div>
	</div>
    
    <script type="text/javascript">
	    /**
	     * 登录成功后进入患者选择页面
	     */
		$(document).ready(function()
		{
			var openUrl = "../patient/index.html?loginFlag=true";

			// $Author :jin_pengssss
            // $Date : 2012/11/09 17:51$
            // [BUG]0010795 MODIFY BEGIN
            // 传入域ID
			jQuery("#patientSelectDialog").load(openUrl,{patientDomain:'${patientDomain}'});
			
			// [BUG]0010795 MODIFY BEGIN
			var option = 
			{
			    dialogClass: "my-dialog",
				title: "请选择患者",  width: 950, height: 550,
				modal:true
			};

		    $("#patientSelectDialog").dialog(option);
		    
		    return false;
		});
	    
		//$Author :jin_peng
		// $Date : 2012/10/31 17:38$
		// [BUG]0010836 ADD BEGIN
		document.onkeydown = function()
		{
			// 键盘关闭浏览器记录用户退出系统日志
			keyClosed('${Constants.EXIT_KINDS_FLAG_CLOSED}');
		}
	    
		window.onbeforeunload = function()
		{
			// 关闭按钮关闭浏览器记录用户退出系统日志
			buttonClosed('${Constants.EXIT_KINDS_FLAG_CLOSED}');
		}
		// [BUG]0010836 ADD END
	</script>
</body>
</html>
