function checkEmpty(obj, str, objText)
{
    var temp = str;
    temp = temp.trim();
    if (temp == "") {
    	var promptMsg = objText + "不能为空！";
    	showMsg("提示",promptMsg);
        obj.focus();
        return false;
    }
    else {
        return true;
    }
}

function checkList(obj, value, objText)
{
    if (value == "-1") {
    	var promptMsg = "请选择" + objText + "！";
    	showMsg("提示",promptMsg);
        obj.focus();
        return false;
    }
    else {
        return true;
    }
}

function checkPositiveIntNumber(obj, str, objText)
{
    var result = str.match(/^(-|\+)?\d+$/);
    if (result == null) {
    	var promptMsg = objText + "必须是整数！";
    	showMsg("提示",promptMsg);
        obj.focus();
        return false;
    }
    return true;
}

function checkIntNumber(obj, str, objText)
{
    var result = str.match(/^\d+$/);
    if (result == null) {
    	var promptMsg = objText + "必须是正整数！";
    	showMsg("提示",promptMsg);
        obj.focus();
        return false;
    }
    return true;
}

function checkNumber(obj, str, objText)
{
    if (str.match(/^(-|\+)?\d+$/) == null && str.match(/^-?([1-9]\d*\.\d*|0\.\d*[1-9]\d*|0?\.0+|0)$/) == null) {
    	var promptMsg = objText + "必须是浮点数！";
    	showMsg("提示",promptMsg);
        obj.focus();
        return false;
    }
    else {
        return true;
    }
}

function checkDate(obj, str, objText)
{
    if (isDateString(str) == false) {
    	var promptMsg = objText + "的日期格式错误或不是一个合法的日期！";
    	showMsg("提示",promptMsg);
        obj.focus();
        return false;
    }
    else {
        return true;
    }
}

function checkSqlText(obj, str, objText) 
{
    var re = /select|update|delete|exec|count|'|"|=|;|>|<|%/i;
    var e = re.test(str);
    if (e) {
    	var promptMsg = objText + "不能含有特殊字符和SQL关键字！";
    	showMsg("提示",promptMsg);
        obj.focus();
        return false;
    }
    else {
        return true;
    }
}

/******************************以下为加强功能函数******************************/

// 去除字符串的首尾的空格
String.prototype.trim = function ()
{
    return this.replace(/(^\s*)|(\s*$)/g, "");
}

// 去除字符串的左侧的空格
String.prototype.ltrim = function ()
{
    return this.replace(/(^\s*)/g, "");
}

// 去除字符串的右侧的空格
String.prototype.rtrim = function ()
{
    return this.replace(/(\s*$)/g, "");
}

// 去除字符串的首尾的%
String.prototype.trimPS = function ()
{
    return this.replace(/(^%*)|(%*$)/g, "");
}

// 去除字符串的左侧的%
String.prototype.ltrimPS = function ()
{
    return this.replace(/(^%*)/g, "");
}

// 去除字符串的右侧的%
String.prototype.rtrimPS = function ()
{
    return this.replace(/(%*$)/g, "");
}

/******************************以下为适配函数******************************/

// 判断日期函数
function isDateString(sDate)
{
    var iaMonthDays = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
    var iaDate = new Array(3);
    var year, month, day;
    if (arguments.length != 1) {
        return false;
    }
    iaDate = sDate.toString().split("-");
    if (iaDate.length != 3) {
        return false;
    }
    if (iaDate[1].length > 2 || iaDate[2].length > 2) {
        return false;
    }
    year = parseFloat(iaDate[0]);
    month = parseFloat(iaDate[1]);
    day = parseFloat(iaDate[2]);
    if (year < 1900 || year > 2100) {
        return false;
    }
    if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) {
        iaMonthDays[1] = 29;
    }
    if (month < 1 || month > 12) {
        return false;
    }
    if (day < 1 || day > iaMonthDays[month - 1]) {
        return false;
    }
    return true;
}

// 检查文本的长度(中文字为两个字符)
function checkTextLength(strTemp, textName, textLength)
{
    var str = strTemp;
    num = str.length;
    var arr = str.match(/[^\\\\\\\\\\\\\\\\x00-\\\\\\\\\\\\\\\\x80]/ig);
    if (arr != null) {
        num += arr.length;
    }
    if (parseInt(num) > parseInt(textLength)) {
    	var promptMsg = textName + "的长度不能大于" + textLength;
    	showMsg("提示",promptMsg);
        return false;
    }
    else {
        return true;
    }
}
