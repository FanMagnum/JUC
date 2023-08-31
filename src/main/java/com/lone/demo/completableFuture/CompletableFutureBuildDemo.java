package com.lone.demo.completableFuture;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class CompletableFutureBuildDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // CompletableFuture初始化，不推荐使用构造方法，使用静态方法runAsync和supplyAsync
        // 默认线程池 ForkJoinPool
        // 自定义线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(() -> {
            Thread.currentThread().setName("runAsync");
            log.info(Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, threadPool);


        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            Thread.currentThread().setName("supplyAsync");
            log.info(Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Hello supplyAsync";
        }, threadPool);

        log.info(String.valueOf(voidCompletableFuture.get()));
        log.info(completableFuture.get());

        threadPool.shutdown();

    }
}
