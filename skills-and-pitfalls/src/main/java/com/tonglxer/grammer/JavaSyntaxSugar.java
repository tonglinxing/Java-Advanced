package com.tonglxer.grammer;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * 一些Java的语法特性
 * 提升编程效率
 *
 * 主要是Java8开始新增的语法糖
 *
 * @Author Tong LinXing
 * @date 2020/11/24
 */
public class JavaSyntaxSugar {

    public static void main(String[] args) {
        lambdaExample();
        streamExample();
        System.out.println(optionalExample(
                new Student(24, "tonglinxing")));
    }

    /**
     * lambda表达式
     * (parameters) -> expression
     * or (parameters) ->{ statements; }
     * 支持lambda的接口使用@FunctionalInterface标记
     * 且只包含一个方法
     *
     * 例子是利用lambda实现了Runnable接口
     * */
    private static void lambdaExample() {
        new Thread(() -> {
            System.out.println("I am a lambda expression.");
        }).start();
    }

    /**
     * Stream 流
     * 获取数据源-->转换成Stream-->执行操作
     * 返回一个新的Stream-->再以新的Stream继续执行操作
     * 直至最后操作输出最终结果
     *
     * */
    private static void streamExample() {
        List<Integer> age = new LinkedList<>();
        age.add(3);
        age.add(1);
        age.add(2);
        /**
         * 1. 将集合（此处是列表）映射成数据流
         * 2. 排序（可以传入Comparator参数进行自定义规则排序）
         * 3. 过滤条件（这里也是lambda写法，过滤大于1的数）
         * 4. 打印过滤后的结果
         * （案例输出： 2 3）
         * */
        System.out.println("I am a stream example.");
        age.stream().sorted().filter(a -> a>1).forEach(g -> {
            System.out.print(g);
        });
        System.out.println();
    }

    /**
     * Optional可以理解成一个数据容器，甚至可以封装null，
     * 如果值存在调用isPresent()方法会返回true
     * 可用于解决空指针异常，代替繁琐的if-else判断
     *
     * */
    private static String optionalExample(Student student) {
//        if (student != null) {
//            return student.getName();
//        }
//        return null;
        /** 上述代码的简单替换 */
        Optional<Student> stu = Optional.ofNullable(student);
        // Student::getName 方法引用的写法
        return stu.map(Student::getName).orElse(null);
    }
    
    // 匿名静态内部类，用于demo测试
    @Data
    @AllArgsConstructor
    private static class Student {
        int age;
        String name;
    }

}
