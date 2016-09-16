(function ($) 
{
    $.suggest = function (input, options) 
    {
        var $input = $(input).attr("autocomplete", "off");
        var $results;
        var timeout = false;
        // hold timeout ID for suggestion results to appear	 要显示的提示结果的timeout ID
        var prevLength = 0;
        // last recorded length of $input.val() 最近录入的字符串长度
        var cache = [];
        // cache MRU list 最近使用列表
        var cacheSize = 0;
        // size of cache in chars (bytes?) 缓存的字节长度
        var selectedValue = options.selectedValue;
        // 当前选中的值
        var selectedText = "";
        var self = this;
		var resultItems = null;
		var resultContainer = null;
				
        // 根据当前选中的值，查询当前选中的文本              	
        if (selectedValue != "") 
        {
            for (var index = 0; index < options.source.length; index++) 
            {
                if (selectedValue == options.source[index][0]) 
                {
                    selectedText = $.trim(options.source[index][1]);
                    $input.val(selectedText);
                    prevLength = $input.val().length;
                    break;
                }
            }
        }
        
        if ($.trim($input.val()) == '' || $.trim($input.val()) == options.tip) {
            $input.val(options.tip).css('color', '#aaa');
        }
        
        if (!options.attachObject)
        {
            // 设置输入时关联显示的列表DIV
            options.attachObject = $(document.createElement("ul")).appendTo('body');
        }
        
        $results = $(options.attachObject);
        $results.addClass(options.resultsClass);

        //失去焦点时，处理
        $input.blur(function (event) 
        {
            self.closing = setTimeout( function () 
            {
                $results.hide();
		        if ($.trim($input.val()) == '' || $.trim($input.val()) == options.tip) {
		            	$input.val(options.tip).css('color', '#aaa');
		        }
		        else
		        {
		        	if(selectedText == "")
		            	$input.val(options.tip).css('color', '#aaa');
		        	else
		             	$input.val(selectedText);
		        }
            }, 200);
            //设置当焦点离开时，关联显示的列表DIV在指定时间后隐藏
        });
        
		//得到焦点时，处理
        $input.focus(function ()
        {
	        if ($.trim($input.val()) == '' || $.trim($input.val()) == options.tip)             
            {
            	$(this).val('').css('color', '#000');
            	suggest();
            	$(this).select();
            }
        });

		//点击时，处理
        $input.click(function ()
        {
            $(this).val('').css('color', '#000');
			suggest();
            $(this).select();

        });

        //绑定键盘处理事件
        $input.keyup(processKey);

        function resetPosition() 
        {
            //重新设置关联显示的列表DIV的位置
            // requires jquery.dimension plugin
            //var offset = $input.offset();
            var position = $input.position();
            $results.css({
                top : (position.top + input.offsetHeight) + 'px', left : position.left + 'px' 
            });
        }
        
        //键盘事件处理
        function processKey(e) 
        {
            // handling up/down/escape requires results to be visible
            // handling enter/tab requires that AND a result to be selected
//            if ((/27$|38$|40$/.test(e.keyCode) && $results.is(':visible')) || (/^13$|^9$/.test(e.keyCode) && getCurrentResult())) 
            if ((/27$|38$|40$/.test(e.keyCode) && $results.is(':visible')) || (/^13$|^9$/.test(e.keyCode)))
            {
                if (e.preventDefault) {
                    e.preventDefault();
                }
                if (e.stopPropagation) {
                    e.stopPropagation();
                }
                e.cancelBubble = true;
                e.returnValue = false;
                switch (e.keyCode) 
                {
                    case 38:
                        // up
                        prevResult();                        
                        //取前一个结果
                        break;
                    case 40:
                        // down
                        nextResult();
                        //取下一个结果
                        break;
                    case 13:
                        // return //取当前选中结果
                        selectCurrentResult();
						if(options.onKeyUp)
							options.onKeyUp(e);
                        $input.blur();
                        break;
                    case 27:
                        //	escape //结果列表隐藏
                        $results.hide();
                        break;
                }
            }
            // 如果上次查询的条件，与本次查询的条件不同，定义为一次新的查询
            else if ($input.val().length != prevLength) 
            {
                if (timeout) {
                    clearTimeout(timeout);
                }
                timeout = setTimeout(suggest, options.delay);
            }
        }

		//提示方法
        function suggest() 
        {
            resetPosition();
            var q = $.trim($input.val());
            displayItems(q);
        }

        // 显示列表项目方法
        function displayItems(items) 
        {
            var html = '';

            //显示列表项目，清空上次的查询结果
            selectedValue = "";
            selecteText = "";
            prevLength = $input.val().length;
            if (options.onSelect) {
                options.onSelect($input[0], { value: "", text: "" });
            }
            if (items == '') 
            {
                for (var i = 0; i < options.hot_list.length; i++) 
                {
                    var key = options.hot_list[i][0];     		//列表项目值
                    var text = options.hot_list[i][1];    		//列表项目名称
                    var searchText = options.hot_list[i][2];    //列表项目对应的检索条件，比如拼音码
                    if (searchText == undefined) {
                        searchText = "";
                    }
                    if (selectedValue != "" && selectedValue == options.source[i][0])
                    {
                        html += '<tr rel="' + key + '">';
                        html += '<td style="font-weight: bold;">' + text + '</td>' + '<td width="10px"></td><td style="font-weight: bold;">' + searchText + '</td>';
                        html += '</tr>';
                    }
                    else
                    {
                        html += '<tr rel="' + key + '">';
                        html += '<td>' + text + '</td>' + '<td width="10px"></td><td>' + searchText + '</td>';
                        html += '</tr>';
                    }
                }
                if (html == '') 
                    html = '<div class="gray ac_result_tip">对不起，找不到任何结果</div>';
				else
                	html = '<div class="gray ac_result_tip">请输入拼音或者↑↓选择</div><div class="ac_result_table"><table>' + html + '</div></table>';
            }
            else 
            {
                for (var i = 0; i < options.source.length; i++) 
                {
                    var key = options.source[i][0];           //列表项目值
                    var text = options.source[i][1];          //列表项目名称
                    var searchText = options.source[i][2];    //列表项目对应的检索条件，比如拼音码
                    if (searchText == undefined) {
                        searchText = "";
                    }
                    var inputText = items.toLowerCase(); 
                    var fullText = "";
                    if(options.mode == "py")
						fullText = searchText.toLowerCase();
                    else if(options.mode == "text")
                    	fullText = text.toLowerCase();
                	if (fullText.indexOf(inputText) != -1)
                    {
                        if (selectedValue != "" && selectedValue == options.source[i][0])
                        {
	                        html += '<tr rel="' + key + '">';
	                        html += '<td style="font-weight: bold;">' + text + '</td>' + '<td width="10px"></td><td style="font-weight: bold;">' + searchText + '</td>';
	                        html += '</tr>';
                        }
                        else
                        {
	                        html += '<tr rel="' + key + '">';
	                        html += '<td>' + text + '</td>' + '<td width="10px"></td><td>' + searchText + '</td>';
	                        html += '</tr>';
                        }
                    }
                }
                if (html == '') {
                    suggest_tip = '<div class="gray ac_result_tip">对不起，找不到：' + items + '</div>';
    				html = suggest_tip;
                }
                else {
                    suggest_tip = '<div class="gray ac_result_tip">' + items + '，找到以下结果</div>';
	                html = suggest_tip + '<div class="ac_result_table"><table>' + html + '</table></div>';
                }
            }

            $results.html(html).show();
            
			resultItems = getResultContainerItems();
			resultContainer = getResultContainer();

            if(resultItems.length >= options.pageSize)
            	$results.children("div.ac_result_table").css("height", options.maxHeight);	
			
			resultContainer.mousedown(function(event) {					
					if(event.target == resultContainer[0])
				    {
					    // use another timeout to make sure the blur-event-handler on the input was already triggered
					    setTimeout(function() {
					        clearTimeout(self.closing);
					        $input.focus();
					    }, 13);
				    }
				});

			resultItems.mouseover(function() {
				resultItems.removeClass(options.selectClass);
				$(this).addClass(options.selectClass);
			})
			.click(function(e) {
				e.preventDefault(); 
				e.stopPropagation();
				selectCurrentResult();
			});

	        // help IE users if possible
	        try {
			    $results.bgiframe({opacity:false});
	        }
	        catch (e) { } 
        }

        function getResultContainer()
        {
        	return $results.children('div.ac_result_table');
        }
        
        function getResultContainerItems()
        {
        	return $results.children('div.ac_result_table').children('table').children('tbody').children('tr');
        }
        
        function getCurrentResult() 
        {
            if (!$results.is(':visible')) {
                return false;
            }            
            var $currentResult = resultItems.filter("." + options.selectClass);
            if (!$currentResult.length) {
                $currentResult = false;
            }
            return $currentResult;
        }

        function selectCurrentResult() 
        {
            $currentResult = getCurrentResult();
            if ($currentResult) 
            {
                $input.val($currentResult.children('td').html());
                var resultKey = $currentResult.attr('rel');
                selectedValue = resultKey;
                selectedText = $input.val();
                prevLength = $input.val().length;
                $results.hide();
                if ( $(options.dataContainer) ) {
                    $(options.dataContainer).val(resultKey);
                }
                if (options.onSelect) {
                    options.onSelect($input[0], 
                    	{
	                        value: selectedValue,
	                        text: selectedText
	                    });
                }
            }
        }

        function nextResult() 
        {            
            $currentResult = getCurrentResult();
            if ($currentResult)
            {
                deactivate($currentResult);
                if($currentResult[0] == resultItems.filter(':last-child')[0])
				   activate(resultItems.filter(':first-child'));
				else
				   activate($currentResult.next())
            }
            else
            {
				if(resultItems.length != 0)
                	activate(resultItems.filter(':first-child'));
            }
        }

        function prevResult() 
        {
            $currentResult = getCurrentResult();
            if ($currentResult)
            {
                deactivate($currentResult);
                if($currentResult[0] == resultItems.filter(':first-child')[0])
				   activate(resultItems.filter(':last-child'));
				else
                	activate($currentResult.prev());
            }
            else {
				if(resultItems.length != 0)
    	            activate(resultItems.filter(':last-child'));
            }
        }
        
        function deactivate(item)
        {
        	item.removeClass(options.selectClass); 
        }
        
        function activate(item)
        {
        	item.addClass(options.selectClass);
        	if(hasScroll())
        		scrollTo(item);
        }
        
        function hasScroll () 
       	{
            return resultContainer.height() < resultContainer[$.fn.prop ? "prop" : "attr"]("scrollHeight");
        }
        
        
        function scrollTo(item) 
        {
            var offset = item.offset().top - resultContainer.offset().top,
			scroll = resultContainer.scrollTop(),
			elementHeight = resultContainer.height();
            if (offset < 0) {
                 resultContainer.scrollTop(scroll + offset);
            } else if (offset >= elementHeight) {
                 resultContainer.scrollTop(scroll + offset - elementHeight + item.height());
            }
        }        
    }

    $.fn.suggest = function (source, options) 
    {
        if (!source) {
            return;
        }
        
        options = options || {};
        options.source = source || [];
        options.hot_list = options.hot_list || [];
        options.delay = options.delay || 0;
        options.resultsClass = options.resultsClass || 'ac_results';
        options.selectClass = options.selectClass || 'ac_over';
        options.matchClass = options.matchClass || 'ac_match';
//		options.minchars = options.minchars || 1;
//		options.delimiter = options.delimiter || '\n';
        options.onSelect = options.onSelect || false;
//		options.dataDelimiter = options.dataDelimiter || '\t';
//		options.dataContainer = options.dataContainer || '#SuggestResult';
        options.attachObject = options.attachObject || null;
        options.selectedValue = options.selectedValue || '';
        options.tip = options.tip || '拼音';
		        
        this.each(function () 
        {
            new $.suggest(this, options);
        });

        return this;
    };
})(jQuery);
