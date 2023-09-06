package com.lone.demo.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class ReEntryLockDemo {

    private static Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        ReentryLock1();
        log.info("---------------------");
        ReentryLock2();

    }

    private static void ReentryLock1() {
        final Object o = new Object();
        ExecutorService pool = Executors.newFixedThreadPool(1);
        pool.execute(() -> {
            synchronized (o) {
                log.info("外层");
                synchronized (o) {
                    log.info("中层");
                    synchronized (o) {
                        log.info("内层");
                    }
                }
            }
        });
        pool.shutdown();
    }

    private static void ReentryLock2() {
        ExecutorService pool = Executors.newFixedThreadPool(1);
        pool.execute(() -> {
            try {
                lock.lock();
                log.info("外层");
                try {
                    lock.lock();
                    log.info("中层");
                    try {
                        lock.lock();
                        log.info("内层");
                    } finally {
                        lock.unlock();
                    }
                } finally {
                    lock.unlock();
                }
            }finally {
                lock.unlock();
            }
        });
        pool.shutdown();
    }
}
