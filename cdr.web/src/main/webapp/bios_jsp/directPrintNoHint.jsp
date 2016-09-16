<%@ page contentType="text/html;charset=GBK" %>
<html> 
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=GBK">
 
</head>
<body>
<a href="#" onClick="chageDiv('http://192.168.1.110:8080/report','/report','AAA.brt|AAA.brt')">print</a>

<div id="div1" STYLE="z-index:-1"></div>

<script language="javascript">
	
function chageDiv(appRoot,appmap,rpt)
{
	var html = "<object classid='clsid:8AD9C840-044E-11D1-B3E9-00805F499D93'	codebase='";
	html += appRoot+"/j2re-1_4_1-windows-i586-i.exe#Version=1,4,1,0'	width='0' height='0' id='report1_directPrintApplet'>"	;
	html += "<param name='name' value='report1_directPrintApplet'>"	;
	html += "<param name='code' value='bios.report.web.print.DirectPrintApplet.class'>";
	html += "<param name='archive' value='"+appmap+"/bios_web_res/applet//ReportPrint.jar'>";	
	html += "<param name='type' value='application/x-java-applet;version=1.4'>";
	html += "<param name='rootURL' value='"+appRoot+"'>";	
	html += "<param name='fileName' value='"+rpt+"'>";
	html += "<param name='needSelectPrinter' value='yes'>";
	html += "<param name='needShowHint' value='no'>";
	html += "<param name='encode' value='GBK'>";
	html += "<param name='scriptable' value='true'>";
	html += "<COMMENT>";
	html += "<embed type='application/x-java-applet;version=1.4' ";
	html += "pluginspage='"+appRoot+"/j2re-1_4_1-windows-i586-i.exe#Version=1,4,1,0' "; 
	html += "width='0' height='0' ";
	html += "name='report1_directPrintApplet' "; 
	html += "id='report1_directPrintApplet' "; 
	html += "CODE = 'bios.report.web.print.DirectPrintApplet.class' "; 
	html += "ARCHIVE ='"+appmap+"/bios_web_res/applet/ReportPrint.jar' "; 
	html += "rootURL='"+appRoot+"' ";
	html += "fileName='"+rpt+"' "; 
	html += "needSelectPrinter='yes' ";
	html += "needShowHint='no' ";
	html += "encode='GBK' ";
	html += "scriptable='true'>";
	html += "</embed>";
	html += "</COMMENT> ";
  html += "</object>";

  document.getElementById("div1").innerHTML = html;
  
}

</script>


</body> 
</html> 