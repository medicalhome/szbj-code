/*$(document).ready(function() {
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
});*/

/*function showDiagnosis(dd,data)
{
	var x = dd.x;
	var y = dd.y;

	$('<table id="tabstip-diagnosis" class="table">'+
			'<tr style="font-weight:bold">'+
				'<td height="28" align="center">科室</td>'+
				'<td height="28" align="center">医生</td>'+
				'<td height="28" align="center">诊断日期</td>'+
				'<td height="28" align="center">就诊类别</td>'+
				'<td height="28" align="center">是否主要诊断</td>'+
				'<td height="28" align="center">待查</td>'+
				'<td height="28" align="center">传染病</td>'+
			'</tr>'+
			'<tr><td align="left">'+data.diagnosticDeptName+'</td>'+
				'<td align="left">'+data.diagnosisDoctorName+'</td>'+
				'<td align="center">'+data.diagnosisDate+'</td>'+
				'<td align="left">'+data.visitTypeName+'</td>'+
				'<td align="left">'+data.mainDiagnosisFlag+'</td>'+
				'<td align="left">'+data.uncertainFlag+'</td>'+
				'<td align="left">'+data.contagiousFlag+'</td>'+
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
	$("#tabstip-diagnosis").css({width:'550px',maxWidth:'550px',height:'60px'});
	
	//淡入显示tip内容
	promptFadeIn("#tabstip-diagnosis");
}*/

