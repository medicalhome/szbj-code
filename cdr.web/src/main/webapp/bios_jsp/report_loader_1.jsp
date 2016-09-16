<%@page contentType="text/html; charset=gbk"%>
<%
String encode = "GBK";
request.setCharacterEncoding(encode);

String rpt = request.getParameter("rpt");

String params = request.getParameter("params");
if(params != null)
	params = params.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\"", "&quot;");
String vars = request.getParameter("vars");
if(vars != null)
	vars = vars.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\"", "&quot;");

String rfscache = request.getParameter("rfscache");

String rptwidth = request.getParameter("rptwidth");
String rptheight = request.getParameter("rptheight");
String fitwidth = request.getParameter("fitwidth");

String toolbardisplay = request.getParameter("toolbardisplay");
String xlsbtn = request.getParameter("xlsbtn");
String xlsbtn1 = request.getParameter("xlsbtn1");
String pdfbtn = request.getParameter("pdfbtn");
String docbtn = request.getParameter("docbtn");
String printbtn = request.getParameter("printbtn");
%>

<html>
<head>    

<script language="javascript">
function remove_loading() {
			document.getElementById("params_form").style.visibility = 'hidden';
            
      var targelem = document.getElementById('splash');
      targelem.style.display = 'none';
      targelem.style.visibility = 'hidden';
}
</script>

</head>
  
<body onLoad="remove_loading();">
	
	<form id='params_form' action='/report/ReportEmitter' target="report_frame" style='margin:0px;'>
		
		<input type='hidden' name='rpt' value="<%=rpt%>">
		
		<% if (params != null) { %>
			<input type='hidden' name='params' value="<%=params%>">
		<% } %>
		
		<% if (vars != null) { %>
			<input type='hidden' name='vars' value="<%=vars%>">
		<% } %>
		
		<% if (rfscache != null) { %>
			<input type='hidden' name='rfscache' value="<%=rfscache%>">
		<% } %>
		
		<% if (rptwidth != null) { %>
			<input type='hidden' name='rptwidth' value="<%=rptwidth%>">
		<% } %>
		
		<% if (rptheight != null) { %>
			<input type='hidden' name='rptheight' value="<%=rptheight%>">
		<% } %>
		
		<% if (fitwidth != null) { %>
			<input type='hidden' name='fitwidth' value="<%=fitwidth%>">
		<% } %>
		
		<% if (toolbardisplay != null) { %>
			<input type='hidden' name='toolbardisplay' value="<%=toolbardisplay%>">
		<% } %>

		<% if (xlsbtn != null) { %>
			<input type='hidden' name='xlsbtn' value="<%=xlsbtn%>">
		<% } %>
		
		<% if (xlsbtn1 != null) { %>
			<input type='hidden' name='xlsbtn1' value="<%=xlsbtn1%>">
		<% } %>
		
		<% if (pdfbtn != null) { %>
			<input type='hidden' name='pdfbtn' value="<%=pdfbtn%>">
		<% } %>
		
		<% if (docbtn != null) { %>
			<input type='hidden' name='docbtn' value="<%=docbtn%>">
		<% } %>
		
		<% if (printbtn != null) { %>
			<input type='hidden' name='printbtn' value="<%=printbtn%>">
		<% } %>
		
		<input type='hidden' name='rptmargin' value="0">		
		
	</form>
	
	<iframe name="report_frame" src="" style="width:100%;height:100%;" frameborder="0"></iframe>
	
	<script type="text/javascript">
		document.getElementById("params_form").submit();
	</script>
	
	<div id="splash" style="position: absolute; margin-left: -75px; margin-top: -50px; left: 50%; top: 50%; width: 150px; border: 1px solid #c0c0c0; background-color: #fafafa; text-align: center;">
		<img src="img/loading.gif" width="32" height="32" style="margin: 10px 15px 0" />
		<div style="margin: 5px 15px 10px; color: #656565; font: 12px Verdana, 'Lucida Sans', sans-serif">报表加载中...</div>
	</div>>
	
</body>
</html>