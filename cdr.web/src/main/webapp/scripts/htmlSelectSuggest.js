(function ($) 
{
    $.fn.htmlSelectSuggest = function (options) 
    {
    	return this.each(
    		function() 
    		{
				function init()
				{
					createSelectSuggest();
					preSet();
					addInputSuggest();					
				}
				
				function createSelectSuggest()
				{
					element.hide();
				}
				
				function preSet()
				{
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
				
				function addInputSuggest()
				{
			        input = $("<input>")
								.insertAfter(element)
			    				.css({ width: options.width });
			    	
			    	var suggestOptions = {};
			    	suggestOptions.hot_list =  hotItems;
			    	suggestOptions.selectedValue = element.val();

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
						suggestOptions.tip = "拼音";
					else if(options.mode == "text")
						suggestOptions.tip = "文本";
					suggestOptions.pageSize = options.pageSize;   
					suggestOptions.maxHeight = options.maxHeight;   
					 	
					$(input).suggest(source, suggestOptions);
				}
				
				function selectItem(input, data)
				{
					if(data.value != "")
						element.val(data.value);
					else 
						element.val("-1");
				}

				options = $.extend({
					pageSize: 10,
					selectedValue: "",
					width: "120",
					maxHeight: "250",
					attachObject: false,
					onSelect: false,
					onKeyUp: false,
					mode: "py" //py(按拼音检索), text(按文本检索)
				},
				options);
				options.attachObject = false;
				options.onSelect = selectItem;
				
				var element = $(this);
				var source = new Array();
				var hotItems = new Array();

				init();
				return this;
			}
		);
    };	
})(jQuery);
