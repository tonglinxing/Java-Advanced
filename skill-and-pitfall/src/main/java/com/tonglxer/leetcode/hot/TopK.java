package com.tonglxer.leetcode.hot;

import java.util.PriorityQueue;

/**
 * @Author Tong LinXing
 * @date 2021/2/12
 */
public class TopK {
    // 小顶堆
    public int[] heapSort(int[] nums, int k) {
        int[] res = new int[k];
        if (k == 0) {
            return res;
        }
        // 默认小顶堆
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (int i = 0; i < k; ++i) {
            queue.offer(nums[i]);
        }
        for (int i = k; i < nums.length; ++i) {
            // 堆顶即为该堆中最小值
            // 若堆最小值小于当前值，则进行替换
            if (queue.peek() < nums[i]) {
                queue.poll();
                queue.offer(nums[i]);
            }
        }
        // 将堆中值转为数组
        for (int i = 0; i < k; ++i) {
            res[i] = queue.poll();
        }
        return res;
    }
}
