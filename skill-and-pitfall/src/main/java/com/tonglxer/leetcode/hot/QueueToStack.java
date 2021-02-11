package com.tonglxer.leetcode.hot;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 队列实现栈
 *
 * @Author Tong LinXing
 * @date 2021/2/10
 */
public class QueueToStack {
    Queue<Integer> queue = new LinkedList<>();

    // 记录栈顶元素
    int top = Integer.MIN_VALUE;

    /**
     * 直接push进队列即可
     * 具体由出栈时操作
     *
     * @param val
     */
    public void push(int val) {
        queue.offer(val);
        top = val;
    }

    /**
     * 返回栈顶元素
     *
     * @return 栈顶元素
     */
    public int top() {
        return this.top;
    }

    /**
     * 每次出栈都要对队列进行翻转
     * 倒数第二个用于保存top
     * 倒数第一个用于真正的出栈
     *
     * @return 栈顶元素
     */
    public int pop() {
        int size = queue.size();
        // 保留两个，一个用于更新top一个用于出栈
        while (size > 2) {
            queue.offer(queue.poll());
        }
        this.top = queue.peek();
        // 保留top后依然需要翻转一下
        queue.offer(queue.poll());
        return queue.poll();
    }

}
