package com.tonglxer.grammer.common;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Java的反射机制：
 * 对于任意一个类，都能够知道这个类的所有属性和方法，
 * 对于任意一个对象，都能够调用它的任意一个方法和属性。
 *
 * @Author Tong LinXing
 * @date 2020/11/26
 */
@Slf4j
public class Reflection {

    public static void reflectionDemo() {
        Student instance = new Student(24, "TongLinXing");
        /**
         * 1. 成员变量和方法的获取
         * */
        Class clazz = instance.getClass();
        // 获取类public的成员变量, 包含父类
        Field[] publicFields = clazz.getFields();
        // 获取类所有成员变量, 不包含父类
        Field[] fields = clazz.getDeclaredFields();
        // 获取类public的方法, 包含父类
        Method[] publicMethods = clazz.getMethods();
        // 获取类的方法, 不包含父类
        Method[] methods = clazz.getDeclaredMethods();

        /**
         * 2. 访问私有方法
         * */
        try {
            Method method = clazz.getDeclaredMethod("show");
            // 获取私有方法的访问权（单纯获取权限而非修改权限）
            method.setAccessible(true);
            method.invoke(instance);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            log.error("Method have some error: ", e);
        }

        /**
         * 3. 修改私有变量
         * */
        try {
            Field field = clazz.getDeclaredField("name");
            System.out.println("The old name is : " + instance.getName());
            field.setAccessible(true);
            field.set(instance, "tonglxer");
            System.out.println("The new name is : " + instance.getName());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            log.error("Field have some error: ", e);
        }
    }

}
