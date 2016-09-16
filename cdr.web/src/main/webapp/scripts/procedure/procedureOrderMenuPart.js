/*function showProcedureOrder(event,data)
{
	var x = event.x;
	var y = event.y;

	$('<table id="tabstip-procedure" class="table">'+
			'<tr style="font-weight:bold"><td height="28" align="center">执行科室</td>'+
				'<td height="28" align="center">开始时间</td>'+
				'<td height="28" align="center">结束时间</td>'+
				'<td height="28" align="center">主刀医生</td>'+
				'<td height="28" align="center">麻醉医生</td>'+
				'<td height="28" align="center">等级</td>'+
				'<td height="28" align="center">工作量</td>'+
				'<td height="28" align="center">愈合等级</td>'+
			'</tr>'+
			'<tr><td>'+data.surgicalDeptName+'</td>'+
				'<td>'+data.startTime+'</td>'+
				'<td>'+data.endTime+'</td>'+
				'<td>'+data.zhudao+'</td>'+
				'<td>'+data.mazui+'</td>'+
				'<td>'+data.procedureLevel+'</td>'+
				'<td>'+data.workload+'</td>'+
				'<td>'+data.healingGradeName+'</td>'+
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
	
	//设置提示信息x轴位置
	$("#tabstip-procedure").css({width:'550px',maxWidth:'550px',height:'60px'});
	
	//淡入显示tip内容
	promptFadeIn("#tabstip-procedure");
}
*/