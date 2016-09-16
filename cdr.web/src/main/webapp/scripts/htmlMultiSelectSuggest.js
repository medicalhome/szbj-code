(function ($) 
{
    $.fn.htmlMultiSelectSuggest = function (options) 
    {
    	return this.each(
    		function() 
    		{
				function init()
				{
					createSelectSuggest();
					preSet();
					addInputSuggest(false);					
				}
				
				function createSelectSuggest()
				{
					element.hide();
					//element.attr("multiple", "multiple");
					
					//创建一个多选的容器，一般是ul
	                holder = $(document.createElement("ul"));
	                holder.attr("class", "holder");
                    element.after(holder);
				}
				
				function preSet()
				{
		            element.children("option:selected").each(function(i, option) {
		                option = $(option);                    
	                    if(option.val() != "-1")
                    		addItem(option.text(), option.val(), true);
		            });
				
					//初始化数据源
					element.children("option").each(
						function() {
							if(this.value != "-1")
							{						
								source[source.length++] = new Array(
									this.value, this.text, $(this).attr("searchtext"), this);
							}
						}
					);
			
					var itemCount = 0;
					if(source.length > options.pageSize) 
						itemCount = options.pageSize;
					else
						itemCount = source.length;
			
					var itemIndex = 0;
					for(var index = 0; index < itemCount; index++)
						hotItems[hotItems.length++] = new Array(source[index][0], 
							source[index][1], source[index][2], source[index][3]);
				}
				

				function addInputSuggest(focusme)
				{
	                var li = $(document.createElement("li"));
	                var input = $(document.createElement("input"));

	                li.attr({
	                    "class": "bit-input",
	                    "id": elemid + "_annoninput"
	                });
	                input.attr({
	                    "type": "text",
	                    "class": "maininput",						
	                    "size": "8"
	                });

	                holder.prepend(li.append(input));
			    	
			    	var suggestOptions = {};
			    	suggestOptions.hot_list =  hotItems;
			    	suggestOptions.selectedValue = options.selectedValue;

	                if(options.attachObject == false)
	                {
		                var suggest = $(document.createElement("div"));
		                suggest.attr("id", element.attr("id") + "_suggest");
		                suggest.attr("class", "suggest ac_results");
		                suggest.css("float", "right");
		                
	                    element.after(suggest);
	                    suggestOptions.attachObject = "#" + suggest.attr("id"); 
	                }	
					else
			    		suggestOptions.attachObject = options.attachObject; 

			        suggestOptions.onSelect = options.onSelect;
					suggestOptions.onKeyUp = options.onKeyUp;   					
					suggestOptions.mode = options.mode;   
					if(options.mode == "py")
						suggestOptions.tip = "拼音(多选)";
					else if(options.mode == "text")
						suggestOptions.tip = "文本(多选)";
					suggestOptions.pageSize = options.pageSize;   
					suggestOptions.maxHeight = options.maxHeight;   
					
					input.suggest(source, suggestOptions);
					
					inp = input;

	                if (focusme) {
	                    setTimeout(function() {
	                    	input.focus();
	                    },
	                    1);
	                }
				}
				
	            function addItem(title, value, preadded, focusme) {
	                if (!maxItems()) {
	                    return false;
	                }

	                var li = document.createElement("li");
	                var txt = document.createTextNode(title);
	                var aclose = document.createElement("a");
	                $(li).attr({
	                    "class": "bit-box",
	                    "rel": value
	                });
	                $(li).prepend(txt);
	                $(aclose).attr({
	                    "class": "closebutton",
	                    "href": "#"
	                });
	
	                li.appendChild(aclose);
	                holder.append(li);
	
	                $(aclose).click(function() {
	                    removeItem($(this).parent("li"));
	                    return false;
	                });
	
	                //是否是预先增加
	                if (!preadded) {
	                    $("#" + elemid + "_annoninput").remove();
	                    var _item;
	                    addInputSuggest(focusme);
	                    if (element.children("option[value=" + value + "]").length) {
	                        _item = element.children("option[value=" + value + "]");
	                        _item.attr("selected", "selected");
	                    }
	                    element.change();
	                }
	                
	                if(inp != null)
                	{
	                	inp.focus();
                	}
	            }

	            function removeItem(item) {	
                    item.fadeOut("fast");
                    element.children('option[value="' + item.attr("rel") + '"]').removeAttr("selected");
                    item.remove();
                    element.change();
                    //deleting = 0;
	            }

	            function maxItems() {
	                if (options.maxItems != 0) {
	                    if (holder.children("li.bit-box").length < options.maxItems) {
	                        return true;
	                    }
	                    else{
	                    	var promptMsg = "最多能同时选择" + options.maxItems + "个项目！"
	                    	showMsg("提示",promptMsg);
	                        return false;
	                    }
	                }
	                else
	                	return true;
	            }

				function selectItem(input, data)
				{
					if(data.value != "")
					{
			            var selected = false;
			            element.children("option:selected").each(function(i, option) {
			                option = $(option);                    
		                    if(option.val() == data.value)
		                    {
	                    		selected = true;
	                    		return false;
	                    	}
			            });
						if(selected == false)
							addItem(data.text, data.value, false, false);
						else
						{
							showMsg("提示","不能同时多次选择同一个项目！",undefined,true,"$('#" + inp.parent("li").attr('id') + "').children('input').first().focus();");
						}
							
					}
				}
				
				options = $.extend({
					pageSize: 10,
					maxItems: 3, 					
					selectedValue: "",
					maxHeight: "250",
					attachObject: false,
					onSelect: false,
					onKeyUp: false,
					mode: "py" //py(按拼音检索), text(按文本检索)
				},
				options);
				
				options.onSelect = selectItem;
				var element = $(this);
	            var elemid = element.attr("id");
				var holder = null;
				
				var inp = null;
				
				var source = new Array();
				var hotItems = new Array();
	
				init();
				return this;
			}
		);
    };	
})(jQuery);
