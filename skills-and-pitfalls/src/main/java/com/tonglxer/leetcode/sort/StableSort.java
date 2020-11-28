package com.tonglxer.leetcode.sort;

import java.util.Arrays;

/**
 * 稳定排序
 * 冒泡、直接插入、折半插入、归并、基数
 *
 * @Author Tong LinXing
 * @date 2020/11/27
 */
public class StableSort {

    public static void main(String[] args) {
        int[] nums = new int[]{3,1,2,4,6,3};
        insertionSort(nums);
        Arrays.stream(nums).forEach( n -> System.out.print(n + " "));
    }
    /**
     * 直接插入排序
     *
     * 1. 第一个元素作为已排序的子数组初始内容
     * 2. 取出下一个元素，在已经排序的元素序列中从后向前扫描
     * 3. 如果该元素（已排序）大于新元素，将该元素移到下一位置
     * 4. 重复步骤3，直到找到已排序的元素<=新元素的位置（或 j == 0时）
     * 5. 将新元素插入到该位置后（插入0位置）
     * 6. 重复步骤2~5直至所有元素排序完毕
     *
     * @param nums  待排序数组
     */
    public static void insertionSort(int[] nums){
        // 从1开始可以减少一次循环
        for (int i=1; i<nums.length; i++) {
            int temp = nums[i];
            for (int j=i; j>=0; j--) {
                if (j > 0 && nums[j-1] > temp) {
                    // 将较大的值后移一位
                    nums[j] = nums[j-1];
                } else {
                    // 当前一个元素小于temp或已经没有前一个元素时将temp插入位置j
                    nums[j] = temp;
                    break;
                }
            }
        }
    }

}
