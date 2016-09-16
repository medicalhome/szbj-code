// 意见反馈记录添加和显示
function sendFeedback(feedbackContent,userId,userName,parentFeedbackSn)
{
	var label = '请输入反馈/回复内容！';
	feedbackContent = $.trim(feedbackContent);
	if(feedbackContent == ''||label == feedbackContent)
	{
		return;
	}
	// 转换换行符，使评论信息在页面换行显示。
	feedbackContent = feedbackContent.replace(/\n/g,"<br/>");
	$.ajax({url:'../visit/feedbackCommit.html',
		type:'post',
		dataType:'text',
		data:{feedbackContent:feedbackContent,userId:userId,userName:userName,parentFeedbackSn:parentFeedbackSn},
		success:function(data){
			var result=jQuery.parseJSON(data);
			if(parentFeedbackSn == undefined)
			{
				$("#feedbackTable").prepend(
						"<tr>"+
						"<td colspan='2' align='left' style='font-size:13px;width:770px;padding-left:10px;padding-right:12px;'>"+feedbackContent+"</td>"+
						"</tr><tr style='border-bottom:dashed 1px #B3C4D4;'>"+
						"<td align='left' style='font-size:12px;color:#585959;padding-left:10px;'>"+result.commitDate+"&nbsp;&nbsp;&nbsp;来自用户"+userName+"</td>"+
						"<td align='right' style='font-size:12px;padding-right:12px;'><a id='replyCount"+result.feedbackSn+"' href='javascript:void(0);' style='color:#585959;' onclick=\"showReplysForFeedback('"+result.feedbackSn+"','"+userId+"','"+userName+"',$(this));\">回复("+result.replyCount+")</a></td>"+
				"</tr>");
				$("#feedbackContent").val('');	
			}
			else
			{
				$('#replyTable'+parentFeedbackSn).prepend(
						"<tr>"+
						"<td align='left' style='font-size:12px;width:720px;padding-left:10px;padding-right:12px;'>"+userName+"回复："+feedbackContent+"</td>"+
						"</tr><tr style='border-bottom:dashed 1px #B3C4D4;'>"+
						"<td align='left' style='font-size:12px;color:#585959;padding-left:10px;'>"+result.commitDate+"</td>"+
				"</tr>");
				$('#replyContent'+parentFeedbackSn).val('');	
				$('#replyCount'+parentFeedbackSn).text('回复('+result.replyCount+')');
			}
			},
		error:function(data){
			showMsg("提示","系统问题，请稍后重试！");
		}
	});
}


// 意见反馈回复信息添加和显示
function showReplysForFeedback(parentFeedbackSn,userId,userName,tr)
{
	var feedbackTrId='feedback'+parentFeedbackSn;
	if($('#'+feedbackTrId).length>0)
	{
		$('#'+feedbackTrId).closest("tr").remove();
	}
	else
	{
		tr.closest("tr").after("<tr><td colspan='2'><div id='"+feedbackTrId+"'></div></td></tr>");
		jQuery('#'+feedbackTrId).load('../visit/feedbackReplays.html',{"parentFeedbackSn":parentFeedbackSn,"userId":userId,"userName":userName});		
	}
}
	