package com.tonglxer.leetcode.sort;

import java.util.Arrays;

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
    }

    private static void quickSort(int[] nums, int low, int high) {
        int n = nums.length;
        // 边界条件
        if (n == 0 || low >= high ) {
            return;
        }
        int left = low;
        int right = high;
        // 最低位元素设置为哨兵
        int temp = nums[left];
        while (left < right) {
            // 因为哨兵在左，所以一定要从右边开始遍历
            while (left < right && nums[right] >= temp) {
                right--;
            }
            nums[left] = nums[right];

            while (left < right && nums[left] <= temp) {
                left++;
            }
            nums[right] = nums[left];
        }
        // left==right的点填充哨兵值
        nums[left] = temp;
        // 至此一次排序结束
        System.out.println("Sorting: " + Arrays.toString(nums));
        // 开始递归
        quickSort(nums, low, left-1);
        quickSort(nums, left+1, high);
    }


}
