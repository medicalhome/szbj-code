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
			cutName(".headiconHelp",11);
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
							style="padding-left: 8px;">CDR临床数据中心常见问题</span></a>
					</h3>
					<h3>
						<a href="javascript:void(0);" onclick="jumpTo('#to_menu01')"
							class="heading uncollapsible banTab"><span
							style="padding-left: 20px;">一.常见数据问题</span></a>
					</h3>
					<div id="findPatient">
						<div id="menu0101" class="marginHelp" onclick="jumpTo('#to_menu0101')">
							<table style="width:100%"><tr>
								<td><img src="../images/pic_small.gif" border="0" align="center"
								style="padding-left: 4px;" /></td>
								<td style="word-break:break-all;"><div class="headiconHelp">&nbsp;1.1门诊患者存在就诊次数重复，或就诊次数多于实际就诊的数据</div></td>
							</tr></table>
						</div>
						<div id="menu0102" class="marginHelp" onclick="jumpTo('#to_menu0102')">
							<table style="width:100%"><tr>
								<td><img src="../images/pic_small.gif" border="0" align="center"
								style="padding-left: 4px;" /></td>
								<td><div class="headiconHelp">&nbsp;1.2部分门诊用药医嘱数据没有药物使用次剂量、用药途径等信息</div></td>
							</tr></table>
						</div>
						<div id="menu0102" class="marginHelp" onclick="jumpTo('#to_menu0103')">
							<table style="width:100%"><tr>
								<td><img src="../images/pic_small.gif" border="0" align="center"
								style="padding-left: 4px;" /></td>
								<td><div class="headiconHelp">&nbsp;1.3部分门诊患者只有挂号信息，没有诊断，医嘱等其它信息。</div></td>
							</tr></table>
						</div>
						<div id="menu0102" class="marginHelp" onclick="jumpTo('#to_menu0104')">
							<table style="width:100%"><tr>
								<td><img src="../images/pic_small.gif" border="0" align="center"
								style="padding-left: 4px;" /></td>
								<td><div class="headiconHelp">&nbsp;1.4住院患者医嘱确认时间比医嘱下达时间早</div></td>
							</tr></table>
						</div>
						<div id="menu0102" class="marginHelp" onclick="jumpTo('#to_menu0105')">
							<table style="width:100%"><tr>
								<td><img src="../images/pic_small.gif" border="0" align="center"
								style="padding-left: 4px;" /></td>
								<td><div class="headiconHelp">&nbsp;1.5部分检验，微生物、形态学检验报告关联不到就诊</div></td>
							</tr></table>
						</div>
						<div id="menu0102" class="marginHelp" onclick="jumpTo('#to_menu0106')">
							<table style="width:100%"><tr>
								<td><img src="../images/pic_small.gif" border="0" align="center"
								style="padding-left: 4px;" /></td>
								<td><div class="headiconHelp">&nbsp;1.6部分影像中心检查报告关联不到就诊</div></td>
							</tr></table>
						</div>
						<div id="menu0102" class="marginHelp" onclick="jumpTo('#to_menu0107')">
							<table style="width:100%"><tr>
								<td><img src="../images/pic_small.gif" border="0" align="center"
								style="padding-left: 4px;" /></td>
								<td><div class="headiconHelp">&nbsp;1.7部分电子病历的病历文书关联不到就诊</div></td>
							</tr></table>
						</div>
					</div>
					<h3>
						<a href="javascript:void(0);" onclick="jumpTo('#to_menu02')"
							class="heading uncollapsible banTab"><span
							style="padding-left: 15px;">二.系统操作常见问题</span></a>
					</h3>
					<div id="visitData">
						<div id="menu0201" class="marginHelp" onclick="jumpTo('#to_menu0201')">
							<table style="width:100%"><tr>
								<td><img src="../images/pic_small.gif" border="0" align="center"
								style="padding-left: 4px;" /></td>
								<td><div class="headiconHelp">&nbsp;2.1为什么我的画面展示是错乱的？</div></td>
							</tr></table>
						</div>
						<div id="menu0202" class="marginHelp" onclick="jumpTo('#to_menu0202')">
							<table style="width:100%"><tr>
								<td><img src="../images/pic_small.gif" border="0" align="center"
								style="padding-left: 4px;" /></td>
								<td><div class="headiconHelp">&nbsp;2.2综合视图查看详细数据后，导航栏不见了怎么办？</div></td>
							</tr></table>
						</div>
						<div id="menu0203" class="marginHelp" onclick="jumpTo('#to_menu0203')">
							<table style="width:100%"><tr>
								<td><img src="../images/pic_small.gif" border="0" align="center"
								style="padding-left: 4px;" /></td>
								<td><div class="headiconHelp">&nbsp;2.3根据患者住院号，门诊号等信息在患者检索画面中检索不到患者的原因？</div></td>
							</tr></table>
						</div>
				</div>
			</td>
			<td style="vertical-align: top;">
				<div id="help_slow" style="height:465px;overflow-y: auto;">
					<table style="margin:5px;">
						<tr style="margin:5px;">
							<td><div id="help_main"><h2 style="text-align: center;">CDR临床数据中心常见问题</h2></div></td>
						<tr>
						<tr style="margin:5px;">
							<!-- $Author :chang_xuewen
         					 $Date : 2014/02/07 10:36$
        					 [BUG]0042170 MODIFY BEGIN -->
							<td><div id="help_main"><h4 style="text-align: right;color:#e28822;">技术支持：010-88324528</h4></div></td>
							<!-- [BUG]0042170 MODIFY END -->
						<tr>
						<tr><td><div id="to_menu01" style="margin:5px;">
							<h2 class="textAlignHelp">一.常见数据问题</h2>
						</div></td></tr>
						<tr><td><div id="to_menu0101" style="margin:5px;">
							<h3 class="textAlignHelp" style="margin:5px;">1.1问题1:门诊患者存在就诊次数重复，或就诊次数多于实际就诊的数据</h3>
						</div></td></tr>
						<tr><td><div style="margin:5px;">&emsp;
							原因说明: 可能以下俩个原因导致:
						</div></td></tr>
						<tr><td><div style="margin:5px;">&emsp;
							1.患者有多张就诊卡，每张就诊卡的就诊次数都是单独计算的。
						</div></td></tr>
						<tr><td><div style="margin:5px;">&emsp;
							2.患者就诊次数并不单指一次挂号就诊，会包括单独交费等，因此会多于实际就诊次数(HIS已安排改造,改造后数据将不存在此问题)。
						</div></td></tr>
						<tr><td><div id="to_menu0102" style="margin:5px;">
							<h3 class="textAlignHelp" style="margin:5px;">1.2问题2: 部分门诊用药医嘱数据没有药物使用次剂量、用药途径等信息</h3>
						</div></td></tr>
						<tr><td><div style="margin:5px;">&emsp;
							原因说明: 退费重收时，因为是将原有的医嘱退掉，药品医嘱使用剂量、频率等与原医嘱一致，没有再单独记录,所以退费重收产生的医嘱中没有这部分信息。
						</div></td></tr>
						<tr><td><div id="to_menu0103" style="margin:5px;">
							<h3 class="textAlignHelp" style="margin:5px;">1.3问题3: 部分门诊患者只有挂号信息，没有诊断，医嘱等其它信息</h3>
						</div></td></tr>
						<tr><td><div style="margin:5px;">&emsp;
							原因说明：通常的情况是，患者使用了另外的卡就诊，也有少数情况是患者挂了号没来就诊。
						</div></td></tr>
						<tr><td><div id="to_menu0104" style="margin:5px;">
							<h3 class="textAlignHelp" style="margin:5px;">1.4问题4：住院患者医嘱确认时间比医嘱下达时间早</h3>
						</div></td></tr>
						<tr><td><div style="margin:5px;">&emsp;
							原因说明：历史数据记录的时间点有些问题，目前已提交BUG，待更正。
						</div></td></tr>
						<tr><td><div id="to_menu0105" style="margin:5px;">
							<h3 class="textAlignHelp" style="margin:5px;">1.5问题5：部分检验，微生物、形态学检验报告关联不到就诊</h3>
						</div></td></tr>
						<tr><td><div style="margin:5px;">&emsp;
							原因说明：检验业务系统历史数据存在数据不整合情况，有很多数据与就诊关联不上，只能挂到患者名下。
						</div></td></tr>
						<tr><td><div id="to_menu0106" style="margin:5px;">
							<h3 class="textAlignHelp" style="margin:5px;">1.6问题6：部分影像中心检查报告关联不到就诊</h3>
						</div></td></tr>
						<tr><td><div style="margin:5px;">&emsp;
							原因说明：检查业务系统历史数据存在数据不整合情况，有很多数据与就诊关联不上，只能挂到患者名下。
						</div></td></tr>
						<tr><td><div id="to_menu0107" style="margin:5px;">
							<h3 class="textAlignHelp" style="margin:5px;">1.7问题7：部分电子病历的病历文书关联不到就诊</h3>
						</div></td></tr>
						<tr><td><div style="margin:5px;">&emsp;
							原因说明：EMR历史数据存在数据不整合情况，有数据与就诊关联不上，只能挂到患者名下。
						</div></td></tr>
						<tr><td><div id="to_menu0107" style="margin:5px;">
							<h3 class="textAlignHelp" style="margin:5px;">1.8问题8：查不到患者门诊数据 ，或者查不到患者住院数据</h3>
						</div></td></tr>
						<tr><td><div style="margin:5px;">&emsp;
							原因说明：患者从门诊转住院时，没有开电子入院许可登记，使得门诊数据与住院数据没有关联上，请联系010-88324528，后台处理数据。
						</div></td></tr>
						
						<tr><td><div id="to_menu02" style="margin:5px;">
							<h2 class="textAlignHelp">二.系统操作常见问题</h2>
						</div></td></tr>						
						<tr><td><div id="to_menu0201" style="margin:5px;">
							<h3 class="textAlignHelp" style="margin:5px;">2.1 问题1: 为什么我的画面展示是错乱的？</h3>
						</div></td></tr>
						<tr><td><div>&emsp;&emsp;答：由于本系统是基于IE6/IE8进行设计的，目前针对IE7/IE9以及其他浏览器的兼容处理还待改善，当您感觉画面展示相对布局等错乱时，请检查浏览器的版本设置。具体方法如下：按键盘F12键，弹出以下画面后，在红色框中选择IE8即可。</div></td></tr>
						<tr><td><img src="../images/help/llqsz.png" style="width:600px;height:200px;margin:5px;" /></td></tr>
						<tr><td><div class="marginHelp ca">图5.1浏览器的设置</div></td></tr>
						<tr><td><div id="to_menu0202" style="margin:5px;">
							<h3 class="textAlignHelp" style="margin:5px;">2.2 问题2:综合视图查看详细数据后，导航栏不见了怎么办？</h3>
						</div></td></tr>
						<tr><td><div>&emsp;&emsp;答：查看详细后，页面左侧的导航栏会自动隐藏，此时点击画面左侧中部向右的箭头，即可重新看见导航栏。同样，想隐藏导航栏时，可在右侧示例图中的红色框部分点击向左的箭头即可。</div></td></tr>
						<tr>
							<td><div style="width:100%;text-align: center;"><img src="../images/help/dhl1.png" style="width:260px;height:500px;margin:5px;" />
							<img src="../images/help/dhl2.png" style="width:260px;height:500px;margin:5px;" /></div></td>
						</tr>
						<tr><td><div class="marginHelp ca">图4.2药物设置</div></td></tr>
						<tr><td><div id="to_menu0203" style="margin:5px;">
							<h3 class="textAlignHelp" style="margin:5px;">2.3问题3：根据患者住院号，门诊号等信息在患者检索画面中检索不到患者的原因？</h3>
						</div></td></tr>
						<tr><td><div style="margin:5px;">&emsp;
							答：首先请确认检索条件输入信息是否有误，如果无误，请确认该患者是否是权限访问内患者，例如：访问权限为【科室患者】，但检索的患者没有在该用户出诊科室就过诊。
						</div></td></tr>
					</table>
				</div>
			</td>
		</tr>
	</table>
</body>
</html>