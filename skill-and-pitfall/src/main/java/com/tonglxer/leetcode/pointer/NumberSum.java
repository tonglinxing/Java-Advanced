package com.tonglxer.leetcode.pointer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Tong LinXing
 * @date 2020/12/8
 */
public class NumberSum {

    public static void main(String[] args) {
        int[] nums = new int[]{1,3,5,6,2};
        System.out.println(Arrays.toString(twoSum_Pointer(nums, 11)));
    }

    private static int[] twoSum_Map(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i=0; i< nums.length; i++) {
            int temp = target - nums[i];
            if (map.containsKey(temp) && map.get(temp) != i) {
                return new int[]{map.get(temp), i};
            }
            map.put(nums[i], i);
        }
        return new int[]{};
    }

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



}
