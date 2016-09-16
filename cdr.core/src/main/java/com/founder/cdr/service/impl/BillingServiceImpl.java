package com.founder.cdr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.founder.cdr.entity.BillingItem;
import com.founder.cdr.entity.BillingReceipt;
import com.founder.cdr.entity.Prescription;
import com.founder.cdr.service.BillingService;
import com.founder.fasf.core.util.daohelper.entity.EntityDao;

@Component
public class BillingServiceImpl implements BillingService
{

    @Autowired
    private EntityDao entityDao;

    @Override
    @Transactional
    public void saveBilling(List<BillingReceipt> billingReceipt,
            List<BillingItem> billingItem, List<Prescription> prescriptions,
            List<Object> orders)
    {
        for (BillingReceipt billingReceiptEntity : billingReceipt)
        {
            entityDao.insert(billingReceiptEntity);
        }

        for (BillingItem billingItemEntity : billingItem)
        {
            entityDao.insert(billingItemEntity);
        }
        for (Prescription prescription : prescriptions)
        {
            entityDao.update(prescription);
        }
        for (Object order : orders)
        {
            entityDao.update(order);
        }
    }
}
