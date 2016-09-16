<%@ page contentType="text/html;charset=GBK" %>
<%
String ctxPath = request.getContextPath();
String appRoot = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + ctxPath;
%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=GBK">
	<script src='<%=ctxPath%>/bios_web_res/bios_report_common.js'></script>
	<script src='<%=ctxPath%>/bios_web_res/bios_report_print.js'></script>
	
	<script language='javascript'>
		
	//初始化报表打印程序，第一个参数为web应用的根目录，第二个参数为应用的url编码
	BiosReportPrint.init("<%=appRoot%>", "GBK");
	
	//报表列表
	var rptList = [];
	
	/* 初始化可以打印的报表列表 */
	(function initReports() {
		var printCfg_xsp = new BiosPrintConfig();
		printCfg_xsp.printer = "Microsoft XPS Document Writer";
		
		var rptModel = new BiosReportModel();
		rptModel.rpt = 'Demo/分组报表/5分组汇总.brt';
		rptModel.setPrintCfg(printCfg_xsp);
		rptList.push(rptModel);
			
		rptModel = new BiosReportModel();
		rptModel.rpt = 'Demo/参数报表/时间参数.brt';
		rptModel.setParam('year', '1997');
		rptList.push(rptModel);
		
		rptModel = new BiosReportModel();
		rptModel.rpt = 'Demo/参数报表/时间参数.brt';
		rptModel.setParam('year', '1998');
		rptList.push(rptModel);
	})();
	
	/* 打印选中的报表 */
	function printReports(){
		var rptToPrint = [];
		for(var i=0;i<rptList.length;i++){
			if(document.all['rpt' + i].checked)
				rptToPrint.push(rptList[i]);
		}
		
		var printOption = {};
		printOption.showHint = document.all['option_hint'].checked;
		printOption.selectPrinter = document.all['option_printer'].checked;
		printOption.hintText = '确定打印选中的 ' + rptToPrint.length + ' 张报表?';
		
		BiosReportPrint.print(rptToPrint, printOption);
	}
	
	
	/* 打印接口调用详细示例 */
	function printReportDemo() {

		var rptModel = new BiosReportModel(); //创建一个报表访问模型
		rptModel.rpt = 'Demo/分组报表/5分组汇总.brt'; //设置报表的rpt参数
	
		//可参考以下代码对报表模型进行更详细的设置
		//报表参数设置
		/*
		rptModel.setParam('year', '2013'); //为报表参数 year 设置值
		rptModel.removeParam('year'); //删除报表参数 year
		rptModel.setParam('month', '3'); //设置参数month
		
		//报表变量设置
		rptModel.setVar('var1', 'testvalue'); //为报表变量 var1 设置值 
		rptModel.removeVar('var1'); //删除报表变量 var1
		rptModel.setVar('var2', 'hello'); //设置变量 var2
		
		//报表内置参数设置
		rptModel.setProp('rfscache', 'true'); //设置报表访问的内置参数 rfscache, 刷新缓存，重新生成报表
		rptModel.removeProp('rfscache'); //删除访问参数 rfscache
		rptModel.setProp('rptheight', '600'); //页面高度设置
		rptModel.setProp('fitwidth', 'true'); //设置页面宽度为适应
		//可设置更多其它内置参数...
		
		var query_str = rptModel.getQueryString(); //获取 rpt=xx.brt&params=p1=xx;p2=yy;&vars=v1=xxx;v2=yyy 格式的报表访问字符串
		var query_prop_str = rptModel.getQueryStringWithProp(); //获取包含了内置参数的报表访问字符串
		*/
		
		
		var printCfg = new BiosPrintConfig(); //创建打印设置对象
		printCfg.printer = "Microsoft XPS Document Writer"; //设置打印机名称
	
		//更多设置
		/*
		printCfg.startPage = 1; //打印起始页，从1开始
		printCfg.endPage = 0; //打印结束页，0表示尾页
		printCfg.landscape = true; //纸张横向
		printCfg.align = 'left'; //打印定位，居左。可设置值: 'left','center','right'
		printCfg.scale = 1; //打印缩放比例，1为保持原始大小，0.5为缩小到50%，以此类推
		
		//页面属性设置
		printCfg.pageFormat.paper = "A4"; //页面属性，纸张名称，自定义纸张时，不需要设置该属性
		printCfg.pageFormat.paperWidth = 200; //自定义纸张宽度，单位:毫米
		printCfg.pageFormat.paperHeight = 150; //自定义纸张高度，单位:毫米
		printCfg.pageFormat.marginTop = 25; //上边距，单位:毫米
		printCfg.pageFormat.marginBottom = 25; //下边距，单位:毫米
		printCfg.pageFormat.marginLeft = 20; //左边距，单位:毫米
		printCfg.pageFormat.marginRight = 20; //右边距，单位:毫米
		*/
		
		//或不设置上述的打印属性，仅设置id，将根据id使用服务器配置文件中的打印设置
		//var printCfg = new BiosPrintConfig();
		//printCfg.id = 'my_config';
		
		rptModel.setPrintCfg(printCfg); //为报表模型添加打印设置信息
			
				
		var rptArr = [];//要进行批量打印的报表数组
		//添加多个报表
		rptArr.push(rptModel);
		//rptArr.push(rptModel2);
		//rptArr.push(rptModel3);
		
		//打印任务属性，showHint 打印前是否提示, hintText 提示的文字信息, selectPrinter 是否弹出打印机选择界面
		var printProp = {showHint:true,hintText:'是否要打印测试报表?',selectPrinter:false};
		
		//打印多张报表
		BiosReportPrint.print(rptArr, printProp);
		//也可以打印单张报表
		//BiosReportPrint.print(rptModel, printProp);	
	}
	</script>

</head>

<body>	
	<table style="font-size:13px" cellspacing=0>
  <tr>
  	<td colspan='2'><b>批量打印</b></td>
  </tr>
  <tr>
  	<td style='border-top: 1px solid gray;' valign='top'>选择报表：</td>
  	<td style='border-top: 1px solid gray;'>
  		<input type='checkbox' name='rpt0' checked>员工信息分组汇总统计 &nbsp;
  		<input type='checkbox' name='rpt1' checked>销售额统计年报_1997年 &nbsp;
  		<input type='checkbox' name='rpt2' checked>销售额统计年报_1998年 &nbsp;
		</td>
  </tr>
  <tr>
  	<td valign='top'>打印选项：</td>
  	<td>
  		<input type='checkbox' name='option_hint' checked>打印前提示 &nbsp;
  		<input type='checkbox' name='option_printer' checked>让用户选择打印机 &nbsp;
		</td>
  </tr>
  <tr>
  	<td style='border-top: 1px solid gray;' colspan='2' align='right'><input type="button" value=" 打  印 " onclick="printReports()"></td>
  </tr>
</table>

<br/>
<br/>
<input type='button' value='打印接口详细示例' onclick='printReportDemo();'>
	
</body>	
<html>