/*function showDocument(dd,data)
{
	var x = dd.x;
	var y = dd.y;

	$('<table id="tabstip-doc" class="table">'+
			'<tr style="font-weight:bold">'+
				'<td height="28" align="center">文档作者</td>'+
				'<td height="28" align="center">文档修改者</td>'+
				'<td height="28" align="center">提交时间</td>'+
			'</tr>'+'<tr>'+
				'<td align="left">'+data.documentAuthorName+'</td>'+
				'<td align="left">'+data.documentModifierName+'</td>'+
				'<td align="center">'+data.submitTime+'</td>'+
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
	$("#tabstip-doc").css({width:'550px',maxWidth:'550px',height:'60px'});
	
	//淡入显示tip内容
	promptFadeIn("#tabstip-doc");
}
*/