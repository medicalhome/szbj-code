select mv.*　
from (select mv1.*, row_number()over(order by visit_date desc) rn from medical_visit mv1 where mv1.patient_sn in /*searchPra.patientIds*/(10, 20, 30) and mv1.delete_flag = '0') mv
where rn = 1 
