package com.tonglxer.leetcode.alibaba;

/**
 * @Author Tong LinXing
 * @date 2021/3/20
 */

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = Integer.parseInt(sc.nextLine());
        List<Integer> ans = new LinkedList<>();
        for (int i=0; i<n; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            int c = sc.nextInt();
            sc.nextLine();
            int res = findMin(a, b, c);
            ans.add(res);

        }
        for (int a : ans) {
            System.out.println(a);
        }
    }

    public static int findMin(int a, int b, int c) {
        // a和b一样的位
        int same = a&b;
        // n为不一样的位
        int n = (a|b)^c;
        int res = 0;

        while (n!=0) {
            if ((n&1) == 1) {
                res++;
            }
            if ((same&1) == 1) {
                res++;
            }
            same = same/2;
            n = n/2;
        }
        return res;
    }
}
