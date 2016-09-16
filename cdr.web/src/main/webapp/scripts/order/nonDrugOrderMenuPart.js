$(document).ready(function() {
	$("#tabs-animate").toggle(function() {
		$("#dd_detailContent").hide(1000);
		$("#tabs-animate").animate({
			"margin-left" : "2px"
		}, 1, function() {
			$("#moreTabs").animate({
				"width" : "97%",
				"margin-left" : "2px"
			}, 1000);
			$("#tabs-animate").addClass("paneSeperator_closed");
			$("#tabs-animate").removeClass("paneSeperator_open");
		});
	}, function() {
		$("#moreTabs").animate({
			"width" : "69.8%"
		}, 800);
		$("#dd_detailContent").show(1000);
		$("#tabs-animate").animate({
			"margin-left" : "2px"
		}, function() {
			$("#tabs-animate").removeClass("paneSeperator_closed");
			$("#tabs-animate").addClass("paneSeperator_open");
		});
	});
});


/*function showNondrugOrder(event,data)
{
	var x = event.x;
	var y = event.y;
	
	// ie6情况下添加滚动条偏移量
	if(isIE6())
	{
		x = x + document.documentElement.scrollLeft;
		y = y + document.documentElement.scrollTop;
	}
	 $Author :chang_xuewen
	  * $Date : 2013/10/31 11:00$
	  * [BUG]0038735 MODIFY BEGIN
	  
	$('<table id="tabstip-nondrug" class="table">'+
			'<tr style="font-weight:bold"><td height="28" align="center">病区</td>'+
				'<td height="28" align="center">长期或临时</td>'+
				'<td height="28" align="center">开嘱科室</td>'+
				'<td height="28" align="center">执行科室</td>'+
				'<td height="28" align="center">开嘱人</td>'+
				'<td height="28" align="center">医嘱录入时间</td>'+
				'<td height="28" align="center">取消人</td>'+
				'<td height="28" align="center">取消时间</td>'+
			'</tr>'+
			'<tr><td align="left">'+data.wardName+'</td>'+
				'<td align="left">'+data.temporaryFlag+'</td>'+
				'<td align="left">'+data.orderDeptName+'</td>'+
				'<td align="left">'+data.execDeptName+'</td>'+
				'<td align="left">'+data.orderPersonName+'</td>'+
				'<td align="center">'+data.orderTime+'</td>'+
				'<td align="left">'+data.cancelPersonName+'</td>'+
				'<td align="center">'+data.cancelTime+'</td>'+
			'</tr></table>').css(
	{
		
		display: 'none',
		position : 'absolute',
		top : y + 5,
		left : x + 5,
		maxWidth: '550px',
		'word-break' : 'break-all',
		border : '1px solid #99ccff',
		padding : '2px',
		'background-color' : '#D5EAFF',
		lineHeight: '20px'
	}).appendTo("#ajaxDialog");
	 [BUG]0038735 MODIFY END
	  

	//设置提示信息x轴位置
	$("#tabstip-nondrug").css({width:'550px',maxWidth:'550px',height:'60px'});
	
	//淡入显示tip内容
	promptFadeIn();
}



 * 提示信息淡出显示
 * 
function promptFadeIn()
{
	//淡入显示tip内容
	setTimeout("if($('#tabstip-nondrug') != undefined){$('#tabstip-nondrug').fadeIn(500)}",400 );
}

function removeTabsTip()
{
	$('#tabstip-nondrug').remove();
}*/
