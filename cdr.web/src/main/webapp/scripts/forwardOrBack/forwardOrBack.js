//$Author :jin_peng
// $Date : 2013/05/29 15:20$
// [BUG]0031929 ADD BEGIN
/**
 * 执行缓存记录
 */
function loadContent(hash) 
{
	if(hash != "" && hash != null)
	{
		if(currentContext != hash)
		{
			var exec = hash.split("@");
			
			if(exec.length > 1 )
			{
				if(exec[0] == "1")
				{
					if($("#dynamicContent").size() <= 0)
					{
						var url = '../visit/menuPart_'+getPatientSn()+'.html';
						
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

						g_firstLoad = false;
						
				        $("#dyContent").load(url, {'patientSn':getPatientSn()}, function(response, status, xhr) 
				        {
				    		//处理Session过期
				    		var sessionExpried = handleExpiredSession(xhr);
				    		// 如果session没有过期
				    		if(!sessionExpried)
				    		{
				    			//如果异步加载数据成功
				    		    if (status == "success") 
				    		    {
				    		    	eval(exec[1]);
				    		    	
				    			    //页面加载完毕后更改主页面头中页面视图转换链接的状态
				    			    //changeViewLabelStatus("#normal");
				    			    
				    			    //setupDatePickerSetting();
				    			    
				    			    // 初始化带有datepicker样式的日期输入框
				    			    //setupDatePicker();

				    	    		//afterLoadPanelSuccess();
				    		    }
				    		    
				    			//如果状态是error或者timeout, 显示错误对话框
				    			else if(status == "error" || status == "timeout")
				    			{
				    				setTimeout("showErrorDialog('#errorDialog', '" + xhr + "','" + status + "')",70);
				    			}
				    		}
				    	});
					}
					else
					{
						eval(exec[1]);
					}
				}
				else
				{
					eval(exec[1]);
				}
			}
			else
			{
				eval(exec[0]);
			}
			
			addContext(hash,"0");
		}
	}
	else
	{
		initLoad();
		
		currentContext = "";
	}
}

/**
 * 加载缓存记录
 */
function addContext(content, isAdd)
{
	currentContext = content;

	if(isAdd != "0")
	{
		$.history.load(currentContext);
	}
}

/**
 * 添加浏览器缓存历史
 */
function addContextHistory(content,isAdd)
{
	if(currentContext != null)
	{
		if(isAdd != '0')
		{
			addContext(content,"1");
		}
	}
	else
	{
		currentContext = "";
	}
}

/**
 * json字符串转化为json对象
 */
function changeIntoJson(data)
{
	if(data != null && data != "" && 
			data != undefined && typeof(data) != "object")
	{
		data = new Function("return " + data)();
	}
	
	return data;
}

/**
 * json对象转化为json字符串(低版本浏览器 eg：ie6，ie7... etc)
 */
