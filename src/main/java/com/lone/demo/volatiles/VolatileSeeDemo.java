package com.lone.demo.volatiles;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class VolatileSeeDemo {
    private static volatile boolean flag = true;
    //private static boolean flag = true;

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            while (flag) {
                //do nothing
            }
            log.info("flag = {}, 程序停止", flag);
        }, "t1").start();

        TimeUnit.SECONDS.sleep(1);

        flag = false;
        log.info("flag = {}", flag);
    }
}
