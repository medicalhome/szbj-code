(function($)
{
    theKey = 0,
    theMax = [],
    theNum = [],
	theObj = [],
	theWidth = [],
	theCurrentNum = [],
	$.fn.extend({
	    myMarquee: function(obj, n)
	    {
	        var objId = this.attr("id");
	        if (objId == 'scrollshow')
	        {
	            $(this).closest(".part_c").prev(".part_t").find(".l").hover(function()
	            {
	                obj.closest(".part_c").prev(".part_t").find(".l").addClass("l_hover");
	            }, function()
	            {
	                obj.closest(".part_c").prev(".part_t").find(".l").removeClass("l_hover");
	            });
	            $(this).closest(".part_c").prev(".part_t").find(".r").hover(function()
	            {
	                obj.closest(".part_c").prev(".part_t").find(".r").addClass("r_hover");
	            }, function()
	            {
	                obj.closest(".part_c").prev(".part_t").find(".r").removeClass("r_hover");
	            });
	            
        		banTab($(this).find('li a'));
        		activeTab($(this).find('li a').slice(0, n));
        		$(this).find('li a').each(function(i)
        		{
        			if((i+1) % n == 0 || i == ($(this).find('li a').size() - 1))
    				{
        				var cla = $(this).attr("class");
        				
        				if(cla != undefined)
    					{
        					cla += " tbTab";
    					}
        				else
    					{
        					cla = "tbTab";
    					}
        				
        				$(this).attr("class",cla);
    				}
        		});
	        }
	        prevClick = function(_key)
	        {
	            $(theObj[_key]).find('ul').animate({ left: '+=' + (theWidth[_key]) }, 'fast');
	            controlTab();
	        };
	        nextClick = function(_key)
	        {
	            $(theObj[_key]).find('ul').animate({ left: '-=' + (theWidth[_key]) }, 'fast');
	            controlTab();
	        };
	        controlTab = function()
	        {
	        	banTab($(theObj[_key]).find('ul li a'));
	            activeTab($(theObj[_key]).find('ul li a').slice(theCurrentNum[_key] * n, theCurrentNum[_key] * n + n));
	        }
	        $(this).each(function()
	        {
	            var ulWidth = (parseInt($(this).find("ul li").css("width")) + parseInt($(this).find("ul li").css("padding-left")) + parseInt($(this).find("ul li").css("padding-right")) + parseInt($(this).find("ul li").css("margin-left")) + parseInt($(this).find("ul li").css("margin-right"))) * $(this).find("ul li").size();
	            $(this).find("ul").css("width", parseInt($(this).find("ul li").css("width")) * $(this).find("ul li").size() + "px");
	            theNum[theKey] = 1;
	            theCurrentNum[theKey] = 0;
	            theObj[theKey] = $(this);
	            theWidth[theKey] = $(this).find('ul li').outerWidth() * n;
	            theMax[theKey] = Math.ceil($(this).find('li').size() / n);
	            if (objId == 'scrollshow')
	            {
	                $(this).closest('.part_c').prev('.part_t').find('.l').attr('key', theKey).click(function()
	                {
	                    _key = $(this).attr('key');
	                    theCurrentNum[_key] = theCurrentNum[_key] - 1;
	                    prevClick(_key);
	                    if (--theNum[_key] == 1)
	                    {
	                        $(this).hide().next().show();
	                    }
	                    if (theMax[_key] > 1) theObj[_key].closest('.part_c').prev('.part_t').find('.r').show().next().hide();
	                });
	                $(this).closest('.part_c').prev('.part_t').find('.r').attr('key', theKey).click(function()
	                {
	                    _key = $(this).attr('key');
	                    theCurrentNum[_key] = theCurrentNum[_key] + 1;
	                    nextClick(_key);
	                    if (++theNum[_key] == theMax[_key])
	                    {
	                        $(this).hide().next().show();
	                    }
	                    if (theMax[_key] >= 1) theObj[_key].closest('.part_c').prev('.part_t').find('.l').show().next().hide();
	                });
	            }
	            theKey++;
	        });
	    }
	});
})(jQuery);