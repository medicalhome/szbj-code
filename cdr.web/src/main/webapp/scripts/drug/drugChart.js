//$Author:jin_peng
// $Date:2013/01/04 10:38
// $[BUG]0012888 ADD BEGIN
var dataPie = null;
var dataTrenderAmount = null;
var dataPerCount = null;
var finishedFlip = null;
var patientSn = null;
var firstDrugName = null;
var d = 0;
var s = null;

/*
 * 
 */
function listFlip(vid)
{
	if(finishedFlip == vid)
	{
		return;
	}
	
	$("#chartId").flip(
	{
		direction:'rl',
		color: '#ffffff',
		onEnd: function()
		{
			$("#chartId").hide();
			$("#listId").show();
			finishedFlip = vid;
			$("#listId").removeClass("turnHeight");
		}
	});
}

/*
 * 
 */
function chartFlip(vid,dataPieTotal,dataPieSource)
{
	if(finishedFlip == vid)
	{
		return;
	}
	
	$("#listId").addClass("turnHeight");
	
	$("#listId").flip(
	{
		direction:'lr',
		color: '#ffffff',
		onBegin: function()
		{
			
		},
		onEnd: function()
		{
			$("#listId").hide();
			$("#chartId").show();
		
			if(s != 1)
			{
				loadChart("../drug/pieChart_" + getPatientSn() + ".html",{},'#pieId');
				
				s = 1;
			}
			else
			{
				if(isIE6())
	    		{
					window.setTimeout("$('.base').first().css('width','100%');drawPieChart();adjustPromptFont();",0);
	    		}
				else
				{
					drawPieChart();
					
					adjustPromptFont();
				}
			}
			
			finishedFlip = vid;
		}
	});
}

function structureData(dataPieTotal,dataPieSource)
{
	var displayCount = 0;
	
    dataPie = [];
	
	if (dataPieSource != null && dataPieSource.length != 0) 
	{
		for(var i = 0; i < dataPieSource.length; i++)
		{
			var pieDisplayContent = dataPieSource[i].split("-");
			
			var pieData = new Number(pieDisplayContent[1]);
			
			dataPie.push({label: pieDisplayContent[0], data: (pieData + 0)});
			
			displayCount += pieData;
		}
		
		var othersCount = dataPieTotal - displayCount;
		
		if(othersCount > 0)
		{
			dataPie.push({label: "其他", data: othersCount});
		}
		
		firstDrugName = dataPieSource[0].split("-")[0];
	}
}

function drawPieChart()
{
	d = 0;
	
	$.plot($("#graphPie"), dataPie, 
	{
		series: 
		{
			pie: 
			{ 
				show: true,
				radius: 0.9,
				innerRadius: 0,
				startAngle: 0,
				tilt: 1,
				offset: 
				{
					top: 0,
					left: -40
				},
				stroke: 
				{
					color: "#fff",
					width: 0.5
				},
				label: 
				{
					show: true,
					radius: 0.7,
					formatter: function(label, series)
					{
						var displayLabel = '<div style="font-size:8pt;text-align:center;padding:2px;color:white;cursor:default" onclick=\"pieClickDiv(\'' + label + '\')\">'+Math.round(series.percent)+'%</div>';
						
						var otherLabel = '<div style="font-size:8pt;text-align:center;padding:2px;color:white;">'+Math.round(series.percent)+'%</div>';
						
						if(label == "其他")
						{
							var otherLabel = '<div style="font-size:8pt;text-align:center;padding:2px;color:white;">'+ (100 - d) +'%</div>';
							
							return otherLabel;
						}
						
						d += Math.round(series.percent);
						
						return displayLabel;
					},
					background: 
					{ 
						//color: "#cc0000",
						opacity: 0
					},
					threshold: 0
				},
				highlight: 
				{
					color: "#000000",
					opacity: 0.5
				}	
			}
		},
		legend: 
		{
			show: true
		},
		grid:
		{
			hoverable:true,
			clickable:true
		}
	});
}

