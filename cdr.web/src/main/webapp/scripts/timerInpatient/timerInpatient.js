/*三测单曲线图拐点序数，为了删除与添加鼠标浮动到拐点之上时显示的内容*/
var previousPoint = null;
/*三测单曲线图的初始化数据*/
var dataAll = null;
/*显示三测单曲线图列高度*/
var ledHeight = null;

/*
 * 整理三测单各个业务的数据
 */
function generateData(sourceData) 
{
	var dataSource = [];

	if (sourceData != null && sourceData.length != 0) 
	{
		for ( var i = 0; i < sourceData.length; i++) 
		{
			var dataArray = sourceData[i].split("-");
			
			//页面三测单各个业务曲线横坐标值
			dataArray[0] = stringAddZero(dataArray[0]);
			
			//页面三测单各个业务曲线纵坐标值
			dataArray[1] = stringAddZero(dataArray[1]);
			
			dataSource.push([ new Number(dataArray[0]),
					new Number(dataArray[1]), dataArray[2], dataArray[3] ]);
		}
	}

	return dataSource;
}

/*
 * 设置三测单各个业务初始化数据及页面线与点的样式及显示
 */
function setData(temData, bpLowData, bpHighData, pulseData) 
{
	dataAll = 
	[ {
		seriesid : "temp",
		data : temData, //体温初始化数据
		xaxis : 1, //使用的横坐标序数
		yaxis : 1, //使用的纵坐标序数
		lines : 
		{
			show : true //图形线的是否显示
		},
		points : 
		{
			symbol : "square", //图形拐点形状为矩形
			fill : true, //图形拐点是否填充
			fillColor : "red" //图形拐点填充的颜色
		},
		color : "red" //图形线与拐点边框显示颜失色
	}, 
	//以下内容注释同上
	{
		seriesid : "lowpressure",
		data : bpLowData,
		xaxis : 1,
		yaxis : 2,
		lines : 
		{
			show : false
		},
		points : 
		{
			symbol : "triangle_modify", //图形拐点形状为三角形
			fill : true,
			fillColor : "blue"
		},
		color : "blue"
	}, 
	{
		seriesid : "highpressure",
		data : bpHighData,
		xaxis : 1,
		yaxis : 2,
		lines : 
		{
			show : false
		},
		points : 
		{
			symbol : "triangle_del_modify", //图形拐点形状为倒三角形
			fill : true,
			fillColor : "blue"
		},
		color : "blue"
	}, 
	{
		seriesid : "pulse",
		data : pulseData,
		xaxis : 1,
		yaxis : 3,
		lines : 
		{
			show : true
		},
		points : 
		{
			symbol : "circle", //图形拐点形状为圆形
			fill : true,
			fillColor : "black"
		},
		color : "black"
	} ];
}

/*
 * 初始化图形显示参数并且开始画图形
 */
function initLegendViewParams() 
{
	var plot = $.plot($("#placeholder"), dataAll, 
	{
		series : 
		{
			points : 
			{
				show : true, //设置图形拐点是否显示
				radius : 2 //设置图形拐点大小
			}
		},
		grid : 
		{
			hoverable : true, //设置图形表格鼠标划动事件
			borderWidth : 1, //表格外边框宽度
			borderColor : "#99ccff" //表格外边框颜色
		},
		xaxes : 
		[ {
			position : 'bottom', //图形横坐标显示位置
			min : 0, //图形横坐标区间最小值
			max : 168, //图形横坐标最大值
			tickSize : 4 //图形横坐标画线间隔大小
		} ],
		yaxes : 
		[ {
			position : 'left', //图形纵坐标显示位置
			min : 35, //图形纵坐标区间最小值
			max : 43, //图形纵坐标区间最大值
			tickSize : 1 //图像纵坐标画线间隔大小 
		}, 
		//以下注释同上
		{
			position : 'left',
			min : 60,
			max : 220,
			tickSize : 20
		}, 
		{
			position : 'left',
			min : 40,
			max : 200,
			tickSize : 20
		} ]
	});
	
	// $Author : wu_jianfeng
	// $Date : 2013/04/18 13:52$
	// [BUG]0014990 ADD BEGIN
	//显示临床事件
	removeClinicalEvent();
	showClinicalEvent(plot);
	// [BUG]0014990 ADD END
}

// $Author : wu_jianfeng
// $Date : 2013/04/18 13:52$
// [BUG]0014990 ADD BEGIN
/*
 * 显示临床事件
 */
function showClinicalEvent(plot) {
	//得到三测单所有series
	var allSeries = plot.getData();
	var $placeholder = $("#placeholder");
	// $Author : tong_meng
	// $Date : 2013/11/27 17:03$
	// [BUG]0040006 MODIFY BEGIN
	var inpatientDay_entrance_time = $("input[name='inpatientDay_entrance_time']").val();
	var inpatientDay_status_count = $("input[name='inpatientDay_status_count']").val();
	var $inpatientDay = $("#inpatientDay_" + inpatientDay_entrance_time);
	//显示入院事件
	//入院第一天显示入院事件
	for(var index = 0; index < allSeries.length; index++) {
		var series = allSeries[index];
		if(series.seriesid === "temp"){
			var points = series.datapoints.points;
			/*if(points.length != 0) {*/
				var pageX = parseInt(series.xaxis.p2c(points[0]));
				var pageY = parseInt(series.yaxis.p2c(points[1]));					 
				var position = "top";
				var showArrow = false;
				if($inpatientDay.length){
					var admitTime = $inpatientDay.find("#inpatientDay_entrance_time div").html().substr(10);
					if(isNaN(pageX))
					{
						pageX = series.xaxis.p2c(admitTime.substring(0,admitTime.length-3)) + series.xaxis.p2c(24)*(inpatientDay_status_count%7 - 1);
					}
					if(isNaN(pageY))
					{
						pageY = 30;
					}
					$('<span></span>').tipTip(
						{					
							content: "入<br>院 <br>" + admitTime,
							showArrow: showArrow,
							defaultPosition: position,
							tipCss: "clinicalevent",
							offset: {x: pageX, y: pageY},
							alignTo : "#placeholder"
						}
					); 
				}
			/*}*/
		}		
	}
	// [BUG]0040006 MODIFY END
}
// [BUG]0014990 ADD END

