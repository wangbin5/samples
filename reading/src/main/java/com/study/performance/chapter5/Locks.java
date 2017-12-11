package com.study.performance.chapter5;

import com.google.common.annotations.VisibleForTesting;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;

/**
 * Created by Administrator on 2017/12/7.
 */
public class Locks {
    private ReentrantLock reentrantLock;

    private Lock lock;

    private ReentrantReadWriteLock reentrantReadWriteLock;

    private LongAdder adders;

    private ForkJoinTask task;

    private Executor executor;

    private StampedLock stampedLock;

    private CopyOnWriteArrayList copyOnWriteArrayList;

    private BlockingQueue queue;

    private CountDownLatch countDownLatch;

    private CyclicBarrier cyclicBarrier;

    private Semaphore semaphore;

    private Exchanger exchanger;
    @Test
    public void test(){
        reentrantReadWriteLock.readLock();
    }
}
