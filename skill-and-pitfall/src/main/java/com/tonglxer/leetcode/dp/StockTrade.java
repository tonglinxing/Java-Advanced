package com.tonglxer.leetcode.dp;

/**
 * @Author Tong LinXing
 * @date 2020/12/3
 */
public class StockTrade {
    public static void main(String[] args) {
        int[] prices = new int[] {
                3,2,6,5,0,3
        };
        System.out.println(maxProfit(prices, 2));
    }

    private static int maxProfit(int[] prices, int k) {
        int n = prices.length;
        if (n == 0 | k <= 0) {
            return 0;
        }
        int[][][] dp = new int[n][k+1][2];
        for (int i=0; i<n; i++) {
            for (int j=k; j>=1; j--) {
                if (i==0) {
                    dp[i][j][0] = 0;
                    dp[i][j][1] = -prices[i];
                    continue;
                }
                dp[i][j][0] = Math.max(dp[i-1][j][0], dp[i-1][j][1] + prices[i]);
                dp[i][j][1] = Math.max(dp[i-1][j][1], dp[i-1][j-1][0] - prices[i]);
            }
        }
        return dp[n-1][k][0];
    }

    private static int maxProfit_Two(int[] prices) {
        int n = prices.length;
        int dp_1_0 = 0;
        int dp_1_1 = Integer.MIN_VALUE;
        int dp_2_0 = 0;
        int dp_2_1 = Integer.MIN_VALUE;
        for (int i=0; i<n; i++) {
            dp_1_0 = Math.max(dp_1_0, dp_1_1 + prices[i]);
            dp_1_1 = Math.max(dp_1_1, -prices[i]);
            dp_2_0 = Math.max(dp_2_0, dp_2_1 + prices[i]);
            dp_2_1 = Math.max(dp_2_1, dp_1_1 - prices[i]);
        }
        return dp_2_0;
    }
}
