/**
 * 
 * 根据报告与规则，得到相应审计内容并插入到审计表中
 * @version 1.0, 2013/09/24  
 * @author jinpeng
 * @since 1.0
 */
declare

  systime date;
  
  itemResCount integer;
  
begin
  
  select to_date(/*systemTime*/,'yyyy-mm-dd hh24:mi:ss') into systime from dual;
  
  execute immediate 'select count(1)
			           from lab_result_composite_item lrci, lab_result_item lri
			          where lri.lab_result_composite_item_sn = lrci.composite_item_sn
			            and lrci.lab_report_sn = :1
			            and lrci.item_code in (' || /*lrciItemCodeWhere*/ || ')
			            and ' || /*lriRuleWhere*/ || '
			            and lrci.delete_flag = 0
			            and lri.delete_flag = 0'
		       into itemResCount
		      using '' || /*labReportSn*/ || '';

  if itemResCount > 0 then
  
  execute immediate 'insert into system_audit_log
					  (audit_log_sn,
					   account_log_sn,
					   patient_lid,
					   patient_domain,
					   operation_time,
					   data_type_name,
					   business_data_no,
					   item_name,
					   message_content,
					   success_send_flag,
					   create_time,
					   update_time,
					   org_code,
					   org_name,
					   update_count,
					   delete_flag)
					  select seq_system_audit_log.nextval,
					         :1,
					         :2,
					         :3,
					         :4,
					         :5,
					         :6,
					         res.item_name,
					         empty_clob(),
					         ''0'',
					         :7,
					         :8,
							 :9,
							 :10,
					         1,
					         ''0''
					    from (select to_char(wmsys.wm_concat(res_temp.item_name)) item_name
  								from (select distinct lrci.item_name
										from lab_result_composite_item lrci, lab_result_item lri
									   where lri.lab_result_composite_item_sn = lrci.composite_item_sn
									     and lrci.lab_report_sn = :11
									     and lrci.item_code in (' || /*lrciItemCodeWhere*/ || ')
									     and ' || /*lriRuleWhere*/ || '
									     and lrci.delete_flag = 0
									     and lri.delete_flag = 0) res_temp) res'
	using /*accountLogSn*/, /*patientLid*/, /*patientDomain*/, systime, /*dataTypeName*/, /*businessDataNo*/, systime, systime,'','', '' || /*labReportSn*/ || '';
	
  end if;
  
end;;