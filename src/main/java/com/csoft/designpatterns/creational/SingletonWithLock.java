package com.csoft.designpatterns.creational;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class SingletonWithLock {

    private static SingletonWithLock instance;
    private static final Lock lock = new ReentrantLock();

    private SingletonWithLock() {
    }

    /**
     * Gets the instance with reentrant lock to have thread-safety.
     * <p>
     * The lock guarantees that only one consumer at the time is trying to
     * spawn the instance lazy creation.
     * <p>
     * If the instance is already created, access can happen freely.
     *
     * @return SingletonWithLock
     */

    public static SingletonWithLock getInstance() {
        if (instance == null) {
            lock.lock();
            try {
                if (instance == null)
                    instance = new SingletonWithLock();
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }
}