// $Author :jin_peng
// $Date : 2013/06/18 15:20$
// [BUG]0031929 ADD BEGIN
// tab enter键问题
var tabEnterClass = ".tabEnter,tr:[name='tabEnter'],span[name='tabEnter'],div[name='tabEnter'],li:not(.banTab)," +
		"select:not([multiple]),.maininput,a,input[type='text']:not(.maininput),input[type='button'],.xtab";

var current = null;

var stopBubble = false;
var tabflag = true;
var tabflagDetail = true;
var numi = 0;

function logical()
{
	var sel = selector();
	
	addTableEtc(sel);
	
	sel.unbind("click.eff");
	sel.unbind("focus.eff");
	sel.unbind("blur.eff");
	sel.unbind("keydown.eff");
	
	sel.bind("click.eff",function()
	{
		current = $(this);
	});
	
	sel.bind("focus.eff",function(event)
	{
		styleTableOver($(this));
	});
	
	sel.bind("blur.eff",function(event)
	{
		styleTableOut($(this));
		
		var cla = $(this).attr("class");
		
		if(cla != undefined && cla.indexOf("xtab") >= 0)
		{
			banTab($(".xtab:visible"));
		}
		
	});
	
	sel.bind("keydown.eff",function(event)
	{
		if(event.keyCode == 9)
		{
			var cla = $(this).attr("class");
			
			if(cla != undefined && cla.indexOf("tbTab") >= 0)
			{
				activeTab($(".xtab:visible"));
				
				$(".xtab:visible").focus();
				
				return false;
			}
			
			if(cla != undefined && cla.indexOf("xtab") >= 0)
			{
				banTab($(".xtab:visible"));
				
				firstElementNotFresh().focus();
				
				return false;
			}
			
			// 住院视图查询条件正序
			if($("#zyfirst").size() > 0)
			{
				inpatientConditionProcess(cla, $(this));
				
				if(!tabflag)
				{
					tabflag = true;
					
					return false;
				}
			}
			
			// 时间轴视图查询条件正序
			if($("#timfirst").size() > 0)
			{
				timerConditionProcess(cla, $(this));
				
				if(!tabflag)
				{
					tabflag = true;
					
					return false;
				}
			}
			
			stopBubble(event);
		}
		
		if (event.keyCode == 13) 
		{
			enterPress($(this));
		}
		
	});
}

function enterPress(myjQueryObjects,isFocusCurrent)
{
	var isEnd = false;
	
	if(isFocusCurrent == undefined)
	{
		current = $(this);
	}
	
	if(isTable(myjQueryObjects) || isImg(myjQueryObjects) || isSpan(myjQueryObjects) || isDiv(myjQueryObjects))
	{
		myjQueryObjects.click();
		
		isEnd = true;
	}
	else if(isLi(myjQueryObjects))
	{
		myjQueryObjects.children("a:visible").first().click();
		
		isEnd = true;
	}
	
	stopBubble(event);
	
	if(isEnd)
	{
		return false;
	}
}

function firstElement()
{
	var eleName = null;
	
	if($("#mzfirst").size() > 0)
	{
		eleName = $("#mzfirst");
	}
	else if($("#zyfirst").size() > 0)
	{
		eleName = $("#zyfirst");
	}
	else if($("#timfirst").size() > 0)
	{
		eleName = $("#timfirst");
	}
	else if($("#menu01").size() > 0)
	{
		eleName = $("#menu01");
	}
	else
	{
		eleName = $("#qjfirst");
	}
	
	return eleName;
}

function firstElementNotFresh()
{
	var eleName = null;
	
	if($("#mzfirst").size() > 0)
	{
		eleName = $("#date");
	}
	else if($("#zyfirst").size() > 0)
	{
		eleName = $("#dischargeDateAndVisitSn");
	}
	else if($("#timfirst").size() > 0)
	{
		eleName = $("#visitDate");
	}
	else if($("#menu01").size() > 0)
	{
		eleName = $("#menu01");
	}
	else
	{
		eleName = $("#visitType");
	}
	
	return eleName;
}

function inpatientConditionProcess(cla, obj)
{
	if(cla != undefined && cla.indexOf("tiTab") >= 0)
	{
		$(".zytab_1").focus();
		
		tabflag = false;
	}
	else
	{
		var clastr = obj.attr("class");
		
		if(clastr != undefined && clastr.indexOf("zytab") >= 0)
		{
			var zy = $("*[class*='zytab']:not(:disabled)");
			
			var len = zy.size();
			
			if(obj.attr("id") != zy.eq(0).attr("id"))
			{
				zy.eq(zy.index(obj) - 1).focus();
			}
			else
			{
				$("#tabLeft").focus();
			}
			
			tabflag = false;
		}
	}
}

