select 	cv.cs_id, 
		cv.cv_id,
		cv.cv_code,
		cv.cv_value 
from code_system cy, code_value cv 
where cy.cs_code = /*dictCode*/ 
and   cv.cs_id=cy.cs_id(+)