var dataTimeLabel = null;

function structureTrenderData(dataTrendSource,approvedDrugName)
{
	dataTrenderAmount = [];
	dataPerCount = [];
	dataTimeLabel = [];
	
	if(dataTrendSource != null && dataTrendSource.length != 0)
	{
		for(var i = 0; i < dataTrendSource.length; i++)
		{
			var dataSource = dataTrendSource[i].split(";");
			
			var totalDosage = new Number(dataSource[1]);
			
			dataTimeLabel.push(dataSource[0]);
			
			if(approvedDrugName != "草药")
			{
				var dosage = new Number(dataSource[2]);
				
				dataTrenderAmount.push([(i*2/10), dosage, approvedDrugName, dataSource[4], totalDosage, dataSource[3]]);
			}
			else
			{
				dataTrenderAmount.push([(i*2/10), totalDosage, approvedDrugName]);
			}
		}
	}
}

function drawTrenderChart()
{
	$.plot($("#flotTrender"), [ dataTrenderAmount, dataPerCount], 
	{
        series: 
        {
            stack: null,
            lines: 
            { 
            	show: false, 
            	fill: true, 
            	steps: false 
            },
            bars: 
            { 
            	show: true, 
            	barWidth: 0.15 
            }
        },
        xaxes : 
		[ {
			position : 'bottom', //图形横坐标显示位置
			min : 0, //图形横坐标区间最小值
			max : 2, //图形横坐标最大值
			tickSize : 0.2 //图形横坐标画线间隔大小
		} ],
        grid:
    	{
        	hoverable:true
    	}
    });
}

/*
 * 绑定鼠标划动事件并且设置显示内容及内容显示条件
 */
function initPieHoverContent() 
{
	$("#graphPie").bind(
			"plothover",pieHover);
	
	$("#graphPie").bind(
			"plotclick",pieClick);
}

function initTrendHoverContent()
{
	$("#flotTrender").bind(
			"plothover",trenderHover);
}

var previousPoint = null;

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
		'background-color' : '#ff6600',
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

function pieHover(event, pos, obj) 
{
	if(!obj)
	{
		$("#tooltip").remove();
		previousPoint = null;
		return;
	}
	
	//percent = parseFloat(obj.series.percent).toFixed(2);
	var percentPie = 0;
	
	if(obj.series.label != "其他")
	{
		percentPie = Math.round(obj.series.percent);
	}
	else
	{
		percentPie = 100 - d;
	}
	
	//标识鼠标是否移动到的图形拐点处
	if (obj) 
	{
		//$Author :jin_peng
		// $Date : 2013/01/23 11:38$
		// [BUG]0012888 MODIFY BEGIN
		if (previousPoint != obj.series.label) 
		{
			//拐点的序数
			previousPoint = obj.series.label;

			$("#tooltip").remove();

			//设置拐点显示内容
			showTooltip(pos.pageX + 8, pos.pageY, obj.series.label + "：" + percentPie + "%" );
		}
		// [BUG]0012888 MODIFY END
	}
}

function pieClick(event, pos, obj) 
{
	if (!obj)
	{
		return;
	}
	
	if(obj.series.label != "其他")
	{
		loadChart("../drug/trendChart_" + getPatientSn() + ".html",{approvedDrugName:obj.series.label},'#trenderId');
	}
}

function pieClickDiv(label)
{
	loadChart("../drug/trendChart_" + getPatientSn() + ".html",{approvedDrugName:label},'#trenderId');
}