// $Author :jin_peng
// $Date : 2012/12/20 13:52$
// [BUG]0012702 ADD BEGIN
/*
 * 图形曲线的显示或隐藏
 */
function showOrHideChart(vid)
{
	var vids = $("#" + vid);
	
	if(vids.attr("class") == 's')
	{
		vids.html(showFigure(vid,'hide'));
		
		vids.attr("class","h");
	}
	else
	{
		vids.html(showFigure(vid,'show'));
		
		vids.attr("class","s");
	}
}

/*
 * 图形显示或隐藏并设置显示后的标识图标
 */
function showFigure(vid,type)
{
	var res = '';
	
	if(vid == 'square')
	{
		if(type == 'show')
		{
			dataAll[0].data = temp;
			
			res = '■';
		}
		else
		{
			dataAll[0].data = [];
			
			res = '□';
		}
	}
	else if(vid == 'triangle')
	{
		if(type == 'show')
		{
			dataAll[1].data = bloodLow;
			
			dataAll[2].data = bloodHigh;
			
			res = '▲▼';
		}
		else
		{
			dataAll[1].data = [];
			
			dataAll[2].data = [];
			
			res = '△▽';
		}
	}
	else
	{
		if(type == 'show')
		{
			dataAll[3].data = pulse;
			
			res = '●';
		}
		else
		{
			dataAll[3].data = [];
			
			res = '○';
		}
	}
	
	initLegendViewParams();
	
	return res;
}
//[BUG]0012702 ADD END

/*
 * 设置鼠标划动到图形拐点时显示内容及其样式
 */
function showTooltip(x, y, contents) 
{
	$('<div id="tooltip">' + contents + '</div>').css(
	{
		display: 'none',
		position : 'absolute',
		top : y + 5,
		left : x + 5,
		border : '1px solid #fdd',
		padding : '2px',
		'background-color' : '#fee',
		opacity : 0.80
	}).appendTo("body");
	
	var tol = $("#tooltip");
	var tolWidth = tol.width();
	
	//当页面拐点里页面右边缘达到指定距离时，tip内容向左侧显示，以便查看
	if(document.body.offsetWidth - tolWidth - 10 < x)
	{
		tol.css("left",x - tolWidth - 5);
	}
	
	//淡出显示
	tol.fadeIn(200);
}

/*
 * 设置鼠标划动到指定标签时显示内容及其样式
 */
function showCommonToolTip(x, y, contents) 
{
	
	$('<div id="commontooltip">' + contents + '</div>').css(
	{
		display: 'none',
		position : 'absolute',
		top : y + 5,
		left : x + 5,
		maxWidth: '300px',
		'word-break' : 'break-all',
		border : '1px solid #99ccff',
		padding : '2px',
		'background-color' : '#D5EAFF',
		lineHeight: '20px'
	}).appendTo("body");
}

/*
 * 显示tip内容
 * */
function createTip(contents)
{
	var x = event.x;
	var y = event.y;
	
	// ie6情况下添加滚动条偏移量
	if(isIE6())
	{
		x = x + document.documentElement.scrollLeft;
		y = y + document.documentElement.scrollTop;
	}
	
	//创建tip内容对象
	showCommonToolTip(x, y, contents);
	
	//淡入显示tip内容
	promptDisplay();
}

/*
 * 删除tip内容
 * */
function removeTip()
{
	//删除tip内容
	$('#commontooltip').remove();
}

/*
 * 设置鼠标划动到指定标签时显示内容及其样式(检验异常项提示)
 */
function showLabToolTip(x, y, contents) 
{
	//初始化提示信息
	showCommonToolTip(x, y, contents);
	
	// $Author :jin_peng
    // $Date : 2012/08/23 14:10$
    // [BUG]0007863 MODIFY BEGIN
	//针对检验设置相关样式
	$("#commontooltip").css({maxWidth:'400px',maxHeight:'300px',overflow:'auto',top:y,left:x}).bind("mouseout",function(){removeLabTip();});
	// [BUG]0007863 MODIFY END
}

/*
 * 显示tip内容(长期用药医嘱)
 */
function showLongTermOrder(data)
{
/*	var x = event.x;
	var y = event.y;*/
	
	// chrome,firefox用pageX,pageY。 IE用后面那个。获取鼠标在页面中的位置，而不是鼠标在屏幕中的位置
	var x = event.pageX ? event.pageX : event.clientX + document.body.scrollLeft + document.documentElement.scrollLeft;
	var y = event.pageY ? event.pageY : event.clientY + document.body.scrollTop + document.documentElement.scrollTop;

	// ie6情况下添加滚动条偏移量
	if(isIE6())
	{
		x = x + document.documentElement.scrollLeft;
		y = y + document.documentElement.scrollTop;
	}
	/*Author:tong_meng BUG:0038314 MODIFY BEGIN Date:2013/10/23 15:44*/
	/*Author:yang_mingjie BUG:0044564 MODIFY BEGIN Date:2014/06/06 11:30*/
	var contents = "药物商品名：" + data.drugName + "( 药品名：" + data.name + "), 药品规格：" + data.specification+ ", 单次剂量：" + data.dosage + data.dosageUnit + ", 用药频率：" + data.medicineFreqName + ", 用药途径：" + data.routeName;
	/*Author:yang_mingjie BUG:0044564 MODIFY END Date:2014/06/06 11:30*/
	/*Author:tong_meng BUG:0038314 MODIFY END Date:2013/10/23 15:44*/
	
	showCommonToolTip(x,y,contents);
	
	//设置提示信息x轴位置
	$("#commontooltip").css({left:getDisplayPlace(x),width:'200px',maxWidth:'200px'});
	
	//淡入显示tip内容
	promptDisplay();
}

