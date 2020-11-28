package com.tonglxer.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 多线程交替打印
 * 以三线程为例
 * 模拟三线程按序交替打印各自线程名字
 *
 * @Author Tong LinXing
 * @date 2020/11/27
 */
@Slf4j
public class MultiThreadPrint {

    private static Thread A, B, C;

    public static void main(String[] args) {
        /**
         * 简单的demo，所以没有使用异步控制
         * 故无法同时调用两种实现方法
         * 否则数据会重叠
         * */
        lockSupport();
        System.out.println();
//        lockCondition();
    }

    /**
     * LockSupport是JUC.locks下的工具类
     * 基于Unsafe类实现
     * unpark(Thread t)：唤醒指定线程
     * park()：阻塞当前线程
     *
     * 似乎因为执行的太快而导致结果不对 尚未定位到原因
     * 暂时的解决办法：Thread.sleep(300);
     * */
    private static void lockSupport() {
        System.out.println("基于LockSupport实现：");
        A = new Thread(() -> {
            for (int i=0; i<5; i++) {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    log.error("InterruptedException: ", e);
                }
                System.out.print(Thread.currentThread().getName());
                // 唤醒B线程
                LockSupport.unpark(B);
                // 当前线程阻塞
                LockSupport.park();
            }
        }, "A");
        B = new Thread(() -> {
            for (int i=0; i<5; i++) {
                // 阻塞当前线程，等待A线程唤醒
                LockSupport.park();
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    log.error("InterruptedException: ", e);
                }
                System.out.print(Thread.currentThread().getName());
                // 唤醒C线程
                LockSupport.unpark(C);
            }
        }, "B");
        C = new Thread(() -> {
            for (int i=0; i<5; i++) {
                // 阻塞当前线程，等待B线程唤醒
                LockSupport.park();
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    log.error("InterruptedException: ", e);
                }
                System.out.print(Thread.currentThread().getName() + " ");
                // 唤醒A线程
                LockSupport.unpark(A);
            }
        }, "C");
        A.start();
        B.start();
        C.start();
    }


    /**
     * 通过可重入锁的Condition进行选择性唤醒其他线程
     * */
    @SuppressWarnings("all")
    private static void lockCondition() {
        System.out.println("基于可重入锁实现：");
        ReentrantLock lock = new ReentrantLock();
        Condition conditionA = lock.newCondition();
        Condition conditionB = lock.newCondition();
        Condition conditionC = lock.newCondition();
        new Thread(() -> {
            try {
                lock.lock();
                for (int i=0;i<5;i++) {
                    Thread.sleep(300);
                    System.out.print(Thread.currentThread().getName());
                    conditionB.signal();
                    conditionA.await();
                }
            } catch (InterruptedException e) {
                log.error("InterruptedException: ", e);
            }
        }, "A").start();
        new Thread(() -> {
            try {
                lock.lock();
                for (int i=0;i<5;i++) {
                    Thread.sleep(300);
                    System.out.print(Thread.currentThread().getName());
                    conditionC.signal();
                    conditionB.await();
                }
            } catch (InterruptedException e) {
                log.error("InterruptedException: ", e);
            }
        }, "B").start();
        new Thread(() -> {
            try {
                lock.lock();
                for (int i=0;i<5;i++) {
                    Thread.sleep(300);
                    System.out.print(Thread.currentThread().getName() + " ");
                    conditionA.signal();
                    conditionC.await();
                }
            } catch (InterruptedException e) {
                log.error("InterruptedException: ", e);
            }
        }, "C").start();
    }
}
