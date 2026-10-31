package com.csoft.designpatterns.creational;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertSame;

class SingletonWithLockTest {

    @Test
    void testReturnsSameInstance() {
        var singleton = SingletonWithLock.getInstance();
        assertThat(singleton).isNotNull();

        var secondSingleton = SingletonWithLock.getInstance();
        assertThat(secondSingleton).isNotNull();

        // same identity, not just equality
        assertSame(secondSingleton, singleton);

    }

    @Test
    void testThreadSafety() throws InterruptedException {
        int N = 100;
        CountDownLatch latch = new CountDownLatch(N);
        List<SingletonWithLock> collected = new ArrayList<>();

        // using virtual threads, launch N generations of singleton and collect;
        // use a CountDownLatch to await for completion
        try (var es = Executors.newVirtualThreadPerTaskExecutor()) {
            for (var n = 0; n < N; n++) {
                es.submit(() -> {
                    var singleton = SingletonWithLock.getInstance();
                    collected.add(singleton);
                    latch.countDown();
                });
            }
        }
        latch.await();

        // verify that all N are the same reference
        var first = collected.getFirst();
        collected.forEach(it -> assertSame(first, it));
    }

}