/*
 * 显示tip内容(长期用药医嘱)
 */
function getDisplayPlace(x)
{
	//获取当页面达到一定横坐标距离提示信息右边显示不下时提示信息向左显示，此为该分界点 
	var tabRightWidth = parseNumber(replaceContent($(".tabRightin").css("width"),"px","")) * 5 + 
		parseNumber(replaceContent($("#tabLeft").css("width"),"px",""));
	
	//当页面缩小到达一定值时需要的偏移量
	var xOffset = -10;
	
	//当鼠标达到页面的分界点时做以下x轴处理
	if(x > tabRightWidth)
	{
		//获取页面缩小后达到一定值（滚动条出现）时的偏移量
		xOffset = 205;
	}
	
	return x - xOffset;
}

/*
 * 显示tip内容(检查项目)
 */
function createExamTip(contents)
{
	// $Author :chang_xuewen
	// $Date : 2013/12/05 11:00$
	// [BUG]0040313 ADD BEGIN
	if(contents.indexOf("#") < 2 ){
		//alert($(contents).html());
		contents = $(contents).html();
	}
	// [BUG]0040313 ADD END
	// $Author:jin_peng
    // $Date:2012/11/01 16:03
    // $[BUG]0010908 ADD BEGIN
	if(contents.indexOf("NURSINGNONE") > -1)
	{
		return;
	}
	else if(contents.indexOf("FONT") < 0)
	{
		contents = "<table id=tblid style=border:0px; cellspacing=1 cellpadding=2 class=table ><tr><td>检查对应操作记录存在但显示内容：护理操作项目名称为空</td></tr></table>";
	}
	
	// $[BUG]0010908 ADD END
	
	//调用检验项目的tip处理方法，处理方法相同
	createLabTip(contents);
}
/*
 * 删除tip内容(检查项目)
 */
function removeExamTip()
{
	//调用检验项目的tip处理方法，处理方法相同
	removeLabTip();
}

/*
 * 显示tip内容(检验项目) 
 * */
function createLabTip(contents)
{
/*	//获取鼠标的x，y坐标值
	var x = event.x;
	var y = event.y;*/
	
	// chrome,firefox用pageX,pageY。 IE用后面那个。获取鼠标在页面中的位置，而不是鼠标在屏幕中的位置
	var x = event.pageX ? event.pageX : event.clientX + document.body.scrollLeft + document.documentElement.scrollLeft;
	var y = event.pageY ? event.pageY : event.clientY + document.body.scrollTop + document.documentElement.scrollTop;
	
	// ie6情况下添加滚动条偏移量
	if(isIE6())
	{
		x = x + document.documentElement.scrollLeft;
		y = y + document.documentElement.scrollTop;
	}
	
	// $Author:jin_peng
    // $Date:2012/11/01 16:03
    // $[BUG]0010908 ADD BEGIN
	if(contents.indexOf("LABNURSINGNONE") > -1)
	{
		return;
	}
	else if(contents.indexOf("FONT") < 0 && contents.indexOf("NURSINGVALIDATION") > -1)
	{
		contents = "<table id=tblid style=border:0px; cellspacing=1 cellpadding=2 class=table ><tr><td>检验对应操作记录存在但显示内容：护理操作项目名称为空</td></tr></table>";
	}
	
	// $[BUG]0010908 ADD END
	
	//创建检验tip内容对象
	showLabToolTip(x, y-7, contents);
	
	//设置提示信息x轴位置
	$("#commontooltip").css("left",getPromptX(x,contents));
	
	//淡入显示tip内容
	promptDisplay();
}

/*
 * 删除tip内容 (检验项目)
 * */
function removeLabTip()
{
	//获取异常显示内容对象
	var tip = $("#commontooltip");
	
	if(tip != undefined && tip != null)
	{
		try
		{
			//获取显示内容区域的最左横坐标
			var tipMinX = replaceContent(tip.css("left"),"px","");
			//获取显示内容区域的最上纵坐标
			var tipMinY = replaceContent(tip.css("top"),"px","");
			//获取显示内容区域的最右横坐标
			var tipMaxX = parseNumber(tipMinX) + parseNumber(replaceContent(tip.css("width"),"px",""));
			//获取显示内容区域的最下纵坐标
			var tipMaxY = parseNumber(tipMinY) + parseNumber(replaceContent(tip.css("height"),"px",""));
			//获取鼠标x，y值
			var x = event.x;
			var y = event.y;
			
			// ie6情况下添加滚动条偏移量
			if(isIE6())
			{
				x = x + document.documentElement.scrollLeft;
				y = y + document.documentElement.scrollTop;
			}
			
			//当鼠标不在提示内容范围内时，删除提示内容
			if(x < tipMinX || x > tipMaxX || y < tipMinY || y > tipMaxY)
			{
				//删除tip内容
				removeTip();
			}
			
		}catch(ex){}
	}
}

