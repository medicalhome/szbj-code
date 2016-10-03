select dr.*,modr.batch_no,modr.dispensing_quantity,modr.dispensing_unit
  from medication_order mo,--用药医嘱
       med_order_dispensing_record modr,--用药医嘱与发药记录
       dispensing_record dr--发药记录
 where mo.order_sn=modr.order_sn
   and modr.dispensing_sn=dr.dispensing_sn
   and mo.delete_flag=0
   and modr.delete_flag=0
   and dr.delete_flag=0
   and mo.order_sn=/*orderSn*/
   and not exists
      (select 1
         from hide_drug hd
        where mo.drug_code = hd.drug_code
          and hd.delete_flag = '0'
          and hd.user_name = /*userSn*/)


  