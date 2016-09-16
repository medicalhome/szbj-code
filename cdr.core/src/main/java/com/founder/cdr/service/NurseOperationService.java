package com.founder.cdr.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

public interface NurseOperationService
{

    @Transactional
    public void saveList(List<Object> nursingOperationList,
            List<Object> execOrderExistsList);

}
