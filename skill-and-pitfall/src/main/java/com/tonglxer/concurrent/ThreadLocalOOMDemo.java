package com.tonglxer.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ThreadLocal变量的使用
 * 底层是Map，其中key为弱引用，value为强引用
 * 在线程池使用场景下，如果不手动remove掉ThreadLocal变量的话
 * 会导致内存泄漏发生 -> 可能引发OOM
 *
 * @Author Tong LinXing
 * @date 2020/12/5
 */
@Slf4j
public class ThreadLocalOOMDemo {
    private static final int THREAD_SIZE = 5;

    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_SIZE);
        try {
            for (int i = 0; i < THREAD_SIZE; i++) {
                try {
                    executorService.execute(() -> {
                        Thread current = Thread.currentThread();
                        threadLocal.set(current.toString());
                        System.out.println("ThreadLocal value : " + threadLocal.get());
                    });
                        Thread.sleep(500L);
                    } catch (InterruptedException e) {
                        log.error("ThreadLocalOOMDemo is cancel.");
                } finally {
                    // 如果不手动移除的话，ThreadLocal变量会在线程池生命周期内一直存在
                    // 导致内存泄漏问题
                    threadLocal.remove();
                }
            }
        } finally {
            executorService.shutdown();
        }
    }
}
