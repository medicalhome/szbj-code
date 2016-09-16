/**
*
*[FUN]查询操作记录是否重复
*@version 1.0, 2013/08/26
*@author 常学文
*@since 1.0
*/
select count(1) from
Nursing_Operation nor
where nor.order_sn=/*orderSn*/
and nor.order_status_code=/*orderStatus*/
and nor.operation_time=TO_DATE(/*operationDate*/,'YYYY-MM-DD HH24:MI:SS');