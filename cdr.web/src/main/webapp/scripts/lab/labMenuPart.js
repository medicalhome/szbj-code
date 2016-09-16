/*function showDetailTip(event,id,json)
{
	alert("1");
	var x = event.x;
	var y = event.y;
	
	aler();
	
	var dataName = new Array(); // title
	var dataValue = new Array();// title对应的值
	
	for(var i in json)
	{
		dataName.push(i);
		dataValue.push(json[i]);
	}
	
	var appendName ;
	var appendValue ;
	
	for(var i in dataName)
	{
		if(appendName != undefined)
		{
			appendName += '<td height="28" align="center">'+dataName[i]+'</td>';
		}
		else
		{
			appendName = '<td height="28" align="center">'+dataName[i]+'</td>';
		}	
	}
	
	for(var i in dataValue)
	{
		if(appendValue != undefined)
		{
			appendValue += '<td align="center">'+dataValue[i]+'</td>';
		}
		else
		{
			appendValue = '<td align="center">'+dataValue[i]+'</td>';
		}
	}
	
	$('<table id="tabstip-lab" class="table">'+
		'<tr style="font-weight:bold">'+appendName+'</tr>'+
		'<tr>'+appendValue+'</tr></table>').css(
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
	$(id).css({width:'550px',maxWidth:'550px',height:'60px'});
	
	//淡入显示tip内容
	promptFadeIn(id);
}

*/