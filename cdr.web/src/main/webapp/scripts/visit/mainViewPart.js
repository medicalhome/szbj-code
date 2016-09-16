function setMenuSettings() {
    var displayMenus = getDisplayMenus();
    var displayMenuList = displayMenus.split(",");
    var allMenus = getAllMenus();
    var allMenuList = allMenus.split(",");

    if (displayMenus == "") {
        for (var index = 0; index < allMenuList.length; index++) {
            var menuCode = allMenuList[index];
            $("#menu" + menuCode).hide();
        }
    } else {
        for (var index = 0; index < allMenuList.length; index++) {
            var menuCode = allMenuList[index];
            var foundMenu = false;
            for (var displayIndex = 0; displayIndex < displayMenuList.length; displayIndex++) {
                var displayMenuCode = displayMenuList[displayIndex];
                var partMenuCode = displayMenuCode.substring(0, menuCode.length);
                //alert("displayMenuCode : " + displayMenuCode);
                //alert("partMenuCode : " + partMenuCode);
                if (partMenuCode == menuCode) {
                    foundMenu = true;
                    break;
                }
            }
            if (foundMenu == false) $("#menu" + menuCode).hide();
        }
    }
}

/**
 * 使用指定的URL和数据局部刷新页面
 */
function loadDiagnosisPanel(url, data, id, vid) {
    var lid;
    if (id == undefined) {
        lid = "#dynamicContent";
    } else {
        lid = id;
    }
    $('#diagnosisLoadingScreen').show();
    $('#diagnosisContent').hide();

    //解决IE浏览器缓存问题
    var a = Math.random();
    if (url.indexOf("?") > 0) {
        url = url + "&rand=" + a;
    } else {
        url = url + "?rand=" + a;
    }

    jQuery(lid).load(url, data,
    function(response, status, xhr) {
        //处理Session过期
        var sessionExpried = handleExpiredSession(xhr);
        // 如果session没有过期
        if (!sessionExpried) {
            //如果异步加载数据成功
            if (status == "success") {
                $('#diagnosisContent').show();
                $('#diagnosisLoadingScreen').hide();
            }
            //如果状态是error或者timeout, 显示错误对话框
            else if (status == "error" || status == "timeout") {
            	setTimeout("showErrorDialog('#errorDialog', '" + xhr + "','" + status + "')",70);
            }
        }
    });
}