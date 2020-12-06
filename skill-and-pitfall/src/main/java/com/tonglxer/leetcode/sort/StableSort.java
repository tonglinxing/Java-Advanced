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
        System.out.println("InsertSort：");
        Arrays.stream(nums).forEach( n -> System.out.print(n + " "));
        System.out.println("\nMergeSort：");
        mergeSort(nums, 0, 5);
        System.out.println(Arrays.toString(nums));
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


    private static void mergeSort(int[] nums, int low, int high) {
        // 边界条件
        if (low == high) {
            return;
        } else {
            // 这种写法可以防止整型溢出
            int mid = low + (high-low)/2;
            mergeSort(nums, low, mid);
            mergeSort(nums, mid+1, high);
            merge(nums, low, mid+1, high);
        }
    }

    private static void merge(int[] nums, int low, int mid, int high) {
        int[] leftNums = new int[mid-low];
        int[] rightNums = new int[high-mid+1];
        for (int i=low;i<mid;i++) {
            leftNums[i-low] = nums[i];
        }
        for (int i=mid; i<=high;i++) {
            rightNums[i-mid] = nums[i];
        }

        int i = 0, j = 0;
        // arrays数组的第一个元素
        int  k = low;


        //比较这两个数组的值，哪个小，就往数组上放
        while (i < leftNums.length && j < rightNums.length) {
            //谁比较小，谁将元素放入大数组中,移动指针，继续比较下一个
            if (leftNums[i] < rightNums[j]) {
                nums[k] = leftNums[i];
                i++;
                k++;
            } else {
                nums[k] = rightNums[j];
                j++;
                k++;
            }
        }

        //如果左边的数组还没比较完，右边的数都已经完了，那么将左边的数抄到大数组中(剩下的都是大数字)
        while (i < leftNums.length) {
            nums[k] = leftNums[i];
            i++;
            k++;
        }
        //如果右边的数组还没比较完，左边的数都已经完了，那么将右边的数抄到大数组中(剩下的都是大数字)
        while (j < rightNums.length) {
            nums[k] = rightNums[j];
            k++;
            j++;
        }
    }

}
