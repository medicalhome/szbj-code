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
  	<td colspan='2' style="font-size:14px"><b>��������ʾ��</b></td>
  </tr>
 	<tr>
  	<td colspan='2'><hr></td>
  </tr>
  <tr>  
    <td>1997�꣺</td>
  	<td><a href="/report/ReportEmitter?rpt=Demo/��������/ʱ�����.brt&params=year=1997"><%=contextPath%>/ReportEmitter?rpt=ʱ�����.brt&amp;params=year=1997</a><td/>
	</tr>
	<tr>  
    <td>2006�꣺</td>
  	<td><a href="/report/ReportEmitter?rpt=Demo/��������/ʱ�����.brt&params=year=2006"><%=contextPath%>/ReportEmitter?rpt=ʱ�����.brt&amp;params=year=2006</a><td/>
	</tr> 	
  <tr>  
    <td>2007�꣺</td>
  	<td><a href="/report/ReportEmitter?rpt=Demo/��������/ʱ�����.brt&params=year=2007"><%=contextPath%>/ReportEmitter?rpt=ʱ�����.brt&amp;params=year=2007</a><td/>
	</tr> 			
</table>
</body>

</html>
