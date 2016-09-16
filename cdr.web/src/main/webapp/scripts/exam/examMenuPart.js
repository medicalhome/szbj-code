/*function showExamOrder(event,data)
{
	var x = event.x;
	var y = event.y;
	
	$('<table id="tabstip-exam" class="table">'+
			'<tr style="font-weight:bold"><td height="28" align="center">检查部位</td>'+
				'<td height="28" align="center">医嘱状态</td>'+
				'<td height="28" align="center">申请日期</td>'+
				'<td height="28" align="center">检查科室</td>'+
				'<td height="28" align="center">检查方法</td>'+
				'<td height="28" align="center">检查医生</td>'+
				'<td height="28" align="center">检查日期</td>'+
				'<td height="28" align="center">检查结果</td>'+
			'</tr>'+
			'<tr><td align="left">'+data.examinationRegionName+'</td>'+
				'<td align="left">'+data.orderStatusName+'</td>'+
				'<td align="center">'+data.requestDate+'</td>'+
				'<td align="center">'+data.orderDeptName+'</td>'+
				'<td align="left">'+data.examinationMethodName+'</td>'+
				'<td align="left">'+data.examiningDoctorName+'</td>'+
				'<td align="center">'+data.examinationDate+'</td>'+
				'<td align="left">'+data.imagingConclusion+'</td>'+
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
	$("#tabstip-exam").css({width:'550px',maxWidth:'550px',height:'60px'});
	
	//淡入显示tip内容
	promptFadeIn("#tabstip-exam");
}
*/