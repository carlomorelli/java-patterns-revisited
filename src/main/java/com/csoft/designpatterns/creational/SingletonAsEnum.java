package com.csoft.designpatterns.creational;

public enum SingletonAsEnum {

    INSTANCE;

    /**
     * Guarantees thread-safety and serialization.
     *
     * @return SingletonAsEnum
     */
    public static SingletonAsEnum getInstance() {
        return INSTANCE;
    }
}
