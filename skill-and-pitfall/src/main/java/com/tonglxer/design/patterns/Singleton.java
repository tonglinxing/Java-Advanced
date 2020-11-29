package com.tonglxer.design.patterns;

/**
 * 单例模式的几种写法
 * 只写了常用的四种
 * 其他的要么线程不安全 要么效率低 要么类似
 *
 * (请忽略类的static修饰符)
 *
 * @Author Tong LinXing
 * @date 2020/11/25
 */
public class Singleton {
    // 枚举类单例的测试
    public static void main(String[] args) {
        Singleton4.INSTANCE.showEnum();
    }

    /**
     * 基础写法 -> 饿汉模式
     *
     * 平时比较常用的一个写法，但是不满足懒加载要求。
     * */
    public static class Singleton1 {
        private static Singleton1 instance = new Singleton1();
        // 单例要把构造函数设为私有，否则没有单例的意义
        private Singleton1() {
        }
        public static Singleton1 getInstance() {
            return instance;
        }
    }

    /**
     * 静态内部类 -> 懒汉模式
     *
     * 相较于双重检查锁，既保持了懒加载的特性又使得代码简洁
     *
     * 延迟初始化。调用getInstance()才初始化Singleton对象
     * 因为静态内部类在被使用时才会被加载而不是像普通静态资源一样在类加载时被加载。
     * */
    public static class Singleton2 {
        private Singleton2() {
        }
        private static Singleton2 getInstance() {
            return Inner.instance;
        }
        private static class Inner {
            private static final Singleton2 instance = new Singleton2();
        }
    }

    /**
     * 双重检查锁单例 -> 懒汉模式
     *
     *
     * 1. 为什么用volatile关键字修饰instance？
     * <code>instance = new Singleton3();</code>
     * JVM可能会对这段代码的指令重排，volatile可以禁止重排
     * 以防止被其他线程访问到还未初始化完成的对象
     *
     * 2. 为什么需要双重判空？
     * 第一次判空后到进入同步代码块这段时间内可能会有另一个线程刚好完成了实例创建
     * 故需要在进入同步代码块后再一次判空
     * */
    public static class Singleton3 {
        private volatile static Singleton3 instance;
        private Singleton3() {
        }
        public static Singleton3 getInstance() {
            if (instance == null) {
                synchronized (Singleton3.class) {
                    if (instance == null) {
                        instance = new Singleton3();
                    }
                }
            }
            return instance;
        }
    }


    /**
     * 枚举类(小而美的一种写法) -> 饿汉模式
     *
     * 上述其他写法都有一个问题：
     * 可以通过Java的反射机制获取构造函数生成多个实例
     * 而该写法，JVM会阻止反射获取枚举类的私有构造方法。
     * */
    public static enum Singleton4 {
        INSTANCE;
        public void showEnum() {
            System.out.println("Hello, I am enum singleton.");
        }
    }
}
