/*
*	@auther:tong_meng
*/
/*IF deleteCodeDrugIds != null && deleteCodeDrugIds.length != 0*/
delete
  from hide_drug hd
 where not exists 
(
      select 'X'
      from hide_drug hd_x
      where hd_x.drug_code in /*deleteCodeDrugIds*/(10,20,30)
      and hd.drug_code = hd_x.drug_code
      and hd.delete_flag = '0'
      and user_name = /*userSn*/
)
/*END*/

/*IF deleteCodeDrugIds == null || deleteCodeDrugIds.length == 0*/
delete 
  from hide_drug 
 where user_name = /*userSn*/
   and delete_flag = '0'
/*END*/