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
                7,6,5,4,3,2,1
        };
        quickSort(nums, 0, nums.length-1);
    }

    private static void quickSort(int[] nums, int left, int right) {
        if (nums == null || left >=right || nums.length<=1) {
            return;
        }
        int mid = partition(nums, left, right);
        // 开始递归
        quickSort(nums, left, mid-1);
        quickSort(nums, mid+1, right);
    }

    private static int partition(int[] nums, int left, int right) {
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
        // 返回分界点随后继续递归
        return left;
    }


}
