package com.yly.cdr.batch.wmq;
//package com.yly.cdr.batch.wmq;
//
//import org.springframework.transaction.TransactionException;
//import org.springframework.transaction.support.DefaultTransactionStatus;
//
//public class WMQTransactionStatus extends DefaultTransactionStatus
//{
//    private boolean completed = false;
//
//    private boolean rollbackOnly = false;
//
//    public WMQTransactionStatus(Object transaction, boolean newTransaction,
//            boolean newSynchronization, boolean readOnly, boolean debug,
//            Object suspendedResources)
//    {
//        // super(transaction, newTransaction, newSynchronization, readOnly,
//        // debug, suspendedResources);
//        super(transaction, true, false, false, false, null);
//    }
//
//    @Override
//    public Object createSavepoint() throws TransactionException
//    {
//        return null;
//    }
//
//    @Override
//    public void rollbackToSavepoint(Object savepoint)
//            throws TransactionException
//    {
//    }
//
//    @Override
//    public void releaseSavepoint(Object savepoint) throws TransactionException
//    {
//    }
//
//    @Override
//    public boolean isNewTransaction()
//    {
//        return true;
//    }
//
//    @Override
//    public boolean hasSavepoint()
//    {
//        return false;
//    }
//
//    @Override
//    public void setRollbackOnly()
//    {
//        rollbackOnly = true;
//    }
//
//    @Override
//    public boolean isRollbackOnly()
//    {
//        return rollbackOnly;
//    }
//
//    @Override
//    public void flush()
//    {
//    }
//
//    @Override
//    public boolean isCompleted()
//    {
//        return completed;
//    }
//
//    public void setCompleted(boolean completed)
//    {
//        this.completed = completed;
//    }
//}