/*
 * 获取试调出的提示内容横坐标值
 * */
function getPromptX(x,contents)
{
	//x轴增量（当显示内容为纯文本时）
	var xAdd = -11;
	//获取当页面达到一定横坐标距离提示信息右边显示不下时提示信息向左显示，此为该分界点 
	
	var promptClass = '';
	
	if($(".tabRight").size > 0)
	{
		promptClass = ".tabRight";
	}
	else
	{
		promptClass = ".tabRightin";
	}
	
	var tabRightWidth = parseNumber(replaceContent($(promptClass).css("width"),"px","")) * 3.5 + 
		parseNumber(replaceContent($("#tabLeft").css("width"),"px",""));
	//当页面缩小到达一定值时需要的偏移量
	var xOffset = 0;
	
	// $Author :jin_peng
    // $Date : 2012/08/23 14:10$
    // [BUG]0007863 MODIFY BEGIN
	//当提示信息不为纯文本信息时需要算显示的横坐标
	if(contents.indexOf("table") >= 0)
	{
		//x轴增量（当显示内容不为纯文本时）
		xAdd = 1;
		//设置一个最小宽度
		$("#commontooltip").css("minWidth","300px");
		
		//当鼠标达到页面的分界点时做以下x轴处理
		if(x > tabRightWidth)
		{
			//x轴增量（当显示内容x轴超过分界点时）
			xAdd = -15;
			//获取页面缩小后达到一定值（滚动条出现）时的偏移量
			$("#commontooltip").css("left","400px");
			xOffset = parseNumber(replaceContent($("#commontooltip").css("width"),"px",""));
			
			//此为x轴坐标不再随页面缩小而变，页面内容宽度达到定值
			if(document.body.offsetWidth < 960)
			{
				xOffset = 400;
			}
		}
	}
	// [BUG]0007863 MODIFY END
	
	//返回处理后的x轴坐标值
	return x - xOffset - xAdd;
}

/*
 * 将字符值转换为数值
 * */
function parseNumber(v)
{
	var res = v;
	
	if(res != undefined && res != null && !isNaN(res))
	{
		res = new Number(res);
	}
	
	return res;
}

/*
 * 字符串指定值替换
 * */
function replaceContent(v,s,e)
{
	if(v != undefined && v != null)
	{
		return v.replace(s,e);
	}
	
	return v;
}

/*
 * 提示信息淡出显示
 * */
function promptDisplay()
{
	//淡入显示tip内容
	setTimeout("if($('#commontooltip') != undefined){$('#commontooltip').fadeIn(500)}",400 );
}

/*
 * 绑定鼠标划动事件并且设置显示内容及内容显示条件
 */
function initHoverContent() 
{
	$("#placeholder").bind(
			"plothover",
			function(event, pos, item) 
			{
				//标识鼠标是否移动到的图形拐点处
				if (item) {
					if (previousPoint != item.dataIndex) 
					{
						//拐点的序数
						previousPoint = item.dataIndex;

						$("#tooltip").remove();
						var x = item.datapoint[2], y = item.datapoint[1], unit = item.datapoint[3];

						//设置拐点显示内容
						showTooltip(item.pageX, item.pageY, y + unit + "/" + x );
					}
				} else 
				{
					$("#tooltip").remove();
					previousPoint = null;
				}
			});
}

/*
 * 当图形初始化数据位.##形式时在数据前添加0(这是由于数据库中的数值类型转换成字符型所致)
 */
function stringAddZero(str) 
{
	if (str != null && str != undefined) 
	{
		if (str.substring(str, 0, 1) == ".") 
		{
			str = "0" + str;
		}
	}
	return str;
}

/*
 * 隐藏图形横纵坐标div部分的显示内容
 */
function dropXYLabel() 
{
	$(".tickLabels").hide();
}

/*
 * 处理横坐标显示的小时数，此方法待用
 */
function getHour(h) 
{
	if (h >= 0 && h < 24) 
	{
		return new Number(h);
	}

	if (h >= 24 && h < 48) 
	{
		return h - 24;
	}

	if (h >= 48 && h < 72) 
	{
		return h - 48;
	}

	if (h >= 72 && h < 96) 
	{
		return h - 72;
	}

	if (h >= 96 && h < 120) 
	{
		return h - 96;
	}

	if (h >= 120 && h < 144) 
	{
		return h - 120;
	}

	if (h >= 144 && h < 168) 
	{
		return h - 144;
	}

}

/*
 * 初始化设置住院视图页面布局及内容先睡尺寸
 */
