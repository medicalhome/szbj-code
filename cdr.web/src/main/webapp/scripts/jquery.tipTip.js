 /*
 * TipTip
 * Copyright 2010 Drew Wilson
 * www.drewwilson.com
 * code.drewwilson.com/entry/tiptip-jquery-plugin
 *
 * Version 1.3   -   Updated: Mar. 23, 2010
 *
 * This Plug-In will create a custom tooltip to replace the default
 * browser tooltip. It is extremely lightweight and very smart in
 * that it detects the edges of the browser window and will make sure
 * the tooltip stays within the current window size. As a result the
 * tooltip will adjust itself to be displayed above, below, to the left 
 * or to the right depending on what is necessary to stay within the
 * browser window. It is completely customizable as well via CSS.
 *
 * This TipTip jQuery plug-in is dual licensed under the MIT and GPL licenses:
 *   http://www.opensource.org/licenses/mit-license.php
 *   http://www.gnu.org/licenses/gpl.html
 */

(function($){
	$.fn.tipTip = function(options) {
	 	
		return this.each(function(){
			$.tipTip(this, options);
		});

	};
	
	$.tipTip = function(element, options) {
		function init() {
			prerender();
			render();
		}

		function prerender(){
	 		tiptip_holder = $('<div class="tiptip_holder" style="max-width:'+ opts.maxWidth +';"></div>');
			tiptip_content = $('<div class="' + opts.tipCss + '"></div>');
			if(opts.showArrow) {
				tiptip_arrow = $('<div class="tiptip_arrow"></div>');
				$(opts.alignTo).append(tiptip_holder.html(tiptip_content).prepend(tiptip_arrow.html('<div class="tiptip_arrow_inner"></div>')));
			}
			else {
				$(opts.alignTo).append(tiptip_holder.html(tiptip_content));
			}

			//当有element的title时，移除title属性
			if($element.attr("title") != null) {
				$element.removeAttr("title");
			}
			
			if(opts.activation == "hover"){
				$element.hover(function(){
					active_tiptip();
				}, function(){
					if(!opts.keepAlive){
						deactive_tiptip();
					}
				});
				if(opts.keepAlive){
					tiptip_holder.hover(function(){}, function(){
						deactive_tiptip();
					});
				}
			} else if(opts.activation == "focus"){
				$element.focus(function(){
					active_tiptip();
				}).blur(function(){
					deactive_tiptip();
				});
			} else if(opts.activation == "click"){
				$element.click(function(){
					active_tiptip();
					return false;
				}).hover(function(){},function(){
					if(!opts.keepAlive){
						deactive_tiptip();
					}
				});
				if(opts.keepAlive){
					tiptip_holder.hover(function(){}, function(){
						deactive_tiptip();
					});
				}
			}
		}
		
		function render() {
			if(opts.show == "always")
				active_tiptip();
		}

		function active_tiptip(){
			opts.enter.call(this);
			
			tiptip_content.html(tip_content);
			tiptip_holder.hide().css("margin", "0");
			if(tiptip_arrow != null)
				tiptip_arrow.removeAttr("style");

			var top = 0, left = 0;
			var org_width = 0, org_height = 0;
			if(opts.offset != null) {
				top = opts.offset.y;
				left = opts.offset.x;
			}
			else {
				top = parseInt($element.offset().top);
				left = parseInt($element.offset().left);
				org_width = parseInt($element.outerWidth());
				org_height = parseInt($element.outerHeight());
			}

			var tip_w = tiptip_holder.outerWidth();
			var tip_h = tiptip_holder.outerHeight();
			var w_compare = Math.round((org_width - tip_w) / 2); //宽度比较
			var h_compare = Math.round((org_height - tip_h) / 2); //高度比较
			var marg_left = Math.round(left + w_compare);
			var marg_top = Math.round(top + org_height + opts.edgeOffset);
			var t_class = "";
			var arrow_top = "";
			var arrow_left = Math.round(tip_w - 12) / 2;

            if(opts.defaultPosition == "bottom"){
            	t_class = "_bottom";
           	} else if(opts.defaultPosition == "top"){ 
           		t_class = "_top";
           	} else if(opts.defaultPosition == "left"){
           		t_class = "_left";
           	} else if(opts.defaultPosition == "right"){
           		t_class = "_right";
           	}
			
			var $alignTo = null;
			if(opts.alignTo == "body")
				$alignTo = $(window);
			else
				$alignTo = $(opts.alignTo);
				
			var right_compare = (w_compare + left) < parseInt($alignTo.scrollLeft());
			var left_compare = (tip_w + left + 15) > parseInt($alignTo.width());
			
			if(t_class == "_right" || t_class == "_left") {
				if((right_compare && w_compare < 0) || (t_class == "_right" && !left_compare) || (t_class == "_left" && left < (tip_w + opts.edgeOffset + 5))){
					t_class = "_right";
					arrow_top = Math.round(tip_h - 13) / 2;
					arrow_left = -12;
					marg_left = Math.round(left + org_width + opts.edgeOffset);
					marg_top = Math.round(top + h_compare);
				} else if((left_compare && w_compare < 0) || (t_class == "_left" && !right_compare)){
					t_class = "_left";
					arrow_top = Math.round(tip_h - 13) / 2;
					arrow_left =  Math.round(tip_w);
					marg_left = Math.round(left - (tip_w + opts.edgeOffset + 2));
					marg_top = Math.round(top + h_compare);
				}
			}

			var top_compare = (top + org_height + opts.edgeOffset + tip_h + 8) > parseInt($alignTo.height() + $alignTo.scrollTop());
			var bottom_compare = ((top + org_height) - (opts.edgeOffset + tip_h + 8)) < 0;
			
			if(t_class == "_top" || t_class == "_bottom") {
				if(top_compare || (t_class == "_bottom" && top_compare) || (t_class == "_top" && !bottom_compare)){
					if(t_class == "_top" || t_class == "_bottom"){
						t_class = "_top";
					} else {
						t_class = t_class+"_top";
					}
					arrow_top = tip_h;
					marg_top = Math.round(top - (tip_h + 2 + opts.edgeOffset));
				} else if(bottom_compare | (t_class == "_top" && bottom_compare) || (t_class == "_bottom" && !top_compare)){
					if(t_class == "_top" || t_class == "_bottom"){
						t_class = "_bottom";
					} else {
						t_class = t_class+"_bottom";
					}
					arrow_top = -12;						
					marg_top = Math.round(top + org_height + opts.edgeOffset);
				}
				
				if(right_compare  && w_compare < 0){
					marg_left = Math.round(left + org_width + opts.edgeOffset);
				}
				else if (left_compare && w_compare < 0) {
					marg_left = Math.round(left - (tip_w + opts.edgeOffset));					
				}
			}
					
			if(t_class == "_right_top" || t_class == "_left_top"){
				marg_top = marg_top + 5;
			} else if(t_class == "_right_bottom" || t_class == "_left_bottom"){		
				marg_top = marg_top - 5;
			}
			if(t_class == "_left_top" || t_class == "_left_bottom"){	
				marg_left = marg_left + 5;
			}
			if(tiptip_arrow != null)
				tiptip_arrow.css({"margin-left": arrow_left+"px", "margin-top": arrow_top+"px"});
			tiptip_holder.css({"margin-left": marg_left+"px", "margin-top": marg_top+"px"}).addClass("tip"+t_class);

			if (timeout){ clearTimeout(timeout); }
			timeout = setTimeout(function(){ tiptip_holder.stop(true,true).fadeIn(opts.fadeIn); }, opts.delay);	
		}
		
		function deactive_tiptip(){
			opts.exit.call(this);
			if (timeout){ clearTimeout(timeout); }
			tiptip_holder.fadeOut(opts.fadeOut);
		}		

		var defaults = { 
			activation: "hover",
			keepAlive: false,
			maxWidth: "200px",
			edgeOffset: 3,
			defaultPosition: "bottom",
			delay: 100,
			fadeIn: 200,
			fadeOut: 200,
			attribute: "title",
			content: '', // HTML or String to fill TipTIp with
			offset: null,
			alignTo : "body",
			show: "always",
			showArrow : true,
			tipCss :  "tiptip_holder",
		  	enter: function(){},
		  	exit: function(){}
	  	};
	 	var opts = $.extend(defaults, options);

		var tiptip_holder = null;
		var tiptip_content = null;
		var tiptip_arrow = null;
		var tip_content = "";		
		
		var $element = $(element);
		var timeout = null;

		if(opts.content != "")		
			tip_content = opts.content;
		else
			tip_content = $element.attr(opts.attribute);

		if(tip_content != "")
			init();	
	};
})(jQuery);  	