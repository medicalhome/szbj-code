/**
 * 查询状态为未运行的消息，还需要添加v3消息ID条件过滤。
 * @param vid v3消息ID
 * @return 
 */
  SELECT a.id,
         a.vid,
         a.content,
         a.datetime,
         a.state
    FROM MESSAGE a
   WHERE ROWNUM = 1 AND a.state = '0' AND a.vid = /*messageId*/
ORDER BY a.datetime