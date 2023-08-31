package com.lone.demo.completableFuture;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

@Slf4j
public class CompletableFutureDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> future = new FutureTask<>(new MyThread());
        new Thread(future, "t1").start();
        log.info(future.get());
    }
}

// 没有返回值
/*class MyThread implements Runnable {
    @Override
    public void run() {

    }
}*/
@Slf4j
class MyThread implements Callable<String> {
    @Override
    public String call() throws Exception {
        log.info("---- come in call()");
        return "hello callable";
    }
}