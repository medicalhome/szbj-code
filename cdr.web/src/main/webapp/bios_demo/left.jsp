<%@page contentType="text/html; charset=gbk"%>
<%@page import="java.util.List,bios.report.server.treeviewer.*"%>
<jsp:useBean id="getter" scope="page" class="bios.report.server.treeviewer.ReportsGetter" />
<%
  List dataItems = getter.getDataItems(pageContext.getServletContext());
  String root = request.getContextPath();
%>
<html>

	<head>
		<title></title>
		<link href="js/treeview.css" rel="stylesheet" type="text/css" />
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<style type="text/css">		
		</style>
	</head>

	<body leftmargin="20" background="img/leftbg.gif">
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td class="q" align="left">
					<script language="JavaScript" src="js/treeview.js"></script>
					<div id="a">

<script language="JavaScript">
<!--
//�½����˵�
var context = '<%=root%>';
var tree=new TreeView("tree");
tree.add(1, 0, "���� - ��ʾ", "welcome.htm", "", "mainFrame", "img/project.gif");

tree.add(100, 1, "����Ӧ��", "", "", "", "img/folder.gif");

tree.add(200, 1, "��������", "", "", "", "img/folder.gif");
tree.add(201, 200, "URL����(GET)", context+"/bios_demo/url_params.jsp", "", "mainFrame", "img/report.gif");
tree.add(202, 200, "���ύ(POST)", context+"/bios_demo/form_params.jsp", "", "mainFrame", "img/report.gif");
tree.add(203, 200, "��ȡ����", context+"/ReportEmitter?rpt=Demo/��������/��ȡ����.brt", "", "mainFrame", "img/report.gif");
tree.add(203, 200, "CDR", context+"/ReportEmitter?rpt=Demo/��������/CDR.brt&params=sn=2422818", "", "mainFrame", "img/report.gif");

tree.add(300, 1, "���鱨��", "", "", "", "img/folder.gif");
tree.add(301, 300, "1��ͨ����", context+"/ReportEmitter?rpt=Demo/���鱨��/1��ͨ����.brt", "", "mainFrame", "img/report.gif");
tree.add(302, 300, "2���˷���", context+"/ReportEmitter?rpt=Demo/���鱨��/2���˷���.brt", "", "mainFrame", "img/report.gif");
tree.add(303, 300, "3���η���", context+"/ReportEmitter?rpt=Demo/���鱨��/3���η���.brt", "", "mainFrame", "img/report.gif");
tree.add(304, 300, "4����ȫ����", context+"/ReportEmitter?rpt=Demo/���鱨��/4����ȫ����.brt", "", "mainFrame", "img/report.gif");
tree.add(305, 300, "5�������", context+"/ReportEmitter?rpt=Demo/���鱨��/5�������.brt", "", "mainFrame", "img/report.gif");

tree.add(400, 1, "������չ", "", "", "", "img/folder.gif");
tree.add(401, 400, "1��㽻��", context+"/ReportEmitter?rpt=Demo/������չ/1��㽻��.brt", "", "mainFrame", "img/report.gif");
tree.add(402, 400, "2�����Ƭ", context+"/ReportEmitter?rpt=Demo/������չ/2�����Ƭ.brt", "", "mainFrame", "img/report.gif");
tree.add(403, 400, "3�����Ƭ", context+"/ReportEmitter?rpt=Demo/������չ/3�����Ƭ.brt", "", "mainFrame", "img/report.gif");
tree.add(404, 400, "4���������Ƭ", context+"/ReportEmitter?rpt=Demo/������չ/4���������Ƭ.brt", "", "mainFrame", "img/report.gif");
tree.add(405, 400, "5��չ�뾲̬", context+"/ReportEmitter?rpt=Demo/������չ/5��չ�뾲̬.brt", "", "mainFrame", "img/report.gif");
tree.add(406, 400, "6�ۻ�����", context+"/ReportEmitter?rpt=Demo/������չ/6�ۻ�����.brt", "", "mainFrame", "img/report.gif");
tree.add(407, 400, "7ռ����ʾ", context+"/ReportEmitter?rpt=Demo/������չ/7ռ����ʾ.brt", "", "mainFrame", "img/report.gif");
tree.add(408, 400, "8�����������", context+"/ReportEmitter?rpt=Demo/������չ/8�����������.brt", "", "mainFrame", "img/report.gif");
tree.add(409, 400, "9����������", context+"/ReportEmitter?rpt=Demo/������չ/9����������.brt", "", "mainFrame", "img/report.gif");

