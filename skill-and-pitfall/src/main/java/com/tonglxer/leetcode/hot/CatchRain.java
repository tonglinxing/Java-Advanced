package com.tonglxer.leetcode.hot;

/**
 * 接雨水问题
 *
 * @Author Tong LinXing
 * @date 2021/2/25
 */
public class CatchRain {
    /**
     * 暴力法
     *
     * @param height
     * @return
     */
    public static int trap(int[] height) {
        int n = height.length;
        int res = 0;
        if (n < 3) {
            return 0;
        }
        for (int i=1; i<n-1; i++) {
            int leftMax = 0;
            int rightMax = 0;
            // 从左往右查找右侧最大值
            for (int j=i; j<n; j++) {
                rightMax = Math.max(rightMax, height[j]);
            }
            // 从右往左查找左侧最大值
            for (int j=i; j>=0; j--) {
                leftMax = Math.max(leftMax, height[i]);
            }
            // 取两者更小值加入答案
            res += Math.min(leftMax, rightMax) - height[i];
        }
        return res;
    }

    /**
     * 带备忘录的暴力法
     *
     * @param height
     * @return
     */
    public static int trap2(int[] height) {
        int n = height.length;
        if (n < 3) {
            return 0;
        }
        int[] leftMax = new int[n];
        int[] rightMax = new int[n];
        // base case
        leftMax[0] = 0;
        rightMax[n-1] = 0;
        // 提前算好左右最大值
        for (int i=1; i<n; i++) {
            leftMax[i] = Math.max(height[i], leftMax[i-1]);
        }
        for (int i=n-2; i>=0; i--) {
            rightMax[i] = Math.max(height[i], rightMax[i-1]);
        }
        int res = 0;
        // 计算答案
        for (int i=1; i<n-1; i++) {
            res += Math.min(leftMax[i], rightMax[i]) - height[i];
        }
        return res;
    }

    /**
     * 双指针法
     *
     * @param height
     * @return
     */
    public static int trap3(int[] height) {
        int n = height.length;
        if (n < 3) {
            return 0;
        }
        int left = 0;
        int right = n-1;
        // [0, left] 中最大值
        int leftMax = height[0];
        // [right, n-1] 中最大值
        int rightMax = height[n-1];
        int res = 0;
        while (left <= right) {
            // 实时更新两边最大值
            leftMax = Math.max(leftMax, height[left]);
            rightMax = Math.max(rightMax, height[right]);
            // 只关注当前与较小的最大值的差
            if (leftMax < rightMax) {
                res += leftMax - height[left];
                left++;
            } else {
                res += rightMax - height[right];
                right--;
            }
        }
        return res;
    }
}
