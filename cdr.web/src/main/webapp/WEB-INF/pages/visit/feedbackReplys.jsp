<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>意见反馈回复信息</title>
	<meta http-equiv="Pragma" content="no-cache" />
	<meta http-equiv="Cache-Control" content="no-cache" />
	<meta http-equiv="Expires" content="0" />
	<script type="text/javascript" src="../scripts/visit/feedback.js"></script>
	<script type="text/javascript">
    $(document).ready(function(){
	    var label = '请输入反馈/回复内容！';
	    $('.replyContent').css('color','red').val(label).focus(function(){                    
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
	<table style="table-layout:fixed;border:0px;background-color:#F6E0FC;" class="blockTable" cellpadding="2" cellspacing="1" >
		<tr height="60px">
			<!-- $Author :jin_peng
					 $Date : 2013/01/28 18:06$
					 [BUG]0013639 MODIFY BEGIN -->
			<td align="left" style="width:93%"><textarea id="replyContent${parentFeedbackSn}" class="replyContent" rows="3" style="overflow-y:auto;margin-left:9px;width:96%" /></td>
			<!-- [BUG]0013639 MODIFY END -->
			<td align="right"><div style="padding-right:11px;"><input type="button" value="回复" style="background:#C4E2F6;width:51px;height:23px;margin-top:24px;border:1px solid blue;cursor:hand;" onclick="sendFeedback($('#replyContent'+${parentFeedbackSn}).val(),'${userId}','${userName}','${parentFeedbackSn}');"/></div></td>
		</tr>
		<tr>
			<td colspan="2" style="word-break:break-all;WORD-WRAP: break-word;margin-top:4px;padding-left:40px;" align="left">
				<table id="replyTable${parentFeedbackSn}" width="730px">
					<c:forEach items="${userFeedbackReplys}" var="userReply" varStatus="status">
						<tr style="${status.count==1?'':'border-top:dashed 1px #B3C4D4;'}">
							<td align="left" style="font-size:12px;width:720px;padding-left:10px;padding-right:12px;">${userReply.userName}回复：${userReply.content}</td>
						</tr>
						<tr>
							<td align="left" style="font-size:12px;color:#585959;padding-left:10px;"><fmt:formatDate value="${userReply.createTime}" type="date" pattern="yyyy年MM月dd日HH:mm" /></td>
						</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>