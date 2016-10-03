package com.yly.cdr.sql;

import java.util.List;

import com.founder.fasf.web.paging.PagingContext;
import com.yly.cdr.dto.patient.PatientListAdvanceSearchPra;

public class PatientListAdvanceSearchSql
{
    public static final String SELECT_PATIENT_BY_ASCONDITION = ""
        + "select p.patient_sn, p.patient_eid, p.patient_name, p.birth_date, p.gender_name,mv.visit_date "
        + "from patient p left outer join "
        + "(select visit_date, patient_sn from "
        + "(select visit_date, patient_sn, row_number()over(partition by patient_sn order by visit_date desc) rn from medical_visit where delete_flag=''0'') "
        + "where rn=1 ) mv "
        + "on p.patient_sn = mv.patient_sn where p.delete_flag = ''0''";

    public static String getPagingSQL(PatientListAdvanceSearchPra conditions,
            PagingContext pagingContext)
    {
        int pageCount = pagingContext.getRowCnt();
        int pageNo = pagingContext.getPageNo();
        int recordStartNum = (pageNo - 1) * pageCount;
        int recordEndNum = pageNo * pageCount;

        StringBuffer SQL = new StringBuffer();
        if (pagingContext.getPageNo() == 1)
        {
            SQL.append("select patient_sn, patient_eid, patient_name, birth_date,gender_name,visit_date from (");
        }
        else
        {
            SQL.append("select patient_sn, patient_eid, patient_name, birth_date,gender_name,visit_date from (");
            SQL.append("select patient_sn, patient_eid, patient_name, birth_date,gender_name,visit_date, rownum rownum_ from (");
        }

        SQL.append(getSQL(conditions));

        if (pagingContext.getPageNo() == 1)
            SQL.append(") where rownum <= 10");
        else
            SQL.append(") row_ where rownum <= " + recordEndNum
                + ") where rownum_ > " + recordStartNum);

        return SQL.toString();
    }

    public static String getSQL(PatientListAdvanceSearchPra conditions)
    {
        StringBuffer SQL = new StringBuffer();

        SQL.append(SELECT_PATIENT_BY_ASCONDITION);

        if (conditions.getPatientSql() != null
            && conditions.getPatientSql().length() != 0)
        {
            SQL.append(" and " + conditions.getPatientSql());
        }

        if ((conditions.getVisitSql() != null && conditions.getVisitSql().length() != 0)
            || (conditions.getDeptIds() != null && conditions.getDeptIds().size() != 0))
        {
            SQL.append(" and exists (select ''X'' from medical_visit mv where mv.patient_sn = p.patient_sn and mv.delete_flag = ''0''");
            if (conditions.getVisitSql() != null
                && conditions.getVisitSql().length() != 0)
                SQL.append(" and " + conditions.getVisitSql());
            if (conditions.getDeptIds() != null
                && conditions.getDeptIds().size() != 0)
            {
                SQL.append(" and mv.visit_dept_id in "
                    + getScope(conditions.getDeptIds()));
            }
            SQL.append(")");
        }

        if (conditions.getEmpiSql() != null
            && conditions.getEmpiSql().length() != 0)
        {
            SQL.append(" and exists (select ''X'' from patient_cross_index pci  where pci.patient_eid = p.patient_eid and pci.delete_flag = ''0'' and "
                + conditions.getEmpiSql() + " )");
        }

        if (conditions.getDiagnosisSql() != null
            && conditions.getDiagnosisSql().length() != 0)
        {
            SQL.append("and exists (select ''X'' from diagnosis dia where dia.patient_sn = p.patient_sn and dia.delete_flag = ''0'' and "
                + conditions.getDiagnosisSql() + " )");
        }

        if (conditions.getLabItemSql() != null
            && conditions.getLabItemSql().length() != 0)
        {
            SQL.append("and exists (select ''X'' from lab_result_item lri "
                + "inner join lab_result_composite_item lrci on lri.lab_result_composite_item_sn = lrci.composite_item_sn "
                + "inner join lab_result lr on lrci.lab_report_sn = lr.lab_report_sn "
                + "where lri.delete_flag = ''0'' and lrci.delete_flag = ''0'' and lr.delete_flag = ''0'' and lr.patient_sn = p.patient_sn and "
                + conditions.getLabItemSql() + " )");
        }

        return SQL.toString();
    }

    private static String getScope(List<String> conditions)
    {
        StringBuffer scopeCondition = new StringBuffer("(");
        for (int i = 0; i < conditions.size(); i++)
        {
            if (i == conditions.size() - 1)
            {
                scopeCondition.append("''" + conditions.get(i) + "''");
            }
            else
            {
                scopeCondition.append("''" + conditions.get(i) + "'',");
            }
        }
        scopeCondition.append(")");
        return scopeCondition.toString();
    }
}
