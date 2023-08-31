package com.lone.demo;

import lombok.extern.slf4j.Slf4j;

/**
(终止)模式之两阶段终止
Two Phase Termination
在一个线程 T1 中如何“优雅”终止线程 T2？这里的【优雅】指的是给 T2 一个料理后事的机会。
* */

public class TPTDemo {
    public static void main(String[] args) throws InterruptedException {
        TwoPhaseTermination twoPhaseTermination = new TwoPhaseTermination();
        twoPhaseTermination.start();
        Thread.sleep(3500);
        twoPhaseTermination.stop();
    }
}

@Slf4j(topic = "c.TwoPhaseTermination")
class TwoPhaseTermination {
    private Thread monitor;

    public void start() {
        monitor = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        Thread.sleep(1000);
                        log.debug("处理事务");
                    } catch (InterruptedException e) {
                        // 清除中断状态
                        Thread.currentThread().interrupt();
                    }
                }
                log.debug("处理后事");
            }
        },  "monitor");
        monitor.start();
    }

    //利用 isInterrupted
    //interrupt 可以打断正在执行的线程，无论这个线程是在 sleep，wait，还是正常运行
    public void stop() {
        monitor.interrupt();
    }
}