/**
 * 
 * [FUN]V05-101就诊列表
 * @version 1.0, 2012/05/28  
 * @author 魏鹏
 * @since 1.0
 * 影像图片
 */
select image_content,
       image_format
  from medical_imaging
 WHERE delete_flag = '0' 
   and imaging_sn = /*imagingSn*/
