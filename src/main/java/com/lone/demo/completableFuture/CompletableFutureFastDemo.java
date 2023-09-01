package com.lone.demo.completableFuture;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
public class CompletableFutureFastDemo {
    public static void main(String[] args) {
        CompletableFuture<String> playerA = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "Player A";
        });

        CompletableFuture<String> playerB = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "Player B";
        });

        CompletableFuture<String> faster = playerA.applyToEither(playerB, f -> f + " is winner");
        log.info("{}", faster.join());
    }
}