function trenderHover(event, pos, obj)
{
	//标识鼠标是否移动到的图形拐点处
	if (obj) 
	{
		var ds = obj.datapoint[1];
		
		if (previousPoint != ds) 
		{
			//拐点的序数
			previousPoint = ds;

			$("#tooltip").remove();

			var dss = null;
			
			if(obj.datapoint[3] == "草药")
			{
				dss = ds;
			}
			else
			{
				dss = "<font style='font-weight:bold'>总剂量</font>：" + (filterNull(obj.datapoint[5]) == null ? "" : obj.datapoint[5]) + 
					(filterNull(obj.datapoint[6]) == null ? "" : " " + obj.datapoint[6]) + "，<font style='font-weight:bold'>次剂量</font>：" + 
					ds + (filterNull(obj.datapoint[4]) == null ? "" : " " + obj.datapoint[4]);
			}
			
			//设置拐点显示内容
			showTooltip(pos.pageX + 5, pos.pageY, dss);
		}
	}
	else
	{
		$("#tooltip").remove();
		previousPoint = null;
	}
}

function filterNull(s)
{
	var res = null;
	
	if(s != undefined && s != null && s != "null" && s != "")
	{
		res = s;
	}
	
	return res;
}

/*
* 加载指定图形内容
*/
function loadChart(url, data, vid, isFirst)
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
	
	if(vid == "#trenderId" && !isFirst)
	{
		showLoadingScreen(
		{
			loadingScreenId: "loadingScreenChart", 
			loadingMessageId: "loadingMessageChart",
			modal: true
		});
	}
	
	jQuery(vid).load(url, data, function(response, status, xhr) 
	{
		//如果状态是error或者timeout, 显示错误对话框
		if(status == "error" || status == "timeout")
		{
			if(!isFirst)
			{
				// 关闭加载提示
				chartCloseLoadingScreen(
				{
					loadingScreenId: "loadingScreenChart", 
					loadingMessageId: "loadingMessageChart"
				});
			}
			
			setTimeout("showErrorDialog('#errorDialog', '" + xhr + "','" + status + "')",70);
			
			return;
		}
		
		if(vid == "#trenderId")
		{
			$(".xAxis").find(".tickLabel").each(function(i)
			{
				var dataLabel = dataTimeLabel[i];
				
				$(this).html(dataLabel == undefined ? "" : dataLabel);
			});
			
			drawPieChart();
			
			adjustPromptFont();
			
			if(!isFirst)
			{
				// 关闭加载提示
				chartCloseLoadingScreen(
				{
					loadingScreenId: "loadingScreenChart", 
					loadingMessageId: "loadingMessageChart"
				});
			}
		}
		else if(vid == "#pieId")
		{
			//$("#fFont").html("请选择相应药品");
		}
	});
}

function chartCloseLoadingScreen(settings) 
{
	$("#loadingScreenChart").remove();
	$("#chartLoading").append('<div id="loadingScreenChart" style="display: none;background-color:#ffffff"><div id="loadingMessageChart" style="font-size:110%;color:#FF8C10;font-weight:bold;padding-left:20px;line-height:36px">数据加载中，请稍候...</div></div>');
}

/**
 * 翻页处理
 */
function jumpToChartPage(pageno,id)
{
	var form = $("[name='trenderPagingform']");
	
	var href = form.attr("action");
	
	href = href + "?pageNo=" + pageno;
	
	loadChart(href, form.serializeObject(), id);
}

/**
 * 点击页码刷新时是动态加载，不存在IE缓存问题，另起方法名用于趋势图翻页调用
 */
function loadPanelChartPage(url, data, lid)
{
	jQuery(lid).load(url, data, function(response, status, xhr) 
	{
		//如果状态是error或者timeout, 显示错误对话框
	    if(status == "error" || status == "timeout")
	    {
	    	setTimeout("showErrorDialog('#errorDialog', '" + xhr + "','" + status + "')",70);
			
			return ;
	    }
	});
}

function setChartPatientSn(pSn)
{
	patientSn = pSn;
}

function getChartPatientSn()
{
	return patientSn;
}

function isIE6()
{
	if($.browser.version == '6.0')
	{
		return true;
	}
	
	return false;
}
//$[BUG]0012888 ADD END