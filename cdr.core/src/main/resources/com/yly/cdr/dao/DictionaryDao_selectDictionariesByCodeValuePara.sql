

/**
 * @return 字典列表
 */
SELECT a.cs_code,
       a.cs_name,
       b.cv_id,
       b.cv_code,
       b.cv_value,
       b.py_code,
       b.seq_no
  FROM code_system a, code_value b       
 WHERE a.cs_id = b.cs_id
   AND a.cs_code=/*csId*/
   AND a.delete_flag = 0 
   AND b.delete_flag = 0
