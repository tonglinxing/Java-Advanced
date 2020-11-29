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

    /**
     * <Question>为什么《阿里巴巴Java开发手册》上要禁止使用Executors来创建线程池?</Question>
     *
     * Executors创建出来的线程池使用的全都是无界队列
     * 而使用无界队列会带来很多弊端，最重要的就是，它可以无限保存任务，因此很有可能造成OOM异常。
     * 同时在某些类型的线程池里面
     * 使用无界队列还会导致maximumPoolSize、keepAliveTime、handler等参数失效。
     *
     * */


    public static void main(String[] args) {
        /**
         * CPU密集型：线程个数为CPU核数。
         * 这几个线程可以并行执行，不存在线程切换到开销
         * 提高了cpu的利用率的同时也减少了切换线程导致的性能损耗
         *
         * IO密集型：线程个数为CPU核数的两倍。
         * 其中的线程在IO操作的时候，其他线程可以继续用cpu，提高了cpu的利用率。
         *
         * */
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