function timerConditionProcess(cla, obj)
{
	if(cla != undefined && cla.indexOf("tiTab") >= 0)
	{
		$(".timertab_1").focus();
		
		tabflag = false;
	}
	else
	{
		var clastr = obj.attr("class");
		
		if(clastr != undefined && clastr.indexOf("timertab") >= 0)
		{
			var timer = $("*[class*='timertab']:not(:disabled)");
			
			var len = timer.size();
			
			if(obj.attr("id") != timer.eq(0).attr("id"))
			{
				timer.eq(timer.index(obj) - 1).focus();
			}
			else
			{
				$("#im").focus();
			}
			
			tabflag = false;
		}
	}
}

function loadTab()
{
	if(current != undefined && current != null)
	{
		var crid = current.attr("id");
		
		focusElement(firstElement());
		
		if(crid != null && crid != undefined)
		{
			if(!$("#" + crid).attr("disabled"))
			{
				$("#" + crid).focus();
			}
		}
		else
		{
			current.focus();
		}
		
		if($("#folderDetail") != undefined && $("#folderDetail").size() > 0)
		{
			$("#folderDetail").focus();
		}
	}
}

function folderConditionProcess(cla, obj)
{
	if(cla != undefined && cla.indexOf("tiTab") >= 0)
	{
		$(".folder_1").focus();
		
		tabflagDetail = false;
	}
	else
	{
		var clastr = obj.attr("class");
		
		if(clastr != undefined && clastr.indexOf("folder") >= 0)
		{
			var folder = $("*[class*='folder']:not(:disabled)");
			
			var len = folder.size();
			
			if(obj.attr("id") != folder.eq(0).attr("id"))
			{
				folder.eq(folder.index(obj) - 1).focus();
			}
			else
			{
				$("#pfmemo").focus();
			}
			
			tabflagDetail = false;
		}
	}
}

function titleLinkProcessKey(obj)
{
	var cla = obj.attr("class");
	
	if(cla != undefined && cla.indexOf("tbTab") >= 0)
	{
		activeTab($(".xtab:visible"));
		
		$(".xtab:visible").focus();
		
		return false;
	}
	
	if(cla != undefined && cla.indexOf("xtab") >= 0)
	{
		banTab($(".xtab:visible"));
		
		firstElementNotFresh().focus();
		
		return false;
	}
	
	return true;
}

function titleLinkProcessBlur(obj)
{
	var cla = obj.attr("class");
	
	if(cla != undefined && cla.indexOf("xtab") >= 0)
	{
		banTab($(".xtab:visible"));
	}
}

function intergratedFirstFocus()
{
	var fir = $("#menu01");
	
	if(fir.size() > 0)
	{
		fir.focus();
	}
}

function integratedInit()
{
	$(".addTab").find("tr:[style*='cursor']").attr("name","tabEnter");
	$(".spanAddEnter").attr("name","tabEnter");
	
	logical();
}

function integratedRestore()
{
	$(".spanAddEnter").attr("name","tabEnter");
    $(".addTab").find("tr:[style*='cursor']").attr("name","tabEnter");
    $(".portlet-header").removeAttr("name");
    banTab($(".portlet-header"));
	
	logical();
}

function integratedSmall()
{
	$(".spanAddEnter").removeAttr("name");
    banTab($(".spanAddEnter"));
    $(".addTab").find("tr:[style*='cursor']").removeAttr("name");
    banTab($(".addTab").find("tr:[style*='cursor']"));
    $(".portlet-header").attr("name","tabEnter");
}

function integratedSmallClick($bigportlet)
{
	$bigportlet.find(".spanAddEnter").removeAttr("name");
    banTab($bigportlet.find(".spanAddEnter"));
    $bigportlet.find(".addTab").find("tr:[style*='cursor']").removeAttr("name");
    banTab($bigportlet.find(".addTab").find("tr:[style*='cursor']"));
    $bigportlet.find(".portlet-header").attr("name","tabEnter");
}

function integratedSmallLoad()
{
	$(".bigportlets").find(".addTab").find("tr:[style*='cursor']").attr("name","tabEnter");
    $(".bigportlets").find(".spanAddEnter").attr("name","tabEnter");
    
    $(".bigportlets").find(".portlet-header").removeAttr("name");
    banTab($(".bigportlets").find(".portlet-header"));
    
    logical();
}

function styleTableOut(myjQueryObject)
{
	if(isTable(myjQueryObject))
	{
		if(myjQueryObject.data("events")["mouseout"] 
			|| myjQueryObject[0]["onmouseout"] != null)
		{
			myjQueryObject.trigger("mouseout");
		}
	}
}

function styleTableOver(myjQueryObject)
{
	if(isTable(myjQueryObject))
	{
		//alert(myjQueryObject[0]["onmouseover"] + "    " + myjQueryObject.data("events")["mouseover"] + "    " + myjQueryObject[0].tagName);
		if(myjQueryObject.data("events")["mouseover"] 
			|| myjQueryObject[0]["onmouseover"] != null)
		{
			myjQueryObject.trigger("mouseover");
		}
	}
}

