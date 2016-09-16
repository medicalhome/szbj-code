<%@page contentType="text/html; charset=gbk"%>
<%
String contextPath = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/style.css" />
</head>
<body leftmargin="20">
<table border="0">
  <tr>
  	<td colspan='2' style="font-size:14px"><b>参数报表示例</b></td>
  </tr>
 	<tr>
  	<td colspan='2'><hr></td>
  </tr>
  <tr>  
    <td>1997年：</td>
  	<td><a href="/report/ReportEmitter?rpt=Demo/参数报表/时间参数.brt&params=year=1997"><%=contextPath%>/ReportEmitter?rpt=时间参数.brt&amp;params=year=1997</a><td/>
	</tr>
	<tr>  
    <td>2006年：</td>
  	<td><a href="/report/ReportEmitter?rpt=Demo/参数报表/时间参数.brt&params=year=2006"><%=contextPath%>/ReportEmitter?rpt=时间参数.brt&amp;params=year=2006</a><td/>
	</tr> 	
  <tr>  
    <td>2007年：</td>
  	<td><a href="/report/ReportEmitter?rpt=Demo/参数报表/时间参数.brt&params=year=2007"><%=contextPath%>/ReportEmitter?rpt=时间参数.brt&amp;params=year=2007</a><td/>
	</tr> 			
</table>
</body>

</html>
