/*
*	PrescriptionDao_selectCodeDrug.sql
*/
select * 
from code_drug 
where code = /*drugCode*/''
and serial = /*serialNo*/''
and delete_flag = '0'