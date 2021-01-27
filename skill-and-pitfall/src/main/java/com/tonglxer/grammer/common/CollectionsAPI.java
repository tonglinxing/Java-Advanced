package com.tonglxer.grammer.common;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * 一些集合的接口方法使用
 *
 * @Author Tong LinXing
 * @date 2021/1/27
 */
public class CollectionsAPI {
    public static void main(String[] args) {
        sortCompare();
    }


    /**
     * 对对象排序有两种方法可以自定义排序规则：
     * Comparable是排序接口:
     * 若一个类实现了Comparable接口，就意味着该类支持排序。
     * 而Comparator是比较器: (用多了lambda可能会搞混这两个)
     * 若需要控制某个类的次序，可以建立一个该类的比较器来进行排序。
     *
     * 1. 传入自定义比较器
     */
    private static void sortCompare() {
        int[][] nums = {{1,3}, {1,2}, {4,5}, {3,7}};
        Arrays.sort(nums, (o1, o2) -> {
            // 若第一位数字相等则比较第二位数字升序
            if (o1[0] == o2[0]) {
                return o1[1] - o2[1];
            } else {
                // 按第一位数字升序排列
                return o1[0] - o2[0];
            }
        });
        for (int i=0; i<nums.length; i++) {
            System.out.println(Arrays.toString(nums[i]));
        }
        // 类似的TreeMap也可以使用Comparator，此处以键的年龄降序排列
        TreeMap<Worker, String> map = new TreeMap<>((o1, o2) -> o2.age - o1.age);
        // 将map.entrySet()转换成list
        List<Map.Entry<Worker,String>> list = new ArrayList<>(map.entrySet());
        // 将map按键升序
        Collections.sort(list, (o1, o2) -> o1.getValue().compareTo(o2.getValue()));
    }

    /**
     * 2. 实现Comparable接口
     */
    @AllArgsConstructor
    private class Worker implements Comparable<Worker> {
        private int age;
        private int workerTime;
        private String name;

        @Override
        public int compareTo(Worker o) {
            // 按年龄升序，若相等则按工龄升序
            if (this.age == o.age) {
                return this.workerTime - o.workerTime;
            } else {
                return this.age - o.age;
            }
        }

        /**
         * 平时用惯了lombok注解，手写一下equal和hashcode的重写逻辑
         *
         * @param o 比较的对象实例
         * @return true相同，反之则然
         */
        @Override
        public boolean equals(Object o) {
            // 若地址相同则判同
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Worker worker = (Worker) o;
            return age == worker.age &&
                    workerTime == worker.workerTime &&
                    Objects.equals(name, worker.name);
        }

        @Override
        public int hashCode() {
            // hash()是Objects类原生的方法，直接使用即可
            return Objects.hash(age, workerTime, name);
        }
    }
}
