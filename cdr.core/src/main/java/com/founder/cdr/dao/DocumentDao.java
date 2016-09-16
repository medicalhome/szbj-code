package com.founder.cdr.dao;

import com.founder.cdr.entity.ClinicalDocument;
import com.founder.cdr.entity.Message;
import com.founder.fasf.core.util.daohelper.annotation.Arguments;

public interface DocumentDao
{

    @Arguments({ "message", "clinicalDocument" })
    public void updateClinicalDocumentDocumentCda(Message message,
            ClinicalDocument clinicalDocument);
}
