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
	<div id="tFont" style="width:10px;position:absolute;margin-top:50px;margin-left:14px !important;margin-left:-33px;font-size:180%;color:#FF8C10;font-weight:bold"
		onmouseover="showTooltip(event.x, event.y, '${approvedDrugName}')" onmouseout="$('#tooltip').remove();">${approvedDrugName}</div>
	<div id="flotTrender" style="margin-left:48px;margin-top:25px;width:90%;height:300px;top:0px !important;top:30px;"></div> <!-- width:628px; height:320px;-->
	<div style="margin-left:63px;margin-top:-2px !important;margin-top:40px;width:140px"><!-- margin-top:49px;border:0;margin-left:63px;-->
		&nbsp;
		<div style="margin-right:15px;float:left;">
			<div style="float:left;width:14px;BORDER-BOTTOM: #ccc 1px solid; BORDER-LEFT: #ccc 1px solid; PADDING-BOTTOM: 1px; PADDING-LEFT: 1px;PADDING-RIGHT: 1px; BORDER-TOP: #ccc 1px solid; BORDER-RIGHT: #ccc 1px solid; PADDING-TOP: 1px">
				<div style="float:left;BORDER-BOTTOM: #F8E7B3 5px solid; BORDER-LEFT: #F8E7B3 5px solid; WIDTH: 4px; HEIGHT: 0px;OVERFLOW: hidden; BORDER-TOP: #F8E7B3 5px solid; BORDER-RIGHT: #F8E7B3 5px solid">
		
				</div>
			</div>
			<div style="float:left;margin-left:3px;color:#545454;font-size:smaller;">
				<c:choose>
					<c:when test="${approvedDrugName != '草药'}">
						次剂量
					</c:when>
					<c:otherwise>
						草药副数
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		
		<!--<c:if test="${approvedDrugName != '草药'}">
			<div style="float:left;">
				<div style="float:left;width:14px;BORDER-BOTTOM: #ccc 1px solid; BORDER-LEFT: #ccc 1px solid; PADDING-BOTTOM: 1px; PADDING-LEFT: 1px;PADDING-RIGHT: 1px; BORDER-TOP: #ccc 1px solid; BORDER-RIGHT: #ccc 1px solid; PADDING-TOP: 1px">
					<div style="float:left;BORDER-BOTTOM: #DFEFFC 5px solid; BORDER-LEFT: #DFEFFC 5px solid; WIDTH: 4px; HEIGHT: 0px;OVERFLOW: hidden; BORDER-TOP: #DFEFFC 5px solid; BORDER-RIGHT: #DFEFFC 5px solid">
					
					</div>
				</div>
				<div style="float:left;margin-left:3px;color:#545454;font-size:smaller;">次剂量</div>
			</div>
		</c:if>-->
	</div>
	<div id="trenderPaging">
		<form name="trenderPagingform" method="get"
			action="../drug/trendChart_${patientSn}.html">
			<input type="hidden" name="approvedDrugName" value="${approvedDrugName}" />
			<div id="trenderPagingDiv" style="float:right;width:400px;margin-right:13px;margin-top:2px"><!-- margin-top:-5px; -->
				<c:if test="${pagingContext.pageNo < pagingContext.totalPageCnt}">
					<div class="lastPage" style="float:right;">
						<a href="javascript:void(0);"
							onclick="jumpToChartPage(${pagingContext.totalPageCnt},'#trenderId');return false;"><img
							src="../images/3.gif"
							style="border: 0px; width: 21px; height: 16px;" /></a>
					</div>
					<div class="nextPage" style="float:right;">
						<a href="javascript:void(0);"
							onclick="jumpToChartPage(${pagingContext.pageNo + 1},'#trenderId');return false;"><img
							src="../images/4.gif"
							style="border: 0px; width: 41px; height: 16px;" /></a>
					</div>
				</c:if>
				<c:if test="${pagingContext.pageNo > 1}">
					<div class="prevPage" style="float:right;">
						<a href="javascript:void(0);"
							onclick="jumpToChartPage(${pagingContext.pageNo-1},'#trenderId');return false;"><img
							src="../images/2.gif"
							style="border: 0px; width: 41px; height: 16px;" /></a>
					</div>
					<div class="firstPage" style="float:right;">
						<a href="javascript:void(0);"
							onclick="jumpToChartPage(1,'#trenderId');return false;"><img
							src="../images/1.gif"
							style="border: 0px; width: 21px; height: 16px;" /></a>
					</div>
				</c:if>
				<div class="pagedesc" style="float:right;width:170px;margin-left:10px">共${pagingContext.totalRowCnt}条记录！第${pagingContext.pageNo}页/共${pagingContext.totalPageCnt}页</div>
			</div>
		</form>
	</div>
	<script type="text/javascript">
		$(function ()
		{
			setTitelLabel();
			
			structureTrenderData(${displayTrendList},'${approvedDrugName}');
			
			adjustTrendWidth();
			
			drawTrenderChart();
			
			initTrendHoverContent();
			
			adjustTrendChartLayout();
		});
		
		function setTitelLabel()
		{
			var approvedDrugName = '${approvedDrugName}';
			
			if(approvedDrugName.length > 9)
			{
				approvedDrugName = approvedDrugName.substring(0,8) + "…";
			}
			
			$("#tFont").html(approvedDrugName);
		}
		
		function adjustTrendChartLayout()
		{
			var screenHeight = window.screen.height;
			
			var trendHeight = parseNumber(replaceContent($("#trenderId").css("height"),"px","")) - parseNumber(replaceContent($("#flotTrender").css("top"),"px","")) - 75;
			
			$("#flotTrender").css("height",trendHeight + "px");
		}
		
		function adjustTrendWidth()
		{
			var trendWidth = parseNumber(replaceContent($("#trenderId").css("width"),"px","")) - parseNumber(replaceContent($("#flotTrender").css("marginLeft"),"px","")) - 18;
			
			$("#flotTrender").css("width",trendWidth + "px");
		}
		
	</script>
</body>
</html>
