package com.tonglxer.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ExecutionException;

/**
 *
 *
 * @Author Tong LinXing
 * @date 2020/11/28
 */
@Slf4j
public class FutureTaskDemo {
    public static void main(String[] args) {
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                // 核心线程 -> 阻塞队列 -> 创建非核心线程 -> 达到最大线程数后执行拒绝策略
                2, 5,
                // 非核心线程数在空闲时的存活时间
                10, TimeUnit.SECONDS,
                // 阻塞队列，当线程数量等于corePoolSize时，请求加入阻塞队列里
                new LinkedBlockingDeque<>(10),
                // 拒绝策略，即阻塞队列和最大线程数均满后提交的请求
                new ThreadPoolExecutor.CallerRunsPolicy()
        );

        /**
         * @param runnable the runnable task
         * @param result the result to return on successful completion.（可缺省）
         * */
        FutureTask future = new FutureTask(() -> {
            try {
                Thread.sleep(300);
                System.out.println("我是执行中的任务...");
                Thread.sleep(300);
            } catch (InterruptedException e) {
                log.error("future task is interrupted.");
            }
        }, "任务执行完成."); //可以添加return替代第二个参数
        // 将任务提交到线程池
        threadPool.submit(future);
        System.out.println("任务创建完成，进入主线程...");
        try {
            System.out.println(future.get());
        } catch (ExecutionException | InterruptedException e) {
            log.error("future task is interrupted or executive error.");
        }
        threadPool.shutdown();
    }

}
