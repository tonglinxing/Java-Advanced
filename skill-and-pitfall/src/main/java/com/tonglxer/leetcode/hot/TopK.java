package com.tonglxer.leetcode.hot;

import java.util.PriorityQueue;

/**
 * 求数组最大的K个元素
 *
 * @Author Tong LinXing
 * @date 2021/2/12
 */
public class TopK {
    /**
     * 基于堆排
     *
     * @param nums
     * @param k
     * @return 前k个最大的数
     */
    public int[] heapSort(int[] nums, int k) {
        int[] res = new int[k];
        if (k == 0) {
            return res;
        }
        // 默认小顶堆
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (int num : nums) {
            queue.offer(num);
            if (queue.size() > k) {
                queue.poll();
            }
        }
        // 将堆中值转为数组
        for (int i = 0; i < k; ++i) {
            res[i] = queue.poll();
        }
        return res;
    }

    /**
     * 基于快排思想的快速选择算法
     *
     * @param nums 数组
     * @param k
     * @return 前k个最大的数
     */
    public int[] quickSelect(int[] nums, int k) {
        if (k >= nums.length) {
            return nums;
        }
        int target = nums.length - k;
        int low = 0;
        int high = nums.length - 1;
        while (low < high) {
            int pos = partition(nums, low, high);
            if (pos == target) {
                break;
            } else if (pos < target) {
                low = pos + 1;
            } else {
                high = pos - 1;
            }
        }
        int[] res = new int[k];
        for (int i = target; i<nums.length; i++) {
            int j = 0;
            res[j] = nums[i];
            j++;
        }
        return res;
    }

    private int partition(int[] nums, int low, int high) {
        int pivot = nums[low];
        while (low < high) {
            while (low < high && nums[high] >= pivot) {
                high--;
            }
            nums[low] = nums[high];
            while (low < high && nums[low] <= pivot) {
                low++;
            }
            nums[high] = nums[low];
        }
        nums[low] = pivot;
        return low;
    }

}
