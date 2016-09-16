/**
* jQuery的功能扩展，
* 把FORM中的输入转换为JSON对象
* 示例： parent.loadPanel(form.attr("action"), form.serializeObject(), id)
*/
jQuery.fn.serializeObject = function() {
    var o = {};
    var a = this.serializeArray();
    $.each(a,
    function() {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;

};

//日期输入时格式校验共同接口
//示例：$(".datepicker").bind("blur",parent.isDateString)
function isDateString() {
    var strDate = $(this).val();
    var check = isDateStringCheck(strDate);
    if ("true" == check) {
        //清空错误的时间格式内容
        $(this).val('');
    }
}

//日期输入时格式校验
//isDateString调用此方法
function isDateStringCheck(strDate) {
    if (strDate == null || strDate == '') {
        return ("false");
    }
    var strSeparator = "-"; //日期分隔符 
    var strDateArray;
    var intYear;
    var intMonth;
    var intDay;
    var boolLeapYear;
    var ErrorMsg = ""; //出错信息 
    strDateArray = strDate.split(strSeparator);
    //没有判断长度,其实2008-8-8也是合理的//strDate.length != 10 || 
    if (strDate.length != 10 || strDateArray.length != 3 || strDateArray[0].length != 4) {
        ErrorMsg += "日期格式必须为: yyyy-mm-dd";
        showMsg("提示", ErrorMsg);
        return ("true");
    }
    intYear = parseInt(strDateArray[0], 10);
    intMonth = parseInt(strDateArray[1], 10);
    intDay = parseInt(strDateArray[2], 10);
    if (isNaN(intYear) || isNaN(intMonth) || isNaN(intDay)) {
        ErrorMsg += "日期格式错误: 年月日必须为纯数字";
        showMsg("提示", ErrorMsg);
        return ("true");
    }
    if (intMonth > 12 || intMonth < 1) {
        ErrorMsg += "日期格式错误: 月份必须介于1和12之间";
        showMsg("提示", ErrorMsg);
        return ("true");
    }
    if ((intMonth == 1 || intMonth == 3 || intMonth == 5 || intMonth == 7 || intMonth == 8 || intMonth == 10 || intMonth == 12) && (intDay > 31 || intDay < 1)) {
        ErrorMsg += "日期格式错误: 大月的天数必须介于1到31之间";
        showMsg("提示", ErrorMsg);
        return ("true");
    }
    if ((intMonth == 4 || intMonth == 6 || intMonth == 9 || intMonth == 11) && (intDay > 30 || intDay < 1)) {
        ErrorMsg += "日期格式错误: 小月的天数必须介于1到30之间";
        showMsg("提示", ErrorMsg);
        return ("true");
    }
    if (intMonth == 2) {
        if (intDay < 1) {
            ErrorMsg += "日期格式错误: 日期必须大于或等于1";
            showMsg("提示", ErrorMsg);
            return ("true");
        }
        boolLeapYear = false;
        if ((intYear % 100) == 0) {
            if ((intYear % 400) == 0) boolLeapYear = true;
        } else {
            if ((intYear % 4) == 0) boolLeapYear = true;
        }
        if (boolLeapYear) {
            if (intDay > 29) {
                ErrorMsg += "日期格式错误: 闰年的2月份天数不能超过29";
                showMsg("提示", ErrorMsg);
                return ("true");
            }
        } else {
            if (intDay > 28) {
                ErrorMsg += "日期格式错误: 非闰年的2月份天数不能超过28";
                showMsg("提示", ErrorMsg);
                return ("true");
            }
        }
    }
}

/**
 * 用于对日期进行格式化操作
 */
Date.prototype.format = function(format) {  
    /* 
     * eg:format="yyyy-MM-dd hh:mm:ss"; 
     */  
    var o = {  
        "M+" : this.getMonth() + 1, // month  
        "d+" : this.getDate(), // day  
        "h+" : this.getHours(), // hour  
        "m+" : this.getMinutes(), // minute  
        "s+" : this.getSeconds(), // second  
        "q+" : Math.floor((this.getMonth() + 3) / 3), // quarter  
        "S" : this.getMilliseconds()  
        // millisecond  
    }  
  
    if (/(y+)/.test(format)) {  
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4  
                        - RegExp.$1.length));  
    }  
  
    for (var k in o) {  
        if (new RegExp("(" + k + ")").test(format)) {  
            format = format.replace(RegExp.$1, RegExp.$1.length == 1  
                            ? o[k]  
                            : ("00" + o[k]).substr(("" + o[k]).length));  
        }  
    }  
    return format;  
}  

/**
 * 阻止事件冒泡
 * @param e
 */
