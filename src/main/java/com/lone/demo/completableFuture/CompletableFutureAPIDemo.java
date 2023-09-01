package com.lone.demo.completableFuture;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class CompletableFutureAPIDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        //getResultDemo();
        //thenApplyDemo();
        //handleDemo();
        thenMethodsDemo();
        log.info(Thread.currentThread().getName() + "主线程执行完毕");
    }

    private static void getResultDemo() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello";
        });
        log.info("{}", Thread.currentThread().getName());
        // 不见不散 直到获取到结果
        //log.info(future.get());
        //log.info(future.join());
        // 超时等待 超时后抛出java.util.concurrent.TimeoutException
        //log.info(future.get(500, TimeUnit.MILLISECONDS));
        //立刻获取，如果任务未完成返回缺省值
        //TimeUnit.SECONDS.sleep(2);
        //log.info(future.getNow("XXX"));
        //是否打断get方法立即返回括号值
        System.out.println(future.complete("completeValue" + "\t" + future.join()));
    }

    private static void thenApplyDemo() {
        //thenApply --->计算结果存在依赖关系，这两个线程串行化---->由于存在依赖关系（当前步错，不走下一步），当前步骤有异常的话就叫停
        ExecutorService threadPool = Executors.newFixedThreadPool(1);
        CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        }, threadPool).thenApply(f -> {
            log.info("111");
            //int i = 5 / 0;
            return f + 1;
        }).thenApply(f -> {
            log.info("222");
            return f + 2;
        }).whenComplete((v, e) -> {
          if (e == null) {
              log.info("v={}", v);
          }
        }).exceptionally(e -> {
            e.printStackTrace();
            return 0;
        });
        threadPool.shutdown();
    }

    private static void handleDemo() {
        // handle --->计算结果存在依赖关系，这两个线程串行化---->有异常也可以往下走一步
        ExecutorService threadPool = Executors.newFixedThreadPool(1);
        CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        }, threadPool).handle((f, e) -> {
            log.info("111");
            int i = 5 / 0;
            return f + 1;
        }).handle((f, e) -> {
            if (e != null) {
                log.info(String.valueOf(e));
                return 0;
            }
            log.info("222");
            return f + 2;
        }).whenComplete((v, e) -> {
            if (e == null) {
                log.info("v={}", v);
            }
        }).exceptionally(e -> {
            e.printStackTrace();
            return 0;
        });
        threadPool.shutdown();
    }

    private static void thenMethodsDemo() {
        //thenRun(Runnable runnable) :任务A执行完执行B，并且不需要A的结果
        Void run = CompletableFuture.supplyAsync(() -> "result").thenRun(() -> {}).join();
        log.info("run={}", run);
        //thenAccept(Consumer action): 任务A执行完执行B，B需要A的结果，但是任务B没有返回值
        Void accept = CompletableFuture.supplyAsync(() -> "result").thenAccept(log::info).join();
        log.info("accept={}", accept);
        //thenApply(Function fn): 任务A执行完执行B，B需要A的结果，同时任务B有返回值
        String apply = CompletableFuture.supplyAsync(() -> "result").thenApply(r -> r + " good").join();
        log.info("apply={}", apply);
    }
}
