package com.lone.demo;

import lombok.extern.slf4j.Slf4j;

import static com.lone.demo.Sleeper.sleep;

@Slf4j
public class TWaterTea {
    public static void main(String[] args){
        Thread t1 = new Thread(() -> {
            log.info("洗水壶");
            sleep(1);
            log.info("烧开水");
            sleep(5);
        }, "烧水");
        t1.start();

        Thread t2 = new Thread(() -> {
            log.info("洗茶壶");
            sleep(1);
            log.info("洗茶杯");
            sleep(1);
            log.info("拿茶叶");
            sleep(1);
            try {
                t1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "喜茶");
        t2.start();
    }
}
