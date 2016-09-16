SELECT * 
  FROM PRESCRIPTION P
 WHERE P.DELETE_FLAG='0'
   AND P.VISIT_SN=/*visitSn*/
 /*IF dataSource != null && 'prescription'.equals(dataSource)*/
   AND (P.PRESCRIPTION_TYPE_CODE != '01' OR P.PRESCRIPTION_TYPE_CODE IS NULL) 
 /*END*/
  /*IF orgCode != null && !"".equals(orgCode)*/
   AND P.ORG_CODE = /*orgCode*/
 /*END*/