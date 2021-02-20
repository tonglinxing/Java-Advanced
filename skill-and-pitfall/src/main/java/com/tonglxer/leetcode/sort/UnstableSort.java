package com.tonglxer.leetcode.sort;

import java.util.Arrays;
import java.util.Stack;

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
        quickSortByStack(nums);
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

    /**
     * 快速排序（非递归）
     *
     * ①. 从数列中挑出一个元素，称为"基准"（pivot）。
     * ②. 重新排序数列，所有比基准值小的元素摆放在基准前面，所有比基准值大的元素摆在基准后面（相同的数可以到任一边）。在这个分区结束之后，该基准就处于数列的中间位置。这个称为分区（partition）操作。
     * ③. 把分区之后两个区间的边界（low和high）压入栈保存，并循环①、②步骤
     * @param nums   待排序数组
     */
    public static void quickSortByStack(int[] nums) {
        if (nums.length <= 0) {
            return;
        }
        Stack<Integer> stack = new Stack<Integer>();
        //初始状态的左右指针入栈
        stack.push(0);
        stack.push(nums.length - 1);
        while (!stack.isEmpty()) {
            //出栈进行划分
            int high = stack.pop();
            int low = stack.pop();
            int pivotIdx = partition(nums, low, high);
            //保存中间变量
            if (pivotIdx > low) {
                stack.push(low);
                stack.push(pivotIdx - 1);
            }
            if (pivotIdx < high && pivotIdx >= 0){
                stack.push(pivotIdx + 1);
                stack.push(high);
            }
        }
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


    /**
     * 希尔排序
     * 1. 选择一个增量序列t1，t2，…，tk，其中ti>tj，tk=1；（一般初次取数组半长，之后每次再减半，直到增量为1）
     * 2. 按增量序列个数k，对序列进行k 趟排序；
     * 3. 每趟排序，根据对应的增量ti，将待排序列分割成若干长度为m 的子序列，分别对各子表进行直接插入排序。
     *    仅增量因子为1 时，整个序列作为一个表来处理，表长度即为整个序列的长度。
     *
     * @param nums  待排序数组
     */
    public static void shellSort(int[] nums){
        int gap = nums.length / 2;
        // 不断缩小gap，直到1为止
        while (gap > 0) {
            // 使用当前gap进行组内插入排序
            for (int j = 0; (j+gap) < nums.length; j++){
                for(int k = 0; (k+gap)< nums.length; k += gap){
                    if(nums[k] > nums[k+gap]) {
                        // 交换
                        int temp = nums[k+gap];
                        nums[k+gap] = nums[k];
                        nums[k] = temp;
                        System.out.println("Sorting: " + Arrays.toString(nums));
                    }
                }
            }
            // gap迭代减半
            gap/=2;
        }
    }

    /**
     * 选择排序
     * 1. 从待排序序列中，找到关键字最小的元素；
     * 2. 如果最小元素不是待排序序列的第一个元素，将其和第一个元素互换；
     * 3. 从余下的 N - 1 个元素中，找出关键字最小的元素，重复①、②步，直到排序结束。
     *    仅增量因子为1 时，整个序列作为一个表来处理，表长度即为整个序列的长度。
     *
     * @param nums  待排序数组
     */
    public static void selectSort(int[] nums){
        for(int i = 0; i < nums.length-1; i++){
            int min = i;
            // 找出待排序子数组中值最小的位置
            for(int j = i+1; j < nums.length; j++){
                if(nums[j] < nums[min]){
                    min = j;
                }
            }
            // 与待排序子数组的首位进行交换
            if(min != i){
                int temp = nums[min];
                nums[min] = nums[i];
                nums[i] = temp;
                System.out.println("Sorting: " + Arrays.toString(nums));
            }
        }
    }

    /**
     * 堆排序
     *
     * 1. 先将初始序列K[1..n]建成一个大顶堆, 那么此时第一个元素K1最大, 此堆为初始的无序区.
     * 2. 再将关键字最大的记录K1 (即堆顶, 第一个元素)和无序区的最后一个记录 Kn 交换,
     *    由此得到新的无序区K[1..n−1]和有序区K[n], 且满足K[1..n−1].keys⩽K[n].key
     * 3. 交换K1 和 Kn 后, 堆顶可能违反堆性质, 因此需将K[1..n−1]调整为堆. 然后重复步骤②, 直到无序区只有一个元素时停止.
     * @param nums  待排序数组
     */
    public static void heapSort(int[] nums){
        for (int i = nums.length; i > 0; i--) {
            // i为边界
            max_heapify(nums, i);
            // 堆顶元素(第一个元素)与Kn交换
            int temp = nums[0];
            nums[0] = nums[i-1];
            nums[i-1] = temp;
        }
    }

    private static void max_heapify(int[] nums, int limit){
        if(nums.length <= 0 || nums.length < limit) {
            return;
        }
        int parentIdx = limit / 2;
        while (parentIdx >= 0) {
            if(parentIdx * 2 >= limit){
                continue;
            }
            // 左子节点位置
            int left = parentIdx * 2;
            // 右子节点位置，如果没有右节点，默认为左节点位置
            int right = (left + 1) >= limit ? left : (left + 1);
            int maxChildId = nums[left] >= nums[right] ? left : right;
            // 若子节点最大值大于父节点则交换
            if (nums[maxChildId] > nums[parentIdx]){
                int temp = nums[parentIdx];
                nums[parentIdx] = nums[maxChildId];
                nums[maxChildId] = temp;
            }
            parentIdx--;
        }
        System.out.println("Max_Heapify: " + Arrays.toString(nums));
    }
}
