package com.csoft.designpatterns.creational;

public class SingletonEager {

    public static SingletonEager instance = new SingletonEager();

    private SingletonEager() {
    }

    /**
     * Thread-safety is granted as the instance is initialized at class loading. May lead to
     * more memory consumption.
     *
     * @return Singleton
     */
    public static synchronized SingletonEager getInstance() {
        return instance;
    }
}
