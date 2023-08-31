package com.lone.demo;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class OrderPrint {
    private static ReentrantLock lock = new ReentrantLock();
    private static Condition _2print = lock.newCondition();

    public static void main(String[] args) {
        //new Thread(() -> {
        //    lock.lock();
        //    try {
        //        _2print.await();
        //        System.out.println("1");
        //    } catch (InterruptedException e) {
        //        throw new RuntimeException(e);
        //    } finally {
        //        lock.unlock();
        //    }
        //}).start();
        //
        //new Thread(() -> {
        //    lock.lock();
        //    try {
        //        System.out.println("2");
        //        _2print.signal();
        //    }
        //    finally {
        //        lock.unlock();
        //    }
        //}).start();

        Thread t1 = new Thread(() -> {
            LockSupport.park();
            System.out.println("1");
        });
        t1.start();

        new Thread(() -> {
            System.out.println("2");
            LockSupport.unpark(t1);
        }).start();
    }
}
