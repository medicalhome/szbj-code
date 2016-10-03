<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="translation.tld" prefix="ref"%>
<jsp:useBean id="Constants" class="com.yly.cdr.core.Constants" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>用户意见反馈栏</title>
	<meta http-equiv=”X-UA-Compatible” content=”IE=edge” /> 
	<link rel="Stylesheet" type="text/css" href="../styles/tablelist.css" />
	<script type="text/javascript" src="../scripts/visit/feedback.js"></script>
	<style type="text/css">
		a:link,a:visited{
		 text-decoration:none;  /*超链接无下划线*/
		}
		a:hover{
		 text-decoration:underline;  /*鼠标放上去有下划线*/
		}
	</style>
	<script type="text/javascript">
		$(document).ready(function(){
			var label = '请输入反馈/回复内容！';
			$('#feedbackContent').css('color','red').val(label).focus(function(){                    
				if($(this).val() == label){
					$(this).val('').css('color','black');                    
					}                
				}).blur(function(){                   
			if($(this).val() == ''){                       
				$(this).val(label).css('color','red');                    
				}    
			})
		})
	</script>
</head>
<body style="margin: 0; padding: 0;">
	<form id="conditionForm" name="conditionForm" method="post" action="../visit/list_${patientSn}.html">
		<table style="table-layout:fixed;border:0px;" class="blockTable" cellpadding="2" cellspacing="1" width="800px" >
			<tr>
				<!-- $Author :jin_peng
					 $Date : 2013/01/28 18:06$
					 [BUG]0013639 MODIFY BEGIN -->
				<td align="center"><textarea id="feedbackContent" rows="5" style="overflow-y:auto;margin-top:8px;width:97%"></textarea></td>
				<!-- [BUG]0013639 MODIFY END -->
			</tr>
			<tr>
				<td align="right"><div style="padding-right:11px;height:31px;"><input type="button" style="cursor:hand;border:0px;width:51px;height:23px;margin-top:4px;background-image: url('../images/fk.jpg');" onclick="sendFeedback($('#feedbackContent').val(),'${userId}','${userName}');"/></div></td>
			</tr>
			<tr>
				<td style="word-break:break-all;WORD-WRAP: break-word;margin-top:4px;" align="center">
					<div style="overflow-y:auto;overflow-x:visible;height:340px;width:788px">
						<table id="feedbackTable" width="770px">
							<c:forEach items="${userFeedbacks}" var="userFeedback" varStatus="status">
								<tr style="${status.count==1?'':'border-top:dashed 1px #B3C4D4;'}">
									<td colspan="2" align="left" style="font-size:13px;width:770px;padding-left:10px;padding-right:12px;">${userFeedback.content}</td>
								</tr>
								<tr>
									<td align="left" style="font-size:12px;color:#585959;padding-left:10px;"><fmt:formatDate value="${userFeedback.createTime}" type="date" pattern="yyyy年MM月dd日HH:mm" />&nbsp;&nbsp;&nbsp;来自用户${userFeedback.userName}</td>
									<td align="right" style="font-size:12px;padding-right:12px;"><a id="replyCount${userFeedback.feedbackSn}" href="javascript:void(0);" style="color:#585959;" onclick="showReplysForFeedback('${userFeedback.feedbackSn}','${userId}','${userName}',$(this));">回复(${userFeedback.replyCount})</a></td>
								</tr>
							</c:forEach>
						</table>
					</div>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
