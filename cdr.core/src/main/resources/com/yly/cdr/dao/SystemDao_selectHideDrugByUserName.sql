/*
*	@auther:tong_meng
*/
 SELECT hd.drug_code,
       hd.drug_name,
       hd.serial_no,
       hd.create_time
  FROM hide_drug hd
 WHERE hd.user_name = /*userSn*/
   and hd.delete_flag = '0'
order by create_time
