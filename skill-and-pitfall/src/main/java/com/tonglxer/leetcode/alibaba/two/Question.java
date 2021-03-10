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

    public int maxSizeSlices(int[] slices) {
        int n = slices.length;
        int[] nums1 = new int[n-1];
        int[] nums2 = new int[n-1];
        // 由于首尾只能取其一，故分别判断两种情况
        System.arraycopy(slices, 1, nums1, 0, n-1);
        System.arraycopy(slices, 0, nums2, 0, n-1);
        return Math.max(robRange(nums1), robRange(nums2));
    }

    public int robRange(int[] nums) {
        int n = nums.length;
        // n必然==原始数组长度-1
        int choose = (n+1)/3;
        // dp[i][j]表示从前i个元素取出j个不相邻的元素
        int[][] dp = new int[n+1][choose+1];
        for (int i=1; i<=n; i++) {
            for (int j=1; j<=choose; j++) {
                // 注意i-2<0的特殊情况
                // 类似 打家劫舍+背包
                dp[i][j] = Math.max(dp[i-1][j], (i-2>=0 ? dp[i-2][j-1] : 0) + nums[i-1]);
            }
        }
        // n个元素取choose个值之和
        return dp[n][choose];
    }
}
