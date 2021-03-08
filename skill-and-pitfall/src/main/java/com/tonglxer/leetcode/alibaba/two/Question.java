package com.tonglxer.leetcode.alibaba.two;

/**
 * 3/8
 *
 * @Author Tong LinXing
 * @date 2021/3/8
 */
public class Question {

    /**
     * 给你一个 严格升序排列 的正整数数组 arr 和一个整数 k
     * 请你找到这个数组里第 k 个缺失的正整数
     *
     * @param arr 严格升序数组
     * @param k
     * @return 返回第k个缺失的整数
     */
    public static int findKthPositive(int[] arr, int k) {
        // 分三种情况
        // 1. arr[0] > k -> return k
        // 2. arr[i] - i (正常时此处等于1)> k -> return i + k
        // 3. arr[arr.length-1] == arr.length -> return arr.length+k
        for (int i=0; i<arr.length; i++) {
            if (arr[i] - i > k) {
                return i+k;
            }
        }
        return arr.length + k;
    }
}
