package com.tonglxer.leetcode.hot;

import java.util.Stack;

/**
 * 栈实现队列
 *
 * @Author Tong LinXing
 * @date 2021/2/10
 */
public class StackToQueue {
    // push的栈
    private Stack<Integer> pushStack = new Stack<>();

    // pop时的中转栈
    private Stack<Integer> popStack = new Stack<>();

    /**
     * 值直接压入pushStack
     *
     * @param val
     */
    public void push(int val) {
        pushStack.push(val);
    }

    /**
     * 获取顶层元素，即最早存入的元素
     *
     * @return 顶层元素
     */
    public int peek() {
        if (popStack.isEmpty()) {
            while (!pushStack.isEmpty()) {
                popStack.push(pushStack.pop());
            }
        }
        return popStack.peek();
    }

    /**
     * 顶层元素出栈
     *
     * @return 顶层元素
     */
    public int poll() {
        if (popStack.isEmpty()) {
            while (!pushStack.isEmpty()) {
                popStack.push(pushStack.pop());
            }
        }
        return popStack.pop();
    }

}
