package com.tonglxer.concurrent;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author Tong LinXing
 * @date 2020/11/27
 */
@AllArgsConstructor
@Slf4j
public class TwoThreadPrint {

    private static class MyRunnable implements Runnable {
        private static String str = "tonglinxing";
        private final Object lock = new Object();
        private int count = 0;
        @Override
        public void run() {
            synchronized (lock) {
                while (count <= 10) {
                    // notify不会立刻释放锁，而是执行完同步代码块内容后释放
                    lock.notify();
                    System.out.println(Thread.currentThread().getName() + " print：" + str.charAt(count++));
                    try {
                        Thread.sleep(500);
                        lock.wait();
                    } catch (InterruptedException e) {
                        log.error("InterruptedException: ", e);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        MyRunnable runnable = new MyRunnable();
        ExecutorService threadPool = Executors.newFixedThreadPool(2);
        for (int i=0; i<2; i++) {
            threadPool.submit(runnable);
        }
    }
}