function detailFocusTab(id)
{
	if(id != undefined)
	{
		var obj = $("#" + id);
		
		activeTab(obj.find("ul a").parent("li"));
	    
	    banTab(obj.find("ul a"));
	    
	    obj.find("ul a").parent("li").unbind("keydown.detailA");
	    
	    obj.find("ul a").parent("li").bind("keydown.detailA",function(event)
	    {
	    	if(event.keyCode == 13)
	   		{
	   			$(this).find("a").click();
	   		}
	    });
	    
	    obj.on("tabsselect",function(event,ui)
	    {
	    	var st = ui.tab.toString();
	    	var id = st.split("#")[1];
	    	$("a[href='#" + id + "']").parent("li").focus();
	    })
	}
	else
	{
		
		var selTab = $(".tabEnter");
		
	    addTableEtc(selTab, true);
	    
	    selTab.unbind("keydown.tableEtc");
	    selTab.unbind("focus.tableEtc");
	    selTab.unbind("blur.tableEtc");
	    
	    selTab.bind("keydown.tableEtc",function(event)
	    {
	    	if(event.keyCode == 13)
			{
	    		enterPress($(this),true);
			}
	    });
	    
	    selTab.bind("focus.tableEtc",function(event)
		{
	    	var mouseover = $(this).attr("onmouseover");
	    	
	    	if(mouseover != undefined 
	    			&& mouseover != null && mouseover != "")
	    	{
	    		var exec = mouseover.split(";");
	    		
	    		eval(exec[0]);
	    	}	
		});
		
	    selTab.bind("blur.tableEtc",function(event)
		{
	    	var mouseout = $(this).attr("onmouseout");
	    	
	    	if(mouseout != undefined 
	    			&& mouseout != null && mouseout != "")
	    	{
	    		var exec = mouseout.split(";");
	    		
	    		eval(exec[0]);
	    	}	
		});
	}
}

function addTableEtc(obj, isDetail)
{	
	var isGo = true;
	
	obj.each(function()
    {
		if(isDetail != undefined)
		{
			if($(this).attr("id") == "")
			{
				isGo = false;
			}
		}
		
		if(isGo)
		{
			if(isTable($(this)) || isLi($(this)) 
					|| isImg($(this)) || isSpan($(this)) || isDiv($(this)))
			{
				activeTab($(this));
				
				if($(this).attr("class") != undefined)
				{
					if($(this).attr("class").indexOf("unactive") >= 0 )
					{
						banTab($(this));
					}
				}
			}
			
			if(isTable($(this)))
			{
				if($(this).attr("class") == undefined 
						|| ($(this).attr("class") != undefined && $(this).attr("class").indexOf("addBorder") < 0))
				{
					$(this).attr("hidefocus","true");
				}
			}
	    }
		else
		{
			isGo = true;
		}
	});
}

function setDetailElement(obj)
{
	activeTab(obj);
	
	obj.unbind("keydown.detail");
	
	obj.bind("keydown.detail",function(event)
	{
		if(event.keyCode == 9)
		{
			if($("#loadpatientListView_fav").size() > 0)
			{
				var cla = $(this).attr("class");
				
				folderConditionProcess(cla, $(this));
				
				if(!tabflagDetail)
				{
					tabflagDetail = true;
					
					return false;
				}
			}
		}
		
		if(event.keyCode == 13)
		{
			if($(this).attr("id") == "outpatientViews_2" || $(this).attr("id") == "inpatientViews_3" 
			|| $(this).attr("id") == "timerViews_4" || $(this).attr("id") == "normalViews_5")
			{
				var checked = $(this).is(":checked");
				
				var id = $(this).attr("id");
				
				$("input[name='" + $(this).attr("name") + "']").each(function()
				{
					if(!checked)
					{
						$(this).attr("checked", true);
					}
					else 
					{
						$(this).attr("checked", false);
					}
				});
			}
			else
			{
				$(this).trigger("click");
				
				stopBubble(event);
			}
		}
	});
}

function showDialogFocus()
{
	if(current != null)
	{
		window.setTimeout("current.focus()", 10);
	}
}

function banTab(obj)
{
	obj.attr("tabindex","-1");
}

function activeTab(obj)
{
	obj.attr("tabindex","0");
}

function focusElement(obj)
{
	obj.focus();
}

function isTable(obj)
{
	if(obj[0].tagName == "TABLE" 
		|| obj[0].tagName == "TR"
			|| obj[0].tagName == "TD")
	{
		return true;
	}
	
	return false;
}

function isLi(obj)
{
	if(obj[0].tagName == "LI")
	{
		return true;
	}
	
	return false;
}

function isImg(obj)
{
	if(obj[0].tagName == "IMG")
	{
		return true;
	}
	
	return false;
}

function isSpan(obj)
{
	if(obj[0].tagName == "SPAN")
	{
		return true;
	}
	
	return false;
}

function isDiv(obj)
{
	if(obj[0].tagName == "DIV")
	{
		return true;
	}
	
	return false;
}

function selector()
{
	var sel = $(tabEnterClass);
	
	return sel;
}

function isTabindex(el, name)
{
    var attr = el.getAttributeNode && el.getAttributeNode(name);
    
    return attr ? attr.specified : false
}

// [BUG]0031929 ADD END
