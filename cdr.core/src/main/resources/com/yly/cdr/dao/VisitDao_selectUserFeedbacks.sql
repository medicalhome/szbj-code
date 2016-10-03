/**
 * 用户反馈意见查询
 * 魏鹏 2012/10/18
 */
SELECT * 
  FROM USER_FEEDBACK
 WHERE DELETE_FLAG = '0'
 /*IF userId != null && userId.length() != 0*/
   AND USER_ID = /*userId*/
   AND PARENT_FEEDBACK_SN IS NULL
 /*END*/
 /*IF parentFeedbackSn != null && parentFeedbackSn.length() != 0*/
   AND PARENT_FEEDBACK_SN = /*parentFeedbackSn*/
 /*END*/
 /*IF userId == null && parentFeedbackSn == null*/
   AND PARENT_FEEDBACK_SN IS NULL
 /*END*/
 ORDER BY CREATE_TIME DESC

 