function setDocumentSize(evt) 
{
	//页面可见区域宽度
	var availWidth = document.body.offsetWidth;
	
	//当页面可见区域宽度小于给定值时出现滚动条并保持给定值的宽度
	if (availWidth < 960) 
	{
		availWidth = 960;
	}

	//给定页面第二个表格中最左侧一格的宽度
	var tabLeftWidth = 170;
	//给定页面第二个表格中每个最小单元宽度
	var perTdWidth = Math.floor((availWidth - tabLeftWidth) / 42);
	//给定页面第二个表格中右侧每个大格宽度，每6个最小单元格为一个大格
	var tabRightWidth = perTdWidth * 6;
	
	//$Author :jin_peng
	// $Date : 2012/11/06 17:38$
	// [BUG]0011026 MODIFY BEGIN
	//$Author :jin_peng
	// $Date : 2012/10/08 14:16$
	// [BUG]0010132 MODIFY BEGIN
	//初始设置页面警告div宽度
	var reAllerWidth = availWidth - 759;
	// [BUG]0010132 MODIFY END
	// [BUG]0011026 MODIFY END
	
	//$Author :jin_peng
	// $Date : 2012/12/24 17:38$
	// [BUG]0012698 MODIFY BEGIN
	// 获取选中条宽度
	$("#seleSlide").css("width", tabRightWidth-20);
	
	//设置页面显示两个表格整体宽度
	$(".tlin").css("width", availWidth);
	
	//设置页面最上部分警告栏宽度
	$(".reAllerin").css("width",reAllerWidth);

	//设置页面有第二个表格中右侧大格宽度，每6个最小单元格为一个大格
	$(".tabRightin").css("width", tabRightWidth);

	//设置页面第二个表格中最小表格单元宽度
	$(".perTdin").css("width", perTdWidth);
	
	if($("#threeItemContentt").size() > 0)
	{
		//显示图形外套div宽度
		$("#pl").css("width", perTdWidth * 42 + 5);
	}
	
	// 重置选中条定位
	if(seleCt != null)
	{
		$("#seleSlide").css("marginLeft",seleSlideDistance(seleCt));
	}
	
	// [BUG]0012698 MODIFY END

	// 显示图形div宽度增量
	var addWidthIn = 40;
	
	// ie浏览器设置图形宽度
	if(navigator.userAgent.indexOf("MSIE") > 0)
	{
		addWidthIn = 48;
	}
	
	// ie6做特殊调整
	if(isIE6())
	{
		addWidthIn = 47;
	}
	
	if($("#threeItemContentt").size() > 0)
	{
		//显示图形div宽度
		$("#placeholder").css("width", perTdWidth * 42 + addWidthIn);
		
		//当页面事件为resize时设置图形及图形外套div高度，主要避免页面resize时高度出现0的情况
		if(evt != "resize")
		{
			//取得页面图形显示列的高度
			ledHeight = $("#led").css("height");
			//设置图形显示外套div的高度
			$("#pl").css("height", ledHeight);
			//设置图形div的高度
			$("#placeholder").css("height", parseInt(ledHeight) + 9);
		}
	}
}

/*
 * 初始化设置时间轴视图页面布局及内容先睡尺寸
 */
function setTimerDocumentSize()
{
	//页面可见区域宽度
	var availTimerWidth = document.body.offsetWidth - 0;
	
	//当页面可见区域宽度小于给定值时出现滚动条并保持给定值的宽度s
	if (availTimerWidth < 960) 
	{
		availTimerWidth = 960;
	}
	
	//给定页面第二个表格中最左侧一格的宽度
	var tabLeftTimerWidth = 155;
	//给定页面第二个表格中除左侧第一个表格以外的右侧表格每个单元的宽度
	var tabRightTimerWidth = Math.floor((availTimerWidth - tabLeftTimerWidth) / 7) - 3;
	//初始设置页面警告div宽度
	var reAllerTimerWidth = availTimerWidth - 430;
	
	//设置页面显示两个表格整体宽度
	$(".tl").css("width", availTimerWidth);
	
	//设置页面第二个表格左侧第一个表格宽度
	$(".tabLeft").css("width", tabLeftTimerWidth);
	
	//设置页面第二个表格中除左侧第一个表格以外的右侧表格每个单元的宽度
	$(".tabRight").css("width", tabRightTimerWidth);
	
	//设置页面警告栏的宽度
	$(".reAller").css("width",reAllerTimerWidth);
}

/*
 * 设置时间轴视图和住院视图的折叠效果
 */
function hideOrShowContent(blockName)
{
	var showFlag = $("#show_" + blockName).val();
	var title = $("#" + blockName + "Title");
	var content = $("#" + blockName + "Contentt");
	if(showFlag == "1")
	{
		content.hide();
		title.fadeIn();
		title.find("img").focus();
		$("#show_" + blockName).val("0");
		
		if(blockName == "threeItem")
		{
			$("#im").css("marginLeft","20px");
			
			$("#inpDs").css("paddingLeft","28px");
		}
	}	
	else
	{
		title.hide();
		content.fadeIn();
		$("#show_" + blockName).val("1");
		content.find("img").focus();
		
		if(blockName == "threeItem")
		{
			$("#im").css("marginLeft","0px");
			
			$("#inpDs").css("paddingLeft","10px");
		}
		
		if(blockName == "longTermDrug")
		{
			// ie浏览器设置图形宽度
			if(navigator.userAgent.indexOf("MSIE") <= 0)
			{
				// 调整长期药物医嘱曲线定位
				longTermResize();
			}
		}
	}		
}

/*
 * 阻止事件冒泡
 */
function stopBubble(event) {
    //如果提供了事件对象，则这是一个非IE浏览器
    if ( event && event.stopPropagation )
	{
    	//因此它支持W3C的stopPropagation()方法
    	event.stopPropagation();
	}
    else
	{
    	//否则，我们需要使用IE的方式来取消事件冒泡
        event.cancelBubble = true;
	}
}

