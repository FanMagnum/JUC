package com.lone.demo.cas;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class AtomicIntegerDemo {
    private static final int SIZE = 50;

    public static void main(String[] args) throws InterruptedException {
        MyNumber number = new MyNumber();
        CountDownLatch count = new CountDownLatch(SIZE);
        for (int i = 0; i < SIZE; i++) {
            new Thread(() -> {
                try {
                    for (int j = 0; j < 1000; j++) {
                        number.add();
                    }
                } finally {
                    log.info("thread add finished");
                    count.countDown();
                }
            }, String.valueOf(i)).start();
        }

        count.await();

        log.info("result = {}", number.atomicInteger.get());
    }
}

class MyNumber{
    AtomicInteger atomicInteger = new AtomicInteger();

    public void add(){
        atomicInteger.getAndIncrement();
    }
}