tree.add(500, 1, "ͼ��չ��", "", "", "", "img/folder.gif");
tree.add(501, 500, "��̬ɫ��", context+"/ReportEmitter?rpt=Demo/ͼ��չ��/��̬ɫ��.brt", "", "mainFrame", "img/report.gif");
tree.add(502, 500, "����չʾ", context+"/ReportEmitter?rpt=Demo/ͼ��չ��/����չʾ.brt", "", "mainFrame", "img/report.gif");
tree.add(503, 500, "ͼ�͹���", context+"/ReportEmitter?rpt=Demo/ͼ��չ��/ͼƬ��ͼ��.brt", "", "mainFrame", "img/report.gif");
tree.add(504, 500, "���ֶ�֧��", context+"/ReportEmitter?rpt=Demo/ͼ��չ��/Ա����Ϣ.brt", "", "mainFrame", "img/report.gif");

tree.add(600, 1, "ͼ����ʾ", "", "", "", "img/folder.gif");
tree.add(601, 600, "��״ͼ", context+"/ReportEmitter?rpt=Demo/ͼ����ʾ/��״ͼ.brt", "", "mainFrame", "img/report.gif");
tree.add(602, 600, "��״ͼ", context+"/ReportEmitter?rpt=Demo/ͼ����ʾ/��״ͼ.brt", "", "mainFrame", "img/report.gif");
tree.add(603, 600, "����ͼ", context+"/ReportEmitter?rpt=Demo/ͼ����ʾ/����ͼ.brt", "", "mainFrame", "img/report.gif");
tree.add(604, 600, "����ͼ", context+"/ReportEmitter?rpt=Demo/ͼ����ʾ/����ͼ.brt", "", "mainFrame", "img/report.gif");
tree.add(605, 600, "ɢ��ͼ", context+"/ReportEmitter?rpt=Demo/ͼ����ʾ/ɢ��ͼ.brt", "", "mainFrame", "img/report.gif");
tree.add(606, 600, "ʱ��ͼ", context+"/ReportEmitter?rpt=Demo/ͼ����ʾ/ʱ��ͼ.brt", "", "mainFrame", "img/report.gif");
tree.add(607, 600, "�Ǳ���ͼ", context+"/ReportEmitter?rpt=Demo/ͼ����ʾ/�Ǳ���ͼ.brt", "", "mainFrame", "img/report.gif");
tree.add(608, 600, "ͼ���ۺ�", context+"/ReportEmitter?rpt=Demo/ͼ����ʾ/ͼ���ۺ�.brt", "", "mainFrame", "img/report.gif");
tree.add(609, 600, "ͼ����ȡ", context+"/ReportEmitter?rpt=Demo/�߼�����/ͼ������/column.brt", "", "mainFrame", "img/report.gif");

tree.add(700, 1, "�й�ʽ����", "", "", "", "img/folder.gif");
tree.add(701, 700, "б�ߵ�Ԫ��", context+"/ReportEmitter?rpt=Demo/�й�ʽ����/б��Ӧ��.brt", "", "mainFrame", "img/report.gif");
tree.add(702, 700, "��ͷ����", context+"/ReportEmitter?rpt=Demo/�й�ʽ����/��ͷ����.brt&pagecap=20", "", "mainFrame", "img/report.gif");
tree.add(703, 700, "�����Զ�����", context+"/ReportEmitter?rpt=Demo/�й�ʽ����/�����Զ�����.brt", "", "mainFrame", "img/report.gif");
tree.add(704, 700, "�ӱ���", context+"/bios_jsp/report_loader.jsp?rpt=Demo/�й�ʽ����/�ӱ�����ʾ.brt&params=path_prefix=Demo/", "", "mainFrame", "img/report.gif");

tree.add(800, 1, "TagLib����", "", "", "", "img/folder.gif");
tree.add(801, 800, "��ҳ����", context+"/bios_jsp/common_tag.jsp?rpt=Demo/������չ/4���������Ƭ.brt", "", "mainFrame", "img/report.gif");
tree.add(802, 800, "��ҳ����", context+"/bios_jsp/common_tag.jsp?rpt=Demo/���鱨��/5�������.brt", "", "mainFrame", "img/report.gif");
tree.add(803, 800, "����ǩ", context+"/bios_jsp/reportset_tag.jsp", "", "mainFrame", "img/report.gif");

tree.add(880, 1, "������ͬ��", "", "", "", "img/folder.gif");
tree.add(881, 880, "��������1", context+"/ReportEmitter?rpt=Demo/������ͬ��/��������1.brt", "", "mainFrame", "img/report.gif");
tree.add(882, 880, "��������2", context+"/ReportEmitter?rpt=Demo/������ͬ��/��������2.brt", "", "mainFrame", "img/report.gif");
tree.add(883, 880, "������ͬ��", context+"/ReportEmitter?rpt=Demo/������ͬ��/������ͬ��.brt", "", "mainFrame", "img/report.gif");

