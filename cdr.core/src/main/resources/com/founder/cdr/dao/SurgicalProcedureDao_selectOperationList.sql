 /**
     * V05-704:手术操作列表
     * 显示手术操作列表信息画面
     * author:黄洁玉
     * @param request
     * @return
     */
 
 select prd.procedure_sn,
        prd.operation_type,
        prd.operator,
        prd.operation_type,
        prd.operation_description
   from procedure_record prd
  where prd.procedure_sn = /*procedureSn*/
    and prd.delete_flag = '0'
  order by prd.operation_time desc    