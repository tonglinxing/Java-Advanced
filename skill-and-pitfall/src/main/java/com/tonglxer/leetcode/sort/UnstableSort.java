package com.tonglxer.leetcode.sort;

import java.util.Arrays;
import java.util.Collections;

/**
 * 不稳定排序
 * 快排、希尔、堆、简单选择
 *
 * @Author Tong LinXing
 * @date 2020/11/27
 */
public class UnstableSort {

    public static void main(String[] args) {
        int[] nums = new int[] {
                4,5,2,6,1,9,0
        };
        quickSort(nums, 0, 6);
        for (int n : nums) {
            System.out.print(n + " ");
        }
        System.out.println();
    }

    private static void quickSort(int[] nums, int low, int high) {
        int n = nums.length;
        if (n == 0 || low >= high ) {
            return;
        }
        int left = low;
        int right = high;
        int temp = nums[left];
        while (left < right) {
            while (left < right && nums[right] >= temp) {
                right--;
            }
            while (left < right && nums[left] <= temp) {
                left++;
            }
            nums[right] = nums[left];
        }
        nums[left] = temp;
        System.out.println("Sorting: " + Arrays.toString(nums));
        quickSort(nums, low, left-1);
        quickSort(nums, left+1, high);

    }
}