tree.add(900, 1, "�߼�����", "", "", "", "img/folder.gif");
tree.add(901, 900, "�Զ�������", "", "", "", "img/folder.gif");
tree.add(9011, 901, "��������", context+"/ReportEmitter?rpt=Demo/�߼�����/�Զ�������/��������.brt", "", "mainFrame", "img/report.gif");
tree.add(9012, 901, "������չ", context+"/ReportEmitter?rpt=Demo/�߼�����/�Զ�������/������չ.brt", "", "mainFrame", "img/report.gif");
tree.add(903, 900, "�Զ������ݼ�", context+"/ReportEmitter?rpt=Demo/�߼�����/�Զ������ݼ�.brt", "", "mainFrame", "img/report.gif");
tree.add(904, 900, "�״�ʾ��", context+"/ReportEmitter?rpt=Demo/�߼�����/�״���ʾ.brt", "", "mainFrame", "img/report.gif");
tree.add(905, 900, "��չλ��", context+"/ReportEmitter?rpt=Demo/�߼�����/��չλ��.brt", "", "mainFrame", "img/report.gif");

tree.add(1000, 1, "��������ʾ", "", "", "", "img/folder.gif");
tree.add(1001, 1000, "������", context+"/bios_jsp/param_query.jsp?rpt=Demo/��������/ʱ�����.brt", "", "mainFrame", "img/report.gif");

tree.add(1300, 1, "����Ӧ��", "", "", "", "img/folder.gif");
tree.add(1301, 1300, "��̬����", context+"/ReportEmitter?rpt=Demo/����Ӧ��/��̬����.brt", "", "mainFrame", "img/report.gif");
tree.add(1302, 1300, "��̬չ��", context+"/ReportEmitter?rpt=Demo/����Ӧ��/��̬չ��.brt", "", "mainFrame", "img/report.gif");
tree.add(1303, 1300, "��������", context+"/bios_jsp/param_query.jsp?rpt=Demo/����Ӧ��/��������.brt&fitwidth=true&autoquery=true", "", "mainFrame", "img/report.gif");
tree.add(1305, 1300, "��������2", context+"/bios_demo/analysis2.jsp", "", "mainFrame", "img/report.gif");
tree.add(1304, 1300, "��̬ͳ��", context+"/bios_jsp/param_query.jsp?rpt=Demo/����Ӧ��/��̬ͳ��.brt&fitwidth=true&autoquery=true&params=degree=bios_select_all;dept=bios_select_all", "", "mainFrame", "img/report.gif");

tree.add(1400, 1, "�չʾ", "", "", "", "img/folder.gif");
tree.add(1401, 1400, "���", context+"/ReportEmitter?rpt=Demo/�/�.brt", "", "mainFrame", "img/report.gif");
tree.add(1402, 1400, "��ʽ�", context+"/ReportEmitter?rpt=Demo/�/��ʽ�.brt&toolbardisplay=top", "", "mainFrame", "img/report.gif");
tree.add(1403, 1400, "��Դ�", context+"/ReportEmitter?rpt=Demo/�/��Դ�.brt", "", "mainFrame", "img/report.gif");
tree.add(1404, 1400, "�����", context+"/ReportEmitter?rpt=Demo/�/�����.brt", "", "mainFrame", "img/report.gif");

tree.add(1500, 1, "����Ӧ��", "", "", "", "img/folder.gif");
tree.add(1501, 1500, "��������", context + "/bios_demo/export.jsp", "", "mainFrame", "img/report.gif");
tree.add(1502, 1500, "������ӡ", context + "/bios_jsp/batchPrint.jsp", "", "mainFrame", "img/report.gif");

tree.add(1100, 1, "���߰���", "http://www.bijetsoft.com", "", "_blank", "img/help.gif");

document.write(tree);

var customTree=new TreeView("customTree");
<%
	for(int i=0;i<dataItems.size();i++){
  		DataItem item = (DataItem)dataItems.get(i);
	  	switch(item.getType()){
	  		case DataItem.PROJECT:{
	  		%>
	  			customTree.add(<%=item.getId()%>, <%=item.getParentId()%>, "<%=item.getName()%>", "", "", "", "img/project.gif");
	  		<%
	  			break;	
	  		}
	  		case DataItem.FOLDER:{
		  	%>
	  			customTree.add(<%=item.getId()%>, <%=item.getParentId()%>, "<%=item.getName()%>");
	  		<%
		  		break;
	  		}
	  		case DataItem.FILE:{
	  		%>
	  			customTree.add(<%=item.getId()%>, <%=item.getParentId()%>, "<%=item.getName()%>", "/report/<%=item.getUrl()%>", "", "mainFrame", "img/report.gif");
	  		<%
	  			break;
	  		}
	  	}
	}
%>
document.write(customTree);

//-->
</script>

					</div>
				</td>
			</tr>
		</table>

	</body>
</html>
