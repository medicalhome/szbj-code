package com.yly.cdr.dao;

import com.founder.fasf.core.util.daohelper.annotation.Arguments;
import com.yly.cdr.entity.ClinicalDocument;
import com.yly.cdr.entity.Message;

public interface DocumentDao
{

    @Arguments({ "message", "clinicalDocument" })
    public void updateClinicalDocumentDocumentCda(Message message,
            ClinicalDocument clinicalDocument);
}
