//common methods
function bios_trim(str){
	if(str == null)
		return null;
	if(Object.prototype.toString.apply(str) == "[object String]")
		return str.replace(/^\s*(.+)/gi,"$1").replace(/\s*$/gi,"");
	return str;
}

function bios_replaceAll(s1,s2,s3){
	var r = new RegExp(s2.replace(/([\(\)\[\]\{\}\^\$\+\-\*\?\.\"\'\|\/\\])/g,"\\$1"),"ig");
	return s1.replace(r,s3);
}

function bios_isArray(o) {   
	return Object.prototype.toString.apply(o) == "[object Array]";  	
}

function bios_getInt(dataObj){
	dataObj = parseInt(dataObj);
	if(isNaN(dataObj))
		return 0;
	return dataObj;
}

function bios_getFloat(dataObj){
	dataObj = parseFloat(dataObj);
	if(isNaN(dataObj))
		return 0;
	return dataObj;
}

function bios_msg(msg,time,icon,w) {
	if(time > 0){
		biosPopup.alert({message:msg,titleBar:false,btn:[],height:120,showMask:false,useSlide:true,icoCls:icon,width:w});
		setTimeout(function(){biosPopup.close();},time);
	}else{
		biosPopup.alert({message:msg,titleBar:false,height:150,icoCls:icon,width:w});
	}
}

function bios_getXmlHttpReq() {
	var xmlHttp;
	if (window.ActiveXObject) {
		try{
			return new ActiveXObject("Msxml2.XMLHTTP");
		}catch(e2){
			return new ActiveXObject("Microsoft.XMLHTTP");
		}
	} else if(window.XMLHttpRequest) {
		return new XMLHttpRequest();
	}
	alert("Browser dose not support Ajax!");
}

function bios_getSubEl(parentEl, elName, isIE){
	if(!parentEl)
		return null;
		
	if(isIE)
		return parentEl.all[elName];
		
	var cns = parentEl.children;
	if(cns != null && cns.length > 0){
		for(var i = 0;i < cns.length;i++){
			var el = cns[i];
			if(el.id == elName ||el.name == elName) {
				return el;
			} else {
				var rtn = bios_getSubEl(el, elName);
				if(rtn != null)
					return rtn;
			}
		}			
	}
	return null;
}

function bios_sendRequest(xmlHttpReq, url, data){
	bios_sendRequest(xmlHttpReq, url, data, true);
}

function bios_sendRequest(xmlHttpReq, url, data, asyn){
	xmlHttpReq.open("POST", url, asyn);
	xmlHttpReq.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
	xmlHttpReq.send(data);
}

function bios_isIE(){
	var ua = navigator.userAgent.toLowerCase();
	return ua.indexOf('msie') > -1;
}

function bios_isChrome(){
	var ua = navigator.userAgent.toLowerCase();
	return ua.indexOf('chrome') > -1;
}

function resizeImg(img, w, h) {
	if(img.clientWidth > w || img.clientHeight > h)
		img.width = img.clientWidth/Math.max(img.clientWidth/w, img.clientHeight/h);
}

//report model
var biosMarkLabel = "M-bios_mark-M";

function BiosReportModel(rptStr, paramsObj, varsObj, propsObj){
	this.rpt = rptStr;
	this.params = paramsObj != null ? paramsObj : {};
	this.vars = varsObj != null ? varsObj : {};
	this.props = propsObj != null ? propsObj : {};
}
BiosReportModel.prototype = {
	getQueryString : function(){
		var str = "rpt=" + this.rpt;
		var pstr = this.getParamsString();
		if(pstr.length > 0)
			str += "&params=" + pstr;
		var vstr = this.getVarsString();
		if(vstr.length > 0)
			str += "&vars=" + vstr;
		return str;
	},
	getQueryStringWithProp : function(){
		var str = this.getQueryString();
		for(var prop in this.props){
			str += "&" + prop + "=" + this.props[prop];
		}
		return str;	
	},
	setParam : function(paramName, paramValue){
		this.params[paramName] = paramValue;
	},
	removeParam : function(paramName){
		delete this.params[paramName];
	},
	setVar : function(varName, varValue){
		this.vars[varName] = varValue;
	},
	removeVar : function(varName){
		delete this.vars[varName];
	},
	setProp : function(propName, propValue){
		this.props[propName] = propValue;
	},
	removeProp : function(propName){
		delete this.props[propName];
	},
	getParamsString : function(){
		var str = "";
		for(var pName in this.params){
			if(str.length > 0)
				str += ";";
			str += pName + "=" + this.params[pName];
		}
		return str;
	},
	getVarsString : function(){
		var str = "";
		for(var vName in this.vars){
			if(str.length > 0)
				str += ";";
			str += vName + "=" + this.vars[vName];
		}
		return str;	
	}
};


//accurate calculate
var bios_acc_calc = false;
function bios_add(num1, num2) {
	if (bios_acc_calc != true)
	 return num1 + num2;
	if (typeof num1 != 'number' || typeof num2 != 'number')
	 return num1 + num2;
	var s1 = bios_numToString(num1);
	var s2 = bios_numToString(num2);
	var r1 = 0;
	var r2 = 0;
	try {
		r1 = s1.split(".")[1].length;
	} catch(e) {}
	try {
		r2 = s2.split(".")[1].length;
	} catch(e) {}
	s1 = s1.replace(".", "");
	s2 = s2.replace(".", "");
	if (r1 > r2) {
		for (var m = 0; m < r1 - r2; m++) {
			s2 += 0;
		}
	} else if (r2 > r1) {
		for (var m = 0; m < r2 - r1; m++) {
			s1 += 0;
		}
	}
	var n = Math.pow(10, Math.max(r1, r2));
	return (Number(s1) + Number(s2)) / n;
}
function bios_sub(num1, num2) {
	if (bios_acc_calc != true)
	 return num1 - num2;
	if (typeof num1 != 'number' || typeof num2 != 'number')
	 return num1 - num2;
	var s1 = bios_numToString(num1);
	var s2 = bios_numToString(num2);
	var r1 = 0;
	var r2 = 0;
	try {
		r1 = s1.split(".")[1].length;
	} catch(e) {}
	try {
		r2 = s2.split(".")[1].length;
	} catch(e) {}
	s1 = s1.replace(".", "");
	s2 = s2.replace(".", "");
	if (r1 > r2) {
		for (var m = 0; m < r1 - r2; m++) {
			s2 += 0;
		}
	} else if (r2 > r1) {
		for (var m = 0; m < r2 - r1; m++) {
			s1 += 0;
		}
	}
	var n = Math.pow(10, Math.max(r1, r2));
	return (Number(s1) - Number(s2)) / n;
}
function bios_multi(num1, num2) {
	if (bios_acc_calc != true)
	 return num1 * num2;
	if (typeof num1 != 'number' || typeof num2 != 'number')
	 return num1 * num2;
	var s1 = bios_numToString(num1);
	var s2 = bios_numToString(num2);
	var n = 0;
	try {
		n += s1.split(".")[1].length;
	} catch(e) {}
	try {
		n += s2.split(".")[1].length;
	} catch(e) {}
	return (Number(s1.replace(".", "")) * Number(s2.replace(".", ""))) / Math.pow(10, n);
}
function bios_div(num1, num2) {
	if (bios_acc_calc != true)
	 return num1 / num2;
	if (typeof num1 != 'number' || typeof num2 != 'number')
	 return num1 / num2;
	var s1 = bios_numToString(num1);
	var s2 = bios_numToString(num2);
	var n = 0;
	try {
		n += s1.split(".")[1].length;
	} catch(e) {}
	try {
		n -= s2.split(".")[1].length;
	} catch(e) {}
	return (Number(s1.replace(".", "")) / Number(s2.replace(".", ""))) / Math.pow(10, n);
}
function bios_numToString(num) {
	var s = num.toString();
	if(s.indexOf('e-') > 0) {
		var arr = s.split('e-');
		var d = parseInt(arr[1]);
		s = '0.';
		for(var i = 0; i < d - 1; i++){
			s += '0';
		}
		s = s + arr[0].replace(".", "");
	}
	return s;	
}