/**
 * $Author : wu_jianfeng
 * $Date : 2012/09/4 14:10$
 * $[BUG]0009663 MODIFY BEGIN
 * [FUN]V05-101登陆页面用户验证
 * @version 1.0, 2012/07/17  
 * @author 金鹏
 * @since 1.0
 * 用户信息
 */
SELECT SA.USER_ID, SA.USER_NAME,SA.PASSWD,SA.STATUS,SA.JOB_CATEGORY,SA.DEPT_CODE,SA.INIT_PWD
  FROM SYSTEM_ACCOUNT SA
 WHERE SA.USER_ID = /*userId*/
   AND SA.DELETE_FLAG = '0'
/**[BUG]0009663 MODIFY END*/     