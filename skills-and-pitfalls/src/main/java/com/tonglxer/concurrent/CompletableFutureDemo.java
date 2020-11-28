package com.tonglxer.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 *
 *
 * @Author Tong LinXing
 * @date 2020/11/28
 */
@Slf4j
public class CompletableFutureDemo {
    private static final CompletableFutureDemo instance = new CompletableFutureDemo();


    public static void main(String[] args) {
//        instance.runAsyncDemo();
        instance.supplyAsyncDemo();
    }

    public CompletableFutureDemo getInstance() {
        return instance;
    }


    private void runAsyncDemo() {
        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                log.error("runAsyncDemo is interrupted.");
            }
        });
        try {
            completableFuture.get(); // get()方法会阻塞当前进程直至任务完成
            System.out.println("I am done.");
        } catch (InterruptedException | ExecutionException e) {
            log.error("runAsyncDemo is interrupted or execution error.");
        }

    }

    private void supplyAsyncDemo() {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                log.error("supplyAsyncDemo is interrupted.");
            }
            return "supplyAsyncDemo is done.";
        }, executor).thenApply(r -> {
            System.out.println("任务完成，正在执行Apply回调函数...");
            return "Apply: " + r;
        });
        future.thenRun(() -> {
            System.out.println("Apply回调执行结束，执行Run回调函数...");
        });
        future.thenAccept(r -> {
            System.out.println("Run回调执行结束，执行Accept回调函数...");
        });
        System.out.println("任务已创建，进入主线程..");
        try {
            future.get();
            executor.shutdown();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
