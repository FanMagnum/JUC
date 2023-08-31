package com.lone.demo.completableFuture;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class CompletableFutureUseDemo {
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        try {
            CompletableFuture.supplyAsync(() -> {
                log.info(Thread.currentThread().getName() + "---- come in");
                int result = ThreadLocalRandom.current().nextInt(10);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("----一秒钟后计算出结果为：" + result);
                if (result > 2) {
                   int i =  5 / 0;
                }
                return result;
            }, threadPool).whenComplete((v, e) -> {
                if (e == null) {
                    log.info("----计算完成，更新系统UpdateValue为：" + v);
                }
            }).exceptionally(e -> {
                e.printStackTrace();
                log.error("异常情况：" + e.getMessage() + "\t" + e.getCause());
                return null;
            });

            log.info(Thread.currentThread().getName() + "线程去忙其他任务了");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }

    private static void future1() throws InterruptedException, ExecutionException {
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            log.info(Thread.currentThread().getName() + "---- come in");
            int result = ThreadLocalRandom.current().nextInt(10);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return result;
        });

        log.info(Thread.currentThread().getName() + "线程去忙其他任务了");
        log.info(String.valueOf(completableFuture.get()));
    }
}
