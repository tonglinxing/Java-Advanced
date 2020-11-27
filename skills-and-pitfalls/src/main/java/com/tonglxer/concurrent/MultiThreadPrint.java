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

    public static void main(String[] args) {
        lockSupport();
        lockCondition();
    }

    /**
     * LockSupport是JUC.locks下的工具类
     * unpark()：阻塞当前线程
     * park(Thread t)：唤醒指定线程
     *
     * 这个例子一直提示ABC可能尚未初始化变量
     * 待解决
     *
     * */
    private static void lockSupport() {
        System.out.println("基于LockSupport实现：");
        Thread A, B, C;
        A = new Thread(() -> {
            for (int i=0; i<5; i++) {
                System.out.print(Thread.currentThread().getName());
                LockSupport.unpark(B);
                LockSupport.park();
            }
            System.out.println();
        }, "A");
        B = new Thread(() -> {
            for (int i=0; i<5; i++) {
                System.out.print(Thread.currentThread().getName());
                LockSupport.unpark(C);
                LockSupport.park();
            }
            System.out.println();
        }, "B");
        C = new Thread(() -> {
            for (int i=0; i<5; i++) {
                System.out.print(Thread.currentThread().getName());
                LockSupport.unpark(A);
                LockSupport.park();
            }
            System.out.println();
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
                    System.out.print(Thread.currentThread().getName());
                    conditionB.signal();
                    conditionA.await();
                }
                System.out.println();
            } catch (InterruptedException e) {
                log.error("InterruptedException: ", e);
            }
        }, "A").start();
        new Thread(() -> {
            try {
                lock.lock();
                for (int i=0;i<5;i++) {
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
                    System.out.print(Thread.currentThread().getName());
                    conditionA.signal();
                    conditionC.await();
                }
            } catch (InterruptedException e) {
                log.error("InterruptedException: ", e);
            }
        }, "C").start();
    }
}
