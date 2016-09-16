<%@ page language="java" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="translation.tld" prefix="ref"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="html2.tld" prefix="html2"%>
<%@ taglib uri="html.tld" prefix="html"%>
<jsp:useBean id="Constants" class="com.founder.cdr.core.Constants" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<script>
		$(document).ready(function() {
	
			$("#moreTabs").tabs();
			
			$("#detailViewTab_0").tabs();
	
			tabsBind("#moreTabs");
			
			var firstLi = $("#select_moreTabs1");
			 
			 firstLi.children("a").css("background-image","url(../images/title2.png)").addClass("parentli");
			 
			 $('<img src="../images/close.png"/ style="position:absolute;right:3px;top:10px;cursor:pointer;">').appendTo(firstLi).click(
			 function(){ //关闭按钮,关闭事件绑定 
				 var index = $('#moreTabs ul:first>li').index(firstLi.get(0));
				 
			 	 var orderSn = $("#detailViewTab_0").find("div[name='selectTabs']").attr("id");
			 
				 initPanel(orderSn, index, $("#moreTabs"));
			 }); 
		});
	</script>
</head>
<body style="margin: 0; padding: 0;">
<div id="tab">
	<div id="tabContent">
		<div id="dd_detail">
		</div>
		<div id="moreTabs" style="width:${width};">
			<ul>
				<li id="select_moreTabs1"><a href="#detailViewTab_0" class="parentTabs">
					选项卡
					<span id="img_0" style="float:right;margin-left:-25px;"></span></a>
				</li>
			</ul>
			<div id="detailViewTab_0" class="detailViewTab" style="padding:0px">
			</div>
		</div>
	</div>
</div>
</body>
</html>