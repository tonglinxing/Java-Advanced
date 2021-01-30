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
public class JDKUtilsDemo {
    public static void main(String[] args) {
        CollectionsAndArrays();
        StringMethod();
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
    private static void CollectionsAndArrays() {
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
        List<Integer> intList = new ArrayList<>(8);
        // 可用于初始化
        Collections.addAll(intList, 0, 0, 1);
        // 将集合内元素全部填充为0
        Collections.fill(intList, 0);
        // 获取集合内某一元素的数量（出现频率）
        Collections.frequency(intList, 0);
        // 判断两个集合是否相交，不相交则返回true，反之则然
        Collections.disjoint(intList, intList);
        /**
         * <p>这是一个大坑</p>
         * 将数组转换为列表, 但该列表无法进行增删操作
         * 这个ArrayList类并非java.util.ArrayList类
         * 而是Arrays类的静态内部类
         */
        Arrays.asList(new int[]{1,2,3});
        int[] temp = new int[8];
        // 这个相对于集合的fill，更实用
        Arrays.fill(temp, 1);
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

    /**
     * 字符串相关方法
     * 可能是实际开发中最常使用的一些方法
     * 到处都是字符串 :）
     */
    private static void StringMethod() {
        String str = new String("   hello tonglxer.");
        // 去除前后多余空格后按空格分割
        str.trim().split(" ");
        /**
         * String.trim() 可以去除字符串前后的“半角”空白字符
         * String.strip() 可以去除字符串前后的“全角和半角”空白字符
         */
        str.strip();
        // 判断是否以xx开始或结尾
        str.startsWith(" ");
        str.endsWith("er.");
        // 以指定编码获取字符串的字节数组
//        str.getBytes("UTF-8");
        // 正则表达式匹配，匹配成功返回true，反之则然
        str.matches("[a-zA-Z0-9_-]+");
        // 拼接成新串："   hello tonglxer.I love u."
        str.concat("I love u.");
    }

}
