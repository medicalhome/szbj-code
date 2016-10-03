 /**
	 * V05-701:手术列表
     * V05-703:手术申请详细
     * author:黄洁玉
     * @since 1.0 
     */
 
 select pr.visit_sn,
        pr.order_sn,
        pr.request_no,
        pr.operation_name,
        pr.procedure_level,
        pr.proposal_exec_time,
        pr.precautions,
        pr.description,
        pr.exec_dept,
        pr.exec_dept_name,
        pr.request_dept,
        pr.request_dept_name,
        pr.request_person,
        pr.request_person_name,
        pr.request_date,
        pr.request_reason
   from procedure_request pr
  where pr.delete_flag='0'
    and pr.request_sn=/*requestSn*/13
  order by pr.proposal_exec_time desc    