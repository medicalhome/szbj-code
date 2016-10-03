

/**
 * @return 字典列表
 */
 /*IF "MS026".equals(csId)*/
SELECT a.cs_code,
       a.cs_name,
	   b.code_ward_id cv_id,
       b.code cv_code,
       b.name cv_value,
       b.py_code,
       b.seq_no
  FROM code_system a, code_ward b      
 WHERE a.cs_id = b.cs_id and a.delete_flag = 0 and b.delete_flag = 0
 /*END*/
 /*IF "MS002".equals(csId)*/
SELECT a.cs_code,
       a.cs_name,
	   b.code_icd_id cv_id,
       b.code cv_code,
       b.name cv_value,
       b.py_code,
       b.seq_no
  FROM code_system a, code_icd b      
 WHERE a.cs_id = b.cs_id and a.delete_flag = 0 and b.delete_flag = 0
 /*END*/
 /*IF "MS003".equals(csId)*/
SELECT a.cs_code,
       a.cs_name,
	   b.code_icd_id cv_id,
       b.code cv_code,
       b.name cv_value,
       b.py_code,
       b.seq_no
  FROM code_system a, code_icd b      
 WHERE a.cs_id = b.cs_id and a.delete_flag = 0 and b.delete_flag = 0
 /*END*/
 /*IF "MS040".equals(csId)*/
SELECT a.cs_code,
       a.cs_name,
	   b.exam_item_group_id cv_id,
       b.code cv_code,
       b.name cv_value,
       b.py_code,
       b.seq_no
  FROM code_system a, code_exam_item_group b
 WHERE a.cs_id = b.cs_id and a.delete_flag = 0 and b.delete_flag = 0
 /*END*/
 /*IF "MS039".equals(csId)*/    
SELECT a.cs_code,
       a.cs_name,
       b.code_exam_item_id cv_id,
       b.code cv_code,
       b.name cv_value,
       b.py_code,
       b.seq_no
  FROM code_system a, code_exam_item b
 WHERE a.cs_id = b.cs_id and a.delete_flag = 0 and b.delete_flag = 0
 /*END*/
 /*IF "MS033".equals(csId)*/     
SELECT a.cs_code,
       a.cs_name,
       b.code_lab_item_id cv_id,
       b.code cv_code,
       b.name cv_value,
       b.py_code,
       b.seq_no
  FROM code_system a, code_lab_item b
 WHERE a.cs_id = b.cs_id and a.delete_flag = 0 and b.delete_flag = 0
 /*END*/
 /*IF "MS025".equals(csId)*/     
SELECT a.cs_code,
       a.cs_name,
       b.code_dept_id cv_id,
       b.code cv_code,
       b.name cv_value,
       b.py_code,
       b.seq_no
  FROM code_system a, code_department b
 WHERE a.cs_id = b.cs_id and a.delete_flag = 0 and b.delete_flag = 0
 /*END*/
 /*IF "MS028".equals(csId)*/   
SELECT a.cs_code,
       a.cs_name,
       b.code_drug_id cv_id,
       b.code||b.serial cv_code,
       b.name cv_value,
       b.py_code,
       b.seq_no
  FROM code_system a, code_drug b
 WHERE a.cs_id = b.cs_id and a.delete_flag = 0 and b.delete_flag = 0
 /*END*/
 /*IF "MS024".equals(csId)*/     
SELECT a.cs_code,
       a.cs_name,
       b.code_person_id cv_id,
       b.code cv_code,
       b.name,
       b.py_code,
       b.seq_no
  FROM code_system a, code_person b
 WHERE a.cs_id = b.cs_id and a.delete_flag = 0 and b.delete_flag = 0
 /*END*/
 /*IF "MS053".equals(csId)*/     
SELECT a.cs_code,
       a.cs_name,
       b.CODE_MEDICAL_NAME_ID cv_id,
       b.code cv_code,
       b.name cv_value,
       b.py_code,
       b.seq_no
  FROM code_system a, code_medical_name b
 WHERE a.cs_id = b.cs_id and a.delete_flag = 0 and b.delete_flag = 0
 /*END*/
 /*IF "MS064".equals(csId)*/     
SELECT a.cs_code,
       a.cs_name,
       b.code_lab_subitem_id cv_id,
       b.code cv_code,
       b.name cv_value,
       b.py_code,
       b.seq_no,
       b.full_name
  FROM code_system a, CODE_LAB_SUBITEM b
 WHERE a.cs_id = b.cs_id and a.delete_flag = 0 and b.delete_flag = 0
 /*END*/

