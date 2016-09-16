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
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
  <meta http-equiv="Pragma" content="no-cache"/>
  <meta http-equiv="Cache-Control" content="no-cache"/>
  <meta http-equiv="Expires" content="0"/>
<title>用药医嘱趋势图</title>
</head>
<body>
	<div id="graphPie" class="graph" style="margin-right:20px;margin-top:15px;height:330px;width:70%;border:0;display:block">
		
	</div>
	
	<div style="float:right;width:100%;text-align:center;margin-top:14px;font-size:180%;color:#FF8C10;font-weight:bold">药 物 比 例 图 形</div>
	
	<script type="text/javascript">
		var dataTrenderAmount1 = null;
		var dataPerCount1 = null;
		var dataLabelArray = [];
		var isFilled = false; 
		var resizePie = null;
	
		$(function ()
		{
			var screenWidth = window.screen.width;
			
			var approvedDrugNameValue = ${displayPieContent}[0].split("-")[0];
			
			loadChart("../drug/trendChart_" + getPatientSn() + ".html",{approvedDrugName:approvedDrugNameValue},'#trenderId',true);
			
			structureData(${displayPieTotal},${displayPieContent});
			
			drawPieChart();
			
			if(screenWidth <= 1152)
			{
				$(".myLegendLabel").css("fontSize","11px");
			}
			
			adjustPromptFont();
			
			initPieHoverContent();
		});
		
		function adjustPromptFont()
		{
			var screenWidth = window.screen.width;
			
			var promptWidth = parseNumber(replaceContent($("#pieId").css("width"),"px","")) - parseNumber(replaceContent($("#labelId").css("left"),"px","")) - 19;
			
			var divisor = 12;
			
			var displayFontNum = Math.floor(promptWidth / divisor);
			
			$(".myLegendLabel").each(function(i)
			{
				var labelWidth = parseNumber(replaceContent($(this).css("width"),"px",""));
				
				if(!isFilled)
				{
					dataLabelArray.push($(this).html());
				}
				
				if(labelWidth > promptWidth)
				{
					$(this).html(dataLabelArray[i].substring(0,displayFontNum - 1) + "...");
				}
				else
				{
					$(this).html(dataLabelArray[i]);
				}
			});
			
			isFilled = true;
		}
		
		$(window).resize(function()
		{
			/*if($("#graphPie").size() > 0)
			{
				// 减少页面调用次数
				if(resizePie)
				{
					clearTimeout(resizePie);
				}
				
				resizePie = setTimeout("adjustPromptFont()",150); 
			}*/
		});
		
	</script>
</body>
</html>
