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
		
	//��ʼ�������ӡ���򣬵�һ������ΪwebӦ�õĸ�Ŀ¼���ڶ�������ΪӦ�õ�url����
	BiosReportPrint.init("<%=appRoot%>", "GBK");
	
	//�����б�
	var rptList = [];
	
	/* ��ʼ�����Դ�ӡ�ı����б� */
	(function initReports() {
		var printCfg_xsp = new BiosPrintConfig();
		printCfg_xsp.printer = "Microsoft XPS Document Writer";
		
		var rptModel = new BiosReportModel();
		rptModel.rpt = 'Demo/���鱨��/5�������.brt';
		rptModel.setPrintCfg(printCfg_xsp);
		rptList.push(rptModel);
			
		rptModel = new BiosReportModel();
		rptModel.rpt = 'Demo/��������/ʱ�����.brt';
		rptModel.setParam('year', '1997');
		rptList.push(rptModel);
		
		rptModel = new BiosReportModel();
		rptModel.rpt = 'Demo/��������/ʱ�����.brt';
		rptModel.setParam('year', '1998');
		rptList.push(rptModel);
	})();
	
	/* ��ӡѡ�еı��� */
	function printReports(){
		var rptToPrint = [];
		for(var i=0;i<rptList.length;i++){
			if(document.all['rpt' + i].checked)
				rptToPrint.push(rptList[i]);
		}
		
		var printOption = {};
		printOption.showHint = document.all['option_hint'].checked;
		printOption.selectPrinter = document.all['option_printer'].checked;
		printOption.hintText = 'ȷ����ӡѡ�е� ' + rptToPrint.length + ' �ű���?';
		
		BiosReportPrint.print(rptToPrint, printOption);
	}
	
	
	/* ��ӡ�ӿڵ�����ϸʾ�� */
	function printReportDemo() {

		var rptModel = new BiosReportModel(); //����һ���������ģ��
		rptModel.rpt = 'Demo/���鱨��/5�������.brt'; //���ñ����rpt����
	
		//�ɲο����´���Ա���ģ�ͽ��и���ϸ������
		//�����������
		/*
		rptModel.setParam('year', '2013'); //Ϊ������� year ����ֵ
		rptModel.removeParam('year'); //ɾ��������� year
		rptModel.setParam('month', '3'); //���ò���month
		
		//�����������
		rptModel.setVar('var1', 'testvalue'); //Ϊ������� var1 ����ֵ 
		rptModel.removeVar('var1'); //ɾ��������� var1
		rptModel.setVar('var2', 'hello'); //���ñ��� var2
		
		//�������ò�������
		rptModel.setProp('rfscache', 'true'); //���ñ�����ʵ����ò��� rfscache, ˢ�»��棬�������ɱ���
		rptModel.removeProp('rfscache'); //ɾ�����ʲ��� rfscache
		rptModel.setProp('rptheight', '600'); //ҳ��߶�����
		rptModel.setProp('fitwidth', 'true'); //����ҳ����Ϊ��Ӧ
		//�����ø����������ò���...
		
		var query_str = rptModel.getQueryString(); //��ȡ rpt=xx.brt&params=p1=xx;p2=yy;&vars=v1=xxx;v2=yyy ��ʽ�ı�������ַ���
		var query_prop_str = rptModel.getQueryStringWithProp(); //��ȡ���������ò����ı�������ַ���
		*/
		
		
		var printCfg = new BiosPrintConfig(); //������ӡ���ö���
		printCfg.printer = "Microsoft XPS Document Writer"; //���ô�ӡ������
	
		//��������
		/*
		printCfg.startPage = 1; //��ӡ��ʼҳ����1��ʼ
		printCfg.endPage = 0; //��ӡ����ҳ��0��ʾβҳ
		printCfg.landscape = true; //ֽ�ź���
		printCfg.align = 'left'; //��ӡ��λ�����󡣿�����ֵ: 'left','center','right'
		printCfg.scale = 1; //��ӡ���ű�����1Ϊ����ԭʼ��С��0.5Ϊ��С��50%���Դ�����
		
		//ҳ����������
		printCfg.pageFormat.paper = "A4"; //ҳ�����ԣ�ֽ�����ƣ��Զ���ֽ��ʱ������Ҫ���ø�����
		printCfg.pageFormat.paperWidth = 200; //�Զ���ֽ�ſ�ȣ���λ:����
		printCfg.pageFormat.paperHeight = 150; //�Զ���ֽ�Ÿ߶ȣ���λ:����
		printCfg.pageFormat.marginTop = 25; //�ϱ߾࣬��λ:����
		printCfg.pageFormat.marginBottom = 25; //�±߾࣬��λ:����
		printCfg.pageFormat.marginLeft = 20; //��߾࣬��λ:����
		printCfg.pageFormat.marginRight = 20; //�ұ߾࣬��λ:����
		*/
		
		//�����������Ĵ�ӡ���ԣ�������id��������idʹ�÷����������ļ��еĴ�ӡ����
		//var printCfg = new BiosPrintConfig();
		//printCfg.id = 'my_config';
		
		rptModel.setPrintCfg(printCfg); //Ϊ����ģ����Ӵ�ӡ������Ϣ
			
				
		var rptArr = [];//Ҫ����������ӡ�ı�������
		//��Ӷ������
		rptArr.push(rptModel);
		//rptArr.push(rptModel2);
		//rptArr.push(rptModel3);
		
		//��ӡ�������ԣ�showHint ��ӡǰ�Ƿ���ʾ, hintText ��ʾ��������Ϣ, selectPrinter �Ƿ񵯳���ӡ��ѡ�����
		var printProp = {showHint:true,hintText:'�Ƿ�Ҫ��ӡ���Ա���?',selectPrinter:false};
		
		//��ӡ���ű���
		BiosReportPrint.print(rptArr, printProp);
		//Ҳ���Դ�ӡ���ű���
		//BiosReportPrint.print(rptModel, printProp);	
	}
	</script>

</head>

<body>	
	<table style="font-size:13px" cellspacing=0>
  <tr>
  	<td colspan='2'><b>������ӡ</b></td>
  </tr>
  <tr>
  	<td style='border-top: 1px solid gray;' valign='top'>ѡ�񱨱�</td>
  	<td style='border-top: 1px solid gray;'>
  		<input type='checkbox' name='rpt0' checked>Ա����Ϣ�������ͳ�� &nbsp;
  		<input type='checkbox' name='rpt1' checked>���۶�ͳ���걨_1997�� &nbsp;
  		<input type='checkbox' name='rpt2' checked>���۶�ͳ���걨_1998�� &nbsp;
		</td>
  </tr>
  <tr>
  	<td valign='top'>��ӡѡ�</td>
  	<td>
  		<input type='checkbox' name='option_hint' checked>��ӡǰ��ʾ &nbsp;
  		<input type='checkbox' name='option_printer' checked>���û�ѡ���ӡ�� &nbsp;
		</td>
  </tr>
  <tr>
  	<td style='border-top: 1px solid gray;' colspan='2' align='right'><input type="button" value=" ��  ӡ " onclick="printReports()"></td>
  </tr>
</table>

<br/>
<br/>
<input type='button' value='��ӡ�ӿ���ϸʾ��' onclick='printReportDemo();'>
	
</body>	
<html>