//$Author:bin_zhang
// $Date : 2012/12/25 10:30
// $[BUG]0012694 ADD BEGIN
function hideOrShowAllContent(blockName)
{
 var showFlag = $("#show_"+blockName).val();
 
 if(showFlag=="1")
 {
	 $("[id^='show_']").val(0);
	 $("[id$='Title']").fadeIn();
	 $("[id$='Contentt']").hide();
	 
	 if($("#seleSlide").size() > 0)
	 {
		 $("#im").css("marginLeft","21px");
		 $("#inpDs").css("paddingLeft","29px");
	 }
	 
	 $("#im").attr("src","../images/tree_folder1.gif");
 }
 else
 {
	 $("[id^='show_']").val(1);
	 $("[id$='Title']").hide();
	 $("[id$='Contentt']").fadeIn();
	 
	 if($("#seleSlide").size() > 0)
	 {
		 if(isExpanded)
		 {
			 $("#im").css("marginLeft","22px");
			 $("#inpDs").css("paddingLeft","29px");
		 }
		 else
		 {
			 if($("#threeItemContentt").size() > 0)
			 {
				 $("#im").css("marginLeft","0px");
				 $("#inpDs").css("paddingLeft","10px");
			 }
			 else
			 {
				 $("#im").css("marginLeft","21px");
				 $("#inpDs").css("paddingLeft","29px");
			 }
		 }
	 }
	 
	 $("#im").attr("src","../images/tree_folder2.gif");
	 
	// ie浏览器设置图形宽度
	if(navigator.userAgent.indexOf("MSIE") <= 0)
	{
		// 调整长期药物医嘱曲线定位
		longTermResize();
	}
 }
 
 stopBubble(window.event);
}
// $[BUG]0012694 ADD END
//$Author :jin_peng
// $Date : 2012/10/08 14:16$
// [BUG]0010132 ADD BEGIN
/*
 * 判断住院开始时间不能大于住院结束时间
 */
function searchInpatient(url, formName, id, isAddHistory)
{
	var inpatientStartDate = $("#inpatientStartDate").val();
    var inpatientEndDate = $("#inpatientEndDate").val();
    var attrDisabled = $("#dischargeDateAndVisitSn").attr("disabled");
	if(attrDisabled == true)
	{
		showMsg("提示","正在加载就诊日期选择列表，请稍后。。。");
		return false;
	}
	
	var visitSn = $("#dischargeDateAndVisitSn").val();
	if($.trim(visitSn) == "")
	{
		showMsg("提示","请选择住院（日期）记录");
		return false;
	}
    
    if(inpatientStartDate != "" && inpatientEndDate != "" && inpatientStartDate > inpatientEndDate)
    {
    	showMsg("提示","住院开始时间不能大于住院结束时间！");
    	return;
    }
    
    parent.search(url, formName, id, isAddHistory);
}
//[BUG]0010132 ADD END

//$Author :jin_peng
//$Date : 2012/11/05 17:00$
//[BUG]0011016 ADD BEGIN
/*
* 验证（如果查询条件选择就诊日期）该就诊日期后是否存在就诊记录，如果存在则进行记录查询显示，如果不存在则提示相关信息并不再做相应记录查询显示操作
*/
function searchTimer(url, formName, id, patientSn, isAddHistory)
{
	// 获取就诊日期
	var visitDate = $("#visitDate").val();
	 
	// 如果就诊日期存在则验证是否存在查询记录
	if(visitDate != "" && visitDate != null)
	{
		// 获取相应就诊日期后是否存在记录数
		$.ajax({url:'../timer/validationList_' + patientSn + '.html',
			type:'post',
			dataType:'text',
			data:{visitDate:visitDate},
			success:function(data)
			{
				// 获取就诊日期后是否存在就诊记录
				var count = parseNumber(data);
				
				// 就诊记录存在时正常查询就诊记录
				if(count > 0)
				{
					// 查询相应就诊记录
					search(url, formName, id, isAddHistory);
				}
				else
				{
					// 就诊日期后不存在就诊记录给予提示信息
					showMsg("提示","该日期后不存在就诊记录");
					
					// 查询条件置空
					$("#visitDate").val('');
				}
			},
			error:function(data){
				showMsg("错误","可能系统出现问题，请稍后重试！");
				}
		});
	}
	else
	{
		// 正常查询就诊记录
		search(url, formName, id, isAddHistory);
	}
}
//[BUG]0011016 ADD END

//$Author :jin_peng
//$Date : 2012/10/23 16:46$
//[BUG]0010132 ADD BEGIN TODO
function isIE6()
{
	if($.browser.version == '6.0')
	{
		return true;
	}
	
	return false;
}
//[BUG]0010132 ADD BEGIN

//$Author :jin_peng
//$Date : 2012/12/24 13:36$
//[BUG]0012698 ADD BEGIN
// 是否已伸展页面
var isExpanded = false;
// 选中日期标识
var seleVid;
// 上一次执行动画元素
var previousAnimate = null;
// 选中日期序号
var seleCt = null;
// 某天是否已加载各具体业务内容
var loadTargetCompleted = "";

