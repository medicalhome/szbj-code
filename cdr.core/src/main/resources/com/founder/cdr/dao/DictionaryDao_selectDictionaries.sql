

/**
 * @return 字典列表
 */
SELECT a.cs_code,
       a.cs_name,
       b.cv_id,
       b.cv_code,
       b.cv_value,
       b.py_code,
       b.seq_no,
       null full_name
  FROM code_system a, code_value b       
 WHERE a.cs_id = b.cs_id and a.delete_flag = 0 and b.delete_flag = 0
 union all 
SELECT a.cs_code,
       a.cs_name,
	   b.code_ward_id cv_id,
       b.code,
       b.name,
       b.ward_py_code as py_code,
       b.seq_no,
       null full_name
  FROM code_system a, code_ward b      
 WHERE a.cs_id = b.cs_id and a.delete_flag = 0 and b.delete_flag = 0
 union all 
SELECT a.cs_code,
       a.cs_name,
	   b.code_icd_id cv_id,
       b.code,
       b.name,
       b.py_code,
       b.seq_no,
       null full_name
  FROM code_system a, code_icd b      
 WHERE a.cs_id = b.cs_id and a.delete_flag = 0 and b.delete_flag = 0
 union all 
SELECT a.cs_code,
       a.cs_name,
	   b.exam_item_group_id cv_id,
       b.code,
       b.name,
       b.py_code,
       b.seq_no,
       null full_name
  FROM code_system a, code_exam_item_group b
 WHERE a.cs_id = b.cs_id and a.delete_flag = 0 and b.delete_flag = 0
 union all    
SELECT a.cs_code,
       a.cs_name,
       b.code_exam_item_id cv_id,
       b.code,
       b.name,
       b.py_code,
       b.seq_no,
       null full_name
  FROM code_system a, code_exam_item b
 WHERE a.cs_id = b.cs_id and a.delete_flag = 0 and b.delete_flag = 0
 union all    
SELECT a.cs_code,
       a.cs_name,
       b.code_lab_item_id cv_id,
       b.code,
       b.name,
       b.py_code,
       b.seq_no,
       null full_name
  FROM code_system a, code_lab_item b
 WHERE a.cs_id = b.cs_id and a.delete_flag = 0 and b.delete_flag = 0
 union all    
SELECT a.cs_code,
       a.cs_name,
       b.code_dept_id cv_id,
       b.code,
       b.name,
       b.py_code,
       b.seq_no,
       null full_name
  FROM code_system a, code_department b
 WHERE a.cs_id = b.cs_id and a.delete_flag = 0 and b.delete_flag = 0
 union all    
SELECT a.cs_code,
       a.cs_name,
       b.code_drug_id cv_id,
       b.code||b.serial cv_code,
       b.name,
       b.py_code,
       b.seq_no,
       b.specification full_name
  FROM code_system a, code_drug b
 WHERE a.cs_id = b.cs_id and a.delete_flag = 0 and b.delete_flag = 0
 union all    
SELECT a.cs_code,
       a.cs_name,
       b.code_person_id cv_id,
       b.code,
       b.name,
       b.py_code,
       b.seq_no,
       null full_name
  FROM code_system a, code_person b
 WHERE a.cs_id = b.cs_id and a.delete_flag = 0 and b.delete_flag = 0
 union all 
SELECT a.cs_code,
       a.cs_name,
       b.CODE_MEDICAL_NAME_ID cv_id,
       b.code,
       b.name,
       b.py_code,
       b.seq_no,
       null full_name
  FROM code_system a, code_medical_name b
 WHERE a.cs_id = b.cs_id and a.delete_flag = 0 and b.delete_flag = 0
 union all 
SELECT a.cs_code,
       a.cs_name,
       b.code_lab_subitem_id  cv_id,
       b.code,
       b.name,
       b.py_code,
       b.seq_no,
       b.full_name
  FROM code_system a, CODE_LAB_SUBITEM b
 WHERE a.cs_id = b.cs_id and a.delete_flag = 0 and b.delete_flag = 0

