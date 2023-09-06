package com.lone.demo.interrupt;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class InterruptDemo {
    // 中断协商机制
    private static volatile boolean isStop = false;
    private static AtomicBoolean atomicBoolean = new AtomicBoolean(false);

    public static void main(String[] args) throws InterruptedException {
        //interruptByVolatile();
        //interruptByAtomicBoolean();
        interruptByInterruptApi();
    }

    private static void interruptByVolatile() throws InterruptedException {
        new Thread(() -> {
            while (!isStop) {
                log.info("running...");
            }
            log.info("stop...");
        }, "t1").start();

        TimeUnit.MILLISECONDS.sleep(100);

        new Thread(() -> {
            log.info("interrupt...");
            isStop = true;
        }, "t2").start();
    }

    private static void interruptByAtomicBoolean() throws InterruptedException {
        new Thread(() -> {
            while (!atomicBoolean.get()) {
                log.info("running...");
            }
            log.info("stop...");
        }, "t1").start();

        TimeUnit.MILLISECONDS.sleep(20);

        new Thread(() -> {
            log.info("interrupt...");
            atomicBoolean.set(true);
        }, "t2").start();
    }

    private static void interruptByInterruptApi() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                log.info("running...");
            }
            log.info("stop...");
        }, "t1");
        t1.start();

        TimeUnit.MILLISECONDS.sleep(20);

        new Thread(() -> {
            log.info("interrupt...");
            t1.interrupt();
        }, "t2").start();
    }
}
