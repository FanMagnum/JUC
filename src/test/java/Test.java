import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

@Slf4j(topic = "c.Test")
public class Test {

    public static void testThreadRunnable() {
        // 使用Runnable对象创建线程
        new Thread(() -> log.info("running"), "tRunnable").start();
        log.info("running");
    }

    public static void testThreadFutureTask() throws ExecutionException, InterruptedException {
        FutureTask<Integer> task = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                log.info("running");
                Thread.sleep(1000);
                return 100;
            }
        });
        new Thread(task, "tFutureTask").start();
        log.info("{}", task.get());
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        testThreadFutureTask();
    }
}
