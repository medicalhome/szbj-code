/**
 * [FUN]V05-101 查询相应表是否存在某字段
 * @version 1.0, 2014/04/17  
 * @author 金鹏
 * @since 1.0 
 * 相应表是否存在某字段
 */
select ut.table_name
  from user_tab_cols ut
 where ut.column_name = /*columnName*/
 /*IF tableNames != null && !tableNames.isEmpty()*/
   and ut.table_name in /*tableNames*/(10,20,30)
 /*END*/