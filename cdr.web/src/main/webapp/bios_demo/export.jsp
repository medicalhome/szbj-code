<%@page contentType="text/html; charset=gbk"%>
<html>
<head>
<script language="javascript">
	function getchkval(pre){
		var val = "";
		for(var i=1;;i++){
			var chk = document.all[pre + i];
			if(chk){
				if(chk.checked){
					if(val != "")
						val += "|";
					val += chk.value;
				}
			}else{
				break;
			}
		}
		return val;
	}
	function doExport(){
		paramForm.rpts.value = getchkval('rpt');
		paramForm.submit();	
	}	
</script>
</head>
<body leftmargin="20">
<table style="font-size:13px" cellspacing=0>
  <tr>
  	<td colspan='2'><b>��������</b></td>
  </tr>
  <form id="paramForm" method="post"  action='/report/bios_jsp/batchExport.jsp' method='post'>
  <input type="hidden" name="rpts" value="">
  <tr>
  	<td style='border-top: 1px solid gray;' valign='top'>ѡ�񱨱�</td>
  	<td style='border-top: 1px solid gray;'>
  		<input type='checkbox' name='rpt1' value="rpt=Demo/��������/ʱ�����.brt^params=year=1997" checked>���۶�ͳ���걨_1997�� &nbsp;
  		<input type='checkbox' name='rpt2' value="rpt=Demo/��������/ʱ�����.brt^params=year=1998" checked>���۶�ͳ���걨_1998�� &nbsp;
  		<input type='checkbox' name='rpt3' value="rpt=Demo/���鱨��/5�������.brt" checked>Ա����Ϣ�������ͳ�� &nbsp;
		</td>
  </tr>
  <tr>
  	<td>������ʽ��</td>
  	<td>
  		<SELECT name="filetype">
				<OPTION value="xls">EXCEL��ʽ
			</SELECT>
		</td>
  </tr>
  <tr>
  	<td>�ļ����ƣ�</td>
  	<td>
  		<input type="text" name='filename'>
		</td>
  </tr>  
  <tr>
  	<td style='border-top: 1px solid gray;' colspan='2' align='right'><input type="button" value=" �� �� " onclick="doExport()"></td>
  </tr>
  </form>
</table>
</body>
</html>