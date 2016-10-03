package com.yly.cdr.util;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 类描述
 * Created by masong1 on 2016/9/18.
 */

public class RWLock {
    private static ReadWriteLock rwlock = new ReentrantReadWriteLock();
    public static ReadWriteLock getRWLockInstance(){
        return rwlock;
    }
}
