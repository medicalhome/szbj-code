<%@ page contentType="text/html;charset=GBK" %>
<html> 
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=GBK">
</head>
<body>
<a href="#" onclick="getCheck()">直接打印</a>
<script language="javascript">
	function getCheck(){
		var url = "printHint.jsp?rpt=AAA.brt|AAA.brt";
		openwin(url,'newFream');
	}
</script>
<script language="">
	function openwin(url,name){
		window.open(url,name,"height=50, width=150,top=200,left=400,toolbar=no , menubar=no, scrollbars=0, resizable=0, location=0, status=0");
		window.opener=null;
      window.close();
	}
</script>
</body> 
</html> 