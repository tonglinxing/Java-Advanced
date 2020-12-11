package com.tonglxer.leetcode.dp;

/**
 * @Author Tong LinXing
 * @date 2020/12/10
 */
public class RobProblem {

    public static void main(String[] args) {
        int[] nums = new int[]{4,3,4,9};
        System.out.println(rob(nums));
    }

    private static int rob(int[] nums) {
        int n = nums.length;
        if (n == 0) {
            return 0;
        }
//        int dp[] = new int[n+2];
//        for (int i=n-1; i>=0; i--) {
//            dp[i] = Math.max(dp[i+1], dp[i+2] + nums[i]);
//        }
//        return dp[0];
        int dp_1 = 0, dp_2 = 0;
        int ans = 0;
        for (int i=n-1; i>=0; i--) {
            ans = Math.max(dp_1, dp_2+nums[i]);
            dp_2 = dp_1;
            dp_1 = ans;
        }
        return ans;
    }
}
