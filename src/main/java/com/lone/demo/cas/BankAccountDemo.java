package com.lone.demo.cas;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class BankAccountDemo {
    static final int SIZE = 10;

    public static void main(String[] args) throws InterruptedException {
        MyBank myBank = new MyBank();
        CountDownLatch count = new CountDownLatch(SIZE);
        log.info("初始余额:{}", myBank.account);
        for (int i = 0; i < SIZE; i++) {
            new Thread(() -> {
                try {
                    log.info("{}", Thread.currentThread().getName());
                    myBank.add(new BigDecimal(100));
                } finally {
                    count.countDown();
                }
            }, String.valueOf(i)).start();
        }
        count.await();
        log.info("最终余额:{}", myBank.account);

    }
}
class MyBank{
    BigDecimal account;

    MyBank() {
        account = new BigDecimal(0);
    }

    public synchronized void add(BigDecimal bigDecimal) {
        account = account.add(bigDecimal);
    }
}