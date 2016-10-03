/**
 * @date 2015/05/08 
 * @author wp
 * 查询肝炎检验项目
 */
SELECT   DISTINCT  to_char(ss.MaxDate,'yyyy-mm-dd hh24:mi:ss')  AS MaxDate
FROM     Lab_Result_Item lri
         LEFT JOIN Lab_Result_Composite_Item lrci
           ON lrci.Composite_Item_sn = lri.Lab_Result_Composite_Item_sn
         LEFT JOIN Lab_Result lr
           ON lr.Lab_Report_sn = lrci.Lab_Report_sn
         LEFT JOIN (SELECT   MAX(lr.Report_Date)  AS MaxDate,
                           lr.Lab_Report_sn
                  FROM     Lab_Result lr
                  GROUP BY lr.Lab_Report_sn) ss
           ON ss.Lab_Report_sn = lr.Lab_Report_sn
WHERE    lr.Report_Date >= (To_date(/*reportDate*/,'yyyy-mm-dd hh24:mi:ss') - 1 / 24 * /*intervalHours*/)
         AND lr.Report_Date <= To_date(/*reportDate*/,'yyyy-mm-dd hh24:mi:ss')
         /*IF unnormal == 'true' */
         AND (lri.Item_Value < lri.Low_Value
               OR lri.Item_Value > lri.High_Value)
         /*END*/
         AND lri.Item_Code IN /*itemsList*/(10,20,30)
         AND lr.Patient_Lid = /*patientID*/
         AND lr.source_system_id = /*systemID*/