//printconfig model
function BiosPrintConfig(printer, startPage, endPage){
	this.printer = printer != null ? printer : '';
	this.startPage = startPage != null ? startPage : 1;
	this.endPage = endPage != null ? endPage : 0;
	
	this.landscape;
	this.align;
	this.scale;
	
	this.pageFormat = {};
	
	this.id = null;
}
BiosPrintConfig.prototype = {
	toStr : function() {
		if(this.id != null)
			return "id=" + this.id;
			
		var str = "";
		if(this.printer != '')
			str += 'printer=' + this.printer + ";";
		if(this.startPage != null && this.startPage != 1)
			str += 'startPage=' + this.startPage + ";";
		if(this.endPage != null && this.endPage != 0)
			str += 'endPage=' + this.endPage + ";";
		
		if(this.landscape != null)
			str += 'landscape=' + this.landscape + ";";
		if(this.align != null)
			str + 'align=' + this.align + ";";
		if(this.scale != null)
			str + 'scale=' + this.scale + ";";
		if(this.pageFormat != null){
			for(var prop in this.pageFormat){
				str += prop + "=" + this.pageFormat[prop] + ";";
			}
		}

		return str;
	}
};

BiosReportModel.prototype.setPrintCfg = function(cfg) {
	this.printCfg = cfg;
};

BiosReportModel.prototype.getPrintString = function() {
	var str = this.getQueryString();
	if (this.printCfg) {
		var pstr = this.printCfg.toStr();
		if (pstr.length > 0)
			str += "&printcfg=" + pstr;
	}
	return str;
};

(function bios_print() {
	var appId = 'bios_report_print_obj';
	
	var appRoot = null;
	var encode = 'GBK';
	var resDir = '/bios_web_res/';
		
	var defaultProps = {
		showHint : true,
		hintText : unescape('\u786E\u5B9A\u8981\u8FDB\u884C\u6253\u5370\u64CD\u4F5C?'), // 确定要进行打印操作?
		selectPrinter : true,
		showProgress : false
	};
	
	function buildRpts(rpts) {
		if(bios_isArray(rpts)){
			var str = "";
			for(var i = 0; i < rpts.length; i++){
				if(i > 0)
					str += biosMarkLabel;
				str += rpts[i].getPrintString();
			}
			return str;	
		}else
			return rpts.getPrintString();
	}

	function buildProp(printProp) {
		var str = "";
		if(printProp.showHint == true){
			str += "showHint;";
			if(printProp.hintText != null)
				str += "hintText=" + printProp.hintText + ";";
		}
		if(printProp.selectPrinter == true)
			str += "selectPrinter;";
		if(printProp.showProgress == true)
			str += "showProgress;";
		return str;
	}
	
	function buildApplet(rpts0, props0) {
		var appletHtml = "";
		if(bios_isIE()){
			appletHtml += "<object classid='clsid:8AD9C840-044E-11D1-B3E9-00805F499D93' codebase='";
			appletHtml += appRoot + resDir + "applet/jre-6u45-windows-i586.exe;version=1.6' width='0' height='0' id='" + appId + "'>"	;
			appletHtml += "<param name='type' value='application/x-java-applet;version=1.6'>";
			appletHtml += "<param name='name' value='" + appId + "'>"	;
			appletHtml += "<param name='archive' value='" + appRoot + resDir + "applet/ReportPrint.jar'>";
			appletHtml += "<param name='code' value='bios.report.web.print.NewDirectPrintApplet'>";
			appletHtml += "<param name='scriptable' value='true'>";
			appletHtml += "<param name='rootURL' value='" + appRoot + "'>";	
			appletHtml += "<param name='urlEncode' value='" + encode + "'>";
			if(rpts0 != null)
				appletHtml += "<param name='rpts0' value=\"" + bios_replaceAll(rpts0, '"', '&quot;') + "\">";
			if(props0 != null)
				appletHtml += "<param name='props0' value=\"" + bios_replaceAll(props0, '"', '&quot;') + "\">";
			appletHtml += "</object>";
		}else{
			appletHtml += "<applet width='0' height='0' id='" + appId + "' "; 
			appletHtml += "archive='" + appRoot + resDir + "applet/ReportPrint.jar' "; 
			appletHtml += "code='bios.report.web.print.NewDirectPrintApplet' "; 
			appletHtml += "scriptable='true'>";
			appletHtml += "<param name='rootURL' value='" + appRoot + "'>";	
			appletHtml += "<param name='urlEncode' value='" + encode + "'>";
			if(rpts0 != null)
				appletHtml += "<param name='rpts0' value=\"" + bios_replaceAll(rpts0, '"', '&quot;') + "\">";
			if(props0 != null)
				appletHtml += "<param name='props0' value=\"" + bios_replaceAll(props0, '"', '&quot;') + "\">";
			appletHtml += "</applet>";
		}
		var div = document.createElement("div");
		document.body.appendChild(div);
		div.innerHTML = appletHtml;		
	}
	
	var BiosReportPrint = {
		applet : null,
		init : function(rootPath, urlEncode) {
			if (this.applet == null) {
				if(rootPath == null) {
					alert(unescape("\u672A\u8BBE\u7F6EappRoot\uFF0C\u6253\u5370\u7A0B\u5E8F\u521D\u59CB\u5316\u5931\u8D25!")); // 未设置appRoot，打印程序初始化失败!
					return;	
				}
				appRoot = rootPath;
				
				if(urlEncode)
					encode = urlEncode;
			}
		},
		print : function(reports, props) {
			if(reports == null || (bios_isArray(reports) && reports.length == 0)) {
				alert(unescape("\u8BF7\u9009\u62E9\u8981\u6253\u5370\u7684\u9879\u76EE!")); // 请选择要打印的项目!
				return;
			}

			if(props == null)
				props = {};
			for(var i in defaultProps){
				if(props[i] == null)
					props[i] = defaultProps[i];
			}
			
			if(this.applet == null) {
				if(appRoot == null){
					alert(unescape("\u6253\u5370\u7A0B\u5E8F\u5C1A\u672A\u521D\u59CB\u5316!")); // 打印程序尚未初始化!
					return;
				}
				buildApplet(buildRpts(reports), buildProp(props));
				this.applet = document.getElementById(appId);		
			} else  
				this.applet.printReports(buildRpts(reports), buildProp(props));
		}
	};
	window.BiosReportPrint = BiosReportPrint;
})();