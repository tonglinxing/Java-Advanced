package com.tonglxer.leetcode.dp;

/**
 * 子序列dp问题
 *
 * @Author Tong LinXing
 * @date 2021/2/23
 */
public class SubSequenceDP {

    /**
     * 最长公共子序列
     *
     * @param s1
     * @param s2
     * @return 最长公共子序列长度
     */
    public static int longestCommonSubsequence(String s1, String s2) {
        int len1 = s1.length();
        int len2 = s2.length();
        // base case是第0列与第0行均为0，数组已自动填充了默认值0故未显式说明
        int[][] dp = new int[len1+1][len2+2];
        for (int i=1; i<=len1; i++) {
            for (int j=1; j<=len2; j++) {
                if (s1.charAt(i) == s2.charAt(j)) {
                    dp[i][j] = dp[i-1][j-1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
                }
            }
        }
        return dp[len1][len2];
    }
}
