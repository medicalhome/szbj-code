/*
*	SystemDao_selectSystemMenus.sql
*/
select menu_code, create_time, update_time, menu_sn, parent_menu_sn, menu_level, update_count, delete_flag, delete_time, menu_name, auths 
  from SYSTEM_MENU
 where delete_flag = '0'
   and menu_code in /*menuCodes*/(10,20,30)
 order by menu_code 
