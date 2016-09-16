package com.founder.cdr.web.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.founder.cdr.core.Constants;
import com.founder.cdr.dto.AuditLogDto;
import com.founder.cdr.entity.LabResultCompositeItem;
import com.founder.cdr.entity.PatientCrossIndex;
import com.founder.cdr.util.StringUtils;
import com.founder.cdr.web.mq.process.audit.AuditMessageHelper;

/**
 * 敏感信息审计
 * @author jin_peng
 * @version 1.0, 2012/09/24
 */
@Component
public class AuditProcessHelper
{
    // batch审计发送消息对象
    @Autowired
    private AuditMessageHelper auditMessageHelper;

    /**
     * 构造检验具体结果规则条件
     * @return 检验具体结果规则条件
     */
    public String joinAuditLogWhere()
    {
        StringBuffer auditLogWhere = null;

        String[] auditRules = Constants.AUDIT_RULES;

        int count = 0;

        if (auditRules != null && auditRules.length != 0)
        {
            auditLogWhere = new StringBuffer();

            auditLogWhere.append("(");

            // 开始构造检验具体结果规则条件
            for (String auditRule : auditRules)
            {
                String[] auditCondition = auditRule.split("&");

                auditLogWhere.append("(");

                auditLogWhere.append("lri.item_code in (");

                auditLogWhere.append(auditCondition[0]);

                auditLogWhere.append(")");

                if (!StringUtils.isEmpty(auditCondition[1]))
                {
                    auditLogWhere.append(" and ");

                    auditLogWhere.append(auditCondition[1]);
                }

                auditLogWhere.append(")");

                if (count != (auditRules.length - 1))
                {
                    auditLogWhere.append(" \n");

                    auditLogWhere.append("or ");

                    count++;
                }
            }

            auditLogWhere.append(")");
        }

        return auditLogWhere == null ? null : auditLogWhere.toString();
    }

    /**
     * 构造检验大项目过滤条件
     * @param compositeItemList 检验大项目对象集合
     * @return 检验大项目过滤条件
     */
    public String joinItemWhere(List<LabResultCompositeItem> compositeItemList)
    {
        StringBuffer itemWhere = null;

        int count = 0;

        if (compositeItemList != null && !compositeItemList.isEmpty())
        {
            itemWhere = new StringBuffer();

            for (LabResultCompositeItem labResultCompositeItem : compositeItemList)
            {
                itemWhere.append("''");

                itemWhere.append(labResultCompositeItem.getItemCode());

                itemWhere.append("''");

                if (count != (compositeItemList.size() - 1))
                {
                    itemWhere.append(",");

                    count++;
                }
            }
        }

        return itemWhere == null ? null : itemWhere.toString();
    }

    public void auditSendMessage(PatientCrossIndex pci, AuditLogDto auditLog)
            throws Exception
    {
        auditMessageHelper.mainBusinessProcess(pci, auditLog);
    }

}
