package com.yly.cdr.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.yly.cdr.entity.BillingItem;
import com.yly.cdr.entity.BillingReceipt;
import com.yly.cdr.entity.Prescription;

public interface BillingService
{
    @Transactional
    public void saveBilling(List<BillingReceipt> billingReceipt,
            List<BillingItem> billingItem,
            List<Prescription> prescriptions, List<Object> orders);

}
