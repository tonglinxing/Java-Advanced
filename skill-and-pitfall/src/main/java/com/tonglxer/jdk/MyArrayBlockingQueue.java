package com.tonglxer.jdk;

import lombok.extern.slf4j.Slf4j;

/**
 * 自定义阻塞队列
 * 1. FIFO
 * 2. 写入队列空间不可用时阻塞
 * 3. 获取队列为空时阻塞
 *
 * @Author Tong LinXing
 * @date 2021/2/9
 */
@Slf4j
public class MyArrayBlockingQueue<T> {
    // 队列元素数量
    private int size = 0;

    // 用于存储的数组
    private Object[] items;

    // 队列满锁
    private Object full = new Object();

    // 队列空锁
    private Object empty = new Object();

    // 队列插入位置
    private int putIndex;

    // 队列消费位置
    private int getIndex;


    public MyArrayBlockingQueue(int size) {
        items = new Object[size];
    }

    /**
     * 若队列已满则会阻塞
     *
     * @param t 要放入的元素
     */
    public void put(T t) {
        synchronized (full) {
            while (size == items.length) {
                try {
                    full.wait();
                } catch (InterruptedException e) {
                    log.error("The full queue have some error.");
                    return;
                }
            }
        }
        synchronized (empty) {
            items[putIndex] = t;
            size++;
            putIndex++;
            if (putIndex == items.length) {
                putIndex = 0;
            }
            empty.notify();
        }
    }

    /**
     * 队列为空时会阻塞
     *
     * @return 获取元素
     */
    public T get() {
        synchronized (empty) {
            if (size == 0) {
                try {
                    empty.wait();
                } catch (InterruptedException e) {
                    log.error("The empty queue have some error.");
                    return null;
                }
            }
        }
        synchronized (full) {
            Object result = items[getIndex];
            items[getIndex] = null;
            size--;
            getIndex++;
            if (getIndex == items.length) {
                getIndex = 0;
            }
            full.notify();
            return (T)result;
        }
    }
}
