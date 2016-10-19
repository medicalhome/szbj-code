package com.yly.cdr.util;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RWLock {
    private static ReadWriteLock rwlock = new ReentrantReadWriteLock();
    public static ReadWriteLock getRWLockInstance(){
        return rwlock;
    }
}
