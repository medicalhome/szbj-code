SELECT *
  FROM message
 WHERE id = /*id*/'1'
   AND create_time = to_date(/*createTime*/'2012-10-10', 'YYYY-MM-DD hh24:mi:ss')
