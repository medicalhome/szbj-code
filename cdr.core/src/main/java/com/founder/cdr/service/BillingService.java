package com.founder.cdr.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.founder.cdr.entity.BillingItem;
import com.founder.cdr.entity.BillingReceipt;
import com.founder.cdr.entity.Prescription;

public interface BillingService
{
    @Transactional
    public void saveBilling(List<BillingReceipt> billingReceipt,
            List<BillingItem> billingItem,
            List<Prescription> prescriptions, List<Object> orders);

}
