package com.tonglxer.concurrent;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author Tong LinXing
 * @date 2020/11/22
 */
@Data
@Slf4j
public class ConcurrentCounter {
    private static int THREAD_NUM = 100;

    private int normalCount = 0;

    private int sycCount = 0;

    private  int lockCount = 0;

    private AtomicInteger atomCount =  new AtomicInteger(0);

    private Lock lock = new ReentrantLock();

    // 该倒计时用于模拟多线程同时执行 防止因调用顺序不一致而导致的错峰执行
    private CountDownLatch countDownLatch = new CountDownLatch(THREAD_NUM);

    // 该倒计时用于所有线程执行完毕后通知主线程执行打印操作
    private CountDownLatch mainDownLatch = new CountDownLatch(THREAD_NUM);

    //模拟多线程情况
    public void simulateCurrent() {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_NUM);
        for (int i=0;i<THREAD_NUM;i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        countDownLatch.countDown();
                        countDownLatch.await();
                        // 每个线程执行THREAD_NUM次+1操作
                        for (int i=0; i<THREAD_NUM; i++) {
                            normalCount++;
                            sycAddInteger();
                            lockAddInteger();
                            atomCount.incrementAndGet();
                        }
                    } catch (InterruptedException e) {
                        log.error("Interrupted Exception, {0}", e);
                    }
                    mainDownLatch.countDown();
                }
            });
        }

    }
    // synchronized方法
    private synchronized void sycAddInteger() {
        sycCount++;
    }

    // lock方法
    private void lockAddInteger() {
        lock.lock();
        lockCount++;
        lock.unlock();
    }

    /**
     * 1. 普通++在多线程情况下结果错误
     * 2. Synchronized和Lock运行时间大于原子类
     * （因为2，3两种方式会导致线程进入阻塞状态，出让cpu资源，故在锁释放后该线程会等待cpu资源调度的额外时间）
     * 3. 试试不添加mainDownLatch逻辑，还有什么办法使得主线程输出正确？
     *  3.1 Callable
     *  3.2 Thread.Sleep() -> 时间不好把控
     * */
    public static void main(String[] args) throws InterruptedException{
        for (int i=0; i<10; i++) {
            ConcurrentCounter instance = new ConcurrentCounter();
            instance.simulateCurrent();
            instance.getMainDownLatch().await();
            System.out.println("The Normal count is : " + instance.getNormalCount());
            System.out.println("The Syc count is : " + instance.getSycCount());
            System.out.println("The Lock count is : " + instance.getLockCount());
            System.out.println("The Atom count is : " + instance.getAtomCount());
            System.out.println();
        }
    }
}