/*
* 显示具体业务内容
*/
function executeSpecificity(vid,ct,inpatientDate,visitSn)
{
	// 停止正在执行的上次未执行完成的动画
	if(previousAnimate != null)
	{
		$("." + previousAnimate).stop(true);
	}
	
	var s = parseNumber(replaceContent($("#tabLeft").css("width"),"px",""));
	
	// 调整展开图形位置
	$("#im").css("marginLeft","22px");
	$("#inpDs").css("paddingLeft","30px");
	
	// 隐藏三测单
	$(".threeItemContent").hide();
	$(".threeItemContent").attr("id","h");
	
	// 隐藏三测单简易部分
	$(".threeItemTitle").hide();
	$(".threeItemTitle").attr("id","h");
	
	// 显示日期选中框
	$("#seleSlide").show();
	
	// 隐藏具体业务内容
	$(".cont" + ct).hide();
	
	// 展示加载标识
	$(".loading" + ct).show();
	
	for(var i=1; i<=7; i++)
	{
		$(".inpDt" + i).stop(true);
		
		// 改变所有业务显示宽度
		$(".inpDt" + i).attr("colSpan","1");
		
		// 隐藏所有原始显示内容
		$(".inpDtItem" + i).hide();
		
		// 隐藏所有具体业务显示内容
		$(".inpDtItemShow" + i).hide();
	}

	// 展示选中日期的所有显示内容
	$(".inpDtItemShow" + ct).show();
	
	// 加载拥有具体内容的各具体业务内容
	if(loadTargetCompleted.indexOf(ct) < 0)
	{
		// 将已加载的业务内容加入已加载完成标识中
		loadTargetCompleted += ct;
		
		// 获取参数信息
		var data = {"inpatientDate" : inpatientDate,"visitSn" : visitSn};
		
		if($("#specificallyDiagnosis" + ct).size() > 0)
		{
			// 加载某天具体诊断内容
	 		loadSpecificallyBusinesses("../inpatient/SpecificallyDiagnosis.html", data, "#specificallyDiagnosis" + ct);
		}
		
		if($("#specificallyLongDrug" + ct).size() > 0)
		{
			// 加载某天具体长期药物医嘱内容
	 		loadSpecificallyBusinesses("../inpatient/specificallyLongDrug.html", data, "#specificallyLongDrug" + ct);
		}
		
		if($("#specificallyTempDrug" + ct).size() > 0)
		{
			// 加载某天具体临时药物医嘱内容
	 		loadSpecificallyBusinesses("../inpatient/specificallyTempDrug.html", data, "#specificallyTempDrug" + ct);
		}
		
		if($("#specificallyExam" + ct).size() > 0)
		{
			// 加载某天具体检查内容
	 		loadSpecificallyBusinesses("../inpatient/specificallyExam.html", data, "#specificallyExam" + ct);
		}
		
		if($("#specificallyLab" + ct).size() > 0)
		{
			// 加载某天具体检验内容
	 		loadSpecificallyBusinesses("../inpatient/specificallyLab.html", data, "#specificallyLab" + ct);
		}
		
		if($("#specificallyProcedure" + ct).size() > 0)
		{
			// 加载某天具体手术内容
	 		loadSpecificallyBusinesses("../inpatient/specificallyProcedure.html", data, "#specificallyProcedure" + ct);
	 		
		}
		
		// $Author :chang_xuewen
	    // $Date : 2013/10/24 11:00$
	    // [BUG]0038443 ADD BEGIN
		if($("#specificallyProceduredoc" + ct).size() > 0)
		{
			// 加载某天具体手术相关病历文书内容
			loadSpecificallyBusinesses("../inpatient/specificallyProceduredoc.html", data, "#specificallyProceduredoc" + ct);
	 		
		}
	    // [BUG]0038443 ADD END
		
		if($("#specificallyDocument" + ct).size() > 0)
		{
			// 加载某天具体病例文书内容
	 		loadSpecificallyBusinesses("../inpatient/specificallyDocument.html", data, "#specificallyDocument" + ct);
		}
		
		if($("#specificallyNoneDrug" + ct).size() > 0)
		{
			// 加载某天具体非药物医嘱内容
	 		loadSpecificallyBusinesses("../inpatient/specificallyNoneDrug.html", data, "#specificallyNoneDrug" + ct);
		}
	}
	
	//$("#tabLeft").css("width",s - 50);
	
	$("." + vid).stop(true);
	
	// 展开选中日期的显示内容
	$("." + vid).animate( { colSpan : 36}, 800).animate({width:0},400,function()
	{
		// 隐藏业务加载标识
		$(".loading" + ct).hide();
		
		// 展示某天相应业务内容
		$(".cont" + ct).show();
	});
	
	// 停止未执行完成的选中框移动
	$("#seleSlide").stop(true);
	
	// 选中框移动至目标位置
	$("#seleSlide").animate( 
			{marginLeft : seleSlideDistance(ct) + 2, opacity : 1}, 400).animate({marginLeft : seleSlideDistance(ct) - 4},100).animate(
					{marginLeft : seleSlideDistance(ct) + 2},100).animate({marginLeft : seleSlideDistance(ct)},200,
							function()
							{
								$("#seleSlide").animate({marginLeft : seleSlideDistance(ct)},300);
							});
	
	// 设置选中序号
	seleCt = ct;
	
	// 设置上次选中元素标识
	previousAnimate = vid;
	
	// 设置选中内容标识
	seleVid = vid;
	
	// 是否已延展标识
	isExpanded = true;
}

/*
* 加载指定具体业务内容
*/
function loadSpecificallyBusinesses(url, data, vid)
{
	//解决IE浏览器缓存问题
	var a = Math.random();
	if(url.indexOf("?")>0)
	{
		url = url+"&rand="+a;
	}
	else
	{
		url = url+"?rand="+a;
	}
	
	jQuery(vid).load(url, data, function(response, status, xhr) 
	{
		//如果状态是error或者timeout, 显示错误对话框
		if(status == "error" || status == "timeout")
		{
			setTimeout("showErrorDialog('#errorDialog', '" + xhr + "','" + status + "')",70);
		}
	});
}

/*
* 获取选中框定位位置
*/
function seleSlideDistance(ct)
{
	// 获取最左侧列宽度
	var tabLeftWidth = parseNumber(replaceContent($("#tabLeft").css("width"),"px",""));
	
	// 获取选中目标前的列宽度并按一定增量增加距离
	var tabRightWidth = parseNumber(replaceContent($("#inpDt1").css("width"),"px","")) * (parseNumber(ct) - 1) + 
							(12 + 0.7 * (parseNumber(ct) - 1));
	
	return tabLeftWidth + tabRightWidth;
}

