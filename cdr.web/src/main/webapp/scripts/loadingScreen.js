function showLoadingScreen(settings)
{	
	$("#" + settings.loadingScreenId).dialog({
		autoOpen: false,	// set this to false so we can manually open it
		dialogClass: "loadingScreenWindow",
		closeOnEscape: false,
		draggable: false,
		width: 220,
		minHeight: 36,
		bgiframe: true ,          //解决下拉框遮盖div的bug
		modal: settings.modal,
		buttons: {},
		resizable: false,
		create: function() {
			$(".loadingScreenWindow").addClass("ui-noborder");
		},
		open: function() {
		},
		close: function() {
		}
	}); // end of dialog

	// allow loading screen dialog to be customizable
	$("#" + settings.loadingMessageId).html(settings.message && '' != settings.message ? settings.message : '数据加载中，请稍候...');
	$("#" + settings.loadingScreenId).dialog('option', 'title', settings.title &&  '' != settings.title ? settings.title : 'Loading');
	$("#" + settings.loadingScreenId).dialog('open');
}

function closeLoadingScreen(settings) {
	$("#loadingScreen").remove();
	$("#loading").append("<div id='loadingScreen' style='display:none'><div id='loadingMessage'>数据加载中，请稍候...</div></div>");

	//$("#" + settings.loadingScreenId).dialog("close");
}
