package com.tonglxer.leetcode.hot;

/**
 * 回文相关问题
 *
 * @Author Tong LinXing
 * @date 2021/2/23
 */
public class Palindromic {

    /**
     * 最长回文子串
     *
     * @param s
     * @return 最长回文子串
     */
    public static String longestPalindrome(String s) {
        int len = s.length();
        if (len < 2) {
            return s;
        }
        int maxLen = 1;
        String res = s.substring(0, 1);
        for (int i=0; i<len-1; i++) {
            // 奇数串
            String oddStr = centerSpread(s, i, i);
            // 偶数串
            String evenStr = centerSpread(s, i, i+1);
            // 取其大者
            String maxLenStr = oddStr.length() > evenStr.length() ? oddStr : evenStr;
            // 记录全局最大值
            if (maxLenStr.length() > maxLen) {
                maxLen = maxLenStr.length();
                res = maxLenStr;
            }
        }
        return res;
    }

    /**
     * 中心扩散法
     *
     * @param s
     * @param left
     * @param right
     * @return 当前中心对应的最长回文子串
     */
    private static String centerSpread(String s, int left, int right) {
        // left == right时为奇数串，中心为left(right)对应位置
        // right == left+1时为偶数串，中心为left和right中间的空隙
        int len = s.length();
        int i = left;
        int j = right;
        while (i>=0 && j<len) {
            if (s.charAt(i) == s.charAt(j)) {
                i--;
                j++;
            } else {
                break;
            }
        }
        return s.substring(i+1, j);
    }

    /**
     * 最长回文子序列
     * dp[i][j]代表字符串索引i到j的子串的回文子序列最大长度
     *
     * @param s
     * @return
     */
    public static int longestPalindromeSubSequence(String s) {
        int n = s.length();
        int[][] dp = new int[n][n];
        // 由下至上，由左至右遍历
        for (int i = n-1; i>=0; i--) {
            dp[i][i] = 1;
            for (int j = i+1; j<n; j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    dp[i][j] = dp[i+1][j-1] + 2;
                } else {
                    dp[i][j] = Math.max(dp[i+1][j], dp[i][j-1]);
                }
            }
        }
        return dp[0][n-1];
    }
}