/*
* 恢复住院视图为原始内容
*/
function backShow()
{
	// 是否已延展，如果已延展则进行还原操作
	if(!isExpanded)
	{
		return;
	}
	
	// 调整展开图形位置
	$("#im").css("marginLeft","22px");
	
	// 将选中的目标还原成最小单元
	$("." + seleVid).attr("colSpan","1");
	
	// 停止为执行完成的动画效果
	$("." + seleVid).stop(true);
	
	// 隐藏具体业务内容
	$(".inpDtItemShow1").hide();
	$(".inpDtItemShow2").hide();
	$(".inpDtItemShow3").hide();
	$(".inpDtItemShow4").hide();
	$(".inpDtItemShow5").hide();
	$(".inpDtItemShow6").hide();
	$(".inpDtItemShow7").hide();
	
	// 还原原始视图状态
	$(".inpDt1").delay(0).animate( { colSpan : 6}, 520);
	$(".inpDt2").delay(50).animate( { colSpan : 6}, 520);
	$(".inpDt3").delay(100).animate( { colSpan : 6}, 520);
	$(".inpDt4").delay(150).animate( { colSpan : 6}, 520);
	$(".inpDt5").delay(200).animate( { colSpan : 6}, 520);
	$(".inpDt6").delay(250).animate( { colSpan : 6}, 520);
	$(".inpDt7").delay(300).animate( { colSpan : 6}, 520);
	$(".inpDt7").delay(250).animate( { colSpan : 6}, 0,function () 
	{
		$("#seleSlide").hide();
		//展示三测单简易内容
		$(".threeItemTitle").show().attr("id","threeItemTitle");
		
		$(".threeItemContent").hide().attr("id","threeItemContentt");

		// 将三测单收起。
		$("#show_threeItem").val("0");
		
		// 展示原始内容
		$(".inpDtItem1").show();
		$(".inpDtItem2").show();
		$(".inpDtItem3").show();
		$(".inpDtItem4").show();
		$(".inpDtItem5").show();
		$(".inpDtItem6").show();
		$(".inpDtItem7").show();
		
		// ie浏览器设置图形宽度
		if(navigator.userAgent.indexOf("MSIE") <= 0)
		{
			// 调整长期药物医嘱曲线定位
			longTermResize();
		}
	});
	
	// 设置选中框消失
	$("#seleSlide").animate({marginLeft:0,opacity:0},500);
	
	// 设置是否已延展属性为否
	isExpanded = false;
}

function expandedDetail(id)
{
	$("#"+id).slideToggle(500);
	
	stopBubble(event);
}

//[BUG]0012698 ADD END

// $Author : wu_jianfeng
// $Date : 2013/04/19 13:52$
// [BUG]0014990 ADD BEGIN
function removeClinicalEvent()
{
	$("#placeholder").find(".tiptip_holder").remove();
}
// [BUG]0014990 ADD END


//$Author : tong_meng
//$Date : 2013/10/08 11:00$
//[BUG]0037154 ADD BEGIN

function ajaxOrderSn(patientSn, type, visitSn, approvedDrugName, longTempFlag,
		cancelOrderStatus, inpatientDate) 
{
	$.ajax({url:'../timer/getMedicalOrderSn_' + patientSn + '.html',
			type:'post',
			dataType:'text',
			data : {
				type : type,
				visitSn : visitSn,
				longTempFlag : longTempFlag,
				cancelOrderStatus : cancelOrderStatus,
				inpatientDate : inpatientDate
			},
			success:function(data)
			{
				loadCommonPanel('长期药物医嘱',{
					'url' : '../drug/detail_' + data + '.html?flag=tabs',
					'tabsFlag' : 'true',
					'gotoType' : 'drug',
					'width' : '70%',
					'patientSn' : patientSn,
					'orderSn' : data,
					'trID' : data,
					'type' : type,
					'visitSn' : visitSn,
					'name' : approvedDrugName,
					'inpatientLongTermDate' : inpatientDate,
					'longTempFlag' : longTempFlag,
					'cancelOrderStatus' : cancelOrderStatus
				});
			},
			error:function(data)
			{
				showMsg("错误","可能系统出现问题，请稍后重试！");
			}
	});
}
//[BUG]0037154 ADD END

//$Author :chang_xuewen
//$Date : 2013/11/13 14:50$
//[BUG]0038443 ADD BEGIN
/*function grepProDocNum()
{
	$(".pro_doc_con").each(function(){
		if($(this).find(".pro_cell").length > 5){
			$(".doc_loop").hide();
			$(".doc_more").hide();
		}
		else if(($(this).find(".pro_cell").length + $(this).find(".doc_cell").length) > 5){
			
			$(".doc_loop :gt("+$(this).find(".doc_cell").length+")").hide();
		}
	});
}*/
//[BUG]0038443 ADD END

//$Author :chang_xuewen
//$Date : 2013/12/30 14:50$
//[BUG]0040770 ADD BEGIN
function obtainVisitDate_inp(orgCode,patientSn)
{
	$("#dischargeDateAndVisitSn").attr("disabled",true);
	
	$("#relatedItem").load("../inpatient/obtainVisitDateCondition.html", {"orgCode":orgCode,"patientSn":patientSn},
		function(response, status, xhr) 
	    {
			if(status == "success")
			{
				if(orgCode != null && orgCode.length != 0 && orgCode != "-1")
				{
					$("#relatedItem").css("width","200px");
				}
				else
				{
					$("#relatedItem").css("width","250px");
				}
				
//				adjustingPage();
			}
		
	        //如果状态是error或者timeout, 显示错误对话框
			else if(status == "error" || status == "timeout")
	        {
	        	setTimeout("showErrorDialog('#errorDialog', '" + xhr + "','" + status + "')",70);
	        }
	    });
}
//[BUG]0040770 ADD END