function changeIntoStringLowerVersion(data)
{
	if(data == null || data == "" 
		|| data == undefined)
	{
		return data;
	}
	  
	var res = [];
	  
    if(typeof(data) == "string")
    {
	    return "\""+data.replace(/([\'\"\\])/g,"\\$1").replace(/(\n)/g,"\\n").replace(/(\r)/g,"\\r").replace(/(\t)/g,"\\t")+"\"";
    }
  
    if(typeof(data) == "object")
    {
	    if(!data.sort)
	    {
		    for(var i in data)
	    	{
		    	res.push(i +":"+changeIntoStringLowerVersion(data[i]));
	    	}
		    
		    res = "{" + res.join() + "}";
	    }
	    else
	    {
		    for(var i = 0; i < data.length; i++)
	    	{
		    	res.push(changeIntoStringLowerVersion(data[i]));
	    	}
		    
		    res = "[" + res.join() + "]";
	    }
  
	    return res;
	}
	  
	return data.toString();
}

/**
 * json对象转化为json字符串
 */
function changeIntoString(data)
{
	if(data != null && data != "" && 
			data != undefined && typeof(data) == "object")
	{
		data = JSON.stringify(data);
	}
	
	return data;
}

/**
 * 根据不同版本浏览器进行json转字符串操作
 */
function changeIntoStringMuti(data)
{
	var res = null;
	
	try
	{
		res = changeIntoString(data);
	}
	catch(e)
	{
		res = changeIntoStringLowerVersion(data);
	}
	
	return res;
}

/**
 * 空对象转化为空字符串
 */
function emptyToString(data)
{
	if(data != undefined && data != null)
	{
		data = "'" + data + "'";
	}
	
	return data;
}


// [BUG]0031929 ADD END

function addHistory(args, history, events){
    events = events || {};
    
    if (history.isMainViewMenuPart == "2") {
        changeNav(history.continueGoto);
    }

    if (history.isAddHistory == "1") {
        var eventsText;
        if(typeof events.loadSuccess != "undefined")
        	 eventsText = "loadSuccess: 'loadPanelSuccess'";
        if(typeof events.beforeLoad != "undefined") {
        	 if(typeof eventsText != "undefined") {
        	 	eventsText += ",";
        	 }
        	 eventsText += "beforeLoad: 'beforeLoadPanel'"
        }
		if(typeof eventsText != "undefined" && eventsText != "")
			 eventsText = "{" + eventsText + "}";
			         	        	
        addContextHistory("" + history.isMainViewMenuPart + "@loadHistory('" + args.url + "','" + changeIntoStringMuti(args.data) + "'," + emptyToString(args.id) + "," + 
        	"{vid:" + emptyToString(history.vid) + ",continueGoto:" + emptyToString(history.continueGoto) +  ",isAdd:'0', isAddHistory: '" + 
        	history.isAddHistory + "', isMainViewMenuPart:" + (history.isMainViewMenuPart == undefined ? undefined: '2') + "}, " + eventsText + ")", 
        	history.isAdd);
    }
    
}

function loadHistory(url, data, id, history, events) {

    var $panelContainer = null;
    $panelContainer = getPanelContainer(id);
    
    events = events || {};
    history = history || {};

    data = changeIntoJson(data);
	var loadArgs = {url: url, data: data, id: id};
    	
    //解决IE浏览器缓存问题
    var a = Math.random();
    if (url.indexOf("?") > 0) {
        url = url + "&rand=" + a;
    } else {
        url = url + "?rand=" + a;
    }

	if(history.vid == undefined || history.vid != "#normal"){
	    showLoadingScreen({
	        loadingScreenId: "loadingScreen",
	        loadingMessageId: "loadingMessage",
	        modal: true
	    });
	}

    if(typeof events.beforeLoad != "undefined" && typeof beforeLoadPanel != "undefined")
    	beforeLoadPanel(loadArgs, history);

    $panelContainer.load(url, data,
	    function(response, status, xhr) {
	        //处理Session过期
	        var sessionExpried = handleExpiredSession(xhr);
	        // 如果session没有过期
	        if (!sessionExpried) {
	            //如果异步加载数据成功
	            if (status == "success") {
	
	                setupDatePickerSetting();
	
	                // 初始化带有datepicker样式的日期输入框
	                setupDatePicker();

				    if(typeof events.loadSuccess != "undefined" && typeof loadPanelSuccess != "undefined")
				    	loadPanelSuccess(loadArgs, history);
	                
	                if(typeof history != "undefined") {		                						
						addHistory(loadArgs, history, events);
					}
	                //afterLoadPanelSuccess();
	            }
	
	            //如果状态是error或者timeout, 显示错误对话框
	            else if (status == "error" || status == "timeout") {
	            	setTimeout("showErrorDialog('#errorDialog', '" + xhr + "','" + status + "')",70);
	            }
	
	        	if(history.vid == undefined || history.vid != "#normal"){
	                // 关闭加载提示
	                closeLoadingScreen({
	                    loadingScreenId: "loadingScreen",
	                    loadingMessageId: "loadingMessage"
	                });
	        	}

	        }
	    });

}


