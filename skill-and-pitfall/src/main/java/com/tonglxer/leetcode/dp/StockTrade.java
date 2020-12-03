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
        // 边界条件永远是最容易忽视的点
        if (n == 0 | k <= 0) {
            return 0;
        }
        // 三维dp数组，天数、买卖次数、是否持有股票
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
        if (n == 0) {
            return 0;
        }
        int dp_1_0 = 0;
        int dp_1_1 = Integer.MIN_VALUE;
        int dp_2_0 = 0;
        int dp_2_1 = Integer.MIN_VALUE;
        for (int i=0; i<n; i++) {
            dp_1_0 = Math.max(dp_1_0, dp_1_1 + prices[i]);
            dp_1_1 = Math.max(dp_1_1, -prices[i]);
            dp_2_0 = Math.max(dp_2_0, dp_2_1 + prices[i]);
            dp_2_1 = Math.max(dp_2_1, dp_1_0 - prices[i]);
        }
        return dp_2_0;
    }

    private static int maxProfit_One(int[] prices) {
//        int n = prices.length;
//        if (n == 0) {
//            return 0;
//        }
//        int dp_0 = 0;
//        int dp_1 = Integer.MIN_VALUE;
//        for (int i=0; i<n; i++) {
//            dp_0 = Math.max(dp_0, dp_1 + prices[i]);
//            // 当只能交易一次时，那买入的那天的利润为 0-prices[i]
//            dp_1 = Math.max(dp_1, -prices[i]);
//        }
//        return dp_0;

        // 更高效的方法
        int minPrice = Integer.MAX_VALUE;
        int maxProfit = 0;
        for (int i=0; i< prices.length; i++) {
            if (prices[i] < minPrice) {
                // 维护最低价格
                minPrice = prices[i];
            } else if ((prices[i] - minPrice) > maxProfit) {
                // 更新最大利润
                maxProfit = prices[i] - minPrice;
            }
        }
        return maxProfit;
    }
}