function stopBubble(event) {
    //如果提供了事件对象，则这是一个非IE浏览器
    if ( event && event.stopPropagation )
        //因此它支持W3C的stopPropagation()方法
    	event.stopPropagation();
    else
        //否则，我们需要使用IE的方式来取消事件冒泡
        event.cancelBubble = true;
}

/**
 * 左右分屏，左边列表显示详细提示信息
 */
function showDetailTip(event,id,json)
{
/*	var x = event.x;
	var y = event.y;*/
	// 鼠标在页面中的位置，而不是屏幕中的位置
	var x = event.pageX;
	var y = event.pageY;

	var dataName = new Array(); // title
	var dataValue = new Array();// title对应的值
	
	var divObj = $("#ajaxDialog");
	var offsetTop = divObj.offset().top;
	var offsetLeft = divObj.offset().left;
	// 详细提示框的坐标设置为相对弹出框的位置
	x = x - offsetLeft;
	y = y - offsetTop;
	
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
	
	$('<table id="'+id.substring(1)+'" class="table">'+
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
function showPatientDetailTip(event,id,count,json_out,json_in)
{
	var dialogId = "#ajaxDialog"+count;
	var x = event.x;
	var y = event.y;
	var dataName_out= new Array(); // title
	var dataValue_out = new Array();// title对应的值
	var dataName_in= new Array(); // title
	var dataValue_in = new Array();// title对应的值
	for(var i in json_out)
	{	dataName_out.push(i);
		dataValue_out.push(json_out[i]);
	}
	for(var i in json_in)
	{
		dataName_in.push(i);
		dataValue_in.push(json_in[i]);
	}
	var appendName='<tr>';
	if(dataName_out!=""){		
		appendName+='<td height="28" width="39" align="center" style="background-color : #D5EAFF;border-top: 1px dashed #99ccff;">'+dataName_out[0]+'</td>';		
	}
	for(var i in dataValue_out)
	{
		appendName+='<td height="28" align="center" style="background-color : white;border-top: 1px dashed #99ccff;">'+dataValue_out[i]+'</td>';			
	}
	
	if(dataName_in!=""){		
		appendName+='<td height="28" width="39" align="center" style="background-color : #D5EAFF;border-top: 1px dashed #99ccff;">'+dataName_in[0]+'</td>';
	}
	for(var i in dataValue_in)
	{
		appendName+='<td height="28"  align="center"  style="background-color : white;border-top: 1px dashed #99ccff;">'+dataValue_in[i]+'</td>';			
				
	}
	appendName+='</tr>';
//	var appendName='<tr style="background-color : #D5EAFF;"><td colspan="2">全部就诊号</td></tr>';
//	
//	if(dataName_out!=""){
//		
//			appendName+='<tr><td height="28" align="center" style="background-color : #D5EAFF;border-top: 1px dashed #99ccff;">'+dataName_out[0]+'</td>';
//		
//	}
//	for(var i in dataValue_out)
//	{
//		if(i==0){
//			appendName+='<td height="28" align="center"  style="background-color : white;border-top: 1px dashed #99ccff;">'+dataValue_out[0]+'</td></tr>';			
//		}
//		else{
//			appendName+='<tr><td height="28"  style="background-color : #D5EAFF;"></td><td height="28" align="center"  style="background-color : white;">'+dataValue_out[i]+'</td></tr>';	
//		}
//	}
//	if(dataName_in!=""){
//		appendName+='<tr><td height="28" align="center"  style="background-color : #D5EAFF;border-top: 1px dashed #99ccff;">'+dataName_in[0]+'</td>';		
//	}
//	for(var i in dataValue_in)
//	{
//		if(i==0){
//			appendName+='<td height="28" align="center"  style="background-color : white;border-top: 1px dashed #99ccff;">'+dataValue_in[0]+'</td></tr>';			
//		}
//		else{
//			appendName+='<tr><td height="28"  style="background-color : #D5EAFF;"></td><td height="28" align="center"  style="background-color : white;">'+dataValue_in[i]+'</td></tr>';	
//		}
//	}

	$('<table id="'+id.substring(1)+'" class="table">'+appendName+'</table>').css(
	{
		display: 'none',
		position : 'absolute',
		maxWidth: '550px',
		'word-break' : 'break-all',
		border : '1px solid #99ccff',
		padding : '2px',		
		lineHeight: '20px'
	}).appendTo(dialogId);
	
    var xPos = 10;
    var yPos = parseInt(y);

    if($('#patientList').scrollLeft()==0){
    	xPos= 0;
    	yPos = yPos - 98;
    }
    $(id).css("left", xPos+5);
    $(id).css("top", yPos+5);
	//设置提示信息x轴位置
	//$(id).css({width:'550px',maxWidth:'550px',height:'60px'});
	
	//淡入显示tip内容
	promptFadeIn(id);
}




