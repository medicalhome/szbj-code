select p.* from Prescription p 
where p.prescription_sn = (select mo.prescription_sn from medication_order mo where mo.order_sn = /*orderSn*/'' and mo.delete_flag = 0)
and p.delete_flag = 0   

