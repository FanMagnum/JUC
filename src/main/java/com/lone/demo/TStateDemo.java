package com.lone.demo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TStateDemo {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> log.debug("Running"), "t1");

        Thread t2 = new Thread(() -> {
            while (true) {
            }
        }, "t2");
        t2.start();

        Thread t3 = new Thread(() -> log.debug("Running"), "t3");
        t3.start();

        Thread t4 = new Thread(() -> {
            synchronized (TStateDemo.class) {
                try {
                    Thread.sleep(1000000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t4");
        t4.start();

        Thread t5 = new Thread(() -> {
            try {
                // TIMED_WAITING
                //t2.join(1000);
                t2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t5");
        t5.start();

        Thread t6 = new Thread(() -> {
            synchronized (TStateDemo.class) {
                try {
                    Thread.sleep(1000000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t6");
        t6.start();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.debug("t1 state: {}", t1.getState());
        log.debug("t2 state: {}", t2.getState());
        log.debug("t3 state: {}", t3.getState());
        log.debug("t4 state: {}", t4.getState());
        log.debug("t5 state: {}", t5.getState());
        log.debug("t6 state: {}", t6.getState());

    }
}
