/**
 * $Author :jin_peng
 * $Date : 2012/10/30 17:13$
 * [BUG]0010836 ADD BEGIN
 *  
 * [FUN]V05-101记录系统退出日志
 * @version 1.0, 2012/10/30  
 * @author 金鹏
 * @since 1.0
 * 记录系统退出日志
 */
UPDATE SYSTEM_ACCOUNT_LOG SAL
   SET SAL.EXIT_KINDS_NAME = /*exitKindsName*/,
       SAL.USE_END_DATE = TO_DATE(/*systemTime*/,'YYYY-MM-DD HH24:MI:SS'),
       SAL.UPDATE_TIME = TO_DATE(/*systemTime*/,'YYYY-MM-DD HH24:MI:SS'),
       SAL.UPDATE_COUNT = SAL.UPDATE_COUNT + 1
 WHERE SAL.ACCOUNT_LOG_SN = /*accountLogSn*/
   AND SAL.DELETE_FLAG = 0
/**[BUG]0010836 ADD END*/

 	    