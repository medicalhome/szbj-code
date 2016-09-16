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
<style type="text/css">
<!--
#loader_container {
        text-align: center;
        position: absolute;
        top: 40%;
        width: 100%;
        left: 0;
}
#loader {
        font-family: Tahoma, Helvetica, sans;
        font-size: 11.5px;
        color: #000000;
        background-color: #FFFFFF;
        padding: 10px 0 16px 0;
        margin: 0 auto;
        display: block;
        width: 130px;
        border: 1px solid #5a667b;
        text-align: left;
        z-index: 2;
}
#progress {
        height: 5px;
        font-size: 1px;
        width: 1px;
        position: relative;
        top: 1px;
        left: 0px;
        background-color: #8894a8
}
#loader_bg {
        background-color: #e4e7eb;
        position: relative;
        top: 8px;
        left: 8px;
        height: 7px;
        width: 113px;
        font-size: 1px
}
-->
</style>

<script language="javascript">
var t_id = setInterval(animate,20);
var pos = 0;
var dir = 2;
var len = 0;
function animate() {
      var elem = document.getElementById('progress');
      if(elem != null) {
              if (pos == 0) len += dir;
              if (len > 32 || pos > 79) pos += dir;
              if (pos > 79) len -= dir;
              if (pos > 79 && len == 0) pos = 0;
              elem.style.left = pos;
              elem.style.width = len;
      }
}
function remove_loading() {
      this.clearInterval(t_id);
			
			document.getElementById("params_form").style.visibility = 'hidden';
            
      var targelem = document.getElementById('loader_container');
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
	
	<div id="loader_container">
		<div id="loader">
			<div align="center">报表加载中...</div>
			<div id="loader_bg"><div id="progress"></div></div>
		</div>
	</div>
	
</body>
</html>