package com.tonglxer.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 自定义基于数组的阻塞队列
 * 实现简单的生产者消费者模式
 *
 * @Author Tong LinXing
 * @date 2021/3/29
 */
public class MyArrayBlockQueue {
    // 基于可重入锁
    private final Lock lock = new ReentrantLock();

    // 满锁：队列填满时await，阻塞生产
    private final Condition full = lock.newCondition();

    // 空锁：队列空时await，阻塞消费
    private final Condition empty = lock.newCondition();

    // 数组载体
    private final Object[] items;

    // put时的索引位置
    private int putIndex;

    // take时的索引位置
    private int takeIndex;

    // 当前队列内元素数量
    private int count;

    public MyArrayBlockQueue(int size) {
        this.items = new Object[size];
    }

    /**
     * 生产者
     * @param x 生产的消息
     * @throws InterruptedException
     */
    public void put(Object x) throws InterruptedException {
        lock.lock();
        try {
            if (count == items.length) {
                // 队列已满，等待，直到 not full 才能继续生产
                full.await();
            }
            items[putIndex] = x;
            if (++putIndex == items.length) {
                putIndex = 0;
            }
            ++count;
            // 生产成功，队列已经 not empty 了，发个通知出去
            empty.signal();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 消费者
     * @return
     * @throws InterruptedException
     */
    public Object take() throws InterruptedException {
        lock.lock();
        try {
            if (count == 0) {
                // 队列为空，等待，直到队列 not empty，才能继续消费
                empty.await();
            }
            Object x = items[takeIndex];
            if (++takeIndex == items.length) {
                takeIndex = 0;
            }
            --count;
            // 被消费掉一个，队列 not full 了，发个通知出去
            full.signal();
            return x;
        } finally {
            lock.unlock();
        }
    }
}
