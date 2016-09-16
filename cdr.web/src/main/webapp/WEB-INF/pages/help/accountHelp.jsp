<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
	#help_slow div{
		font-size:12pt;
		line-height:30px;
	}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
    <script type="text/javascript" src="../scripts/jquery.multi-open-accordion-1.5.3.js"></script>
    <script type="text/javascript" src="../scripts/jquery.multi-open-accordion-1.5.3.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			/* $('#accordionHelp').accordion(); */
			$('#accordionHelp').multiOpenAccordion({
				active: [1, 3],
				click: function(event, ui) {
					//console.log('clicked')
				},
				init: function(event, ui) {
					//console.log('whoooooha')
				},
				tabShown: function(event, ui) {
					//console.log('shown')
				},
				tabHidden: function(event, ui) {
					//console.log('hidden')
				}
				
			});
			
			$('#accordionHelp').multiOpenAccordion("option", "active", "all");
		});
		
		function jumpTo(id) {
			$("#help_slow").scrollTop(
					$(id).offset().top - $("#help_main").offset().top);
		}
	</script>
</head>

<body>
	<table cellpadding="0" cellspacing="0" border="0" width="100%" height="100%">
		<tr>
			<td id="menuWHelp"
				class="menuContent">
				<div id="accordionHelp" class="menuNav" style="height:465px;width:190px;overflow-y: auto;position: relative; ">
					<!-- $Author :chang_xuewen
   					 $Date : 2014/02/07 10:36$
  					 [BUG]0042170 MODIFY BEGIN -->
					<a class="heading banTab" style="display: block;padding-left:13px;background-color: #d5eaff;color:#e28822;">技术支持：010-88324528</a>
					<!-- [BUG]0042170 MODIFY END -->
					<h3>
						<a href="javascript:void(0);" onclick="jumpTo('#help_main')"
							class="heading uncollapsible banTab"><span
							style="padding-left: 18px;">CDR临床数据中心操作指南</span></a>
					</h3>
					<h3>
						<a href="javascript:void(0);" onclick="jumpTo('#to_menu01')"
							class="heading uncollapsible banTab"><span
							style="padding-left: 20px;">一.系统登陆及患者选择</span></a>
					</h3>
					<div id="findPatient">
						<div id="menu0101" class="marginHelp" onclick="jumpTo('#to_menu0101')">
							<table style="width:100%"><tr>
								<td><img src="../images/pic_small.gif" border="0" align="center"
								style="padding-left: 4px;" /></td>
								<td><div class="headiconHelp">&nbsp;1.1.系统进入方式</div></td>
							</tr></table>
						</div>
						<div id="menu0102" class="marginHelp" onclick="jumpTo('#to_menu0102')">
							<table style="width:100%"><tr>
								<td><img src="../images/pic_small.gif" border="0" align="center"
								style="padding-left: 4px;" /></td>
								<td><div class="headiconHelp">&nbsp;1.2.登陆后如何找到患者</div></td>
							</tr></table>
						</div>
					</div>
					<h3>
						<a href="javascript:void(0);" onclick="jumpTo('#to_menu02')"
							class="heading uncollapsible banTab"><span
							style="padding-left: 15px;">二.患者临床数据的展现方式</span></a>
					</h3>
					<div id="visitData">
						<div id="menu0201" class="marginHelp" onclick="jumpTo('#to_menu0201')">
							<table style="width:100%"><tr>
								<td><img src="../images/pic_small.gif" border="0" align="center"
								style="padding-left: 4px;" /></td>
								<td><div class="headiconHelp">&nbsp;2.1.门诊视图</div></td>
							</tr></table>
						</div>
						<div id="menu0202" class="marginHelp" onclick="jumpTo('#to_menu0202')">
							<table style="width:100%"><tr>
								<td><img src="../images/pic_small.gif" border="0" align="center"
								style="padding-left: 4px;" /></td>
								<td><div class="headiconHelp">&nbsp;2.2.住院视图</div></td>
							</tr></table>
						</div>
						<div id="menu0203" class="marginHelp" onclick="jumpTo('#to_menu0203')">
							<table style="width:100%"><tr>
								<td><img src="../images/pic_small.gif" border="0" align="center"
								style="padding-left: 4px;" /></td>
								<td><div class="headiconHelp">&nbsp;2.3.时间轴视图</div></td>
							</tr></table>
						</div>
						<div id="menu0204" class="marginHelp" onclick="jumpTo('#to_menu0204')">
							<table style="width:100%"><tr>
								<td><img src="../images/pic_small.gif" border="0" align="center"
								style="padding-left: 4px;" /></td>
								<td><div class="headiconHelp">&nbsp;2.4.综合视图</div></td>
							</tr></table>
						</div>
					</div>
					<h3>
						<a href="javascript:void(0);" onclick="jumpTo('#to_menu03')"
							class="heading uncollapsible banTab"><span
							style="padding-left: 20px;">三.特色视图展示</span></a>
					</h3>
					<div id="specialView">
						<div id="menu0301" class="marginHelp" onclick="jumpTo('#to_menu0301')">
							<table style="width:100%"><tr>
								<td><img src="../images/pic_small.gif" border="0" align="center"
								style="padding-left: 4px;" /></td>
								<td><div class="headiconHelp">&nbsp;3.1.医嘱闭环</div></td>
							</tr></table>
						</div>
						<div id="menu0301" class="marginHelp" onclick="jumpTo('#to_menu0302')">
							<table style="width:100%"><tr>
								<td><img src="../images/pic_small.gif" border="0" align="center"
								style="padding-left: 4px;" /></td>
								<td><div class="headiconHelp">&nbsp;3.2.检验历史对比曲线</div></td>
							</tr></table>
						</div>
					</div>
					<h3>
						<a href="javascript:void(0);" onclick="jumpTo('#to_menu04')"
							class="heading uncollapsible banTab"><span
							style="padding-left: 20px;">四.个性化内容设置</span></a>
					</h3>
					<!-- <h3>
						<a href="javascript:void(0);" onclick="jumpTo('#to_menu05')"
							class="heading uncollapsible banTab"><span
							style="padding-left: 20px;">五.常见问题解决方案</span></a>
					</h3> -->
					<h3>
						<a href="javascript:void(0);" onclick="jumpTo('#to_menu06')"
							class="heading uncollapsible banTab"><span
							style="padding-left: 20px;">五.意见反馈功能</span></a>
					</h3>
				</div>
			</td>
			<td style="vertical-align: top;">
				<div id="help_slow" style="height:465px;overflow-y: auto;">
					<table style="margin:5px;">
						<tr style="margin:5px;">
							<td><div id="help_main"><h2 style="text-align: center;">CDR临床数据中心操作指南</h2></div></td>
						<tr>
						<tr style="margin:5px;">
							<!-- $Author :chang_xuewen
		   					 $Date : 2014/02/07 10:36$
		  					 [BUG]0042170 MODIFY BEGIN -->
							<td><div id="help_main"><h4 style="text-align: right;color:#e28822;">技术支持电话：010-88324528</h4></div></td>
							<!-- [BUG]0042170 MODIFY END -->
						<tr>
						<tr><td><div style="margin:5px;">
							&emsp;&emsp;
							CDR临床数据中心（CDR Clinical Data Repository）集成医院内各个临床应用系统的所有数据，和HIS、LIS、RIS 等系统相连，建设统一的临床数据存储中心，
							将一个患者在医院内发生的所有临床活动所产生的临床数据集中存储在一起，方便临床医生、护士、医院管理人员等及时、准确、全方位360度查询该患者的详细信息，辅助临床医生和护士做出更正确的决策。
						</div></td></tr>
						<tr><td><div id="to_menu01" style="margin:5px;">
							<h2 class="textAlignHelp">一.系统登陆及患者选择</h2>
						</div></td></tr>
						<tr><td><div id="to_menu0101" style="margin:5px;">
							<h3 class="textAlignHelp" style="margin:5px;">1.1系统进入方式</h3>
						</div></td></tr>
						<tr><td><div style="margin:5px;">&emsp;
							方法1：通过HIS门诊/住院医生工作站点击【临床数据中心】按钮。门诊和住院医生工作站【临床数据中心】按钮分别见下图(红色框中标出)：
						</div></td></tr>
						<tr><td><img src="../images/help/mzysz.png" style="width:600px;height:400px;margin: 5px;" /></td></tr>
						<tr><td><div class="marginHelp ca">图1.1门诊医生站【临床数据中心】</div></td></tr>
						<tr><td><img src="../images/help/zyysz.png" style="width:600px;height:400px;margin: 5px;" /></td></tr>
						<tr><td><div class="marginHelp ca">图1.2住院医生站【临床数据中心】</div></td></tr>
						<tr><td><div style="margin:5px;">&emsp;
							方法2：在海泰电子病历系统也可以直点击【临床数据中心】调出CDR画面，查看患者。电子病历【临床数据中心】按钮在下图红色框标出：
						</div></td></tr>
						<tr><td><div style="width:100%;text-align: center;"><img src="../images/help/dzbl.png" style="height:400px;margin: 5px;" /></div></td></tr>
						<tr><td><div class="marginHelp ca">图1.3电子病历【临床数据中心】</div></td></tr>
						<tr><td><div style="margin:5px;">&emsp;
							方法3：输入地址：http://cdr.pkuph.com/cdr/login.html，进入系统登陆画面（初始用户名/密码是您的工号）
						</div></td></tr>
						<tr><td><img src="../images/help/cdr_login.png" style="width:600px;height:400px;margin: 5px;" /></td></tr>
						<tr><td><div class="marginHelp ca">图1.4cdr登陆画面</div></td></tr>
						<tr><td><div style="margin:5px; color:red">&emsp;
							注意：初次登陆时，您的账号和密码相同，系统会弹出相应的提示框，提示您修改密码，需要修改的话，点击【确定】即可跳转到密码修改页面。否则点击【取消】即可。
						</div></td></tr>
						<tr><td><div id="to_menu0102" style="margin:5px;">
							<h3 class="textAlignHelp" style="margin:5px;">1.2.登陆后如何找到患者</h3>
						</div></td></tr>
						<tr><td><div style="margin:5px;">&emsp;
							通过HIS门诊/住院医生站、电子病历系统点击【临床数据中心】按钮后均可直接跳转到当前患者的就诊索引视图画面，查看当前患者在本院的全部就诊相关信息，通过在IE浏览器输入url登陆CDR系统时，进入如下患者列表画面，医生可以检索并查看想了解得的患者相关就诊信息；
						</div></td></tr>
						<tr><td><img src="../images/help/hzlb.png" style="width:600px;height:400px;margin: 5px;" /></td></tr>
						<tr><td><div class="marginHelp ca">图1.5cdr患者列表</div></td></tr>
						<tr><td><div style="margin:5px;">•	门(急)诊：当登录医生的访问权限为【可访问我的患者】则画面将列出登陆医生曾经看诊过的且当天有挂号的门（急）诊患者；如果医生有【可访问本科室门诊患者】的权限，则此列表可展示曾在此科室门诊就诊过，且当天有挂号的门（急）诊患者；</div></td></tr>
						<tr><td><div style="margin:5px;">•	住院(在院）：列出登陆医生所在科室所有在院患者（如果医生访问权限是我的患者则只列出自己的患者）；</div></td></tr>
						<tr><td><div style="margin:5px;">•	最近：显示最近7天的门诊和住院就诊患者数据；</div></td></tr>
						<tr><td><div style="margin:5px;">•	检索：按条件检索患者，用户可以根据门诊号、住院号、姓名、性别等条件自由设置查找患者；</div></td></tr>
						<tr><td><div style="margin:5px;">•	收藏夹：当前登录账户以往收藏的典型患者。在实际使用中，发现典型病例，或者自己关注的患者就诊信息，可直接将该患者收藏至收藏夹，以后再查找该患者时可直接从收藏夹打开并查看患者就诊信息；</div></td></tr>
						<tr><td><div style="margin:5px;">&emsp;
							对于通过HIS门诊/住院医生站、电子病历、以及通过IE登陆CDR画面查看患者相关的就诊信息后，如果想要查看其它患者，我们可以点击画面左上方【患者列表】按钮，重新进行患者的检索。如下图：
						</div></td></tr>
						<tr><td><img src="../images/help/cxxzhz.png" style="width:600px;height:400px;margin: 5px;" /></td></tr>
						<tr><td><div class="marginHelp ca">图1.6如何重新选择患者</div></td></tr>
						<tr><td><div id="to_menu02" style="margin:5px;">
							<h2 class="textAlignHelp">二.患者临床数据的展现方式</h2>
						</div></td></tr>
						<tr><td><div class="marginHelp">
							&emsp;&emsp;
							选择患者后默认进入的是该患者的就诊索引视图(下图）。在就诊索引视图中可以清楚的看到患者历次就诊的就诊基本信息（包括门诊、急诊、住院）。就诊索引视图内每一格代表一次就诊。点击日期可进入到相应的视图界面（门急诊类型进入门诊视图、住院类型进入住院视图）。也可以直接点击图中红框区域功能菜单切换到相应视图显示画面；其中画面上方中央部分会显示患者的过敏及危险性信息，此信息会在每个页面上都有相应的展示。
						</div></td></tr>
						<tr><td><img src="../images/help/jzsy.png" class="marginHelp" style="width:600px;height:400px;" /></td></tr>
						<tr><td><div class="marginHelp ca">图2.1就诊索引视图</div></td></tr>
						<tr><td><div id="to_menu0201" style="margin:5px;">
							<h3 class="textAlignHelp" style="margin:5px;">2.1 门诊视图</h3>
						</div></td></tr>
						<tr><td><div>&emsp;&emsp;门诊视图展示患者门（急）诊就诊的所有信息，包括就诊基本信息、诊断、处方、申请单等。</div></td></tr>
						<tr><td><img src="../images/help/mzst.png" style="width:600px;height:400px;margin:5px;" /></td></tr>
						<tr><td><div class="marginHelp ca">图2.2门诊视图</div></td></tr>
						<tr><td><div style="margin:5px;">•	在门诊视图中，除上图列表数据可查看外，每条数据都可被点击来查看其相应的详细数据。</div></td></tr>
						<tr><td><div style="margin:5px;">•	若某患者数据较多时，可选择性点击右上角的▼将某类数据折叠隐藏或点击▲展开显示，也可点击最上边的▼▲全部折叠和展开。</div></td></tr>
						<tr><td><div style="margin:5px;">•	点击画面上方按钮<img src="../images/button_down.gif"/><img src="../images/button_up.gif"/>，可查看该患者前一次、后一次的门诊就诊信息.</div></td></tr>
						<tr><td><div style="margin:5px;">•	在就诊日期栏选择不同的日期，点击<img src="../images/button_go.gif"/>按钮，即可查看该患者不同日期的门（急）诊就诊。</div></td></tr>
						<tr><td><div id="to_menu0202" style="margin:5px;">
							<h3 class="textAlignHelp marginHelp">2.2住院视图</h3>
						</div></td></tr>
						<tr><td><div>&emsp;&emsp;住院视图展示患者历次住院的就诊。包括患者的三测单、诊断、药物医嘱、其他医嘱、检查、检验、病历文书等内容。</div></td></tr>
						<tr><td><img src="../images/help/zyst.png" style="width:600px;height:400px;margin:5px;" /></td></tr>
						<tr><td><div class="marginHelp ca">图2.3住院视图</div></td></tr>
						<tr><td><div style="margin:5px;">•	在上图序号1点击表头的<img src="../images/tree_folder1.gif"/><img src="../images/tree_folder2.gif"/>可隐藏或展示相应的数据。</div></td></tr>
						<tr><td><div style="margin:5px;">•	当鼠标经过住院视图的检验列表有检验报告并且报告有异常的数据时，鼠标下方会自动展示异常信息。</div></td></tr>
						<tr><td><div style="margin:5px;">•	在三测单左侧，点击相应的图标，右侧的三测单展示栏可隐藏/展示相应的数据.</div></td></tr>
						<tr><td><div style="margin:5px;">•	在上图序号2点击相应日期，可展示患者当天医生所开出的所有医嘱以及病历等数据。</div></td></tr>
						<tr><td><div style="margin:5px;">•	患者历次住院数据，可在上图序号3 处，输入相应的日期，点击<img src="../images/button_go.gif"/>按钮。</div></td></tr>
						<tr><td><div id="to_menu0203" style="margin:5px;">
							<h3 class="textAlignHelp marginHelp">2.3 时间轴视图</h3>
						</div></td></tr>
						<tr><td><div style="margin:5px;">
							&emsp;&emsp;
							时间周视图展示患者的所有就诊（包括门（急）诊、住院）信息。包括患者的诊断、药物医嘱、其他医嘱、检查、检验、病历文书等内容。同样，点击表头的<img src="../images/tree_folder1.gif" /><img src="../images/tree_folder2.gif" />可隐藏或展示相应的数据。当鼠标经过住院视图的检验列表有检验报告并且报告有异常的数据时，鼠标下方会自动展示异常信息。
						</div></td></tr>
						<tr><td><img src="../images/help/sjzst.png" class="marginHelp" style="width:600px;height:400px;margin:5px" /></td></tr>
						<tr><td><div class="marginHelp ca">图2.4时间轴视图</div></td></tr>
						<tr><td><div style="margin:5px;">•	时间轴视图界面每一列代表一次就诊，不区分门诊住院。</div></td></tr>
						<tr><td><div style="margin:5px;">•	点击画面上方<img src="../images/button_down.gif"/><img src="../images/button_up.gif"/>
									按钮，或者输入相应日期点击<img src="../images/button_go.gif"/>按钮，可对时间周视图进行翻页的操作。</div></td></tr>
						<tr><td><div id="to_menu0204" style="margin:5px;">
							<h3 class="textAlignHelp marginHelp">2.4综合视图</h3>
						</div></td></tr>
						<tr><td><div style="margin:5px;">
							&emsp;&emsp;
							在综合视图界面，不是按照就诊时间来展示数据，而是将患者所有记录的诊疗数据按照不同的数据类型来展现。通过界面左边的不同菜单列表可打查看不同的诊疗信息，分为：摘要信息、就诊信息、过敏/不良反应、诊断、医嘱、检查、检验、手术、病历文书。每个菜单，可展示不同的数据。
						</div></td></tr>
						<tr><td><img src="../images/help/zhst.png" style="width:600px;height:400px;margin:5px;" /></td></tr>
						<tr><td><div class="marginHelp ca">图2.5综合视图</div></td></tr>
						<tr><td><div id="to_menu03" class="marginHelp">
							<h2 class="textAlignHelp marginHelp">三.特色视图展示</h2>
						</div></td></tr>
						<tr><td><div id="to_menu0301" style="margin:5px;">
							<h3 class="textAlignHelp marginHelp">3.1 医嘱闭环</h3>
						</div></td></tr>
						<tr><td><div style="margin:5px;">
							&emsp;&emsp;
							对于患者相关的【检查】、【检验】数据，CDR不仅仅展示了相关的医嘱以及报告的数据，更展示了改医嘱的相关执行过程，即医嘱闭环。点击医嘱数据，在相应的详细画面中，选择医嘱跟踪标签页，即可看到相关的医嘱闭环数据。如下图：
						</div></td></tr>
						<tr><td><img src="../images/help/yzbh.png" style="width:600px;height:400px;margin:5px;" /></td></tr>
						<tr><td><div class="marginHelp ca">图3.1医嘱闭环</div></td></tr>
						<tr><td><div id="to_menu0302" class="marginHelp">
							<h3 class="textAlignHelp" style="margin:5px;">3.2 检验历史对比曲线</h3>
						</div></td></tr>
						<tr><td><div style="margin:5px;">
							&emsp;&emsp;
							在检验报告中，不光展示了此次检验的结果，相同检验项目的历次检验结果也会在报告中展示，便于医生对结果做出分析。在检验报告标签页中，单击某个检验项目，例如：单击淋巴细胞百分比，则可展示该检验项目的历次检验结果曲线。点击其他检验项目，则可展示其他检验项目的结果曲线.
						</div></td></tr>
						<tr><td><img src="../images/help/lsqxdb.png" style="width:600px;height:200px;margin:5px;" /></td></tr>
						<tr><td><div class="marginHelp ca">图3.2检验历史对比曲线</div></td></tr>
						<tr><td><div id="to_menu04" style="margin:5px;">
							<h2 class="textAlignHelp" style="margin:5px;">四.个性化内容设置</h2>
						</div></td></tr>
						<tr><td><div style="margin:5px;">
							&emsp;&emsp;
							在使用过程中，对于CDR的菜单，以及视图的相关数据的展示，可以根据自己的个人喜好进行设置。在页面右上角有一个系统设置的按钮，点击此按钮可进入系统设置界面，进行系统相关功能的设置。
						</div></td></tr>
						<tr><td><img src="../images/help/stsz.png" style="width:600px;height:400px;margin:5px;" /></td></tr>
						<tr><td><div class="marginHelp ca">图4.1视图设置</div></td></tr>
						<tr><td><img src="../images/help/ywsz.png" style="width:600px;height:200px;margin:5px;" /></td></tr>
						<tr><td><div class="marginHelp ca">图4.2药物设置</div></td></tr>
						<tr><td><div style="margin:5px;">•	视图部分，需要画面展示哪些内容，可在相应的部分打钩。</div></td></tr>
						<tr><td><div style="margin:5px;">•	药物医嘱部分，当药品太多，需要隐藏一些辅助药物（如氯化钠），则输入药品名，点击【添加】按钮，即可。</div></td></tr>
						<!-- <tr><td><div id="to_menu05" style="margin:5px;">
							<h2 class="textAlignHelp" style="margin:5px;">五.常见问题解决方案</h2>
						</div></td></tr>
						<tr><td><div>&emsp;&emsp;1.为什么我的画面展示是错乱的？</div></td></tr>
						<tr><td><div>&emsp;&emsp;
							答：由于本系统是基于IE6/IE8进行设计的，对IE7/IE9的支持比较差，当感觉画面展示相对比较错乱时，我们需要检查浏览器的版本设置。具体方法如下：按键盘F12键，弹出以下画面后，在红色框中选择IE8即可。
						</div></td></tr>
						<tr><td><img src="../images/help/llqsz.png" style="width:600px;height:200px;margin:5px;" /></td></tr>
						<tr><td><div class="marginHelp ca">图5.1浏览器的设置</div></td></tr>
						<tr><td><div>&emsp;&emsp;2.综合视图查看详细数据后，导航栏不见了怎么办？</div></td></tr>
						<tr><td><div>&emsp;&emsp;答：查看详细后，页面左侧的导航栏会自动隐藏，此时点击画面左侧中部向右的箭头，即可重新看见导航栏。同样，想隐藏导航栏时，可在右侧示例图中的红色框部分点击向左的箭头即可。</div></td></tr>
						<tr>
							<td><div style="width:100%;text-align: center;"><img src="../images/help/dhl1.png" style="width:260px;height:500px;margin:5px;" />
							<img src="../images/help/dhl2.png" style="width:260px;height:500px;margin:5px;" /></div></td>
						</tr>
						<tr><td><div class="marginHelp ca">图4.2药物设置</div></td></tr> -->
						<tr><td><div id="to_menu06" style="margin:5px;">
							<h2 class="textAlignHelp" style="margin:5px;">五.意见反馈功能</h2>
						</div></td></tr>
						<tr><td><div class="marginHelp">&emsp;&emsp;画面右上方有一个意见反馈的连接，点击后出现以下画面：</div></td></tr>
						<tr><td><img src="../images/help/yjfk.png" style="width:600px;height:200px;margin:5px;" /></td></tr>
						<tr><td><div class="marginHelp ca">图5.1意见反馈</div></td></tr>
						<tr><td><div style="padding-bottom: 100px;">&emsp;&emsp;
							在使用过系统后，您对系统的优化有什么意见或建议欢迎您在此画面中反馈给我们，我们将及时作出改善。感谢您的大力支持！
						</div></td></tr>
					</table>
				</div>
			</td>
		</tr>
	</table>
</body>
</html>