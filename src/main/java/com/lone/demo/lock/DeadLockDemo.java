package com.lone.demo.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class DeadLockDemo {
    public static void main(String[] args) {
        Object a = new Object();
        Object b = new Object();

        new Thread(() -> {
            synchronized (a) {
                log.info("持有a锁");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (b) {
                    log.info("想获取b锁，却获取不到");
                }
            }
        },"t1").start();

        new Thread(() -> {
            synchronized (b) {
                log.info("持有b锁");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (a) {
                    log.info("想获取a锁，却获取不到");
                }
            }
        },"t2").start();
    }
}
