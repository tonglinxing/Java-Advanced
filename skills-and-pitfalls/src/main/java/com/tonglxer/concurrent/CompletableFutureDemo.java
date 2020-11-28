package com.tonglxer.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

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
        instance.runAsyncDemo();
    }

    public CompletableFutureDemo getInstance() {
        return instance;
    }


    private void runAsyncDemo() {
        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                log.error("runAsyncDemo is interrupted.");
            }
            System.out.println("hello");
        });
        try {
            System.out.println("I am done." + completableFuture.get());
        } catch (InterruptedException | ExecutionException e) {
            log.error("runAsyncDemo is interrupted or execution error.");
        }

    }
}
