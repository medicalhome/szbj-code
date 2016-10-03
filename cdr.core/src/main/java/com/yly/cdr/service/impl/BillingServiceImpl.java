package com.yly.cdr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.founder.fasf.core.util.daohelper.entity.EntityDao;
import com.yly.cdr.entity.BillingItem;
import com.yly.cdr.entity.BillingReceipt;
import com.yly.cdr.entity.Prescription;
import com.yly.cdr.service.BillingService;

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
