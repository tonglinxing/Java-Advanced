package com.tonglxer.grammer.common;

import java.math.BigDecimal;

/**
 * 针对精确计算的场景，如金融相关
 * 应当注意些什么以及采用什么方案？
 *
 *
 * @Author Tong LinXing
 * @date 2020/11/29
 */
public class BigDecimalDemo {

    public static void main(String[] args) {
        bigDecimal();
        floatOrDouble();
    }

    /**
     * 构造BigDecimal对象的三种方法：
     *
     * //不允许使用
     * 1. BigDecimal BigDecimal(double d);
     * //常用,推荐使用
     * 2. BigDecimal BigDecimal(String s);
     * //常用,推荐使用
     * 3. static BigDecimal valueOf(double d);
     *
     * */
    private static void bigDecimal() {
        System.out.println("BigDecimal构造函数使用不当的坑： \n");
        double d1 = 3.1415926;
        double d2 = 131452.0;

        System.out.println("new BigDecimal("+d1+")=" + new BigDecimal(d1));
        System.out.println("new BigDecimal("+d2+")=" + new BigDecimal(d2));

        System.out.println("new BigDecimal(String.valueOf("+d1+"))=" + new BigDecimal(String.valueOf(d1)));
        System.out.println("new BigDecimal(String.valueOf("+d2+"))=" + new BigDecimal(String.valueOf(d2)));

        System.out.println("BigDecimal.valueOf("+d1+")=" + BigDecimal.valueOf(d1));
        System.out.println("BigDecimal.valueOf("+d2+")=" + BigDecimal.valueOf(d2));

        BigDecimal b1 = BigDecimal.valueOf(1);
        BigDecimal b2 = BigDecimal.valueOf(1.00000);
        // equals方法要求两个对象完全一样
        System.out.println(b1.equals(b2));
        // compareTo方法可以忽略精度进行比较
        System.out.println(b1.compareTo(b2));
    }

    // TODO: 2020/12/2 需要给出原因
    private static void floatOrDouble() {
        System.out.println("\nFloat 和 Double 的精度缺失问题： ");
        // 四则运算结果错误
        System.out.println("1.0 - 0.9 = " + (1.0-0.9));

        // 赋值精度问题
        float a = 2.1f;
        double b = 3.3;
        System.out.println("\na = " + a + " b = " + b);
        b = a;
        System.out.println("let b = a, then b = " + b);
    }
}

