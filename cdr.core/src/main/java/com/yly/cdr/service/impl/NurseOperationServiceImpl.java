package com.yly.cdr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.founder.fasf.core.util.daohelper.entity.EntityDao;
import com.yly.cdr.service.NurseOperationService;

@Component
public class NurseOperationServiceImpl implements NurseOperationService
{

    @Autowired
    private EntityDao entityDao;

    @Override
    @Transactional
    public void saveList(List<Object> nursingOperationList,
            List<Object> execOrderExistsList)
    {
        if (nursingOperationList != null && !nursingOperationList.isEmpty())
        {
            for (Object entity : nursingOperationList)
            {
                entityDao.insert(entity);
            }
        }

        for (Object entity : execOrderExistsList)
        {
            entityDao.update(entity);
        }

    }

}
