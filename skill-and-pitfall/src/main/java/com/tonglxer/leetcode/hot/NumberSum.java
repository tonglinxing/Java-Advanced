package com.tonglxer.leetcode.hot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 探究经典问题：X数之和
 * 针对2，3，4 数之和
 *
 * @Author Tong LinXing
 * @date 2020/12/8
 */
public class NumberSum {

    public static void main(String[] args) {
        int[] nums = new int[]{1,3,5,6,2};
        System.out.println(Arrays.toString(twoSum_Pointer(nums, 11)));
    }

    /**
     * 时间复杂度和空间复杂度均为O(n)
     *
     * */
    private static int[] twoSum_Map(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i=0; i<nums.length; i++) {
            int temp = target - nums[i];
            if (map.containsKey(temp) && map.get(temp)!=i) {
                return new int[]{map.get(temp), i};
            }
            map.put(nums[i], i);
        }
        return new int[]{};
    }

    /**
     * 暴力法
     * 对之后多数之和有一定启发帮助
     * 但在两数之和中是一个很糟糕的方法
     *
     * */
    private static int[] twoSum_Pointer(int[] nums, int target) {
        for (int i=0; i<nums.length; i++) {
            for (int j = i+1; j<nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i,j};
                }
            }
        }
        return new int[]{};
    }

    /**
     *  a+b+c = 0
     *
     * @param nums
     * @return 所有可能的abc组合且不能重复
     */
    public static List<List<Integer>> threeSum(int nums[]) {
        int n = nums.length;
        // 1. 将数组排序
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        // 2. 固定第一个数，转换为两数之和的双指针做法
        for (int first=0; first<nums.length; first++) {
            // 防止重复
            if (first > 0 && nums[first] == nums[first-1]) {
                continue;
            }
            // 第三个数从最后开始处理
            int third = n-1;
            // 转为两数之和的目标值
            int target = -nums[first];
            // 3. 第二个数从第一个数后开始处理
            for (int second=first+1; second<n; second++) {
                // 防止重复
                if (second>first+1 && nums[second] == nums[second-1]) {
                    continue;
                }
                // 4. 迭代查找，若大于目标值则递减第三个数索引
                while (second<third && nums[second]+nums[third] > target) {
                    third--;
                }
                // 考虑指针重合的特殊情况
                if (second == third) {
                    break;
                }
                // 5. 找到一个满足的答案
                if (nums[second] + nums[third] == target) {
                    List<Integer> temp = new ArrayList<>();
                    temp.add(nums[first]);
                    temp.add(nums[second]);
                    temp.add(nums[third]);
                    res.add(temp);
                }
            }
        }
        return res;
    }



}
