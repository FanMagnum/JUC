package com.lone.demo.lockSupport;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class LockSupportDemo {
    public static void main(String[] args) throws InterruptedException {
        //syncWaitNotify();
        //syncAwaitSignal();
        syncParkUnpark();
    }

    private static void syncParkUnpark() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            // 可以先发通知后唤醒
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("{} come in", Thread.currentThread().getName());
            LockSupport.park();
            log.info("{} 被唤醒", Thread.currentThread().getName());
        }, "t1");
        t1.start();

        TimeUnit.SECONDS.sleep(1);

        new Thread(() -> {
            log.info("{} come in", Thread.currentThread().getName());
            LockSupport.unpark(t1);
            log.info("{} 发送通知", Thread.currentThread().getName());
        }, "t2").start();
    }

    private static void syncAwaitSignal() throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        new Thread(() -> {
            lock.lock();
            try {
                log.info("{} come in", Thread.currentThread().getName());
                condition.await();
                log.info("{} 被唤醒", Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "t1").start();

        TimeUnit.SECONDS.sleep(1);

        new Thread(() -> {
            lock.lock();
            try {
                log.info("{} come in", Thread.currentThread().getName());
                condition.signal();
                log.info("{} 发送通知", Thread.currentThread().getName());
            } finally {
                lock.unlock();
            }
        }, "t2").start();
    }

    private static void syncWaitNotify() throws InterruptedException {
        Object o = new Object();
        new Thread(() -> {
            synchronized (o) {
                log.info("{} come in", Thread.currentThread().getName());
                try {
                    o.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("{} 被唤醒", Thread.currentThread().getName());
            }
        }, "t1").start();

        TimeUnit.SECONDS.sleep(1);

        new Thread(() -> {
            synchronized (o) {
                log.info("{} come in", Thread.currentThread().getName());
                o.notify();
                log.info("{} 发出通知", Thread.currentThread().getName());
            }
        }, "t2").start();
    }
}
