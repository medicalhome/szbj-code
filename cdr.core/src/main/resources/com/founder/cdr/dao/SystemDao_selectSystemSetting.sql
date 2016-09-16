/*
*	SystemDao_selectSystemSetting.sql
*/
select *
  from System_Setting
 where delete_flag = '0'
   and user_sn = /*userSn*/
