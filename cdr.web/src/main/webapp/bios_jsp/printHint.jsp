<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib prefix="bios" uri="http://www.bijetsoft.com/BiosReportTags" %>
<%@ page import="java.util.*"%>

<html>
<head>
<title>是否打印</title>
<style>
a{TEXT-DECORATION:none}.style3 {
	font-size: 16px;
	font-family: "宋体";
	color: #0000FF;
}
.style4 {color: #666666}
.style5 {font-size: 14pt}
.style6 {color: #0000FF}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=GBK"></head>
<body>
<p align="center" class="style4"><strong><span class="style6">是否打印?</span></strong></p>
<div align="center">
  <%
	//此JSP参数格式为：rpt=无参数报表名|报表名1(params=param1=value1;param2=value2,vars=var1=value2)|报表名2(params=param1=value1,vars=var1=value2;var2=value2)|...
	request.setCharacterEncoding( "GBK" );
	String report = request.getParameter( "rpt" );
	if( report == null || report.trim().length() == 0 ) throw new Exception( "请输入报表文件名及参数串report=无参数报表名|报表1(params=param1=value1;param2=value2,vars=var1=value2)|报表2(params=param1=value1,vars=var1=value2;var2=value2)|..." );
	
	String appmap = request.getContextPath();
	String serverPort = String.valueOf( request.getServerPort() );
	String serverName = request.getServerName();
	String appRoot = "http://" + serverName + ":" + serverPort + appmap;
%>
  
  <object classid="clsid:8AD9C840-044E-11D1-B3E9-00805F499D93"	codebase="<%=appRoot%>/j2re-1_4_1-windows-i586-i.exe#Version=1,4,1,0"	width="40" height="16" id="report1_directPrintApplet" style="vertical-align:middle">	
	      <param name="name" value="report1_directPrintApplet">	
	      <param name="code" value="bios.report.web.print.DirectPrintApplet.class">	
	      <param name="archive" value="<%=appmap%>/bios_web_res/applet/ReportPrint.jar">	
	      <param name="type" value="application/x-java-applet;version=1.4">	
	      <param name="rootURL" value="<%=appRoot%>">	
	      <param name="fileName" value="<%=report%>">
	      <param name="needShowHint" value="yes">
	      <param name="fontSize" value="16">	
	      <param name="fontColor" value="#0000FF">	
	      <param name="backColor" value="#12632256">	
	      <param name="label" value="是">
	      <param name="needSelectPrinter" value="yes">
	      <param name="encode" value="GBK">
	      <param name="scriptable" value="true">
	      <COMMENT>
	      <embed type="application/x-java-applet;version=1.4" 
	      	pluginspage="<%=appRoot%>/j2re-1_4_1-windows-i586-i.exe#Version=1,4,1,0" 
	      	width="40" 
	      	height="16" 
	      	name="report1_directPrintApplet" 
	      	id="report1_directPrintApplet" 
	      	CODE = "bios.report.web.print.DirectPrintApplet.class" 
	      	ARCHIVE = "<%=appmap%>/bios_web_res/applet/ReportPrint.jar" 
	      	rootURL="<%=appRoot%>" 
	      	fileName="<%=report%>" 
	      	needShowHint="yes" 
	      	fontSize="16" 
	      	fontColor="#0000FF" 
	      	backColor="#12632256" 
	      	label="是" 
	      	needSelectPrinter="yes" 
	      	encode="GBK"
	      	scriptable="true">
	      </embed> 
	      </COMMENT> 
  </object>
	&nbsp;&nbsp; <a href="javascript:window.close()" class="style5">否</a>
	
</div>
</body>
</html>
