package com.csoft.designpatterns.creational;

public class Singleton {

    public static Singleton instance;

    private Singleton() {
    }

    /**
     * Gets the instance with synchronized to have thread-safety.
     *
     * @return Singleton
     */

    public static synchronized Singleton getInstance() {
        // Check if an instance exists
        if (instance == null) {
            // If no instance exists, create one
            instance = new Singleton();
        }
        // Return the existing instance
        return instance;
    }

}
