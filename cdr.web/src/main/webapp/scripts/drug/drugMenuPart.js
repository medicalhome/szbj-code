/*function showDrugOrder(dd,data)
{
	var x = dd.x;
	var y = dd.y;
	
	// ie6情况下添加滚动条偏移量
	if(isIE6())
	{
		x = x + document.documentElement.scrollLeft;
		y = y + document.documentElement.scrollTop;
	}

	$('<table id="tabstip-drug" class="table">'+
			'<tr style="font-weight:bold"><td height="28" align="center">药物类型</td>'+
				'<td height="28" align="center">药物剂型</td>'+
				'<td height="28" align="center">用药途径</td>'+
				'<td height="28" align="center">次剂量</td>'+
				'<td height="28" align="center">总剂量</td>'+
				'<td height="28" align="center">使用频率</td>'+
				'<td height="28" align="center">长期或临时</td>'+
				'<td height="28" align="center">开嘱人</td>'+
				'<td height="28" align="center">开嘱科室</td>'+
			'</tr>'+
			'<tr><td align="left">'+data.medicineTypeName+'</td>'+
				'<td align="left">'+data.medicineForm+'</td>'+
				'<td align="left">'+data.routeName+'</td>'+
				'<td align="left">'+data.dosage+data.dosageUnit+'</td>'+
				'<td align="left">'+data.totalDosage+data.totalDosageUnit+'</td>'+
				'<td align="left">'+data.medicineFreqName+'</td>'+
				'<td align="left">'+data.temporaryFlag+'</td>'+
				'<td align="left">'+data.orderPersonName+'</td>'+
				'<td align="left">'+data.orderDeptName+'</td>'+
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
	$("#tabstip-drug").css({width:'550px',maxWidth:'550px',height:'60px'});
	
	//淡入显示tip内容
	promptFadeIn("#tabstip-drug");
}
*/
function showParentOrder(obj,patientSn,orderSn,name,temporaryFlag,id,event)
{
	var data = {'menuPartFlag':'true','temporaryFlag':temporaryFlag,'width':'70%','orderSn':orderSn,'name':name,'holdName':name,'patientSn':patientSn};
	if(data.name.length > 4){
		data.name = data.name.substring(0,4) + "...";
	}
	if($("#BIGviewFrame").length > 0){//综合视图
		bigShow(obj,'../drug/detail_'+data.orderSn+'.html?flag=tabs',data);
	}else if($("#ntr").length>0){//门诊视图
		stopBubble(event);
//		showDialog('../drug/detail_'+data.orderSn+'.html?flag=tabs','药物医嘱');
		showTabs(obj,'../drug/detail_'+data.orderSn+'.html?flag=tabs',data);		
	}//住院或时间轴视图
	else
		addMoreTabs('#'+orderSn,'../drug/detail_'+data.orderSn+'.html?flag=tabs',data,id);
}
