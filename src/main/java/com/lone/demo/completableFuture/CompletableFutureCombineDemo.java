package com.lone.demo.completableFuture;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

@Slf4j
public class CompletableFutureCombineDemo {
    public static void main(String[] args) {
        CompletableFuture<Integer> result = CompletableFuture.supplyAsync(() -> {
            log.info(Thread.currentThread().getName() + "\t" + "come in 1");
            return 10;
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            log.info(Thread.currentThread().getName() + "\t" + "come in 2");
            return 20;
        }), Integer::sum).thenCombine(CompletableFuture.supplyAsync(() -> {
            log.info(Thread.currentThread().getName() + "\t" + "come in 3");
            return 30;
        }), Integer::sum);

        log.info("result:{}",result.join());

    }
}
